/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>A <code>DTO</code> class providing the data for single software contest winner.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Direct Software Submission Viewer assembly)
 */
public class SoftwareContestWinnerDTO extends UserDTO {

    /**
     * <p>A <code>int</code> providing the placement for the contest winner.</p>
     */
    private int placement;

    /**
     * <p>A <code>double</code> providing the final score assigned to winner's submission.</p>
     */
    private double finalScore;

    /**
     * <p>A <code>long</code> providing the ID of a software project.</p>
     */
    private long projectId;

    /**
     * <p>A <code>long</code> providing the ID of the winner submission</p>
     */
    private long submissionId;

    /**
     * <p>Constructs new <code>SoftwareContestWinnerDTO</code> instance. This implementation does nothing.</p>
     */
    public SoftwareContestWinnerDTO() {
    }

    /**
     * <p>Gets the ID of a software project.</p>
     *
     * @return a <code>long</code> providing the ID of a software project.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID of a software project.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a software project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>Gets the final score assigned to winner's submission.</p>
     *
     * @return a <code>double</code> providing the final score assigned to winner's submission.
     */
    public double getFinalScore() {
        return this.finalScore;
    }

    /**
     * <p>Sets the final score assigned to winner's submission.</p>
     *
     * @param finalScore a <code>double</code> providing the final score assigned to winner's submission.
     */
    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * <p>Gets the placement for the contest winner.</p>
     *
     * @return a <code>int</code> providing the placement for the contest winner.
     */
    public int getPlacement() {
        return this.placement;
    }

    /**
     * <p>Sets the placement for the contest winner.</p>
     *
     * @param placement a <code>int</code> providing the placement for the contest winner.
     */
    public void setPlacement(int placement) {
        this.placement = placement;
    }


     /**
     * <p>Gets the ID of a software project.</p>
     *
     * @return a <code>long</code> providing the ID of the submission.
     */
    public long getSubmissionId() {
        return this.submissionId;
    }

    /**
     * <p>Sets the ID of a software project.</p>
     *
     * @param projectId a <code>long</code> providing the ID of the submission.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    public String getPlacementStr() {
        return DirectUtils.ordinal(getPlacement());
    }
}
