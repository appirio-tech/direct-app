/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dao.EntityNotFoundException;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * Mock for testing!
 *
 * @author onsky
 * @version 1.0
  */
public class MockCopilotProjectDAO implements CopilotProjectDAO {
    /**
     * Mock for testing!
     *
     * @param copilotProfileId Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public List<CopilotProject> getCopilotProjects(long copilotProfileId)
        throws CopilotDAOException {
        List<CopilotProject> result = new ArrayList<CopilotProject>();
        CopilotProject proj = new CopilotProject();
        CopilotProjectStatus status = new CopilotProjectStatus();
        status.setId(2L);
        proj.setStatus(status);
        result.add(proj);

        return result;
    }

    /**
     * Mock for testing!
     *
     * @param entity Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public long create(CopilotProject entity) throws CopilotDAOException {
        return 0;
    }

    /**
     * Mock for testing!
     *
     * @param entityId Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     * @throws EntityNotFoundException Mock for testing!
     */
    public void delete(long entityId) throws CopilotDAOException, EntityNotFoundException {
    }

    /**
     * Mock for testing!
     *
     * @param entityId Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public CopilotProject retrieve(long entityId) throws CopilotDAOException {
        return new CopilotProject();
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public List<CopilotProject> retrieveAll() throws CopilotDAOException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param entity Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     * @throws EntityNotFoundException Mock for testing!
     */
    public void update(CopilotProject entity) throws CopilotDAOException, EntityNotFoundException {
    }
}
