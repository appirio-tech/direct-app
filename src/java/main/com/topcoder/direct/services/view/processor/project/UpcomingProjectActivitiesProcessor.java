/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.project;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.project.ProjectOverviewAction;
import com.topcoder.direct.services.view.dto.UpcomingActivitiesDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>A processor to be used for setting the data for the provided front-end action with details on upcoming activities
 * for contests associated with request project associated with current user.</p>
 *
 * <p>This processor expects the actions of {@link com.topcoder.direct.services.view.action.ViewAction} type with view data of
 * {@link com.topcoder.direct.services.view.dto.UpcomingActivitiesDTO.Aware} type and of
 * {@link com.topcoder.direct.services.view.action.FormAction} type with form data of
 * {@link com.topcoder.direct.services.view.form.ProjectIdForm}
 * type to be passed to it. If any of those requirements is not met then this processor does nothing.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Cockpit Performance Improvement Project Overview and Manage Copilot Posting Assembly
 */
public class UpcomingProjectActivitiesProcessor implements RequestProcessor<FormAction<ProjectIdForm>> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(UpcomingProjectActivitiesProcessor.class);

    /**
     * <p>Constructs new <code>UpcomingProjectActivitiesProcessor</code> instance. This implementation does nothing.</p>
     */
    public UpcomingProjectActivitiesProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(FormAction<ProjectIdForm> action) {
        boolean loadData = true;

        if(action instanceof ProjectOverviewAction) {
            loadData = !((ProjectOverviewAction) action).getExport();
        }

        if (action instanceof ViewAction && loadData) {
            ViewAction viewAction = (ViewAction) action;
            Object data = viewAction.getViewData();
            if (data instanceof UpcomingActivitiesDTO.Aware) {
                long projectId = action.getFormData().getProjectId();
                long currentUserId = action.getSessionData().getCurrentUserId();
                try {
                    // use DataProvider.getUpcomingActivitiesForProject to get upcoming activities for the specified
                    // direct project
                    UpcomingActivitiesDTO projectUpcomingActivities
                        = DataProvider.getUpcomingActivitiesForProject(currentUserId, projectId, 15);
                    UpcomingActivitiesDTO.Aware viewData = (UpcomingActivitiesDTO.Aware) data;
                    viewData.setUpcomingActivities(projectUpcomingActivities);
                } catch (Exception e) {
                    log.error("Failed to retrieve details on upcoming activities on project " + projectId, e);
                    action.setResultCode(ViewAction.RC_UNEXPECTED_ERROR);
                    action.setErrorMessage(e.getMessage());
                    action.setError(e);
                }
            }
        }

    }
}

