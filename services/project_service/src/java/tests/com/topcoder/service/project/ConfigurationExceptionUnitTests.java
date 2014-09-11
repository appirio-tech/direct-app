/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test for <code>{@link ConfigurationException}</code> class.
 * </p>
 *
 * <p>
 * Version 1.1 adds a test case to ensure the inheritance.
 * </p>
 *
 * @author FireIce
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class ConfigurationExceptionUnitTests extends TestCase {

    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Throwable CAUSE = new Exception("UnitTest");

    /**
     * <p>
     * Represents the exception data.
     * </p>
     */
    private static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p>
     * Represents the application code set in exception data.
     * </p>
     */
    private static final String APPLICATION_CODE = "Accuracy";

    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ConfigurationExceptionUnitTests.class);
    }

    /**
     * <p>
     * <code>{@link ConfigurationException}</code> should be subclass of <code>BaseRuntimeException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ConfigurationException should be subclass of BaseRuntimeException",
            ConfigurationException.class.getSuperclass() == BaseRuntimeException.class);
    }

    /**
     * Tests accuracy of <code>ConfigurationException()</code> constructor.
     */
    public void testConfigurationExceptionAccuracy() {
        // Construct ConfigurationException with no-arg constructor
        ConfigurationException exception = new ConfigurationException();

        // Verify that there is a detail message
        assertNull("the message should be null.", exception.getMessage());
    }

    /**
     * Tests accuracy of <code>ConfigurationException(String)</code> constructor. The detail error message should be
     * correct.
     */
    public void testConfigurationExceptionStringAccuracy() {
        // Construct ConfigurationException with a detail message
        ConfigurationException exception = new ConfigurationException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests accuracy of <code>ConfigurationException(String, Throwable)</code> constructor. The detail error message
     * and the original cause of error should be correct.
     */
    public void testConfigurationExceptionStringThrowableAccuracy() {
        // Construct ConfigurationException with a detail message and a cause
        ConfigurationException exception = new ConfigurationException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }

    /**
     * Tests accuracy of <code>ConfigurationException(String, Throwable, ExceptionData)</code> constructor. The detail
     * error message, the cause exception and the exception data should be correct.
     */
    public void testConfigurationExceptionStringThrowableExceptionDataAccuracy() {
        // Construct ConfigurationException with a detail message and a cause
        ConfigurationException exception = new ConfigurationException(DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }
}
