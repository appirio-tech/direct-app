/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


/**
 * <p>
 * This class tests the <code>ChainFilter</code> class. It tests the ChainFilter(Filter)
 * constructor and and(Filter), or(Filter), not(), getFilter() methods.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class ChainFilterTest extends TestCase {
    /**
     * <p>
     * The instance of <code>EqualToFilter</code> for unit test.
     * </p>
     */
    private Filter equalFilter = null;

    /**
     * <p>
     * The instance of <code>LessThanFilter</code> for unit test.
     * </p>
     */
    private Filter lessThanFilter = null;

    /**
     * <p>
     * The instance of <code>GreaterThanFilter</code> for unit test.
     * </p>
     */
    private Filter greaterThanFilter = null;

    /**
     * <p>
     * The instance of <code>ChainFilter</code> for unit test.
     * </p>
     */
    private ChainFilter test = null;

    /**
     * <p>
     * Returns the test suite of <code>ChainFilterTest</code>.
     * </p>
     *
     * @return the test suite of <code>ChainFilterTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ChainFilterTest.class);
    }

    /**
     * <p>
     * Set up the unit test envionment here.
     * </p>
     */
    protected void setUp() {
        // create some filters for test.
        lessThanFilter = new LessThanFilter("reviewer", new Long(10000));
        greaterThanFilter = new GreaterThanFilter("project", new Long(10001));
        equalFilter = new EqualToFilter("committed", new Integer(1));

        // create a ChainFilter instance with equal filter.
        test = new ChainFilter(equalFilter);
    }

    /**
     * <p>
     * Basic test of <code>ChainFilter(Filter)</code> constructor.
     * </p>
     */
    public void testChainFilterCtor_Basic() {
        // check null here.
        assertNotNull("Create ChainFilter failed.", new ChainFilter(equalFilter));
    }

    /**
     * <p>
     * Detail test of <code>ChainFilter(Filter)</code> constructor. Creates a instance and get it's
     * attributes for test.
     * </p>
     */
    public void testChainFilterCtor_Detail() {
        // create the instance for test.
        test = new ChainFilter(equalFilter);

        // check null here.
        assertNotNull("Create ChainFilter failed.", test);

        // check the inner filter here.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());
        assertEquals("The inner filter's name should be: committed.", "committed",
            ((EqualToFilter) test.getFilter()).getName());
        assertEquals("The inner filter's value should be: new Integer(1).", new Integer(1),
            ((EqualToFilter) test.getFilter()).getValue());
    }

    /**
     * <p>
     * Tests <code>ChainFilter(Filter)</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> filter into the constructor,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testChainFilterCtorForException_Null() {
        try {
            test = new ChainFilter(null);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>and(Filter)</code> method with two simple filters.
     * </p>
     */
    public void testAnd_TwoFilters() {
        // construct with and operation.
        ChainFilter chainFilter = test.and(lessThanFilter);

        // check the inner filter here.
        assertEquals("The inner filter should be Filter.AND_FILTER.", Filter.AND_FILTER,
            chainFilter.getFilter().getFilterType());

        // check the filter list here.
        List filters = ((AndFilter) chainFilter.getFilter()).getFilters();
        assertEquals("The inner filter list's size should be: 2.", 2, filters.size());
        assertEquals("The first filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            ((Filter) filters.get(0)).getFilterType());
        assertEquals("The second filter should be Filter.LESS_THAN_FILTER.",
            Filter.LESS_THAN_FILTER, ((Filter) filters.get(1)).getFilterType());
    }

    /**
     * <p>
     * Test of <code>and(Filter)</code> method with more filters.
     * </p>
     */
    public void testAnd_MoreFilters() {
        // construct with and operation.
        ChainFilter chainFilter = test.and(lessThanFilter).and(greaterThanFilter);

        // check the inner filter here.
        assertEquals("The inner filter should be Filter.AND_FILTER.", Filter.AND_FILTER,
            chainFilter.getFilter().getFilterType());

        // check the first level filter list here.
        List filters1 = ((AndFilter) chainFilter.getFilter()).getFilters();
        assertEquals("The first level inner filter list's size should be: 2.", 2, filters1.size());
        assertEquals("The first filter in first level filter list should be Filter.AND_FILTER.",
            Filter.AND_FILTER, ((Filter) filters1.get(0)).getFilterType());
        assertEquals("The second filter in first level filter list should be Filter.GREATER_THAN_FILTER.",
            Filter.GREATER_THAN_FILTER, ((Filter) filters1.get(1)).getFilterType());

        // check the second level filter list here.
        List filters2 = ((AndFilter) filters1.get(0)).getFilters();
        assertEquals("The second level inner filter list's size should be: 2.", 2, filters2.size());
        assertEquals("The first filter in second level filter list should be Filter.EQUAL_TO_FILTER.",
            Filter.EQUAL_TO_FILTER, ((Filter) filters2.get(0)).getFilterType());
        assertEquals("The second filter in second level filter list should be Filter.LESS_THAN_FILTER.",
            Filter.LESS_THAN_FILTER, ((Filter) filters2.get(1)).getFilterType());
    }

    /**
     * <p>
     * Test of <code>and(Filter)</code> method for immutable inner filter.
     * </p>
     */
    public void testAnd_Immutable() {
        // construct with and operation.
        ChainFilter chainFilter = test.and(lessThanFilter);

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());

        // perform and operation again.
        ChainFilter newChainFilter = chainFilter.or(lessThanFilter);

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.AND_FILTER.", Filter.AND_FILTER,
            chainFilter.getFilter().getFilterType());

        // perform and operation more times.
        chainFilter = test.and(lessThanFilter).and(greaterThanFilter);

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());
    }

    /**
     * <p>
     * Tests <code>and(Filter)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> filter into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testAndForException_Null() {
        try {
            test.and(null);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>or(Filter)</code> method with two simple filters.
     * </p>
     */
    public void testOr_TwoFilters() {
        // construct with or operation.
        ChainFilter chainFilter = test.or(greaterThanFilter);

        // check the inner filter here.
        assertEquals("The inner filter should be Filter.OR_FILTER.", Filter.OR_FILTER,
            chainFilter.getFilter().getFilterType());

        // check the filter list here.
        List filters = ((OrFilter) chainFilter.getFilter()).getFilters();
        assertEquals("The inner filter list's size should be: 2.", 2, filters.size());
        assertEquals("The first filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            ((Filter) filters.get(0)).getFilterType());
        assertEquals("The second filter should be Filter.GREATER_THAN_FILTER.",
            Filter.GREATER_THAN_FILTER, ((Filter) filters.get(1)).getFilterType());
    }

    /**
     * <p>
     * Test of <code>or(Filter)</code> method with more filters.
     * </p>
     */
    public void testOr_MoreFilters() {
        // construct with or operation.
        ChainFilter chainFilter = test.or(lessThanFilter).or(greaterThanFilter);

        // check the inner filter here.
        assertEquals("The inner filter should be Filter.OR_FILTER.", Filter.OR_FILTER,
            chainFilter.getFilter().getFilterType());

        // check the first level filter list here.
        List filters1 = ((OrFilter) chainFilter.getFilter()).getFilters();
        assertEquals("The first level inner filter list's size should be: 2.", 2, filters1.size());
        assertEquals("The first filter in first level filter list should be Filter.OR_FILTER.",
            Filter.OR_FILTER, ((Filter) filters1.get(0)).getFilterType());
        assertEquals("The second filter in first level filter list should be Filter.GREATER_THAN_FILTER.",
            Filter.GREATER_THAN_FILTER, ((Filter) filters1.get(1)).getFilterType());

        // check the second level filter list here.
        List filters2 = ((OrFilter) filters1.get(0)).getFilters();
        assertEquals("The second level inner filter list's size should be: 2.", 2, filters2.size());
        assertEquals("The first filter in second level filter list should be Filter.EQUAL_TO_FILTER.",
            Filter.EQUAL_TO_FILTER, ((Filter) filters2.get(0)).getFilterType());
        assertEquals("The second filter in second level filter list should be Filter.LESS_THAN_FILTER.",
            Filter.LESS_THAN_FILTER, ((Filter) filters2.get(1)).getFilterType());
    }

    /**
     * <p>
     * Test of <code>or(Filter)</code> method for immutable inner filter.
     * </p>
     */
    public void testOr_Immutable() {
        // construct with or operation.
        ChainFilter chainFilter = test.or(lessThanFilter);

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());

        // perform or operation again.
        ChainFilter newChainFilter = chainFilter.and(lessThanFilter);

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.OR_FILTER.", Filter.OR_FILTER,
            chainFilter.getFilter().getFilterType());

        // perform or operation more times.
        chainFilter = test.or(lessThanFilter).or(greaterThanFilter);

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());
    }

    /**
     * <p>
     * Tests <code>or(Filter)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> filter into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testOrForException_Null() {
        try {
            test.or(null);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>not()</code> method with using it only once.
     * </p>
     */
    public void testNot_Once() {
        // construct with not operation.
        ChainFilter chainFilter = test.not();

        // check the inner filter here.
        assertEquals("The inner filter should be Filter.NOT_FILTER.", Filter.NOT_FILTER,
            chainFilter.getFilter().getFilterType());

        // check the filter list here.
        assertEquals("The first filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            ((NotFilter) chainFilter.getFilter()).getFilter().getFilterType());
    }

    /**
     * <p>
     * Test of <code>not()</code> method with using it more times.
     * </p>
     */
    public void testNot_MoreTimes() {
        // construct with not operation.
        ChainFilter chainFilter = test.not().not();

        // check the inner filter here.
        assertEquals("The inner filter should be Filter.NOT_FILTER.", Filter.NOT_FILTER,
            chainFilter.getFilter().getFilterType());

        // check the first level filter here.
        Filter firstLevel = ((NotFilter) chainFilter.getFilter()).getFilter();
        assertEquals("The second level filter should be Filter.NOT_FILTER.", Filter.NOT_FILTER,
            firstLevel.getFilterType());

        // check the second level filter here.
        Filter secondLevel = ((NotFilter) firstLevel).getFilter();
        assertEquals("The second level filter should be Filter.EQUAL_TO_FILTER.",
            Filter.EQUAL_TO_FILTER, secondLevel.getFilterType());
    }

    /**
     * <p>
     * Test of <code>not()</code> method for immutable inner filter.
     * </p>
     */
    public void testNot_Immutable() {
        // construct with not operation.
        ChainFilter chainFilter = test.not();

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());

        // perform or operation again.
        ChainFilter newChainFilter = chainFilter.and(lessThanFilter);

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.NOT_FILTER.", Filter.NOT_FILTER,
            chainFilter.getFilter().getFilterType());

        // perform or operation more times.
        chainFilter = test.not().not();

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());
    }

    /**
     * <p>
     * Test of the combination of <code>and(Filter)</code>, <code>or(Filter)</code>, and
     * <code>not()</code> methods.
     * </p>
     */
    public void testAllOperations() {
        // construct with all operations.
        ChainFilter chainFilter = test.or(lessThanFilter).and(greaterThanFilter).not();

        // check the inner filter here.
        assertEquals("The inner filter should be Filter.NOT_FILTER.", Filter.NOT_FILTER,
            chainFilter.getFilter().getFilterType());

        // check the first level filter list here.
        Filter andFilter = ((NotFilter) chainFilter.getFilter()).getFilter();
        assertEquals("The inner filter should be Filter.AND_FILTER.", Filter.AND_FILTER,
            andFilter.getFilterType());

        // check the second level filter list here.
        List filters1 = ((AndFilter) andFilter).getFilters();
        assertEquals("The first filter should be Filter.OR_FILTER.", Filter.OR_FILTER,
            ((Filter) filters1.get(0)).getFilterType());
        assertEquals("The second filter should be Filter.GREATER_THAN_FILTER.",
            Filter.GREATER_THAN_FILTER, ((Filter) filters1.get(1)).getFilterType());

        // check the third level filter list here.
        List filters2 = ((OrFilter) filters1.get(0)).getFilters();
        assertEquals("The inner filter list's size should be: 2.", 2, filters2.size());
        assertEquals("The first filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            ((Filter) filters2.get(0)).getFilterType());
        assertEquals("The second filter should be Filter.LESS_THAN_FILTER.",
            Filter.LESS_THAN_FILTER, ((Filter) filters2.get(1)).getFilterType());
    }

    /**
     * <p>
     * Test of <code>getFilter()</code> method for inner filter. It tests for get the original
     * inner filter.
     * </p>
     */
    public void testGetFilter_Original() {
        // perform with and operation.
        test.and(lessThanFilter);

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());

        // perform with not operation.
        test.not();

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());

        // perform with more operations.
        test.or(greaterThanFilter).and(test.getFilter());

        // check the original inner filter.
        assertEquals("The inner filter should be Filter.EQUAL_TO_FILTER.", Filter.EQUAL_TO_FILTER,
            test.getFilter().getFilterType());
    }

    /**
     * <p>
     * Test of <code>getFilter()</code> method for inner filter. It tests for get the new inner
     * filter.
     * </p>
     */
    public void testGetFilter_New() {
        // perform with and operation.
        ChainFilter chainFilter = test.or(lessThanFilter);

        // check the new inner filter.
        assertEquals("The inner filter should be Filter.OR_FILTER.", Filter.OR_FILTER,
            chainFilter.getFilter().getFilterType());

        // perform with not operation.
        chainFilter = chainFilter.not();

        // check the new inner filter.
        assertEquals("The inner filter should be Filter.NOT_FILTER.", Filter.NOT_FILTER,
            chainFilter.getFilter().getFilterType());

        // perform with more operations.
        chainFilter = chainFilter.or(greaterThanFilter).and(test.getFilter());

        // check the new inner filter.
        assertEquals("The inner filter should be Filter.AND_FILTER.", Filter.AND_FILTER,
            chainFilter.getFilter().getFilterType());
    }
}
