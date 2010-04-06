/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.UpcomingActivitiesDTO;

import java.io.Serializable;

/**
 * <p>A DTO class providing the data for <code>Project Overview</code> page view for single project.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProjectOverviewDTO extends CommonDTO implements Serializable, ProjectStatsDTO.Aware,
                                                             UpcomingActivitiesDTO.Aware,
                                                             LatestProjectActivitiesDTO.Aware {

    /**
     * <p>A <code>ProjectStatsDTO</code> providing statistics on project.</p>
     */
    private ProjectStatsDTO projectStats;

    /**
     * <p>An <code>UpcomingActivitiesDTO</code> providing details on upcoming activities on projects associated with
     * current user.</p>
     */
    private UpcomingActivitiesDTO upcomingActivities;

    /**
     * <p>A <code>LatestProjectActivitiesDTO</code> providing details on latest activities on requested project.</p>
     */
    private LatestProjectActivitiesDTO latestProjectActivities;

    /**
     * <p>Constructs new <code>ProjectOverviewDTO</code> instance. This implementation does nothing.</p>
     */
    public ProjectOverviewDTO() {
    }

    /**
     * <p>Gets the statistics for requested project.</p>
     *
     * @return a <code>ProjectStatsDTO</code> providing statistics on project.
     */
    public ProjectStatsDTO getProjectStats() {
        return this.projectStats;
    }

    /**
     * <p>Sets the statistics for single project.</p>
     *
     * @param projectStats a <code>ProjectStatsDTO</code> providing statistics on project.
     */
    public void setProjectStats(ProjectStatsDTO projectStats) {
        this.projectStats = projectStats;
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

    /**
     * <p>Gets the latest activities on requested project.</p>
     *
     * @return a <code>LatestProjectActivitiesDTO</code> providing details on latest activities on requested project.
     */
    public LatestProjectActivitiesDTO getLatestProjectActivities() {
        return this.latestProjectActivities;
    }

    /**
     * <p>Sets the latest activities on requested project.</p>
     *
     * @param latestProjectActivities a <code>LatestProjectActivitiesDTO</code> providing details on latest activities on
     *        requested project.
     */
    public void setLatestProjectActivities(LatestProjectActivitiesDTO latestProjectActivities) {
        this.latestProjectActivities = latestProjectActivities;
    }
}
