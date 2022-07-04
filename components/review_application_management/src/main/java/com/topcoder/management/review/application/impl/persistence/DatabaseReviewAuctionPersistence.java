/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.management.review.application.Helper;
import com.topcoder.management.review.application.ReviewApplicationResourceRole;
import com.topcoder.management.review.application.ReviewApplicationRole;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.ReviewAuctionCategory;
import com.topcoder.management.review.application.ReviewAuctionType;
import com.topcoder.management.review.application.impl.ReviewAuctionPersistence;
import com.topcoder.management.review.application.impl.ReviewAuctionPersistenceException;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of ReviewAuctionPersistence that accesses review auction data in database persistence
 * using JDBC and DB Connection Factory component. This class uses Logging Wrapper component to log errors and debug
 * information.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when configure() method is called just once
 * right after instantiation and entities passed to it are used by the caller in thread safe manner. It uses thread safe
 * DBConnectionFactory and Log instances.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class DatabaseReviewAuctionPersistence extends BaseDatabasePersistence implements ReviewAuctionPersistence {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DatabaseReviewAuctionPersistence.class.getName();

    /**
     * <p>
     * Represents the SQL string to insert review auction.
     * </p>
     */
    private static final String SQL_INSERT_AUCTION = "INSERT INTO review_auction (project_id,"
        + " review_auction_type_id) VALUES (?, ?)";

    /**
     * <p>
     * Represents the SQL string to query review application resource role.
     * </p>
     */
    private static final String SQL_QUERY_APPLICATION_RESOURCE_ROLE = "SELECT review_application_role_id,"
        + " resource_role_id, unique_role FROM review_application_role_resource_role_xref";

    /**
     * <p>
     * Represents the SQL string to query review application role.
     * </p>
     */
    private static final String SQL_QUERY_APPLICATION_ROLE = "SELECT review_application_role_id, name,"
        + " review_auction_type_id, positions FROM review_application_role_lu"
        + " ORDER BY review_auction_type_id, order_index";

    /**
     * <p>
     * Represents the SQL string to query review auction type.
     * </p>
     */
    private static final String SQL_QUERY_AUCTION_TYPE = "SELECT rat.review_auction_type_id, rat.name as rat_name,"
        + " rat.review_auction_category_id, rac.name as rac_name FROM review_auction_type_lu rat"
        + " INNER JOIN review_auction_category_lu rac"
        + " ON rat.review_auction_category_id = rac.review_auction_category_id ORDER BY rat.review_auction_type_id";

    /**
     * <p>
     * Represents the SQL string to query review auction category.
     * </p>
     */
    private static final String SQL_QUERY_AUCTION_CATEGORY = "SELECT review_auction_category_id, name"
        + " FROM review_auction_category_lu ORDER BY review_auction_category_id";

    /**
     * <p>
     * Represents the SQL string to query assigned resource role ID.
     * </p>
     */
    private static final String SQL_QUERY_ASSIGNED_RESOURCE_ROLE_ID = "SELECT distinct project_id, resource_role_id"
        + " FROM resource WHERE project_id in (%1$s)";

    /**
     * <p>
     * Represents the SQL string to query review auction category ID.
     * </p>
     */
    private static final String SQL_QUERY_AUCTION_CATEGORY_ID = "SELECT review_auction_category_id"
        + " FROM review_auction ra INNER JOIN review_auction_type_lu rat"
        + " ON rat.review_auction_type_id = ra.review_auction_type_id WHERE ra.review_auction_id = ?";

    /**
     * Creates an instance of DatabaseReviewAuctionPersistence.
     */
    public DatabaseReviewAuctionPersistence() {
        // Empty
    }

    /**
     * Create a review auction.
     *
     * @param auction
     *            the review auction to create
     *
     * @return the created ReviewAuction
     *
     * @throws IllegalArgumentException
     *             if auction is null
     * @throws ReviewAuctionPersistenceException
     *             if any other error occurred during the operation
     */
    public ReviewAuction createAuction(ReviewAuction auction) throws ReviewAuctionPersistenceException {
        String signature = CLASS_NAME + ".createAuction(ReviewAuction auction)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"auction"},
            new Object[] {auction});

        try {
            ParameterCheckUtility.checkNotNull(auction, "auction");

            ReviewAuctionType auctionType = auction.getAuctionType();
            ValidationUtility.checkNotNull(auctionType, "auction.getAuctionType()",
                ReviewAuctionPersistenceException.class);

            // Create connection:
            Connection connection = getConnectionHelper();
            try {
                long id = Helper.executeUpdate(log, signature, ReviewAuctionPersistenceException.class, connection,
                    true, SQL_INSERT_AUCTION, new Object[] {auction.getProjectId(), auctionType.getId()});

                auction.setId(id);
            } finally {
                // Close the connection
                Helper.closeConnection(log, signature, connection);
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {auction});
            return auction;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionPersistenceException(
                "Failed to create the review auction.", e));
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Returns all auction types.
     *
     * @return all auction types, empty list will be returned if there's no auction types defined in the system
     *         (normally won't happen)
     *
     * @throws ReviewAuctionPersistenceException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuctionType> getAuctionTypes() throws ReviewAuctionPersistenceException {
        String signature = CLASS_NAME + ".getAuctionTypes()";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature, null, null);

        try {
            // Create connection:
            Connection connection = getConnectionHelper();
            try {
                // Retrieve ReviewApplicationRole's
                Map<Long, List<ReviewApplicationRole>> reviewApplicationRolesMap =
                    getReviewApplicationRoles(log, signature, connection);

                // Retrieve ReviewAuctionType's
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_AUCTION_TYPE);
                try {
                    // Execute the statement
                    ResultSet resultSet = preparedStatement.executeQuery();
                    List<ReviewAuctionType> types = new ArrayList<ReviewAuctionType>();
                    while (resultSet.next()) {
                        ReviewAuctionCategory category = new ReviewAuctionCategory(
                            resultSet.getLong("review_auction_category_id"),
                            resultSet.getString("rac_name"));

                        long reviewAuctionTypeId = resultSet.getLong("review_auction_type_id");

                        ReviewAuctionType type = new ReviewAuctionType(reviewAuctionTypeId,
                            resultSet.getString("rat_name"), category,
                            reviewApplicationRolesMap.get(reviewAuctionTypeId));

                        types.add(type);
                    }

                    // Log Exit
                    LoggingWrapperUtility.logExit(log, signature, new Object[] {types});
                    return types;
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
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionPersistenceException(
                "Failed to get all auction types.", e));
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Returns all auction categories.
     *
     * @return all auction categories, empty list will be returned if there's no auction categories defined in the
     *         system (normally won't happen)
     *
     * @throws ReviewAuctionPersistenceException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuctionCategory> getAuctionCategories() throws ReviewAuctionPersistenceException {
        String signature = CLASS_NAME + ".getAuctionCategories()";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature, null, null);

        try {
            // Create connection:
            Connection connection = getConnectionHelper();
            try {
                // Create a prepared statement:
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_AUCTION_CATEGORY);
                try {
                    // Execute the statement
                    ResultSet resultSet = preparedStatement.executeQuery();

                    List<ReviewAuctionCategory> categories = new ArrayList<ReviewAuctionCategory>();
                    while (resultSet.next()) {
                        ReviewAuctionCategory category = new ReviewAuctionCategory(
                            resultSet.getLong("review_auction_category_id"),
                            resultSet.getString("name"));

                        categories.add(category);
                    }

                    // Log Exit
                    LoggingWrapperUtility.logExit(log, signature, new Object[] {categories});
                    return categories;
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
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionPersistenceException(
                "Failed to get all auction categories.", e));
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Returns all distinct assigned resource role IDs for given list of projects.
     *
     * @param projectIds
     *            the project IDs, can't be null/empty
     *
     * @return a map from Long to List&lt;Long&gt;, key will be project ID, value will be list of assigned resource role
     *         IDs for the project. empty map means no resource has been assigned for any of the projects.
     *
     * @throws IllegalArgumentException
     *             if projectIds is null or empty, contains null or non-positive item.
     * @throws ReviewAuctionPersistenceException
     *             if any other error occurred during the operation
     */
    public Map<Long, List<Long>> getAssignedProjectResourceRoleIds(Set<Long> projectIds)
        throws ReviewAuctionPersistenceException {
        String signature = CLASS_NAME + ".getAssignedProjectResourceRoleIds(Set<Long> projectIds)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature, new String[] {"auctiprojectIdsonId"},
            new Object[] {projectIds});

        try {
            ParameterCheckUtility.checkNotNullNorEmpty(projectIds, "projectIds");
            for (Long projectId : projectIds) {
                ParameterCheckUtility.checkNotNull(projectId, "element of projectIds");
                ParameterCheckUtility.checkPositive(projectId, "element of projectIds");

            }

            // Create connection:
            Connection connection = getConnectionHelper();
            try {
                // Create a prepared statement:
                PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format(SQL_QUERY_ASSIGNED_RESOURCE_ROLE_ID, getInString(projectIds)));
                try {
                    // Execute the statement
                    ResultSet resultSet = preparedStatement.executeQuery();

                    Map<Long, List<Long>> result = new HashMap<Long, List<Long>>();

                    while (resultSet.next()) {
                        long projectId = resultSet.getLong("project_id");
                        long resourceRoleId = resultSet.getLong("resource_role_id");

                        List<Long> projectRoleIds = result.get(projectId);
                        if (projectRoleIds == null) {
                            projectRoleIds = new ArrayList<Long>();
                            result.put(projectId, projectRoleIds);
                        }
                        projectRoleIds.add(resourceRoleId);
                    }

                    // Log Exit
                    LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
                    return result;
                } finally {
                    // Close the prepared statement
                    Helper.closeStatement(log, signature, preparedStatement);
                }
            } finally {
                // Close the connection
                Helper.closeConnection(log, signature, connection);
            }
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionPersistenceException(
                "Failed to get all distinct assigned resource role IDs.", e));
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Returns the auction category ID for given auction ID.
     *
     * @param auctionId
     *            the auction ID
     *
     * @return the auction category ID
     *
     * @throws IllegalArgumentException
     *             if auctionId &lt;= 0
     * @throws ReviewAuctionPersistenceException
     *             if any other error occurred during the operation
     */
    public long getAuctionCategoryId(long auctionId) throws ReviewAuctionPersistenceException {
        String signature = CLASS_NAME + ".getAuctionCategoryId(long auctionId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"auctionId"},
            new Object[] {auctionId});

        try {
            ParameterCheckUtility.checkPositive(auctionId, "auctionId");

            // Create connection:
            Connection connection = getConnectionHelper();
            try {
                // Create a prepared statement:
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_AUCTION_CATEGORY_ID);
                try {
                    preparedStatement.setLong(1, auctionId);

                    // Execute the statement
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (!resultSet.next()) {
                        throw new ReviewAuctionPersistenceException("No auction category found by the auction ID '"
                            + auctionId + "'.");
                    }

                    long result = resultSet.getLong("review_auction_category_id");

                    // Log Exit
                    LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
                    return result;
                } finally {
                    // Close the prepared statement
                    Helper.closeStatement(log, signature, preparedStatement);
                }
            } finally {
                // Close the connection
                Helper.closeConnection(log, signature, connection);
            }
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (SQLException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionPersistenceException(
                "Failed to get auction category ID for given auction ID '" + auctionId + "'.", e));
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Gets the IN string.
     *
     * @param projectIds
     *            the project ids
     *
     * @return the string.
     */
    private static String getInString(Set<Long> projectIds) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Long projectId : projectIds) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(projectId);
        }

        return sb.toString();
    }

    /**
     * Gets the ReviewApplicationResourceRole instances.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param connection
     *            the connection
     *
     * @return the map, key is auction type id, value is the ReviewApplicationResourceRole instances list
     *
     * @throws SQLException
     *             if any error occurs
     */
    private static Map<Long, List<ReviewApplicationRole>> getReviewApplicationRoles(Log log, String signature,
        Connection connection) throws SQLException {
        Map<Long, List<ReviewApplicationResourceRole>> reviewApplicationResourceRolesMap =
            getReviewApplicationResourceRoles(log, signature, connection);

        // Retrieve ReviewApplicationRole's
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_APPLICATION_ROLE);
        try {
            Map<Long, List<ReviewApplicationRole>> reviewApplicationRolesMap =
                new HashMap<Long, List<ReviewApplicationRole>>();

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long applicationRoleId = resultSet.getLong("review_application_role_id");
                ReviewApplicationRole applicationRole = new ReviewApplicationRole(applicationRoleId,
                    resultSet.getString("name"), resultSet.getLong("positions"),
                    reviewApplicationResourceRolesMap.get(applicationRoleId));

                long auctionTypeId = resultSet.getLong("review_auction_type_id");

                List<ReviewApplicationRole> applicationRoles = reviewApplicationRolesMap.get(auctionTypeId);
                if (applicationRoles == null) {
                    applicationRoles = new ArrayList<ReviewApplicationRole>();
                    reviewApplicationRolesMap.put(auctionTypeId, applicationRoles);
                }
                applicationRoles.add(applicationRole);
            }

            return reviewApplicationRolesMap;
        } finally {
            // Close the prepared statement
            Helper.closeStatement(log, signature, preparedStatement);
        }
    }

    /**
     * Gets the ReviewApplicationResourceRole instances.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param connection
     *            the connection
     *
     * @return the map, key is application role id, value is the ReviewApplicationResourceRole instances list
     *
     * @throws SQLException
     *             if any error occurs
     */
    private static Map<Long, List<ReviewApplicationResourceRole>> getReviewApplicationResourceRoles(Log log,
        String signature, Connection connection) throws SQLException {
        // Create a prepared statement:
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_APPLICATION_RESOURCE_ROLE);

        try {
            Map<Long, List<ReviewApplicationResourceRole>> reviewApplicationResourceRolesMap =
                new HashMap<Long, List<ReviewApplicationResourceRole>>();

            // Execute the statement
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ReviewApplicationResourceRole applicationResourceRole = new ReviewApplicationResourceRole(
                    resultSet.getLong("resource_role_id"), resultSet.getBoolean("unique_role"));

                long applicationRoleId = resultSet.getLong("review_application_role_id");
                List<ReviewApplicationResourceRole> applicationResourceRoles = reviewApplicationResourceRolesMap
                    .get(applicationRoleId);
                if (applicationResourceRoles == null) {
                    applicationResourceRoles = new ArrayList<ReviewApplicationResourceRole>();
                    reviewApplicationResourceRolesMap.put(applicationRoleId, applicationResourceRoles);
                }
                applicationResourceRoles.add(applicationResourceRole);
            }

            return reviewApplicationResourceRolesMap;
        } finally {
            // Close the prepared statement
            Helper.closeStatement(log, signature, preparedStatement);
        }
    }

    /**
     * Creates a connection.
     *
     * @return the database connection
     *
     * @throws ReviewAuctionPersistenceException
     *             if any other error occurred during the operation
     */
    private Connection getConnectionHelper() throws ReviewAuctionPersistenceException {
        try {
            return super.getConnection();
        } catch (IllegalStateException e) {
            throw new ReviewAuctionPersistenceException("The instance is not configured yet.", e);
        } catch (DBConnectionException e) {
            throw new ReviewAuctionPersistenceException("Failed to create a database connection.", e);
        }
    }
}
