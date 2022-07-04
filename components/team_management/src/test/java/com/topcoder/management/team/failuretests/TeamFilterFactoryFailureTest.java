/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.failuretests;

import com.topcoder.management.team.TeamFilterFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for TeamFilterFactory class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class TeamFilterFactoryFailureTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamFilterFactoryFailureTest.class);
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
            TeamFilterFactory.createNameFilter(null);
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
            TeamFilterFactory.createNameFilter("   ");
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
            TeamFilterFactory.createProjectIdFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createCaptainResourceIdFilter(long captainResourceId)</code> method.
     *
     * <p>
     * captainResourceId is negative.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateCaptainResourceIdFilter_failure_negative_captainResourceId() throws Exception {
        try {
            TeamFilterFactory.createCaptainResourceIdFilter(-1);
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
            TeamFilterFactory.createCustomPropertyNameFilter(null);
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
            TeamFilterFactory.createCustomPropertyNameFilter("   ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
