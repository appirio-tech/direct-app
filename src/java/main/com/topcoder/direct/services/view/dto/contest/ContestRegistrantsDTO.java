/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.form.ContestIdForm;

import java.util.List;

/**
 * <p>A <code>DTO</code> class providing the data for displaying by <code>Contest Registrants</code> view.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestRegistrantsDTO extends CommonDTO implements ContestStatsDTO.Aware, ContestRegistrantDTO.Aware,
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
     * <p>A <code>List</code> providing the details for registrants for contest.</p>
     */
    private List<ContestRegistrantDTO> contestRegistrants;

    /**
     * <p>Constructs new <code>ContestRegistrantsDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestRegistrantsDTO() {
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
     * <p>Gets the list of registrants for requested contest.</p>
     *
     * @return a <code>ContestRegistrantDTO</code> providing the details on registrants for requested contest.
     */
    public List<ContestRegistrantDTO> getContestRegistrants() {
        return this.contestRegistrants;
    }

    /**
     * <p>Sets the list of registrants for requested contest.</p>
     *
     * @param registrants a <code>ContestRegistrantDTO</code> providing the details on registrants for requested
     *                    contest.
     */
    public void setContestRegistrants(List<ContestRegistrantDTO> registrants) {
        this.contestRegistrants = registrants;
    }
}
