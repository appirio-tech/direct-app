/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.longcontest.services;

import com.topcoder.web.tc.rest.longcontest.resources.CompetitorResource;
import com.topcoder.web.tc.rest.longcontest.resources.MarathonMatchDetailsResource;
import com.topcoder.web.tc.rest.longcontest.resources.SearchResult;

/**
 * The marathon match analytics service interface. This interface will handle the call to rest service.
 *
 * @author Ghost_141
 * @since 1.0 (PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress)
 * @version 1.0
 */
public interface MarathonMatchAnalyticsService {
    /**
     * Get the marathon match details by given round id and group type.
     *
     * @param groupType the group type.
     * @param accessToken the access token.
     * @param roundId the round id.
     * @return the instance of MarathonMatchDetailsResource.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     */
    public MarathonMatchDetailsResource getMarathonMatchDetails(long roundId, String groupType, String accessToken)
            throws MarathonMatchAnalyticsServiceException;

    /**
     * Get the registrants data by calling rest service.
     *
     * @param roundId the round id.
     * @param accessToken the access token.
     * @return the registrants data.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     */
    public SearchResult<CompetitorResource> getRegistrants(long roundId,
            String accessToken) throws MarathonMatchAnalyticsServiceException;

}

