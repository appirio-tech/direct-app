/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.project;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.project.ProjectStatsDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>A processor to be used for setting the data for the provided front-end action with details on statistics for
 * requested project associated with current user.</p>
 *
 * <p>This processor expects the actions of {@link ViewAction} type with view data of {@link ProjectStatsDTO.Aware} type
 * and of {@link FormAction} type with form data of {@link ProjectIdForm} type to be passed to it. If any of those
 * requirements is not met then this processor does nothing.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProjectStatsProcessor implements RequestProcessor<FormAction<ProjectIdForm>> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(ProjectStatsProcessor.class);

    /**
     * <p>Constructs new <code>ProjectStatsProcessor</code> instance. This implementation does nothing.</p>
     */
    public ProjectStatsProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(FormAction<ProjectIdForm> action) {
        if (action instanceof ViewAction) {
            ViewAction viewAction = (ViewAction) action;
            Object data = viewAction.getViewData();
            if (data instanceof ProjectStatsDTO.Aware) {
                long projectId = action.getFormData().getProjectId();
                try {
                    ProjectStatsDTO projectStats = DataProvider.getProjectStats(projectId);
                    ProjectStatsDTO.Aware viewData = (ProjectStatsDTO.Aware) data;
                    viewData.setProjectStats(projectStats);
                } catch (Exception e) {
                    log.error("Failed to retrieve details on project stats for project " + projectId, e);
                    action.setResultCode(ViewAction.RC_UNEXPECTED_ERROR);
                    action.setErrorMessage(e.getMessage());
                    action.setError(e);
                }
            }
        }
    }
}
