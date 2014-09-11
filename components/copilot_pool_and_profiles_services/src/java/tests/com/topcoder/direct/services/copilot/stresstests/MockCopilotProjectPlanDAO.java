package com.topcoder.direct.services.copilot.stresstests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;

public class MockCopilotProjectPlanDAO implements CopilotProjectPlanDAO {

    public CopilotProjectPlan getCopilotProjectPlan(long copilotProjectId) throws CopilotDAOException {
        try {
            TimeUnit.MILLISECONDS.sleep(15);
        } catch (InterruptedException e) {
            throw new CopilotDAOException("sleep error", e);
        }
        return null;
    }

    public long create(CopilotProjectPlan entity) throws CopilotDAOException {

        return 0;
    }

    public void delete(long entityId) throws CopilotDAOException {

    }

    public CopilotProjectPlan retrieve(long entityId) throws CopilotDAOException {

        return null;
    }

    public List<CopilotProjectPlan> retrieveAll() throws CopilotDAOException {

        return null;
    }

    public void update(CopilotProjectPlan entity) throws CopilotDAOException {

    }

}
