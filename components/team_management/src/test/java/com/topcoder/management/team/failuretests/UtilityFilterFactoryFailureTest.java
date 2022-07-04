/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.failuretests;

import com.topcoder.management.team.TeamFilterFactory;
import com.topcoder.management.team.UtilityFilterFactory;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for UtilityFilterFactory class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class UtilityFilterFactoryFailureTest extends TestCase {
    /**
     * Represents a Filter that is used in the test.
     */
    private Filter first = TeamFilterFactory.createNameFilter("a");

    /**
     * Represents another Filter that is used in the test.
     */
    private Filter second = TeamFilterFactory.createProjectIdFilter(1);

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UtilityFilterFactoryFailureTest.class);
    }

    /**
     * Failure test of <code>createAndFilter(Filter first, Filter second)</code>
     * method.
     * <p>
     * first is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateAndFilter_failure_null_first() throws Exception {
        try {
            UtilityFilterFactory.createAndFilter(null, second);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createAndFilter(Filter first, Filter second)</code>
     * method.
     * <p>
     * second is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateAndFilter_failure_null_second() throws Exception {
        try {
            UtilityFilterFactory.createAndFilter(first, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createAndFilter(Filter[] filters)</code> method.
     * <p>
     * filters is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateAndFilter_failure_null_filters() throws Exception {
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
     * filters is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateAndFilter_failure_empty_filters() throws Exception {
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
     * filters contains null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateAndFilter_failure_filters_contains_null() throws Exception {
        try {
            UtilityFilterFactory.createAndFilter(new Filter[]{first, second, null });
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createOrFilter(Filter first, Filter second)</code>
     * method.
     * <p>
     * first is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateOrFilter_failure_null_first() throws Exception {
        try {
            UtilityFilterFactory.createOrFilter(null, second);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createOrFilter(Filter first, Filter second)</code>
     * method.
     * <p>
     * second is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateOrFilter_failure_null_second() throws Exception {
        try {
            UtilityFilterFactory.createOrFilter(first, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createOrFilter(Filter[] filters)</code> method.
     * <p>
     * filters is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateOrFilter_failure_null_filters() throws Exception {
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
     * filters is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateOrFilter_failure_empty_filters() throws Exception {
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
     * filters contains null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateOrFilter_failure_filters_contains_null() throws Exception {
        try {
            UtilityFilterFactory.createOrFilter(new Filter[]{first, second, null });
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createNotFilter(Filter filter)</code> method.
     * <p>
     * filter is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateNotFilter_failure_null_filter() throws Exception {
        try {
            UtilityFilterFactory.createNotFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
