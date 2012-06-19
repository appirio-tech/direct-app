/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project.edit;


import java.io.Serializable;

/**
 * <p>
 * This class represents the project notification setting in the edit cockpit project page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class ProjectNotificationSetting implements Serializable {

    /**
     * The id of the project.
     */
    private long projectId;

    /**
     * The id of the user.
     */
    private long userId;

    /**
     * The forum notification flag.
     */
    private boolean forumNotification;

    /**
     * Gets the id of the user.
     *
     * @return the id of the user.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the id of the user.
     *
     * @param userId the id of the user.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the id of the project.
     *
     * @return the id of the project.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the id of the project.
     *
     * @param projectId the id of the project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the flag of forum notification.
     *
     * @return the flag of forum notification.
     */
    public boolean isForumNotification() {
        return forumNotification;
    }

    /**
     * Sets the flag of forum notification.
     *
     * @param forumNotification the flag of forum notification.
     */
    public void setForumNotification(boolean forumNotification) {
        this.forumNotification = forumNotification;
    }
}
