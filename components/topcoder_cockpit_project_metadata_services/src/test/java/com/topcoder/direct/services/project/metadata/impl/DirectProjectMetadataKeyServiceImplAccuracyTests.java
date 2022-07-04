/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.metadata.DirectProjectMetadataKeyValidator;
import com.topcoder.direct.services.project.metadata.accuracytests.impl.AccuracyTestHelper;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKeyAudit;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValueAudit;

/**
 * <p>
 * Accuracy test for DirectProjectMetadataKeyServiceImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DirectProjectMetadataKeyServiceImplAccuracyTests {
    /**
     * <p>
     * Represents the user id used in tests.
     * </p>
     */
    private static final long USER_ID = 1;

    /**
     * <p>
     * Represents the instance of DirectProjectMetadataKeyServiceImpl used in tests.
     * </p>
     */
    private DirectProjectMetadataKeyServiceImpl instance;

    /**
     * <p>
     * Represents the instance of DirectProjectMetadataKey used in tests.
     * </p>
     */
    private DirectProjectMetadataKey projectMetadataKey;

    /**
     * <p>
     * Represents the instance of EntityManager used in tests.
     * </p>
     */
    private EntityManager em;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return the test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataKeyServiceImplAccuracyTests.class);
    }

    /**
     * <p>
     * Sets up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        em = AccuracyTestHelper.createEntityManager();

        AccuracyTestHelper.loadDB(em);

        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);

        DirectProjectMetadataKeyValidatorImpl directProjectMetadataKeyValidator = new DirectProjectMetadataKeyValidatorImpl();
        directProjectMetadataKeyValidator.setEntityManager(em);
        directProjectMetadataKeyValidator.setLoggerName("accTest");

        instance = new DirectProjectMetadataKeyServiceImpl();
        instance.setDirectProjectMetadataKeyValidator(directProjectMetadataKeyValidator);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);
        instance.setEntityManager(em);
        instance.setLoggerName("loggerName");

        projectMetadataKey = createDirectProjectMetadataKey();
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        AccuracyTestHelper.clearDB(em);
        em = null;
    }

    /**
     * <p>
     * Accuracy test for DirectProjectMetadataKeyServiceImpl(). Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testCtor() throws Exception {
        instance = new DirectProjectMetadataKeyServiceImpl();
        assertNull("The entityManager is incorrect.", AccuracyTestHelper.getField(
            AbstractDirectProjectMetadataService.class, instance, "entityManager"));
        assertNull("The logger is incorrect.", AccuracyTestHelper.getField(AbstractDirectProjectMetadataService.class,
            instance, "logger"));
    }

    /**
     * <p>
     * Accuracy test for createProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId). The
     * projectMetadataKey should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateProjectMetadataKey1() throws Exception {
        em.getTransaction().begin();
        projectMetadataKey.setId(instance.createProjectMetadataKey(projectMetadataKey, USER_ID));
        em.getTransaction().commit();

        assertDirectProjectMetadataKey(em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId()),
            projectMetadataKey);

        List<DirectProjectMetadataKeyAudit> audits = em.createQuery(
            "SELECT directProjectMetadataKeyAudit "
                + "FROM DirectProjectMetadataKeyAudit directProjectMetadataKeyAudit").getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataKeyAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 1, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", projectMetadataKey.getId(), audit.getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadataKey.getName(), audit.getName());
        assertEquals("The audit is incorrect", projectMetadataKey.getDescription(), audit.getDescription());
        assertEquals("The audit is incorrect", projectMetadataKey.getClientId(), audit.getClientId());
        assertEquals("The audit is incorrect", projectMetadataKey.getGrouping(), audit.getGrouping());
        assertEquals("The audit is incorrect", projectMetadataKey.isSingle(), audit.isSingle());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());
    }

    /**
     * <p>
     * Accuracy test for createProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long USER_ID). The
     * projectMetadataKey should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateProjectMetadataKey2() throws Exception {
        List<DirectProjectMetadataPredefinedValue> predefinedValues = new ArrayList<DirectProjectMetadataPredefinedValue>();
        DirectProjectMetadataPredefinedValue predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(1);
        predefinedValue.setPredefinedMetadataValue("1");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);

        predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(2);
        predefinedValue.setPredefinedMetadataValue("2");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);

        projectMetadataKey.setPredefinedValues(predefinedValues);
        projectMetadataKey.setClientId(null);
        projectMetadataKey.setGrouping(null);

        em.getTransaction().begin();
        projectMetadataKey.setId(instance.createProjectMetadataKey(projectMetadataKey, USER_ID));
        em.getTransaction().commit();

        assertDirectProjectMetadataKey(em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId()),
            projectMetadataKey);

        // verify the DirectProjectMetadataPredefinedValue
        DirectProjectMetadataPredefinedValue result = em.find(DirectProjectMetadataPredefinedValue.class,
            projectMetadataKey.getPredefinedValues().get(0).getId());
        assertDirectProjectMetadataPredefinedValue(result, projectMetadataKey.getPredefinedValues().get(0));
        result = em.find(DirectProjectMetadataPredefinedValue.class, projectMetadataKey.getPredefinedValues().get(1)
            .getId());
        assertDirectProjectMetadataPredefinedValue(result, projectMetadataKey.getPredefinedValues().get(1));

        // verify the audit
        List<DirectProjectMetadataKeyAudit> audits = em.createQuery(
            "SELECT directProjectMetadataKeyAudit "
                + "FROM DirectProjectMetadataKeyAudit directProjectMetadataKeyAudit").getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataKeyAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 1, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", projectMetadataKey.getId(), audit.getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadataKey.getName(), audit.getName());
        assertEquals("The audit is incorrect", projectMetadataKey.getDescription(), audit.getDescription());
        assertEquals("The audit is incorrect", projectMetadataKey.getClientId(), audit.getClientId());
        assertEquals("The audit is incorrect", projectMetadataKey.getGrouping(), audit.getGrouping());
        assertEquals("The audit is incorrect", projectMetadataKey.isSingle(), audit.isSingle());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());
    }

    /**
     * <p>
     * Accuracy test for updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long USER_ID). The
     * projectMetadataKey should be updated.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateProjectMetadataKey1() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("Delete from DirectProjectMetadataKeyAudit").executeUpdate();
        em.getTransaction().commit();

        updateDirectProjectMetadataKey(projectMetadataKey);
        em.getTransaction().begin();
        instance.updateProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        assertDirectProjectMetadataKey(em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId()),
            projectMetadataKey);

        // verify the audit
        List<DirectProjectMetadataKeyAudit> audits = em.createQuery(
            "SELECT directProjectMetadataKeyAudit "
                + "FROM DirectProjectMetadataKeyAudit directProjectMetadataKeyAudit").getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataKeyAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 2, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", projectMetadataKey.getId(), audit.getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadataKey.getName(), audit.getName());
        assertEquals("The audit is incorrect", projectMetadataKey.getDescription(), audit.getDescription());
        assertEquals("The audit is incorrect", projectMetadataKey.getClientId(), audit.getClientId());
        assertEquals("'createProjectMetadataKeyThe audit is incorrect", projectMetadataKey.getGrouping(), audit
            .getGrouping());
        assertEquals("The audit is incorrect", projectMetadataKey.isSingle(), audit.isSingle());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());
    }

    /**
     * <p>
     * Accuracy test for updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long USER_ID). The
     * projectMetadataKey should be updated.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateProjectMetadataKey2() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("Delete from DirectProjectMetadataKeyAudit").executeUpdate();
        em.getTransaction().commit();

        updateDirectProjectMetadataKey(projectMetadataKey);
        List<DirectProjectMetadataPredefinedValue> predefinedValues = new ArrayList<DirectProjectMetadataPredefinedValue>();
        DirectProjectMetadataPredefinedValue predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(1);
        predefinedValue.setPredefinedMetadataValue("1");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);

        predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(2);
        predefinedValue.setPredefinedMetadataValue("2");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);

        projectMetadataKey.setPredefinedValues(predefinedValues);
        projectMetadataKey.setClientId(null);
        projectMetadataKey.setGrouping(null);

        em.getTransaction().begin();
        instance.updateProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        assertDirectProjectMetadataKey(em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId()),
            projectMetadataKey);

        // verify the DirectProjectMetadataPredefinedValue
        DirectProjectMetadataPredefinedValue result = em.find(DirectProjectMetadataPredefinedValue.class,
            projectMetadataKey.getPredefinedValues().get(0).getId());
        assertDirectProjectMetadataPredefinedValue(result, projectMetadataKey.getPredefinedValues().get(0));
        result = em.find(DirectProjectMetadataPredefinedValue.class, projectMetadataKey.getPredefinedValues().get(1)
            .getId());
        assertDirectProjectMetadataPredefinedValue(result, projectMetadataKey.getPredefinedValues().get(1));

        // verify the audit
        List<DirectProjectMetadataKeyAudit> audits = em.createQuery(
            "SELECT directProjectMetadataKeyAudit "
                + "FROM DirectProjectMetadataKeyAudit directProjectMetadataKeyAudit").getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataKeyAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 2, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", projectMetadataKey.getId(), audit.getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadataKey.getName(), audit.getName());
        assertEquals("The audit is incorrect", projectMetadataKey.getDescription(), audit.getDescription());
        assertEquals("The audit is incorrect", projectMetadataKey.getClientId(), audit.getClientId());
        assertEquals("The audit is incorrect", projectMetadataKey.getGrouping(), audit.getGrouping());
        assertEquals("The audit is incorrect", projectMetadataKey.isSingle(), audit.isSingle());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());
    }

    /**
     * <p>
     * Accuracy test for updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long USER_ID). The
     * projectMetadataKey should be updated.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateProjectMetadataKey3() throws Exception {
        List<DirectProjectMetadataPredefinedValue> predefinedValues = new ArrayList<DirectProjectMetadataPredefinedValue>();
        DirectProjectMetadataPredefinedValue predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(1);
        predefinedValue.setPredefinedMetadataValue("1");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);

        predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(2);
        predefinedValue.setPredefinedMetadataValue("2");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("Delete from DirectProjectMetadataKeyAudit").executeUpdate();
        em.getTransaction().commit();

        updateDirectProjectMetadataKey(projectMetadataKey);
        em.getTransaction().begin();
        instance.updateProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        DirectProjectMetadataKey result = em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId());
        assertDirectProjectMetadataKey(result, projectMetadataKey);
        assertTrue("The predefinedValues is incorrect.", result.getPredefinedValues().isEmpty());

        // verify the audit
        List<DirectProjectMetadataKeyAudit> audits = em.createQuery(
            "SELECT directProjectMetadataKeyAudit "
                + "FROM DirectProjectMetadataKeyAudit directProjectMetadataKeyAudit").getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataKeyAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 2, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", projectMetadataKey.getId(), audit.getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadataKey.getName(), audit.getName());
        assertEquals("The audit is incorrect", projectMetadataKey.getDescription(), audit.getDescription());
        assertEquals("The audit is incorrect", projectMetadataKey.getClientId(), audit.getClientId());
        assertEquals("The audit is incorrect", projectMetadataKey.getGrouping(), audit.getGrouping());
        assertEquals("The audit is incorrect", projectMetadataKey.isSingle(), audit.isSingle());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());
    }

    /**
     * <p>
     * Accuracy test for saveProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long USER_ID). The
     * projectMetadataKey should be saved.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSaveProjectMetadataKey1() throws Exception {
        List<DirectProjectMetadataPredefinedValue> predefinedValues = new ArrayList<DirectProjectMetadataPredefinedValue>();
        DirectProjectMetadataPredefinedValue predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(1);
        predefinedValue.setPredefinedMetadataValue("1");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);

        predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(2);
        predefinedValue.setPredefinedMetadataValue("2");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);

        projectMetadataKey.setPredefinedValues(predefinedValues);
        projectMetadataKey.setClientId(null);
        projectMetadataKey.setGrouping(null);

        em.getTransaction().begin();
        instance.saveProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        Query query = em.createQuery("SELECT directProjectMetadataKey FROM DirectProjectMetadataKey  "
            + "directProjectMetadataKey WHERE name = '" + projectMetadataKey.getName() + "'");
        DirectProjectMetadataKey key2 = (DirectProjectMetadataKey) query.getSingleResult();
        projectMetadataKey.setId(key2.getId());
        assertDirectProjectMetadataKey(key2, projectMetadataKey);

        // verify the DirectProjectMetadataPredefinedValue
        query = em.createQuery("SELECT predifinedValue FROM DirectProjectMetadataPredefinedValue  "
            + "predifinedValue WHERE projectMetadataKey = " + projectMetadataKey.getId() + "");

        List<DirectProjectMetadataPredefinedValue> list = query.getResultList();
        projectMetadataKey.getPredefinedValues().get(0).setId(list.get(0).getId());
        projectMetadataKey.getPredefinedValues().get(1).setId(list.get(1).getId());

        assertDirectProjectMetadataPredefinedValue(list.get(0), projectMetadataKey.getPredefinedValues().get(0));
        assertDirectProjectMetadataPredefinedValue(list.get(1), projectMetadataKey.getPredefinedValues().get(1));

        // verify the audit
        List<DirectProjectMetadataKeyAudit> audits = em.createQuery(
            "SELECT directProjectMetadataKeyAudit "
                + "FROM DirectProjectMetadataKeyAudit directProjectMetadataKeyAudit").getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataKeyAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 2, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", projectMetadataKey.getId(), audit.getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadataKey.getName(), audit.getName());
        assertEquals("The audit is incorrect", projectMetadataKey.getDescription(), audit.getDescription());
        assertEquals("The audit is incorrect", projectMetadataKey.getClientId(), audit.getClientId());
        assertEquals("The audit is incorrect", projectMetadataKey.getGrouping(), audit.getGrouping());
        assertEquals("The audit is incorrect", projectMetadataKey.isSingle(), audit.isSingle());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());
    }

    /**
     * <p>
     * Accuracy test for saveProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long USER_ID). The
     * projectMetadataKey should be saved.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSaveProjectMetadataKey2() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("Delete from DirectProjectMetadataKeyAudit").executeUpdate();
        em.getTransaction().commit();

        updateDirectProjectMetadataKey(projectMetadataKey);
        List<DirectProjectMetadataPredefinedValue> predefinedValues = new ArrayList<DirectProjectMetadataPredefinedValue>();
        DirectProjectMetadataPredefinedValue predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(1);
        predefinedValue.setPredefinedMetadataValue("1");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);

        predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPosition(2);
        predefinedValue.setPredefinedMetadataValue("2");
        predefinedValue.setProjectMetadataKey(projectMetadataKey);
        predefinedValues.add(predefinedValue);

        projectMetadataKey.setPredefinedValues(predefinedValues);
        projectMetadataKey.setClientId(null);
        projectMetadataKey.setGrouping(null);

        em.getTransaction().begin();
        instance.saveProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        assertDirectProjectMetadataKey(em.find(DirectProjectMetadataKey.class, projectMetadataKey.getId()),
            projectMetadataKey);

        // verify the DirectProjectMetadataPredefinedValue
        DirectProjectMetadataPredefinedValue result = em.find(DirectProjectMetadataPredefinedValue.class,
            projectMetadataKey.getPredefinedValues().get(0).getId());
        assertDirectProjectMetadataPredefinedValue(result, projectMetadataKey.getPredefinedValues().get(0));
        result = em.find(DirectProjectMetadataPredefinedValue.class, projectMetadataKey.getPredefinedValues().get(1)
            .getId());
        assertDirectProjectMetadataPredefinedValue(result, projectMetadataKey.getPredefinedValues().get(1));

        // verify the audit
        List<DirectProjectMetadataKeyAudit> audits = em.createQuery(
            "SELECT directProjectMetadataKeyAudit "
                + "FROM DirectProjectMetadataKeyAudit directProjectMetadataKeyAudit").getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataKeyAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 2, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", projectMetadataKey.getId(), audit.getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadataKey.getName(), audit.getName());
        assertEquals("The audit is incorrect", projectMetadataKey.getDescription(), audit.getDescription());
        assertEquals("The audit is incorrect", projectMetadataKey.getClientId(), audit.getClientId());
        assertEquals("The audit is incorrect", projectMetadataKey.getGrouping(), audit.getGrouping());
        assertEquals("The audit is incorrect", projectMetadataKey.isSingle(), audit.isSingle());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());

        List<DirectProjectMetadataPredefinedValueAudit> list = em.createQuery(
            "SELECT " + "directProjectMetadataPredefinedValueAudit FROM DirectProjectMetadataPredefinedValueAudit"
                + " directProjectMetadataPredefinedValueAudit").getResultList();
        assertEquals("There should 2 audit record.", 2, list.size());

        DirectProjectMetadataPredefinedValueAudit predefinedValueAudit = list.get(0);
        if (predefinedValueAudit.getPosition() == 1) {
            assertEquals("The audit is incorrect", 2, predefinedValueAudit.getAuditActionTypeId());
            assertEquals("The audit is incorrect", projectMetadataKey.getId(), predefinedValueAudit
                .getProjectMetadataKeyId());
            assertEquals("The audit is incorrect", USER_ID, predefinedValueAudit.getActionUserId());
            assertEquals("The audit is incorrect", 1, predefinedValueAudit.getPosition());
            assertEquals("The audit is incorrect", "1", predefinedValueAudit.getPredefinedMetadataValue());
        } else {
            predefinedValueAudit = list.get(1);
            assertEquals("The audit is incorrect", 2, predefinedValueAudit.getAuditActionTypeId());
            assertEquals("The audit is incorrect", projectMetadataKey.getId(), predefinedValueAudit
                .getProjectMetadataKeyId());
            assertEquals("The audit is incorrect", USER_ID, predefinedValueAudit.getActionUserId());
            assertEquals("The audit is incorrect", 1, predefinedValueAudit.getPosition());
            assertEquals("The audit is incorrect", "1", predefinedValueAudit.getPredefinedMetadataValue());
        }
    }

    /**
     * <p>
     * Accuracy test for deleteProjectMetadataKey(long projectMetadataKeyId, long USER_ID).
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteProjectMetadataKey() throws Exception {
        long id1 = instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        long id2 = instance.createProjectMetadataKey(createDirectProjectMetadataKey(), USER_ID);
        assertNotNull("Should be created", instance.getProjectMetadataKey(id1));
        assertNotNull("Should be created", instance.getProjectMetadataKey(id2));

        em.getTransaction().begin();
        em.createQuery("Delete from DirectProjectMetadataKeyAudit").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        instance.deleteProjectMetadataKey(id1, USER_ID);
        em.getTransaction().commit();
        assertNull("Should be deleted", instance.getProjectMetadataKey(id1));

        List<DirectProjectMetadataKeyAudit> audits = em.createQuery(
            "SELECT directProjectMetadataKeyAudit "
                + "FROM DirectProjectMetadataKeyAudit directProjectMetadataKeyAudit").getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataKeyAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 3, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", projectMetadataKey.getId(), audit.getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadataKey.getName(), audit.getName());
        assertEquals("The audit is incorrect", projectMetadataKey.getDescription(), audit.getDescription());
        assertEquals("The audit is incorrect", projectMetadataKey.getClientId(), audit.getClientId());
        assertEquals("The audit is incorrect", projectMetadataKey.getGrouping(), audit.getGrouping());
        assertEquals("The audit is incorrect", projectMetadataKey.isSingle(), audit.isSingle());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());

        instance.deleteProjectMetadataKey(id2, USER_ID);
        assertNull("Should be deleted", instance.getProjectMetadataKey(id2));
    }

    /**
     * <p>
     * Accuracy test for getProjectMetadataKey(long projectMetadataKeyId). Return null if not find.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetProjectMetadataKey1() throws Exception {
        assertNull("Should return null.", instance.getProjectMetadataKey(10000000L));
    }

    /**
     * <p>
     * Accuracy test for getProjectMetadataKey(long projectMetadataKeyId). Return the found one.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetProjectMetadataKey2() throws Exception {
        instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        assertDirectProjectMetadataKey(instance.getProjectMetadataKey(projectMetadataKey.getId()), projectMetadataKey);
    }

    /**
     * <p>
     * Accuracy test for getCommonProjectMetadataKeys(). Return empty list when no record found.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetCommonProjectMetadataKeys1() throws Exception {
        em.getTransaction().begin();
        em.createQuery("Delete from DirectProjectMetadataPredefinedValue").executeUpdate();
        em.createQuery("Delete from DirectProjectMetadata").executeUpdate();
        em.createQuery("Delete from DirectProjectMetadataKey").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        assertTrue("Should be empty list.", instance.getCommonProjectMetadataKeys().isEmpty());
    }

    /**
     * <p>
     * Accuracy test for getCommonProjectMetadataKeys(). Return the list of
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetCommonProjectMetadataKeys2() throws Exception {
        projectMetadataKey.setClientId(null);
        projectMetadataKey.setGrouping(null);
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        List<DirectProjectMetadataKey> result = instance.getCommonProjectMetadataKeys();

        assertEquals("The getCommonProjectMetadataKeys is incorrect.", 2, result.size());

        if (result.get(0).getId() == projectMetadataKey.getId()) {
            assertDirectProjectMetadataKey(result.get(0), projectMetadataKey);
        } else {
            assertDirectProjectMetadataKey(result.get(1), projectMetadataKey);
        }
    }

    /**
     * <p>
     * Accuracy test for getClientProjectMetadataKeys(long clientId, Boolean grouping). When no records found,return
     * empty list.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetClientProjectMetadataKeys1() throws Exception {
        assertTrue("Should be empty list.", instance.getClientProjectMetadataKeys(1000000L, null).isEmpty());
    }

    /**
     * <p>
     * Accuracy test for getClientProjectMetadataKeys(long clientId, Boolean grouping).
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetClientProjectMetadataKeys2() throws Exception {
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setGrouping(Boolean.FALSE);
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        List<DirectProjectMetadataKey> result = instance.getClientProjectMetadataKeys(1, null);

        assertEquals("'getClientProjectMetadataKeys' should be correct.", 2, result.size());
        if (result.get(0).getId() == projectMetadataKey.getId()) {
            assertDirectProjectMetadataKey(result.get(0), projectMetadataKey);
        } else {
            assertDirectProjectMetadataKey(result.get(1), projectMetadataKey);
        }
    }

    /**
     * <p>
     * Accuracy test for getClientProjectMetadataKeys(long clientId, Boolean grouping).
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetClientProjectMetadataKeys3() throws Exception {
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setGrouping(Boolean.TRUE);
        em.getTransaction().begin();
        instance.createProjectMetadataKey(projectMetadataKey, USER_ID);
        em.getTransaction().commit();

        List<DirectProjectMetadataKey> result = instance.getClientProjectMetadataKeys(1, Boolean.TRUE);

        assertEquals("'getClientProjectMetadataKeys' should be correct.", 2, result.size());
        if (result.get(0).getId() == projectMetadataKey.getId()) {
            assertDirectProjectMetadataKey(result.get(0), projectMetadataKey);
        } else {
            assertDirectProjectMetadataKey(result.get(1), projectMetadataKey);
        }
    }

    /**
     * <p>
     * Accuracy test for setDirectProjectMetadataKeyValidator( DirectProjectMetadataKeyValidator
     * directProjectMetadataKeyValidator). The value should be set properly.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testSetDirectProjectMetadataKeyValidator() throws Exception {
        DirectProjectMetadataKeyValidator validator = new DirectProjectMetadataKeyValidatorImpl();
        instance.setDirectProjectMetadataKeyValidator(validator);
        assertSame("The directProjectMetadataKeyValidator is incorrect.", validator, AccuracyTestHelper.getField(null,
            instance, "directProjectMetadataKeyValidator"));
    }

    /**
     * <p>
     * Accuracy test for checkInitialization() method. When the instance is initialized properly, no exception should be
     * thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testCheckInitialization() throws Exception {
        instance.checkInitialization();
    }

    /**
     * Gets the instance of DirectProjectMetadataKey for test.
     *
     * @return the created instance.
     */
    private DirectProjectMetadataKey createDirectProjectMetadataKey() {
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setName("key 11");
        projectMetadataKey.setDescription("desc 11");
        projectMetadataKey.setGrouping(Boolean.TRUE);
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setSingle(true);
        projectMetadataKey.setPredefinedValues(null);
        return projectMetadataKey;
    }

    /**
     * Update the instance of DirectProjectMetadataKey for test.
     *
     * @param projectMetadataKey
     *            the instance of DirectProjectMetadataKey to update
     */
    private void updateDirectProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey) {
        projectMetadataKey.setName(projectMetadataKey.getName() + "_u");
        projectMetadataKey.setDescription(projectMetadataKey.getDescription() + "_u");
        projectMetadataKey.setGrouping(!projectMetadataKey.getGrouping());
        projectMetadataKey.setClientId(projectMetadataKey.getClientId() + 1);
        projectMetadataKey.setSingle(!projectMetadataKey.isSingle());
        projectMetadataKey.setPredefinedValues(new ArrayList<DirectProjectMetadataPredefinedValue>());
    }

    /**
     * Compare the 2 instances of DirectProjectMetadataKey.
     *
     * @param key1
     *            the first instance.
     * @param key2
     *            the second instance.
     */
    private static void assertDirectProjectMetadataKey(DirectProjectMetadataKey key1, DirectProjectMetadataKey key2) {
        Assert.assertEquals("The id is incorrect.", key1.getId(), key2.getId());
        Assert.assertEquals("The name is incorrect.", key1.getName(), key2.getName());
        Assert.assertEquals("The description is incorrect.", key1.getDescription(), key2.getDescription());
        Assert.assertEquals("The clientId is incorrect.", key1.getClientId(), key2.getClientId());
        Assert.assertEquals("The grouping is incorrect.", key1.getGrouping(), key2.getGrouping());
        if (key2.getPredefinedValues() == null) {
            Assert.assertTrue("The predefinedValues is incorrect.", key1.getPredefinedValues() == null
                || key1.getPredefinedValues().isEmpty());
        } else {
            Assert.assertEquals("The id is incorrect.", key1.getPredefinedValues().size(), key2.getPredefinedValues()
                .size());
        }
    }

    /**
     * Compare the 2 instances of DirectProjectMetadataPredefinedValue.
     *
     * @param key1
     *            the first instance.
     * @param key2
     *            the second instance.
     */
    private static void assertDirectProjectMetadataPredefinedValue(DirectProjectMetadataPredefinedValue key1,
        DirectProjectMetadataPredefinedValue key2) {
        Assert.assertEquals("The id is incorrect.", key1.getId(), key2.getId());
        Assert.assertEquals("The position is incorrect.", key1.getPosition(), key2.getPosition());
        Assert.assertEquals("The predefinedMetadataValue is incorrect.", key1.getPredefinedMetadataValue(), key2
            .getPredefinedMetadataValue());
        Assert.assertEquals("The projectMetadataKey is incorrect.", key1.getProjectMetadataKey().getId(), key2
            .getProjectMetadataKey().getId());
    }
}