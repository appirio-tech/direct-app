/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import java.util.List;

import com.topcoder.direct.services.copilot.dto.CopilotProjectDTO;
import com.topcoder.direct.services.copilot.model.CopilotProject;

/**
 * <p>
 * This interface represents a copilot project service. It extends GenericService&lt;CopilotProject&gt; and provides
 * additional methods for retrieving a list of copilot projects for the specified copilot and retrieving copilot
 * project details by the given copilot project ID.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations of this interface must be thread safe when entities passed to them are used
 * by the caller in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface CopilotProjectService extends GenericService<CopilotProject> {
    /**
     * Retrieves the copilot projects for the copilot with the specified profile ID. Returns an empty list if the
     * copilot has no associated projects.
     *
     * @param copilotProfileId the ID of the copilot profile
     * @return the copilot projects for the specified copilot (not null, doesn't contain null)
     * @throws IllegalArgumentException if copilotProfileId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public List<CopilotProjectDTO> getCopilotProjects(long copilotProfileId) throws CopilotServiceException;

    /**
     * Retrieves the details of the copilot project with the specified ID.
     *
     * @param copilotProjectId the ID of the copilot project
     * @return the retrieved copilot project details or null if copilot project with the given ID doesn't exist
     * @throws IllegalArgumentException if copilotProjectId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public CopilotProjectDTO getCopilotProjectDTO(long copilotProjectId) throws CopilotServiceException;
}
