/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.ReviewAuctionCategory;
import com.topcoder.management.review.application.ReviewAuctionType;

/**
 * <p>
 * This interface defines contract for accessing review auction related data from persistence.
 * </p>
 *
 * <p>
 * It provides methods to create review auction, get auction types and auction categories lookup values, get auction
 * category ID for a given auction ID, and get assigned project resource IDs for given project IDs.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe when configure()
 * method is called just once right after instantiation and entities passed to them are used by the caller in thread
 * safe manner.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public interface ReviewAuctionPersistence {
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
    public ReviewAuction createAuction(ReviewAuction auction) throws ReviewAuctionPersistenceException;

    /**
     * Returns all auction types.
     *
     * @return all auction types, empty list will be returned if there's no auction types defined in the system
     *         (normally won't happen)
     *
     * @throws ReviewAuctionPersistenceException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuctionType> getAuctionTypes() throws ReviewAuctionPersistenceException;

    /**
     * Returns all auction categories.
     *
     * @return all auction categories, empty list will be returned if there's no auction categories defined in the
     *         system (normally won't happen)
     *
     * @throws ReviewAuctionPersistenceException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuctionCategory> getAuctionCategories() throws ReviewAuctionPersistenceException;

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
     *             if projectIds is null or empty, or contains null or non-positive item.
     * @throws ReviewAuctionPersistenceException
     *             if any other error occurred during the operation
     */
    public Map<Long, List<Long>> getAssignedProjectResourceRoleIds(Set<Long> projectIds)
        throws ReviewAuctionPersistenceException;

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
    public long getAuctionCategoryId(long auctionId) throws ReviewAuctionPersistenceException;

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *            the configuration object
     *
     * @throws IllegalArgumentException
     *             if configuration is null (is not thrown by implementations that don't use any configuration
     *             parameters)
     * @throws ReviewApplicationManagementConfigurationException
     *             if some error occurred when initializing an instance using the given configuration (is not thrown by
     *             implementations that don't use any configuration parameters)
     */
    public void configure(ConfigurationObject configuration);
}
