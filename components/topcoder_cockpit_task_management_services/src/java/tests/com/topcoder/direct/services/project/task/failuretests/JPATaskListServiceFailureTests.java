/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.failuretests;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.direct.services.project.task.EntityNotFoundException;
import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.PermissionException;
import com.topcoder.direct.services.project.task.PersistenceException;
import com.topcoder.direct.services.project.task.impl.JPATaskListService;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for JPATaskListService.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class JPATaskListServiceFailureTests extends TestCase {
    /**
     * <p>
     * The JPATaskListService instance for testing.
     * </p>
     */
    private JPATaskListService instance;

    /**
     * <p>
     * The TaskList instance for testing.
     * </p>
     */
    private TaskList taskList;

    /**
     * <p>
     * The UserService instance for testing.
     * </p>
     */
    private UserService userService;

    /**
     * <p>
     * The EntityManager instance for testing.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new JPATaskListService();

        userService = mock(UserService.class);
        entityManager = mock(EntityManager.class);
        NotificationService notificationService = mock(NotificationService.class);

        instance.setUserService(userService);
        instance.setEntityManager(entityManager);
        instance.setNotificationService(notificationService);

        taskList = new TaskList();
        taskList.setName("TC");
        taskList.setId(8);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(JPATaskListServiceFailureTests.class);
    }

    /**
     * <p>
     * Tests JPATaskListService#addTaskList(long,TaskList) for failure.
     * It tests the case that when taskList is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskList_NullTaskList() throws Exception {
        try {
            instance.addTaskList(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#addTaskList(long,TaskList) for failure.
     * It tests the case that when taskList name is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskList_NullTaskListName() throws Exception {
        taskList.setName(null);
        try {
            instance.addTaskList(1, taskList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#addTaskList(long,TaskList) for failure.
     * It tests the case that when taskList name is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskList_EmptyTaskListName() throws Exception {
        taskList.setName(" ");
        try {
            instance.addTaskList(1, taskList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#addTaskList(long,TaskList) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddTaskList_PermissionException() throws Exception {
        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.addTaskList(1, taskList);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#updateTaskList(long,TaskList) for failure.
     * It tests the case that when taskList is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateTaskList_NullTaskList() throws Exception {
        try {
            instance.updateTaskList(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#updateTaskList(long,TaskList) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateTaskList_EntityNotFoundException() throws Exception {
        try {
            instance.updateTaskList(1, taskList);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#updateTaskList(long,TaskList) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateTaskList_PermissionException() throws Exception {
        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.updateTaskList(1, taskList);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#getTaskList(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskList_PermissionException() throws Exception {
        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.getTaskList(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#getDefaultTaskList(long,long) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDefaultTaskList_EntityNotFoundException() throws Exception {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new BigDecimal(0));
        try {
            instance.getDefaultTaskList(1, 1);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#getDefaultTaskList(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDefaultTaskList_PermissionException() throws Exception {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new BigDecimal(1));

        query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Long(1));

        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.getDefaultTaskList(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#getDefaultTaskList(long,long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDefaultTaskList_PersistenceException() throws Exception {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new BigDecimal(1));

        query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn("invalid");
        try {
            instance.getDefaultTaskList(1, 1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#deleteTaskList(long,long) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteTaskList_EntityNotFoundException() throws Exception {
        try {
            instance.deleteTaskList(1, 1);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#deleteTaskList(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteTaskList_PermissionException() throws Exception {
        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.deleteTaskList(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#resolveTaskList(long,long) for failure.
     * Expects EntityNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testResolveTaskList_EntityNotFoundException() throws Exception {
        try {
            instance.resolveTaskList(1, 1);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#resolveTaskList(long,long) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testResolveTaskList_PermissionException() throws Exception {
        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.resolveTaskList(1, 1);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests JPATaskListService#getTaskLists(long,TaskFilter) for failure.
     * Expects PermissionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskLists_PermissionException() throws Exception {
        when(userService.getUserHandle(1)).thenThrow(new UserServiceException("error"));
        try {
            instance.getTaskLists(1, null);
            fail("PermissionException expected.");
        } catch (PermissionException e) {
            //good
        }
    }

}