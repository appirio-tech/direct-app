/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.stresstests;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.topcoder.direct.services.project.task.TaskListService;
import com.topcoder.direct.services.project.task.model.TaskFilter;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskStatus;

/**
 * <p>
 * Stress test cases for JPATaskListService.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class JPATaskListServiceStressTests extends TestCase {
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
	 * JPATaskListService instance for testing.
	 * </p>
	 */
	private TaskListService impl;

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
		impl = (TaskListService) new FileSystemXmlApplicationContext("test_files/stress/spring.xml").getBean("taskListService");
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
		return new TestSuite(JPATaskListServiceStressTests.class);
	}

	/**
	 * <p>
	 * Tests JPATaskListService#curd() for stress purpose.
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
						TaskList taskList = new TaskList();
						taskList.setProjectId(1);
						taskList.setName("name1");
						taskList.setNotes("notes1");
						impl.addTaskList(1, taskList);
						taskList.setNotes("notes2");
						impl.updateTaskList(1, taskList);
						TaskList ret = impl.getTaskList(1, taskList.getId());
						assertEquals("the result is wrong", "notes2", ret.getNotes());
						impl.deleteTaskList(1, taskList.getId());
						ret= impl.getTaskList(1, taskList.getId());
						assertEquals("the result is wrong", null, ret);
					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskListService#curd", NUMBER);
	}
	/**
	 * <p>
	 * Tests JPATaskListService#getDefaultTaskList() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_getDefaultTaskList() throws Throwable {
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
						TaskList ret= impl.getDefaultTaskList(1, 1);
						assertEquals("the result is wrong", "Project Tasks List", ret.getName());
					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskListService#getDefaultTaskList", NUMBER);
	}
	/**
	 * <p>
	 * Tests JPATaskListService#resolveTaskList() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_resolveTaskList() throws Throwable {
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
						impl.resolveTaskList(1, 1);

						TaskList ret = impl.getTaskList(1, 1);
						assertEquals("the result is wrong", "tasklist1", ret.getName());
						assertEquals("the result is wrong", false, ret.isActive());
						assertEquals("the result is wrong", TaskStatus.COMPLETED, ret.getTasks().get(0).getStatus());
					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskListService#getDefaultTaskList", NUMBER);
	}

	/**
	 * <p>
	 * Tests JPATaskListService#getTaskListsWithTasks() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_getTaskListsWithTasks() throws Throwable {
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
						List<TaskList> ret = impl.getTaskListsWithTasks(1, null);

						assertEquals("the result is wrong", 1, ret.size());


						TaskFilter filter = new TaskFilter();
						filter.setName("task1");
						ret = impl.getTaskListsWithTasks(1, filter );

						assertEquals("the result is wrong", 1, ret.size());
					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("JPATaskListService#getDefaultTaskList", NUMBER);
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