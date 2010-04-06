/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import com.topcoder.direct.services.view.dto.CoPilotStatsDTO;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.LatestActivitiesDTO;
import com.topcoder.direct.services.view.dto.UpcomingActivitiesDTO;

/**
 * <p>A DTO class providing the data to be displayed on <code>Dashboard</code> page view.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DashboardDTO extends CommonDTO implements CoPilotStatsDTO.Aware, LatestActivitiesDTO.Aware,
                                                       UpcomingActivitiesDTO.Aware {

    /**
     * <p>A <code>CoPilotStatsDTO</code> providing statistics on co-piloting projects and available co-pilots.</p>
     */
    private CoPilotStatsDTO coPilotStats;

    /**
     * <p>A <code>LatestActivitiesDTO</code> providing details on latest activities on projects associated with current
     * user.</p>
     */
    private LatestActivitiesDTO latestActivities;

    /**
     * <p>An <code>UpcomingActivitiesDTO</code> providing details on upcoming activities on projects associated with
     * current user.</p>
     */
    private UpcomingActivitiesDTO upcomingActivities;

    /**
     * <p>Constructs new <code>DashboardDTO</code> instance. This implementation does nothing.</p>
     */
    public DashboardDTO() {
    }

    /**
     * <p>Gets the statistics on co-piloting projects and available co-pilots.</p>
     *
     * @return a <code>CoPilotStatsDTO</code> providing statistics on co-piloting projects and available co-pilots.
     */
    public CoPilotStatsDTO getCoPilotStats() {
        return this.coPilotStats;
    }

    /**
     * <p>Sets the statistics on co-piloting projects and available co-pilots.</p>
     *
     * @param coPilotStats a <code>CoPilotStatsDTO</code> providing statistics on co-piloting projects and available
     *        co-pilots.
     */
    public void setCoPilotStats(CoPilotStatsDTO coPilotStats) {
        this.coPilotStats = coPilotStats;
    }

    /**
     * <p>Gets the details on latest activities for projects associated with current user.</p>
     *
     * @return a <code>LatestActivitiesDTO</code> providing the details on latest activities for projects associated
     *         with current user.
     */
    public LatestActivitiesDTO getLatestActivities() {
        return latestActivities;
    }

    /**
     * <p>Sets the details on latest activities for projects associated with current user.</p>
     *
     * @param latestActivities a <code>LatestActivitiesDTO</code> providing the details on latest activities for
     *        projects associated with current user.
     */
    public void setLatestActivities(LatestActivitiesDTO latestActivities) {
        this.latestActivities = latestActivities;
    }

    /**
     * <p>Gets the details on upcoming activities for projects associated with current user.</p>
     *
     * @return a <code>LatestActivitiesDTO</code> providing the details on latest activities for projects associated
     *         with current user.
     */
    public UpcomingActivitiesDTO getUpcomingActivities() {
        return upcomingActivities;
    }

    /**
     * <p>Sets the details on upcoming activities for projects associated with current user.</p>
     *
     * @param upcomingActivities a <code>LatestActivitiesDTO</code> providing the details on latest activities for
     *        projects associated with current user.
     */
    public void setUpcomingActivities(UpcomingActivitiesDTO upcomingActivities) {
        this.upcomingActivities = upcomingActivities;
    }
}
