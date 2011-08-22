/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import org.springframework.web.util.HtmlUtils;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>A <code>Struts</code> action to be used for processing requests for updating general feedback for milestone round.
 * </p>
 *
 * <p>
 *   Version 1.1 (TC Direct Replatforming Release 3) change notes:
 *   <ul>
 *     <li>The {@link #executeAction()} method was totally updated to work for the new studio contest.</li>
 *   </ul>
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since Submission Viewer Release 4 assembly
 */
public class UpdateGeneralSubmissionsFeedbackAction extends ContestAction {

    /**
     * <p>A <code>String</code> providing the text for the general feedback.</p>
     */
    private String feedbackText;

    /**
     * <p>Constructs new <code>UpdateGeneralSubmissionsFeedbackAction</code> instance. This implementation does
     * nothing.</p>
     */
    public UpdateGeneralSubmissionsFeedbackAction() {
    }

    /**
     * <p>Gets the text for the general feedback.</p>
     *
     * @return a <code>String</code> providing the text for the general feedback.
     */
    public String getFeedbackText() {
        return this.feedbackText;
    }

    /**
     * <p>Sets the text for the general feedback.</p>
     *
     * @param feedbackText a <code>String</code> providing the text for the general feedback.
     */
    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    /**
     * <p>Handles the incoming request. Update the general feedback text for a contest submissions.</p>
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
        if (!DirectUtils.hasWritePermission(this, currentUser, projectId, false)) {
            throw new DirectException("Have no permission to update the general feedback.");
        }
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(currentUser, projectId);
        if (!DirectUtils.isPhaseOpen(softwareCompetition, PhaseType.MILESTONE_REVIEW_PHASE)) {
            throw new DirectException("The milestone review phase is not open.");
        }
        
        // only works for studio contest
        if (DirectUtils.isStudio(softwareCompetition)) {
            softwareCompetition.getProjectHeader().getProjectStudioSpecification().setGeneralFeedback(HtmlUtils.htmlEscape(feedbackText));
            softwareCompetition.setProjectHeaderReason("Update general feedback");
            contestServiceFacade.updateSoftwareContest(currentUser, softwareCompetition, softwareCompetition.getProjectHeader().getTcDirectProjectId(),
                    DirectUtils.getMultiRoundEndDate(softwareCompetition), DirectUtils.getSubmissionEndDate(softwareCompetition));
        }
    }
}
