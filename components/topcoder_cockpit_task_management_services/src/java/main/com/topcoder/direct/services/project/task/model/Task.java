/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * This entity represents a Task.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because it is mutable.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class Task extends BaseTaskEntity {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -7142083599292799710L;
    /**
     * <p>
     * Represents the start date.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date startDate;
    /**
     * <p>
     * Represents the due date.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date dueDate;
    /**
     * <p>
     * Represents the assignees.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<UserDTO> assignees;
    /**
     * <p>
     * Represents the priority.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private TaskPriority priority;
    /**
     * <p>
     * Represents the attachments.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<TaskAttachment> attachments;
    /**
     * <p>
     * Represents the status.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private TaskStatus status;
    /**
     * <p>
     * Represents the task list id.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long taskListId;

    /**
     * Creates an instance of Task.
     */
    public Task() {
        // Empty
    }

    /**
     * Gets the start date.
     *
     * @return the start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate
     *            the start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the due date.
     *
     * @return the due date.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     *
     * @param dueDate
     *            the due date.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the assignees.
     *
     * @return the assignees.
     */
    public List<UserDTO> getAssignees() {
        return assignees;
    }

    /**
     * Sets the assignees.
     *
     * @param assignees
     *            the assignees.
     */
    public void setAssignees(List<UserDTO> assignees) {
        this.assignees = assignees;
    }

    /**
     * Gets the priority.
     *
     * @return the priority.
     */
    public TaskPriority getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     *
     * @param priority
     *            the priority.
     */
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    /**
     * Gets the attachments.
     *
     * @return the attachments.
     */
    public List<TaskAttachment> getAttachments() {
        return attachments;
    }

    /**
     * Sets the attachments.
     *
     * @param attachments
     *            the attachments.
     */
    public void setAttachments(List<TaskAttachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * Gets the status.
     *
     * @return the status.
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the status.
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Gets the task list id.
     *
     * @return the task list id.
     */
    public long getTaskListId() {
        return taskListId;
    }

    /**
     * Sets the task list id.
     *
     * @param taskListId
     *            the task list id.
     */
    public void setTaskListId(long taskListId) {
        this.taskListId = taskListId;
    }

    public boolean isOverdue() {
        if(this.dueDate == null) {
            return false;
        }
        return this.dueDate.compareTo(new Date()) < 0;
    }
}