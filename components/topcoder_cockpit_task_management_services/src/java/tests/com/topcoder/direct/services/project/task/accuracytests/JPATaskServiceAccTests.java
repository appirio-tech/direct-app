/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.impl.JPATaskListService;
import com.topcoder.direct.services.project.task.impl.JPATaskService;
import com.topcoder.direct.services.project.task.model.ContestDTO;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskAttachment;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.log4j.Log4jLogFactory;

/**
 * Accuracy tests for JPATaskService.
 *
 * @author amazingpig
 * @version 1.0
 */
public class JPATaskServiceAccTests extends TestCase {
	/**
     * Represents the JPATaskService instance to test.
     * */
    private JPATaskService instance;
    /**
     * <p>Sets up the unit tests.</p>
     */
    @Override
    public void setUp() throws Exception {
        AccTestHelper.clearUpDataBase();
        AccTestHelper.initDataBase();
        instance = new JPATaskService();
        instance.setAttachmentDirectory("test_files/accuracy/attachmentDirectory");
        instance.setLog(new Log4jLogFactory().createLog("test"));
        instance.setEntityManager(AccTestHelper.entityManager);
        instance.setNotificationService(EasyMock.createNiceMock(NotificationService.class));
        UserService userService = EasyMock.createNiceMock(UserService.class);
        instance.setUserService(userService);

        User user = new User();
        user.setUserId(1);
        user.setHandle("u1");
        EasyMock.expect(userService.getUser(1)).andReturn(user).anyTimes();
        EasyMock.expect(userService.getUserHandle(1)).andReturn("u1").anyTimes();
        EasyMock.expect(userService.getUserHandle(2)).andReturn("u2").anyTimes();
        EasyMock.expect(userService.getEmailAddress(1)).andReturn("to1@tc.com");
        EasyMock.expect(userService.getEmailAddress(2)).andReturn("to2@tc.com");
        EasyMock.replay(userService);


        JPATaskListService listService = new JPATaskListService();
        listService.setLog(new Log4jLogFactory().createLog("test"));
        listService.setEntityManager(AccTestHelper.entityManager);
        listService.setNotificationService(EasyMock.createNiceMock(NotificationService.class));
        listService.setUserService(userService);

        instance.setTaskListService(listService);

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
     * Accuracy test for method addTaskAttachment.
     * @throws Exception
     */
    public void test_add_get_delete_TaskAttachment() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        AccTestHelper.entityManager.getTransaction().begin();
        Task created = instance.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();


        TaskAttachment attachment = new TaskAttachment();
        attachment.setFileName("a.txt");
        attachment.setTaskId(1);
        attachment.setMimeType("txt");
        AccTestHelper.entityManager.getTransaction().begin();
        instance.addTaskAttachment(1, attachment , new ByteArrayInputStream("hello".getBytes()));
        AccTestHelper.entityManager.getTransaction().commit();

        InputStream res = instance.getTaskAttachmentContent(1, attachment.getId());
        byte[] b = new byte[res.available()];
        //the content should be correct
        res.read(b );
        assertEquals(new String(b),"hello");

        AccTestHelper.entityManager.getTransaction().begin();
        instance.deleteTaskAttachment(1, attachment.getId());
        AccTestHelper.entityManager.getTransaction().commit();

        //should be deleted
        res = instance.getTaskAttachmentContent(1, attachment.getId());
        assertNull(res);
    }
    /**
     * Accuracy test for method groupTasksByStatus.
     * @throws Exception
     */
    public void test_groupTasksByStatus() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        Task task2 = new Task();
        task2.setName("name2");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setNotes("n1");
        task2.setPriority(TaskPriority.HIGH);
        task2.setTaskListId(1);

        ContestDTO contest2 = new ContestDTO();
        contest2.setContestId(2);
        List<ContestDTO> associatedToContests2 = Arrays.asList(contest );
        task2.setAssociatedToContests(associatedToContests2);



        Task task3 = new Task();
        task3.setName("name3");
        task3.setStatus(TaskStatus.IN_PROGRESS);
        task3.setNotes("n1");
        task3.setPriority(TaskPriority.HIGH);
        task3.setTaskListId(1);

        ContestDTO contest3 = new ContestDTO();
        contest3.setContestId(2);
        List<ContestDTO> associatedToContests3 = Arrays.asList(contest );
        task3.setAssociatedToContests(associatedToContests3);


