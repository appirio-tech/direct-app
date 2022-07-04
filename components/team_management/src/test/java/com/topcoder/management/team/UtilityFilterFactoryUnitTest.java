/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.util.Arrays;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for UtilityFilterFactory class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UtilityFilterFactoryUnitTest extends TestCase {
    /** Provide some simple filters for testing. */
    private Filter f1 = new NullFilter("null");

    /** Provide some simple filters for testing. */
    private Filter f2 = new BetweenFilter("between", new Long(1), new Long(10));

    /** Provide some simple filters for testing. */
    private Filter f3 = new GreaterThanFilter("greater", new Long(1));

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UtilityFilterFactoryUnitTest.class);
    }

    /**
     * Accuracy test of <code>createAndFilter(Filter first, Filter second)</code> method.
     * <p>
     * Simply create a filter and check the correctness.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateAndFilter1_Accuracy() throws Exception {
        AndFilter filter = (AndFilter) UtilityFilterFactory.createAndFilter(f1, f2);

        assertTrue("inner filters incorrect", Arrays.equals(filter.getFilters().toArray(new Filter[0]), new Filter[] {
            f1, f2}));
    }

    /**
     * Failure test of <code>createAndFilter(Filter first, Filter second)</code> method.
     * <p>
     * Call this method with null first.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateAndFilter1_Failure1() throws Exception {
        try {
            UtilityFilterFactory.createAndFilter(null, f2);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createAndFilter(Filter first, Filter second)</code> method.
     * <p>
     * Call this method with null second.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateAndFilter1_Failure2() throws Exception {
        try {
            UtilityFilterFactory.createAndFilter(f1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createAndFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with a bunch of filters.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateAndFilter2_Accuracy1() throws Exception {
        AndFilter filter = (AndFilter) UtilityFilterFactory.createAndFilter(new Filter[] {f1, f2, f3});

        assertTrue("inner filters incorrect", Arrays.equals(filter.getFilters().toArray(new Filter[0]), new Filter[] {
            f1, f2, f3}));
    }

    /**
     * Accuracy test of <code>createAndFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with a single filter.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateAndFilter2_Accuracy2() throws Exception {
        AndFilter filter = (AndFilter) UtilityFilterFactory.createAndFilter(new Filter[] {f1});

        assertTrue("inner filters incorrect", Arrays.equals(filter.getFilters().toArray(new Filter[0]),
            new Filter[] {f1}));
    }

    /**
     * Failure test of <code>createAndFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with null filters.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateAndFilter2_Failure1() throws Exception {
        try {
            UtilityFilterFactory.createAndFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createAndFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with empty filters.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateAndFilter2_Failure2() throws Exception {
        try {
            UtilityFilterFactory.createAndFilter(new Filter[0]);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createAndFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with filters containing nulls.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateAndFilter2_Failure3() throws Exception {
        try {
            UtilityFilterFactory.createAndFilter(new Filter[] {f1, null});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createOrFilter(Filter first, Filter second)</code> method.
     * <p>
     * Simply create a filter and check the correctness.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateOrFilter1_Accuracy() throws Exception {
        OrFilter filter = (OrFilter) UtilityFilterFactory.createOrFilter(f1, f2);

        assertTrue("inner filters incorrect", Arrays.equals(filter.getFilters().toArray(new Filter[0]), new Filter[] {
            f1, f2}));
    }

    /**
     * Failure test of <code>createOrFilter(Filter first, Filter second)</code> method.
     * <p>
     * Call this method with null first.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateOrFilter1_Failure1() throws Exception {
        try {
            UtilityFilterFactory.createOrFilter(null, f2);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createOrFilter(Filter first, Filter second)</code> method.
     * <p>
     * Call this method with null second.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateOrFilter1_Failure2() throws Exception {
        try {
            UtilityFilterFactory.createOrFilter(f1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createOrFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with a bunch of filters.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateOrFilter2_Accuracy1() throws Exception {
        OrFilter filter = (OrFilter) UtilityFilterFactory.createOrFilter(new Filter[] {f1, f2, f3});

        assertTrue("inner filters incorrect", Arrays.equals(filter.getFilters().toArray(new Filter[0]), new Filter[] {
            f1, f2, f3}));
    }

    /**
     * Accuracy test of <code>createOrFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with a single filter.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateOrFilter2_Accuracy2() throws Exception {
        OrFilter filter = (OrFilter) UtilityFilterFactory.createOrFilter(new Filter[] {f1});

        assertTrue("inner filters incorrect", Arrays.equals(filter.getFilters().toArray(new Filter[0]),
            new Filter[] {f1}));
    }

    /**
     * Failure test of <code>createOrFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with null filters.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateOrFilter2_Failure1() throws Exception {
        try {
            UtilityFilterFactory.createOrFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createOrFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with empty filters.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateOrFilter2_Failure2() throws Exception {
        try {
            UtilityFilterFactory.createOrFilter(new Filter[0]);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createOrFilter(Filter[] filters)</code> method.
     * <p>
     * Call this method with filters containing nulls.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateOrFilter2_Failure3() throws Exception {
        try {
            UtilityFilterFactory.createOrFilter(new Filter[] {f1, null});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createNotFilter(Filter filter)</code> method.
     * <p>
     * Call this method with a given filter.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateNotFilterAccuracy() throws Exception {
        NotFilter filter = (NotFilter) UtilityFilterFactory.createNotFilter(f3);

        assertEquals("inner filters incorrect", ((GreaterThanFilter) f3).getName(), ((GreaterThanFilter) filter
            .getFilter()).getName());
        assertEquals("inner filters incorrect", ((GreaterThanFilter) f3).getValue(), ((GreaterThanFilter) filter
            .getFilter()).getValue());
    }

    /**
     * Failure test of <code>createNotFilter(Filter filter)</code> method.
     * <p>
     * Call this method with null filter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateNotFilterFailure() throws Exception {
        try {
            UtilityFilterFactory.createNotFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
