/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * A <code>Struts</code> action to be used for handling requests for save rank of milestone submissions for
 * <code>Studio</code> contest.
 * </p>
 * 
 * <p>
 *   Version 1.1 (TC Direct Replatforming Release 3) change notes:
 *   <ul>
 *     <li>The {@link #executeAction()} method was totally updated to work for the new studio contest.</li>
 *   </ul>
 * </p>
 * 
 * <p>
 *   Version 1.2 (TC Direct Replatforming Release 5) change notes:
 *   <ul>
 *     <li>Changed the class name from <code>SaveMilestoneAction</code> to <code>SaveContestSubmissionRankAction</code> because
 *     this action can save submissions rank for both Milestone Round and Final Round.</li>
 *   </ul>
 * </p>
 *
 * @author flexme
 * @since Submission Viewer Release 3 assembly
 * @version 1.2
 */
public class SaveContestSubmissionRankAction extends ContestAction {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 4334966071352160715L;

    /**
     * <p>
     * A <code>String</code> providing the ranking of the milestone submissions.
     * </p>
     */
    private String ranks;

    /**
     * <p>
     * A <code>String</code> providing the submission ids which have extra purchases.
     * </p>
     * @since 1.1
     */
    private String additionalPurchases;

    /**
     * <p>
     * Represents the round type.
     * </p>
     * @since 1.1
     */
    private ContestRoundType roundType;

    /**
     * <p>
     * Constructs new <code>SaveContestSubmissionRank</code> instance. This implementation does nothing.
     * </p>
     */
    public SaveContestSubmissionRankAction() {
    }

    /**
     * <p>
     * Gets the ranking of the milestone submissions.
     * </p>
     * 
     * @return A <code>String</code> providing the ranking of the milestone submissions.
     */
    public String getRanks() {
        return ranks;
    }

    /**
     * <p>
     * Sets the ranking of the milestone submissions.
     * </p>
     * 
     * @param ranks
     *            A <code>String</code> providing the ranking of the milestone submissions.
     */
    public void setRanks(String ranks) {
        this.ranks = ranks;
    }

    /**
     * <p>
     * Gets the submission ids which have extra purchases.
     * </p>
     * 
     * @return the submission ids which have extra purchases.
     * @since 1.1
     */
    public String getAdditionalPurchases() {
        return additionalPurchases;
    }

    /**
     * <p>
     * Sets the submission ids which have extra purchases.
     * </p>
     * 
     * @param additionalPurchases the submission ids which have extra purchases.
     * @since 1.1
     */
    public void setAdditionalPurchases(String additionalPurchases) {
        this.additionalPurchases = additionalPurchases;
    }

    /**
     * <p>
     * Gets the round type.
     * </p>
     * 
     * @return the round type
     * @since 1.1
     */
    public ContestRoundType getRoundType() {
        return roundType;
    }

    /**
     * <p>
     * Sets the round type.
     * </p>
     * 
     * @param roundType the round type.
     * @since 1.1
     */
    public void setRoundType(ContestRoundType roundType) {
        this.roundType = roundType;
    }

    /**
     * <p>
     * Handles the incoming request. Save the ranking of the milestone submissions.
     * </p>
     * 
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        long projectId = getProjectId();
        if (projectId <= 0) {
            throw new DirectException("projectId less than 0 or not defined.");
        }
        TCSubject currentUser = getCurrentUser();
        // check if the user have write permission
        if (!DirectUtils.hasWritePermission(this, currentUser, projectId, false)) {
            throw new DirectException("Have no permission to update the general feedback.");
        }
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(currentUser, projectId);
        PhaseType phaseType = roundType == ContestRoundType.MILESTONE ? PhaseType.MILESTONE_REVIEW_PHASE : PhaseType.REVIEW_PHASE;
        if (!DirectUtils.isPhaseOpen(softwareCompetition, phaseType)) {
            throw new DirectException("The phase is not open.");
        }
        
        // only works for studio contest
        if (DirectUtils.isStudio(softwareCompetition)) {
            List<Submission> submissions = DirectUtils.getStudioContestSubmissions(projectId, roundType, currentUser, contestServiceFacade);
            Map<Long, Integer> placement = new HashMap<Long, Integer>();
            Set<Long> extraPurchase = new HashSet<Long>();
            if (ranks.length() > 0) {
                int rank = 1;
                for (String submissionId : ranks.split(",")) {
                    if (!"null".equals(submissionId)) {
                        placement.put(Long.parseLong(submissionId), rank++);
                    }
                }
            }
            if (additionalPurchases.length() > 0) {
                for (String submissionId : additionalPurchases.split(",")) {
                    extraPurchase.add(Long.parseLong(submissionId));
                }
            }
            
            // update the submission placement
            for (Submission submission : submissions) {
                int rank = 10;
                if (placement.containsKey(submission.getId())) {
                    rank = placement.get(submission.getId());
                } else if (extraPurchase.contains(submission.getId())) {
                    rank = placement.size() + 1;
                }
                contestServiceFacade.saveStudioSubmisionWithRankAndFeedback(currentUser, projectId, submission.getId(), rank, null,
                        true, phaseType);
                
                if (extraPurchase.contains(submission.getId())) {
                    submission.setExtra(true);
                } else {
                    submission.setExtra(false);
                }
            }
            contestServiceFacade.updateSoftwareSubmissions(currentUser, submissions);
        }
    }
}
