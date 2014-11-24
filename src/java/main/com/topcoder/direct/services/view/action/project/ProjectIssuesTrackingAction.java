/*
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestIssuesTrackingDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectIssueTrackingDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Version 1..0 (TC Cockpit Bug Tracking R1 Cockpit Project Tracking Assembly) change notes:
 * A <code>Struts</code> action to be used for handling requests for viewing the
 * <code>Project Issue Tracking</code> page for requested project.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 3 v1.0) change notes:
 *   <ol>
 *     <li>Update {@link #execute()} to process the direct project bugs</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * @author xjtufreeman, Veve
 * @version 1.2
 */
public class ProjectIssuesTrackingAction extends BaseDirectStrutsAction implements FormAction<ProjectIdForm>,
        ViewAction<ProjectIssueTrackingDTO> {

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * <p>A <code>ProjectIssueTrackingDTO</code> providing the view data for displaying by <code>Project Issues Tracking</code>
     * view.</p>
     */
    private ProjectIssueTrackingDTO viewData = new ProjectIssueTrackingDTO();

    /**
     * <p>Constructs new <code>ProjectIssuesTrackingAction</code> instance. This implementation does nothing.</p>
     */
    public ProjectIssuesTrackingAction() {
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
    public ProjectIssueTrackingDTO getViewData() {
        return this.viewData;
    }

    /**
     * Executes the action
     *
     * @throws Exception if any error occurs
     */
    @Override
    protected void executeAction() throws Exception {
        // Gets the contests of the cockpit project first
        List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(getSessionData().getCurrentUserId(), formData.getProjectId());
        Map<ContestBriefDTO, ContestIssuesTrackingDTO> issues = DataProvider.getDirectProjectIssues(contests);

        getViewData().setProjectIssues(issues);

        // put project into the session
        if (contests.size() > 0) {
            getSessionData().setCurrentProjectContext(contests.get(0).getProject());
        } else {
            getSessionData().setCurrentProjectContext(DataProvider.createProjectBriefDTO(formData.getProjectId(),
                    getProjectServiceFacade().getProject(DirectUtils.getTCSubjectFromSession(),
                            formData.getProjectId()).getName()));
        }

        // set project bugs
        List<TcJiraIssue> bugs = JiraRpcServiceWrapper.getIssuesForDirectProject(getFormData().getProjectId());
        getViewData().setProjectBugs(bugs);

        getSessionData().setCurrentSelectDirectProjectID(
                getFormData().getProjectId());
    }
}
