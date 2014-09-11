/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for TeamManagerException class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamManagerExceptionUnitTest extends TestCase {
    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamManagerExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>TeamManagerException(String msg)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerExceptionAccuracy1() throws Exception {
        TeamManagerException e = new TeamManagerException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * <p>
     * Accuracy test of <code>TeamManagerException(String msg, Throwable cause)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerExceptionAccuracy2() throws Exception {
        Exception cause = new Exception("cause");
        TeamManagerException e = new TeamManagerException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
