/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.SoftwareContestWinnerDTO;
import com.topcoder.direct.services.view.dto.UserDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;

import java.util.List;

/**
 * <p>A <code>DTO</code> class providing the data for displaying by <code>Software Contest Submissions</code> view.</p>
 *
 * <p>
 * Version 1.0.1 (Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getCopilotPostingWinner()} method.</li>
 *     <li>Added {@link #getCopilotPostingRunnerUp()} method.</li>
 *   </ol>
 * </p>
 *
 * <p>Version 1.0.2 (TC Direct Contest Dashboard Update Assembly) change notes:
 * - change to extend from BaseContestCommonDTO.
 * - remove ContestStatsDTO and corresponding get/set methods.
 * </p>
 *
 * <p>Version 1.0.3 (Release Assembly - TC Direct Cockpit Release Two) change notes:
  * - add property milestoneWinners to store the winner of the milestone round.
  * </p>
 *
 * <p>
 * Version 1.0.4 (Adding Contest Approval feature in Direct Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #showApproval} property.</li>
 *     <li>Added {@link #approvalCommitted} property.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.0.5 (Release Assembly - TopCoder Cockpit Software Milestone Management) Change notes:
 *   <ol>
 *     <li>Added {@link #milestonePrizeNumber} property.</li>
 *     <li>Added {@link #milestonePrizeAmount} property.</li>
 *     <li>Added {@link #milestoneSubmissionsGeneralFeedback} property.</li>
 *   </ol>
 * </p>
 *
 * @author isv, TCSASSEMBLER
 * @version 1.0.5
 */
