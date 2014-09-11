/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.impl.JPATaskListService;
import com.topcoder.direct.services.project.task.impl.JPATaskService;
import com.topcoder.direct.services.project.task.model.ContestDTO;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskFilter;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.log4j.Log4jLogFactory;

/**
 * Accuracy tests for JPATaskListService.
 *
 * @author amazingpig
 * @version 1.0
 */
public class JPATaskListServiceAccTests extends TestCase {
	/**
     * Represents the JPATaskListService instance to test.
     * */
    private JPATaskListService instance;
    /**
     * <p>Sets up the unit tests.</p>
     */
    @Override
    public void setUp() throws Exception {
        AccTestHelper.clearUpDataBase();
    	AccTestHelper.initDataBase();
        instance = new JPATaskListService();
        instance.setLog(new Log4jLogFactory().createLog("test"));
        instance.setEntityManager(AccTestHelper.entityManager);
        instance.setNotificationService(EasyMock.createNiceMock(NotificationService.class));
    	UserService userService = EasyMock.createNiceMock(UserService.class);
        instance.setUserService(userService);

    	EasyMock.expect(userService.getUser(1)).andReturn(new User()).anyTimes();
    	EasyMock.expect(userService.getUserHandle(1)).andReturn("tc").anyTimes();
    	EasyMock.expect(userService.getEmailAddress(1)).andReturn("to1@tc.com");
    	EasyMock.expect(userService.getEmailAddress(2)).andReturn("to2@tc.com");
    	EasyMock.replay(userService);

    }

    /**
     * <p>Cleans up the unit tests.</p>
     */
    @Override
    public void tearDown() throws Exception {
        instance = null;
        try {
        	if (AccTestHelper.entityManager.getTransaction().isActive()) {
        		AccTestHelper.entityManager.getTransaction().rollback();
        	}
        } catch (Exception e) {
			//ignore
		}
        AccTestHelper.clearUpDataBase();
    }

