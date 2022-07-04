package com.topcoder.direct.services.copilot.stresstests;

import java.util.Date;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;

public class MockUtilityDAO implements UtilityDAO {

    public int getContestBugCount(long contestId) throws CopilotDAOException {
        return 0;
    }

    public Date getContestLatestBugResolutionDate(long contestId) throws CopilotDAOException {
        return null;
    }

    public double getCopilotEarnings(long userId) throws CopilotDAOException {
        return 0;
    }

    public long[] getCopilotProjectContests(long copilotUserId, long tcDirectProjectId) throws CopilotDAOException {
        return null;
    }

}
