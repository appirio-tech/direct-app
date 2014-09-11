/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test cases for class DAOException. All the method are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DAOExceptionTest extends TestCase {

    /** The error message used for testing. */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * An exception instance used to create the DAOException.
     */
    private final Exception cause = new Exception();

    /** An exception data used to create the DAOException. */
    private ExceptionData exceptionData;

    /**
     * <p>
     * Aggregates all tests.
     * </p>
     *
     * @return A test suite will be returned
     */
    public static Test suite() {
        return new TestSuite(DAOExceptionTest.class);
    }

    /**
     * Sets up testing environment.
     *
     * @throws Exception
     *                 when error occurs
     */
    @Override
    public void setUp() throws Exception {
        exceptionData = new ExceptionData();
        exceptionData.setApplicationCode("Application Code");
        exceptionData.setErrorCode("Error Code");
    }

    /**
     * Clears the testing environment.
     *
     * @throws Exception
     *                 when error occurs
     */
    @Override
    public void tearDown() throws Exception {
        exceptionData = null;
    }

    /**
     * Test method for DAOException(String message).<br>
     * Target: tests the creation.<br>
     */
    public void testCtor1_Accuracy() {
        DAOException impl = new DAOException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate DAOException.", impl);
        assertTrue("DAOException should subclass BaseCriticalException",
                impl instanceof BaseCriticalException);
        assertEquals(
                "Error message is not properly propagated to super class.",
                ERROR_MESSAGE, impl.getMessage());
    }

    /**
     * Test method for DAOException(String message, Throwable cause).<br>
     * Target: tests the creation.<br>
     */
    public void testCtor2_Accuracy() {
        DAOException impl = new DAOException(ERROR_MESSAGE, cause);
        assertNotNull("Unable to instantiate DAOException.", impl);
        assertTrue("DAOException should subclass BaseCriticalException",
                impl instanceof BaseCriticalException);
        assertEquals(
                "Error message is not properly propagated to super class.",
                ERROR_MESSAGE, impl.getMessage());
        assertEquals("The inner exception should match.", cause, impl
                .getCause());
    }

    /**
     * Test method for DAOException(String message, ExceptionData data).<br>
     * Target: tests the creation.<br>
     */
    public void testCtor3_Accuracy() {
        DAOException impl = new DAOException(ERROR_MESSAGE, exceptionData);
        assertNotNull("Unable to instantiate DAOException.", impl);
        assertTrue("DAOException should subclass BaseCriticalException",
                impl instanceof BaseCriticalException);
        assertEquals(
                "Error message is not properly propagated to super class.",
                ERROR_MESSAGE, impl.getMessage());
        assertEquals("The Application Code should match.", "Application Code",
                impl.getApplicationCode());
        assertEquals("The Error Code should match.", "Error Code", impl
                .getErrorCode());
    }

    /**
     * Test method for DAOException(String message, Throwable cause,
     * ExceptionData data).<br>
     * Target: tests the creation.<br>
     */
    public void testCtor4_Accuracy() {
        DAOException impl = new DAOException(ERROR_MESSAGE, cause,
                exceptionData);
        assertNotNull("Unable to instantiate DAOException.", impl);
        assertTrue("DAOException should subclass BaseCriticalException",
                impl instanceof BaseCriticalException);
        assertEquals(
                "Error message is not properly propagated to super class.",
                ERROR_MESSAGE, impl.getMessage());
        assertEquals("The inner exception should match.", cause, impl
                .getCause());
        assertEquals("The Application Code should match.", "Application Code",
                impl.getApplicationCode());
        assertEquals("The Error Code should match.", "Error Code", impl
                .getErrorCode());
    }

}
