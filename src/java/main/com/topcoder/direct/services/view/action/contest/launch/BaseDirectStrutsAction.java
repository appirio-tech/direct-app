/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.topcoder.clients.model.Project;
import com.topcoder.service.facade.admin.AdminServiceFacade;
import com.topcoder.direct.services.view.ajax.CustomFormatAJAXResult;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.pipeline.PipelineServiceFacade;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.studio.contest.ContestManager;

/**
 * <p>
 * This is the base action of all actions in this component.
 * </p>
 * <p>
 * It sets the <code>AggregateDataModel</code> to the model using the prepare logic of struts framework (in the
 * assembly/development the logic of this class could be implemented directly in <code>AbstractAction</code> and
 * delegate the logic of the action to the template method. It also manages the exceptions thrown by the template
 * methods.
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are reused).
 * This class is mutable and stateful: it's not thread safe.
 * </p>
 * <p>
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note
 * - It will throw out exception when it is not in json request
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.1
 */
public abstract class BaseDirectStrutsAction extends AbstractAction implements Preparable {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(BaseDirectStrutsAction.class);

    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = 3320745591427458154L;

    /**
     * <p>
     * Represents the result key to store result in the aggregate model.
     * </p>
     */
    private static final String RESULT_KEY = "result";

    /**
     * <p>
     * It's the service used to performing the creation or update of the project.
     * </p>
     * <p>
     * It's used in the <code>executeAction</code> method. It can't be null when the logic is performed.
     * </p>
     * *
     * <p>
     * It's injected by the setter and returned by the getter.
     * </p>
     */
    private ProjectServiceFacade projectServiceFacade;

    /**
     * <p>
     * Represents the service used to performing the creation or update of the contest.
     * </p>
     * <p>
     * It's used in the <code>executeAction</code> method. It can't be null when the logic is performed.
     * </p>
     * <p>
     * It's injected by the setter and returned by the getter.
     * </p>
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * <p>
     * It's used to retrieve the capacity full dates.
     * </p>
     * <p>
     * It will be not null because it will be injected. It can't be null.
     * </p>
     */
    private PipelineServiceFacade pipelineServiceFacade;

    /**
     * <p>
     * Contest manager interface.
     * </p>
     */
    private ContestManager contestManager;

    private AdminServiceFacade adminServiceFacade;

    /**
     * <p>
     * Creates a <code>BaseDirectStrutsAction</code> instance.
     * </p>
     */
    protected BaseDirectStrutsAction() {
    }

