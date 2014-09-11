/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.failuretests.search;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.management.review.application.search.ReviewApplicationFilterBuilder;

/**
 * <p>
 * Failure test for ReviewApplicationFilterBuilder class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewApplicationFilterBuilderFailureTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewApplicationFilterBuilderFailureTests.class);
    }

    /**
     * <p>
     * Failure test for createAuctionIdFilter(long auctionId) method, when auctionId is not positive, throws
     * IllegalArgumentException.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateAuctionIdFilter_AuctionIdIsZero() {
        ReviewApplicationFilterBuilder.createAuctionIdFilter(0);
    }

    /**
     * <p>
     * Failure test for createAuctionIdFilter(long auctionId) method, when auctionId is not positive, throws
     * IllegalArgumentException.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateAuctionIdFilter_AuctionIdIsNegative() {
        ReviewApplicationFilterBuilder.createAuctionIdFilter(-1);
    }

    /**
     * <p>
     * Failure test for createUserIdFilter(long userId) method, when userId is not positive, throws
     * IllegalArgumentException.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserIdFilter_UserIdIsZero() {
        ReviewApplicationFilterBuilder.createUserIdFilter(0);
    }

    /**
     * <p>
     * Failure test for createUserIdFilter(long userId) method, when userId is not positive, throws
     * IllegalArgumentException.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserIdFilter_UserIdIsNegative() {
        ReviewApplicationFilterBuilder.createUserIdFilter(-1);
    }

    /**
     * <p>
     * Failure test for createApplicationStatusIdFilter(long statusId) method, when statusId is not positive, throws
     * IllegalArgumentException.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateApplicationStatusIdFilter_StatusIdIsZero() {
        ReviewApplicationFilterBuilder.createApplicationStatusIdFilter(0);
    }

    /**
     * <p>
     * Failure test for createApplicationStatusIdFilter(long statusId) method, when statusId is not positive, throws
     * IllegalArgumentException.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateApplicationStatusIdFilter_StatusIdIsNegative() {
        ReviewApplicationFilterBuilder.createApplicationStatusIdFilter(-1);
    }
}