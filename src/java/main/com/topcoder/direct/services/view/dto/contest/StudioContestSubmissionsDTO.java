/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.form.ContestIdForm;
import com.topcoder.service.studio.SubmissionData;

import java.util.List;

/**
 * <p>A <code>DTO</code> class providing the data for displaying by <code>Studio Contest Submissions</code> view.</p>
 *
 * @author isv
 * @version 1.0 (Submission Viewer Release 1 assembly)
 */
public class StudioContestSubmissionsDTO extends CommonDTO implements ContestStatsDTO.Aware, ContestIdForm.Aware {

    /**
     * <p>A <code>long</code> providing the ID of contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>ContestStatsDTO </code> providing the statistics on contest.</p>
     */
    private ContestStatsDTO contestStats;

    /**
     * <p>A <code>List</code> listing the submissions for requested contest.</p>
     */
    private List<SubmissionData> contestSubmissions;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether contest has milestone round set or not.</p>
     */
    private boolean hasMilestoneRound;

    /**
     * <p>Constructs new <code>StudioContestSubmissionsDTO</code> instance. This implementation does nothing.</p>
     */
    public StudioContestSubmissionsDTO() {
    }

    /**
     * <p>Gets the statistics on contest.</p>
     *
     * @return a <code>ContestStatsDTO </code> providing the statistics on contest.
     */
    public ContestStatsDTO getContestStats() {
        return this.contestStats;
    }

    /**
     * <p>Sets the statistics on contest.</p>
     *
     * @param contestStats a <code>ContestStatsDTO </code> providing the statistics on contest.
     */
    public void setContestStats(ContestStatsDTO  contestStats) {
        this.contestStats = contestStats;
    }

    /**
     * <p>Gets the ID of contest.</p>
     *
     * @return a <code>long</code> providing the ID of contest.
     */
    public long getContestId() {
        return this.contestId;
    }

    /**
     * <p>Sets the ID of contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>Gets the list of submissions for contest.</p>
     *
     * @return a <code>List</code> listing the submissions for requested contest.
     */
    public List<SubmissionData> getContestSubmissions() {
        return contestSubmissions;
    }

    /**
     * <p>Sets the list of submissions for contest.</p>
     *
     * @param contestSubmissions a <code>List</code> listing the submissions for requested contest.
     */
    public void setContestSubmissions(List<SubmissionData> contestSubmissions) {
        this.contestSubmissions = contestSubmissions;
    }

    /**
     * <p>Gets the flag indicating whether contest has milestone round set or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether contest has milestone round set or not.
     */
    public boolean getHasMilestoneRound() {
        return this.hasMilestoneRound;
    }

    /**
     * <p>Sets the flag indicating whether contest has milestone round set or not.</p>
     *
     * @param hasMilestoneRound a <code>boolean</code> providing the flag indicating whether contest has milestone round set or not.
     */
    public void setHasMilestoneRound(boolean hasMilestoneRound) {
        this.hasMilestoneRound = hasMilestoneRound;
    }

    /**
     * <p>Gets the total number of submissions for requested contest.</p>
     *
     * @return an <code>int</code> providing the total number of submissions for requested contest.
     */
    public int getSubmissionsCount() {
        List<SubmissionData> submissions = getContestSubmissions();
        if (submissions != null) {
            return submissions.size();
        } else {
            return 0;
        }
    }
}
