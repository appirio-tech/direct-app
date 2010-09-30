/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.specreview.services;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * This entity represents the comment for a specific question in the review
 * scorecard.
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
public class UserComment implements Serializable {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -1833193147486043523L;

    /**
     * Represents the comment id.
     * It has getter & setter.
     * It can be any value.
     */
    private long commentId;

    /**
     * Represents the user making the comment.
     * It has getter & setter.
     * It can be any value.
     */
    private String commentBy;

    /**
     * Represents the comment date.
     * It has getter & setter.
     * It can be any value.
     */
    private Date commentDate;

    /**
     * Represents the comment.
     * It has getter & setter.
     * It can be any value.
     */
    private String comment;

    /**
     * Empty constructor.
     */
    public UserComment() {
    }

    /**
     * Retrieve the commentId field.
     * 
     * @return the commentId
     */
    public long getCommentId() {
        return commentId;
    }

    /**
     * Set the commentId field.
     * 
     * @param commentId
     *            the commentId to set
     */
    public void setCommentId(long commentId) {
        this.commentId = commentId;
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
     * @param commentBy
     *            the commentBy to set
     */
    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    /**
     * Retrieve the commentDate field.
     * 
     * @return the commentDate
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * Set the commentDate field.
     * 
     * @param commentDate
     *            the commentDate to set
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * Retrieve the comment field.
     * 
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the comment field.
     * 
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
