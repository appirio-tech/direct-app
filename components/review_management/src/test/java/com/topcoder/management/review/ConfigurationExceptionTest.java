/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This class tests the <code>ConfigurationException</code> class. It tests the
 * ConfigurationException(String), ConfigurationException(String, Throwable) constructors.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {
    /**
     * <p>
     * The string of exception message for unit test.
     * </p>
     */
    private static final String TEST_MESSAGE = "Configuration Exception.";

    /**
     * <p>
     * The instance of <code>ConfigurationException</code> for unit test.
     * </p>
     */
    private ConfigurationException test = null;

    /**
     * <p>
     * Returns the test suite of <code>ConfigurationExceptionTest</code>.
     * </p>
     *
     * @return the test suite of <code>ConfigurationExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ConfigurationExceptionTest.class);
    }

    /**
     * <p>
     * Basic test of <code>ConfigurationException(String)</code> constructor.
     * </p>
     */
    public void testConfigurationExceptionCtor1_Basic() {
        // check null here.
        assertNotNull("Create ConfigurationException failed.",
            new ConfigurationException(TEST_MESSAGE));
    }

    /**
     * <p>
     * Detail Test of <code>ConfigurationException(String)</code> constructor. Creates a instance
     * and get it's attributes for test.
     * </p>
     */
    public void testConfigurationExceptionCtor1_Detail() {
        // create a exception instance for test.
        test = new ConfigurationException(TEST_MESSAGE);

        // check null here.
        assertNotNull("Create ConfigurationException failed.", test);

        // check the type here.
        assertTrue("The ConfigurationException should extend from ReviewManagementException.",
            test instanceof ReviewManagementException);

        // check error message here.
        assertEquals("Equal error message expected.", TEST_MESSAGE, test.getMessage());
    }

    /**
     * <p>
     * Test of <code>ConfigurationException(String, Throwable)</code> constructor with error.
     * </p>
     */
    public void testConfigurationExceptionCtor2_Error() {
        Throwable cause = new Error();

        // create a exception instance for test.
        test = new ConfigurationException(TEST_MESSAGE, cause);

        // check null here.
        assertNotNull("Create ConfigurationException failed.", test);

        // check the type here.
        assertTrue("The ConfigurationException should extend from ReviewManagementException.",
            test instanceof ReviewManagementException);

        // check error message and cause here.
        assertNotNull("Error message expected.", test.getMessage());
        assertEquals("Equal inner cause expected.", cause, test.getCause());
    }

    /**
     * <p>
     * Test of <code>ConfigurationException(String, Throwable)</code> constructor with exception.
     * </p>
     */
    public void testConfigurationExceptionCtor2_Exception() {
        Throwable cause = new Exception();

        // create a exception instance for test.
        test = new ConfigurationException(TEST_MESSAGE, cause);

        // check null here.
        assertNotNull("Create ConfigurationException failed.", test);

        // check the type here.
        assertTrue("The ConfigurationException should extend from ReviewManagementException.",
            test instanceof ReviewManagementException);

        // check error message and cause here.
        assertNotNull("Error message expected.", test.getMessage());
        assertEquals("Equal inner cause expected.", cause, test.getCause());
    }

    /**
     * <p>
     * Test if <code>ConfigurationException</code> works properly. With the constructor of
     * <code>ConfigurationException(String)</code>.
     * </p>
     */
    public void testConfigurationExceptionUsage_Ctor1() {
        try {
            throw new ConfigurationException(TEST_MESSAGE);
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Test if <code>ConfigurationException</code> works properly. With the constructor of
     * <code>ConfigurationException(String, Throwable)</code>.
     * </p>
     */
    public void testConfigurationExceptionUsage_Ctor2() {
        try {
            throw new ConfigurationException(TEST_MESSAGE, new Exception());
        } catch (ConfigurationException e) {
            // success
        }
    }
}
