/*
 * Copyright (C) 2012-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.Helper;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.ReviewApplicationResourceRole;
import com.topcoder.management.review.application.ReviewApplicationRole;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.ReviewAuctionCategory;
import com.topcoder.management.review.application.ReviewAuctionManager;
import com.topcoder.management.review.application.ReviewAuctionManagerException;
import com.topcoder.management.review.application.ReviewAuctionType;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.util.log.Log;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.util.sql.databaseabstraction.NullColumnValueException;

/**
 * <p>
 * This class is an implementation of ReviewAuctionManager that uses Search Builder component to search review auctions
 * in persistence and pluggable ReviewAuctionPersistence instance to create review auction in persistence and retrieve
 * review auction categories/types lookup values, get auction category ID for a given auction ID, and get assigned
 * project resource IDs for given project IDs. This class uses Logging Wrapper component to log errors and debug
 * information.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 *
 * <pre>
 * &lt;?xml version="1.0"?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name="com.topcoder.management.review.application.impl.ReviewAuctionManagerImpl"&gt;
 *     &lt;Property name="loggerName"&gt;
 *       &lt;Value&gt;myLogger&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="objectFactoryConfig"&gt;
 *       &lt;Property name="DatabaseReviewAuctionPersistence"&gt;
 *         &lt;Property name="type"&gt;
 *           &lt;Value&gt;
 *             com.topcoder.management.review.application.impl.persistence.DatabaseReviewAuctionPersistence
 *           &lt;/Value&gt;
 *         &lt;/Property&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="searchBundleManagerNamespace"&gt;
 *       &lt;Value&gt;ReviewApplicationManagement.SearchBuilderManager&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="specReviewAuctionSearchBundleName"&gt;
 *       &lt;Value&gt;specReviewAuctionSearchBundle&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="contestReviewAuctionSearchBundleName"&gt;
 *       &lt;Value&gt;contestReviewAuctionSearchBundle&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="contestReviewAuctionCategoryId"&gt;
 *       &lt;Value&gt;1&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="specReviewAuctionCategoryId"&gt;
 *       &lt;Value&gt;2&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="persistenceKey"&gt;
 *       &lt;Value&gt;DatabaseReviewAuctionPersistence&lt;/Value&gt;
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
 *
 * // Create an instance of ReviewApplicationManagerImpl using custom configuration
 * reviewAuctionManager = new ReviewAuctionManagerImpl(ReviewAuctionManagerImpl.DEFAULT_CONFIG_FILENAME,
 *     ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
 *
 * // Create an instance of ReviewAuctionManagerImpl using custom configuration
 * ConfigurationObject configuration = getConfig(ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
 * reviewAuctionManager = new ReviewAuctionManagerImpl(configuration);
 *
 * // Get Review Auction Categories
 * List&lt;ReviewAuctionCategory&gt; categories = reviewAuctionManager.getAuctionCategories();
 *
 * // Get Review Auction Types
 * List&lt;ReviewAuctionType&gt; types = reviewAuctionManager.getAuctionTypes();
 *
 * // Create Auction
 * ReviewAuction auction = new ReviewAuction();
 * auction.setProjectId(100000);
 * auction.setAuctionType(types.get(0));
 *
 * auction = reviewAuctionManager.createAuction(auction);
 *
 * long auctionId = auction.getId();
 *
 * // Search Auctions by auction category ID
 * List&lt;ReviewAuction&gt; auctions = reviewAuctionManager.searchOpenAuctions(1);
 *
 * // Search Auctions by aution category ID and project category IDs
 * auctions = reviewAuctionManager.searchOpenAuctions(1, Arrays.asList(1L, 2L, 3L));
 *
 * // Get Auction
 * auction = reviewAuctionManager.getAuction(auctionId);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable because auctionTypes, auctionCategories and auctionTypesMap as
 * cached data are mutable variables, however it is assumed that they will be only initialized once and won't be
 * reloaded, hence this class can be considered as thread safe when entities passed to it are used by the caller in
 * thread safe manner. It uses thread safe SearchBundle, ReviewApplicationPersistence and Log instances.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0.2
 */
public class ReviewAuctionManagerImpl implements ReviewAuctionManager {
    /**
     * The default configuration file path for this class. It's an immutable static constant.
     */
    public static final String DEFAULT_CONFIG_FILENAME =
        "com/topcoder/management/review/application/impl/ReviewAuctionManagerImpl.properties";
    /**
     * The default configuration namespace for this class. It's an immutable static constant.
     */
    public static final String DEFAULT_CONFIG_NAMESPACE =
        "com.topcoder.management.review.application.impl.ReviewAuctionManagerImpl";

    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ReviewAuctionManagerImpl.class.getName();

