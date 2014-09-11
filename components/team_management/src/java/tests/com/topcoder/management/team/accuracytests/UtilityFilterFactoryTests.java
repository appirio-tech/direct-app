/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.team.accuracytests;

import com.topcoder.management.team.UtilityFilterFactory;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Accuracy test cases for the <code>UtilityFilterFactory</code> class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class UtilityFilterFactoryTests extends TestCase {
    /**
     * A test filter.
     */
    private static final Filter FILTER_1 = new EqualToFilter("prop1", "value1");

    /**
     * A test filter.
     */
    private static final Filter FILTER_2 = new EqualToFilter("prop2", "value2");

    /**
     * A test filter.
     */
    private static final Filter FILTER_3 = new EqualToFilter("prop3", "value3");

    /**
     * Tests the <code>createAndFilter(Filter, Filter)</code> method.
     */
    public void testCreateAndFilter2() {
        AndFilter filter = (AndFilter) UtilityFilterFactory.createAndFilter(FILTER_1, FILTER_2);
        Iterator it = filter.getFilters().iterator();
        assertEquals("the first filter should be FILTER_1", FILTER_1, it.next());
        assertEquals("the second filter should be FILTER_2", FILTER_2, it.next());
    }

    /**
     * Tests the <code>createAndFilter(Filter[])</code> method.
     */
    public void testCreateAndFilter1() {
        AndFilter filter =
            (AndFilter) UtilityFilterFactory.createAndFilter(new Filter[] {FILTER_1, FILTER_2, FILTER_3});
        Iterator it = filter.getFilters().iterator();
        assertEquals("the first filter should be FILTER_1", FILTER_1, it.next());
        assertEquals("the second filter should be FILTER_2", FILTER_2, it.next());
        assertEquals("the second filter should be FILTER_3", FILTER_3, it.next());
    }

    /**
     * Tests the <code>createOrFilter(Filter, Filter)</code> method.
     */
    public void testCreateOrFilter2() {
        OrFilter filter = (OrFilter) UtilityFilterFactory.createOrFilter(FILTER_1, FILTER_2);
        Iterator it = filter.getFilters().iterator();
        assertEquals("the first filter should be FILTER_1", FILTER_1, it.next());
        assertEquals("the second filter should be FILTER_2", FILTER_2, it.next());
    }

    /**
     * Tests the <code>createOrFilter(Filter[])</code> method.
     */
    public void testCreateOrFilter1() {
        OrFilter filter =
            (OrFilter) UtilityFilterFactory.createOrFilter(new Filter[] {FILTER_1, FILTER_2, FILTER_3});
        Iterator it = filter.getFilters().iterator();
        assertEquals("the first filter should be FILTER_1", FILTER_1, it.next());
        assertEquals("the second filter should be FILTER_2", FILTER_2, it.next());
        assertEquals("the second filter should be FILTER_3", FILTER_3, it.next());
    }

    /**
     * Tests the <code>createNotFilter</code> method.
     */
    public void testCreateNotFilter() {
        NotFilter filter = (NotFilter) UtilityFilterFactory.createNotFilter(FILTER_1);
        assertTrue("the negated filter should be FILTER_1", filter.getFilter() instanceof EqualToFilter);
    }
}
