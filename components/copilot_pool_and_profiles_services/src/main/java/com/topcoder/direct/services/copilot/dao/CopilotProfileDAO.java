/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao;

import com.topcoder.direct.services.copilot.model.CopilotProfile;

/**
 * <p>This interface represents a copilot profile DAO. It extends GenericDAO&lt;CopilotProfile&gt; and provides an
 * additional method for retrieving copilot profile by the given copilot user ID.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be thread safe when entities passed to them
 * are used by the caller in thread safe manner.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface CopilotProfileDAO extends GenericDAO<CopilotProfile> {

    /**
     * <p>Retrieves the copilot profile of the user with the given ID.</p>
     *
     * @param userId the user ID of the copilot
     *
     * @return the retrieved copilot profile or null if copilot user with the given ID doesn't exist
     *
     * @throws IllegalArgumentException if userId <= 0
     * @throws CopilotDAOException      if some other error occurred
     */
    CopilotProfile getCopilotProfile(long userId) throws CopilotDAOException;
}

