/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import org.springframework.web.util.HtmlUtils;

/**
 * <p>A <code>Struts</code> action to be used for processing requests for updating general feedback for milestne round.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Submission Viewer Release 4 assembly)
 */
public class UpdateGeneralSubmissionsFeedbackAction extends StudioOrSoftwareContestAction {

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
        if (isStudioCompetition()) {
            ContestServiceFacade contestServiceFacade = getContestServiceFacade();
            TCSubject tcSubject = getCurrentUser();
            boolean updateResult
                = contestServiceFacade.updateSubmissionsGeneralFeedback(tcSubject, getContestId(),
                                                                        HtmlUtils.htmlEscape(getFeedbackText()));
            setResult(updateResult);
        } else {
            setResult(false);
        }
    }
}
