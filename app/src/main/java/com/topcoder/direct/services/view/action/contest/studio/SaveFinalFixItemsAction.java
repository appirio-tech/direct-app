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
 * <p>A <code>Struts</code> action to be used for handling requests for saving the requirements for 
 * <code>Final Fix</code> for single <code>Studio</code> contest.</p>
 * 
 * @author isv
 * @version 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One)
 */
public class SaveFinalFixItemsAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>String</code> providing the format for the date of resource's registration to project.</p>
     */
    private static final String REGISTRATION_DATE_FORMAT = "MM.dd.yyyy hh:mm a";

    /**
     * <p>A <code>long</code> providing the ID of a contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the Final Fix requirements are committed or
     * not.</p>
     */
    private boolean committed;

    /**
     * <p>A <code>String[]</code> providing the requirements for Final Fix.</p>
     */
    private String[] finalFixItems;

    /**
     * <p>Constructs new <code>SaveFinalFixItemsAction</code> instance. This implementation does nothing.</p>
     */
    public SaveFinalFixItemsAction() {
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
     * <p>Sets the flag indicating whether the Final Fix requirements are committed or not.</p>
     *
     * @param committed a <code>boolean</code> providing the flag indicating whether the Final Fix requirements are
     * committed or not.
     */
    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    /**
     * <p>Sets the requirements for Final Fix.</p>
     *
     * @param finalFixItems a <code>String[]</code> providing the requirements for Final Fix.
     */
    public void setFinalFixItems(String[] finalFixItems) {
        this.finalFixItems = finalFixItems;
    }

    /**
     * <p>Handles the incoming request.</p>
     *
     * <p>Updates the pre-existing Approval review with the items submitted by user.</p>
     *
     * @throws Exception if any error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        if (this.finalFixItems == null || this.finalFixItems.length == 0) {
            throw new IllegalStateException("No final fix items specified");
        } else {
            for (int i = 0; i < finalFixItems.length; i++) {
                String finalFixItem = finalFixItems[i];
                if (finalFixItem == null || finalFixItem.trim().length() == 0) {
                    throw new IllegalStateException("Final Fix item must not be empty");
                }
            }
        }
        
        // get user and contest details
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        SoftwareCompetition softwareCompetition =
                getContestServiceFacade().getSoftwareContestByProjectId(currentUser, contestId);

        // check user permission
        boolean userHasWriteFullPermission = DirectUtils.hasWritePermission(this, currentUser, this.contestId, false);
        if (!userHasWriteFullPermission) {
            throw new DirectException("User does not have a permission to access a project");
        }

        // Get or create Approver Resource for user
        Resource approverResource = DirectUtils.getUserResourceByRole(currentUser.getUserId(), softwareCompetition,
                ResourceRole.RESOURCE_ROLE_APPROVER_ID);
        if (approverResource == null) {
            approverResource = DirectUtils.registerUserToContest(this.contestId,
                    ResourceRole.RESOURCE_ROLE_APPROVER_ID, currentUser.getUserId(),
                    getSessionData().getCurrentUserHandle());
        }

        // update the approval scorecard

        // get scorecard first
        Phase[] phases = softwareCompetition.getProjectPhases().getAllPhases();
        Phase approvalPhase = phases[phases.length - 1];
        Long scorecardId = Long.parseLong(approvalPhase.getAttribute("Scorecard ID").toString());

        if (scorecardId == null) {
            throw new DirectException("There is no Approval scorecard specified for project");
        }
        Scorecard scorecard = getProjectServices().getScorecard(scorecardId);

        // Create and submit Approval review scorecard based on scorecard
        // This approval scorecard is expected to have only one group with only one section with only one question. 
        Group group = scorecard.getAllGroups()[0];
        Section section = group.getAllSections()[0];
        Question question = section.getAllQuestions()[0];
        Item item = new Item();
        item.setQuestion(question.getId());
        item.setAnswer("1/4");

        //  update review scorecard with new final-fix items
        for (int i = 0; i < finalFixItems.length; ++i) {
            Comment comment = new Comment();
            comment.setAuthor(approverResource.getId());
            comment.setComment(finalFixItems[i]);
            comment.setCommentType(CommentType.COMMENT_TYPE_REQUIRED);
            comment.setExtraInfo("Not Fixed");

            item.addComment(comment);
        }

        Review approvalReview = getProjectServices().getReviewsByPhase(contestId, approvalPhase.getId())[0];

        // set committed to true only if the submission doesn't need final-fix. 
        approvalReview.setModificationUser(String.valueOf(currentUser.getUserId()));
        approvalReview.setModificationTimestamp(new Date());
        approvalReview.setItems(Arrays.asList(item));
        approvalReview.setCommitted(committed);

        getProjectServices().updateReview(approvalReview);

        // Set success result for JSON processor
        Map success = new HashMap();
        success.put("success", "true");

        setResult(success);        
    }
}
