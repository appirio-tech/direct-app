/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPosition;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of <code>TeamManager</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTeamManager implements TeamManager {
    /**
     * Constructs an instance.
     */
    public MockTeamManager() {
    }

    /**
     * Creates the team.
     *
     * @param team
     *            The team to be created
     * @param userId
     *            The user Id for logging and auditing purposes
     */
    public void createTeam(TeamHeader team, long userId) {
    }

    /**
     * Updates the team.
     *
     * @param team
     *            The team to be updated
     * @param userId
     *            The user Id for logging and auditing purposes
     */
    public void updateTeam(TeamHeader team, long userId) {
    }

    /**
     * Removes the team and all positions associated with it.
     *
     * @param teamId
     *            The ID of the team to be removed
     * @param userId
     *            The user Id for logging and auditing purposes
     */
    public void removeTeam(long teamId, long userId) {
    }

    /**
     * Retrieves the team and all associated positions. Returns null if it does not exist.
     *
     * @param teamId
     *            The ID of the team to be retrieved
     * @return always null.
     */
    public Team getTeam(long teamId) {
        return null;
    }

    /**
     * Finds all teams associated with the project with the given ID. Returns empty array
     * if none found.
     *
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @return An array of matching TeamHeader, or empty if no matches found
     */
    public TeamHeader[] findTeams(long projectId) {
        TeamHeader team = new TeamHeader();
        team.setTeamId(1);

        return new TeamHeader[] {team};
    }

    /**
     * Finds all teams associated with the projects with the given IDs. Returns empty
     * array if none found.
     *
     * @param projectIds
     *            The IDs of the projects whose teams are to be retrieved
     * @return An array of matching TeamHeader.
     */
    public TeamHeader[] findTeams(long[] projectIds) {
        TeamHeader team = new TeamHeader();
        team.setTeamId(1);

        return new TeamHeader[] {team};
    }

    /**
     * Finds all teams that match the criteria in the passed filter. Returns empty array
     * if none found.
     *
     * @param filter
     *            The filter criteria to match teams
     * @return always null.
     */
    public TeamHeader[] findTeams(Filter filter) {
        return null;
    }

    /**
     * Gets the team, and all its positions, that the position with the given ID belongs
     * to. Returns null if it does not exist.
     *
     * @param positionId
     *            The ID of the position whose team is to be retrieved
     * @return always null.
     */
    public Team getTeamFromPosition(long positionId) {
        return null;
    }

    /**
     * Adds a position to the existing team with the given teamID.
     *
     * @param teamId
     *            The ID of the team to which the position is to be added
     * @param position
     *            TeamPosition to add to the team with the given teamId
     * @param userId
     *            The user Id for logging and auditing purposes
     */
    public void addPosition(long teamId, TeamPosition position, long userId) {
    }

    /**
     * Updates a position.
     *
     * @param position
     *            TeamPosition to update
     * @param userId
     *            The user Id for logging and auditing purposes
     */
    public void updatePosition(TeamPosition position, long userId) {
    }

    /**
     * Removes a position.
     *
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     */
    public void removePosition(long positionId, long userId) {
    }

    /**
     * Retrieves the position with the given ID.
     *
     * @param positionId
     *            The ID of the position to get
     * @return always null.
     */
    public TeamPosition getPosition(long positionId) {
        return null;
    }

    /**
     * Finds all positions that match the criteria in the passed filter.
     *
     * @param filter
     *            The filter criteria to match positions
     * @return always null.
     */
    public TeamPosition[] findPositions(Filter filter) {
        return null;
    }
}
