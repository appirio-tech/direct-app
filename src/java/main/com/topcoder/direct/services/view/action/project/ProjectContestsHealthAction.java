/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
import com.topcoder.direct.services.view.dto.contest.ContestIssuesTrackingDTO;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.util.logging.Logger;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>An action to be used for servicing the requests for getting the details on contests health for selected 
 * <code>TC Direct Project</code>.</p>
 *  
 * @author isv
 * @version 1.0 (Project Health Update assembly)
 */
public class ProjectContestsHealthAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(ProjectContestsHealthAction.class);

    /**
     * <p>A <code>long</code> providing the ID of a project to get contests health for.</p>
     */
    private long projectId;

    /**
     * <p>Constructs new <code>ProjectContestsHealthAction</code> instance. This implementation does nothing.</p>
     */
    public ProjectContestsHealthAction() {
    }

    /**
     * <p>Gets the ID of a project to get contests health for.</p>
     *
     * @return a <code>long</code> providing the ID of a project to get contests health for.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID of a project to get contests health for.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a project to get contests health for.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p> This is the template method where the action logic will be performed by children classes. </p>
     *
     * @throws Exception if any error occurs
     */
    @Override
    protected void executeAction() {
        log.info("Started");
        try {
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

            // Retrieve the contests health for project
            log.info("Retrieving contests health data for project " + getProjectId());
            Map<ContestBriefDTO, ContestHealthDTO> contests =
               DataProvider.getProjectContestsHealth(tcSubject.getUserId(), getProjectId(), true);
            log.info("Retrieved contests health data for project " + getProjectId() 
                      + ", contests count = " + contests.size());

            // Update details for JIRA issues for contests health
            log.info("Retrieving issues from JIRA for project " + getProjectId());
            Map<ContestBriefDTO, ContestIssuesTrackingDTO> issues = DataProvider.getDirectProjectIssues(
                new ArrayList<ContestBriefDTO>(contests.keySet()));
            log.info("Retrieved issues from JIRA for project " + getProjectId() + ", issues count = " + issues.size());
            for (Map.Entry<ContestBriefDTO, ContestIssuesTrackingDTO> contestIssues : issues.entrySet()) {
                ContestHealthDTO health = contests.get(contestIssues.getKey());
                if (health != null) {
                    health.setUnresolvedIssuesNumber(contestIssues.getValue().getUnresolvedIssuesNumber());
                    DashboardHelper.setContestStatusColor(health);
                }
            }
            
            // Get dashboard data for each contest
            for (ContestBriefDTO contest : contests.keySet()) {
                ContestDashboardDTO contestDashboardData =
                    DataProvider.getContestDashboardData(contest.getId(), !contest.isSoftware(), false);
                contests.get(contest).setDashboardData(contestDashboardData);
            }

            setResult(contests);
        } catch (Exception e) {
            DirectUtils.getServletResponse().setStatus(500);
            log.error("Got an error", e);
        } finally {
            log.info("Finished");
        }
    }
}
