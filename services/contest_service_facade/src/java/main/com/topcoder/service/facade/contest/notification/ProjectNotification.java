/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.notification;

import java.io.Serializable;
import java.util.List;

/**
 * This class stores the notificaiton settings for a whole TC direct project. It includes a list of
 * ContestNotification instances which represents the notification settings for contests of this TC
 * direct project.
 * 
 * 
 * @author hohosky
 * @version 1.0
 */
public class ProjectNotification implements Serializable {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 340017340124753434L;

    /**
     * The id of the project.
     */
    private long projectId;

    /**
     * The name of the project.
     */
    private String name;

    /**
     * A list of ContestNotification instances.
     */
    private List<ContestNotification> contestNotifications;

    /**
     * Gets the project id.
     * 
     * @return the id of the project.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     * 
     * @param projectId the id of the project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the name of the project.
     * 
     * @return the name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project.
     * 
     * @param name the name of the project.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of ContestNotification instances.
     * 
     * @return the list of ContestNotification instances.
     */
    public List<ContestNotification> getContestNotifications() {
        return contestNotifications;
    }

    /**
     * Sets the ContestNotifications.
     * 
     * @param contestNotifications the list of ContestNotification instances to set.
     */
    public void setContestNotifications(List<ContestNotification> contestNotifications) {
        this.contestNotifications = contestNotifications;
    }
}
