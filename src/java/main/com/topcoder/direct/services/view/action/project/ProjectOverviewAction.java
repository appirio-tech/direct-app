/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.model.CopilotProjectFeedback;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.ActivityDTO;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
import com.topcoder.direct.services.view.dto.contest.ContestIssuesTrackingDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.project.LatestProjectActivitiesDTO;
import com.topcoder.direct.services.view.dto.project.ProjectCopilotStatDTO;
import com.topcoder.direct.services.view.dto.project.ProjectOverviewDTO;
import com.topcoder.direct.services.view.dto.project.ProjectPermissionInfoDTO;
import com.topcoder.direct.services.view.dto.project.ProjectStatsDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;
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
 * <p>
 * Version 2.0 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
 * <li>
 *     Add methods {@link #getProjectStatsAjax()} and {@link #getProjectActivitiesAjax()} to get the parts of the project
 *     overview via ajax to improve the performance. The codes to get project stats and activities are removed from
 *     the preprocessor of the action and execute method of the action.
 * </li>
 * </p>
 *
 * <p>
 * Version 2.1 (Module Assembly - TopCoder Copilot Feedback Integration) changes:
 * <ul>
 *     <li>Add the logic of getting copilots feedback for the project overview page</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.2 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 * <ol>
 *     <li>Read TopCoder Account Managers for the project</li>
 *     <li>Add the email addresses for the TopCoder Platform Specialists</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 2.3 (Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly)
 * <ul>
 *     <li>Adds email data for client managers and TopCoder platform managers.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.4 (topcoder Direct - Add Project Billings to project overview) @author deedee @challenge 30045142
 * <ul>
 *     <li>Update {@link #execute()} to set billing accounts</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.5 (topcoder Direct Refactor Jira RPC and VM Count Retrieval to separate AJAX requests)
 * @author Veve @challenge 30045453
 * <ul>
 *     <li>Add method {@link #getProjectIssuesStatsAjax()}</li>
 *     <li>Update method {@link #getProjectStatsAjax()}</li>
 * </ul>
 * </p>
 *
 * @author isv, Veve, Blues, GreatKevin
 * @version 2.5
 */
public class ProjectOverviewAction extends AbstractAction implements FormAction<ProjectIdForm>,
                                                                     ViewAction<ProjectOverviewDTO> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(ProjectOverviewAction.class);

    /*
     * <p>The constant string for filtering out active bug races in JQL query</p>
     */
    private static final String FILTER_ACTIVE_BUG_RACES = "status = Open OR status = \"In Progress\" OR status = Reopened";

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
     * The copilot project DAO.
     *
     * @since 2.1
     */
    private CopilotProjectDAO copilotProjectDAO;

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
     * The member profile url base.
     */
    private String memberProfileUrlBase;

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
     * Gets the copilot project DAO.
     *
     * @return the copilot project DAO.
     * @since 2.1
     */
    public CopilotProjectDAO getCopilotProjectDAO() {
        return copilotProjectDAO;
    }

    /**
     * Sets the copilot project DAO.
     *
     * @param copilotProjectDAO the copilot project DAO.
     * @since 2.1
     */
    public void setCopilotProjectDAO(CopilotProjectDAO copilotProjectDAO) {
        this.copilotProjectDAO = copilotProjectDAO;
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
            try {
                ProjectStatsDTO projectStats = new ProjectStatsDTO();
                getViewData().setProjectStats(projectStats);

                // get project
                ProjectData project = getProjectServiceFacade().getProject(DirectUtils.getTCSubjectFromSession(), formData.getProjectId());
                getViewData().getProjectStats().setProject(DirectUtils.createProjectBriefDTOFromProjectData(project));
                // set project billing accounts
                viewData.setBillingAccounts(getProjectServiceFacade().getBillingAccountsByProject(getFormData().getProjectId()));
                getSessionData().setCurrentProjectContext(getViewData().getProjectStats().getProject());

                // set the current direct project id in session, the contest details
                // codes incorrectly
                // use setCurrentProjectContext to override the current chosen
                // direct project with current
                // chosen contest, for the safe, we put the direct project id into
                // session separately again
                getSessionData().setCurrentSelectDirectProjectID(
                        getSessionData().getCurrentProjectContext().getId());

                if (!export) {
                    // Check if the project's forum has any threads
                    long forumThreadsCount = DataProvider.getTopCoderDirectProjectForumThreadsCount(project.getProjectId());
                    getViewData().setHasForumThreads(forumThreadsCount > 0);
                } else {
                    // we need actual cost and projected cost for export
                    projectStats = DataProvider.getProjectStats(DirectUtils.getTCSubjectFromSession(), getFormData().getProjectId());
                    viewData.setProjectStats(projectStats);
                    setDashboardProjectStat();
                }

                // gets and sets the statistics of the project copilots
                setCopilotStats(DataProvider.getDirectProjectCopilotStats(formData.getProjectId()));

                // set the copilot feedback data
		        setCopilotsFeedback();

                // set all data for project general information table to the view data
                setProjectGeneralInfo(project);

                if(export) {
                    for(long clientId : viewData.getProjectGeneralInfo().getClientManagers()) {
                        viewData.getProjectGeneralInfo().getClientManagersHandles().put(clientId, userService.getUserHandle(clientId));
                    }
                    for(long managerId : viewData.getProjectGeneralInfo().getTopcoderManagers()) {
                        viewData.getProjectGeneralInfo().getTopcoderManagersHandles().put(managerId, userService.getUserHandle(managerId));
                    }
                    for (long accountManagerId : viewData.getProjectGeneralInfo().getAccountManagers()) {
                        viewData.getProjectGeneralInfo().getAccountManagersHandles().put(accountManagerId, userService.getUserHandle(accountManagerId));
                        viewData.getProjectGeneralInfo().getAccountManagersHandles().put(accountManagerId, userService.getUserHandle(accountManagerId));
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
     * Sets the feedback of the copilots of the project.
     *
     * @throws Exception if there is any error.
     * @since 2.1
     */
    private void setCopilotsFeedback() throws Exception {

        final List<ProjectCopilotStatDTO> copilotProjects = getCopilotStats();
        long currentUserId = DirectUtils.getTCSubjectFromSession().getUserId();

        // get feedback for each copilot of this project
        for (ProjectCopilotStatDTO cp : copilotProjects) {
            final long copilotProjectId = cp.getCopilotInfo().getCopilotProjectId();

            final CopilotProjectFeedback feedback = getCopilotProjectDAO().getCopilotProjectFeedback(copilotProjectId, currentUserId);

            // the feedback can be null if the copilot does not have any feedback for this project
            cp.setFeedback(feedback);
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
     * Gets the project statistics via ajax.
     *
     * @return the result code.
     * @since 2.0
     */
    public String getProjectStatsAjax() {

        try {
            // get project stats
            ProjectStatsDTO projectStats = DataProvider.getProjectStats(DirectUtils.getTCSubjectFromSession(), getFormData().getProjectId());
            viewData.setProjectStats(projectStats);

            // get and set dashboard stats
            setDashboardProjectStat();

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("draftContestsNumber", projectStats.getDraftContestsNumber());
            result.put("pipelineContestsNumber", projectStats.getPipelineContestsNumber());
            result.put("runningContestsNumber", projectStats.getRunningContestsNumber());
            result.put("finishedContestsNumber", projectStats.getFinishedContestsNumber());
            result.put("cancelledNumber", viewData.getDashboardProjectStat().getCancelledNumber());
            result.put("completedNumber", viewData.getDashboardProjectStat().getCompletedNumber());
            result.put("totalMemberCost", viewData.getDashboardProjectStat().getTotalMemberCost());
            result.put("averageMemberCostPerContest", viewData.getDashboardProjectStat().getAverageMemberCostPerContest());
            result.put("totalContestFee", viewData.getDashboardProjectStat().getTotalContestFee());
            result.put("averageContestFeePerContest", viewData.getDashboardProjectStat().getAverageContestFeePerContest());
            result.put("totalProjectCost", viewData.getDashboardProjectStat().getTotalProjectCost());
            result.put("averageContestDuration", viewData.getDashboardProjectStat().getAverageContestDuration());
            result.put("averageFulfillment", viewData.getDashboardProjectStat().getAverageFulfillment());

            setResult(result);
        } catch (Throwable error) {
            if(getModel() != null) {
                setResult(error);
            }
        }

        return SUCCESS;
    }

    public String getProjectIssuesStatsAjax() {
        try {


            Map<String, Integer> result = new HashMap<String, Integer>();

            // get and set project issues statistics
            List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                    getSessionData().getCurrentUserId(), getFormData().getProjectId());
            Map<ContestBriefDTO, ContestIssuesTrackingDTO> issues = DataProvider.getDirectProjectIssues(contests);

            int totalUnresolvedIssues = 0;
            int totalOngoingBugRaces = 0;

            for (Map.Entry<ContestBriefDTO, ContestIssuesTrackingDTO> contestIssues : issues.entrySet()) {
                totalUnresolvedIssues += contestIssues.getValue().getUnresolvedIssuesNumber();
                totalOngoingBugRaces += contestIssues.getValue().getUnresolvedBugRacesNumber();
            }

            // count project level issues
            List<TcJiraIssue> activeProjectLevelBugRaces = JiraRpcServiceWrapper.getBugRacesForDirectProject(
                    getFormData().getProjectId(), FILTER_ACTIVE_BUG_RACES);
            totalOngoingBugRaces += activeProjectLevelBugRaces.size();

            result.put("unresolvedIssuesNumber", totalUnresolvedIssues);
            result.put("ongoingBugRacesNumber", totalOngoingBugRaces);

            setResult(result);

        } catch (Throwable error) {
            if(getModel() != null) {
                setResult(error);
            }
        }

        return SUCCESS;
    }


    /**
     * Gets the project activities via ajax.
     *
     * @return the result code
     * @since 2.0
     */
    public String getProjectActivitiesAjax() {

        try {
            LatestProjectActivitiesDTO latestActivities
                    = DataProvider.getLatestActivitiesForProject(DirectUtils.getTCSubjectFromSession().getUserId(), getFormData().getProjectId());
            viewData.setLatestProjectActivities(latestActivities);

            Map<String, List<Map<String, Object>>> m = new HashMap<String, List<Map<String, Object>>>();
            for(Map.Entry<ContestBriefDTO, List<ActivityDTO>>  entry: latestActivities.getActivities().entrySet()) {
                List<Map<String, Object>> activities = new ArrayList<Map<String, Object>>();
                for(ActivityDTO activityDTO : entry.getValue()) {
                    Map<String, Object> activity = new HashMap<String, Object>();
                    activity.put("title", entry.getKey().getTitle());
                    activity.put("typeShortName", activityDTO.getType().getShortName());
                    activity.put("typeName", activityDTO.getType().getName());
                    activity.put("typeActionText", activityDTO.getType().getActionText());
                    activity.put("contestId", activityDTO.getContest().getId());
                    activity.put("originatorId", activityDTO.getOriginatorId());
                    activity.put("originatorHandle", activityDTO.getOriginatorHandle());
                    activity.put("date", activityDTO.getDate());
                    activity.put("memberProfileUrlBase", getMemberProfileUrlBase());

                    activities.add(activity);
                }
                m.put(Long.toString(entry.getKey().getId()), activities);
            }

            setResult(m);
        } catch (Throwable error) {
            if(getModel() != null) {
                setResult(error);
            }
        }

        return SUCCESS;
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
        List<Long> accountManagers = new ArrayList<Long>();
        Long appirioManager;
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
            } else if (keyId == 14L) {
                // topcoder account managers
                accountManagers.add(Long.parseLong(m.getMetadataValue()));
            } else if (keyId == 15L) {
                // topcoder appirio manager
                getViewData().getProjectGeneralInfo().setAppirioManager(Long.parseLong(m.getMetadataValue()));
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
        getViewData().getProjectGeneralInfo().setAccountManagers(accountManagers);

        for(Long platformManagerId : tcManagers) {
            getViewData().getProjectGeneralInfo().getProjectResourceEmails().put(platformManagerId, userService.getEmailAddress(platformManagerId));
        }

        for(Long clientManagerId : clientManagers) {
            getViewData().getProjectGeneralInfo().getProjectResourceEmails().put(clientManagerId, userService.getEmailAddress(clientManagerId));
        }

        for(Long accountManagerId : accountManagers) {
            getViewData().getProjectGeneralInfo().getProjectResourceEmails().put(accountManagerId, userService.getEmailAddress(accountManagerId));
        }

        int actualCost = 0;

        if(getViewData().getDashboardProjectStat() != null) {
            // for export only
            actualCost = (int) Math.round(getViewData().getDashboardProjectStat().getTotalProjectCost());
        }


        getViewData().getProjectGeneralInfo().setActualCost(actualCost);

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

        if(getPermissionServiceFacade() == null) {
            throw new IllegalStateException("The permission service facade is not initialized");
        }

        final List<Permission> permissionsByProject = getPermissionServiceFacade().getPermissionsByProject(currentUser, getFormData().getProjectId());

        ProjectPermissionInfoDTO permissionInfo = new ProjectPermissionInfoDTO();
        boolean hasFullPermission = false;

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
                    if (p.getUserId() == currentUser.getUserId())
                    {
                        hasFullPermission = true;
                    }
                }
            }
        }

        getViewData().getProjectGeneralInfo().setPermissionInfo(permissionInfo);

        getViewData().getProjectGeneralInfo().setCanAccessPermissionEdit(hasFullPermission);
    }

    /**
     * Sets the flag of export.
     *
     * @param export flag on whether export.
     */
    public void setExport(boolean export) {
        this.export = export;
    }

    /**
     * Gets the export flag.
     *
     * @return the export flag.
     */
    public boolean getExport() {
        return this.export;
    }

    /**
     * Gets the user service.
     *
     * @return the user service.
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Sets the user service.
     *
     * @param userService the user service.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets the memberProfileUrlBase.
     *
     * @return the memberProfileUrlBase.
     */
    public String getMemberProfileUrlBase() {
        return memberProfileUrlBase;
    }

    /**
     * Sets the memberProfileUrlBase.
     *
     * @param memberProfileUrlBase the memberProfileUrlBase.
     */
    public void setMemberProfileUrlBase(String memberProfileUrlBase) {
        this.memberProfileUrlBase = memberProfileUrlBase;
    }
}
