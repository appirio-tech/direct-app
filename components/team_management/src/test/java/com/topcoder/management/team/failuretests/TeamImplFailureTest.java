/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.failuretests;

import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.impl.TeamImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for TeamImpl class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class TeamImplFailureTest extends TestCase {
    /**
     * Represents a TeamImpl instance that is used in the test.
     */
    private TeamImpl team = new TeamImpl();

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamImplFailureTest.class);
    }

    /**
     * Failure test of
     * <code>TeamImpl(TeamHeader teamHeader, TeamPosition[] positions)</code>
     * constructor.
     * <p>
     * teamHeader is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamImpl_failure_null_teamHeader() throws Exception {
        try {
            new TeamImpl(null, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TeamImpl(TeamHeader teamHeader, TeamPosition[] positions)</code>
     * constructor.
     * <p>
     * positions contains null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamImpl_failure_positions_contains_null() throws Exception {
        try {
            new TeamImpl(new TeamHeader(), new TeamPosition[]{null });
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setTeamHeader(TeamHeader teamHeader)</code>
     * method.
     * <p>
     * teamHeader is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetTeamHeader_failure_null_teamHeader() throws Exception {
        try {
            team.setTeamHeader(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setPositions(TeamPosition[] positions)</code>
     * method.
     * <p>
     * positions contains null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetPositions_failure_positions_contains_null() throws Exception {
        try {
            team.setPositions(new TeamPosition[]{null });
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
