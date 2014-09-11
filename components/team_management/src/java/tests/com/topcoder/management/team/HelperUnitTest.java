/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.util.Date;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for Helper class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTest extends TestCase {
    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(HelperUnitTest.class);
    }

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.addConfig();
    }

    /**
     * <p>
     * Tears down the environment for the TestCase.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Accuracy test of <code>assertNonNegative(long number, String name)</code> method.
     * <p>
     * Call this method with a positive integer.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertNonNegativeAccuracy1() throws Exception {
        Helper.assertNonNegative(1, null);
    }

    /**
     * Accuracy test of <code>assertNonNegative(long number, String name)</code> method.
     * <p>
     * Call this method with zero.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertNonNegativeAccuracy2() throws Exception {
        Helper.assertNonNegative(0, null);
    }

    /**
     * Failure test of <code>assertNonNegative(long number, String name)</code> method.
     * <p>
     * Call this method with a negative integer.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertNonNegativeFailure() throws Exception {
        try {
            Helper.assertNonNegative(-1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>assertObjectNotNull(Object obj, String name)</code> method.
     * <p>
     * Test this method with a date object.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertObjectNotNullAccuracy1() throws Exception {
        Helper.assertObjectNotNull(new Date(), "date");
    }

    /**
     * Accuracy test of <code>assertObjectNotNull(Object obj, String name)</code> method.
     * <p>
     * Test this method with an empty string.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertObjectNotNullAccuracy2() throws Exception {
        Helper.assertObjectNotNull("", "");
    }

    /**
     * Failure test of <code>assertObjectNotNull(Object obj, String name)</code> method.
     * <p>
     * Call this method with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertObjectNotNullFailure() throws Exception {
        try {
            Helper.assertObjectNotNull(null, "null");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>assertStringNotNullOrEmpty(String str, String name)</code> method.
     * <p>
     * Call this method with non empty string.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertStringNotNullOrEmptyAccuracy() throws Exception {
        Helper.assertStringNotNullOrEmpty("a", null);
    }

    /**
     * Failure test of <code>assertStringNotNullOrEmpty(String str, String name)</code> method.
     * <p>
     * Call this method with null string.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertStringNotNullOrEmptyFailure1() throws Exception {
        try {
            Helper.assertStringNotNullOrEmpty(null, "null");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>assertStringNotNullOrEmpty(String str, String name)</code> method.
     * <p>
     * Call this method with empty string.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertStringNotNullOrEmptyFailure2() throws Exception {
        try {
            Helper.assertStringNotNullOrEmpty("", "empty");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>assertStringNotNullOrEmpty(String str, String name)</code> method.
     * <p>
     * Call this method with trimmed empty string.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertStringNotNullOrEmptyFailure3() throws Exception {
        try {
            Helper.assertStringNotNullOrEmpty("          ", "trim'd empty");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>assertStringNotNullOrEmpty_ShorterThan256(String str, String name)</code> method.
     * <p>
     * Call this method with a string of 255 length.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertStringNotNullOrEmpty_ShorterThan256Accuracy() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 255; i++) {
            sb.append('a');
        }
        Helper.assertStringNotNullOrEmpty_ShorterThan256(sb.toString(), null);
    }

    /**
     * Failure test of <code>assertStringNotNullOrEmpty_ShorterThan256(String str, String name)</code> method.
     * <p>
     * Call this method with null string.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertStringNotNullOrEmpty_ShorterThan256Failure1() throws Exception {
        try {
            Helper.assertStringNotNullOrEmpty_ShorterThan256(null, "null");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>assertStringNotNullOrEmpty_ShorterThan256(String str, String name)</code> method.
     * <p>
     * Call this method with empty string.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertStringNotNullOrEmpty_ShorterThan256Failure2() throws Exception {
        try {
            Helper.assertStringNotNullOrEmpty_ShorterThan256("", "empty");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>assertStringNotNullOrEmpty_ShorterThan256(String str, String name)</code> method.
     * <p>
     * Call this method with trimmed empty string.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertStringNotNullOrEmpty_ShorterThan256Failure3() throws Exception {
        try {
            Helper.assertStringNotNullOrEmpty_ShorterThan256("          ", "trim'd empty");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>assertStringNotNullOrEmpty_ShorterThan256(String str, String name)</code> method.
     * <p>
     * Call this method with a string longer than 255.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAssertStringNotNullOrEmpty_ShorterThan256Failure4() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 256; i++) {
            sb.append('a');
        }
        try {
            Helper.assertStringNotNullOrEmpty_ShorterThan256(sb.toString(), "too long");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
