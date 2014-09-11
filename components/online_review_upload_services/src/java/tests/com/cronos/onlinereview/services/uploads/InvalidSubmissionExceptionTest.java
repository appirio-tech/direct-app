/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of <code>{@link InvalidSubmissionException}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class InvalidSubmissionExceptionTest extends TestCase {
    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>InvalidSubmissionExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(InvalidSubmissionExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidSubmissionException#InvalidSubmissionException(String message, long submissionId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testInvalidSubmissionException_accuracy_1() {
        InvalidSubmissionException exception = new InvalidSubmissionException(TestHelper.EXCEPTION_MESSAGE,
                TestHelper.SUBMISSION_ID);
        assertEquals("Failed to create InvalidSubmissionException", TestHelper.EXCEPTION_MESSAGE, exception
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidSubmissionException#InvalidSubmissionException(String message,
     * Throwable cause, long submissionId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     */
    public void testInvalidSubmissionException_accuracy_2() {
        InvalidSubmissionException exception = new InvalidSubmissionException(TestHelper.EXCEPTION_MESSAGE,
                new Exception(), TestHelper.SUBMISSION_ID);
        assertTrue("Failed to create InvalidSubmissionException", exception.getMessage().contains(
                TestHelper.EXCEPTION_MESSAGE));
        assertNotNull("Failed to create InvalidSubmissionException", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link InvalidSubmissionException#getSubmissionId()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is set.
     * </p>
     *
     */
    public void testGetSubmissionId_accuracy() {
        InvalidSubmissionException exception = new InvalidSubmissionException(TestHelper.EXCEPTION_MESSAGE,
                TestHelper.SUBMISSION_ID);
        assertEquals("Failed to get submission id", TestHelper.SUBMISSION_ID, exception.getSubmissionId());
    }
}