public class SoftwareContestSubmissionsDTO extends BaseContestCommonDTO implements ProjectIdForm.Aware, 
                                                                                   ContestStatsDTO.Aware {

    /**
     * <p>A <code>long</code> providing the ID of software project.</p>
     */
    private long projectId;

    /**
     * <p>A <code>SoftwareContestWinnerDTO</code> providing the details for first place winner for contest.</p>
     */
    private SoftwareContestWinnerDTO firstPlaceWinner;

    /**
     * <p>A <code>SoftwareContestWinnerDTO</code> providing the details for second place winner for contest.</p>
     */
    private SoftwareContestWinnerDTO secondPlaceWinner;

    /**
     * <p>A <code>List</code> providing the details on submissions for contest.</p>
     */
    private List<SoftwareSubmissionDTO> submissions;

    /**
     * <p>A <code>List</code> providing the details for reviewers for this contest.</p>
     */
    private List<UserDTO> reviewers;

    /**
     * <p>A <code>boolean </code> to indicate whether to show spec review comments.</p>
     */
    private boolean showSpecReview;

    /**
     * The milestone winners of the contest.
     * @since 1.0.3
     */
    private List<SoftwareContestWinnerDTO> milestoneWinners;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the section for performing approval by user is to
     * be shown or not.</p>
     * 
     * @since 1.0.4
     */
    private boolean showApproval;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the approval scorecard is alrady submitted by
     * user.</p>
     * 
     * @since 1.0.4
     */
    private boolean approvalCommitted;
    
    /**
     * <p>The maximum number of milestone prize winners.</p>
     * 
     * @since 1.0.5
     */
    private int milestonePrizeNumber;
    
    /**
     * <p>The milestone prize amount.</p>
     * 
     * @since 1.0.5
     */
    private double milestonePrizeAmount;
    
    /**
     * <p>The milestone submissions general feedback.</p>
     * 
     * @since 1.0.5
     */
    private String milestoneSubmissionsGeneralFeedback;       

    /**
     * <p>Constructs new <code>SoftwareContestSubmissionsDTO</code> instance. This implementation does nothing.</p>
     */
    public SoftwareContestSubmissionsDTO() {
    }

    /**
     * <p>Gets the details for reviewers for this contest.</p>
     *
     * @return a <code>List</code> providing the details for reviewers for this contest.
     */
    public List<UserDTO> getReviewers() {
        return this.reviewers;
    }

    /**
     * <p>Sets the details for reviewers for this contest.</p>
     *
     * @param reviewers a <code>List</code> providing the details for reviewers for this contest.
     */
    public void setReviewers(List<UserDTO> reviewers) {
        this.reviewers = reviewers;
    }

    /**
     * <p>Gets the details on submissions for contest.</p>
     *
     * @return a <code>List</code> providing the details on submissions for contest.
     */
    public List<SoftwareSubmissionDTO> getSubmissions() {
        return this.submissions;
    }

    /**
     * <p>Sets the details on submissions for contest.</p>
     *
     * @param submissions a <code>List</code> providing the details on submissions for contest.
     */
    public void setSubmissions(List<SoftwareSubmissionDTO> submissions) {
        this.submissions = submissions;
    }

    /**
     * <p>Gets the details for second place winner for contest.</p>
     *
     * @return a <code>SoftwareContestWinnerDTO</code> providing the details for second place winner for contest.
     */
    public SoftwareContestWinnerDTO getSecondPlaceWinner() {
        return this.secondPlaceWinner;
    }

    /**
     * <p>Sets the details for second place winner for contest.</p>
     *
     * @param secondPlaceWinner a <code>SoftwareContestWinnerDTO</code> providing the details for second place winner
     *        for contest.
     */
    public void setSecondPlaceWinner(SoftwareContestWinnerDTO secondPlaceWinner) {
        this.secondPlaceWinner = secondPlaceWinner;
    }

    /**
     * <p>Gets the details for first place winner for contest.</p>
     *
     * @return a <code>SoftwareContestWinnerDTO</code> providing the details for first place winner for contest.
     */
    public SoftwareContestWinnerDTO getFirstPlaceWinner() {
        return this.firstPlaceWinner;
    }

    /**
     * <p>Sets the details for first place winner for contest.</p>
     *
     * @param firstPlaceWinner a <code>SoftwareContestWinnerDTO</code> providing the details for first place winner for
     *        contest.
     */
    public void setFirstPlaceWinner(SoftwareContestWinnerDTO firstPlaceWinner) {
        this.firstPlaceWinner = firstPlaceWinner;
    }

    /**
     * <p>Gets the ID of contest.</p>
     *
     * @return a <code>long</code> providing the ID of software project.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID of contest.</p>
     *
     * @param projectId a <code>long</code> providing the ID of software project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>Gets the ID of contest to get statistics for.</p>
     *
     * @return a <code>long</code> providing the ID of contest to get statistics for.
     */
    public long getContestId() {
        return getProjectId();
    }

    /**
     * <p>Gets the number of reviewers for requested contest.</p>
     *
     * @return an <code>int</code> providing the number of reviewers for software contest.
     */
    public int getReviewersCount() {
        if (this.reviewers == null) {
            return 0;
        } else {
            return this.reviewers.size();
        }
    }

    /**
     * <p>Gets the witdh (in percents) for columns with review scores.</p>
     *
     * @return an <code>int</code> providing the with (in percents) for single column for scores from single reviewer.
     */
    public int getReviewScoreColumnsWidth() {
        int count = getReviewersCount();
        if (count == 0) {
            count = 1;
        }
        return 30 / count;
    }

    /**
     * <p>Gets the winner of <code>Copilot Posting</code> contest. This method must be called only of the current
     * contest is of <code>Copilot Posting</code> type.</p>
     * 
     * @return a <code>SoftwareContestWinnerDTO</code> providing the details for winner of <code>Copilot Posting</code>
     *         contest.
     * @since 1.0.1 
     */
    public SoftwareContestWinnerDTO getCopilotPostingWinner() {
        List<SoftwareSubmissionDTO> copilotPostingSubmissions = getSubmissions();
        for (SoftwareSubmissionDTO submission : copilotPostingSubmissions) {
            List<SoftwareSubmissionReviewDTO> reviews = submission.getReviews();
            if (reviews != null) {
                for (SoftwareSubmissionReviewDTO review : reviews) {
                    if (review.getFinalScore() == 100F) {
                        SoftwareContestWinnerDTO winner = new SoftwareContestWinnerDTO();
                        winner.setFinalScore(review.getFinalScore());
                        winner.setPlacement(1);
                        winner.setHandle(submission.getSubmitter().getHandle());
                        winner.setId(submission.getSubmitter().getId());
                        return winner; 
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * <p>Gets the runner-up of <code>Copilot Posting</code> contest. This method must be called only of the current
     * contest is of <code>Copilot Posting</code> type.</p>
     * 
     * @return a <code>SoftwareContestWinnerDTO</code> providing the details for runner-up of 
     *         <code>Copilot Posting</code> contest.
     * @since 1.0.1 
     */
    public SoftwareContestWinnerDTO getCopilotPostingRunnerUp() {
        List<SoftwareSubmissionDTO> copilotPostingSubmissions = getSubmissions();
        for (SoftwareSubmissionDTO submission : copilotPostingSubmissions) {
            List<SoftwareSubmissionReviewDTO> reviews = submission.getReviews();
            if (reviews != null) {
                for (SoftwareSubmissionReviewDTO review : reviews) {
                    if (review.getFinalScore() == 80F) {
                        SoftwareContestWinnerDTO winner = new SoftwareContestWinnerDTO();
                        winner.setFinalScore(review.getFinalScore());
                        winner.setPlacement(2);
                        winner.setHandle(submission.getSubmitter().getHandle());
                        winner.setId(submission.getSubmitter().getId());
                        return winner; 
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the showSpecReview field.
     *
     * @return the showSpecReview
     */
    public boolean isShowSpecReview() {
        return showSpecReview;
    }

    /**
     * Set the showSpecReview field.
     *
     * @param showSpecReview the showSpecReview to set
     */
    public void setShowSpecReview(boolean showSpecReview) {
        this.showSpecReview = showSpecReview;
    }

    /**
     * Gets the milestone winners.
     *
     * @return the milestone winners.
     * @since 1.0.3
     */
    public List<SoftwareContestWinnerDTO> getMilestoneWinners() {
        return milestoneWinners;
    }

    /**
     * Sets the milestone winners.
     *
     * @param milestoneWinners the milestone winners.
     * @since 1.0.3
     */
    public void setMilestoneWinners(List<SoftwareContestWinnerDTO> milestoneWinners) {
        this.milestoneWinners = milestoneWinners;
    }

    /**
     * <p>Gets the flag indicating whether the section for performing approval by user is to be shown or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the section for performing approval by user
     *         is to be shown or not.
     * @since 1.0.4
     */
    public boolean getShowApproval() {
        return this.showApproval;
    }

    /**
     * <p>Sets the flag indicating whether the section for performing approval by user is to be shown or not.</p>
     *
     * @param showApproval a <code>boolean</code> providing the flag indicating whether the section for performing
     *                     approval by user is to be shown or not.
     * @since 1.0.4
     */
    public void setShowApproval(boolean showApproval) {
        this.showApproval = showApproval;
    }

    /**
     * <p>Gets the flag indicating whether the approval scorecard is alrady submitted by user.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the approval scorecard is alrady submitted
     *         by user.
     * @since 1.0.4
     */
    public boolean getApprovalCommitted() {
        return this.approvalCommitted;
    }

    /**
     * <p>Sets the flag indicating whether the approval scorecard is alrady submitted by user.</p>
     *
     * @param approvalCommitted a <code>boolean</code> providing the flag indicating whether the approval scorecard is
     *                          alrady submitted by user.
     * @since 1.0.4
     */
    public void setApprovalCommitted(boolean approvalCommitted) {
        this.approvalCommitted = approvalCommitted;
    }

    /**
     * Gets the maximum number of milestone prize winners.
     * 
     * @return the maximum number of milestone prize winners
     * @since 1.0.5
     */
    public int getMilestonePrizeNumber() {
        return milestonePrizeNumber;
    }

    /**
     * Sets the maximum number of milestone prize winners.
     * 
     * @param milestonePrizeNumber the maximum number of milestone prize winners to set
     * @since 1.0.5
     */
    public void setMilestonePrizeNumber(int milestonePrizeNumber) {
        this.milestonePrizeNumber = milestonePrizeNumber;
    }

    /**
     * Gets the milestone prize amount.
     * 
     * @return the milestone prize amount
     * @since 1.0.5
     */
    public double getMilestonePrizeAmount() {
        return milestonePrizeAmount;
    }

    /**
     * Sets the milestone prize amount.
     * 
     * @param milestonePrizeAmount the milestone prize amount to set
     * @since 1.0.5
     */
    public void setMilestonePrizeAmount(double milestonePrizeAmount) {
        this.milestonePrizeAmount = milestonePrizeAmount;
    }

    /**
     * Sets the milestone submissions general feedback.
     * 
     * @param milestoneSubmissionsGeneralFeedback the milestone submissions general feedback to set
     * @since 1.0.5
     */
    public void setMilestoneSubmissionsGeneralFeedback(String milestoneSubmissionsGeneralFeedback) {
        this.milestoneSubmissionsGeneralFeedback = milestoneSubmissionsGeneralFeedback;
    }

    /**
     * Gets the milestone submissions general feedback.
     * 
     * @return the milestone submissions general feedback
     * @since 1.0.5
     */
    public String getMilestoneSubmissionsGeneralFeedback() {
        return milestoneSubmissionsGeneralFeedback;
    }
}
