/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import com.topcoder.direct.services.project.task.EntityNotFoundException;
import com.topcoder.direct.services.project.task.NotificationException;
import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.PermissionException;
import com.topcoder.direct.services.project.task.PersistenceException;
import com.topcoder.direct.services.project.task.TaskListService;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.direct.services.project.task.TaskService;
import com.topcoder.direct.services.project.task.model.ContestDTO;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskAttachment;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.Log;

/**
 * <p>
 * All unit tests for class: <code>JPATaskService</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class JPATaskServiceTests extends BaseUnitTests {

    /**
     * <p>
     * Represents the instance of TaskService for unit tests.
     * </p>
     */
    private TaskService taskService;


    /**
     * <p>
     * Represents the created project for unit tests.
     * </p>
     */
    private ContestDTO contest;

    /**
     * <p>
     * Represents the created user for unit tests.
     * </p>
     */
    private User user;

    /**
     * <p>
     * Represents the created task list for unit tests.
     * </p>
     */
    private TaskList taskList;


    /**
     * <p>
     * Represents the task service for failure tests.
     * </p>
     */
    private JPATaskService beanFailure;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JPATaskServiceTests.class);
    }



    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception to JUnit.
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        ApplicationContext ctx = super.getApplicationContext();
        taskService = (TaskService) ctx.getBean("taskService");

        user = super.createUsers(1).get(0);
        contest = new ContestDTO();
        contest.setContestId(123);
        contest.setContestName("test_contest");
        persist(contest);

        taskList = new TaskList();
        taskList.setActive(true);
        taskList.setCreatedBy(user.getHandle());
        taskList.setCreatedDate(new Date());
        taskList.setProjectId(contest.getContestId());
        taskList.setName("task list name for test");
        // set the permitted users
        UserDTO permittedUser = new UserDTO();
        permittedUser.setHandle(user.getHandle());
        permittedUser.setUserId(user.getUserId());
        List<UserDTO> users = new ArrayList<UserDTO>();
        users.add(permittedUser);
        taskList.setPermittedUsers(users);

        persist(taskList);

        beanFailure = new JPATaskService();
        beanFailure.setEntityManager(super.getEntityManager());
        Log log = (Log) ctx.getBean("logBean");
        beanFailure.setLog(log);
        NotificationService notificationService = (NotificationService) ctx.getBean("notificationService");
        beanFailure.setNotificationService(notificationService);
        UserService userService = (UserService) ctx.getBean("mockUserService");
        beanFailure.setUserService(userService);

        String attacmentDirectory = "test_files" + File.separator + "attachments";
        TaskListService taskListService = (TaskListService) ctx.getBean("taskListService");
        beanFailure.setAttachmentDirectory(attacmentDirectory);
        beanFailure.setTaskListService(taskListService);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#checkInitialization()}.
     * </p>
     * Accuracy test if all fields are properly set, no exceptions should be thrown.
     */
    @Test
    public void testCheckInitialization() {
        ApplicationContext ctx = super.getApplicationContext();
        JPATaskService bean = new JPATaskService();
        bean.setEntityManager(super.getEntityManager());
        Log log = (Log) ctx.getBean("logBean");
        bean.setLog(log);
        NotificationService notificationService = (NotificationService) ctx.getBean("notificationService");
        bean.setNotificationService(notificationService);
        UserService userService = (UserService) ctx.getBean("mockUserService");
        bean.setUserService(userService);

        String attacmentDirectory = "test_files" + File.separator + "attachments";
        TaskListService taskListService = (TaskListService) ctx.getBean("taskListService");
        bean.setAttachmentDirectory(attacmentDirectory);
        bean.setTaskListService(taskListService);

        bean.checkInitialization();

    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#checkInitialization()}.
     * </p>
     * Accuracy test if all fields are properly set, no exceptions should be thrown.
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationNullDirectory() {
        ApplicationContext ctx = super.getApplicationContext();
        JPATaskService bean = new JPATaskService();
        bean.setEntityManager(super.getEntityManager());
        Log log = (Log) ctx.getBean("logBean");
        bean.setLog(log);
        NotificationService notificationService = (NotificationService) ctx.getBean("notificationService");
        bean.setNotificationService(notificationService);
        UserService userService = (UserService) ctx.getBean("mockUserService");
        bean.setUserService(userService);

        String attacmentDirectory = null;
        TaskListService taskListService = (TaskListService) ctx.getBean("taskListService");
        bean.setAttachmentDirectory(attacmentDirectory);
        bean.setTaskListService(taskListService);

        bean.checkInitialization();

    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#checkInitialization()}.
     * </p>
     * Accuracy test if all fields are properly set, no exceptions should be thrown.
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationEmptyDirectory() {
        ApplicationContext ctx = super.getApplicationContext();
        JPATaskService bean = new JPATaskService();
        bean.setEntityManager(super.getEntityManager());
        Log log = (Log) ctx.getBean("logBean");
        bean.setLog(log);
        NotificationService notificationService = (NotificationService) ctx.getBean("notificationService");
        bean.setNotificationService(notificationService);
        UserService userService = (UserService) ctx.getBean("mockUserService");
        bean.setUserService(userService);

        String attacmentDirectory = "  ";
        TaskListService taskListService = (TaskListService) ctx.getBean("taskListService");
        bean.setAttachmentDirectory(attacmentDirectory);
        bean.setTaskListService(taskListService);

        bean.checkInitialization();

    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#checkInitialization()}.
     * </p>
     * Accuracy test if all fields are properly set, no exceptions should be thrown.
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationNullTaskListService() {
        ApplicationContext ctx = super.getApplicationContext();
        JPATaskService bean = new JPATaskService();
        bean.setEntityManager(super.getEntityManager());
        Log log = (Log) ctx.getBean("logBean");
        bean.setLog(log);
        NotificationService notificationService = (NotificationService) ctx.getBean("notificationService");
        bean.setNotificationService(notificationService);
        UserService userService = (UserService) ctx.getBean("mockUserService");
        bean.setUserService(userService);

        String attacmentDirectory = "test_files" + File.separator + "attachments";
        TaskListService taskListService = null;
        bean.setAttachmentDirectory(attacmentDirectory);
        bean.setTaskListService(taskListService);

        bean.checkInitialization();

    }

    /**
     * <p>
     * Tests the constructor: {@link JPATaskService#JPATaskService()}.
     * </p>
     * Accuracy test to check if the instance can be created properly.
     */
    @Test
    public void testJPATaskService() {
        JPATaskService instance = new JPATaskService();
        assertNotNull("The instance cannot be created.", instance);
        assertTrue("The instance should has correct type.", instance instanceof JPATaskService);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * Accuracy test to check if the task can be added.
     * @throws Exception to JUnit.
     */
    @Test
    public void testAddTask() throws Exception {
        Task task = new Task();
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setName("Task Name");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(taskList.getId());
        // set the assignee
        UserDTO assignee = new UserDTO();
        assignee.setHandle(user.getHandle());
        assignee.setUserId(user.getUserId());
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        assignees.add(assignee);
        task.setAssignees(assignees);
        Task result = taskService.addTask(user.getUserId(), task);
        // email should be sent to the assignees
        Task finalResult = taskService.getTask(user.getUserId(), result.getId());
        assertEquals("should has the same name.", "Task Name", finalResult.getName());
        assertEquals("assignees should be correct.", user.getHandle(), finalResult.getAssignees().get(0).getHandle());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * If the task list id does not exist, exception should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testAddTaskTaskListNotExists() throws Exception {
        Task task = new Task();
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setName("Task Name");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(taskList.getId() + 10001);
        taskService.addTask(user.getUserId(), task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * If the user is not permitted, exception should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testAddTaskTaskListNoPermission() throws Exception {
        Task task = new Task();
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setName("Task Name");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(taskList.getId());

        User newUser = super.createUsers(1).get(0);
        newUser.setHandle("NEW_USER");
        persist(newUser);
        taskService.addTask(newUser.getUserId(), task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * If the name of the task is null or empty, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddTaskInvalidTaskName() throws Exception {
        Task task = new Task();
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setName(null);
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(taskList.getId());

        taskService.addTask(user.getUserId(), task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * If the name of the task is null or empty, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddTaskInvalidTaskName2() throws Exception {
        Task task = new Task();
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setName("  ");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(taskList.getId());

        taskService.addTask(user.getUserId(), task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * If the task is null, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddTaskNullTask() throws Exception {
        taskService.addTask(user.getUserId(), null);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#updateTask(long, Task)}.
     * </p>
     * Accuracy test to check if the task is updated.
     * @throws Exception to JUnit.
     */
    @Test
    public void testUpdateTask() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        task.setStatus(TaskStatus.COMPLETED);
        taskService.updateTask(user.getUserId(), task);
        super.getEntityManager().clear();
        task = super.getEntityManager().find(Task.class, task.getId());
        assertEquals("The task should be updated.", TaskStatus.COMPLETED, task.getStatus());
        // the notification email should send
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#updateTask(long, Task)}.
     * </p>
     * If the old task does not exist, exception should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testUpdateTaskNotFound() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        task.setStatus(TaskStatus.COMPLETED);
        task.setId(10001);
        taskService.updateTask(user.getUserId(), task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#updateTask(long, Task)}.
     * </p>
     * Accuracy test to check if the task status is not change, no email should send.
     * @throws Exception to JUnit.
     */
    @Test
    public void testUpdateTaskNotChange() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        taskService.updateTask(user.getUserId(), task);
        super.getEntityManager().clear();
        task = super.getEntityManager().find(Task.class, task.getId());
        assertEquals("The task should be updated.", TaskStatus.IN_PROGRESS, task.getStatus());
        // the notification email should not be send
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#updateTask(long, Task)}.
     * </p>
     * Failure test if the task is not valid.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testUpdateTaskInvalidTask() throws Exception {
        taskService.updateTask(user.getUserId(), null);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getTask(long, long)}.
     * </p>
     * Accuracy test to check if the task can be retrieved.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTask() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        long taskId = task.getId();
        Task result = taskService.getTask(user.getUserId(), taskId);
        assertEquals("should has the same name.", result.getName(), task.getName());
    }
    /**
     * <p>
     * Tests the method: {@link JPATaskService#getTask(long, long)}.
     * </p>
     * Accuracy test to check if the task does not exist, null should be returned.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskNotExist() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        long taskId = task.getId();
        Task result = taskService.getTask(user.getUserId(), taskId + 10001);
        assertNull("should be null.", result);
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskService#getTask(long, long)}.
     * </p>
     * If the user is not permitted, exception should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetTaskNotPermitted() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        long taskId = task.getId();
        User newUser = super.createUsers().get(0);
        newUser.setHandle("NEW_ONE");
        persist(newUser);
        taskService.getTask(newUser.getUserId(), taskId);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#deleteTask(long, long)}.
     * </p>
     * Accuracy test to check if the task can be deleted.
     * @throws Exception to JUnit.
     */
    @Test
    public void testDeleteTask() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        long taskId = task.getId();
        taskService.deleteTask(user.getUserId(), taskId);
        // find the task
        super.getEntityManager().clear();
        task = super.getEntityManager().find(Task.class, taskId);
        assertNull("The task should be deleted.", task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#deleteTask(long, long)}.
     * </p>
     * If the task does not exist, exception should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testDeleteTaskNotExist() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        long taskId = task.getId();
        taskService.deleteTask(user.getUserId(), taskId + 10001);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTaskAttachment(long, TaskAttachment, InputStream)}.
     * </p>
     * Accuracy test to check if the attachment can be successfully added.
     * @throws Exception to JUnit.
     */
    @Test
    public void testAddTaskAttachment() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setTaskId(task.getId());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        InputStream inputStream = new ByteArrayInputStream("abcde".getBytes());
        try {
            taskService.addTaskAttachment(user.getUserId(), attachment, inputStream);
            inputStream.close();
            inputStream = taskService.getTaskAttachmentContent(user.getUserId(), attachment.getId());
            // check the content
            byte[] bytes = new byte[10240];
            int len = inputStream.read(bytes);
            assertTrue("should contains content.", len > 0 && len < 10);
            inputStream.close();
        } finally {
            taskService.deleteTaskAttachment(user.getUserId(), attachment.getId());
        }

    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTaskAttachment(long, TaskAttachment, InputStream)}.
     * </p>
     * If the attachment is invalid, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddTaskAttachmentInvalidAttachment() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setTaskId(task.getId());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        InputStream inputStream = new ByteArrayInputStream("abc".getBytes());
        taskService.addTaskAttachment(user.getUserId(), null, inputStream);
        inputStream.close();
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTaskAttachment(long, TaskAttachment, InputStream)}.
     * </p>
     * If the attachment task is not found, EntityNotFoundException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testAddTaskAttachmentTaskNotFound() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setTaskId(task.getId() + 1000);
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        InputStream inputStream = new ByteArrayInputStream("abc".getBytes());
        taskService.addTaskAttachment(user.getUserId(), attachment, inputStream);
        inputStream.close();
    }

    /**
     * <p>
     * Tests the  method: {@link JPATaskService#deleteTaskAttachment(long, long).
     * </p>
     * Accuracy test to check if the attachment can be deleted.
     * @throws Exception to JUnit.
     */
    @Test
    public void testDeleteTaskAttachment() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setCreatedBy(user.getHandle());
        attachment.setCreatedDate(new Date());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        attachment.setTaskId(task.getId());
        persist(attachment);
        // create the file
        File file = new File("test_files" + File.separator + "attachments" + File.separator + attachment.getId());
        try {
            // write something to the attachment file
            String content = "This is a test. Hahahahahha";
            OutputStream os = new FileOutputStream(file);
            os.write(content.getBytes());
            os.close();

            taskService.deleteTaskAttachment(user.getUserId(), attachment.getId());

            assertFalse("the file should be deleted.", file.exists());

            // entity is removed
            super.getEntityManager().clear();
            assertNull("should be deleted",
                super.getEntityManager().find(TaskAttachment.class, attachment.getId()));
        } finally {
            file.delete();
        }
    }

    /**
     * <p>
     * Tests the  method: {@link JPATaskService#deleteTaskAttachment(long, long).
     * </p>
     * EntityNotFoundException is expected if the attachment does not exist in the persistence.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testDeleteTaskAttachmenNotExistInPersistence() throws Exception {
        taskService.deleteTaskAttachment(user.getUserId(), 10001);
    }

    /**
     * <p>
     * Tests the  method: {@link JPATaskService#deleteTaskAttachment(long, long).
     * </p>
     * If the file does not exist, throw PersistenceException.
     * @throws Exception to JUnit.
     */
    @Test (expected = PersistenceException.class)
    public void testDeleteTaskAttachmentNotExitFile() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setCreatedBy(user.getHandle());
        attachment.setCreatedDate(new Date());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        attachment.setTaskId(task.getId());
        persist(attachment);
        taskService.deleteTaskAttachment(user.getUserId(), attachment.getId());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getTaskAttachmentContent(long, long)}.
     * </p>
     * Accuracy tests to check if the attachment content can be found.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskAttachmentContent() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setCreatedBy(user.getHandle());
        attachment.setCreatedDate(new Date());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        attachment.setTaskId(task.getId());
        persist(attachment);
        // create the file
        File file = new File("test_files" + File.separator + "attachments" + File.separator + attachment.getId());
        try {
            // write something to the attachment file
            String content = "This is a test. Hahahahahha";
            OutputStream os = new FileOutputStream(file);
            os.write(content.getBytes());
            os.close();

            InputStream is = taskService.getTaskAttachmentContent(user.getUserId(), attachment.getId());
            // check the content
            byte[] bytes = new byte[10240];
            int len = is.read(bytes);
            assertTrue("should contains content.", len > 10);
            is.close();
        } finally {
            file.delete();
        }
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getTaskAttachmentContent(long, long)}.
     * </p>
     * Accuracy tests to check if the attachment content can not be found, null should return.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskAttachmentContentNotExist() throws Exception {
        InputStream is = taskService.getTaskAttachmentContent(user.getUserId(), 100001);
        assertNull("null should return.", is);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getTaskAttachmentContent(long, long)}.
     * </p>
     * Accuracy tests to check if the attachment content can be found.
     * @throws Exception to JUnit.
     */
    @Test (expected = PersistenceException.class)
    public void testGetTaskAttachmentContentFileNotFound() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setCreatedBy(user.getHandle());
        attachment.setCreatedDate(new Date());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        attachment.setTaskId(task.getId());
        persist(attachment);

        taskService.getTaskAttachmentContent(user.getUserId(), attachment.getId());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByPriority(List)}.
     * </p>
     * Accuracy tests to check if the order of the tasks are properly sorted.
     */
    @Test
    public void testGroupTasksByPriority() {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        TaskPriority[] priorityes = new TaskPriority[] {TaskPriority.LOW, TaskPriority.HIGH, TaskPriority.NORMAL};
        int totalTaskCount = 0;
        for (int i = 0; i < 3; i++) {
            TaskList taskList1 = new TaskList();
            List<Task> tasks = new ArrayList<Task>();
            for (TaskPriority priority : priorityes) {
                for (int j = 0; j < 3; j++) {
                    Task task = new Task();
                    task.setCreatedDate(new Date(new Date().getTime() - (long) (Math.random() * 1000)));
                    task.setPriority(priority);
                    tasks.add(task);
                    totalTaskCount++;
                }
            }
            taskList1.setTasks(tasks);
            taskLists.add(taskList1);
        }

        int resultNum = 0;
        SortedMap<TaskPriority, List<Task>> result = taskService.groupTasksByPriority(taskLists);
        for (TaskPriority priority : result.keySet()) {
            List<Task> tasks = result.get(priority);
            long prev = 0;
            for (Task task : tasks) {
                assertEquals("should group by priority.", priority, task.getPriority());
                // sorted by createdDate
                assertTrue("should be ascending.", task.getCreatedDate().getTime() >= prev);
                prev = task.getCreatedDate().getTime();
                resultNum++;
            }
        }
        assertEquals("The total num should be equal.", totalTaskCount, resultNum);

    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByPriority(List)}.
     * </p>
     * If the task list is null.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByPriorityNulLList() {
        List<TaskList> taskLists = null;
        taskService.groupTasksByPriority(taskLists);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByPriority(List)}.
     * </p>
     * If the task list contains null.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByPriorityContainsNull() {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskLists.add(null);
        taskService.groupTasksByPriority(taskLists);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByPriority(List)}.
     * </p>
     * If the task list contains null tasks.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByPriorityNullTasks() {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskList = new TaskList();
        List<Task> tasks = null;
        taskList.setTasks(tasks);
        taskLists.add(taskList);
        taskService.groupTasksByPriority(taskLists);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByPriority(List)}.
     * </p>
     * If the task list contains null tasks.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByPriorityNullTask() {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(null);
        taskList.setTasks(tasks);
        taskLists.add(taskList);
        taskService.groupTasksByPriority(taskLists);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByPriority(List)}.
     * </p>
     * If the task list contains null priority.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByPriorityNullPriority() {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task();
        task.setName("test name");
        task.setPriority(null);
        task.setCreatedDate(new Date());
        tasks.add(task);
        taskList.setTasks(tasks);
        taskLists.add(taskList);
        taskService.groupTasksByPriority(taskLists);
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByPriority(List)}.
     * </p>
     * If the task list contains null created date.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByPriorityNullCreatedDate() {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task();
        task.setName("test name");
        task.setPriority(TaskPriority.HIGH);
        task.setCreatedDate(null);
        tasks.add(task);
        taskList.setTasks(tasks);
        taskLists.add(taskList);
        taskService.groupTasksByPriority(taskLists);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByAssignee(List)}.
     * </p>
     * Accuracy test to check if the task lists are group by assignees.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGroupTasksByAssignee() throws Exception {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        UserDTO[] assignees = new UserDTO[] {new UserDTO(), new UserDTO(), new UserDTO(), new UserDTO()};
        int index = 1;
        for (UserDTO assignee : assignees) {
            assignee.setHandle("user_" + index++);
            assignee.setUserId(index);
        }
        int totalTaskCount = 0;
        for (int i = 0; i < 3; i++) {
            TaskList taskList1 = new TaskList();
            List<Task> tasks = new ArrayList<Task>();

            for (int j = 0; j < 3; j++) {
                Task task = new Task();
                task.setCreatedDate(new Date(new Date().getTime() - (long) (Math.random() * 1000)));
                task.setAssignees(Arrays.asList(assignees));
                tasks.add(task);
                totalTaskCount++;
            }

            taskList1.setTasks(tasks);
            taskLists.add(taskList1);
        }

        int resultNum = 0;
        SortedMap<UserDTO, List<Task>> result = taskService.groupTasksByAssignee(taskLists);
        // check the key set
        result.keySet().containsAll(Arrays.asList(assignees));
        Arrays.asList(assignees).contains(result.keySet());
        for (UserDTO assignee : result.keySet()) {
            List<Task> tasks = result.get(assignee);
            long prev = 0;

            for (Task task : tasks) {
                // sorted by createdDate
                assertTrue("should be ascending.", task.getCreatedDate().getTime() >= prev);
                prev = task.getCreatedDate().getTime();
                resultNum++;
            }
        }
        assertEquals("The total num should be equal.", totalTaskCount * assignees.length, resultNum);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByAssignee(List)}.
     * </p>
     * If the assignees is null, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByAssigneeNullAssignee() throws Exception {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task();
        task.setName("test name");
        List<UserDTO> assignees = null;
        task.setAssignees(assignees);
        task.setCreatedDate(new Date());
        tasks.add(task);
        taskList.setTasks(tasks);
        taskLists.add(taskList);
        taskService.groupTasksByAssignee(taskLists);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByAssignee(List)}.
     * </p>
     * If the assignees is null, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByAssigneeContainsNullAssignee() throws Exception {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task();
        task.setName("test name");
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        assignees.add(null);
        task.setAssignees(assignees);
        task.setCreatedDate(new Date());
        tasks.add(task);
        taskList.setTasks(tasks);
        taskLists.add(taskList);
        taskService.groupTasksByAssignee(taskLists);
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByDueDate(List)}.
     * </p>
     * Accuracy test to check if the order of the tasks are properly sorted by dueDate.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGroupTasksByDueDate() throws Exception {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date[] dueDates =
            new Date[] {df.parse("2012.04.01"), df.parse("2012.04.02"), df.parse("2012.04.03"), df.parse("2012.04.04")};
        int totalTaskCount = 0;
        for (int i = 0; i < 3; i++) {
            TaskList taskList1 = new TaskList();
            List<Task> tasks = new ArrayList<Task>();
            for (Date dueDate : dueDates) {
                for (int j = 0; j < 3; j++) {
                    Task task = new Task();
                    task.setCreatedDate(new Date(new Date().getTime() - (long) (Math.random() * 1000)));
                    task.setDueDate(new Date(dueDate.getTime() + (long) (Math.random() * 1000L * 1000L)));
                    tasks.add(task);
                    totalTaskCount++;
                }
            }
            taskList1.setTasks(tasks);
            taskLists.add(taskList1);
        }

        int resultNum = 0;
        SortedMap<Date, List<Task>> result = taskService.groupTasksByDueDate(taskLists);
        // check the key set
        result.keySet().containsAll(Arrays.asList(dueDates));
        Arrays.asList(dueDates).contains(result.keySet());
        for (Date dueDate : result.keySet()) {
            List<Task> tasks = result.get(dueDate);
            long prev = 0;

            for (Task task : tasks) {
                assertEquals("should group by status.", df.format(dueDate), df.format(task.getDueDate()));
                // sorted by createdDate
                assertTrue("should be ascending.", task.getCreatedDate().getTime() >= prev);
                prev = task.getCreatedDate().getTime();
                resultNum++;
            }
        }
        assertEquals("The total num should be equal.", totalTaskCount, resultNum);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByDueDate(List)}.
     * </p>
     * If the due date in the task is null, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByDueDateNullDueDate() throws Exception {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task();
        task.setName("test name");
        task.setDueDate(null);
        task.setCreatedDate(new Date());
        tasks.add(task);
        taskList.setTasks(tasks);
        taskLists.add(taskList);
        taskService.groupTasksByAssignee(taskLists);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByStartDate(List)}.
     * </p>
     * Accuracy test to check if the order of the tasks are properly sorted by startDate.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGroupTasksByStartDate() throws Exception {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date[] startDates =
            new Date[] {df.parse("2012.04.01"), df.parse("2012.04.02"), df.parse("2012.04.03"), df.parse("2012.04.04")};
        int totalTaskCount = 0;
        for (int i = 0; i < 3; i++) {
            TaskList taskList1 = new TaskList();
            List<Task> tasks = new ArrayList<Task>();
            for (Date startDate : startDates) {
                for (int j = 0; j < 3; j++) {
                    Task task = new Task();
                    task.setCreatedDate(new Date(new Date().getTime() - (long) (Math.random() * 1000)));
                    task.setStartDate(new Date(startDate.getTime() + (long) (Math.random() * 1000L * 1000L)));
                    tasks.add(task);
                    totalTaskCount++;
                }
            }
            taskList1.setTasks(tasks);
            taskLists.add(taskList1);
        }

        int resultNum = 0;
        SortedMap<Date, List<Task>> result = taskService.groupTasksByStartDate(taskLists);
        // check the key set
        result.keySet().containsAll(Arrays.asList(startDates));
        Arrays.asList(startDates).contains(result.keySet());
        for (Date startDate : result.keySet()) {
            List<Task> tasks = result.get(startDate);
            long prev = 0;

            for (Task task : tasks) {
                assertEquals("should group by status.", df.format(startDate), df.format(task.getStartDate()));
                // sorted by createdDate
                assertTrue("should be ascending.", task.getCreatedDate().getTime() >= prev);
                prev = task.getCreatedDate().getTime();
                resultNum++;
            }
        }
        assertEquals("The total num should be equal.", totalTaskCount, resultNum);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByStartDate(List)}.
     * </p>
     * If the start date in the task is null, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByDueDateNullStartDate() throws Exception {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task();
        task.setName("test name");
        task.setStartDate(null);
        task.setCreatedDate(new Date());
        tasks.add(task);
        taskList.setTasks(tasks);
        taskLists.add(taskList);
        taskService.groupTasksByAssignee(taskLists);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByStatus(List)}.
     * </p>
     * Accuracy tests to check if the order of the tasks are properly sorted.
     */
    @Test
    public void testGroupTasksByStatus() {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        TaskStatus[] statuses =
            new TaskStatus[] {TaskStatus.COMPLETED, TaskStatus.IN_PROGRESS, TaskStatus.WAIT_ON_DEPENDENCY,
                TaskStatus.NOT_STARTED};
        int totalTaskCount = 0;
        for (int i = 0; i < 3; i++) {
            TaskList taskList1 = new TaskList();
            List<Task> tasks = new ArrayList<Task>();
            for (TaskStatus status : statuses) {
                for (int j = 0; j < 3; j++) {
                    Task task = new Task();
                    task.setCreatedDate(new Date(new Date().getTime() - (long) (Math.random() * 1000)));
                    task.setStatus(status);
                    tasks.add(task);
                    totalTaskCount++;
                }
            }
            taskList1.setTasks(tasks);
            taskLists.add(taskList1);
        }

        int resultNum = 0;
        SortedMap<TaskStatus, List<Task>> result = taskService.groupTasksByStatus(taskLists);
        for (TaskStatus status : result.keySet()) {
            List<Task> tasks = result.get(status);
            long prev = 0;
            for (Task task : tasks) {
                assertEquals("should group by status.", status, task.getStatus());
                // sorted by createdDate
                assertTrue("should be ascending.", task.getCreatedDate().getTime() >= prev);
                prev = task.getCreatedDate().getTime();
                resultNum++;
            }
        }
        assertEquals("The total num should be equal.", totalTaskCount, resultNum);

    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#groupTasksByStatus(List)}.
     * </p>
     * If the task list contains null status.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGroupTasksByStatusNullStatus() {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task();
        task.setName("test name");
        task.setStatus(null);
        task.setCreatedDate(new Date());
        tasks.add(task);
        taskList.setTasks(tasks);
        taskLists.add(taskList);
        taskService.groupTasksByStatus(taskLists);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getNumberOfCompletedTasks(long, long)}.
     * </p>
     * Accuracy test to check if the number of the completed task is properly retrieved.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetNumberOfCompletedTasks() throws Exception {
        createTask(TaskStatus.COMPLETED);
        createTask(TaskStatus.NOT_STARTED);
        createTask(TaskStatus.COMPLETED);
        int num = taskService.getNumberOfCompletedTasks(user.getUserId(), contest.getContestId());
        assertEquals("2 completed tasks of the project.", 2, num);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getNumberOfCompletedTasks(long, long)}.
     * </p>
     * If the project is not found, EntityNotFoundException should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testGetNumberOfCompletedTasksProjectNotExist() throws Exception {
        taskService.getNumberOfCompletedTasks(user.getUserId(), 10001);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getNumberOfCompletedTasks(long, long)}.
     * </p>
     * Accuracy test to check if the number of the completed task is properly retrieved.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetNumberOfCompletedTasksNo() throws Exception {
        createTask(TaskStatus.NOT_STARTED);
        createTask(TaskStatus.NOT_STARTED);
        createTask(TaskStatus.NOT_STARTED);
        int num = taskService.getNumberOfCompletedTasks(user.getUserId(), contest.getContestId());
        assertEquals("2 completed tasks of the project.", 0, num);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getNumberOfAllTasks(long, long)}.
     * </p>
     * Accuracy test to check if the tasks can be retrieved.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetNumberOfAllTasks() throws Exception {
        // no task
        int num = taskService.getNumberOfAllTasks(user.getUserId(), contest.getContestId());
        assertEquals("no task of the project.", 0, num);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getNumberOfAllTasks(long, long)}.
     * </p>
     * If the project is not found, EntityNotFoundException should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testGetNumberOfAllTasksProjectNotExist() throws Exception {
        taskService.getNumberOfAllTasks(user.getUserId(), 10001);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getNumberOfAllTasks(long, long)}.
     * </p>
     * Accuracy test to check if the tasks can be retrieved.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetNumberOfAllTasksHasTasks() throws Exception {
        createTask(TaskStatus.COMPLETED);
        createTask(TaskStatus.NOT_STARTED);
        int num = taskService.getNumberOfAllTasks(user.getUserId(), contest.getContestId());
        assertEquals("2 tasks of the project.", 2, num);
    }

    /**
     * <p>
     * Creates a task for unit tests.
     * </p>
     * @param status the status of the task to create.
     * @return the created task.
     */
    private Task createTask(TaskStatus status) {
        Task task = new Task();
        task.setStatus(status);
        task.setPriority(TaskPriority.HIGH);
        task.setCreatedBy(user.getHandle());
        task.setCreatedDate(new Date());
        task.setName("name");
        task.setNotes("something to note");
        task.setTaskListId(taskList.getId());
        persist(task);
        return task;
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskService#setTaskListService(TaskListService)}.
     * </p>
     * Accuracy test to check if the taskListService can be set.
     */
    @Test
    public void testSetTaskListService() {
        JPATaskService service = new JPATaskService();
        TaskListService taskListService =
            (TaskListService) super.getApplicationContext().getBean("taskListService");

        service.setTaskListService(taskListService);
        TaskListService value = (TaskListService) getField(service, "taskListService");
        assertEquals("should be equal.", value, taskListService);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#setAttachmentDirectory(String)}.
     * </p>
     * Accuracy test to check if the attachmentDirectory can be successfully set.
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void testSetAttachmentDirectory() throws Exception {
        JPATaskService service = new JPATaskService();
        service.setAttachmentDirectory("abc");
        String value = (String) getField(service, "attachmentDirectory");
        assertEquals("should be equal.", value, "abc");
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskService#getNumberOfAllTasks(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetNumberOfAllTasksPermisionError() throws Exception {
        taskService.getNumberOfAllTasks(user.getUserId() + 10001, contest.getContestId());
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskService#getNumberOfCompletedTasks(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetNumberOfCompletedTasksPermisionError() throws Exception {
        taskService.getNumberOfCompletedTasks(user.getUserId() + 10001, contest.getContestId());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getTaskAttachmentContent(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetTaskAttachmentContentPermisionError() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setCreatedBy(user.getHandle());
        attachment.setCreatedDate(new Date());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        attachment.setTaskId(task.getId());
        persist(attachment);
        // create the file
        File file = new File("test_files" + File.separator + "attachments" + File.separator + attachment.getId());
        try {
            // write something to the attachment file
            String content = "This is a test. Hahahahahha";
            OutputStream os = new FileOutputStream(file);
            os.write(content.getBytes());
            os.close();

            taskService.getTaskAttachmentContent(user.getUserId() + 10000, attachment.getId());

        } finally {
            file.delete();
        }
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#deleteTaskAttachment(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testDeleteTaskAttachmentContentPermisionError() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setCreatedBy(user.getHandle());
        attachment.setCreatedDate(new Date());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        attachment.setTaskId(task.getId());
        persist(attachment);
        // create the file
        File file = new File("test_files" + File.separator + "attachments" + File.separator + attachment.getId());
        try {
            // write something to the attachment file
            String content = "This is a test. Hahahahahha";
            OutputStream os = new FileOutputStream(file);
            os.write(content.getBytes());
            os.close();

            taskService.deleteTaskAttachment(user.getUserId() + 1000, attachment.getId());

        } finally {
            file.delete();
        }
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTaskAttachment(long, TaskAttachment, InputStream)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PersistenceException.class)
    public void testAddTaskAttachmentContentPersistenceError() throws Exception {
        EntityManager entityManagerFailure = Mockito.mock(EntityManager.class);
        Mockito.doThrow(javax.persistence.PersistenceException.class).when(entityManagerFailure).flush();
        beanFailure.setEntityManager(entityManagerFailure);
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setTaskId(task.getId());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        InputStream inputStream = new ByteArrayInputStream("abcde".getBytes());
        beanFailure.addTaskAttachment(user.getUserId(), attachment, inputStream);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTaskAttachment(long,
     * TaskAttachment, InputStream)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testAddTaskAttachmentContentPermisionError() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setTaskId(task.getId());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        InputStream inputStream = new ByteArrayInputStream("abcde".getBytes());
        taskService.addTaskAttachment(user.getUserId() + 1000, attachment, inputStream);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTaskAttachment(long, TaskAttachment, InputStream)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddTaskAttachmentContentIAE() throws Exception {
        // create the task
        Task task = createTask(TaskStatus.IN_PROGRESS);
        // create the attachment
        TaskAttachment attachment = new TaskAttachment();
        attachment.setTaskId(task.getId());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        InputStream inputStream = new ByteArrayInputStream("abcde".getBytes());
        taskService.addTaskAttachment(user.getUserId(), null, inputStream);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#deleteTask(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testDeleteTaskPermisionError() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        long taskId = task.getId();
        taskService.deleteTask(user.getUserId() + 1000, taskId);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddTaskIAE() throws Exception {
        taskService.addTask(user.getUserId(), null);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PersistenceException.class)
    public void testAddTaskPersistenceError() throws Exception {
        Task task = new Task();
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setName("Task Name");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(taskList.getId());
        // set the assignee
        UserDTO assignee = new UserDTO();
        assignee.setHandle(user.getHandle());
        assignee.setUserId(user.getUserId());
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        assignees.add(assignee);
        task.setAssignees(assignees);
        beanFailure.addTask(user.getUserId(), task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testAddTaskPermisionError() throws Exception {
        Task task = new Task();
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setName("Task Name");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(taskList.getId());
        // set the assignee
        UserDTO assignee = new UserDTO();
        assignee.setHandle(user.getHandle());
        assignee.setUserId(user.getUserId());
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        assignees.add(assignee);
        task.setAssignees(assignees);
        taskService.addTask(user.getUserId() + 1000, task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#addTask(long, Task)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = NotificationException.class)
    public void testAddTaskNotificationError() throws Exception {
        NotificationService notificationService = Mockito.mock(NotificationService.class);
        Mockito.doThrow(NotificationException.class).when(notificationService).notifyTaskCreation(
            Mockito.anyLong(), Mockito.any(Task.class));
        beanFailure.setNotificationService(notificationService);
        Task task = new Task();
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setName("Task Name");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(taskList.getId());
        // set the assignee
        UserDTO assignee = new UserDTO();
        assignee.setHandle(user.getHandle());
        assignee.setUserId(user.getUserId());
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        assignees.add(assignee);
        task.setAssignees(assignees);
        try {
            super.getEntityManager().getTransaction().begin();
            beanFailure.addTask(user.getUserId(), task);
            super.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            super.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#updateTask(long, Task)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PersistenceException.class)
    public void testUpdateTaskPersistenceError() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        task.setStatus(TaskStatus.COMPLETED);
        beanFailure.updateTask(user.getUserId(), task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#updateTask(long, Task)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testUpdateTaskIAE() throws Exception {
        taskService.updateTask(user.getUserId(), null);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#updateTask(long, Task)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testUpdateTaskPermisionError() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        task.setStatus(TaskStatus.COMPLETED);
        taskService.updateTask(user.getUserId() + 1000, task);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#updateTask(long, Task)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = NotificationException.class)
    public void testUpdateTaskNotificationError() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        task.setStatus(TaskStatus.COMPLETED);
        NotificationService notificationService = Mockito.mock(NotificationService.class);
        Mockito.doThrow(NotificationException.class).when(notificationService).notifyTaskStatusChange(
            Mockito.anyLong(), Mockito.any(TaskStatus.class), Mockito.any(Task.class));
        beanFailure.setNotificationService(notificationService);
        try {
            super.getEntityManager().clear();
            super.getEntityManager().getTransaction().begin();
            beanFailure.updateTask(user.getUserId(), task);
            super.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            super.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskService#getTask(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetTaskPermisionError() throws Exception {
        Task task = createTask(TaskStatus.IN_PROGRESS);
        long taskId = task.getId();
        taskService.getTask(user.getUserId() + 1000, taskId);
    }
}
