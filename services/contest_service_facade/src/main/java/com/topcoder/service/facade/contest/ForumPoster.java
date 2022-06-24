/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

/**
 * The forum poster.
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class ForumPoster {
    /**
     * The post handle.
     */
    private String handle;
    /**
     * The post handle id.
     */
    private String handleId;
    /**
     * The days since post, unit (days).
     */
    private String daysSincePost;

    /**
     * Default constructor.
     */
    public ForumPoster() {
    }

    /**
     * Gets the handle.
     * 
     * @return the handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the handle.
     * 
     * @param handle
     *            the handle to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Gets the handleId.
     * 
     * @return the handleId
     */
    public String getHandleId() {
        return handleId;
    }

    /**
     * Sets the handleId.
     * 
     * @param handleId
     *            the handleId to set
     */
    public void setHandleId(String handleId) {
        this.handleId = handleId;
    }

    /**
     * Gets the daysSincePost.
     * 
     * @return the daysSincePost
     */
    public String getDaysSincePost() {
        return daysSincePost;
    }

    /**
     * Sets the daysSincePost.
     * 
     * @param daysSincePost
     *            the daysSincePost to set
     */
    public void setDaysSincePost(String daysSincePost) {
        this.daysSincePost = daysSincePost;
    }

}
