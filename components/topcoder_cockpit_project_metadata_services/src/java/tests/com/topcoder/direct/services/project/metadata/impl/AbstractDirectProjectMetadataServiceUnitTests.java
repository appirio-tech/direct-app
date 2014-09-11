/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.TestsHelper;

/**
 * <p>
 * Unit tests for {@link AbstractDirectProjectMetadataService} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AbstractDirectProjectMetadataServiceUnitTests {
    /**
     * <p>
     * Represents the <code>AbstractDirectProjectMetadataService</code> instance used in tests.
     * </p>
     */
    private AbstractDirectProjectMetadataService instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AbstractDirectProjectMetadataServiceUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new MockDirectProjectMetadataService();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AbstractDirectProjectMetadataService()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockDirectProjectMetadataService();

        assertNull("'entityManager' should be correct.", TestsHelper.getField(instance, "entityManager"));
        assertNull("'logger' should be correct.", TestsHelper.getField(instance, "logger"));
        assertNull("'auditActionTypeIdMap' should be correct.", TestsHelper
            .getField(instance, "auditActionTypeIdMap"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEntityManager()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getEntityManager() throws Exception {
        EntityManager value = TestsHelper.getEntityManager();
        instance.setEntityManager(value);

        assertSame("'getEntityManager' should be correct.", value, instance.getEntityManager());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEntityManager(EntityManager entityManager) </code>.<br>
     * The value should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_setEntityManager() throws Exception {
        EntityManager value = TestsHelper.getEntityManager();
        instance.setEntityManager(value);

        assertSame("'setEntityManager' should be correct.", value, TestsHelper.getField(instance, "entityManager"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLogger()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getLogger() {
        instance.setLoggerName("loggerName");

        assertNotNull("'getLogger' should be correct.", instance.getLogger());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLoggerName(String loggerName)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setLoggerName_1() {
        instance.setLoggerName("loggerName");

        assertNotNull("'setLoggerName' should be correct.", instance.getLogger());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLoggerName(String loggerName)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setLoggerName_2() {
        instance.setLoggerName("loggerName");
        instance.setLoggerName(null);

        assertNull("'setLoggerName' should be correct.", instance.getLogger());
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInitialization()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_checkInitialization() throws Exception {
        instance.setEntityManager(TestsHelper.getEntityManager());
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);

        instance.checkInitialization();

        // Good
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with entityManager is <code>null</code>.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_entityManagerNull() throws Exception {
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with auditActionTypeIdMap is
     * <code>null</code>.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_auditActionTypeIdMapNull() throws Exception {
        instance.setEntityManager(TestsHelper.getEntityManager());

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with auditActionTypeIdMap contains
     * <code>null</code> key.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_auditActionTypeIdMapContainsNullKey() throws Exception {
        instance.setEntityManager(TestsHelper.getEntityManager());
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);
        auditActionTypeIdMap.put(null, 4);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with auditActionTypeIdMap contains
     * empty key.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_auditActionTypeIdMapContainsEmptyKey() throws Exception {
        instance.setEntityManager(TestsHelper.getEntityManager());
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);
        auditActionTypeIdMap.put(TestsHelper.EMPTY_STRING, 4);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with auditActionTypeIdMap contains
     * <code>null</code> value.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_auditActionTypeIdMapContainsNullValue() throws Exception {
        instance.setEntityManager(TestsHelper.getEntityManager());
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);
        auditActionTypeIdMap.put("new", null);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with auditActionTypeIdMap doesn't
     * contain values for keys "create", "update", "delete".<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_auditActionTypeIdMapRequiredMissing() throws Exception {
        instance.setEntityManager(TestsHelper.getEntityManager());
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);

        instance.checkInitialization();
    }
}
