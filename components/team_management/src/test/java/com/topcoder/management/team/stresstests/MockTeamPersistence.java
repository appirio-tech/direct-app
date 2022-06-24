/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 *
 * TCS Project Team Management 1.0 (Unit Tests)
 *
 * @ StubTeamPersistence.java
 */
package com.topcoder.management.team.stresstests;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPersistence;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.impl.TeamImpl;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A stub class implements TeamPersistence used in tests. It implements all the basic methods of TeamPersistence
 * using HashMap to store teams and positions, except the auditing and searching with filters (as stated in the test
 * classes of the three filter factories, the usages of Filter need many components that have nothing to do with the
 * manager scope of this component to be set up), and will not check the arguments (which is the responsibility of
 * TeamManager to be tested). For filter search methods, empty array will be returned and the filter will be saved and
 * can be retrieved by the test case, the userId for auditing purpose will also be saved.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTeamPersistence implements TeamPersistence {
    /**
     * Empty constructor.
     */
    public MockTeamPersistence() {
    }

    /**
     * Adds a position to the existing team with the given teamId. Put position to positions, put its id to the
     * positions list of team id and put the team id to belongedTeams of it, save the user id.
     *
     * @param teamId The ID of the team to which the position is to be added
     * @param position TeamPosition to add to the team with the given teamId
     * @param userId The user Id for logging and auditing purposes
     *
     * @see TeamPersistence#addPosition(long, TeamPosition, long)
     */
    public void addPosition(long teamId, TeamPosition position, long userId) {
        // empty
    }

    /**
     * Creates the team. Adds the team to headers, add teamId to participants with key projectId, and add an
     * empty ArrayList with key teamId to containedPositions. Saves the userId.
     *
     * @param team The team to be created
     * @param userId The user Id for logging and auditing purposes
     *
     * @see TeamPersistence#createTeam(TeamHeader, long)
     */
    public void createTeam(TeamHeader team, long userId) {
        // empty
    }

    /**
     * Finds all positions that match the criteria in the passed filter. Returns empty array if none found.
     * Only save the filter and return an empty array.
     *
     * @param filter The filter criteria to match positions
     *
     * @return An array of matching TeamPosition, or empty if no matches found
     *
     * @see TeamPersistence#findPositions(Filter)
     */
    public TeamPosition[] findPositions(Filter filter) {
        return new TeamPosition[0];
    }

    /**
     * Finds all teams associated with the project with the given ID. Returns empty array if none found. Get
     * the participant list from participants and get the TeamHeaders from headers.
     *
     * @param projectId The ID of the project whose teams are to be retrieved
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     *
     * @see TeamPersistence#findTeams(long)
     */
    public TeamHeader[] findTeams(long projectId) {
        return new TeamHeader[0];
    }

    /**
     * Finds all teams associated with the projects with the given IDs. Returns empty array if none found. Get
     * all participant lists from participants and get the TeamHeaders from headers.
     *
     * @param projectIds The IDs of the projects whose teams are to be retrieved
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     *
     * @see TeamPersistence#findTeams(long[])
     */
    public TeamHeader[] findTeams(long[] projectIds) {
        return new TeamHeader[0];
    }

    /**
     * Finds all teams that match the criteria in the passed filter. Returns empty array if none found. Only
     * save the filter and return an empty array.
     *
     * @param filter The filter criteria to match teams
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     *
     * @see TeamPersistence#findTeams(Filter)
     */
    public TeamHeader[] findTeams(Filter filter) {
        return new TeamHeader[0];
    }

    /**
     * Retrieves the position with the given ID. Returns null if it does not exist.
     *
     * @param positionId The ID of the position to get
     *
     * @return TeamPosition with the given ID, or null if none found
     *
     * @see TeamPersistence#getPosition(long)
     */
    public TeamPosition getPosition(long positionId) {
        TeamPosition position = new TeamPosition();
        position.setPositionId(1);
        position.setName("position" + positionId);
        position.setDescription("description");
        position.setPaymentPercentage(20);

        return position;
    }

    /**
     * Retrieves the team and all associated positions. Returns null if it does not exist. Get the team header
     * and all its positions, return a new TeamImpl with them.
     *
     * @param teamId The ID of the team to be retrieved
     *
     * @return Team with the given ID, or null if none found
     *
     * @see TeamPersistence#getTeam(long)
     */
    public Team getTeam(long teamId) {
        Team team = new TeamImpl();

        TeamHeader header = new TeamHeader();
        header.setName("TeamManager");
        header.setDescription("description");
        header.setProjectId(1);
        header.setCaptainResourceId(1);
        header.setCaptainPaymentPercentage(10);

        TeamPosition position = new TeamPosition();
        position.setPositionId(1);
        position.setName("new position");
        position.setDescription("description");
        position.setPaymentPercentage(20);

        team.setTeamHeader(header);
        team.setPositions(new TeamPosition[] {position});

        return team;
    }

    /**
     * Gets the team, and all its positions, that the position with the given ID belongs to. Returns null if it
     * does not exist. Get the team id and call getTeam();
     *
     * @param positionId The ID of the position whose team is to be retrieved
     *
     * @return Team that contains the position with the given ID
     *
     * @see TeamPersistence#getTeamFromPosition(long)
     */
    public Team getTeamFromPosition(long positionId) {
        Team team = new TeamImpl();

        TeamHeader header = new TeamHeader();
        header.setName("TeamManager");
        header.setDescription("description");
        header.setProjectId(1);
        header.setCaptainResourceId(1);
        header.setCaptainPaymentPercentage(10);

        TeamPosition position = new TeamPosition();
        position.setPositionId(positionId);
        position.setName("position");
        position.setDescription("description");
        position.setPaymentPercentage(20);

        team.setTeamHeader(header);
        team.setPositions(new TeamPosition[] {position});

        return team;
    }

    /**
     * Removes a position. Remove it from containedPositions's list, belongedTeams and positions, save the
     * userId.
     *
     * @param positionId The ID of the position to remove
     * @param userId The user Id for logging and auditing purposes
     *
     * @see TeamPersistence#removePosition(long, long)
     */
    public void removePosition(long positionId, long userId) {
        // empty
    }

    /**
     * Removes the team and all positions associated with it. Get all positions with that team, remove them
     * from positions and belongedTeams, remove the team from participants, containedPositions and headers, save
     * userId.
     *
     * @param teamId The ID of the team to be removed
     * @param userId The user Id for logging and auditing purposes
     *
     * @see TeamPersistence#removeTeam(long, long)
     */
    public void removeTeam(long teamId, long userId) {
        // empty
    }

    /**
     * Updates a position. Simply put it to positions and save userId.
     *
     * @param position TeamPosition to update
     * @param userId The user Id for logging and auditing purposes
     *
     * @see TeamPersistence#updatePosition(TeamPosition, long)
     */
    public void updatePosition(TeamPosition position, long userId) {
        // empty
    }

    /**
     * Updates the team. Retrieve the old team and check whether the project id is different, remove it from
     * the old project's participants and add it to the new one's if different. Then put the new team into headers,
     * save the userId.
     *
     * @param team The team to be updated
     * @param userId The user Id for logging and auditing purposes
     *
     * @see TeamPersistence#updateTeam(TeamHeader, long)
     */
    public void updateTeam(TeamHeader team, long userId) {
        // empty
    }

    /**
     * Getter of lastPositionFilter, also reset the field to null.
     *
     * @return the lastPositionFilter
     */
    public Filter getLastPositionFilter() {
        return new NullFilter(null);
    }

    /**
     * Getter of lastTeamFilter, also reset the field to null.
     *
     * @return the lastTeamFilter
     */
    public Filter getLastTeamFilter() {
        return new NullFilter(null);
    }

    /**
     * Getter of lastUserId, also reset the field to -1.
     *
     * @return the lastUserId
     */
    public long getLastUserId() {
        return 1;
    }
}
