/*
 * Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.project.ProjectContestsDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * The project game plan action.
 *
 * <p>
 * Version 1.1 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * @author Veve
 * @version 1.1
 */
public class ProjectGamePlanAction extends BaseDirectStrutsAction implements FormAction<ProjectIdForm>, ViewAction<ProjectContestsDTO> {

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
    public ProjectGamePlanAction() {
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
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project requested for this action.</p>
     *
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    protected void executeAction() throws Exception {
        getSessionData().setCurrentProjectContext(
                DirectUtils.getCurrentProjectBrief(this.getProjectServiceFacade(), getFormData().getProjectId()));

        getSessionData().setCurrentSelectDirectProjectID(getSessionData().getCurrentProjectContext().getId());
    }
}
