/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest.studio;

import com.topcoder.management.deliverable.Submission;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A DTO representing a single <code>Final Fix</code> submission for <code>Studio</code> contest.</p>
 * 
 * @author isv
 * @version 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One)
 */
public class StudioFinalFix implements Serializable {

    /**
     * <p>A <code>Submission</code> providing the details for Final Fix submission.</p>
     */
    private Submission submission;

    /**
     * <p>A <code>List</code> providing the requirements for Final Fix submission.</p>
     */
    private List<Comment> comments;

    /**
     * <p>A <code>String</code> providing the additional comment for Final Fix submission.</p>
     */
    private String additionalComment;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the respective Final Fix/Final Review round is
     * finished or not.</p>
     */
    private boolean committed;

    /**
     * <p>Constructs new <code>StudioFinalFix</code> instance. This implementation does nothing.</p>
     */
    public StudioFinalFix() {
    }

    /**
     * <p>Gets the additional comment for Final Fix submission.</p>
     *
     * @return a <code>String</code> providing the additional comment for Final Fix submission.
     */
    public String getAdditionalComment() {
        return this.additionalComment;
    }

    /**
     * <p>Sets the additional comment for Final Fix submission.</p>
     *
     * @param additionalComment a <code>String</code> providing the additional comment for Final Fix submission.
     */
    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    /**
     * <p>Gets the requirements for Final Fix submission.</p>
     *
     * @return a <code>List</code> providing the requirements for Final Fix submission.
     */
    public List<Comment> getComments() {
        return this.comments;
    }

    /**
     * <p>Sets the requirements for Final Fix submission.</p>
     *
     * @param comments a <code>List</code> providing the requirements for Final Fix submission.
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * <p>Gets the details for Final Fix submission.</p>
     *
     * @return a <code>Submission</code> providing the details for Final Fix submission.
     */
    public Submission getSubmission() {
        return this.submission;
    }

    /**
     * <p>Sets the details for Final Fix submission.</p>
     *
     * @param submission a <code>Submission</code> providing the details for Final Fix submission.
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    /**
     * <p>Gets the flag indicating whether the respective Final Fix/Final Review round is finished or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the respective Final Fix/Final Review round
     *         is finished or not.
     */
    public boolean getCommitted() {
        return this.committed;
    }

    /**
     * <p>Sets the flag indicating whether the respective Final Fix/Final Review round is finished or not.</p>
     *
     * @param committed a <code>boolean</code> providing the flag indicating whether the respective Final Fix/Final
     * Review round is finished or not.
     */
    public void setCommitted(boolean committed) {
        this.committed = committed;
    }
}
