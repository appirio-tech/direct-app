/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.payment.ProjectPayment;
import com.topcoder.management.payment.ProjectPaymentAdjustment;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.ProjectPaymentType;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.SpecificationFactoryException;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;

/**
 * <p>
 * Helper class for the component. It provides useful common methods for all the classes in this component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author sparemax
 * @version 1.0.1
 */
public final class Helper {
    /**
     * <p>
     * Represents the child key 'objectFactoryConfig'.
     * </p>
     */
    private static final String KEY_OF_CONFIG = "objectFactoryConfig";

    /**
     * <p>
     * Represents the property key 'loggerName'.
     * </p>
     */
    private static final String KEY_LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Creates the persistence.
     * </p>
     *
     * @param <T>
     *            the object type.
     * @param targetType
     *            the target type.
     * @param config
     *            the configuration object.
     * @param persistenceKey
     *            the persistence key.
     * @param configKey
     *            the configuration object key.
     *
     * @return the created object.
     *
     * @throws ProjectPaymentManagementConfigurationException
     *             if any error occurs when creating object.
     */
    @SuppressWarnings("unchecked")
    static <T> T createPersistence(Class<?> targetType, ConfigurationObject config, String persistenceKey,
        String configKey) {
        try {
            // Create object factory
            ObjectFactory objectFactory = new ObjectFactory(new ConfigurationObjectSpecificationFactory(Helper
                .getChildConfig(config, KEY_OF_CONFIG)));

            String key = Helper.getProperty(config, persistenceKey, true);

            // Create the object
            Object obj = objectFactory.createObject(key);

            if (!targetType.isAssignableFrom(obj.getClass())) {
                throw new ProjectPaymentManagementConfigurationException(
                    "The created object is not of correct type: " + targetType.getName());
            }

            ConfigurationObject childConfig = Helper.getChildConfig(config, configKey);

            if (ProjectPaymentAdjustmentPersistence.class.isAssignableFrom(targetType)) {
                ((ProjectPaymentAdjustmentPersistence) obj).configure(childConfig);
            } else {
                ((ProjectPaymentPersistence) obj).configure(childConfig);
            }

            return (T) obj;

        } catch (SpecificationFactoryException e) {
            throw new ProjectPaymentManagementConfigurationException(
                "Failed to create an instance of ConfigurationObjectSpecificationFactory.", e);
        } catch (InvalidClassSpecificationException e) {
            throw new ProjectPaymentManagementConfigurationException("An error occurs when creating object.", e);
        }
    }

    /**
     * Gets the configuration object.
     *
     * @param filePath
     *            the path of the configuration file
     * @param namespace
     *            the configuration namespace
     *
     * @return the configuration object.
     *
     * @throws IllegalArgumentException
     *             if filePath or namespace is null/empty
     * @throws ProjectPaymentManagementConfigurationException
     *             if error occurred while reading the configuration
     */
    static ConfigurationObject getConfiguration(String filePath, String namespace) {
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(filePath, "filePath");
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(namespace, "namespace");

        try {
            // Create configuration file manager:
            ConfigurationFileManager manager = new ConfigurationFileManager(filePath);

            // Get configuration object from the manager:
            ConfigurationObject configuration = manager.getConfiguration(namespace);
            // Get configuration of this class:
            return Helper.getChildConfig(configuration, namespace);
        } catch (ConfigurationPersistenceException e) {
            throw new ProjectPaymentManagementConfigurationException("Failed to create get configuration object.", e);
        } catch (IOException e) {
            throw new ProjectPaymentManagementConfigurationException("An I/O error has occurred.", e);
        }
    }

