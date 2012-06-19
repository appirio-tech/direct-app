/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.contest.*;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.project.*;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.permission.PermissionServiceFacade;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.user.UserService;
import com.topcoder.shared.util.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for viewing the
 * <code>Project Overview</code> page for requested project.
 * </p>
 * <p>
 * Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
 * <ul>
 * <li>Added {@link #setDashboardProjectStat()} method. Updated
 * {@link #execute()} method to set dashboard project data and dashboard contests data.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.0.2 - 	Cockpit Performance Improvement Project Overview and Manage Copilot Posting Change Note
 * <ul>
 * <li>Remove the logic of filter out upcoming activities because ProjectOverviewAction new uses the new action preprocessor
 * <code>UpcomingProjectActivitiesProcessor</code>.</li>
 * <li>Update the method setDashboardContests to use new method to get contests health data.</code>.</li>
 * </ul>
 * </p>
 *  <p>
 * Version 1.0.3 - 	TC Cockpit Bug Tracking R1 Cockpit Project Tracking version 1.0 Change Notes
 * <ul>
 * <li>Added codes to set issue tracking health status of contests.</li>
 * <li>Added codes to set unresolved issues number and ongoing bug races number of the project</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.0.4 - 	Direct Improvements Assembly Release 2 Change Note
 * <ul>
 * <li>Update the execute method to set the current project name.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.0.5 (TC Cockpit Contest Duration Calculation Updates Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Fixed typo in name of {@link DashboardHelper#setContestStatusColor(ContestHealthDTO)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.0.6 (Project Health Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Removed <code>setDashboardContests</code> method as project contests health status is now retrieved
 *     separately via AJAX call.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Project Overview Update 1) Change notes:
 *   <ol>
 *     <li>Updated {@link #execute()} method to retrieve project forum category and forum messages count.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Module Assembly - TC Cockpit Project Overview Project General Info) Change notes:
 *   <ol>
 *     <li>Updated {@link #execute()} method to retrieve data for the newly added project general information table.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TC Cockpit Edit Project and Project General Info Update version 1.0) change notes:
 * <ol>
 *     <li>Update {@link #setProjectGeneralInfo(com.topcoder.service.project.ProjectData)} to set project ratings and
 *     additional project information.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration)
 * <ol>
 *     <li>Add project permission information to the project general info section</li>
 * </ol>
 * </p>
 * 
 * @author isv, Veve, Blues, GreatKevin
 * @version 1.4
 */
public class ProjectOverviewAction extends AbstractAction implements FormAction<ProjectIdForm>,
                                                                     ViewAction<ProjectOverviewDTO> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(ProjectOverviewAction.class);

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * <p>A <code>ProjectOverviewDTO</code> providing the view data for displaying by <code>Project Overview</code>
     * view.</p>
     */
    private ProjectOverviewDTO viewData = new ProjectOverviewDTO();

    /**
     * The statistics of the copilots of the project.
     *
     * @since 1.1
     */
    private List<ProjectCopilotStatDTO> copilotStats;

    /**
     * The project service facade. It will be set via spring injection.
     *
     * @since 1.1
     */
    private ProjectServiceFacade projectServiceFacade;

    /**
     * The project metadata service. It will be set via spring injection in applicationContext.xml.
     *
     * @since 1.2
     */
    private DirectProjectMetadataService projectMetadataService;

    /**
     * The user service.
     */
    private UserService userService;

    /**
     * The permission service facade.
     * @since 1.4
     */
    private PermissionServiceFacade permissionServiceFacade;

    /**
     * The flag determines whether it's a call to export project general info.
     */
    private boolean export = false;

    /**
     * Gets the project service facade.
     *
     * @return the project service facade.
     * @since 1.1
     */
    public ProjectServiceFacade getProjectServiceFacade() {
        return projectServiceFacade;
    }

    /**
     * Sets the project service facade.
     *
     * @param projectServiceFacade the project service facade.
     * @since 1.1
     */
    public void setProjectServiceFacade(ProjectServiceFacade projectServiceFacade) {
        this.projectServiceFacade = projectServiceFacade;
    }

    /**
     * Gets the permission service facade.
     *
     * @return the permission service facade.
     * @since 1.4
     */
    public PermissionServiceFacade getPermissionServiceFacade() {
        return permissionServiceFacade;
    }

    /**
     * Sets the permission service facade.
     *
     * @param permissionServiceFacade the permission service facade instance.
     * @since 1.4
     */
    public void setPermissionServiceFacade(PermissionServiceFacade permissionServiceFacade) {
        this.permissionServiceFacade = permissionServiceFacade;
    }

    /**
     * Gets the project metadata service.
     *
     * @return the project metadata service.
     * @since 1.2
     */
    public DirectProjectMetadataService getProjectMetadataService() {
        return projectMetadataService;
    }

    /**
     * Sets the project metadata service.
     *
     * @param projectMetadataService the project metadata service.
     * @since 1.2
     */
    public void setProjectMetadataService(DirectProjectMetadataService projectMetadataService) {
        this.projectMetadataService = projectMetadataService;
    }

    /**
     * Gets the statistics of the copilots of the project.
     *
     * @return The statistics of the copilots of the project.
     * @since 1.1
     */
    public List<ProjectCopilotStatDTO> getCopilotStats() {
        return copilotStats;
    }

    /**
     * Sets the statistics of the copilots of the project.
     *
     * @param copilotStats The statistics of the copilots of the project.
     * @since 1.1
     */
    public void setCopilotStats(List<ProjectCopilotStatDTO> copilotStats) {
        this.copilotStats = copilotStats;
    }

    /**
     * <p>Constructs new <code>ProjectOverviewAction</code> instance. This implementation does nothing.</p>
     */
    public ProjectOverviewAction() {
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
    public ProjectOverviewDTO getViewData() {
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
            getSessionData().setCurrentProjectContext(getViewData().getProjectStats().getProject());

            // set the current direct project id in session, the contest details
            // codes incorrectly
            // use setCurrentProjectContext to override the current chosen
            // direct project with current
            // chosen contest, for the safe, we put the direct project id into
            // session separately again
            getSessionData().setCurrentSelectDirectProjectID(
                    getSessionData().getCurrentProjectContext().getId());

            try {
                // set dashboard project status
                setDashboardProjectStat();

                // get project issues
                ProjectData project = null;

                // get the project forum information and update the project name and project id
                // because they are not shown when the project has no contests
                project = getProjectServiceFacade().getProject(DirectUtils.getTCSubjectFromSession(), formData.getProjectId());

                if (!export) {
                    List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(getSessionData().getCurrentUserId(), formData.getProjectId());
                    Map<ContestBriefDTO, ContestIssuesTrackingDTO> issues = DataProvider.getDirectProjectIssues(contests);

                    int totalUnresolvedIssues = 0;
                    int totalOngoingBugRaces = 0;

                    // update dashboard health
                    for(Map.Entry<ContestBriefDTO, ContestIssuesTrackingDTO> contestIssues : issues.entrySet()) {
                        totalUnresolvedIssues += contestIssues.getValue().getUnresolvedIssuesNumber();
                        totalOngoingBugRaces += contestIssues.getValue().getUnresolvedBugRacesNumber();
                    }

                    // set the project name if it's not set yet
                    for (ProjectBriefDTO projectDTO : getViewData().getUserProjects().getProjects()) {
                        if (projectDTO.getId() == getSessionData().getCurrentProjectContext().getId()) {
                            getSessionData().getCurrentProjectContext().setName(project.getName());
                        }
                    }

                    getViewData().getDashboardProjectStat().setUnresolvedIssuesNumber(totalUnresolvedIssues);
                    getViewData().getDashboardProjectStat().setOngoingBugRacesNumber(totalOngoingBugRaces);

                    getViewData().getProjectStats().getProject().setName(project.getName());
                    getViewData().getProjectStats().getProject().setId(project.getProjectId());
                    getViewData().getProjectStats().getProject().setProjectForumCategoryId(project.getForumCategoryId());

                    // Check if the project's forum has any threads
                    long forumThreadsCount = DataProvider.getTopCoderDirectProjectForumThreadsCount(project.getProjectId());
                    getViewData().setHasForumThreads(forumThreadsCount > 0);
                }

                // gets and sets the statistics of the project copilots
                setCopilotStats(DataProvider.getDirectProjectCopilotStats(formData.getProjectId()));

                // set all data for project general information table to the view data
                setProjectGeneralInfo(project);

                if(export) {
                    for(long clientId : viewData.getProjectGeneralInfo().getClientManagers()) {
                        viewData.getProjectGeneralInfo().getClientManagersHandles().put(clientId, userService.getUserHandle(clientId));
                    }
                    for(long managerId : viewData.getProjectGeneralInfo().getTopcoderManagers()) {
                        viewData.getProjectGeneralInfo().getTopcoderManagersHandles().put(managerId, userService.getUserHandle(managerId));
                    }
                    for(ProjectCopilotStatDTO copilot : getCopilotStats()) {
                        viewData.getProjectGeneralInfo().getCopilotHandles().put(copilot.getCopilotInfo().getUserId(), copilot.getCopilotInfo().getHandle());
                    }

                    return "download";
                }
                
                
            } catch (Exception e) {
                log.error("Project Overview error: ", e);
                return ERROR;
            }

            return SUCCESS;
        } else {
            return result;
        }
    }

    /**
     * Set dashboard project status data.
     * 
     * @throws Exception if any exception occurs
     */
    private void setDashboardProjectStat() throws Exception {

        List<ProjectData> tcDirectProjects = new ArrayList<ProjectData>();
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(formData.getProjectId());
        tcDirectProjects.add(projectData);

        List<EnterpriseDashboardProjectStatDTO> enterpriseProjectStats = DataProvider
                .getDirectProjectStats(tcDirectProjects, getSessionData().getCurrentUserId());
        getViewData().setDashboardProjectStat(enterpriseProjectStats.get(0));
        DashboardHelper.setAverageContestDurationText(getViewData().getDashboardProjectStat());
    }

    /**
     * Sets all the data for project general information table of project overview page.
     *
     * @param project the <code>ProjectData</code> instance which contains the name and id of the project.
     * @throws Exception if any exception occurs
     * @since 1.2
     */
    private void setProjectGeneralInfo(ProjectData project) throws Exception {
        getViewData().getProjectGeneralInfo().setProject(project);

        // throw ISE if project metadata service is not injected
        if (getProjectMetadataService() == null) {
            throw new IllegalStateException("Project metadata service is not initialized and injected.");
        }

        final List<DirectProjectMetadata> metadata = getProjectMetadataService().getProjectMetadataByProject(getFormData().getProjectId());
        List<Long> clientManagers = new ArrayList<Long>();
        List<Long> tcManagers = new ArrayList<Long>();
        Map<String, List<String>> additionalProjectInfo = new HashMap<String, List<String>>();

        for (DirectProjectMetadata m : metadata) {
            long keyId = m.getProjectMetadataKey().getId();
            long clientId = -1;
            
            if(m.getProjectMetadataKey().getClientId() != null) {
                clientId = m.getProjectMetadataKey().getClientId();
            }

            if (keyId == 1L) {
                // client managers
                clientManagers.add(Long.parseLong(m.getMetadataValue()));
            } else if (keyId == 2L) {
                // topcoder project managers
                tcManagers.add(Long.parseLong(m.getMetadataValue()));
            } else if (keyId == 3L) {
                // budget
                getViewData().getProjectGeneralInfo().setTotalBudget(Integer.parseInt(m.getMetadataValue()));
            } else if (keyId == 4L) {
                // svn
                getViewData().getProjectGeneralInfo().setSvn(m.getMetadataValue());
            } else if (keyId == 5L) {
                // jira
                getViewData().getProjectGeneralInfo().setJira(m.getMetadataValue());
            } else if (keyId == 6L) {
                // planned duration
                getViewData().getProjectGeneralInfo().setPlannedDuration(Integer.parseInt(m.getMetadataValue()));
            } else if (keyId == 10L) {
                // business impact rating
                getViewData().getProjectGeneralInfo().setBusinessImpactRating(Integer.parseInt(m.getMetadataValue()));
            } else if (keyId == 11L) {
                // risk level rating
                getViewData().getProjectGeneralInfo().setRiskLevelRating(Integer.parseInt(m.getMetadataValue()));
            } else if (keyId == 12L) {
                // cost rating
                getViewData().getProjectGeneralInfo().setCostRating(Integer.parseInt(m.getMetadataValue()));
            } else if (keyId == 13L) {
                // difficulty rating
                getViewData().getProjectGeneralInfo().setDifficultyRating(Integer.parseInt(m.getMetadataValue()));
            }

            if (clientId > 0) {
                String keyName = m.getProjectMetadataKey().getName();
                
                if(!additionalProjectInfo.containsKey(keyName)) {
                    List<String> values = new ArrayList<String>();
                    values.add(m.getMetadataValue());
                    additionalProjectInfo.put(keyName, values);
                } else {
                    List<String> values = additionalProjectInfo.get(keyName);
                    values.add(m.getMetadataValue());
                }
            }
        }

        getViewData().getProjectGeneralInfo().setAdditionalProjectInfo(additionalProjectInfo);
        getViewData().getProjectGeneralInfo().setClientManagers(clientManagers);
        getViewData().getProjectGeneralInfo().setTopcoderManagers(tcManagers);

        // set the project cost
        getViewData().getProjectGeneralInfo().setActualCost((int) Math.round(getViewData().getDashboardProjectStat().getTotalProjectCost()));
        
        DataProvider.setProjectGeneralInfo(getViewData().getProjectGeneralInfo());

        // set project permission info
        setProjectPermissionInfo();
    }

    /**
     * Sets the project permission data into the view data.
     *
     * @throws Exception if there is any error.
     * @since 1.4
     */
    private void setProjectPermissionInfo() throws Exception {
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        final List<Permission> permissionsByProject = getPermissionServiceFacade().getPermissionsByProject(currentUser, getFormData().getProjectId());

        ProjectPermissionInfoDTO permissionInfo = new ProjectPermissionInfoDTO();

        if (permissionsByProject != null) {
            for(Permission p : permissionsByProject) {
                if(p.getPermissionType().getPermissionTypeId() == PermissionType.PERMISSION_TYPE_PROJECT_REPORT) {
                    permissionInfo.setReportPermissionNumber(permissionInfo.getReportPermissionNumber() + 1);
                } else if(p.getPermissionType().getPermissionTypeId() == PermissionType.PERMISSION_TYPE_PROJECT_READ) {
                    permissionInfo.setReadPermissionNumber(permissionInfo.getReadPermissionNumber() + 1);
                } else if(p.getPermissionType().getPermissionTypeId() == PermissionType.PERMISSION_TYPE_PROJECT_WRITE) {
                    permissionInfo.setWritePermissionNumber(permissionInfo.getWritePermissionNumber() + 1);
                } else if(p.getPermissionType().getPermissionTypeId() == PermissionType.PERMISSION_TYPE_PROJECT_FULL) {
                    permissionInfo.setFullPermissionNumber(permissionInfo.getFullPermissionNumber() + 1);
                }
            }
        }

        getViewData().getProjectGeneralInfo().setPermissionInfo(permissionInfo);

        getViewData().getProjectGeneralInfo().setCanAccessPermissionEdit(DirectUtils.isCockpitAdmin(currentUser)
                || DirectUtils.isTcOperations(currentUser)
                || DirectUtils.isTcStaff(currentUser));
    }

    /**
     * Sets the flag of export.
     *
     * @param export flag on whether export.
     */
    public void setExport(boolean export) {
        this.export = export;
    }

    public boolean getExport() {
        return this.export;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
