/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of <code>{@link InvalidSubmissionStatusException}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class InvalidSubmissionStatusExceptionTest extends TestCase {
    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>InvalidSubmissionStatusExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(InvalidSubmissionStatusExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidSubmissionStatusException#InvalidSubmissionStatusException(String message,
     * long submissionStatusId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     */
    public void testInvalidSubmissionStatusException_accuracy_1() {
        InvalidSubmissionStatusException exception = new InvalidSubmissionStatusException(
                TestHelper.EXCEPTION_MESSAGE, TestHelper.SUBMISSION_STATUS_ID);
        assertEquals("Failed to create InvalidSubmissionStatusException", TestHelper.EXCEPTION_MESSAGE,
                exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidSubmissionStatusException#InvalidSubmissionStatusException(String message,
     * Throwable cause, long submissionStatusId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     */
    public void testInvalidSubmissionStatusException_accuracy_2() {
        InvalidSubmissionStatusException exception = new InvalidSubmissionStatusException(
                TestHelper.EXCEPTION_MESSAGE, new Exception(), TestHelper.SUBMISSION_STATUS_ID);
        assertTrue("Failed to create InvalidSubmissionStatusException", exception.getMessage().contains(
                TestHelper.EXCEPTION_MESSAGE));
        assertNotNull("Failed to create InvalidSubmissionStatusException", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link InvalidSubmissionStatusException#getSubmissionStatusId()}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects the same which is set.
     * </p>
     *
     */
    public void testGetSubmissionStatusId_accuracy() {
        InvalidSubmissionStatusException exception = new InvalidSubmissionStatusException(
                TestHelper.EXCEPTION_MESSAGE, TestHelper.SUBMISSION_STATUS_ID);
        assertEquals("Failed to get submission status id", TestHelper.SUBMISSION_STATUS_ID, exception
                .getSubmissionStatusId());
    }
}
