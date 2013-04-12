/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
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
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.project.Prize;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for checkout a list of submissions for
 * <code>Studio</code> contest.
 * </p>
 *
 * <p>
 *   Version 1.1 (TC Direct Replatforming Release 3) change notes:
 *   <ul>
 *     <li>The {@link #executeAction()} method was totally updated to work for the new studio contest.</li>
 *   </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) Change notes:
 *   <ol>
 *     <li>Updated {@link #executeAction()} method to add parameter softwareCompetition when calling
 *     updated method {@link DirectUtils#getContestStats(TCSubject, long, SoftwareCompetition)}.</li>
 *   </ol>
 * </p>
 *
 * @author flexme, minhu
 * @since Submission Viewer Release 3 assembly
 * @version 1.2
 */
public class ContestSubmissionsCheckoutAction extends ContestAction {
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
     * The billing account id of the contest used for check out.
     */
    private long contestBillingAccountId;

    /**
     * The flag to show whether the current user has access to the billing account used for check out.
     */
    private boolean canAccessBillingAccount;

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
     * Gets the contest billing account id.
     *
     * @return the contest billing account id
     */
    public long getContestBillingAccountId() {
        return contestBillingAccountId;
    }

    /**
     * Gets whether the current user can access the contest billing account
     *
     * @return the flag to show whether the current user has access to the billing account used for check out.
     */
    public boolean getCanAccessBillingAccount() {
        return this.canAccessBillingAccount;
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
        getFormData().setContestId(getProjectId());
        long projectId = getProjectId();
        if (projectId <= 0) {
            throw new DirectException("projectId less than 0 or not defined.");
        }

        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(currentUser, projectId);

        // only works for studio contest
        if (DirectUtils.isStudio(softwareCompetition)) {
            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            this.sessionData = new SessionData(request.getSession());

            boolean hasCheckpointRound = DirectUtils.isMultiRound(softwareCompetition);
            getViewData().setHasCheckpointRound(hasCheckpointRound);
            getViewData().setPrizes(DirectUtils.getContestPrizes(softwareCompetition));
            getViewData().setAdditionalPrize(DirectUtils.getAdditionalPrize(softwareCompetition));

            if (hasCheckpointRound) {
                Prize checkpointPrize = DirectUtils.getCheckpointPrize(softwareCompetition);
                getViewData().setCheckpointPrize(checkpointPrize.getPrizeAmount());


                if (getFormData().getRoundType() == ContestRoundType.FINAL) {
                    List<Submission> checkpointSubmissions = DirectUtils.getStudioContestSubmissions(projectId, ContestRoundType.CHECKPOINT, currentUser, contestServiceFacade);

                    int count = 0;

                    for (Submission submission : checkpointSubmissions) {
                        if (submission.isExtra() || submission.getFinalScore()!= null && submission.getFinalScore() > 10.0) {
                            count++;
                        }
                    }

                    getViewData().setCheckpointAwardNumber(checkpointPrize.getNumberOfSubmissions() > count ? count : checkpointPrize.getNumberOfSubmissions());
                }
            }

            if (getFormData().getRoundType() == ContestRoundType.CHECKPOINT) {
                getViewData().setCheckpointRoundFeedbackText(softwareCompetition.getProjectHeader().getProjectStudioSpecification().getGeneralFeedback());
            }

            // Set submissions data
            ContestRoundType roundType = getFormData().getRoundType();
            PhaseType reviewPhaseType;
            if (roundType == ContestRoundType.FINAL) {
                reviewPhaseType = PhaseType.REVIEW_PHASE;
            } else {
                reviewPhaseType = PhaseType.CHECKPOINT_REVIEW_PHASE;
            }
            List<Submission> submissions = DirectUtils.getStudioContestSubmissions(projectId, roundType, currentUser, contestServiceFacade);
            if (DirectUtils.isPhaseScheduled(softwareCompetition, reviewPhaseType)) {
                viewData.setPhaseOpen(false);
            } else {
                viewData.setPhaseOpen(true);
            }
            getViewData().setContestSubmissions(submissions);
            getViewData().setSubmissionFeedback(DirectUtils.getStudioSubmissionsFeedback(currentUser, contestServiceFacade, submissions, projectId, reviewPhaseType));

            boolean hasCheckout = DirectUtils.getContestCheckout(softwareCompetition, roundType);
            getViewData().setHasCheckout(hasCheckout);

            this.contestBillingAccountId = DirectUtils.getBillingIdForProject(currentUser, projectId);

            this.canAccessBillingAccount = false;

            List<Project> billingAccounts = DirectUtils.getBillingAccounts(currentUser);
            Project selectedBilling = null;
            for(Project billingAccount : billingAccounts) {
                if (this.contestBillingAccountId == billingAccount.getId()) {
                    this.canAccessBillingAccount = true;
                    selectedBilling = billingAccount;
                    break;
                }
            }

            if (!this.canAccessBillingAccount) {
                // add the billing account so user can check out
                selectedBilling = new Project();
                selectedBilling.setId(this.contestBillingAccountId);
                selectedBilling.setName("HIDDEN BILLING NAME");
            }

            getViewData().setBillingAccount(selectedBilling);

            // For normal request flow prepare various data to be displayed to user

            // Set contest stats
            ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, projectId, softwareCompetition);

            getViewData().setContestStats(contestStats);

            // set the number of prizes
            int prizeNumber = DirectUtils.getContestPrizeNumber(softwareCompetition, roundType);
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
