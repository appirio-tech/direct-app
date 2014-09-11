/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.dao;

import javax.ejb.ApplicationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for the <code>ContestEligibilityPersistenceException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityPersistenceExceptionTests extends TestCase {

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
        TestSuite suite = new TestSuite(ContestEligibilityPersistenceExceptionTests.class);
        return suite;
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityPersistenceException()</code> constructor.
     * </p>
     * <p>
     * Verifies the exception is properly created.
     * </p>
     */
    public void testCtor1Accuracy() {
        ContestEligibilityPersistenceException exception = new ContestEligibilityPersistenceException();
        assertNotNull("Unable to instantiate ContestEligibilityPersistenceException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityPersistenceException(String)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ContestEligibilityPersistenceException exception =
            new ContestEligibilityPersistenceException(MESSAGE);
        assertNotNull("Unable to instantiate ContestEligibilityPersistenceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception
            .getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityPersistenceException(Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the cause are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        ContestEligibilityPersistenceException exception = new ContestEligibilityPersistenceException(CAUSE);
        assertNotNull("Unable to instantiate ContestEligibilityPersistenceException.", exception);
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContestEligibilityPersistenceException(String, Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        ContestEligibilityPersistenceException exception =
            new ContestEligibilityPersistenceException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ContestEligibilityPersistenceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception
            .getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ContestEligibilityPersistenceException</code> subclasses
     * <code>Exception</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ContestEligibilityPersistenceException does not subclass Exception.",
            new ContestEligibilityPersistenceException() instanceof Exception);
    }

    /**
     * <p>
     * ApplicationException annotation test, verifies <code>ContestEligibilityPersistenceException</code>
     * contains the <code>ApplicationException</code> annotation.
     * </p>
     */
    public void testApplicationExceptionAnnotation() {
        assertTrue("ContestEligibilityPersistenceException does not annotate ApplicationException.",
            ContestEligibilityPersistenceException.class.isAnnotationPresent(ApplicationException.class));
    }
}