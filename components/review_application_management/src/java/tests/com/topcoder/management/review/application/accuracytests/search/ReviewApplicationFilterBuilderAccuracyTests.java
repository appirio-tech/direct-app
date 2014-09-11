/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.accuracytests.search;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.management.review.application.search.ReviewApplicationFilterBuilder;
import com.topcoder.search.builder.filter.EqualToFilter;

/**
 * <p>
 * Accuracy test for ReviewApplicationFilterBuilder class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewApplicationFilterBuilderAccuracyTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewApplicationFilterBuilderAccuracyTests.class);
    }

    /**
     * <p>
     * Accuracy test for createAuctionIdFilter(long auctionId), the filter should be returned.
     * </p>
     */
    @Test
    public void testCreateAuctionIdFilter() {
        EqualToFilter filter = (EqualToFilter) ReviewApplicationFilterBuilder.createAuctionIdFilter(1);

        assertEquals("The name is incorrect.", "auctionId", filter.getName());
        assertEquals("The value is incorrect.", new Long(1), filter.getValue());
    }

    /**
     * <p>
     * Accuracy test for createUserIdFilter(long userId), the filter should be returned.
     * </p>
     */
    @Test
    public void testCreateUserIdFilter() {
        EqualToFilter filter = (EqualToFilter) ReviewApplicationFilterBuilder.createUserIdFilter(2);

        assertEquals("The name is incorrect.", "userId", filter.getName());
        assertEquals("The value is incorrect.", new Long(2), filter.getValue());
    }

    /**
     * <p>
     * Accuracy test for createApplicationStatusIdFilter(long statusId), the filter should be returned.
     * </p>
     */
    @Test
    public void testCreateApplicationStatusIdFilter() {
        EqualToFilter filter = (EqualToFilter) ReviewApplicationFilterBuilder.createApplicationStatusIdFilter(3);

        assertEquals("The name is incorrect.", "statusId", filter.getName());
        assertEquals("The value is incorrect.", new Long(3), filter.getValue());
    }
}