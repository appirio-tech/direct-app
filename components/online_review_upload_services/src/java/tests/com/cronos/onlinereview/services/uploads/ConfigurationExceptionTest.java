/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of <code>{@link ConfigurationException}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {
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
     * Accuracy test of <code>{@link ConfigurationException#ConfigurationException(String message)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testConfigurationException_accuracy_1() {
        ConfigurationException exception = new ConfigurationException(TestHelper.EXCEPTION_MESSAGE);
        assertEquals("Failed to create ConfigurationException", TestHelper.EXCEPTION_MESSAGE, exception
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link ConfigurationException#ConfigurationException(String message, Throwable cause)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testConfigurationException_accuracy_2() {
        ConfigurationException exception = new ConfigurationException(TestHelper.EXCEPTION_MESSAGE,
                new Exception());
        assertTrue("Failed to create ConfigurationException", exception.getMessage().contains(
                TestHelper.EXCEPTION_MESSAGE));
        assertNotNull("Failed to create ConfigurationException", exception.getCause());
    }
}
