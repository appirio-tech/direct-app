/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import com.topcoder.direct.services.project.task.EntityNotFoundException;
import com.topcoder.direct.services.project.task.NotificationException;
import com.topcoder.direct.services.project.task.PermissionException;
import com.topcoder.direct.services.project.task.PersistenceException;
import com.topcoder.direct.services.project.task.TaskListService;
import com.topcoder.direct.services.project.task.model.ContestDTO;
import com.topcoder.direct.services.project.task.model.MilestoneDTO;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskFilter;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.util.log.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This is the JPA based implementation of service for managing task lists in cockpit.
 * </p>
 * <p>
 * It extends BaseJPAService to utilize the common properties.
 * </p>
 * <p>
 * It uses JPA EntityManager (configured into base class) to perform the operations with persistence.
 * </p>
 * <p>
 * Sample Configuration: (all the fields are required except the log)
 *
 * <pre>
 *  &lt;bean id=&quot;taskListService&quot;
 *     class=&quot;com.topcoder.direct.services.project.task.impl.JPATaskListService&quot;&gt;
 *     &lt;property name=&quot;log&quot; ref=&quot;logBean&quot; /&gt;
 *     &lt;property name=&quot;userService&quot; ref=&quot;mockUserService&quot; /&gt;
 *     &lt;property name=&quot;notificationService&quot; ref=&quot;notificationService&quot;/&gt;
 *   &lt;/bean&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Sample API Usage:
 * <pre>
 * TaskListService taskListService = (TaskListService) ctx.getBean(&quot;taskListService&quot;);
 * taskListService.addTaskList(123, taskList);
 * other APIs are similar.
 * </pre>
 * </p>
 * <p>
 * <b>Thread-Safety:</b> This class is mutable, but can be used thread safely under following conditions:
 * setters should not be called after initialization and method arguments will not be used concurrently.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Tasks Management Release 2)
 * - Fix the task lists with 0 task issue
 * </p>
 *
 * @author Mozgastik, TCSASSEMBLER
 * @version 1.1
 */
public class JPATaskListService extends BaseJPAService implements TaskListService {

    /**
     * <p>
     * Represents the query statement to retrieve the task list of a given project.
     * </p>
     */
    private static final String QUERY_DEFAULT_TASKLIST_STATEMENT =
        "SELECT tl.id FROM TaskList tl WHERE tl.projectId = ?1 AND tl.default = true";

    /**
     * <p>
     * Represents the query statement to search tasks with the given criteria.
     * </p>
     */
    private static final String QUERY_TASK_LISTS_STATEMENT_PREFIX =
        "SELECT DISTINCT tl FROM TaskList tl LEFT JOIN tl.tasks t LEFT JOIN t.assignees a "
            + "LEFT JOIN t.associatedToProjectMilestones tm LEFT JOIN tl.associatedToProjectMilestones tlm "
            + "LEFT JOIN t.associatedToContests tc LEFT JOIN tl.associatedToContests tlc "
            + "LEFT JOIN tl.permittedUsers pu WHERE 1 = 1 ";

    /**
     * <p>
     * Represents the name of the class for logging.
     * </p>
     */
    private static final String CLASS_NAME = JPATaskListService.class.getName();

