/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskAttachment;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;

/**
 * <p>
 * This is the service contract for managing tasks in cockpit.
 * </p>
 * <p>
 * <b>Thread-Safety:</b>Implementations should be thread-safe, but the use of the Spring IoC container to
 * inject configurations will not be treated as a factor in thread-safety. Also, it can be assumed that method
 * inputs will not be used concurrently.
 * </p>
 * @author Mozgastik, TCSDEVELOPER
 * @version 1.0
 */
public interface TaskService {

    /**
     * <p>
     * Adds a new Task, and return the added Task entity.
     * </p>
     * <p>
     * Notifications will be sent to all assignees of the task.
     * </p>
     * @param userId the ID of user performing this action.
     * @param task the task to add.
     * @return the added task. It will never be null.
     * @throws IllegalArgumentException if task is null or getName() null/empty or getStatus() null or
     *             getPriority() null.
     * @throws EntityNotFoundException if task list referred by task.getTaskListId() doesn't exist in
     *             persistence.
     * @throws PermissionException if specified user doesn't exist.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws NotificationException if error occurs with sending notifications.
     * @throws TaskManagementException if any other error occurs.
     */
    public Task addTask(long userId, Task task)
        throws TaskManagementException;

    /**
     * <p>
     * Updates an existing Task.
     * </p>
     * <p>
     * If task status will be changed, then notifications will be sent to all assignees of the task as well as
     * the creator of the task.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param task the task to update.
     *
     * @throws IllegalArgumentException If task is null or getName() null/empty or getStatus() null or
     *             getPriority() null.
     * @throws EntityNotFoundException If specified task or task list referred by task.getTaskListId()
     *             doesn't exist in persistence.
     * @throws PermissionException If specified user doesn't exist or isn't permitted to perform the
     *             requested action.
     * @throws PersistenceException If any persistence related error occurs.
     * @throws NotificationException If error occurs with sending notifications.
     * @throws TaskManagementException if any other error occurs.
     */
    public void updateTask(long userId, Task task)
        throws TaskManagementException;

    /**
     * <p>
     * Retrieves an existing Task.
     * </p>
     * @param userId the ID of user performing this action.
     * @param taskId the id of the task to retrieve.
     *
     * @return retrieved entity. It will return null if not found or the task list of the task does not exist.
     *
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the requested
     *             action.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws TaskManagementException if any other error occurs.
     */
    public Task getTask(long userId, long taskId)
        throws TaskManagementException;

    /**
     * <p>
     * Deletes an existing Task.
     * </p>
     * @param userId the ID of user performing this action.
     * @param taskId the Task ID.
     *
     * @throws PermissionException - If specified user doesn't exist or isn't permitted to perform the
     *             requested action.
     * @throws EntityNotFoundException - If specified task doesn't exist in persistence.
     * @throws PersistenceException - If any persistence related error occurs.
     * @throws TaskManagementException if any other error occurs.
     */
    public void deleteTask(long userId, long taskId)
        throws TaskManagementException;

    /**
     * <p>
     * Add an attachment to a task, and return the added TaskAttachment entity.
     * </p>
     * <p>
     * The attachment content will be passed to the method as an InputStream.
     * </p>
     * <p>
     * This method will not close the passed in InputStream.
     * </p>
     * @param userId the ID of user performing this action.
     * @param attachment the task attachment entity.
     * @param inputStream the task attachment content.
     * @return the added task attachment. Not null.
     * @throws IllegalArgumentException - If any argument is null or attachment has getFileName() null/empty or
     *             getMimeType() null/empty.
     * @throws EntityNotFoundException - If task (referred by attachment.getTaskId()) doesn't exist in
     *             persistence.
     * @throws PermissionException - If specified user doesn't exist.
     * @throws PersistenceException - If any persistence related error occurs (including file I/O error,
     *             because file system also plays a role of persistence).
     * @throws TaskManagementException if any other error occurs.
     */
    public TaskAttachment addTaskAttachment(long userId, TaskAttachment attachment, InputStream inputStream)
        throws TaskManagementException;

    /**
     * <p>
     * Deletes an existing TaskAttachment.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param attachmentId the ask attachment ID to delete.
     *
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the requested
     *             action.
     * @throws EntityNotFoundException if specified task attachment doesn't exist in persistence.
     * @throws PersistenceException if any persistence related error occurs (including file I/O error, because
     *             file system also plays a role of persistence).
     * @throws TaskManagementException if any other error occurs.
     */
    public void deleteTaskAttachment(long userId, long attachmentId)
        throws TaskManagementException;

