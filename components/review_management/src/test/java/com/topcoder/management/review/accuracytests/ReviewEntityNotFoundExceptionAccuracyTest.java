/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ReviewEntityNotFoundExceptionAccuracyTest.java
 */
package com.topcoder.management.review.accuracytests;

import com.topcoder.management.review.ReviewEntityNotFoundException;
import com.topcoder.management.review.ReviewManagementException;
import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for <code>ReviewEntityNotFoundException</code> class.
 * </p>
 *
 * @author wiedzmin
 * @version 1.0
 */
public class ReviewEntityNotFoundExceptionAccuracyTest extends TestCase {

    /**Message.*/
    private static final String message = "error message";

    /**Id.*/
    private static final long id = 100;

    /**
     * <p>
     * Test constructor and method getId().
     * </p>
     */
    public void testException() {
        ReviewEntityNotFoundException exception = new ReviewEntityNotFoundException(message, id);

        assertTrue("ReviewEntityNotFoundException is not derived from ReviewManagementException.",
                exception instanceof ReviewManagementException);

        //check message existence
        assertEquals("Exception message is not initialized correctly.",
                message, exception.getMessage());

        //check id
        assertEquals("Id is not initialized correctly.", id, exception.getId());
    }
}