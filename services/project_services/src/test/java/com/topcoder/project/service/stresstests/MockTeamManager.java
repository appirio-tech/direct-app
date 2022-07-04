/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of {@link TeamManager}.
 * </p>
 * 
 * @author stylecheck
 * @version 1.1
 * @since 1.0
 */
public class MockTeamManager implements TeamManager {

    /**
     * <p>
     * Mock Implementation.
     * </p>
     */
    public MockTeamManager() {
        // set instance variables to null
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param team
     *            The team to be created
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     */
    public void createTeam(TeamHeader team, long userId) {
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param team
     *            The team to be updated
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     */
    public void updateTeam(TeamHeader team, long userId) {
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param teamId
     *            The ID of the team to be removed
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     */
    public void removeTeam(long teamId, long userId) {
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return Team with the given ID, or null if none found
     * @param teamId
     *            The ID of the team to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     */
    public Team getTeam(long teamId) {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     */
    public TeamHeader[] findTeams(long projectId) {
        return new TeamHeader[] {new TeamHeader("Team1", true, 1, 1, 1, 80, "Test") };
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectIds
     *            The IDs of the projects whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectIds is null or contains any negative IDs
     */
    public TeamHeader[] findTeams(long[] projectIds) {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param filter
     *            The filter criteria to match teams
     * @throws IllegalArgumentException
     *             If filter is null
     */
    public TeamHeader[] findTeams(Filter filter) {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return Team that contains the position with the given ID
     * @param positionId
     *            The ID of the position whose team is to be retrieved
     * @throws IllegalArgumentException
     *             If positionId is negative
     */
    public Team getTeamFromPosition(long positionId) {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param teamId
     *            The ID of the team to which the position is to be added
     * @param position
     *            TeamPosition to add to the team with the given teamId
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, or teamId or userId is negative
     */
    public void addPosition(long teamId, TeamPosition position, long userId) {
        // do nothing
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param position
     *            TeamPosition to update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, userId is negative
     */
    public void updatePosition(TeamPosition position, long userId) {
        // do nothing
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     */
    public void removePosition(long positionId, long userId) {
        // do nothing
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return TeamPosition with the given ID, or null if none found
     * @param positionId
     *            The ID of the position to get
     * @throws IllegalArgumentException
     *             If positionId is negative
     */
    public TeamPosition getPosition(long positionId) {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return An array of matching TeamPosition, or empty if no matches found
     * @param filter
     *            The filter criteria to match positions
     * @throws IllegalArgumentException
     *             If filter is null
     */
    public TeamPosition[] findPositions(Filter filter) {
        return null;
    }

}
