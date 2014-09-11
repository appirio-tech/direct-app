/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import com.cronos.onlinereview.services.uploads.InvalidSubmissionException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link InvalidSubmissionException} class.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class InvalidSubmissionExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InvalidSubmissionExceptionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidSubmissionException#InvalidSubmissionException(String, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidSubmissionException() throws Exception {
        InvalidSubmissionException invalidProjectException = new InvalidSubmissionException("test", 1);
        //check for null
        assertNotNull("InvalidSubmissionException creation failed", invalidProjectException);
        assertEquals("getSubmissionId failed", invalidProjectException.getSubmissionId(), 1);
        assertEquals("Error message is not properly propagated to super class.", "test", invalidProjectException.getMessage());
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidSubmissionException#InvalidSubmissionException(String, Throwable, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidSubmissionException1() throws Exception {
        //check for null
        InvalidSubmissionException invalidProjectException = new InvalidSubmissionException("test", new Throwable(), 2);
        assertNotNull("InvalidSubmissionException creation failed", invalidProjectException);
        assertEquals("getSubmissionId failed", invalidProjectException.getSubmissionId(), 2);
    }
}
