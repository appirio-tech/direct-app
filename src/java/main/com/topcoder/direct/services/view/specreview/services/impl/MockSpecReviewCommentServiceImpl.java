/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.specreview.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.specreview.services.SpecReviewComment;
import com.topcoder.direct.specreview.services.SpecReviewCommentService;
import com.topcoder.direct.specreview.services.SpecReviewCommentServiceException;
import com.topcoder.direct.specreview.services.UserComment;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * A mock class of <code>SpecReviewCommentService</code>, provide in-memory
 * store for test.
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
public class MockSpecReviewCommentServiceImpl implements
        SpecReviewCommentService {
    /**
     * The map used to store specification review comment list.
     */
    private Map<Long, List<SpecReviewComment>> specReviewCommentsMap = new HashMap<Long, List<SpecReviewComment>>();

    /**
     * Used to generate comment id.
     */
    private long idGenerator = 1L;

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
            boolean isStudio, long questionId, UserComment comment)
            throws SpecReviewCommentServiceException {
        List<SpecReviewComment> specReviewComments = getSpecReviewComments(
                tcSubject, projectId, isStudio);
        SpecReviewComment sComment = null;
        for (SpecReviewComment specReviewComment : specReviewComments) {
            if (specReviewComment.getQuestionId() == questionId) {
                sComment = specReviewComment;
            }
        }
        if (sComment == null) {
            sComment = new SpecReviewComment();
            sComment.setQuestionId(questionId);
            sComment.setComments(new ArrayList<UserComment>());
            specReviewComments.add(sComment);
        }

        comment.setCommentId(idGenerator++);
        sComment.getComments().add(comment);
        return comment.getCommentId();
    }

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
            long projectId, boolean isStudio)
            throws SpecReviewCommentServiceException {
        if (!specReviewCommentsMap.containsKey(projectId)) {
            specReviewCommentsMap.put(projectId,
                    new ArrayList<SpecReviewComment>());
        }
        return specReviewCommentsMap.get(projectId);
    }

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
            boolean isStudio, long questionId, UserComment comment)
            throws SpecReviewCommentServiceException {
        List<SpecReviewComment> specReviewComments = getSpecReviewComments(
                tcSubject, projectId, isStudio);
        for (SpecReviewComment specReviewComment : specReviewComments) {
            if (specReviewComment.getQuestionId() == questionId) {
                List<UserComment> comments = specReviewComment.getComments();
                for (UserComment userComment : comments) {
                    if (userComment.getCommentId() == comment.getCommentId()) {
                        userComment.setComment(comment.getComment());
                        userComment.setCommentBy(comment.getCommentBy());
                        userComment.setCommentDate(comment.getCommentDate());
                        specReviewComment.setComments(comments);
                        
                        return;
                    }
                }
            }
        }
    }

}
