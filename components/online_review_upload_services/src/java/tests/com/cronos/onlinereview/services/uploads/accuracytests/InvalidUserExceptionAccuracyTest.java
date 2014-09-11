/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import com.cronos.onlinereview.services.uploads.InvalidUserException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link InvalidUserException} class.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class InvalidUserExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InvalidUserExceptionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidUserException#InvalidUserException(String, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidUserException() throws Exception {
        InvalidUserException invalidProjectException = new InvalidUserException("test", 1);
        //check for null
        assertNotNull("InvalidUserException creation failed", invalidProjectException);
        assertEquals("getUserId failed", invalidProjectException.getUserId(), 1);
        assertEquals("Error message is not properly propagated to super class.", "test", invalidProjectException.getMessage());
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidUserException#InvalidUserException(String, Throwable, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidUserException1() throws Exception {
        //check for null
        InvalidUserException invalidProjectException = new InvalidUserException("test", new Throwable(), 2);
        assertNotNull("InvalidUserException creation failed", invalidProjectException);
        assertEquals("getUserId failed", invalidProjectException.getUserId(), 2);
    }
}
