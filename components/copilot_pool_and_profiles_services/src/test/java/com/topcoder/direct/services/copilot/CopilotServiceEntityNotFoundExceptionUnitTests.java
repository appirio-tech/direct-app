/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for the CopilotServiceEntityNotFoundException.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotServiceEntityNotFoundExceptionUnitTests {
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
     * Tests accuracy of <code>CopilotServiceEntityNotFoundException(String)</code> constructor. Verifies the error
     * message is properly propagated.
     */
    @Test
    public void testCtor1Accuracy() {
        CopilotServiceEntityNotFoundException exception =
            new CopilotServiceEntityNotFoundException(MSG, "entityTypeName", 1);
        assertNotNull("Unable to instantiate CopilotServiceEntityNotFoundException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MSG, exception.getMessage());
        assertEquals("entityTypeName should be 'entityTypeName'.", "entityTypeName", exception.getEntityTypeName());
        assertTrue("entity id should be 1.", exception.getEntityId() == 1);
    }

    /**
     * Tests accuracy of <code>CopilotServiceEntityNotFoundException(String, Throwable)</code> constructor. Verifies
     * the error message and the cause are properly propagated.
     */
    @Test
    public void testCtor2Accuracy() {
        CopilotServiceEntityNotFoundException exception =
            new CopilotServiceEntityNotFoundException(MSG, CAUSE, "entityTypeName", 1);
        assertNotNull("Unable to instantiate CopilotServiceEntityNotFoundException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MSG, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
        assertEquals("entityTypeName should be 'entityTypeName'.", "entityTypeName", exception.getEntityTypeName());
        assertTrue("entity id should be 1.", exception.getEntityId() == 1);
    }

    /**
     * Tests accuracy of <code>CopilotServiceEntityNotFoundException(String, ExceptionData)</code> constructor.
     * Verifies the error message and the exceptionData are properly propagated.
     */
    @Test
    public void testCtor3Accuracy() {
        CopilotServiceEntityNotFoundException exception =
            new CopilotServiceEntityNotFoundException(MSG, DATA, "entityTypeName", 1);
        assertNotNull("Unable to instantiate CopilotServiceEntityNotFoundException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MSG, exception.getMessage());
        assertEquals("ExceptionData is not properly propagated to super class.", DATA.getErrorCode(), exception
            .getErrorCode());
        assertEquals("entityTypeName should be 'entityTypeName'.", "entityTypeName", exception.getEntityTypeName());
        assertTrue("entity id should be 1.", exception.getEntityId() == 1);
    }

    /**
     * Tests accuracy of <code>CopilotServiceEntityNotFoundException(String, Throwable, ExceptionData)</code>
     * constructor. Verifies the error message, the cause, and the exceptionData are properly propagated.
     */
    @Test
    public void testCtor4Accuracy() {
        CopilotServiceEntityNotFoundException exception =
            new CopilotServiceEntityNotFoundException(MSG, CAUSE, DATA, "entityTypeName", 1);
        assertNotNull("Unable to instantiate CopilotServiceEntityNotFoundException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MSG, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
        assertEquals("ExceptionData is not properly propagated to super class.", DATA.getErrorCode(), exception
            .getErrorCode());
        assertEquals("entityTypeName should be 'entityTypeName'.", "entityTypeName", exception.getEntityTypeName());
        assertTrue("entity id should be 1.", exception.getEntityId() == 1);
    }

    /**
     * Inheritance test, verifies <code>CopilotServiceEntityNotFoundException</code> subclasses
     * <code>CopilotServiceException</code>.
     */
    @Test
    public void testInheritance() {
        assertTrue("CopilotServiceEntityNotFoundException does not subclass CopilotServiceException.",
            new CopilotServiceEntityNotFoundException(MSG, "entityTypeName", 1) instanceof CopilotServiceException);
    }
}