/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import java.util.Date;

/**
 * <p>
 * The statistics of the copilot posting submission.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - Cockpit Copilot Posting Skills Update and Submission Revamp)
 */
public class CopilotSubmissionStatDTO extends CopilotStatDTO {
    /**
     * The submission id.
     */
    private long submissionId;

    /**
     * The contest id.
     */
    private long contestId;

    /**
     * The submit time.
     */
    private Date submitTime;

    /**
     * Gets the submission id.
     *
     * @return the submission id.
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * Sets the submission id.
     *
     * @param submissionId the submission id.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * Gets the contest id.
     *
     * @return the contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id.
     *
     * @param contestId the contest id.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the submit time.
     *
     * @return the submit time.
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * Sets the submit time.
     *
     * @param submitTime the submit time.
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
}
