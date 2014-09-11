/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.impl;

import com.topcoder.management.team.Helper;
import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;

/**
 * <p>
 * Simple bean implementation of the Team interface. Supports all setters and getters by defining fields based on java
 * bean naming conventions. Also provides is the customary empty constructor, as well as a convenience full
 * constructor so the user can set all properties in one go.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TeamImpl implements Team {

    /**
     * <p>
     * Represents the team information, including name, project ID, and whether the team is finalized.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Once set, this value will never be null. Default is null.
     * </p>
     */
    private TeamHeader teamHeader = null;

    /**
     * <p>
     * Represents the positions that have been set for this team.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Set to a non-null array on construction, with zero to many members. Afterwards, it will never be null, and be
     * reset to have zero or more members. No member shall ever be null. Default is an empty array.
     * </p>
     */
    private TeamPosition[] positions;

    /**
     * Default constructor.
     */
    public TeamImpl() {
        this.positions = new TeamPosition[0];
    }

    /**
     * Full constructor. This convenience constructor allows for setting all values in one go. positions can be null
     * if no position for this team, besides, positions will be shallowly copied.
     * @param teamHeader
     *            the team information, including name, project ID, and whether the team is finalized
     * @param positions
     *            the positions that have been set for this team
     * @throws IllegalArgumentException
     *             If teamHeader is null, or positions contains null elements
     */
    public TeamImpl(TeamHeader teamHeader, TeamPosition[] positions) {
        setTeamHeader(teamHeader);
        setPositions(positions);
    }

    /**
     * Gets the teamHeader field value. May return null.
     * @return The teamHeader field value
     */
    public TeamHeader getTeamHeader() {
        return teamHeader;
    }

    /**
     * Sets the teamHeader field value.
     * @param teamHeader
     *            The teamHeader field value
     * @throws IllegalArgumentException
     *             If teamHeader is null
     */
    public void setTeamHeader(TeamHeader teamHeader) {
        Helper.assertObjectNotNull(teamHeader, "teamHeader");
        this.teamHeader = teamHeader;
    }

    /**
     * Gets the shallow copy of the positions field value. It may be empty, but will not be null.
     * @return The positions field value
     */
    public TeamPosition[] getPositions() {
        return (TeamPosition[]) positions.clone();
    }

    /**
     * Sets the positions field value. positions can be null if no position for this team, besides, positions will be
     * shallowly copied.
     * @param positions
     *            The positions field value
     * @throws IllegalArgumentException
     *             If positions contains null elements
     */
    public void setPositions(TeamPosition[] positions) {
        if (positions == null) {
            this.positions = new TeamPosition[0];
        } else {
            for (int i = 0; i < positions.length; i++) {
                Helper.assertObjectNotNull(positions[i], "positions[" + i + "]");
            }
            this.positions = (TeamPosition[]) positions.clone();
        }
    }
}
