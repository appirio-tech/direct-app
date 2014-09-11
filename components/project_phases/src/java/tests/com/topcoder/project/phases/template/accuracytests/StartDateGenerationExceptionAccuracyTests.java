/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.project.phases.template.PhaseTemplateException;
import com.topcoder.project.phases.template.StartDateGenerationException;

/**
 * The class <code>StartDateGenerationExceptionAccuracyTests</code> contains tests for the class
 * {@link <code>StartDateGenerationException</code>}.
 * @author FireIce
 * @version 1.0
 */
public class StartDateGenerationExceptionAccuracyTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Exception CAUSE = new Exception();

    /**
     * Tests accuracy of <code>StartDateGenerationException(String)</code> constructor. The detail error message
     * should be correct.
     */
    public void testStartDateGenerationExceptionStringAccuracy() {
        // Construct StartDateGenerationException with a detail message
        StartDateGenerationException exception = new StartDateGenerationException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests accuracy of <code>StartDateGenerationException(String, Throwable)</code> constructor. The detail error
     * message and the original cause of error should be correct.
     */
    public void testStartDateGenerationExceptionStringThrowableAccuracy() {
        // Construct StartDateGenerationException with a detail message and a cause
        StartDateGenerationException exception = new StartDateGenerationException(DETAIL_MESSAGE, CAUSE);

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
     * <code>StartDateGenerationException</code> should be a sub-class of <code>PhaseTemplateException
     * </code>.
     * </p>
     */
    public void testIneritance() {
        assertTrue("inheritance fails",
            StartDateGenerationException.class.getSuperclass() == PhaseTemplateException.class);
    }

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(StartDateGenerationExceptionAccuracyTests.class);
    }
}
