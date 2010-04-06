/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.form.ContestIdForm;

/**
 * <p>A <code>DTO</code> class providing the data for displaying by <code>Contest Details</code> view.</p>
 *
 * @author isv
 * @version 1.0
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
}
