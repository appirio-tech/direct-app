/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for InvalidTeamException class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InvalidTeamExceptionUnitTest extends TestCase {
    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InvalidTeamExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>InvalidTeamException(String msg)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testInvalidTeamExceptionAccuracy1() throws Exception {
        InvalidTeamException e = new InvalidTeamException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * <p>
     * Accuracy test of <code>InvalidTeamException(String msg, Throwable cause)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testInvalidTeamExceptionAccuracy2() throws Exception {
        Exception cause = new Exception("cause");
        InvalidTeamException e = new InvalidTeamException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
