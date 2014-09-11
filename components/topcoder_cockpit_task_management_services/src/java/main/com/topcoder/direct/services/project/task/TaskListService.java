/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

import java.util.List;

import com.topcoder.direct.services.project.task.model.TaskFilter;
import com.topcoder.direct.services.project.task.model.TaskList;

/**
 * <p>
 * This is the service contract for managing task lists in Cockpit.
 * </p>
 * <p>
 * <b>Thread-Safety:</b>Implementations should be thread-safe,
 * but the use of the Spring IoC container to inject configurations
 * will not be treated as a factor in thread-safety. Also, it can be assumed that method inputs will not be
 * used concurrently.
 * </p>
 * @author Mozgastik, TCSDEVELOPER
 * @version 1.0
 */
public interface TaskListService {

    /**
     * <p>
     * Adds a new TaskList, and return the added TaskList entity.
     * </p>
     * @param userId the ID of user performing this action.
     * @param taskList the task list to add.
     * @return the added task list. It will not be null.
     *
     * @throws IllegalArgumentException if taskList is null or has getName() null/empty.
     * @throws PermissionException if specified user doesn't exist.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws TaskManagementException if any other error occurs.
     */
    public TaskList addTaskList(long userId, TaskList taskList)
        throws TaskManagementException;

    /**
     * <p>
     * Updates an existing TaskList.
     * </p>
     * @param userId the operation user id.
     * @param taskList the task list to update.
     * @throws IllegalArgumentException if taskList is null or has getName() null/empty.
     * @throws EntityNotFoundException if specified task list doesn't exist in persistence.
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the
     *             requested action.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws TaskManagementException if any other error occurs.
     */
    public void updateTaskList(long userId, TaskList taskList)
        throws TaskManagementException;

    /**
     * <p>
     * Retrieve an existing TaskList.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param taskListId the ask list ID.
     *
     * @return the retrieved entity, or null if the task list is not found.
     *
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the
     *             requested action.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws TaskManagementException if any other error occurs.
     */
    public TaskList getTaskList(long userId, long taskListId)
        throws TaskManagementException;

    /**
     * <p>
     * Retrieves the default TaskList for a project.
     * </p>
     * NOTE, this method will create a default task list if it does not exists.
     *
     * @param userId the ID of user performing this action.
     * @param projectId the project ID.
     * @return the default task list. Not null (default task list will be added if not exists yet).
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the requested
     *             action.
     * @throws EntityNotFoundException if specified project doesn't exist in persistence.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws TaskManagementException if any other error occurs.
     */
    public TaskList getDefaultTaskList(long userId, long projectId)
        throws TaskManagementException;

    /**
     * <p>
     * Deletes an existing TaskList.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param taskListId the task list ID.
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the requested
     *             action.
     * @throws EntityNotFoundException if specified task list doesn't exist in persistence.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws TaskManagementException if any other error occurs.
     */
    public void deleteTaskList(long userId, long taskListId)
        throws TaskManagementException;

    /**
     * <p>
     * Resolves a TaskList, by marking all Tasks in the list as &quot;Completed&quot; and setting the TaskList
     * as inactive (i.e. archived).
     * </p>
     * @param userId the ID of user performing this action.
     * @param taskListId the task list ID.
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the requested
     *             action.
     * @throws EntityNotFoundException if specified task list doesn't exist in persistence.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws NotificationException if failed to sent the notification emails.
     * @throws TaskManagementException if any other error occurs.
     */
    public void resolveTaskList(long userId, long taskListId)
        throws TaskManagementException;

    /**
     * <p>
     * Retrieves TaskList's according to a TaskFilter (without tasks in each taskList).
     * </p>
     *
     * <p>
     * Note that only TaskList data will be returned, without Task's in TaskList's and only the task lists to
     * which the specified user has access will be returned.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param taskFilter the task filter. If null, all task lists will be returned (without filtering).
     * @return retrieved entities. Empty if none found. Not null.
     *
     * @throws PersistenceException if any persistence related error occurs.
     * @throws PermissionException if the user is not permitted for the query.
     * @throws TaskManagementException if any other error occurs.
     */
    public List<TaskList> getTaskLists(long userId, TaskFilter taskFilter)
        throws TaskManagementException;

    /**
     * <p>
     * Retrieves TaskList's according to a TaskFilter (with tasks in each taskList).
     * </p>
     *
     * <p>
     * Note that only TaskList data will be returned, without Task's in TaskList's and only the task lists to
     * which the specified user has access will be returned.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param taskFilter the task filter. If null, all task lists will be returned (without filtering).
     * @return retrieved entities. Empty if none found. Not null.
     *
     * @throws PersistenceException if any persistence related error occurs.
     * @throws PermissionException if the user is not permitted for the query.
     * @throws TaskManagementException if any other error occurs.
     */
    public List<TaskList> getTaskListsWithTasks(long userId, TaskFilter taskFilter)
        throws TaskManagementException;
}