    /**
     * Accuracy test for method getTaskListsWithTasks.
     * @throws Exception
     */
    public void test_getTaskListsWithTasks() throws Exception {
        TaskList tasklist = new TaskList();
        tasklist.setActive(true);
        tasklist.setProjectId(1);
        tasklist.setName("namefilter");
        tasklist.setDefault(true);
        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        tasklist.setAssociatedToContests(associatedToContests );
        tasklist.setNotes("notes1");
        tasklist.setNumberOfAllTasks(10);
        tasklist.setNumberOfCompletedTasks(1);
        UserDTO u1 = new UserDTO();
        u1.setUserId(1);
        tasklist.setPermittedUsers(Arrays.asList(u1));


        JPATaskService taskService = new JPATaskService();
        taskService.setAttachmentDirectory("test_files/accuracy/attachmentDirectory");
        taskService.setLog(new Log4jLogFactory().createLog("test"));
        taskService.setEntityManager(AccTestHelper.entityManager);
        taskService.setNotificationService(EasyMock.createNiceMock(NotificationService.class));
        UserService userService = EasyMock.createNiceMock(UserService.class);
        taskService.setUserService(userService);

        User user = new User();
        user.setUserId(1);
        user.setHandle("u1");
        EasyMock.expect(userService.getUser(1)).andReturn(user).anyTimes();
        EasyMock.expect(userService.getUserHandle(1)).andReturn("u1").anyTimes();
        EasyMock.expect(userService.getUserHandle(2)).andReturn("u2").anyTimes();
        EasyMock.expect(userService.getEmailAddress(1)).andReturn("to1@tc.com");
        EasyMock.expect(userService.getEmailAddress(2)).andReturn("to2@tc.com");
        EasyMock.replay(userService);

        taskService.setTaskListService(instance);

        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        AccTestHelper.entityManager.getTransaction().begin();
        taskService.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();

        tasklist.setTasks(Arrays.asList(task));
        AccTestHelper.entityManager.getTransaction().begin();
        TaskList created = instance.addTaskList(1, tasklist );
        AccTestHelper.entityManager.getTransaction().commit();

        TaskFilter taskFilter = new TaskFilter();
        List<TaskList> ret = instance.getTaskListsWithTasks(1, taskFilter );

        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0).getId(), 1);
    }


    /**
     * Accuracy test for method getTaskListsWithTasks.
     * @throws Exception
     */
    public void test_getTaskLists() throws Exception {
        TaskList tasklist = new TaskList();
        tasklist.setActive(true);
        tasklist.setProjectId(1);
        tasklist.setName("namefilter");
        tasklist.setDefault(true);
        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        tasklist.setAssociatedToContests(associatedToContests );
        tasklist.setNotes("notes1");
        tasklist.setNumberOfAllTasks(10);
        tasklist.setNumberOfCompletedTasks(1);
        UserDTO u1 = new UserDTO();
        u1.setUserId(1);
        tasklist.setPermittedUsers(Arrays.asList(u1));


        JPATaskService taskService = new JPATaskService();
        taskService.setAttachmentDirectory("test_files/accuracy/attachmentDirectory");
        taskService.setLog(new Log4jLogFactory().createLog("test"));
        taskService.setEntityManager(AccTestHelper.entityManager);
        taskService.setNotificationService(EasyMock.createNiceMock(NotificationService.class));
        UserService userService = EasyMock.createNiceMock(UserService.class);
        taskService.setUserService(userService);

        User user = new User();
        user.setUserId(1);
        user.setHandle("u1");
        EasyMock.expect(userService.getUser(1)).andReturn(user).anyTimes();
        EasyMock.expect(userService.getUserHandle(1)).andReturn("u1").anyTimes();
        EasyMock.expect(userService.getUserHandle(2)).andReturn("u2").anyTimes();
        EasyMock.expect(userService.getEmailAddress(1)).andReturn("to1@tc.com");
        EasyMock.expect(userService.getEmailAddress(2)).andReturn("to2@tc.com");
        EasyMock.replay(userService);

        taskService.setTaskListService(instance);

        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        AccTestHelper.entityManager.getTransaction().begin();
        taskService.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();

        tasklist.setTasks(Arrays.asList(task));
        AccTestHelper.entityManager.getTransaction().begin();
        TaskList created = instance.addTaskList(1, tasklist );
        AccTestHelper.entityManager.getTransaction().commit();

        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setName("1");
        List<TaskList> ret = instance.getTaskListsWithTasks(1, taskFilter );

        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0).getId(), 1);
    }
    /**
     * Accuracy test for method resolveTaskList.
     * @throws Exception
     */
    public void test_resolveTaskList() throws Exception {
        TaskList tasklist = new TaskList();
        tasklist.setActive(true);
        tasklist.setProjectId(1);
        tasklist.setName("name1");
        tasklist.setDefault(true);
        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        tasklist.setAssociatedToContests(associatedToContests );
        tasklist.setNotes("notes1");
        tasklist.setNumberOfAllTasks(10);
        tasklist.setNumberOfCompletedTasks(1);
        UserDTO u1 = new UserDTO();
        u1.setUserId(1);
        tasklist.setPermittedUsers(Arrays.asList(u1));


        JPATaskService taskService = new JPATaskService();
        taskService.setAttachmentDirectory("test_files/accuracy/attachmentDirectory");
        taskService.setLog(new Log4jLogFactory().createLog("test"));
        taskService.setEntityManager(AccTestHelper.entityManager);
        taskService.setNotificationService(EasyMock.createNiceMock(NotificationService.class));
        UserService userService = EasyMock.createNiceMock(UserService.class);
        taskService.setUserService(userService);

        User user = new User();
        user.setUserId(1);
        user.setHandle("u1");
        EasyMock.expect(userService.getUser(1)).andReturn(user).anyTimes();
        EasyMock.expect(userService.getUserHandle(1)).andReturn("u1").anyTimes();
        EasyMock.expect(userService.getUserHandle(2)).andReturn("u2").anyTimes();
        EasyMock.expect(userService.getEmailAddress(1)).andReturn("to1@tc.com");
        EasyMock.expect(userService.getEmailAddress(2)).andReturn("to2@tc.com");
        EasyMock.replay(userService);

        taskService.setTaskListService(instance);

        AccTestHelper.entityManager.getTransaction().begin();
        TaskList created = instance.addTaskList(1, tasklist );
        AccTestHelper.entityManager.getTransaction().commit();

        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(tasklist.getId());

        AccTestHelper.entityManager.getTransaction().begin();
        taskService.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();

        AccTestHelper.entityManager.clear();

        AccTestHelper.entityManager.getTransaction().begin();
        instance.resolveTaskList(1, tasklist.getId());
        AccTestHelper.entityManager.getTransaction().commit();

        AccTestHelper.entityManager.clear();
        tasklist = instance.getTaskList(1, tasklist.getId());
        AccTestHelper.entityManager.clear();
        task = AccTestHelper.entityManager.find(Task.class, task.getId());
        //marking all Tasks in the list as &quot;Completed&quot; and setting the TaskList
        //* as inactive (i.e. archived).
        assertEquals(false, tasklist.isActive());
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }
    /**
     * Accuracy test for method addTaskList.
     * @throws Exception
     */
    public void test_addTaskList() throws Exception {
		TaskList tasklist = new TaskList();
		tasklist.setActive(true);
		tasklist.setProjectId(1);
		tasklist.setName("name1");
		ContestDTO contest = new ContestDTO();
		contest.setContestId(1);
		List<ContestDTO> associatedToContests = Arrays.asList(contest );
		tasklist.setAssociatedToContests(associatedToContests );
		tasklist.setNotes("notes1");
		tasklist.setNumberOfAllTasks(10);
		tasklist.setNumberOfCompletedTasks(1);
		UserDTO u1 = new UserDTO();
		u1.setUserId(1);
		tasklist.setPermittedUsers(Arrays.asList(u1 ));


		AccTestHelper.entityManager.getTransaction().begin();
		TaskList created = instance.addTaskList(1, tasklist );
		AccTestHelper.entityManager.getTransaction().commit();

		assertEquals(created.getId() > 0, true);
		assertEquals(created.getName(), "name1");
		assertEquals(created.getCreatedBy(), "tc");
		assertEquals(created.getNumberOfAllTasks(), 10);
		assertEquals(created.getAssociatedToContests().get(0).getContestId(), 1);
    }


    /**
     * Accuracy test for method updateTaskList.
     * @throws Exception
     */
    public void test_updateTaskList() throws Exception {
		TaskList tasklist = new TaskList();
		tasklist.setActive(true);
		tasklist.setProjectId(1);
		tasklist.setName("name1");
		ContestDTO contest = new ContestDTO();
		contest.setContestId(1);
		List<ContestDTO> associatedToContests = Arrays.asList(contest );
		tasklist.setAssociatedToContests(associatedToContests );
		tasklist.setNotes("notes1");
		tasklist.setNumberOfAllTasks(10);
		tasklist.setNumberOfCompletedTasks(1);
		UserDTO u1 = new UserDTO();
		u1.setUserId(1);
		tasklist.setPermittedUsers(Arrays.asList(u1));


		AccTestHelper.entityManager.getTransaction().begin();
		TaskList created = instance.addTaskList(1, tasklist );
		AccTestHelper.entityManager.getTransaction().commit();


		created.setName("name2");
		created.setNumberOfAllTasks(20);
		AccTestHelper.entityManager.clear();

		AccTestHelper.entityManager.getTransaction().begin();
		instance.updateTaskList(1, created );
		AccTestHelper.entityManager.getTransaction().commit();

		TaskList updated = instance.getTaskList(1, created.getId());
		assertEquals(updated.getId() > 0, true);
		assertEquals(updated.getName(), "name2");
		assertEquals(updated.getCreatedBy(), "tc");
		assertEquals(updated.getNumberOfAllTasks(), 20);
		assertEquals(updated.getLastModifiedBy(), "tc");
    }


    /**
     * Accuracy test for method getTaskList.
     * @throws Exception
     */
    public void test_getTaskList() throws Exception {
        TaskList tasklist = new TaskList();
        tasklist.setActive(true);
        tasklist.setProjectId(1);
        tasklist.setName("name1");
        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        tasklist.setAssociatedToContests(associatedToContests );
        tasklist.setNotes("notes1");
        tasklist.setNumberOfAllTasks(10);
        tasklist.setNumberOfCompletedTasks(1);
        UserDTO u1 = new UserDTO();
        u1.setUserId(1);
        tasklist.setPermittedUsers(Arrays.asList(u1));


        AccTestHelper.entityManager.getTransaction().begin();
        TaskList created = instance.addTaskList(1, tasklist );
        AccTestHelper.entityManager.getTransaction().commit();


        TaskList updated = instance.getTaskList(1, created.getId());
        assertEquals(updated.getId() > 0, true);
        assertEquals(updated.getName(), "name1");
        assertEquals(updated.getCreatedBy(), "tc");
        assertEquals(updated.getNumberOfAllTasks(), 10);
    }

    /**
     * Accuracy test for method deleteTaskList.
     * @throws Exception
     */
    public void test_deleteTaskList() throws Exception {
        TaskList tasklist = new TaskList();
        tasklist.setActive(true);
        tasklist.setProjectId(1);
        tasklist.setName("name1");
        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        tasklist.setAssociatedToContests(associatedToContests );
        tasklist.setNotes("notes1");
        tasklist.setNumberOfAllTasks(10);
        tasklist.setNumberOfCompletedTasks(1);
        UserDTO u1 = new UserDTO();
        u1.setUserId(1);
        tasklist.setPermittedUsers(Arrays.asList(u1));

        AccTestHelper.entityManager.getTransaction().begin();
        TaskList created = instance.addTaskList(1, tasklist );
        AccTestHelper.entityManager.getTransaction().commit();

        AccTestHelper.entityManager.getTransaction().begin();
         instance.deleteTaskList(1, created.getId());
         AccTestHelper.entityManager.getTransaction().commit();
        TaskList updated = instance.getTaskList(1, created.getId());
        assertEquals(updated, null);
    }
    /**
     * Accuracy test for method getDefaultTaskList.
     * @throws Exception
     */
    public void test_getDefaultTaskList() throws Exception {
        TaskList tasklist = new TaskList();
        tasklist.setActive(true);
        tasklist.setProjectId(1);
        tasklist.setName("name1");
        tasklist.setDefault(true);
        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        tasklist.setAssociatedToContests(associatedToContests );
        tasklist.setNotes("notes1");
        tasklist.setNumberOfAllTasks(10);
        tasklist.setNumberOfCompletedTasks(1);
        UserDTO u1 = new UserDTO();
        u1.setUserId(1);
        tasklist.setPermittedUsers(Arrays.asList(u1));


        AccTestHelper.entityManager.getTransaction().begin();
        TaskList created = instance.addTaskList(1, tasklist );
        AccTestHelper.entityManager.getTransaction().commit();

        TaskList defaultlist = instance.getDefaultTaskList(1, 1);
        assertEquals(created.getId(), defaultlist.getId());
    }

}
