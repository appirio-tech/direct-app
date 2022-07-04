/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.UploadServicesException;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link UploadServicesException} class.
 * </p>
 * 
 * @author kshatriyan
 * @version 1.0
 */
public class UploadServicesExceptionAccuracyTest extends TestCase {
    /** The error message used for testing. */
    private static final String ERROR_MESSAGE = "test error message";

    /** An exception instance used to create the UploadServicesException. */
    private final Exception cause = new NullPointerException();

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * 
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UploadServicesExceptionAccuracyTest.class);
    }

    /**
     * Test UploadServicesException constructor with correct message, the message can be retrieved correctly later.
     */
    public void testCtor1() {
        UploadServicesException cde = new UploadServicesException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate UploadServicesException.", cde);
        assertTrue("UploadServicesException should inherit the Exception", cde instanceof Exception);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, cde.getMessage());
    }

    /**
     * Test UploadServicesException constructor with correct error message, cause, the message and cause can be
     * retrieved correctly later.
     */
    public void testCtor2() {
        UploadServicesException ce = new UploadServicesException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate UploadServicesException.", ce);
        assertTrue("UploadServicesException should inherit the Exception", ce instanceof Exception);
        assertTrue("Error message is not properly propagated to super class.", ce.getMessage().indexOf(
                ERROR_MESSAGE) >= 0);
        assertEquals("The inner exception should match", ce.getCause(), cause);
    }
}
