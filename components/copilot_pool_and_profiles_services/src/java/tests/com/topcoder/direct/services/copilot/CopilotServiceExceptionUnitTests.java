/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for the CopilotServiceException.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotServiceExceptionUnitTests {
    /**
     * Represents the error message for testing.
     */
    private static final String MSG = "error message";

    /**
     * Represents the Exception instance used for testing.
     */
    private static final Exception CAUSE = new Exception();

    /**
     * Represents the ExceptionData instance used for testing.
     */
    private static final ExceptionData DATA = new ExceptionData();

    /**
     * Tests accuracy of <code>CopilotServiceException(String)</code> constructor. Verifies the error message is
     * properly propagated.
     */
    @Test
    public void testCtor1Accuracy() {
        CopilotServiceException exception = new CopilotServiceException(MSG);
        assertNotNull("Unable to instantiate CopilotServiceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MSG, exception.getMessage());
    }

    /**
     * Tests accuracy of <code>CopilotServiceException(String, Throwable)</code> constructor. Verifies the error
     * message and the cause are properly propagated.
     */
    @Test
    public void testCtor2Accuracy() {
        CopilotServiceException exception = new CopilotServiceException(MSG, CAUSE);
        assertNotNull("Unable to instantiate CopilotServiceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MSG, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * Tests accuracy of <code>CopilotServiceException(String, ExceptionData)</code> constructor. Verifies the error
     * message and the exceptionData are properly propagated.
     */
    @Test
    public void testCtor3Accuracy() {
        CopilotServiceException exception = new CopilotServiceException(MSG, DATA);
        assertNotNull("Unable to instantiate CopilotServiceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MSG, exception.getMessage());
        assertEquals("ExceptionData is not properly propagated to super class.", DATA.getErrorCode(), exception
            .getErrorCode());
    }

    /**
     * Tests accuracy of CopilotServiceException(String, Throwable, ExceptionData) constructor. Verifies the error
     * message, the cause, and the exceptionData are properly propagated.
     */
    @Test
    public void testCtor4Accuracy() {
        CopilotServiceException exception = new CopilotServiceException(MSG, CAUSE, DATA);
        assertNotNull("Unable to instantiate CopilotServiceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MSG, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
        assertEquals("ExceptionData is not properly propagated to super class.", DATA.getErrorCode(), exception
            .getErrorCode());
    }

    /**
     * Inheritance test, verifies CopilotServiceException subclasses BaseCriticalException.
     */
    @Test
    public void testInheritance() {
        assertTrue("CopilotServiceException does not subclass BaseCriticalException.", new CopilotServiceException(
            MSG) instanceof BaseCriticalException);
    }
}