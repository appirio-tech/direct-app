/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A <code>Struts</code> action for handling requests for checking the feedback
 * text for a contest submission.
 * </p>
 * 
 * @author FireIce
 * @version 1.0
 */
public class HasContestSubmissionFeedbackAction extends ContestAction {
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
     * Represents the round type.
     */
    private ContestRoundType roundType;

    /**
     * <p>
     * Constructs new <code>HasContestSubmissionFeedbackAction</code> instance. This implementation does nothing.
     * </p>
     */
    public HasContestSubmissionFeedbackAction() {

    }

    /**
     * <p>
     * Handles the incoming request. Check whether submission has feedback.
     * </p>
     *
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        long projectId = getProjectId();
        TCSubject currentUser = getCurrentUser();
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
            throw new DirectException("Have no permission to check submission feedback.");
        }

        String feedbackText = contestServiceFacade.getStudioSubmissionFeedback(currentUser, projectId, submissionId, phaseType);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("hasFeedback", feedbackText != null && feedbackText.trim().length() > 0);
        setResult(result);
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
     * Gets the round type.
     * 
     * @return the round type.
     */
    public ContestRoundType getRoundType() {
        return roundType;
    }

    /**
     * Sets the round type.
     * 
     * @param roundType the round type
     */
    public void setRoundType(ContestRoundType roundType) {
        this.roundType = roundType;
    }
}
