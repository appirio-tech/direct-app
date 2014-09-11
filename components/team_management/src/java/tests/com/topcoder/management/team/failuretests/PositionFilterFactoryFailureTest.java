/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.failuretests;

import com.topcoder.management.team.PositionFilterFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for PositionFilterFactory class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class PositionFilterFactoryFailureTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PositionFilterFactoryFailureTest.class);
    }

    /**
     * Failure test of <code>createNameFilter(String name)</code> method.
     *
     * <p>
     * name is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateNameFilter_failure_null_name() throws Exception {
        try {
            PositionFilterFactory.createNameFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>createNameFilter(String name)</code> method.
     *
     * <p>
     * name is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateNameFilter_failure_empty_name() throws Exception {
        try {
            PositionFilterFactory.createNameFilter("    ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>createResourceIdFilter(long resourceId)</code> method.
     *
     * <p>
     * resourceId is negative.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateResourceIdFilter_failure_negative_resourceId() throws Exception {
        try {
            PositionFilterFactory.createResourceIdFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeamIdFilter(long teamId)</code> method.
     *
     * <p>
     * teamId is negative.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeamIdFilter_failure_negative_teamId() throws Exception {
        try {
            PositionFilterFactory.createTeamIdFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createProjectIdFilter(long projectId)</code> method.
     *
     * <p>
     * projectId is negative.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateProjectIdFilter_failure_negative_projectId() throws Exception {
        try {
            PositionFilterFactory.createProjectIdFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createCustomPropertyNameFilter(String name)</code> method.
     *
     * <p>
     * name is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateCustomPropertyNameFilter_failure_null_name() throws Exception {
        try {
            PositionFilterFactory.createCustomPropertyNameFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createCustomPropertyNameFilter(String name)</code> method.
     *
     * <p>
     * name is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateCustomPropertyNameFilter_failure_empty_name() throws Exception {
        try {
            PositionFilterFactory.createCustomPropertyNameFilter("   ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
