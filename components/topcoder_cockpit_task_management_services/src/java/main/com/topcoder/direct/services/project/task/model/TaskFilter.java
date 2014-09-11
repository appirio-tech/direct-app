/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This is the search filter for tasks.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because it is mutable.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class TaskFilter implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -7834592721896878174L;

    /**
     * <p>
     * Represents the name.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String name;
    /**
     * <p>
     * Represents the assignee id.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Long assigneeId;
    /**
     * <p>
     * Represents the start due date.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date dueDateFrom;
    /**
     * <p>
     * Represents the end due date.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date dueDateTo;
    /**
     * <p>
     * Represents the priorities.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<TaskPriority> priorities;
    /**
     * <p>
     * Represents the statuses.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<TaskStatus> statuses;
    /**
     * <p>
     * Represents the associated to project milestone ids.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<Long> associatedToProjectMilestoneIds;
    /**
     * <p>
     * Represents the associated to contest ids.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<Long> associatedToContestIds;
    /**
     * <p>
     * Represents the project ids.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<Long> projectIds;
    /**
     * <p>
     * Represents the flag indicating whether the project is active.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Boolean isProjectActive;

    /**
     * Creates an instance of TaskFilter.
     */
    public TaskFilter() {
        // Empty
    }

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the assignee id.
     *
     * @return the assignee id.
     */
    public Long getAssigneeId() {
        return assigneeId;
    }

    /**
     * Sets the assignee id.
     *
     * @param assigneeId
     *            the assignee id.
     */
    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    /**
     * Gets the start due date.
     *
     * @return the start due date.
     */
    public Date getDueDateFrom() {
        return dueDateFrom;
    }

    /**
     * Sets the start due date.
     *
     * @param dueDateFrom
     *            the start due date.
     */
    public void setDueDateFrom(Date dueDateFrom) {
        this.dueDateFrom = dueDateFrom;
    }

    /**
     * Gets the end due date.
     *
     * @return the end due date.
     */
    public Date getDueDateTo() {
        return dueDateTo;
    }

    /**
     * Sets the end due date.
     *
     * @param dueDateTo
     *            the end due date.
     */
    public void setDueDateTo(Date dueDateTo) {
        this.dueDateTo = dueDateTo;
    }

    /**
     * Gets the priorities.
     *
     * @return the priorities.
     */
    public List<TaskPriority> getPriorities() {
        return priorities;
    }

    /**
     * Sets the priorities.
     *
     * @param priorities
     *            the priorities.
     */
    public void setPriorities(List<TaskPriority> priorities) {
        this.priorities = priorities;
    }

    /**
     * Gets the statuses.
     *
     * @return the statuses.
     */
    public List<TaskStatus> getStatuses() {
        return statuses;
    }

    /**
     * Sets the statuses.
     *
     * @param statuses
     *            the statuses.
     */
    public void setStatuses(List<TaskStatus> statuses) {
        this.statuses = statuses;
    }

    /**
     * Gets the associated to project milestone ids.
     *
     * @return the associated to project milestone ids.
     */
    public List<Long> getAssociatedToProjectMilestoneIds() {
        return associatedToProjectMilestoneIds;
    }

    /**
     * Sets the associated to project milestone ids.
     *
     * @param associatedToProjectMilestoneIds
     *            the associated to project milestone ids.
     */
    public void setAssociatedToProjectMilestoneIds(List<Long> associatedToProjectMilestoneIds) {
        this.associatedToProjectMilestoneIds = associatedToProjectMilestoneIds;
    }

    /**
     * Gets the associated to contest ids.
     *
     * @return the associated to contest ids.
     */
    public List<Long> getAssociatedToContestIds() {
        return associatedToContestIds;
    }

    /**
     * Sets the associated to contest ids.
     *
     * @param associatedToContestIds
     *            the associated to contest ids.
     */
    public void setAssociatedToContestIds(List<Long> associatedToContestIds) {
        this.associatedToContestIds = associatedToContestIds;
    }

    /**
     * Gets the project ids.
     *
     * @return the project ids.
     */
    public List<Long> getProjectIds() {
        return projectIds;
    }

    /**
     * Sets the project ids.
     *
     * @param projectIds
     *            the project ids.
     */
    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }

    /**
     * Gets the flag indicating whether the project is active.
     *
     * @return the flag indicating whether the project is active.
     */
    public Boolean getProjectActive() {
        return isProjectActive;
    }

    /**
     * Sets the flag indicating whether the project is active.
     *
     * @param isProjectActive
     *            the flag indicating whether the project is active.
     */
    public void setProjectActive(Boolean isProjectActive) {
        this.isProjectActive = isProjectActive;
    }
}