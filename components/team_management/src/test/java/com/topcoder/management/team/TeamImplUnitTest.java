/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.util.Arrays;

import com.topcoder.management.team.impl.TeamImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for TeamImpl class. Pay attention getters will be tested throughout the setters and constructors, so there
 * will be no special test cases for them.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamImplUnitTest extends TestCase {
    /** Prepare a simple team header for testing. */
    private TeamHeader header = new TeamHeader();

    /** Prepare several simple team positions for testing. */
    private TeamPosition position1 = new TeamPosition("position1", false, 0, 0, "name1", 0, false);

    /** Prepare several simple team positions for testing. */
    private TeamPosition position2 = new TeamPosition("position2", false, 0, 0, "name2", 0, false);

    /** Prepare a TeamImpl for testing. */
    private TeamImpl team = new TeamImpl();

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamImplUnitTest.class);
    }

    /**
     * Accuracy test of <code>TeamImpl()</code> constructor.
     * <p>
     * DOC ME PLEASE!!!!!!!!!!!
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamImpl1_Accuracy() throws Exception {
        team = new TeamImpl();
        assertNull("header mismatch", team.getTeamHeader());
        assertEquals("position mismatch", 0, team.getPositions().length);
    }

    /**
     * Accuracy test of <code>TeamImpl(TeamHeader teamHeader, TeamPosition[] positions)</code> constructor.
     * <p>
     * Call this constructor with specific header and positions, then get them to verify.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamImpl2_Accuracy1() throws Exception {
        team = new TeamImpl(header, new TeamPosition[] {position1, position2});
        assertEquals("header mismatch", header, team.getTeamHeader());
        assertTrue("position mismatch", Arrays.equals(new TeamPosition[] {position1, position2}, team.getPositions()));
    }

    /**
     * Accuracy test of <code>TeamImpl(TeamHeader teamHeader, TeamPosition[] positions)</code> constructor.
     * <p>
     * Call this constructor with empty positions.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamImpl2_Accuracy2() throws Exception {
        team = new TeamImpl(header, new TeamPosition[0]);
        assertEquals("header mismatch", header, team.getTeamHeader());
        assertEquals("position mismatch", 0, team.getPositions().length);
    }

    /**
     * Failure test of <code>TeamImpl(TeamHeader teamHeader, TeamPosition[] positions)</code> constructor.
     * <p>
     * Call this constructor with null team header.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamImpl2_Failure1() throws Exception {
        try {
            new TeamImpl(null, new TeamPosition[] {position1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamImpl(TeamHeader teamHeader, TeamPosition[] positions)</code> constructor.
     * <p>
     * Call this constructor with positions containing null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamImpl2_Failure2() throws Exception {
        try {
            new TeamImpl(header, new TeamPosition[] {position1, null});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setTeamHeader(TeamHeader teamHeader)</code> method.
     * <p>
     * Simply set a header to verify.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetTeamHeaderAccuracy() throws Exception {
        team.setTeamHeader(header);
        assertEquals("header mismatch", header, team.getTeamHeader());
    }

    /**
     * Failure test of <code>setTeamHeader(TeamHeader teamHeader)</code> method.
     * <p>
     * Call this method with null header.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetTeamHeaderFailure() throws Exception {
        try {
            team.setTeamHeader(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setPositions(TeamPosition[] positions)</code> method.
     * <p>
     * Call this method with specified positions, and get them to verify.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPositionsAccuracy1() throws Exception {
        team.setPositions(new TeamPosition[] {position1, position2});
        assertTrue("position mismatch", Arrays.equals(new TeamPosition[] {position1, position2}, team.getPositions()));
    }

    /**
     * Accuracy test of <code>setPositions(TeamPosition[] positions)</code> method.
     * <p>
     * Call this method with a single position, and get it to verify.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPositionsAccuracy2() throws Exception {
        team.setPositions(new TeamPosition[] {position2});
        assertTrue("position mismatch", Arrays.equals(new TeamPosition[] {position2}, team.getPositions()));
    }

    /**
     * Accuracy test of <code>setPositions(TeamPosition[] positions)</code> method.
     * <p>
     * Call this method with empty positions.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPositionsAccuracy3() throws Exception {
        team.setPositions(new TeamPosition[0]);
        assertEquals("position mismatch", 0, team.getPositions().length);
    }

    /**
     * Accuracy test of <code>setPositions(TeamPosition[] positions)</code> method.
     * <p>
     * Call this method with null positions.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPositionsAccuracy4() throws Exception {
        team.setPositions(null);
        assertEquals("position mismatch", 0, team.getPositions().length);
    }

    /**
     * Failure test of <code>setPositions(TeamPosition[] positions)</code> method.
     * <p>
     * Call this method when positions contains null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPositionsFailure() throws Exception {
        try {
            team.setPositions(new TeamPosition[] {position2, null});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
