/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import com.topcoder.management.scorecard.validation.ValidationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ValidationException class.
 *
 * @author zhuzeyuan
 * @version 1.0
 */
public class ValidationExceptionTest extends TestCase {
    /**
     * Aggragates all tests in this class.
     *
     * @return Test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ValidationExceptionTest.class);
    }

    /**
     * Accuracy test of <code>ValidationException(String message)</code> constructor.
     *
     * <p>
     * Test a simple exception's throwing.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidationExceptionAccuracy1() throws Exception {
        ValidationException ve = new ValidationException("message");
        assertEquals("message is incorrect.", "message", ve.getMessage());
    }

    /**
     * Accuracy test of <code>ValidationException(String message, Throwable cause)</code> constructor.
     *
     * <p>
     * Test with a simple cause.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidationExceptionAccuracy2() throws Exception {
        Exception e = new Exception("a simple cause");
        ValidationException ce = new ValidationException("message", e);
        assertEquals("message is incorrect.", "message", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
    }
}
