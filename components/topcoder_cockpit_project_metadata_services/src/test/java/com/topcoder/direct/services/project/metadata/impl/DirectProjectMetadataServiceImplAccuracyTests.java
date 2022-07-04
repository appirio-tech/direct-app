/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

import com.topcoder.direct.services.project.metadata.DirectProjectMetadataValidator;
import com.topcoder.direct.services.project.metadata.accuracytests.impl.AccuracyTestHelper;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataAudit;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.CompositeFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.CompositeOperator;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTO;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyIdValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyNameValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;

/**
 * <p>
 * Accuracy test for DirectProjectMetadataServiceImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DirectProjectMetadataServiceImplAccuracyTests {
    /**
     * <p>
     * Represents the user id used in tests.
     * </p>
     */
    private static final long USER_ID = 1;

    /**
     * <p>
     * Represents the instance of DirectProjectMetadataServiceImpl used in tests.
     * </p>
     */
    private DirectProjectMetadataServiceImpl instance;

    /**
     * <p>
     * Represents the instance of DirectProjectMetadata used in tests.
     * </p>
     */
    private DirectProjectMetadata projectMetadata;

    /**
     * <p>
     * Represents the id of key used in tests.
     * </p>
     */
    private long keyId;

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
        return new JUnit4TestAdapter(DirectProjectMetadataServiceImplAccuracyTests.class);
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

        // initializes the instance
        instance = new DirectProjectMetadataServiceImpl();
        instance.setDirectProjectMetadataValidator(createValidator());
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);
        instance.setEntityManager(em);
        instance.setLoggerName("acctest");

        projectMetadata = createDirectProjectMetadata();
        keyId = projectMetadata.getProjectMetadataKey().getId();
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
     * Accuracy test for DirectProjectMetadataServiceImpl(). The instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testCtor() throws Exception {
        instance = new DirectProjectMetadataServiceImpl();
        assertNull("The entityManager is incorrect.", AccuracyTestHelper.getField(
            AbstractDirectProjectMetadataService.class, instance, "entityManager"));
        assertNull("The logger is incorrect.", AccuracyTestHelper.getField(AbstractDirectProjectMetadataService.class,
            instance, "logger"));
    }

    /**
     * Compare the 2 instances of DirectProjectMetadata.
     *
     * @param data1
     *            the first instance.
     * @param data2
     *            the second instance.
     */
    private void assertDirectProjectMetadata(DirectProjectMetadata data1, DirectProjectMetadata data2) {
        Assert.assertEquals("The id is incorrect.", data1.getId(), data2.getId());
        Assert.assertEquals("The metadataValue is incorrect.", data1.getMetadataValue(), data2.getMetadataValue());
        Assert.assertEquals("The tDirectProjectId is incorrect.", data1.getTcDirectProjectId(), data2
            .getTcDirectProjectId());
        Assert.assertEquals("The projectMetadataKey is incorrect.", data1.getProjectMetadataKey().getId(), data2
            .getProjectMetadataKey().getId());
    }

    /**
     * <p>
     * Accuracy test for createProjectMetadata(DirectProjectMetadata projectMetadata, long userId). The instance should
     * be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateProjectMetadata() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        DirectProjectMetadata directProjectMetadata = em.find(DirectProjectMetadata.class, projectMetadata.getId());
        assertDirectProjectMetadata(directProjectMetadata, projectMetadata);

        List<DirectProjectMetadataAudit> audits = em.createQuery(
            "SELECT directProjectMetadataAudit FROM DirectProjectMetadataAudit directProjectMetadataAudit")
            .getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 1, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());

        assertEquals("The audit is incorrect", projectMetadata.getTcDirectProjectId(), audit.getTcDirectProjectId());
        assertEquals("The audit is incorrect", projectMetadata.getProjectMetadataKey().getId(), audit
            .getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadata.getMetadataValue(), audit.getMetadataValue());
    }

    /**
     * <p>
     * Accuracy test for updateProjectMetadata(DirectProjectMetadata projectMetadata, long USER_ID). The projectMetadata
     * should be updated.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateProjectMetadata() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("Delete from DirectProjectMetadataAudit").executeUpdate();
        em.getTransaction().commit();

        updateDirectProjectMetadata(projectMetadata);
        em.getTransaction().begin();
        instance.updateProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        assertDirectProjectMetadata(em.find(DirectProjectMetadata.class, projectMetadata.getId()), projectMetadata);

        List<DirectProjectMetadataAudit> audits = em.createQuery(
            "SELECT directProjectMetadataAudit FROM DirectProjectMetadataAudit directProjectMetadataAudit")
            .getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 2, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());

        assertEquals("The audit is incorrect", projectMetadata.getTcDirectProjectId(), audit.getTcDirectProjectId());
        assertEquals("The audit is incorrect", projectMetadata.getProjectMetadataKey().getId(), audit
            .getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadata.getMetadataValue(), audit.getMetadataValue());
    }

    /**
     * <p>
     * Accuracy test for saveProjectMetadata(DirectProjectMetadata projectMetadata, long USER_ID). It used to create
     * projectMetadata.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSaveProjectMetadata1() throws Exception {
        em.getTransaction().begin();
        instance.saveProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        Query query = em.createQuery("SELECT directProjectMetadata FROM DirectProjectMetadata  "
            + "directProjectMetadata WHERE metadataValue = '" + projectMetadata.getMetadataValue() + "'");
        DirectProjectMetadata data2 = (DirectProjectMetadata) query.getSingleResult();
        projectMetadata.setId(data2.getId());
        assertDirectProjectMetadata(projectMetadata, data2);

        List<DirectProjectMetadataAudit> audits = em.createQuery(
            "SELECT directProjectMetadataAudit FROM DirectProjectMetadataAudit directProjectMetadataAudit")
            .getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 2, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());

        assertEquals("The audit is incorrect", projectMetadata.getTcDirectProjectId(), audit.getTcDirectProjectId());
        assertEquals("The audit is incorrect", projectMetadata.getProjectMetadataKey().getId(), audit
            .getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadata.getMetadataValue(), audit.getMetadataValue());
    }

    /**
     * <p>
     * Accuracy test for saveProjectMetadata(DirectProjectMetadata projectMetadata, long USER_ID). It used to update
     * projectMetadata.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSaveProjectMetadata2() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("Delete from DirectProjectMetadataAudit").executeUpdate();
        em.getTransaction().commit();

        updateDirectProjectMetadata(projectMetadata);
        em.getTransaction().begin();
        instance.saveProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        assertDirectProjectMetadata(em.find(DirectProjectMetadata.class, projectMetadata.getId()), projectMetadata);

        List<DirectProjectMetadataAudit> audits = em.createQuery(
            "SELECT directProjectMetadataAudit FROM DirectProjectMetadataAudit directProjectMetadataAudit")
            .getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 2, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());

        assertEquals("The audit is incorrect", projectMetadata.getTcDirectProjectId(), audit.getTcDirectProjectId());
        assertEquals("The audit is incorrect", projectMetadata.getProjectMetadataKey().getId(), audit
            .getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadata.getMetadataValue(), audit.getMetadataValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteProjectMetadata(long projectMetadataId,
     * long USER_ID)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteProjectMetadata() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        DirectProjectMetadata projectMetadata2 = createDirectProjectMetadata();
        projectMetadata2.setProjectMetadataKey(projectMetadata.getProjectMetadataKey());

        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata2, USER_ID);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("Delete from DirectProjectMetadataAudit").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        instance.deleteProjectMetadata(projectMetadata.getId(), USER_ID);
        em.getTransaction().commit();
        assertNull("Should be deleted.", em.find(DirectProjectMetadata.class, projectMetadata.getId()));

        List<DirectProjectMetadataAudit> audits = em.createQuery(
            "SELECT directProjectMetadataAudit FROM DirectProjectMetadataAudit directProjectMetadataAudit")
            .getResultList();
        assertEquals("There should one audit record.", 1, audits.size());

        DirectProjectMetadataAudit audit = audits.get(0);
        assertEquals("The audit is incorrect", 3, audit.getAuditActionTypeId());
        assertEquals("The audit is incorrect", USER_ID, audit.getActionUserId());

        assertEquals("The audit is incorrect", projectMetadata.getTcDirectProjectId(), audit.getTcDirectProjectId());
        assertEquals("The audit is incorrect", projectMetadata.getProjectMetadataKey().getId(), audit
            .getProjectMetadataKeyId());
        assertEquals("The audit is incorrect", projectMetadata.getMetadataValue(), audit.getMetadataValue());

        instance.deleteProjectMetadata(projectMetadata2.getId(), USER_ID);
        assertNull("Should be deleted.", em.find(DirectProjectMetadata.class, projectMetadata2.getId()));
    }

    /**
     * <p>
     * Accuracy test for getProjectMetadata(long projectMetadataId). When no record found, return null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetProjectMetadata1() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();
        assertNull("Should return null.", instance.getProjectMetadata(projectMetadata.getId() + 10));
    }

    /**
     * <p>
     * Accuracy test for getProjectMetadata(long projectMetadataId). When record found, return it.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetProjectMetadata2() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        DirectProjectMetadata projectMetadata2 = createDirectProjectMetadata();
        projectMetadata2.setProjectMetadataKey(projectMetadata.getProjectMetadataKey());

        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata2, USER_ID);
        em.getTransaction().commit();

        assertDirectProjectMetadata(instance.getProjectMetadata(projectMetadata.getId()), projectMetadata);
    }

    /**
     * <p>
     * Accuracy test for getProjectMetadataByProject(long tcDirectProjectId). When no record found, return empty list.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetProjectMetadataByProject1() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        assertTrue("Should be empty.", instance.getProjectMetadataByProject(1000000L).isEmpty());
    }

    /**
     * <p>
     * Accuracy test for getProjectMetadataByProject(long tcDirectProjectId). When record found, return the list.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetProjectMetadataByProject2() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, USER_ID);
        em.getTransaction().commit();

        DirectProjectMetadata projectMetadata2 = createDirectProjectMetadata();
        projectMetadata2.setProjectMetadataKey(projectMetadata.getProjectMetadataKey());

        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata2, USER_ID);
        em.getTransaction().commit();

        DirectProjectMetadata projectMetadata3 = createDirectProjectMetadata();
        projectMetadata3.setTcDirectProjectId(6);
        projectMetadata3.setProjectMetadataKey(projectMetadata.getProjectMetadataKey());

        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata3, USER_ID);
        em.getTransaction().commit();

        List<DirectProjectMetadata> list = instance.getProjectMetadataByProject(projectMetadata.getTcDirectProjectId());
        assertEquals("Should have 2 record.", 2, list.size());

        assertDirectProjectMetadata(list.get(0), projectMetadata);
        assertDirectProjectMetadata(list.get(1), projectMetadata2);
    }

    /**
     * <p>
     * Accuracy test for addProjectMetadata(long tcDirectProjectId, List&lt;DirectProjectMetadataDTO&gt;
     * projectMetadataList, long USER_ID). The records should be added.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAddProjectMetadata1() throws Exception {
        List<DirectProjectMetadataDTO> projectMetadataList = new ArrayList<DirectProjectMetadataDTO>();

        DirectProjectMetadataDTO metadataDTO1 = new DirectProjectMetadataDTO();
        metadataDTO1.setMetadataValue("v 1");
        metadataDTO1.setProjectMetadataKeyId(keyId);
        projectMetadataList.add(metadataDTO1);

        DirectProjectMetadataDTO metadataDTO2 = new DirectProjectMetadataDTO();
        metadataDTO2.setMetadataValue("v 2");
        metadataDTO2.setProjectMetadataKeyId(keyId);
        projectMetadataList.add(metadataDTO2);

        em.getTransaction().begin();
        instance.addProjectMetadata(1, projectMetadataList, USER_ID);
        em.getTransaction().commit();

        Query query = em.createQuery("SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE projectMetadataKey = " + keyId + " Order by metadataValue");
        List<DirectProjectMetadata> list = (List<DirectProjectMetadata>) query.getResultList();
        assertEquals("Should contains 2 record.", 2, list.size());
    }

    /**
     * <p>
     * Accuracy test for addProjectMetadata(long tcDirectProjectId, List&lt;DirectProjectMetadataDTO&gt;
     * projectMetadataList, long USER_ID). No records added.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAddProjectMetadata2() throws Exception {
        List<DirectProjectMetadataDTO> projectMetadataList = new ArrayList<DirectProjectMetadataDTO>();

        em.getTransaction().begin();
        instance.addProjectMetadata(1, projectMetadataList, USER_ID);
        em.getTransaction().commit();

        Query query = em.createQuery("SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE projectMetadataKey = " + keyId + " Order by metadataValue");
        List<DirectProjectMetadata> list = (List<DirectProjectMetadata>) query.getResultList();
        assertTrue("Should be empty.", list.isEmpty());
    }

    /**
     * <p>
     * Accuracy test for addProjectMetadata(long[] tcDirectProjectIds, DirectProjectMetadataDTO projectMetadata, long
     * USER_ID). The records should be added.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAddProjectMetadata3() throws Exception {
        DirectProjectMetadataDTO metadataDTO1 = new DirectProjectMetadataDTO();
        metadataDTO1.setMetadataValue("v 1");
        metadataDTO1.setProjectMetadataKeyId(keyId);

        em.getTransaction().begin();
        instance.addProjectMetadata(new long[] { 12, 13 }, metadataDTO1, USER_ID);
        em.getTransaction().commit();

        Query query = em.createQuery("SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE tcDirectProjectId = 12 and metadataValue ='v 1'");
        List<DirectProjectMetadata> list = (List<DirectProjectMetadata>) query.getResultList();
        assertEquals("Should be addeed.", 1, list.size());

        query = em.createQuery("SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE tcDirectProjectId = 13 and metadataValue ='v 1'");
        list = (List<DirectProjectMetadata>) query.getResultList();
        assertEquals("Should be added.", 1, list.size());
    }

    /**
     * <p>
     * Accuracy test for addProjectMetadata(long[] tcDirectProjectIds, DirectProjectMetadataDTO projectMetadata, long
     * USER_ID). The records should be added.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testAddProjectMetadata4() throws Exception {
        DirectProjectMetadataDTO metadataDTO1 = new DirectProjectMetadataDTO();
        metadataDTO1.setMetadataValue("v 5");
        metadataDTO1.setProjectMetadataKeyId(keyId);

        em.getTransaction().begin();
        instance.addProjectMetadata(new long[] {}, metadataDTO1, USER_ID);
        em.getTransaction().commit();

        Query query = em.createQuery("SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE metadataValue ='v 5'");
        assertTrue("Should be empty.", query.getResultList().isEmpty());
    }

    /**
     * <p>
     * Accuracy test for searchProjects(DirectProjectFilter filter). When the filter is MetadataKeyIdValueFilter.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchProjects1() throws Exception {
        MetadataKeyIdValueFilter filter = new MetadataKeyIdValueFilter();
        filter.setProjectMetadataKeyId(1);
        filter.setMetadataValue("v 1");
        filter.setMetadataValueOperator(MetadataValueOperator.EQUALS);

        List<TcDirectProject> list = instance.searchProjects(filter);
        assertEquals("The searchProjects is incorrect.", 2, list.size());
        TcDirectProject project = list.get(0);
        assertEquals("The searchProjects is incorrect.", 1, project.getProjectId());

        project = list.get(1);
        assertEquals("The searchProjects is incorrect.", 2, project.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for searchProjects(DirectProjectFilter filter). When the filter is MetadataKeyIdValueFilter.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchProjects2() throws Exception {
        MetadataKeyIdValueFilter filter = new MetadataKeyIdValueFilter();
        filter.setProjectMetadataKeyId(1);
        filter.setMetadataValue("v 51");
        filter.setMetadataValueOperator(MetadataValueOperator.EQUALS);

        assertTrue("The searchProjects is incorrect.", instance.searchProjects(filter).isEmpty());
    }

    /**
     * <p>
     * Accuracy test for searchProjects(DirectProjectFilter filter). When the filter is MetadataKeyIdValueFilter.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchProjects3() throws Exception {
        MetadataKeyIdValueFilter filter = new MetadataKeyIdValueFilter();
        filter.setProjectMetadataKeyId(1);
        filter.setMetadataValue("v 1");
        filter.setMetadataValueOperator(MetadataValueOperator.LIKE);

        List<TcDirectProject> list = instance.searchProjects(filter);
        assertEquals("The searchProjects is incorrect.", 3, list.size());
        TcDirectProject project = list.get(0);
        assertEquals("The searchProjects is incorrect.", 1, project.getProjectId());

        project = list.get(1);
        assertEquals("The searchProjects is incorrect.", 2, project.getProjectId());

        project = list.get(2);
        assertEquals("The searchProjects is incorrect.", 3, project.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for searchProjects(DirectProjectFilter filter). When the filter is MetadataKeyNameValueFilter.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchProjects4() throws Exception {
        MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
        filter.setProjectMetadataKeyName("n 1");
        filter.setMetadataValue("v 1");
        filter.setMetadataValueOperator(MetadataValueOperator.EQUALS);

        List<TcDirectProject> list = instance.searchProjects(filter);

        assertEquals("The searchProjects is incorrect.", 2, list.size());
    }

    /**
     * <p>
     * Accuracy test for searchProjects(DirectProjectFilter filter). When the filter is MetadataKeyNameValueFilter.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchProjects5() throws Exception {
        MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
        filter.setProjectMetadataKeyName("n 1");
        filter.setMetadataValue("v 51");
        filter.setMetadataValueOperator(MetadataValueOperator.EQUALS);

        assertTrue("The searchProjects is incorrect.", instance.searchProjects(filter).isEmpty());
    }

    /**
     * <p>
     * Accuracy test for searchProjects(DirectProjectFilter filter). When the filter is MetadataKeyNameValueFilter.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchProjects6() throws Exception {
        MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
        filter.setProjectMetadataKeyName("n 1");
        filter.setMetadataValue("v 1");
        filter.setMetadataValueOperator(MetadataValueOperator.LIKE);

        List<TcDirectProject> list = instance.searchProjects(filter);

        assertEquals("The searchProjects is incorrect.", 3, list.size());
    }

    /**
     * <p>
     * Accuracy test for searchProjects(DirectProjectFilter filter). When the filter is CompositeFilter.
     * </p>
     *
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchProjects7() throws Exception {
        MetadataKeyIdValueFilter filter1 = new MetadataKeyIdValueFilter();
        filter1.setProjectMetadataKeyId(1);
        filter1.setMetadataValue("v 1");
        filter1.setMetadataValueOperator(MetadataValueOperator.LIKE);

        MetadataKeyNameValueFilter filter2 = new MetadataKeyNameValueFilter();
        filter2.setProjectMetadataKeyName("n 2");
        filter2.setMetadataValue("v 1");
        filter2.setMetadataValueOperator(MetadataValueOperator.LIKE);

        List<DirectProjectFilter> projectFilters = new ArrayList<DirectProjectFilter>();
        projectFilters.add(filter1);
        projectFilters.add(filter2);

        CompositeFilter filter = new CompositeFilter();
        filter.setCompositeOperator(CompositeOperator.AND);
        filter.setProjectFilters(projectFilters);

        List<TcDirectProject> list = instance.searchProjects(filter);

        assertEquals("'searchProjects' should be correct.", 0, list.size());
    }

    /**
     * <p>
     * Accuracy test for searchProjects(DirectProjectFilter filter). When the filter is CompositeFilter.
     * </p>
     *
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchProjects8() throws Exception {
        MetadataKeyIdValueFilter filter1 = new MetadataKeyIdValueFilter();
        filter1.setProjectMetadataKeyId(3);
        filter1.setMetadataValue("v 1");
        filter1.setMetadataValueOperator(MetadataValueOperator.LIKE);

        MetadataKeyNameValueFilter filter2 = new MetadataKeyNameValueFilter();
        filter2.setProjectMetadataKeyName("n 3");
        filter2.setMetadataValue("v 1");
        filter2.setMetadataValueOperator(MetadataValueOperator.LIKE);

        List<DirectProjectFilter> projectFilters = new ArrayList<DirectProjectFilter>();
        projectFilters.add(filter1);
        projectFilters.add(filter2);

        CompositeFilter filter = new CompositeFilter();
        filter.setCompositeOperator(CompositeOperator.AND);
        filter.setProjectFilters(projectFilters);

        List<TcDirectProject> list = instance.searchProjects(filter);

        assertEquals("'searchProjects' should be correct.", 1, list.size());
    }

    /**
     * <p>
     * Accuracy test for searchProjects(DirectProjectFilter filter). When the filter is CompositeFilter.
     * </p>
     *
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchProjects9() throws Exception {
        MetadataKeyIdValueFilter filter1 = new MetadataKeyIdValueFilter();
        filter1.setProjectMetadataKeyId(1);
        filter1.setMetadataValue("v 1");
        filter1.setMetadataValueOperator(MetadataValueOperator.LIKE);

        MetadataKeyNameValueFilter filter2 = new MetadataKeyNameValueFilter();
        filter2.setProjectMetadataKeyName("n 2");
        filter2.setMetadataValue("v 1");
        filter2.setMetadataValueOperator(MetadataValueOperator.LIKE);

        List<DirectProjectFilter> projectFilters = new ArrayList<DirectProjectFilter>();
        projectFilters.add(filter1);
        projectFilters.add(filter2);

        CompositeFilter filter = new CompositeFilter();
        filter.setCompositeOperator(CompositeOperator.OR);
        filter.setProjectFilters(projectFilters);

        List<TcDirectProject> list = instance.searchProjects(filter);

        assertEquals("'searchProjects' should be correct.", 3, list.size());
    }

    /**
     * <p>
     * Accuracy test for checkInitialization(). When the instance is configured, no exception should be thrown.
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
     * Creates the DirectProjectMetadataValidator instance.
     *
     * @return the DirectProjectMetadataValidator instance.
     */
    private DirectProjectMetadataValidator createValidator() {
        DirectProjectMetadataValidatorImpl validator = new DirectProjectMetadataValidatorImpl();
        validator.setValidatorMapping(new HashMap<Long, String>());
        validator.setPredefinedKeys(new HashMap<Long, Boolean>());
        validator.setEntityManager(em);
        validator.setLoggerName("acctest");
        return validator;
    }

    /**
     * Create instance of DirectProjectMetadata used in test.
     *
     * @return the created instance.
     */
    private DirectProjectMetadata createDirectProjectMetadata() {
        DirectProjectMetadata projectMetadata = new DirectProjectMetadata();
        projectMetadata.setTcDirectProjectId(5);
        projectMetadata.setProjectMetadataKey(createDirectProjectMetadataKey());
        projectMetadata.setMetadataValue("new_value");
        return projectMetadata;
    }

    /**
     * Updates the projectMetadata used in test.
     *
     * @param projectMetadata
     *            the instance to update.
     */
    private void updateDirectProjectMetadata(DirectProjectMetadata projectMetadata) {
        projectMetadata.setTcDirectProjectId(projectMetadata.getTcDirectProjectId() + 1);
        projectMetadata.setMetadataValue(projectMetadata.getMetadataValue() + "u");
    }

    /**
     * Creates the DirectProjectMetadataKey instance used in test.
     *
     * @return the created instance.
     */
    private DirectProjectMetadataKey createDirectProjectMetadataKey() {
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(4);
        key.setName("n 4");
        key.setDescription("desc 4");
        key.setGrouping(null);
        key.setClientId(null);
        key.setSingle(false);

        return key;
    }
}