/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.team.accuracytests;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;

import com.topcoder.management.team.impl.TeamImpl;

import junit.framework.TestCase;

/**
 * Accuracy test cases for the <code>TeamImpl</code> class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class TeamImplTests extends TestCase {
    /**
     * Tests the constructor.
     */
    public void testCtor() {
        TeamHeader header = new TeamHeader();
        TeamPosition position = new TeamPosition();

        Team team = new TeamImpl(header, new TeamPosition[] {position});

        assertEquals("the team header should match the passed header", header, team.getTeamHeader());
        TeamPosition[] pos = team.getPositions();
        assertEquals("there should be 1 position", 1, pos.length);
        assertEquals("the position should equal the passed position", position, pos[0]);
    }

    /**
     * Tests the <code>getTeamHeader</code> and <code>setTeamHeader</code> methods.
     */
    public void testGetSetTeamHeader() {
        Team team = new TeamImpl();

        assertNull("the header should initially be null", team.getTeamHeader());
        TeamHeader header = new TeamHeader();
        team.setTeamHeader(header);
        assertEquals("the header should match the set header", header, team.getTeamHeader());
    }

    /**
     * Tests the <code>setPositions</code> and <code>getPositions</code> method.
     */
    public void testGetSetPositions() {
        Team team = new TeamImpl();

        assertEquals("the positions should initially be an empty array", 0, team.getPositions().length);

        TeamPosition position = new TeamPosition();

        team.setPositions(new TeamPosition[] {position});

        TeamPosition[] pos = team.getPositions();
        assertEquals("there should now be 1 position", 1, pos.length);
        assertEquals("the one position should be the set position", position, pos[0]);

        team.setPositions(null);

        assertEquals("the positions should be reset to an empty array", 0, team.getPositions().length);
    }
}
