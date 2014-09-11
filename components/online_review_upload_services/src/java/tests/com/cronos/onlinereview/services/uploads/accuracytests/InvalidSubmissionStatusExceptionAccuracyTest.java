/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import com.cronos.onlinereview.services.uploads.InvalidSubmissionStatusException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link InvalidSubmissionStatusException} class.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class InvalidSubmissionStatusExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InvalidSubmissionStatusExceptionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidSubmissionStatusException#InvalidSubmissionStatusException(String, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidSubmissionStatusException() throws Exception {
        InvalidSubmissionStatusException invalidProjectException = new InvalidSubmissionStatusException("test", 1);
        //check for null
        assertNotNull("InvalidSubmissionStatusException creation failed", invalidProjectException);
        assertEquals("getSubmissionStatusId failed", invalidProjectException.getSubmissionStatusId(), 1);
        assertEquals("Error message is not properly propagated to super class.", "test", invalidProjectException.getMessage());
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidSubmissionStatusException#InvalidSubmissionStatusException(String, Throwable, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidSubmissionStatusException1() throws Exception {
        //check for null
        InvalidSubmissionStatusException invalidProjectException = new InvalidSubmissionStatusException("test", new Throwable(), 2);
        assertNotNull("InvalidSubmissionStatusException creation failed", invalidProjectException);
        assertEquals("getSubmissionStatusId failed", invalidProjectException.getSubmissionStatusId(), 2);
    }
}
