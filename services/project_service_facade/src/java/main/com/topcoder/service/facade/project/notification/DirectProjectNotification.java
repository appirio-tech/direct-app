/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project.notification;

import java.io.Serializable;

/**
 * This class presents the project forum notification setting of a Cockpit user.
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class DirectProjectNotification implements Serializable {

    /**
     * The id of the direct project.
     */
    private long projectId;

    /**
     * The name of the project.
     */
    private String name;

    /**
     * The forum category id of the project.
     */
    private long forumId;

    /**
     * The forum notification set flag.
     */
    private boolean forumNotification;

    /**
     * Gets the direct project id.
     *
     * @return  the direct project id.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param projectId the direct project id.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the direct project name.
     *
     * @return the direct project name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the direct project name.
     *
     * @param name the direct project name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the project forum id.
     *
     * @return the project forum id.
     */
    public long getForumId() {
        return forumId;
    }

    /**
     * Sets the project forum id.
     *
     * @param forumId the project forum id.
     */
    public void setForumId(long forumId) {
        this.forumId = forumId;
    }

    /**
     * Gets the forum notification settings.
     *
     * @return the forum notification setting.
     */
    public boolean isForumNotification() {
        return forumNotification;
    }

    /**
     * Sets the forum notification setting.
     *
     * @param forumNotification the forum notification setting.
     */
    public void setForumNotification(boolean forumNotification) {
        this.forumNotification = forumNotification;
    }
}
