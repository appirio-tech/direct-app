/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import com.topcoder.management.team.impl.TeamImpl;
import com.topcoder.management.team.impl.TeamManagerImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for TeamManagerImpl class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamManagerImplUnitTest2Teams extends TestCase {
    /** The ByteArrayOutputStream for testing of logs. */
    private ByteArrayOutputStream out;

    /** Provide a mock TeamPersistence for testing. */
    private MockTeamPersistence persistence;

    /** Provide a TeamManagerImpl for testing. */
    private TeamManagerImpl manager;

    /** Provide a TeamHeader for testing. */
    private TeamHeader header = new TeamHeader("header_name", false, 1, 1, 1, 30, "header_des");

    /** Provide positions for testing. */
    private TeamPosition position1 = new TeamPosition("position1", false, 2, 20, "position1_name", 11, false);

    /** Provide positions for testing. */
    private TeamPosition position2 = new TeamPosition("position2", false, 3, 30, "position2_name", 12, false);

    /** Provide a team for testing. */
    private Team team = new TeamImpl(header, new TeamPosition[] {position1, position2});

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamManagerImplUnitTest2Teams.class);
    }

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.addConfig();

        // Set up an ByteArrayOutputStream for logs to write
        out = new ByteArrayOutputStream();
        LogManager.setLogFactory(new BasicLogFactory(new PrintStream(out)));

        // Prepare the manager with logger
        persistence = new MockTeamPersistence();
        manager = new TeamManagerImpl(persistence, IDGeneratorFactory.getIDGenerator(TestHelper.IDGENERATOR_NAME),
            LogManager.getLog());
    }

    /**
     * <p>
     * Tears down the environment for the TestCase.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Accuracy test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method with a simple team header, and check if the parameters are passed to the persistence
     * correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamAccuracy1() throws Exception {
        // Simply create a team
        header.setTeamId(0);
        manager.createTeam(header, 4);
        assertTrue("team id must be modified", header.getTeamId() != 0);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "createTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", header, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when teamId is negative, description is null, name's length is just fit, captainResourceId =
     * 0.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamAccuracy2() throws Exception {
        // Prepare a header with negative teamId.
        header = TestHelper.myNewTeamHeader("name ", false, 1, -1, 0, 1, null);
        manager.createTeam(header, 4);
        assertTrue("team id must be modified", header.getTeamId() != -1);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "createTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", header, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when name and description are of the maximum length.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamAccuracy3() throws Exception {
        // Prepare a header with negative teamId.
        header = TestHelper.myNewTeamHeader(TestHelper.generateString(31), false, 1, -1, 0, 1, TestHelper
            .generateString(255));
        manager.createTeam(header, 4);
        assertTrue("team id must be modified", header.getTeamId() != -1);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "createTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", header, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method with null team.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure1() throws Exception {
        try {
            manager.createTeam(null, 4);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method with negative userId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure2() throws Exception {
        try {
            manager.createTeam(header, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when team is finalized.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure3() throws Exception {
        try {
            header.setFinalized(true);
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - null name.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure4() throws Exception {
        try {
            header.setName(null);
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - empty name.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure5() throws Exception {
        try {
            header.setName("         ");
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - name too short.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure6() throws Exception {
        try {
            header.setName("4444");
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - name too long.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure7() throws Exception {
        try {
            // Prepare a long name
            header.setName(TestHelper.generateString(32));
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - description too long.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure8() throws Exception {
        try {
            // Prepare a long description
            header.setDescription(TestHelper.generateString(256));
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - negative projectId.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure9() throws Exception {
        try {
            // Prepare a header with negative captainResourceId
            header = TestHelper.myNewTeamHeader("team_name", false, -1, 1, 1, 10, "description");
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - negative captainResourceId.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure10() throws Exception {
        try {
            // Prepare a header with negative captainResourceId
            header = TestHelper.myNewTeamHeader("team_name", false, 1, 1, -1, 10, "description");
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - invalid captainPaymentPercentage.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure11() throws Exception {
        try {
            // Prepare a header with invalid captainPaymentPercentage
            header = TestHelper.myNewTeamHeader("team_name", false, 1, 1, 1, -1, "description");
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - invalid captainPaymentPercentage.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure12() throws Exception {
        try {
            // Prepare a header with invalid captainPaymentPercentage
            header = TestHelper.myNewTeamHeader("team_name", false, 1, 1, 1, 0, "description");
            manager.createTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamFailure_PersistenceFailure() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.createTeam(header, 4);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Simply modify a team and then update it. Check if the parameters are passed to the persistence correctly, and
     * the logger actually performs actions.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamAccuracy1() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);

        // Try to modify something
        header.setName("new_name");
        manager.updateTeam(header, 4);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "updateTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", header, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when description is null, name's length is just fit, captainResourceId = 0, finalized = true.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamAccuracy2() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);

        // Try to modify something
        header = TestHelper.myNewTeamHeader("name ", true, 1, header.getTeamId(), 0, 1, null);
        manager.updateTeam(header, 4);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "updateTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", header, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when name and description are of the maximum length.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamAccuracy3() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);

        // Try to modify something
        header = TestHelper.myNewTeamHeader(TestHelper.generateString(31), false, 1, header.getTeamId(), 0, 1,
            TestHelper.generateString(255));
        manager.updateTeam(header, 4);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "updateTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", header, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method with null team.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure1() throws Exception {
        try {
            manager.updateTeam(null, 4);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method with negative userId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure2() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            manager.updateTeam(header, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - null name.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure3() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            header.setName(null);
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - empty name.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure4() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            header.setName("         ");
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - name too short.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure5() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            header.setName("4444");
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - name too long.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure6() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            // Prepare a long name
            header.setName(TestHelper.generateString(32));
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - description too long.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure7() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            // Prepare a long description
            header.setDescription(TestHelper.generateString(256));
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - negative projectId.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure8() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            // Prepare a header with negative captainResourceId
            header = TestHelper.myNewTeamHeader("team_name", false, -1, header.getTeamId(), 1, 10, "description");
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - negative captainResourceId.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure9() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            // Prepare a header with negative captainResourceId
            header = TestHelper.myNewTeamHeader("team_name", false, 1, header.getTeamId(), -1, 10, "description");
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - invalid captainPaymentPercentage.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure10() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            // Prepare a header with invalid captainPaymentPercentage
            header = TestHelper.myNewTeamHeader("team_name", false, 1, header.getTeamId(), 1, -1, "description");
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - invalid captainPaymentPercentage.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure11() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            // Prepare a header with invalid captainPaymentPercentage
            header = TestHelper.myNewTeamHeader("team_name", false, 1, header.getTeamId(), 1, 0, "description");
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - team's resource id not unique.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure12() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            // Prepare a header with the same resource id as one of the positions
            header.setCaptainResourceId(position2.getMemberResourceId());
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when team's name conflicts with the position's name.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamAccuracy4() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);

        // Prepare a header with the same name as one of the positions
        header.setName(position2.getName());
        manager.updateTeam(header, 4);

        // No exception should be thrown
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team - team's captain percentage too large.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure14() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            // Prepare a header with the captain percentage too large.
            header.setCaptainPaymentPercentage(101 - position1.getPaymentPercentage()
                - position2.getPaymentPercentage());
            manager.updateTeam(header, 4);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team id - team's id negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure15() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            // Prepare a header with the negative team's id.
            header = TestHelper.myNewTeamHeader("name ", false, 1, -1, 1, 1, "description");
            manager.updateTeam(header, 4);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when invalid team id - team's id unknown.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure16() throws Exception {
        try {
            // Prepare a header with the negative team's id.
            header.setTeamId(10);
            manager.updateTeam(header, 4);
            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure_PersistenceFailure1() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.updateTeam(header, 4);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateTeamFailure_PersistenceFailure2() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            persistence.setThrowException(1);
            manager.updateTeam(header, 4);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>removeTeam(long teamId, long userId)</code> method.
     * <p>
     * Try to remove an existed team. Everything should work fine.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveTeamAccuracy() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);

        // Call this method
        manager.removeTeam(10, 20);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "removeTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(20), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>removeTeam(long teamId, long userId)</code> method.
     * <p>
     * Call this method with negative teamId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveTeamFailure1() throws Exception {
        try {
            manager.removeTeam(-1, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>removeTeam(long teamId, long userId)</code> method.
     * <p>
     * Call this method with negative userId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveTeamFailure2() throws Exception {
        try {
            manager.removeTeam(1, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removeTeam(long teamId, long userId)</code> method.
     * <p>
     * Call this method when teamId is not existed.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveTeamFailure3() throws Exception {
        try {
            manager.removeTeam(10, 20);
            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removeTeam(long teamId, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveTeamFailure_PersistenceFailure1() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.removeTeam(10, 20);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removeTeam(long teamId, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveTeamFailure_PersistenceFailure2() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            persistence.setThrowException(1);
            manager.removeTeam(10, 20);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>getTeam(long teamId)</code> method.
     * <p>
     * Force this method to return a specified value, and then compare them.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetTeamAccuracy1() throws Exception {
        persistence.setTeam(team);
        assertEquals("getTeam incorrect", team, manager.getTeam(10));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>getTeam(long teamId)</code> method.
     * <p>
     * Call this method when there is no team retrieved.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetTeamAccuracy2() throws Exception {
        assertEquals("getTeam incorrect", null, manager.getTeam(10));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>getTeam(long teamId)</code> method.
     * <p>
     * Call this method with negative teamId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetTeamFailure() throws Exception {
        try {
            manager.getTeam(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>getTeam(long teamId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetTeamFailure_PersistenceFailure() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.getTeam(1);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>findTeams(long projectId)</code> method.
     * <p>
     * Force this method to return some value, and get it to compare. Also, it will be checked if persistence is
     * invoked correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams1_Accuracy1() throws Exception {
        persistence.setHeader(header);
        TeamHeader[] ret = manager.findTeams(10);
        assertTrue("findTeams(long projectId) incorrect", Arrays.equals(new TeamHeader[] {header}, ret));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "findTeams(long)", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>findTeams(long projectId)</code> method.
     * <p>
     * Force this method to return null, and get it to compare. Also, it will be checked if persistence is invoked
     * correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams1_Accuracy2() throws Exception {
        TeamHeader[] ret = manager.findTeams(10);
        assertTrue("findTeams(long projectId) incorrect", Arrays.equals(new TeamHeader[0], ret));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "findTeams(long)", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>findTeams(long projectId)</code> method.
     * <p>
     * Call this method with negative projectId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams1_Failure() throws Exception {
        try {
            manager.findTeams(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>findTeams(long projectId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams1_PersistenceFailure() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.findTeams(1);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>findTeams(long[] projectIds)</code> method.
     * <p>
     * Force this method to return some value, and get it to compare. Also, it will be checked if persistence is
     * invoked correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams2_Accuracy1() throws Exception {
        persistence.setHeader(header);
        long[] arr = new long[] {10, 20};
        TeamHeader[] ret = manager.findTeams(arr);
        assertTrue("findTeams(long[] projectIds) incorrect", Arrays.equals(new TeamHeader[] {header}, ret));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "findTeams(long[])", invoke[0]);
        assertEquals("persistence invoked incorrectly", arr, invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>findTeams(long[] projectIds)</code> method.
     * <p>
     * Force this method to return null, and get it to compare. Also, it will be checked if persistence is invoked
     * correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams2_Accuracy2() throws Exception {
        long[] arr = new long[] {10, 20};
        TeamHeader[] ret = manager.findTeams(arr);
        assertTrue("findTeams(long[] projectIds) incorrect", Arrays.equals(new TeamHeader[0], ret));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "findTeams(long[])", invoke[0]);
        assertEquals("persistence invoked incorrectly", arr, invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>findTeams(long[] projectIds)</code> method.
     * <p>
     * Call this method with negative projectIds.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams2_Failure1() throws Exception {
        try {
            manager.findTeams((long[]) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>findTeams(long[] projectIds)</code> method.
     * <p>
     * Call this method when projectIds contains negative number.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams2_Failure2() throws Exception {
        try {
            manager.findTeams(new long[] {1, -1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>findTeams(long[] projectIds)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams2_PersistenceFailure() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.findTeams(new long[] {1});
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>findTeams(Filter filter)</code> method.
     * <p>
     * Force this method to return some value, and get it to compare. Also, it will be checked if persistence is
     * invoked correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams3_Accuracy1() throws Exception {
        persistence.setHeader(header);
        Filter filter = TeamFilterFactory.createNameFilter("name");
        TeamHeader[] ret = manager.findTeams(filter);
        assertTrue("findTeams(Filter filter) incorrect", Arrays.equals(new TeamHeader[] {header}, ret));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "findTeams(Filter)", invoke[0]);
        assertEquals("persistence invoked incorrectly", filter, invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>findTeams(Filter filter)</code> method.
     * <p>
     * Force this method to return null, and get it to compare. Also, it will be checked if persistence is invoked
     * correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams3_Accuracy2() throws Exception {
        Filter filter = TeamFilterFactory.createNameFilter("name");
        TeamHeader[] ret = manager.findTeams(filter);
        assertTrue("findTeams(Filter filter) incorrect", Arrays.equals(new TeamHeader[0], ret));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "findTeams(Filter)", invoke[0]);
        assertEquals("persistence invoked incorrectly", filter, invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>findTeams(Filter filter)</code> method.
     * <p>
     * Call this method with null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams3_Failure() throws Exception {
        try {
            manager.findTeams((Filter) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>findTeams(Filter filter)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindTeams3_PersistenceFailure() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.findTeams(TeamFilterFactory.createNameFilter("name"));
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>getTeamFromPosition(long positionId)</code> method.
     * <p>
     * Force this method to return a specified value, and then compare them.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetTeamFromPositionAccuracy1() throws Exception {
        persistence.setTeam(team);
        assertEquals("getTeamFromPosition incorrect", team, manager.getTeamFromPosition(10));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeamFromPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>getTeamFromPosition(long positionId)</code> method.
     * <p>
     * Call this method when there is no team retrieved.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetTeamFromPositionAccuracy2() throws Exception {
        assertEquals("getTeamFromPosition incorrect", null, manager.getTeamFromPosition(10));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeamFromPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>getTeamFromPosition(long positionId)</code> method.
     * <p>
     * Call this method with negative positionId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetTeamFromPositionFailure() throws Exception {
        try {
            manager.getTeamFromPosition(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>getTeamFromPosition(long positionId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetTeamFromPosition_PersistenceFailure() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.getTeamFromPosition(1);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }
}
