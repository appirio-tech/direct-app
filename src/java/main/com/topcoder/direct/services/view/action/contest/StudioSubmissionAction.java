/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.StudioContestSubmissionDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.StudioContestSubmissionForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.SubmissionData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing a single submission for
 * <code>Studio</code> contest.</p>
 *
 * <p>
 * Version 1.1 (Direct Submission Viewer Release 2) change notes:
 * <ul>
 * <li>
 * Update {@link #executeAction()} method to set prize number to the view data.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 *   Version 1.2 (Direct Submission Viewer Release 4) change notes:
 *   <ul>
 *     <li>Updated {@link #executeAction()} method to set currentContest property to the view data.</li>
 *     <li>Updated {@link #executeAction()} method to set hasCheckout property to the view data.</li>
 *   </ul>
 * </p>
 *
 * <p>
 *   Version 1.3 (TC Direct Release Assembly 7) change notes:
 *   <ul>
 *     <li>Updated {@link #executeAction()} method to set hasContestWritePermission flag to the view data.</li>
 *   </ul>
 * </p>
 *
 * @author isv, flexme, TCSASSEMBLER
 * @since Submission Viewer Release 1 assembly
 * @version 1.3
 */
public class StudioSubmissionAction extends StudioOrSoftwareContestAction {

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>A <code>StudioContestSubmissionDTO</code> providing the view data for displaying by <code>Studio Contest
     * Submission</code> view.</p>
     */
    private StudioContestSubmissionDTO viewData;

    /**
     * <p>A <code>StudioContestSubmissionForm</code> providing the parameters of the incoming request.</p>
     */
    private StudioContestSubmissionForm formData;

    /**
     * <p>Constructs new <code>StudioSubmissionAction</code> instance. This implementation does nothing.</p>
     */
    public StudioSubmissionAction() {
        this.viewData = new StudioContestSubmissionDTO();
        this.formData = new StudioContestSubmissionForm(this.viewData);
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
     * @return a <code>StudioContestSubmissionDTO</code> providing the collector for data to be rendered by the view
     *         mapped to this action.
     */
    public StudioContestSubmissionDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>StudioContestSubmissionForm</code> providing the data for form submitted by user..
     */
    public StudioContestSubmissionForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Handles the incoming request. Retrieves the details for requested submission for requested contest and binds
     * it to view data along with other necessary details.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        getFormData().setContestId(getContestId());
        if (isStudioCompetition()) {
            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            this.sessionData = new SessionData(request.getSession());

            ContestServiceFacade contestServiceFacade = getContestServiceFacade();
            TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();

            // Set flag indicating on milestone round presence
            long contestId = getContestId();
            StudioCompetition studioCompetition = contestServiceFacade.getContest(currentUser, contestId);
            getViewData().setHasMilestoneRound(studioCompetition.getContestData().getMultiRound());

            // Set submissions count
            ContestRoundType roundType = getFormData().getRoundType();
            List<SubmissionData> submissions = DirectUtils.getStudioContestSubmissions(studioCompetition.getContestData(), roundType,
                                                                                       currentUser,
                                                                                       contestServiceFacade);
            int submissionsCount = submissions.size();
            getViewData().setSubmissionsCount(submissionsCount);
            getViewData().setHasCheckout(DirectUtils.getSubmissionsCheckout(submissions, roundType));

            // Set submission data
            long submissionId = getFormData().getSubmissionId();
            SubmissionData submission = contestServiceFacade.retrieveSubmission(currentUser, submissionId);
            getViewData().setSubmission(submission);

            // Set IDs of next and previous submissions
            for (int i = 0; i < submissions.size(); i++) {
                SubmissionData submissionData = submissions.get(i);
                if (submissionData.getSubmissionId() == submission.getSubmissionId()) {
                    if (i > 0) {
                        getViewData().setPreviousSubmissionId(submissions.get(i - 1).getSubmissionId());
                    }
                    if ((i + 1) < submissions.size()) {
                        getViewData().setNextSubmissionId(submissions.get(i + 1).getSubmissionId());
                    }
                    break;
                }
            }

            // For normal request flow prepare various data to be displayed to user

            // Set contest stats
            //ContestStatsDTO contestStats = DirectUtils.getContestStats(contestServiceFacade, currentUser, contestId);
            ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, contestId, true);

            getViewData().setContestStats(contestStats);

             // set the number of prizes
            int prizeNumber = DirectUtils.getContestPrizeNumber(studioCompetition, roundType);
            getViewData().setPrizeNumber(prizeNumber);
            
            // Set projects data
            List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
            UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
            userProjectsDTO.setProjects(projects);
            getViewData().setUserProjects(userProjectsDTO);

            // Set current project contests
            List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                    currentUser.getUserId(), contestStats.getContest().getProject().getId());
            getSessionData().setCurrentProjectContests(contests);
            for (TypedContestBriefDTO contest : contests) {
                if (contest.getId() == contestId) {
                    getViewData().setCurrentContest(contest);
                    break;
                }
            }

            // Set current project context based on selected contest
            getSessionData().setCurrentProjectContext(contestStats.getContest().getProject());
            
            // set contest permission
            viewData.setHasContestWritePermission(DirectUtils
                    .hasWritePermission(this, currentUser, contestId, true));
        }
    }
}
