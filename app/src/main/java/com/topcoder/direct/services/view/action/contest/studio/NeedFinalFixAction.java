/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.studio;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.project.phases.Phase;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.SoftwareCompetition;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A <code>Struts</code> action used for handling the AJAX requests indicating whether the winning submission for
 * <code>Studio</code> contest requires <code>Final Fix</code> or not.</p>
 *
 * @author isv
 * @version 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One)
 */
public class NeedFinalFixAction extends BaseDirectStrutsAction {
    
    /**
     * <p>A <code>long</code> providing the ID of a contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the winning submission for contest requires Final
     * Fix or not.</p>
     */
    private boolean needFinalFix;

    /**
     * <p>A <code>long</code> providing the ID of a winning submission for contest.</p>
     */
    private long submissionId;

    /**
     * <p>Constructs new <code>NeedFinalFixAction</code> instance. This implementation does nothing.</p>
     */
    public NeedFinalFixAction() {
    }

    /**
     * <p>Sets the ID of a winning submission for contest.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a winning submission for contest.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p>Sets the flag indicating whether the winning submission for contest requires Final Fix or not.</p>
     *
     * @param needFinalFix a <code>boolean</code> providing the flag indicating whether the winning submission for
     * contest requires Final Fix or not.
     */
    public void setNeedFinalFix(boolean needFinalFix) {
        this.needFinalFix = needFinalFix;
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
     * <p>Handles the incoming request.</p>
     * 
     * <p>Registers current user as Approver for requested contest and pre-fills the Approval review scorecard. If final
     * fix are not requested then review is committed; otherwise the review is left uncommitted and current user is also
     * registered to contest as Final Reviewer.</p>
     *
     * @throws Exception if any error occurs
     */
    @Override
    protected void executeAction() throws Exception {
        // get user and contest details
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        SoftwareCompetition softwareCompetition =
                getContestServiceFacade().getSoftwareContestByProjectId(currentUser, this.contestId);

       // check user permission
        boolean userHasWriteFullPermission = DirectUtils.hasWritePermission(this, currentUser, this.contestId, false);

        if (!userHasWriteFullPermission) {
            throw new DirectException("User does not have a permission");
        }

        // add current user as the approver to the contest (if necessary)
        Resource approverResource = DirectUtils.getUserResourceByRole(currentUser.getUserId(), softwareCompetition,
                ResourceRole.RESOURCE_ROLE_APPROVER_ID);
        if (approverResource == null) {
            approverResource = DirectUtils.registerUserToContest(this.contestId,
                    ResourceRole.RESOURCE_ROLE_APPROVER_ID, currentUser.getUserId(),
                    getSessionData().getCurrentUserHandle());
        }

        // fill the approval scorecard

        // get scorecard first
        Long scorecardId;
        Phase[] phases = softwareCompetition.getProjectPhases().getAllPhases();
        Phase approvalPhase = phases[phases.length - 1];
        scorecardId = Long.parseLong(approvalPhase.getAttribute("Scorecard ID").toString());

        if (scorecardId == null) {
            throw new DirectException("There is no Approval scorecard specified for project");
        }
        
        Scorecard scorecard = getProjectServices().getScorecard(scorecardId);

        // Create and submit Approval review scorecard based on scorecard
        // This approval scorecard is expected to have only one group with only one section with only one question. 
        Group group = scorecard.getAllGroups()[0];
        Section section = group.getAllSections()[0];
        Question question = section.getAllQuestions()[0];

        Comment comment = new Comment();
        comment.setAuthor(approverResource.getId());
        comment.setComment("Ok");
        comment.setCommentType(CommentType.COMMENT_TYPE_COMMENT);

        Item item = new Item();
        item.setQuestion(question.getId());
        item.addComment(comment);
        if (!needFinalFix) {
            item.setAnswer("4/4");
        } else {
            item.setAnswer("1/4");
        }

        Review approvalReview = new Review();

        approvalReview.setAuthor(approverResource.getId());
        approvalReview.setCreationUser(String.valueOf(currentUser.getUserId()));
        approvalReview.setCreationTimestamp(new Date());
        approvalReview.setModificationUser(String.valueOf(currentUser.getUserId()));
        approvalReview.setModificationTimestamp(new Date());
        if (!this.needFinalFix) {
            approvalReview.setInitialScore(100F);
            approvalReview.setScore(100F);
            approvalReview.setCommitted(true);
        } else {
            approvalReview.setInitialScore(25F);
            approvalReview.setScore(25F);
        }

        approvalReview.setSubmission(this.submissionId);
        approvalReview.setScorecard(scorecard.getId());
        approvalReview.setItems(Arrays.asList(item));

        Comment approvalComment = new Comment();
        approvalComment.setId(-1);
        approvalComment.setAuthor(approverResource.getId());
        approvalComment.setComment("");
        if (!needFinalFix) {
            approvalComment.setExtraInfo("Approved");
        } else {
            approvalComment.setExtraInfo("Rejected");
        }
        approvalComment.setCommentType(new CommentType(12, "Approval Review Comment"));

        approvalReview.setComments(Arrays.asList(approvalComment));
        approvalReview.setProjectPhase(approvalPhase.getId());
        getContestServiceFacade().createReview(approvalReview);

       // Set success result for JSON processor
        Map<String, String> success = new HashMap<String, String>();
        success.put("success", "true");

        setResult(success);
    }
}