    /**
     * <p>
     * Creates the instance of JPATaskListService.
     * </p>
     * <p>
     * This is the default constructor of JPATaskListService.
     * </p>
     */
    public JPATaskListService() {
        // does nothing
    }

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
     */
    public TaskList addTaskList(long userId, TaskList taskList) throws PersistenceException, PermissionException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#addTaskList(long userId, TaskList taskList)";
        Log log = getLog();
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "taskList"},
            new Object[] {userId, taskList});
        // check the parameter
        ServiceHelper.checkNull(log, methodName, taskList, "taskList");
        ServiceHelper.checkNullOrEmpty(log, methodName, taskList.getName(), "name in taskList");

        // get the handle of the operator, if the user does not exist, PermissionException
        // will be thrown
        String callerHandle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);

        taskList.setCreatedBy(callerHandle);
        taskList.setCreatedDate(new Date());
        taskList.setLastModifiedBy(null);
        taskList.setLastModifiedDate(null);

        // persist the object and throw PersistenceException if error occurs
        getEntityManager().persist(taskList);

        // flush to update the id
        ServiceHelper.flush(log, methodName, getEntityManager());

        // log and exit
        return ServiceHelper.logExit(log, methodName, taskList);
    }

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
     */
    public void updateTaskList(long userId, TaskList taskList)
        throws PermissionException, PersistenceException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#updateTaskList(long userId, TaskList taskList)";
        Log log = getLog();
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "taskList"},
            new Object[] {userId, taskList});
        ServiceHelper.checkNull(log, methodName, taskList, "taskList");
        ServiceHelper.checkNullOrEmpty(log, methodName, taskList.getName(), "name in taskList");
        // check permission and entity existence
        TaskList oldTaskList = getTaskList(userId, taskList.getId());
        if (oldTaskList == null) {
            throw ServiceHelper.logException(log, methodName, new EntityNotFoundException(
                "The task list is not found for id:" + taskList.getId()));
        }
        // populate audit fields
        String callerHandle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);
        taskList.setCreatedBy(oldTaskList.getCreatedBy());
        taskList.setCreatedDate(oldTaskList.getCreatedDate());
        taskList.setLastModifiedBy(callerHandle);
        taskList.setLastModifiedDate(new Date());

        // perform action
        getEntityManager().merge(taskList);

        // flush the update
        ServiceHelper.flush(log, methodName, getEntityManager());

        // log and exit
        ServiceHelper.logExit(log, methodName);
    }

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
     */
    public TaskList getTaskList(long userId, long taskListId) throws PermissionException, PersistenceException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#getTaskList(long userId, long taskListId)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {
            "userId", "taskListId"},  new Object[] {userId, taskListId});

        // check the permission
        String callerHandle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);

        // get the task list
        TaskList taskList = getEntityManager().find(TaskList.class, taskListId);
        if (taskList == null) {
            // the task list does not exist, return null
            return ServiceHelper.logExit(log, methodName, taskList);
        }

        // create the user dto
        UserDTO callerUserDTO = new UserDTO();
        callerUserDTO.setUserId(userId);
        callerUserDTO.setHandle(callerHandle);

        // check the permission
        boolean permitted = false;
        if (callerHandle.equals(taskList.getCreatedBy())) {
            // if the user is the creator of this task list, should always permitted
            permitted = true;
        } else if (taskList.getPermittedUsers() == null || taskList.getPermittedUsers().size() == 0) {
            // if the task list is public, everyone is permitted
            permitted = true;
        } else if (taskList.getPermittedUsers().size() > 0) {
            // if the user is in the permitted list, should be permitted
            List<UserDTO> permittedUsers = taskList.getPermittedUsers();
            for(UserDTO ud : permittedUsers) {
                if(ud.getUserId() == userId) {
                    permitted = true;
                    break;
                }
            }
        }

        if (!permitted) {
            throw ServiceHelper.logException(log, methodName,
                new PermissionException("The user is not permitted for the operation:" + userId));
        }

        // fetch the tasks since it is lazy-fetch
        List<Task> tasks = taskList.getTasks();
        if (tasks != null) {
            for (int i = 0; i < tasks.size(); i++) {
                tasks.get(i);
            }
        }

        // log and exit
        return ServiceHelper.logExit(log, methodName, taskList);
    }

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
     */
    public TaskList getDefaultTaskList(long userId, long projectId) throws PermissionException, PersistenceException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#getDefaultTaskList(long userId, long projectId)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {
            "userId", "projectId"}, new Object[] {userId, projectId});

        // check the existence of the project
        ServiceHelper.existsProject(log, methodName, getEntityManager(), projectId);

        // get the default task list id
        Long taskListId = ServiceHelper.querySingleResult(
            log, methodName, getEntityManager(),
                QUERY_DEFAULT_TASKLIST_STATEMENT, new Object[] {projectId}, Long.class);

        TaskList taskList = null;
        if (taskListId == null) {
            // if the default task list not found, create one
            // populate the task list
            taskList = new TaskList();
            taskList.setName("Project Tasks");
            taskList.setNotes("The default Project Tasks List");
            taskList.setAssociatedToProjectMilestones(new ArrayList<MilestoneDTO>());
            taskList.setAssociatedToContests(new ArrayList<ContestDTO>());
            taskList.setProjectId(projectId);
            taskList.setActive(true);
            taskList.setDefault(true);

            String callerHandle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);
            taskList.setCreatedBy(callerHandle);
            taskList.setCreatedDate(new Date());

            taskList = addTaskList(userId, taskList);
        } else {
            // get the full list and check the permission
            taskList = getTaskList(userId, taskListId);
        }

        // log the exit
        return ServiceHelper.logExit(log, methodName, taskList);
    }

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
     */
    public void deleteTaskList(long userId, long taskListId) throws PermissionException, PersistenceException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#deleteTaskList(long userId, long taskListId)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName,
            new String[] {"userId", "taskListId"}, new Object[] {userId, taskListId});

        // get the task list and do the permission check
        TaskList taskList = getTaskList(userId, taskListId);
        if (taskList == null) {
            throw ServiceHelper.logException(log, methodName,
                new EntityNotFoundException("The task list does not exists for:" + taskListId));
        }

        // remove the task list
        getEntityManager().remove(taskList);

        // log and exit
        ServiceHelper.logExit(log, methodName);
    }

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
     */
    public void resolveTaskList(long userId, long taskListId)
        throws PermissionException, PersistenceException, NotificationException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#resolveTaskList(long userId, long taskListId)";
        Log log = getLog();

        // log the entrance
        ServiceHelper.logEntrance(log, methodName,
            new String[] {"userId", "taskListId"}, new Object[] {userId, taskListId});

        // get the task list and this will check the permission
        TaskList taskList = getTaskList(userId, taskListId);
        if (taskList == null) {
            throw ServiceHelper.logException(log, methodName,
                new EntityNotFoundException("The task list for id:" + taskListId + " is not found."));
        }

        String callerHandle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);
        // Save old versions of task statuses, they will be needed for notifications
        List<TaskStatus> oldTaskStatuses = new ArrayList<TaskStatus>();
        if (taskList.getTasks() != null) {
            for (Task task : taskList.getTasks()) {
                // save the old status
                oldTaskStatuses.add(task.getStatus());

                // update the task
                task.setStatus(TaskStatus.COMPLETED);
                task.setLastModifiedBy(callerHandle);
                task.setLastModifiedDate(new Date());

                // update the task
                getEntityManager().merge(task);
            }
        }

        // UPDATE in TC Cockpit Tasks Management Release 2 - do not archive the default task list
        if(!taskList.isDefault()) {
            // set the task list archived
            taskList.setActive(false);
        }

        // update the task list and it will handle the auditing
        updateTaskList(userId, taskList);

        // send notifications if necessary
        for (int i = 0; i < oldTaskStatuses.size(); i++) {
            TaskStatus oldStatus = oldTaskStatuses.get(i);
            Task newTask = taskList.getTasks().get(i);
            TaskStatus newStatus = newTask.getStatus();

            if (!oldStatus.equals(newStatus)) {
                // sent the notification email if status changed
                getNotificationService().notifyTaskStatusChange(userId, oldStatus, newTask);
            }
        }

        // log the exit
        ServiceHelper.logExit(log, methodName);
    }

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
     */
    public List<TaskList> getTaskLists(long userId, TaskFilter taskFilter)
        throws PersistenceException, PermissionException {

        // prepare for logging
        Log log = getLog();
        final String methodName = CLASS_NAME + "#getTaskLists(long userId, TaskFilter taskFilter)";

        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "taskFilter"},
            new Object[] {userId, taskFilter});

        // create the query statement and the parameters
        List<Object> parameters = new ArrayList<Object>();
        StringBuilder statementBuilder = new StringBuilder(QUERY_TASK_LISTS_STATEMENT_PREFIX);
        int paramIndex = 1;

        // filter by the user id
        String callerHandle = ServiceHelper.getUserHandle(log, methodName, getUserService(), userId);
        statementBuilder.append("AND (");
        statementBuilder.append("pu.userId IS NULL OR pu.userId = ?").append(paramIndex++);
        statementBuilder.append(" OR tl.createdBy = ?").append(paramIndex++);
        statementBuilder.append(") ");

        parameters.add(userId);
        parameters.add(callerHandle);

        if (taskFilter != null) {
            // filter by the task name
            String taskName = taskFilter.getName();
            taskName = (taskName == null) ? null : "%" + taskName + "%";
            paramIndex = buildWhereStatement(statementBuilder,
                "t.name LIKE ?#", taskName, parameters, paramIndex);

            // filter by the assignee id
            Long assigneeId = taskFilter.getAssigneeId();
            paramIndex = buildWhereStatement(statementBuilder,
                "a.userId = ?#", assigneeId, parameters, paramIndex);

            // filter by the DueDateFrom
            Date dueDateFrom = taskFilter.getDueDateFrom();
            paramIndex = buildWhereStatement(statementBuilder,
                "t.dueDate >= ?#", dueDateFrom, parameters, paramIndex);

            // filter by the dueDateTo
            Date dueDateTo = taskFilter.getDueDateTo();
            paramIndex = buildWhereStatement(statementBuilder,
                "t.dueDate <= ?#", dueDateTo, parameters, paramIndex);

            // filter by the priorities
            List<TaskPriority> priorities = taskFilter.getPriorities();
            paramIndex = buildWhereStatement(statementBuilder,
                "t.priority IN (?#)", priorities, parameters, paramIndex);

            // filter by the statuses
            List<TaskStatus> statues = taskFilter.getStatuses();
            paramIndex = buildWhereStatement(statementBuilder,
                "t.status IN (?#)", statues, parameters, paramIndex);

            // filter by the milestone
            List<Long> milestones = taskFilter.getAssociatedToProjectMilestoneIds();
            paramIndex = buildWhereStatement(statementBuilder,
                "(tm.milestoneId IN (?#) OR tlm.milestoneId IN (?#))", milestones, parameters, paramIndex);

            // filter by contests
            List<Long> contests = taskFilter.getAssociatedToContestIds();
            paramIndex = buildWhereStatement(statementBuilder,
                "(tc.contestId IN (?#) OR tlc.contestId IN (?#))", contests, parameters, paramIndex);

            // filter by project ids
            List<Long> projects = taskFilter.getProjectIds();
            paramIndex = buildWhereStatement(statementBuilder,
                "tl.projectId IN (?#)", projects, parameters, paramIndex);

            // filter by isActive
            Boolean isActive = taskFilter.getProjectActive();
            paramIndex = buildWhereStatement(statementBuilder,
                "tl.active = ?#", isActive, parameters, paramIndex);
        }

        // query the result with the parameters
        List<TaskList> result = ServiceHelper.queryListResult(log, methodName, getEntityManager(),
            statementBuilder.toString(), parameters.toArray());

        // log and exit, since it is lazy fetch, don't print the tasks in the log
        return ServiceHelper.logExit(log, methodName, result, false);
    }

    /**
     * <p>
     * Builds the where clause for the query statement.
     * </p>
     * @param sb the string builder of the query statement.
     * @param statement the statement to add.
     * @param value the value of the parameter.
     * @param parameters the existing parameters.
     * @param paramIndex the current parameter index.
     * @return the new parameter index.
     */
    private int buildWhereStatement(StringBuilder sb, String statement,
        Object value, List<Object> parameters,  int paramIndex) {
        if (value == null) {
            return paramIndex;
        }
        if (value instanceof List<?>) {
            if (((List<?>) value).size() == 0) {
                // empty list is not allowed by HQL, consider to be null
                return paramIndex;
            }
        }

        // replace the place holder
        statement = statement.replaceAll("\\?#", "?" + paramIndex);
        sb.append(" AND ").append(statement);
        parameters.add(value);
        paramIndex++;

        return paramIndex;
    }


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
     */
    public List<TaskList> getTaskListsWithTasks(long userId, TaskFilter taskFilter)
        throws PersistenceException, PermissionException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#getTaskListsWithTasks(long userId, TaskFilter taskFilter)";
        Log log = getLog();
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "taskFilter"},
            new Object[] {taskFilter, taskFilter});

        // get the tasks
        List<TaskList> taskLists = getTaskLists(userId, taskFilter);

        // get the task for lazy loaded reason
        for (TaskList taskList : taskLists) {
            List<Task> tasks = taskList.getTasks();
            if (tasks != null) {
                // we must iterate the tasks, otherwise we cannot get the tasks
                for (int i = 0; i < tasks.size(); i++) {
                    tasks.get(i);
                }
            }
        }

        // log and exit
        return ServiceHelper.logExit(log, methodName, taskLists);
    }


}

