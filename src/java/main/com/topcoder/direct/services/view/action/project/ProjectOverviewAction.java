/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.contest.*;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectOverviewDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.service.project.ProjectData;
import com.topcoder.shared.util.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for viewing the
 * <code>Project Overview</code> page for requested project.
 * </p>
 * <p>
 * Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
 * <ul>
 * <li>Added {@link #setDashboardProjectStat()} and {@link #setDashboardContests()} method. Updated
 * {@link #execute()} method to set dashboard project data and dashboard contests data.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.0.2 - 	Cockpit Performance Improvement Project Overview and Manage Copilot Posting Change Note
 * <ul>
 * <li>Remove the logic of filter out upcoming activities because ProjectOverviewAction new uses the new action preprocessor
 * <code>UpcomingProjectActivitiesProcessor</code>.</li>
 * <li>Update the method setDashboardContests to use new method to get contests health data.</code>.</li>
 * </ul>
 * </p>
 *  <p>
 * Version 1.0.3 - 	TC Cockpit Bug Tracking R1 Cockpit Project Tracking version 1.0 Change Notes
 * <ul>
 * <li>Added codes to set issue tracking health status of contests.</li>
 * <li>Added codes to set unresolved issues number and ongoing bug races number of the project</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.0.4 - 	Direct Improvements Assembly Release 2 Change Note
 * <ul>
 * <li>Update the execute method to set the current project name.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.0.5 (TC Cockpit Contest Duration Calculation Updates Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Fixed typo in name of {@link DashboardHelper#setContestStatusColor(ContestHealthDTO)} method.</li>
 *   </ol>
 * </p>
 * @author isv, Veve, isv
 * @version 1.0.5
 */
public class ProjectOverviewAction extends AbstractAction implements FormAction<ProjectIdForm>,
                                                                     ViewAction<ProjectOverviewDTO> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(ProjectOverviewAction.class);

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * <p>A <code>ProjectOverviewDTO</code> providing the view data for displaying by <code>Project Overview</code>
     * view.</p>
     */
    private ProjectOverviewDTO viewData = new ProjectOverviewDTO();

    /**
     * <p>Constructs new <code>ProjectOverviewAction</code> instance. This implementation does nothing.</p>
     */
    public ProjectOverviewAction() {
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user..
     */
    public ProjectIdForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>Object</code> providing the collector for data to be rendered by the view mapped to this action.
     */
    public ProjectOverviewDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project requested for this action.</p>
     *
     * @return a <code>String</code> referencing the next view or action to route request to. This implementation
     *         returns {@link #SUCCESS} always.
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            getSessionData().setCurrentProjectContext(getViewData().getProjectStats().getProject());

            // set the current direct project id in session, the contest details
            // codes incorrectly
            // use setCurrentProjectContext to override the current chosen
            // direct project with current
            // chosen contest, for the safe, we put the direct project id into
            // session separately again
            getSessionData().setCurrentSelectDirectProjectID(
                    getSessionData().getCurrentProjectContext().getId());

            try {
                // set dashboard project status
                setDashboardProjectStat();

                // set dashboard contests
                setDashboardContests();

                // get project issues
                List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(getSessionData().getCurrentUserId(), formData.getProjectId());
                Map<ContestBriefDTO, ContestIssuesTrackingDTO> issues = DataProvider.getDirectProjectIssues(contests);

                int totalUnresolvedIssues = 0;
                int totalOngoingBugRaces = 0;

                // update dashboard health
                for(Map.Entry<ContestBriefDTO, ContestIssuesTrackingDTO> contestIssues : issues.entrySet()) {
                    ContestHealthDTO health = getViewData().getContests().get(contestIssues.getKey());

                    if (health != null) {
                        health.setUnresolvedIssuesNumber(contestIssues.getValue().getUnresolvedIssuesNumber());

                        // update health color
                        DashboardHelper.setContestStatusColor(health);
                    }

                    totalUnresolvedIssues += contestIssues.getValue().getUnresolvedIssuesNumber();
                    totalOngoingBugRaces += contestIssues.getValue().getUnresolvedBugRacesNumber();
                }

				// set the project name if it's not set yet
				for(ProjectBriefDTO project : getViewData().getUserProjects().getProjects()) {
					if(project.getId() == getSessionData().getCurrentProjectContext().getId()) {
						getSessionData().getCurrentProjectContext().setName(project.getName());
					}
				}

                viewData.getDashboardProjectStat().setUnresolvedIssuesNumber(totalUnresolvedIssues);
                viewData.getDashboardProjectStat().setOngoingBugRacesNumber(totalOngoingBugRaces);


            } catch (Exception e) {
                log.error("Project Overview error: ", e);
                return ERROR;
            }

            return SUCCESS;
        } else {
            return result;
        }
    }

    /**
     * Set dashboard project status data.
     * 
     * @throws Exception if any exception occurs
     */
    private void setDashboardProjectStat() throws Exception {

        List<ProjectData> tcDirectProjects = new ArrayList<ProjectData>();
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(formData.getProjectId());
        tcDirectProjects.add(projectData);

        List<EnterpriseDashboardProjectStatDTO> enterpriseProjectStats = DataProvider
                .getDirectProjectStats(tcDirectProjects, getSessionData().getCurrentUserId());
        viewData.setDashboardProjectStat(enterpriseProjectStats.get(0));
        DashboardHelper.setAverageContestDurationText(viewData.getDashboardProjectStat());
    }

    /**
     * Set dashboard contests data.
     * <p/>
     * <p>Updated in 1.0.2: use DateProvider.getProjectContestsHealth to get contest health data of all the active
     * and scheduled contests of this project.</p>
     *
     * @throws Exception if any exception occurs
     */
    private void setDashboardContests() throws Exception {

         Map<ContestBriefDTO, ContestHealthDTO> contests =
            DataProvider.getProjectContestsHealth(getSessionData().getCurrentUserId(), formData.getProjectId(), true);

        viewData.setContests(contests);
    }

}
