/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Project Contests</code> page
 * for requested project.</p>
 * <p/>
 * <p>
 * Version 1.1 - Release Assembly - 24hrs TopCoder Cockpit Project Contests Calendar View change:
 * - Add getter to return current date on server
 * </p>
 * <p/>
 * <p>
 * Version 1.2 - TopCoder Cockpit - Bug Race Project Contests View changes:
 * - Add bug races as project contests to contest list view.
 * </p>
 * <p/>
 * <p>
 * Version 1.3 - Release Assembly - TopCoder Cockpit Project Contest Results
 * Export Part 1
 * - Add checking for showing contest download panel
 * </p>
 * <p/>
 * <p>
 * Version 1.4 - Release Assembly - TC Direct Issue Tracking Tab Update Assembly 3
 * - Added logic to display the direct project bugs.
 * </p>
 * <p>
 * Version 1.5 (BUGR-8694 TC Cockpit Add project bug races to the project game plan and project contests page)
 * <ul>
 *   <li>Add project level bug races to active contests and project contests page</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.6 (TopCoder Direct - Update jira issues retrieval to Ajax) @author -jacob- @challenge 30044583
 * <ul>
 *     <li>Remove the codes to get the project bug races / issues</li>
 * </ul>
 * </p>
 *
 * @author isv, GreatKevin, xjtufreeman, Veve, -jacob-
 * @version 1.6
 */
public class ProjectContestsAction extends BaseDirectStrutsAction implements FormAction<ProjectIdForm>,
        ViewAction<ProjectContestsDTO> {

    /**
     * <p>The constant string for show_contests_download.</p>
     */
    private static final String SHOW_CONTESTS_DOWNLOAD = "show_contests_download";


    /**
     * <p>The constant string for filtering out active bug races in JQL query</p>
     *
     * @since 1.5
     */
    private static final String FILTER_ACTIVE_BUG_RACES = "status = Open OR status = \"In Progress\" OR status = Reopened";

    /**
     * Logger for this class.
     */
    private static final Logger logger = Logger.getLogger(ProjectContestsAction.class);

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * <p>A <code>ProjectContestsDTO</code> providing the view data for displaying by <code>Project Contests</code>
     * view.</p>
     */
    private ProjectContestsDTO viewData = new ProjectContestsDTO();

    /**
     * <p>Constructs new <code>ProjectContestsAction</code> instance. This implementation does nothing.</p>
     */
    public ProjectContestsAction() {
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
    public ProjectContestsDTO getViewData() {
        return this.viewData;
    }

    /**
     * Gets current date on server.
     *
     * @return current date on server.
     * @since 1.1
     */
    public Date getCalendarToday() {
        return new Date();
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project requested for this action.</p>
     *
     * @throws Exception if an unexpected error occurs while processing the request.
     * @since 1.6
     */
    @Override
    protected void executeAction() throws Exception {
        List<ProjectContestDTO> contests = getViewData().getProjectContests().getContests();

        if (!contests.isEmpty()) {
            getSessionData().setCurrentProjectContext(contests.get(0).getContest().getProject());
        } else { 
            return ;
        }

        // set the attribute of flag whether to show the contests download panel
        if (getSessionData().getCurrentProjectContext() != null) {
            getSessionData().getSession().setAttribute(SHOW_CONTESTS_DOWNLOAD,
                    DataProvider.showContestsDownload(getSessionData().getCurrentProjectContext().getId()));
        } else {
            getSessionData().setCurrentProjectContext(
                    DirectUtils.getCurrentProjectBrief(this.getProjectServiceFacade(), getFormData().getProjectId()));
        }

        getSessionData().setCurrentSelectDirectProjectID(getSessionData().getCurrentProjectContext().getId());
    }
}
