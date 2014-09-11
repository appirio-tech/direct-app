/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.Log;

/**
 * <p>
 * All unit tests class for <code>BaseJPAService</code>.
 * </p>
 * <p>
 * Since this class is an abstract class, so a mock up subclass MockJPATestService is to do the concrete
 * tests.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseJPAServiceTests extends BaseUnitTests {


    /**
     * <p>
     * Represents the spring application context for unit tests.
     * </p>
     */
    private ApplicationContext ctx;

    /**
     * <p>
     * Represents the MockTestService and its concrete bean is expected from BaseJPAService for unit tests.
     * </p>
     */
    private MockTestService mockTestService;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseJPAServiceTests.class);
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
        ctx = super.getApplicationContext();
        mockTestService = (MockTestService) ctx.getBean("mockTestService");
    }

    /**
     * <p>
     * Tests the constructor: {@link BaseJPAService#BaseJPAService()}.
     * </p>
     * Accuracy test to check if the instance can be created correctly.
     * @throws Exception to JUnit.
     */
    @Test
    public void testBaseJPAService() throws Exception {
        BaseJPAService baseService = new BaseJPAService() {
        };
        assertNotNull("The instance cannot be created.", baseService);
        assertTrue("Should be the instance of BaseJPAService", baseService instanceof BaseJPAService);
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#checkInitialization()}.
     * </p>
     * If the entityManager is not injected, TaskManagementConfigurationException is expected.
     */
    @Test (expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationMissingUserService() {
        BaseJPAService baseService = new BaseJPAService() {
        };
        EntityManager entityManager = mockTestService.getTestEntityManager();
        Log log = mockTestService.getTestLog();
        NotificationService notificationService = mockTestService.getTestNotificationService();
        UserService userService = null;

        baseService.setEntityManager(entityManager);
        baseService.setLog(log);
        baseService.setNotificationService(notificationService);
        baseService.setUserService(userService);

        baseService.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#checkInitialization()}.
     * </p>
     * If the entityManager is not injected, TaskManagementConfigurationException is expected.
     */
    @Test (expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationMissingNotificationService() {
        BaseJPAService baseService = new BaseJPAService() {
        };
        EntityManager entityManager = mockTestService.getTestEntityManager();
        Log log = mockTestService.getTestLog();
        NotificationService notificationService = null;
        UserService userService = mockTestService.getTestUserService();

        baseService.setEntityManager(entityManager);
        baseService.setLog(log);
        baseService.setNotificationService(notificationService);
        baseService.setUserService(userService);

        baseService.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#checkInitialization()}.
     * </p>
     * If the entityManager is not injected, TaskManagementConfigurationException is expected.
     */
    @Test (expected = TaskManagementConfigurationException.class)
    public void testCheckInitializationMissingEntityManager() {
        BaseJPAService baseService = new BaseJPAService() {
        };
        EntityManager entityManager = null;
        Log log = mockTestService.getTestLog();
        NotificationService notificationService = mockTestService.getTestNotificationService();
        UserService userService = mockTestService.getTestUserService();

        baseService.setEntityManager(entityManager);
        baseService.setLog(log);
        baseService.setNotificationService(notificationService);
        baseService.setUserService(userService);

        baseService.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#checkInitialization()}.
     * </p>
     * No exception should be thrown if all fields are properly injected.
     */
    @Test
    public void testCheckInitializationAccuracy() {
        BaseJPAService baseService = new BaseJPAService() {
        };
        EntityManager entityManager = mockTestService.getTestEntityManager();
        Log log = mockTestService.getTestLog();
        NotificationService notificationService = mockTestService.getTestNotificationService();
        UserService userService = mockTestService.getTestUserService();

        baseService.setEntityManager(entityManager);
        baseService.setLog(log);
        baseService.setNotificationService(notificationService);
        baseService.setUserService(userService);

        baseService.checkInitialization();

    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#checkInitialization()}.
     * </p>
     * The Log is optional field, no exception should be thrown if the log is null.
     */
    @Test
    public void testCheckInitializationMissingLog() {
        BaseJPAService baseService = new BaseJPAService() {
        };
        EntityManager entityManager = mockTestService.getTestEntityManager();
        Log log = null;
        NotificationService notificationService = mockTestService.getTestNotificationService();
        UserService userService = mockTestService.getTestUserService();

        baseService.setEntityManager(entityManager);
        baseService.setLog(log);
        baseService.setNotificationService(notificationService);
        baseService.setUserService(userService);

        baseService.checkInitialization();
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#setLog(Log log)}.
     * </p>
     * <p>
     * Checks if the log is null, the log is properly set.
     * </p>
     */
    @Test
    public void testSetLogNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        Log log = null;
        // set the log to the baseJPAService
        baseJPAService.setLog(log);
        // checks if the log is properly set
        Assert.assertEquals("The log is not properly set if is null", log, baseJPAService.getLog());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#setLog(Log log)}.
     * </p>
     * <p>
     * Checks if the log is not null, the log is properly set.
     * </p>
     */
    @Test
    public void testSetLogNotNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        Log log = mockTestService.getTestLog();
        // set the log to the baseJPAService
        baseJPAService.setLog(log);
        // checks if the log is properly set
        Assert.assertEquals("The log is not properly set if is not null",
            log, baseJPAService.getLog());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#getLog()}.
     * </p>
     * <p>
     * Checks if the log is null, the log can be properly retrieved.
     * </p>
     */
    @Test
    public void testGetLogNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        Log log = null;
        // set the log to the baseJPAService
        baseJPAService.setLog(log);
        // checks if the log is properly retrieved
        Assert.assertEquals("The log is not properly set if is null", log, baseJPAService.getLog());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#getLog()}.
     * </p>
     * <p>
     * Checks if the log is not null, the log can be properly retrieved.
     * </p>
     */
    @Test
    public void testGetLogNotNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        Log log = mockTestService.getTestLog();
        // set the log to the baseJPAService
        baseJPAService.setLog(log);
        // checks if the log is properly retrieved
        Assert.assertEquals("The log is not properly set if is not null",
            log, baseJPAService.getLog());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#setUserService(UserService userService)}.
     * </p>
     * <p>
     * Checks if the userService is null, the userService is properly set.
     * </p>
     */
    @Test
    public void testSetUserServiceNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        UserService userService = null;
        // set the userService to the baseJPAService
        baseJPAService.setUserService(userService);
        // checks if the userService is properly set
        Assert.assertEquals("The userService is not properly set if is null",
            userService, baseJPAService.getUserService());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#setUserService(UserService userService)}.
     * </p>
     * <p>
     * Checks if the userService is not null, the userService is properly set.
     * </p>
     */
    @Test
    public void testSetUserServiceNotNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        UserService userService = mockTestService.getTestUserService();
        // set the userService to the baseJPAService
        baseJPAService.setUserService(userService);
        // checks if the userService is properly set
        Assert.assertEquals("The userService is not properly set if is not null",
            userService, baseJPAService.getUserService());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#getUserService()}.
     * </p>
     * <p>
     * Checks if the userService is null, the userService can be properly retrieved.
     * </p>
     */
    @Test
    public void testGetUserServiceNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        UserService userService = null;
        // set the userService to the baseJPAService
        baseJPAService.setUserService(userService);
        // checks if the userService is properly retrieved
        Assert.assertEquals("The userService is not properly set if is null",
            userService, baseJPAService.getUserService());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#getUserService()}.
     * </p>
     * <p>
     * Checks if the userService is not null, the userService can be properly retrieved.
     * </p>
     */
    @Test
    public void testGetUserServiceNotNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        UserService userService = mockTestService.getTestUserService();
        // set the userService to the baseJPAService
        baseJPAService.setUserService(userService);
        // checks if the userService is properly retrieved
        Assert.assertEquals("The userService is not properly set if is not null",
            userService, baseJPAService.getUserService());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#setEntityManager(EntityManager entityManager)}.
     * </p>
     * <p>
     * Checks if the entityManager is null, the entityManager is properly set.
     * </p>
     */
    @Test
    public void testSetEntityManagerNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        EntityManager entityManager = null;
        // set the entityManager to the baseJPAService
        baseJPAService.setEntityManager(entityManager);
        // checks if the entityManager is properly set
        Assert.assertEquals("The entityManager is not properly set if is null",
            entityManager, baseJPAService.getEntityManager());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#setEntityManager(EntityManager entityManager)}.
     * </p>
     * <p>
     * Checks if the entityManager is not null, the entityManager is properly set.
     * </p>
     */
    @Test
    public void testSetEntityManagerNotNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        EntityManager entityManager = mockTestService.getTestEntityManager();
        // set the entityManager to the baseJPAService
        baseJPAService.setEntityManager(entityManager);
        // checks if the entityManager is properly set
        Assert.assertEquals("The entityManager is not properly set if is not null",
            entityManager, baseJPAService.getEntityManager());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#getEntityManager()}.
     * </p>
     * <p>
     * Checks if the entityManager is null, the entityManager can be properly retrieved.
     * </p>
     */
    @Test
    public void testGetEntityManagerNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        EntityManager entityManager = null;
        // set the entityManager to the baseJPAService
        baseJPAService.setEntityManager(entityManager);
        // checks if the entityManager is properly retrieved
        Assert.assertEquals("The entityManager is not properly set if is null",
            entityManager, baseJPAService.getEntityManager());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#getEntityManager()}.
     * </p>
     * <p>
     * Checks if the entityManager is not null, the entityManager can be properly retrieved.
     * </p>
     */
    @Test
    public void testGetEntityManagerNotNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {
        };
        // set the value
        EntityManager entityManager = mockTestService.getTestEntityManager();
        // set the entityManager to the baseJPAService
        baseJPAService.setEntityManager(entityManager);
        // checks if the entityManager is properly retrieved
        Assert.assertEquals("The entityManager is not properly set if is not null",
            entityManager, baseJPAService.getEntityManager());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#setNotificationService(NotificationService notificationService)}.
     * </p>
     * <p>
     * Checks if the notificationService is null, the notificationService is properly set.
     * </p>
     */
    @Test
    public void testSetNotificationServiceNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {

        };
        // set the value
        NotificationService notificationService = null;
        // set the notificationService to the baseJPAService
        baseJPAService.setNotificationService(notificationService);
        // checks if the notificationService is properly set
        Assert.assertEquals("The notificationService is not properly set if is null",
            notificationService, baseJPAService.getNotificationService());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#setNotificationService(NotificationService notificationService)}.
     * </p>
     * <p>
     * Checks if the notificationService is not null, the notificationService is properly set.
     * </p>
     */
    @Test
    public void testSetNotificationServiceNotNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {

        };
        // set the value
        NotificationService notificationService = mockTestService.getTestNotificationService();
        // set the notificationService to the baseJPAService
        baseJPAService.setNotificationService(notificationService);
        // checks if the notificationService is properly set
        Assert.assertEquals("The notificationService is not properly set if is not null",
            notificationService, baseJPAService.getNotificationService());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#getNotificationService()}.
     * </p>
     * <p>
     * Checks if the notificationService is null, the notificationService can be properly retrieved.
     * </p>
     */
    @Test
    public void testGetNotificationServiceNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {

        };
        // set the value
        NotificationService notificationService = null;
        // set the notificationService to the baseJPAService
        baseJPAService.setNotificationService(notificationService);
        // checks if the notificationService is properly retrieved
        Assert.assertEquals("The notificationService is not properly set if is null",
            notificationService, baseJPAService.getNotificationService());
    }

    /**
     * <p>
     * Tests the method: {@link BaseJPAService#getNotificationService()}.
     * </p>
     * <p>
     * Checks if the notificationService is not null, the notificationService can be properly retrieved.
     * </p>
     */
    @Test
    public void testGetNotificationServiceNotNull() {
        // create the BaseJPAService instance for unit tests
        BaseJPAService baseJPAService = new BaseJPAService() {

        };
        // set the value
        NotificationService notificationService = mockTestService.getTestNotificationService();
        // set the notificationService to the baseJPAService
        baseJPAService.setNotificationService(notificationService);
        // checks if the notificationService is properly retrieved
        Assert.assertEquals("The notificationService is not properly set if is not null",
            notificationService, baseJPAService.getNotificationService());
    }
}
