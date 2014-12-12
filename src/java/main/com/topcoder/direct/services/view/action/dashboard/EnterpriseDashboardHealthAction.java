/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardStatusColor;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.service.project.ProjectData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>A Struts action to be used for handling requests for retrieving the data for <code>Enterprise Health</code> area
 * of <code>Enterprise Dashboard</code> view.</p>
 * 
 * @author isv
 * @version 1.0 (TC Cockpit Performance Improvement Enterprise Dashboard 1 assembly)
 */
public class EnterpriseDashboardHealthAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>List</code> providing the statistics for projects associated to current user.</p>
     */
    private List<EnterpriseDashboardProjectStatDTO> dashboardProjects;

    /**
     * <p>Constructs new <code>EnterpriseDashboardHealthAction</code> instance. This implementation does nothing.</p>
     */
    public EnterpriseDashboardHealthAction() {
    }

    /**
     * <p>Gets the statistics for projects associated to current user.</p>
     *
     * @return a <code>List</code> providing the statistics for projects associated to current user.
     */
    public List<EnterpriseDashboardProjectStatDTO> getDashboardProjects() {
        return this.dashboardProjects;
    }

    /**
     * <p>Sets the statistics for projects associated to current user.</p>
     *
     * @param dashboardProjects a <code>List</code> providing the statistics for projects associated to current
     *                                 user.
     */
    public void setDashboardProjects(List<EnterpriseDashboardProjectStatDTO> dashboardProjects) {
        this.dashboardProjects = dashboardProjects;
    }

    /**
     * <p>Handles the incoming request. Retrieves the data for <code>Enterprise Health</code> area and binds it to
     * request for consumption by further views or interceptors.</p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // Get the list of TC Direct Projects accessible to current user
        List<ProjectData> tcDirectProjects = getProjects();

        // Get the overall stats for user projects
        List<EnterpriseDashboardProjectStatDTO> enterpriseProjectStats 
            = DataProvider.getEnterpriseProjectStats(tcDirectProjects);
        sortEnterpriseDashboardProjectStatDTOByName(enterpriseProjectStats);
        setDashboardProjects(enterpriseProjectStats);

        // set projects status color
        setProjectStatusColor();
        
        setResult(getDashboardProjects());
    }

    /**
     * <p>Evaluates the current status of the projects and assigns respective color to project.</p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    private void setProjectStatusColor() throws Exception {
        for (EnterpriseDashboardProjectStatDTO project : getDashboardProjects()) {
            ProjectContestsListDTO contests = DataProvider.getProjectContests(
                getSessionData().getCurrentUserId(), project.getProject().getId());
            boolean hasRed = false;
            boolean hasOrange = false;

            for (ProjectContestDTO projectContest : contests.getContests()) {
                // just call all running and scheduled contest's query
                if (DashboardHelper.SCHEDULED.equalsIgnoreCase(projectContest.getStatus().getShortName())
                    || DashboardHelper.RUNNING
                    .equalsIgnoreCase(projectContest.getStatus().getShortName())) {
                    ContestDashboardDTO contest = DataProvider
                        .getContestDashboardData(projectContest.getContest().getId(), projectContest.getIsStudio(),
                                                 true);

                    DashboardHelper.setContestStatusColor(contest);
                    if (contest.getContestStatusColor() == DashboardStatusColor.RED) {
                        hasRed = true;
                        break;
                    } else if (contest.getContestStatusColor() == DashboardStatusColor.ORANGE) {
                        hasOrange = true;
                    }
                }
            }
            if (hasRed) {
                project.setProjectStatusColor(DashboardStatusColor.RED);
            } else if (hasOrange) {
                project.setProjectStatusColor(DashboardStatusColor.ORANGE);
            } else {
                project.setProjectStatusColor(DashboardStatusColor.GREEN);
            }
        }
    }

    /**
     * <p>Sorts the projects stats for enterprise dashboard.</p>
     * 
     * @param values a <code>List</code> of projects to sort. 
     */
    private void sortEnterpriseDashboardProjectStatDTOByName(List<EnterpriseDashboardProjectStatDTO> values) {
        Collections.sort(values, new Comparator<EnterpriseDashboardProjectStatDTO>() {
            public int compare(EnterpriseDashboardProjectStatDTO e1, EnterpriseDashboardProjectStatDTO e2) {
                if (e1.getProject().getName() == null) {
                    return -1;
                }
                if (e2.getProject().getName() == null) {
                    return 1;
                }

                String name1 = e1.getProject().getName().trim().toLowerCase();
                String name2 = e2.getProject().getName().trim().toLowerCase();
                return name1.compareTo(name2);
            }
        });
    }
}
