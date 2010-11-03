/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.io.Serializable;
import java.util.Map;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.UpcomingActivitiesDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;

/**
 * <p>
 * A DTO class providing the data for <code>Project Overview</code> page view
 * for single project.
 * </p>
 * <p>
 * Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
 * <ul>
 * <li>Added {@link #dashboardProjectStat} and {@link #contests} parameters. </li>
 * </ul>
 * </p>
 * 
 * @author isv, TCSASSEMBLER
 * @version 1.0.1
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
     * <p>
     * A <code>EnterpriseDashboardProjectStatDTO</code> providing statistics on
     * project.
     * </p>
     * 
     * @since 1.0.1
     */
    private EnterpriseDashboardProjectStatDTO dashboardProjectStat;

    /**
     * <p>
     * A map of <code>ContestBriefDTO</code> and
     * <code>ContestDashboardDTO</code> providing statistics on
     * contest.
     * </p>
     * 
     * @since 1.0.1
     */
    private Map<ContestBriefDTO, ContestDashboardDTO> contests;

    /**
     * <p>
     * Constructs new <code>ProjectOverviewDTO</code> instance. This
     * implementation does nothing.
     * </p>
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

    /**
     * Retrieves the dashboardProjectStat field.
     * 
     * @return the dashboardProjectStat
     * @since 1.0.1
     */
    public EnterpriseDashboardProjectStatDTO getDashboardProjectStat() {
        return dashboardProjectStat;
    }

    /**
     * Sets the dashboardProjectStat field.
     * 
     * @param dashboardProjectStat
     *            the dashboardProjectStat to set
     * @since 1.0.1
     */
    public void setDashboardProjectStat(
            EnterpriseDashboardProjectStatDTO dashboardProjectStat) {
        this.dashboardProjectStat = dashboardProjectStat;
    }

    /**
     * Retrieves the contests field.
     *
     * @return the contests
     * @since 1.0.1
     */
    public Map<ContestBriefDTO, ContestDashboardDTO> getContests() {
        return contests;
    }

    /**
     * Sets the contests field.
     *
     * @param contests the contests to set
     * @since 1.0.1
     */
    public void setContests(Map<ContestBriefDTO, ContestDashboardDTO> contests) {
        this.contests = contests;
    }
}
