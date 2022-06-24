/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.InvalidProjectException;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link InvalidProjectException} class.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class InvalidProjectExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InvalidProjectExceptionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidProjectException#InvalidProjectException(String, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidProjectException() throws Exception {
        InvalidProjectException invalidProjectException = new InvalidProjectException("test", 1);
        //check for null
        assertNotNull("InvalidProjectException creation failed", invalidProjectException);
        assertEquals("getProjectId failed", invalidProjectException.getProjectId(), 1);
        assertEquals("Error message is not properly propagated to super class.", "test", invalidProjectException.getMessage());
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidProjectException#InvalidProjectException(String, Throwable, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidProjectException1() throws Exception {
        //check for null
        InvalidProjectException invalidProjectException = new InvalidProjectException("test", new Throwable(), 2);
        assertNotNull("InvalidProjectException creation failed", invalidProjectException);
        assertEquals("getProjectId failed", invalidProjectException.getProjectId(), 2);
    }
}
