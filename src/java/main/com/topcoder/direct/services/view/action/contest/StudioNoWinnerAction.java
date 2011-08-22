/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.StudioNoWinnerDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.StudioNoWinnerForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>A base <code>Struts</code> action to be used for handling requests for <code>Can't Choose a Winner</code> flow for
 * <code>Studio</code> contests.</p>  
 *
 * <p>
 *   Version 1.1 (TC Direct Replatforming Release 5) change notes:
 *   <ul>
 *     <li>Changed the super class from <code>StudioOrSoftwareContestAction</code> to <code>SaveContestSubmissionRankAction</code> because
 *     this action need to save submission ranks.</li>
 *     <li>Updated {@link #executeAction()} method to work for the new Studio contest.</code>
 *   </ul>
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1 (Submission Viewer Release 4 assembly)
 */
public class StudioNoWinnerAction extends SaveContestSubmissionRankAction {

    /**
     * <p>A <code>StudioNoWinnerForm</code> providing the parameters of the incoming request.</p>
     */
    private StudioNoWinnerForm formData;

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>A <code>StudioNoWinnerDTO</code> providing the view data for displaying by views from <code>Can't Choose a
     * Winner</code> flow for <code>Studio</code> contest.</p>
     */
    private StudioNoWinnerDTO viewData;


    /**
     * <p>A <code>boolean</code> providing the flag indicating whether incoming request should cause the contest to be
     * abandoned or not.</p>
     */
    private boolean abandonContest;

    /**
     * <p>Constructs new <code>StudioNoWinnerAction</code> instance. This implementation does nothing.</p>
     */
    public StudioNoWinnerAction() {
        this.viewData = new StudioNoWinnerDTO();
        this.formData = new StudioNoWinnerForm(this.viewData);
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>StudioNoWinnerForm</code> providing the data for form submitted by user..
     */
    public StudioNoWinnerForm getFormData() {
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
     * @return a <code>StudioNoWinnerDTO</code> providing the collector for data to be rendered by the view mapped to
     *         this action.
     */
    public StudioNoWinnerDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Gets the flag indicating whether incoming request should cause the contest to be abandoned or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether incoming request should cause the contest to
     *         be abandoned or not.
     */
    public boolean getAbandonContest() {
        return this.abandonContest;
    }

    /**
     * <p>Sets the flag indicating whether incoming request should cause the contest to be abandoned or not.</p>
     *
     * @param abandonContest a <code>boolean</code> providing the flag indicating whether incoming request should cause
     *                       the contest to be abandoned or not.
     */
    public void setAbandonContest(boolean abandonContest) {
        this.abandonContest = abandonContest;
    }

    /**
     * <p>Handles the incoming request. Retrieves the list of submissions for requested contest and binds it to view
     * data along with other necessary details.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        long projectId = getProjectId();
        getFormData().setContestId(projectId);
        if (projectId <= 0) {
            throw new DirectException("projectId less than 0 or not defined.");
        }
        
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(currentUser, projectId);
        
        if (DirectUtils.isStudio(softwareCompetition)) {
            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            this.sessionData = new SessionData(request.getSession());

            // Abandon contest if necessary
            if (getAbandonContest()) {
                // save the submission ranks
                setRanks("");
                setAdditionalPurchases("");
                setRoundType(formData.getRoundType());
                super.executeAction();
            }

            // For normal request flow prepare various data to be displayed to user

            // Set contest stats
            //ContestStatsDTO contestStats = DirectUtils.getContestStats(contestServiceFacade, currentUser, contestId);
            ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, projectId, true);
            getViewData().setContestStats(contestStats);

            // Set projects data
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
        }
    }
}
