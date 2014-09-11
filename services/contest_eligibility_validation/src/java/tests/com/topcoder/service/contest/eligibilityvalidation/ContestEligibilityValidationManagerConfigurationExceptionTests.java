/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for the <code>ContestEligibilityValidationManagerConfigurationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityValidationManagerConfigurationExceptionTests extends TestCase {

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
        TestSuite suite = new TestSuite(ContestEligibilityValidationManagerConfigurationExceptionTests.class);
        return suite;
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityValidationManagerConfigurationException()</code> constructor.
     * </p>
     * <p>
     * Verifies the exception is properly created.
     * </p>
     */
    public void testCtor1Accuracy() {
        ContestEligibilityValidationManagerConfigurationException exception =
            new ContestEligibilityValidationManagerConfigurationException();
        assertNotNull("Unable to instantiate ContestEligibilityValidationManagerConfigurationException.",
            exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityValidationManagerConfigurationException(String)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ContestEligibilityValidationManagerConfigurationException exception =
            new ContestEligibilityValidationManagerConfigurationException(MESSAGE);
        assertNotNull("Unable to instantiate ContestEligibilityValidationManagerConfigurationException.",
            exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception
            .getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityValidationManagerConfigurationException(Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the cause are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        ContestEligibilityValidationManagerConfigurationException exception =
            new ContestEligibilityValidationManagerConfigurationException(CAUSE);
        assertNotNull("Unable to instantiate ContestEligibilityValidationManagerConfigurationException.",
            exception);
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>ContestEligibilityValidationManagerConfigurationException(String, Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        ContestEligibilityValidationManagerConfigurationException exception =
            new ContestEligibilityValidationManagerConfigurationException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ContestEligibilityValidationManagerConfigurationException.",
            exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception
            .getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ContestEligibilityValidationManagerConfigurationException</code>
     * subclasses <code>RuntimeException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
            "ContestEligibilityValidationManagerConfigurationException does not subclass RuntimeException.",
            new ContestEligibilityValidationManagerConfigurationException() instanceof RuntimeException);
    }
}