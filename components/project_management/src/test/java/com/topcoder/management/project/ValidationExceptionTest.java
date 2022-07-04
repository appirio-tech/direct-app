/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ValidationException class.
 *
 * @author iamajia
 * @version 1.0
 */
public class ValidationExceptionTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ValidationExceptionTest.class);
    }

    /**
     * Accuracy test of <code>ValidationException(String message)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidationExceptionAccuracy1() throws Exception {
        ValidationException ve = new ValidationException("test");
        assertEquals("message is incorrect.", "test", ve.getMessage());
    }

    /**
     * Accuracy test of <code>ValidationException(String message, Throwable cause)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidationExceptionAccuracy2() throws Exception {
        Exception e = new Exception("error1");
        ValidationException ve = new ValidationException("error2", e);
        assertEquals("message is incorrect.", "error2", ve.getMessage());
        assertEquals("cause is incorrect.", e, ve.getCause());
    }
}
