package com.topcoder.direct.services.copilot.stresstests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;

public class MockCopilotProfileDAO implements CopilotProfileDAO {

    public CopilotProfile getCopilotProfile(long userId) throws CopilotDAOException {
        try {
            TimeUnit.MILLISECONDS.sleep(15);
        } catch (InterruptedException e) {
            throw new CopilotDAOException("sleep error", e);
        }
        return null;
    }

    public long create(CopilotProfile entity) throws CopilotDAOException {

        return 0;
    }

    public void delete(long entityId) throws CopilotDAOException {

    }

    public CopilotProfile retrieve(long entityId) throws CopilotDAOException {
        try {
            TimeUnit.MILLISECONDS.sleep(15);
        } catch (InterruptedException e) {
            throw new CopilotDAOException("sleep error", e);
        }
        return null;
    }

    public List<CopilotProfile> retrieveAll() throws CopilotDAOException {
        try {
            TimeUnit.MILLISECONDS.sleep(15);
        } catch (InterruptedException e) {
            throw new CopilotDAOException("sleep error", e);
        }
        return new ArrayList<CopilotProfile>();
    }

    public void update(CopilotProfile entity) throws CopilotDAOException {

    }

}
