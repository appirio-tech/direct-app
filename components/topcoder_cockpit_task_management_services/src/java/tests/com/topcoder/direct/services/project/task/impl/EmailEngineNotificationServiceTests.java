/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.topcoder.direct.services.project.task.NotificationException;
import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.log4j.Log4jLogFactory;

/**
 * <p>
 * All unit tests class for <code>EmailEngineNotificationService</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmailEngineNotificationServiceTests extends BaseUnitTests {

    /**
     * <p>
     * Represents the spring application context for unit tests.
     * </p>
     */
    private ApplicationContext ctx;

    /**
     * <p>
     * Represents the instance of NotificationService for unit tests.
     * </p>
     */
    private NotificationService notificationService;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(EmailEngineNotificationServiceTests.class);
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
        ctx = super.getApplicationContext();
        notificationService = (NotificationService) ctx.getBean("notificationService");
    }

    /**
     * <p>
     * Tests the constructor: {@link EmailEngineNotificationService#EmailEngineNotificationService()}.
     * </p>
     * Accuracy test to check if the instance can be created correctly.
     * @throws Exception to JUnit.
     */
    @Test
    public void testEmailEngineNotificationService() throws Exception {
        EmailEngineNotificationService instance = new EmailEngineNotificationService();
        assertNotNull("The instance cannot be created.", instance);
        assertTrue("Should be the instance of EmailEngineNotificationService",
            instance instanceof EmailEngineNotificationService);
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * Accuracy test if all the fields are properly injected, no exceptions should be thrown.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void testCheckInitialization() throws Exception {

        EmailEngineNotificationService bean = new EmailEngineNotificationService();
        LogFactory factory = new Log4jLogFactory();
        Log log = factory.createLog("test_log");

        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * Accuracy test if the optional log is not injected, no exceptions should be thrown.
     * </p>
     */
    @Test
    public void testCheckInitializationMissingLog() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = null;
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the userService is not properly injected, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationMissingUserService() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = null;
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the sender is null, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationSenderNull() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = null;
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the sender is empty, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationSenderEmpty() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "   ";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the create subject template is null, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationCreateSubjectTemplateNull() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = null;
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the create subject template is empty, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationCreateSubjectTemplateEmpty() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "   ";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the create body template is null, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationCreateBodyTemplateNull() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = null;
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the create body template is empty, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationCreateBodyTemplateEmpty() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "   ";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the status subject template is null, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationStatusSubjectTemplateNull() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = null;

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the status subject template is empty, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationStatusSubjectTemplateEmpty() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "test_files";
        String taskStatusChangeEmailSubjectTemplateText = "  ";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the status body template is null, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationStatusBodyTemplateNull() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = null;
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#checkInitialization()}.
     * </p>
     * <p>
     * If the status body template is empty, TaskManagementConfigurationException is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationStatusBodyTemplateEmpty() {
        LogManager.setLogFactory(new Log4jLogFactory());
        EmailEngineNotificationService bean = new EmailEngineNotificationService();

        Log log = LogManager.getLog();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        String emailSender = "tc@tc.com";
        String taskCreationEmailBodyTemplatePath = "test_files";
        String taskCreationEmailSubjectTemplateText = "test_string";
        String taskStatusChangeEmailBodyTemplatePath = "   ";
        String taskStatusChangeEmailSubjectTemplateText = "test_string";

        bean.setEmailSender(emailSender);
        bean.setLog(log);
        bean.setTaskCreationEmailBodyTemplatePath(taskCreationEmailBodyTemplatePath);
        bean.setTaskCreationEmailSubjectTemplateText(taskCreationEmailSubjectTemplateText);
        bean.setTaskStatusChangeEmailBodyTemplatePath(taskStatusChangeEmailBodyTemplatePath);
        bean.setTaskStatusChangeEmailSubjectTemplateText(taskStatusChangeEmailSubjectTemplateText);
        bean.setUserService(userService);
        bean.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#notifyTaskStatusChange(long, TaskStatus, Task)}
     * .
     * </p>
     * Accuracy tests to check if the task status change emails can be successfully sent.
     * @throws Exception to JUnit.
     */
    @Test
    public void testNotifyTaskStatusChange() throws Exception {
        List<User> users = super.createUsers();
        // create the task to send the mail
        Task task = new Task();
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        for (User user : users) {
            UserDTO assignee = new UserDTO();
            assignee.setHandle(user.getHandle());
            assignee.setUserId(user.getUserId());
            assignees.add(assignee);
        }
        task.setAssignees(assignees);
        task.setStatus(TaskStatus.COMPLETED);
        task.setName("Test Status Change Project");
        task.setCreatedBy(users.get(1).getHandle());
        notificationService.notifyTaskStatusChange(users.get(0).getUserId(), TaskStatus.IN_PROGRESS, task);
        // the mails should send, and you will see it in the smtp dev null
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#notifyTaskStatusChange(long, TaskStatus, Task)}
     * .
     * </p>
     * Exception should be thrown if the email cannot be sent.
     * @throws Exception to JUnit.
     */
    @Test (expected = NotificationException.class)
    public void testNotifyTaskStatusChangeFailedToSend() throws Exception {
        List<User> users = super.createUsers();
        // create the task to send the mail
        Task task = new Task();
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        for (User user : users) {
            UserDTO assignee = new UserDTO();
            assignee.setHandle(user.getHandle());
            assignee.setUserId(user.getUserId() + 10001);
            assignees.add(assignee);
        }
        task.setAssignees(assignees);
        task.setStatus(TaskStatus.COMPLETED);
        task.setName("Test Status Change Project");
        task.setCreatedBy(users.get(1).getHandle());
        notificationService.notifyTaskStatusChange(users.get(0).getUserId(), TaskStatus.IN_PROGRESS, task);
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#notifyTaskStatusChange(long, TaskStatus, Task)}
     * .
     * </p>
     * Accuracy tests to check if the task status change emails can be successfully sent,
     * if the assignee is not the same as the created user.
     * @throws Exception to JUnit.
     */
    @Test
    public void testNotifyTaskStatusChangeDiffrenUser() throws Exception {
        List<User> users = super.createUsers();
        // create the task to send the mail
        Task task = new Task();
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        for (User user : users) {
            if (user.getUserId() == users.get(1).getUserId()) {
                // ignore the creator
                continue;
            }
            UserDTO assignee = new UserDTO();
            assignee.setHandle(user.getHandle());
            assignee.setUserId(user.getUserId());
            assignees.add(assignee);
        }
        task.setAssignees(assignees);
        task.setStatus(TaskStatus.COMPLETED);
        task.setName("Test Status Change Project");
        task.setCreatedBy(users.get(1).getHandle());
        notificationService.notifyTaskStatusChange(users.get(0).getUserId(), TaskStatus.IN_PROGRESS, task);
        // the mails should send, and you will see it in the smtp dev null
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#notifyTaskStatusChange(long, TaskStatus, Task)}
     * .
     * </p>
     * If the task is null, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotifyTaskStatusChangeInvalidTask() throws Exception {
        List<User> users = super.createUsers();
        notificationService.notifyTaskStatusChange(users.get(0).getUserId(), TaskStatus.IN_PROGRESS, null);
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#notifyTaskCreation(long, Task)}.
     * </p>
     * <p>
     * Accuracy test to check if the email can be successfully sent.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void testNotifyTaskCreation() throws Exception {
        List<User> users = super.createUsers();
        // create the task to send the mail
        Task task = new Task();
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        for (User user : users) {
            UserDTO assignee = new UserDTO();
            assignee.setHandle(user.getHandle());
            assignee.setUserId(user.getUserId());
            assignees.add(assignee);
        }
        task.setStartDate(new Date());
        task.setDueDate(new Date(new Date().getTime() + 86400 * 1000L * 5));
        task.setAssignees(assignees);
        task.setName("Test Project");
        task.setStatus(TaskStatus.COMPLETED);
        task.setPriority(TaskPriority.HIGH);

        // send the mail
        notificationService.notifyTaskCreation(users.get(0).getUserId(), task);

        // the mails should send, and you will see it in the smtp dev null
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#notifyTaskCreation(long, Task)}.
     * </p>
     * <p>
     * Accuracy test to check if the email can be successfully sent.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void testNotifyTaskCreationDifferents() throws Exception {
        List<User> users = super.createUsers();
        // create the task to send the mail

        List<UserDTO> assignees = new ArrayList<UserDTO>();
        for (User user : users) {
            UserDTO assignee = new UserDTO();
            assignee.setHandle(user.getHandle());
            assignee.setUserId(user.getUserId());
            assignees.add(assignee);
        }

        TaskStatus[] statues = new TaskStatus[] {
            TaskStatus.COMPLETED,
            TaskStatus.IN_PROGRESS,
            TaskStatus.NOT_STARTED,
            TaskStatus.WAIT_ON_DEPENDENCY
        };

        TaskPriority [] priorities = new TaskPriority[] {
            TaskPriority.HIGH,
            TaskPriority.LOW,
            TaskPriority.NORMAL
        };

        for (TaskStatus status : statues) {
            for (TaskPriority priority : priorities) {
                Task task = new Task();
                task.setStartDate(new Date());
                task.setDueDate(new Date(new Date().getTime() + 86400 * 1000L * 5));
                task.setAssignees(assignees);
                task.setName("Test Project");
                task.setStatus(status);
                task.setPriority(priority);
                // send the mail
                notificationService.notifyTaskCreation(users.get(0).getUserId(), task);
            }
        }

        // the mails should send, and you will see it in the smtp dev null
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#notifyTaskCreation(long, Task)}.
     * </p>
     * <p>
     * If the task is null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotifyTaskCreationNullTask() throws Exception {
        List<User> users = super.createUsers();
        // send the mail
        notificationService.notifyTaskCreation(users.get(0).getUserId(), null);
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#notifyTaskCreation(long, Task)}.
     * </p>
     * <p>
     * If the mail is failed to sent, NotificationException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = NotificationException.class)
    public void testNotifyTaskCreationFailedToSend() throws Exception {
        List<User> users = super.createUsers();
        // create the task to send the mail
        Task task = new Task();
        List<UserDTO> assignees = new ArrayList<UserDTO>();
        for (User user : users) {
            UserDTO assignee = new UserDTO();
            assignee.setHandle(user.getHandle());
            assignee.setUserId(user.getUserId() + 10001);
            assignees.add(assignee);
        }
        task.setStartDate(new Date());
        task.setDueDate(new Date(new Date().getTime() + 86400 * 1000L * 5));
        task.setAssignees(assignees);
        task.setName("Test Project");
        task.setStatus(TaskStatus.COMPLETED);
        task.setPriority(TaskPriority.HIGH);

        // send the mail
        notificationService.notifyTaskCreation(users.get(0).getUserId(), task);
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#setUserService(UserService)}.
     * </p>
     * Accuracy test to check if the user service is successfully set.
     * @throws Exception to JUnit.
     */
    @Test
    public void testSetUserService() throws Exception {
        EmailEngineNotificationService service = new EmailEngineNotificationService();
        UserService userService = (UserService) ctx.getBean("mockUserService");
        service.setUserService(userService);
        UserService result = (UserService) super.getField(service, "userService");
        assertEquals("should be equal.", userService, result);

    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#setLog(Log)}.
     * </p>
     * Accuracy test to check if the log is successfully set.
     * @throws Exception to JUnit.
     */
    @Test
    public void testSetLog() throws Exception {
        EmailEngineNotificationService service = new EmailEngineNotificationService();
        Log log = (Log) ctx.getBean("logBean");
        service.setLog(log);
        Log result = (Log) super.getField(service, "log");
        assertEquals("should be equal.", log, result);
    }

    /**
     * <p>
     * Tests the method: {@link EmailEngineNotificationService#setEmailSender(String)}.
     * </p>
     * Accuracy test to check if the email sender is successfully set.
     */
    @Test
    public void testSetEmailSender() {
        EmailEngineNotificationService service = new EmailEngineNotificationService();
        String sender = "tc@tc.com";
        service.setEmailSender(sender);
        String result = (String) super.getField(service, "emailSender");
        assertEquals("should be equal.", sender, result);
    }

    /**
     * <p>
     * Tests the method:
     * {@link EmailEngineNotificationService#setTaskCreationEmailSubjectTemplateText(String)}.
     * </p>
     * Accuracy test to check if the create email subject is successfully set.
     */
    @Test
    public void testSetTaskCreationEmailSubjectTemplateText() {
        EmailEngineNotificationService service = new EmailEngineNotificationService();
        String value = "abc";
        service.setTaskCreationEmailSubjectTemplateText(value);
        String result = (String) super.getField(service, "taskCreationEmailSubjectTemplateText");
        assertEquals("should be equal.", value, result);
    }

    /**
     * <p>
     * Tests the method:
     * {@link EmailEngineNotificationService#setTaskCreationEmailBodyTemplatePath(String)}.
     * </p>
     * Accuracy test to check if the creation body template is successfully set.
     */
    @Test
    public void testSetTaskCreationEmailBodyTemplatePath() {
        EmailEngineNotificationService service = new EmailEngineNotificationService();
        String value = "abc";
        service.setTaskCreationEmailBodyTemplatePath(value);
        String result = (String) super.getField(service, "taskCreationEmailBodyTemplatePath");
        assertEquals("should be equal.", value, result);
    }

    /**
     * <p>
     * Tests the method:
     * {@link EmailEngineNotificationService#setTaskStatusChangeEmailSubjectTemplateText(String)}
     * </p>
     * Accuracy test to check if the status change subject is successfully set.
     */
    @Test
    public void testSetTaskStatusChangeEmailSubjectTemplateText() {
        EmailEngineNotificationService service = new EmailEngineNotificationService();
        String value = "abc";
        service.setTaskStatusChangeEmailSubjectTemplateText(value);
        String result = (String) super.getField(service, "taskStatusChangeEmailSubjectTemplateText");
        assertEquals("should be equal.", value, result);
    }

    /**
     * <p>
     * Tests the method:
     * {@link EmailEngineNotificationService#setTaskStatusChangeEmailBodyTemplatePath(String)}
     * </p>
     * Accuracy test to check if the status change body template is successfully set.
     */
    @Test
    public void testSetTaskStatusChangeEmailBodyTemplatePath() {
        EmailEngineNotificationService service = new EmailEngineNotificationService();
        String value = "abc";
        service.setTaskStatusChangeEmailBodyTemplatePath(value);
        String result = (String) super.getField(service, "taskStatusChangeEmailBodyTemplatePath");
        assertEquals("should be equal.", value, result);
    }

}
