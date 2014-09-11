/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This entity represents a Task List.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because it is mutable.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Tasks Management Release 2)
 * <ul>
 *     <li>Adds method {@link #getActiveTasks()}</li>
 *     <li>Adds method {@link #getCompletedTasks()} ()}</li>
 * </ul>
 * </p>
 *
 * @author albertwang, sparemax, TCSASSEMBLER
 * @version 1.1
 */
public class TaskList extends BaseTaskEntity {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -4113704202279716348L;
    /**
     * <p>
     * Represents the permitted users.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<UserDTO> permittedUsers;
    /**
     * <p>
     * Represents the project id.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long projectId;
    /**
     * <p>
     * Represents the number of completed tasks.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private int numberOfCompletedTasks;
    /**
     * <p>
     * Represents the number of all tasks.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private int numberOfAllTasks;
    /**
     * <p>
     * Represents the tasks.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<Task> tasks;
    /**
     * <p>
     * Represents the flag indicating whether the task list is active.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private boolean isActive;
    /**
     * <p>
     * Represents the flag indicating whether the task list is the default "Project Task List" of this project.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private boolean isDefault;

    /**
     * Creates an instance of TaskList.
     */
    public TaskList() {
        // Empty
    }

    /**
     * Gets the permitted users.
     *
     * @return the permitted users.
     */
    public List<UserDTO> getPermittedUsers() {
        return permittedUsers;
    }

    /**
     * Sets the permitted users.
     *
     * @param permittedUsers
     *            the permitted users.
     */
    public void setPermittedUsers(List<UserDTO> permittedUsers) {
        this.permittedUsers = permittedUsers;
    }

    /**
     * Gets the project id.
     *
     * @return the project id.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId
     *            the project id.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the number of completed tasks.
     *
     * @return the number of completed tasks.
     */
    public int getNumberOfCompletedTasks() {
        return numberOfCompletedTasks;
    }

    /**
     * Sets the number of completed tasks.
     *
     * @param numberOfCompletedTasks
     *            the number of completed tasks.
     */
    public void setNumberOfCompletedTasks(int numberOfCompletedTasks) {
        this.numberOfCompletedTasks = numberOfCompletedTasks;
    }

    /**
     * Gets the number of all tasks.
     *
     * @return the number of all tasks.
     */
    public int getNumberOfAllTasks() {
        return numberOfAllTasks;
    }

    /**
     * Sets the number of all tasks.
     *
     * @param numberOfAllTasks
     *            the number of all tasks.
     */
    public void setNumberOfAllTasks(int numberOfAllTasks) {
        this.numberOfAllTasks = numberOfAllTasks;
    }

    /**
     * Gets the tasks.
     *
     * @return the tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Gets the completed tasks.
     *
     * @return the completed tasks.
     * @since 1.1
     */
    public List<Task> getCompletedTasks() {
        List<Task> completedTasks = new ArrayList<Task>();

        if(tasks == null || tasks.size() == 0) {
            return completedTasks;
        }

        for(Task t : tasks) {
            if(t.getStatus() == TaskStatus.COMPLETED) {
                completedTasks.add(t);
            }
        }

        return completedTasks;
    }

    /**
     * Gets the active tasks.
     *
     * @return the active tasks.
     * @since 1.1
     */
    public List<Task> getActiveTasks() {
        List<Task> activeTasks = new ArrayList<Task>();

        if(tasks == null || tasks.size() == 0) {
            return activeTasks;
        }

        for(Task t : tasks) {
            if(t.getStatus() != TaskStatus.COMPLETED) {
                activeTasks.add(t);
            }
        }

        return activeTasks;
    }

    /**
     * Sets the tasks.
     *
     * @param tasks
     *            the tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets the flag indicating whether the task list is active.
     *
     * @return the flag indicating whether the task list is active.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the flag indicating whether the task list is active.
     *
     * @param isActive
     *            the flag indicating whether the task list is active.
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the flag indicating whether the task list is the default "Project Task List" of this project.
     *
     * @return the flag indicating whether the task list is the default "Project Task List" of this project.
     */
    public boolean isDefault() {
        return isDefault;
    }

    /**
     * Sets the flag indicating whether the task list is the default "Project Task List" of this project.
     *
     * @param isDefault
     *            the flag indicating whether the task list is the default "Project Task List" of this project.
     */
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}