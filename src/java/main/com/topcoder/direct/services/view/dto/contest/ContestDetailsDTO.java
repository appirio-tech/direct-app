/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.form.ContestIdForm;
import com.topcoder.marathonmatch.service.dto.CompetitorInfoDTO;
import com.topcoder.marathonmatch.service.dto.MMCommonInfoDTO;

import java.util.List;

/**
 * <p>A <code>DTO</code> class providing the data for displaying by <code>Contest Details</code> view.</p>
 *
 * <p>
 * Version 1.0.1 (Direct Contest Dashboard Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #dashboard} property with respective accessor/mutator methods.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.0.2 (TC Direct Release Assembly 7) Change notes:
 *   <ol>
 *     <li>Added {@link #hasContestWritePermission} property with respective accessor/mutator methods.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.0.3 (Direct Release 6 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Made the class the descendant of {@link BaseContestCommonDTO} and removed contesStats property.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.1 (PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress) Change notes:
 * <ol>
 *     <li>Add property {@link #commonInfo} to support the marathon match contest.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress -
 *              Dashboard and Submissions Tab) Change notes:
 * <ol>
 *     <li>Remove property {@link #commonInfo}.</li>
 *     <li>Update the class to extend <code>MarathonMatchCommonDTO</code></li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2.1 ([Bug Bounty] - TopCoder Direct Bug Fixes Round 1 issus#62)
 * <ul>
 *     <li>Add {@link #reviewType} and its setter/getter</li>
 * </ul>
 * </p>
 *
 * @author isv, TCSASSEMBLER, Ghost_141, deedee
 * @version 1.2.1
 */
public class ContestDetailsDTO extends MarathonMatchCommonDTO implements ContestStatsDTO.Aware, ContestDTO.Aware,
                                                            ContestIdForm.Aware {

    /**
     * <p>A <code>long</code> providing the ID of contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>ContestDTO</code> providing the details on contest.</p>
     */
    private ContestDTO contest;

    /**
     * <p>A <code>ContestDashboardDTO</code> providing the details for contest dashboard.</p>
     *
     * @since 1.0.1
     */
    private ContestDashboardDTO dashboard;

    /**
     * <p>
     * A boolean used to represent whether user has write permission to this
     * contest.
     * </p>
     *
     * @since 1.0.2
     */
    private boolean hasContestWritePermission;

    /**
     * <p>A <code>boolean </code> to indicate whether to show spec review comments.</p>
     */
    private boolean showSpecReview;

    /**
     * Review Type value
     *
     * @since 1.2.1
     */
    private String reviewType;

    /**
     * <p>Constructs new <code>ContestDetailsDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestDetailsDTO() {
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
     * <p>Gets the details on contest.</p>
     *
     * @return a <code>ContestDTO</code> providing the details on contest.
     */
    public ContestDTO getContest() {
        return this.contest;
    }

    /**
     * <p>Sets the details on contest.</p>
     *
     * @param contest a <code>ContestDTO</code> providing the details on contest.
     */
    public void setContest(ContestDTO contest) {
        this.contest = contest;
    }

    /**
     * <p>Gets the details for contest dashboard.</p>
     *
     * @return a <code>ContestDashboardDTO</code> providing the details for contest dashboard.
     * @since 1.0.1
     */
    public ContestDashboardDTO getDashboard() {
        return this.dashboard;
    }

    /**
     * <p>Sets the details for contest dashboard.</p>
     *
     * @param dashboard a <code>ContestDashboardDTO</code> providing the details for contest dashboard.
     * @since 1.0.1
     */
    public void setDashboard(ContestDashboardDTO dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * Get hasContestWritePermission field.
     *
     * @return the hasContestWritePermission
     * @since 1.0.2
     */
    public boolean isHasContestWritePermission() {
        return hasContestWritePermission;
    }

    /**
     * Set hasContestWritePermission field.
     *
     * @param hasContestWritePermission             the hasContestWritePermission to set
     * @since 1.0.2
     */
    public void setHasContestWritePermission(boolean hasContestWritePermission) {
        this.hasContestWritePermission = hasContestWritePermission;
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
     * Getter for #reviewType
     *
     * @return reviewType
     * @since 1.2.1
     */
    public String getReviewType() {
        return reviewType;
    }

    /**
     * Setter for #reviewType
     *
     * @param reviewType
     * @since 1.2.1
     */
    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }
}
