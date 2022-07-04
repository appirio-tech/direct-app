/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import com.topcoder.util.errorhandling.BaseRuntimeException;

import junit.framework.TestCase;


/**
 * <p>Unit tests for <code>{@link SpecificationReviewServiceConfigurationException}</code>
 * class.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class SpecificationReviewServiceConfigurationExceptionTests extends TestCase {
    /**
     * <p>Represents a detail message.</p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>Represents an error cause.</p>
     */
    private static final Throwable CAUSE = new Exception("UnitTests");

    /**
     * <p><code>SpecificationReviewServiceConfigurationException</code> should be subclass
     * of <code>BaseRuntimeException</code>.</p>
     */
    public void testInheritance() {
        assertTrue("SpecificationReviewServiceConfigurationException should be subclass of BaseRuntimeException.",
            SpecificationReviewServiceConfigurationException.class.getSuperclass() == BaseRuntimeException.class);
    }

    /**
     * <p>Tests accuracy of <code>SpecificationReviewServiceConfigurationException()</code> constructor.<br>
     * Instance should be correctly created.</p>
     */
    public void testCtor1() {
        SpecificationReviewServiceConfigurationException exception
            = new SpecificationReviewServiceConfigurationException();

        // Verify the error detail message
        assertNull("Detailed error message should be null.", exception.getMessage());
        // Verify the error cause
        assertNull("Error cause should be null.", exception.getCause());
    }

    /**
     * <p>Tests accuracy of
     * <code>SpecificationReviewServiceConfigurationException(String)</code> constructor.<br>
     * Instance should be correctly created.</p>
     */
    public void testCtor2() {
        SpecificationReviewServiceConfigurationException exception
            = new SpecificationReviewServiceConfigurationException(DETAIL_MESSAGE);

        // Verify the error detail message
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>Tests accuracy of
     * <code>SpecificationReviewServiceConfigurationException(Throwable)</code> constructor.<br>
     * Instance should be correctly created.</p>
     */
    public void testCtor3() {
        SpecificationReviewServiceConfigurationException exception
            = new SpecificationReviewServiceConfigurationException(CAUSE);

        // Verify the error cause
        assertSame("Error cause should be correct.", CAUSE, exception.getCause());
    }

    /**
     * <p>Tests accuracy of <code>SpecificationReviewServiceConfigurationException(String,
     * Throwable)</code> constructor.<br>
     * Instance should be correctly created.</p>
     */
    public void testCtor4() {
        SpecificationReviewServiceConfigurationException exception
            = new SpecificationReviewServiceConfigurationException(DETAIL_MESSAGE, CAUSE);

        // Verify the detail error message
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE,
            exception.getMessage());
        // Verify the error cause
        assertSame("Error cause should be correct.", CAUSE, exception.getCause());
    }
}
