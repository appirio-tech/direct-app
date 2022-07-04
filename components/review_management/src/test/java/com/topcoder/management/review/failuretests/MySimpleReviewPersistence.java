/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.failuretests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.management.review.DuplicateReviewEntityException;
import com.topcoder.management.review.ReviewEntityNotFoundException;
import com.topcoder.management.review.ReviewPersistence;
import com.topcoder.management.review.ReviewPersistenceException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Review;
import com.topcoder.search.builder.filter.Filter;

/**
 * This class is a mock one of ReviewPersistence interface. This class is created for testing if the
 * DefaultReviewManager is correctly implemented for failure part.
 *
 * <p>
 * The purpose of this class is created to throw DuplicateReviewEntityException, ReviewPersistenceException
 * and ReviewEntityNotFoundException on purpose. These exception should be caught in the DefaultReviewManager
 * class and
 * </p>
 *
 * @author Chenhong
 * @version 1.0
 */
public class MySimpleReviewPersistence implements ReviewPersistence {

    /**
     * Represents the namespace for this class. It is set in the constructor.
     */
    private String namespace = null;

    /**
     * Represents the reivew instances in the persistence layer. The key is Long instance and the value is review
     * instance.
     */
    private Map reviews = new HashMap();

    /**
     * Default constructor.
     *
     */
    public MySimpleReviewPersistence() {
        // empyt.
    }

    /**
     * Create a new MySimpleReviewPersistence with namespace as its parameter.
     *
     * @param namespace
     *            the namespace for this class
     * @throws IllegalArgumentException
     *             if namespace is null or is empty string
     *
     */
    public MySimpleReviewPersistence(String namespace) {
        if (namespace == null) {
            throw new IllegalArgumentException("Parameter namespace should not be null.");
        }

        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter namespace should not be empty string.");
        }