    /**
     * <p>
     * Represents the property key 'specReviewAuctionSearchBundleName'.
     * </p>
     */
    private static final String KEY_SPEC_AUCTION_SB_NAME = "specReviewAuctionSearchBundleName";

    /**
     * <p>
     * Represents the property key 'contestReviewAuctionSearchBundleName'.
     * </p>
     */
    private static final String KEY_CONTEST_AUCTION_SB_NAME = "contestReviewAuctionSearchBundleName";

    /**
     * <p>
     * Represents the property key 'iterativeReviewAuctionSearchBundleName'.
     * </p>
     */
    private static final String KEY_ITERATIVE_AUCTION_SB_NAME = "iterativeReviewAuctionSearchBundleName";

    /**
     * <p>
     * Represents the property key 'specReviewAuctionCategoryId'.
     * </p>
     */
    private static final String KEY_SPEC_GATEGORY_ID = "specReviewAuctionCategoryId";

    /**
     * <p>
     * Represents the property key 'contestReviewAuctionCategoryId'.
     * </p>
     */
    private static final String KEY_CONTEST_GATEGORY_ID = "contestReviewAuctionCategoryId";

    /**
     * <p>
     * Represents the property key 'iterativeReviewAuctionCategoryId'.
     * </p>
     */
    private static final String KEY_ITERATIVE_GATEGORY_ID = "iterativeReviewAuctionCategoryId";

    /**
     * The review auction persistence instance used by this class. Can be non-null ReviewAuctionPersistence. It will be
     * initialized in the constructor method and never changed after that. Cannot be null after initialization.
     */
    private final ReviewAuctionPersistence persistence;
    /**
     * Represents the logger used to perform logging. Can be Log instance, could be null, null means no logging is
     * required. It will be initialized in the constructor and never changed after that.
     */
    private final Log log;
    /**
     * The search bundle used by this class when searching for review auctions for "Specification Review" auction
     * category. Can be non-null SearchBundle. It will be initialized in the constructor method and never changed after
     * that. Cannot be null after initialization.
     */
    private final SearchBundle specReviewAuctionSearchBundle;
    /**
     * The search bundle used by this class when searching for review auctions for "Contest Review" auction category.
     * Can be non-null SearchBundle. It will be initialized in the constructor method and never changed after that.
     * Cannot be null after initialization.
     */
    private final SearchBundle contestReviewAuctionSearchBundle;
    /**
     * The search bundle used by this class when searching for review auctions for "Iterative Review" auction category.
     * Can be non-null SearchBundle. It will be initialized in the constructor method and never changed after that.
     * Cannot be null after initialization.
     */
    private final SearchBundle iterativeReviewAuctionSearchBundle;
    /**
     * The review auction category ID for "Specification Review". Can be positive integer. It will be initialized in the
     * constructor method and never changed after that. Cannot be non-positive after initialization.
     */
    private final long specReviewAuctionCategoryId;
    /**
     * The review auction category ID for "Contest Review". Can be positive integer. It will be initialized in the
     * constructor method and never changed after that. Cannot be non-positive after initialization.
     */
    private final long contestReviewAuctionCategoryId;
    /**
     * The review auction category ID for "Iterative Review". Can be positive integer. It will be initialized in the
     * constructor method and never changed after that. Cannot be non-positive after initialization.
     */
    private final long iterativeReviewAuctionCategoryId;
    /**
     * Represents the lookup values for review auction types. Can be non-null, non-empty list, items in the list must be
     * non-null ReviewAuctionType object. It will be initialized in getAuctionTypes method when this method gets invoked
     * for the first time, and will not change afterwards. It will be returned by getAuctionTypes method.
     */
    private List<ReviewAuctionType> auctionTypes;
    /**
     * Represents the lookup values for review auction categories. Can be non-null, non-empty list, items in the list
     * must be non-null ReviewAuctionCategory object. It will be initialized in getAuctionCategories method when this
     * method gets invoked for the first time, and will not change afterwards. It will be returned by
     * getAuctionCategories method.
     */
    private List<ReviewAuctionCategory> auctionCategories;
    /**
     * Represents the mapping from for review auction type IDs to the review auction types. Can be non-null, non-empty
     * map, values in the map must be non-null ReviewAuctionType object, keys in the map must be non-null, positive
     * Long. It will be initialized in getAuctionTypes method when this method gets invoked for the first time, and will
     * not change afterwards. It will be used in searchOpenAuctions method to lookup the auction types.
     */
    private Map<Long, ReviewAuctionType> auctionTypesMap;

