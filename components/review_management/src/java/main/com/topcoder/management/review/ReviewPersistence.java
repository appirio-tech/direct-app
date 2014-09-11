/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Review;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines the contract of managing the review entities in the
 * persistence. It provides the persistence functionalities to create, update
 * and search reviews. Additionally, application users can also add comment for
 * review and item, and get all the comment types from the persistence. The
 * constructor of the implementation must take a string argument as namespace.
 * </p>
 * <p>
 * Thread Safety: The implementation of this interface is not required to be
 * thread safe.
 * </p>
 * @author woodjhon, urtks
 * @version 1.0
 */
public interface ReviewPersistence {
    /**
     * <p>
     * Create the review in the persistence. This method is also responsible for
     * creating the associated Comment, and Item, but not for CommentType.
     * </p>
     * @param review
     *            the Review to create
     * @param operator
     *            the operator who creates the review
     * @exception IllegalArgumentException
     *                if either of arguments is null, or operator is empty
     *                string or review fails to pass the validation.
     * @exception DuplicateReviewEntityException
     *                if review id already exists.
     * @exception ReviewPersistenceException
     *                if failed to create the instance in the persistence
     */
    public void createReview(Review review, String operator) throws DuplicateReviewEntityException,
        ReviewPersistenceException;

    /**
     * <p>
     * Update the review in the persistence. The update method is also
     * responsible for creating, deleting, updating the associated Items.
     * </p>
     * @param review
     *            the review to update
     * @param operator
     *            the operator who updates the review
     * @exception IllegalArgumentException
     *                if either of arguments is null, operator is empty string,
     *                or review fails to pass the validation.
     * @exception ReviewEntityNotFoundException
     *                if given review id does not exist
     * @exception ReviewPersistenceException
     *                if any other error occurred.
     */
    public void updateReview(Review review, String operator) throws ReviewEntityNotFoundException,
        ReviewPersistenceException;

    /**
     * <p>
     * Get the review with given review id from the persistence.
     * </p>
     * @param id
     *            the review id
     * @return the Review instance with given id
     * @exception IllegalArgumentException
     *                if id is not positive
     * @exception ReviewEntityNotFoundException
     *                if given id does not exist in the database
     * @exception ReviewPersistenceException
     *                if any other error occurred.
     */
    public Review getReview(long id) throws ReviewEntityNotFoundException,
        ReviewPersistenceException;

    /**
     * <p>
     * Search the reviews with given search filters. If complete is false, the
     * associated items and comments of the matching review will not been
     * retrieved. Return empty array if no review matches the filter.
     * </p>
     * <p>
     * In the version 1.0, the filter supports at most five fields:
     * <ol>
     * <li>scorecardType --- the score card type, must be java.Long type</li>
     * <li>submission --- the review submission id, must be java.Long type</li>
     * <li>reviewer --- the author of the review, must be java.Long type</li>
     * <li>project --- the project id of the review, must be java.Long type</li>
     * <li>committed --- indicate if the review has been committed, must be
     * java.lang.Integer type. Either new Integer(1) representing committed, or
     * new Integer(0), represent not committed</li>
     * </ol>
     * </p>
     * @param filter
     *            the filter that specifies the search conditions
     * @param complete
     *            a boolean indicating if the complete review data is retrieved
     * @return an array of matching reviews, possible empty.
     * @exception IllegalArgumentException
     *                if filter is null
     * @exception ReviewPersistenceException
     *                if failed to search the reviews.
     */
    public Review[] searchReviews(Filter filter, boolean complete)
        throws ReviewPersistenceException;

    /**
     * <p>
     * Add comment to review with given review id. this method is responsible
     * for creating the Comment, but not for the CommentType of the given
     * comment.
     * </p>
     * @param reviewId
     *            the review id
     * @param comment
     *            the Comment instance to add
     * @param operator
     *            the operator who adds the comment
     * @exception IllegalArgumentException
     *                if comment or operator is null, reviewId is not positive,
     *                operator is empty string.
     * @exception ReviewEntityNotFoundException
     *                if reviewId does not exists
     * @exception ReviewPersistenceException
     *                if any other error occurred.
     */
    public void addReviewComment(long reviewId, Comment comment, String operator)
        throws ReviewEntityNotFoundException, ReviewPersistenceException;

    /**
     * <p>
     * Add the comment to the item with given item id. this method is
     * responsible for creating the Comment, but not for the CommentType of the
     * given comment.
     * </p>
     * @param itemId
     *            the item id
     * @param comment
     *            the Comment to add
     * @param operator
     *            the operator who adds the comment
     * @exception IllegalArgumentException
     *                if comment or operator is null, itemId is not positive,
     *                operator is empty string.
     * @exception ReviewEntityNotFoundException
     *                if itemId does not exists
     * @exception ReviewPersistenceException
     *                if any other error occurred.
     */
    public void addItemComment(long itemId, Comment comment, String operator)
        throws ReviewEntityNotFoundException, ReviewPersistenceException;

    /**
     * <p>
     * Get all the CommentType instances from the persistence. Return empty
     * array if no type is found.
     * </p>
     * @return An array containing all the CommentType instances
     * @exception ReviewPersistenceException
     *                if failed to get the types
     */
    public CommentType[] getAllCommentTypes() throws ReviewPersistenceException;
}
