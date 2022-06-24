/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test cases for <code>DeliverablePersistenceException</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class DeliverablePersistenceExceptionAccuracyTests extends TestCase {
    /**
     * Tests constructor with error message.
     */
    public void testConstructorWithString() {
        String message = "Error!!";
        DeliverablePersistenceException exception = new DeliverablePersistenceException(message);
        assertNotNull("Unable to instantiate DeliverablePersistenceException", exception);
        assertTrue("Error message not set correctly", exception.getMessage().startsWith(message));
    }

    /**
     * Tests constructor with error message and cause.
     */
    public void testConstructorWithStringAndCause() {
        String message = "Error~~";
        Exception cause = new Exception();
        DeliverablePersistenceException exception = new DeliverablePersistenceException(message, cause);
        assertNotNull("Unable to instantiate DeliverablePersistenceException", exception);
        assertTrue("Error message not set correctly", exception.getMessage().startsWith(message));
        assertEquals("Cause not set correctly", cause, exception.getCause());
    }
}