    /**
     * <p>
     * Returns the file content of a TaskAttachment as an InputStream.
     * </p>
     * @param userId the ID of user performing this action.
     * @param attachmentId the task attachment ID.
     *
     * @return retrieved task attachment content. It will return null if not found.
     *
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the requested
     *             action.
     * @throws PersistenceException if any persistence related error occurs (including file I/O error, because
     *             file system also plays a role of persistence).
     * @throws TaskManagementException if any other error occurs.
     */
    public InputStream getTaskAttachmentContent(long userId, long attachmentId)
        throws TaskManagementException;


    /**
     * <p>
     * Groups the Tasks of given TaskList's by task priority and return the result.
     * </p>
     *
     * @param taskLists the Task lists to group by priority.
     *
     * @return the result of grouping. Map key is task priority value, map value is a list of tasks with
     *         corresponding task priority. It is not null, and contains no null keys/values nor empty values.
     *
     * @throws IllegalArgumentException if argument is null or contains an element which is null or its
     *             getTasks() is null or contains an element which is null or its getPriority() is null or its
     *             getCreatedDate is null.
     */
    public SortedMap<TaskPriority, List<Task>> groupTasksByPriority(List<TaskList> taskLists);

    /**
     * <p>
     * Groups the Tasks of given TaskList's by task assignee and return the result.
     * </p>
     *
     * @param taskLists the task lists to group by assignees.
     *
     * @return result of grouping. Map key is task assignee, map value is a list of tasks with corresponding
     *         assignee. If will not be null, or contains no null keys/values nor empty values.
     *
     * @throws IllegalArgumentException if argument is null or contains an element which is null or its
     *            getTasks() is null or contains an element which is null or its getAssignees() is null or
     *            contains a null element, or the getCreatedDate in the task is null.
     */
    public SortedMap<UserDTO, List<Task>> groupTasksByAssignee(List<TaskList> taskLists);

    /**
     * <p>
     * Groups the Tasks of given TaskList's by task due date and return the result.
     * </p>
     * NOTE, the group by date is truncated to the day precision, i.e,
     * 2013.08.08 08:08:08 -&gt; 2013.08.08
     * @param taskLists the task lists to group by the due date.
     * @return the result of grouping. Map key is task start date value, map value is a list of tasks
     *        with corresponding start date. It will not be null, and contains no null keys/values nor empty
     *        values.
     * @throws IllegalArgumentException if argument is null or contains an element which is null or its
     *             getTasks() is null or contains an element which is null or its getDueDate() is null, or
     *             the getCreatedDate() is null.
     */
    public SortedMap<Date, List<Task>> groupTasksByDueDate(List<TaskList> taskLists);

    /**
     * <p>
     * Groups the Tasks of given TaskList's by task start date and return the result.
     * </p>
     * NOTE, the group by date is truncated to the day precision, i.e,
     * 2013.08.08 08:08:08 -&gt; 2013.08.08
     * @param taskLists the task lists to group by the start date.
     * @return the result of grouping. Map key is task start date value, map value is a list of tasks
     *        with corresponding start date. It will not be null, and contains no null keys/values nor empty
     *        values.
     * @throws IllegalArgumentException if argument is null or contains an element which is null or its
     *             getTasks() is null or contains an element which is null or its getStartDate() is null, or
     *             the getCreatedDate() is null.
     */
    public SortedMap<Date, List<Task>> groupTasksByStartDate(List<TaskList> taskLists);


    /**
     * <p>
     * Groups the Tasks of given TaskList's by task status and return the result.
     * </p>
     * @param taskLists the task lists to group by the status.
     * @return the result of grouping. Map key is task status value, map value is a list of tasks with
     *        corresponding task status. It will not be null, and contains no null keys/values nor empty
     *        values.
     *
     * @throws IllegalArgumentException if argument is null or contains an element which is null or its
     *             getTasks() is null or contains an element which is null or its getStatus() is null, or its
     *             getCreatedDate() is null.
     */
    public SortedMap<TaskStatus, List<Task>> groupTasksByStatus(List<TaskList> taskLists);

    /**
     * <p>
     * Gets the number of completed tasks of a given project.
     * </p>
     * @param userId the ID of user performing this action.
     * @param projectId the Project ID.
     *
     * @return the amount of completed tasks. It will not be negative.
     *
     * @throws EntityNotFoundException - If specified project doesn't exist in persistence.
     * @throws PersistenceException - If any persistence related error occurs.
     * @throws PermissionException if the user is not permitted for the operation.
     * @throws TaskManagementException if any other error occurs.
     */
    public int getNumberOfCompletedTasks(long userId, long projectId)
        throws TaskManagementException;

    /**
     * <p>
     * Gets the number of all tasks of a given project.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param projectId the project ID.
     *
     * @return the amount of tasks. Not negative.
     *
     * @throws EntityNotFoundException if specified project doesn't exist in persistence.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws PermissionException if the user is not permitted for the operation.
     * @throws TaskManagementException if any other error occurs.
     */
    public int getNumberOfAllTasks(long userId, long projectId)
        throws TaskManagementException;
}

