/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import java.util.List;

import com.topcoder.direct.services.copilot.dto.CopilotPoolMember;
import com.topcoder.direct.services.copilot.dto.CopilotProfileDTO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;

/**
 * <p>
 * This interface represents a copilot profile service. It extends GenericService&lt;CopilotProfile&gt; and provides
 * additional methods for retrieving a list of copilot pool members, retrieving copilot profile and its details by
 * the given copilot user ID.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations of this interface must be thread safe when entities passed to them are used
 * by the caller in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface CopilotProfileService extends GenericService<CopilotProfile> {
    /**
     * Retrieves all copilot pool members from persistence. Returns an empty list if none are found.
     *
     * @return the retrieved copilot pool members (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<CopilotPoolMember> getCopilotPoolMembers() throws CopilotServiceException;

    /**
     * Retrieves the copilot profile details.
     *
     * @param userId the user ID of the copilot
     * @return the retrieved copilot profile details or null if copilot user with the given ID doesn't exist
     * @throws IllegalArgumentException if userId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public CopilotProfileDTO getCopilotProfileDTO(long userId) throws CopilotServiceException;

    /**
     * Retrieves the copilot profile of the user with the given ID.
     *
     * @param userId the user ID of the copilot
     * @return the retrieved copilot profile or null if copilot user with the given ID doesn't exist
     * @throws IllegalArgumentException if userId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public CopilotProfile getCopilotProfile(long userId) throws CopilotServiceException;
}
