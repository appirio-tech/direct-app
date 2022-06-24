/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.util.List;

/**
 * <p>
 * This interface defines the contract to manage review auctions.
 * </p>
 *
 * <p>
 * It provides methods to create review auction, search open review auctions, retrieve review auction by ID, retrieve
 * auction categories and auction types lookup values.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe when entities
 * passed to them are used by the caller in thread safe manner.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public interface ReviewAuctionManager {
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
    public ReviewAuction createAuction(ReviewAuction auction) throws ReviewAuctionManagerException;

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
    public List<ReviewAuction> searchOpenAuctions(long auctionCategoryId) throws ReviewAuctionManagerException;

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
     *             if auctionCategoryId &lt;= 0, projectCategoryIds is empty or contains null or non-positive long
     *             value.
     * @throws ReviewAuctionManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuction> searchOpenAuctions(long auctionCategoryId, List<Long> projectCategoryIds)
        throws ReviewAuctionManagerException;

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
    public ReviewAuction getAuction(long auctionId) throws ReviewAuctionManagerException;

    /**
     * Returns all auction categories.
     *
     * @return all auction categories, empty list will be returned if there's no auction categories defined in the
     *         system (normally won't happen)
     *
     * @throws ReviewAuctionManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuctionCategory> getAuctionCategories() throws ReviewAuctionManagerException;

    /**
     * Returns all auction types.
     *
     * @return all auction types, empty list will be returned if there's no auction types defined in the system
     *         (normally won't happen)
     *
     * @throws ReviewAuctionManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewAuctionType> getAuctionTypes() throws ReviewAuctionManagerException;
}
