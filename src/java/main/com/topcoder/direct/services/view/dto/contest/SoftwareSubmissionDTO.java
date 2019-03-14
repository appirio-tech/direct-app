/*
 * Copyright (C) 2010-1012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.UserDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>A <code>DTO</code> class providing the data for single software contest submission.</p>
 *
 * <p>
 * Version 1.0.1 (Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #uploadId} property.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 1.0.2 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) Change notes:
 *   <ol>
 *     <li>Added {@link #checkpointFeedback} property.</li>
 *     <li>Added {@link #checkpointReviewScore} property.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER, TCSASSEMBLER
 * @version 1.0.2
 */
public class SoftwareSubmissionDTO implements Serializable {

    /**
     * <p>A <code>long</code> providing the ID for submission.</p>
     */
    private long submissionId;

    /**
     * <p>A <code>Date</code> providing the submission date.</p>
     */
    private Date submissionDate;

    /**
     * <p>A <code>Double</code> providing the screening score for submission.</p>
     */
    private Double screeningScore;

    /**
     * <p>A <code>Double</code> providing the initial score for submission.</p>
     */
    private Double initialScore;

    /**
     * <p>A <code>Float</code> providing the final score for submission.</p>
     */
    private Double finalScore;

    /**
     * <p>A <code>UserDTO</code> providing the details for the submitter.</p>
     */
    private UserDTO submitter;

    /**
     * <p>A <code>List</code> providing the details for reviews for submission.</p>
     */
    private List<SoftwareSubmissionReviewDTO> reviews;

    /**
     * <p>A <code>Boolean</code> providing the flag indicating whether the submission passed screening or not.</p>
     */
    private Boolean passedScreening;

    /**
     * <p>A <code>Boolean</code> providing the flag indicating whether the submission passed review or not.</p>
     */
    private Boolean passedReview;

    /**
     * <p>A <code>Integer</code> providing the submission placement based on review scores.</p>
     */
    private Integer placement;

    /**
     * <p>A <code>SoftwareSubmissionReviewDTO</code> providing the details for screening review.</p>
     */
    private SoftwareSubmissionReviewDTO screeningReview;

    /**
     * <p>A <code>long</code> providing the ID for the upload of submission.</p>
     *
     * @since 1.0.1
     */
    private long uploadId;
    
    /**
     * <p>A <code>String</code> providing the feedback for the checkpoint submission.</p>
     *
     * @since 1.0.2
     */
    private String checkpointFeedback;

    
    /**
     * <p>A <code>Float</code> providing the checkpoint review score for the checkpoint submission.</p>
     *
     * @since 1.0.2
     */
    private Double checkpointReviewScore;

    /**
     * <p>Constructs new <code>SoftwareSubmissionDTO</code> instance. This implementation does nothing.</p>
     */
    public SoftwareSubmissionDTO() {
    }

    /**
     * <p>Gets the flag indicating whether the submission passed review or not.</p>
     *
     * @return a <code>Boolean</code> providing the flag indicating whether the submission passed review or not.
     */
    public Boolean getPassedReview() {
        return this.passedReview;
    }

    /**
     * <p>Sets the flag indicating whether the submission passed review or not.</p>
     *
     * @param passedReview a <code>Boolean</code> providing the flag indicating whether the submission passed review or
     *                     not.
     */
    public void setPassedReview(Boolean passedReview) {
        this.passedReview = passedReview;
    }

    /**
     * <p>Gets the flag indicating whether the submission passed screening or not.</p>
     *
     * @return a <code>Boolean</code> providing the flag indicating whether the submission passed screening or not.
     */
    public Boolean getPassedScreening() {
        return this.passedScreening;
    }

    /**
     * <p>Sets the flag indicating whether the submission passed screening or not.</p>
     *
     * @param passedScreening a <code>Boolean</code> providing the flag indicating whether the submission passed
     *                        screening or not.
     */
    public void setPassedScreening(Boolean passedScreening) {
        this.passedScreening = passedScreening;
    }

    /**
     * <p>Gets the details for reviews for submission.</p>
     *
     * @return a <code>List</code> providing the details for reviews for submission.
     */
    public List<SoftwareSubmissionReviewDTO> getReviews() {
        return this.reviews;
    }

    /**
     * <p>Sets the details for reviews for submission.</p>
     *
     * @param reviews a <code>List</code> providing the details for reviews for submission.
     */
    public void setReviews(List<SoftwareSubmissionReviewDTO> reviews) {
        this.reviews = reviews;
    }

    /**
     * <p>Gets the details for the submitter.</p>
     *
     * @return a <code>UserDTO</code> providing the details for the submitter.
     */
    public UserDTO getSubmitter() {
        return this.submitter;
    }

