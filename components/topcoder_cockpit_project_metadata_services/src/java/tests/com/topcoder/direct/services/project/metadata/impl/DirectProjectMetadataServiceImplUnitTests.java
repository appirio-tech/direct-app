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

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataKeyService;
import com.topcoder.direct.services.project.metadata.EntityNotFoundException;
import com.topcoder.direct.services.project.metadata.TestsHelper;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
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
 * Unit tests for {@link DirectProjectMetadataServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DirectProjectMetadataServiceImplUnitTests {

    /**
     * <p>
     * Represents the <code>DirectProjectMetadataServiceImpl</code> instance used in tests.
     * </p>
     */
    private DirectProjectMetadataServiceImpl instance;

    /**
     * <p>
     * Represents the project metadata key used in tests.
     * </p>
     */
    private DirectProjectMetadata projectMetadata;

    /**
     * <p>
     * Represents the user id used in tests.
     * </p>
     */
    private long userId = 1;

    /**
     * <p>
     * Represents the id of key used in tests.
     * </p>
     */
    private long keyId;

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
        return new JUnit4TestAdapter(DirectProjectMetadataServiceImplUnitTests.class);
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

        DirectProjectMetadataValidatorImpl directProjectMetadataValidator =
            new DirectProjectMetadataValidatorImpl();
        directProjectMetadataValidator.setValidatorMapping(new HashMap<Long, String>());
        directProjectMetadataValidator.setPredefinedKeys(new HashMap<Long, Boolean>());
        directProjectMetadataValidator.setEntityManager(em);
        directProjectMetadataValidator.setLoggerName("loggerName");

        instance = new DirectProjectMetadataServiceImpl();
        instance.setDirectProjectMetadataValidator(directProjectMetadataValidator);
        instance.setAuditActionTypeIdMap(auditActionTypeIdMap);
        instance.setEntityManager(em);
        instance.setLoggerName("loggerName");


        // Persist key entity
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setName("name3");
        key.setDescription("some text");
        key.setGrouping(null);
        key.setClientId(null);
        key.setSingle(false);

        // Create project metadata key
        keyId = ((DirectProjectMetadataKeyService) TestsHelper.APP_CONTEXT.getBean("directProjectMetadataKeyService"))
            .createProjectMetadataKey(key, userId);

        projectMetadata = new DirectProjectMetadata();
        projectMetadata.setTcDirectProjectId(5);
        projectMetadata.setProjectMetadataKey(key);
        projectMetadata.setMetadataValue("new_value");
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
     * Accuracy test for the constructor <code>DirectProjectMetadataServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DirectProjectMetadataServiceImpl();

        assertNull("'entityManager' should be correct.", TestsHelper.getField(instance, "entityManager"));
        assertNull("'logger' should be correct.", TestsHelper.getField(instance, "logger"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createProjectMetadata() throws Exception {
        instance.createProjectMetadata(projectMetadata, userId);

        DirectProjectMetadata directProjectMetadata =
            em.find(DirectProjectMetadata.class, projectMetadata.getId());

        assertEquals("'createProjectMetadata' should be correct.",
            "new_value", directProjectMetadata.getMetadataValue());
        assertEquals("'createProjectMetadata' should be correct.",
            5, directProjectMetadata.getTcDirectProjectId());
        assertTrue("'createProjectMetadata' should be correct.",
            directProjectMetadata.getProjectMetadataKey().getId() > 0);
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code> with projectMetadata is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createProjectMetadata_projectMetadataNull() throws Exception {
        projectMetadata = null;

        instance.createProjectMetadata(projectMetadata, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code> with entity fails the validation.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_createProjectMetadata_ValidationError() throws Exception {
        projectMetadata.setProjectMetadataKey(null);

        instance.createProjectMetadata(projectMetadata, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateProjectMetadata() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, userId);
        em.getTransaction().commit();

        projectMetadata.setTcDirectProjectId(8);
        em.getTransaction().begin();
        instance.updateProjectMetadata(projectMetadata, userId);
        em.getTransaction().commit();

        DirectProjectMetadata directProjectMetadata =
            em.find(DirectProjectMetadata.class, projectMetadata.getId());

        assertEquals("'updateProjectMetadata' should be correct.",
            "new_value", directProjectMetadata.getMetadataValue());
        assertEquals("'updateProjectMetadata' should be correct.",
            8, directProjectMetadata.getTcDirectProjectId());
        assertTrue("'updateProjectMetadata' should be correct.",
            directProjectMetadata.getProjectMetadataKey().getId() > 0);
    }

    /**
     * <p>
     * Failure test for the method <code>updateProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code> with projectMetadata is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateProjectMetadata_projectMetadataNull() throws Exception {
        projectMetadata = null;

        instance.updateProjectMetadata(projectMetadata, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>updateProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code> with entity fails the validation.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_updateProjectMetadata_ValidationError() throws Exception {
        projectMetadata.setProjectMetadataKey(null);

        instance.updateProjectMetadata(projectMetadata, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>updateProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code> with requested entity is not found in db.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateProjectMetadata_EntityNotFoundError() throws Exception {
        projectMetadata.setId(Long.MAX_VALUE);

        instance.updateProjectMetadata(projectMetadata, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>saveProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_saveProjectMetadata_1() throws Exception {
        em.getTransaction().begin();
        instance.saveProjectMetadata(projectMetadata, userId);
        em.getTransaction().commit();

        Query query = em.createQuery(
            "SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE metadataValue = 'new_value'");

        DirectProjectMetadata directProjectMetadata = (DirectProjectMetadata) query.getSingleResult();


        assertEquals("'saveProjectMetadata' should be correct.",
            "new_value", directProjectMetadata.getMetadataValue());
        assertEquals("'saveProjectMetadata' should be correct.",
            5, directProjectMetadata.getTcDirectProjectId());
        assertTrue("'saveProjectMetadata' should be correct.",
            directProjectMetadata.getProjectMetadataKey().getId() > 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>saveProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_saveProjectMetadata_2() throws Exception {
        em.getTransaction().begin();
        instance.createProjectMetadata(projectMetadata, userId);
        em.getTransaction().commit();

        projectMetadata.setTcDirectProjectId(8);
        em.getTransaction().begin();
        instance.saveProjectMetadata(projectMetadata, userId);
        em.getTransaction().commit();

        DirectProjectMetadata directProjectMetadata =
            em.find(DirectProjectMetadata.class, projectMetadata.getId());

        assertEquals("'saveProjectMetadata' should be correct.",
            "new_value", directProjectMetadata.getMetadataValue());
        assertEquals("'saveProjectMetadata' should be correct.",
            8, directProjectMetadata.getTcDirectProjectId());
        assertTrue("'saveProjectMetadata' should be correct.",
            directProjectMetadata.getProjectMetadataKey().getId() > 0);
    }

    /**
     * <p>
     * Failure test for the method <code>saveProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code> with projectMetadata is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_saveProjectMetadata_projectMetadataNull() throws Exception {
        projectMetadata = null;

        instance.saveProjectMetadata(projectMetadata, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>saveProjectMetadata(DirectProjectMetadata projectMetadata,
     * long userId)</code> with entity fails the validation.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_saveProjectMetadata_ValidationError() throws Exception {
        projectMetadata.setProjectMetadataKey(null);

        instance.saveProjectMetadata(projectMetadata, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteProjectMetadata(long projectMetadataId,
     * long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_deleteProjectMetadata() throws Exception {
        instance.createProjectMetadata(projectMetadata, userId);

        instance.deleteProjectMetadata(projectMetadata.getId(), userId);

        assertNull("'deleteProjectMetadata' should be correct.",
            em.find(DirectProjectMetadata.class, projectMetadata.getId()));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteProjectMetadata(long projectMetadataId,
     * long userId)</code> with requested entity is not found in db.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_deleteProjectMetadata_EntityNotFoundError() throws Exception {
        instance.deleteProjectMetadata(Long.MAX_VALUE, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectMetadata(long projectMetadataId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getProjectMetadata_1() throws Exception {
        instance.createProjectMetadata(projectMetadata, userId);

        DirectProjectMetadata res = instance.getProjectMetadata(projectMetadata.getId());

        assertEquals("'getProjectMetadata' should be correct.",
            "new_value", res.getMetadataValue());
        assertEquals("'getProjectMetadata' should be correct.",
            5, res.getTcDirectProjectId());
        assertTrue("'getProjectMetadata' should be correct.",
            res.getProjectMetadataKey().getId() > 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectMetadata(long projectMetadataId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getProjectMetadata_2() throws Exception {
        DirectProjectMetadata res = instance.getProjectMetadata(Long.MAX_VALUE);

        assertNull("'getProjectMetadata' should be correct.", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectMetadataByProject(long tcDirectProjectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getProjectMetadataByProject_1() throws Exception {
        List<DirectProjectMetadata> res = instance.getProjectMetadataByProject(1);

        assertEquals("'getProjectMetadataByProject' should be correct.", 3, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectMetadataByProject(long tcDirectProjectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getProjectMetadataByProject_2() throws Exception {
        List<DirectProjectMetadata> res = instance.getProjectMetadataByProject(Long.MAX_VALUE);

        assertEquals("'getProjectMetadataByProject' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>addProjectMetadata(long tcDirectProjectId,
     * List&lt;DirectProjectMetadataDTO&gt; projectMetadataList, long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_addProjectMetadata1() throws Exception {
        List<DirectProjectMetadataDTO> projectMetadataList = new ArrayList<DirectProjectMetadataDTO>();
        DirectProjectMetadataDTO dto = new DirectProjectMetadataDTO();
        dto.setMetadataValue("new_value1");
        dto.setProjectMetadataKeyId(keyId);
        projectMetadataList.add(dto);
        dto = new DirectProjectMetadataDTO();
        dto.setMetadataValue("new_value2");
        dto.setProjectMetadataKeyId(keyId);
        projectMetadataList.add(dto);

        em.getTransaction().begin();
        instance.addProjectMetadata(1, projectMetadataList, userId);
        em.getTransaction().commit();

        Query query = em.createQuery(
            "SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE metadataValue = 'new_value1'");
        DirectProjectMetadata directProjectMetadata = (DirectProjectMetadata) query.getSingleResult();


        assertEquals("'addProjectMetadata' should be correct.",
            "new_value1", directProjectMetadata.getMetadataValue());
        assertEquals("'addProjectMetadata' should be correct.",
            1, directProjectMetadata.getTcDirectProjectId());
        assertTrue("'addProjectMetadata' should be correct.",
            directProjectMetadata.getProjectMetadataKey().getId() > 0);

        query = em.createQuery(
            "SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE metadataValue = 'new_value2'");
        directProjectMetadata = (DirectProjectMetadata) query.getSingleResult();


        assertEquals("'addProjectMetadata' should be correct.",
            "new_value2", directProjectMetadata.getMetadataValue());
        assertEquals("'addProjectMetadata' should be correct.",
            1, directProjectMetadata.getTcDirectProjectId());
        assertTrue("'addProjectMetadata' should be correct.",
            directProjectMetadata.getProjectMetadataKey().getId() > 0);
    }

    /**
     * <p>
     * Failure test for the method <code>addProjectMetadata(long tcDirectProjectId,
     * List&lt;DirectProjectMetadataDTO&gt; projectMetadataList, long userId)</code>
     * with projectMetadataList is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_addProjectMetadata1_projectMetadataListNull() throws Exception {
        instance.addProjectMetadata(1, null, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>addProjectMetadata(long tcDirectProjectId,
     * List&lt;DirectProjectMetadataDTO&gt; projectMetadataList, long userId)</code>
     * with projectMetadataList contains <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_addProjectMetadata1_projectMetadataListContainsNull() throws Exception {
        List<DirectProjectMetadataDTO> projectMetadataList = new ArrayList<DirectProjectMetadataDTO>();
        DirectProjectMetadataDTO dto = new DirectProjectMetadataDTO();
        dto.setMetadataValue("new_value1");
        dto.setProjectMetadataKeyId(keyId);
        projectMetadataList.add(dto);

        projectMetadataList.add(null);

        instance.addProjectMetadata(1, projectMetadataList, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>addProjectMetadata(long tcDirectProjectId,
     * List&lt;DirectProjectMetadataDTO&gt; projectMetadataList, long userId)</code>
     * with entity fails the validation.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_addProjectMetadata1_ValidationError() throws Exception {
        List<DirectProjectMetadataDTO> projectMetadataList = new ArrayList<DirectProjectMetadataDTO>();
        DirectProjectMetadataDTO dto = new DirectProjectMetadataDTO();
        dto.setMetadataValue(null);
        dto.setProjectMetadataKeyId(keyId);
        projectMetadataList.add(dto);

        instance.addProjectMetadata(1, projectMetadataList, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>addProjectMetadata(long[] tcDirectProjectIds,
     * DirectProjectMetadataDTO projectMetadata, long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_addProjectMetadata2() throws Exception {
        DirectProjectMetadataDTO dto = new DirectProjectMetadataDTO();
        dto.setMetadataValue("new_value1");
        dto.setProjectMetadataKeyId(keyId);

        em.getTransaction().begin();
        instance.addProjectMetadata(new long[] {7, 8}, dto, userId);
        em.getTransaction().commit();

        Query query = em.createQuery(
            "SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE metadataValue = 'new_value1'");

        assertEquals("'addProjectMetadata' should be correct.", 2, query.getResultList().size());
    }

    /**
     * <p>
     * Failure test for the method <code>addProjectMetadata(long[] tcDirectProjectIds,
     * DirectProjectMetadataDTO projectMetadata, long userId)</code>
     * with tcDirectProjectIds is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_addProjectMetadata2_tcDirectProjectIdsNull() throws Exception {
        DirectProjectMetadataDTO dto = new DirectProjectMetadataDTO();
        dto.setMetadataValue("new_value1");
        dto.setProjectMetadataKeyId(keyId);

        instance.addProjectMetadata(null, dto, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>addProjectMetadata(long[] tcDirectProjectIds,
     * DirectProjectMetadataDTO projectMetadata, long userId)</code>
     * with projectMetadataList is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_addProjectMetadata2_projectMetadataListNull() throws Exception {
        instance.addProjectMetadata(new long[] {1}, null, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>addProjectMetadata(long[] tcDirectProjectIds,
     * DirectProjectMetadataDTO projectMetadata, long userId)</code>
     * with entity fails the validation.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_addProjectMetadata2_ValidationError() throws Exception {
        DirectProjectMetadataDTO dto = new DirectProjectMetadataDTO();
        dto.setMetadataValue(null);
        dto.setProjectMetadataKeyId(keyId);

        instance.addProjectMetadata(new long[] {1}, dto, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchProjects(DirectProjectFilter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchProjects_1() throws Exception {
        MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
        filter.setProjectMetadataKeyName("name2");
        filter.setMetadataValue("second value");
        filter.setMetadataValueOperator(MetadataValueOperator.EQUALS);

        List<TcDirectProject> res = instance.searchProjects(filter);

        assertEquals("'searchProjects' should be correct.", 1, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchProjects(DirectProjectFilter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchProjects_2() throws Exception {
        MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
        filter.setProjectMetadataKeyName("name2");
        filter.setMetadataValue("sec");
        filter.setMetadataValueOperator(MetadataValueOperator.LIKE);

        List<TcDirectProject> res = instance.searchProjects(filter);

        assertEquals("'searchProjects' should be correct.", 1, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchProjects(DirectProjectFilter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchProjects_3() throws Exception {
        MetadataKeyIdValueFilter filter = new MetadataKeyIdValueFilter();
        filter.setProjectMetadataKeyId(2);
        filter.setMetadataValue("second value");
        filter.setMetadataValueOperator(MetadataValueOperator.EQUALS);

        List<TcDirectProject> res = instance.searchProjects(filter);

        assertEquals("'searchProjects' should be correct.", 1, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchProjects(DirectProjectFilter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchProjects_4() throws Exception {
        MetadataKeyIdValueFilter filter = new MetadataKeyIdValueFilter();
        filter.setProjectMetadataKeyId(2);
        filter.setMetadataValue("sec");
        filter.setMetadataValueOperator(MetadataValueOperator.LIKE);

        List<TcDirectProject> res = instance.searchProjects(filter);

        assertEquals("'searchProjects' should be correct.", 1, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchProjects(DirectProjectFilter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchProjects_5() throws Exception {
        MetadataKeyNameValueFilter filter1 = new MetadataKeyNameValueFilter();
        filter1.setProjectMetadataKeyName("name2");
        filter1.setMetadataValue("second value");
        filter1.setMetadataValueOperator(MetadataValueOperator.EQUALS);
        MetadataKeyIdValueFilter filter2 = new MetadataKeyIdValueFilter();
        filter2.setProjectMetadataKeyId(1);
        filter2.setMetadataValue("first");
        filter2.setMetadataValueOperator(MetadataValueOperator.LIKE);

        List<DirectProjectFilter> projectFilters = new ArrayList<DirectProjectFilter>();
        projectFilters.add(filter1);
        projectFilters.add(filter2);

        CompositeFilter filter = new CompositeFilter();
        filter.setCompositeOperator(CompositeOperator.OR);
        filter.setProjectFilters(projectFilters);
        List<TcDirectProject> res = instance.searchProjects(filter);

        assertEquals("'searchProjects' should be correct.", 1, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchProjects(DirectProjectFilter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchProjects_6() throws Exception {
        MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
        filter.setProjectMetadataKeyName("not_exist");
        filter.setMetadataValueOperator(MetadataValueOperator.EQUALS);

        List<TcDirectProject> res = instance.searchProjects(filter);

        assertEquals("'searchProjects' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>searchProjects(DirectProjectFilter filter)</code> with filter is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchProjects_filterNull() throws Exception {
        instance.searchProjects(null);
    }

    /**
     * <p>
     * Failure test for the method <code>searchProjects(DirectProjectFilter filter)</code> with filter is invalid.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchProjects_filterInvalid1() throws Exception {
        DirectProjectFilter filter = new MockDirectProjectFilter();

        instance.searchProjects(filter);
    }

    /**
     * <p>
     * Failure test for the method <code>searchProjects(DirectProjectFilter filter)</code> with filter is invalid.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchProjects_filterInvalid2() throws Exception {
        CompositeFilter filter = new CompositeFilter();
        filter.setCompositeOperator(CompositeOperator.OR);

        instance.searchProjects(filter);
    }

    /**
     * <p>
     * Failure test for the method <code>searchProjects(DirectProjectFilter filter)</code> with filter is invalid.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchProjects_filterInvalid3() throws Exception {
        MetadataKeyNameValueFilter filter1 = new MetadataKeyNameValueFilter();
        filter1.setProjectMetadataKeyName("name2");
        filter1.setMetadataValue("second value");
        filter1.setMetadataValueOperator(MetadataValueOperator.EQUALS);

        List<DirectProjectFilter> projectFilters = new ArrayList<DirectProjectFilter>();
        projectFilters.add(filter1);

        CompositeFilter filter = new CompositeFilter();
        filter.setCompositeOperator(CompositeOperator.OR);
        filter.setProjectFilters(projectFilters);

        instance.searchProjects(filter);
    }

    /**
     * <p>
     * Failure test for the method <code>searchProjects(DirectProjectFilter filter)</code> with filter is invalid.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchProjects_filterInvalid4() throws Exception {
        MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
        filter.setProjectMetadataKeyName("name2");
        filter.setMetadataValue("second %value");
        filter.setMetadataValueOperator(MetadataValueOperator.EQUALS);

        instance.searchProjects(filter);
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
     * Failure test for the method <code>checkInitialization()</code> with directProjectMetadataValidator is
     * <code>null</code>.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_directProjectMetadataValidatorNull() throws Exception {
        instance.setDirectProjectMetadataValidator(null);

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
