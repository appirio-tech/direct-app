/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao;

import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;

/**
 * <p>This interface represents a copilot project plan DAO. It extends GenericDAO&lt;CopilotProjectPlan&gt; and provides
 * an additional method for retrieving copilot project plan by the given copilot project ID.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be thread safe when entities passed to them
 * are used by the caller in thread safe manner.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface CopilotProjectPlanDAO extends GenericDAO<CopilotProjectPlan> {

    /**
     * <p>Retrieves the copilot project plan of copilot project with the given ID.</p>
     *
     * @param copilotProjectId the ID of the copilot project
     *
     * @return the retrieved copilot project plan (or null if copilot project with the given ID doesn't exist or there
     *         is no associated plan)
     *
     * @throws IllegalArgumentException if copilotProjectId <= 0
     * @throws CopilotDAOException      if some other error occurred
     */
    CopilotProjectPlan getCopilotProjectPlan(long copilotProjectId) throws CopilotDAOException;
}

