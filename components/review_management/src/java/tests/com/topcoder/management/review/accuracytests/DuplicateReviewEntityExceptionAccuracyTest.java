/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DuplicateReviewEntityExceptionAccuracyTest.java
 */
package com.topcoder.management.review.accuracytests;

import junit.framework.TestCase;
import com.topcoder.management.review.DuplicateReviewEntityException;
import com.topcoder.management.review.ReviewManagementException;

/**
 * <p>
 * Accuracy tests for <code>DuplicateReviewEntityException</code> class.
 * </p>
 *
 * @author wiedzmin
 * @version 1.0
 */
public class DuplicateReviewEntityExceptionAccuracyTest extends TestCase {

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
        DuplicateReviewEntityException exception = new DuplicateReviewEntityException(message, id);

        assertTrue("DuplicateReviewEntityException is not derived from ReviewManagementException.",
                exception instanceof ReviewManagementException);

        //check message existence
        assertEquals("Exception message is not initialized correctly.",
                message, exception.getMessage());

        //check id
        assertEquals("Id is not initialized correctly.", id, exception.getId());
    }
}