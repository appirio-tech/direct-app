/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.project.phases.template.PhaseTemplateException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * The class <code>PhaseTemplateExceptionAccuracyTests</code> contains tests for the class
 * {@link <code>PhaseTemplateException</code>}.
 * @author FireIce
 * @version 1.0
 */
public class PhaseTemplateExceptionAccuracyTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Exception CAUSE = new Exception();

    /**
     * Tests accuracy of <code>PhaseTemplateException(String)</code> constructor. The detail error message should be
     * correct.
     */
    public void testPhaseTemplateExceptionStringAccuracy() {
        // Construct PhaseTemplateException with a detail message
        PhaseTemplateException exception = new PhaseTemplateException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests accuracy of <code>PhaseTemplateException(String, Throwable)</code> constructor. The detail error message
     * and the original cause of error should be correct.
     */
    public void testPhaseTemplateExceptionStringThrowableAccuracy() {
        // Construct PhaseTemplateException with a detail message and a cause
        PhaseTemplateException exception = new PhaseTemplateException(DETAIL_MESSAGE, CAUSE);

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
     * <code>PhaseTemplateException</code> should be a sub-class of <code>BaseException</code>.
     * </p>
     */
    public void testIneritance() {
        assertTrue("inheritance fails", PhaseTemplateException.class.getSuperclass() == BaseException.class);
    }

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PhaseTemplateExceptionAccuracyTests.class);
    }
}
