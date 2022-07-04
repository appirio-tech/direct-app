/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;
import com.topcoder.asset.entities.User;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link ManagerServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ManagerServiceImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>ManagerServiceImpl</code> instance used in tests.
     * </p>
     */
    private ManagerServiceImpl instance;

    /**
     * <p>
     * Represents the entity manager used in tests.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Represents the log used in tests.
     * </p>
     */
    private Log log = LogManager.getLog(getClass().getName());

    /**
     * <p>
     * Represents the project id used in tests.
     * </p>
     */
    private long projectId = 11;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ManagerServiceImplUnitTests.class);
    }

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
        super.setUp();
        loadDB(true);

        entityManager = getEntityManager();

        instance = new ManagerServiceImpl();
        instance.setEntityManager(entityManager);
        instance.setLog(log);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ManagerServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ManagerServiceImpl();

        assertNull("'entityManager' should be correct.", getField(instance, "entityManager"));
        assertNull("'log' should be correct.", getField(instance, "log"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_checkInit_1() {
        instance.checkInit();

        assertNotNull("'checkInit' should be correct.", getField(instance, "entityManager"));
        assertNotNull("'checkInit' should be correct.", getField(instance, "log"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_checkInit_2() {
        instance.setLog(null);

        instance.checkInit();

        assertNotNull("'checkInit' should be correct.", getField(instance, "entityManager"));
        assertNull("'checkInit' should be correct.", getField(instance, "log"));
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> with entityManager is null.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_entityManagerNull() {
        instance.setEntityManager(null);

        instance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClientManagers(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getClientManagers_1() throws Exception {
        clearDB();

        List<User> res = instance.getClientManagers(projectId);

        assertEquals("'getClientManagers' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClientManagers(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getClientManagers_2() throws Exception {
        List<User> res = instance.getClientManagers(projectId);

        assertEquals("'getClientManagers' should be correct.", 2, res.size());
        User user1 = res.get(0);
        User user2 = res.get(1);
        if (user1.getId() > user2.getId()) {
            user1 = res.get(1);
            user2 = res.get(0);
        }
        assertEquals("'getClientManagers' should be correct.", 1L, user1.getId());
        assertEquals("'getClientManagers' should be correct.", "cm1", user1.getName());
        assertEquals("'getClientManagers' should be correct.", 2L, user2.getId());
        assertEquals("'getClientManagers' should be correct.", "cm2", user2.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClientManagers(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getClientManagers_3() throws Exception {
        projectId = 12;
        List<User> res = instance.getClientManagers(projectId);

        assertEquals("'getClientManagers' should be correct.", 1, res.size());
        User user = res.get(0);
        assertEquals("'getClientManagers' should be correct.", 3L, user.getId());
        assertEquals("'getClientManagers' should be correct.", "cm3", user.getName());
    }

    /**
     * <p>
     * Failure test for the method <code>getClientManagers(long projectId)</code> with projectId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getClientManagers_projectIdNegative() throws Exception {
        projectId = -1;

        instance.getClientManagers(projectId);
    }

    /**
     * <p>
     * Failure test for the method <code>getClientManagers(long projectId)</code> with projectId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getClientManagers_projectIdZero() throws Exception {
        projectId = 0;

        instance.getClientManagers(projectId);
    }

    /**
     * <p>
     * Failure test for the method <code>getClientManagers(long projectId)</code> with a persistence error has
     * occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getClientManagers_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.getClientManagers(projectId);
    }

    /**
     * <p>
     * Failure test for the method <code>getClientManagers(long projectId)</code> with entity manager has been
     * closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getClientManagers_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.getClientManagers(projectId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTopCoderManagers(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getTopCoderManagers_1() throws Exception {
        clearDB();

        List<User> res = instance.getTopCoderManagers(projectId);

        assertEquals("'getTopCoderManagers' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTopCoderManagers(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getTopCoderManagers_2() throws Exception {
        List<User> res = instance.getTopCoderManagers(projectId);

        assertEquals("'getTopCoderManagers' should be correct.", 2, res.size());
        User user1 = res.get(0);
        User user2 = res.get(1);
        if (user1.getId() > user2.getId()) {
            user1 = res.get(1);
            user2 = res.get(0);
        }
        assertEquals("'getTopCoderManagers' should be correct.", 4L, user1.getId());
        assertEquals("'getTopCoderManagers' should be correct.", "tcm1", user1.getName());
        assertEquals("'getTopCoderManagers' should be correct.", 5L, user2.getId());
        assertEquals("'getTopCoderManagers' should be correct.", "tcm2", user2.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTopCoderManagers(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getTopCoderManagers_3() throws Exception {
        projectId = 12;
        List<User> res = instance.getTopCoderManagers(projectId);

        assertEquals("'getTopCoderManagers' should be correct.", 1, res.size());
        User user = res.get(0);
        assertEquals("'getTopCoderManagers' should be correct.", 6L, user.getId());
        assertEquals("'getTopCoderManagers' should be correct.", "tcm3", user.getName());
    }

    /**
     * <p>
     * Failure test for the method <code>getTopCoderManagers(long projectId)</code> with projectId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getTopCoderManagers_projectIdNegative() throws Exception {
        projectId = -1;

        instance.getTopCoderManagers(projectId);
    }

    /**
     * <p>
     * Failure test for the method <code>getTopCoderManagers(long projectId)</code> with projectId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getTopCoderManagers_projectIdZero() throws Exception {
        projectId = 0;

        instance.getTopCoderManagers(projectId);
    }

    /**
     * <p>
     * Failure test for the method <code>getTopCoderManagers(long projectId)</code> with a persistence error has
     * occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getTopCoderManagers_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.getTopCoderManagers(projectId);
    }

    /**
     * <p>
     * Failure test for the method <code>getTopCoderManagers(long projectId)</code> with entity manager has been
     * closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getTopCoderManagers_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.getTopCoderManagers(projectId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCopilots(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getCopilots_1() throws Exception {
        clearDB();

        List<User> res = instance.getCopilots(projectId);

        assertEquals("'getCopilots' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCopilots(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getCopilots_2() throws Exception {
        List<User> res = instance.getCopilots(projectId);

        assertEquals("'getCopilots' should be correct.", 2, res.size());
        User user1 = res.get(0);
        User user2 = res.get(1);
        if (user1.getId() > user2.getId()) {
            user1 = res.get(1);
            user2 = res.get(0);
        }
        assertEquals("'getCopilots' should be correct.", 7L, user1.getId());
        assertEquals("'getCopilots' should be correct.", "cp1", user1.getName());
        assertEquals("'getCopilots' should be correct.", 8L, user2.getId());
        assertEquals("'getCopilots' should be correct.", "cp2", user2.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCopilots(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getCopilots_3() throws Exception {
        projectId = 12;
        List<User> res = instance.getCopilots(projectId);

        assertEquals("'getCopilots' should be correct.", 1, res.size());
        User user = res.get(0);
        assertEquals("'getCopilots' should be correct.", 9L, user.getId());
        assertEquals("'getCopilots' should be correct.", "cp3", user.getName());
    }

    /**
     * <p>
     * Failure test for the method <code>getCopilots(long projectId)</code> with projectId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getCopilots_projectIdNegative() throws Exception {
        projectId = -1;

        instance.getCopilots(projectId);
    }

    /**
     * <p>
     * Failure test for the method <code>getCopilots(long projectId)</code> with projectId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getCopilots_projectIdZero() throws Exception {
        projectId = 0;

        instance.getCopilots(projectId);
    }

    /**
     * <p>
     * Failure test for the method <code>getCopilots(long projectId)</code> with a persistence error has
     * occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getCopilots_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.getCopilots(projectId);
    }

    /**
     * <p>
     * Failure test for the method <code>getCopilots(long projectId)</code> with entity manager has been
     * closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getCopilots_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.getCopilots(projectId);
    }
}