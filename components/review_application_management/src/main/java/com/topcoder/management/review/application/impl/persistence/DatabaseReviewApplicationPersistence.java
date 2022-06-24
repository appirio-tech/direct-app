/*
 * Copyright (C) 2012-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.management.review.application.Helper;
import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.ReviewApplicationStatus;
import com.topcoder.management.review.application.impl.ReviewApplicationPersistence;
import com.topcoder.management.review.application.impl.ReviewApplicationPersistenceException;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of ReviewApplicationPersistence that accesses review application data in database
 * persistence using JDBC and DB Connection Factory component. This class uses Logging Wrapper component to log errors
 * and debug information.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when configure() method is called just once
 * right after instantiation and entities passed to it are used by the caller in thread safe manner. It uses thread safe
 * DBConnectionFactory and Log instances.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0.1
 */
public class DatabaseReviewApplicationPersistence extends BaseDatabasePersistence implements
    ReviewApplicationPersistence {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DatabaseReviewApplicationPersistence.class.getName();

    /**
     * <p>
     * Represents the SQL string to insert review application.
     * </p>
     */
    private static final String SQL_INSERT_APPLICATION = "INSERT INTO review_application (user_id,"
        + " review_auction_id, review_application_role_id, review_application_status_id, create_date, modify_date)"
        + " VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL string to update review application.
     * </p>
     */
    private static final String SQL_UPDATE_APPLICATION = "UPDATE review_application SET user_id = ?,"
        + " review_auction_id = ?, review_application_role_id = ?, review_application_status_id = ?, modify_date = ?"
        + " WHERE review_application_id = ?";

    /**
     * <p>
     * Represents the SQL string to query review application statues.
     * </p>
     */
    private static final String SQL_QUERY_APPLICATION_STATUS = "SELECT review_application_status_id, name"
        + " FROM review_application_status_lu ORDER BY review_application_status_id";

    /**
     * Creates an instance of DatabaseReviewApplicationPersistence.
     */
    public DatabaseReviewApplicationPersistence() {
        // Empty
    }

    /**
     * Create a review application.
     *
     * @param application
     *            the review application to create
     *
     * @return the created ReviewApplication
     *
     * @throws IllegalArgumentException
     *             if application is null
     * @throws ReviewApplicationPersistenceException
     *             if any other error occurred during the operation
     */
    public ReviewApplication createApplication(ReviewApplication application)
        throws ReviewApplicationPersistenceException {
        String signature = CLASS_NAME + ".createApplication(ReviewApplication application)";
        Log log = getLog();

        return saveApplication(log, signature, SQL_INSERT_APPLICATION, true, application);
    }

    /**
     * Update a review application.
     *
     * @param application
     *            the review application to update
     *
     * @throws IllegalArgumentException
     *             if application is null
     * @throws ReviewApplicationPersistenceException
     *             if any other error occurred during the operation
     */
    public void updateApplication(ReviewApplication application) throws ReviewApplicationPersistenceException {
        String signature = CLASS_NAME + ".updateApplication(ReviewApplication application)";
        Log log = getLog();

        saveApplication(log, signature, SQL_UPDATE_APPLICATION, false, application);
    }

    /**
     * Returns all application statuses.
     *
     * @return all application statuses, empty list will be returned if there's no application statuses defined
     *
     * @throws ReviewApplicationPersistenceException
     *             if any other error occurred during the operation in the system (normally won't happen)
     */
    public List<ReviewApplicationStatus> getApplicationStatuses() throws ReviewApplicationPersistenceException {
        String signature = CLASS_NAME + ".getApplicationStatuses()";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature, null, null);

        try {
            // Create connection:
            Connection connection = getConnectionHelper();

            try {
                // Create a prepared statement:
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_APPLICATION_STATUS);
                try {
                    // Execute the statement
                    ResultSet resultSet = preparedStatement.executeQuery();

                    List<ReviewApplicationStatus> statuses = new ArrayList<ReviewApplicationStatus>();
                    while (resultSet.next()) {
                        ReviewApplicationStatus status = new ReviewApplicationStatus(
                            resultSet.getLong("review_application_status_id"),
                            resultSet.getString("name"));

                        statuses.add(status);
                    }

                    // Log Exit
                    LoggingWrapperUtility.logExit(log, signature, new Object[] {statuses});
                    return statuses;
                } finally {
                    // Close the prepared statement
                    Helper.closeStatement(log, signature, preparedStatement);
                }
            } finally {
                // Close the connection
                Helper.closeConnection(log, signature, connection);
            }
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationPersistenceException(
                "Failed to get all application statuses.", e));
        } catch (ReviewApplicationPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Create or update a review application.
     *
     * @param log
     *            the logger
     * @param signature
     *            the signature
     * @param sql
     *            the SQL string
     * @param create
     *            <code>true</code> for create; <code>false</code> for update.
     * @param application
     *            the application
     *
     * @return the the application
     *
     * @throws IllegalArgumentException
     *             if application is null
     * @throws ReviewApplicationPersistenceException
     *             if any other error occurred during the operation
     */
    private ReviewApplication saveApplication(Log log, String signature, String sql, boolean create,
        ReviewApplication application) throws ReviewApplicationPersistenceException {
        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"application"},
            new Object[] {application});

        try {
            ParameterCheckUtility.checkNotNull(application, "application");

            ReviewApplicationStatus status = application.getStatus();
            ValidationUtility.checkNotNull(status, "application.getStatus()",
                ReviewApplicationPersistenceException.class);

            // Create connection:
            Connection connection = getConnectionHelper();
            Timestamp now = new Timestamp(new Date().getTime());
            
            try {
                Object[] params;
                if (create) {
                    params = new Object[] {application.getUserId(), application.getAuctionId(),
                        application.getApplicationRoleId(), status.getId(), now, now};
                } else {
                    params = new Object[] {application.getUserId(), application.getAuctionId(),
                        application.getApplicationRoleId(), status.getId(), now, application.getId()};
                }

                long id = Helper.executeUpdate(log, signature, ReviewApplicationPersistenceException.class, connection,
                    create, sql, params);

                Object[] returnValue;
                if (create) {
                    application.setId(id);
                    application.setCreateDate(now);

                    returnValue = new Object[] {application};
                } else {
                    returnValue = null;
                }
                application.setModifyDate(now);

                // Log Exit
                LoggingWrapperUtility.logExit(log, signature, returnValue);
                return application;
            } finally {
                // Close the connection
                Helper.closeConnection(log, signature, connection);
            }
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationPersistenceException(
                "A database access error has occurred.", e));
        } catch (ReviewApplicationPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Creates a connection.
     *
     * @return the database connection
     *
     * @throws ReviewApplicationPersistenceException
     *             if any other error occurred during the operation
     */
    private Connection getConnectionHelper() throws ReviewApplicationPersistenceException {
        try {
            return super.getConnection();
        } catch (IllegalStateException e) {
            throw new ReviewApplicationPersistenceException("The instance is not configured yet.", e);
        } catch (DBConnectionException e) {
            throw new ReviewApplicationPersistenceException("Failed to create a database connection.", e);
        }
    }
}
