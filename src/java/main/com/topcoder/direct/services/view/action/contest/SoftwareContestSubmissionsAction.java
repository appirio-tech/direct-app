/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.SoftwareContestWinnerDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestFinalFixDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionReviewDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.SoftwareCompetition;
import org.apache.log4j.Logger;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing a list of submissions for
 * <code>Software</code> contest.</p>
 * <p/>
 * <p>
 * Version 1.0.1 (Direct Release 6 Assembly 1.0) Change notes:
 * <ol>
 * <li>Updated {@link #executeAction()} method to use appropriate method for calculating contest stats.</li>
 * </ol>
 * </p>
 * <p/>
 * <p>
 * Version 1.0.2 (Direct Manage Copilot Postings Assembly 1.0) Change notes:
 * <ol>
 * <li>Updated {@link #executeAction()} method to user appropriate method for calculating contest stats.</li>
 * </ol>
 * </p>
 * <p/>
 * <p>
 * Version 1.0.3 (TC Direct Contest Dashboard Update Assembly 1.0) Change notes:
 * <ol>
 * <li>Updated {@link #executeAction()} method to set contest dashboard data.</li>
 * </ol>
 * </p>
 * <p>
  * Version 1.1 (Release Assembly - TC Direct Cockpit Release Two) Change notes:
  * <ol>
  * <li>Updated {@link #executeAction()} method to set milestone submissions data.</li>
  * </ol>
  * </p>
 *
 * <p>
 * Version 1.2 (Module Assembly - Adding Contest Approval Feature in Direct Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added logic for checking if user can perform Approval and if approval is already completed by user.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Cockpit Software Milestone Management) Change notes:
 *   <ol>
 *     <li>Updated to support software milestone management.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
 *  <ul>
 *      <li>Updated to show approval phase result and approval phase comment</li>
 *  </ul>
 * </p>
 *
 * <p>
 * Version 1.5 (Release Assembly - TC Direct Cockpit Release Eight)
 * <ul>
 *     <li>Update the action execution logic to allow open final tab when there is no milestone submissions.</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.5
 */
public class SoftwareContestSubmissionsAction extends StudioOrSoftwareContestAction {

    /**
     * <p>A <code>long</code> providing the ID for <code>Approver</code> resource role.</p>
     */
    private static final long RESOURCE_ROLE_APPROVER_ID = 10;

    /**
     * <p>A <code>long</code> providing the ID for <code>Approval</code> scorecard type.</p>
     */
    private static final long SCORECARD_TYPE_APPROVAL_ID = 3;

    /**
     * <p>The approval review score which indicated the approval phase is passed.</p>
     *
     * @since 1.4
     */
    private static final int PASS_APPROVAL_SCORE = 100;

    /**
     * <p>Logger for this class.</p>
     */
    private static final Logger logger = Logger.getLogger(SoftwareContestSubmissionsAction.class);

    /**
     * <p>A <code>ProjectIdForm</code> providing the parameters of the incoming request.</p>
     */
    private ProjectIdForm formData;

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>
     *   Represents the round type of viewing software contest submission, will be set in request.
     * </p>
     * @since 1.1
     */
    private ContestRoundType roundType;

    /**
     * <p>
     *   Flag used to determine whether redirect to milestone round. It will be set to true if milestone review is
     *   open and final review is not started.
     * </p>
     * @since 1.1
     */
    private boolean redirectToMilestone;

    /**
     * <p>Flag used to determine whether current phase is milestone submission.</p>
     * 
     * @since 1.3
     */ 
    private boolean inMilestoneSubmissionPhase;

    /**
     * <p>Flag used to determine whether current phase is milestone review.</p>
     * 
     * @since 1.3
     */ 
    private boolean inMilestoneReviewPhase;

    /**
     * <p>Flag used to determine whether the milestone review is committed.</p>
     * 
     * @since 1.3
     */ 
    private boolean milestoneReviewCommitted;

    /**
     * <p>Flag used to determine whether the user has write permission on the contest.</p>
     * 
     * @since 1.3
     */ 
    private boolean hasWritePermission;

    /**
     * <p>Flag used to determine whether current phase is after milestone review.</p>
     * 
     * @since 1.3
     */ 
    private boolean afterMilestoneReviewPhase;

    /**
     * <p>The number of milestone winners.</p>
     * 
     * @since 1.3
     */ 
    private int milestoneWinnersNumber;

    /**
     * <p>A <code>SoftwareContestSubmissionsDTO</code> providing the view data for displaying by <code>Software Contest
     * Submissions</code> view.</p>
     */
    private SoftwareContestSubmissionsDTO viewData;

    private List<ContestFinalFixDTO> finalFixes;

    public List<ContestFinalFixDTO> getFinalFixes() {
        return finalFixes;
    }

    public void setFinalFixes(List<ContestFinalFixDTO> finalFixes) {
        this.finalFixes = finalFixes;
    }

    /**
     * Gets the contest round type.
     *
     * @return the contest round type.
     * @since 1.1
     */
    public ContestRoundType getRoundType() {
        return roundType;
    }

    /**
     * Sets the contest round type.
     *
     * @param roundType the contest group type.
     * @since 1.1
     */
    public void setRoundType(ContestRoundType roundType) {
        this.roundType = roundType;
    }

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
     * Overrides the {@link #execute()} to check redirectToMilestone, if true, return the result 'milestone' to redirect
     * to milestone submissions page.
     *
     * @return the result code
     * @throws Exception if an unexpected error occurs.
     * @since 1.1
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            if (this.redirectToMilestone) {
                return "milestone";
            }
        }
        return result;
    }

    /**
     * <p>Handles the incoming request. Retrieves the list of submissions for requested contest and binds it to view
     * data along with other necessary details.</p>
     *
     * <p>
     *  Updates in version 1.1:
     *  - adds codes to get milestone submissions data if the round type is milestone round.
     * </p>
     *
     * <p>
     *  Updates in version 1.4 (Release Assembly - TC Direct Cockpit Release Seven)
     *  - Updates the codes to check approval result and set reject reason if the approval is rejected
     * </p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        getFormData().setProjectId(getProjectId());

        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());

        // Get current user
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        SoftwareCompetition softwareCompetition 
            = getContestServiceFacade().getSoftwareContestByProjectId(currentUser, getFormData().getProjectId());

        boolean hasMilestoneRound = DirectUtils.isMultiRound(softwareCompetition);

        // get the round type
        ContestRoundType roundType = getRoundType();

        // if round type is not specified, default to FINAL
        if (roundType == null) {
            roundType = ContestRoundType.FINAL;
            setRoundType(roundType);
        }

        if (hasMilestoneRound) {
            if (roundType == ContestRoundType.FINAL) {
                boolean isMilestoneSubmissionClosed =
                    DirectUtils.isMilestoneSubmissionPhaseClosed(softwareCompetition);
                if (!isMilestoneSubmissionClosed) {
                    // if the milestone is not confirmed, redirect to milestone submission page
                    this.redirectToMilestone = true;
                    return;
                }
            }
        }

        hasWritePermission = DirectUtils.hasWritePermission(this, currentUser, this.getProjectId(), false);

        if (roundType == ContestRoundType.FINAL) {
            // Set submissions, winners, reviewers data
            DataProvider.setSoftwareSubmissionsData(getViewData());
        } else {
            inMilestoneSubmissionPhase = DirectUtils.isPhaseOpen(
                softwareCompetition, PhaseType.MILESTONE_SUBMISSION_PHASE);
            inMilestoneReviewPhase = DirectUtils.isPhaseOpen(
                softwareCompetition, PhaseType.MILESTONE_REVIEW_PHASE);
            if (!inMilestoneReviewPhase) {
                // closed = not open and not scheduled
                afterMilestoneReviewPhase = !DirectUtils.isPhaseScheduled(
                    softwareCompetition, PhaseType.MILESTONE_REVIEW_PHASE);
            }
            DataProvider.setSoftwareMilestoneSubmissionsData(getViewData());
            // sort the winner DTOs
            Collections.sort(getViewData().getMilestoneWinners(), new Comparator<SoftwareContestWinnerDTO>(){
                // sort based on placement
                public int compare(SoftwareContestWinnerDTO o1, SoftwareContestWinnerDTO o2) {
                    return o1.getPlacement() - o2.getPlacement();
                }                
            });
            // sort the milestone submissions 
            Collections.sort(getViewData().getSubmissions(), new Comparator<SoftwareSubmissionDTO>(){
                // sort based on review score
                public int compare(SoftwareSubmissionDTO o1, SoftwareSubmissionDTO o2) {
                    double score1 = (o1.getReviews() == null || o1.getReviews().size() == 0 ||
                                    o1.getReviews().get(0).getFinalScore() == null) ? 0 : 
                                        o1.getReviews().get(0).getFinalScore();
                    double score2 = (o2.getReviews() == null || o2.getReviews().size() == 0 ||
                                    o2.getReviews().get(0).getFinalScore() == null) ? 0 : 
                                        o2.getReviews().get(0).getFinalScore();
                    if (score1 > score2) return -1;
                    else if (score1 < score2)return 1;
                    return 0;
                }                
            });
            
            // get submission feedback and review score
            if (inMilestoneReviewPhase || afterMilestoneReviewPhase) {
                milestoneReviewCommitted = true;
            }
            milestoneWinnersNumber = 0;
            for (SoftwareSubmissionDTO dto : getViewData().getSubmissions()) {
                if (dto.getReviews() != null && dto.getReviews().size() > 0) {
                    SoftwareSubmissionReviewDTO review = dto.getReviews().get(0);
                    dto.setMilestoneFeedback(review.getMilestoneFeedback());
                    dto.setMilestoneReviewScore(review.getFinalScore());
                    if (!review.getCommitted()) {
                        // if there's a review not committed, set the flag to false
                        milestoneReviewCommitted = false;
                    }
                    if (review.getFinalScore() > 10.1)milestoneWinnersNumber++;
                } else {
                    // there's no review yet, set the flag to false
                    milestoneReviewCommitted = false;                    
                }
            }           
            
            // get general feedback
            getViewData().setMilestoneSubmissionsGeneralFeedback(
                getProjectServices().getSoftwareMilestoneSubmissionsGeneralFeedback(getProjectId()));
        }

        // For normal request flow prepare various data to be displayed to user
        // Set contest stats
        ContestStatsDTO contestStats = DirectUtils.getContestStats(getCurrentUser(), getProjectId(),
            softwareCompetition);
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

        // set whether to show spec review
        viewData.setShowSpecReview(getSpecificationReviewService()
                .getSpecificationReview(currentUser, getProjectId()) != null);

        DirectUtils.setDashboardData(currentUser, getProjectId(), viewData,
                getContestServiceFacade(), true);
        
        // Determine if user can perform approval
        boolean approvalPhaseIsOpen = DirectUtils.isPhaseOpen(softwareCompetition, PhaseType.APPROVAL_PHASE);
        boolean userHasWriteFullPermission = DirectUtils.hasWritePermission(this, currentUser, getProjectId(), false);
        Resource approverResource = DirectUtils.getUserResourceByRole(currentUser.getUserId(), softwareCompetition,
                                                                      RESOURCE_ROLE_APPROVER_ID);
        boolean isApprovalCommitted = false;
        boolean isApprovalRejected = false;
        String approvalComment = null;
        List<Review> approvalReviews = new ArrayList<Review>();

        if (approverResource != null) {
            isApprovalCommitted = DirectUtils.hasReview(getProjectServices(), softwareCompetition,
                                                        PhaseType.APPROVAL_PHASE.getId(),
                                                        SCORECARD_TYPE_APPROVAL_ID, approverResource.getId(), approvalReviews);
            // check approval review to see the approval result and comment
            if(approvalReviews.size() > 0) {
                for(Review r : approvalReviews) {
                    if(r != null) {
                        if(r.getScore() != null && r.getScore() < PASS_APPROVAL_SCORE) {
                            // there is rejected approval phase
                            isApprovalRejected = true;
                            // check review comment
                            for(Item item : r.getAllItems()) {
                                if(item.getAllComments() != null) {
                                    for(Comment comment : item.getAllComments()) {
                                        if(comment.getAuthor() == approverResource.getId()) {
                                            approvalComment = comment.getComment();
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        getViewData().setApprovalRejected(isApprovalRejected);
        getViewData().setApprovalComment(approvalComment);

        if (logger.isDebugEnabled()) {
            logger.debug("Context for approval by user " + currentUser.getUserId() + " for project " + getProjectId() 
                         + ": approvalPhaseIsOpen = " + approvalPhaseIsOpen + ", userHasWriteFullPermission = " 
                         + userHasWriteFullPermission + ", approverResource = " + approverResource 
                         + ", isApprovalCommitted = " + isApprovalCommitted);
        }

        getViewData().setShowApproval((approvalPhaseIsOpen || isApprovalCommitted) && userHasWriteFullPermission);
        getViewData().setApprovalCommitted(isApprovalCommitted);

        // add final fixes of the contest if exist
        setFinalFixes(DataProvider.getContestFinalFixes(getProjectId()));


        if (softwareCompetition.getProjectHeader().getProjectCategory().getId() == 29) {
            DirectUtils.setCopilotDashboardSpecificData(getProjectServices(), getProjectServiceFacade(),
                    getMetadataService(), getProjectId(),  softwareCompetition.getProjectHeader().getTcDirectProjectId(),
                    getViewData().getDashboard());
        }
    }

    /**
     * Checks whether all the submission are reviewed.
     *
     * @return true if all submissions are review, false otherwise.
     */
    public boolean isAllSubmissionReviewed() {
        final List<SoftwareSubmissionDTO> submissions = getViewData().getSubmissions();

        for(SoftwareSubmissionDTO s : submissions) {
            if (s.getReviews() == null || s.getReviews().size() == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the flag used to determine whether current phase is milestone submission.
     * 
     * @return the flag used to determine whether current phase is milestone submission
     * @since 1.3
     */
    public boolean isInMilestoneSubmissionPhase() {
        return inMilestoneSubmissionPhase;
    }

    /**
     * Gets the flag used to determine whether current phase is milestone review.
     * 
     * @return the flag used to determine whether current phase is milestone review
     * @since 1.3
     */
    public boolean isInMilestoneReviewPhase() {
        return inMilestoneReviewPhase;
    }

    /**
     * Gets the flag used to determine whether current phase is after milestone review.
     * 
     * @return the flag used to determine whether current phase is after milestone review
     * @since 1.3
     */
    public boolean isAfterMilestoneReviewPhase() {
        return afterMilestoneReviewPhase;
    }

    /**
     * Gets the flag used to determine whether the milestone review is committed.
     * 
     * @return the flag used to determine whether the milestone review is committed
     * @since 1.3
     */
    public boolean isMilestoneReviewCommitted() {
        return milestoneReviewCommitted;
    }

    /**
     * Gets the flag used to determine whether the user has write permission on the contest.
     * 
     * @return the flag used to determine whether the user has write permission on the contest
     * @since 1.3
     */
    public boolean isHasWritePermission() {
        return hasWritePermission;
    }

    /**
     * Gets the number of milestone winners.
     * 
     * @return the number of milestone winners
     * @since 1.3
     */
    public int getMilestoneWinnersNumber() {
        return milestoneWinnersNumber;
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
