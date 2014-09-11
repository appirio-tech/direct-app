/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao;

import java.util.Date;

/**
 * <p>This interface represents a helper utility DAO. It provides methods for retrieving the number of bug races for
 * specific contest, the latest bug resolution date for specific contest, the earnings of specific copilot user and
 * contest IDs for specific copilot user and TC direct project.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface UtilityDAO {

    /**
     * <p>Retrieves the number of bugs for contest with the specified ID. Returns 0 if contest with the given ID doesn't
     * exist.</p>
     *
     * @param contestId the ID of the contest
     *
     * @return the retrieved contest bug count (not negative)
     *
     * @throws IllegalArgumentException if contestId <= 0
     * @throws CopilotDAOException      if any error occurs during persistence operation
     */
    int getContestBugCount(long contestId) throws CopilotDAOException;

    /**
     * <p>Retrieves the earnings of the copilot with the specified user ID.</p>
     *
     * @param userId the ID of the copilot user
     *
     * @return the retrieved copilot earnings (not negative)
     *
     * @throws IllegalArgumentException if userId <= 0
     * @throws CopilotDAOException      if any error occurs during persistence operation
     */
    double getCopilotEarnings(long userId) throws CopilotDAOException;

    /**
     * <p>Retrieves the latest bug resolution date for the contest. Returns null if contest with the given ID doesn't
     * exist.</p>
     *
     * @param contestId the ID of the contest
     *
     * @return the latest bug resolution date for the contest or null if the contest has no bugs
     *
     * @throws IllegalArgumentException if contestId <= 0
     * @throws CopilotDAOException      if any error occurs during persistence operation
     */
    Date getContestLatestBugResolutionDate(long contestId) throws CopilotDAOException;

    /**
     * <p>Retrieves IDs of all contests for the specified copilot user and TC direct project. Returns an empty array if
     * copilot user or TC direct project with the specified ID doesn't exist.</p>
     *
     * @param copilotUserId     the ID of the copilot user
     * @param tcDirectProjectId the ID of the TC direct project
     *
     * @return the retrieved IDs of copilot project contests (not null)
     *
     * @throws IllegalArgumentException if copilotUserId <= 0 or tcDirectProjectId <= 0
     * @throws CopilotDAOException      if any error occurs during persistence operation
     */
    long[] getCopilotProjectContests(long copilotUserId, long tcDirectProjectId) throws CopilotDAOException;
}

