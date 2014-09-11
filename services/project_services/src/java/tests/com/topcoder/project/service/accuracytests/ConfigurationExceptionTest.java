/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.project.service.ConfigurationException;
import com.topcoder.project.service.ProjectServicesException;

/**
 * <p>
 * Tests the functionality of <code>ConfigurationExceptionTest</code> class for accuracy.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {
    /**
     * <p>
     * Instance of <code>ConfigurationException</code> for test.
     * </p>
     */
    private ConfigurationException configurationException;

    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>ConfigurationExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ConfigurationExceptionTest.class);
    }

    /**
     * <p>
     * Sets up the environment before each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        configurationException = new ConfigurationException("message");
    }

    /**
     * <p>
     * Tears down the environment after execution of each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        configurationException = null;
    }

    /**
     * <p>
     * Accuracy test of <code>ConfigurationException(String msg)</code> constructor. Creates an instance and get its
     * attributes for test.
     * </p>
     *
     * <p>
     * Tests for instantiation and inheritance and proper exception message propagation.
     * </p>
     *
     */
    public void testConfigurationException_accuracy1() {
        assertNotNull("Unable to create the instance properly", configurationException);
        assertTrue("ConfigurationException should extend ProjectServicesException",
                configurationException instanceof ProjectServicesException);
        assertEquals("Unable to get the message properly", "message", configurationException.getMessage());
    }

    /**
     * <p>
     * Accuracy test of <code>ConfigurationException(String msg, Throwable cause)</code> constructor. Creates an
     * instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Tests for instantiation and inheritance and proper exception message and cause propagation.
     * </p>
     *
     */
    public void testConfigurationException_accuracy2() {
        Exception cause = new Exception();
        configurationException = new ConfigurationException("message", cause);
        assertNotNull("Unable to create the instance properly", configurationException);
        assertTrue("ConfigurationException should extend ProjectServicesException",
                configurationException instanceof ProjectServicesException);
        assertTrue("Unable to get the message properly", configurationException.getMessage().startsWith("message"));
        assertEquals("Unable to get the cause properly", cause, configurationException.getCause());
    }
}
