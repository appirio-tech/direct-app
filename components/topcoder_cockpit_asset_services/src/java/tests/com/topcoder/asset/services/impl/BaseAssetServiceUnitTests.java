/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link BaseAssetService} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class BaseAssetServiceUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>BaseAssetService</code> instance used in tests.
     * </p>
     */
    private BaseAssetService instance;

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
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseAssetServiceUnitTests.class);
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
        loadDB(false);

        entityManager = getEntityManager();

        instance = new MockBaseAssetService();
        instance.setEntityManager(entityManager);
        instance.setLog(log);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseAssetService()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockBaseAssetService();

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
     * Failure test for the method <code>checkInit()</code>
     * with entityManager is null.<br>
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
     * Accuracy test for the method <code>getEntityManager()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getEntityManager() {
        instance.setEntityManager(entityManager);

        assertSame("'getEntityManager' should be correct.",
            entityManager, instance.getEntityManager());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEntityManager(EntityManager entityManager)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setEntityManager() {
        instance.setEntityManager(entityManager);

        assertSame("'setEntityManager' should be correct.",
            entityManager, getField(instance, "entityManager"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLog()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getLog() {
        instance.setLog(log);

        assertSame("'getLog' should be correct.",
            log, instance.getLog());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLog(Log log)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setLog() {
        instance.setLog(log);

        assertSame("'setLog' should be correct.",
            log, getField(instance, "log"));
    }
}