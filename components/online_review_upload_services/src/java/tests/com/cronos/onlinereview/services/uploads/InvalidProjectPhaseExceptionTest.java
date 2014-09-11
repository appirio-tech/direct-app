/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of <code>{@link InvalidProjectPhaseException}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class InvalidProjectPhaseExceptionTest extends TestCase {
    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>InvalidProjectPhaseExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(InvalidProjectPhaseExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidProjectPhaseException#InvalidProjectPhaseException(String message,
     * long projectPhaseId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testInvalidProjectPhaseException_accuracy_1() {
        InvalidProjectPhaseException exception = new InvalidProjectPhaseException(
                TestHelper.EXCEPTION_MESSAGE, TestHelper.PROJECT_PHASE_ID);
        assertEquals("Failed to create InvalidProjectPhaseException", TestHelper.EXCEPTION_MESSAGE, exception
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidProjectPhaseException#InvalidProjectPhaseException(String message,
     * Throwable cause, long projectPhaseId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     */
    public void testInvalidProjectPhaseException_accuracy_2() {
        InvalidProjectPhaseException exception = new InvalidProjectPhaseException(
                TestHelper.EXCEPTION_MESSAGE, new Exception(), TestHelper.PROJECT_PHASE_ID);
        assertTrue("Failed to create InvalidProjectPhaseException", exception.getMessage().contains(
                TestHelper.EXCEPTION_MESSAGE));
        assertNotNull("Failed to create InvalidProjectPhaseException", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link InvalidProjectPhaseException#getProjectPhaseId()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is set.
     * </p>
     *
     */
    public void testGetProjectPhaseId_accuracy() {
        InvalidProjectPhaseException exception = new InvalidProjectPhaseException(
                TestHelper.EXCEPTION_MESSAGE, TestHelper.PROJECT_PHASE_ID);
        assertEquals("Failed to get project phase id", TestHelper.PROJECT_PHASE_ID, exception
                .getProjectPhaseId());
    }
}
