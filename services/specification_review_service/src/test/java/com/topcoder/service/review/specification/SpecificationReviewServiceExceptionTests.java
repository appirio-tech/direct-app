/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseCriticalException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link SpecificationReviewServiceException}</code> class.
 * </p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class SpecificationReviewServiceExceptionTests extends TestCase {
    /**
     * <p>
     * Represents a detail message.
     * </p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Represents an error cause.
     * </p>
     */
    private static final Throwable CAUSE = new Exception("UnitTests");

    /**
     * <p>
     * <code>SpecificationReviewServiceException</code> should be subclass of
     * <code>BaseCriticalException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("SpecificationReviewServiceException should be subclass of BaseCriticalException.",
            SpecificationReviewServiceException.class.getSuperclass() == BaseCriticalException.class);
    }

    /**
     * <p>
     * <code>SpecificationReviewServiceException</code> should have annotation
     * &quot;@ApplicationException(rollback = true)&quot;.
     * </p>
     */
    public void testAnnotation() {
        ApplicationException annotation = SpecificationReviewServiceException.class
            .getAnnotation(ApplicationException.class);
        assertTrue(
            "SpecificationReviewServiceException should have annotation '@ApplicationException(rollback = true)'.",
            annotation.rollback());
    }

    /**
     * <p>
     * Tests accuracy of <code>SpecificationReviewServiceException()</code>
     * constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor1() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException();

        // Verify the error detail message
        assertNull("Detailed error message should be null.", exception.getMessage());
        // Verify the error cause
        assertNull("Error cause should be null.", exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>SpecificationReviewServiceException(String)</code>
     * constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor2() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException(
            DETAIL_MESSAGE);

        // Verify the error detail message
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>SpecificationReviewServiceException(Throwable)</code>
     * constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor3() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException(
            CAUSE);

        // Verify the error cause
        assertSame("Error cause should be correct.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>SpecificationReviewServiceException(String, Throwable)</code>
     * constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor4() {
        SpecificationReviewServiceException exception = new SpecificationReviewServiceException(
            DETAIL_MESSAGE, CAUSE);

        // Verify the detail error message
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());
        // Verify the error cause
        assertSame("Error cause should be correct.", CAUSE, exception.getCause());
    }
}
