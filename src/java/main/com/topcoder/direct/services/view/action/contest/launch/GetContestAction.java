/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.view.dto.contest.*;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.service.project.ProjectData;
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
 * <li>Remove contestId and studioCompetition fields and getter/setter for them because this studio contest is the
 * same as software contest now.</li>
 * <li>Update {@link #executeAction()} method to only handle the <code>SoftwareCompetition</code>.</li>
 * <li>Update PrizeSortByPlace to sort <code>Prize</code>.</li>
 * <li>Update {@link #isSoftware()} method to use the new logic to determine whether a contest is a software contest
 * or not.</li>
 * <li>Update {@link #getViewData()} method to only handle the <code>SoftwareCompetition</code>.</li>
 * <li>Remove fillContestStats method because we are using DirectUtils.getContestStats method instead.</li>
 * <li>Update {@link #getSessionData()} method to only handle the <code>SoftwareCompetition</code>.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.4 - TC Direct Replatforming Release 4 Change Note
 * <ul>
 * <li>Update {@link #isSoftware()} method to support the copilot contest as a software contest.</li>
 * <li>Update {@link #executeAction()} method to set the contest type to studio if contestStats.getIsStudio returns
 * true.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.5 - BUGR-6609 Change Note
 * <ul>
 * <li>Added {@link #subEndDate} and {@link #contestEndDate} fields. Also the getters were added.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.6 - Release Assembly - TC Direct Cockpit Release Four change note
 * <ol>
 *     <li>Update {@link #getProjectName(long)} to use project service to retrieve project name</li>
 * </ol>
 * </p>
 *
 * @author fabrizyo, FireIce, isv, morehappiness, GreatKevin
 * @version 1.6
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
     * The action type.
     * </p>
     *
     * @see TYPE
     */
    private TYPE type;

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
     * <p>
     * Copilots for this contest
     * </p>
     */
    private List<ContestCopilotDTO> copilots;

    /**
     * <p>
     * The copilot cost of the contest
     * </p>
     */
    private double copilotCost;

    /**
     * <p>
     * Whether user is admin
     * </p>
     */
    private boolean admin;

    private Set<Long> copilotProjectTypes;

    private String budget;

    private String otherManagingExperienceString;

    /**
     * The submission end date.
     * @since 1.5
     */
    private String subEndDate;
    
    /**
     * The contest end date.
     * @since 1.5
     */
    private String contestEndDate;
    
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

        if (type == null) {
            throw new DirectException("action type is null.");
        }

        HttpSession session = DirectUtils.getServletRequest().getSession(true);

        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        admin = DirectUtils.isRole(currentUser, "Administrator");

        final String SESSION_KEY = String.valueOf(projectId);

        // retrieve software competition
        // TODO: this is TEMPORARY solution to eliminate same call for contest and contest json
        if (this.type == TYPE.CONTEST_JSON && session.getAttribute(SESSION_KEY) != null) {
            softwareCompetition = (SoftwareCompetition) session.getAttribute(SESSION_KEY);
            session.removeAttribute(String.valueOf(projectId));
        }

        if (softwareCompetition == null) {
            softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(DirectStrutsActionsHelper
                .getTCSubjectFromSession(), projectId);
        }
        if (DirectUtils.isStudio(softwareCompetition)) {
            softwareCompetition.setType(CompetionType.STUDIO);
        }
        setResult(softwareCompetition);
        subEndDate = DirectUtils.getDateString(DirectUtils.getSubmissionEndDate(softwareCompetition));
        contestEndDate = DirectUtils.getDateString(DirectUtils.getEndDate(softwareCompetition));

        // depends on the type :
        // 1. if contest, store softwareCompetition in session
        // 2. if json data for contest, stops here since we are getting it
        // 3. if copilot, keep going
        switch(this.type) {
          case CONTEST:
               session.setAttribute(SESSION_KEY, softwareCompetition);
               break;
          case CONTEST_JSON:
               return;
          case COPILOT_CONTEST:
        }

        // Set contest stats
        ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, projectId);
        getViewData().setContestStats(contestStats);

        getViewData().setDashboard(
            DataProvider.getContestDashboardData(projectId, DirectUtils.isStudio(softwareCompetition), false));
        DirectUtils.setDashboardData(currentUser, projectId, getViewData(), getContestServiceFacade(), !DirectUtils
            .isStudio(softwareCompetition));

        if (softwareCompetition.getProjectData().getContestSales() != null
            && softwareCompetition.getProjectData().getContestSales().size() > 0) {
            contestStats.setPaymentReferenceId(softwareCompetition.getProjectData().getContestSales().get(0)
                .getSaleReferenceId());
        }

        List<Prize> prizes = softwareCompetition.getProjectHeader().getPrizes();
        if (prizes != null) {
            Collections.sort(prizes, new PrizeSortByPlace());

            if (softwareCompetition.getProjectData().getContestSales() != null
                && softwareCompetition.getProjectData().getContestSales().size() > 0) {
                contestStats.setPaymentReferenceId(softwareCompetition.getProjectData().getContestSales().get(0)
                    .getSaleReferenceId());
            }

            // set copilots
            this.copilots = DataProvider.getCopilotsForDirectProject(softwareCompetition.getProjectHeader()
                .getTcDirectProjectId());

            this.copilotCost = calculateCopilotCost(softwareCompetition.getResources());

            // set contest permission
            viewData
                .setHasContestWritePermission(DirectUtils.hasWritePermission(this, currentUser, projectId, false));

            // set whether to show spec review
            viewData.setShowSpecReview(contestStats.isShowSpecReview());
        }

        // calculate the contest issues tracking health
        getViewData().getDashboard().setUnresolvedIssuesNumber(
            getViewData().getContestStats().getIssues().getUnresolvedIssuesNumber());
        DashboardHelper.setContestStatusColor(getViewData().getDashboard());

        getViewData().getDashboard().setAllPhases(sortContestPhases(getViewData().getDashboard().getAllPhases()));

        if (softwareCompetition.getProjectHeader().getProjectCategory().getId() == 29) {
            // set project
            Project project = getProjectServices().getProject(projectId);
            copilotProjectTypes = new HashSet<Long>();

            if (project.getProjectCopilotTypes() != null && project.getProjectCopilotTypes().size() > 0) {
                for (ProjectCopilotType type : project.getProjectCopilotTypes()) {
                    copilotProjectTypes.add(type.getId());
                }
            }

            budget = null;
            otherManagingExperienceString = null;
            if (project.getCopilotContestExtraInfos() != null && project.getCopilotContestExtraInfos().size() > 0) {
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
    }

    /**
     * Gets whether the contest is scheduled
     * @since 1.6
     */
    public String specReviewScheduled() {
        try {

            if (null == getContestServiceFacade()) {
                throw new IllegalStateException("The contest service facade is not initialized.");
            }

            if (projectId <= 0) {
                throw new DirectException("projectId less than 0 or not defined.");
            }

            final Submission[] activeSpecSubmission = getContestServiceFacade().getSoftwareActiveSubmissions(
                    getProjectId(), 2);

            Map<String, String> result = new HashMap<String, String>();

            if (activeSpecSubmission != null && activeSpecSubmission.length >= 1) {
                // there is active specification submission
                result.put("specReviewScheduled", "true");
            } else {
                // no active specification submission
                result.put("specReviewScheduled", "false");
            }

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e);
            }
            return ERROR;
        }
        return SUCCESS;
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
     * /**
     * <p>
     * Gets the copilots of the software contest
     * </p>
     *
     * @return the copilots of the software contest.
     */
    public List<ContestCopilotDTO> getCopilots() {
        return copilots;
    }

    /**
     * <p>
     * Gets the copilot cost
     * </p>
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
    public class PrizeSortByPlace implements Comparator<Prize> {
        public int compare(Prize o1, Prize o2) {
            if (o1.getPlace() > o2.getPlace())
                return 1;
            else if (o1.getPlace() < o2.getPlace())
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
    public SessionData getSessionData() {
        if (sessionData == null || sessionData.getCurrentProjectContext() == null) {

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);

            if (sessionData == null) {
                sessionData = new SessionData(session);
            }

            if (session != null) {
                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(softwareCompetition.getProjectHeader().getId());
                try {
                    project.setName(getProjectName(softwareCompetition.getProjectHeader().getTcDirectProjectId()));
                } catch (Exception ex) {
                    // ignore
                }
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
     * <p>
     * Update in version 1.6 - use project service facade to get the project name
     * </p>
     *
     * @param projectId client project id
     * @return the project name. It could be null if no match is found.
     * @throws Exception if any error occurs
     */
    private String getProjectName(long projectId) throws Exception {
        try {
            // for project which is not active, the project name cannot be find u

            final ProjectData project = getProjectServiceFacade().getProject(getCurrentUser(), projectId);

            if (project != null) {
                return project.getName();
            } else {
                return null;
            }
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
     * <p>
     * Check whether user is admin
     * </p>
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
     *
     * @return a <code>Map</code> mapping the project copilot type ids to category names.
     * @throws Exception if an unexpected error occurs.
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

    /**
     * <p>
     * Sets the action type.
     * </p>
     *
     * @param type the type to set
     */
    public void setType(TYPE type) {
        this.type = type;
    }

    /**
     * Gets the submission end date.
     * @since 1.5
     */
    public String getSubEndDate() {
        return subEndDate;
    }
    
    /**
     * Gets the contest end date.
     * @since 1.5
     */
    public String getContestEndDate() {
        return contestEndDate;
    }
    
    /**
     * <p>
     * The static type enum to indicate in which mode this action is being called.
     * </p>
     *
     * @author BeBetter
     */
    public static enum TYPE {
        COPILOT_CONTEST, CONTEST, CONTEST_JSON
    }

    private static List<ProjectPhaseDTO> sortContestPhases(List<ProjectPhaseDTO> phases) {
        List<ProjectPhaseDTO> specPart = new ArrayList<ProjectPhaseDTO>();
        List<ProjectPhaseDTO> reviewPart = new ArrayList<ProjectPhaseDTO>();
        List<ProjectPhaseDTO> finalPart = new ArrayList<ProjectPhaseDTO>();
        
        for(ProjectPhaseDTO p : phases) {
            if(p.getPhaseType().getOrder() <= ProjectPhaseType.SPECIFICATION_REVIEW.getOrder()) {
                specPart.add(p);
            } else if (p.getPhaseType().getOrder() >= ProjectPhaseType.FINAL_FIX.getOrder()) {
                finalPart.add(p);
            } else {
                reviewPart.add(p);
            }
        }

        StartDateComparator sc = new StartDateComparator();
        PhaseOrderComparator pc = new PhaseOrderComparator();
        Collections.sort(specPart, sc);
        Collections.sort(finalPart, sc);
        Collections.sort(reviewPart, pc);

        specPart.addAll(reviewPart);
        specPart.addAll(finalPart);
        
//        for(ProjectPhaseDTO p : specPart) {
//            System.out.println("start date:" + p.getStartTime() + " phase name:" + p.getPhaseType().getShortName() + " phase order:" + p.getPhaseType().getOrder());
//        }

        return specPart;

    }

    private static class StartDateComparator implements Comparator<ProjectPhaseDTO>{

        public int compare(ProjectPhaseDTO o1, ProjectPhaseDTO o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    }

    private static class PhaseOrderComparator implements Comparator<ProjectPhaseDTO>{

            public int compare(ProjectPhaseDTO p1, ProjectPhaseDTO p2) {
                int o1 = p1.getPhaseType().getOrder();
                int o2 = p2.getPhaseType().getOrder();
                return (o1>o2 ? 1 : (o1==o2 ? 0 : -1));
            }
        }
}
