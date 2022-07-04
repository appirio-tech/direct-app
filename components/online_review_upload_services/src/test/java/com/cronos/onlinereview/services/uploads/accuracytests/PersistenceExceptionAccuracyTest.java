/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.PersistenceException;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link PersistenceException} class.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class PersistenceExceptionAccuracyTest extends TestCase {

    /** The error message used for testing. */
    private static final String ERROR_MESSAGE = "test error message";

    /** An exception instance used to create the PersistenceException. */
    private final Exception cause = new NullPointerException();

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PersistenceExceptionAccuracyTest.class);
    }

    /**
     * Test PersistenceException constructor with correct message, the message can be retrieved correctly later.
     */
    public void testCtor1() {
        PersistenceException cde = new PersistenceException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate PersistenceException.", cde);
        assertTrue("PersistenceException should inherit the Exception", cde instanceof Exception);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, cde.getMessage());
    }

    /**
     * Test PersistenceException constructor with correct error message, cause, the message and cause can be
     * retrieved correctly later.
     */
    public void testCtor2() {
        PersistenceException ce = new PersistenceException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate PersistenceException.", ce);
        assertTrue("PersistenceException should inherit the Exception", ce instanceof Exception);
        assertTrue("Error message is not properly propagated to super class.", ce.getMessage().indexOf(
            ERROR_MESSAGE) >= 0);
        assertEquals("The inner exception should match", ce.getCause(), cause);
    }
}
