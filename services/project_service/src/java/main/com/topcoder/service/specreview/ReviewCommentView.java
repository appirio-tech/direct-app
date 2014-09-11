/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents the entity for review_comment_view table.
 * 
 * It defines a view event of the review comment identified by a comment id.
 * 
 * @author snow01
 * @version 1.0
 * @since Cockpit Launch Contest - Inline Spec Review Part 2
 */
public class ReviewCommentView implements Serializable {

    /**
     * Default Serial Version Id.
     */
    private static final long serialVersionUID = 1L;

    /** The view id. */
    private long viewId;

    /** The comment id. */
    private long commentId;

    /** The view user. */
    private String viewUser;

    /** The view time. */
    private Date viewTime;

    /**
     * Instantiates a new review comment view.
     */
    public ReviewCommentView() {
    }

    /**
     * Gets the view id.
     * 
     * @return the view id
     */
    public long getViewId() {
        return viewId;
    }

    /**
     * Sets the view id.
     * 
     * @param viewId
     *            the new view id
     */
    public void setViewId(long viewId) {
        this.viewId = viewId;
    }

    /**
     * Gets the comment id.
     * 
     * @return the comment id
     */
    public long getCommentId() {
        return commentId;
    }

    /**
     * Sets the comment id.
     * 
     * @param commentId
     *            the new comment id
     */
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    /**
     * Gets the view user.
     * 
     * @return the view user
     */
    public String getViewUser() {
        return viewUser;
    }

    /**
     * Sets the view user.
     * 
     * @param viewUser
     *            the new view user
     */
    public void setViewUser(String viewUser) {
        this.viewUser = viewUser;
    }

    /**
     * Gets the view time.
     * 
     * @return the view time
     */
    public Date getViewTime() {
        return viewTime;
    }

    /**
     * Sets the view time.
     * 
     * @param viewTime
     *            the new view time
     */
    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }

}
