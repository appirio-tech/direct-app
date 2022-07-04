/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * This action permits to create a project. It simply collect the data to create a project and then create it.
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
public class CreateProjectAction extends ProjectAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = 4587618567540676457L;

    /**
     * <p>
     * This is the name of project.
     * </p>
     * <p>
     * It's mapped to the similar name field (type) in related <code>ProjectData</code> instance. It must be not null,
     * max 255 chars, not empty. It's changed by the setter and returned by the getter.
     * </p>
     */
    private String projectName;

    /**
     * <p>
     * This is the description of project.
     * </p>
     * <p>
     * It's mapped to the similar name field (type) in related <code>ProjectData</code> instance. It can be null, max
     * 20000 chars, it can be empty. It's changed by the setter and returned by the getter.
     * </p>
     */
    private String projectDescription;

    /**
     * <p>
     * Creates a <code>CreateProjectAction</code> instance.
     * </p>
     */
    public CreateProjectAction() {
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * Create the project based on the input parameters.
     * </p>
     * <p>
     * Validation: not present, already completely done by the annotations in the setters.
     * </p>
     * <p>
     *
     * @throws IllegalStateException
     *             if the project service facade is not set.
     * @throws Exception
     *             if any other error occurs
     * @see ProjectServiceFacade#createProject(com.topcoder.security.TCSubject, ProjectData)
     */
    protected void executeAction() throws Exception {
        ProjectServiceFacade projectServiceFacade = getProjectServiceFacade();

        if (null == projectServiceFacade) {
            throw new IllegalStateException("The project service facade is not initialized");
        }

        // create the project data with the input parameters.
        ProjectData projectData = new ProjectData();
        projectData.setName(getProjectName());
        projectData.setDescription(getProjectDescription());

        // delegate to ProjectServiceFacade to create the project.
        projectData = projectServiceFacade.createProject(DirectStrutsActionsHelper.getTCSubjectFromSession(),
                projectData);

        setResult(projectData);
    }

    /**
     * <p>
     * Gets the project name.
     * </p>
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * <p>
     * Sets the project name.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception, Struts 2 Validation Framework is used instead.
     * </p>
     *
     * @param projectName
     *            the project name to set
     */
    @RequiredStringValidator(message = "The projectName field should not be null or empty",
            key = "i18n.CreateProjectAction.projectNameRequired")
    @StringLengthFieldValidator(message = "The length of projectName should be between 1 and 255 after trimmed",
            key = "i18n.CreateProjectAction.projectNameLength",
            minLength = "1",
            maxLength = "255")
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * <p>
     * Gets the project description.
     * </p>
     *
     * @return the project description
     */
    public String getProjectDescription() {
        return projectDescription;
    }

    /**
     * <p>
     * Sets the project description.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception, Struts 2 Validation Framework is used instead.
     * </p>
     *
     * @param projectDescription
     *            the project description to set
     */
    @StringLengthFieldValidator(message = "The length of projectDescription should be 2000 at max after trimmed",
            key = "i18n.CreateProjectAction.projectDescriptionLength",
            maxLength = "2000")
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
}
