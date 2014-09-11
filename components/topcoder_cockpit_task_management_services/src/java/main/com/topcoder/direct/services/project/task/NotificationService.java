/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskStatus;

/**
 * <p>
 * This is the service contract for sending notifications.
 * </p>
 * <p>
 * <b>Thread-Safety:</b>
 * Implementations should be thread-safe, but the use of the Spring IoC container to inject configurations
 * will not be treated as a factor in thread-safety. Also, it can be assumed that method inputs will not be
 * used concurrently.
 * </p>
 * @author Mozgastik, TCSDEVELOPER
 * @version 1.0
 */
public interface NotificationService {

    /**
     * <p>
     * Sends notification about task creation.
     * </p>
     * @param userId the id of user performing this action.
     * @param task the created task.
     *
     * @throws IllegalArgumentException if task is null or task.getName() is null/empty or task.getStatus() is
     *             null or task.getPriority() is null.
     *
     * @throws NotificationException if any error occurs, for example failed to get the user email from the
     *             userService.
     */
    public void notifyTaskCreation(long userId, Task task) throws NotificationException;

    /**
     * <p>
     * Send notification about task status change.
     * </p>
     *
     * <p>
     * When status changes, the email will sent to the corresponding assignees of the task, and the creator of
     * this task.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param oldTask the task status before status change.
     * @param newTask the task after status change.
     * @throws IllegalArgumentException if any of the tasks (oldTask or newTask) is null or have
     *             Task.getName() null/empty or Task.getStatus() null, or the Task.getCreatedBy is null or empty.
     *
     * @throws NotificationException if any error occurs, such as failed to get the user's email addresses.
     */
    public void notifyTaskStatusChange(long userId, TaskStatus oldTask, Task newTask) throws NotificationException;
}

