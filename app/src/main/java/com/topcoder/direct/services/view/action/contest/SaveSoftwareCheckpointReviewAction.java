/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>
 * This struts action is used to save software checkpoint review placements and feedbacks.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Software Checkpoint Management)
 */
public class SaveSoftwareCheckpointReviewAction extends ContestAction {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 4567896071352160715L;

    /**
     * The submission ids.
     */
    private long[] submissionIds;
    
    /**
     * The placements of the submissions.
     */
    private int[] placements;
    
    /**
     * The feedbacks of the submissions.
     */
    private String[] feedbacks;
    
    /**
     * Whether or not commit the review.
     */
    private boolean committed;
    
    /**
     * The general feedback of the submissions.
     */
    private String generalFeedback;    

    /**
     * <p>
     * Constructs new <code>SaveSoftwareCheckpointReviewAction</code> instance.
     * </p>
     */
    public SaveSoftwareCheckpointReviewAction() {
    }

    /**
     * <p>
     * Handles the incoming request. Save the placements and feedbacks of the checkpoint submissions.
     * </p>
     * 
     * @throws Exception if an unexpected error occurs
     */
    @Override
    public void executeAction() throws Exception {
        TCSubject currentUser = getCurrentUser();
        // check if the user have write permission
        if (!DirectUtils.hasWritePermission(this, currentUser, getProjectId(), false)) {
            throw new DirectException("Have no write permission on the contest.");
        }

        // perform validation
        validateData();
        
        // check if the phase is open
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(
            currentUser, getProjectId());
        if (!DirectUtils.isPhaseOpen(softwareCompetition, PhaseType.CHECKPOINT_REVIEW_PHASE)) {
            throw new DirectException("The CHECKPOINT_REVIEW_PHASE is not open.");
        }
        
        // only works for software contest
        if (DirectUtils.isStudio(softwareCompetition)) {
            throw new DirectException("This action only works for software contest.");
        }
        
        // save the review
        contestServiceFacade.saveSoftwareCheckpointReviewWithRankAndFeedback(currentUser, getProjectId(),
            submissionIds, placements, feedbacks, committed, generalFeedback);
        
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "ok");
        setResult(result);
    }
    
    /**
     * Validates the input parameters.
     * 
     * @throws DirectException if any parameter is invalid
     */
    private void validateData() throws DirectException {
        try {
            ParameterCheckUtility.checkPositive(getProjectId(), "projectId");
            ParameterCheckUtility.checkNotNull(submissionIds, "submissionIds");
            ParameterCheckUtility.checkNotNull(placements, "placements");
            ParameterCheckUtility.checkNotNull(feedbacks, "feedbacks");
            if (submissionIds.length != placements.length || placements.length != feedbacks.length || 
                submissionIds.length == 0) {
                throw new IllegalArgumentException(
                    "The submissionIds, placements, feedbacks should be non-empty arrays with same length.");
            }
            for (long submissionId : submissionIds) {
                ParameterCheckUtility.checkPositive(submissionId, "submissionId");
            }
            for (long placement : placements) {
                ParameterCheckUtility.checkInRange(placement, 1, 10, true, true, "placement");
            }
        } catch (IllegalArgumentException e) {
            throw new DirectException("There's invalid parameter: " + e.getMessage(), e);
        }
    }

    /**
     * Gets the submission ids.
     * 
     * @return the submission ids
     */
    public long[] getSubmissionIds() {
        return submissionIds;
    }

    /**
     * Sets the submission ids.
     * 
     * @param submissionIds the submission ids to set
     */
    public void setSubmissionIds(long[] submissionIds) {
        this.submissionIds = submissionIds;
    }

    /**
     * Gets the placements of the submissions.
     * 
     * @return the placements of the submissions
     */
    public int[] getPlacements() {
        return placements;
    }

    /**
     * Sets the placements of the submissions.
     * 
     * @param placements the placements of the submissions
     */
    public void setPlacements(int[] placements) {
        this.placements = placements;
    }

    /**
     * Gets the feedbacks of the submissions.
     * 
     * @return the feedbacks of the submissions
     */
    public String[] getFeedbacks() {
        return feedbacks;
    }

    /**
     * Sets the feedbacks of the submissions.
     * 
     * @param feedbacks the feedbacks of the submissions
     */
    public void setFeedbacks(String[] feedbacks) {
        this.feedbacks = feedbacks;
    }

    /**
     * Gets the flag that indicating whether or not to commit the review.
     * 
     * @return the flag that indicating whether or not to commit the review
     */
    public boolean isCommitted() {
        return committed;
    }

    /**
     * Sets the flag that indicating whether or not to commit the review.
     * 
     * @param committed the flag that indicating whether or not to commit the review
     */
    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    /**
     * Gets the general feedback of the submissions.
     * 
     * @return the general feedback of the submissions
     */
    public String getGeneralFeedback() {
        return generalFeedback;
    }

    /**
     * Sets the general feedback of the submissions.
     * 
     * @param generalFeedback the general feedback of the submissions
     */
    public void setGeneralFeedback(String generalFeedback) {
        this.generalFeedback = generalFeedback;
    }
}
