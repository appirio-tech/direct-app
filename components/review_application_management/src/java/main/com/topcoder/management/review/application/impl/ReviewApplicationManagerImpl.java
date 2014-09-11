/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.Helper;
import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.ReviewApplicationManager;
import com.topcoder.management.review.application.ReviewApplicationManagerException;
import com.topcoder.management.review.application.ReviewApplicationStatus;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.util.log.Log;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.util.sql.databaseabstraction.NullColumnValueException;

/**
 * <p>
 * This class is an implementation of ReviewApplicationManager that uses Search Builder component to search review
 * applications (by auction ID, user ID, application status ID) in persistence and pluggable
 * ReviewApplicationPersistence instance to create/update review applications in persistence and retrieve review
 * application status lookup values. This class uses Logging Wrapper component to log errors and debug information.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 *
 * <pre>
 * &lt;?xml version="1.0"?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name="com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl"&gt;
 *     &lt;Property name="loggerName"&gt;
 *       &lt;Value&gt;myLogger&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="objectFactoryConfig"&gt;
 *       &lt;Property name="DatabaseReviewApplicationPersistence"&gt;
 *         &lt;Property name="type"&gt;
 *           &lt;Value&gt;
 *             com.topcoder.management.review.application.impl.persistence.DatabaseReviewApplicationPersistence
 *           &lt;/Value&gt;
 *         &lt;/Property&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="searchBundleManagerNamespace"&gt;
 *       &lt;Value&gt;ReviewApplicationManagement.SearchBuilderManager&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="reviewApplicationSearchBundleName"&gt;
 *       &lt;Value&gt;reviewApplicationSearchBundle&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="persistenceKey"&gt;
 *       &lt;Value&gt;DatabaseReviewApplicationPersistence&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="persistenceConfig"&gt;
 *       &lt;Property name="loggerName"&gt;
 *         &lt;Value&gt;myLogger&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name="dbConnectionFactoryConfig"&gt;
 *         &lt;Property name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"&gt;
 *           &lt;Property name="connections"&gt;
 *             &lt;Property name="default"&gt;
 *               &lt;Value&gt;myConnection&lt;/Value&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name="myConnection"&gt;
 *               &lt;Property name="producer"&gt;
 *                   &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *               &lt;/Property&gt;
 *               &lt;Property name="parameters"&gt;
 *                 &lt;Property name="jdbc_driver"&gt;
 *                   &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="jdbc_url"&gt;
 *                   &lt;Value&gt;
 *                     jdbc:informix-sqli://localhost:1526/tcs_catalog:informixserver=ol_topcoder
 *                   &lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="SelectMethod"&gt;
 *                   &lt;Value&gt;cursor&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="user"&gt;
 *                   &lt;Value&gt;informix&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="password"&gt;
 *                   &lt;Value&gt;123456&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *               &lt;/Property&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *         &lt;/Property&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name="connectionName"&gt;
 *         &lt;Value&gt;myConnection&lt;/Value&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 *
 * <pre>
 * // Create an instance of ReviewAuctionManagerImpl using default configuration
 * ReviewAuctionManager reviewAuctionManager = new ReviewAuctionManagerImpl();
 * // Get Review Auction Types
 * List&lt;ReviewAuctionType&gt; types = reviewAuctionManager.getAuctionTypes();
 *
 * // Create Auction
 * ReviewAuction auction = new ReviewAuction();
 * auction.setProjectId(100000);
 * auction.setAuctionType(types.get(0));
 *
 * auction = reviewAuctionManager.createAuction(auction);
 * long auctionId = auction.getId();
 *
 * // Create an instance of ReviewApplicationManagerImpl using default configuration
 * ReviewApplicationManager reviewApplicationManager = new ReviewApplicationManagerImpl();
 *
 * // Create an instance of ReviewApplicationManagerImpl using custom configuration
 * reviewApplicationManager = new ReviewApplicationManagerImpl(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME,
 *     ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
 *
 * // Create an instance of ReviewApplicationManagerImpl using custom configuration
 * ConfigurationObject configuration = getConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
 * reviewApplicationManager = new ReviewApplicationManagerImpl(configuration);
 *
 * // Get Review Application Statuses
 * List&lt;ReviewApplicationStatus&gt; statuses = reviewApplicationManager.getApplicationStatuses();
 *
 * // Create Review Application
 * ReviewApplication application = new ReviewApplication();
 * application.setUserId(123);
 * application.setAuctionId(auctionId);
 * application.setApplicationRoleId(4);
 * application.setStatus(statuses.get(0));
 * application.setCreateDate(new Date());
 *
 * application = reviewApplicationManager.createApplication(application);
 *
 * long applicationId = application.getId();
 *
 * // Update Review Application
 * application.setStatus(statuses.get(1));
 *
 * reviewApplicationManager.updateApplication(application);
 *
 * // Search by auction ID
 * Filter auctionIdFilter = ReviewApplicationFilterBuilder.createAuctionIdFilter(auctionId);
 * List&lt;ReviewApplication&gt; applications = reviewApplicationManager.searchApplications(auctionIdFilter);
 *
 * // Search by user ID
 * Filter userIdFilter = ReviewApplicationFilterBuilder.createUserIdFilter(123);
 * applications = reviewApplicationManager.searchApplications(userIdFilter);
 *
 * // Search by application status ID
 * Filter applicationStatusIdFilter = ReviewApplicationFilterBuilder.createApplicationStatusIdFilter(statuses.get(1)
 *     .getId());
 * applications = reviewApplicationManager.searchApplications(applicationStatusIdFilter);
 *
 * // Search by combination of filters
 * Filter filter = new AndFilter(auctionIdFilter, new AndFilter(userIdFilter, applicationStatusIdFilter));
 * applications = reviewApplicationManager.searchApplications(filter);
 * </pre>
 *
 * </p>
 *
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable because applicationStatuses and applicationStatusesMap as
 * cached data are mutable variables, however it is assumed that they will be only initialized once and won't be
 * reloaded, hence this class can be considered as thread safe when entities passed to it are used by the caller in
 * thread safe manner. It uses thread safe SearchBundle, ReviewApplicationPersistence and Log instances.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class ReviewApplicationManagerImpl implements ReviewApplicationManager {
    /**
     * The default configuration file path for this class. It's an immutable static constant.
     */
    public static final String DEFAULT_CONFIG_FILENAME =
        "com/topcoder/management/review/application/impl/ReviewApplicationManagerImpl.properties";

    /**
     * The default configuration namespace for this class. It's an immutable static constant.
     */
    public static final String DEFAULT_CONFIG_NAMESPACE =
        "com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl";

    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ReviewApplicationManagerImpl.class.getName();

    /**
     * <p>
     * Represents the property key 'reviewApplicationSearchBundleName'.
     * </p>
     */
    private static final String KEY_APPLICATION_SB_NAME = "reviewApplicationSearchBundleName";

    /**
     * The review application persistence instance used by this class. Can be non-null ReviewApplicationPersistence. It
     * will be initialized in the constructor method and never changed after that. Cannot be null after initialization.
     */
    private final ReviewApplicationPersistence persistence;

    /**
     * Represents the logger used to perform logging. Can be Log instance, could be null, null means no logging is
     * required. It will be initialized in the constructor and never changed after that.
     */
    private final Log log;

    /**
     * The search bundle used by this class when searching for review applications. Can be non-null SearchBundle. It
     * will be initialized in the constructor method and never changed after that. Cannot be null after initialization.
     */
    private final SearchBundle reviewApplicationSearchBundle;

    /**
     * Represents the lookup values for review application statuses. Can be non-null, non-empty list, items in the list
     * must be non-null ReviewApplicationStatus object. It will be initialized in getApplicationStatuses method when
     * this method gets invoked for the first time, and will not change afterwards. It will be returned by
     * getApplicationStatuses method.
     */
    private List<ReviewApplicationStatus> applicationStatuses;

    /**
     * Represents the mapping from for review application status IDs to the review application statuses. Can be
     * non-null, non-empty map, values in the map must be non-null ReviewApplicationStatus object, keys in the map must
     * be non-null, positive Long. It will be initialized in getApplicationStatuses method when this method gets invoked
     * for the first time, and will not change afterwards. It will be used in searchApplications method to lookup the
     * application statuses.
     */
    private Map<Long, ReviewApplicationStatus> applicationStatusesMap;

    /**
     * Constructor which initializes the ReviewApplicationManagerImpl from default configuration file.
     *
     * @throws ReviewApplicationManagementConfigurationException
     *             if error occurred while reading the configuration or initializing this class
     */
    public ReviewApplicationManagerImpl() {
        this(DEFAULT_CONFIG_FILENAME, DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * Constructor with given configuration file path and namespace.
     *
     * @param filePath
     *            the path of the configuration file
     * @param namespace
     *            the configuration namespace
     *
     * @throws IllegalArgumentException
     *             if filePath or namespace is null/empty
     * @throws ReviewApplicationManagementConfigurationException
     *             if error occurred while reading the configuration or initializing this class
     */
    public ReviewApplicationManagerImpl(String filePath, String namespace) {
        this(Helper.getConfiguration(filePath, namespace));
    }

    /**
     * Constructor with the given configuration object.
     *
     * @param configuration
     *            the configuration object for this class
     *
     * @throws IllegalArgumentException
     *             if configuration is null
     * @throws ReviewApplicationManagementConfigurationException
     *             if error occurred while reading the configuration or initializing this class
     */
    public ReviewApplicationManagerImpl(ConfigurationObject configuration) {
        ParameterCheckUtility.checkNotNull(configuration, "configuration");

        // Get Log instance to be used
        log = Helper.getLog(configuration);

        // Create search bundle manager:
        SearchBundleManager searchBundleManager = Helper.getSearchBundleManager(configuration);

        // Get search bundle from the manager:
        reviewApplicationSearchBundle = Helper.getSearchBundle(searchBundleManager, configuration,
            KEY_APPLICATION_SB_NAME);

        // Create persistence implementation instance:
        persistence = Helper.createPersistence(ReviewApplicationPersistence.class, configuration);

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
     * @throws ReviewApplicationManagerException
     *             if any other error occurred during the operation
     */
    public ReviewApplication createApplication(ReviewApplication application) throws ReviewApplicationManagerException {
        String signature = CLASS_NAME + ".createApplication(ReviewApplication application)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"application"},
            new Object[] {application});

        try {
            ReviewApplication result = persistence.createApplication(application);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ReviewApplicationPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationManagerException(
                "Failed to create the review application.", e));
        }
    }

    /**
     * Update a review application.
     *
     * @param application
     *            the review application to update
     *
     * @throws IllegalArgumentException
     *             if application is null
     * @throws ReviewApplicationManagerException
     *             if any other error occurred during the operation
     */
    public void updateApplication(ReviewApplication application) throws ReviewApplicationManagerException {
        String signature = CLASS_NAME + ".updateApplication(ReviewApplication application)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"application"},
            new Object[] {application});

        try {
            persistence.updateApplication(application);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ReviewApplicationPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationManagerException(
                "Failed to update the review application.", e));
        }
    }

    /**
     * Search review applications.
     *
     * @param filter
     *            the filter for searching review applications (null if all review applications need to be retrieved).
     *
     * @return the list of ReviewApplication's matching the search filter, empty list will be returned if there's no
     *         matching ReviewApplication.
     *
     * @throws ReviewApplicationManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewApplication> searchApplications(Filter filter) throws ReviewApplicationManagerException {
        String signature = CLASS_NAME + ".getApplicationStatuses()";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature, null, null);

        try {
            // Create filter that matches all records if filter is null
            if (filter == null) {
                filter = new NotFilter(new NullFilter("id"));
            }

            // Search using Search Builder
            CustomResultSet resultSet = (CustomResultSet) reviewApplicationSearchBundle.search(filter);

            // Create a list for result
            List<ReviewApplication> result = new ArrayList<ReviewApplication>();

            // Call getApplicationStatuses to initialize the application statuses cache if not yet
            getApplicationStatuses();

            while (resultSet.next()) {
                ReviewApplication reviewApplication = new ReviewApplication();

                reviewApplication.setId(resultSet.getLong("review_application_id"));
                reviewApplication.setUserId(resultSet.getLong("user_id"));
                reviewApplication.setAuctionId(resultSet.getLong("review_auction_id"));
                reviewApplication.setApplicationRoleId(resultSet.getLong("review_application_role_id"));
                reviewApplication.setStatus(
                    applicationStatusesMap.get(resultSet.getLong("review_application_status_id")));
                reviewApplication.setCreateDate(resultSet.getTimestamp("create_date"));

                result.add(reviewApplication);
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationManagerException(
                "Failed to search the review applications.", e));
        } catch (InvalidCursorStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationManagerException(
                "Failed to search the review applications.", e));
        } catch (NullColumnValueException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationManagerException(
                "Failed to search the review applications.", e));
        } catch (SearchBuilderException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationManagerException(
                "Failed to search the review applications.", e));
        } catch (ClassCastException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationManagerException(
                "The result is invalid.", e));
        }
    }

    /**
     * Returns all application statuses.
     *
     * @return all application statuses, empty list will be returned if there's no application statuses defined in the
     *         system (normally won't happen)
     *
     * @throws ReviewApplicationManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewApplicationStatus> getApplicationStatuses() throws ReviewApplicationManagerException {
        String signature = CLASS_NAME + ".getApplicationStatuses()";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature, null, null);

        try {
            if (applicationStatuses == null) {
                applicationStatuses = persistence.getApplicationStatuses();

                applicationStatusesMap = new HashMap<Long, ReviewApplicationStatus>();
                for (ReviewApplicationStatus status : applicationStatuses) {
                    applicationStatusesMap.put(status.getId(), status);
                }
            }
            List<ReviewApplicationStatus> result = Collections.unmodifiableList(applicationStatuses);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (ReviewApplicationPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewApplicationManagerException(
                "Failed to get all application statuses.", e));
        }
    }
}
