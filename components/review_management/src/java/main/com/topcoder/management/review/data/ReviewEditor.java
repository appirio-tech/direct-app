/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The <code>ReviewEditor</code> class provides the ability to edit the properties of a review while automatically
 * updating the modification user and modification date whenever a property is edited. This class allows the client
 * using this component to associate a user with an editing session and then avoid the hassles of having to manually
 * call the <code>setModificationUser</code> and <code>setModificationTimestamp</code> whenever changes are made.
 * </p>
 * <p>
 * This class simply parallels the set/reset/add/remove/clear methods of the <code>Review</code> class. Each
 * set/reset/add/remove/clear call simply calls the same method on the <code>Review</code> instance and then invokes
 * the <code>setModificationUser</code> and <code>setModificationTimestamp</code> methods.
 * </p>
 * <p>
 * Thread-safe: This class is immutable, but is not thread safe (because <code>Review</code> is not thread safe).
 * </p>
 *
 * @author aubergineanode, vividmxx
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reviewEditor", propOrder = {"review", "user"})
public class ReviewEditor implements Serializable {

    /**
     * <p>
     * The review that this <code>ReviewEditor</code> edits.
     * </p>
     * <p>
     * This field is set in the constructor, is immutable, and can never be null. It is used in all of the
     * set/reset/add/remove/clear methods (which just call through to the corresponding method of the
     * <code>Review</code> itself and also set the modification user and datetime). It can be retrieved through the
     * <code>getReview</code> method.
     * </p>
     */
    private final Review review;

    /**
     * <p>
     * The user that is assigned as the modification user of the review on each set/reset/add/remove/clear call.
     * </p>
     * <p>
     * This field is set in the constructor, is immutable, and can never be null, but no other assumptions are made
     * about its value (it could be the empty string, all whitespace, etc). This field is used to set the modification
     * user of the review in all of the set/reset/add/remove/clear methods. The value of this field can be retrieved
     * through the <code>getUser</code> method.
     * </p>
     */
    private final String user;
    
    
    
	/**
	 * <p>
     * Creates a <code>ReviewEditor</code> to edit a newly created <code>Review</code> and an empty user. 
     * The creation user and datetime, modification user and datetime will be set on the newly created 
     * <code>Review</code>.
     * </p>
	 */
	public ReviewEditor() {
		this(new Review(), "");
		updateCreationUserAndDatetime();
        updateModificationUserAndDatetime();
	}

