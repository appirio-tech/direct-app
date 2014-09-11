/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.EntityNotFoundException;
import com.topcoder.direct.services.project.metadata.TestsHelper;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKeyAudit;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValueAudit;

/**
 * <p>
 * Unit tests for {@link DirectProjectMetadataKeyServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DirectProjectMetadataKeyServiceImplUnitTests {

    /**
     * <p>
     * Represents the <code>DirectProjectMetadataKeyServiceImpl</code> instance used in tests.
     * </p>
     */
    private DirectProjectMetadataKeyServiceImpl instance;

    /**
     * <p>
     * Represents the project metadata key used in tests.
     * </p>
     */
    private DirectProjectMetadataKey projectMetadataKey;

    /**
     * <p>
     * Represents the user id used in tests.
     * </p>
     */
    private long userId = 1;

    /**
     * <p>
     * Represents the entity manager used in tests.
     * </p>
     */
    private EntityManager em;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataKeyServiceImplUnitTests.class);
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
        em = TestsHelper.getEntityManager();

        TestsHelper.clearDB(em);
        TestsHelper.loadDB(em);

        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);

        DirectProjectMetadataKeyValidatorImpl directProjectMetadataKeyValidator =
            new DirectProjectMetadataKeyValidatorImpl();
        directProjectMetadataKeyValidator.setEntityManager(em);
        directProjectMetadataKeyValidator.setLoggerName("loggerName");

        instance = new DirectProjectMetadataKeyServiceImpl();
        instance.setDirectProjectMetadataKeyValidator(directProjectMetadataKeyValidator);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);
        instance.setEntityManager(em);
        instance.setLoggerName("loggerName");

        projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setName("name3");
        projectMetadataKey.setDescription("some text");
        projectMetadataKey.setGrouping(null);
        projectMetadataKey.setClientId(null);
        projectMetadataKey.setSingle(true);
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        TestsHelper.clearDB(em);
        em = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DirectProjectMetadataKeyServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DirectProjectMetadataKeyServiceImpl();

        assertNull("'entityManager' should be correct.", TestsHelper.getField(instance, "entityManager"));
        assertNull("'logger' should be correct.", TestsHelper.getField(instance, "logger"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createProjectMetadataKey_1() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, userId);
        em.getTransaction().commit();

        DirectProjectMetadataKey directProjectMetadataKey =
            em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId());
        DirectProjectMetadataKeyAudit directProjectMetadataKeyAudit =
            em.find(DirectProjectMetadataKeyAudit.class, 1L);

        assertEquals("'createProjectMetadataKey' should be correct.",
            "name3", directProjectMetadataKey.getName());
        assertEquals("'createProjectMetadataKey' should be correct.",
            "some text", directProjectMetadataKey.getDescription());
        assertNull("'createProjectMetadataKey' should be correct.",
            directProjectMetadataKey.getClientId());
        assertNull("'createProjectMetadataKey' should be correct.",
            directProjectMetadataKey.getGrouping());
        assertTrue("'createProjectMetadataKey' should be correct.",
            directProjectMetadataKey.isSingle());

        assertEquals("'createProjectMetadataKey' should be correct.",
            projectMetadataKey.getId(), directProjectMetadataKeyAudit.getProjectMetadataKeyId());
        assertEquals("'createProjectMetadataKey' should be correct.",
            "name3", directProjectMetadataKeyAudit.getName());
        assertEquals("'createProjectMetadataKey' should be correct.",
            "some text", directProjectMetadataKeyAudit.getDescription());
        assertNull("'createProjectMetadataKey' should be correct.",
            directProjectMetadataKeyAudit.getClientId());
        assertNull("'createProjectMetadataKey' should be correct.",
            directProjectMetadataKeyAudit.getGrouping());
        assertTrue("'createProjectMetadataKey' should be correct.",
            directProjectMetadataKeyAudit.isSingle());
        assertEquals("'createProjectMetadataKey' should be correct.",
            1L, directProjectMetadataKeyAudit.getActionUserId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createProjectMetadataKey_2() throws Exception {
        List<DirectProjectMetadataPredefinedValue> predefinedValues =
            new ArrayList<DirectProjectMetadataPredefinedValue>();
        DirectProjectMetadataPredefinedValue predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(1);
        predefinedValue.setPredefinedMetadataValue("value");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);
        projectMetadataKey.setPredefinedValues(predefinedValues);

        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, userId);
        em.getTransaction().commit();

        DirectProjectMetadataPredefinedValueAudit directProjectMetadataPredefinedValueAudit =
            em.find(DirectProjectMetadataPredefinedValueAudit.class, 1L);

        assertEquals("'createProjectMetadataKey' should be correct.",
            projectMetadataKey.getId(), directProjectMetadataPredefinedValueAudit.getProjectMetadataKeyId());
        assertEquals("'createProjectMetadataKey' should be correct.",
            1, directProjectMetadataPredefinedValueAudit.getPosition());
        assertEquals("'createProjectMetadataKey' should be correct.",
            "value", directProjectMetadataPredefinedValueAudit.getPredefinedMetadataValue());
        assertEquals("'createProjectMetadataKey' should be correct.",
            1L, directProjectMetadataPredefinedValueAudit.getActionUserId());
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code> with projectMetadataKey is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createProjectMetadataKey_projectMetadataKeyNull() throws Exception {
        projectMetadataKey = null;

        instance.createProjectMetadataKey(projectMetadataKey, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code> with entity fails the validation.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_createProjectMetadataKey_ValidationError() throws Exception {
        projectMetadataKey.setClientId(1L);

        instance.createProjectMetadataKey(projectMetadataKey, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateProjectMetadataKey() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, userId);
        em.getTransaction().commit();

        projectMetadataKey.setDescription("new description");
        projectMetadataKey.setPredefinedValues(new ArrayList<DirectProjectMetadataPredefinedValue>());
        em.getTransaction().begin();
        instance.updateProjectMetadataKey(projectMetadataKey, userId);
        em.getTransaction().commit();

        DirectProjectMetadataKey directProjectMetadataKey =
            em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId());

        assertEquals("'updateProjectMetadataKey' should be correct.",
            "name3", directProjectMetadataKey.getName());
        assertEquals("'updateProjectMetadataKey' should be correct.",
            "new description", directProjectMetadataKey.getDescription());
        assertNull("'updateProjectMetadataKey' should be correct.",
            directProjectMetadataKey.getClientId());
        assertNull("'updateProjectMetadataKey' should be correct.",
            directProjectMetadataKey.getGrouping());
        assertTrue("'updateProjectMetadataKey' should be correct.",
            directProjectMetadataKey.isSingle());
    }

    /**
     * <p>
     * Failure test for the method <code>updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code> with projectMetadataKey is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateProjectMetadataKey_projectMetadataKeyNull() throws Exception {
        projectMetadataKey = null;

        instance.updateProjectMetadataKey(projectMetadataKey, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code> with entity fails the validation.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_updateProjectMetadataKey_ValidationError() throws Exception {
        projectMetadataKey.setClientId(1L);

        instance.updateProjectMetadataKey(projectMetadataKey, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code> with requested entity is not found in db.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateProjectMetadataKey_EntityNotFoundError() throws Exception {
        projectMetadataKey.setId(Long.MAX_VALUE);

        instance.updateProjectMetadataKey(projectMetadataKey, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>saveProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_saveProjectMetadataKey_1() throws Exception {
        em.getTransaction().begin();
        instance.saveProjectMetadataKey(projectMetadataKey, userId);
        em.getTransaction().commit();

        Query query = em.createQuery(
            "SELECT directProjectMetadataKey FROM DirectProjectMetadataKey directProjectMetadataKey"
            + " WHERE name = 'name3'");

        DirectProjectMetadataKey directProjectMetadataKey = (DirectProjectMetadataKey) query.getSingleResult();


        assertEquals("'saveProjectMetadataKey' should be correct.",
            "name3", directProjectMetadataKey.getName());
        assertEquals("'saveProjectMetadataKey' should be correct.",
            "some text", directProjectMetadataKey.getDescription());
        assertNull("'saveProjectMetadataKey' should be correct.",
            directProjectMetadataKey.getClientId());
        assertNull("'saveProjectMetadataKey' should be correct.",
            directProjectMetadataKey.getGrouping());
        assertTrue("'saveProjectMetadataKey' should be correct.",
            directProjectMetadataKey.isSingle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>saveProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_saveProjectMetadataKey_2() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, userId);
        em.getTransaction().commit();

        projectMetadataKey.setDescription("new description");
        projectMetadataKey.setPredefinedValues(new ArrayList<DirectProjectMetadataPredefinedValue>());
        em.getTransaction().begin();
        instance.saveProjectMetadataKey(projectMetadataKey, userId);
        em.getTransaction().commit();

        DirectProjectMetadataKey directProjectMetadataKey =
            em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId());

        assertEquals("'saveProjectMetadataKey' should be correct.",
            "name3", directProjectMetadataKey.getName());
        assertEquals("'saveProjectMetadataKey' should be correct.",
            "new description", directProjectMetadataKey.getDescription());
        assertNull("'saveProjectMetadataKey' should be correct.",
            directProjectMetadataKey.getClientId());
        assertNull("'saveProjectMetadataKey' should be correct.",
            directProjectMetadataKey.getGrouping());
        assertTrue("'saveProjectMetadataKey' should be correct.",
            directProjectMetadataKey.isSingle());
    }

    /**
     * <p>
     * Failure test for the method <code>saveProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code> with projectMetadataKey is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_saveProjectMetadataKey_projectMetadataKeyNull() throws Exception {
        projectMetadataKey = null;

        instance.saveProjectMetadataKey(projectMetadataKey, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>saveProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey,
     * long userId)</code> with entity fails the validation.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_saveProjectMetadataKey_ValidationError() throws Exception {
        projectMetadataKey.setClientId(1L);

        instance.saveProjectMetadataKey(projectMetadataKey, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteProjectMetadataKey(long projectMetadataKeyId,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_deleteProjectMetadataKey() throws Exception {
        instance.createProjectMetadataKey(projectMetadataKey, userId);

        instance.deleteProjectMetadataKey(projectMetadataKey.getId(), userId);

        assertNull("'deleteProjectMetadataKey' should be correct.",
            em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId()));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteProjectMetadataKey(long projectMetadataKeyId,
     * long userId)</code> with requested entity is not found in db.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_deleteProjectMetadataKey_EntityNotFoundError() throws Exception {
        instance.deleteProjectMetadataKey(Long.MAX_VALUE, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectMetadataKey(long projectMetadataKeyId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getProjectMetadataKey_1() throws Exception {
        instance.createProjectMetadataKey(projectMetadataKey, userId);

        DirectProjectMetadataKey res = instance.getProjectMetadataKey(projectMetadataKey.getId());

        assertEquals("'getProjectMetadataKey' should be correct.",
            "name3", res.getName());
        assertEquals("'getProjectMetadataKey' should be correct.",
            "some text", res.getDescription());
        assertNull("'getProjectMetadataKey' should be correct.",
            res.getClientId());
        assertNull("'getProjectMetadataKey' should be correct.",
            res.getGrouping());
        assertTrue("'getProjectMetadataKey' should be correct.",
            res.isSingle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectMetadataKey(long projectMetadataKeyId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getProjectMetadataKey_2() throws Exception {
        DirectProjectMetadataKey res = instance.getProjectMetadataKey(Long.MAX_VALUE);

        assertNull("'getProjectMetadataKey' should be correct.", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCommonProjectMetadataKeys()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getCommonProjectMetadataKeys_1() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, userId);
        em.getTransaction().commit();

        List<DirectProjectMetadataKey> res = instance.getCommonProjectMetadataKeys();

        assertEquals("'getCommonProjectMetadataKeys' should be correct.",
            2, res.size());

        DirectProjectMetadataKey directProjectMetadataKey = res.get(0);
        if (directProjectMetadataKey.getId() != projectMetadataKey.getId()) {
            directProjectMetadataKey = res.get(1);
        }
        assertEquals("'getCommonProjectMetadataKeys' should be correct.",
            "name3", directProjectMetadataKey.getName());
        assertEquals("'getCommonProjectMetadataKeys' should be correct.",
            "some text", directProjectMetadataKey.getDescription());
        assertNull("'getCommonProjectMetadataKeys' should be correct.",
            directProjectMetadataKey.getClientId());
        assertNull("'getCommonProjectMetadataKeys' should be correct.",
            directProjectMetadataKey.getGrouping());
        assertTrue("'getCommonProjectMetadataKeys' should be correct.",
            directProjectMetadataKey.isSingle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCommonProjectMetadataKeys()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getCommonProjectMetadataKeys_2() throws Exception {
        TestsHelper.clearDB(em);

        List<DirectProjectMetadataKey> res = instance.getCommonProjectMetadataKeys();

        assertEquals("'getCommonProjectMetadataKeys' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClientProjectMetadataKeys(long clientId, Boolean grouping)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getClientProjectMetadataKeys_1() throws Exception {
        List<DirectProjectMetadataKey> res = instance.getClientProjectMetadataKeys(2, null);

        assertEquals("'getClientProjectMetadataKeys' should be correct.", 1, res.size());

        DirectProjectMetadataKey directProjectMetadataKey = res.get(0);
        assertEquals("'getClientProjectMetadataKeys' should be correct.",
            "name2", directProjectMetadataKey.getName());
        assertEquals("'getClientProjectMetadataKeys' should be correct.",
            "description2", directProjectMetadataKey.getDescription());
        assertEquals("'getClientProjectMetadataKeys' should be correct.",
            2L, directProjectMetadataKey.getClientId().longValue());
        assertTrue("'getClientProjectMetadataKeys' should be correct.",
            directProjectMetadataKey.getGrouping());
        assertFalse("'getClientProjectMetadataKeys' should be correct.",
            directProjectMetadataKey.isSingle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClientProjectMetadataKeys(long clientId, Boolean grouping)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getClientProjectMetadataKeys_2() throws Exception {
        List<DirectProjectMetadataKey> res = instance.getClientProjectMetadataKeys(2, true);

        assertEquals("'getClientProjectMetadataKeys' should be correct.", 1, res.size());

        DirectProjectMetadataKey directProjectMetadataKey = res.get(0);
        assertEquals("'getClientProjectMetadataKeys' should be correct.",
            "name2", directProjectMetadataKey.getName());
        assertEquals("'getClientProjectMetadataKeys' should be correct.",
            "description2", directProjectMetadataKey.getDescription());
        assertEquals("'getClientProjectMetadataKeys' should be correct.",
            2L, directProjectMetadataKey.getClientId().longValue());
        assertTrue("'getClientProjectMetadataKeys' should be correct.",
            directProjectMetadataKey.getGrouping());
        assertFalse("'getClientProjectMetadataKeys' should be correct.",
            directProjectMetadataKey.isSingle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClientProjectMetadataKeys(long clientId, Boolean grouping)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getClientProjectMetadataKeys_3() throws Exception {
        List<DirectProjectMetadataKey> res = instance.getClientProjectMetadataKeys(Long.MAX_VALUE, true);

        assertEquals("'getClientProjectMetadataKeys' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDirectProjectMetadataKeyValidator(
     * DirectProjectMetadataKeyValidator directProjectMetadataKeyValidator)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setDirectProjectMetadataKeyValidator() {
        assertNotNull("'setDirectProjectMetadataKeyValidator' should be correct.",
            TestsHelper.getField(instance, "directProjectMetadataKeyValidator"));
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
        instance.setEntityManager(null);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with directProjectMetadataKeyValidator is
     * <code>null</code>.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_directProjectMetadataKeyValidatorNull() throws Exception {
        instance.setDirectProjectMetadataKeyValidator(null);

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
        instance.setAuditActionTypeIdMap(null);

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
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);

        instance.checkInitialization();
    }
}
