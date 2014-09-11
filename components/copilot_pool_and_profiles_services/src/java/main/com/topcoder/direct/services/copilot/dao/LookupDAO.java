/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao;

import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;

import java.util.List;

/**
 * <p>This interface represents a lookup DAO. It provides method for retrieving all copilot profile statuses, copilot
 * project statuses and copilot types from persistence.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface LookupDAO {

    /**
     * <p>Retrieves all copilot profile statuses from persistence. Returns an empty list if none are found.</p>
     *
     * @return the retrieved copilot profile statuses (not null, doesn't contain null)
     *
     * @throws CopilotDAOException if any error occurred
     */
    List<CopilotProfileStatus> getAllCopilotProfileStatuses() throws CopilotDAOException;

    /**
     * <p>Retrieves all copilot project statuses from persistence. Returns an empty list if none are found.</p>
     *
     * @return the retrieved copilot project statuses (not null, doesn't contain null)
     *
     * @throws CopilotDAOException if any error occurred
     */
    List<CopilotProjectStatus> getAllCopilotProjectStatuses() throws CopilotDAOException;

    /**
     * <p>Retrieves all copilot types from persistence. Returns an empty list if none are found.</p>
     *
     * @return the retrieved copilot types (not null, doesn't contain null)
     *
     * @throws CopilotDAOException if any error occurred
     */
    List<CopilotType> getAllCopilotTypes() throws CopilotDAOException;
}

