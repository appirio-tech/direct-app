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
public class TeamManagerImplUnitTest3Positions extends TestCase {
    /** The ByteArrayOutputStream for testing of logs. */
    private ByteArrayOutputStream out;

    /** Provide a mock TeamPersistence for testing. */
    private MockTeamPersistence persistence;

    /** Provide a TeamManagerImpl for testing. */
    private TeamManagerImpl manager;

    /** Provide a TeamHeader for testing. */
    private TeamHeader header = new TeamHeader("header_name", false, 1, 1, 1, 40, "header_des");

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
        return new TestSuite(TeamManagerImplUnitTest3Positions.class);
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
     * Accuracy test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Simply add position1 to an existed team with position1. Check if the parameters are passed to the persistence
     * correctly, and the logger actually performs actions.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionAccuracy1() throws Exception {
        // Force the persistence to return a preset team with a single position1.
        persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

        // Add a position
        position2.setPositionId(0);
        manager.addPosition(header.getTeamId(), position2, 4);
        assertTrue("position id must be modified", position2.getPositionId() != 0);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "addPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        assertEquals("persistence invoked incorrectly", position2, invoke[2]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[3]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when positionId is negative, description is null, name's length is just fit, memberResourceId =
     * -1(filled==false).
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionAccuracy2() throws Exception {
        // Force the persistence to return a preset team with a single position1.
        persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

        // Add a position
        position2 = TestHelper.myNewTeamPosition(null, false, -1, 1, "name ", -1, false);
        manager.addPosition(header.getTeamId(), position2, 4);
        assertTrue("position id must be modified", position2.getPositionId() != 0);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "addPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        assertEquals("persistence invoked incorrectly", position2, invoke[2]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[3]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when name and description are of the maximum length.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionAccuracy3() throws Exception {
        // Force the persistence to return a preset team with a single position1.
        persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

        // Add a position
        position2 = TestHelper.myNewTeamPosition(TestHelper.generateString(255), false, -1, 1, TestHelper
            .generateString(31), -1, false);
        manager.addPosition(header.getTeamId(), position2, 4);
        assertTrue("position id must be modified", position2.getPositionId() != 0);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "addPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        assertEquals("persistence invoked incorrectly", position2, invoke[2]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[3]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Two positions all with memberResourceId = -1 is legal. (negative resourceId will not be taken into account
     * during uniqueness checking)
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionAccuracy4() throws Exception {
        // Force the persistence to return a preset team with a single position1.
        position1 = TestHelper.myNewTeamPosition(null, false, -1, 10, "position1", 1, false);
        persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

        // Add a position
        position2 = TestHelper.myNewTeamPosition(null, false, -1, 10, "position2", 1, false);
        manager.addPosition(header.getTeamId(), position2, 4);
        assertTrue("position id must be modified", position2.getPositionId() != 0);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeam", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "addPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(header.getTeamId()), invoke[1]);
        assertEquals("persistence invoked incorrectly", position2, invoke[2]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[3]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method with negative teamId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure1() throws Exception {
        try {
            manager.addPosition(-1, position1, 4);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method with null position.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure2() throws Exception {
        try {
            manager.addPosition(1, null, 4);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method with negative userId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure3() throws Exception {
        try {
            manager.addPosition(1, position1, -4);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when team is existed.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure4() throws Exception {
        try {
            manager.addPosition(1, position1, 4);
            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when team is finalized.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure5() throws Exception {
        try {
            // Force the persistence to return the preset team.
            header.setFinalized(true);
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position name is null.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure6() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2.setName(null);
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position name is trimmed empty.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure7() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2.setName("      ");
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position name is too short.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure8() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2.setName("name");
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position name is too long.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure9() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2.setName(TestHelper.generateString(32));
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position description is too long.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure10() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2.setDescription(TestHelper.generateString(256));
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's name is the same as the captain's.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionAccuracy5() throws Exception {
        // Force the persistence to return the preset team.
        persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

        // Try to add the position
        position2.setName(header.getName());
        manager.addPosition(header.getTeamId(), position2, 4);

        // No exception should be thrown.
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's name not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure12() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2.setName(position1.getName());
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's memberResourceId not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure13() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2.setMemberResourceId(header.getCaptainResourceId());
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's memberResourceId not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure14() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2.setMemberResourceId(position1.getMemberResourceId());
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's paymentPercentage is too large.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure15() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2.setPaymentPercentage(101 - header.getCaptainPaymentPercentage()
                - position1.getPaymentPercentage());
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's paymentPercentage is negative.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure16() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2 = TestHelper.myNewTeamPosition(null, false, 20, -1, "name1", 30, false);
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's paymentPercentage is zero.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure17() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2 = TestHelper.myNewTeamPosition(null, false, 20, 0, "name1", 30, false);
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's resourceId is negative but filled = true.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPositionFailure18() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));

            // Try to add the position
            position2 = TestHelper.myNewTeamPosition(null, true, -1, 10, "name1", 30, false);
            manager.addPosition(header.getTeamId(), position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPosition_PersistenceFailure1() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.addPosition(header.getTeamId(), position2, 4);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>addPosition(long teamId, TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddPosition_PersistenceFailure2() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1}));
        try {
            persistence.setThrowException(1);
            manager.addPosition(header.getTeamId(), position2, 4);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Simply update an existed position. Check if the parameters are passed to the persistence correctly, and the
     * logger actually performs actions.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionAccuracy1() throws Exception {
        // Force the persistence to return a preset team with two positions.
        persistence.setTeam(team);

        // Update the position
        position2.setName("new_name");
        manager.updatePosition(position2, 4);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeamFromPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(position2.getPositionId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "updatePosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", position2, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when description is null, name's length is just fit, memberResourceId = -1(filled==false).
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionAccuracy2() throws Exception {
        // Force the persistence to return a preset team with two positions.
        persistence.setTeam(team);

        // Update the position
        position2 = TestHelper.myNewTeamPosition(null, false, -1, 1, "name ", position2.getPositionId(), false);
        manager.updatePosition(position2, 4);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeamFromPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(position2.getPositionId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "updatePosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", position2, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when name and description are of the maximum length.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionAccuracy3() throws Exception {
        // Force the persistence to return a preset team with two positions.
        persistence.setTeam(team);

        // Update the position
        position2 = TestHelper.myNewTeamPosition(TestHelper.generateString(255), false, 0, 1, TestHelper
            .generateString(31), position2.getPositionId(), false);
        manager.updatePosition(position2, 4);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeamFromPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(position2.getPositionId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "updatePosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", position2, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Two positions all with memberResourceId = -1 is legal. (negative resourceId will not be taken into account
     * during uniqueness checking)
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionAccuracy4() throws Exception {
        // Force the persistence to return a preset team with a single position1.
        position1 = TestHelper.myNewTeamPosition(null, false, -1, 10, "position1", 1, false);
        persistence.setTeam(new TeamImpl(header, new TeamPosition[] {position1, position2}));

        // Add a position
        position2 = TestHelper.myNewTeamPosition(null, false, -1, 10, "position2", 1, false);
        manager.updatePosition(position2, 4);
        assertTrue("position id must be modified", position2.getPositionId() != 0);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getTeamFromPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(position2.getPositionId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "updatePosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", position2, invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method with null position.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure1() throws Exception {
        try {
            manager.updatePosition(null, 4);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method with negative userId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure2() throws Exception {
        try {
            manager.updatePosition(position1, -4);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when team is finalized.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure3() throws Exception {
        try {
            // Force the persistence to return the preset team.
            header.setFinalized(true);
            persistence.setTeam(team);

            // Try to add the position
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position name is null.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure4() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2.setName(null);
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position name is trimmed empty.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure5() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2.setName("      ");
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position name is too short.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure6() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2.setName("name");
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position name is too long.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure7() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2.setName(TestHelper.generateString(32));
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position description is too long.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure8() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2.setDescription(TestHelper.generateString(256));
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's is the same as the captain's.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionAccuracy5() throws Exception {
        // Force the persistence to return the preset team.
        persistence.setTeam(team);

        // Try to add the position
        position2.setName(header.getName());
        manager.updatePosition(position2, 4);

        // No exception should be thrown.
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's name not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure10() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2.setName(position1.getName());
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's memberResourceId not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure11() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2.setMemberResourceId(header.getCaptainResourceId());
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's memberResourceId not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure12() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2.setMemberResourceId(position1.getMemberResourceId());
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's paymentPercentage is too large.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure13() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2.setPaymentPercentage(101 - header.getCaptainPaymentPercentage()
                - position1.getPaymentPercentage());
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's paymentPercentage is negative.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure14() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2 = TestHelper.myNewTeamPosition(null, false, 20, -1, "name1", 30, false);
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's paymentPercentage is zero.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure15() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2 = TestHelper.myNewTeamPosition(null, false, 20, 0, "name1", 30, false);
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's id is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure16() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2 = TestHelper.myNewTeamPosition(null, false, 20, 1, "name1", -1, false);
            manager.updatePosition(position2, 4);

            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's id is unknown.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure17() throws Exception {
        try {
            // Try to add the position
            position2 = TestHelper.myNewTeamPosition(null, false, 20, 1, "name1", 100, false);
            manager.updatePosition(position2, 4);

            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when position's resourceId is negative, but filled = true.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePositionFailure18() throws Exception {
        try {
            // Force the persistence to return the preset team.
            persistence.setTeam(team);

            // Try to add the position
            position2 = TestHelper.myNewTeamPosition(null, true, -1, 10, "name1", 30, false);
            manager.updatePosition(position2, 4);

            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePosition_PersistenceFailure1() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.updatePosition(position2, 4);

            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdatePosition_PersistenceFailure2() throws Exception {
        // Force persistence to return this preset team.
        persistence.setTeam(team);
        try {
            persistence.setThrowException(1);
            manager.updatePosition(position2, 4);

            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Simply attempt to remove a position. Check if the parameters are passed to the persistence correctly, and the
     * logger actually performs actions.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePositionAccuracy() throws Exception {
        // Force the persistence to return a preset team and a preset position1.
        persistence.setTeam(team);
        persistence.setPosition(position1);

        // Update the position
        manager.removePosition(position1.getPositionId(), 4);

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(position1.getPositionId()), invoke[1]);
        invoke = persistence.getMethodInvoke(1);
        assertEquals("persistence invoked incorrectly", "getTeamFromPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(position1.getPositionId()), invoke[1]);
        invoke = persistence.getMethodInvoke(2);
        assertEquals("persistence invoked incorrectly", "removePosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(position1.getPositionId()), invoke[1]);
        assertEquals("persistence invoked incorrectly", new Long(4), invoke[2]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Call this method with negative positionId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePositionFailure1() throws Exception {
        try {
            manager.removePosition(-1, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Call this method with negative userId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePositionFailure2() throws Exception {
        try {
            manager.removePosition(1, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Remove a non-existed positionId.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePositionFailure3() throws Exception {
        try {
            manager.removePosition(1, 1);
            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Call this method when team is finalized.
     * </p>
     * <p>
     * Expect PositionRemovalException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePositionFailure4() throws Exception {
        // Force the persistence to return some specified values
        header.setFinalized(true);
        persistence.setTeam(team);
        persistence.setPosition(position1);

        try {
            manager.removePosition(position1.getPositionId(), 4);
            fail("Expect PositionRemovalException.");
        } catch (PositionRemovalException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Call this method when position is published.
     * </p>
     * <p>
     * Expect PositionRemovalException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePositionFailure5() throws Exception {
        // Force the persistence to return some specified values
        position1.setPublished(true);
        persistence.setTeam(team);
        persistence.setPosition(position1);

        try {
            manager.removePosition(position1.getPositionId(), 4);
            fail("Expect PositionRemovalException.");
        } catch (PositionRemovalException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Call this method when position is filled.
     * </p>
     * <p>
     * Expect PositionRemovalException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePositionFailure6() throws Exception {
        // Force the persistence to return some specified values
        position1.setFilled(true);
        persistence.setTeam(team);
        persistence.setPosition(position1);

        try {
            manager.removePosition(position1.getPositionId(), 4);
            fail("Expect PositionRemovalException.");
        } catch (PositionRemovalException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePosition_PersistenceFailure1() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.removePosition(position1.getPositionId(), 4);

            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePosition_PersistenceFailure2() throws Exception {
        // Force persistence to return this preset team.
        persistence.setPosition(position1);
        try {
            persistence.setThrowException(1);
            manager.removePosition(position1.getPositionId(), 4);

            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemovePosition_PersistenceFailure3() throws Exception {
        // Force persistence to return this preset team.
        persistence.setPosition(position1);
        persistence.setTeam(team);
        try {
            persistence.setThrowException(2);
            manager.removePosition(position1.getPositionId(), 4);

            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // Make sure this exception has been logged
            assertTrue("ERROR missing in the log", out.toString().indexOf("ERROR") != -1);
        }
    }

    /**
     * Accuracy test of <code>getPosition(long positionId)</code> method.
     * <p>
     * Force this method to return a specified value, and then compare them.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPositionAccuracy1() throws Exception {
        persistence.setPosition(position1);
        assertEquals("getPosition incorrect", position1, manager.getPosition(10));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>getPosition(long positionId)</code> method.
     * <p>
     * Call this method when there is no team retrieved.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPositionAccuracy2() throws Exception {
        assertEquals("getPosition incorrect", null, manager.getPosition(10));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "getPosition", invoke[0]);
        assertEquals("persistence invoked incorrectly", new Long(10), invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>getPosition(long positionId)</code> method.
     * <p>
     * Call this method with negative positionId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPositionFailure() throws Exception {
        try {
            manager.getPosition(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Accuracy test of <code>findPositions(Filter filter)</code> method.
     * <p>
     * Force this method to return some value, and get it to compare. Also, it will be checked if persistence is
     * invoked correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindPositions_Accuracy1() throws Exception {
        persistence.setPosition(position1);
        Filter filter = PositionFilterFactory.createNameFilter("name");
        TeamPosition[] ret = manager.findPositions(filter);
        assertTrue("findPositions(long projectId) incorrect", Arrays.equals(new TeamPosition[] {position1}, ret));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "findPositions", invoke[0]);
        assertEquals("persistence invoked incorrectly", filter, invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Accuracy test of <code>findPositions(Filter filter)</code> method.
     * <p>
     * Force this method to return null, and get it to compare. Also, it will be checked if persistence is invoked
     * correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindPositions_Accuracy2() throws Exception {
        Filter filter = PositionFilterFactory.createNameFilter("name");
        TeamPosition[] ret = manager.findPositions(filter);
        assertTrue("findPositions(Filter) incorrect", Arrays.equals(new TeamPosition[0], ret));

        // Verify if persistence is invoked correctly.
        Object[] invoke = persistence.getMethodInvoke(0);
        assertEquals("persistence invoked incorrectly", "findPositions", invoke[0]);
        assertEquals("persistence invoked incorrectly", filter, invoke[1]);

        // Check if the required items are present in the log
        assertTrue("debug missing in the log", out.toString().indexOf("DEBUG") != -1);
        assertTrue("info missing in the log", out.toString().indexOf("INFO") != -1);
    }

    /**
     * Failure test of <code>findPositions(Filter filter)</code> method.
     * <p>
     * Call this method with null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindPositions_Failure() throws Exception {
        try {
            manager.findPositions((Filter) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }

    /**
     * Failure test of <code>findPositions(Filter filter)</code> method.
     * <p>
     * Call this method when forcing the persistence to throw TeamPersistenceException.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindPositions_PersistenceFailure() throws Exception {
        try {
            persistence.setThrowException(0);
            manager.findPositions(PositionFilterFactory.createNameFilter("name"));
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // This exception is thrown by MockTeamPersistence, so no ERROR logs for it
        }
    }
}
