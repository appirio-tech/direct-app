/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.task;

import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.TaskListService;
import com.topcoder.direct.services.project.task.TaskService;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

/**
 * <p>
 * Base Action for all the tasks actions.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly TC - Cockpit Tasks Management Services Setup and Quick Add Task)
 */
public abstract class BaseTaskAction extends BaseDirectStrutsAction {

    /**
     * The task notification service.
     */
    private NotificationService taskNotificationService;

    /**
     * The task service.
     */
    private TaskService taskService;

    /**
     * The task list service.
     */
    private TaskListService taskListService;

    /**
     * Gets the task notification service.
     *
     * @return the task notification service.
     */
    public NotificationService getTaskNotificationService() {
        return taskNotificationService;
    }

    /**
     * Sets the task notification service.
     *
     * @param taskNotificationService the task notification service.
     */
    public void setTaskNotificationService(NotificationService taskNotificationService) {
        this.taskNotificationService = taskNotificationService;
    }

    /**
     * Gets the task service.
     *
     * @return the task service.
     */
    public TaskService getTaskService() {
        return taskService;
    }

    /**
     * Sets the task service.
     *
     * @param taskService the task service.
     */
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Gets the task list service.
     *
     * @return the task list service.
     */
    public TaskListService getTaskListService() {
        return taskListService;
    }

    /**
     * Sets the task list service.
     *
     * @param taskListService the task list service.
     */
    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }
}