    /**
     * Executes the SQL.
     *
     * @param log
     *            the logger
     * @param signature
     *            the signature
     * @param connection
     *            the connection
     * @param returnsId
     *            <code>true</code> indicating that generated key should be returned; <code>false</code>
     *            otherwise.
     * @param sql
     *            the SQL string
     * @param params
     *            the parameters
     *
     * @return the id or update count when returnsId is false
     *
     * @throws SQLException
     *             if a database access error occurs.
     */
    public static long executeUpdate(Log log, String signature, Connection connection, boolean returnsId, String sql,
        Object... params) throws SQLException {
        PreparedStatement preparedStatement;
        if (returnsId) {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            preparedStatement = connection.prepareStatement(sql);
        }
        try {
            // Set parameters
            setParameters(preparedStatement, params);

            // Execute
            int count = preparedStatement.executeUpdate();

            if (returnsId) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                return resultSet.getLong(1);
            }

            return count;
        } finally {
            // Close the prepared statement
            closeStatement(log, signature, preparedStatement);
        }
    }

    /**
     * Create and return a database connection.
     *
     * @param log
     *            the logger
     * @param signature
     *            the signature
     * @param dbConnectionFactory
     *            the connection factory
     * @param connectionName
     *            the connection name
     * @param autoCommit
     *            the auto commit flag
     *
     * @return the database connection
     *
     * @throws IllegalStateException
     *             if the instance is not configured yet (dbConnectionFactory is null).
     * @throws ProjectPaymentManagementPersistenceException
     *             if any error occurs while creating the connection.
     * @throws SQLException
     *             if any other error occurs
     */
    public static Connection getConnection(Log log, String signature, DBConnectionFactory dbConnectionFactory,
        String connectionName, boolean autoCommit) throws ProjectPaymentManagementPersistenceException, SQLException {

        try {
            ValidationUtility.checkNotNull(dbConnectionFactory, "dbConnectionFactory", IllegalStateException.class);

            Connection connection;
            if (connectionName == null) {
                connection = dbConnectionFactory.createConnection();
            } else {
                connection = dbConnectionFactory.createConnection(connectionName);
            }
            try {
                connection.setAutoCommit(autoCommit);
            } catch (SQLException e){
                //transaction has been managed
            }

            return connection;
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (DBConnectionException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature,
                new ProjectPaymentManagementPersistenceException("Failed to get a database connection.", e));
        }
    }

    /**
     * Closes the statement.
     *
     * @param log
     *            the logger
     * @param signature
     *            the signature
     * @param statement
     *            the statement (not null)
     */
    public static void closeStatement(Log log, String signature, Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            // Log exception
            LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Closes the connection.
     *
     * @param log
     *            the logger
     * @param signature
     *            the signature
     * @param connection
     *            the connection (not null)
     */
    public static void closeConnection(Log log, String signature, Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            // Log exception
            LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * <p>
     * Gets a Log instance with the configuration object.
     * </p>
     *
     * @param config
     *            the configuration object.
     *
     * @return the Log instance or <code>null</code> if the logger name is not specified.
     *
     * @throws ProjectPaymentManagementConfigurationException
     *             if 'loggerName' is not a string, an empty string or some error occurred.
     */
    public static Log getLog(ConfigurationObject config) {
        String loggerName = getProperty(config, KEY_LOGGER_NAME, false);
        return (loggerName == null) ? null : LogManager.getLog(loggerName);

    }

    /**
     * <p>
     * Gets a child ConfigurationObject from given configuration object.
     * </p>
     *
     * @param config
     *            the given configuration object.
     * @param key
     *            the child name.
     *
     * @return the retrieved child ConfigurationObject.
     *
     * @throws ProjectPaymentManagementConfigurationException
     *             if the child ConfigurationObject is missing or some error occurred.
     */
    public static ConfigurationObject getChildConfig(ConfigurationObject config, String key) {
        try {
            ConfigurationObject child = config.getChild(key);

            if (child == null) {
                throw new ProjectPaymentManagementConfigurationException("The child '" + key + "' of '"
                    + config.getName() + "' is required.");
            }

            return child;
        } catch (ConfigurationAccessException e) {
            throw new ProjectPaymentManagementConfigurationException(
                "An error occurred while accessing the configuration.", e);
        }
    }

    /**
     * <p>
     * Gets a property value from given configuration object.
     * </p>
     *
     * @param config
     *            the given configuration object.
     * @param key
     *            the property key.
     * @param required
     *            the flag indicates whether the property is required.
     *
     * @return the retrieved property value or <code>null</code> if the optional property is not present.
     *
     * @throws ProjectPaymentManagementConfigurationException
     *             if the property is missing when required is <code>true</code>, not a string, an empty string or
     *             some error occurred.
     */
    public static String getProperty(ConfigurationObject config, String key, boolean required) {
        try {
            if (!config.containsProperty(key)) {
                if (required) {
                    throw new ProjectPaymentManagementConfigurationException("The property '" + key
                        + "' is required.");
                }

                // Return the default value
                return null;
            }

            Object valueObj = config.getPropertyValue(key);

            if (!(valueObj instanceof String)) {
                throw new ProjectPaymentManagementConfigurationException("The property '" + key
                    + "' should be a String.");
            }

            String valueStr = ((String) valueObj).trim();

            if (valueStr.length() == 0) {
                // The value is empty
                throw new ProjectPaymentManagementConfigurationException("The property '" + key
                    + "' can not be empty.");
            }

            return valueStr;
        } catch (ConfigurationAccessException e) {
            throw new ProjectPaymentManagementConfigurationException(
                "An error occurred while accessing the configuration.", e);
        }
    }

    /**
     * Converts the object to string.
     *
     * @param obj
     *            the object
     *
     * @return the string representation of the object.
     */
    public static String toString(Object obj) {
        if (obj instanceof ProjectPaymentType) {
            return toString((ProjectPaymentType) obj);
        } else if (obj instanceof ProjectPayment) {
            return toString((ProjectPayment) obj);
        } else if (obj instanceof ProjectPaymentAdjustment) {
            return toString((ProjectPaymentAdjustment) obj);
        } else if (obj instanceof List<?>) {
            return toString((List<?>) obj);
        }

        return String.valueOf(obj);
    }

    /**
     * <p>
     * Converts the ProjectPaymentAdjustment object to a string.
     * </p>
     *
     * @param obj
     *            the object
     *
     * @return the string
     */
    private static String toString(ProjectPaymentAdjustment obj) {
        return toString(obj.getClass().getName(),
            new String[] {"projectId", "resourceRoleId", "fixedAmount", "multiplier"},
            new Object[] {obj.getProjectId(), obj.getResourceRoleId(), obj.getFixedAmount(), obj.getMultiplier()});
    }

    /**
     * <p>
     * Converts the ProjectPaymentType object to a string.
     * </p>
     *
     * @param obj
     *            the object
     *
     * @return the string
     */
    private static String toString(ProjectPaymentType obj) {
        return toString(obj.getClass().getName(),
            new String[] {"projectPaymentTypeId", "name", "mergeable", "pactsPaymentTypeId"},
            new Object[] {obj.getProjectPaymentTypeId(), obj.getName(), obj.isMergeable(), obj.getPactsPaymentTypeId()});
    }

    /**
     * <p>
     * Converts the ProjectPayment object to a string.
     * </p>
     *
     * @param obj
     *            the object
     *
     * @return the string
     */
    private static String toString(ProjectPayment obj) {
        return toString(obj.getClass().getName(),
            new String[] {"projectPaymentId", "projectPaymentType", "resourceId",
                "submissionId", "amount", "pactsPaymentId", "createUser", "createDate", "modifyUser", "modifyDate"},
            new Object[] {obj.getPactsPaymentId(), toString((Object) obj.getProjectPaymentType()), obj.getResourceId(),
                obj.getSubmissionId(), obj.getAmount(), obj.getPactsPaymentId(),
                obj.getCreateUser(), obj.getCreateDate(), obj.getModifyUser(), obj.getModifyDate()});
    }

    /**
     * Converts the List to a string.
     *
     * @param obj
     *            the List.
     *
     * @return the string.
     */
    private static String toString(List<?> obj) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        boolean first = true;
        for (Object element : obj) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }

            sb.append(toString(element));
        }

        sb.append("]");

        return sb.toString();
    }

    /**
     * Converts the fields to a string.
     *
     * @param className
     *            the class name.
     * @param fields
     *            the fields.
     * @param values
     *            the values of fields.
     *
     * @return the string.
     */
    private static String toString(String className, String[] fields, Object[] values) {
        StringBuilder sb = new StringBuilder();

        sb.append(className).append("{");

        int num = fields.length;
        for (int i = 0; i < num; i++) {
            if (i != 0) {
                // Append a comma
                sb.append(", ");
            }

            sb.append(fields[i]).append(":").append(values[i]);
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * Sets the parameters.
     *
     * @param preparedStatement
     *            the prepared statement
     * @param params
     *            the parameters
     *
     * @throws SQLException
     *             if any error occurs
     */
    private static void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        // Set parameters
        for (int i = 0; i < params.length; i++) {
            Object paramValue = params[i];

            preparedStatement.setObject(i + 1, paramValue);
        }
    }
}
