/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.project.SoftwareCompetition;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for submitting approval scorecards by users.</p>
 * 
 * @author isv
 * @version 1.0 (Module Assembly Adding Contest Approval Feature in Direct Assembly 1.0)
 */
public class SubmitApprovalAction extends BaseDirectStrutsAction {
    
    /**
     * <p>Logger for this class.</p>
     */
    private static final Logger logger = Logger.getLogger(SubmitApprovalAction.class);

    /**
     * <p>Represents the scorecard id phase attribute key.</p>
     */
    private static final String SCORECARD_ID_PHASE_ATTRIBUTE_KEY = "Scorecard ID";

    /**
     * <p>A <code>long</code> providing the ID for <code>Approval</code> scorecard type.</p>
     */
    private static final long SCORECARD_TYPE_APPROVAL_ID = 3;

    /**
     * <p>The constant value to represents the id of resource is unset.</p>
     */
    private static final long UNSET_RESOURCE_ID = -1;

    /**
     * <p>A <code>long</code> providing the ID for <code>Approver</code> resource role.</p>
     */
    private static final long RESOURCE_ROLE_APPROVER_ID = 10;

    /**
     * <p> Constant for key of resource info: handle. </p>
     */
    private static final String RESOURCE_INFO_HANDLE = "Handle";

    /**
     * <p> Constant for key of resource info : payment. </p>
     */
    private static final String RESOURCE_INFO_PAYMENT = "Payment";

    /**
     * <p> Constant for key of resource info: payment status. </p>
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS = "Payment Status";

    /**
     * <p> Constant for key of resource info: user id. </p>
     */
    private static final String RESOURCE_INFO_USER_ID = "External Reference ID";

    /**
     * <p> Constant for key of resource info: registration date. </p>
     */
    private static final String RESOURCE_INFO_REGISTRATION_DATE = "Registration Date";

    /**
     * <p> Constant value for payment status "not paid". </p>
     */
    private static final String NOT_PAID_PAYMENT_STATUS_VALUE = "No";

    /**
     * Date format for resource creation date.
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM.dd.yyyy hh:mm a", Locale.US);

    /**
     * <p>A <code>long</code> providing the ID of a contest.</p>
     */
    private long projectId;

    /**
     * <p>A <code>long</code> providing the ID of a winning submission.</p>
     */
    private long submissionId;

    /**
     * <p>A <code>String</code> providing the reason for rejecting the final fixes.</p>
     */
    private String rejectionReason;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the final fix was approved or not.</p>
     */
    private boolean approved;

    /**
     * <p>Constructs new <code>SubmitApprovalAction</code> instance. This implementation does nothing.</p>
     */
    public SubmitApprovalAction() {
    }

    /**
     * <p>Gets the flag indicating whether the final fix was approved or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the final fix was approved or not.
     */
    public boolean getApproved() {
        return this.approved;
    }

    /**
     * <p>Sets the flag indicating whether the final fix was approved or not.</p>
     *
     * @param approved a <code>boolean</code> providing the flag indicating whether the final fix was approved or not.
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * <p>Gets the reason for rejecting the final fixes.</p>
     *
     * @return a <code>String</code> providing the reason for rejecting the final fixes.
     */
    public String getRejectionReason() {
        return this.rejectionReason;
    }

    /**
     * <p>Sets the reason for rejecting the final fixes.</p>
     *
     * @param rejectionReason a <code>String</code> providing the reason for rejecting the final fixes.
     */
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    /**
     * <p>Gets the ID of a contest.</p>
     *
     * @return a <code>long</code> providing the ID of a contest.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID of a contest.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a contest.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>Gets the ID of a winning submission.</p>
     *
     * @return a <code>long</code> providing the ID of a winning submission.
     */
    public long getSubmissionId() {
        return this.submissionId;
    }