    /**
     * <p>Sets the details for the submitter.</p>
     *
     * @param submitter a <code>UserDTO</code> providing the details for the submitter.
     */
    public void setSubmitter(UserDTO submitter) {
        this.submitter = submitter;
    }

    /**
     * <p>Gets the final score for submission.</p>
     *
     * @return a <code>Double</code> providing the final score for submission.
     */
    public Double getFinalScore() {
        return this.finalScore;
    }

    /**
     * <p>Sets the final score for submission.</p>
     *
     * @param finalScore a <code>Double</code> providing the final score for submission.
     */
    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * <p>Gets the initial score for submission.</p>
     *
     * @return a <code>Double</code> providing the initial score for submission.
     */
    public Double getInitialScore() {
        return this.initialScore;
    }

    /**
     * <p>Sets the initial score for submission.</p>
     *
     * @param initialScore a <code>Double</code> providing the initial score for submission.
     */
    public void setInitialScore(Double initialScore) {
        this.initialScore = initialScore;
    }

    /**
     * <p>Gets the screening score for submission.</p>
     *
     * @return a <code>Double</code> providing the screening score for submission.
     */
    public Double getScreeningScore() {
        return this.screeningScore;
    }

    /**
     * <p>Sets the screening score for submission.</p>
     *
     * @param screeningScore a <code>Double</code> providing the screening score for submission.
     */
    public void setScreeningScore(Double screeningScore) {
        this.screeningScore = screeningScore;
    }

    /**
     * <p>Gets the submission date.</p>
     *
     * @return a <code>Date</code> providing the submission date.
     */
    public Date getSubmissionDate() {
        return this.submissionDate;
    }

    /**
     * <p>Sets the submission date.</p>
     *
     * @param submissionDate a <code>Date</code> providing the submission date.
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     * <p>Gets the ID for submission.</p>
     *
     * @return a <code>long</code> providing the ID for submission.
     */
    public long getSubmissionId() {
        return this.submissionId;
    }

    /**
     * <p>Sets the ID for submission.</p>
     *
     * @param submissionId a <code>long</code> providing the ID for submission.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p>Gets the submission placement based on review scores.</p>
     *
     * @return a <code>Integer</code> providing the submission placement based on review scores.
     */
    public Integer getPlacement() {
        return this.placement;
    }

    /**
     * <p>Sets the submission placement based on review scores.</p>
     *
     * @param placement a <code>Integer</code> providing the submission placement based on review scores.
     */
    public void setPlacement(Integer placement) {
        this.placement = placement;
    }

    /**
     * <p>Gets the details for screening review.</p>
     *
     * @return a <code>SoftwareSubmissionReviewDTO</code> providing the details for screening review.
     */
    public SoftwareSubmissionReviewDTO getScreeningReview() {
        return this.screeningReview;
    }

    /**
     * <p>Sets the details for screening review.</p>
     *
     * @param screeningReview a <code>SoftwareSubmissionReviewDTO</code> providing the details for screening review.
     */
    public void setScreeningReview(SoftwareSubmissionReviewDTO screeningReview) {
        this.screeningReview = screeningReview;
    }

    /**
     * <p>Gets the ID for the upload of submission.</p>
     *
     * @return a <code>long</code> providing the ID for the upload of submission.
     * @since 1.0.1
     */
    public long getUploadId() {
        return this.uploadId;
    }

    /**
     * <p>Sets the ID for the upload of submission.</p>
     *
     * @param uploadId a <code>long</code> providing the ID for the upload of submission.
     * @since 1.0.1
     */
    public void setUploadId(long uploadId) {
        this.uploadId = uploadId;
    }

    /**
     * Sets the feedback for the checkpoint submission.
     * 
     * @param checkpointFeedback the feedback for the checkpoint submission to set
     * @since 1.0.2
     */
    public void setCheckpointFeedback(String checkpointFeedback) {
        this.checkpointFeedback = checkpointFeedback;
    }

    /**
     * Gets the feedback for the checkpoint submission.
     * 
     * @return the feedback for the checkpoint submission
     * @since 1.0.2
     */
    public String getCheckpointFeedback() {
        return checkpointFeedback;
    }

    /**
     * Sets the checkpoint review score for the checkpoint submission.
     * 
     * @param checkpointReviewScore the checkpoint review score for the checkpoint submission to set
     * @since 1.0.2
     */
    public void setCheckpointReviewScore(Double checkpointReviewScore) {
        this.checkpointReviewScore = checkpointReviewScore;
    }

    /**
     * Gets the checkpoint review score for the checkpoint submission.
     * 
     * @return the checkpoint review score for the checkpoint submission
     * @since 1.0.2
     */
    public Double getCheckpointReviewScore() {
        return checkpointReviewScore;
    }

}
