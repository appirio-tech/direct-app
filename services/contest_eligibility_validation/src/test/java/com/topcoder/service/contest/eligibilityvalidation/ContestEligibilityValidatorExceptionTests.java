/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for the <code>ContestEligibilityValidatorException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityValidatorExceptionTests extends TestCase {
    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Represents the <code>Exception</code> instance used for testing.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Creates a test suite for the tests.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ContestEligibilityValidatorExceptionTests.class);
        return suite;
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityValidatorException()</code> constructor.
     * </p>
     * <p>
     * Verifies the exception is properly created.
     * </p>
     */
    public void testCtor1Accuracy() {
        ContestEligibilityValidatorException exception = new ContestEligibilityValidatorException();
        assertNotNull("Unable to instantiate ContestEligibilityValidatorException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityValidatorException(String)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ContestEligibilityValidatorException exception = new ContestEligibilityValidatorException(MESSAGE);
        assertNotNull("Unable to instantiate ContestEligibilityValidatorException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception
            .getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityValidatorException(Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the cause are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        ContestEligibilityValidatorException exception = new ContestEligibilityValidatorException(CAUSE);
        assertNotNull("Unable to instantiate ContestEligibilityValidatorException.", exception);
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityValidatorException(String, Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        ContestEligibilityValidatorException exception =
            new ContestEligibilityValidatorException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ContestEligibilityValidatorException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception
            .getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ContestEligibilityValidatorException</code> subclasses
     * <code>Exception</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ContestEligibilityValidatorException does not subclass Exception.",
            new ContestEligibilityValidatorException() instanceof Exception);
    }
}