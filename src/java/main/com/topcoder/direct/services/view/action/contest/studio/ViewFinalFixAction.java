/*
 * Copyright (C) 2013 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.studio;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.studio.FinalFixStatus;
import com.topcoder.direct.services.view.dto.contest.studio.StudioFinalFix;
import com.topcoder.direct.services.view.dto.contest.studio.StudioFinalFixDTO;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A <code>Struts</code> action used for handling the requests for viewing the details on Final Fixes for a single
 * <code>Studio</code> contest.</p>
 *
 * <p>
 * Version 1.1 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 * 
 * @author isv, Veve
 * @version 1.1
 */
public class ViewFinalFixAction extends StudioOrSoftwareContestAction {

    /**
     * <p>A <code>StudioFinalFixDTO</code> providing the data to be rendered on the page.</p>
     */
    private StudioFinalFixDTO viewData;

    /**
     * <p>Gets the data to be rendered on the page.</p>
     *
     * @return a <code>StudioFinalFixDTO</code> providing the data to be rendered on the page.
     */
    public StudioFinalFixDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Constructs new <code>ViewFinalFixAction</code> instance. This implementation does nothing.</p>
     */
    public ViewFinalFixAction() {
        this.viewData = new StudioFinalFixDTO();
    }

    /**
     * <p>Handles the incoming request.</p>
     *
     * <p>Analyzes the current status of the Final Fixes for requested contest and retrieves appropriate data from 
     * database to be rendered by subsequent view.</p>
     *
     * @throws Exception if some error occurs during method execution
     */
    @Override
    public void executeAction() throws Exception {
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();

        // get contest data and phases
        long contestId = getProjectId();
        SoftwareCompetition softwareCompetition 
            = contestServiceFacade.getSoftwareContestByProjectId(currentUser, contestId);

        // set dashboard data etc. for the data above the final-fix tab
        DirectUtils.setDashboardData(currentUser, getProjectId(), viewData,
                getContestServiceFacade(), !DirectUtils.isStudio(softwareCompetition));
        ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, getProjectId(), softwareCompetition);
        this.viewData.setContestStats(contestStats);

        this.viewData.setContestId(getProjectId());

        // Prepare the data for final fixes
        Phase[] phases = softwareCompetition.getProjectPhases().getAllPhases();
        Phase lastPhase = phases[phases.length - 1];

        // get final fix status
        boolean isApproval = lastPhase.getPhaseType().getId() == PhaseType.APPROVAL_PHASE.getId();
        boolean isFinalReview = lastPhase.getPhaseType().getId() == PhaseType.FINAL_REVIEW_PHASE.getId();
        
        // Get the winning submission
        Submission winningSubmission = null;
        List<Submission> submissions = DirectUtils.getStudioContestSubmissions(contestId,
                ContestRoundType.FINAL, currentUser, getContestServiceFacade());
        for (Submission submission : submissions) {
            if (submission.getPlacement() != null && submission.getPlacement() == 1) {
                winningSubmission = submission;
            }
        }

