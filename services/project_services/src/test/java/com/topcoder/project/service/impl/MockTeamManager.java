/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.impl;

import com.topcoder.management.team.InvalidPositionException;
import com.topcoder.management.team.InvalidTeamException;
import com.topcoder.management.team.PositionRemovalException;
import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.UnknownEntityException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of <code>TeamManager</code>.
 * </p>
 * <p>
 * It is just used for tests.
 * </p>
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
     * @param team
     *            The team to be created
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws InvalidTeamException
     *             If the team contains invalid data
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void createTeam(TeamHeader team, long userId) throws InvalidTeamException {
    }

    /**
     * Updates the team.
     * @param team
     *            The team to be updated
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws InvalidTeamException
     *             If the team contains invalid data
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void updateTeam(TeamHeader team, long userId) throws InvalidTeamException {
    }

    /**
     * Removes the team and all positions associated with it.
     * @param teamId
     *            The ID of the team to be removed
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws UnknownEntityException
     *             If teamId is unknown
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void removeTeam(long teamId, long userId) throws UnknownEntityException {
    }

    /**
     * Retrieves the team and all associated positions. Returns null if it does not exist.
     * @return Team with the given ID, or null if none found
     * @param teamId
     *            The ID of the team to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public Team getTeam(long teamId) {
        return null;
    }

    /**
     * Finds all teams associated with the project with the given ID. Returns empty array if none
     * found.
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamHeader[] findTeams(long projectId) {
        TeamHeader team = new TeamHeader();
        team.setTeamId(1);
        return new TeamHeader[] {team};
    }

    /**
     * Finds all teams associated with the projects with the given IDs. Returns empty array if none
     * found.
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectIds
     *            The IDs of the projects whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectIds is null or contains any negative IDs
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamHeader[] findTeams(long[] projectIds) {
        TeamHeader team = new TeamHeader();
        team.setTeamId(1);
        return new TeamHeader[] {team};
    }

    /**
     * Finds all teams that match the criteria in the passed filter. Returns empty array if none
     * found.
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param filter
     *            The filter criteria to match teams
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamHeader[] findTeams(Filter filter) {
        return null;
    }

    /**
     * Gets the team, and all its positions, that the position with the given ID belongs to. Returns
     * null if it does not exist.
     * @return Team that contains the position with the given ID
     * @param positionId
     *            The ID of the position whose team is to be retrieved
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public Team getTeamFromPosition(long positionId) {
        return null;
    }

    /**
     * Adds a position to the existing team with the given teamID.
     * @param teamId
     *            The ID of the team to which the position is to be added
     * @param position
     *            TeamPosition to add to the team with the given teamId
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, or teamId or userId is negative
     * @throws InvalidPositionException
     *             If the position contains invalid data
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void addPosition(long teamId, TeamPosition position, long userId)
        throws InvalidPositionException {
    }

    /**
     * Updates a position.
     * @param position
     *            TeamPosition to update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, userId is negative
     * @throws InvalidPositionException
     *             If the position contains invalid data
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void updatePosition(TeamPosition position, long userId) throws InvalidPositionException {

    }

    /**
     * Removes a position
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws PositionRemovalException
     *             if the position is already published or filled, or the team is finalized.
     * @throws UnknownEntityException
     *             If positionID is unknown
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void removePosition(long positionId, long userId) throws UnknownEntityException,
        PositionRemovalException {
    }

    /**
     * Retrieves the position with the given ID. Returns null if it does not exist.
     * @return TeamPosition with the given ID, or null if none found
     * @param positionId
     *            The ID of the position to get
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamPosition getPosition(long positionId) {
        return null;
    }

    /**
     * Finds all positions that match the criteria in the passed filter. Returns empty array if none
     * found.
     * @param filter
     *            The filter criteria to match positions
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     * @return An array of matching TeamPosition, or empty if no matches found
     */
    public TeamPosition[] findPositions(Filter filter) {
        return null;
    }
}
