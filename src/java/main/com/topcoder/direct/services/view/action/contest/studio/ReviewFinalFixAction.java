/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.studio;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.SoftwareCompetition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for actions related to reviewing the Final Fixes
 * by the clients.</p>
 * 
 * @author isv
 * @version 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One)
 */
public class ReviewFinalFixAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>long</code> providing the ID of a contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>String</code> providing the action to be performed against review (save, accept, reject).</p>
     */
    private String action;

    /**
     * <p>A <code>String</code> providing the additional comment for review.</p>
     */
    private String additionalComment;

    /**
     * <p>A <code>boolean[]</code> providing the flags indicating whether respective Final Fix requirement has been
     * fixed or not.</p>
     */
    private boolean[] itemFixedStatuses;

    /**
     * <p>Constructs new <code>ReviewFinalFixAction</code> instance. This implementation does nothing.</p>
     */
    public ReviewFinalFixAction() {
    }

    /**
     * <p>Sets the ID of a contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>Sets the flags indicating whether respective Final Fix requirement has been fixed or not.</p>
     *
     * @param itemFixedStatuses a <code>boolean[]</code> providing the flags indicating whether respective Final Fix
     * requirement has been fixed or not.
     */
    public void setItemFixedStatuses(boolean[] itemFixedStatuses) {
        this.itemFixedStatuses = itemFixedStatuses;
    }

    /**
     * <p>Sets the additional comment for review.</p>
     *
     * @param additionalComment a <code>String</code> providing the additional comment for review.
     */
    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    /**
     * <p>Sets the action to be performed against review (save, accept, reject).</p>
     *
     * @param action a <code>String</code> providing the action to be performed against review (save, accept, reject).
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * <p>Handles the incoming request.</p>
     *
     * <p>Performs the requested action with the latest Final Review (e.g. saves, accepts or rejects).</p>
     *
     * @throws Exception if any error occurs
     */
    @Override
    protected void executeAction() throws Exception {
        if ("accept".equals(this.action)) {
            for (int i = 0; i < this.itemFixedStatuses.length; i++) {
                boolean itemFixedStatus = this.itemFixedStatuses[i];
                if (!itemFixedStatus) {
                    throw new DirectException("All items must be accepted");
                }
            }
        } else if ("reject".equals(this.action)) {
            boolean allAccepted = true;
            for (int i = 0; i < this.itemFixedStatuses.length; i++) {
                boolean itemFixedStatus = this.itemFixedStatuses[i];
                if (!itemFixedStatus) {
                    allAccepted = false;
                    break;
                }
            }
            if (allAccepted) {
                throw new DirectException("Can not reject final fixes if all items must be accepted");
            }
        }

        // get user and contest details
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        SoftwareCompetition softwareCompetition =
                getContestServiceFacade().getSoftwareContestByProjectId(currentUser, this.contestId);

        // check user permission
        boolean userHasWriteFullPermission = DirectUtils.hasWritePermission(this, currentUser, this.contestId, false);

        if (!userHasWriteFullPermission) {
            throw new DirectException("User does not have a permission to access contest");
        }
        Resource approverResource
                = DirectUtils.getUserResourceByRole(currentUser.getUserId(), softwareCompetition,
                ResourceRole.RESOURCE_ROLE_APPROVER_ID);
        if (approverResource == null) {
            approverResource = DirectUtils.registerUserToContest(this.contestId,
                    ResourceRole.RESOURCE_ROLE_APPROVER_ID, currentUser.getUserId(),
                    getSessionData().getCurrentUserHandle());
        }


        // get the latest final review phase
        Phase[] phases = softwareCompetition.getProjectPhases().getAllPhases();
        Phase finalReviewPhase = phases[phases.length - 1];

        Review finalReviewReview = getProjectServices().getReviewsByPhase(this.contestId, finalReviewPhase.getId())[0];
        List<Comment> comments = finalReviewReview.getItems().get(0).getComments();    // there is only one item
        int itemFixedStatusesIndex = 0;
        Comment additionalFinalReviewComment = null;
        for (int i = 0; i < comments.size(); ++i) {
            Comment comment = comments.get(i);
            if (comment.getCommentType().getId() == CommentType.COMMENT_TYPE_REQUIRED.getId()) {
                comment.setExtraInfo(this.itemFixedStatuses[itemFixedStatusesIndex++] ? "Fixed" : "Not Fixed");
            } else if (comment.getCommentType().getId() == CommentType.COMMENT_TYPE_FINAL_REVIEW.getId()) {
                additionalFinalReviewComment = comment;
            }
        }

        if (this.additionalComment != null && (this.additionalComment.trim().length() > 0)){
            if (additionalFinalReviewComment == null) {
                additionalFinalReviewComment = new Comment();
                additionalFinalReviewComment.setId(-1);
                finalReviewReview.getItems().get(0).addComment(additionalFinalReviewComment);
            }
            additionalFinalReviewComment.setAuthor(approverResource.getId());
            additionalFinalReviewComment.setComment(this.additionalComment);
            additionalFinalReviewComment.setCommentType(CommentType.COMMENT_TYPE_FINAL_REVIEW);
        }

        if (this.action.equals("accept")) {
            finalReviewReview.setCommitted(true);

            Comment comment = createFinalReviewComment(approverResource, true);
            finalReviewReview.addComment(comment);

            finalReviewReview.setInitialScore(100F);
            finalReviewReview.setScore(100F);

        } else if (this.action.equals("reject")) {
            finalReviewReview.setCommitted(true);

            Comment comment = createFinalReviewComment(approverResource, false);
            finalReviewReview.addComment(comment);

        } else if (!this.action.equals("save")) {
            throw new DirectException("Unsupported action: " + this.action);
        }

        getProjectServices().updateReview(finalReviewReview);

        Map success = new HashMap();
        success.put("success", "true");

        setResult(success);        
    }

    /**
     * <p>Creates new comment of {@link CommentType#COMMENT_TYPE_FINAL_REVIEW} type which indicates either on approval
     * or rejection of final review.</p>
     * 
     * @param approverResource a <code>Resource</code> referencing the <code>Approver</code> resource for project.  
     * @param approved <code>true</code> if final review is to be approved; <code>false</code> otherwise.
     * @return a <code>Comment</code> providing the details for newly created comment. 
     */
    private Comment createFinalReviewComment(Resource approverResource, boolean approved) {
        Comment comment = new Comment();
        comment.setId(-1);
        comment.setAuthor(approverResource.getId());
        comment.setComment("");
        comment.setExtraInfo(approved ? "Approved" : "Rejected");
        comment.setCommentType(CommentType.COMMENT_TYPE_FINAL_REVIEW);
        return comment;
    }
}
