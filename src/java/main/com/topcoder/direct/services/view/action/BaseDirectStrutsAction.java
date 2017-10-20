/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.configs.ReferenceDataBean;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataKeyService;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.service.facade.admin.AdminServiceFacade;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.permission.PermissionServiceFacade;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.pipeline.PipelineServiceFacade;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.service.user.UserService;
import com.topcoder.web.ejb.user.UserPreferenceHome;
import org.apache.log4j.Logger;
import org.apache.struts2.util.TokenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
 * <p>
 * Version 1.2.4 - Release Assembly - TopCoder Cockpit AJAX Revamp Change Note
 * <ul>
 * <li>Set the error message into JSON result, the previous one does not set error message right.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.3 - Module Assembly - TopCoder Cockpit Project Dashboard Edit Project version 1.0
 * <ul>
 * <li>Add direct project metadata and direct project metadata key service.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.4 - Release Assembly - TC Cockpit Create Project Refactoring Assembly Part One Change Note
 * <ul>
 * <li>Add <code>permissionServiceFacade</code> reference.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.5 (Topcoder Security Groups Backend - Direct Permissions Propagation Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #authorizationService} property.</li>
 *   </ol>
 * </p>
 *  <p>
 * Version 1.5 - Module Assembly - TC Cockpit Project Milestones Service Integration and Testing Change Note
 * <ul>
 * <li>Add <code>permissionServiceFacade</code> reference.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 - (Release Assembly - TC Direct Cockpit Release Three version 1.0)
 * <ul>
 *  <li>
 *      Add sorting by project name ignoring case to {@link #getProjects()}
 *  </li>  
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7 (Release Assembly - TopCoder Cockpit Project Overview Performance Improvement)
 * <ul>
 *     <li>
 *         Move up the ajax support result key and methods getResult, setResult and isJsonRequest to
 *         com.topcoder.direct.services.view.action.AbstractAction.
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.8 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
 * <ul>
 *     <li>
 *         Update {@link #getProjects()} to add a project status request parameter.
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9 (TopCoder Direct Performance Improvement - My Projects)
 * <ul>
 *     <li>Update {@link #getProjects()} to refactor get projects logic to DataProvider class</li>
 * </ul>
 * </p>
 *
 * @author fabrizyo, FireIce, murphydog, GreatKevin
 * @version 1.9
 */
public abstract class BaseDirectStrutsAction extends com.topcoder.direct.services.view.action.AbstractAction  {
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
     * Admin service facade.
     */
    private AdminServiceFacade adminServiceFacade;

    /**
     * Represents the UserService object. It has getter & setter. Can be any value.
     *
     * @since 1.2.1
     */
    private UserService userService;
    
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
     * Represents the user preference home ejb object. It has getter & setter. Can be any value.
     */
    private UserPreferenceHome userPreferenceHome;

    /**
     * Represents the permission service facade ejb service.
     *
     * @since 1.4
     */
    private PermissionServiceFacade permissionServiceFacade;

    /**
     * The direct project metadata key service.
     * @since 1.3
     */
    private DirectProjectMetadataKeyService metadataKeyService;

    /**
     * <p>A <code>AuthorizationService</code> providing the interface to authorization service.</p>
     * 
     * @since 1.5
     */
    private AuthorizationService authorizationService;

    /**
     * The direct project metadata service.
     * @since 1.3
     */
    private DirectProjectMetadataService metadataService;

    /**
     * The milestone responsible person service.
     * @since 1.5
     */
    private ResponsiblePersonService milestoneResponsiblePersonService;

    /**
     * The milestone service.
     * @since 1.5
     */
    private MilestoneService milestoneService;

    /**
     * The unique token name per action request.
     */
    private String tokenName;
    
    /**
     * <p>
     * Creates a <code>BaseDirectStrutsAction</code> instance.
     * </p>
     */
    protected BaseDirectStrutsAction() {
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

            // set error result if aggregate model exists
            if (getModel() != null) {
                setResult(e);
            }

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
        /**List<DashboardProjectSearchResultDTO> searchUserProjects = DataProvider.searchUserProjects(DirectStrutsActionsHelper
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
        });		*/

        // long timeLog = System.currentTimeMillis();

        List<ProjectData> projects = new ArrayList<ProjectData>();

        Map<Long, String> projectsOfUser = DataProvider.getProjectsOfUser(getCurrentUser().getUserId(), 1);

        for (Map.Entry<Long, String> entry : projectsOfUser.entrySet()) {
            ProjectData project = new ProjectData();
            project.setName(entry.getValue());
            project.setProjectId(entry.getKey());
            projects.add(project);
        }

        // sort the projects by project name ignore case
        Collections.sort(projects, new ProjectDataNameComparator());

        // System.out.println("PERFORMANCE_LOG:" + DirectUtils.getTCSubjectFromSession().getUserId()
        //        + " BaseDirectStrutsAction#getProjects took " + (System.currentTimeMillis() - timeLog) + " ms");
        
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

    /**
     * Get the userPreferenceHome field.
     *
     * @return the userPreferenceHome
     */
    public UserPreferenceHome getUserPreferenceHome() {
        return userPreferenceHome;
    }

    /**
     * Set the userPreferenceHome field.
     *
     * @param userPreferenceHome the userPreferenceHome to set
     */
    public void setUserPreferenceHome(UserPreferenceHome userPreferenceHome) {
        this.userPreferenceHome = userPreferenceHome;
    }

    /**
     * Gets the permission service facade.
     *
     * @return the permission service facade
     * @since 1.4
     */
    public PermissionServiceFacade getPermissionServiceFacade() {
        return permissionServiceFacade;
    }

    /**
     * Sets the permission service facade.
     *
     * @param permissionServiceFacade the permission service facade.
     * @since 1.4
     */
    public void setPermissionServiceFacade(PermissionServiceFacade permissionServiceFacade) {
        this.permissionServiceFacade = permissionServiceFacade;
    }

    /**
     * Gets the direct project metadata key service.
     *
     * @return the direct project metadata key service.
     * @since 1.3
     */
    public DirectProjectMetadataKeyService getMetadataKeyService() {
        return metadataKeyService;
    }

    /**
     * Sets the direct project metadata key service.
     *
     * @param metadataKeyService the direct project metadata key service.
     * @since 1.3
     */
    public void setMetadataKeyService(DirectProjectMetadataKeyService metadataKeyService) {
        this.metadataKeyService = metadataKeyService;
    }

    /**
     * Gets the direct project metadata service.
     *
     * @return the direct project metadata service.
     * @since 1.3
     */
    public DirectProjectMetadataService getMetadataService() {
        return metadataService;
    }

    /**
     * Sets the direct project metadata service
     *
     * @param metadataService the direct project metadata service.
     * @since 1.3
     */
    public void setMetadataService(DirectProjectMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    /**
     * <p>Gets the interface to authorization service.</p>
     *
     * @return a <code>AuthorizationService</code> providing the interface to authorization service.
     * @since 1.5
     */
    public AuthorizationService getAuthorizationService() {
        return this.authorizationService;
    }

    /**
     * <p>Sets the interface to authorization service.</p>
     *
     * @param authorizationService a <code>AuthorizationService</code> providing the interface to authorization
     *                             service.
     * @since 1.5
     */
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
    
    /**
     * Gets the milestone responsible person service.
     *
     * @return the milestone responsible person service.
     *
     * @since 1.5
     */
    public ResponsiblePersonService getMilestoneResponsiblePersonService() {
        return milestoneResponsiblePersonService;
    }

    /**
     * Sets the milestone responsible person service.
     *
     * @param milestoneResponsiblePersonService  the milestone responsible person service.
     * @since 1.5
     */
    public void setMilestoneResponsiblePersonService(ResponsiblePersonService milestoneResponsiblePersonService) {
        this.milestoneResponsiblePersonService = milestoneResponsiblePersonService;
    }

    /**
     * Gets the project milestone service.
     *
     * @return the project milestone service.
     * @since 1.5
     */
    public MilestoneService getMilestoneService() {
        return milestoneService;
    }

    /**
     * Sets the project milestone service.
     *
     * @param milestoneService the project milestone service.
     * @since 1.5
     */
    public void setMilestoneService(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    /**
     * Comparator to sort the ProjectData instance by the name ignoring the case.
     *
     * @since 1.6
     */
    private class ProjectDataNameComparator implements Comparator<ProjectData> {
        public int compare(ProjectData o1, ProjectData o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    public String getTokenName() {
        if (tokenName == null) {
            tokenName = TokenHelper.generateGUID();
        }

        return tokenName;
    }
}
