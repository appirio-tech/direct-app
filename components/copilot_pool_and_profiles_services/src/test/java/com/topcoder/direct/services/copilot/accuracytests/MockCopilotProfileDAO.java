/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dao.EntityNotFoundException;
import com.topcoder.direct.services.copilot.model.CopilotProfile;

import java.util.ArrayList;
import java.util.List;


/**
 * Mock for testing!
 *
 * @author onsky
 * @version 1.0
  */
public class MockCopilotProfileDAO implements CopilotProfileDAO {
    /**
     * Mock for testing!
     *
     * @param userId Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public CopilotProfile getCopilotProfile(long userId)
        throws CopilotDAOException {
        return new CopilotProfile();
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
    public long create(CopilotProfile entity) throws CopilotDAOException {
        return 1L;
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
    public CopilotProfile retrieve(long entityId) throws CopilotDAOException {
        return new CopilotProfile();
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public List<CopilotProfile> retrieveAll() throws CopilotDAOException {
        List<CopilotProfile> result = new ArrayList<CopilotProfile>();
        result.add(new CopilotProfile());

        return result;
    }

    /**
     * Mock for testing!
     *
     * @param entity Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     * @throws EntityNotFoundException Mock for testing!
     */
    public void update(CopilotProfile entity) throws CopilotDAOException, EntityNotFoundException {
    }
}
