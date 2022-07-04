/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.ConfigurationException;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link ConfigurationException} class.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class ConfigurationExceptionAccuracyTest extends TestCase {
    /** The error message used for testing. */
    private static final String ERROR_MESSAGE = "test error message";

    /** An exception instance used to create the ConfigurationException. */
    private final Exception cause = new NullPointerException();

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ConfigurationExceptionAccuracyTest.class);
    }

    /**
     * Test ConfigurationException constructor with correct message, the message can be retrieved correctly later.
     */
    public void testCtor1() {
        ConfigurationException cde = new ConfigurationException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate ConfigurationException.", cde);
        assertTrue("ConfigurationException should inherit the Exception", cde instanceof Exception);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, cde.getMessage());
    }

    /**
     * Test ConfigurationException constructor with correct error message, cause, the message and cause can be
     * retrieved correctly later.
     */
    public void testCtor2() {
        ConfigurationException ce = new ConfigurationException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate ConfigurationException.", ce);
        assertTrue("ConfigurationException should inherit the Exception", ce instanceof Exception);
        assertTrue("Error message is not properly propagated to super class.", ce.getMessage().indexOf(
            ERROR_MESSAGE) >= 0);
        assertEquals("The inner exception should match", ce.getCause(), cause);
    }}