    /**
     * <p>
     * Prepare the action before its logic.
     * </p>
     * <p>
     * This method is used by its related interceptor before of the setters/getters of parameters (params
     * interceptor).
     * </p>
     *
     * @throws Exception if any problem occurs.
     */
    public void prepare() throws Exception {
        setModel(new AggregateDataModel());
        getModel().setAction(getAction());
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * It uses <b>Template Method</b> Design Pattern to perform the logic of the concrete action. Each concrete action
     * should implement the {@link #executeAction()} method. If some error occurs then set the exception with the
     * {@link #RESULT_KEY} key to the model.
     * </p>
     *
     * @return <code>SUCCESS</code> always
     */
    @Override
    public String execute() throws Exception {
        try {
            executeAction();
        } catch (Exception e) {
            logger.error("Error when executing action : " + getAction() + " : " + e.getMessage(), e);
            if (isJsonRequest()) {
                setResult(e);
            } else {
                throw e;
            }
        }
        return SUCCESS;
    }

    /**
     * <p>
     * This is the template method where the action logic will be performed by children classes.
     * </p>
     *
     * @throws Exception if any error occurs
     */
    protected abstract void executeAction() throws Exception;

    /**
     * <p>
     * Determine if it is json request or not.
     * </p>
     *
     * @return true if it is json request
     * @throws Exception
     */
    protected boolean isJsonRequest() throws Exception {
        return ActionContext.getContext().getActionInvocation().getResult() instanceof CustomFormatAJAXResult;
    }

    /**
     * <p>
     * Set the result of the action to the aggregate model.
     * </p>
     * <p>
     * the object will be under the {@link #RESULT_KEY} key in the model map.
     * </p>
     *
     * @param result the result to set to the model
     */
    public void setResult(Object result) {
        getModel().setData(RESULT_KEY, result);
    }

    /**
     * <p>
     * Gets the result from the aggregate model.
     * </p>
     * <p>
     * The result is under the {@link #RESULT_KEY} key in the model map (it can be null if it's not present).
     * </p>
     *
     * @return the result from the model.
     */
    public Object getResult() {
        return getModel().getData(RESULT_KEY);
    }

    /**
     * <p>
     * Gets the project service facade.
     * </p>
     *
     * @return the project service facade
     */
    public ProjectServiceFacade getProjectServiceFacade() {
        return projectServiceFacade;
    }

    /**
     * <p>
     * Set the project service facade.
     * </p>
     *
     * @param projectServiceFacade the project service facade to set
     * @throws IllegalArgumentException if <b>projectServiceFacade</b> is <code>null</code>
     */
    public void setProjectServiceFacade(ProjectServiceFacade projectServiceFacade) {
        DirectStrutsActionsHelper.checkNull(projectServiceFacade, "projectServiceFacade");

        this.projectServiceFacade = projectServiceFacade;
    }

    /**
     * <p>
     * Gets the contest service facade.
     * </p>
     *
     * @return the contest service facade
     */
    public ContestServiceFacade getContestServiceFacade() {
        return contestServiceFacade;
    }

    /**
     * <p>
     * Sets the contest service facade.
     * </p>
     *
     * @param contestServiceFacade the contest service facade to set
     * @throws IllegalArgumentException if <b>contestServiceFacade</b> is <code>null</code>
     */
    public void setContestServiceFacade(ContestServiceFacade contestServiceFacade) {
        DirectStrutsActionsHelper.checkNull(contestServiceFacade, "contestServiceFacade");

        this.contestServiceFacade = contestServiceFacade;
    }

    /**
     * <p>
     * Gets the pipeline service facade.
     * </p>
     *
     * @return the pipeline service facade
     */
    public PipelineServiceFacade getPipelineServiceFacade() {
        return pipelineServiceFacade;
    }

    /**
     * <p>
     * Set the pipeline Service facade.
     * </p>
     *
     * @param pipelineServiceFacade the pipeline service facade to set
     * @throws IllegalArgumentException if <b>pipelineServiceFacade</b> is <code>null</code>
     */
    public void setPipelineServiceFacade(PipelineServiceFacade pipelineServiceFacade) {
        DirectStrutsActionsHelper.checkNull(pipelineServiceFacade, "pipelineServiceFacade");

        this.pipelineServiceFacade = pipelineServiceFacade;
    }

    public ContestManager getContestManager() {
        return contestManager;
    }

    public void setContestManager(ContestManager contestManager) {
        this.contestManager = contestManager;
    }

    public AdminServiceFacade getAdminServiceFacade() {
        return adminServiceFacade;
    }

    public void setAdminServiceFacade(AdminServiceFacade adminServiceFacade) {
        this.adminServiceFacade = adminServiceFacade;
    }

    public List<ProjectData> getProjects() throws Exception {
        if (null == projectServiceFacade) {
            throw new IllegalStateException("The project service facade is not initialized.");
        }

        // get all the projects through facade.
        List<ProjectData> projects = projectServiceFacade.getAllProjects(DirectStrutsActionsHelper
            .getTCSubjectFromSession());

        return projects;
    }

    public List<ProjectData> getBillingProjects() throws Exception {
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

        return projectDatas;
    }

    /**
     * Gets the current user of <code>TCSubject</code>.
     *
     * @return the current user of <code>TCSubject</code>
     */
    public TCSubject getCurrentUser() {
        return DirectStrutsActionsHelper.getTCSubjectFromSession();
    }
}
