/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.util.List;

import javax.ejb.Remote;

import com.topcoder.security.TCSubject;

/**
 * The Interface SpecReviewService.
 *
 * Version 1.0.1 (Spec Review Finishing Touches v1.0) Change Notes:
 *  - Made the getSpecReviews method return instance of SpecReview rather than a list.
 *  - Added the methods to get reviewer and writer updates.
 *  - Added the methods to mark ready for review, review done and resubmit for review.
 * Version 1.0.2 (Cockpit Security Facade V1.0) Change:
 *  - Add TCSubject as parameter for methods:saveReviewStatus,saveReviewComment,markReviewCommentSeen,markReviewDone
 *                                           markReadyForReview, resubmitForReview
 * @author snow01, TCSASSEMBLER
 * @since Cockpit Launch Contest - Inline Spec Review Part 2
 * @version 1.0.2
 */
@Remote
public interface SpecReviewService {

    /**
     * Gets the instance of <code>SpecReview</code> for specified contest id.
     *
     * Updated for Version 1.0.1
     *  - Changed to get SpecReview rather than list.
     *  - SpecReview contains list of spec section reviews.
     *
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     *
     * @return the instance of <code>SpecReview</code> that matches the specified contest id.
     *
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    SpecReview getSpecReviews(long contestId, boolean studio) throws SpecReviewServiceException;

    /**
     * Save specified review comment and review status for specified section and specified contest id to persistence.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param isPass
     *            the is pass
     * @param role
     *            the user role type
     *
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    void saveReviewStatus(TCSubject tcSubject, long contestId, boolean studio, String sectionName, String comment,
            boolean isPass, String role) throws SpecReviewServiceException;

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param role
     *            the user role type
     *
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    void saveReviewComment(TCSubject tcSubject, long contestId, boolean studio, String sectionName, String comment,
            String role) throws SpecReviewServiceException;

    /**
     * Mark review comment with specified comment id as seen.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param commentId
     *            the comment id
     *
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    void markReviewCommentSeen(TCSubject tcSubject, long commentId) throws SpecReviewServiceException;

    /**
     * Gets List of <code>UpdatedSpecSectionData</code>, the updates made by reviewer of specs for specified contest id,
     * since last notification.
     *
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether the contest is studio or not
     * @return List of <code>UpdatedSpecSectionData</code>, the updates made by reviewer for specified contest id, since
     *         last notification.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public List<UpdatedSpecSectionData> getReviewerUpdates(long contestId, boolean studio)
            throws SpecReviewServiceException;

    /**
     * Gets List of <code>UpdatedSpecSectionData</code>, the updates made by writer of specs for specified contest id,
     * since last notification.
     *
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether the contest is studio or not
     * @return List of <code>UpdatedSpecSectionData</code>, the updates made by writer for specified contest id, since
     *         last notification.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public List<UpdatedSpecSectionData> getWriterUpdates(long contestId, boolean studio)
            throws SpecReviewServiceException;

    /**
     * Marks the spec_review to review done for specified contest id.
     *
     * It simply updates the review_done_time to the current.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReviewDone(TCSubject tcSubject, long contestId, boolean studio) throws SpecReviewServiceException;

    /**
     * Marks the spec_review to ready for review for specified contest id.
     *
     * It simply updates the ready_for_review_time to the current and updates the review status to READY.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReadyForReview(TCSubject tcSubject, long contestId, boolean studio)
        throws SpecReviewServiceException;

    /**
     * Marks the spec_review to resubmit for review for specified contest id.
     *
     * It simply updates the ready_for_review_time to the current.
     *
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void resubmitForReview(TCSubject tcSubject, long contestId, boolean studio)
        throws SpecReviewServiceException;

}
