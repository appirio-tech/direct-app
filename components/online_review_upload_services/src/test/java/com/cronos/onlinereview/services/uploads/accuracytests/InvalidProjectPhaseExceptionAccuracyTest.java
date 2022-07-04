/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.InvalidProjectPhaseException;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link InvalidProjectPhaseException} class.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class InvalidProjectPhaseExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InvalidProjectPhaseExceptionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidProjectPhaseException#InvalidProjectPhaseException(String, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidProjectPhaseException() throws Exception {
        InvalidProjectPhaseException invalidProjectException = new InvalidProjectPhaseException("test", 1);
        //check for null
        assertNotNull("InvalidProjectPhaseException creation failed", invalidProjectException);
        assertEquals("getProjectPhaseId failed", invalidProjectException.getProjectPhaseId(), 1);
        assertEquals("Error message is not properly propagated to super class.", "test", invalidProjectException.getMessage());
    }

    /**
     * <p>
     * Accuracy test for{@link InvalidProjectPhaseException#InvalidProjectPhaseException(String, Throwable, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_InvalidProjectPhaseException1() throws Exception {
        //check for null
        InvalidProjectPhaseException invalidProjectException = new InvalidProjectPhaseException("test", new Throwable(), 2);
        assertNotNull("InvalidProjectPhaseException creation failed", invalidProjectException);
        assertEquals("getProjectPhaseId failed", invalidProjectException.getProjectPhaseId(), 2);
    }
}
