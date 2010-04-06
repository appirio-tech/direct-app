/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.project;

import com.topcoder.direct.services.view.action.TopCoderDirectAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

import java.util.List;

/**
 * <p>A processor to be used for setting the session data for the provided front-end action with details on contests for
 * currently selected project associated with current user.</p>
 *
 * <p>This processor expects the actions of {@link TopCoderDirectAction} type to be passed to it.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CurrentProjectContestsProcessor implements RequestProcessor<TopCoderDirectAction> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(CurrentProjectContestsProcessor.class);

    /**
     * <p>Constructs new <code>CurrentProjectContestsProcessor</code> instance. This implementation does nothing.</p>
     */
    public CurrentProjectContestsProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>TopCoderDirectAction</code> representing the current action mapped to incoming request.
     */
    public void processRequest(TopCoderDirectAction action) {
        ProjectBriefDTO currentProject = action.getSessionData().getCurrentProjectContext();
        if (currentProject != null) {
            long projectId = currentProject.getId();
            long currentUserId = action.getSessionData().getCurrentUserId();
            try {
                List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(currentUserId, projectId);
                action.getSessionData().setCurrentProjectContests(contests);
            } catch (Exception e) {
                log.error("Failed to retrieve details on project contests for current project " + projectId, e);
                action.setResultCode(ViewAction.RC_UNEXPECTED_ERROR);
                action.setErrorMessage(e.getMessage());
                action.setError(e);
            }
        }
    }
}
