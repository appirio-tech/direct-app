/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.SoftwareContestWinnerDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestFinalFixDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionReviewDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotSkillDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotStatDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotSubmissionStatDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;
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
  * <li>Updated {@link #executeAction()} method to set checkpoint submissions data.</li>
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
 * Version 1.3 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) Change notes:
 *   <ol>
 *     <li>Updated to support software checkpoint management.</li>
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
 *     <li>Update the action execution logic to allow open final tab when there is no checkpoint submissions.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 (Module Assembly - Cockpit Copilot Posting Skills Update and Submission Revamp)
 * <ul>
 *     <li>
 *         Adds properties {@link #copilotUserIds}, {@link #viewType}, {@link #inReviewPhase},
 *         and {@link #copilotSubmissions}. Their getter/setter are added.
 *     </li>
 *     <li>
 *         Update {@link #executeAction()} to add copilot posting submissions page data.
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7 (TopCoder Cockpit Copilot Posting Submission Game Plan Preview and Stats)
 * <ul>
 *     <li>Updated {@link #executeAction()} to set billing account id which the contest uses into the view data</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.8 (Release Assembly - TopCoder Cockpit Project Planner and game plan preview Update)
 * <ul>
 *     <li>Added property {@link #fixedBugRaceFee} and its getter and setter for copilot posting submissions</li>
 *     <li>Added property {@link #percentageBugRaceFee} and its getter and setter for copilot posting submissions</li>
 *     <li>Updated {@link #executeAction()} to set newly added two properties of bug race fee for copilot posting contest</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.8
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

    private static final Date NEW_COPILOT_POSTING_REVIEW_START_DATE = new Date(113, 11, 5);

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
     *   Flag used to determine whether redirect to checkpoint round. It will be set to true if checkpoint review is
     *   open and final review is not started.
     * </p>
     * @since 1.1
     */
    private boolean redirectToCheckpoint;

    /**
     * <p>Flag used to determine whether current phase is checkpoint submission.</p>
     * 
     * @since 1.3
     */ 
    private boolean inCheckpointSubmissionPhase;

    /**
     * <p>Flag used to determine whether current phase is checkpoint review.</p>
     * 
     * @since 1.3
     */ 
    private boolean inCheckpointReviewPhase;


    /**
     * <p>Flag used to determine whether current open phase is review phase</p>
     *
     * @since 1.6
     */
    private boolean inReviewPhase;

    /**
     * <p>Flag used to determine whether the checkpoint review is committed.</p>
     * 
     * @since 1.3
     */ 
    private boolean checkpointReviewCommitted;

    /**
     * <p>Flag used to determine whether the user has write permission on the contest.</p>
     * 
     * @since 1.3
     */ 
    private boolean hasWritePermission;

    /**
     * <p>Flag used to determine whether current phase is after checkpoint review.</p>
     * 
     * @since 1.3
     */ 
    private boolean afterCheckpointReviewPhase;

    /**
     * <p>The number of checkpoint winners.</p>
     * 
     * @since 1.3
     */ 
    private int checkpointWinnersNumber;

    /**
     * <p>A <code>SoftwareContestSubmissionsDTO</code> providing the view data for displaying by <code>Software Contest
     * Submissions</code> view.</p>
     */
    private SoftwareContestSubmissionsDTO viewData;

    /**
     * The view type for the copilot posting submissions page. There are three view types:
     * 1) Grid view - default
     * 2) List view
     * 3) Comparison
     *
     * @since 1.6
     */
    private String viewType;

    private List<ContestFinalFixDTO> finalFixes;

    /**
     * The list of copilot posting submissions. It's only used when the contest is of type copilot posting.
     *
     * @since 1.6
     */
    private List<CopilotSubmissionStatDTO> copilotSubmissions;

    /**
     * The list of copilot user ids to compare. It's only used when the contest is of type copilot posting.
     * And the view type is 'comparison'.
     *
     * @since 1.6
     */
    private List<Long> copilotUserIds;


    private List<CopilotSkillDTO> copilotSkills;


    /**
     * The fixed bug race fee.
     *
     * @since 1.8
     */
    private Double fixedBugRaceFee;

    /**
     * The fixed bug race fee.
     *
     * @since 1.8
     */
    private Double percentageBugRaceFee;

    /**
     * Whether enable copilot posting submission preview for this contest.
     */
    private boolean enableCopilotPostingSubmissionPreview;

    public List<ContestFinalFixDTO> getFinalFixes() {
        return finalFixes;
    }

    public void setFinalFixes(List<ContestFinalFixDTO> finalFixes) {
        this.finalFixes = finalFixes;
    }

    /**
     * Gets the fixed bug race fee.
     *
     * @return the fixed bug race fee.
     * @since 1.8
     */
    public Double getFixedBugRaceFee() {
        return fixedBugRaceFee;
    }

    /**
     * Sets the fixed bug race fee.
     *
     * @param fixedBugRaceFee the fixed bug race fee.
     * @since 1.8
     */
    public void setFixedBugRaceFee(Double fixedBugRaceFee) {
        this.fixedBugRaceFee = fixedBugRaceFee;
    }

    /**
     * Gets the percentage bug race fee.
     *
     * @return the percentage bug race fee.
     * @since 1.8
     */
    public Double getPercentageBugRaceFee() {
        return percentageBugRaceFee;
    }

    /**
     * Sets the percentage bug race fee.
     *
     * @param percentageBugRaceFee the percentage bug race fee.
     * @since 1.8
     */
    public void setPercentageBugRaceFee(Double percentageBugRaceFee) {
        this.percentageBugRaceFee = percentageBugRaceFee;
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

    public List<CopilotSkillDTO> getCopilotSkills() {
        return copilotSkills;
    }

    public boolean isEnableCopilotPostingSubmissionPreview() {
        return enableCopilotPostingSubmissionPreview;
    }

    /**
     * Overrides the {@link #execute()} to check redirectToCheckpoint, if true, return the result 'checkpoint' to redirect
     * to checkpoint submissions page.
     *
     * @return the result code
     * @throws Exception if an unexpected error occurs.
     * @since 1.1
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            if (this.redirectToCheckpoint) {
                return "checkpoint";
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
     *  - adds codes to get checkpoint submissions data if the round type is checkpoint round.
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

        boolean hasCheckpointRound = DirectUtils.isMultiRound(softwareCompetition);

        // get the round type
        ContestRoundType roundType = getRoundType();

        // if round type is not specified, default to FINAL
        if (roundType == null) {
            roundType = ContestRoundType.FINAL;
            setRoundType(roundType);
        }

        if (hasCheckpointRound) {
            if (roundType == ContestRoundType.FINAL) {
                boolean isCheckpointSubmissionClosed =
                    DirectUtils.isCheckpointSubmissionPhaseClosed(softwareCompetition);
                if (!isCheckpointSubmissionClosed) {
                    // if the checkpoint is not confirmed, redirect to checkpoint submission page
                    this.redirectToCheckpoint = true;
                    return;
                }
            }
        }

        hasWritePermission = DirectUtils.hasWritePermission(this, currentUser, this.getProjectId(), false);

        if (roundType == ContestRoundType.FINAL) {
            // Set submissions, winners, reviewers data
            DataProvider.setSoftwareSubmissionsData(getViewData());

            inReviewPhase = DirectUtils.isPhaseOpen(softwareCompetition, PhaseType.REVIEW_PHASE);
        } else {
            inCheckpointSubmissionPhase = DirectUtils.isPhaseOpen(
                softwareCompetition, PhaseType.CHECKPOINT_SUBMISSION_PHASE);
            inCheckpointReviewPhase = DirectUtils.isPhaseOpen(
                softwareCompetition, PhaseType.CHECKPOINT_REVIEW_PHASE);
            if (!inCheckpointReviewPhase) {
                // closed = not open and not scheduled
                afterCheckpointReviewPhase = !DirectUtils.isPhaseScheduled(
                    softwareCompetition, PhaseType.CHECKPOINT_REVIEW_PHASE);
            }
            DataProvider.setSoftwareCheckpointSubmissionsData(getViewData());
            // sort the winner DTOs
            Collections.sort(getViewData().getCheckpointWinners(), new Comparator<SoftwareContestWinnerDTO>(){
                // sort based on placement
                public int compare(SoftwareContestWinnerDTO o1, SoftwareContestWinnerDTO o2) {
                    return o1.getPlacement() - o2.getPlacement();
                }                
            });
            // sort the checkpoint submissions 
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
            if (inCheckpointReviewPhase || afterCheckpointReviewPhase) {
                checkpointReviewCommitted = true;
            }
            checkpointWinnersNumber = 0;
            for (SoftwareSubmissionDTO dto : getViewData().getSubmissions()) {
                if (dto.getReviews() != null && dto.getReviews().size() > 0) {
                    SoftwareSubmissionReviewDTO review = dto.getReviews().get(0);
                    dto.setCheckpointFeedback(review.getCheckpointFeedback());
                    dto.setCheckpointReviewScore(review.getFinalScore());
                    if (!review.getCommitted()) {
                        // if there's a review not committed, set the flag to false
                        checkpointReviewCommitted = false;
                    }
                    if (review.getFinalScore() > 10.1)checkpointWinnersNumber++;
                } else {
                    // there's no review yet, set the flag to false
                    checkpointReviewCommitted = false;                    
                }
            }           
            
            // get general feedback
            getViewData().setCheckpointSubmissionsGeneralFeedback(
                getProjectServices().getSoftwareCheckpointSubmissionsGeneralFeedback(getProjectId()));
        }

        // For normal request flow prepare various data to be displayed to user
        // Set contest stats
        ContestStatsDTO contestStats = DirectUtils.getContestStats(getCurrentUser(), getProjectId(),
            softwareCompetition);
        getViewData().setContestStats(contestStats);

        // set billing account
        getViewData().getContestStats().getContest()
                .setBillingAccountId(Long.parseLong(softwareCompetition.getProjectHeader().getProperty(
                        ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY)));

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


        if (softwareCompetition.getProjectHeader().getProjectCategory().getId() == ContestType.COPILOT_POSTING.getId()) {
            // special handling for copilot posting

            // 1) set data for copilot posting dashboard
            DirectUtils.setCopilotDashboardSpecificData(getProjectServices(), getProjectServiceFacade(),
                    getMetadataService(), getProjectId(),  softwareCompetition.getProjectHeader().getTcDirectProjectId(),
                    getViewData().getDashboard());

            // 2) set stats for the copilot posting submissions
            List<CopilotStatDTO> copilotSubmissions = new ArrayList<CopilotStatDTO>();
            for(SoftwareSubmissionDTO sub : getViewData().getSubmissions()) {

                if (viewType != null && viewType.equalsIgnoreCase("comparison")
                        && !copilotUserIds.contains(sub.getSubmitter().getId())) {
                    continue;
                }

                CopilotSubmissionStatDTO copilotSub = new CopilotSubmissionStatDTO();
                copilotSub.setUserId(sub.getSubmitter().getId());
                copilotSub.setHandle(sub.getSubmitter().getHandle());
                copilotSub.setSubmissionId(sub.getSubmissionId());
                copilotSub.setSubmitTime(sub.getSubmissionDate());
                copilotSubmissions.add(copilotSub);
            }

            DataProvider.setCopilotFullStatistics(copilotSubmissions, getViewData().getDashboard().getDirectProjectTypeId());

            setCopilotSubmissions((List<CopilotSubmissionStatDTO>)(List<?>) copilotSubmissions);

            // set copilot skills
            copilotSkills = DataProvider.getCopilotSkillRules();

            if(getViewType() == null) {
                // default to list view
                setViewType("list");
            }

            // set project bug race fee configuration
            ProjectData directProjectData = getProjectServiceFacade().getProject(currentUser,
                    softwareCompetition.getProjectHeader().getTcDirectProjectId());
            setFixedBugRaceFee(directProjectData.getFixedBugContestFee());
            setPercentageBugRaceFee(directProjectData.getPercentageBugContestFee());

            enableCopilotPostingSubmissionPreview = true;

            // check if to enable copilot posting submission preview
            for(CopilotSubmissionStatDTO cs : getCopilotSubmissions()) {
                if (cs.getSubmitTime().before(NEW_COPILOT_POSTING_REVIEW_START_DATE)) {
                    enableCopilotPostingSubmissionPreview = false;
                    break;
                }
            }
        }
    }

    /**
     * Gets the copilot submissions.
     *
     * @return the copilot submissions
     * @since 1.6
     */
    public List<CopilotSubmissionStatDTO> getCopilotSubmissions() {
        return copilotSubmissions;
    }

    /**
     * Sets the copilot submissions.
     *
     * @param copilotSubmissions the copilot submissions
     * @since 1.6
     */
    public void setCopilotSubmissions(List<CopilotSubmissionStatDTO> copilotSubmissions) {
        this.copilotSubmissions = copilotSubmissions;
    }

    /**
     * Gets the view type.
     *
     * @return the view type.
     * @since 1.6
     */
    public String getViewType() {
        return viewType;
    }

    /**
     * Sets the view type.
     *
     * @param viewType the view type.
     * @since 1.6
     */
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    /**
     * Gets the copilot user ids.
     *
     * @return the copilot user ids.
     * @since 1.6
     */
    public List<Long> getCopilotUserIds() {
        return copilotUserIds;
    }

    /**
     * Sets the copilot user ids.
     *
     * @param copilotUserIds the copilot user ids.
     * @since 1.6
     */
    public void setCopilotUserIds(List<Long> copilotUserIds) {
        this.copilotUserIds = copilotUserIds;
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
     * Gets the flag used to determine whether current phase is checkpoint submission.
     * 
     * @return the flag used to determine whether current phase is checkpoint submission
     * @since 1.3
     */
    public boolean isInCheckpointSubmissionPhase() {
        return inCheckpointSubmissionPhase;
    }

    /**
     * Gets inReviewPhase flag.
     *
     * @return the inReviewPhase flag
     * @since 1.6
     */
    public boolean isInReviewPhase() {
        return inReviewPhase;
    }

    /**
     * Gets the flag used to determine whether current phase is checkpoint review.
     * 
     * @return the flag used to determine whether current phase is checkpoint review
     * @since 1.3
     */
    public boolean isInCheckpointReviewPhase() {
        return inCheckpointReviewPhase;
    }

    /**
     * Gets the flag used to determine whether current phase is after checkpoint review.
     * 
     * @return the flag used to determine whether current phase is after checkpoint review
     * @since 1.3
     */
    public boolean isAfterCheckpointReviewPhase() {
        return afterCheckpointReviewPhase;
    }

    /**
     * Gets the flag used to determine whether the checkpoint review is committed.
     * 
     * @return the flag used to determine whether the checkpoint review is committed
     * @since 1.3
     */
    public boolean isCheckpointReviewCommitted() {
        return checkpointReviewCommitted;
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
     * Gets the number of checkpoint winners.
     * 
     * @return the number of checkpoint winners
     * @since 1.3
     */
    public int getCheckpointWinnersNumber() {
        return checkpointWinnersNumber;
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
