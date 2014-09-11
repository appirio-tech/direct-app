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
 * This <code>ReviewManager</code> interface defines the contract of managing the review
 * entities. It provides the management functionalities to create, update
 * and search review entities. It can also be used to add comment for review
 * and item, and get all the comment types from the manager.
 * </p>
 *
 * <p>
 * Thread safety: Implementation of this class is not required to be thread-safe.
 * </p>
 *
 * @author woodjhon, icyriver
 * @version 1.0
 */
public interface ReviewManager {
    /**
     * <p>
     * This method creates the review entity in the manager. This method will also
     * create the associated <code>Comment</code> and <code>Item</code> instances.
     * </p>
     *
     * @param review the review entity to be created in the manager.
     * @param operator the operator who creates the review entity.
     *
     * @throws IllegalArgumentException if any argument is null, or operator is empty string,
     *                                  or review fails to pass the validation.
     * @throws ReviewManagementException if failed to create the review entity in the manager.
     */
    public void createReview(Review review, String operator) throws ReviewManagementException;

    /**
     * <p>
     * This method updates the review entity in the manager. This method will also be responsible
     * for creating, deleting, updating the associated <code>Item</code> instances.
     * </p>
     *
     * @param review the review entity to update into the manager.
     * @param operator the operator who updates the review entity.
     *
     * @throws IllegalArgumentException if arguments is null, or operator is empty string,
     *                                  or review fails to pass the validation.
     * @throws ReviewManagementException if failed to update the review entity in the manager.
     */
    public void updateReview(Review review, String operator) throws ReviewManagementException;

    /**
     * <p>
     * This method gets the review entity from the manager with given review entity id.
     * </p>
     *
     * @param id the id of the review entity to be retrieved.
     *
     * @return the retrieved review entity with its id.
     *
     * @throws IllegalArgumentException if the given id is not positive.
     * @throws ReviewManagementException if failed to get the review entity from the manager.
     */
    public Review getReview(long id) throws ReviewManagementException;

    /**
     * <p>
     * This method will search the review entities with the given search filters.
     * If the complete argument is false, the associated items and comments of
     * the matching review entity will not been retrieved.
     * </p>
     *
     * <p>
     * Five fields supported by the <code>searchReviews</code> method in version 1.0:
     * <ul>
     * <li>
     * scorecardType - the score card type, must be Long type.
     * </li>
     * <li>
     * submission - the review submission id, must be Long type.
     * </li>
     * <li>
     * reviewer - the author of the review, must be Long type.
     * </li>
     * <li>
     * project - the project id of the review, must be Long type.
     * </li>
     * <li>
     * committed --- indicate if the review has been committed,
     * using Integer(1) representing committed, or Integer(0)
     * representing not committed.
     * </li>
     * </ul>
     * </p>
     *
     * @param filter the filter that specifies the search conditions.
     * @param complete a boolean variable indicating if the complete review data is retrieved.
     *
     * @return the array of matching review entities, or empty array if no review entity matches
     *         the filter.
     *
     * @throws IllegalArgumentException if the filter is null.
     * @throws ReviewManagementException if failed to search the review entities from the manager.
     */
    public Review[] searchReviews(Filter filter, boolean complete) throws ReviewManagementException;

    /**
     * <p>
     * This method will add comment to review with given review id. This method
     * is also responsible for creating the <code>Comment</code> instance.
     * </p>
     *
     * @param reviewId the id of review entity.
     * @param comment the <code>Comment</code> instance to be added.
     * @param operator the operator who adds the comment.
     *
     * @throws IllegalArgumentException if any argument is null, or reviewId is not positive,
     *                                  or operator is empty string.
     * @throws ReviewManagementException if failed to add comment for the review entity in the manager.
     */
    public void addReviewComment(long reviewId, Comment comment, String operator)
        throws ReviewManagementException;

    /**
     * <p>
     * This method will add the comment to the item with given item id. This method
     * is also responsible for creating the <code>Comment</code> instance.
     * </p>
     *
     * @param itemId the item id.
     * @param comment the <code>Comment</code> instance to be added.
     * @param operator the operator who adds the comment.
     *
     * @throws IllegalArgumentException if any argument is null, or itemId is not positive,
     *                                  or operator is empty string.
     * @throws ReviewManagementException if failed to add comment for the item entity in the manager.
     */
    public void addItemComment(long itemId, Comment comment, String operator)
        throws ReviewManagementException;

    /**
     * <p>
     * This method will get all the <code>CommentType</code> instance from the manager.
     * </p>
     *
     * @return the array of all <code>CommentType</code> instances from the persistence,
     *         or empty array if no comment type can be found.
     *
     * @throws ReviewManagementException if failed to get all comment types from manager.
     */
    public CommentType[] getAllCommentTypes() throws ReviewManagementException;
}
