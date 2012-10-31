/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRegistrantsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.ContestRegistrantsForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.user.Registrant;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Contest Registrants</code>
 * page for requested contest.</p>
 *
 * <p>Version 1.1 (Direct Registrants List assembly) change notes:
 *   <ul>
 *     <li>The class is made a descendant of <code>StudioOrSoftwareContestAction</code>.</li>
 *     <li>Using contest service facade for necessary data retrieval.</li>
 *   </ul>
 * </p>
 *
 * <p>Version 1.2 (TC Direct Contest Dashboard Update Assembly) change notes:
 *   <ul>
 *     <li>Update executeAction method to set contest dashboard data.</li>
 *   </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Cockpit Software Milestone Management) Change notes:
 *   <ol>
 *     <li>Updated {@link #executeAction()} method to add parameter softwareCompetition when calling
 *     updated method {@link DirectUtils#getContestStats(TCSubject, long, SoftwareCompetition)}.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (Cockpit Customer Copilot Posting Process Revamp Copilot Posting Dashboard)
 * <ol>
 *     <li>Add data for getting copilot dashboard data to the {@link #executeAction()}</li>
 * </ol>
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.4
 */
public class ContestRegistrantsAction extends StudioOrSoftwareContestAction {

    /**
     * <p>A <code>ContestIdForm</code> providing the ID of a requested contest.</p>
     */
    private ContestRegistrantsForm formData;

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     *
     * @since 1.1
     */
    private SessionData sessionData;

    /**
     * <p>A <code>ContestRegistrantsDTO</code> providing the view data for displaying by
     * <code>Contest Registrants</code> view. </p>
     */
    private ContestRegistrantsDTO viewData;

    /**
     * <p>Constructs new <code>ContestRegistrantsAction</code> instance. This implementation does nothing.</p>
     */
    public ContestRegistrantsAction() {
        this.viewData = new ContestRegistrantsDTO();
        this.formData = new ContestRegistrantsForm(this.viewData);
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>ContestRegistrantsForm</code> providing the data for form submitted by user..
     */
    public ContestRegistrantsForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>Object</code> providing the collector for data to be rendered by the view mapped to this action.
     */
    public ContestRegistrantsDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project for contest requested for this action.</p>
     *
     * @return <code>SUCCESS</code> if request is to be forwarded to respective view or <code>download</code> if
     *         response is to be written to client directly.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            if (getFormData().isExcel()) {
                return "download";
            }
        }
        return result;
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project for contest requested for this action.</p>
     *
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    public void executeAction() throws Exception {
        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());

        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();

        // Set registrants data
        long contestId = getProjectId();
        SoftwareCompetition competition = contestServiceFacade.getSoftwareContestByProjectId(currentUser, contestId);
        List<Registrant> registrants = contestServiceFacade.getRegistrantsForProject(currentUser, contestId);

        getViewData().setContestId(contestId);
        getViewData().setContestRegistrants(registrants);

        // For normal request flow prepare various data to be displayed to user
        if (!getFormData().isExcel()) {
            // Set contest stats
            ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, contestId, competition);
            getViewData().setContestStats(contestStats);

            // Set projects data
            List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
            UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
            userProjectsDTO.setProjects(projects);
            getViewData().setUserProjects(userProjectsDTO);

            // Set current project contests
            List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                currentUser.getUserId(), contestStats.getContest().getProject().getId());
            this.sessionData.setCurrentProjectContests(contests);

            // Set current project context based on selected contest
            this.sessionData.setCurrentProjectContext(contestStats.getContest().getProject());
            this.sessionData.setCurrentSelectDirectProjectID(contestStats.getContest().getProject().getId());
        }
        
        // set whether to show spec review
        viewData.setShowSpecReview(getSpecificationReviewService()
                .getSpecificationReview(currentUser, contestId) != null);
        
        DirectUtils.setDashboardData(currentUser, contestId, viewData,
                getContestServiceFacade(), !DirectUtils.isStudio(competition));

        if (competition.getProjectHeader().getProjectCategory().getId() == 29) {
            DirectUtils.setCopilotDashboardSpecificData(getProjectServices(), getProjectServiceFacade(),
                    getMetadataService(), contestId,  competition.getProjectHeader().getTcDirectProjectId(),
                    getViewData().getDashboard());;
        }
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     * @since 1.1
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>
     * Gets the mapping to be used for looking up the project copilot types by IDs.
     * </p>
     *
     * @return a <code>Map</code> mapping the project copilot type ids to category names.
     * @throws Exception if an unexpected error occurs.
     */
    public Map<Long, String> getAllProjectCopilotTypes() throws Exception {
        return DataProvider.getAllProjectCopilotTypes();
    }
}
