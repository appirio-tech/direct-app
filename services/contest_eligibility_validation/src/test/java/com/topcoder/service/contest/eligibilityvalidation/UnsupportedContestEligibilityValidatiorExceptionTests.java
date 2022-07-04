/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for the <code>UnsupportedContestEligibilityValidatiorException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnsupportedContestEligibilityValidatiorExceptionTests extends TestCase {
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
        TestSuite suite = new TestSuite(UnsupportedContestEligibilityValidatiorExceptionTests.class);
        return suite;
    }

    /**
     * <p>
     * Tests accuracy of <code>UnsupportedContestEligibilityValidatiorException()</code> constructor.
     * </p>
     * <p>
     * Verifies the exception is properly created.
     * </p>
     */
    public void testCtor1Accuracy() {
        UnsupportedContestEligibilityValidatiorException exception =
            new UnsupportedContestEligibilityValidatiorException();
        assertNotNull("Unable to instantiate UnsupportedContestEligibilityValidatiorException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>UnsupportedContestEligibilityValidatiorException(String)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        UnsupportedContestEligibilityValidatiorException exception =
            new UnsupportedContestEligibilityValidatiorException(MESSAGE);
        assertNotNull("Unable to instantiate UnsupportedContestEligibilityValidatiorException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception
            .getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>UnsupportedContestEligibilityValidatiorException(Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the cause are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        UnsupportedContestEligibilityValidatiorException exception =
            new UnsupportedContestEligibilityValidatiorException(CAUSE);
        assertNotNull("Unable to instantiate UnsupportedContestEligibilityValidatiorException.", exception);
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>UnsupportedContestEligibilityValidatiorException(String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        UnsupportedContestEligibilityValidatiorException exception =
            new UnsupportedContestEligibilityValidatiorException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate UnsupportedContestEligibilityValidatiorException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception
            .getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>UnsupportedContestEligibilityValidatiorException</code> subclasses
     * <code>ContestEligibilityValidationManagerException</code>.
     * </p>
     */
    public void testInheritance() {
        UnsupportedContestEligibilityValidatiorException instance =
            new UnsupportedContestEligibilityValidatiorException();
        assertTrue("UnsupportedContestEligibilityValidatiorException does not"
            + " subclass ContestEligibilityValidationManagerException.",
            instance instanceof ContestEligibilityValidationManagerException);
    }
}