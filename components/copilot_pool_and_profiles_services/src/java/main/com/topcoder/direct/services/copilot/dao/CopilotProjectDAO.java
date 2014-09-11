/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao;

import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectFeedback;

import java.util.List;

/**
 * <p>This interface represents a copilot project DAO. It extends GenericDAO&lt;CopilotProject&gt; and provides an
 * additional method for retrieving a list of copilot projects for the specified copilot.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be thread safe when entities passed to them
 * are used by the caller in thread safe manner.</p>
 *
 * <p>
 *    Version 1.1 (Module Assembly - TopCoder Copilot Feedback Integration) updates:
 *    <ul>
 *        <li>Add method {@link #getCopilotProjectFeedback(long, long)}</li>
 *        <li>Add method {@link #addCopilotProjectFeedback(com.topcoder.direct.services.copilot.model.CopilotProjectFeedback, long)}</li>
 *        <li>Add method {@link #updateCopilotProjectFeedback(com.topcoder.direct.services.copilot.model.CopilotProjectFeedback, long)}</li>
 *    </ul>
 * </p>
 *
 * @author saarixx, GreatKevin
 * @version 1.1
 */
public interface CopilotProjectDAO extends GenericDAO<CopilotProject> {

    /**
     * <p>Retrieves the copilot projects for the copilot with the specified profile ID. Returns an empty list if the
     * copilot has no associated projects.</p>
     *
     * @param copilotProfileId the ID of the copilot profile
     *
     * @return the copilot projects for the specified copilot (not null, doesn't contain null)
     *
     * @throws IllegalArgumentException if copilotProfileId <= 0
     * @throws CopilotDAOException      if some other error occurred
     */
    List<CopilotProject> getCopilotProjects(long copilotProfileId) throws CopilotDAOException;

    /**
     * Gets the copilot feedback on the given copilot project id by the given user.
     *
     * @param copilotProjectId the copilot project id.
     * @param userId the id of the user who gives the feedback.
     * @return the <code>CopilotProjectFeedback</code> instance
     * @throws CopilotDAOException if error occurs
     *
     * @since 1.1
     */
    CopilotProjectFeedback getCopilotProjectFeedback(long copilotProjectId, long userId) throws CopilotDAOException;

    /**
     * Adds the copilot feedback to the given copilot project.
     *
     * @param feedback the feedback to add.
     * @param copilotProjectId the copilot project id.
     * @throws CopilotDAOException if error occurs.
     *
     * @since 1.1
     */
    void addCopilotProjectFeedback(CopilotProjectFeedback feedback, long copilotProjectId) throws CopilotDAOException;

    /**
     * Updates the copilot feedback of the given copilot project.
     *
     * @param feedback the feedback to add.
     * @param copilotProjectId the copilot project id.
     * @throws CopilotDAOException if error occurs.
     *
     * @since 1.1
     */
    void updateCopilotProjectFeedback(CopilotProjectFeedback feedback, long copilotProjectId) throws CopilotDAOException;
}

