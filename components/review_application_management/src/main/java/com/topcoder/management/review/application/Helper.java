/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.management.review.application.impl.persistence.BaseDatabasePersistence;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
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
 * @version 1.0
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
     * Represents the child key 'persistenceConfig'.
     * </p>
     */
    private static final String KEY_PERSISTENCE_CONFIG = "persistenceConfig";

    /**
     * <p>
     * Represents the property key 'loggerName'.
     * </p>
     */
    private static final String KEY_LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Represents the property key 'searchBundleManagerNamespace'.
     * </p>
     */
    private static final String KEY_SBM_NAMESPACE = "searchBundleManagerNamespace";

    /**
     * <p>
     * Represents the property key 'persistenceKey'.
     * </p>
     */
    private static final String KEY_PERSISTENCE_KEY = "persistenceKey";

    /**
     * <p>
     * Prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * Executes the SQL.
     *
     * @param <T>
     *            the exception type.
     * @param log
     *            the logger
     * @param signature
     *            the signature
     * @param eClass
     *            the exception class.
     * @param connection
     *            the connection
     * @param returnsId
     *            <code>true</code> indicating that generated key should be returned; <code>false</code> otherwise.
     * @param sql
     *            the SQL string
     * @param params
     *            the parameters
     *
     * @return the id or 0 when returnsId is false
     *
     * @throws SQLException
     *             if a database access error occurs.
     * @throws T
     *             if no data was updated.
     */
    public static <T extends ReviewApplicationManagementException> long executeUpdate(Log log, String signature,
        Class<T> eClass, Connection connection, boolean returnsId, String sql, Object... params)
        throws SQLException, T {
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
            ValidationUtility.checkPositive(count, "update count", eClass);

            if (returnsId) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                return resultSet.getLong(1);
            }

            // Not used value
            return 0;
        } finally {
            // Close the prepared statement
            closeStatement(log, signature, preparedStatement);
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
     * Creates the persistence.
     * </p>
     *
     * @param <T>
     *            the object type.
     * @param targetType
     *            the target type.
     * @param config
     *            the configuration object.
     *
     * @return the created object.
     *
     * @throws ReviewApplicationManagementConfigurationException
     *             if any error occurs when creating object.
     */
    @SuppressWarnings("unchecked")
    public static <T> T createPersistence(Class<?> targetType, ConfigurationObject config) {
        try {
            // Create object factory
            ObjectFactory objectFactory = new ObjectFactory(new ConfigurationObjectSpecificationFactory(
                Helper.getChildConfig(config, KEY_OF_CONFIG)));

            String key = Helper.getProperty(config, KEY_PERSISTENCE_KEY, true);

            // Create the object
            Object obj = objectFactory.createObject(key);

            if (!targetType.isAssignableFrom(obj.getClass())) {
                throw new ReviewApplicationManagementConfigurationException(
                    "The created object is not of correct type: " + targetType.getName());
            }

            ConfigurationObject childConfig = Helper.getChildConfig(config, KEY_PERSISTENCE_CONFIG);

            ((BaseDatabasePersistence) obj).configure(childConfig);

            return (T) obj;

        } catch (SpecificationFactoryException e) {
            throw new ReviewApplicationManagementConfigurationException(
                "Failed to create an instance of ConfigurationObjectSpecificationFactory.", e);
        } catch (InvalidClassSpecificationException e) {
            throw new ReviewApplicationManagementConfigurationException("An error occurs when creating object.", e);
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
     *@return the Log instance or <code>null</code> if the logger name is not specified.
     *
     * @throws ReviewApplicationManagementConfigurationException
     *             if 'loggerName' is not a string, an empty string or some error occurred.
     */
    public static Log getLog(ConfigurationObject config) {
        String loggerName = getProperty(config, KEY_LOGGER_NAME, false);
        return (loggerName == null) ? null : LogManager.getLog(loggerName);

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
     * @throws ReviewApplicationManagementConfigurationException
     *             if error occurred while reading the configuration
     */
    public static ConfigurationObject getConfiguration(String filePath, String namespace) {
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
            throw new ReviewApplicationManagementConfigurationException(
                "Failed to create configuration file manager.", e);
        } catch (IOException e) {
            throw new ReviewApplicationManagementConfigurationException(
                "Failed to create configuration file manager.", e);
        }
    }

    /**
     * <p>
     * Creates the SearchBundleManager object.
     * </p>
     *
     * @param config
     *            the configuration.
     *
     * @return the search bundle manager.
     *
     * @throws ReviewApplicationManagementConfigurationException
     *             if any error occurs.
     */
    public static SearchBundleManager getSearchBundleManager(ConfigurationObject config) {
        // Get search bundle manager namespace from the configuration:
        String searchBundleManagerNamespace = Helper.getProperty(config, KEY_SBM_NAMESPACE, true);

        try {
            return new SearchBundleManager(searchBundleManagerNamespace);
        } catch (SearchBuilderConfigurationException e) {
            throw new ReviewApplicationManagementConfigurationException("Fails to create search bundle manager.", e);
        }
    }

    /**
     * <p>
     * Gets a search bundle from the SearchBundleManager object.
     * </p>
     *
     * @param searchBundleManager
     *            the SearchBundleManager object.
     * @param config
     *            the configuration.
     * @param key
     *            the key to retrieve the name of search bundle from the configuration.
     *
     * @return the search bundle.
     *
     * @throws ReviewApplicationManagementConfigurationException
     *             if any error occurs.
     */
    public static SearchBundle getSearchBundle(SearchBundleManager searchBundleManager,
        ConfigurationObject config, String key) {
        String name = Helper.getProperty(config, key, true);

        SearchBundle searchBundle = searchBundleManager.getSearchBundle(name);

        if (searchBundle == null) {
            throw new ReviewApplicationManagementConfigurationException(
                "There is no search bundle found with the name '" + name + "'.");
        }

        return searchBundle;
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
     * @throws ReviewApplicationManagementConfigurationException
     *             if the child ConfigurationObject is missing or some error occurred.
     */
    public static ConfigurationObject getChildConfig(ConfigurationObject config, String key) {
        try {
            ConfigurationObject child = config.getChild(key);

            if (child == null) {
                throw new ReviewApplicationManagementConfigurationException("The child '" + key + "' of '"
                    + config.getName() + "' is required.");
            }

            return child;
        } catch (ConfigurationAccessException e) {
            throw new ReviewApplicationManagementConfigurationException(
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
     * @throws ReviewApplicationManagementConfigurationException
     *             if the property is missing when required is <code>true</code>, not a string, an empty string or
     *             some error occurred.
     */
    public static String getProperty(ConfigurationObject config, String key, boolean required) {
        try {
            if (!config.containsProperty(key)) {
                if (required) {
                    throw new ReviewApplicationManagementConfigurationException("The property '" + key
                        + "' is required.");
                }

                // Return the default value
                return null;
            }

            Object valueObj = config.getPropertyValue(key);

            if (!(valueObj instanceof String)) {
                throw new ReviewApplicationManagementConfigurationException("The property '" + key
                    + "' should be a String.");
            }

            String valueStr = ((String) valueObj).trim();

            if (valueStr.length() == 0) {
                // The value is empty
                throw new ReviewApplicationManagementConfigurationException("The property '" + key
                    + "' can not be empty.");
            }

            return valueStr;
        } catch (ConfigurationAccessException e) {
            throw new ReviewApplicationManagementConfigurationException(
                "An error occurred while accessing the configuration.", e);
        }
    }

    /**
     * <p>
     * Concatenates the given values to a string.
     * </p>
     *
     * @param values
     *            the values.
     *
     * @return the result.
     */
    static String concat(Object... values) {
        StringBuilder sb = new StringBuilder();

        for (Object value : values) {
            sb.append(value);
        }

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
