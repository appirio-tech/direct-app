/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.comment.specification;

import java.util.List;

import com.topcoder.security.TCSubject;

/**
 * <p>
 * This interface defines the contact to get spec review comments, add spec
 * review comment and update spec review comment.
 * </p>
 * 
 * <strong>Thread-safety:</strong>
 * <p>
 * Implementation needs to be thread-safe.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
public interface SpecReviewCommentService {
    /**
     * Get specification review comments for the given project.
     * 
     * @param tcSubject
     *            - the tc subject.
     * @param projectId
     *            - the project id.
     * @param isStudio
     *            - indicate it's studio contest to not
     * @return - a list of specification review comment for the project.
     * @throws IllegalArgumentException
     *             if the tcSubject is null.
     * @throws SpecReviewCommentServiceException
     *             if any error occurs.
     */
    public List<SpecReviewComment> getSpecReviewComments(TCSubject tcSubject,
            long projectId, boolean isStudio) throws SpecReviewCommentServiceException;

    /**
     * Add specification review comment for specific question.
     * 
     * @param tcSubject
     *            - tc subject.
     * @param projectId
     *            - the project id.
     * @param isStudio
     *            - indicate it's studio contest to not
     * @param questionId
     *            - the question id.
     * @param comment
     *            - the user comment.
     * @return - the comment id.
     * @throws IllegalArgumentException
     *             if the tcSubject or comment argument is
     *             null.
     * @throws SpecReviewCommentServiceException
     *             if any error occurs.
     */
    public long addSpecReviewComment(TCSubject tcSubject, long projectId,
            boolean isStudio, long questionId, UserComment comment) throws SpecReviewCommentServiceException;

    /**
     * Update the specification comment for specific question in the review
     * scorecard.
     * 
     * @param tcSubject
     *            - the tc subject.
     * @param projectId
     *            - the project id.
     * @param isStudio
     *            - indicate it's studio contest to not
     * @param questionId
     *            - the question id.
     * @param comment
     *            - the comment to update.
     * @throws IllegalArgumentException
     *             - if the tcSubject or comment is null.
     * @throws SpecReviewCommentServiceException
     *             if any error occurs.
     */
    public void updateSpecReviewComment(TCSubject tcSubject, long projectId,
            boolean isStudio, long questionId, UserComment comment) throws SpecReviewCommentServiceException;
}
