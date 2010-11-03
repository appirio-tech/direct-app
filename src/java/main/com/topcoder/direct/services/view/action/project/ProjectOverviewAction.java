/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.ActivityDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.dto.project.ProjectOverviewDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.service.project.ProjectData;

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
 * 
 * @author isv, TCSASSEMBLER
 * @version 1.0.1
 */
public class ProjectOverviewAction extends AbstractAction implements FormAction<ProjectIdForm>,
                                                                     ViewAction<ProjectOverviewDTO> {

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

            // remove irrelevant activities
            List<ActivityDTO> activities = new ArrayList<ActivityDTO>(viewData
                    .getUpcomingActivities().getActivities());
            viewData.getUpcomingActivities().getActivities().clear();
            for (ActivityDTO activityDTO : activities) {
                if (activityDTO.getContest().getProject().getId() == formData
                        .getProjectId()) {
                    viewData.getUpcomingActivities().getActivities().add(
                            activityDTO);
                }
            }
            
            try {
                // set dashboard project status
                setDashboardProjectStat();

                // set dashboard contests
                setDashboardContests();
            } catch (Exception e) {
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
                .getEnterpriseProjectStats(tcDirectProjects);
        viewData.setDashboardProjectStat(enterpriseProjectStats.get(0));
        DashboardHelper.setAverageConestDurationText(viewData.getDashboardProjectStat());
    }

    /**
     * Set dashboard contests data.
     * 
     * @throws Exception if any exception occurs
     */
    private void setDashboardContests() throws Exception {
        Map<ContestBriefDTO, ContestDashboardDTO> contests = new HashMap<ContestBriefDTO, ContestDashboardDTO>();
        ContestDashboardDTO contest;

        ProjectContestsListDTO projectContestsListDTO = DataProvider
                .getProjectContests(getSessionData().getCurrentUserId(),
                        formData.getProjectId());
        for (ProjectContestDTO projectContest : projectContestsListDTO
                .getContests()) {
            // just call all running and scheduled contest's query
            if (DashboardHelper.SCHEDULED.equalsIgnoreCase(projectContest.getStatus().getShortName())
                    || DashboardHelper.RUNNING.equalsIgnoreCase(projectContest.getStatus().getShortName())) {
                contest = DataProvider.getContestDashboardData(projectContest
                        .getContest().getId(), projectContest.getIsStudio(), true);

                DashboardHelper.setContestStatusColor(contest);
                contests.put(projectContest.getContest(), contest);
            }
        }

        viewData.setContests(contests);
    }

}
