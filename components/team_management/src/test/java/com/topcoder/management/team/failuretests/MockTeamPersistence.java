/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.failuretests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPersistence;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.impl.TeamImpl;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This mock class implements TeamPersistence interface, and it is used in the
 * test.
 * </p>
 *
 * @author King_Bette
 * @version 1.0
 */
public class MockTeamPersistence implements TeamPersistence {
    /**
     * key is team id, value is team.
     */
    private Map teams = new HashMap();

    /**
     * Represents a flag that indicated that if throws TeamPersistenceException
     * when calling any method.
     */
    private boolean throwException = false;

    /**
     * Creates the team.
     *
     * @param team
     *            The team to be created
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void createTeam(TeamHeader team, long userId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (userId < 0) {
            throw new IllegalArgumentException();
        }
        if (team == null) {
            throw new IllegalArgumentException();
        }
        teams.put(new Long(team.getTeamId()), new TeamImpl(team, null));
    }

    /**
     * Updates the team.
     *
     * @param team
     *            The team to be updated
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void updateTeam(TeamHeader team, long userId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (userId < 0) {
            throw new IllegalArgumentException();
        }
        if (team == null) {
            throw new IllegalArgumentException();
        }
        getTeam(team.getTeamId()).setTeamHeader(team);
    }

    /**
     * Removes the team and all positions associated with it.
     *
     * @param teamId
     *            The ID of the team to be removed
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void removeTeam(long teamId, long userId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (userId < 0) {
            throw new IllegalArgumentException();
        }
        if (teamId < 0) {
            throw new IllegalArgumentException();
        }
        teams.remove(new Long(teamId));
    }

    /**
     * Retrieves the team and all associated positions. Returns null if it does
     * not exist.
     *
     * @return Team with the given ID, or null if none found
     * @param teamId
     *            The ID of the team to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public Team getTeam(long teamId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (teamId < 0) {
            throw new IllegalArgumentException();
        }
        return (Team) teams.get(new Long(teamId));
    }

    /**
     * Finds all teams associated with the project with the given ID. Returns
     * empty array if none found.
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public TeamHeader[] findTeams(long projectId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        List result = new ArrayList();
        if (projectId < 0) {
            throw new IllegalArgumentException();
        }
        for (Iterator iter = teams.values().iterator(); iter.hasNext();) {
            TeamHeader teamHeader = ((Team) iter.next()).getTeamHeader();
            if (teamHeader.getProjectId() == projectId) {
                result.add(teamHeader);
            }
        }
        return (TeamHeader[]) result.toArray(new TeamHeader[result.size()]);
    }

    /**
     * Finds all teams associated with the projects with the given IDs. Returns
     * empty array if none found.
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectIds
     *            The IDs of the projects whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectIds is null or contains any negative IDs
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public TeamHeader[] findTeams(long[] projectIds) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (projectIds == null) {
            throw new IllegalArgumentException();
        }
        List result = new ArrayList();
        for (int i = 0; i < projectIds.length; i++) {
            if (projectIds[i] < 0) {
                throw new IllegalArgumentException();
            }
            for (Iterator iter = teams.values().iterator(); iter.hasNext();) {
                TeamHeader teamHeader = ((Team) iter.next()).getTeamHeader();
                if (teamHeader.getProjectId() == projectIds[i]) {
                    result.add(teamHeader);
                }
            }
        }
        return (TeamHeader[]) result.toArray(new TeamHeader[result.size()]);
    }

    /**
     * Finds all teams that match the criteria in the passed filter. Returns
     * empty array if none found.
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param filter
     *            The filter criteria to match teams
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public TeamHeader[] findTeams(Filter filter) {
        if (filter == null) {
            throw new IllegalArgumentException();
        }
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        return new TeamHeader[0];
    }

    /**
     * Gets the team, and all its positions, that the position with the given ID
     * belongs to. Returns null if it does not exist.
     *
     * @return Team that contains the position with the given ID
     * @param positionId
     *            The ID of the position whose team is to be retrieved
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public Team getTeamFromPosition(long positionId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (positionId < 0) {
            throw new IllegalArgumentException();
        }
        for (Iterator iter = teams.values().iterator(); iter.hasNext();) {
            Team team = (Team) iter.next();
            TeamPosition[] positions = team.getPositions();
            for (int i = 0; i < positions.length; i++) {
                if (positionId == positions[i].getPositionId()) {
                    return team;
                }
            }
        }
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
     * @throws IllegalArgumentException
     *             If position is null, or teamId or userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void addPosition(long teamId, TeamPosition position, long userId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (userId < 0) {
            throw new IllegalArgumentException();
        }
        if (teamId < 0) {
            throw new IllegalArgumentException();
        }
        if (position == null) {
            throw new IllegalArgumentException();
        }
        Team team = getTeam(teamId);
        List positions = new ArrayList(Arrays.asList(team.getPositions()));
        positions.add(position);
        team.setPositions((TeamPosition[]) positions.toArray(new TeamPosition[0]));
    }

    /**
     * Updates a position.
     *
     * @param position
     *            TeamPosition to update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void updatePosition(TeamPosition position, long userId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (userId < 0) {
            throw new IllegalArgumentException();
        }
        if (position == null) {
            throw new IllegalArgumentException();
        }
        for (Iterator iter = teams.values().iterator(); iter.hasNext();) {
            Team team = (Team) iter.next();
            TeamPosition[] positions = team.getPositions();
            for (int i = 0; i < positions.length; i++) {
                if (position.getPositionId() == positions[i].getPositionId()) {
                    positions[i] = position;
                }
            }
            team.setPositions(positions);
        }
    }

    /**
     * Removes a position.
     *
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public void removePosition(long positionId, long userId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (userId < 0) {
            throw new IllegalArgumentException();
        }
        if (positionId < 0) {
            throw new IllegalArgumentException();
        }
        for (Iterator iter = teams.values().iterator(); iter.hasNext();) {
            Team team = (Team) iter.next();
            TeamPosition[] positions = team.getPositions();
            List result = new ArrayList(Arrays.asList(positions));
            for (int i = 0; i < positions.length; i++) {
                if (positionId == positions[i].getPositionId()) {
                    result.remove(positions[i]);
                }
            }
            team.setPositions((TeamPosition[]) result.toArray(new TeamPosition[0]));
        }
    }

    /**
     * Retrieves the position with the given ID. Returns null if it does not
     * exist.
     *
     * @return TeamPosition with the given ID, or null if none found
     * @param positionId
     *            The ID of the position to get
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public TeamPosition getPosition(long positionId) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (positionId < 0) {
            throw new IllegalArgumentException();
        }
        for (Iterator iter = teams.values().iterator(); iter.hasNext();) {
            Team team = (Team) iter.next();
            TeamPosition[] positions = team.getPositions();
            for (int i = 0; i < positions.length; i++) {
                if (positionId == positions[i].getPositionId()) {
                    return positions[i];
                }
            }
        }
        return null;
    }

    /**
     * Finds all positions that match the criteria in the passed filter. Returns
     * empty array if none found.
     *
     * @return An array of matching TeamPosition, or empty if no matches found
     * @param filter
     *            The filter criteria to match positions
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    public TeamPosition[] findPositions(Filter filter) {
        if (throwException) {
            throw new TeamPersistenceException("test");
        }
        if (filter == null) {
            throw new IllegalArgumentException();
        }
        return new TeamPosition[0];
    }

    /**
     * Set to related field.
     *
     * @param throwException
     *            indicated that if throws TeamPersistenceException when calling
     *            any method.
     */
    public void setThrowException(boolean throwException) {
        this.throwException = throwException;
    }
}
