/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import junit.framework.TestCase;

import com.topcoder.service.review.specification.SpecificationReviewServiceException;
import com.topcoder.util.errorhandling.BaseCriticalException;

/**
 * <p>
 * Accuracy tests for <code>SpecificationReviewServiceException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SpecificationReviewServiceExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "the error message";

    /**
     * <p>
     * The inner exception for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>
     * Accuracy test for constructor <code>SpecificationReviewServiceException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException(MESSAGE);
        assertNotNull("Unable to create SpecificationReviewServiceException instance.", exception);
        assertTrue("The SpecificationReviewServiceException should be instance of Exception.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SpecificationReviewServiceException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException(
            (String) null);
        assertNotNull("Unable to create SpecificationReviewServiceException instance.", exception);
        assertTrue("The SpecificationReviewServiceException should be instance of Exception.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SpecificationReviewServiceException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_NotNull() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException(
            (String) null);
        assertNotNull("Unable to create SpecificationReviewServiceException instance.", exception);
        assertTrue("The SpecificationReviewServiceException should be instance of Exception.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>SpecificationReviewServiceException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException(MESSAGE,
            CAUSE);
        assertNotNull("Unable to create SpecificationReviewServiceException instance.", exception);
        assertTrue(
            "The SpecificationReviewServiceException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>SpecificationReviewServiceException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable_NotNull() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException(MESSAGE,
            CAUSE);
        assertNotNull("Unable to create SpecificationReviewServiceException instance.", exception);
        assertTrue(
            "The SpecificationReviewServiceException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>SpecificationReviewServiceException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException(null,
            (Throwable) null);
        assertNotNull("Unable to create SpecificationReviewServiceException instance.", exception);
        assertTrue(
            "The SpecificationReviewServiceException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
