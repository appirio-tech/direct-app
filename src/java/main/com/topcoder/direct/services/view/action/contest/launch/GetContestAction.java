/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.topcoder.direct.services.view.dto.contest.*;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import org.apache.struts2.ServletActionContext;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDetailsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.management.project.CopilotContestExtraInfo;
import com.topcoder.management.project.CopilotContestExtraInfoType;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCopilotType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>
 * This action will get a contest with the given id. This action requires <code>projectId</code> or
 * <code>contestId</code> parameter (mutual exclusive) be present for each call.
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are reused).
 * This class is mutable and stateful: it's not thread safe.
 * </p>
 * <p>
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note
 * <ul>
 * <li>Adds the legacy code to show other parts of the contest detail page.</li>
 * <li>Preserves the studio competition to fill the details later.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.2 - View/Edit/Activate Software Contests v1.0 Assembly Change Note
 * <ul>
 * <li>Preserves the software competition to fill the details later.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.2.1 - Direct - Contest Dashboard Assembly Change Note
 * <ul>
 * <li>Updated {@link #executeAction()} method to retrieve data for contest dashboard section.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.2.2 - Direct - Project Dashboard Assembly Change Note
 * <ul>
 * <li>Updated {@link #executeAction()} method to retrieve data for contest dashboard section use non-cached model.</li>
 * </ul>
 * <p>
 * Version 1.2.3 - Direct - Project Dashboard Assembly Change Note
 * <ul>
 * <li>Updated {@link #executeAction()} method to retrieve data for contest dashboard section use non-cached model.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.2.4 - TC Direct Release Assembly 7 Change Note
 * <ul>
 * <li>Updated {@link #executeAction()} method to set hasContestWritePermission flag to the view data.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.2.5 - TC Cockpit Bug Tracking R1 Cockpit Project Tracking version 1.0 Change Note
 * <ul>
 * <li>Updated {@link #executeAction()} method to set UnresolvedIssuesNumber to the dashboard view data.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 - TC Direct Replatforming Release 1 Change Note
 * <ul>
 * <li>Remove contestId and studioCompetition fields and getter/setter for them because this studio contest is the same as software contest now.</li>
 * <li>Update {@link #executeAction()} method to only handle the <code>SoftwareCompetition</code>.</li>
 * <li>Update PrizeSortByPlace to sort <code>Prize</code>.</li>
 * <li>Update {@link #isSoftware()} method to use the new logic to determine whether a contest is a software contest or not.</li>
 * <li>Update {@link #getViewData()} method to only handle the <code>SoftwareCompetition</code>.</li>
 * <li>Remove fillContestStats method because we are using DirectUtils.getContestStats method instead.</li>
 * <li>Update {@link #getSessionData()} method to only handle the <code>SoftwareCompetition</code>.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.4 - TC Direct Replatforming Release 4 Change Note
 * <ul>
 * <li>Update {@link #isSoftware()} method to support the copilot contest as a software contest.</li>
 * <li>Update {@link #executeAction()} method to set the contest type to studio if contestStats.getIsStudio returns true.</li>
 * </ul>
 * </p>
 * 
 * @author fabrizyo, FireIce, isv, morehappiness
 * @version 1.4
 */
public class GetContestAction extends ContestAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = 7980735050638514625L;

    /**
     * <p>
     * This is the id of project of contest.
     * </p>
     * <p>
     * It's used to retrieve the software competition. It can be 0 (it means not present) or greater than 0 if it's
     * present. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * view data. It is copied from old details page to preserve some portion of the existing page.
     * </p>
     */
    private ContestDetailsDTO viewData;

    /**
     * <p>
     * Session data. It is copied from old details page to preserve some portion of the existing page.
     * </p>
     */
    private SessionData sessionData;

    /**
     * <p>
     * <code>softwareCompetition</code> to hold the software competition.
     * </p>
     */
    private SoftwareCompetition softwareCompetition;

    /**
     * <p>Copilots for this contest</p>
     */
    private List<ContestCopilotDTO> copilots;

    /**
     * <p>The copilot cost of the contest</p>
     */
    private double copilotCost;

    /**
    * <p> Whether user is admin </p>
    */
    private boolean admin;
    
    private Set<Long> copilotProjectTypes;
    
    private String budget;
    
    private String otherManagingExperienceString;
    
    /**
     * <p>
     * Creates a <code>GetContestAction</code> instance.
     * </p>
     */
    public GetContestAction() {
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * The returned software or studio contest will be available as result.
     * </p>
     *
     * @throws IllegalStateException if the contest service facade is not set.
     * @throws Exception if any other error occurs
     * @see ContestServiceFacade#getContest(com.topcoder.security.TCSubject, long)
     * @see ContestServiceFacade#getSoftwareContestByProjectId(com.topcoder.security.TCSubject, long)
     */
    protected void executeAction() throws Exception {
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();

        if (null == contestServiceFacade) {
            throw new IllegalStateException("The contest service facade is not initialized.");
        }

        if (projectId <= 0) {
            throw new DirectException("projectId less than 0 or not defined.");
        }

        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        admin = DirectUtils.isRole(currentUser, "Administrator");
            softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(DirectStrutsActionsHelper
                .getTCSubjectFromSession(), projectId);
        setResult(softwareCompetition);
        // Set contest stats
        ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, projectId);
        if(DirectUtils.isStudio(softwareCompetition)) {
            softwareCompetition.setType(CompetionType.STUDIO);
        }
        getViewData().setContestStats(contestStats);
        getViewData().setDashboard(DataProvider.getContestDashboardData(projectId, DirectUtils.isStudio(softwareCompetition), false));
        DirectUtils.setDashboardData(currentUser, projectId, getViewData(), getContestServiceFacade(), !DirectUtils.isStudio(softwareCompetition));
        
        if (softwareCompetition.getProjectData().getContestSales() != null && softwareCompetition.getProjectData().getContestSales().size() > 0)
        {
            contestStats.setPaymentReferenceId(softwareCompetition.getProjectData().getContestSales().get(0).getSaleReferenceId());
        }

        List<Prize> prizes = softwareCompetition.getProjectHeader().getPrizes();
        if (prizes != null) {
            Collections.sort(prizes, new PrizeSortByPlace());
                        
            if (softwareCompetition.getProjectData().getContestSales() != null && softwareCompetition.getProjectData().getContestSales().size() > 0)
            {
                contestStats.setPaymentReferenceId(softwareCompetition.getProjectData().getContestSales().get(0).getSaleReferenceId());
            }

            // set copilots
            this.copilots = DataProvider.getCopilotsForDirectProject(softwareCompetition.getProjectHeader().getTcDirectProjectId());

            this.copilotCost = calculateCopilotCost(softwareCompetition.getResources());
            
            // set contest permission
            viewData.setHasContestWritePermission(DirectUtils
                    .hasWritePermission(this, currentUser, projectId, false));

            // set whether to show spec review
            viewData.setShowSpecReview(getSpecificationReviewService()
                    .getSpecificationReview(currentUser, projectId) != null);
        }

        // calculate the contest issues tracking health
        getViewData().getDashboard().setUnresolvedIssuesNumber(getViewData().getContestStats().getIssues().getUnresolvedIssuesNumber());
        DashboardHelper.setContestStatusColor(getViewData().getDashboard());

        if (softwareCompetition.getProjectHeader().getProjectCategory().getId() == 29) {
            // set project
            Project project = getProjectServices().getProject(projectId);
            copilotProjectTypes = new HashSet<Long>();
            for (ProjectCopilotType type : project.getProjectCopilotTypes()) {
                copilotProjectTypes.add(type.getId());
            }

            budget = null;
            otherManagingExperienceString = null;
            for (CopilotContestExtraInfo extraInfo : project.getCopilotContestExtraInfos()) {
                if (extraInfo.getType().getId() == CopilotContestExtraInfoType.BUDGET.getId()) {
                    budget = extraInfo.getValue();
                }
                if (extraInfo.getType().getId() == CopilotContestExtraInfoType.OTHER_MANAGING_EXPERIENCE.getId()) {
                    otherManagingExperienceString = extraInfo.getValue();
                }
            }

        }
    }

    /**
     * <p>
     * Gets the project id.
     * </p>
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the project id.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception.
     * </p>
     *
     * @param projectId the project id to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
    /**
     * <p>Gets the copilots of the software contest</p>
     *
     * @return the copilots of the software contest.
     */
    public List<ContestCopilotDTO> getCopilots() {
        return copilots;
    }

    /**
     * <p>Gets the copilot cost</p>
     *
     * @return the cost of the copilots.
     */
    public double getCopilotCost() {
        return copilotCost;
    }
    /**
     * <p>
     * Determines if it is software contest or not.
     * </p>
     *
     * @return true if it is software contest
     */
    public boolean isSoftware() {
        return !DirectUtils.isStudio(softwareCompetition);
    }

    /**
     * <p>
     * Gets the view data.
     * </p>
     *
     * @return the view data
     * @throws Exception if any error occurs
     */
    public ContestDetailsDTO getViewData() throws Exception {

        if (viewData == null) {
            viewData = new ContestDetailsDTO();

            final long testContestId = 4;
            ContestDTO contestDTO = DataProvider.getContest(testContestId);
            viewData.setContest(contestDTO);

            // project

            // right side
            List<ProjectBriefDTO> projects = DataProvider.getUserProjects(getSessionData().getCurrentUserId());
            UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
            userProjectsDTO.setProjects(projects);
            viewData.setUserProjects(userProjectsDTO);
        }

        return viewData;
    }
    
    /**
     * The <code>Comparator</code> to compare two <code>Prize</code> object ordered by the prize place.
     * 
     * @author TCSASSEMBER
     */
    public class PrizeSortByPlace implements Comparator<Prize>{
        public int compare(Prize o1, Prize o2) {
            if (o1.getPlace() > o2.getPlace())
                return 1;
            else if(o1.getPlace() < o2.getPlace())
                return -1;
            else
                return 0;
        }
    }

    /**
     * <p>
     * Gets the session data.
     * </p>
     *
     * @return the session data
     * @throws Exception if any error occurs
     */
    public SessionData getSessionData() throws Exception {
        if (sessionData == null) {
            HttpServletRequest request = ServletActionContext.getRequest();

            HttpSession session = request.getSession(false);
            if (session != null) {
                sessionData = new SessionData(session);
                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(softwareCompetition.getProjectHeader().getId());
                project.setName(getProjectName(softwareCompetition.getProjectHeader().getTcDirectProjectId()));
                sessionData.setCurrentProjectContext(project);

                 long directProjectId = softwareCompetition.getProjectHeader().getTcDirectProjectId();

                sessionData.setCurrentSelectDirectProjectID(directProjectId);
            }
        }
        return sessionData;
    }

    /**
     * <p>
     * Gets project name. NOTE: it is fixing some bug which software competition project header is missing project
     * name population.
     * </p>
     *
     * @param projectId client project id
     * @return the project name. It could be null if no match is found.
     * @throws Exception if any error occurs
     */
    private String getProjectName(long projectId) throws Exception {
        try {
            /*for (ProjectData project : getProjects()) {
                if (projectId == project.getProjectId()) {
                    return project.getName();
                }
            }*/
            List<ProjectBriefDTO> projects = DataProvider.getUserProjects(getCurrentUser().getUserId());

            for (ProjectBriefDTO project : projects) {
                if (projectId == project.getId()) {
                    return project.getName();
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Calculate the copilot cost by checking the contest resources payment.
     *
     * @param resources all the resources of the contest.
     * @return the copilot cost.
     */
    private double calculateCopilotCost(Resource[] resources) {
        double result = 0;

        for (Resource r : resources) {
            if (r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {
                String copilotPaymentStr = r.getProperty("Payment");
                double copilotFee = 0;

                try {
                    copilotFee = Double.valueOf(copilotPaymentStr);
                } catch (Exception ex) {
                    // ignore
                }
                result += copilotFee;
            }
        }
        return result;
    }

    /**
    * <p>Check whether user is admin</p>
    *
    * @return whether logged in user is admin
    */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * <p>
     * Gets the mapping to be used for looking up the project copilot types by IDs.
     * </p>
     * @return a <code>Map</code> mapping the project copilot type ids to category names.
     * @throws Exception
     *             if an unexpected error occurs.
     * @since 1.5
     */
    public Map<Long, String> getAllProjectCopilotTypes() throws Exception {
        return DataProvider.getAllProjectCopilotTypes();
    }

    /**
     * @return the copilotProjectTypes
     */
    public Set<Long> getCopilotProjectTypes() {
        return copilotProjectTypes;
    }

    /**
     * @return the budget
     */
    public String getBudget() {
        return budget;
    }

    /**
     * @return the otherManagingExperienceString
     */
    public String getOtherManagingExperienceString() {
        return otherManagingExperienceString;
    }    
}