    /**
     * Constructor which initializes the ReviewAuctionManagerImpl from default configuration file.
     *
     * @throws ReviewApplicationManagementConfigurationException
     *             if error occurred while reading the configuration or initializing this class
     */
    public ReviewAuctionManagerImpl() {
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
    public ReviewAuctionManagerImpl(String filePath, String namespace) {
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
    public ReviewAuctionManagerImpl(ConfigurationObject configuration) {
        ParameterCheckUtility.checkNotNull(configuration, "configuration");

        // Get Log instance to be used
        log = Helper.getLog(configuration);

        // Create search bundle manager:
        SearchBundleManager searchBundleManager = Helper.getSearchBundleManager(configuration);

        // Get search bundles from the manager.
        specReviewAuctionSearchBundle = Helper.getSearchBundle(searchBundleManager, configuration,
            KEY_SPEC_AUCTION_SB_NAME);
        contestReviewAuctionSearchBundle = Helper.getSearchBundle(searchBundleManager, configuration,
            KEY_CONTEST_AUCTION_SB_NAME);
        iterativeReviewAuctionSearchBundle = Helper.getSearchBundle(searchBundleManager, configuration,
            KEY_ITERATIVE_AUCTION_SB_NAME);

        // Create persistence implementation instance:
        persistence = Helper.createPersistence(ReviewAuctionPersistence.class, configuration);

        // Get various IDs:
        specReviewAuctionCategoryId = getPropertyPositive(configuration, KEY_SPEC_GATEGORY_ID);
        contestReviewAuctionCategoryId = getPropertyPositive(configuration, KEY_CONTEST_GATEGORY_ID);
        iterativeReviewAuctionCategoryId = getPropertyPositive(configuration, KEY_ITERATIVE_GATEGORY_ID);
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
     * @throws ReviewAuctionManagerException
     *             if any other error occurred during the operation
     */
    public ReviewAuction createAuction(ReviewAuction auction) throws ReviewAuctionManagerException {
        String signature = CLASS_NAME + ".createAuction(ReviewAuction auction)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"auction"},
            new Object[] {auction});

        try {
            ReviewAuction result = persistence.createAuction(auction);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Failed to create the review auction.", e));
        }
    }

    /**
     * Search open auctions by auction category ID.
     *
     * @param auctionCategoryId
     *            the auction category ID
     *
     * @return the list of ReviewAuction's matching the search filter, empty list will be returned if there's no
     *         matching ReviewAuction.
     *
     * @throws IllegalArgumentException
     *             if auctionCategoryId &lt;= 0
     * @throws ReviewAuctionManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuction> searchOpenAuctions(long auctionCategoryId) throws ReviewAuctionManagerException {
        String signature = CLASS_NAME + ".searchOpenAuctions(long auctionCategoryId)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"auctionCategoryId"},
            new Object[] {auctionCategoryId});

        try {

            List<ReviewAuction> result = searchOpenAuctions(auctionCategoryId, null);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ReviewAuctionManagerException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Search open auctions by auction category ID and project category IDs.
     *
     * @param auctionCategoryId
     *            the auction category ID
     * @param projectCategoryIds
     *            the list of project category ids (can be null if no need to filter by project categories)
     *
     * @return the list of ReviewAuction's matching the search filter, empty list will be returned if there's no
     *         matching ReviewAuction.
     *
     * @throws IllegalArgumentException
     *             if auctionCategoryId &lt;= 0 or projectCategoryIds is empty or contains null or non-positive long
     *             value.
     * @throws ReviewAuctionManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuction> searchOpenAuctions(long auctionCategoryId, List<Long> projectCategoryIds)
        throws ReviewAuctionManagerException {
        String signature = CLASS_NAME + ".searchOpenAuctions(long auctionCategoryId, List<Long> projectCategoryIds)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"auctionCategoryId", "projectCategoryIds"},
            new Object[] {auctionCategoryId, projectCategoryIds});

        checkArgs(log, signature, auctionCategoryId, projectCategoryIds);

        try {
            Filter filter = new EqualToFilter("projectStatusId", 1);
            if (projectCategoryIds != null) {
                filter = new AndFilter(filter, new InFilter("projectCategoryId", projectCategoryIds));
            }

            CustomResultSet resultSet = search(log, signature, auctionCategoryId, filter);

            // Create a list for result
            List<ReviewAuction> result = new ArrayList<ReviewAuction>();
            Map<Long, Long> reviewersNeeded = new HashMap<Long, Long>();

            // Call getAuctionTypes to initialize the auction types cache if not yet
            getAuctionTypes();

            while (resultSet.next()) {
                boolean isOpen = resultSet.getBoolean("open");
                if (isOpen) {

                    ReviewAuction reviewAuction = new ReviewAuction();

                    reviewAuction.setId(resultSet.getLong("review_auction_id"));

                    reviewAuction.setOpen(true);

                    reviewAuction.setAuctionType(auctionTypesMap.get(resultSet.getLong("review_auction_type_id")));
                    reviewAuction.setAssignmentDate(resultSet.getDate("assignment_date"));
                    reviewAuction.setProjectId(resultSet.getLong("project_id"));
                    reviewAuction.setOpenPositions(new ArrayList<Long>());

                    result.add(reviewAuction);

                    reviewersNeeded.put(reviewAuction.getId(), resultSet.getLong("reviewers_required"));
                }
            }

            // If result list is empty, return directly
            if (!result.isEmpty()) {
                prepareResult(result, reviewersNeeded);
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Failed to search open auctions.", e));
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Failed to search open auctions.", e));
        } catch (InvalidCursorStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Failed to search open auctions.", e));
        } catch (NullColumnValueException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Failed to search open auctions.", e));
        } catch (ClassCastException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "The result is invalid.", e));
        }
    }

    /**
     * Retrieve the review auction by auction ID.
     *
     * @param auctionId
     *            the auction ID
     *
     * @return the ReviewAuction, or null if there's no such ReviewAuction.
     *
     * @throws IllegalArgumentException
     *             if auctionId &lt;= 0
     * @throws ReviewAuctionManagerException
     *             if any other error occurred during the operation
     */
    public ReviewAuction getAuction(long auctionId) throws ReviewAuctionManagerException {
        String signature = CLASS_NAME + ".getAuction(long auctionId)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"auctionId"},
            new Object[] {auctionId});

        try {
            long auctionCategoryId;

            ReviewAuction reviewAuction = null;
            try {
                auctionCategoryId = persistence.getAuctionCategoryId(auctionId);
            } catch (ReviewAuctionPersistenceException e) {
                if (e.getMessage().startsWith("No auction category found")) {
                    // Log Exit
                    LoggingWrapperUtility.logExit(log, signature, new Object[] {reviewAuction});
                    return reviewAuction;
                }

                throw e;
            }

            Filter filter = new EqualToFilter("id", auctionId);

            CustomResultSet resultSet = search(log, signature, auctionCategoryId, filter);

            // Call getAuctionTypes to initialize the auction types cache if not yet
            getAuctionTypes();

            if (resultSet.next()) {
                reviewAuction = getReviewAuction(resultSet);
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {reviewAuction});
            return reviewAuction;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Failed to get review auction.", e));
        } catch (ClassCastException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "The result is invalid.", e));
        } catch (ReviewAuctionManagerException e) {
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
     * @throws ReviewAuctionManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuctionCategory> getAuctionCategories() throws ReviewAuctionManagerException {
        String signature = CLASS_NAME + ".getAuctionCategories()";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature, null, null);

        try {
            if (auctionCategories == null) {
                auctionCategories = persistence.getAuctionCategories();
            }

            List<ReviewAuctionCategory> result = Collections.unmodifiableList(auctionCategories);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Failed to get all auction categories.", e));
        }
    }

