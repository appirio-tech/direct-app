/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ChainFilterAccuracyTest.java
 */
package com.topcoder.management.review.accuracytests;

import com.topcoder.management.review.ChainFilter;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.OrFilter;
import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for <code>ChainFilterAccuracy</code> class.
 * </p>
 *
 * @author wiedzmin
 * @version 1.0
 */
public class ChainFilterAccuracyTest extends TestCase {

    /** ChainFilter instance that will be tested.*/
    private ChainFilter chainFilter;

    /**Filter that is used for testing.*/
    private Filter filter;

    /**
     * <p>
     * Set up environment.
     * </p>
     */
    public void setUp() {
        filter = new GreaterThanFilter("age", new Integer(20));
        chainFilter = new ChainFilter(filter);
    }

    /**
     * <p>
     * Test method and().
     * In this test AND filter will be created, that contains two subfilters.
     * Also, inner Filter of base ChainFilter must not be changed.
     * </p>
     */
    public void testAndFilter() {
        //check that inner Filter has type GREATER_THAN_FILTER
        assertEquals("Inner Filter must be immutable.",
                Filter.GREATER_THAN_FILTER, chainFilter.getFilter().getFilterType());

        Filter newFilter = new EqualToFilter("name", "john");
        ChainFilter newChainFilter = chainFilter.and(newFilter);

        //expected filter must have type AND_FILTER
        assertEquals("Filter has invalid type.", Filter.AND_FILTER, newChainFilter.getFilter().getFilterType());

        //new AND filter must contain 2 filters
        assertEquals("AND filter is invalid.", 2, ((AndFilter) newChainFilter.getFilter()).getFilters().size());

        //inner Filter of base ChainFilter must not be changed and still have type
        assertEquals("Inner Filter must not be changed.",
                Filter.GREATER_THAN_FILTER, chainFilter.getFilter().getFilterType());
    }

    /**
     * <p>
     * Test method or().
     * In this test OR filter will be created, that contains two subfilters.
     * Also, inner Filter of base ChainFilter must not be changed.
     * </p>
     */
    public void testOrFilter() {
        //check that inner Filter has type GREATER_THAN_FILTER
        assertEquals("Inner Filter must be immutable.",
                Filter.GREATER_THAN_FILTER, chainFilter.getFilter().getFilterType());

        Filter newFilter = new EqualToFilter("name", "john");
        ChainFilter newChainFilter = chainFilter.or(newFilter);

        //expected filter must have type OR_FILTER
        assertEquals("Filter has invalid type.", Filter.OR_FILTER, newChainFilter.getFilter().getFilterType());

        //new OR filter must contain 2 filters
        assertEquals("AND filter is invalid.", 2, ((OrFilter) newChainFilter.getFilter()).getFilters().size());

        //inner Filter of base ChainFilter must not be changed and still have type
        assertEquals("Inner Filter must not be changed.",
                Filter.GREATER_THAN_FILTER, chainFilter.getFilter().getFilterType());
    }

    /**
     * <p>
     * Test method not().
     * In this test NOT filter will be created.
     * Also, inner Filter of base ChainFilter must not be changed.
     * </p>
     */
    public void testNotFilter() {
        //check that inner Filter has type GREATER_THAN_FILTER
        assertEquals("Inner Filter must be immutable.",
                Filter.GREATER_THAN_FILTER, chainFilter.getFilter().getFilterType());

        ChainFilter notChainFilter = chainFilter.not();

        //expected filter must have type NOT_FILTER
        assertEquals("Filter has invalid type.", Filter.NOT_FILTER, notChainFilter.getFilter().getFilterType());

        //inner Filter of base ChainFilter must not be changed and still have type
        assertEquals("Inner Filter must not be changed.",
                Filter.GREATER_THAN_FILTER, chainFilter.getFilter().getFilterType());
    }
}