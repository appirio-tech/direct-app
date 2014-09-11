/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.IdAlreadySetException;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test cases for <code>IdAlreadySetException</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class IdAlreadySetExceptionAccuracyTests extends TestCase {
    /**
     * Tests constructor with error message.
     */
    public void testConstructor() {
        String message = "This is an error message";
        IdAlreadySetException exception = new IdAlreadySetException(message);
        assertNotNull("Unable to instantiate IdAlreadySetException", exception);
        assertEquals("Error message not set correctly", message, exception.getMessage());
    }
}
