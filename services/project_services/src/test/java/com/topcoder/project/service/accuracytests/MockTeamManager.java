/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Mock implementation of ResourceManager used for accuracy test cases.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class MockTeamManager implements TeamManager {

    /**
     * Not implemented.
     *
     * @param arg0
     *            long parameter.
     * @param arg1
     *            TeamPosition parameter.
     * @param arg2
     *            long parameter.
     *
     */
    public void addPosition(long arg0, TeamPosition arg1, long arg2) {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            TeamHeader parameter.
     * @param arg1
     *            long parameter.
     *
     */
    public void createTeam(TeamHeader arg0, long arg1) {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Filter parameter.
     *
     * @return null always.
     */
    public TeamPosition[] findPositions(Filter arg0) {
        return null;
    }

    /**
     * Returns a sample array containing a single TeamHeader.
     *
     * @param arg0
     *            long parameter.
     *
     * @return a sample array containing a single TeamHeader.
     */
    public TeamHeader[] findTeams(long arg0) {
        return new TeamHeader[] { new TeamHeader() };
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Filter parameter.
     *
     * @return null always.
     */
    public TeamHeader[] findTeams(long[] arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Filter parameter.
     *
     * @return null always.
     */
    public TeamHeader[] findTeams(Filter arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            long parameter.
     *
     * @return null always.
     */
    public TeamPosition getPosition(long arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            long parameter.
     *
     * @return null always.
     */
    public Team getTeam(long arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            long parameter.
     *
     * @return null always.
     */
    public Team getTeamFromPosition(long arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            long parameter.
     * @param arg1
     *            long parameter.
     *
     */
    public void removePosition(long arg0, long arg1) {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            long parameter.
     * @param arg1
     *            long parameter.
     *
     */
    public void removeTeam(long arg0, long arg1) {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            TeamPosition parameter.
     * @param arg1
     *            long parameter.
     *
     */
    public void updatePosition(TeamPosition arg0, long arg1) {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            TeamHeader parameter.
     * @param arg1
     *            long parameter.
     *
     */
    public void updateTeam(TeamHeader arg0, long arg1) {

    }

}