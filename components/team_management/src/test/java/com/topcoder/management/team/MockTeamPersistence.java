/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * A mock persistence for TeamPersistence interface. In this implementation method invokes will be recorded, and can
 * be retrieved to verify the correctness. Return values for methods can also be specified for unit tests.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTeamPersistence implements TeamPersistence {
    /** An array list to record the method invokes. */
    private List methodInvokes = new ArrayList();

    /** The Team instance to be returned by this mock class. */
    private Team team = null;

    /** The TeamHeader instance to be returned by this mock class. */
    private TeamHeader header = null;

    /** The TeamPosition instance to be returned by this mock class. */
    private TeamPosition position = null;

    /**
     * Prepare the number of method invokings before this class is finally forced to throw TeamPersistenceException
     * for all invokes. Negative if never throw.
     */
    private int throwException = -1;

    /**
     * Empty constructor.
     */
    public MockTeamPersistence() {
        // Does nothing
    }

    /**
     * Return the (i)th method invoke.
     * @param i
     *            the index of the method invoke to be returned
     * @return an array for a single method invoke. Its first element is a String of the method name; while the rest
     *         are the parameters recorded.
     */
    public Object[] getMethodInvoke(int i) {
        return (Object[]) methodInvokes.get(i);
    }

    /**
     * Set the team to be returned in this class.
     * @param team
     *            the given team
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Set the team header to be returned in this class.
     * @param header
     *            the given header
     */
    public void setHeader(TeamHeader header) {
        this.header = header;
    }

    /**
     * Set the team position to be returned in this class.
     * @param position
     *            the given position
     */
    public void setPosition(TeamPosition position) {
        this.position = position;
    }

    /**
     * To set the number of method invokings until this mock class is finally forced to throw
     * TeamPersistenceException.
     * @param count
     *            the number of method invokings
     */
    public void setThrowException(int count) {
        throwException = count;
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param team
     *            the given team
     * @param userId
     *            the given userId
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws TeamPersistenceException
     *             if throwException is set to true (used for testing purpose)
     */
    public void createTeam(TeamHeader team, long userId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        methodInvokes.add(new Object[] {"createTeam", team, new Long(userId)});
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param team
     *            the given team
     * @param userId
     *            the given userId
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public void updateTeam(TeamHeader team, long userId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        methodInvokes.add(new Object[] {"updateTeam", team, new Long(userId)});
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param teamId
     *            the given teamId
     * @param userId
     *            the given userId
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public void removeTeam(long teamId, long userId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        methodInvokes.add(new Object[] {"removeTeam", new Long(teamId), new Long(userId)});
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param teamId
     *            the given teamId
     * @return the specified return value in this mock class.
     * @throws IllegalArgumentException
     *             if teamId is negative
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public Team getTeam(long teamId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        Helper.assertNonNegative(teamId, "teamId");
        methodInvokes.add(new Object[] {"getTeam", new Long(teamId)});
        return team;
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param projectId
     *            the given projectId
     * @return the specified return value in this mock class.
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public TeamHeader[] findTeams(long projectId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        Helper.assertNonNegative(projectId, "projectId");
        // Prepare some sample return values
        methodInvokes.add(new Object[] {"findTeams(long)", new Long(projectId)});
        if (header != null) {
            return new TeamHeader[] {header};
        } else {
            return new TeamHeader[0];
        }
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param projectIds
     *            the given projectIds
     * @return the specified return value in this mock class.
     * @throws IllegalArgumentException
     *             If projectIds is null or contains any negative IDs
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public TeamHeader[] findTeams(long[] projectIds) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        // Check IAEs
        Helper.assertObjectNotNull(projectIds, "projectIds");
        for (int i = 0; i < projectIds.length; i++) {
            Helper.assertNonNegative(projectIds[i], "projectIds");
        }
        // Prepare some sample return values
        methodInvokes.add(new Object[] {"findTeams(long[])", projectIds});
        if (header != null) {
            return new TeamHeader[] {header};
        } else {
            return new TeamHeader[0];
        }
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param filter
     *            the given filter
     * @return the specified return value in this mock class.
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public TeamHeader[] findTeams(Filter filter) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        Helper.assertObjectNotNull(filter, "filter");
        methodInvokes.add(new Object[] {"findTeams(Filter)", filter});
        if (header != null) {
            return new TeamHeader[] {header};
        } else {
            return new TeamHeader[0];
        }
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param positionId
     *            the given positionId
     * @return the specified return value in this mock class.
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public Team getTeamFromPosition(long positionId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        Helper.assertNonNegative(positionId, "positionId");
        methodInvokes.add(new Object[] {"getTeamFromPosition", new Long(positionId)});
        return team;
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param teamId
     *            the given teamId
     * @param position
     *            the given position
     * @param userId
     *            the given userId
     * @throws IllegalArgumentException
     *             If position is null, or teamId or userId is negative
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public void addPosition(long teamId, TeamPosition position, long userId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        methodInvokes.add(new Object[] {"addPosition", new Long(teamId), position, new Long(userId)});
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param position
     *            the given position
     * @param userId
     *            the given userId
     * @throws IllegalArgumentException
     *             If position is null, userId is negative
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public void updatePosition(TeamPosition position, long userId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        methodInvokes.add(new Object[] {"updatePosition", position, new Long(userId)});
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param positionId
     *            the given positionId
     * @param userId
     *            the given userId
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public void removePosition(long positionId, long userId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        methodInvokes.add(new Object[] {"removePosition", new Long(positionId), new Long(userId)});
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param positionId
     *            the given positionId
     * @return the specified return value in this mock class.
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public TeamPosition getPosition(long positionId) {
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        Helper.assertNonNegative(positionId, "positionId");
        methodInvokes.add(new Object[] {"getPosition", new Long(positionId)});
        return position;
    }

    /**
     * A mock version for this method. Parameters will be recorded.
     * @param filter
     *            the given filter
     * @return the specified return value in this mock class.
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             if (throwException--)==0 is set to true (used for testing purpose)
     */
    public TeamPosition[] findPositions(Filter filter) {
        // Check if forced to throw exception
        if ((throwException--) == 0) {
            throw new TeamPersistenceException("used for testing only");
        }
        Helper.assertObjectNotNull(filter, "filter");
        // Return some sample values
        methodInvokes.add(new Object[] {"findPositions", filter});
        if (position != null) {
            return new TeamPosition[] {position};
        } else {
            return new TeamPosition[0];
        }
    }
}
