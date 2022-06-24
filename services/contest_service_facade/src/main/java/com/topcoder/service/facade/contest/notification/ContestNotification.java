/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.notification;

import java.io.Serializable;

/**
 * This class reprents the notification settings of a contest. The notification settings include
 * contest timeline notification and contest forum watch notification.
 * 
 * @author hohosky
 * @version 1.0
 */
public class ContestNotification implements Serializable {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 2742320281043254L;

    /**
     * The contest id.
     */
    private long contestId;

    /**
     * The contest name.
     */
    private String name;

    /**
     * The contest type.
     *
     * @since Direct Notification Assembly
     */
    private String type;

    /**
     * Flag indicates whether the forum is watched.
     */
    private boolean forumNotification;

    /**
     * Flag indicates whether the contest timeline notification is on.
     */
    private boolean projectNotification;

    /**
     * The category id of the forum of the contest.
     */
    private long forumId;

    /**
     * is studio type
     */
    private boolean isStudio;

    /**
     * Gets the contest id.
     * 
     * @return the id of the contest.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the id of the contest.
     * 
     * @param contestId the id of the contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the name of the contest.
     * 
     * @return the name of the contest.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the contest.
     * 
     * @param name the name of the contest.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets if the forum watching is on.
     * 
     * @return true if forum watching is on, false otherwise.
     */
    public boolean isForumNotification() {
        return forumNotification;
    }

    /**
     * Sets the forum watching flag.
     * 
     * @param forumNotification the forum watching flag to set.
     */
    public void setForumNotification(boolean forumNotification) {
        this.forumNotification = forumNotification;
    }

    /**
     * Gets if the contest timeline notification is on.
     * 
     * @return true of contest timeline notification is on, false otherwise.
     */
    public boolean isProjectNotification() {
        return projectNotification;
    }

    /**
     * Sets the contest timeline notification flag.
     * 
     * @param projectNotification the contest timeline notification flag.
     */
    public void setProjectNotification(boolean projectNotification) {
        this.projectNotification = projectNotification;
    }

    /**
     * Gets the forum id of the contest.
     * 
     * @return the forum id.
     */
    public long getForumId() {
        return forumId;
    }

    /**
     * Sets the forum id of the contest.
     * 
     * @param forumId the id of the forum.
     */
    public void setForumId(long forumId) {
        this.forumId = forumId;
    }

    /**
     * Gets the type of the contest.
     *
     * @return the type of the contest.
     * @since Direct Notification Assembly
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the contest.
     *
     * @param type the type of the contest.
     * @since Direct Notification Assembly
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Gets isStudio
     *
     * @return isStudio.
     */
    public boolean isStudio() {
        return isStudio;
    }

    /**
     * Sets isStudio
     *
     * @param isStudio isStudio
     */
    public void setIsStudio(boolean isStudio) {
        this.isStudio = isStudio;
    }
}
