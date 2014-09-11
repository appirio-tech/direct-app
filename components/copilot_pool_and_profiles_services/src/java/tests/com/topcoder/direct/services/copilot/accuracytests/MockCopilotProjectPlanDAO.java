/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.dao.EntityNotFoundException;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;

import java.util.List;


/**
 * Mock for testing!
 *
 * @author onsky
 * @version 1.0
  */
public class MockCopilotProjectPlanDAO implements CopilotProjectPlanDAO {
    /**
     * Mock for testing!
     *
     * @param copilotProjectId Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public CopilotProjectPlan getCopilotProjectPlan(long copilotProjectId)
        throws CopilotDAOException {
        return new CopilotProjectPlan();
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
    public long create(CopilotProjectPlan entity) throws CopilotDAOException {
        return 2;
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
    public CopilotProjectPlan retrieve(long entityId) throws CopilotDAOException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public List<CopilotProjectPlan> retrieveAll() throws CopilotDAOException {
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
    public void update(CopilotProjectPlan entity) throws CopilotDAOException, EntityNotFoundException {
    }
}
