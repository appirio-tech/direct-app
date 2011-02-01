/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.CommonDTO;
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
 * @author isv
 * @version 1.0.1 (Direct Software Submission Viewer assembly)
 */
public class SoftwareContestSubmissionsDTO extends CommonDTO implements ProjectIdForm.Aware, ContestStatsDTO.Aware {

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
     * <p>A <code>ContestStatsDTO </code> providing the statistics on contest.</p>
     */
    private ContestStatsDTO contestStats;
    
    /**
     * <p>A <code>boolean </code> to indicate whether to show spec review comments.</p>
     */
    private boolean showSpecReview;

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
}
