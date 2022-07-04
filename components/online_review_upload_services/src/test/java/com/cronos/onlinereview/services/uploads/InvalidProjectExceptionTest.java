/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of <code>{@link InvalidProjectException}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class InvalidProjectExceptionTest extends TestCase {
    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>InvalidProjectExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(InvalidProjectExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidProjectException#InvalidProjectException(String message, long projectId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     */
    public void testInvalidProjectException_accuracy_1() {
        InvalidProjectException exception = new InvalidProjectException(TestHelper.EXCEPTION_MESSAGE,
                TestHelper.PROJECT_ID);
        assertEquals("Failed to create InvalidProjectException", TestHelper.EXCEPTION_MESSAGE, exception
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidProjectException#InvalidProjectException(String message, Throwable cause,
     * long projectId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     */
    public void testInvalidProjectException_accuracy_2() {
        InvalidProjectException exception = new InvalidProjectException(TestHelper.EXCEPTION_MESSAGE,
                new Exception(), TestHelper.PROJECT_ID);
        assertTrue("Failed to create InvalidProjectException", exception.getMessage().contains(
                TestHelper.EXCEPTION_MESSAGE));
        assertNotNull("Failed to create InvalidProjectException", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link InvalidProjectException#getProjectId()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is set.
     * </p>
     *
     */
    public void testGetProjectId_accuracy() {
        InvalidProjectException exception = new InvalidProjectException(TestHelper.EXCEPTION_MESSAGE,
                TestHelper.PROJECT_ID);
        assertEquals("Failed to get project id", TestHelper.PROJECT_ID, exception.getProjectId());
    }
}
