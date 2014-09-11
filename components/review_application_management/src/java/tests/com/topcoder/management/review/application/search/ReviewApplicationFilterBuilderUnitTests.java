/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.search;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.search.builder.filter.EqualToFilter;

/**
 * <p>
 * Unit tests for {@link ReviewApplicationFilterBuilder} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReviewApplicationFilterBuilderUnitTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewApplicationFilterBuilderUnitTests.class);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAuctionIdFilter(long auctionId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createAuctionIdFilter() {
        long auctionId = 1;
        EqualToFilter filter = (EqualToFilter) ReviewApplicationFilterBuilder.createAuctionIdFilter(auctionId);

        assertEquals("'createAuctionIdFilter' should be correct.", auctionId, filter.getValue());
    }

    /**
     * <p>
     * Failure test for the method <code>createAuctionIdFilter(long auctionId)</code> with auctionId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAuctionIdFilter_auctionIdNegative() {
        long auctionId = -1;

        ReviewApplicationFilterBuilder.createAuctionIdFilter(auctionId);
    }

    /**
     * <p>
     * Failure test for the method <code>createAuctionIdFilter(long auctionId)</code> with auctionId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAuctionIdFilter_auctionIdZero() {
        long auctionId = 0;

        ReviewApplicationFilterBuilder.createAuctionIdFilter(auctionId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createUserIdFilter(long userId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createUserIdFilter() {
        long userId = 1;
        EqualToFilter filter = (EqualToFilter) ReviewApplicationFilterBuilder.createUserIdFilter(userId);

        assertEquals("'createUserIdFilter' should be correct.", userId, filter.getValue());
    }

    /**
     * <p>
     * Failure test for the method <code>createUserIdFilter(long userId)</code> with userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createUserIdFilter_userIdNegative() {
        long userId = -1;

        ReviewApplicationFilterBuilder.createUserIdFilter(userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createUserIdFilter(long userId)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createUserIdFilter_userIdZero() {
        long userId = 0;

        ReviewApplicationFilterBuilder.createUserIdFilter(userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createApplicationStatusIdFilter(long statusId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createApplicationStatusIdFilter() {
        long statusId = 1;
        EqualToFilter filter = (EqualToFilter) ReviewApplicationFilterBuilder.createApplicationStatusIdFilter(statusId);

        assertEquals("'createApplicationStatusIdFilter' should be correct.", statusId, filter.getValue());
    }

    /**
     * <p>
     * Failure test for the method <code>createApplicationStatusIdFilter(long statusId)</code> with statusId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createApplicationStatusIdFilter_statusIdNegative() {
        long statusId = -1;

        ReviewApplicationFilterBuilder.createApplicationStatusIdFilter(statusId);
    }

    /**
     * <p>
     * Failure test for the method <code>createApplicationStatusIdFilter(long statusId)</code> with statusId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createApplicationStatusIdFilter_statusIdZero() {
        long statusId = 0;

        ReviewApplicationFilterBuilder.createApplicationStatusIdFilter(statusId);
    }
}