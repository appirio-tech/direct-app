package com.topcoder.direct.services.copilot.stresstests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;

public class MockLookupDAO implements LookupDAO {

    public List<CopilotProfileStatus> getAllCopilotProfileStatuses() throws CopilotDAOException {
        // sleep 20ms to mock dao delay
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new CopilotDAOException("sleep error", e);
        }
        return null;
    }

    public List<CopilotProjectStatus> getAllCopilotProjectStatuses() throws CopilotDAOException {
        // sleep 20ms to
        // mock dao delay
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new CopilotDAOException("sleep error", e);
        }
        return null;
    }

    public List<CopilotType> getAllCopilotTypes() throws CopilotDAOException { // sleep 20ms to mock dao delay
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new CopilotDAOException("sleep error", e);
        }
        return null;
    }

}
