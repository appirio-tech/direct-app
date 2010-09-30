/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import java.util.Date;

import net.sf.json.JSONSerializer;

import org.springframework.web.util.HtmlUtils;

import com.topcoder.direct.specreview.services.SpecReviewCommentService;
import com.topcoder.direct.specreview.services.UserComment;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This action is responsible for handling the request to add or update a specification review comment.
 * It extends <code>SpecificationReviewAction</code>.
 * </p>
 *
 * <p>
 * <code>SpecReviewCommentService</code> is used to perform the request.
 * The result data is aggregated into <code>SaveSpecificationReviewCommentActionResultData</code>.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="saveSpecificationReviewCommentAction" class="saveSpecificationReviewCommentAction"&gt;
 *     &lt;result name="input"&gt;/input.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/displayComment.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b>
 * This class is mutable and not thread safe. This should be fine since different instances of this
 * class should be created to serve different user requests and will not be shared across user
 * threads (this will be taken care of by Struts 2 framework).
 * The business services used by this component are reasonably thread safe and are responsible
 * for handling transactions. Hence this class can be considered as being effectively thread safe.
 * </p>
 *
 * @author caru, TCSDEVELOPER
 * @version 1.0
 */
public class SaveSpecificationReviewCommentAction extends SpecificationReviewAction {

    /**
     * Represents the action value when comment is being added.
     */
    private static final String ACTION_ADD = "add";

    /**
     * Represents the action value when comment is being updated.
     */
    private static final String ACTION_UPDATE = "update";

    /**
     * <p>
     * Represents the question id.
     * </p>
     *
     * <p>
     * Initially set to 0 (default), can be any value. It set by setter and accessed by getter. Used
     * in <code>execute</code> method.
     * </p>
     */
    private long questionId;

    /**
     * <p>
     * Represents the comment id.
     * </p>
     *
     * <p>
     * Initially set to 0 (default), can be any value. It set by setter and accessed by getter. Used
     * in <code>execute</code> method.
     * </p>
     */
    private long commentId;

    /**
     * <p>
     * Represents the comment.
     * </p>
     *
     * <p>
     * Initially set to null, can be any value. It set by setter and accessed by getter. Used
     * in <code>execute</code> method.
     * </p>
     */
    private String comment;
    
    /**
     * <p>
     * Represents the user who commented.
     * </p>
     *
     * <p>
     * Initially set to null, can be any value. It set by setter and accessed by getter. Used
     * in <code>execute</code> method.
     * </p>
     */
    private String commentBy;

    /**
     * <p>
     * Represents the action.
     * </p>
     *
     * <p>
     * Initially set to null, can be any value. It set by setter and accessed by getter. Used
     * in <code>execute</code> method.
     * </p>
     */
    private String action;

    /**
     * <p>
     * Represents the specification review comment service used to save the specification review comments.
     * <p>
     *
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and accessed by getter. Used in
     * <code>execute</code> method.
     * </p>
     */
    private SpecReviewCommentService specReviewCommentService;

    /**
     * Default constructor, creates new instance.
     */
    public SaveSpecificationReviewCommentAction() {
    }

    /**
     * <p>
     * Executes the request to save a specification review comment.
     * </p>
     *
     * <p>
     * No exception is thrown by this method. If any exception occurs, the exception is wrapped in
     * <code>SaveSpecificationReviewCommentActionException</code> with a proper message, stored in the model
     * using the <code>setException</code> method, and <code>Action.ERROR</code> is returned.
     * </p>
     *
     * <p>
     * If any required field hasn't been injected or <code>action</code> is not <code>ACTION_ADD</code> or
     * <code>ACTION_UPDATE</code> then <code>SaveSpecificationReviewCommentActionException</code>
     * will be stored in the model and <code>Action.ERROR</code> is returned.
     * </p>
     *
     * <p>
     * If comment is null or empty, then a validation error will be added to model and <code>Action.ERROR</code>
     * is returned.
     * </p>
     *
     * @return the action result String, will either be <code>Action.SUCCESS</code> or
     *         <code>Action.ERROR</code> depending on whether method was successful
     */
    public String execute() {
        try {
            // make sure required fields are present and valid
            if (comment == null || comment.trim().length() == 0) {
                addValidationError("comment", new String[] {"comment cannot be null/empty."});
                return ERROR;
            }

            if (action == null || !(action.equals(ACTION_ADD) || action.equals(ACTION_UPDATE))) {
                return Helper.handleActionError(this, new SaveSpecificationReviewCommentActionException(
                    "action cannot be null and must be either " + ACTION_ADD + " or " + ACTION_UPDATE + "."));
            }

            if (specReviewCommentService == null) {
                return Helper.handleActionError(this, new SaveSpecificationReviewCommentActionException(
                    "specReviewCommentService has not been injected."));
            }

            // prepare the user comment
            UserComment userComment = new UserComment();
            userComment.setCommentDate(new Date());
            userComment.setCommentBy(commentBy);
            comment = HtmlUtils.htmlEscape(comment);
            
            if (action.equals(ACTION_ADD)) {
                userComment.setComment(comment);

                // the SpecReviewCommentService is responsible for setting the remaining fields
                // for the add operation
                long newCommentId = specReviewCommentService.addSpecReviewComment(getTCSubject(),
                    getContestId(), isStudio(), questionId, userComment);
                userComment.setCommentId(newCommentId);
            } else {
                userComment.setCommentId(commentId);
                userComment.setComment(comment);

                // the SpecReviewCommentService is responsible for setting the remaining fields for the update
                // operation
                specReviewCommentService.updateSpecReviewComment(getTCSubject(), getContestId(), isStudio(),
                    questionId, userComment);
            }

            // store the result data
            SaveSpecificationReviewCommentActionResultData result =
                new SaveSpecificationReviewCommentActionResultData();
            result.setUserComment(userComment);
            setResultData(result);
            return SUCCESS;
        } catch (Exception e) {
            return Helper.handleActionError(this, new SaveSpecificationReviewCommentActionException(
                "An error occurred in execute method for SaveSpecificationReviewCommentAction.", e));
        }
    }

    /**
     * Getter for question id.
     *
     * @return the question id
     */
    public long getQuestionId() {
        return questionId;
    }

    /**
     * Setter for question id.
     *
     * @param questionId the question id
     */
    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    /**
     * Getter for comment id.
     *
     * @return the comment id
     */
    public long getCommentId() {
        return commentId;
    }

    /**
     * Setter for comment id.
     *
     * @param commentId the comment id
     */
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    /**
     * Getter for comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter for comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Getter for action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter for action.
     *
     * @param action the action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Getter for specification review comment service.
     *
     * @return the specification review comment service
     */
    public SpecReviewCommentService getSpecReviewCommentService() {
        return specReviewCommentService;
    }

    /**
     * Setter for specification review comment service.
     *
     * @param specReviewCommentService the specification review comment service
     * @throws IllegalArgumentException if argument is null
     */
    public void setSpecReviewCommentService(SpecReviewCommentService specReviewCommentService) {
        ExceptionUtils.checkNull(specReviewCommentService, null, null, "specReviewCommentService cannot be null.");
        this.specReviewCommentService = specReviewCommentService;
    }

    /**
     * Retrieve the commentBy field.
     *
     * @return the commentBy
     */
    public String getCommentBy() {
        return commentBy;
    }

    /**
     * Set the commentBy field.
     *
     * @param commentBy the commentBy to set
     */
    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }
}
