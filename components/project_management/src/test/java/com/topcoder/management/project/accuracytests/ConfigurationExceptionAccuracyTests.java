/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.management.project.ConfigurationException;

import junit.framework.TestCase;


/**
 * Accuracy test cases for <code>ConfigurationException</code>.
 *
 * @author skatou
 * @version 1.0
 */
public class ConfigurationExceptionAccuracyTests extends TestCase {
    /**
     * Tests constructor with message.
     */
    public void testExceptionMessage() {
        String message = "Error Message";
        ConfigurationException exception = new ConfigurationException(message);
        assertNotNull(exception);
        assertTrue(exception.getMessage().indexOf(message) >= 0);
        assertEquals(null, exception.getCause());
    }

    /**
     * Tests constructor with message and cause.
     */
    public void testExceptionMessageCause() {
        String message = "Error Message";
        Exception cause = new Exception();
        ConfigurationException exception = new ConfigurationException(message, cause);
        assertNotNull(exception);
        assertTrue(exception.getMessage().indexOf(message) >= 0);
        assertEquals(cause, exception.getCause());
    }
}