	/**
     * <p>
     * Creates a <code>ReviewEditor</code> to edit a newly created <code>Review</code>. The creation user and
     * datetime, modification user and datetime will be set on the newly created <code>Review</code>.
     * </p>
     *
     * @param user
     *            The name of the user to set as the creation and modification user of the review (and modification user
     *            if any future changes are made)
     * @exception IllegalArgumentException
     *                if <code>user</code> is null
     */
    public ReviewEditor(String user) {
        this(new Review(), user);
        updateCreationUserAndDatetime();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Creates a new <code>ReviewEditor</code> for the given <code>Review</code> and the given user to be assigned
     * as the modification user on each set/reset/add/remove/clear call.
     * </p>
     *
     * @param review
     *            The review to be edited
     * @param user
     *            The user to set as the modification user on each set/reset/add/remove/clear call
     * @throws IllegalArgumentException
     *             If <code>review</code> or <code>user</code> is null.
     */
    public ReviewEditor(Review review, String user) {
        if (user == null) {
            throw new IllegalArgumentException("user should not be null.");
        }
        if (review == null) {
            throw new IllegalArgumentException("review should not be null.");
        }
        this.user = user;
        this.review = review;
    }

    /**
     * <p>
     * Sets the identifier of the <code>Review</code> being edited, and sets modification user and datetime for the
     * <code>Review</code>.
     * </p>
     *
     * @param id
     *            The identifier to assign to the review
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive
     */
    public void setId(long id) {
        review.setId(id);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Resets the identifier of the <code>Review</code> being edited to its unassigned value, and sets modification
     * user and datetime for the <code>Review</code>.
     * </p>
     */
    public void resetId() {
        review.resetId();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Sets the author of the <code>Review</code> being edited, and sets modification user and datetime for the
     * <code>Review</code>.
     * </p>
     *
     * @param author
     *            The author to assign to the review
     * @throws IllegalArgumentException
     *             if <code>author</code> is not positive
     */
    public void setAuthor(long author) {
        review.setAuthor(author);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Resets the author of the <code>Review</code> being edited to its unassigned value, and sets modification user
     * and datetime for the <code>Review</code>.
     * </p>
     */
    public void resetAuthor() {
        review.resetAuthor();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Sets the submission of the <code>Review</code> being edited, and sets modification user and datetime for the
     * <code>Review</code>.
     *
     * @param submission
     *            The submission to assign to the review
     * @throws IllegalArgumentException
     *             if <code>submission</code> is not positive
     */
    public void setSubmission(long submission) {
        review.setSubmission(submission);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Resets the submission of the <code>Review</code> being edited to its unassigned value, and sets modification
     * user and datetime for the <code>Review</code>.
     * </p>
     */
    public void resetSubmission() {
        review.resetSubmission();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Sets the project phase of the <code>Review</code> being edited, and sets modification user and datetime for the
     * <code>Review</code>.
     *
     * @param projectPhase
     *            The project phase to assign to the review
     * @throws IllegalArgumentException
     *             if <code>projectPhase</code> is not positive
     */
    public void setProjectPhase(long projectPhase) {
        review.setProjectPhase(projectPhase);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Resets the project phase of the <code>Review</code> being edited to its unassigned value, and sets modification
     * user and datetime for the <code>Review</code>.
     * </p>
     */
    public void resetProjectPhase() {
        review.resetProjectPhase();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Sets the scorecard of the <code>Review</code> being edited, and sets modification user and datetime for the
     * <code>Review</code>.
     * </p>
     *
     * @param scorecard
     *            The scorecard to assign to the review
     * @throws IllegalArgumentException
     *             if <code>scorecard</code> is not positive
     */
    public void setScorecard(long scorecard) {
        review.setScorecard(scorecard);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Resets the scorecard of the <code>Review</code> being edited to its unassigned value, and sets modification
     * user and datetime for the <code>Review</code>.
     * </p>
     */
    public void resetScorecard() {
        review.resetScorecard();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Sets the committed status of the <code>Review</code> being edited, and sets modification user and datetime for
     * the <code>Review</code>.
     * </p>
     *
     * @param committed
     *            The flag indicating whether the <code>Review</code> is to be considered committed or not
     */
    public void setCommitted(boolean committed) {
        review.setCommitted(committed);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Resets the committed status of the <code>Review</code> being edited to its non-committed state, and sets
     * modification user and datetime for the <code>Review</code>.
     * </p>
     */
    public void resetCommitted() {
        review.resetCommitted();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Sets the score of the <code>Review</code> being edited, and sets modification user and datetime for the
     * <code>Review</code>.
     * </p>
     *
     * @param score
     *            The score to associate with the <code>Review</code>
     * @throws IllegalArgumentException
     *             If <code>score</code> wraps a negative value, NaN, or POSITIVE_INFINITY
     */
    public void setScore(Float score) {
        review.setScore(score);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Resets the score of the <code>Review</code> being edited to its unassigned value, and sets modification user
     * and datetime for the <code>Review</code>.
     * </p>
     */
    public void resetScore() {
        review.resetScore();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Adds an item to the items list of the <code>Review</code> being edited, and sets modification user and datetime
     * for the <code>Review</code>.
     * </p>
     *
     * @param item
     *            The item to add
     * @throws IllegalArgumentException
     *             If <code>item</code> is null
     */
    public void addItem(Item item) {
        review.addItem(item);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Adds items in the array to the items list of the <code>Review</code> being edited, and sets modification user
     * and datetime for the <code>Review</code>.
     * </p>
     *
     * @param items
     *            The array of items to add to the items list of the <code>Review</code>
     * @throws IllegalArgumentException
     *             If <code>items</code> is null or has null entries
     */
    public void addItems(Item[] items) {
        review.addItems(items);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Removes an item from the items list of the <code>Review</code> being edited, and sets modification user and
     * datetime for the <code>Review</code>.
     * </p>
     *
     * @param item
     *            The item to remove
     * @throws IllegalArgumentException
     *             If <code>item</code> is null
     */
    public void removeItem(Item item) {
        review.removeItem(item);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Removes items in the array from the items list of the <code>Review</code> being edited, and sets modification
     * user and datetime for the <code>Review</code>.
     * </p>
     *
     * @param items
     *            The array of items to remove from the items list of the <code>Review</code>
     * @throws IllegalArgumentException
     *             If <code>items</code> is null or has null entries
     */
    public void removeItems(Item[] items) {
        review.removeItems(items);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Clears all items from the items list of the <code>Review</code> being edited, and sets modification user and
     * datetime for the <code>Review</code>.
     * </p>
     */
    public void clearItems() {
        review.clearItems();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Adds a comment to the comments list of the <code>Review</code> being edited, and sets modification user and
     * datetime for the <code>Review</code>.
     * </p>
     *
     * @param comment
     *            The comment to add
     * @throws IllegalArgumentException
     *             If <code>comment</code> is null
     */
    public void addComment(Comment comment) {
        review.addComment(comment);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Adds comments in the array to the comments list of the <code>Review</code> being edited, and sets modification
     * user and datetime for the <code>Review</code>.
     * </p>
     *
     * @param comments
     *            The array of comments to add to the comments list of the <code>Review</code>
     * @throws IllegalArgumentException
     *             If <code>comments</code> is null or has null entries
     */
    public void addComments(Comment[] comments) {
        review.addComments(comments);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Removes a comment from the comments list of the <code>Review</code> being edited, and sets modification user
     * and datetime for the <code>Review</code>.
     * </p>
     *
     * @param comment
     *            The comment to remove
     * @throws IllegalArgumentException
     *             If <code>comment</code> is null
     */
    public void removeComment(Comment comment) {
        review.removeComment(comment);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Removes comments in the array from the comments list of the <code>Review</code> being edited, and sets
     * modification user and datetime for the <code>Review</code>.
     * </p>
     *
     * @param comments
     *            The array of comments to remove from the comments list of the <code>Review</code>
     * @throws IllegalArgumentException
     *             If <code>comments</code> is null or has null entries
     */
    public void removeComments(Comment[] comments) {
        review.removeComments(comments);
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Clears all comments from the comments list of the <code>Review</code> being edited, and sets modification user
     * and datetime for the <code>Review</code>.
     * </p>
     */
    public void clearComments() {
        review.clearComments();
        updateModificationUserAndDatetime();
    }

    /**
     * <p>
     * Retrieves the review that is being edited.
     * <p>
     *
     * @return The review being edited, it will always be non-null
     */
    public Review getReview() {
        return review;
    }

    /**
     * <p>
     * Retrieves the user that is set as the modification user each time the set/reset/add/remove/clear methods is
     * called.
     * </p>
     *
     * @return The user that is set as the modification user on each call to set a review property, it will always be
     *         non-null
     */
    public String getUser() {
        return user;
    }

    /**
     * <p>
     * This is a helper class to set the modification user and datetime for the <code>Review</code>.
     * </p>
     */
    private void updateModificationUserAndDatetime() {
        review.setModificationUser(user);
        review.setModificationTimestamp(new Date());
    }

    /**
     * <p>
     * This is a helper class to set the creation user and datetime for the <code>Review</code>.
     * </p>
     */
    private void updateCreationUserAndDatetime() {
        review.setCreationUser(user);
        review.setCreationTimestamp(new Date());
    }
}
