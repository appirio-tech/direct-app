/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * <p>
 * Version 1.1 Change notes:
 *   <ol>
 *     <li>Added {@link #submissionId} property.</li>
 *   </ol>
 * </p>
 * 
 * @author isv
 * @version 1.1
 */
public class ContestFinalFixDTO implements Serializable {

    private long contestId;

    private String finalFixerHandle;

    private long finalFixerUserId;

    private Date finalFixDate;

    private int versionNumber;

    private boolean approved;

    private Date finalReviewDate;

    private boolean reviewed;

    private String finalFixSub;

    private long uploadId;

    /**
     * <p>A <code>Long</code> providing the ID of submission for final fix.</p>
     * 
     * @since 1.1
     */
    private Long submissionId;

    public long getUploadId() {
        return uploadId;
    }

    public void setUploadId(long uploadId) {
        this.uploadId = uploadId;
    }

    public String getFinalFixSub() {
        return finalFixSub;
    }

    public void setFinalFixSub(String finalFixSub) {
        this.finalFixSub = finalFixSub;
    }

    public long getContestId() {
        return contestId;
    }

    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    public String getFinalFixerHandle() {
        return finalFixerHandle;
    }

    public void setFinalFixerHandle(String finalFixerHandle) {
        this.finalFixerHandle = finalFixerHandle;
    }

    public long getFinalFixerUserId() {
        return finalFixerUserId;
    }

    public void setFinalFixerUserId(long finalFixerUserId) {
        this.finalFixerUserId = finalFixerUserId;
    }

    public Date getFinalFixDate() {
        return finalFixDate;
    }

    public void setFinalFixDate(Date finalFixDate) {
        this.finalFixDate = finalFixDate;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Date getFinalReviewDate() {
        return finalReviewDate;
    }

    public void setFinalReviewDate(Date finalReviewDate) {
        this.finalReviewDate = finalReviewDate;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    /**
     * <p>Gets the ID of submission for final fix.</p>
     *
     * @return a <code>Long</code> providing the ID of submission for final fix.
     * @since 1.1
     */
    public Long getSubmissionId() {
        return this.submissionId;
    }

    /**
     * <p>Sets the ID of submission for final fix.</p>
     *
     * @param submissionId a <code>Long</code> providing the ID of submission for final fix.
     * @since 1.1
     */
    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }
}
