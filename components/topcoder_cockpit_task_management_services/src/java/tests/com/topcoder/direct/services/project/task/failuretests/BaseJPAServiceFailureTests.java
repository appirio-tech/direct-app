/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.failuretests;

import static org.mockito.Mockito.mock;

import javax.persistence.EntityManager;

import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.direct.services.project.task.impl.BaseJPAService;
import com.topcoder.service.user.UserService;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseJPAService.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BaseJPAServiceFailureTests extends TestCase {

    /**
     * <p>
     * The BaseJPAService instance for testing.
     * </p>
     */
    private BaseJPAService instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new BaseJPAService() {
        };

        UserService userService = mock(UserService.class);
        EntityManager entityManager = mock(EntityManager.class);
        NotificationService notificationService = mock(NotificationService.class);

        instance.setUserService(userService);
        instance.setEntityManager(entityManager);
        instance.setNotificationService(notificationService);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseJPAServiceFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseJPAService#checkInitialization() for failure.
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
     * Tests BaseJPAService#checkInitialization() for failure.
     * It tests the case that when entityManager is null and expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure2() {
        instance.setEntityManager(null);
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseJPAService#checkInitialization() for failure.
     * It tests the case that when notificationService is null and expects TaskManagementConfigurationException.
     * </p>
     */
    public void testCheckInitialization_Failure3() {
        instance.setNotificationService(null);
        try {
            instance.checkInitialization();
            fail("TaskManagementConfigurationException expected.");
        } catch (TaskManagementConfigurationException e) {
            //good
        }
    }

}