/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.impl.BaseUnitTests;
import com.topcoder.direct.services.project.task.model.ContestDTO;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskAttachment;
import com.topcoder.direct.services.project.task.model.TaskFilter;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.User;


/**
 * <p>
 * This class shows the main usage of this component.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class DemoTests extends BaseUnitTests {

    /**
     * <p>
     * Instance of TaskListService for the demo.
     * </p>
     */
    private TaskListService taskListService;

    /**
     * <p>
     * Instance of TaskService for the demo.
     * </p>
     */
    private TaskService taskService;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DemoTests.class);
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
        taskListService =
            (TaskListService) super.getApplicationContext().getBean("taskListService");
        taskService =
            (TaskService) super.getApplicationContext().getBean("taskService");
    }

    /**
     * <p>
     * Shows the usage of the task list service.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void testShowTaskListService() throws Exception {
        // prepare the data
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
        taskList = taskListService.addTaskList(user.getUserId(), taskList);

        // update the task list
        taskList.setActive(false);
        taskListService.updateTaskList(user.getUserId(), taskList);

        // get the task list by the id
        taskListService.getTaskList(user.getUserId(), taskList.getId());

        // query the task lists with task
        taskListService.getTaskListsWithTasks(user.getUserId(), new TaskFilter());

        // query the task lists without task
        taskListService.getTaskLists(user.getUserId(), new TaskFilter());

        // resolve the task list
        taskListService.resolveTaskList(user.getUserId(), taskList.getId());

        // delete the task list
        taskListService.deleteTaskList(user.getUserId(), taskList.getId());
    }

    /**
     * <p>
     * Shows the usage of the task service.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void testShowTaskService() throws Exception {
        // prepare the data
        User user = super.createUsers().get(0);
        ContestDTO project = new ContestDTO();
        project.setContestId(123);
        project.setContestName("Test Project");
        persist(project);
        TaskList taskList = new TaskList();
        long projectId = project.getContestId();
        taskList.setProjectId(projectId);
        taskList.setName("Test TaskList");
        taskList = taskListService.addTaskList(user.getUserId(), taskList);

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

        // add the task
        task = taskService.addTask(user.getUserId(), task);

        TaskAttachment attachment = new TaskAttachment();
        attachment.setTaskId(task.getId());
        attachment.setFileName("test_file");
        attachment.setMimeType("text");
        InputStream inputStream = new ByteArrayInputStream("abcde".getBytes());

        // add the task attachment and delete it
        try {
            attachment = taskService.addTaskAttachment(user.getUserId(),
                attachment, inputStream);
            // get the task attachment
            taskService.getTaskAttachmentContent(
                user.getUserId(), attachment.getId());
        } finally {
            taskService.deleteTaskAttachment(
                user.getUserId(), attachment.getId());
        }

        // get the number of tasks
        taskService.getNumberOfAllTasks(user.getUserId(), projectId);
        // get the number of all completed tasks
        taskService.getNumberOfCompletedTasks(
            user.getUserId(), projectId);

        // group by status, others are similar
        List<TaskList> taskLists = taskListService.getTaskListsWithTasks(
            user.getUserId(), null);
        taskService.groupTasksByStatus(taskLists);

        // get the task
        taskService.getTask(user.getUserId(), task.getId());

        // delete the task
        taskService.deleteTask(user.getUserId(), task.getId());
    }

}
