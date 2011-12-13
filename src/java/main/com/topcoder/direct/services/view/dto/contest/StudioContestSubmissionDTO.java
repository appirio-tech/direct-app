/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.form.ContestIdForm;
import com.topcoder.management.deliverable.Submission;

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
 * <p>
 * Version 1.2 (Direct Submission Viewer Release 4) change notes:
 * <ul>
 * <li>Added {@link #currentContest} private field and getter/setter for it.</li>
 * <li>Added {@link #hasCheckout} private field and getter/setter for it.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (TC Direct Release Assembly 7) change notes:
 * <ul>
 * <li>Added {@link #hasContestWritePermission} property with respective accessor/mutator methods.</li>
 * </ul>
 * </p>
 *
 * <p>Version 1.4 (TC Direct Contest Dashboard Update Assembly) change notes:
 * - change to extend from BaseContestCommonDTO.
 * - remove ContestStatsDTO and corresponding get/set methods.
 * </p>
 *
 * <p>
 *   Version 1.4 (TC Direct Replatforming Release 3) change notes:
 *   <ul>
 *     <li>Change the type of {@link #submission} from <code>SubmissionData</code> to <code>Submission</code>.</li>
 *     <li>Added {@link #feedbackText} property with respective accessor/mutator methods.</p>
 *   </ul>
 * </p>
 * 
 * <p>
 *   Version 1.5 (TC Direct Replatforming Release 5) change notes:
 *   <ul>
 *     <li>Added {@link #fonts} property with respective accessor/mutator methods.</li>
 *     <li>Added {@link #stockArts} property with respective accessor/mutator methods.</li>
 *   </ul>
 * </p>
 *
 * <p>
 *   Version 1.6 (Release Assembly - TopCoder Cockpit Submission Viewer Revamp) change notes:
 *   <ul>
 *     <li>Changed {@link # .</code>
 *   </ul>
 * </p>
 * 
 * @author isv, flexme, minhu
 * @since Submission Viewer Release 1 assembly
 * @version 1.6
 */
public class StudioContestSubmissionDTO extends BaseContestCommonDTO implements ContestStatsDTO.Aware, ContestIdForm.Aware {

    /**
     * <p>A <code>long</code> providing the ID of contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>Submission</code> providing the details for requested <code>Studio</code> submission.</p>
     */
    private Submission submission;

    /**
     * <p>A <code>submissionArtifacts</code> providing the artifacts (file names) of the submission</p>
     * @since 1.6
     */
    private List<String> submissionArtifacts;

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
     * <p>A <code>TypedContestBriefDTO</code> providing the details for current contest.</p>
     *
     * @since 1.3
     */
    private TypedContestBriefDTO currentContest;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the submissions have already been checked out.
     * </p>
     *
     * @since 1.3
     */
    private boolean hasCheckout;
    
    /**
     * <p>
     * A boolean used to represent whether user has write permission to this
     * contest.
     * </p>
     *
     * @since 1.3
     */
    private boolean hasContestWritePermission;

    /**
     * <p>
     * A <code>String</code> providing the client feedback of the submission.
     * </p>
     * 
     * @since 1.4
     */
    private String feedbackText;

    /**
     * <p>
     * A <code>boolean</code> represents whether the corresponding phase is open. If the phase is scheduled, user can't do
     * any operation.
     * </p>
     * 
     * @since 1.5
     */
    private boolean phaseOpen;

    /**
     * Represents the Fonts external contents of the submission.
     * 
     * @since 1.5
     */
    private List<Map<String, String>> fonts;
    
    /**
     * Represents the Stock Art external contents of the submission.
     * 
     * @since 1.5
     */
    private List<Map<String, String>> stockArts;
    
    /**
     * <p>Constructs new <code>StudioContestSubmissionDTO</code> instance. This implementation does nothing.</p>
     */
    public StudioContestSubmissionDTO() {
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
     * @return a <code>Submission</code> providing the details for requested <code>Studio</code> submission.
     */
    public Submission getSubmission() {
        return this.submission;
    }

    /**
     * <p>Sets the details for requested submission.</p>
     *
     * @param submission a <code>Submission</code> providing the details for requested <code>Studio</code>
     * submission.
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

     /**
     * <p>Get the submissionArtifacts.</p>
     *
     * @return the artifacts of the current submission
     * @since 1.6
     */
    public List<String> getSubmissionArtifacts() {
        return this.submissionArtifacts;
    }

    /**
     * <p>Sets the submissionArtifacts.</p>
     *
     * @param submissionArtifacts - submission Artifacts
     * @since 1.6
     */
    public void setSubmissionArtifacts(List<String> submissionArtifacts) {
        this.submissionArtifacts = submissionArtifacts;
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

    /**
     * <p>Gets the details for current contest.</p>
     *
     * @return a <code>TypedContestBriefDTO</code> providing the details for current contest.
     * @since 1.3
     */
    public TypedContestBriefDTO getCurrentContest() {
        return this.currentContest;
    }

    /**
     * <p>Sets the details for current contest.</p>
     *
     * @param currentContest a <code>TypedContestBriefDTO</code> providing the details for current contest.
     * @since 1.3
     */
    public void setCurrentContest(TypedContestBriefDTO currentContest) {
        this.currentContest = currentContest;
    }

    /**
     * <p>Gets the flag indicating whether the submissions have already been checked out.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the submissions have already been checked
     *         out.
     * @since 1.3
     */
    public boolean getHasCheckout() {
        return hasCheckout;
    }

    /**
     * <p>Sets the flag indicating whether the submissions have already been checked out.</p>
     *
     * @param hasCheckout a <code>boolean</code> providing the flag indicating whether the submissions have already been
     *        checked out.
     * @since 1.3
     */
    public void setHasCheckout(boolean hasCheckout) {
        this.hasCheckout = hasCheckout;
    }

    /**
     * Get hasContestWritePermission field.
     * 
     * @return the hasContestWritePermission
     * @since 1.3
     */
    public boolean isHasContestWritePermission() {
        return hasContestWritePermission;
    }

    /**
     * Set hasContestWritePermission field.
     * 
     * @param hasContestWritePermission
     *            the hasContestWritePermission to set
     * @since 1.3
     */
    public void setHasContestWritePermission(boolean hasContestWritePermission) {
        this.hasContestWritePermission = hasContestWritePermission;
    }

    /**
     * Gets the client feedback of the submission.
     * 
     * return the client feedback of the submission.
     * @since 1.4
     */
    public String getFeedbackText() {
        return feedbackText;
    }

    /**
     * Sets the client feedback of the submission.
     *
     * @param feedbackText the client feedback of the submission.
     * @since 1.4
     */
    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }
    
    /**
     * Gets whether the phase is open.
     *
     * @return true if the phase is open, false otherwise.
     * @since 1.5
     */
    public boolean isPhaseOpen() {
        return phaseOpen;
    }

    /**
     * Sets whether the phase is open.
     * 
     * @param phaseOpen true if the phase is open, false otherwise.
     * @since 1.5
     */
    public void setPhaseOpen(boolean phaseOpen) {
        this.phaseOpen = phaseOpen;
    }

    /**
     * Gets the Fonts external contents of the submission.
     * 
     * @return the Fonts external contents of the submission.
     * @since 1.5
     */
    public List<Map<String, String>> getFonts() {
        return fonts;
    }

    /**
     * Sets the Fonts external contents of the submission.
     * 
     * @param fonts the Fonts external contents of the submission.
     * @since 1.5
     */
    public void setFonts(List<Map<String, String>> fonts) {
        this.fonts = fonts;
    }

    /**
     * Gets the Fonts external contents of the submission.
     * 
     * @return the Fonts external contents of the submission.
     * @since 1.5
     */
    public List<Map<String, String>> getStockArts() {
        return stockArts;
    }

    /**
     * Sets the Fonts external contents of the submission.
     * 
     * @param stockArts the Fonts external contents of the submission.
     * @since 1.5
     */
    public void setStockArts(List<Map<String, String>> stockArts) {
        this.stockArts = stockArts;
    }
}
