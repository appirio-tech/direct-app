/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ConfigurationException class.
 *
 * @author iamajia
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ConfigurationExceptionTest.class);
    }

    /**
     * Accuracy test of <code>ConfigurationException(String message)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testConfigurationExceptionAccuracy1() throws Exception {
        ConfigurationException ce = new ConfigurationException("test");
        assertEquals("message is incorrect.", "test", ce.getMessage());
    }

    /**
     * Accuracy test of <code>ConfigurationException(String message, Throwable cause)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testConfigurationExceptionAccuracy2() throws Exception {
        Exception e = new Exception("error1");
        ConfigurationException ce = new ConfigurationException("error2", e);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
    }
}
