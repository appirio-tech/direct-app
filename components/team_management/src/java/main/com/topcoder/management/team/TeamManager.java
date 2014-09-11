/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This represents the interface that defines all business methods for managing a team. This includes CRUD operations
 * for teams and positions in those teams, as well as broader search capabilities using filters. Specifically, one can
 * create, update a team, as long as the data in it is valid, and delete a team. The team and its positions can be
 * retrieved by either the team identifier, or by any position identifier in that team. Once can search for teams in
 * the projects they exist, or by more robust use of Filters from the Search Builder 1.3 component. Similarly, one can
 * add and update positions in a team, again, as long as the data is valid. The position can be also removed as long
 * as it is legal to do so. The position can be retrieved by its identifier, or through using Filters, just like
 * teams.
 * </p>
 * <p>
 * The implementations will provide specifics as to what constitutes a valid team and position, or a legal removal of
 * a position. They will also provide IDs when entities are created.
 * </p>
 * <p>
 * There is one implementation provided with this version release: TeamManagerImpl.
 * </p>
 * <p>
 * Thread safety: There are no threading restrictions put on implementations.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface TeamManager {
    /**
     * Creates the team. A new team id will be appended to this team.
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
    public void createTeam(TeamHeader team, long userId) throws InvalidTeamException, TeamPersistenceException;

    /**
     * Updates the team. The team id must be present before this update.
     * @param team
     *            The team to be updated
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws InvalidTeamException
     *             If the team contains invalid data
     * @throws UnknownEntityException
     *             If teamId is unknown
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void updateTeam(TeamHeader team, long userId) throws InvalidTeamException, UnknownEntityException,
        TeamPersistenceException;

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
    public void removeTeam(long teamId, long userId) throws UnknownEntityException, TeamPersistenceException;

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
    public Team getTeam(long teamId) throws TeamPersistenceException;

    /**
     * Finds all teams associated with the project with the given ID. Returns empty array if none found.
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamHeader[] findTeams(long projectId) throws TeamPersistenceException;

    /**
     * Finds all teams associated with the projects with the given IDs. Returns empty array if none found.
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectIds
     *            The IDs of the projects whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectIds is null or contains any negative IDs
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamHeader[] findTeams(long[] projectIds) throws TeamPersistenceException;

    /**
     * Finds all teams that match the criteria in the passed filter. Returns empty array if none found.
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param filter
     *            The filter criteria to match teams
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamHeader[] findTeams(Filter filter) throws TeamPersistenceException;

    /**
     * Gets the team, and all its positions, that the position with the given ID belongs to. Returns null if it does
     * not exist.
     * @return Team that contains the position with the given ID
     * @param positionId
     *            The ID of the position whose team is to be retrieved
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public Team getTeamFromPosition(long positionId) throws TeamPersistenceException;

    /**
     * Adds a position to the existing team with the given teamID. A new position id will be appended to this position.
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
     * @throws UnknownEntityException
     *             If position's teamId is unknown
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void addPosition(long teamId, TeamPosition position, long userId) throws InvalidPositionException,
        TeamPersistenceException, UnknownEntityException;

    /**
     * Updates a position. The position id must be present before this update.
     * @param position
     *            TeamPosition to update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, userId is negative
     * @throws InvalidPositionException
     *             If the position contains invalid data
     * @throws UnknownEntityException
     *             If positionID is unknown
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void updatePosition(TeamPosition position, long userId) throws InvalidPositionException,
        UnknownEntityException, TeamPersistenceException;

    /**
     * Removes a position. The position must be present, not published and not filled before the remove. And the team
     * must not be finalized.
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws PositionRemovalException
     *             if the position is already published or filled, ot the team is finalized.
     * @throws UnknownEntityException
     *             If positionID is unknown
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void removePosition(long positionId, long userId) throws PositionRemovalException, UnknownEntityException,
        TeamPersistenceException;

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
    public TeamPosition getPosition(long positionId) throws TeamPersistenceException;

    /**
     * Finds all positions that match the criteria in the passed filter. Returns empty array if none found.
     * @return An array of matching TeamPosition, or empty if no matches found
     * @param filter
     *            The filter criteria to match positions
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamPosition[] findPositions(Filter filter) throws TeamPersistenceException;
}
