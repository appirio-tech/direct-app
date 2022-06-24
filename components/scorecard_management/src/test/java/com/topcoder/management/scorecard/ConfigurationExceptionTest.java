/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ConfigurationException class.
 *
 * @author zhuzeyuan
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {
    /**
     * Aggragates all tests in this class.
     *
     * @return Test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ConfigurationExceptionTest.class);
    }

    /**
     * Accuracy test of <code>ConfigurationException(String message)</code> constructor.
     *
     * <p>
     * Test a simple exception's throwing.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testConfigurationExceptionAccuracy1() throws Exception {
        ConfigurationException ce = new ConfigurationException("message");
        assertEquals("message is incorrect.", "message", ce.getMessage());
    }

    /**
     * Accuracy test of <code>ConfigurationException(String message, Throwable cause)</code> constructor.
     *
     * <p>
     * Test with a simple cause.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testConfigurationExceptionAccuracy2() throws Exception {
        Exception e = new Exception("a simple cause");
        ConfigurationException ce = new ConfigurationException("message", e);
        assertEquals("message is incorrect.", "message", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
    }
}
