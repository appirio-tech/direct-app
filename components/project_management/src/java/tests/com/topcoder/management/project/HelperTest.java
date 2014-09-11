/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Helper class.
 *
 * @author iamajia
 * @version 1.0
 */
public class HelperTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(HelperTest.class);
    }

    /**
     * Accuracy test of <code>checkNumberPositive(long number, String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCheckNumberPositiveAccuracy1() throws Exception {
        Helper.checkNumberPositive(1, "test");
    }

    /**
     * Accuracy test of <code>checkNumberPositive(long number, String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCheckNumberPositiveAccuracy2() throws Exception {
        Helper.checkNumberPositive(10, "test");
    }

    /**
     * Failure test of <code>checkNumberPositive(long number, String name)</code> method.
     * <p>
     * number <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCheckNumberPositiveFailure1() throws Exception {
        try {
            Helper.checkNumberPositive(0, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>checkNumberPositive(long number, String name)</code> method.
     * <p>
     * number <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCheckNumberPositiveFailure2() throws Exception {
        try {
            Helper.checkNumberPositive(-1, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>checkObjectNotNull(Object obj, String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCheckObjectNotNullAccuracy() throws Exception {
        Helper.checkObjectNotNull(new Long(1), "test");
    }

    /**
     * Failure test of <code>checkObjectNotNull(Object obj, String name)</code> method.
     * <p>
     * obj is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCheckObjectNotNullFailure() throws Exception {
        try {
            Helper.checkObjectNotNull(null, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>checkStringNotNullOrEmpty(String str, String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCheckStringNotNullOrEmptyAccuracy() throws Exception {
        Helper.checkStringNotNullOrEmpty("test", "test");
    }

    /**
     * Failure test of <code>checkStringNotNullOrEmpty(String str, String name)</code> method.
     * <p>
     * str is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCheckStringNotNullOrEmptyFailure1() throws Exception {
        try {
            Helper.checkStringNotNullOrEmpty(null, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>checkStringNotNullOrEmpty(String str, String name)</code> method.
     * <p>
     * obj is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCheckStringNotNullOrEmptyFailure2() throws Exception {
        try {
            Helper.checkStringNotNullOrEmpty("   ", "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
