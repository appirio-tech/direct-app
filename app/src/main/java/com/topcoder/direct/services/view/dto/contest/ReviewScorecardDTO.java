/*
 * Copyright (C) 2010 - 2016 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * <p>A DTO providing the statistics on requested contest.</p>
 *
 * <p>
 * Version 1.0.1 Change notes (TC Cockpit Bug Tracking R1 Contest Tracking assembly):
 * - Add property totalJiraIssuesNumber.
 *
 * Version 1.0.2 Change notes (TC Cockpit Bug Tracking R1 Cockpit Project Tracking assembly):
 * - Add property issues.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Direct Cockpit Release One) changes:
 * - Add property checkpointSubmissionNumber, finalSubmissionNumber and isMultipleRound
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) changes:
 * - Add property {@link #inCheckpointSubmissionOrCheckpointReview}.
 * </p>
 *
 * <p>
 * Version 1.3 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #showStudioFinalFixTab} property.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Direct VM Instances Management) changes:
 * - Add property {@link #totalActiveVMNumber}.
 * </p>
 *
 * <p>
 * Version 1.5 (Release Assembly - Port Design Challenge Forum to use Dev Forum) changes:
 * - Add property {@link #isNewForum} and its getter and setter.
 * </p>
 *
 * <p>
 * Version 1.6 (TopCoder Direct - Update jira issues retrieval to Ajax) @author -jacob- @challenge 30044583
 * - Removed issues related fields.
 * </p>
 *
 * @author isv, Veve, jiajizhou86, -jacob-
 * @version 1.6
 */
public class ReviewScorecardDTO implements Serializable {
    /**
     * <p>A <code>Date</code> providing the start date and time for contest.</p>
     */
    private long scorecardTypeId;

    /**
     * <p>A <code>Date</code> providing the end date and time for contest.</p>
     */
    private long projectCategoryId;

    /**
     * <p>A <code>int</code> providing the number of submissions for contest.</p>
     */
    private long scorecardId;

    /**
     * <p>A <code>int</code> providing the number of registrants for contest.</p>
     */
    private String scorecardName;

    private String scorecardVersion;

    /**
     * <p>Constructs new <code>ContestStatsDTO</code> instance. This implementation does nothing.</p>
     */
    public ReviewScorecardDTO() {
        // this.isStudio = true;
    }

    public long getScorecardTypeId() {
        return scorecardTypeId;
    }

    public void setScorecardTypeId(long scorecardTypeId) {
        this.scorecardTypeId = scorecardTypeId;
    }

    public long getProjectCategoryId() {
        return projectCategoryId;
    }

    public void setProjectCategoryId(long projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    public long getScorecardId() {
        return scorecardId;
    }

    public void setScorecardId(long scorecardId) {
        this.scorecardId = scorecardId;
    }

    public String getScorecardName() {
        return scorecardName;
    }

    public void setScorecardName(String scorecardName) {
        this.scorecardName = scorecardName;
    }

    public String getScorecardVersion() {
        return scorecardVersion;
    }

    public void setScorecardVersion(String scorecardVersion) {
        this.scorecardVersion = scorecardVersion;
    }
}
