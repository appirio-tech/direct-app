/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test cases for <code>DeliverableCheckingException</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class DeliverableCheckingExceptionAccuracyTests extends TestCase {
    /**
     * Tests constructor with error message.
     */
    public void testConstructorWithString() {
        String message = "Error!!";
        DeliverableCheckingException exception = new DeliverableCheckingException(message);
        assertNotNull("Unable to instantiate DeliverableCheckingException", exception);
        assertTrue("Error message not set correctly", exception.getMessage().startsWith(message));
    }

    /**
     * Tests constructor with error message and cause.
     */
    public void testConstructorWithStringAndCause() {
        String message = "Error~~";
        Exception cause = new Exception();
        DeliverableCheckingException exception = new DeliverableCheckingException(message, cause);
        assertNotNull("Unable to instantiate DeliverableCheckingException", exception);
        assertTrue("Error message not set correctly", exception.getMessage().startsWith(message));
        assertEquals("Cause not set correctly", cause, exception.getCause());
    }
}
