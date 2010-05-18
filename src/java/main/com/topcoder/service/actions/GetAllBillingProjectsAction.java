/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.model.Project;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * This action gets all billing projects, but just their <code>id</code>, <code>name</code> and <code>description</code>
 * fields.
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public class GetAllBillingProjectsAction extends ProjectAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -6847788213687835673L;

    /**
     * <p>
     * Creates a <code>GetAllBillingProjectsAction</code> instance.
     * </p>
     */
    public GetAllBillingProjectsAction() {
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * All the billing projects will be set as result.
     * </p>
     *
     * @throws IllegalStateException
     *             if the project service facade is not set.
     * @throws Exception
     *             if any other error occurs
     * @see ProjectServiceFacade#getClientProjectsByUser(com.topcoder.security.TCSubject)
     */
    protected void executeAction() throws Exception {
        // get the project service facade
        ProjectServiceFacade projectServiceFacade = getProjectServiceFacade();

        if (null == projectServiceFacade) {
            throw new IllegalStateException("The project service facade is not initialized");
        }
        // retrieve all client projects with the current user
        List<Project> projects = projectServiceFacade.getClientProjectsByUser(DirectStrutsActionsHelper
                .getTCSubjectFromSession());

        // wrap the data with project data, only id, name and description are retrieved
        List<ProjectData> projectDatas = new ArrayList<ProjectData>();
        for (Project project : projects) {
            ProjectData projectData = new ProjectData();
            projectData.setProjectId(project.getId());
            projectData.setName(project.getName());
            projectData.setDescription(project.getDescription());

            projectDatas.add(projectData);
        }

        // set the result
        setResult(projectDatas);
    }
}
