/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.failuretests;

import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.direct.services.project.task.NotificationException;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.direct.services.project.task.impl.EmailEngineNotificationService;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.UserService;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for EmailEngineNotificationService.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class EmailEngineNotificationServiceFailureTests extends TestCase {
    /**
     * <p>
     * The EmailEngineNotificationService instance for testing.
     * </p>
     */
    private EmailEngineNotificationService instance;

    /**
     * <p>
     * The Task instance for testing.
     * </p>
     */
    private Task task;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new EmailEngineNotificationService();

        UserService userService = mock(UserService.class);

        instance.setUserService(userService);
        instance.setEmailSender("emailSender");
        instance.setTaskCreationEmailBodyTemplatePath("taskCreationEmailBodyTemplatePath");
        instance.setTaskCreationEmailSubjectTemplateText("taskCreationEmailSubjectTemplateText");
        instance.setTaskStatusChangeEmailBodyTemplatePath("taskStatusChangeEmailBodyTemplatePath");
        instance.setTaskStatusChangeEmailSubjectTemplateText("taskStatusChangeEmailSubjectTemplateText");

        task = new Task();
        task.setName("name");
        task.setPriority(TaskPriority.NORMAL);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setCreatedBy("CN");

        List<UserDTO> assignees = new ArrayList<UserDTO>();
        UserDTO assignee = new UserDTO();
        assignee.setHandle("TC");
        assignee.setUserId(1);
        assignees.add(assignee);

        task.setAssignees(assignees);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(EmailEngineNotificationServiceFailureTests.class);
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when userService is null and expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure1() {
        instance.setUserService(null);
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when emailSender is null and expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure2() {
        instance.setEmailSender(null);
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when emailSender is empty and expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure3() {
        instance.setEmailSender(" ");
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when taskCreationEmailSubjectTemplateText is null and
     * expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure4() {
        instance.setTaskCreationEmailSubjectTemplateText(null);
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when taskCreationEmailSubjectTemplateText is empty and
     * expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure5() {
        instance.setTaskCreationEmailSubjectTemplateText(" ");
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when taskCreationEmailBodyTemplatePath is null and
     * expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure6() {
        instance.setTaskCreationEmailBodyTemplatePath(null);
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when taskCreationEmailBodyTemplatePath is empty and
     * expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure7() {
        instance.setTaskCreationEmailBodyTemplatePath(" ");
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when taskStatusChangeEmailBodyTemplatePath is null and
     * expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure8() {
        instance.setTaskStatusChangeEmailBodyTemplatePath(null);
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when taskStatusChangeEmailBodyTemplatePath is empty and
     * expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure9() {
        instance.setTaskStatusChangeEmailBodyTemplatePath(" ");
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when taskStatusChangeEmailSubjectTemplateText is null and
     * expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure10() {
        instance.setTaskStatusChangeEmailSubjectTemplateText(null);
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#checkInitialization() for failure.
     * It tests the case that when taskStatusChangeEmailSubjectTemplateText is empty and
     * expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure11() {
        instance.setTaskStatusChangeEmailSubjectTemplateText(" ");
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskCreation(long,Task) for failure.
     * It tests the case that when task is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskCreation_NullTask() throws Exception {
        try {
            instance.notifyTaskCreation(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskCreation(long,Task) for failure.
     * It tests the case that when task name is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskCreation_NullTaskName() throws Exception {
        task.setName(null);
        try {
            instance.notifyTaskCreation(1, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskCreation(long,Task) for failure.
     * It tests the case that when task name is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskCreation_EmptyTaskName() throws Exception {
        task.setName(" ");
        try {
            instance.notifyTaskCreation(1, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskCreation(long,Task) for failure.
     * It tests the case that when task Status is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskCreation_NullTaskStatus() throws Exception {
        task.setStatus(null);
        try {
            instance.notifyTaskCreation(1, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskCreation(long,Task) for failure.
     * It tests the case that when task Priority is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskCreation_NullTaskPriority() throws Exception {
        task.setPriority(null);
        try {
            instance.notifyTaskCreation(1, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskCreation(long,Task) for failure.
     * Expects NotificationException.
     * </p>
     */
    public void testNotifyTaskCreation_NotificationException() {
        try {
            instance.notifyTaskCreation(1, task);
            fail("NotificationException expected.");
        } catch (NotificationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskStatusChange(long,TaskStatus,Task) for failure.
     * It tests the case that when oldTask is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskStatusChange_NullOldTask() throws Exception {
        try {
            instance.notifyTaskStatusChange(1, null, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskStatusChange(long,TaskStatus,Task) for failure.
     * It tests the case that when newTask is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskStatusChange_NullNewTask() throws Exception {
        try {
            instance.notifyTaskStatusChange(1, TaskStatus.COMPLETED, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskStatusChange(long,TaskStatus,Task) for failure.
     * It tests the case that when newTask name is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskStatusChange_NullNewTaskName() throws Exception {
        task.setName(null);
        try {
            instance.notifyTaskStatusChange(1, TaskStatus.COMPLETED, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskStatusChange(long,TaskStatus,Task) for failure.
     * It tests the case that when newTask name is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskStatusChange_EmptyNewTaskName() throws Exception {
        task.setName(" ");
        try {
            instance.notifyTaskStatusChange(1, TaskStatus.COMPLETED, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskStatusChange(long,TaskStatus,Task) for failure.
     * It tests the case that when newTask Status is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskStatusChange_NullNewTaskStatus() throws Exception {
        task.setStatus(null);
        try {
            instance.notifyTaskStatusChange(1, TaskStatus.COMPLETED, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskStatusChange(long,TaskStatus,Task) for failure.
     * It tests the case that when newTask CreatedBy is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskStatusChange_NullNewTaskCreatedBy() throws Exception {
        task.setCreatedBy(null);
        try {
            instance.notifyTaskStatusChange(1, TaskStatus.COMPLETED, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskStatusChange(long,TaskStatus,Task) for failure.
     * It tests the case that when newTask CreatedBy is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testNotifyTaskStatusChange_EmptyNewTaskCreatedBy() throws Exception {
        task.setCreatedBy(" ");
        try {
            instance.notifyTaskStatusChange(1, TaskStatus.COMPLETED, task);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests EmailEngineNotificationService#notifyTaskStatusChange(long,TaskStatus,Task) for failure.
     * Expects NotificationException.
     * </p>
     */
    public void testNotifyTaskStatusChange_NotificationException() {
        try {
            instance.notifyTaskStatusChange(1, TaskStatus.COMPLETED, task);
            fail("NotificationException expected.");
        } catch (NotificationException e) {
            //good
        }
    }
}