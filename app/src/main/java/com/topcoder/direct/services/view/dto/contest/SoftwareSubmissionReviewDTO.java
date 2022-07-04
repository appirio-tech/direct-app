/*
 * Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.UserDTO;

import java.io.Serializable;

/**
 * <p>A <code>DTO</code> class providing the data for single software contest submission review.</p>
 *
 * <p>
 * Version 1.0.1 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) Change notes:
 *   <ol>
 *     <li>Added {@link #checkpointFeedback} property.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER, TCSASSEMBLER
 * @version 1.0.1
 * @since 1.0 (Direct Software Submission Viewer assembly)
 */
public class SoftwareSubmissionReviewDTO implements Serializable {

    /**
     * <p>A <code>UserDTO</code> providing the details for reviewer.</p>
     */
    private UserDTO reviewer;

    /**
     * <p>A <code>Double</code> providing the review final score.</p>
     */
    private Double finalScore;

    /**
     * <p>A <code>long</code> providing the ID of submission associated with this review.</p>
     */
    private long submissionId;

    /**
     * <p>A <code>long</code> providing the review ID.</p>
     */
    private long reviewId;

    /**
     * <p>A <code>Double</code> providing the initial score for review.</p>
     */
    private Double initialScore;
    
    /**
     * <p>A <code>String</code> providing the feedback for the checkpoint submission.</p>
     *
     * @since 1.0.1
     */
    private String checkpointFeedback;

    /**
     * <p>Constructs new <code>SoftwareSubmissionReviewDTO</code> instance. This implementation does nothing.</p>
     */
    public SoftwareSubmissionReviewDTO() {
    }

    /**
     * <p>Gets the initial score for review.</p>
     *
     * @return a <code>Double</code> providing the initial score for review.
     */
    public Double getInitialScore() {
        return this.initialScore;
    }

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the review is committed or not.</p>
     */
    private boolean committed;

    /**
     * <p>Gets the flag indicating whether the review is committed or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the review is committed or not.
     */
    public boolean getCommitted() {
        return this.committed;
    }

    /**
     * <p>Sets the flag indicating whether the review is committed or not.</p>
     *
     * @param committed a <code>boolean</code> providing the flag indicating whether the review is committed or not.
     */
    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    /**
     * <p>Sets the initial score for review.</p>
     *
     * @param initialScore a <code>Double</code> providing the initial score for review.
     */
    public void setInitialScore(Double initialScore) {
        this.initialScore = initialScore;
    }

    /**
     * <p>Gets the review ID.</p>
     *
     * @return a <code>long</code> providing the review ID.
     */
    public long getReviewId() {
        return this.reviewId;
    }

    /**
     * <p>Sets the review ID.</p>
     *
     * @param reviewId a <code>long</code> providing the review ID.
     */
    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * <p>Gets the ID of submission associated with this review.</p>
     *
     * @return a <code>long</code> providing the ID of submission associated with this review.
     */
    public long getSubmissionId() {
        return this.submissionId;
    }

    /**
     * <p>Sets the ID of submission associated with this review.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of submission associated with this review.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p>Gets the review final score.</p>
     *
     * @return a <code>Double</code> providing the review final score.
     */
    public Double getFinalScore() {
        return this.finalScore;
    }

    /**
     * <p>Sets the review final score.</p>
     *
     * @param finalScore a <code>Double</code> providing the review final score.
     */
    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * <p>Gets the details for reviewer.</p>
     *
     * @return a <code>UserDTO</code> providing the details for reviewer.
     */
    public UserDTO getReviewer() {
        return this.reviewer;
    }

    /**
     * <p>Sets the details for reviewer.</p>
     *
     * @param reviewer a <code>UserDTO</code> providing the details for reviewer.
     */
    public void setReviewer(UserDTO reviewer) {
        this.reviewer = reviewer;
    }

    public String toString() {
        return "SoftwareSubmissionReviewDTO{" +
               "reviewer=" + reviewer.toString() +
               ", finalScore=" + finalScore +
               ", submissionId=" + submissionId +
               ", reviewId=" + reviewId +
               ", initialScore=" + initialScore +
               ", committed=" + committed +
               ", checkpointFeedback=" + checkpointFeedback +
               '}';
    }

    /**
     * Sets the feedback for the checkpoint submission.
     * 
     * @param checkpointFeedback the feedback for the checkpoint submission to set
     * @since 1.0.1
     */
    public void setCheckpointFeedback(String checkpointFeedback) {
        this.checkpointFeedback = checkpointFeedback;
    }

    /**
     * Gets the feedback for the checkpoint submission.
     * 
     * @return the feedback for the checkpoint submission
     * @since 1.0.1
     */
    public String getCheckpointFeedback() {
        return checkpointFeedback;
    }
}
