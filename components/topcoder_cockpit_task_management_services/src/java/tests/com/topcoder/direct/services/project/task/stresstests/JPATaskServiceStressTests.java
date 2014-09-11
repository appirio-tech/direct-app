/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.stresstests;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.topcoder.direct.services.project.task.TaskService;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskAttachment;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;

/**
 * <p>
 * Stress test cases for JPATaskService.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class JPATaskServiceStressTests extends TestCase {
	/**
	 * <p>
	 * This constant represents the test count used for testing.
	 * </p>
	 */
	private static final int NUMBER = 1;

	/**
	 * <p>
	 * This constant represents the current time used for testing.
	 * </p>
	 */
	private long current = -1;

	/**
	 * <p>
	 * JPATaskService instance for testing.
	 * </p>
	 */
	private TaskService impl;

	/**
	 * the error.
	 */
	private static Throwable error;

	/**
	 * <p>
	 * Sets up the unit tests.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit.
	 */
	@Override
    @Before
	public void setUp() throws Exception {
		impl = (TaskService) new FileSystemXmlApplicationContext("test_files/stress/spring.xml").getBean("taskService");
		error = null;
		StressHelper.clearDB();
		StressHelper.prepareDB();
	}

	/**
	 * <p>
	 * Cleans up the unit tests.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit.
	 */
	@Override
    @After
	public void tearDown() throws Exception {
		impl = null;
		StressHelper.clearDB();
	}

	/**
	 * <p>
	 * Return all tests.
	 * </p>
	 *
	 * @return all tests
	 */
	public static Test suite() {
		return new TestSuite(JPATaskServiceStressTests.class);
	}




	/**
	 * <p>
	 * Tests JPATaskService#groupTasksByPriority() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_groupTasksByPriority() throws Throwable {
		start();
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER);

		for (int i = 0; i < NUMBER; i++) {
			executorService.submit(new Runnable() {
				/**
				 * <p>
				 * The testing thread running the process method.
				 * </p>
				 */
				public void run() {
					try {
						List<TaskList> taskLists = getTaskLists();
						SortedMap<TaskPriority, List<Task>> ret = impl.groupTasksByPriority(taskLists );
						assertEquals("the result is wrong", 2, ret.size());
						assertEquals("the result is wrong", 5, ret.get(TaskPriority.HIGH).size());
						assertEquals("the result is wrong", 5, ret.get(TaskPriority.LOW).size());

						assertEquals("the result is wrong", TaskPriority.HIGH, ret.get(TaskPriority.HIGH).get(0).getPriority());
						assertEquals("the result is wrong", TaskPriority.HIGH, ret.get(TaskPriority.HIGH).get(1).getPriority());
						assertEquals("the result is wrong", TaskPriority.HIGH, ret.get(TaskPriority.HIGH).get(2).getPriority());
						assertEquals("the result is wrong", TaskPriority.HIGH, ret.get(TaskPriority.HIGH).get(3).getPriority());
						assertEquals("the result is wrong", TaskPriority.HIGH, ret.get(TaskPriority.HIGH).get(4).getPriority());

						assertEquals("the result is wrong", TaskPriority.LOW, ret.get(TaskPriority.LOW).get(0).getPriority());
						assertEquals("the result is wrong", TaskPriority.LOW, ret.get(TaskPriority.LOW).get(1).getPriority());
						assertEquals("the result is wrong", TaskPriority.LOW, ret.get(TaskPriority.LOW).get(2).getPriority());
						assertEquals("the result is wrong", TaskPriority.LOW, ret.get(TaskPriority.LOW).get(3).getPriority());
						assertEquals("the result is wrong", TaskPriority.LOW, ret.get(TaskPriority.LOW).get(4).getPriority());

					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskService#groupTasksByPriority", NUMBER);
	}
	/**
	 * <p>
	 * Tests JPATaskService#groupTasksByDueDate() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_groupTasksByDueDate() throws Throwable {
		start();
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER);

		for (int i = 0; i < NUMBER; i++) {
			executorService.submit(new Runnable() {
				/**
				 * <p>
				 * The testing thread running the process method.
				 * </p>
				 */
				public void run() {
					try {
						List<TaskList> taskLists = getTaskLists();
						SortedMap<Date, List<Task>> ret = impl.groupTasksByDueDate(taskLists );
						assertEquals("the result is wrong", 2, ret.size());
						assertEquals("the result is wrong", 5, ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).size());
						assertEquals("the result is wrong", 5, ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).size());

						assertEquals("the result is wrong", new SimpleDateFormat("yyyyMMdd").parse("20130400"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).get(0).getDueDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130400"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).get(1).getDueDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130400"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).get(2).getDueDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130400"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).get(3).getDueDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130400"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).get(4).getDueDate());

						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130401"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).get(0).getDueDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130401"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).get(1).getDueDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130401"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).get(2).getDueDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130401"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).get(3).getDueDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130401"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).get(4).getDueDate());
					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskService#groupTasksByDueDate", NUMBER);
	}
	/**
	 * <p>
	 * Tests JPATaskService#groupTasksByStartDate() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_groupTasksByStartDate() throws Throwable {
		start();
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER);

		for (int i = 0; i < NUMBER; i++) {
			executorService.submit(new Runnable() {
				/**
				 * <p>
				 * The testing thread running the process method.
				 * </p>
				 */
				public void run() {
					try {
						List<TaskList> taskLists = getTaskLists();
						SortedMap<Date, List<Task>> ret = impl.groupTasksByStartDate(taskLists );
						assertEquals("the result is wrong", 3, ret.size());

						assertEquals("the result is wrong", 4, ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).size());
						assertEquals("the result is wrong", 4, ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).size());
						assertEquals("the result is wrong", 2, ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130402")).size());

						assertEquals("the result is wrong", new SimpleDateFormat("yyyyMMdd").parse("20130400"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).get(0).getStartDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130400"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).get(1).getStartDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130400"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).get(2).getStartDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130400"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130400")).get(3).getStartDate());

						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130401"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).get(0).getStartDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130401"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).get(1).getStartDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130401"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).get(2).getStartDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130401"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130401")).get(3).getStartDate());


						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130402"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130402")).get(0).getStartDate());
						assertEquals("the result is wrong",new SimpleDateFormat("yyyyMMdd").parse("20130402"), ret.get(new SimpleDateFormat("yyyyMMdd").parse("20130402")).get(1).getStartDate());

					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskService#groupTasksByStartDate", NUMBER);
	}
	/**
	 * <p>
	 * Tests JPATaskService#groupTasksByStatus() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_groupTasksByStatus() throws Throwable {
		start();
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER);

		for (int i = 0; i < NUMBER; i++) {
			executorService.submit(new Runnable() {
				/**
				 * <p>
				 * The testing thread running the process method.
				 * </p>
				 */
				public void run() {
					try {
						List<TaskList> taskLists = getTaskLists();
						SortedMap<TaskStatus, List<Task>> ret = impl.groupTasksByStatus(taskLists );
						assertEquals("the result is wrong", 2, ret.size());
						assertEquals("the result is wrong", 5, ret.get(TaskStatus.COMPLETED).size());
						assertEquals("the result is wrong", 5, ret.get(TaskStatus.IN_PROGRESS).size());

						assertEquals("the result is wrong", TaskStatus.COMPLETED, ret.get(TaskStatus.COMPLETED).get(0).getStatus());
						assertEquals("the result is wrong", TaskStatus.COMPLETED, ret.get(TaskStatus.COMPLETED).get(1).getStatus());
						assertEquals("the result is wrong", TaskStatus.COMPLETED, ret.get(TaskStatus.COMPLETED).get(2).getStatus());
						assertEquals("the result is wrong", TaskStatus.COMPLETED, ret.get(TaskStatus.COMPLETED).get(3).getStatus());
						assertEquals("the result is wrong", TaskStatus.COMPLETED, ret.get(TaskStatus.COMPLETED).get(4).getStatus());

						assertEquals("the result is wrong", TaskStatus.IN_PROGRESS, ret.get(TaskStatus.IN_PROGRESS).get(0).getStatus());
						assertEquals("the result is wrong", TaskStatus.IN_PROGRESS, ret.get(TaskStatus.IN_PROGRESS).get(1).getStatus());
						assertEquals("the result is wrong", TaskStatus.IN_PROGRESS, ret.get(TaskStatus.IN_PROGRESS).get(2).getStatus());
						assertEquals("the result is wrong", TaskStatus.IN_PROGRESS, ret.get(TaskStatus.IN_PROGRESS).get(3).getStatus());
						assertEquals("the result is wrong", TaskStatus.IN_PROGRESS, ret.get(TaskStatus.IN_PROGRESS).get(4).getStatus());


					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskService#groupTasksByStatus", NUMBER);
	}
	/**
	 * <p>
	 * Tests JPATaskService#groupTasksByAssignee() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_groupTasksByAssignee() throws Throwable {
		start();
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER);

		for (int i = 0; i < NUMBER; i++) {
			executorService.submit(new Runnable() {
				/**
				 * <p>
				 * The testing thread running the process method.
				 * </p>
				 */
				public void run() {
					try {
						List<TaskList> taskLists = getTaskLists();
						SortedMap<UserDTO, List<Task>> ret = impl.groupTasksByAssignee(taskLists );
						assertEquals("the result is wrong", 2, ret.size());
						Iterator<List<Task>> it = ret.values().iterator();
						List<Task> list1 = it.next();
						List<Task> list2 = it.next();
						assertEquals("the result is wrong", 5,list1.size());
						assertEquals("the result is wrong", 5,list2.size());

						assertEquals("the result is wrong", 0, list1.get(0).getAssignees().get(0).getUserId());
						assertEquals("the result is wrong", 0, list1.get(1).getAssignees().get(0).getUserId());
						assertEquals("the result is wrong", 0, list1.get(2).getAssignees().get(0).getUserId());
						assertEquals("the result is wrong", 0, list1.get(3).getAssignees().get(0).getUserId());
						assertEquals("the result is wrong", 0, list1.get(4).getAssignees().get(0).getUserId());

						assertEquals("the result is wrong", 1, list2.get(0).getAssignees().get(0).getUserId());
						assertEquals("the result is wrong", 1, list2.get(1).getAssignees().get(0).getUserId());
						assertEquals("the result is wrong", 1, list2.get(2).getAssignees().get(0).getUserId());
						assertEquals("the result is wrong", 1, list2.get(3).getAssignees().get(0).getUserId());
						assertEquals("the result is wrong", 1, list2.get(4).getAssignees().get(0).getUserId());

					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskService#groupTasksByAssignee", NUMBER);
	}



	/**
	 * <p>
	 * Tests JPATaskService#curd() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_curd() throws Throwable {
		start();
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER);

		for (int i = 0; i < NUMBER; i++) {
			executorService.submit(new Runnable() {
				/**
				 * <p>
				 * The testing thread running the process method.
				 * </p>
				 */
				public void run() {
					try {
						Task task = new Task();
						task.setName("task1");
						task.setNotes("notes1");
						task.setTaskListId(1);
						task.setPriority(TaskPriority.HIGH);
						task.setStatus(TaskStatus.COMPLETED);
						impl.addTask(1, task);
						task.setNotes("notes2");
						impl.updateTask(1, task);
						Task ret = impl.getTask(1, task.getId());
						assertEquals("the result is wrong", "notes2", ret.getNotes());
						impl.deleteTask(1, task.getId());
						ret= impl.getTask(1, task.getId());
						assertEquals("the result is wrong", null, ret);
					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskService#curd", NUMBER);
	}
	/**
	 * <p>
	 * Tests JPATaskService#attachmentCURD() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_attachmentCURD() throws Throwable {
		start();
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER);

		for (int i = 0; i < NUMBER; i++) {
			executorService.submit(new Runnable() {
				/**
				 * <p>
				 * The testing thread running the process method.
				 * </p>
				 */
				public void run() {
					try {
						TaskAttachment attachment = new TaskAttachment();
						attachment.setFileName("1.jpeg");
						attachment.setMimeType("jpeg");
						attachment.setTaskId(1);
						impl.addTaskAttachment(1, attachment , new FileInputStream("test_files/stress/1.jpeg"));

						InputStream at = impl.getTaskAttachmentContent(1, attachment.getId());
						assertEquals("the result is wrong", at.available(), 20201);

						impl.deleteTaskAttachment(1, attachment.getId());
						 at = impl.getTaskAttachmentContent(1, attachment.getId());
						assertNull("the result is wrong", at);

					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskService#attachmentCURD", NUMBER);
	}
	private List<TaskList> getTaskLists() throws ParseException {
		ArrayList<TaskList> tasklist = new ArrayList<TaskList>();
		TaskList list1 = new TaskList();
		List<Task> tasks = new ArrayList<Task>();
		for (int i = 0; i < 10; i++) {
			Task task1 = new Task();
			task1.setName("task"+i);
			task1.setPriority(i % 2== 0 ? TaskPriority.HIGH : TaskPriority.LOW);
			task1.setStatus(i % 2== 0 ? TaskStatus.COMPLETED : TaskStatus.IN_PROGRESS);
			task1.setStartDate(new SimpleDateFormat("yyyyMMdd").parse("2013040"+(i/4)));
			task1.setDueDate(new SimpleDateFormat("yyyyMMdd").parse("2013040"+(i/5)));
			task1.setCreatedDate(new SimpleDateFormat("yyyyMMdd").parse("2013040"+i));
			List<UserDTO> assignees = new ArrayList<UserDTO>();
			UserDTO dto1 = new UserDTO();
			dto1.setUserId(i  / 5);
			dto1.setHandle("user" + (i/5));
			assignees.add(dto1 );
			task1.setAssignees(assignees );
			tasks.add(task1 );
		}
		list1.setTasks(tasks );
		tasklist.add(list1 );
		return tasklist;
	}
	/**
	 * <p>
	 * Starts to count time.
	 * </p>
	 */
	private void start() {
		current = System.currentTimeMillis();
	}

	/**
	 * <p>
	 * Prints test result.
	 * </p>
	 *
	 * @param name
	 *            the test name
	 * @param count
	 *            the run count
	 */
	private void printResult(String name, long count) {
		System.out.println("The test [" + name + "] run " + count
				+ " times, took time: "
				+ (System.currentTimeMillis() - current) + " ms");
	}

	/**
	 * <p>
	 * Blocks the execution of the current thread until all work thread ends
	 * their execution
	 * </p>
	 *
	 * @param executorService
	 *            the ExecutorService instance
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	private static void awaitTermination(ExecutorService executorService)
			throws Throwable {
		executorService.shutdown();

		while (!executorService.isTerminated()) {
			executorService.awaitTermination(30, TimeUnit.SECONDS);

			if (error != null) {
				throw error;
			}

		}
}
}