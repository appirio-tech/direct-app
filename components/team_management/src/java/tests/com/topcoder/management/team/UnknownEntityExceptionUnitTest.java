/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for UnknownEntityException class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnknownEntityExceptionUnitTest extends TestCase {
    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UnknownEntityExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>UnknownEntityException(String msg)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUnknownEntityExceptionAccuracy1() throws Exception {
        UnknownEntityException e = new UnknownEntityException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * <p>
     * Accuracy test of <code>UnknownEntityException(String msg, Throwable cause)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUnknownEntityExceptionAccuracy2() throws Exception {
        Exception cause = new Exception("cause");
        UnknownEntityException e = new UnknownEntityException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