        if (isApproval) {
            boolean isApprovalRejected = false;
            Review[] reviewsByPhase =
                    getProjectServices().getReviewsByPhase(contestId, lastPhase.getId());
            if (reviewsByPhase.length == 0) {
                this.viewData.setDataUnavailable(true);
                return;
            }
            Review approvalReview = reviewsByPhase[0];
            Comment approvalComment = DirectUtils
                    .getReviewCommentByTypeId(CommentType.COMMENT_TYPE_APPROVAL_REVIEW.getId(), approvalReview);
            if (approvalComment != null) {
                isApprovalRejected = "Rejected".equalsIgnoreCase(String.valueOf(approvalComment.getExtraInfo()));
            }
            if (isApprovalRejected) {
                if (approvalReview.isCommitted()) {
                    this.viewData.setFinalFixStatus(FinalFixStatus.IN_PROGRESS);
                } else {
                    this.viewData.setFinalFixStatus(FinalFixStatus.NOT_STARTED);
                    
                    // Bind the Final Fix details based on approval review to view
                    StudioFinalFix finalFix = new StudioFinalFix();
                    Item[] approvalReviewItems = approvalReview.getAllItems();
                    List<com.topcoder.direct.services.view.dto.contest.studio.Comment> finalFixComments 
                            = new ArrayList<com.topcoder.direct.services.view.dto.contest.studio.Comment>();
                    for (Item item : approvalReviewItems) {
                        Comment[] itemComments = item.getAllComments();
                        for (Comment itemComment : itemComments) {
                            com.topcoder.direct.services.view.dto.contest.studio.Comment com
                                    = new com.topcoder.direct.services.view.dto.contest.studio.Comment();
                            com.setComment(itemComment.getComment());
                            finalFixComments.add(com);
                        }
                    }
                    finalFix.setComments(finalFixComments);
                    finalFix.setSubmission(winningSubmission);
                    this.viewData.setFinalFixes(Arrays.asList(finalFix));
                    
                    // Get the details for winning submitter
                    if (winningSubmission != null) {
                        Resource[] resources = softwareCompetition.getResources();
                        for (int i = 0; i < resources.length; i++) {
                            Resource resource = resources[i];
                            if (resource.getId() == winningSubmission.getUpload().getOwner()) {
                                this.viewData.setWinnerResource(resource);
                            }
                        }
                    }
                }
            } else {
                throw new DirectException("The submission is already approved");
            }
        } else if (isFinalReview) {
            Phase precedingPhase = phases[phases.length - 2];
            boolean isFinalFixPreceding = precedingPhase.getPhaseType().getId() == PhaseType.FINAL_FIX_PHASE.getId();
            boolean isPrecedingPhaseOpen = (precedingPhase.getPhaseStatus().getId() == PhaseStatus.OPEN.getId());
            boolean isPrecedingPhaseClosed = (precedingPhase.getPhaseStatus().getId() == PhaseStatus.CLOSED.getId());
            
            if (isFinalFixPreceding) {
                if (isPrecedingPhaseOpen) {
                    this.viewData.setFinalFixStatus(FinalFixStatus.IN_PROGRESS);
                } else if (isPrecedingPhaseClosed) {
                    Review[] reviewsByPhase =
                            getProjectServices().getReviewsByPhase(contestId, lastPhase.getId());
                    if (reviewsByPhase.length == 0) {
                        this.viewData.setDataUnavailable(true);
                        return;
                    }
                    Review finalReview = reviewsByPhase[0];
                    
                    Comment finalReviewComment = DirectUtils.getReviewCommentByTypeId(
                            CommentType.COMMENT_TYPE_FINAL_REVIEW.getId(), finalReview);
                    if (finalReviewComment == null) {
                        this.viewData.setFinalFixStatus(FinalFixStatus.REVIEW);
                    } else if ("Rejected".equalsIgnoreCase(String.valueOf(finalReviewComment.getExtraInfo()))) {
                        if (finalReview.isCommitted()) {
                            this.viewData.setFinalFixStatus(FinalFixStatus.IN_PROGRESS);
                        } else {
                            this.viewData.setFinalFixStatus(FinalFixStatus.REVIEW);
                        }
                    } else if ("Approved".equalsIgnoreCase(String.valueOf(finalReviewComment.getExtraInfo()))) {
                        this.viewData.setFinalFixStatus(FinalFixStatus.COMPLETED);
                    }
                }
            }

            // Bind data for all existing final fixes to view 
            if (this.viewData.getFinalFixStatus() != FinalFixStatus.NOT_STARTED) {
                // Get the Final Fix submissions and convert them into map per project phase for faster lookup
                List<Submission> finalFixSubmissions = DirectUtils.getStudioContestSubmissions(contestId,
                        ContestRoundType.STUDIO_FINAL_FIX_SUBMISSION, currentUser, getContestServiceFacade());
                Map<Long, Submission> finalFixSubmissionsMap = new HashMap<Long, Submission>();
                for (Submission finalFixSubmission : finalFixSubmissions) {
                    finalFixSubmissionsMap.put(finalFixSubmission.getUpload().getProjectPhase(), finalFixSubmission);
                }

                List<StudioFinalFix> finalFixes = new ArrayList<StudioFinalFix>();
                for (int i = 0; i < phases.length; i++) {
                    Phase phase = phases[i];
                    if (phase.getPhaseType().getId() == PhaseType.FINAL_FIX_PHASE.getId()) {
                        Review[] reviewsByPhase =
                                getProjectServices().getReviewsByPhase(contestId, phases[i + 1].getId());
                        if (reviewsByPhase.length == 0) {
                            this.viewData.setDataUnavailable(true);
                            return;
                        }
                        Review finalReview = reviewsByPhase[0];
                        StudioFinalFix finalFix = new StudioFinalFix();
                        Item[] finalReviewItems = finalReview.getAllItems();
                        List<com.topcoder.direct.services.view.dto.contest.studio.Comment> finalFixComments
                                = new ArrayList<com.topcoder.direct.services.view.dto.contest.studio.Comment>();
                        Comment finalReviewAdditionalComment = null;
                        for (Item item : finalReviewItems) {
                            Comment[] itemComments = item.getAllComments();
                            for (Comment itemComment : itemComments) {
                                if (itemComment.getCommentType().getId() == CommentType.COMMENT_TYPE_REQUIRED.getId()) {
                                    com.topcoder.direct.services.view.dto.contest.studio.Comment com
                                            = new com.topcoder.direct.services.view.dto.contest.studio.Comment();
                                    com.setComment(itemComment.getComment());
                                    if (itemComment.getExtraInfo() != null) {
                                        com.setFixed("Fixed".equalsIgnoreCase(String.valueOf(itemComment.getExtraInfo())));
                                    }
                                    finalFixComments.add(com);
                                } else if (itemComment.getCommentType().getId() ==
                                        CommentType.COMMENT_TYPE_FINAL_REVIEW.getId()) {
                                    finalReviewAdditionalComment = itemComment;
                                }
                            }
                        }
                        finalFix.setComments(finalFixComments);
                        if (finalReviewAdditionalComment != null) {
                            finalFix.setAdditionalComment(finalReviewAdditionalComment.getComment());
                        }
                        finalFix.setSubmission(finalFixSubmissionsMap.get(phase.getId()));
                        finalFix.setCommitted(finalReview.isCommitted());
                        finalFixes.add(finalFix);
                    }
                }
                this.viewData.setFinalFixes(finalFixes);
            }
        }
    }
}
