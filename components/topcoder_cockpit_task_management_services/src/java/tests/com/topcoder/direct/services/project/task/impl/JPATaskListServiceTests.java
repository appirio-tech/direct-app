/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.topcoder.direct.services.project.task.EntityNotFoundException;
import com.topcoder.direct.services.project.task.NotificationException;
import com.topcoder.direct.services.project.task.NotificationService;
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
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserService;

/**
 * <p>
 * All unit tests for class: <code>JPATaskListService</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class JPATaskListServiceTests extends BaseUnitTests {


    /**
     * <p>
     * Represents the instance of TaskListService for unit tests.
     * </p>
     */
    private TaskListService taskListService;

    /**
     * <p>
     * Represents the bean for failure tests.
     * </p>
     */
    private JPATaskListService beanFailure;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JPATaskListServiceTests.class);
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
        taskListService = (TaskListService) super.getApplicationContext().getBean("taskListService");
        beanFailure = new JPATaskListService();
        beanFailure.setEntityManager(super.getEntityManager());
        NotificationService notificationService =
            (NotificationService) super.getApplicationContext().getBean("notificationService");
        UserService userService =
            (UserService) super.getApplicationContext().getBean("mockUserService");
        beanFailure.setNotificationService(notificationService);
        beanFailure.setUserService(userService);
    }

    /**
     * <p>
     * Tests the constructor: {@link JPATaskListService#JPATaskListService()}.
     * </p>
     * Accuracy test to check if the instance of JPATaskListService can be created correctly.
     */
    @Test
    public void testJPATaskListService() {
        JPATaskListService instance = new JPATaskListService();
        assertNotNull("The instance is not created.", instance);
        assertTrue("The instance cannot be created properly.", instance instanceof JPATaskListService);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#addTaskList(long, TaskList)}.
     * </p>
     * Accuracy test to check if the taskList can be added.
     * @throws Exception to JUnit.
     */
    @Test
    public void testAddTaskList() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // find the taskList from the database
        result = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("The name of the task should be equal.", result.getName(), taskList.getName());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#addTaskList(long, TaskList)}.
     * </p>
     * If the task list is null, IllegalArgumentException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddTaskListNullTaskList() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        taskListService.addTaskList(user.getUserId(), null);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#addTaskList(long, TaskList)}.
     * </p>
     * If the user does not exist, PermissionException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testAddTaskListNotPermitted() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        taskListService.addTaskList(user.getUserId() + 10001, taskList);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#addTaskList(long, TaskList)}.
     * </p>
     * Accuracy test to check if the taskList can be added, mainly checking the boolean values.
     * @throws Exception to JUnit.
     */
    @Test
    public void testAddTaskListBooleanFields() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");

        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        taskList.setActive(true);
        taskList.setDefault(true);
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // find the taskList from the database
        result = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("The name of the task should be equal.", result.getName(), taskList.getName());
        assertEquals("Should be active.", true, taskList.isActive());
        assertEquals("Should be default.", true, taskList.isDefault());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#updateTaskList(long, TaskList)}.
     * </p>
     * <p>
     * Accuracy test to check if the taskList can be updated.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void testUpdateTaskList() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);

        // update the name of the taskList
        result.setName("Test Update Project");
        // update it
        taskListService.updateTaskList(users.get(1).getUserId(), result);

        // retrieve it from database
        TaskList updatedResult = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("The name of the taskList should be updated.", "Test Update Project", updatedResult.getName());
        assertEquals("The modifier of the taskList should be updated.",
            users.get(1).getHandle(), updatedResult.getLastModifiedBy());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#updateTaskList(long, TaskList)}.
     * </p>
     * <p>
     * If the task list cannot be found, EntityNotFoundException is excpected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testUpdateTaskListOldTaskListNotFound() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        taskList.setId(123321);
        taskListService.updateTaskList(user.getUserId(), taskList);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#updateTaskList(long, TaskList)}.
     * </p>
     * <p>
     * If the task list is null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testUpdateTaskListNullTaskList() throws Exception {
        List<User> users = super.createUsers();
        // update it
        taskListService.updateTaskList(users.get(1).getUserId(), null);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is properly retrieved.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListAccuracy() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // find the taskList from the database
        result = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("The name of the task should be equal.", result.getName(), taskList.getName());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is not exist, null should be returned.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListNotExist() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        TaskList result = taskListService.getTaskList(user.getUserId(), 10001);
        assertNull("The id of the taskList should be updated.", result);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is properly retrieved if the operator is the creator.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListPermittedByCreator() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // find the taskList from the database
        result = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("The name of the task should be equal.", result.getName(), taskList.getName());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is properly retrieved if the task list is public to everyone.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListPermittedByPublic() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // find the taskList from the database
        result = taskListService.getTaskList(users.get(1).getUserId(), result.getId());
        assertEquals("The name of the task should be equal.", result.getName(), taskList.getName());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is properly retrieved if the operator is in the permitted list.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListPermittedByPermittedUsers() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        List<UserDTO> permittedUsers = new ArrayList<UserDTO>();
        UserDTO dto = new UserDTO();
        dto.setHandle(users.get(2).getHandle());
        dto.setUserId(users.get(2).getUserId());
        permittedUsers.add(dto);
        taskList.setPermittedUsers(permittedUsers);
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // find the taskList from the database
        result = taskListService.getTaskList(users.get(2).getUserId(), result.getId());
        assertEquals("The name of the task should be equal.", result.getName(), taskList.getName());
        // check if the permitted list is correct
        assertEquals("should contains the permitted users", 1, result.getPermittedUsers().size());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * If the operator is not permitted, PermissionException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetTaskListNotPermitted() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        List<UserDTO> permittedUsers = new ArrayList<UserDTO>();
        UserDTO dto = new UserDTO();
        dto.setHandle(users.get(2).getHandle());
        dto.setUserId(users.get(2).getUserId());
        permittedUsers.add(dto);
        taskList.setPermittedUsers(permittedUsers);
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // the user is not permitted
        taskListService.getTaskList(users.get(3).getUserId(), result.getId());

    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is properly retrieved, and mainly test the task list.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListAccuracyTasks() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // create a task for that taskList
        Task task = new Task();
        task.setTaskListId(result.getId());
        task.setName("Task1");
        task.setCreatedBy(user.getHandle());
        task.setCreatedDate(new Date());
        task.setStatus(TaskStatus.COMPLETED);
        task.setPriority(TaskPriority.NORMAL);
        persist(task);

        // find the taskList from the database
        result = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("should contains the task.", 1, result.getTasks().size());
        Task task2 = result.getTasks().get(0);
        assertEquals("should be equal", task.getStatus(), task2.getStatus());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is properly retrieved, and mainly test the permitted user list.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListAccuracyPermittedUsers() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        List<UserDTO> permittedUsers = new ArrayList<UserDTO>();
        UserDTO dto = new UserDTO();
        dto.setHandle(users.get(2).getHandle());
        dto.setUserId(users.get(2).getUserId());
        permittedUsers.add(dto);
        taskList.setPermittedUsers(permittedUsers);
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // find the taskList from the database
        result = taskListService.getTaskList(users.get(2).getUserId(), result.getId());
        assertEquals("The name of the task should be equal.", result.getName(), taskList.getName());
        // check if the permitted list is correct
        assertEquals("should contains the permitted users", 1, result.getPermittedUsers().size());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is properly retrieved, and mainly test the number of all tasks.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListAccuracyNumberOfAllTasks() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // create a task for that taskList
        Task task1 = new Task();
        task1.setTaskListId(result.getId());
        task1.setName("Task1");
        task1.setCreatedBy(user.getHandle());
        task1.setCreatedDate(new Date());
        task1.setStatus(TaskStatus.COMPLETED);
        task1.setPriority(TaskPriority.NORMAL);
        persist(task1);

        Task task2 = new Task();
        task2.setTaskListId(result.getId());
        task2.setName("Task2");
        task2.setCreatedBy(user.getHandle());
        task2.setCreatedDate(new Date());
        task2.setStatus(TaskStatus.NOT_STARTED);
        task2.setPriority(TaskPriority.NORMAL);
        persist(task2);

        Task task3 = new Task();
        task3.setTaskListId(result.getId());
        task3.setName("Task3");
        task3.setCreatedBy(user.getHandle());
        task3.setCreatedDate(new Date());
        task3.setStatus(TaskStatus.COMPLETED);
        task3.setPriority(TaskPriority.NORMAL);
        persist(task3);

        // find the taskList from the database
        result = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("should contains 3 tasks.", 3, result.getNumberOfAllTasks());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is properly retrieved, and mainly test the number of completed tasks.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListAccuracyNumberOfCompletedTasks() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // create a task for that taskList
        Task task1 = new Task();
        task1.setTaskListId(result.getId());
        task1.setName("Task1");
        task1.setCreatedBy(user.getHandle());
        task1.setCreatedDate(new Date());
        task1.setStatus(TaskStatus.COMPLETED);
        task1.setPriority(TaskPriority.NORMAL);
        persist(task1);

        Task task2 = new Task();
        task2.setTaskListId(result.getId());
        task2.setName("Task2");
        task2.setCreatedBy(user.getHandle());
        task2.setCreatedDate(new Date());
        task2.setStatus(TaskStatus.NOT_STARTED);
        task2.setPriority(TaskPriority.NORMAL);
        persist(task2);

        Task task3 = new Task();
        task3.setTaskListId(result.getId());
        task3.setName("Task3");
        task3.setCreatedBy(user.getHandle());
        task3.setCreatedDate(new Date());
        task3.setStatus(TaskStatus.COMPLETED);
        task3.setPriority(TaskPriority.NORMAL);
        persist(task3);

        // find the taskList from the database
        result = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("should contains 2 completed tasks.", 2, result.getNumberOfCompletedTasks());
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list is properly retrieved, and mainly test the contests and milestones.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetTaskListAccuracyContestMileStore() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        List<ContestDTO> associatedToContests = new ArrayList<ContestDTO>();
        associatedToContests.add(project);
        taskList.setAssociatedToContests(associatedToContests);

        List<MilestoneDTO> associatedToProjectMilestones = new ArrayList<MilestoneDTO>();
        MilestoneDTO milestone = new MilestoneDTO();
        milestone.setMilestoneName("milestone1");
        milestone.setMilestoneId(456);
        persist(milestone);
        associatedToProjectMilestones.add(milestone);
        taskList.setAssociatedToProjectMilestones(associatedToProjectMilestones);
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // find the taskList from the database
        result = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("should contains 1 contest.", 1, result.getAssociatedToContests().size());
        assertEquals("should contains 1 milestone.", "milestone1",
            result.getAssociatedToProjectMilestones().get(0).getMilestoneName());
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getDefaultTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the default task list can be retrieved if there is no default task list.
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetDefaultTaskListNoDefault() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = taskListService.getDefaultTaskList(user.getUserId(), project.getContestId());
        assertEquals("should has the correct name", "Project Tasks List", taskList.getName());
        assertEquals("should be default", true, taskList.isDefault());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getDefaultTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the default task list can be retrieved if there is default task list existed.
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetDefaultTaskListExistDefault() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = taskListService.getDefaultTaskList(user.getUserId(), project.getContestId());
        assertEquals("should has the correct name", "Project Tasks List", taskList.getName());

        // should return the previous task list
        taskList = taskListService.getDefaultTaskList(user.getUserId(), project.getContestId());
        assertEquals("should has the correct name", "Project Tasks List", taskList.getName());
        assertEquals("should be default", true, taskList.isDefault());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#deleteTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task list can be removed.
     * @throws Exception to JUnit.
     */
    @Test
    public void testDeleteTaskList() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");

        // add the task list
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);

        // remove the task list
        taskListService.deleteTaskList(user.getUserId(), result.getId());

        // check the existence
        assertNull("The task list should be removed.",
            taskListService.getTaskList(user.getUserId(), taskList.getId()));
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#deleteTaskList(long, long)}.
     * </p>
     * If the task list does not exist, exception should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testDeleteTaskListNotExist() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);

        // remove the task list
        taskListService.deleteTaskList(user.getUserId(), 1);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#resolveTaskList(long, long)}.
     * </p>
     * Accuracy test to check if the task can be resolved.
     * @throws Exception to JUnit.
     */
    @Test
    public void testResolveTaskList() throws Exception  {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // create a task for that taskList
        Task task = new Task();
        task.setTaskListId(result.getId());
        task.setName("Task1");
        task.setCreatedBy(user.getHandle());
        task.setCreatedDate(new Date());
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(TaskPriority.NORMAL);
        persist(task);

        Task task2 = new Task();
        task2.setTaskListId(result.getId());
        task2.setName("Task2");
        task2.setCreatedBy(user.getHandle());
        task2.setCreatedDate(new Date());
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(TaskPriority.NORMAL);
        persist(task2);

        // resolve the task list
        taskListService.resolveTaskList(user.getUserId(), result.getId());

        // check the status list
        result = taskListService.getTaskList(user.getUserId(), result.getId());
        assertEquals("Should not be active.", false, result.isActive());
        assertEquals("Should have 2 tasks.", 2, result.getTasks().size());
        for (Task task3 : result.getTasks()) {
            assertEquals("should be completed.", TaskStatus.COMPLETED, task3.getStatus());
        }
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#resolveTaskList(long, long)}.
     * </p>
     * If the task list does not exist, EntityNotFoundException is expected.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testResolveTaskListNotExist() throws Exception  {
        List<User> users = super.createUsers();
        User user = users.get(0);

        // resolve the task list
        taskListService.resolveTaskList(user.getUserId(), 100001);

    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * Accuracy test if the task is properly retrieved when the filter is null.
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskLists() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), null);
        assertEquals("should have 8 result.", 8, result.size());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when task name in the filter is null.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsNullTaskName() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertEquals("should have 8 result.", 8, result.size());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when task name in the filter is not exist.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsTaskNameNotExist() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setName("NOT_EXIST");
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertEquals("not exist task name.", 0, result.size());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when assignee in the filter does not exist.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsTaskAssigneeIdNotExist() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setAssigneeId(new Long(100001));
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertEquals("not exist task assignee.", 0, result.size());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when assignee in the filter exists.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsTaskAssigneeIdExist() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setAssigneeId(users.get(0).getUserId());
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist task assignee.", result.size() > 0);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when the dueDateFrom or dueDateTo is set.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsTaskDueDate() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setDueDateFrom(new Date());
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist task from due date.", result.size() > 0);

        taskFilter.setDueDateTo(new Date(new Date().getTime() + 100L * 86400L * 1000L));
        result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist task to duedate.", result.size() > 0);

        // not exists
        taskFilter.setDueDateTo(new Date(new Date().getTime() + 5L * 86400L * 1000L));
        result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("does not exist task to duedate.", result.size() == 0);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when the status is set.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsTaskStatus() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        List<TaskStatus> statuses = new ArrayList<TaskStatus>();
        statuses.add(TaskStatus.COMPLETED);
        statuses.add(TaskStatus.WAIT_ON_DEPENDENCY);
        taskFilter.setStatuses(statuses);
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist task status.", result.size() > 0);

        statuses = new ArrayList<TaskStatus>();
        statuses.add(TaskStatus.WAIT_ON_DEPENDENCY);
        taskFilter.setStatuses(statuses);
        result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("not exist task status.", result.size() == 0);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when the priority is set.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsTaskPriority() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        List<TaskPriority> priorities = new ArrayList<TaskPriority>();
        priorities.add(TaskPriority.HIGH);
        priorities.add(TaskPriority.LOW);
        taskFilter.setPriorities(priorities);
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist task priority.", result.size() > 0);

        priorities = new ArrayList<TaskPriority>();
        priorities.add(TaskPriority.NORMAL);
        taskFilter.setPriorities(priorities);
        result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("not exist task priority.", result.size() == 0);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when the milestones is set.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsMilestones() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        List<Long> milestones = getEntityManager().createQuery(
            "select milestone.milestoneId from MilestoneDTO milestone").getResultList();
        taskFilter.setAssociatedToProjectMilestoneIds(milestones);
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist milestone.", result.size() > 0);

        milestones = new ArrayList<Long>();
        taskFilter.setAssociatedToProjectMilestoneIds(milestones);
        result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("empty milestone.", result.size() > 0);

        milestones = new ArrayList<Long>();
        milestones.add(123321L);
        taskFilter.setAssociatedToProjectMilestoneIds(milestones);
        result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("not exist milestone.", result.size() == 0);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when the contests is set.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsContests() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        List<Long> contests = getEntityManager().createQuery(
            "select contest.contestId from ContestDTO contest").getResultList();
        taskFilter.setAssociatedToContestIds(contests);
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist contest.", result.size() > 0);

        contests = new ArrayList<Long>();
        taskFilter.setAssociatedToContestIds(contests);
        result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("empty contest.", result.size() > 0);

        contests = new ArrayList<Long>();
        contests.add(123321L);
        taskFilter.setAssociatedToContestIds(contests);
        result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("not exist contest.", result.size() == 0);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when the projectIds is set.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsProjectIds() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        List<Long> contests = getEntityManager().createQuery(
            "select contest.contestId from ContestDTO contest").getResultList();
        List<Long> projectIds = new ArrayList<Long>();
        projectIds.add(contests.get(0));
        taskFilter.setProjectIds(projectIds);
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist projects.", result.size() > 0);
        for (TaskList taskList : result) {
            assertEquals("should be equal project", contests.get(0),  new Long(taskList.getProjectId()));
        }
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when the isActive is set.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsActive() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setProjectActive(true);
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist projects.", result.size() > 0);
        for (TaskList taskList : result) {
            assertEquals("should be active project", true,  taskList.isActive());
        }
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when several fields are set.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsCombine() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setProjectActive(true);
        List<Long> projectIds = new ArrayList<Long>();
        List<Long> contests = getEntityManager().createQuery(
            "select contest.contestId from ContestDTO contest").getResultList();
        projectIds.add(contests.get(0));
        taskFilter.setProjectIds(projectIds);
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist projects.", result.size() > 0);
        for (TaskList taskList : result) {
            assertEquals("should be active project", true,  taskList.isActive());
            assertEquals("should be equal project", contests.get(0),  new Long(taskList.getProjectId()));
        }
    }
    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     *
     * Accuracy test if the task is properly retrieved when task name in the filter is null.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsTaskName() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setName("COMPLETED");
        List<TaskList> result = taskListService.getTaskLists(users.get(0).getUserId(), taskFilter);
        assertTrue("exist task name.", result.size() > 0);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskListsWithTasks(long, TaskFilter)}.
     * </p>
     *
     * Accuracy test to check if the tasks can be loaded.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTaskListsWithTasks() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        List<UserDTO> users = query.getResultList();
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setName("COMPLETED");
        List<TaskList> result = taskListService.getTaskListsWithTasks(users.get(0).getUserId(), taskFilter);
        assertTrue("exist task name.", result.size() > 0);
        for (TaskList taskList : result) {
            List<Task> tasks = taskList.getTasks();
            boolean containsCompleted = false;
            for (Task task : tasks) {
                // check the task name
                assertTrue("Should contains the name.", task.getName().startsWith("Task Name"));
                if (task.getStatus() == TaskStatus.COMPLETED) {
                    containsCompleted = true;
                }
            }
            assertTrue("should contains complete tasks.", containsCompleted);
        }
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskListsWithTasks(long, TaskFilter)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetTaskListsWithTasksPermissionError() throws Exception {
        taskListService.getTaskListsWithTasks(10001, null);
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskLists(long, TaskFilter)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetTaskListsWithPermissionError() throws Exception {
        taskListService.getTaskLists(10001, null);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#resolveTaskList(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testResolveTaskListPermissionError() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // create a task for that taskList
        Task task = new Task();
        task.setTaskListId(result.getId());
        task.setName("Task1");
        task.setCreatedBy(user.getHandle());
        task.setCreatedDate(new Date());
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(TaskPriority.NORMAL);
        persist(task);

        Task task2 = new Task();
        task2.setTaskListId(result.getId());
        task2.setName("Task2");
        task2.setCreatedBy(user.getHandle());
        task2.setCreatedDate(new Date());
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(TaskPriority.NORMAL);
        persist(task2);

        // resolve the task list
        taskListService.resolveTaskList(user.getUserId() + 1000, result.getId());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#resolveTaskList(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void testResolveTaskListPersistenceError() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // create a task for that taskList
        Task task = new Task();
        task.setTaskListId(result.getId());
        task.setName("Task1");
        task.setCreatedBy(user.getHandle());
        task.setCreatedDate(new Date());
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(TaskPriority.NORMAL);
        persist(task);

        Task task2 = new Task();
        task2.setTaskListId(result.getId());
        task2.setName("Task2");
        task2.setCreatedBy(user.getHandle());
        task2.setCreatedDate(new Date());
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(TaskPriority.NORMAL);
        persist(task2);

        // resolve the task list
        beanFailure.resolveTaskList(user.getUserId(), result.getId());
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#resolveTaskList(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = NotificationException.class)
    public void testResolveTaskListNotificationError() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);
        assertTrue("The id of the taskList should be updated.", result.getId() > 0);

        // create a task for that taskList
        Task task = new Task();
        task.setTaskListId(result.getId());
        task.setName("Task1");
        task.setCreatedBy(user.getHandle());
        task.setCreatedDate(new Date());
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(TaskPriority.NORMAL);
        persist(task);

        Task task2 = new Task();
        task2.setTaskListId(result.getId());
        task2.setName("Task2");
        task2.setCreatedBy(user.getHandle());
        task2.setCreatedDate(new Date());
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(TaskPriority.NORMAL);
        persist(task2);

        NotificationService notificationService = Mockito.mock(NotificationService.class);
        Mockito.doThrow(NotificationException.class).when(notificationService).notifyTaskStatusChange(
            Mockito.anyLong(), Mockito.any(TaskStatus.class), Mockito.any(Task.class));
        beanFailure.setNotificationService(notificationService);

        try {
            // resolve the task list
            super.getEntityManager().getTransaction().begin();
            beanFailure.resolveTaskList(user.getUserId(), result.getId());
            super.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            super.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#deleteTaskList(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testDeleteTaskListPermissionError() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");

        // add the task list
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);

        // remove the task list
        taskListService.deleteTaskList(user.getUserId() + 1000, result.getId());
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getDefaultTaskList(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetDefaultTaskListPermissionError() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        taskListService.getDefaultTaskList(user.getUserId() + 1000, project.getContestId());
    }


    /**
     * <p>
     * Tests the method: {@link JPATaskListService#getTaskList(long, long)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetTaskListWithPermissionError() throws Exception {
        createTaskLists();
        // get a user
        Query query = super.getEntityManager().createQuery("select u from UserDTO u");
        @SuppressWarnings("unchecked")
        List<UserDTO> users = query.getResultList();
        taskListService.getTaskLists(users.get(0).getUserId() + 1000, null);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#updateTaskList(long, TaskList)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void testUpdateTaskListWithPersistenceError() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);

        // update the name of the taskList
        result.setName("Test Update Project");
        // update it
        beanFailure.updateTaskList(users.get(1).getUserId(), result);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#updateTaskList(long, TaskList)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testUpdateTaskListWithPermissionError() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        TaskList result = taskListService.addTaskList(user.getUserId(), taskList);

        // update the name of the taskList
        result.setName("Test Update Project");
        // update it
        taskListService.updateTaskList(users.get(1).getUserId() + 1000, result);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#addTaskList(long, TaskList)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void testAddTaskListWithPersistenceError() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        beanFailure.addTaskList(user.getUserId(), taskList);
    }

    /**
     * <p>
     * Tests the method: {@link JPATaskListService#addTaskList(long, TaskList)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testAddTaskListWithPermissionError() throws Exception {
        List<User> users = super.createUsers();
        User user = users.get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);

        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        taskListService.addTaskList(user.getUserId() + 10001, taskList);
    }


    /**
     * <p>
     * Creates the task lists for unit tests.
     * </p>
     * @throws Exception to JUnit.
     */
    private void createTaskLists() throws Exception {
        List<User> users = super.createUsers(2);
        List<ContestDTO> contests = createContests();
        List<MilestoneDTO> milestones = createMilestones(contests);

        for (User user : users) {
            for (int i = 0; i < 2; i++) {
                createTaskLists(user, users, contests, milestones);
            }
        }
    }


    /**
     * <p>
     * Creates the mile stones for the given contests.
     * </p>
     * @param contests the contests to create.
     * @return the milestones created.
     */
    private List<MilestoneDTO> createMilestones(List<ContestDTO> contests) {
        int id = 1;
        List<MilestoneDTO> milestones = new ArrayList<MilestoneDTO>();
        for (int i = 0; i < contests.size(); i++) {
            for (int j = 0; j < 2; j++) {
                MilestoneDTO milestone = new MilestoneDTO();
                milestone.setMilestoneId(id++);
                milestone.setMilestoneName("Milestone: " + id);
                persist(milestone);
                milestones.add(milestone);
            }
        }
        return milestones;
    }


    /**
     * <p>
     * Creates the contests.
     * </p>
     * @return the created contests.
     */
    private List<ContestDTO> createContests() {
        List<ContestDTO> contests = new ArrayList<ContestDTO>();
        for (int i = 0; i < 2; i++) {
            ContestDTO contest = new ContestDTO();
            contest.setContestId(i + 1);
            contest.setContestName("Contest " + i + 1);
            persist(contest);
            contests.add(contest);
        }
        return contests;
    }


    /**
     * <p>
     * Creates the task lists.
     * </p>
     * @param user the user to create the task lists.
     * @param users the users to be the assignees.
     * @param contests the contests to associate.
     * @param milestones the milestones to associate.
     * @throws Exception to JUnit.
     */
    private void createTaskLists(User user, List<User> users,
        List<ContestDTO> contests, List<MilestoneDTO> milestones) throws Exception {
        createTaskList(user, users, contests, milestones, true);
        createTaskList(user, users, contests, milestones, false);
    }


    /**
     * <p>
     * Create a task list.
     * </p>
     * @param user the user to create the task list.
     * @param users the list of the users.
     * @param contests the list of the contests.
     * @param milestones the list of the milestones.
     * @param isActive if true, the task list is active, otherwise false.
     * @throws Exception to JUnit.
     */
    private void createTaskList(User user, List<User> users,
        List<ContestDTO> contests, List<MilestoneDTO> milestones, boolean isActive) throws Exception {
        TaskList taskList = new TaskList();
        taskList.setActive(isActive);
        taskList.setAssociatedToContests(contests);
        taskList.setAssociatedToProjectMilestones(milestones);
        taskList.setCreatedBy(user.getHandle());
        taskList.setCreatedDate(new Date());
        taskList.setName("Acive:" + isActive + " taskList");
        List<UserDTO> permittedUsers = new ArrayList<UserDTO>();
        for (User u : users) {
            UserDTO dto = new UserDTO();
            dto.setHandle(u.getHandle());
            dto.setUserId(u.getUserId());
            permittedUsers.add(dto);
        }
        taskList.setPermittedUsers(permittedUsers);
        taskList.setProjectId(contests.get(0).getContestId());

        taskList = taskListService.addTaskList(user.getUserId(), taskList);

        // create the tasks
        TaskStatus[] statuses = new TaskStatus[] {TaskStatus.COMPLETED, TaskStatus.IN_PROGRESS};

        TaskPriority[] priorities = new TaskPriority[] {TaskPriority.HIGH, TaskPriority.LOW};

        for (TaskStatus status : statuses) {
            for (TaskPriority priority : priorities) {
                Task task = new Task();
                task.setStatus(status);
                task.setAssignees(permittedUsers);
                task.setAssociatedToContests(contests);
                task.setAssociatedToProjectMilestones(milestones);
                task.setPriority(priority);
                task.setCreatedBy(user.getHandle());
                task.setCreatedDate(new Date());
                task.setStartDate(new Date());
                task.setDueDate(new Date(new Date().getTime() + 86400L * 1000L * 10));
                task.setTaskListId(taskList.getId());
                task.setName("Task Name{" + taskList.getId() + "," + status + "," + priority);
                persist(task);
            }
        }
    }
}
