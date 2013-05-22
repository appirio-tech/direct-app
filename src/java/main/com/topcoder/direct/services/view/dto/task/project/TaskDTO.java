/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.task.project;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * The DTO to transfer the data for a task between front end and back end.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly TC - Cockpit Tasks Management Services Setup and Quick Add Task)
 */
public class TaskDTO implements Serializable {

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The status id of the task.
     */
    private long statusId;

    /**
     * The id of the task list this task is in.
     */
    private long taskListId;

    /**
     * The start date of the task.
     */
    private String startDate;

    /**
     * The due date of the task.
     */
    private String dueDate;

    /**
     * The user ids this task is assigned to.
     */
    private List<Long> assignUserIds;

    /**
     * Gets the name of the task.
     *
     * @return the name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the name of the task.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the status id of the task.
     *
     * @return the status id of the task.
     */
    public long getStatusId() {
        return statusId;
    }

    /**
     * Sets the status id of the task.
     *
     * @param statusId the status id of the task.
     */
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    /**
     * Gets the id of the task list.
     *
     * @return the id of the task list.
     */
    public long getTaskListId() {
        return taskListId;
    }

    /**
     * Sets the id of the task list.
     *
     * @param taskListId the id of the task list.
     */
    public void setTaskListId(long taskListId) {
        this.taskListId = taskListId;
    }

    /**
     * Gets the start date.
     *
     * @return the start date.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the start date.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the due date.
     *
     * @return the due date.
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     *
     * @param dueDate the due date.
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the ids of the users the task is assigned to.
     *
     * @return the ids of the users the task is assigned to.
     */
    public List<Long> getAssignUserIds() {
        return assignUserIds;
    }

    /**
     * Sets the ids of the users the task is assigned to.
     *
     * @param assignUserIds the ids of the users the task is assigned to.
     */
    public void setAssignUserIds(List<Long> assignUserIds) {
        this.assignUserIds = assignUserIds;
    }
}
