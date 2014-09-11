/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.management.project.PersistenceException;

import junit.framework.TestCase;


/**
 * Accuracy test cases for <code>PersistenceException</code>.
 *
 * @author skatou
 * @version 1.0
 */
public class PersistenceExceptionAccuracyTests extends TestCase {
    /**
     * Tests constructor with message.
     */
    public void testExceptionMessage() {
        String message = "Error Message";
        PersistenceException exception = new PersistenceException(message);
        assertNotNull(exception);
        assertTrue(exception.getMessage().indexOf(message) >= 0);
        assertEquals(null, exception.getCause());
    }

    /**
     * Tests constructor with message and cause.
     */
    public void testExceptionMessageCause() {
        String message = "Error Message";
        Exception cause = new Exception();
        PersistenceException exception = new PersistenceException(message, cause);
        assertNotNull(exception);
        assertTrue(exception.getMessage().indexOf(message) >= 0);
        assertEquals(cause, exception.getCause());
    }
}