    /**
     * Returns all auction types.
     *
     * @return all auction types, empty list will be returned if there's no auction types defined in the system
     *         (normally won't happen)
     *
     * @throws ReviewAuctionManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuctionType> getAuctionTypes() throws ReviewAuctionManagerException {
        String signature = CLASS_NAME + ".getAuctionTypes()";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature, null, null);

        try {
            if (auctionTypes == null) {
                auctionTypes = persistence.getAuctionTypes();

                auctionTypesMap = new HashMap<Long, ReviewAuctionType>();
                for (ReviewAuctionType auctionType : auctionTypes) {
                    auctionTypesMap.put(auctionType.getId(), auctionType);
                }
            }
            List<ReviewAuctionType> result = Collections.unmodifiableList(auctionTypes);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (ReviewAuctionPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Failed to get all auction types.", e));
        }
    }

    /**
     * Gets the ReviewAuction instance.
     *
     * @param resultSet
     *            the result set
     *
     * @return the ReviewAuction instance.
     *
     * @throws ReviewAuctionPersistenceException
     *             if failed to get resource role IDs
     * @throws ClassCastException
     *             if result is invalid
     * @throws ReviewAuctionManagerException
     *             if any other error occurs
     */
    private ReviewAuction getReviewAuction(CustomResultSet resultSet) throws ReviewAuctionManagerException,
        ReviewAuctionPersistenceException {
        ReviewAuction reviewAuction = new ReviewAuction();

        try {
            reviewAuction.setId(resultSet.getLong("review_auction_id"));

            ReviewAuctionType auctionType = auctionTypesMap.get(resultSet.getLong("review_auction_type_id"));
            reviewAuction.setAuctionType(auctionType);
            reviewAuction.setAssignmentDate(resultSet.getTimestamp("assignment_date"));

            long projectId = resultSet.getLong("project_id");
            reviewAuction.setProjectId(projectId);

            List<Long> openPositions = new ArrayList<Long>();
            reviewAuction.setOpenPositions(openPositions);

            boolean isOpen = resultSet.getBoolean("open");
            reviewAuction.setOpen(isOpen);

            List<ReviewApplicationRole> applicationRoles = auctionType.getApplicationRoles();

            if (isOpen) {
                long reviewersNeeded = resultSet.getLong("reviewers_required");

                Set<Long> projectIds = new HashSet<Long>();
                projectIds.add(projectId);

                // Get the IDs of the resource roles for projects
                Map<Long, List<Long>> assignedProjectResourceRoleIds = persistence
                    .getAssignedProjectResourceRoleIds(projectIds);

                List<Long> assignedProjectResourceRoleIdsList = assignedProjectResourceRoleIds.get(projectId);

                long neededReviewerCount = Math.max(reviewersNeeded, 0);

                boolean hasOpenPositions = hasOpenPositions(neededReviewerCount, openPositions,
                    assignedProjectResourceRoleIdsList, applicationRoles);

                reviewAuction.setOpen(hasOpenPositions);
            } else {
                int applicationRolesSize = applicationRoles.size();
                for (int i = 0; i < applicationRolesSize; i++) {
                    openPositions.add(0L);
                }
            }

            return reviewAuction;
        } catch (IllegalArgumentException e) {
            throw new ReviewAuctionManagerException("Failed to get review auction.", e);
        } catch (InvalidCursorStateException e) {
            throw new ReviewAuctionManagerException("Failed to get review auction.", e);
        } catch (NullColumnValueException e) {
            throw new ReviewAuctionManagerException("Failed to get review auction.", e);
        }
    }

