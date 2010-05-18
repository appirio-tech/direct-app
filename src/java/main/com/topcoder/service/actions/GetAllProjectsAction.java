/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.List;

import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * This action gets all existing projects.
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
public class GetAllProjectsAction extends ProjectAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -2910787371509066995L;

    /**
     * <p>
     * Creates a <code>GetAllProjectsAction</code> instance.
     * </p>
     */
    public GetAllProjectsAction() {
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * After execution, all projects will be available as result.
     * </p>
     *
     * @throws IllegalStateException
     *             if the project service facade is not set yet
     * @throws Exception
     *             if any other error occurs
     * @see ProjectServiceFacade#getAllProjects(com.topcoder.security.TCSubject)
     */
    protected void executeAction() throws Exception {
        // get the project service facade
        ProjectServiceFacade projectServiceFacade = getProjectServiceFacade();
        if (null == projectServiceFacade) {
            throw new IllegalStateException("The project service facade is not initialized.");
        }

        // get all the projects through facade.
        List<ProjectData> projects = projectServiceFacade.getAllProjects(DirectStrutsActionsHelper
                .getTCSubjectFromSession());

        // set as result
        setResult(projects);
    }
}
