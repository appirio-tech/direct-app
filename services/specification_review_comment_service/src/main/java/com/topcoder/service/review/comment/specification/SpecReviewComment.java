/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.comment.specification;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * This entity represents comments for a specific question in the spec review.
 * </p>
 * 
 * <strong>Thread-safety:</strong>
 * <p>
 * mutable and not thread-safe.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class SpecReviewComment implements Serializable {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 7795942407021448589L;

    /**
     * Represents the question id.
     * It has getter & setter.
     * It can be any value.
     */
    private long questionId;

    /**
     * Represents the list of comments for the question.
     * It has getter & setter.
     * It can be any value.
     */
    private List<UserComment> comments;

    /**
     * Empty constructor.
     */
    public SpecReviewComment() {
    }

    /**
     * Retrieve the questionId field.
     * 
     * @return the questionId
     */
    public long getQuestionId() {
        return questionId;
    }

    /**
     * Set the questionId field.
     * 
     * @param questionId
     *            the questionId to set
     */
    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    /**
     * Retrieve the comments field.
     * 
     * @return the comments
     */
    public List<UserComment> getComments() {
        return comments;
    }

    /**
     * Set the comments field.
     * 
     * @param comments
     *            the comments to set
     */
    public void setComments(List<UserComment> comments) {
        this.comments = comments;
    }
}
