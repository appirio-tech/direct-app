/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.stresstests;

import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.impl.TeamManagerImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * The stress test of team management component.
 * </P>
 *
 * @author biotrail
 * @version 1.0
 */
public class TeamManagementStressTests extends TestCase {
    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final int NUMBER = 1000;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private static long current = -1;

    /**
     * <p>
     * The TeamManagerImpl instance used for testing.
     * </p>
     */
    private TeamManagerImpl manager;

    /**
     * <p>
     * The TeamHeader instance used for testing.
     * </p>
     */
    private TeamHeader header;

    /**
     * <p>
     * The TeamPosition instance used for testing.
     * </p>
     */
    private TeamPosition position;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        StressTestHelper.loadXMLConfig(StressTestHelper.CONFIG_FILE);
        manager = new TeamManagerImpl();

        header = new TeamHeader();
        header.setName("TeamManager");
        header.setDescription("description");
        header.setProjectId(1);
        header.setCaptainResourceId(1);
        header.setCaptainPaymentPercentage(10);

        position = new TeamPosition();
        position.setPositionId(1);
        position.setName("TeamPosition");
        position.setDescription("description");
        position.setPaymentPercentage(20);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearConfig();

        position = null;
        manager = null;
        header = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TeamManagementStressTests.class);
    }

    /**
     * <p>
     * This is the stress tests for creating team.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTeam() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.createTeam(header, 1);
            assertTrue("Failed to create team.", header.getTeamId() >= 0);
        }

        printResult("Create Team");
    }

    /**
     * <p>
     * This is the stress tests for updating team.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTeam() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.createTeam(header, 1);
            manager.updateTeam(header, 1);
            assertTrue("Failed to update team.", header.getTeamId() >= 0);
        }

        printResult("Update Team");
    }

    /**
     * <p>
     * This is the stress tests for removing team.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveTeam() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.createTeam(header, 1);
            manager.removeTeam(header.getTeamId(), 1);
            assertTrue("Failed to remove team.", header.getTeamId() >= 0);
        }

        printResult("Remove Team");
    }

    /**
     * <p>
     * This is the stress tests for adding position.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPosition() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.createTeam(header, 1);
            manager.addPosition(header.getTeamId(), position,1);
            assertTrue("Failed to add position.", position.getPositionId() >= 0);
        }

        printResult("Add Position");
    }

    /**
     * <p>
     * This is the stress tests for updating position.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePosition() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.createTeam(header, 1);
            manager.addPosition(header.getTeamId(), position, 1);
            manager.updatePosition(position, 1);
            assertTrue("Failed to update position.", position.getPositionId() >= 0);
        }

        printResult("Update Position");
    }

    /**
     * <p>
     * This is the stress tests for removing position.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemovePosition() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.createTeam(header, 1);
            manager.addPosition(header.getTeamId(), position, 1);
            manager.removePosition(position.getPositionId(), 1);
            assertTrue("Failed to remove position.", position.getPositionId() >= 0);
        }

        printResult("Remove Position");
    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    private static void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name the test name
     */
    private static void printResult(String name) {
        System.out.println("The test " + name + " running " + NUMBER + " times, took time: "
            + (System.currentTimeMillis() - current) + " ms");
    }
}
