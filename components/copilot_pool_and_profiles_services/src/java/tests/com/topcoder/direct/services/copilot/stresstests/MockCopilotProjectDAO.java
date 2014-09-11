package com.topcoder.direct.services.copilot.stresstests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.model.CopilotProject;

public class MockCopilotProjectDAO implements CopilotProjectDAO {

    public List<CopilotProject> getCopilotProjects(long copilotProfileId) throws CopilotDAOException {
        try {
            TimeUnit.MILLISECONDS.sleep(15);
        } catch (InterruptedException e) {
            throw new CopilotDAOException("sleep error", e);
        }
        return new ArrayList<CopilotProject>();
    }

    public long create(CopilotProject entity) throws CopilotDAOException {

        return 0;
    }

    public void delete(long entityId) throws CopilotDAOException {

    }

    public CopilotProject retrieve(long entityId) throws CopilotDAOException {

        return null;
    }

    public List<CopilotProject> retrieveAll() throws CopilotDAOException {

        return null;
    }

    public void update(CopilotProject entity) throws CopilotDAOException {

    }

}
