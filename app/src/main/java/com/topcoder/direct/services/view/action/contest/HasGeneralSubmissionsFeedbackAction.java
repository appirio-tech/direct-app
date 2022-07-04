/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>A <code>Struts</code> action to be used for processing requests for checking general feedback for checkpoint round.
 * </p>
 *
 * @author FireIce
 */
public class HasGeneralSubmissionsFeedbackAction extends ContestAction {

    /**
     * <p>Constructs new <code>HasGeneralSubmissionsFeedbackAction</code> instance. This implementation does
     * nothing.</p>
     */
    public HasGeneralSubmissionsFeedbackAction() {
    }


    /**
     * <p>Handles the incoming request. Check whether contest has general checkpoint feedback.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        long projectId = getProjectId();
        if (projectId <= 0) {
            throw new DirectException("projectId less than 0 or not defined.");
        }
        TCSubject currentUser = getCurrentUser();
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(currentUser, projectId);
        if (!DirectUtils.isPhaseOpen(softwareCompetition, PhaseType.CHECKPOINT_REVIEW_PHASE)) {
            throw new DirectException("The checkpoint review phase is not open.");
        }
        
        // only works for studio contest
        if (DirectUtils.isStudio(softwareCompetition)) {
            String feedback = softwareCompetition.getProjectHeader().getProjectStudioSpecification().getGeneralFeedback();

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("hasFeedback", feedback != null && feedback.trim().length() > 0);
            setResult(result);
        }
    }
}
