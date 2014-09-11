/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import junit.framework.TestCase;

/**
 * <p>
 * Tests functionality of <code>ProjectDoesNotExistException</code>. All the constructors and
 * inherit relationship are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectDoesNotExistExceptionTest extends TestCase {
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
     * Represents the projectId used for testing.
     * </p>
     */
    private static final long PROJECTID = 1;

    /**
     * <p>
     * Tests accuracy of <code>ProjectDoesNotExistException(String)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor1Accuracy() {
        ProjectDoesNotExistException exception = new ProjectDoesNotExistException(MESSAGE, PROJECTID);
        assertNotNull("Unable to instantiate ProjectDoesNotExistException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("The projectId should be got properly.", PROJECTID, exception.getProjectId());
    }

    /**
     * <p>
     * Tests accuracy of <code>ProjectDoesNotExistException(String, Throwable)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ProjectDoesNotExistException exception = new ProjectDoesNotExistException(MESSAGE, CAUSE, PROJECTID);
        assertNotNull("Unable to instantiate ProjectDoesNotExistException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
        assertEquals("The projectId should be got properly.", PROJECTID, exception.getProjectId());
    }

    /**
     * <p>
     * Tests accuracy of <code>getProjectId()</code> method. Verifies the projectId is got
     * properly.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testGetProjectIdAccuracy() {
        ProjectDoesNotExistException exception = new ProjectDoesNotExistException(MESSAGE, CAUSE, PROJECTID);
        assertEquals("The projectId should be got properly.", PROJECTID, exception.getProjectId());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ProjectDoesNotExistException subclasses ProjectServicesException.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("ProjectDoesNotExistException does not subclass ProjectServicesException.",
                new ProjectDoesNotExistException(MESSAGE, PROJECTID) instanceof ProjectServicesException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ProjectDoesNotExistException subclasses ProjectServicesException.
     * </p>
     */
    public void testInheritance2() {
        assertTrue("ProjectDoesNotExistException does not subclass ProjectServicesException.",
                new ProjectDoesNotExistException(MESSAGE, CAUSE, PROJECTID) instanceof ProjectServicesException);
    }
}
