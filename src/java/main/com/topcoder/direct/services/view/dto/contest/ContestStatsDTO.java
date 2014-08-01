/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.service.project.CompetitionPrize;

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
public class ContestStatsDTO implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the statistics on desired contest.</p>
     */
    public static interface Aware {

        /**
         * <p>Gets the ID of contest to get statistics for.</p>
         *
         * @return a <code>long</code> providing the ID of contest to get statistics for.
         */
        long getContestId();

        /**
         * <p>Sets the statistics on requested contest.</p>
         *
         * @param stats a <code>ContestStatsDTO</code> providing the details on statistics for requested contest.
         */
        void setContestStats(ContestStatsDTO stats);

    }

    /**
     * <p>A <code>ContestBriefDTO</code> providing the basic details for contest.</p>
     */
    private ContestBriefDTO contest;

    /**
     * <p>A <code>Date</code> providing the start date and time for contest.</p>
     */
    private Date startTime;

    /**
     * <p>A <code>Date</code> providing the end date and time for contest.</p>
     */
    private Date endTime;

    /**
     * <p>A <code>int</code> providing the number of submissions for contest.</p>
     */
    private int submissionsNumber;

    /**
     * <p>A <code>int</code> providing the number of registrants for contest.</p>
     */
    private int registrantsNumber;

    /**
     * <p>A <code>int</code> providing the number of forum posts for contest.</p>
     */
    private int forumPostsNumber;

    /**
     * <p>A <code>long</code> providing the id of the contest forum.</p>
     */
    private long forumId;

    /**
     * Flag representing whether the forum is created after porting studio challenge to use software forum.
     *
     * @since 1.5
     */
    private boolean isNewForum;

    /**
     * The competition prizes.
     */
    private List<CompetitionPrize> prizes;

    /**
     * The admin fees
     */
    private Double adminFees;

    /**
     * Whether the contest is studio.
     */
    private Boolean isStudio;

    /**
     * The payment reference id.
     */
    private String paymentReferenceId;

    /**
     * The number of checkpoint submissions.
     * @since 1.1
     */
    private int checkpointSubmissionNumber;

    /**
     * The number of final submissions.
     * @since 1.1
     */
    private int finalSubmissionNumber;

    /**
     * Whether the contest is multiple round.
     * @since 1.1
     */
    private boolean isMultipleRound;

    /**
     * Whether current phase is in checkpoint submission or checkpoint review.
     * @since 1.2
     */
    private boolean inCheckpointSubmissionOrCheckpointReview;

    /**
     * The current status of the contest.
     */
    private String currentStatus;

    /**
     * <p>A <code>int</code> providing the number of jira issues and bug races for contest.</p>
     *
     * @since 1.0.1
     */
    private int totalJiraIssuesNumber;

    /**
     * <p>Provides the issues information which can be used the contest dashboard if needed</p>
     *
     * @since 1.0.2
     */
    private ContestIssuesTrackingDTO issues;

    /**
     * <p>A <code>String</code> providing the SVN module of the contest.</p>
     */
    private String svn;

    /**
     * <p>Shows if it shows the spec review or not.</p>
     */
    private boolean showSpecReview;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the Final Fixes tab is to be displayed on the
     * page or not.</p>
     * 
     * @since 1.3
     */
    private boolean showStudioFinalFixTab;

    /**
     * <p>An <code>int</code> providing the number of active VM's associated to the contest.</p>
     *
     * @since 1.4
     */
    private int totalActiveVMNumber;

    /**
     * <p>Constructs new <code>ContestStatsDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestStatsDTO() {
        this.isStudio = true;
    }

    public boolean isDraft() {
        return this.currentStatus.toLowerCase().indexOf("draft") != -1;
    }

    public boolean isSoftwareContestCompleted() {
        return !isStudio && this.currentStatus.toLowerCase().indexOf("completed") != -1;
    }


    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * <p>Gets the number of submissions for contest.</p>
     *
     * @return a <code>int</code> providing the number of submissions for contest.
     */
    public int getSubmissionsNumber() {
        return this.submissionsNumber;
    }

    /**
     * <p>Sets the number of submissions for contest.</p>
     *
     * @param submissionsNumber a <code>int</code> providing the number of submissions for contest.
     */
    public void setSubmissionsNumber(int submissionsNumber) {
        this.submissionsNumber = submissionsNumber;
    }

    /**
     * <p>Gets the number of registrants for contest.</p>
     *
     * @return a <code>int</code> providing the number of registrants for contest.
     */
    public int getRegistrantsNumber() {
        return this.registrantsNumber;
    }

    /**
     * <p>Sets the number of registrants for contest.</p>
     *
     * @param registrantsNumber a <code>int</code> providing the number of registrants for contest.
     */
    public void setRegistrantsNumber(int registrantsNumber) {
        this.registrantsNumber = registrantsNumber;
    }

    /**
     * <p>Gets the number of forum posts for contest.</p>
     *
     * @return a <code>int</code> providing the number of forum posts for contest.
     */
    public int getForumPostsNumber() {
        return this.forumPostsNumber;
    }

    /**
     * <p>Sets the number of forum posts for contest.</p>
     *
     * @param forumPostsNumber a <code>int</code> providing the number of forum posts for contest.
     */
    public void setForumPostsNumber(int forumPostsNumber) {
        this.forumPostsNumber = forumPostsNumber;
    }

    /**
     * <p>Gets the id of forum for contest.</p>
     *
     * @return a <code>long</code> providing the id of forum for contest.
     */
    public long getForumId() {
        return this.forumId;
    }

    /**
     * <p>Sets the id of forum for contest.</p>
     *
     * @param forumId a <code>long</code> providing the id of forum for contest.
     */
    public void setForumId(long forumId) {
        this.forumId = forumId;
    }

    /**
     * Gets the flag to tell whether the forum is created after porting.
     *
     * @return true if yes, false otherwise
     * @since 1.5
     */
    public boolean isNewForum() {
        return isNewForum;
    }

    /**
     * Sets the flag of isNewForum
     *
     * @param isNewForum the flag
     * @since 1.5
     */
    public void setNewForum(boolean isNewForum) {
        this.isNewForum = isNewForum;
    }

    /**
     * <p>Gets the end date and time for contest.</p>
     *
     * @return a <code>Date</code> providing the end date and time for contest.
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * <p>Sets the end date and time for contest.</p>
     *
     * @param endTime a <code>Date</code> providing the end date and time for contest.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    /**
     * <p>Gets the start date and time for contest.</p>
     *
     * @return a <code>Date</code> providing the start date and time for contest.
     */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * <p>Sets the start date and time for contest.</p>
     *
     * @param startTime a <code>Date</code> providing the start date and time for contest.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * <p>Gets the basic details for contest.</p>
     *
     * @return a <code>ContestBriefDTO</code> providing the basic details for contest.
     */
    public ContestBriefDTO getContest() {
        return this.contest;
    }

    /**
     * <p>Sets the basic details for contest.</p>
     *
     * @param contest a <code>ContestBriefDTO</code> providing the basic details for contest.
     */
    public void setContest(ContestBriefDTO contest) {
        this.contest = contest;
    }

    public Boolean getIsStudio() {
        return isStudio;
    }

    public void setIsStudio(Boolean isStudio) {
        this.isStudio = isStudio;
    }

    public void setPrizes(List<CompetitionPrize> prizes){
        this.prizes = prizes;
    }

    public List<CompetitionPrize> getPrizes(){
        return prizes;
    }

    public void setAdminFees(Double adminFees){
        this.adminFees = adminFees;
    }

    public Double getAdminFees(){
        if(adminFees == null){
            return 0.0;
        }
        return adminFees;
    }

    public Double getTotalMainPrizes(){
        Double totalMainPrizes = 0.0;
        for (int i=0; i < prizes.size(); i++ ) {
           totalMainPrizes += prizes.get(i).getAmount() ;
        }
        return totalMainPrizes;
    }

    public void setPaymentReferenceId(String paymentReferenceId){
        this.paymentReferenceId = paymentReferenceId;
    }

    public String getPaymentReferenceId(){
        return paymentReferenceId;
    }

    /**
     * Gets the svn module.
     *
     * @return the svn module.
     */
    public String getSvn() {
        return svn;
    }

    /**
     * Sets the svn module.
     *
     * @param svn the svn module.
     */
    public void setSvn(String svn) {
        this.svn = svn;
    }

    /**
     * <p>Gets the number of the active VM instances associated to the contest.</p>
     *
     * @return tht total number of active VM instances associated to the contest.
     * @since 1.4
     */
    public int getTotalActiveVMNumber() {
        return totalActiveVMNumber;
    }

    /**
     * <p>Sets the total number of active VM instances associated to the contest.</p>
     *
     * @param totalActiveVMNumber the number of active VM instances.
     * @since 1.4
     */
    public void setTotalActiveVMNumber(int totalActiveVMNumber) {
        this.totalActiveVMNumber = totalActiveVMNumber;
    }

    /**
     * <p>Set if show spec review.</p>
     *
     * @return the showSpecReview
     */
    public boolean isShowSpecReview() {
        return showSpecReview;
    }

    /**
     * <p>Sets the showSpecReview.</p>
     *
     * @param showSpecReview the showSpecReview to set
     */
    public void setShowSpecReview(boolean showSpecReview) {
        this.showSpecReview = showSpecReview;
    }

    /**
     * Gets the number of checkpoint submissions.
     *
     * @return the number of checkpoint submissions.
     * @since 1.1
     */
    public int getCheckpointSubmissionNumber() {
        return checkpointSubmissionNumber;
    }

    /**
     * Sets the number of checkpoint submissions.
     *
     * @param checkpointSubmissionNumber the number of checkpoint submissions.
     * @since 1.1
     */
    public void setCheckpointSubmissionNumber(int checkpointSubmissionNumber) {
        this.checkpointSubmissionNumber = checkpointSubmissionNumber;
    }

    /**
     * Gets the number of final submissions.
     *
     * @return the number of final submissions.
     * @since 1.1
     */
    public int getFinalSubmissionNumber() {
        return finalSubmissionNumber;
    }

    /**
     * Sets the number of final submissions.
     *
     * @param finalSubmissionNumber
     * @since 1.1
     */
    public void setFinalSubmissionNumber(int finalSubmissionNumber) {
        this.finalSubmissionNumber = finalSubmissionNumber;
    }

    /**
     * Gets whether the contest is studio.
     *
     * @return studio the studio flag.
     * @since 1.1
     */
    public Boolean getStudio() {
        return isStudio;
    }

    /**
     * Sets whether the contest is studio.
     *
     * @param studio the studio flag.
     * @since 1.1
     */
    public void setStudio(Boolean studio) {
        isStudio = studio;
    }

    /**
     * Gets whether the contest is multiple rounds.
     *
     * @return whether the contest is multiple rounds.
     * @since 1.1
     */
    public boolean isMultipleRound() {
        return isMultipleRound;
    }

    /**
     * Sets whether the contest is multiple rounds.
     *
     * @param multipleRound whether the contest is multiple rounds.
     * @since 1.1
     */
    public void setMultipleRound(boolean multipleRound) {
        isMultipleRound = multipleRound;
    }

    /**
     * Sets the flag that indicates whether current phase is in checkpoint submission or checkpoint review.
     * 
     * @param inCheckpointSubmissionOrCheckpointReview the flag that indicates whether current phase is
     *      in checkpoint submission or checkpoint review
     * @since 1.2
     */
    public void setInCheckpointSubmissionOrCheckpointReview(boolean inCheckpointSubmissionOrCheckpointReview) {
        this.inCheckpointSubmissionOrCheckpointReview = inCheckpointSubmissionOrCheckpointReview;
    }

    /**
     * Gets the flag that indicates whether current phase is in checkpoint submission or checkpoint review.
     * 
     * @return the flag that indicates whether current phase is in checkpoint submission or checkpoint review
     * @since 1.2
     */
    public boolean isInCheckpointSubmissionOrCheckpointReview() {
        return inCheckpointSubmissionOrCheckpointReview;
    }

    /**
     * <p>Gets the flag indicating whether the Final Fixes tab is to be displayed on the page or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the Final Fixes tab is to be displayed on
     *         the page or not.
     * @since 1.3
     */
    public boolean getShowStudioFinalFixTab() {
        return this.showStudioFinalFixTab;
}

    /**
     * <p>Sets the flag indicating whether the Final Fixes tab is to be displayed on the page or not.</p>
     *
     * @param showStudioFinalFixTab a <code>boolean</code> providing the flag indicating whether the Final Fixes tab is
     * to be displayed on the page or not.
     * @since 1.3
     */
    public void setShowStudioFinalFixTab(boolean showStudioFinalFixTab) {
        this.showStudioFinalFixTab = showStudioFinalFixTab;
    }
}
