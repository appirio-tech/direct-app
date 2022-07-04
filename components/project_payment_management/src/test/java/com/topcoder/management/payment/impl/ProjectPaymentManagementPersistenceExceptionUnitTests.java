/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.management.payment.ProjectPaymentManagementException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for {@link ProjectPaymentManagementPersistenceException} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ProjectPaymentManagementPersistenceExceptionUnitTests {
    /**
     * <p>
     * Represents a detail message.
     * </p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Represents an error cause.
     * </p>
     */
    private static final Throwable CAUSE = new Exception("UnitTests");

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

    /**
     * <p>
     * Initializes the exception data.
     * </p>
     */
    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectPaymentManagementPersistenceExceptionUnitTests.class);
    }

    /**
     * <p>
     * <code>ProjectPaymentManagementPersistenceException</code> should be subclass of
     * <code>ProjectPaymentManagementException</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
            "ProjectPaymentManagementPersistenceException should be subclass of ProjectPaymentManagementException.",
            ProjectPaymentManagementPersistenceException.class.getSuperclass()
                == ProjectPaymentManagementException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>ProjectPaymentManagementPersistenceException(String)</code> constructor.<br>
     * The detail error message should be properly set.
     * </p>
     */
    @Test
    public void testCtor1() {
        ProjectPaymentManagementPersistenceException exception =
            new ProjectPaymentManagementPersistenceException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ProjectPaymentManagementPersistenceException(String, Throwable)</code>
     * constructor.<br>
     * The detail error message and the original cause of error should be properly set.
     * </p>
     */
    @Test
    public void testCtor2() {
        ProjectPaymentManagementPersistenceException exception =
            new ProjectPaymentManagementPersistenceException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ProjectPaymentManagementPersistenceException(String, ExceptionData)</code>
     * constructor.<br>
     * The detail error message and the exception data should be properly set.
     * </p>
     */
    @Test
    public void testCtor3() {
        ProjectPaymentManagementPersistenceException exception =
            new ProjectPaymentManagementPersistenceException(DETAIL_MESSAGE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("Application code should not null.", exception.getApplicationCode());
        assertEquals("Exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
    }

    /**
     * <p>
     * Tests accuracy of <code>ProjectPaymentManagementPersistenceException(String, Throwable,
     * ExceptionData)</code>
     * constructor.<br>
     * The detail error message, the cause exception and the exception data should be properly set.
     * </p>
     */
    @Test
    public void testCtor4() {
        ProjectPaymentManagementPersistenceException exception =
            new ProjectPaymentManagementPersistenceException(DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("Application code should not null.", exception.getApplicationCode());
        assertEquals("Exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }
}
