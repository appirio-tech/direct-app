/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor;

import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

import java.util.List;

/**
 * <p>A processor to be used for setting the data for the provided front-end action with list of projects associated
 * with current user.</p>
 *
 * <p>This processor expects the actions of {@link ViewAction} type with view data of {@link UserProjectsDTO.Aware} type
 * to be passed to it. If any of those requirements is not met then this processor does nothing.</p>
 *
 * @author isv
 * @version 1.0
 */
public class UserProjectsProcessor implements RequestProcessor<ViewAction> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(UserProjectsProcessor.class);

    /**
     * <p>Constructs new <code>UserProjectsProcessor</code> instance. This implementation does nothing.</p>
     */
    public UserProjectsProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action. Analyzes if the action requires
     * details on current user projects and if that's the case then retrieves necessary details from DB and sets
     * action's view data with retrieved values.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(ViewAction action) {
        try {
            Object data = action.getViewData();
            if (data instanceof UserProjectsDTO.Aware) {
                List<ProjectBriefDTO> projects
                    = DataProvider.getUserProjects(action.getSessionData().getCurrentUserId());
                UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
                userProjectsDTO.setProjects(projects);
                UserProjectsDTO.Aware viewData = (UserProjectsDTO.Aware) data;
                viewData.setUserProjects(userProjectsDTO);
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve details on user projects", e);
            action.setResultCode(ViewAction.RC_UNEXPECTED_ERROR);
            action.setErrorMessage(e.getMessage());
            action.setError(e);
        }
    }
}
