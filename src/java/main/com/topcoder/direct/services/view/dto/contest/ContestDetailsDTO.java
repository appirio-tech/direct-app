/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.form.ContestIdForm;

/**
 * <p>A <code>DTO</code> class providing the data for displaying by <code>Contest Details</code> view.</p>
 *
 * <p>
 * Version 1.0.1 (Direct Contest Dashboard Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #dashboard} property with respective accessor/mutator methods.</li>
 *   </ol>
 * </p>

 * @author isv
 * @version 1.0.1
 */
public class ContestDetailsDTO extends CommonDTO implements ContestStatsDTO.Aware, ContestDTO.Aware,
                                                            ContestIdForm.Aware {

    /**
     * <p>A <code>long</code> providing the ID of contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>ContestStatsDTO </code> providing the statistics on contest.</p>
     */
    private ContestStatsDTO contestStats;

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
     * <p>Constructs new <code>ContestDetailsDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestDetailsDTO() {
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
}