    /**
     * Checks the arguments.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param auctionCategoryId
     *            the auction category ID
     * @param projectCategoryIds
     *            the list of project category ids
     *
     * @throws IllegalArgumentException
     *             if auctionCategoryId &lt;= 0 or projectCategoryIds is empty or contains null or non-positive long
     *             value.
     */
    private static void checkArgs(Log log, String signature, long auctionCategoryId, List<Long> projectCategoryIds) {
        try {
            ParameterCheckUtility.checkPositive(auctionCategoryId, "auctionCategoryId");

            if (projectCategoryIds != null) {
                ParameterCheckUtility.checkNotEmpty(projectCategoryIds, "projectCategoryIds");
                for (Long projectCategoryId : projectCategoryIds) {
                    ParameterCheckUtility.checkNotNull(projectCategoryId, "element of projectCategoryIds");
                    ParameterCheckUtility.checkPositive(projectCategoryId, "element of projectCategoryIds");

                }
            }
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Searches the result.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param auctionCategoryId
     *            the auction category ID
     * @param filter
     *            the filter
     *
     * @return the result set.
     *
     * @throws ReviewAuctionManagerException
     *             if any error occurs
     * @throws ClassCastException
     *             if the result is invalid
     */
    private CustomResultSet search(Log log, String signature, long auctionCategoryId, Filter filter)
        throws ReviewAuctionManagerException {
        try {
            // Search using Search Builder
            if (auctionCategoryId == specReviewAuctionCategoryId) {
                return (CustomResultSet) specReviewAuctionSearchBundle.search(filter);
            }
            if (auctionCategoryId == contestReviewAuctionCategoryId) {
                return (CustomResultSet) contestReviewAuctionSearchBundle.search(filter);
            }
            if (auctionCategoryId == iterativeReviewAuctionCategoryId) {
                return (CustomResultSet) iterativeReviewAuctionSearchBundle.search(filter);
            }
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Unsupported auction category ID: " + auctionCategoryId));
        } catch (SearchBuilderException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ReviewAuctionManagerException(
                "Failed to search the result.", e));
        }
    }

    /**
     * Removes review auction which has no open position.
     *
     * @param result
     *            the result
     * @param reviewersNeeded
     *            the reviewers needed map
     *
     * @throws ReviewAuctionPersistenceException
     *             if fails to get assigned resource role IDs
     */
    private void prepareResult(List<ReviewAuction> result, Map<Long, Long> reviewersNeeded)
        throws ReviewAuctionPersistenceException {
        Set<Long> projectIds = new HashSet<Long>();
        for (ReviewAuction auction : result) {
            projectIds.add(auction.getProjectId());
        }

        // Compute open positions

        // Get the IDs of the resource roles for projects
        Map<Long, List<Long>> assignedProjectResourceRoleIds = persistence
            .getAssignedProjectResourceRoleIds(projectIds);

        for (Iterator<ReviewAuction> it = result.iterator(); it.hasNext();) {
            ReviewAuction auction = it.next();

            long neededReviewerCount = Math.max(0, reviewersNeeded.get(auction.getId()));

            List<ReviewApplicationRole> applicationRoles = auction.getAuctionType().getApplicationRoles();
            List<Long> assignedProjectResourceRoleIdsList = assignedProjectResourceRoleIds.get(auction.getProjectId());
            List<Long> openPositions = auction.getOpenPositions();

            boolean hasOpenPositions = hasOpenPositions(neededReviewerCount, openPositions,
                assignedProjectResourceRoleIdsList, applicationRoles);

            if (!hasOpenPositions) {
                it.remove();
            }
        }
    }

    /**
     * Computes open positions.
     *
     * @param neededReviewerCount
     *            the needed reviewer count
     * @param openPositions
     *            the open positions list
     * @param assignedProjectResourceRoleIdsList
     *            the assigned project resource role IDs list
     * @param applicationRoles
     *            the application roles
     *
     * @return true if the project has open positions; false otherwise.
     */
    private static boolean hasOpenPositions(long neededReviewerCount, List<Long> openPositions,
        List<Long> assignedProjectResourceRoleIdsList, List<ReviewApplicationRole> applicationRoles) {
        for (ReviewApplicationRole role : applicationRoles) {
            // Check if the application role is closed
            boolean isRoleClosed = false;
            for (ReviewApplicationResourceRole resourceRole : role.getResourceRoles()) {
                if (resourceRole.isUniqueRole()
                    && (assignedProjectResourceRoleIdsList != null)
                    && assignedProjectResourceRoleIdsList.contains(resourceRole.getResourceRoleId())) {
                    isRoleClosed = true;
                    break;
                }
            }
            if (isRoleClosed) {
                openPositions.add(0L);
            } else {
                long positionsOpen = Math.min(neededReviewerCount, role.getPositions());

                openPositions.add(positionsOpen);
                neededReviewerCount -= positionsOpen;
            }
        }
        for (long positions : openPositions) {
            if (positions > 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>
     * Gets a positive value from given configuration object.
     * </p>
     *
     * @param config
     *            the given configuration object.
     * @param key
     *            the property key.
     *
     * @return the retrieved property value.
     *
     * @throws ReviewApplicationManagementConfigurationException
     *             if the property is missing, not a string, an empty string, not positive or some error occurred.
     */
    private static long getPropertyPositive(ConfigurationObject config, String key) {
        String valueStr = Helper.getProperty(config, key, true);

        try {
            long value = Long.parseLong(valueStr);
            if (value <= 0) {
                throw new ReviewApplicationManagementConfigurationException("The property '" + key
                    + "' should be positive.");
            }

            return value;
        } catch (NumberFormatException e) {
            throw new ReviewApplicationManagementConfigurationException("The property '" + key
                + "' can't be parsed as a long.");
        }
    }
}
