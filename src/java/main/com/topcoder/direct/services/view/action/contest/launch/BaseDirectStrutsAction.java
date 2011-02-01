/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import com.topcoder.project.service.ProjectServices;
import com.topcoder.service.user.UserService;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.configs.ReferenceDataBean;
import com.topcoder.direct.services.view.ajax.CustomFormatAJAXResult;
import com.topcoder.direct.services.view.dto.dashboard.DashboardProjectSearchResultDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.admin.AdminServiceFacade;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.pipeline.PipelineServiceFacade;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.service.studio.StudioService;
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
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note - It will throw out exception when
 * it is not in json request
 * </p>
 * <p>
 * Version 1.2 - Direct Launch Software Contests Assembly Change Note
 * <ul>
 * <li>Add the <code>getReferenceDataBean</code>.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.2.1 - Direct Submission Viewer Release 4 Assembly Change Note
 * <ul>
 * <li>Add the <code>userService</code>.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.2.2 - Direct Release Assembly 5 (72 hrs) Change Note
 * <ul>
 * <li>Change <code>getProjects</code> to call the <code>DataProvider</code> instead of project service facade.</li>
 * </ul>
 * </p> 
 * <p>
 * Version 1.2.3 - TC Direct Release Assembly 7 Change Note
 * <ul>
 * <li>Add studio service and project services field.</li>
 * </ul>
 * </p>
 *
 * @author fabrizyo, FireIce, murphydog, TCSASSEMBLER
 * @version 1.2.3
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
     * Represents the UserService object. It has getter & setter. Can be any value.
     *
     * @since 1.2.1
     */
    private UserService userService;
    
    /**
     * Represents the StudioService object. It has getter & setter. Can be any value.
     *
     * @since 1.2.3
     */
    private StudioService studioService;
    
    /**
     * Represents the ProjectServices object. It has getter & setter. Can be any value.
     *
     * @since 1.2.3
     */
    private ProjectServices projectServices;
    
    /**
     * Represents the specification review service object. It has getter & setter. Can be any value.
     */
    private SpecificationReviewService specificationReviewService;

    /**
     * <p>
     * Bean which holds the reference data.
     * </p>
     */
    private ReferenceDataBean referenceDataBean;

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
        } catch (Throwable e) {
            logger.error("Error when executing action : " + getAction() + " : " + e.getMessage(), e);
            if (isJsonRequest()) {
                setResult(e);
            } else {
                throw new Exception(e);
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

    /**
     * <p>
     * Gets the contest manager.
     * </p>
     *
     * @return the contest manager
     */
    public ContestManager getContestManager() {
        return contestManager;
    }

    /**
     * <p>
     * Sets the contest manager.
     * </p>
     *
     * @param contestManager the contest manager
     */
    public void setContestManager(ContestManager contestManager) {
        this.contestManager = contestManager;
    }

    public AdminServiceFacade getAdminServiceFacade() {
        return adminServiceFacade;
    }

    public void setAdminServiceFacade(AdminServiceFacade adminServiceFacade) {
        this.adminServiceFacade = adminServiceFacade;
    }

    /**
     * <p>
     * Gets all projects.
     * </p>
     *
     * @return all projects
     * @throws Exception if any error occurs
     */
    public List<ProjectData> getProjects() throws Exception {
        List<DashboardProjectSearchResultDTO> searchUserProjects = DataProvider.searchUserProjects(DirectStrutsActionsHelper
            .getTCSubjectFromSession(), "");
			
		List<ProjectData> projects = new ArrayList<ProjectData>();
		
		for (DashboardProjectSearchResultDTO dashboardProjectSearchResultDTO : searchUserProjects) {
			ProjectData projectData = new ProjectData();
			projectData.setName(dashboardProjectSearchResultDTO.getData().getProjectName());
			projectData.setProjectId(dashboardProjectSearchResultDTO.getData().getProjectId());
			projects.add(projectData);
		}

        Collections.sort(projects, new Comparator() {
			public int compare(Object obj1, Object obj2) {
				if (((ProjectData)obj1).getName() == null) {
					return -1;
				}
				if (((ProjectData)obj2).getName() == null) {
					return 1;
				}
				String name1 = ((ProjectData)obj1).getName().trim();
				String name2 = ((ProjectData)obj2).getName().trim();
				return name1.compareTo(name2);
			}
        });		
        return projects;
    }

    /**
     * <p>
     * Gets all billing projects.
     * </p>
     *
     * @return a list of billing projects
     * @throws Exception if any error occurs
     */
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

    /**
     * <p>
     * Sets the reference data bean.
     * </p>
     *
     * @param referenceDataBean the reference data bean
     */
    public void setReferenceDataBean(ReferenceDataBean referenceDataBean) {
        this.referenceDataBean = referenceDataBean;
    }

    /**
     * <p>
     * Gets reference data bean.
     * </p>
     *
     * @return the reference data bean
     * @since Direct Launch Software Contests Assembly
     */
    public ReferenceDataBean getReferenceDataBean() {
        return this.referenceDataBean;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return user service
     * @since 1.2.1
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param userService user service
     * @since 1.2.1
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     * 
     * @return the studioService
     * @since 1.2.3
     */
    public StudioService getStudioService() {
        return studioService;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     * 
     * @param studioService the studioService to set
     * @since 1.2.3
     */
    public void setStudioService(StudioService studioService) {
        this.studioService = studioService;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     * 
     * @return the projectServices
     * @since 1.2.3
     */
    public ProjectServices getProjectServices() {
        return projectServices;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     * 
     * @param projectServices the projectServices to set
     * @since 1.2.3
     */
    public void setProjectServices(ProjectServices projectServices) {
        this.projectServices = projectServices;
    }

    /**
     * Get the specificationReviewService field.
     *
     * @return the specificationReviewService
     */
    public SpecificationReviewService getSpecificationReviewService() {
        return specificationReviewService;
    }

    /**
     * Set the specificationReviewService field.
     *
     * @param specificationReviewService the specificationReviewService to set
     */
    public void setSpecificationReviewService(
            SpecificationReviewService specificationReviewService) {
        this.specificationReviewService = specificationReviewService;
    }
}
