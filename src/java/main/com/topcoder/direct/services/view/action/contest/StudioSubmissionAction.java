/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.StudioContestSubmissionDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.form.StudioContestSubmissionForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionDeclaration;
import com.topcoder.management.deliverable.SubmissionExternalContent;
import com.topcoder.management.deliverable.SubmissionExternalContentProperty;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * <p>
 *   Version 1.4 (TC Direct Replatforming Release 3) change notes:
 *   <ul>
 *     <li>The {@link #executeAction()} method was totally updated to work for the new studio contest.</li>
 *   </ul>
 * </p>
 * 
 * <p>
 *   Version 1.5 (TC Direct Contest Dashboard Update Assembly) change notes:
 *   <ul>
 *     <li>Updated {@link #executeAction()} method to set contest dashboard data.</li>
 *   </ul>
 * </p>
 *
 * <p>
 *   Version 1.6 (TC Direct Replatforming Release 5) change notes:
 *   <ul>
 *     <li>Update {@link #executeAction()} method to load the Fonts and Stock Art submission external content.</code>
 *   </ul>
 * </p>
 *
 * <p>
 *   Version 1.7 (Release Assembly - TopCoder Cockpit Submission Viewer Revamp) change notes:
 *   <ul>
 *     <li>Updated {@link #executeAction()} method to add support for full size view and get image file names,
 *      and set the checkpointReviewOpen flag to viewData.</code>
 *   </ul>
 * </p>
 *
 * <p>
 * Version 1.8 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) Change notes:
 *   <ol>
 *     <li>Updated {@link #executeAction()} method to add parameter softwareCompetition when calling
 *     updated method {@link DirectUtils#getContestStats(TCSubject, long, SoftwareCompetition)}.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.9 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
 *   <ol>
 *     <li>Updated {@link #executeAction()} method to support new <code>Studio Final Fix</code> round type.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.0 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * @author isv, flexme, minhu, Veve
 * @since Submission Viewer Release 1 assembly
 * @version 2.0
 */
public class StudioSubmissionAction extends ContestAction {

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
     * The category type id of the studio contest.
     */
    private long contestTypeId;

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

    public long getContestTypeId() {
        return contestTypeId;
    }

    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then directs the application
     * to single view or full view.</p>
     *
     * @return <code>full</code> if request is to be directed to full view or <code>single</code> if
     *         response is to be directed to single view.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            return getFormData().isFullView() ? "full" : "single";
        }
        return result;
    }

    /**
     * <p>Handles the incoming request. Retrieves the details for requested submission for requested contest and binds
     * it to view data along with other necessary details.</p>
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

        setContestTypeId(softwareCompetition.getProjectHeader().getProjectCategory().getId());

        // only works for studio contest
        if (DirectUtils.isStudio(softwareCompetition)) {
            ContestRoundType roundType = getFormData().getRoundType();
            PhaseType reviewPhaseType;
            if (roundType == ContestRoundType.FINAL) {
                reviewPhaseType = PhaseType.REVIEW_PHASE;
            } else if (roundType == ContestRoundType.STUDIO_FINAL_FIX_SUBMISSION) {
                reviewPhaseType = PhaseType.APPROVAL_PHASE;
            } else {
                reviewPhaseType = PhaseType.CHECKPOINT_REVIEW_PHASE;
                viewData.setCheckpointReviewPhaseOpen(DirectUtils.isPhaseOpen(softwareCompetition,
                        PhaseType.CHECKPOINT_REVIEW_PHASE));
            }
            viewData.setPhaseOpen(true);

            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            this.sessionData = new SessionData(request.getSession());

            boolean hasCheckpointRound = DirectUtils.isMultiRound(softwareCompetition);
            getViewData().setHasCheckpointRound(hasCheckpointRound);

            // Set submissions count
            List<Submission> submissions = DirectUtils.getStudioContestSubmissions(projectId, roundType, currentUser, contestServiceFacade);
            int submissionsCount = submissions.size();
            getViewData().setSubmissionsCount(submissionsCount);
            boolean hasCheckout = DirectUtils.getContestCheckout(softwareCompetition, roundType);
            getViewData().setHasCheckout(hasCheckout);

            // Set submission data
            long submissionId = getFormData().getSubmissionId();
            for (Submission sub : submissions) {
                if (sub.getId() == submissionId) {
                    getViewData().setSubmission(sub);
                    getViewData().setSubmissionArtifacts(DirectUtils.getStudioSubmissionArtifacts(submissionId));
                    break;
                }
            }
            List<Map<String, String>> fonts = new ArrayList<Map<String, String>>();
            List<Map<String, String>> stockArts = new ArrayList<Map<String, String>>();
            SubmissionDeclaration submissionDeclaration = getViewData().getSubmission().getSubmissionDeclaration();
            if (submissionDeclaration != null && submissionDeclaration.hasExternalContent()) {
                for (SubmissionExternalContent externalContent : submissionDeclaration.getExternalContents()) {
                    Map<String, String> properties = new HashMap<String, String>();
                    for (SubmissionExternalContentProperty property : externalContent.getExternalContentProperties()) {
                        properties.put(property.getName(), property.getValue());
                    }
                    if ("Fonts".equalsIgnoreCase(externalContent.getExternalContentType().getName())) {
                        fonts.add(properties);
                    } else if ("Stock Art".equalsIgnoreCase(externalContent.getExternalContentType().getName())) {
                        stockArts.add(properties);
                    }
                }
            }
            getViewData().setFonts(fonts);
            getViewData().setStockArts(stockArts);

            // Set IDs of next and previous submissions
            for (int i = 0; i < submissions.size(); i++) {
                Submission submission = submissions.get(i);
                if (submission.getId() == submissionId) {
                    if (i > 0) {
                        getViewData().setPreviousSubmissionId(submissions.get(i - 1).getId());
                    }
                    if ((i + 1) < submissions.size()) {
                        getViewData().setNextSubmissionId(submissions.get(i + 1).getId());
                    }
                    break;
                }
            }
            
            // set the feedback text
            if (roundType != ContestRoundType.STUDIO_FINAL_FIX_SUBMISSION) {
                getViewData().setFeedbackText(contestServiceFacade.getStudioSubmissionFeedback(currentUser, projectId, submissionId, reviewPhaseType));
            } else {
                getViewData().setFeedbackText("");
            }

            // For normal request flow prepare various data to be displayed to user

            // Set contest stats
            //ContestStatsDTO contestStats = DirectUtils.getContestStats(contestServiceFacade, currentUser, contestId);
            ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, projectId, softwareCompetition);

            getViewData().setContestStats(contestStats);

             // set the number of prizes
            int prizeNumber = DirectUtils.getContestPrizeNumber(softwareCompetition, roundType);
            getViewData().setPrizeNumber(prizeNumber);
            

            // Set current project contests
            List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                    currentUser.getUserId(), contestStats.getContest().getProject().getId());

            for (TypedContestBriefDTO contest : contests) {
                if (contest.getId() == projectId) {
                    getViewData().setCurrentContest(contest);
                    break;
                }
            }

            // Set current project context based on selected contest
            getSessionData().setCurrentProjectContext(contestStats.getContest().getProject());
            getSessionData().setCurrentSelectDirectProjectID(contestStats.getContest().getProject().getId());
            
            // set contest permission
            viewData.setHasContestWritePermission(DirectUtils
                    .hasWritePermission(this, currentUser, projectId, false));
            
            DirectUtils.setDashboardData(currentUser, projectId, viewData, getContestServiceFacade(), true);
        }
    }
}
