/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.StudioContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.StudioContestSubmissionsForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.SubmissionData;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for checkout a list of submissions for
 * <code>Studio</code> contest.
 * </p>
 * 
 * @author flexme
 * @since Submission Viewer Release 3 assembly
 * @version 1.0
 */
public class ContestSubmissionsCheckoutAction extends StudioOrSoftwareContestAction {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 3265962628764084549L;

    /**
     * <p>
     * A <code>StudioContestSubmissionsForm</code> providing the parameters of the incoming request.
     * </p>
     */
    private StudioContestSubmissionsForm formData;

    /**
     * <p>
     * A <code>SessionData</code> providing interface to current session.
     * </p>
     */
    private SessionData sessionData;

    /**
     * <p>
     * A <code>StudioContestSubmissionsDTO</code> providing the view data for displaying by <code>Studio Contest
     * Submissions</code> view.
     * </p>
     */
    private StudioContestSubmissionsDTO viewData;

    /**
     * <p>
     * Constructs new <code>ContestSubmissionsActionCheckout</code> instance. This implementation does nothing.
     * </p>
     */
    public ContestSubmissionsCheckoutAction() {
        this.viewData = new StudioContestSubmissionsDTO();
        this.formData = new StudioContestSubmissionsForm(this.viewData);
    }

    /**
     * <p>
     * Gets the form data.
     * </p>
     * 
     * @return an <code>StudioContestSubmissionsForm</code> providing the data for form submitted by user..
     */
    public StudioContestSubmissionsForm getFormData() {
        return this.formData;
    }

    /**
     * <p>
     * Gets the current session associated with the incoming request from client.
     * </p>
     * 
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>
     * Gets the data to be displayed by view mapped to this action.
     * </p>
     * 
     * @return a <code>StudioContestSubmissionsDTO</code> providing the collector for data to be rendered by the view
     *         mapped to this action.
     */
    public StudioContestSubmissionsDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>
     * Handles the incoming request. Retrieves the list of submissions for requested contest and binds it to view data
     * along with other necessary details.
     * </p>
     * 
     * @throws Exception
     *             if an unexpected error occurs.
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
            getViewData().setPrizes(studioCompetition.getPrizes());
            if (getViewData().getHasMilestoneRound()) {
                getViewData().setMilestonePrize(studioCompetition.getContestData().getMilestonePrizeData().getAmount());
            }
            getViewData().setAdditionalPrize(DirectUtils.getAdditionalPrize(studioCompetition));

            // Set submissions data
            ContestRoundType roundType = getFormData().getRoundType();
            List<SubmissionData> submissions = DirectUtils.getStudioContestSubmissions(studioCompetition.getContestData(), roundType,
                    currentUser, contestServiceFacade);
            getViewData().setContestSubmissions(submissions);
            if (roundType == ContestRoundType.FINAL) {
                StringBuffer paidSubmissions = new StringBuffer();
                for (SubmissionData sub : submissions) {
                    if (sub.isPaidFor()) {
                        paidSubmissions.append(sub.getSubmissionId()).append(",");
                    }
                }
                List<SubmissionData> mileSubmissions = DirectUtils.getStudioContestSubmissions(studioCompetition.getContestData(),
                        ContestRoundType.MILESTONE, currentUser, contestServiceFacade);
                int total = 0;
                for (SubmissionData submission : mileSubmissions) {
                    if (submission.isAwardMilestonePrize() != null && submission.isAwardMilestonePrize()) {
                        total++;
                    }
                    if ((submission.isAwardMilestonePrize() != null && submission.isAwardMilestonePrize())
                            || submission.isPaidFor()) {
                        paidSubmissions.append(submission.getSubmissionId()).append(",");
                    }
                }
                getViewData().setMilestoneAwardNumber(total);
                if (paidSubmissions.length() > 0) {
                    paidSubmissions.deleteCharAt(paidSubmissions.length() - 1);
                }
                getViewData().setPaidSubmissions(paidSubmissions.toString());
            }

            // set billing accounts
            viewData.setBillingAccounts(getProjectServiceFacade().getClientProjectsByUser(currentUser));
            // set flag indicating whether the submissions have already been checked out
            viewData.setHasCheckout(DirectUtils.getSubmissionsCheckout(submissions, roundType));

            // For normal request flow prepare various data to be displayed to user

            // Set contest stats
            ContestStatsDTO contestStats = DirectUtils.getContestStats(contestServiceFacade, currentUser, contestId);
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
            List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(currentUser.getUserId(),
                    contestStats.getContest().getProject().getId());
            getSessionData().setCurrentProjectContests(contests);

            // Set current project context based on selected contest
            getSessionData().setCurrentProjectContext(contestStats.getContest().getProject());
        }
    }
}
