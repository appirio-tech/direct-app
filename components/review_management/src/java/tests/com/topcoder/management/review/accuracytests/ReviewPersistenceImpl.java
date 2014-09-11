/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ReviewPersistenceImpl.java
 */
package com.topcoder.management.review.accuracytests;

import com.topcoder.management.review.DuplicateReviewEntityException;
import com.topcoder.management.review.ReviewEntityNotFoundException;
import com.topcoder.management.review.ReviewPersistence;
import com.topcoder.management.review.ReviewPersistenceException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Review;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Simple implementation of <code>ReviewPersistence</code> class.
 * </p>
 *
 * @author wiedzmin
 * @version 1.0
 */
public class ReviewPersistenceImpl implements ReviewPersistence {

    /**Counter for createReview method calls.*/
    private int createReviewCallCount = 0;

    /**Counter for updateReview method calls.*/
    private int updateReviewCallCount = 0;

    /**Counter for getReview method calls.*/
    private int getReviewCallCount = 0;

    /**Counter for searchReviews method calls.*/
    private int searchReviewsCallCount = 0;

    /**Counter for addReviewComment method calls.*/
    private int addReviewCommentCallCount = 0;

    /**Counter for addItemComment method calls.*/
    private int addItemCommentCallCount = 0;

    /**Counter for getAllCommentTypes method calls.*/
    private int getAllCommentTypesCallCount = 0;

    /**
     * <p>
     * Ctor with no args.
     * </p>
     */
    public ReviewPersistenceImpl() {
        //do nothing
    }


    /**
     * <p>
     * Ctor that accepts string.
     * </p>
     *
     * @param namespace namespace
     */
    public ReviewPersistenceImpl(String namespace) {
        //do nothing
    }

    /**
     * <p>
     * Create review.
     * </p>
     *
     * @param review
     * @param operator
     * @throws DuplicateReviewEntityException
     * @throws ReviewPersistenceException
     */
    public void createReview(Review review,
                             String operator) throws DuplicateReviewEntityException, ReviewPersistenceException {
        createReviewCallCount++;
    }

    /**
     * <p>
     * createReviewCallCount variable.
     * </p>
     *
     * @return createReviewCallCount
     */
    public int createReviewCallCount() {
        return createReviewCallCount;
    }

    /**
     * <p>
     * Update review.
     * </p>
     *
     * @param review
     * @param operator
     * @throws ReviewEntityNotFoundException
     * @throws ReviewPersistenceException
     */
    public void updateReview(Review review,
                             String operator) throws DuplicateReviewEntityException, ReviewPersistenceException {
        updateReviewCallCount++;
    }

    /**
     * <p>
     * updateReviewCallCount variable.
     * </p>
     *
     * @return updateReviewCallCount
     */
    public int updateReviewCallCount() {
        return updateReviewCallCount;
    }

    /**
     * <p>
     * Get review.
     * </p>
     *
     * @param id
     * @return
     * @throws ReviewEntityNotFoundException
     * @throws ReviewPersistenceException
     */
    public Review getReview(long id) throws DuplicateReviewEntityException, ReviewPersistenceException {
        getReviewCallCount++;
        return new Review(1);
    }

    /**
     * <p>
     * getReviewCallCount variable.
     * </p>
     *
     * @return getReviewCallCount
     */
    public int getReviewCallCount() {
        return getReviewCallCount;
    }

    /**
     * <p>
     * Search reviews.
     * </p>
     *
     * @param filter
     * @param complete
     * @return review array
     * @throws ReviewPersistenceException
     */
    public Review[] searchReviews(Filter filter, boolean complete) throws ReviewPersistenceException {
        searchReviewsCallCount++;
        return new Review[0];
    }

    /**
     * <p>
     * searchReviewsCallCount variable.
     * </p>
     *
     * @return searchReviewsCallCount
     */
    public int searchReviewsCallCount() {
        return searchReviewsCallCount;
    }

    /**
     * <p>
     * Add review comment
     * </p>
     *
     * @param reviewId
     * @param comment
     * @param operator
     * @throws ReviewEntityNotFoundException
     * @throws ReviewPersistenceException
     */
    public void addReviewComment(long reviewId,
                                 Comment comment,
                                 String operator) throws DuplicateReviewEntityException, ReviewPersistenceException {
        addReviewCommentCallCount++;
    }

    /**
     * <p>
     * addReviewCommentCallCount variable.
     * </p>
     *
     * @return addReviewCommentCallCount
     */
    public int addReviewCommentCallCount() {
        return addReviewCommentCallCount;
    }

    /**
     * <p>
     * Add item comment.
     * </p>
     *
     * @param itemId
     * @param comment
     * @param operator
     * @throws ReviewEntityNotFoundException
     * @throws ReviewPersistenceException
     */
    public void addItemComment(long itemId,
                               Comment comment,
                               String operator) throws DuplicateReviewEntityException, ReviewPersistenceException {
        addItemCommentCallCount++;
    }

    /**
     * <p>
     * addItemCommentCallCount variable.
     * </p>
     *
     * @return addItemCommentCallCount
     */
    public int addItemCommentCallCount() {
        return addItemCommentCallCount;
    }

    /**
     * <p>
     * Get all comment types.
     * </p>
     *
     * @return comment types array.
     * @throws ReviewPersistenceException
     */
    public CommentType[] getAllCommentTypes() throws ReviewPersistenceException {
        getAllCommentTypesCallCount++;
        return new CommentType[0];
    }

    /**
     * <p>
     * getAllCommentTypesCallCount variable.
     * </p>
     *
     * @return getAllCommentTypesCallCount
     */
    public int getAllCommentTypesCallCount() {
        return getAllCommentTypesCallCount;
    }
}