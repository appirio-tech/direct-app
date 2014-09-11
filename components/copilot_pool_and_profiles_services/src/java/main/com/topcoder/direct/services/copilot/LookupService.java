/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import java.util.List;

import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;

/**
 * <p>
 * This interface represents a lookup service. It provides method for retrieving all copilot profile statuses,
 * copilot project statuses and copilot types from persistence.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations of this interface must be thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface LookupService {
    /**
     * Retrieves all copilot profile statuses from persistence. Returns an empty list if none are found.
     *
     * @return the retrieved copilot profile statuses (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<CopilotProfileStatus> getAllCopilotProfileStatuses() throws CopilotServiceException;

    /**
     * Retrieves all copilot project statuses from persistence. Returns an empty list if none are found.
     *
     * @return the retrieved copilot project statuses (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<CopilotProjectStatus> getAllCopilotProjectStatuses() throws CopilotServiceException;

    /**
     * Retrieves all copilot types from persistence. Returns an empty list if none are found.
     *
     * @return the retrieved copilot types (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<CopilotType> getAllCopilotTypes() throws CopilotServiceException;
}
