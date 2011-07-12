/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.CommonDTO;

/**
 * <p>
 * This class is a simple <code>DTO</code> that holds the dashboard data & contest stats data.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Contest Dashboard Update Assembly 1.0
 */
public class BaseContestCommonDTO extends CommonDTO {
    /**
     * The dashboard data.
     */
    private ContestDashboardDTO dashboard;
    
    /**
     * <p>A <code>ContestStatsDTO </code> providing the statistics on contest.</p>
     */
    private ContestStatsDTO contestStats;

    /**
     * Gets the dashboard field.
     * 
     * @return the dashboard
     */
    public ContestDashboardDTO getDashboard() {
        return dashboard;
    }

    /**
     * Sets the dashboard field.
     *
     * @param dashboard the dashboard to set
     */
    public void setDashboard(ContestDashboardDTO dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * Gets the contestStats field.
     * 
     * @return the contestStats
     */
    public ContestStatsDTO getContestStats() {
        return contestStats;
    }

    /**
     * Sets the contestStats field.
     *
     * @param contestStats the contestStats to set
     */
    public void setContestStats(ContestStatsDTO contestStats) {
        this.contestStats = contestStats;
    }
    
}