    /**
     * <p>Sets the ID of a winning submission.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a winning submission.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p>Handles the incoming request.</p>
     *
     * @throws ContestServiceException if an error occurs while getting contest details from contest service.
     * @throws PermissionServiceException if current user is not granted a permission for accessing desired contest.
     * @throws ReviewManagementException if error occurs while creating Approval review.
     * @throws DirectException if Approval phase is not open or user is not granted a permission to perform Approval or
     *         if Approval scorecard is already committed by user.
     */
    @Override
    protected void executeAction()
        throws ContestServiceException, PermissionServiceException, DirectException, ReviewManagementException {
        // Get current user and project details
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        SoftwareCompetition softwareCompetition
            = getContestServiceFacade().getSoftwareContestByProjectId(currentUser, getProjectId());

        // Check if user is allowed to submit approval
        boolean approvalPhaseIsOpen = DirectUtils.isPhaseOpen(softwareCompetition, PhaseType.APPROVAL_PHASE);
        boolean userHasWriteFullPermission = DirectUtils.hasWritePermission(this, currentUser, getProjectId(), false);
        Resource approverResource = DirectUtils.getUserResourceByRole(currentUser.getUserId(), softwareCompetition,
                                                                      RESOURCE_ROLE_APPROVER_ID);
        boolean isApprovalCommitted = false;
        if (approverResource != null) {
            isApprovalCommitted = DirectUtils.hasReview(getProjectServices(), getProjectId(),
                                                        SCORECARD_TYPE_APPROVAL_ID, approverResource.getId());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Context for approval by user " + currentUser.getUserId() + " for project " + getProjectId() 
                         + " and submission " + getSubmissionId()
                         + " : approvalPhaseIsOpen = " + approvalPhaseIsOpen + ", userHasWriteFullPermission = "
                         + userHasWriteFullPermission + ", approverResource = " + approverResource
                         + ", isApprovalCommitted = " + isApprovalCommitted);
        }
        if (!approvalPhaseIsOpen) {
            throw new DirectException("Approval phase is not open");
        } else if (!userHasWriteFullPermission) {
            throw new DirectException("User does not have a permission to perform approval for this project");
        } else if (isApprovalCommitted) {
            throw new DirectException("Approval scorecard is already committed by user");
        }

        // If not create Approver resource for user
        if (approverResource == null) {
            approverResource = new Resource();
            approverResource.setId(UNSET_RESOURCE_ID);
            approverResource.setProject(getProjectId());
            approverResource.setResourceRole(new ResourceRole(RESOURCE_ROLE_APPROVER_ID));
            approverResource.setProperty(RESOURCE_INFO_HANDLE, getSessionData().getCurrentUserHandle());
            approverResource.setProperty(RESOURCE_INFO_USER_ID, String.valueOf(currentUser.getUserId()));
            approverResource.setProperty(String.valueOf(RESOURCE_INFO_PAYMENT), "0");
            approverResource.setProperty(String.valueOf(RESOURCE_INFO_PAYMENT_STATUS), NOT_PAID_PAYMENT_STATUS_VALUE);
            approverResource.setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
            approverResource = getProjectServices().updateResource(approverResource, 
                                                                   String.valueOf(currentUser.getUserId()));
        }
        
