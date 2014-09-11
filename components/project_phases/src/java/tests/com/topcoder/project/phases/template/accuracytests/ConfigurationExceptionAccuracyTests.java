/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.PhaseTemplateException;

/**
 * The class <code>ConfigurationExceptionAccuracyTests</code> contains tests for the class
 * {@link <code>ConfigurationException</code>}.
 * @author FireIce
 * @version 1.0
 */
public class ConfigurationExceptionAccuracyTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Exception CAUSE = new Exception();

    /**
     * Tests accuracy of <code>ConfigurationException(String)</code> constructor. The detail error message should be
     * correct.
     */
    public void testConfigurationExceptionStringAccuracy() {
        // Construct ConfigurationException with a detail message
        ConfigurationException exception = new ConfigurationException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests accuracy of <code>ConfigurationException(String, Throwable)</code> constructor. The detail error message
     * and the original cause of error should be correct.
     */
    public void testConfigurationExceptionStringThrowableAccuracy() {
        // Construct ConfigurationException with a detail message and a cause
        ConfigurationException exception = new ConfigurationException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE + ", caused by null",
            exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertTrue("Cause should be identical.", CAUSE == exception.getCause());
    }

    /**
     * <p>
     * Test inheritance.
     * </p>
     * <p>
     * <code>ConfigurationException</code> should be a sub-class of <code>PhaseTemplateException</code>.
     * </p>
     */
    public void testIneritance() {
        assertTrue("inheritance fails", ConfigurationException.class.getSuperclass() == PhaseTemplateException.class);
    }

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ConfigurationExceptionAccuracyTests.class);
    }
}
