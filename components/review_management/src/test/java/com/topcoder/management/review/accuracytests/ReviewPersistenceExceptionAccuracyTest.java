/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ReviewPersistenceExceptionAccuracyTest.java
 */
package com.topcoder.management.review.accuracytests;

import com.topcoder.management.review.ReviewPersistenceException;
import com.topcoder.management.review.ReviewManagementException;
import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for <code>ReviewPersistenceException</code> class.
 * </p>
 *
 * @author wiedzmin
 * @version 1.0
 */
public class ReviewPersistenceExceptionAccuracyTest extends TestCase {

    /**Message.*/
    private static final String message = "error message";

    /**Cause.*/
    private static final IllegalArgumentException cause = new IllegalArgumentException();

    /**
     * <p>
     * Test constructor that accepts String.
     * </p>
     */
    public void testCtor1() {
        ReviewPersistenceException exception = new ReviewPersistenceException(message);

        assertTrue("ReviewPersistenceException is not derived from ReviewManagementException.",
                exception instanceof ReviewManagementException);

        //check message existence
        assertEquals("Exception message is not initialized correctly.",
                message, exception.getMessage());
    }

    /**
     * <p>
     * Test constructor that accepts String and Throwable.
     * </p>
     */
    public void testCtor2() {
        ReviewPersistenceException exception = new ReviewPersistenceException(message, cause);

        //check cause existence
        assertEquals("Exception cause is not initialized correctly.",
                cause, exception.getCause());

        //check message existence
        assertTrue("Exception message is not initialized correctly.",
                exception.getMessage().indexOf(message) != -1);
    }
}