/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for TeamPersistenceException class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamPersistenceExceptionUnitTest extends TestCase {
    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamPersistenceExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>TeamPersistenceException(String msg)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPersistenceExceptionAccuracy1() throws Exception {
        TeamPersistenceException e = new TeamPersistenceException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * <p>
     * Accuracy test of <code>TeamPersistenceException(String msg, Throwable cause)</code> constructor.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPersistenceExceptionAccuracy2() throws Exception {
        Exception cause = new Exception("cause");
        TeamPersistenceException e = new TeamPersistenceException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