        TaskList tl1 = new TaskList();
        tl1.setTasks(Arrays.asList(task, task2));
        TaskList tl2 = new TaskList();
        tl2.setTasks(Arrays.asList(task3));

        task.setCreatedDate(new Date(1));
        task2.setCreatedDate(new Date(2));
        task3.setCreatedDate(new Date(3));
        SortedMap<TaskStatus, List<Task>> res = instance.groupTasksByStatus(Arrays.asList(tl1, tl2));

        assertEquals(res.size(), 2);
        assertEquals(res.get(TaskStatus.COMPLETED).size(), 1);
        assertEquals(res.get(TaskStatus.COMPLETED).get(0).getName(), "name1");
        assertEquals(res.get(TaskStatus.IN_PROGRESS).size(), 2);
        assertEquals(res.get(TaskStatus.IN_PROGRESS).get(0).getName(), "name2");
        assertEquals(res.get(TaskStatus.IN_PROGRESS).get(1).getName(), "name3");
    }


    /**
     * Accuracy test for method groupTasksByStartDate.
     * @throws Exception
     */
    public void test_groupTasksByStartDate() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        Task task2 = new Task();
        task2.setName("name2");
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setNotes("n1");
        task2.setPriority(TaskPriority.HIGH);
        task2.setTaskListId(1);

        ContestDTO contest2 = new ContestDTO();
        contest2.setContestId(2);
        List<ContestDTO> associatedToContests2 = Arrays.asList(contest );
        task2.setAssociatedToContests(associatedToContests2);



        Task task3 = new Task();
        task3.setName("name3");
        task3.setStatus(TaskStatus.COMPLETED);
        task3.setNotes("n1");
        task3.setPriority(TaskPriority.HIGH);
        task3.setTaskListId(1);

        ContestDTO contest3 = new ContestDTO();
        contest3.setContestId(2);
        List<ContestDTO> associatedToContests3 = Arrays.asList(contest );
        task3.setAssociatedToContests(associatedToContests3);


        TaskList tl1 = new TaskList();
        tl1.setTasks(Arrays.asList(task, task2));
        TaskList tl2 = new TaskList();
        tl2.setTasks(Arrays.asList(task3));

        task.setCreatedDate(new Date(1));
        task2.setCreatedDate(new Date(2));
        task3.setCreatedDate(new Date(3));
        task.setStartDate(new Date(10*24*3600L*1000));
        task2.setStartDate(new Date(10*24*3600L*1000));
        Date date = new Date(1000*24*3600L*1000);
        task3.setStartDate(date);
        SortedMap<Date, List<Task>> res = instance.groupTasksByStartDate(Arrays.asList(tl1, tl2));


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(10*24*3600L*1000));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date1 = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(new Date(1000*24*3600L*1000));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date2 = calendar.getTime();

        assertEquals(res.size(), 2);
        assertEquals(res.get(date1).size(), 2);
        assertEquals(res.get(date1).get(0).getName(), "name1");
        assertEquals(res.get(date1).get(1).getName(), "name2");
        assertEquals(res.get(date2).size(), 1);
        assertEquals(res.get(date2).get(0).getName(), "name3");
    }
    /**
     * Accuracy test for method groupTasksByDueDate.
     * @throws Exception
     */
    public void test_groupTasksByDueDate() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        Task task2 = new Task();
        task2.setName("name2");
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setNotes("n1");
        task2.setPriority(TaskPriority.HIGH);
        task2.setTaskListId(1);

        ContestDTO contest2 = new ContestDTO();
        contest2.setContestId(2);
        List<ContestDTO> associatedToContests2 = Arrays.asList(contest );
        task2.setAssociatedToContests(associatedToContests2);



        Task task3 = new Task();
        task3.setName("name3");
        task3.setStatus(TaskStatus.COMPLETED);
        task3.setNotes("n1");
        task3.setPriority(TaskPriority.HIGH);
        task3.setTaskListId(1);

        ContestDTO contest3 = new ContestDTO();
        contest3.setContestId(2);
        List<ContestDTO> associatedToContests3 = Arrays.asList(contest );
        task3.setAssociatedToContests(associatedToContests3);


        TaskList tl1 = new TaskList();
        tl1.setTasks(Arrays.asList(task, task2));
        TaskList tl2 = new TaskList();
        tl2.setTasks(Arrays.asList(task3));

        task.setCreatedDate(new Date(1));
        task2.setCreatedDate(new Date(2));
        task3.setCreatedDate(new Date(3));
        task.setDueDate(new Date(10*24*3600L*1000));
        task2.setDueDate(new Date(1000*24*3600L*1000));
        Date date = new Date(10*24*3600L*1000);
        task3.setDueDate(date);
        SortedMap<Date, List<Task>> res = instance.groupTasksByDueDate(Arrays.asList(tl1, tl2));


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(10*24*3600L*1000));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date1 = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(new Date(1000*24*3600L*1000));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date2 = calendar.getTime();

        assertEquals(res.size(), 2);
        assertEquals(res.get(date1).size(), 2);
        assertEquals(res.get(date1).get(0).getName(), "name1");
        assertEquals(res.get(date1).get(1).getName(), "name3");
        assertEquals(res.get(date2).size(), 1);
        assertEquals(res.get(date2).get(0).getName(), "name2");
    }

    /**
     * Accuracy test for method groupTasksByAssignee.
     * @throws Exception
     */
    public void test_groupTasksByAssignee() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        Task task2 = new Task();
        task2.setName("name2");
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setNotes("n1");
        task2.setPriority(TaskPriority.HIGH);
        task2.setTaskListId(1);

        ContestDTO contest2 = new ContestDTO();
        contest2.setContestId(2);
        List<ContestDTO> associatedToContests2 = Arrays.asList(contest );
        task2.setAssociatedToContests(associatedToContests2);



        Task task3 = new Task();
        task3.setName("name3");
        task3.setStatus(TaskStatus.COMPLETED);
        task3.setNotes("n1");
        task3.setPriority(TaskPriority.HIGH);
        task3.setTaskListId(1);

        ContestDTO contest3 = new ContestDTO();
        contest3.setContestId(2);
        List<ContestDTO> associatedToContests3 = Arrays.asList(contest );
        task3.setAssociatedToContests(associatedToContests3);


        TaskList tl1 = new TaskList();
        tl1.setTasks(Arrays.asList(task, task2));
        TaskList tl2 = new TaskList();
        tl2.setTasks(Arrays.asList(task3));

        task.setCreatedDate(new Date(1));
        task2.setCreatedDate(new Date(2));
        task3.setCreatedDate(new Date(3));
        UserDTO u1 = new UserDTO();
        u1.setUserId(1);
        UserDTO u2 = new UserDTO();
        u2.setUserId(2);
        task.setAssignees(Arrays.asList(u1 , u2));
        task2.setAssignees(Arrays.asList(u1));
        task3.setAssignees(Arrays.asList( u2));
        SortedMap<UserDTO, List<Task>> res = instance.groupTasksByAssignee(Arrays.asList(tl1, tl2));

        assertEquals(res.size(), 2);
        assertEquals(res.get(u1).size(), 2);
        assertEquals(res.get(u1).get(0).getName(), "name1");
        assertEquals(res.get(u1).get(1).getName(), "name2");
        assertEquals(res.get(u2).size(), 2);
        assertEquals(res.get(u2).get(0).getName(), "name1");
        assertEquals(res.get(u2).get(1).getName(), "name3");
    }
    /**
     * Accuracy test for method groupTasksByPriority.
     * @throws Exception
     */
    public void test_groupTasksByPriority() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        Task task2 = new Task();
        task2.setName("name2");
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setNotes("n1");
        task2.setPriority(TaskPriority.LOW);
        task2.setTaskListId(1);

        ContestDTO contest2 = new ContestDTO();
        contest2.setContestId(2);
        List<ContestDTO> associatedToContests2 = Arrays.asList(contest );
        task2.setAssociatedToContests(associatedToContests2);



        Task task3 = new Task();
        task3.setName("name3");
        task3.setStatus(TaskStatus.COMPLETED);
        task3.setNotes("n1");
        task3.setPriority(TaskPriority.HIGH);
        task3.setTaskListId(1);

        ContestDTO contest3 = new ContestDTO();
        contest3.setContestId(2);
        List<ContestDTO> associatedToContests3 = Arrays.asList(contest );
        task3.setAssociatedToContests(associatedToContests3);


        TaskList tl1 = new TaskList();
        tl1.setTasks(Arrays.asList(task, task2));
        TaskList tl2 = new TaskList();
        tl2.setTasks(Arrays.asList(task3));

        task.setCreatedDate(new Date(1));
        task2.setCreatedDate(new Date(2));
        task3.setCreatedDate(new Date(3));
        SortedMap<TaskPriority, List<Task>> res = instance.groupTasksByPriority(Arrays.asList(tl1, tl2));

        assertEquals(res.size(), 2);
        assertEquals(res.get(TaskPriority.LOW).size(), 1);
        assertEquals(res.get(TaskPriority.LOW).get(0).getName(), "name2");
        assertEquals(res.get(TaskPriority.HIGH).size(), 2);
        assertEquals(res.get(TaskPriority.HIGH).get(0).getName(), "name1");
        assertEquals(res.get(TaskPriority.HIGH).get(1).getName(), "name3");
    }

    /**
     * Accuracy test for method addTaskList.
     * @throws Exception
     */
    public void test_addTaskList() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        AccTestHelper.entityManager.getTransaction().begin();
        Task created = instance.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();

        assertEquals(created.getId() > 0, true);
        assertEquals(created.getName(), "name1");
        assertEquals(created.getCreatedBy(), "u1");
        assertEquals(created.getNotes(), "n1");
        assertEquals(created.getAssociatedToContests().get(0).getContestId(), 1);
    }

    /**
     * Accuracy test for method updateTask.
     * @throws Exception
     */
    public void test_updateTask() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = AccTestHelper.entityManager.find(ContestDTO.class, 1L);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        AccTestHelper.entityManager.getTransaction().begin();
        Task created = instance.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();

        created.setName("n2");
        AccTestHelper.entityManager.clear();
        AccTestHelper.entityManager.getTransaction().begin();
        instance.updateTask(1, created);
        AccTestHelper.entityManager.getTransaction().commit();

        assertEquals(created.getId() > 0, true);
        assertEquals(created.getName(), "n2");
        assertEquals(created.getCreatedBy(), "u1");
        assertEquals(created.getNotes(), "n1");
        assertEquals(created.getAssociatedToContests().get(0).getContestId(), 1);
    }
    /**
     * Accuracy test for method getTask.
     * @throws Exception
     */
    public void test_getTask() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        AccTestHelper.entityManager.getTransaction().begin();
        Task created = instance.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();

        Task got = instance.getTask(1, created.getId());

        assertEquals(got.getId() > 0, true);
        assertEquals(got.getName(), "name1");
        assertEquals(got.getCreatedBy(), "u1");
        assertEquals(got.getNotes(), "n1");
        assertEquals(got.getAssociatedToContests().get(0).getContestId(), 1);
    }

    /**
     * Accuracy test for method deleteTask.
     * @throws Exception
     */
    public void test_deleteTask() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        AccTestHelper.entityManager.getTransaction().begin();
        Task created = instance.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();


        AccTestHelper.entityManager.getTransaction().begin();
        instance.deleteTask(1, created.getId());
        AccTestHelper.entityManager.getTransaction().commit();

        Task got = instance.getTask(1, created.getId());

        assertEquals(got, null);
    }

    /**
     * Accuracy test for method getNumberOfAllTasks.
     * @throws Exception
     */
    public void test_getNumberOfAllTasks() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        AccTestHelper.entityManager.getTransaction().begin();
        Task created = instance.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();

        int got = instance.getNumberOfAllTasks(1, 1);

        assertEquals(got, 1);
    }

    /**
     * Accuracy test for method getNumberOfCompletedTasks.
     * @throws Exception
     */
    public void test_getNumberOfCompletedTasks() throws Exception {
        Task task = new Task();
        task.setName("name1");
        task.setStatus(TaskStatus.COMPLETED);
        task.setNotes("n1");
        task.setPriority(TaskPriority.HIGH);
        task.setTaskListId(1);

        ContestDTO contest = new ContestDTO();
        contest.setContestId(1);
        List<ContestDTO> associatedToContests = Arrays.asList(contest );
        task.setAssociatedToContests(associatedToContests );


        AccTestHelper.entityManager.getTransaction().begin();
        Task created = instance.addTask(1, task);
        AccTestHelper.entityManager.getTransaction().commit();

        int got = instance.getNumberOfCompletedTasks(1, 1);

        assertEquals(got, 1);
    }



}
