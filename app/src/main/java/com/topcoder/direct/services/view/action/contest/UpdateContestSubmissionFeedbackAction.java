/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.List;

import org.springframework.web.util.HtmlUtils;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>
 * A <code>Struts</code> action for handling requests for updating the feedback
 * text for a contest submission.
 * </p>
 * 
 * <p>
 *   Version 1.1 (TC Direct Replatforming Release 3) change notes:
 *   <ul>
 *     <li>The {@link #executeAction()} method was totally updated to work for the new studio contest.</li>
 *   </ul>
 * </p>
 * 
 * @author flexme
 * @version 1.1
 * @since Direct Submission Viewer Release 2
 */
public class UpdateContestSubmissionFeedbackAction extends ContestAction {
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
     * Represents the round type.
     * @since 1.1
     */
    private ContestRoundType roundType;

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
        long projectId = getProjectId();
        TCSubject currentUser = getCurrentUser();
        if (!DirectUtils.hasWritePermission(this, currentUser, projectId, false)) {
            throw new DirectException("Have no permission to update submission feedback.");
        }
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(currentUser, projectId);
        PhaseType phaseType = (roundType == ContestRoundType.CHECKPOINT ? PhaseType.CHECKPOINT_REVIEW_PHASE : PhaseType.REVIEW_PHASE);
        if (!DirectUtils.isPhaseOpen(softwareCompetition, phaseType)) {
            throw new DirectException("The phase is not open.");
        }
        
        List<Submission> submissions = DirectUtils.getStudioContestSubmissions(projectId, roundType, currentUser, contestServiceFacade);
        boolean find = false;
        for (Submission submission : submissions) {
            if (submission.getId() == submissionId) {
                find = true;
                break;
            }
        }
        if (!find) {
            throw new DirectException("Have no permission to update submission feedback.");
        }
        
        String feedback = HtmlUtils.htmlEscape(feedbackText);
        contestServiceFacade.saveStudioSubmisionWithRankAndFeedback(currentUser, projectId, submissionId, 1, feedback,
                false, phaseType);
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

    /**
     * Gets the round type.
     * 
     * @return the round type.
     * @since 1.1
     */
    public ContestRoundType getRoundType() {
        return roundType;
    }

    /**
     * Sets the round type.
     * 
     * @param roundType the round type
     * @since 1.1
     */
    public void setRoundType(ContestRoundType roundType) {
        this.roundType = roundType;
    }
}
