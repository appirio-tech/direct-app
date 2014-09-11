/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.stresstests;

import java.util.ArrayList;
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

import com.topcoder.direct.services.project.task.impl.EmailEngineNotificationService;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;

/**
 * <p>
 * Stress test cases for EmailEngineNotificationService.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class EmailEngineNotificationServiceStressTests extends TestCase {
	/**
	 * <p>
	 * This constant represents the test count used for testing.
	 * </p>
	 */
	private static final int NUMBER = 10;

	/**
	 * <p>
	 * This constant represents the current time used for testing.
	 * </p>
	 */
	private long current = -1;

	/**
	 * <p>
	 * EmailEngineNotificationService instance for testing.
	 * </p>
	 */
	private EmailEngineNotificationService impl;

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
		impl = (EmailEngineNotificationService) new FileSystemXmlApplicationContext("test_files/stress/spring.xml").getBean("notificationService");
		error = null;
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
	}

	/**
	 * <p>
	 * Return all tests.
	 * </p>
	 *
	 * @return all tests
	 */
	public static Test suite() {
		return new TestSuite(EmailEngineNotificationServiceStressTests.class);
	}

	/**
	 * <p>
	 * Tests EmailEngineNotificationService#notifyTaskCreation() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_notifyTaskCreation() throws Throwable {
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
						task.setName("name");
						task.setPriority(TaskPriority.HIGH);
						task.setStatus(TaskStatus.NOT_STARTED);
						List<UserDTO> assignees = new ArrayList<UserDTO>();
						UserDTO dto = new UserDTO();
						dto.setHandle("user"+NUMBER);
						dto.setUserId(NUMBER);
						assignees.add(dto );
						task.setAssignees(assignees );
						impl.notifyTaskCreation(100, task );
					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("EmailEngineNotificationService#notifyTaskCreation", NUMBER);
	}


	/**
	 * <p>
	 * Tests EmailEngineNotificationService#notifyTaskStatusChange() for stress purpose.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_notifyTaskStatusChange() throws Throwable {
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
						task.setName("name");
						task.setPriority(TaskPriority.HIGH);
						task.setStatus(TaskStatus.NOT_STARTED);
						task.setCreatedBy("user1");
						List<UserDTO> assignees = new ArrayList<UserDTO>();
						UserDTO dto = new UserDTO();
						dto.setHandle("user"+NUMBER);
						dto.setUserId(NUMBER);
						assignees.add(dto );
						task.setAssignees(assignees );

						Task newtask = new Task();
						newtask.setName("name");
						newtask.setCreatedBy("user1");
						newtask.setPriority(TaskPriority.HIGH);
						newtask.setStatus(TaskStatus.COMPLETED);
						newtask.setAssignees(assignees );
						impl.notifyTaskStatusChange(100, TaskStatus.IN_PROGRESS, newtask  );
					} catch (Throwable e) {
						error = e;
					}
				}
			});
		}

		awaitTermination(executorService);

		printResult("EmailEngineNotificationService#notifyTaskStatusChange", NUMBER);
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
		}

		if (error != null) {
			throw error;
		}
	}
}