        // Determine which scorecard is to be used for Approval review
        Long scorecardId = null;
        Set<Phase> phases = softwareCompetition.getProjectPhases().getPhases();
        for (Phase phase : phases) {
            if (phase.getPhaseType().getId() == PhaseType.APPROVAL_PHASE.getId()) {
                scorecardId = Long.parseLong(phase.getAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY).toString());
            }
        }
        if (scorecardId == null) {
            throw new DirectException("There is no Approval scorecard specified for project");
        }
        
        Scorecard scorecard = getProjectServices().getScorecard(scorecardId);

        // Create and submit Approval review scorecard based on scorecard
        List<Item> items = new ArrayList<Item>();
        Group[] groups = scorecard.getAllGroups();
        for (int i = 0; i < groups.length; i++) {
            Group group = groups[i];
            Section[] allSections = group.getAllSections();
            for (int j = 0; j < allSections.length; j++) {
                Section section = allSections[j];
                Question[] questions = section.getAllQuestions();
                for (int k = 0; k < questions.length; k++) {
                    Comment comment = new Comment();
                    comment.setAuthor(approverResource.getId());
                    if (getApproved()) {
                        comment.setComment("Ok");
                    } else {
                        comment.setComment(getRejectionReason());
                    }
                    comment.setCommentType(CommentType.COMMENT_TYPE_COMMENT);

                    Question question = questions[k];
                    Item item = new Item();
                    item.setQuestion(question.getId());
                    item.addComment(comment);
                    if (getApproved()) {
                        item.setAnswer("4/4");
                    } else {
                        item.setAnswer("1/4");
                    }
                    
                    items.add(item);
                }
            }
        }

        Review approvalReview = new Review();
        approvalReview.setAuthor(approverResource.getId());
        approvalReview.setCommitted(true);
        approvalReview.setCreationUser(String.valueOf(currentUser.getUserId()));
        approvalReview.setCreationTimestamp(new Date());
        approvalReview.setModificationUser(String.valueOf(currentUser.getUserId()));
        approvalReview.setModificationTimestamp(new Date());
        if (getApproved()) {
            approvalReview.setInitialScore(100F);
            approvalReview.setScore(100F);
        } else {
            approvalReview.setInitialScore(25F);
            approvalReview.setScore(25F);
        }
        approvalReview.setSubmission(getSubmissionId());
        approvalReview.setScorecard(scorecard.getId());
        approvalReview.setItems(items);

        Comment approvalComment = new Comment();
        approvalComment.setId(UNSET_RESOURCE_ID);
        approvalComment.setAuthor(approverResource.getId());
        approvalComment.setComment("");
        if (getApproved()) {
            approvalComment.setExtraInfo("Approved");
        } else {
            approvalComment.setExtraInfo("Rejected");
        }
        approvalComment.setCommentType(new CommentType(12, "Approval Review Comment"));

        Comment approvalCommentOtherFixes = new Comment();
        approvalCommentOtherFixes.setId(UNSET_RESOURCE_ID);
        approvalCommentOtherFixes.setAuthor(approverResource.getId());
        approvalCommentOtherFixes.setComment("");
        approvalCommentOtherFixes.setExtraInfo("");
        approvalCommentOtherFixes.setCommentType(new CommentType(13, "Approval Review Comment - Other Fixes"));

        approvalReview.setComments(Arrays.asList(approvalComment, approvalCommentOtherFixes));

        getContestServiceFacade().createReview(approvalReview);
        if (logger.isDebugEnabled()) {
            logger.debug("Created Approval review for submission " + getSubmissionId() + " by user "
                         + currentUser.getUserId() + " : " + toString(approvalReview));
        }
        
        // Set success result for JSON processor
        Map success = new HashMap();
        success.put("success", "true");
        
        setResult(success);
    }

    /**
     * <p>Gets the textual presentation for specified review.</p>
     *  
     * @param review a <code>Review</code> providing the details for review. 
     * @return a <code>String</code> providing the textual presentation for specified review.
     */
    private static String toString(Review review) {
        return "Review{" +
               "id=" + review.getId() +
               ", author=" + review.getAuthor() +
               ", submission=" + review.getSubmission() +
               ", scorecard=" + review.getScorecard() +
               ", committed=" + review.isCommitted() +
               ", score=" + review.getScore() +
               ", initialScore=" + review.getInitialScore() +
               ", creationUser='" + review.getCreationUser() + '\'' +
               ", creationTimestamp=" + review.getCreationTimestamp() +
               ", modificationUser='" + review.getModificationUser() + '\'' +
               ", modificationTimestamp=" + review.getModificationTimestamp() +
               ", items=" + review.getItems() +
               ", comments=" + review.getComments() +
               '}';
    }

}
