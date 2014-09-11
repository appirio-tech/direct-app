/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;

import java.util.ArrayList;
import java.util.List;


/**
 * Mock for testing!
 *
 * @author onsky
 * @version 1.0
  */
public class MockLookupDAO implements LookupDAO {
    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public List<CopilotProfileStatus> getAllCopilotProfileStatuses()
        throws CopilotDAOException {
        List<CopilotProfileStatus> result = new ArrayList<CopilotProfileStatus>();
        result.add(new CopilotProfileStatus());

        return result;
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public List<CopilotProjectStatus> getAllCopilotProjectStatuses()
        throws CopilotDAOException {
        List<CopilotProjectStatus> result = new ArrayList<CopilotProjectStatus>();
        result.add(new CopilotProjectStatus());

        return result;
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws CopilotDAOException Mock for testing!
     */
    public List<CopilotType> getAllCopilotTypes() throws CopilotDAOException {
        List<CopilotType> result = new ArrayList<CopilotType>();
        result.add(new CopilotType());

        return result;
    }
}
