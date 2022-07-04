/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.search;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a static helper class that provides factory methods for creating filters that can be used when searching for
 * review applications using ReviewApplicationManagerImpl and possibly other implementations of
 * ReviewApplicationManager.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public final class ReviewApplicationFilterBuilder {
    /**
     * Empty private constructor.
     */
    private ReviewApplicationFilterBuilder() {
        // Empty
    }

    /**
     * Creates a filter that selects review applications by review auction ID.
     *
     * @param auctionId
     *            the ID of the review auction
     *
     * @return the filter
     *
     * @throws IllegalArgumentException
     *             if auctionId &lt;= 0
     */
    public static Filter createAuctionIdFilter(long auctionId) {
        ParameterCheckUtility.checkPositive(auctionId, "auctionId");

        return new EqualToFilter("auctionId", auctionId);
    }

    /**
     * Creates a filter that selects review applications by user ID.
     *
     * @param userId
     *            the ID of user
     *
     * @return the ID of user
     *
     * @throws IllegalArgumentException
     *             if userId &lt;= 0
     */
    public static Filter createUserIdFilter(long userId) {
        ParameterCheckUtility.checkPositive(userId, "userId");

        return new EqualToFilter("userId", userId);
    }

    /**
     * Creates a filter that selects review applications by application status ID.
     *
     * @param statusId
     *            the ID of review application status
     *
     * @return the ID of review application status
     *
     * @throws IllegalArgumentException
     *             if statusId &lt;= 0
     */
    public static Filter createApplicationStatusIdFilter(long statusId) {
        ParameterCheckUtility.checkPositive(statusId, "statusId");

        return new EqualToFilter("statusId", statusId);
    }
}
