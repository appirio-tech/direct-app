/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.team.accuracytests;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPersistence;
import com.topcoder.management.team.TeamPosition;

import com.topcoder.search.builder.filter.Filter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A mock implementation of the <code>TeamPersistence</code> interface used by the accuracy tests.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class MockTeamPersistence implements TeamPersistence {
    /**
     * Holds a trace of all calls made to this instance.
     */
    private final List calls = new ArrayList();

    /**
     * Holds a list of all values to return.
     */
    private final LinkedList returnValues = new LinkedList();

    /**
     * Retrieves a trace of all calls made to methods of this instance.
     *
     * @return a trace of all calls made to methods of this instance
     */
    List getCalls() {
        return calls;
    }

    /**
     * Adds a value to the list of values to be returned.
     */
    void addReturnValue(Object value) {
        returnValues.add(value);
    }

    /**
     * Creates the team.
     *
     * @param team The team to be created
     * @param userId The user Id for logging and auditing purposes
     *
     * @throws IllegalArgumentException If team is null or userId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public void createTeam(TeamHeader team, long userId) {
        calls.add("createTeam");
        calls.add(team);
        calls.add(new Long(userId));
    }

    /**
     * Updates the team.
     *
     * @param team The team to be updated
     * @param userId The user Id for logging and auditing purposes
     *
     * @throws IllegalArgumentException If team is null or userId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public void updateTeam(TeamHeader team, long userId) {
        calls.add("updateTeam");
        calls.add(team);
        calls.add(new Long(userId));
    }

    /**
     * Removes the team and all positions associated with it.
     *
     * @param teamId The ID of the team to be removed
     * @param userId The user Id for logging and auditing purposes
     *
     * @throws IllegalArgumentException If teamId or userId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public void removeTeam(long teamId, long userId) {
        calls.add("removeTeam");
        calls.add(new Long(teamId));
        calls.add(new Long(userId));
    }

    /**
     * Retrieves the team and all associated positions. Returns null if it does not exist.
     *
     * @param teamId The ID of the team to be retrieved
     *
     * @return Team with the given ID, or null if none found
     *
     * @throws IllegalArgumentException If teamId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public Team getTeam(long teamId) {
        calls.add("getTeam");
        calls.add(new Long(teamId));
        return (Team) returnValues.removeFirst();
    }

    /**
     * Finds all teams associated with the project with the given ID. Returns empty array if none found.
     *
     * @param projectId The ID of the project whose teams are to be retrieved
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     *
     * @throws IllegalArgumentException If projectId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public TeamHeader[] findTeams(long projectId) {
        calls.add("findTeams");
        calls.add(new Long(projectId));
        return (TeamHeader[]) returnValues.removeFirst();
    }

    /**
     * Finds all teams associated with the projects with the given IDs. Returns empty array if none found.
     *
     * @param projectIds The IDs of the projects whose teams are to be retrieved
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     *
     * @throws IllegalArgumentException If projectIds is null or contains any negative IDs
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public TeamHeader[] findTeams(long[] projectIds) {
        calls.add("findTeams");
        calls.add(projectIds);
        return (TeamHeader[]) returnValues.removeFirst();
    }

    /**
     * Finds all teams that match the criteria in the passed filter. Returns empty array if none found.
     *
     * @param filter The filter criteria to match teams
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     *
     * @throws IllegalArgumentException If filter is null
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public TeamHeader[] findTeams(Filter filter) {
        calls.add("findTeams");
        calls.add(filter);
        return (TeamHeader[]) returnValues.removeFirst();
    }

    /**
     * Gets the team, and all its positions, that the position with the given ID belongs to. Returns null if it
     * does not exist.
     *
     * @param positionId The ID of the position whose team is to be retrieved
     *
     * @return Team that contains the position with the given ID
     *
     * @throws IllegalArgumentException If positionId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public Team getTeamFromPosition(long positionId) {
        calls.add("getTeamFromPosition");
        calls.add(new Long(positionId));
        Object o = returnValues.removeFirst();
        while (!(o instanceof Team)) {
            o = returnValues.removeFirst();
        }

        return (Team) o;
    }

    /**
     * Adds a position to the existing team with the given teamId.
     *
     * @param teamId The ID of the team to which the position is to be added
     * @param position TeamPosition to add to the team with the given teamId
     * @param userId The user Id for logging and auditing purposes
     *
     * @throws IllegalArgumentException If position is null, or teamId or userId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public void addPosition(long teamId, TeamPosition position, long userId) {
        calls.add("addPosition");
        calls.add(new Long(teamId));
        calls.add(position);
        calls.add(new Long(userId));
    }

    /**
     * Updates a position.
     *
     * @param position TeamPosition to update
     * @param userId The user Id for logging and auditing purposes
     *
     * @throws IllegalArgumentException If position is null, userId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public void updatePosition(TeamPosition position, long userId) {
        calls.add("updatePosition");
        calls.add(position);
        calls.add(new Long(userId));
    }

    /**
     * Removes a position.
     *
     * @param positionId The ID of the position to remove
     * @param userId The user Id for logging and auditing purposes
     *
     * @throws IllegalArgumentException If positionId or userId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public void removePosition(long positionId, long userId) {
        calls.add("removePosition");
        calls.add(new Long(positionId));
        calls.add(new Long(userId));
    }

    /**
     * Retrieves the position with the given ID. Returns null if it does not exist.
     *
     * @param positionId The ID of the position to get
     *
     * @return TeamPosition with the given ID, or null if none found
     *
     * @throws IllegalArgumentException If positionId is negative
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public TeamPosition getPosition(long positionId) {
        calls.add("getPosition");
        calls.add(new Long(positionId));

        return (TeamPosition) returnValues.removeFirst();
    }

    /**
     * Finds all positions that match the criteria in the passed filter. Returns empty array if none found.
     *
     * @param filter The filter criteria to match positions
     *
     * @return An array of matching TeamPosition, or empty if no matches found
     *
     * @throws IllegalArgumentException If filter is null
     * @throws TeamPersistenceException If any system error, such as connection trouble, occurs
     */
    public TeamPosition[] findPositions(Filter filter) {
        calls.add("findPositions");
        calls.add(filter);

        return (TeamPosition[]) returnValues.removeFirst();
    }
}
