/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.ProjectPaymentAdjustment;
import com.topcoder.management.payment.ProjectPaymentAdjustmentValidationException;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.impl.Helper;
import com.topcoder.management.payment.impl.ProjectPaymentAdjustmentPersistence;
import com.topcoder.management.payment.impl.ProjectPaymentManagementPersistenceException;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of ProjectPaymentAdjustmentPersistence that uses JDBC and DB Connection Factory
 * component. This class uses Logging Wrapper component to log errors and debug information.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when configure() method is called just once
 * right after instantiation and entities passed to it are used by the caller in thread safe manner. It uses thread
 * safe DBConnectionFactory and Log instances.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
public class DatabaseProjectPaymentAdjustmentPersistence extends BaseProjectPaymentManagementPersistence implements
    ProjectPaymentAdjustmentPersistence {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DatabaseProjectPaymentAdjustmentPersistence.class.getName();

    /**
     * <p>
     * Represents the SQL string to update payment adjustment.
     * </p>
     */
    private static final String SQL_UPDATE_PAYMENT_ADJUSTMENT = "UPDATE project_payment_adjustment"
        + " SET fixed_amount = ?, multiplier = ? WHERE project_id = ? AND resource_role_id = ?";

    /**
     * <p>
     * Represents the SQL string to insert payment adjustment.
     * </p>
     */
    private static final String SQL_INSERT_PAYMENT_ADJUSTMENT = "INSERT INTO project_payment_adjustment"
        + " (project_id, resource_role_id, fixed_amount, multiplier) VALUES (?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL string to query payment adjustment.
     * </p>
     */
    private static final String SQL_QUERY_PAYMENT_ADJUSTMENT = "SELECT project_id, resource_role_id,"
        + " fixed_amount, multiplier FROM project_payment_adjustment where project_id = ?";

    /**
     * Creates an instance of DatabaseProjectPaymentAdjustmentPersistence.
     */
    public DatabaseProjectPaymentAdjustmentPersistence() {
        // Empty
    }

    /**
     * Creates or updates the given project payment adjustment in persistence.
     *
     * @param projectPaymentAdjustment
     *            the project payment adjustment to create or update
     *
     * @throws IllegalArgumentException
     *             if projectPaymentAdjustment is null
     * @throws ProjectPaymentAdjustmentValidationException
     *             if projectPaymentAdjustment's project id is null, projectPaymentAdjustment's resource role id is
     *             null, projectPaymentAdjustment's fixed amount is negative, projectPaymentAdjustment's multiplier is
     *             negative, if both fixed amount and multiplier are non-null at the same time.
     * @throws ProjectPaymentManagementPersistenceException
     *             if some other error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null)
     */
    public void save(ProjectPaymentAdjustment projectPaymentAdjustment)
        throws ProjectPaymentAdjustmentValidationException, ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".save(ProjectPaymentAdjustment projectPaymentAdjustment)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectPaymentAdjustment"},
            new Object[] {Helper.toString(projectPaymentAdjustment)});

        try {
            // Validate the parameter
            validateProjectPaymentAdjustment(projectPaymentAdjustment);

            Connection connection = Helper.getConnection(log, signature, getDbConnectionFactory(),
                getConnectionName(), false);

            try {

                // Update
                if (Helper.executeUpdate(log, signature, connection, false, SQL_UPDATE_PAYMENT_ADJUSTMENT,
                    projectPaymentAdjustment.getFixedAmount(), projectPaymentAdjustment.getMultiplier(),
                    projectPaymentAdjustment.getProjectId(), projectPaymentAdjustment.getResourceRoleId()) == 0) {
                    // Insert
                    Helper.executeUpdate(log, signature, connection, false, SQL_INSERT_PAYMENT_ADJUSTMENT,
                        projectPaymentAdjustment.getProjectId(), projectPaymentAdjustment.getResourceRoleId(),
                        projectPaymentAdjustment.getFixedAmount(), projectPaymentAdjustment.getMultiplier());
                }

                connection.commit();

            } catch (SQLException e) {
                // Roll back
                rollback(log, signature, connection);

                throw e;
            } finally {
                // Close the connection:
                Helper.closeConnection(log, signature, connection);
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentAdjustmentValidationException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility
                .logException(log, signature, new ProjectPaymentManagementPersistenceException(
                    "Failed to save the project payment adjustment.", e));
        }
    }

    /**
     * Retrieves the given project payment adjustments from persistence by project id.
     *
     * @param projectId
     *            the project id of the project payment adjustment
     *
     * @return the list or project payment adjustments matching the criteria, empty list if nothing was found.
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null)
     */
    public List<ProjectPaymentAdjustment> retrieveByProjectId(long projectId)
        throws ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".retrieveByProjectId(long projectId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectId"},
            new Object[] {projectId});

        try {
            Connection connection = Helper.getConnection(log, signature, getDbConnectionFactory(),
                getConnectionName(), true);

            try {

                // Create a prepared statement:
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_PAYMENT_ADJUSTMENT);
                try {
                    preparedStatement.setLong(1, projectId);
                    // Execute the statement
                    ResultSet resultSet = preparedStatement.executeQuery();

                    List<ProjectPaymentAdjustment> result = new ArrayList<ProjectPaymentAdjustment>();
                    while (resultSet.next()) {
                        ProjectPaymentAdjustment projectPaymentAdjustment = new ProjectPaymentAdjustment();
                        int columnIndex = 1;
                        projectPaymentAdjustment.setProjectId(resultSet.getLong(columnIndex++));
                        projectPaymentAdjustment.setResourceRoleId(resultSet.getLong(columnIndex++));
                        projectPaymentAdjustment.setFixedAmount(resultSet.getBigDecimal(columnIndex++));

                        double multiplier = resultSet.getDouble(columnIndex);
                        if (!resultSet.wasNull()) {
                            projectPaymentAdjustment.setMultiplier(multiplier);
                        }

                        result.add(projectPaymentAdjustment);
                    }

                    // Log Exit
                    LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(result)},
                        entranceTimestamp);
                    return result;
                } finally {
                    // Close the prepared statement
                    Helper.closeStatement(log, signature, preparedStatement);
                }

            } finally {
                // Close the connection:
                Helper.closeConnection(log, signature, connection);
            }
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature,
                new ProjectPaymentManagementPersistenceException(
                    "Failed to retrieve the project payment adjustments.", e));
        }
    }

    /**
     * Configures the instance with use of the given configuration object.
     *
     * @param configuration
     *            the configuration object
     *
     * @throws IllegalArgumentException
     *             if configuration is null
     * @throws ProjectPaymentManagementConfigurationException
     *             if some error occurred when initializing an instance using the given configuration (is not thrown
     *             by implementations that don't use any configuration parameters)
     */
    public void configure(ConfigurationObject configuration) {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".configure(ConfigurationObject configuration)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"configuration"},
            new Object[] {configuration});

        try {
            super.configure(configuration);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentManagementConfigurationException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Validates the project payment adjustment.
     *
     * @param projectPaymentAdjustment
     *            the project payment adjustment.
     *
     * @throws IllegalArgumentException
     *             if projectPaymentAdjustment is null
     * @throws ProjectPaymentAdjustmentValidationException
     *             if projectPaymentAdjustment's project id is null, projectPaymentAdjustment's resource role id is
     *             null, projectPaymentAdjustment's fixed amount is negative, projectPaymentAdjustment's multiplier is
     *             negative, if both fixed amount and multiplier are non-null at the same time.
     */
    private static void validateProjectPaymentAdjustment(ProjectPaymentAdjustment projectPaymentAdjustment)
        throws ProjectPaymentAdjustmentValidationException {
        ParameterCheckUtility.checkNotNull(projectPaymentAdjustment, "projectPaymentAdjustment");

        // Validate the input parameter
        ValidationUtility.checkNotNull(projectPaymentAdjustment.getProjectId(),
            "projectPaymentAdjustment.getProjectId()", ProjectPaymentAdjustmentValidationException.class);
        ValidationUtility.checkNotNull(projectPaymentAdjustment.getResourceRoleId(),
            "projectPaymentAdjustment.getResourceRoleId()", ProjectPaymentAdjustmentValidationException.class);

        BigDecimal fixedAmount = projectPaymentAdjustment.getFixedAmount();
        boolean isFixedAmountNotNull = (fixedAmount != null);
        if (isFixedAmountNotNull) {
            if (fixedAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new ProjectPaymentAdjustmentValidationException(
                    "projectPaymentAdjustment.getFixedAmount() should be not negative.");
            }
        }

        Double multiplier = projectPaymentAdjustment.getMultiplier();
        boolean isMultiplierNotNull = (projectPaymentAdjustment.getMultiplier() != null);
        if (isMultiplierNotNull) {
            ValidationUtility.checkNotNegative(multiplier, "projectPaymentAdjustment.getMultiplier()",
                ProjectPaymentAdjustmentValidationException.class);

            if (isFixedAmountNotNull) {
                throw new ProjectPaymentAdjustmentValidationException(
                    "projectPaymentAdjustment.getFixedAmount() and projectPaymentAdjustment.getMultiplier()"
                        + " should not be non-null at the same time.");
            }
        }
    }

    /**
     * Rolls back.
     *
     * @param log
     *            the logger
     * @param signature
     *            the signature
     * @param connection
     *            the connection (not null)
     */
    private static void rollback(Log log, String signature, Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            // Log exception
            LoggingWrapperUtility.logException(log, signature, e);
        }
    }
}
