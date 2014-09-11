/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;

/**
 * <p>
 * This interface represents a copilot project plan service. It extends GenericService&lt;CopilotProjectPlan&gt; and
 * provides an additional method for retrieving copilot project plan by the given copilot project ID.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations of this interface must be thread safe when entities passed to them are used
 * by the caller in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface CopilotProjectPlanService extends GenericService<CopilotProjectPlan> {
    /**
     * Retrieves the copilot project plan of copilot project with the given ID.
     *
     * @param copilotProjectId the ID of the copilot project
     * @return the retrieved copilot project plan or null if copilot project with the given ID doesn't exist
     * @throws IllegalArgumentException if copilotProjectId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public CopilotProjectPlan getCopilotProjectPlan(long copilotProjectId) throws CopilotServiceException;
}
