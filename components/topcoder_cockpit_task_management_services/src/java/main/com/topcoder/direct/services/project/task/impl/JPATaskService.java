/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import com.topcoder.direct.services.project.task.EntityNotFoundException;
import com.topcoder.direct.services.project.task.NotificationException;
import com.topcoder.direct.services.project.task.PermissionException;
import com.topcoder.direct.services.project.task.PersistenceException;
import com.topcoder.direct.services.project.task.TaskListService;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.direct.services.project.task.TaskManagementException;
import com.topcoder.direct.services.project.task.TaskService;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskAttachment;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.util.log.Log;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>
 * This is the JPA based implementation of service for managing task in Cockpit.
 * </p>
 * <p>
 * It extends BaseJPAService to utilize the common properties.
 * </p>
 * <p>
 * It uses JPA EntityManager (configured into base class) to perform the operations with persistence.
 * </p>
 * <p>
 * It uses NotificationService to send notifications.
 * </p>
 * <p>
 * It uses TaskListService to perform operations with task lists and check permissions.
 * </p>
 * <p>
 * Sample Configuration: (all the fields are required except the log).
 *
 * <pre>
 *     &lt;bean id=&quot;taskService&quot;
 *     class=&quot;com.topcoder.direct.services.project.task.impl.JPATaskService&quot;&gt;
 *     &lt;property name=&quot;log&quot; ref=&quot;logBean&quot; /&gt;
 *     &lt;property name=&quot;userService&quot; ref=&quot;mockUserService&quot; /&gt;
 *     &lt;property name=&quot;notificationService&quot; ref=&quot;notificationService&quot;/&gt;
 *     &lt;property name=&quot;taskListService&quot; ref=&quot;taskListService&quot; /&gt;
 *     &lt;property name=&quot;attachmentDirectory&quot; value=&quot;test_files/attachments/&quot; /&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Sample API Usage:
 * taskService.addTask(userId, task);
 * other APIs are mainly the same.
 * </p>
 * <p>
 * <b>Thread-Safety:</b>This class is mutable, but can be used thread safely under following conditions:
 * setters should not be called after initialization and method arguments will not be used concurrently.
 * </p>
 *
 * <p>
 *  Version 1.1 (Release Assembly - TC Cockpit Tasks Management Release 2)
 *  <ul>
 *      <li>Updates method {@link #updateTask(long, com.topcoder.direct.services.project.task.model.Task)} to reactive task is
 *      any task is mark as incomplete</li>
 *  </ul>
 * </p>
 *
 * <p>
 *  Version 1.2 (TC - Cockpit Tasks Management Assembly 3)
 *  <ul>
 *      <li>Updates the method {@link #groupTasksByDueDate(java.util.List)} to fix the checking on due date is null</li>
 *  </ul>
 * </p>
 *
 * @author Mozgastik, GreatKevin
 * @version 1.2
 */
public class JPATaskService extends BaseJPAService implements TaskService {

    /**
     * <p>
     * Represents the I/O buffer size for reading the attachment.
     * </p>
     */
    private static final int IO_BUFFER_SIZE = 4096;

    /**
     * <p>
     * Represents the query statement to get the completed tasks number.
     * </p>
     */
    private static final String NUMBER_OF_COMPLETED_TASKS_QUERY =
        "SELECT COUNT(DISTINCT t) FROM Task t WHERE t.status = ?1 AND t.taskListId IN "
            + "(SELECT DISTINCT tl.id FROM TaskList tl LEFT JOIN tl.permittedUsers pu "
            + "WHERE tl.projectId = ?2 AND (pu.userId IS NULL OR pu.userId = ?3 OR tl.createdBy = ?4))";

    /**
     * <p>
     * Represents the query statement to get the number of all the tasks corresponding to the user.
     * </p>
     */
    private static final String NUMBER_OF_ALL_TASKS_QUERY = "SELECT COUNT(DISTINCT t) FROM Task t "
        + "WHERE t.taskListId IN "
        + "(SELECT DISTINCT (tl.id) FROM TaskList tl LEFT JOIN tl.permittedUsers pu "
        + "WHERE tl.projectId = ?1 AND (pu.userId IS NULL OR pu.userId = ?2 OR tl.createdBy = ?3))";

    /**
     * <p>
     * Represents the name of the class for logging.
     * </p>
     */
    private static final String CLASS_NAME = JPATaskService.class.getName();

    /**
     * <p>
     * Represents the path of directory where the task attachment files will be stored.
     * </p>
     * <p>
     * It's used by methods which work with task attachments.
     * </p>
     * <p>
     * It's mutable, has setter for injection.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null/empty.
     * </p>
     */
    private String attachmentDirectory;

    /**
     * <p>
     * Represents the service for managing task lists.
     * </p>
     * <p>
     * It's used by all business methods to obtain task lists and check permissions.
     * </p>
     * <p>
     * It's mutable, has setter for injection.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null.
     * </p>
     */
    private TaskListService taskListService;

    /**
     * <p>
     * Creates the instance of JPATaskService.
     * </p>
     * <p>
     * This is the default constructor of JPATaskService.
     * </p>
     */
    public JPATaskService() {
        // does nothing
    }

    /**
     * <p>
     * Validates configuration parameters.
     * </p>
     * Note, in this class: the required fields are:
     * taskListService(not null), and the attachmentDirectory is not null or empty.
     * and the userService, notificationService should also required for super-class.
     * @throws TaskManagementConfigurationException if any configuration parameter has invalid value.
     */
    @Override
    @PostConstruct
    public void checkInitialization() {
        super.checkInitialization();
        ServiceHelper.checkState(taskListService == null, "The taskListService is not properly injected.");
        ServiceHelper.checkState(attachmentDirectory == null || attachmentDirectory.trim().length() == 0,
            "The attachmentDirectory cannot be null or empty.");
    }

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
        throws TaskManagementException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#addTask(long userId, Task task)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName,
            new String[] {"userId", "task"}, new Object[] {userId, task});

        // validate the values in the task, IllegalArgumentException should be thrown if invalid,
        // or throw the EntityNotFoundException if the taskListId does not exist for task list,
        // or throw the PermissionException if the user is not permitted for the operation
        validateTask(log, methodName, userId, task);

        // audit and persist the task
        String callerHandle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);
        task.setCreatedBy(callerHandle);
        task.setCreatedDate(new Date());
        task.setLastModifiedBy(null);
        task.setLastModifiedDate(null);

        // persist to the database
        getEntityManager().persist(task);
        ServiceHelper.flush(log, methodName, getEntityManager());

        // Send notifications
        getNotificationService().notifyTaskCreation(userId, task);

        // log and exit
        return ServiceHelper.logExit(log, methodName, task);
    }

    /**
     * <p>
     * Validates the task.
     * </p>
     * @param log the logger for logging.
     * @param methodName the name of the calling method for logging.
     * @param userId the id of the operation user.
     * @param task the task to be checked.
     * @throws TaskManagementException if failed to get the task list.
     *
     * @throws IllegalArgumentException if the task is null, or the name in the task is null or empty, or the
     *             status or the priority is null.
     * @throws EntityNotFoundException if the task list does not exist.
     */
    private void validateTask(Log log, final String methodName, long userId, Task task)
        throws TaskManagementException {

        // validate the parameters
        checkTaskParameter(log, methodName, task);

        // check permissions
        TaskList taskList = taskListService.getTaskList(userId, task.getTaskListId());
        if (taskList == null) {
            throw ServiceHelper.logException(log, methodName,
                new EntityNotFoundException("The task list for the id:" + task.getTaskListId() + " does not exist."));
        }
    }

    /**
     * <p>
     * Checks if the task is valid.
     * </p>
     * @param log the logger for logging.
     * @param methodName the name of the calling method for logging.
     * @param task the task to check.
     *
     * @throws IllegalArgumentException if the task is null, or the name in the task is null or empty, or the
     *             status or the priority is null.
     */
    private void checkTaskParameter(Log log, final String methodName, Task task) {
        ServiceHelper.checkNull(log, methodName, task, "task");
        ServiceHelper.checkNullOrEmpty(log, methodName, task.getName(), "name in task");
        ServiceHelper.checkNull(log, methodName, task.getStatus(), "status in task");
        ServiceHelper.checkNull(log, methodName, task.getPriority(), "priority in task");
    }

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
     * @throws IllegalArgumentException If task is null or getName() null/empty or getStatus() null or
     *             getPriority() null.
     * @throws EntityNotFoundException If specified task or task list referred by task.getTaskListId()
     *             doesn't exist in persistence.
     * @throws PermissionException If specified user doesn't exist or isn't permitted to perform the
     *             requested action.
     * @throws PersistenceException If any persistence related error occurs.
     * @throws NotificationException If error occurs with sending notifications.
     * @throws TaskManagementException if any other error occurs..
     */
    public void updateTask(long userId, Task task) throws TaskManagementException {

        // prepare for logging
        final String methodName = CLASS_NAME + "#updateTask(long userId, Task task)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "task"}, new Object[] {userId, task});

        // validate the values in the task, IllegalArgumentException should be thrown if invalid,
        // or throw the EntityNotFoundException if the taskListId does not exist for task list,
        // or throw the PermissionException if the user is not permitted for the operation
        validateTask(log, methodName, userId, task);

        // get the existing version of the task from persistence
        Task oldTask = getEntityManager().find(Task.class, task.getId());
        if (oldTask == null) {
            throw ServiceHelper.logException(log, methodName, new EntityNotFoundException(
                "The task is not found for id:" + task.getId()));
        }

        // save the old status (we have to save the status here, otherwise, after merge,
        // the oldTask's status will automatically change.
        TaskStatus oldStatus = oldTask.getStatus();

        // audit and update the task
        String callerHandle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);
        task.setCreatedBy(oldTask.getCreatedBy());
        task.setCreatedDate(oldTask.getCreatedDate());
        task.setLastModifiedBy(callerHandle);
        task.setLastModifiedDate(new Date());

        // merge the task
        getEntityManager().merge(task);

        if(oldStatus == TaskStatus.COMPLETED && task.getStatus() != oldStatus) {
            // new status changed from completed to another status. If task list is
            // archived, we need to reactive the task list
            TaskList taskList = taskListService.getTaskList(userId, task.getTaskListId());
            if(taskList.isActive() == false) {
                taskList.setActive(true);
                // do the update
                getEntityManager().merge(taskList);
            }
        }

        // flush to the data base
        ServiceHelper.flush(log, methodName, getEntityManager());

        // send notifications if necessary
        if (!task.getStatus().equals(oldStatus)) {
            getNotificationService().notifyTaskStatusChange(userId, oldStatus, task);
        }

        // log and exit
        ServiceHelper.logExit(log, methodName);
    }

    /**
     * <p>
     * Retrieves an existing Task.
     * </p>
     * @param userId the ID of user performing this action.
     * @param taskId the id of the task to retrieve.
     *
     * @return retrieved entity. It will return null if not found or the task list of the task does not exist.
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the requested
     *             action.
     * @throws PersistenceException if any persistence related error occurs.
     * @throws TaskManagementException if any other error occurs.
     */
    public Task getTask(long userId, long taskId) throws TaskManagementException {
        // prepare for logging.
        final String methodName = CLASS_NAME + "#getTask(long userId, long taskId)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "taskId"}, new Object[] {userId, taskId});

        Task task = getEntityManager().find(Task.class, taskId);
        if (task != null) {
            // check the permission
            taskListService.getTaskList(userId, task.getTaskListId());
        }

        // log the exit
        return ServiceHelper.logExit(log, methodName, task);
    }

    /**
     * <p>
     * Deletes an existing Task.
     * </p>
     * @param userId the ID of user performing this action.
     * @param taskId the Task ID.
     * @throws PermissionException - If specified user doesn't exist or isn't permitted to perform the
     *             requested action.
     * @throws EntityNotFoundException - If specified task doesn't exist in persistence.
     * @throws PersistenceException - If any persistence related error occurs.
     * @throws TaskManagementException if any other error occurs.
     */
    public void deleteTask(long userId, long taskId) throws TaskManagementException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#deleteTask(long userId, long taskId)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "taskId"}, new Object[] {userId, taskId});
        // the permission will be check here
        Task task = getTask(userId, taskId);
        if (task == null) {
            throw ServiceHelper.logException(
                log, methodName, new EntityNotFoundException("The task is not found:" + taskId));
        }

        // remove the object
        getEntityManager().remove(task);

        // log and exit
        ServiceHelper.logExit(log, methodName);
    }

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
        throws TaskManagementException {
        // prepare for logging
        final String methodName = CLASS_NAME
            + "#addTaskAttachment(long userId, TaskAttachment attachment, InputStream inputStream)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "attachment", "inputStream"},
            new Object[] {userId, attachment, inputStream});

        // validate the parameters
        ServiceHelper.checkNull(log, methodName, attachment, "attachment");
        ServiceHelper.checkNullOrEmpty(log, methodName, attachment.getFileName(), "fileName in attachment");
        ServiceHelper.checkNullOrEmpty(log, methodName, attachment.getMimeType(), "mimeType in attachment");
        ServiceHelper.checkNull(log, methodName, inputStream, "inputStream");

        // check the permission
        Task task = getTask(userId, attachment.getTaskId());
        if (task == null) {
            throw ServiceHelper.logException(log, methodName,
                new EntityNotFoundException("The task " + attachment.getTaskId() + " does not exists."));
        }


        String callerHandle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);
        attachment.setCreatedBy(callerHandle);
        attachment.setCreatedDate(new Date());
        attachment.setLastModifiedBy(null);
        attachment.setLastModifiedDate(null);

        // persist the database
        getEntityManager().persist(attachment);

        ServiceHelper.flush(log, methodName, getEntityManager());

        // update the attachment file
        OutputStream outputStream = null;
        try {
            File attachmentFile = new File(attachmentDirectory, attachment.getId() + "");
            // we must set the append false to make sure the attachment file is fully override
            outputStream = new FileOutputStream(attachmentFile, false);
            byte[] bytes = new byte[IO_BUFFER_SIZE];
            while (true) {
                int len = inputStream.read(bytes);
                if (len < 0) {
                    break;
                }
                outputStream.write(bytes, 0, len);
            }
        } catch (FileNotFoundException e) {
            throw ServiceHelper.logException(log, methodName,
                new PersistenceException(
                    "File not found:" + attachmentDirectory + File.separator + attachment.getId()));
        } catch (IOException e) {
            throw ServiceHelper.logException(log, methodName,
                new PersistenceException("I/O error occurs while reading the stream or writing to file:"
                    + attachmentDirectory + File.separator + attachment.getId()));
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        // log and exit
        return ServiceHelper.logExit(log, methodName, attachment);
    }

    /**
     * <p>
     * Deletes an existing TaskAttachment.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param attachmentId the ask attachment ID to delete.
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the requested
     *             action.
     * @throws EntityNotFoundException if specified task attachment doesn't exist in persistence.
     * @throws PersistenceException if any persistence related error occurs (including file I/O error, because
     *             file system also plays a role of persistence).
     * @throws TaskManagementException if any other error occurs.
     */
    public void deleteTaskAttachment(long userId, long attachmentId) throws TaskManagementException {
     // prepare for logging
        Log log = getLog();
        final String methodName = CLASS_NAME + "#deleteTaskAttachment(long userId, long attachmentId)";
        // log the entrance
        ServiceHelper.logEntrance(log, methodName,
            new String[] {"userId", "attachmentId"}, new Object[] {userId, attachmentId});

        // get the file and delete the entity (this will check the persistence)
        File file = getTaskAttachmentFile(log, methodName, userId, attachmentId, true);
        if (file == null) {
            // does not exist in persistence
            throw ServiceHelper.logException(log, methodName,
                new EntityNotFoundException("The file for attachmentId is not found:" + attachmentId));
        }
        if (!file.exists() || !file.isFile()) {
            throw ServiceHelper.logException(log, methodName,
                new PersistenceException("File not found:" + attachmentDirectory + File.separator + attachmentId));
        }
        // delete the file
        file.delete();

        // log the exit
        ServiceHelper.logExit(log, methodName);
    }

    /**
     * <p>
     * Returns the file content of a TaskAttachment as an InputStream.
     * </p>
     * @param userId the ID of user performing this action.
     * @param attachmentId the task attachment ID.
     *
     * @return retrieved task attachment content. It will return null if not found.
     * @throws PermissionException if specified user doesn't exist or isn't permitted to perform the requested
     *             action.
     * @throws PersistenceException if any persistence related error occurs (including file I/O error, because
     *             file system also plays a role of persistence).
     * @throws TaskManagementException if any other error occurs.
     */
    public InputStream getTaskAttachmentContent(long userId, long attachmentId)
        throws TaskManagementException {
        // prepare for logging
        Log log = getLog();
        final String methodName = CLASS_NAME + "#getTaskAttachmentContent(long userId, long attachmentId)";
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "attachmentId"},
            new Object[] {userId, attachmentId});

        // get the file of the attachment (this will check the persistence)
        File file = getTaskAttachmentFile(log, methodName, userId, attachmentId, false);
        if (file == null) {
            // return null and log it if the attachment is not found
            return ServiceHelper.logExit(log, methodName, null);
        }

        try {
            // create the file stream
            return ServiceHelper.logExit(log, methodName, new FileInputStream(file));
        } catch (FileNotFoundException e) {
            // the file in the I/O system does not exist
            throw ServiceHelper.logException(log, methodName,
                new PersistenceException("File not found:" + attachmentDirectory + File.separator + attachmentId, e));
        }
    }

    /**
     * <p>
     * Gets the file of the attachment, and delete the entity in database if necessary.
     * </p>
     * @param log the logger for logging.
     * @param methodName the name of the method for logging.
     * @param userId the user id of the operation.
     * @param attachmentId the id of the attachment to retrieve.
     * @param shouldDelete if true, the entity will be deleted in the database.
     * @return the file of the attachment, null will return if the attachment is not found.
     * @throws TaskManagementException if failed to get the task.
     */
    private File getTaskAttachmentFile(Log log, final String methodName,
        long userId, long attachmentId, boolean shouldDelete) throws TaskManagementException {

        TaskAttachment taskAttachment = getEntityManager().find(TaskAttachment.class, attachmentId);
        if (taskAttachment == null) {
            // not found
            return null;
        }
        // check the permission
        getTask(userId, taskAttachment.getTaskId());

        if (shouldDelete) {
            // remove from the database
            getEntityManager().remove(taskAttachment);
        }

        // return the file
        return new File(attachmentDirectory, attachmentId + "");
    }

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
    public SortedMap<TaskPriority, List<Task>> groupTasksByPriority(List<TaskList> taskLists) {
        // prepare for logging
        final String methodName = CLASS_NAME + "#groupTasksByPriority(List<TaskList> taskLists)";
        Log log = getLog();
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"taskLists"}, new Object[] {taskLists});

        // validate the parameter
        ServiceHelper.checkNull(log, methodName, taskLists, "taskLists");
        // validate the task lists
        validateTaskLists(log, methodName, taskLists, "priority");

        // create the map to store the result
        SortedMap<TaskPriority, List<Task>> result = new TreeMap<TaskPriority, List<Task>>();
        for (TaskList taskList : taskLists) {
            for (Task task : taskList.getTasks()) {
                List<Task> tasks = result.get(task.getPriority());
                if (tasks == null) {
                    tasks = new ArrayList<Task>();
                    result.put(task.getPriority(), tasks);
                }
                tasks.add(task);
            }
        }

        // sort the result by the create date
        for (TaskPriority priority : result.keySet()) {
            List<Task> tasks = result.get(priority);
            sortTasks(tasks);
        }
        // log and exit
        return ServiceHelper.logExit(log, methodName, result);
    }

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
    public SortedMap<UserDTO, List<Task>> groupTasksByAssignee(List<TaskList> taskLists) {
        // prepare for logging
        final String methodName = CLASS_NAME + "#groupTasksByAssignee(List<TaskList> taskLists)";
        Log log = getLog();
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"taskLists"}, new Object[] {taskLists});

        // validate the parameters
        ServiceHelper.checkNull(log, methodName, taskLists, "taskLists");
        // validate the task lists
        validateTaskLists(log, methodName, taskLists, "assignees");

        // create the map to store the result
        SortedMap<UserDTO, List<Task>> result = new TreeMap<UserDTO, List<Task>>(
            new Comparator<UserDTO>() {
                public int compare(UserDTO user1, UserDTO user2) {
                    if (user1.getUserId() == user2.getUserId()) {
                        return 0;
                    }
                    return user1.getUserId() > user2.getUserId() ? 1 : -1;
                }
            }
        );
        for (TaskList taskList : taskLists) {
            for (Task task : taskList.getTasks()) {
                List<UserDTO> assignees = task.getAssignees();
                for (UserDTO assignee : assignees) {
                    List<Task> tasks = result.get(assignee);
                    if (tasks == null) {
                        tasks = new ArrayList<Task>();
                        result.put(assignee, tasks);
                    }
                    tasks.add(task);
                }
            }
        }

        // sort the result by the create date
        for (UserDTO assignee : result.keySet()) {
            List<Task> tasks = result.get(assignee);
            sortTasks(tasks);
        }

        // log and exit
        return ServiceHelper.logExit(log, methodName, result);
    }

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
    public SortedMap<Date, List<Task>> groupTasksByDueDate(List<TaskList> taskLists) {
        // prepare for logging
        final String methodName = CLASS_NAME + "#groupTasksByDueDate(List<TaskList> taskLists)";
        Log log = getLog();
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"taskLists"}, new Object[] {taskLists});

        // validate the parameters
        ServiceHelper.checkNull(log, methodName, taskLists, "taskLists");
        // validate the task lists
        // validateTaskLists(log, methodName, taskLists, "dueDate");

        // group the result by the due date
        SortedMap<Date, List<Task>> result = groupByDate(taskLists, "dueDate");

        // log and exit
        return ServiceHelper.logExit(log, methodName, result);
    }

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
    public SortedMap<Date, List<Task>> groupTasksByStartDate(List<TaskList> taskLists) {
        // prepare for logging
        final String methodName = CLASS_NAME + "#groupTasksByStartDate(List<TaskList> taskLists)";
        Log log = getLog();
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"taskLists"}, new Object[] {taskLists});
        ServiceHelper.checkNull(log, methodName, taskLists, "taskLists");

        // validate the task lists
        validateTaskLists(log, methodName, taskLists, "startDate");

        // group the result by the start date
        SortedMap<Date, List<Task>> result = groupByDate(taskLists, "startDate");

        // log and exit
        return ServiceHelper.logExit(log, methodName, result);
    }

    /**
     * <p>
     * Group the the taskLists by the startDate or dueDate (given in the propertyName).
     * </p>
     * NOTE, the group by date is truncated to the day precision, i.e,
     * 2013.08.08 08:08:08 -&gt; 2013.08.08
     * @param taskLists the task lists to group by.
     * @param propertyName the name of the field to group by (startDate or dueDate).
     *
     * @return return the result of grouping. Map key is task start date value, map value is a list of tasks
     *        with corresponding start date. It will not be null, and contains no null keys/values nor empty
     *        values.
     */
    private SortedMap<Date, List<Task>> groupByDate(List<TaskList> taskLists, String propertyName) {

        // create the result map
        SortedMap<Date, List<Task>> result = new TreeMap<Date, List<Task>>();
        for (TaskList taskList : taskLists) {
            for (Task task : taskList.getTasks()) {
                Date date = null;
                if (propertyName.equals("startDate")) {
                    date = task.getStartDate();
                } else {
                    date = task.getDueDate();
                }

                if(date == null) {
                    continue;
                }

                // truncate to the day precision, i.e,
                // 2013.08.08 08:08:08 -&gt; 2013.08.08
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                date = calendar.getTime();
                List<Task> tasks = result.get(date);
                if (tasks == null) {
                    tasks = new ArrayList<Task>();
                    result.put(date, tasks);
                }
                tasks.add(task);
            }
        }

        // sort the result by the create date
        for (Date startDate : result.keySet()) {
            List<Task> tasks = result.get(startDate);
            sortTasks(tasks);
        }
        return result;
    }

    /**
     * <p>
     * Sorts the list of tasks by the created date.
     * </p>
     * @param tasks the task list to sort.
     */
    private void sortTasks(List<Task> tasks) {
        Collections.sort(tasks, new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                return task1.getCreatedDate().compareTo(task2.getCreatedDate());
            }
        });
    }

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
    public SortedMap<TaskStatus, List<Task>> groupTasksByStatus(List<TaskList> taskLists) {
        // prepare for logging
        final String methodName = CLASS_NAME + "#groupTasksByStatus(List<TaskList> taskLists)";
        Log log = getLog();
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"taskLists"}, new Object[] {taskLists});
        ServiceHelper.checkNull(log, methodName, taskLists, "taskLists");

        // validate the task lists
        validateTaskLists(log, methodName, taskLists, "status");

        // create the map to store the result
        SortedMap<TaskStatus, List<Task>> result = new TreeMap<TaskStatus, List<Task>>();
        for (TaskList taskList : taskLists) {
            for (Task task : taskList.getTasks()) {
                List<Task> tasks = result.get(task.getStatus());
                if (tasks == null) {
                    tasks = new ArrayList<Task>();
                    result.put(task.getStatus(), tasks);
                }
                tasks.add(task);
            }
        }

        // sort the result by the create date
        for (TaskStatus status : result.keySet()) {
            List<Task> tasks = result.get(status);
            sortTasks(tasks);
        }
        // log and exit
        return ServiceHelper.logExit(log, methodName, result);
    }

    /**
     * <p>
     * Validates the task lists.
     * </p>
     * <p>
     * This is a helper method using in the group by methods.
     * </p>
     * @param log the name of the logger for logging.
     * @param methodName the name of the method.
     * @param taskLists the task lists to validate.
     * @param propertyName the name of the task to validate.
     *
     * @throws IllegalArgumentException if the task list is invalid.
     */
    private void validateTaskLists(Log log, final String methodName, List<TaskList> taskLists, String propertyName) {
        for (TaskList taskList : taskLists) {
            // validate the taskList
            ServiceHelper.checkNull(log, methodName, taskList, "taskList in taskLists");

            // validate the tasks
            List<Task> tasks = taskList.getTasks();
            ServiceHelper.checkNull(log, methodName, tasks, "tasks in taskLists");
            for (Task task : tasks) {
                // validate the task
                ServiceHelper.checkNull(log, methodName, task, "task in tasks");

                // validate the createdDate
                ServiceHelper.checkNull(log, methodName, task.getCreatedDate(), "createDate in task");

                // validate the properties
                if (propertyName.equals("status")) {
                    ServiceHelper.checkNull(log, methodName, task.getStatus(), "status in task");
                } else if (propertyName.equals("startDate")) {
                    ServiceHelper.checkNull(log, methodName, task.getStartDate(), "startDate in task");
                } else if (propertyName.equals("dueDate")) {
                    ServiceHelper.checkNull(log, methodName, task.getDueDate(), "dueDate in task");
                } else if (propertyName.equals("assignees")) {
                    // validate the assignees
                    List<UserDTO> assignees = task.getAssignees();
                    ServiceHelper.checkNull(log, methodName, assignees, "assignees in task");
                    for (UserDTO assignee : assignees) {
                        ServiceHelper.checkNull(log, methodName, assignee, "assignee in assignees");
                    }
                } else if (propertyName.equals("priority")) {
                    ServiceHelper.checkNull(log, methodName, task.getPriority(), "priority in task");
                }
            }
        }
    }

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
     */
    public int getNumberOfCompletedTasks(long userId, long projectId)
        throws PersistenceException, PermissionException {

        // prepare for logging
        final String methodName = CLASS_NAME + "#getNumberOfCompletedTasks(long userId, long projectId)";
        Log log = getLog();
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {
            "userId", "projectId"}, new Object[] {userId, projectId});

        // check the existence of the project
        ServiceHelper.existsProject(log, methodName, getEntityManager(), projectId);

        // perform the operation of retrieving all the number completed tasks
        String handle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);

        // get the number of the completed tasks
        int resultNum = ServiceHelper.queryRecordNum(log, methodName,
            getEntityManager(), NUMBER_OF_COMPLETED_TASKS_QUERY, new Object[] {
                TaskStatus.COMPLETED, projectId, userId, handle});

        // log and return the result
        return ServiceHelper.logExit(log, methodName, resultNum);
    }

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
     */
    public int getNumberOfAllTasks(long userId, long projectId)
        throws PersistenceException, PermissionException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#getNumberOfAllTasks(long userId, long projectId)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {
            "userId", "projectId"}, new Object[] {userId, projectId});

        // check the existence of the project
        ServiceHelper.existsProject(log, methodName, getEntityManager(), projectId);

        // get the user handle
        String handle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);

        // perform the operation of retrieving all the number tasks
        int resultNum = ServiceHelper.queryRecordNum(log, methodName,
            getEntityManager(), NUMBER_OF_ALL_TASKS_QUERY, new Object[] {projectId, userId, handle});

        return ServiceHelper.logExit(log, methodName, resultNum);
    }




    /**
     * <p>
     * Sets the path of directory where the task attachment files will be stored.
     * </p>
     * @param attachmentDirectory
     * the path of directory where the task attachment files will be stored.
     */
    public void setAttachmentDirectory(String attachmentDirectory) {
        this.attachmentDirectory = attachmentDirectory;
    }

    /**
     * <p>
     * Sets the service for managing task lists.
     * </p>
     *
     * @param taskListService the service for managing task lists.
     */
    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

}

