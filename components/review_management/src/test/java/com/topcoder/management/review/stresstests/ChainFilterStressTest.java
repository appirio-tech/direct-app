/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.stresstests;

import com.topcoder.management.review.ChainFilter;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.TestCase;

/**
 * <p>
 * Stress test of ChainFilter.
 * </p>
 * @author still
 * @version 1.0
 */
public class ChainFilterStressTest extends TestCase {
    /** The number of times each method will be run. */
    public static final int RUN_TIMES = 100000;

    /**
     * Stress test of the default constructor of ChainFilter.
     */
    public void testCtor() {
        Filter filter = new EqualToFilter("committed", new Integer(1));
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertNotNull("Failed to create ChainFilter.",
                    new ChainFilter(filter));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing ChainFilter(Filter) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of ChainFilter.and(Filter).
     *
     */
    public void testAnd() {
        Filter filter1 = new EqualToFilter("committed", new Integer(1));
        Filter filter2 = new EqualToFilter("reviewer", new Long(10000));
        Filter expectedFilter = new AndFilter(filter1, filter2);
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            ChainFilter chainFilter = new ChainFilter(filter1);
            Filter resultFilter = chainFilter.and(filter2).getFilter();
            assertEquals("Should be the euqal.", expectedFilter.getFilterType()
                    , resultFilter.getFilterType());
            assertEquals("Should be the euqal.", chainFilter.getFilter().getFilterType()
                    , filter1.getFilterType());
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing and(Filter) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of ChainFilter.or(Filter).
     *
     */
    public void testOr() {
        Filter filter1 = new EqualToFilter("committed", new Integer(1));
        Filter filter2 = new EqualToFilter("reviewer", new Long(10000));
        Filter expectedFilter = new OrFilter(filter1, filter2);
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            Filter resultFilter = new ChainFilter(filter1).or(filter2).getFilter();
            assertEquals("Should be the euqal.", expectedFilter.getFilterType()
                    , resultFilter.getFilterType());
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing or(Filter) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of ChainFilter.not().
     *
     */
    public void testNot() {
        Filter filter1 = new EqualToFilter("committed", new Integer(1));
        Filter expectedFilter = new NotFilter(filter1);
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            Filter resultFilter = new ChainFilter(filter1).not().getFilter();
            assertEquals("Should be the euqal.", expectedFilter.getFilterType()
                    , resultFilter.getFilterType());
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing not() for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
    /**
     * Stress test of ChainFilter.getFilter().
     *
     */
    public void testGetFilter() {
        Filter filter = new BetweenFilter("reviewer", new Integer(10000), new Integer(100));
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertEquals("Should be the equal.",
                    new ChainFilter(filter).getFilter(), filter);
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing getFilter() for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
}
