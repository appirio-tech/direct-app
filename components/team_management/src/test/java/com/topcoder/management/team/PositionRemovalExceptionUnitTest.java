/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for PositionRemovalException class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PositionRemovalExceptionUnitTest extends TestCase {
    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PositionRemovalExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>PositionRemovalException(String msg)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testPositionRemovalExceptionAccuracy1() throws Exception {
        PositionRemovalException e = new PositionRemovalException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * <p>
     * Accuracy test of <code>PositionRemovalException(String msg, Throwable cause)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testPositionRemovalExceptionAccuracy2() throws Exception {
        Exception cause = new Exception("cause");
        PositionRemovalException e = new PositionRemovalException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
