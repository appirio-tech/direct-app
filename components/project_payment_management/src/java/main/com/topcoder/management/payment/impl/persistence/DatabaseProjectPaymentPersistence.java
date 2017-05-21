/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.ProjectPayment;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.ProjectPaymentManagementDataIntegrityException;
import com.topcoder.management.payment.ProjectPaymentNotFoundException;
import com.topcoder.management.payment.ProjectPaymentType;
import com.topcoder.management.payment.ProjectPaymentValidationException;
import com.topcoder.management.payment.impl.Helper;
import com.topcoder.management.payment.impl.ProjectPaymentManagementPersistenceException;
import com.topcoder.management.payment.impl.ProjectPaymentPersistence;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of ProjectPaymentPersistence that uses JDBC and DB Connection Factory component.
 * This class uses Logging Wrapper component to log errors and debug information.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when configure() method is called just once
 * right after instantiation and entities passed to it are used by the caller in thread safe manner. It uses thread
 * safe DBConnectionFactory and Log instances.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0.1
 */
public class DatabaseProjectPaymentPersistence extends BaseProjectPaymentManagementPersistence implements
    ProjectPaymentPersistence {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DatabaseProjectPaymentPersistence.class.getName();

    /**
     * <p>
     * Represents the SQL string to insert project payment.
     * </p>
     */
    private static final String SQL_INSERT_PAYMENT = "INSERT INTO project_payment (resource_id, submission_id,"
        + " amount, pacts_payment_id, create_user, create_date, modify_user, modify_date, project_payment_type_id)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL string to update project payment.
     * </p>
     */
    private static final String SQL_UPDATE_PAYMENT = "UPDATE project_payment set project_payment_type_id = ?,"
        + " resource_id = ?, submission_id = ?, amount = ?, pacts_payment_id = ?, modify_user = ?, modify_date = ?"
        + " WHERE project_payment_id = ?";

    /**
     * <p>
     * Represents the SQL string to query project payment.
     * </p>
     */
    private static final String SQL_QUERY_PAYMENT = "SELECT pp.resource_id, pp.submission_id, pp.amount,"
        + " pp.pacts_payment_id, pp.create_user, pp.create_date, pp.modify_user, pp.modify_date,"
        + " ppt.project_payment_type_id, ppt.name, ppt.mergeable"
        + " FROM project_payment pp LEFT JOIN project_payment_type_lu ppt"
        + " on pp.project_payment_type_id = ppt.project_payment_type_id WHERE pp.project_payment_id = ?";

    /**
     * <p>
     * Represents the SQL string to delete project payment.
     * </p>
     */
    private static final String SQL_DELETE_PAYMENT = "DELETE FROM project_payment WHERE project_payment_id = ?";

    /**
     * <p>
     * Represents the SQL string to query foreign key.
     * </p>
     */
    private static final String SQL_QUERY_FK = "SELECT COUNT(*) FROM %1$s WHERE %2$s = ?";

    /**
     * Creates an instance of DatabaseProjectPaymentPersistence.
     */
    public DatabaseProjectPaymentPersistence() {
        // Empty
    }

    /**
     * Creates the given project payment in persistence.
     *
     * @param projectPayment
     *            the project payment with updated data
     * @param operator
     *            ID of the user doing the operation
     *
     * @return the inserted project payment with generated id.
     *
     * @throws IllegalArgumentException
     *             if projectPayment is null
     * @throws ProjectPaymentValidationException
     *             if projectPayment's id is not null, projectPayment's type is null, projectPaymentType's
     *             projectPaymentTypeId is null, projectPayment's resourceId is null, projectPayment's amount is null
     *             or negative, projectPayment's createDate is null.
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if project payment integrity is broken, for example if project payment is being persisted with
     *             payment type that is not present in DB.
     * @throws ProjectPaymentManagementPersistenceException
     *             if some other error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null)
     */
    public ProjectPayment create(ProjectPayment projectPayment, String operator) throws ProjectPaymentValidationException,
        ProjectPaymentManagementDataIntegrityException, ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".create(ProjectPayment projectPayment, String operator)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectPayment", "operator"},
            new Object[] {Helper.toString(projectPayment), operator});

        try {
            // Validate the parameters
            validateProjectPayment(projectPayment, true);
            ParameterCheckUtility.checkNotNullNorEmpty(operator, "operator");

            Connection connection = Helper.getConnection(log, signature, getDbConnectionFactory(),
                getConnectionName(), true);

            try {
                // Check project payment integrity
                checkProjectPaymentIntegrity(log, signature, connection, projectPayment);

                // INSERT
                long projectPaymentId = Helper.executeUpdate(log, signature, connection, true, SQL_INSERT_PAYMENT,
                    projectPayment.getResourceId(), projectPayment.getSubmissionId(), projectPayment.getAmount(),
                    projectPayment.getPactsPaymentId(),
                    operator, new Timestamp(new Date().getTime()),
                    operator, new Timestamp(new Date().getTime()),
                    projectPayment.getProjectPaymentType().getProjectPaymentTypeId());

                projectPayment.setProjectPaymentId(projectPaymentId);

            } finally {
                // Close the connection:
                Helper.closeConnection(log, signature, connection);
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(projectPayment)},
                entranceTimestamp);
            return projectPayment;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentValidationException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentManagementDataIntegrityException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature,
                new ProjectPaymentManagementPersistenceException("Failed to create the project payment.", e));
        }
    }

    /**
     * Updates the given project payment in persistence.
     *
     * @param projectPayment
     *            the project payment with updated data
     * @param operator
     *            ID of the user doing the operation
     *
     * @throws IllegalArgumentException
     *             if projectPayment is null
     * @throws ProjectPaymentValidationException
     *             if projectPayment's id is null or negative, projectPayment's type is null, projectPaymentType's
     *             projectPaymentTypeId is null, projectPayment's resourceId is null, projectPayment's amount is null
     *             or negative, projectPayment's createDate is null.
     * @throws ProjectPaymentNotFoundException
     *             if project payment with ID equal to projectPayment.getProjectPaymentId() doesn't exist in
     *             persistence
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if project payment integrity is broken, for example if project payment is being persisted with
     *             payment type that is not present in DB.
     * @throws ProjectPaymentManagementPersistenceException
     *             if some other error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null)
     */
    public void update(ProjectPayment projectPayment, String operator) throws ProjectPaymentValidationException,
        ProjectPaymentNotFoundException, ProjectPaymentManagementDataIntegrityException,
        ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".update(ProjectPayment projectPayment, String operator)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectPayment", "operator"},
            new Object[] {Helper.toString(projectPayment), operator});

        try {
            // Validate the parameters
            validateProjectPayment(projectPayment, false);
            ParameterCheckUtility.checkNotNullNorEmpty(operator, "operator");

            Connection connection = Helper.getConnection(log, signature, getDbConnectionFactory(),
                getConnectionName(), true);

            try {
                // Check project payment integrity
                checkProjectPaymentIntegrity(log, signature, connection, projectPayment);

                // UPDATE
                if (Helper.executeUpdate(log, signature, connection, false, SQL_UPDATE_PAYMENT, projectPayment
                    .getProjectPaymentType().getProjectPaymentTypeId(), projectPayment.getResourceId(),
                    projectPayment.getSubmissionId(), projectPayment.getAmount(), projectPayment.getPactsPaymentId(),
                    operator, new Timestamp(new Date().getTime()), projectPayment.getProjectPaymentId()) == 0) {
                    // Log exception
                    throw LoggingWrapperUtility.logException(log, signature, new ProjectPaymentNotFoundException(
                        "The project payment doesn't exist."));
                }

            } finally {
                // Close the connection:
                Helper.closeConnection(log, signature, connection);
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentValidationException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentManagementDataIntegrityException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature,
                new ProjectPaymentManagementPersistenceException("Failed to update the project payment.", e));
        }
    }

    /**
     * Retrieves the given project payment from persistence by its id.
     *
     * @param projectPaymentId
     *            the id of the project payment
     *
     * @return the project payment, null if nothing was found
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null)
     */
    public ProjectPayment retrieve(long projectPaymentId) throws ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".retrieve(long projectPaymentId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectPaymentId"},
            new Object[] {projectPaymentId});

        try {
            Connection connection = Helper.getConnection(log, signature, getDbConnectionFactory(),
                getConnectionName(), true);

            try {

                // Create a prepared statement:
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_PAYMENT);
                try {
                    preparedStatement.setLong(1, projectPaymentId);
                    // Execute the statement
                    ResultSet resultSet = preparedStatement.executeQuery();

                    ProjectPayment result = null;
                    if (resultSet.next()) {
                        int columnIndex = 1;
                        result = new ProjectPayment();
                        result.setProjectPaymentId(projectPaymentId);
                        result.setResourceId(resultSet.getLong(columnIndex++));

                        result.setSubmissionId(getLongNullable(resultSet, columnIndex++));

                        result.setAmount(resultSet.getBigDecimal(columnIndex++));

                        result.setPactsPaymentId(getLongNullable(resultSet, columnIndex++));

                        result.setCreateUser(resultSet.getString(columnIndex++));
                        result.setCreateDate(resultSet.getTimestamp(columnIndex++));
                        result.setModifyUser(resultSet.getString(columnIndex++));
                        result.setModifyDate(resultSet.getTimestamp(columnIndex++));

                        ProjectPaymentType projectPaymentType = new ProjectPaymentType();
                        projectPaymentType.setProjectPaymentTypeId(resultSet.getLong(columnIndex++));
                        projectPaymentType.setName(resultSet.getString(columnIndex++));
                        projectPaymentType.setMergeable(resultSet.getBoolean(columnIndex++));
                        result.setProjectPaymentType(projectPaymentType);
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
                new ProjectPaymentManagementPersistenceException("Failed to retrieve the project payment.", e));
        }
    }

    /**
     * Deletes the given project payment from persistence.
     *
     * @param projectPaymentId
     *            the id of project payment to delete
     *
     * @return the flag indicating if project payment was deleted
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null)
     */
    public boolean delete(long projectPaymentId) throws ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".delete(long projectPaymentId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectPaymentId"},
            new Object[] {projectPaymentId});

        try {
            Connection connection = Helper.getConnection(log, signature, getDbConnectionFactory(),
                getConnectionName(), true);

            try {

                // DELETE
                boolean result = (Helper.executeUpdate(log, signature, connection, false, SQL_DELETE_PAYMENT,
                    projectPaymentId) != 0);

                // Log Exit
                LoggingWrapperUtility.logExit(log, signature, new Object[] {result}, entranceTimestamp);
                return result;

            } finally {
                // Close the connection:
                Helper.closeConnection(log, signature, connection);
            }
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature,
                new ProjectPaymentManagementPersistenceException("Failed to update the project payment.", e));
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
     * Checks the project payment integrity.
     *
     * @param log
     *            the logger
     * @param signature
     *            the signature
     * @param connection
     *            the connection.
     * @param projectPayment
     *            the project payment.
     *
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if project payment integrity is broken, for example if project payment is being persisted with
     *             payment type that is not present in DB.
     * @throws SQLException
     *             if any other error occurs
     */
    private void checkProjectPaymentIntegrity(Log log, String signature, Connection connection,
        ProjectPayment projectPayment) throws ProjectPaymentManagementDataIntegrityException, SQLException {
        checkRecord(log, signature, "projectPaymentTypeId", connection, "project_payment_type_lu",
            "project_payment_type_id", projectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        checkRecord(log, signature, "resourceId", connection, "resource", "resource_id", projectPayment
            .getResourceId());

        Long submissionId = projectPayment.getSubmissionId();
        if (submissionId != null) {
            checkRecord(log, signature, "submissionId", connection, "submission", "submission_id", submissionId);
        }
    }

    /**
     * Checks if the record exists.
     *
     * @param log
     *            the logger
     * @param signature
     *            the signature
     * @param fieldName
     *            the field name.
     * @param connection
     *            the connection.
     * @param table
     *            the table.
     * @param key
     *            the key.
     * @param value
     *            the value.
     *
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if the record is not found.
     * @throws SQLException
     *             if any other error occurs
     */
    private static void checkRecord(Log log, String signature, String fieldName, Connection connection, String table,
        String key, Object value) throws ProjectPaymentManagementDataIntegrityException, SQLException {
        String sql = String.format(SQL_QUERY_FK, table, key);

        // Create a prepared statement:
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setObject(1, value);
            // Execute the statement
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            if (resultSet.getLong(1) == 0) {
                throw new ProjectPaymentManagementDataIntegrityException("'" + fieldName + "' doesn't exist.");
            }
        } finally {
            // Close the prepared statement
            Helper.closeStatement(log, signature, preparedStatement);
        }
    }

    /**
     * Validates the project payment.
     *
     * @param projectPayment
     *            the project payment.
     * @param create
     *            true for create; false for update.
     *
     * @throws IllegalArgumentException
     *             if projectPayment is null
     * @throws ProjectPaymentValidationException
     *             if projectPayment's id is not null (when create is true) or projectPayment's id is null or negative
     *             (when create is false), projectPayment's type is null, projectPaymentType's projectPaymentTypeId is
     *             null, projectPayment's resourceId is null, projectPayment's amount is null or negative,
     *             projectPayment's createDate is null.
     */
    private static void validateProjectPayment(ProjectPayment projectPayment, boolean create)
        throws ProjectPaymentValidationException {
        ParameterCheckUtility.checkNotNull(projectPayment, "projectPayment");

        // Validate the input parameter

        if (create) {
            if (projectPayment.getProjectPaymentId() != null) {
                throw new ProjectPaymentValidationException("projectPayment.getProjectPaymentId() should be null.");
            }
        } else {
            Long projectPaymentId = projectPayment.getProjectPaymentId();
            ValidationUtility.checkNotNull(projectPaymentId, "projectPaymentAdjustment.getProjectPaymentId()",
                ProjectPaymentValidationException.class);
            ValidationUtility.checkNotNegative(projectPaymentId.doubleValue(),
                "projectPaymentAdjustment.getProjectPaymentId()", ProjectPaymentValidationException.class);
        }

        ProjectPaymentType projectPaymentType = projectPayment.getProjectPaymentType();
        ValidationUtility.checkNotNull(projectPaymentType, "projectPaymentAdjustment.getProjectPaymentType()",
            ProjectPaymentValidationException.class);
        ValidationUtility.checkNotNull(projectPaymentType.getProjectPaymentTypeId(),
            "projectPaymentAdjustment.getProjectPaymentType().getProjectPaymentTypeId()",
            ProjectPaymentValidationException.class);

        ValidationUtility.checkNotNull(projectPayment.getResourceId(), "projectPaymentAdjustment.getResourceId()",
            ProjectPaymentValidationException.class);

        BigDecimal amount = projectPayment.getAmount();
        ValidationUtility.checkNotNull(amount, "projectPaymentAdjustment.getAmount()",
            ProjectPaymentValidationException.class);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProjectPaymentValidationException("projectPayment.getAmount() should be not negative.");
        }
    }

    /**
     * Gets long.
     *
     * @param resultSet
     *            the result set
     * @param columnIndex
     *            the column index
     *
     * @return the value
     *
     * @throws SQLException
     *             if any error occurs
     */
    private static Long getLongNullable(ResultSet resultSet, int columnIndex) throws SQLException {
        long pactsPaymentId = resultSet.getLong(columnIndex);
        if (!resultSet.wasNull()) {
            return pactsPaymentId;
        }

        return null;
    }
}
