/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.form.ContestIdForm;
import com.topcoder.service.studio.SubmissionData;

/**
 * <p>A <code>DTO</code> class providing the data for displaying by <code>Studio Contest Submission</code> view.</p>
 *
 * <p>
 * Version 1.1 (Direct Submission Viewer Release 2) change notes:
 * <ul>
 * <li>Added {@link #prizeNumber} private field and getter/setter for it.</li>
 * </ul>
 * </p>
 *
 * @author isv, flexme
 * @since Submission Viewer Release 1 assembly
 * @version 1.1
 */
public class StudioContestSubmissionDTO extends CommonDTO implements ContestStatsDTO.Aware, ContestIdForm.Aware {

    /**
     * <p>A <code>long</code> providing the ID of contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>ContestStatsDTO </code> providing the statistics on contest.</p>
     */
    private ContestStatsDTO contestStats;

    /**
     * <p>A <code>SubmissionData</code> providing the details for requested <code>Studio</code> submission.</p>
     */
    private SubmissionData submission;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether contest has milestone round set or not.</p>
     */
    private boolean hasMilestoneRound;

    /**
     * <p>A <code>int</code> providing the total number of submissions for requested contest..</p>
     */
    private int submissionsCount;

    /**
     * <p>A <code>long</code> providing the ID of a submission which precedes requested submission in the total list of
     * submissions for requested contest.</p>
     */
    private long previousSubmissionId;

    /**
     * <p>A <code>long</code> providing the ID of a submission which follows after requested submission in the total
     * list of submissions for requested contest.</p>
     */
    private long nextSubmissionId;

    /**
     * <p>An <code>int</code> providing the number of the prize slots.</p> 
     */
    private int prizeNumber;

    /**
     * <p>Constructs new <code>StudioContestSubmissionDTO</code> instance. This implementation does nothing.</p>
     */
    public StudioContestSubmissionDTO() {
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
    public void setContestStats(ContestStatsDTO contestStats) {
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
     * <p>Gets the details for requested submission.</p>
     *
     * @return a <code>SubmissionData</code> providing the details for requested <code>Studio</code> submission.
     */
    public SubmissionData getSubmission() {
        return this.submission;
    }

    /**
     * <p>Sets the details for requested submission.</p>
     *
     * @param submission a <code>SubmissionData</code> providing the details for requested <code>Studio</code>
     * submission.
     */
    public void setSubmission(SubmissionData submission) {
        this.submission = submission;
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
     * @param hasMilestoneRound a <code>boolean</code> providing the flag indicating whether contest has milestone round
     * set or not.
     */
    public void setHasMilestoneRound(boolean hasMilestoneRound) {
        this.hasMilestoneRound = hasMilestoneRound;
    }

    /**
     * <p>Gets the total number of submissions for requested contest..</p>
     *
     * @return a <code>int</code> providing the total number of submissions for requested contest..
     */
    public int getSubmissionsCount() {
        return this.submissionsCount;
    }

    /**
     * <p>Sets the total number of submissions for requested contest..</p>
     *
     * @param submissionsCount a <code>int</code> providing the total number of submissions for requested contest..
     */
    public void setSubmissionsCount(int submissionsCount) {
        this.submissionsCount = submissionsCount;
    }

    /**
     * <p>Gets the ID of a submission which precedes requested submission in the total list of submissions for requested
     * contest.</p>
     *
     * @return a <code>long</code> providing the ID of a submission which precedes requested submission in the total
     *         list of submissions for requested contest.
     */
    public long getPreviousSubmissionId() {
        return this.previousSubmissionId;
    }

    /**
     * <p>Sets the ID of a submission which precedes requested submission in the total list of submissions for requested
     * contest.</p>
     *
     * @param previousSubmissionId a <code>long</code> providing the ID of a submission which precedes requested
     *        submission in the total list of.
     */
    public void setPreviousSubmissionId(long previousSubmissionId) {
        this.previousSubmissionId = previousSubmissionId;
    }

    /**
     * <p>Gets the ID of a submission which follows after requested submission in the total list of submissions for
     * requested contest.</p>
     *
     * @return a <code>long</code> providing the ID of a submission which follows after requested submission in the
     *         total list of submissions for requested contest.
     */
    public long getNextSubmissionId() {
        return this.nextSubmissionId;
    }

    /**
     * <p>Sets the ID of a submission which follows after requested submission in the total list of submissions for
     * requested contest.</p>
     *
     * @param nextSubmissionId a <code>long</code> providing the ID of a submission which follows after requested
     *        submission in the total list of submissions for requested contest.
     */
    public void setNextSubmissionId(long nextSubmissionId) {
        this.nextSubmissionId = nextSubmissionId;
    }

    /**
     * <p>Gets the number of the prize slots.</p>
     *
     * @return An <code>int</code> providing the number of the prize slots
     * @since 1.1
     */
    public int getPrizeNumber() {
        return prizeNumber;
    }

    /**
     * <p>Sets the number of the prize slots.</p>
     * 
     * @param prizeNumber An <code>int</code> providing the number of the prize slots
     * @since 1.1
     */
    public void setPrizeNumber(int prizeNumber) {
        this.prizeNumber = prizeNumber;
    }
}
