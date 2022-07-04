/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import com.topcoder.service.review.specification.SpecificationReviewServiceConfigurationException;
import com.topcoder.util.errorhandling.BaseRuntimeException;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for <code>SpecificationReviewServiceConfigurationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SpecificationReviewServiceConfigurationExceptionAccuracyTest extends TestCase {

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
     * Accuracy test for constructor
     * <code>SpecificationReviewServiceConfigurationException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr1() {
        SpecificationReviewServiceConfigurationException exception = new SpecificationReviewServiceConfigurationException(
            MESSAGE);
        assertNotNull("Unable to create SpecificationReviewServiceConfigurationException instance.",
            exception);
        assertTrue(
            "The SpecificationReviewServiceConfigurationException should be instance of Exception.",
            exception instanceof BaseRuntimeException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>SpecificationReviewServiceConfigurationException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr2() {
        SpecificationReviewServiceConfigurationException exception = new SpecificationReviewServiceConfigurationException(
            MESSAGE);
        assertNotNull("Unable to create SpecificationReviewServiceConfigurationException instance.",
            exception);
        assertTrue(
            "The SpecificationReviewServiceConfigurationException should be instance of Exception.",
            exception instanceof BaseRuntimeException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>SpecificationReviewServiceConfigurationException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        SpecificationReviewServiceConfigurationException exception = new SpecificationReviewServiceConfigurationException(
            (String) null);
        assertNotNull("Unable to create SpecificationReviewServiceConfigurationException instance.",
            exception);
        assertTrue(
            "The SpecificationReviewServiceConfigurationException should be instance of Exception.",
            exception instanceof BaseRuntimeException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>SpecificationReviewServiceConfigurationException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        SpecificationReviewServiceConfigurationException exception = new SpecificationReviewServiceConfigurationException(
            MESSAGE, CAUSE);
        assertNotNull("Unable to create SpecificationReviewServiceConfigurationException instance.",
            exception);
        assertTrue(
            "The SpecificationReviewServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>SpecificationReviewServiceConfigurationException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowableNotNull() {
        SpecificationReviewServiceConfigurationException exception = new SpecificationReviewServiceConfigurationException(
            MESSAGE, CAUSE);
        assertNotNull("Unable to create SpecificationReviewServiceConfigurationException instance.",
            exception);
        assertTrue(
            "The SpecificationReviewServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>SpecificationReviewServiceConfigurationException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        SpecificationReviewServiceConfigurationException exception = new SpecificationReviewServiceConfigurationException(
            null, (Throwable) null);
        assertNotNull("Unable to create SpecificationReviewServiceConfigurationException instance.",
            exception);
        assertTrue(
            "The SpecificationReviewServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
