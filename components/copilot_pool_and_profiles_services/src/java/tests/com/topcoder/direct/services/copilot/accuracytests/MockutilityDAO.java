/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;

import java.util.Date;


/**
 * Mock for testing!
 *
 * @author onsky
 * @version 1.0
  */
public class MockutilityDAO implements UtilityDAO {
    /**
     * Mock for testing!
     *
     * @param contestId Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public int getContestBugCount(long contestId) throws CopilotDAOException {
        return 0;
    }

    /**
     * Mock for testing!
     *
     * @param contestId Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public Date getContestLatestBugResolutionDate(long contestId)
        throws CopilotDAOException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param userId Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public double getCopilotEarnings(long userId) throws CopilotDAOException {
        return 0;
    }

    /**
     * Mock for testing!
     *
     * @param copilotUserId Mock for testing!
     * @param tcDirectProjectId Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public long[] getCopilotProjectContests(long copilotUserId, long tcDirectProjectId)
        throws CopilotDAOException {
        return new long[] { 1, 2 };
    }
}
