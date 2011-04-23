/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestIssuesTrackingDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectIssueTrackingDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for viewing the
 * <code>Project Issue Tracking</code> page for requested project.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (TC Cockpit Bug Tracking R1 Cockpit Project Tracking Assembly)
 */
public class ProjectIssuesTrackingAction extends AbstractAction implements FormAction<ProjectIdForm>,
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

            try {
                // Gets the contests of the cockpit project first
                List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(getSessionData().getCurrentUserId(), formData.getProjectId());

                Map<ContestBriefDTO, ContestIssuesTrackingDTO> issues = DataProvider.getDirectProjectIssues(contests);

                getViewData().setProjectIssues(issues);

                List<ProjectBriefDTO> projects
                    = DataProvider.getUserProjects(getSessionData().getCurrentUserId());

                UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
                userProjectsDTO.setProjects(projects);

                viewData.setUserProjects(userProjectsDTO);

                // put project into the session
                if (contests.size() > 0) {
                    getSessionData().setCurrentProjectContext(contests.get(0).getProject());
                } else {
                    for (ProjectBriefDTO p : projects) {
                        if (p.getId() == getFormData().getProjectId()) {
                            getSessionData().setCurrentProjectContext(p);
                            break;
                        }
                    }

                }

                getSessionData().setCurrentSelectDirectProjectID(
                        getFormData().getProjectId());

                // set project contests
                getSessionData().setCurrentProjectContests(contests);

            } catch (Exception e) {
                return ERROR;
            }

            return SUCCESS;
        } else {
            return result;
        }
    }
}
