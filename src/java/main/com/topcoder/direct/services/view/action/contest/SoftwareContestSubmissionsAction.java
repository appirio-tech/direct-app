/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing a list of submissions for
 * <code>Software</code> contest.</p>
 *
 * <p>
 * Version 1.0.1 (Direct Release 6 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #executeAction()} method to use appropriate method for calculating contest stats.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.0.2 (Direct Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #executeAction()} method to user appropriate method for calculating contest stats.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.0.3 (TC Direct Contest Dashboard Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #executeAction()} method to set contest dashboard data.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER, TCSASSEMBLER
 * @version 1.0.3 (Direct Software Submission Viewer assembly)
 */
public class SoftwareContestSubmissionsAction extends StudioOrSoftwareContestAction {

    /**
     * <p>A <code>ProjectIdForm</code> providing the parameters of the incoming request.</p>
     */
    private ProjectIdForm formData;

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>A <code>SoftwareContestSubmissionsDTO</code> providing the view data for displaying by <code>Software Contest
     * Submissions</code> view.</p>
     */
    private SoftwareContestSubmissionsDTO viewData;

    /**
     * <p>Constructs new <code>SoftwareContestSubmissionsAction</code> instance. This implementation does nothing.</p>
     */
    public SoftwareContestSubmissionsAction() {
        this.viewData = new SoftwareContestSubmissionsDTO();
        this.formData = new ProjectIdForm(this.viewData);
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>ProjectIdForm</code> providing the data for form submitted by user.
     */
    public ProjectIdForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return a <code>SoftwareContestSubmissionsDTO</code> providing the collector for data to be rendered by the view
     *         mapped to this action.
     */
    public SoftwareContestSubmissionsDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Handles the incoming request. Retrieves the list of submissions for requested contest and binds it to view
     * data along with other necessary details.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        getFormData().setProjectId(getProjectId());
        if (!isStudioCompetition()) {
            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            this.sessionData = new SessionData(request.getSession());

            // Set submissions, winners, reviewers data
            DataProvider.setSoftwareSubmissionsData(getViewData());

            // For normal request flow prepare various data to be displayed to user
            // Set contest stats
            ContestStatsDTO contestStats = DirectUtils.getContestStats(getCurrentUser(), getProjectId(), false);
            getViewData().setContestStats(contestStats);

            // Set projects data
            TCSubject currentUser = getCurrentUser();
            List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
            UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
            userProjectsDTO.setProjects(projects);
            getViewData().setUserProjects(userProjectsDTO);

            // Set current project contests
            List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                    currentUser.getUserId(), contestStats.getContest().getProject().getId());
            getSessionData().setCurrentProjectContests(contests);

            // Set current project context based on selected contest
            getSessionData().setCurrentProjectContext(contestStats.getContest().getProject());
            
            // set whether to show spec review
            viewData.setShowSpecReview(getSpecificationReviewService()
                    .getSpecificationReview(currentUser, getProjectId()) != null);
            
            DirectUtils.setDashboardData(currentUser, getProjectId(), viewData,
                    getContestServiceFacade(), isSoftware());
        }
    }
}
