/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import junit.framework.TestCase;

/**
 * <p>Unit test case for {@link EntityNotFoundException}. </p>
 * <p>It tests all constructors of the class to ensure that
 * all parameters are handled correctly.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class EntityNotFoundExceptionTest extends TestCase {
    /**
     * <p> Tests {@link EntityNotFoundException#EntityNotFoundException(String)} constructor.</p>
     */
    public void testConstructorWithMessage() {
        final String message = "test";

        final EntityNotFoundException exception =
            new EntityNotFoundException(message);
        assertEquals("Message is not forwarded to super constructor", message, exception.getMessage());
    }

    /**
     * <p> Tests {@link EntityNotFoundException#EntityNotFoundException(String,Throwable)} constructor.</p>
     */
    public void testConstructorWithMessageCause() {
        final String message = "test";
        final Throwable cause = new Throwable();

        final EntityNotFoundException exception =
            new EntityNotFoundException(message, cause);
        assertEquals("Message is not forwarded to super constructor", message, exception.getMessage());
        assertEquals("Cause is not forwarded to super constructor", cause, exception.getCause());
    }
}
