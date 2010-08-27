/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import org.springframework.web.util.HtmlUtils;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.studio.SubmissionFeedback;

/**
 * <p>
 * A <code>Struts</code> action for handling requests for updating the feedback
 * text for a contest submission.
 * </p>
 * 
 * @author flexme
 * @version 1.0 (Direct Submission Viewer Release 2)
 */
public class UpdateContestSubmissionFeedbackAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -114742516476417481L;

    /**
     * <p>
     * A <code>long</code> providing the id of the contest submission.
     * </p>
     */
    private long submissionId;

    /**
     * <p>
     * A <code>String</code> providing the content of the feedback text.
     * </p>
     */
    private String feedbackText;

    /**
     * <p>
     * Constructs new <code>UpdateContestSubmissionFeedbackAction</code> instance. This implementation does nothing.
     * </p>
     */
    public UpdateContestSubmissionFeedbackAction() {

    }

    /**
     * <p>
     * Handles the incoming request. Update the feedback text for a contest
     * submission.
     * </p>
     *
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        SubmissionFeedback feedback = new SubmissionFeedback();
        feedback.setFeedbackText(HtmlUtils.htmlEscape(feedbackText));
        feedback.setSubmissionId(submissionId);
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        contestServiceFacade.updateSubmissionsFeedback(tcSubject, new SubmissionFeedback[] { feedback });
        setResult(feedback);
    }

    /**
     * <p>
     * Gets the id of the contest submission.
     * </p>
     *
     * @return a <code>long</code> providing the id of the contest submission.
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * <p>
     * Sets the id of the contest submission.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the id of the contest
     *            submission.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p>
     * Gets the content of the feedback text.
     * </p>
     *
     * @return A <code>String</code> providing the content of the feedback text.
     */
    public String getFeedbackText() {
        return feedbackText;
    }

    /**
     * <p>
     * Sets the content of the feedback text.
     * </p>
     *
     * @param feedbackText
     *            A <code>String</code> providing the content of the feedback
     *            text.
     */
    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }
}
