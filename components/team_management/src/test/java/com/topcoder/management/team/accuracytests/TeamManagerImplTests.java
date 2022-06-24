/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.team.accuracytests;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPosition;

import com.topcoder.management.team.impl.TeamImpl;
import com.topcoder.management.team.impl.TeamManagerImpl;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.config.ConfigManager;

import com.topcoder.util.idgenerator.IDGeneratorFactory;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Accuracy test cases for the <code>TeamImpl</code> class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class TeamManagerImplTests extends TestCase {
    /**
     * The team manager instance to use for the test.
     */
    private TeamManager tm;

    /**
     * The <code>TeamPersistence</code> instance to use for the test.
     */
    private MockTeamPersistence tp;

    /**
     * Initializes <code>tp</code> and <code>tm</code> to a new instance prior to each test.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        tearDown();
        manager.add("AccuracyTestConfig.xml");

        tp = new MockTeamPersistence();
        tm = new TeamManagerImpl(tp, IDGeneratorFactory.getIDGenerator("team_id_seq"), null);
    }

    /**
     * Cleans up the configuration manager after each test.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator it = manager.getAllNamespaces(); it.hasNext();) {
            manager.removeNamespace((String) it.next());
        }
    }

    /**
     * Creates a new team header with reasonable defaults.
     *
     * @param teamId the team ID
     */
    private static TeamHeader createTeamHeader(long teamId) {
        TeamHeader header = new TeamHeader();
        header.setName("the name");
        header.setFinalized(false);
        header.setProjectId(3);
        if (teamId >= 0) {
            header.setTeamId(teamId);
        }
        header.setCaptainResourceId(7);
        header.setCaptainPaymentPercentage(20);
        header.setDescription("description");
        return header;
    }

    /**
     * Creates a new <code>Team</code> with the specified ID.
     *
     * @param teamId the team ID
     * @return a new <code>Team</code> with the specified ID
     */
    private static Team createTeam(long teamId) {
        return new TeamImpl(createTeamHeader(teamId), new TeamPosition[0]);
    }

    /**
     * Creates a new <code>TeamPosition</code> instance.
     *
     * @return a new <code>TeamPosition</code> instance
     */
    private static TeamPosition createTeamPosition() {
        return new TeamPosition("description", true, 5, 50, "position name", 4, true);
    }

    /**
     * Tests the <code>createTeam</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testCreateTeam() throws Exception {
        TeamHeader header = createTeamHeader(-1);

        tm.createTeam(header, 13);
        Iterator it = tp.getCalls().iterator();
        assertEquals("createTeam was not called", "createTeam", it.next());
        assertEquals("the arguments do not match the expected arguments", header, it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(13), it.next());
        assertTrue("the team ID should be set", header.getTeamId() >= 0);
        assertFalse("no more calls should be made to persistence", it.hasNext());
    }

    /**
     * Tests the <code>updateTeam</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testUpdateTeam() throws Exception {
        TeamHeader header = createTeamHeader(5);
        Team team = createTeam(5);
        tp.addReturnValue(team);
        tp.addReturnValue(team);

        tm.updateTeam(header, 15);
        Iterator it = tp.getCalls().iterator();
        assertEquals("getTeam was not called", "getTeam", it.next());
        assertEquals("bad argument to getTeam", new Long(5), it.next());
        String nextCall = (String) it.next();
        if (nextCall.equals("getTeam")) {
            System.err.println("warning: unnecessary second call to getTeam");
            assertEquals("bad argument to getTeam", new Long(5), it.next());
            assertEquals("updateTeam was not called", "updateTeam", it.next());
        } else if (!nextCall.equals("updateTeam")) {
            fail("updateTeam should be called");
        }

        assertEquals("the arguments do not match the expected arguments", header, it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(15), it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());
    }

    /**
     * Tests the <code>removeTeam</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testRemoveTeam() throws Exception {
        tp.addReturnValue(createTeam(5));

        tm.removeTeam(5, 15);
        Iterator it = tp.getCalls().iterator();
        assertEquals("getTeam was not called", "getTeam", it.next());
        assertEquals("bad argument to getTeam", new Long(5), it.next());
        assertEquals("removeTeam was not called", "removeTeam", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(5), it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(15), it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());
    }


    /**
     * Tests the <code>getTeam</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testGetTeam() throws Exception {
        Team team = createTeam(7);
        tp.addReturnValue(team);
        tp.addReturnValue(team);

        Team ret = tm.getTeam(7);

        Iterator it = tp.getCalls().iterator();
        assertEquals("getTeam was not called", "getTeam", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(7), it.next());
        if (it.hasNext()) {
            Object call = it.next();
            if ("getTeam".equals(call)) {
                System.err.println("warning: unnecessary call to getTeam");
            } else {
                fail("unexpected call " + call);
            }
        }

        assertEquals("unexpected return from getTeam", team, ret);
    }

    /**
     * Tests the <code>findTeams(long)</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testFindTeamsByProjectId() throws Exception {
        TeamHeader[] headers = new TeamHeader[0];
        tp.addReturnValue(headers);

        TeamHeader[] ret = tm.findTeams(9);

        Iterator it = tp.getCalls().iterator();
        assertEquals("findTeams was not called", "findTeams", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(9), it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());

        assertEquals("unexpected return from findTeams", headers, ret);
    }

    /**
     * Tests the <code>findTeams(long[])</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testFindTeamsByProjectIds() throws Exception {
        TeamHeader[] headers = new TeamHeader[0];
        tp.addReturnValue(headers);

        long[] ids = new long[9];
        TeamHeader[] ret = tm.findTeams(ids);

        Iterator it = tp.getCalls().iterator();
        assertEquals("findTeams was not called", "findTeams", it.next());
        assertEquals("the arguments do not match the expected arguments", ids, it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());

        assertEquals("unexpected return from findTeams", headers, ret);
    }

    /**
     * Tests the <code>findTeams(Filter)</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testFindTeamsByFilter() throws Exception {
        TeamHeader[] headers = new TeamHeader[0];
        tp.addReturnValue(headers);

        Filter filter = new EqualToFilter("name", "name");

        TeamHeader[] ret = tm.findTeams(filter);

        Iterator it = tp.getCalls().iterator();
        assertEquals("findTeams was not called", "findTeams", it.next());
        assertEquals("the arguments do not match the expected arguments", filter, it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());

        assertEquals("unexpected return from findTeams", headers, ret);
    }

    /**
     * Tests the <code>getTeamFromPosition</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testGetTeamFromPosition() throws Exception {
        Team team = createTeam(4);

        tp.addReturnValue(team);

        Team ret = tm.getTeamFromPosition(5);

        Iterator it = tp.getCalls().iterator();
        assertEquals("getTeamFromPosition was not called", "getTeamFromPosition", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(5), it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());

        assertEquals("unexpected return from getTeamFromPosition", team, ret);
    }

    /**
     * Tests the <code>addPosition</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testAddPosition() throws Exception {
        TeamPosition pos = createTeamPosition();

        tp.addReturnValue(createTeam(3));
        tp.addReturnValue(pos);

        tm.addPosition(4, pos, 6);

        Iterator it = tp.getCalls().iterator();
        assertEquals("getTeam was not called", "getTeam", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(4), it.next());
        assertEquals("addPosition was not called", "addPosition", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(4), it.next());
        assertEquals("the arguments do not match the expected arguments", pos, it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(6), it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());
    }

    /**
     * Tests the <code>updatePosition</code> method.
     */
    public void testUpdatePosition() throws Exception {
        TeamPosition pos = createTeamPosition();
        tp.addReturnValue(pos);
        tp.addReturnValue(createTeam(4));

        tm.updatePosition(pos, 7);

        Iterator it = tp.getCalls().iterator();
        String call = (String) it.next();
        if (call.equals("getPosition")) {
            System.err.println("warning: unnecessary call to getPosition");
            assertEquals("the arguments do not match the expected arguments", new Long(4), it.next());
            assertEquals("getTeamFromPosition was not called", "getTeamFromPosition", it.next());
        } else if (!call.equals("getTeamFromPosition")) {
            fail("getTeamFromPosition should be called");
        }

        assertEquals("the arguments do not match the expected arguments", new Long(4), it.next());
        assertEquals("updatePosition was not called", "updatePosition", it.next());
        assertEquals("the arguments do not match the expected arguments", pos, it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(7), it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());
    }

    /**
     * Tests the <code>removePosition</code> method.
     */
    public void testRemovePosition() throws Exception {
        TeamPosition pos = createTeamPosition();
        pos.setPublished(false);
        pos.setFilled(false);

        tp.addReturnValue(pos);
        tp.addReturnValue(createTeam(4));

        tm.removePosition(pos.getPositionId(), 8);

        Iterator it = tp.getCalls().iterator();
        assertEquals("getPosition was not called", "getPosition", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(pos.getPositionId()), it.next());
        assertEquals("getTeamFromPosition was not called", "getTeamFromPosition", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(4), it.next());
        assertEquals("removePosition should be called", "removePosition", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(4), it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(8), it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());
    }

    /**
     * Tests the <code>getPosition</code> method.
     */
    public void testGetPosition() throws Exception {
        TeamPosition pos = createTeamPosition();

        tp.addReturnValue(pos);

        TeamPosition ret = tm.getPosition(pos.getPositionId());

        assertEquals("the returned value should match the expected value", pos, ret);
        Iterator it = tp.getCalls().iterator();
        assertEquals("getPosition was not called", "getPosition", it.next());
        assertEquals("the arguments do not match the expected arguments", new Long(pos.getPositionId()), it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());
    }

    public void testFindPositions() throws Exception {
        TeamPosition[] pos = new TeamPosition[] {createTeamPosition()};

        tp.addReturnValue(pos);

        Filter filter = new EqualToFilter("name", "name");
        TeamPosition[] ret = tm.findPositions(filter);

        assertEquals("the returned value should match the expected value", pos, ret);
        Iterator it = tp.getCalls().iterator();
        assertEquals("findPositions was not called", "findPositions", it.next());
        assertEquals("the arguments do not match the expected arguments", filter, it.next());
        assertFalse("no more calls should be made to persistence", it.hasNext());
    }
}
