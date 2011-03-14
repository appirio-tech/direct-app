/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.project.ProjectOverviewDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.service.project.ProjectData;

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
 *
 * </p>
 * 
 * @author isv, TCSASSEMBLER
 * @version 1.0.2
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
                .getDirectProjectStats(tcDirectProjects, getSessionData().getCurrentUserId());
        viewData.setDashboardProjectStat(enterpriseProjectStats.get(0));
        DashboardHelper.setAverageConestDurationText(viewData.getDashboardProjectStat());
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

        viewData.setContests(contests);

    }

}
