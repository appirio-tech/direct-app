/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;

import java.util.List;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Project Contests</code> page
 * for requested project.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProjectContestsAction extends AbstractAction implements FormAction<ProjectIdForm>,
                                                                     ViewAction<ProjectContestsDTO> {

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
            List<ProjectContestDTO> contests = getViewData().getProjectContests().getContests();
            if (contests.isEmpty()) {
                getSessionData().setCurrentProjectContext(getViewData().getProjectStats().getProject());
            } else {
                getSessionData().setCurrentProjectContext(contests.get(0).getContest().getProject());
            }
            return SUCCESS;
        } else {
            return result;
        }
    }
}
