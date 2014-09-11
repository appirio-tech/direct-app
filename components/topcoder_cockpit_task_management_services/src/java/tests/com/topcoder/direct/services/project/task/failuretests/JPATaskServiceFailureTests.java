/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.failuretests;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.direct.services.project.task.EntityNotFoundException;
import com.topcoder.direct.services.project.task.NotificationException;
import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.PermissionException;
import com.topcoder.direct.services.project.task.PersistenceException;
import com.topcoder.direct.services.project.task.TaskListService;
import com.topcoder.direct.services.project.task.impl.JPATaskService;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskAttachment;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for JPATaskService.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class JPATaskServiceFailureTests extends TestCase {
    /**
     * <p>
     * The JPATaskService instance for testing.
     * </p>
     */
    private JPATaskService instance;

    /**
     * <p>
     * The TaskList instance for testing.
     * </p>
     */
    private TaskList taskList;

    /**
     * <p>
     * The NotificationService instance for testing.
     * </p>
     */
    private NotificationService notificationService;

    /**
     * <p>
     * The UserService instance for testing.
     * </p>
     */
    private UserService userService;

    /**
     * <p>
     * The EntityManager instance for testing.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * The TaskListService instance for testing.
     * </p>
     */
    private TaskListService taskListService;

    /**
     * <p>
     * The TaskList list for testing.
     * </p>
     */
    private List<TaskList> taskLists;

    /**
     * <p>
     * The UserDTO list for testing.
     * </p>
     */
    private List<UserDTO> assignees;

    /**
     * <p>
     * The Task list for testing.
     * </p>
     */
    private List<Task> tasks;

    /**
     * <p>
     * The Task instance for testing.
     * </p>
     */
    private Task task;

    /**
     * <p>
     * The TaskAttachment instance for testing.
     * </p>
     */
    private TaskAttachment attachment;

    /**
     * <p>
     * The InputStream instance for testing.
     * </p>
     */
    private InputStream inputStream;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new JPATaskService();

        userService = mock(UserService.class);
        entityManager = mock(EntityManager.class);
        taskListService = mock(TaskListService.class);
        notificationService = mock(NotificationService.class);

        instance.setUserService(userService);
        instance.setEntityManager(entityManager);
        instance.setTaskListService(taskListService);
        instance.setAttachmentDirectory("test_files");
        instance.setNotificationService(notificationService);

        taskList = new TaskList();
        taskList.setName("TC");
        taskList.setId(8);

        task = new Task();
        assignees = new ArrayList<UserDTO>();
        task.setAssignees(assignees);
        task.setCreatedDate(new Date());
        task.setName("name");
        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.COMPLETED);
        task.setTaskListId(1);
        task.setId(1);

        tasks = new ArrayList<Task>();
        tasks.add(task);

        taskList.setTasks(tasks);
        taskList.setCreatedDate(new Date());

        taskLists = new ArrayList<TaskList>();
        taskLists.add(taskList);

        attachment = new TaskAttachment();
        attachment.setFileName("test");
        attachment.setMimeType("text");
        attachment.setTaskId(1);

        inputStream = new ByteArrayInputStream("test".getBytes());
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(JPATaskServiceFailureTests.class);
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByAssignee(List) for failure.
     * It tests the case that when taskLists is null and expects IllegalArgumentException.
     * </p>
     */
    public void testGroupTasksByAssignee_NullTaskLists() {
        try {
            instance.groupTasksByAssignee(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByAssignee(List) for failure.
     * It tests the case that when taskLists contains null element and expects IllegalArgumentException.
     * </p>
     */
    public void testGroupTasksByAssignee_NullInTaskLists() {
        taskLists.add(null);
        try {
            instance.groupTasksByAssignee(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByAssignee(List) for failure.
     * It tests the case that when getTasks() is null and expects IllegalArgumentException.
     * </p>
     */
    public void testGroupTasksByAssignee_NullTasks() {
        taskList.setTasks(null);
        taskLists.add(taskList);
        try {
            instance.groupTasksByAssignee(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByAssignee(List) for failure.
     * It tests the case that when getTasks() contains null and expects IllegalArgumentException.
     * </p>
     */
    public void testGroupTasksByAssignee_NullInTasks() {
        tasks.add(null);
        taskLists.add(taskList);
        try {
            instance.groupTasksByAssignee(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByAssignee(List) for failure.
     * It tests the case that when getAssignees() is null and expects IllegalArgumentException.
     * </p>
     */
    public void testGroupTasksByAssignee_NullAssignees() {
        task.setAssignees(null);
        taskLists.add(taskList);
        try {
            instance.groupTasksByAssignee(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByAssignee(List) for failure.
     * It tests the case that when getAssignees() contains null and expects IllegalArgumentException.
     * </p>
     */
    public void testGroupTasksByAssignee_NullInAssignees() {
        assignees.add(null);
        taskLists.add(taskList);
        try {
            instance.groupTasksByAssignee(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByAssignee(List) for failure.
     * It tests the case that when getCreatedDate() is null and expects IllegalArgumentException.
     * </p>
     */
    public void testGroupTasksByAssignee_NullCreatedDate() {
        task.setCreatedDate(null);
        taskLists.add(taskList);
        try {
            instance.groupTasksByAssignee(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTask(long,Task) for failure.
     * It tests the case that when task is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTask_NullTask() throws Exception {
        try {
            instance.addTask(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTask(long,Task) for failure.
     * It tests the case that when task name is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTask_NullTaskName() throws Exception {
        task.setName(null);
        try {
            instance.addTask(1, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTask(long,Task) for failure.
     * It tests the case that when task name is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTask_EmptyTaskName() throws Exception {
        task.setName(" ");
        try {
            instance.addTask(1, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTask(long,Task) for failure.
     * It tests the case that when task Status is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTask_NullTaskStatus() throws Exception {
        task.setStatus(null);
        try {
            instance.addTask(1, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTask(long,Task) for failure.
     * It tests the case that when task Priority is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTask_NullTaskPriority() throws Exception {
        task.setPriority(null);
        try {
            instance.addTask(1, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTask(long,Task) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTask_EntityNotFoundException() throws Exception {
        try {
            instance.addTask(1, task);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTask(long,Task) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTask_PermissionException() throws Exception {
        when(taskListService.getTaskList(1, 1)).thenReturn(taskList);
        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.addTask(1, task);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTask(long,Task) for failure.
     * Expects NotificationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTask_NotificationException() throws Exception {
        when(taskListService.getTaskList(1, 1)).thenReturn(taskList);
        doThrow(new NotificationException("error")).when(notificationService).notifyTaskCreation(1, task);
        try {
            instance.addTask(1, task);
            fail("NotificationException expected.");
        } catch (NotificationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#updateTask(long,Task) for failure.
     * It tests the case that when task is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateTask_NullTask() throws Exception {
        try {
            instance.updateTask(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#updateTask(long,Task) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateTask_EntityNotFoundException() throws Exception {
        try {
            instance.updateTask(1, task);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#updateTask(long,Task) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateTask_PermissionException() throws Exception {
        when(taskListService.getTaskList(1, 1)).thenReturn(taskList);
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.updateTask(1, task);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#updateTask(long,Task) for failure.
     * Expects NotificationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateTask_NotificationException() throws Exception {
        when(taskListService.getTaskList(1, 1)).thenReturn(taskList);
        Task oldTask = new Task();
        oldTask.setStatus(TaskStatus.IN_PROGRESS);
        when(entityManager.find(Task.class, 1L)).thenReturn(oldTask);
        doThrow(new NotificationException("error")).when(notificationService).notifyTaskStatusChange(1,
            TaskStatus.IN_PROGRESS, task);
        try {
            instance.updateTask(1, task);
            fail("NotificationException expected.");
        } catch (NotificationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#getTask(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTask_PermissionException() throws Exception {
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(taskListService.getTaskList(1, 1)).thenThrow(new PermissionException("error"));
        try {
            instance.getTask(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#getTask(long,long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTask_PersistenceException() throws Exception {
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(taskListService.getTaskList(1, 1)).thenThrow(new PersistenceException("error"));
        try {
            instance.getTask(1, 1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#deleteTask(long,long) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteTask_EntityNotFoundException() throws Exception {
        try {
            instance.deleteTask(1, 1);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#deleteTask(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteTask_PermissionException() throws Exception {
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(taskListService.getTaskList(1, 1)).thenThrow(new PermissionException("error"));
        try {
            instance.deleteTask(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#deleteTask(long,long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteTask_PersistenceException() throws Exception {
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(taskListService.getTaskList(1, 1)).thenThrow(new PersistenceException("error"));
        try {
            instance.deleteTask(1, 1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTaskAttachment(long,TaskAttachment,InputStream) for failure.
     * It tests the case that when attachment is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskAttachment_NullAttachment() throws Exception {
        try {
            instance.addTaskAttachment(1, null, inputStream);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTaskAttachment(long,TaskAttachment,InputStream) for failure.
     * It tests the case that when getFileName() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskAttachment_NullFileName() throws Exception {
        attachment.setFileName(null);
        try {
            instance.addTaskAttachment(1, attachment, inputStream);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTaskAttachment(long,TaskAttachment,InputStream) for failure.
     * It tests the case that when getFileName() is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskAttachment_EmptyFileName() throws Exception {
        attachment.setFileName(" ");
        try {
            instance.addTaskAttachment(1, attachment, inputStream);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTaskAttachment(long,TaskAttachment,InputStream) for failure.
     * It tests the case that when getMimeType() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskAttachment_NullMimeType() throws Exception {
        attachment.setMimeType(null);
        try {
            instance.addTaskAttachment(1, attachment, inputStream);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTaskAttachment(long,TaskAttachment,InputStream) for failure.
     * It tests the case that when getMimeType() is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskAttachment_EmptyMimeType() throws Exception {
        attachment.setMimeType(" ");
        try {
            instance.addTaskAttachment(1, attachment, inputStream);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTaskAttachment(long,TaskAttachment,InputStream) for failure.
     * It tests the case that when inputStream is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskAttachment_NullInputStream() throws Exception {
        try {
            instance.addTaskAttachment(1, attachment, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTaskAttachment(long,TaskAttachment,InputStream) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskAttachment_EntityNotFoundException() throws Exception {
        try {
            instance.addTaskAttachment(1, attachment, inputStream);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#addTaskAttachment(long,TaskAttachment,InputStream) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskAttachment_PermissionException() throws Exception {
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(taskListService.getTaskList(1, 1)).thenThrow(new PermissionException("error"));
        try {
            instance.addTaskAttachment(1, attachment, inputStream);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#deleteTaskAttachment(long,long) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteTaskAttachment_EntityNotFoundException() throws Exception {
        try {
            instance.deleteTaskAttachment(1, 1);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#deleteTaskAttachment(long,long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteTaskAttachment_PersistenceException() throws Exception {
        when(entityManager.find(TaskAttachment.class, 1L)).thenReturn(attachment);
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(taskListService.getTaskList(1, 1)).thenThrow(new PersistenceException("error"));
        try {
            instance.deleteTaskAttachment(1, 1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#deleteTaskAttachment(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteTaskAttachment_PermissionException() throws Exception {
        when(entityManager.find(TaskAttachment.class, 1L)).thenReturn(attachment);
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(taskListService.getTaskList(1, 1)).thenThrow(new PermissionException("error"));
        try {
            instance.deleteTaskAttachment(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#getTaskAttachmentContent(long,long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskAttachmentContent_PersistenceException() throws Exception {
        when(entityManager.find(TaskAttachment.class, 1L)).thenReturn(attachment);
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(taskListService.getTaskList(1, 1)).thenThrow(new PersistenceException("error"));
        try {
            instance.getTaskAttachmentContent(1, 1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#getTaskAttachmentContent(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskAttachmentContent_PermissionException() throws Exception {
        when(entityManager.find(TaskAttachment.class, 1L)).thenReturn(attachment);
        when(entityManager.find(Task.class, 1L)).thenReturn(task);
        when(taskListService.getTaskList(1, 1)).thenThrow(new PermissionException("error"));
        try {
            instance.getTaskAttachmentContent(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByPriority(List) for failure.
     * It tests the case that when taskLists is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGroupTasksByPriority_NullTaskLists() throws Exception {
        try {
            instance.groupTasksByPriority(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByPriority(List) for failure.
     * It tests the case that when getPriority is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGroupTasksByPriority_NullPriority() throws Exception {
        task.setPriority(null);
        taskLists.add(taskList);
        try {
            instance.groupTasksByPriority(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByPriority(List) for failure.
     * It tests the case that when taskLists contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGroupTasksByPriority_NullInTaskLists() throws Exception {
        taskLists.add(null);
        try {
            instance.groupTasksByPriority(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByDueDate(List) for failure.
     * It tests the case that when taskLists is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGroupTasksByDueDate_NullTaskLists() throws Exception {
        try {
            instance.groupTasksByDueDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByDueDate(List) for failure.
     * It tests the case that when taskLists contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGroupTasksByDueDate_NullInTaskLists() throws Exception {
        taskLists.add(null);
        try {
            instance.groupTasksByDueDate(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByStartDate(List) for failure.
     * It tests the case that when taskLists is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGroupTasksByStartDate_NullTaskLists() throws Exception {
        try {
            instance.groupTasksByStartDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByStartDate(List) for failure.
     * It tests the case that when taskLists contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGroupTasksByStartDate_NullInTaskLists() throws Exception {
        taskLists.add(null);
        try {
            instance.groupTasksByStartDate(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByStatus(List) for failure.
     * It tests the case that when taskLists is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGroupTasksByStatus_NullTaskLists() throws Exception {
        try {
            instance.groupTasksByStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#groupTasksByStatus(List) for failure.
     * It tests the case that when taskLists contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGroupTasksByStatus_NullInTaskLists() throws Exception {
        taskLists.add(null);
        try {
            instance.groupTasksByStatus(taskLists);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#getNumberOfCompletedTasks(long,long) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetNumberOfCompletedTasks_EntityNotFoundException() throws Exception {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new BigDecimal(0));
        try {
            instance.getNumberOfCompletedTasks(1, 1);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#getNumberOfCompletedTasks(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetNumberOfCompletedTasks_PermissionException() throws Exception {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new BigDecimal(1));

        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.getNumberOfCompletedTasks(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#getNumberOfAllTasks(long,long) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetNumberOfAllTasks_EntityNotFoundException() throws Exception {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new BigDecimal(0));
        try {
            instance.getNumberOfAllTasks(1, 1);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskService#getNumberOfAllTasks(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetNumberOfAllTasks_PermissionException() throws Exception {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new BigDecimal(1));

        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.getNumberOfAllTasks(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

}