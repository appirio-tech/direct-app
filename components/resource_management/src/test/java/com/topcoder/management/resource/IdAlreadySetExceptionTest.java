/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import junit.framework.TestCase;

/**
 * Unit tests for the class: IdAlreadySetException.
 *
 * @author kinfkong
 * @version 1.0
 */
public class IdAlreadySetExceptionTest extends TestCase {

    /**
     * Tests the definition of the IDAlreadySetException.
     *
     * It must extend the class: IllegalStateException.
     */
    public void testIdAlreadySetException_Definition() {
        Exception exception = new IdAlreadySetException("Exception for test");
        assertTrue("It must extend IllegalStateException.", exception instanceof IllegalStateException);
    }

    /**
     * Tests constructor IdAlreadySetException(String).
     */
    public void testIdAlreadySetExceptionString() {
        // instance is created
        Exception exception = new IdAlreadySetException("Exception for test");
        assertNotNull("Instance cannot be created.", exception);
        // message should be set properly.
        assertEquals("Messages does not set properly.", "Exception for test", exception.getMessage());
    }
}
