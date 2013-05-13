/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
import com.topcoder.direct.services.view.dto.contest.ContestIssuesTrackingDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.util.logging.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>An action to be used for servicing the requests for getting the details on contests health for selected 
 * <code>TC Direct Project</code>.</p>
 *
 * <p>
 * Version 1.1 (BUGR-8693 TC Cockpit Add active bug races of project to the project overview page)
 * <ul>
 *     <li>Adds property {@link #activeBugRaces} and its getter</li>
 *     <li>Updates method {@link #executeAction()} to include project bug races into the project contests health data</li>
 * </ul>
 * </p>
 *  
 * @author isv, Veve
 * @version 1.1
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
     * <p>The list of active bug races of the project</p>
     *
     * @since 1.1
     */
    private List<TcJiraIssue> activeBugRaces;

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
     * Gets the active bug races of the project.
     *
     * @return the list of active bug races.
     * @since 1.1
     */
    public List<TcJiraIssue> getActiveBugRaces() {
        return activeBugRaces;
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
            TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();

            // Retrieve the contests health for project
            log.info("Retrieving contests health data for project " + getProjectId());
            Map<ContestBriefDTO, ContestHealthDTO> contests =
               DataProvider.getProjectContestsHealth(currentUser.getUserId(), getProjectId(), true);
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

            // get project bug races in two steps
            final String activeBugRaceStatusFilter = "status = Open OR status = \"In Progress\" OR status = Reopened";

            // 1) get contest level bug races of this project
            final ProjectContestsListDTO projectContestsDTO = DataProvider.getProjectContests(currentUser.getUserId(), getProjectId());
            List<ProjectContestDTO> projectContests = projectContestsDTO.getContests();
            Set<Long> projectContestIds = new HashSet<Long>();
            for (ProjectContestDTO contest : projectContests) {
                projectContestIds.add(contest.getContest().getId());
            }
            List<TcJiraIssue> bugRaces = JiraRpcServiceWrapper.getBugRaceForDirectProject(projectContestIds, activeBugRaceStatusFilter);

            // 2) get project level bug races of this project
            bugRaces.addAll(JiraRpcServiceWrapper.getBugRacesForDirectProject(getProjectId(), activeBugRaceStatusFilter));

            this.activeBugRaces = bugRaces;

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
