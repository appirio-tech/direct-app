/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.project;

import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.LatestActivitiesDTO;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>A processor to be used for setting the data for the provided front-end action with details on latest activities
 * for projects associated with current user.</p>
 *
 * <p>This processor expects the actions of {@link ViewAction} type with view data of {@link LatestActivitiesDTO.Aware}
 * type to be passed to it. If any of those requirements is not met then this processor does nothing.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LatestActivitiesProcessor implements RequestProcessor<ViewAction> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(LatestActivitiesProcessor.class);

    /**
     * <p>Constructs new <code>LatestActivitiesProcessor</code> instance. This implementation does nothing.</p>
     */
    public LatestActivitiesProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(ViewAction action) {
        long currentUserId = action.getSessionData().getCurrentUserId();
        try {
            Object data = action.getViewData();
            if (data instanceof LatestActivitiesDTO.Aware) {
                LatestActivitiesDTO latestActivities
                    = DataProvider.getLatestActivitiesForUserProjects(currentUserId, 15);
                LatestActivitiesDTO.Aware viewData = (LatestActivitiesDTO.Aware) data;
                viewData.setLatestActivities(latestActivities);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve details on latest activities on projects for user " + currentUserId, e);
            action.setResultCode(ViewAction.RC_UNEXPECTED_ERROR);
            action.setErrorMessage(e.getMessage());
            action.setError(e);
        }
    }
}
