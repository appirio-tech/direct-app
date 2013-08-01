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
 * @author GreatKevin
 * @version 1.1
 */
public class TaskDTO implements Serializable {

    /**
     * The constant to indicate a long field not set.
     * @since 1.1
     */
    public static final long NOT_SET_LONG = -1000;

    /**
     * The id of the task.
     * @since 1.1
     */
    private long id;

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The notes of the task.
     * @since 1.1
     */
    private String notes;

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
     * The priority id of the task.
     * @since 1.1
     */
    private long priorityId = NOT_SET_LONG;

    /**
     * The associated milestone id of the task.
     * @since 1.1
     */
    private long associatedMilestoneId = NOT_SET_LONG;

    /**
     * The associated contest id of the task.
     * @since 1.1
     */
    private long associatedContestId = NOT_SET_LONG;

    /**
     * The user ids this task is assigned to.
     */
    private List<Long> assignUserIds;

    /**
     * Gets the id of the task.
     *
     * @return the id of the task.
     * @since 1.1
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the task.
     *
     * @param id the id of the task.
     * @since 1.1
     */
    public void setId(long id) {
        this.id = id;
    }

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

    /**
     * Gets the priority id.
     *
     * @return the priority id.
     * @since 1.1
     */
    public long getPriorityId() {
        return priorityId;
    }

    /**
     * Sets the priority id.
     *
     * @param priorityId the priority id.
     * @since 1.1
     */
    public void setPriorityId(long priorityId) {
        this.priorityId = priorityId;
    }

    /**
     * Gets the associated milestone id.
     *
     * @return the associated milestone id.
     * @since 1.1
     */
    public long getAssociatedMilestoneId() {
        return associatedMilestoneId;
    }

    /**
     * Sets the associated milestone id.
     *
     * @param associatedMilestoneId the associated milestone id.
     * @since 1.1
     */
    public void setAssociatedMilestoneId(long associatedMilestoneId) {
        this.associatedMilestoneId = associatedMilestoneId;
    }

    /**
     * Gets the associated contest id.
     *
     * @return the associated contest id.
     * @since 1.1
     */
    public long getAssociatedContestId() {
        return associatedContestId;
    }

    /**
     * Sets the associated contest id.
     *
     * @param associatedContestId the associated contest id.
     * @since 1.1
     */
    public void setAssociatedContestId(long associatedContestId) {
        this.associatedContestId = associatedContestId;
    }

    /**
     * Gets the notes.
     *
     * @return the notes.
     * @since 1.1
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     *
     * @param notes the notes.
     * @since 1.1
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