        this.namespace = namespace;
    }

    /**
     * <p>
     * Create a review in the persistence. This method will throw DuplicateReviewEntityException and
     * ReviewPersistenceException on purpose.
     * </p>
     *
     * @param review
     *            the review to be created in the persistence.
     * @param operator
     *            the operator who creates the review entity.
     *
     * @throws IllegalArgumentException
     *             if any argument is null, or operator is empty string, or review fails to pass the validation.
     * @throws DuplicateReviewEntityException
     *             if review entity id already exists in the persistence.
     * @throws ReviewPersistenceException
     *             if any other error occurred.
     */
    public void createReview(Review review, String operator) throws ReviewPersistenceException {
        if (review == null) {
            throw new IllegalArgumentException("Parameter review should not be null.");
        }

        if (operator == null) {
            throw new IllegalArgumentException("Parameter operator should not be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter operator should not be empty string.");
        }

        if (reviews.containsKey(new Long(review.getId()))) {
            throw new DuplicateReviewEntityException("Review entity id already exists.", review.getId());
        }

        // throw ReviewPersistenceException on purpose.
        if (review.getId() == 10000) {
            throw new ReviewPersistenceException("This exception is thrown on purpose.");
        }

        reviews.put(new Long(review.getId()), review);
    }

    /**
     * <p>
     * Updates the review entity in the persistence. This method will throw ReviewEntityNotFoundException and
     * ReviewPersistenceException on purpose.
     * </p>
     *
     * @param review
     *            the review entity to update into the persistence.
     * @param operator
     *            the operator who updates the review entity.
     *
     * @throws ReviewEntityNotFoundException
     *             if given review entity does not exist in the persistence.
     * @throws ReviewPersistenceException
     *             if any other error occurred.
     */
    public void updateReview(Review review, String operator) throws ReviewPersistenceException {
        // if the given review entity does not exist in the persistence,
        // throw ReviewEntityNotFoundException.
        if (!reviews.containsKey(new Long(review.getId()))) {
            throw new ReviewEntityNotFoundException("The review entiy not found.", review.getId());
        }

        // throw ReviewPersistenceException on purpose.
        if (review.getId() == 10000) {
            throw new ReviewPersistenceException("This exception is thrown on purpose.");
        }

        reviews.put(new Long(review.getId()), review);
    }

    /**
     * <p>
     * Get the review with id. This method will throw ReviewEntityNotFoundException and ReviewPersistenceException on
     * purpose.
     * </p>
     *
     * @param id
     *            the id of the review entity to be retrieved.
     *
     * @return the retrieved review entity with its id.
     *
     * @throws ReviewEntityNotFoundException
     *             if the given id does not exist in the persistence.
     * @throws ReviewPersistenceException
     *             if any other error occurred.
     */
    public Review getReview(long id) throws ReviewPersistenceException {
        // if the given review entity does not exist in the persistence,
        // throw ReviewEntityNotFoundException.
        if (!reviews.containsKey(new Long(id))) {
            throw new ReviewEntityNotFoundException("The review entiy not found.", id);
        }

        // throw ReviewPersistenceException on purpose.
        if (id == 1000) {
            throw new ReviewPersistenceException("This exception is thrown on purpose.");
        }

        return null;
    }

    /**
     * <p>
     * Search reivews with Filter instance. This method will throw ReviewPersistenceException on purpose.
     * </p>
     *
     * @param filter
     *            the filter that specifies the search conditions.
     * @param complete
     *            a boolean variable indicating if the complete review data is retrieved.
     *
     * @return the array of matching review entities, or empty array if no review entity matches the filter.
     *
     * @throws ReviewPersistenceException
     *             if failed to search the review entities.
     */
    public Review[] searchReviews(Filter filter, boolean complete) throws ReviewPersistenceException {
        // throw ReviewPersistenceException on purpose.
        if (filter.getFilterType() == 2) {
            throw new ReviewPersistenceException("Throw ReviewPersistenceException on purpose.");
        }

        return new Review[0];
    }

    /**
     * <p>
     * Add review comment to review with given review id. This method will do nothing, but throw
     * ReviewEntityNotFoundException and ReviewPersistenceException on purpose.
     * </p>
     *
     * @param reviewId
     *            the id of review entity.
     * @param comment
     *            the Comment instance to be added.
     * @param operator
     *            the operator who adds the comment.
     *
     * @throws ReviewEntityNotFoundException
     *             if reviewId does not exists in the persistence.
     * @throws ReviewPersistenceException
     *             if any other error occurred.
     */
    public void addReviewComment(long reviewId, Comment comment, String operator) throws ReviewPersistenceException {
        // if the given review entity does not exist in the persistence,
        // throw ReviewEntityNotFoundException.
        if (!reviews.containsKey(new Long(reviewId))) {
            throw new ReviewEntityNotFoundException("The review entiy not found.", reviewId);
        }

        // throw ReviewPersistenceException on purpose.
        if (reviewId == 1000) {
            throw new ReviewPersistenceException("This exception is thrown on purpose.");
        }
    }

    /**
     * <p>
     * Add Item comment. This method do nothing, but will throw ReviewEntityNotFoundException and
     * ReviewPersistenceException on purpose.
     * </p>
     *
     * @param itemId
     *            the item id.
     * @param comment
     *            the Comment instance to be added.
     * @param operator
     *            the operator who adds the comment.
     *
     * @throws ReviewEntityNotFoundException
     *             if itemId does not exists in the persistence.
     * @throws ReviewPersistenceException
     *             if any other error occurred.
     */
    public void addItemComment(long itemId, Comment comment, String operator) throws ReviewPersistenceException {
        if (itemId == 100) {
            throw new ReviewEntityNotFoundException("Throw ReviewEntityNotFoundException on purpose.", itemId);
        }

        if (itemId == 1000) {
            throw new ReviewPersistenceException("Throw ReviewPersistenceException on purpose.");
        }

    }

    /**
     * <p>
     * Get all comment types in the persistence. It will throw ReviewPersistenceException on purpose.
     * </p>
     *
     * @return the array of all <code>CommentType</code> instance from the persistence, or empty array if no comment
     *         type can be found.
     *
     * @throws ReviewPersistenceException
     *             if failed to get the comment types.
     */
    public CommentType[] getAllCommentTypes() throws ReviewPersistenceException {
        throw new ReviewPersistenceException("Throw ReviewPersistenceException on purpose.");
    }

}