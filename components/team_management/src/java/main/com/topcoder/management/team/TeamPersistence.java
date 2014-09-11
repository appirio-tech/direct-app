/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The interface that defines all business method for actual persistence of team data. It basically mirrors all
 * methods defined in the TeamManager interface except for introducing its runtime TeamPersistenceException to every
 * method. It is expected that TeamManager implementations will use it to perform actual persistence of the data.
 * </p>
 * <p>
 * TeamPersistence is expected to be only used in the context of the TeamManager implementations. As such,
 * implementations can rely on the manager to validate data and provide primary identifiers.
 * </p>
 * <p>
 * There is one implementation provided with this version release: InformixTeamPersistence.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface TeamPersistence {
    /**
     * Creates the team.
     * @param team
     *            The team to be created
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void createTeam(TeamHeader team, long userId) throws TeamPersistenceException;

    /**
     * Updates the team.
     * @param team
     *            The team to be updated
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void updateTeam(TeamHeader team, long userId) throws TeamPersistenceException;

    /**
     * Removes the team and all positions associated with it.
     * @param teamId
     *            The ID of the team to be removed
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void removeTeam(long teamId, long userId) throws TeamPersistenceException;

    /**
     * Retrieves the team and all associated positions. Returns null if it does not exist.
     * @return Team with the given ID, or null if none found
     * @param teamId
     *            The ID of the team to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
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
     *             If any system error, such as connection trouble, occurs
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
     *             If any system error, such as connection trouble, occurs
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
     *             If any system error, such as connection trouble, occurs
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
     *             If any system error, such as connection trouble, occurs
     */
    public Team getTeamFromPosition(long positionId) throws TeamPersistenceException;

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
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void addPosition(long teamId, TeamPosition position, long userId) throws TeamPersistenceException;

    /**
     * Updates a position.
     * @param position
     *            TeamPosition to update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void updatePosition(TeamPosition position, long userId) throws TeamPersistenceException;

    /**
     * Removes a position.
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void removePosition(long positionId, long userId) throws TeamPersistenceException;

    /**
     * Retrieves the position with the given ID. Returns null if it does not exist.
     * @return TeamPosition with the given ID, or null if none found
     * @param positionId
     *            The ID of the position to get
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
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
     *             If any system error, such as connection trouble, occurs
     */
    public TeamPosition[] findPositions(Filter filter) throws TeamPersistenceException;
}
