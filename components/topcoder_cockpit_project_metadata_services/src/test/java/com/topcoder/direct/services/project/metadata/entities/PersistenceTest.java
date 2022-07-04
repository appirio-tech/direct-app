/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataAudit;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKeyAudit;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValueAudit;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;

import junit.framework.JUnit4TestAdapter;

/**
 * <p>
 * This JUnit Test suite contains the persistence test cases of this component.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class PersistenceTest {
    /**
     * The date format used for converting Date instances to strings. It is used in formatDate().
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * EntityManager instance used for the testing.
     */
    private static EntityManager em;

    /**
     * EntityTransaction instance used for the testing.
     */
    private static EntityTransaction et;

    /**
     * The test suite.
     * @return the test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PersistenceTest.class);
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception to jUnit.
     */
    @Before
    public void setUp() throws Exception {
    	try {
        if (em == null) {
            em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
            et = em.getTransaction();
        }
        runSQL("test_files/sql/clear.sql", em);
    	} catch (Throwable e) {
    		e.printStackTrace();
		}
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to jUnit.
     */
    @After
    public void tearDown() throws Exception {
        if (et.isActive()) {
            et.rollback();
        }
        if ((em != null) && em.isOpen()) {
            runSQL("test_files/sql/clear.sql", em);
        }
    }

    /**
     * <p>
     * Verifies the accuracy of <code>TcDirectProject</code> entity mapping by doing persist and
     * find on entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testTcDirectProject_Create() throws Exception {
        et.begin();

        // Create and persist
        TcDirectProject defined = getTcDirectProject();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        TcDirectProject found = em.find(TcDirectProject.class, defined.getProjectId());
        assertNotNull("TcDirectProject persistence.", found);

        assertTcDirectProject(defined, found);

    }

    /**
     * <p>
     * Verifies the accuracy of <code>TcDirectProject</code>
     * entity mapping by doing persist, merge, refresh, and find on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testTcDirectProject_Update() throws Exception {
        et.begin();

        // Create and persist
        TcDirectProject defined = getTcDirectProject();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        TcDirectProject found = em.find(TcDirectProject.class, defined.getProjectId());
        assertNotNull("TcDirectProject persistence.", found);

        assertTcDirectProject(defined, found);

        et.begin();

        // update it
        defined.setName("new_name");
        em.merge(defined);

        // Commit the transaction
        et.commit();

        et.begin();

        // Refresh
        em.refresh(found);

        // Verify the update
        assertTcDirectProject(defined, found);

        et.commit();
    }

    /**
     * <p>
     * Verifies the accuracy of <code>TcDirectProject</code> entity mapping by doing persist,
     * find, and delete on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testTcDirectProject_Delete() throws Exception {
        et.begin();

        // Create and persist
        TcDirectProject defined = getTcDirectProject();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        TcDirectProject found = em.find(TcDirectProject.class, defined.getProjectId());
        assertNotNull("TcDirectProject persistence.", found);

        assertTcDirectProject(defined, found);

        et.begin();

        // delete
        em.remove(defined);
        et.commit();

        TcDirectProject deleted = em.find(TcDirectProject.class, found.getProjectId());
        assertNull("TcDirectProject entity is not removed.", deleted);
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataAudit</code> entity mapping by doing persist,
     * and find on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataAudit_Create() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataAudit defined = getDirectProjectMetadataAudit();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataAudit found = em.find(DirectProjectMetadataAudit.class, defined.getId());
        assertNotNull("DirectProjectMetadataAudit persistence.", found);

        assertDirectProjectMetadataAudit(defined, found);
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataAudit</code> entity mapping by doing persist,
     * find, merge, and refresh on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataAudit_Update() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataAudit defined = getDirectProjectMetadataAudit();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        et.begin();

        // Retrieve it
        DirectProjectMetadataAudit found = em.find(DirectProjectMetadataAudit.class, defined.getId());
        assertNotNull("DirectProjectMetadataAudit persistence.", found);

        assertDirectProjectMetadataAudit(defined, found);

        // update it
        defined.setMetadataValue("new_metadata");
        em.merge(defined);

        // Commit the transaction
        et.commit();

        et.begin();

        // Refresh
        em.refresh(found);

        // Verify the update
        assertDirectProjectMetadataAudit(defined, found);

        et.commit();
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataAudit</code> entity mapping by doing persist,
     * find, and delete on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataAudit_Delete() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataAudit defined = getDirectProjectMetadataAudit();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataAudit found = em.find(DirectProjectMetadataAudit.class, defined.getId());
        assertNotNull("DirectProjectMetadataAudit persistence.", found);
        assertDirectProjectMetadataAudit(defined, found);

        et.begin();
        // delete
        em.remove(defined);
        et.commit();

        DirectProjectMetadataAudit deleted = em.find(DirectProjectMetadataAudit.class, found.getId());
        assertNull("DirectProjectMetadataAudit entity is not removed.", deleted);

    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataKeyAudit</code> entity mapping by doing persist,
     * and find on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataKeyAudit_Create() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataKeyAudit defined = getDirectProjectMetadataKeyAudit();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataKeyAudit found = em.find(DirectProjectMetadataKeyAudit.class, defined.getId());
        assertNotNull("DirectProjectMetadataKeyAudit persistence.", found);

        assertDirectProjectMetadataKeyAudit(defined, found);
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataKeyAudit</code> entity mapping by doing persist,
     * find, merge, and refresh on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataKeyAudit_Update() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataKeyAudit defined = getDirectProjectMetadataKeyAudit();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        et.begin();

        // Retrieve it
        DirectProjectMetadataKeyAudit found = em.find(DirectProjectMetadataKeyAudit.class, defined.getId());
        assertNotNull("DirectProjectMetadataKeyAudit persistence.", found);

        assertDirectProjectMetadataKeyAudit(defined, found);

        // update it
        defined.setName("new_name");
        em.merge(defined);

        // Commit the transaction
        et.commit();

        et.begin();

        // Refresh
        em.refresh(found);

        // Verify the update
        assertDirectProjectMetadataKeyAudit(defined, found);
        et.commit();
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataKeyAudit</code> entity mapping by doing persist,
     * find, merge, refresh and delete on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataKeyAudit_Delete() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataKeyAudit defined = getDirectProjectMetadataKeyAudit();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataKeyAudit found = em.find(DirectProjectMetadataKeyAudit.class, defined.getId());
        assertNotNull("DirectProjectMetadataKeyAudit persistence.", found);

        assertDirectProjectMetadataKeyAudit(defined, found);

        // delete
        et.begin();
        em.remove(defined);
        et.commit();

        DirectProjectMetadataKeyAudit deleted = em.find(DirectProjectMetadataKeyAudit.class, found.getId());
        assertNull("DirectProjectMetadataKeyAudit entity is not removed.", deleted);
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataPredefinedValueAudit</code> entity mapping by doing persist,
     * and find on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataPredefinedValueAudit_Create() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataPredefinedValueAudit defined = getDirectProjectMetadataPredefinedValueAudit();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataPredefinedValueAudit found =
            em.find(DirectProjectMetadataPredefinedValueAudit.class, defined.getId());
        assertNotNull("DirectProjectMetadataPredefinedValueAudit persistence.", found);

        assertDirectProjectMetadataPredefinedValueAudit(defined, found);

    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataPredefinedValueAudit</code> entity mapping by doing persist,
     * find, merge, and refresh on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataPredefinedValueAudit_Update() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataPredefinedValueAudit defined = getDirectProjectMetadataPredefinedValueAudit();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        et.begin();

        // Retrieve it
        DirectProjectMetadataPredefinedValueAudit found =
            em.find(DirectProjectMetadataPredefinedValueAudit.class, defined.getId());
        assertNotNull("DirectProjectMetadataPredefinedValueAudit persistence.", found);

        assertDirectProjectMetadataPredefinedValueAudit(defined, found);

        // update it
        defined.setPredefinedMetadataValue("new_metadata");
        em.merge(defined);

        // Commit the transaction
        et.commit();

        et.begin();

        // Refresh
        em.refresh(found);

        // Verify the update
        assertDirectProjectMetadataPredefinedValueAudit(defined, found);
        et.commit();

    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataPredefinedValueAudit</code> entity mapping by doing persist,
     * find, and delete on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataPredefinedValueAudit_Delete() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataPredefinedValueAudit defined = getDirectProjectMetadataPredefinedValueAudit();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataPredefinedValueAudit found =
            em.find(DirectProjectMetadataPredefinedValueAudit.class, defined.getId());
        assertNotNull("DirectProjectMetadataPredefinedValueAudit persistence.", found);

        assertDirectProjectMetadataPredefinedValueAudit(defined, found);

        // delete
        et.begin();
        em.remove(defined);
        et.commit();

        DirectProjectMetadataPredefinedValueAudit deleted =
            em.find(DirectProjectMetadataPredefinedValueAudit.class, found.getId());
        assertNull("DirectProjectMetadataPredefinedValueAudit entity is not removed.", deleted);
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataKey</code> entity mapping by doing persist,
     * and find on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataKey_Create() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataKey defined = getDirectProjectMetadataKey();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataKey found = em.find(DirectProjectMetadataKey.class, defined.getId());
        assertNotNull("DirectProjectMetadataKey persistence.", found);

        assertDirectProjectMetadataKey(defined, found);
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataKey</code> entity mapping by doing persist,
     * find, merge, and refresh on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataKey_Update() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataKey defined = getDirectProjectMetadataKey();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        et.begin();

        // Retrieve it
        DirectProjectMetadataKey found = em.find(DirectProjectMetadataKey.class, defined.getId());
        assertNotNull("DirectProjectMetadataKey persistence.", found);

        assertDirectProjectMetadataKey(defined, found);

        // update it
        defined.setDescription("new_description");
        em.merge(defined);

        // Commit the transaction
        et.commit();

        et.begin();

        // Refresh
        em.refresh(found);

        // Verify the update
        assertDirectProjectMetadataKey(defined, found);
        et.commit();

    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataKey</code> entity mapping by doing persist,
     * find, and delete on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataKey_Delete() throws Exception {
        et.begin();

        // Create and persist
        DirectProjectMetadataKey defined = getDirectProjectMetadataKey();
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataKey found = em.find(DirectProjectMetadataKey.class, defined.getId());
        assertNotNull("DirectProjectMetadataKey persistence.", found);

        assertDirectProjectMetadataKey(defined, found);

        // delete
        et.begin();
        em.remove(defined);
        et.commit();

        DirectProjectMetadataKey deleted = em.find(DirectProjectMetadataKey.class, found.getId());
        assertNull("DirectProjectMetadataKey entity is not removed.", deleted);
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataPredefinedValue</code> entity mapping by doing persist,
     * and find on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataPredefinedValue_Create() throws Exception {
        et.begin();

        DirectProjectMetadataKey metadataKey = getDirectProjectMetadataKey();
        DirectProjectMetadataPredefinedValue defined = metadataKey.getPredefinedValues().get(0);

        // Create and persist
        em.persist(metadataKey);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataPredefinedValue found =
            em.find(DirectProjectMetadataPredefinedValue.class, defined.getId());
        assertNotNull("DirectProjectMetadataPredefinedValue persistence.", found);

        assertDirectProjectMetadataPredefinedValue(defined, found);

    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataPredefinedValue</code> entity mapping by doing persist,
     * find, merge, and refresh on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataPredefinedValue_Update() throws Exception {
        et.begin();

        DirectProjectMetadataKey metadataKey = getDirectProjectMetadataKey();
        DirectProjectMetadataPredefinedValue defined = metadataKey.getPredefinedValues().get(0);

        // Create and persist
        em.persist(metadataKey);

        // flush the change to DB
        em.flush();
        et.commit();

        et.begin();

        // Retrieve it
        DirectProjectMetadataPredefinedValue found =
            em.find(DirectProjectMetadataPredefinedValue.class, defined.getId());
        assertNotNull("DirectProjectMetadataPredefinedValue persistence.", found);

        assertDirectProjectMetadataPredefinedValue(defined, found);

        // update it
        defined.setPredefinedMetadataValue("new_metadata");
        em.merge(defined);

        // Commit the transaction
        et.commit();

        et.begin();

        // Refresh
        em.refresh(found);

        // Verify the update
        assertDirectProjectMetadataPredefinedValue(defined, found);
        et.commit();

    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadataPredefinedValue</code> entity mapping by doing persist,
     * find, merge, refresh and delete on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataPredefinedValue_Delete() throws Exception {
        et.begin();

        DirectProjectMetadataKey metadataKey = getDirectProjectMetadataKey();
        DirectProjectMetadataPredefinedValue defined = metadataKey.getPredefinedValues().get(0);

        // Create and persist
        em.persist(metadataKey);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadataPredefinedValue found =
            em.find(DirectProjectMetadataPredefinedValue.class, defined.getId());
        assertNotNull("DirectProjectMetadataPredefinedValue persistence.", found);

        assertDirectProjectMetadataPredefinedValue(defined, found);

        // delete
        et.begin();
        metadataKey.getPredefinedValues().remove(defined);
        em.remove(defined);
        et.commit();

        DirectProjectMetadataPredefinedValue deleted =
            em.find(DirectProjectMetadataPredefinedValue.class, found.getId());
        assertNull("DirectProjectMetadataPredefinedValue entity is not removed.", deleted);

    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadata</code> entity mapping by doing persist,
     * and find on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadata_Create() throws Exception {
        et.begin();

        DirectProjectMetadata defined = getDirectProjectMetadata();

        // Create and persist
        DirectProjectMetadataKey mKey = defined.getProjectMetadataKey();
        em.persist(mKey);
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadata found = em.find(DirectProjectMetadata.class, defined.getId());
        assertNotNull("DirectProjectMetadata persistence.", found);

        assertDirectProjectMetadata(defined, found);

    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadata</code> entity mapping by doing persist,
     * find, merge, and refresh on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadata_Update() throws Exception {
        et.begin();

        DirectProjectMetadata defined = getDirectProjectMetadata();

        // Create and persist
        DirectProjectMetadataKey mKey = defined.getProjectMetadataKey();
        em.persist(mKey);
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        et.begin();

        // Retrieve it
        DirectProjectMetadata found = em.find(DirectProjectMetadata.class, defined.getId());
        assertNotNull("DirectProjectMetadata persistence.", found);

        assertDirectProjectMetadata(defined, found);

        // update it
        defined.setMetadataValue("new_metadata");
        em.merge(defined);

        // Commit the transaction
        et.commit();

        et.begin();

        // Refresh
        em.refresh(found);

        // Verify the update
        assertDirectProjectMetadata(defined, found);
        et.commit();
    }

    /**
     * <p>
     * Verifies the accuracy of <code>DirectProjectMetadata</code> entity mapping by doing persist,
     * find, and delete on the entity.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadata_Delete() throws Exception {
        et.begin();

        DirectProjectMetadata defined = getDirectProjectMetadata();

        // Create and persist
        DirectProjectMetadataKey mKey = defined.getProjectMetadataKey();
        em.persist(mKey);
        em.persist(defined);

        // flush the change to DB
        em.flush();
        et.commit();

        // Retrieve it
        DirectProjectMetadata found = em.find(DirectProjectMetadata.class, defined.getId());
        assertNotNull("DirectProjectMetadata persistence.", found);

        assertDirectProjectMetadata(defined, found);

        // delete
        et.begin();
        em.remove(defined);
        et.commit();

        DirectProjectMetadata deleted = em.find(DirectProjectMetadata.class, found.getId());
        assertNull("DirectProjectMetadata entity is not removed.", deleted);

    }

    /**
     * Dummy unit test to close opening connection.
     *
     * @throws Exception to junit.
     */
    @Test
    public void testCloseConnection() throws Exception {
        if (em != null) {
            em.close();
        }
    }

    /**
     * <p>
     * Runs the given file.
     * </p>
     * @param file The SQL file to be executed.
     * @param em the entity manager used to run the SQL.
     * @throws Exception to JUnit
     */
    static void runSQL(String file, EntityManager em) throws Exception {
        String content = getFileAsString(file);
        if (em != null) {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            for (String st : content.split(";")) {
                em.createNativeQuery(st).executeUpdate();
            }

            em.getTransaction().commit();
        }
    }

    /**
     * <p>
     * Gets the file content as string.
     * </p>
     * @param file The file to get its content.
     * @return The content of file
     * @throws Exception to JUnit
     */
    private static String getFileAsString(String file) throws Exception {
        StringBuilder buff = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s;

        while ((s = in.readLine()) != null) {
            buff.append(s);
        }

        in.close();

        return buff.toString();
    }

    /**
     * Construct default instance for TcDirectProject entity.
     *
     * @return TcDirectProject instance.
     */
    private static TcDirectProject getTcDirectProject() {
        TcDirectProject entity = new TcDirectProject();
        entity.setName("name");
        entity.setDescription("description");
        entity.setUserId(1);
        entity.setProjectStatusId(1);
        entity.setCreateDate(new Date());
        return entity;
    }

    /**
     * Construct default instance for DirectProjectMetadataAudit entity.
     *
     * @return DirectProjectMetadataAudit instance.
     */
    private static DirectProjectMetadataAudit getDirectProjectMetadataAudit() {
        DirectProjectMetadataAudit entity = new DirectProjectMetadataAudit();

        entity.setActionUserId(1);
        entity.setActionDate(new Date());
        entity.setAuditActionTypeId(1);
        entity.setMetadataValue("metadata");
        entity.setProjectMetadataId(1);
        entity.setProjectMetadataKeyId(1);
        entity.setTcDirectProjectId(1);

        return entity;
    }

    /**
     * Construct default instance for DirectProjectMetadataKeyAudit entity.
     *
     * @return DirectProjectMetadataKeyAudit instance.
     */
    private static DirectProjectMetadataKeyAudit getDirectProjectMetadataKeyAudit() {
        DirectProjectMetadataKeyAudit entity = new DirectProjectMetadataKeyAudit();
        entity.setActionUserId(1);
        entity.setActionDate(new Date());
        entity.setAuditActionTypeId(1);
        entity.setClientId(Long.valueOf(1));
        entity.setDescription("DirectProjectMetadataKeyAudit");
        entity.setGrouping(Boolean.TRUE);
        entity.setName("name");
        entity.setProjectMetadataKeyId(1);
        entity.setSingle(false);
        return entity;
    }

    /**
     * Construct default instance for DirectProjectMetadataPredefinedValueAudit entity.
     *
     * @return DirectProjectMetadataPredefinedValueAudit instance.
     */
    private static DirectProjectMetadataPredefinedValueAudit getDirectProjectMetadataPredefinedValueAudit() {
        DirectProjectMetadataPredefinedValueAudit entity = new DirectProjectMetadataPredefinedValueAudit();
        entity.setActionUserId(1);
        entity.setActionDate(new Date());
        entity.setAuditActionTypeId(1);
        entity.setPosition(1);
        entity.setPredefinedMetadataValue("DirectProjectMetadataPredefinedValueAudit");
        entity.setProjectMetadataKeyId(1);
        entity.setProjectMetadataPredefinedValueId(1);
        return entity;
    }

    /**
     * Construct default instance for DirectProjectMetadataKey entity.
     *
     * @return DirectProjectMetadataKey instance.
     */
    private static DirectProjectMetadataKey getDirectProjectMetadataKey() {
        DirectProjectMetadataKey entity = new DirectProjectMetadataKey();
        entity.setName("name");
        entity.setDescription("description");
        entity.setGrouping(Boolean.TRUE);
        entity.setClientId(Long.valueOf(1));
        entity.setSingle(true);

        List<DirectProjectMetadataPredefinedValue> pool = new ArrayList<DirectProjectMetadataPredefinedValue>();
        entity.setPredefinedValues(pool);

        int position = 0;
        DirectProjectMetadataPredefinedValue m1 = new DirectProjectMetadataPredefinedValue();
        m1.setPredefinedMetadataValue("metadata_" + position);
        m1.setPosition(position);
        m1.setProjectMetadataKey(entity);

        position++;
        DirectProjectMetadataPredefinedValue m2 = new DirectProjectMetadataPredefinedValue();
        m2.setPredefinedMetadataValue("metadata_" + position);
        m2.setPosition(position);
        m2.setProjectMetadataKey(entity);

        pool.add(m1);
        pool.add(m2);

        return entity;
    }

    /**
     * Construct default instance for DirectProjectMetadata entity.
     *
     * @return DirectProjectMetadata instance.
     */
    private static DirectProjectMetadata getDirectProjectMetadata() {
        DirectProjectMetadata entity = new DirectProjectMetadata();
        entity.setMetadataValue("metadata");
        entity.setTcDirectProjectId(1);
        entity.setProjectMetadataKey(getDirectProjectMetadataKey());
        return entity;
    }

    /**
     * Convert date into String representation.
     *
     * @param date the date instance.
     * @return string representation of date.
     */
    private static String convertDateToString(Date date) {
        return (date == null) ? null : DATE_FORMAT.format(date);
    }

    /**
     * <p>
     * Asserts the equality of two <code>TcDirectProject</code> entities.
     * </p>
     * @param expected the expected value.
     * @param actual the actual value.
     */
    private static void assertTcDirectProject(TcDirectProject expected, TcDirectProject actual) {
        if ((expected == null) || (actual == null)) {
            assertEquals("TcDirectProject MissMatch.", expected, actual);
        }

        assertEquals("TcDirectProject's createDate MissMatch.",
                convertDateToString(expected.getCreateDate()), convertDateToString(actual.getCreateDate()));
        assertEquals("TcDirectProject's description MissMatch.", expected.getDescription(), actual.getDescription());
        assertEquals("TcDirectProject's modifyDate MissMatch.",
                convertDateToString(expected.getModifyDate()), convertDateToString(actual.getModifyDate()));
        assertEquals("TcDirectProject's name MissMatch.", expected.getName(), actual.getName());
        assertEquals("TcDirectProject's projectId MissMatch.", expected.getProjectId(), actual.getProjectId());
        assertEquals("TcDirectProject's projectStatusId MissMatch.",
                expected.getProjectStatusId(), actual.getProjectStatusId());
        assertEquals("TcDirectProject's userId MissMatch.", expected.getUserId(), actual.getUserId());
    }

    /**
     * <p>
     * Asserts the equality of two <code>DirectProjectMetadataAudit</code> entities.
     * </p>
     * @param expected the expected value.
     * @param actual the actual value.
     */
    private static void assertDirectProjectMetadataAudit(DirectProjectMetadataAudit expected,
            DirectProjectMetadataAudit actual) {
        if ((expected == null) || (actual == null)) {
            assertEquals("DirectProjectMetadataAudit MissMatch.", expected, actual);
        }

        assertEquals("DirectProjectMetadataAudit's actionDate MissMatch.",
                convertDateToString(expected.getActionDate()), convertDateToString(actual.getActionDate()));
        assertEquals("DirectProjectMetadataAudit's actionUserId MissMatch.",
                expected.getActionUserId(), actual.getActionUserId());
        assertEquals("DirectProjectMetadataAudit's auditActionTypeId MissMatch.",
                expected.getAuditActionTypeId(), actual.getAuditActionTypeId());
        assertEquals("DirectProjectMetadataAudit's id MissMatch.",
                expected.getId(), actual.getId());
        assertEquals("DirectProjectMetadataAudit's metadataValue MissMatch.",
                expected.getMetadataValue(), actual.getMetadataValue());
        assertEquals("DirectProjectMetadataAudit's projectMetadataId MissMatch.",
                expected.getProjectMetadataId(), actual.getProjectMetadataId());
        assertEquals("DirectProjectMetadataAudit's projectMetadataKeyId MissMatch.",
                expected.getProjectMetadataKeyId(), actual.getProjectMetadataKeyId());
        assertEquals("DirectProjectMetadataAudit's tcDirectProjectId MissMatch.",
                expected.getTcDirectProjectId(), actual.getTcDirectProjectId());
    }

    /**
     * <p>
     * Asserts the equality of two <code>DirectProjectMetadataKeyAudit</code> entities.
     * </p>
     * @param expected the expected value.
     * @param actual the actual value.
     */
    private static void assertDirectProjectMetadataKeyAudit(DirectProjectMetadataKeyAudit expected,
            DirectProjectMetadataKeyAudit actual) {
        if ((expected == null) || (actual == null)) {
            assertEquals("DirectProjectMetadataKeyAudit MissMatch.", expected, actual);
        }

        assertEquals("DirectProjectMetadataKeyAudit's single MissMatch.",
                expected.isSingle(), actual.isSingle());
        assertEquals("DirectProjectMetadataKeyAudit's actionDate MissMatch.",
                convertDateToString(expected.getActionDate()), convertDateToString(actual.getActionDate()));
        assertEquals("DirectProjectMetadataKeyAudit's actionUserId MissMatch.",
                expected.getActionUserId(), actual.getActionUserId());
        assertEquals("DirectProjectMetadataKeyAudit's auditActionTypeId MissMatch.",
                expected.getAuditActionTypeId(), actual.getAuditActionTypeId());
        assertEquals("DirectProjectMetadataKeyAudit's clientId MissMatch.",
                expected.getClientId(), actual.getClientId());
        assertEquals("DirectProjectMetadataKeyAudit's description MissMatch.",
                expected.getDescription(), actual.getDescription());
        assertEquals("DirectProjectMetadataKeyAudit's grouping MissMatch.",
                expected.getGrouping(), actual.getGrouping());
        assertEquals("DirectProjectMetadataKeyAudit's id MissMatch.",
                expected.getId(), actual.getId());
        assertEquals("DirectProjectMetadataKeyAudit's name MissMatch.",
                expected.getName(), actual.getName());
        assertEquals("DirectProjectMetadataKeyAudit's projectMetadataKeyId MissMatch.",
                expected.getProjectMetadataKeyId(), actual.getProjectMetadataKeyId());
    }

    /**
     * <p>
     * Asserts the equality of two <code>DirectProjectMetadataPredefinedValueAudit</code> entities.
     * </p>
     * @param expected the expected value.
     * @param actual the actual value.
     */
    private static void assertDirectProjectMetadataPredefinedValueAudit(
            DirectProjectMetadataPredefinedValueAudit expected, DirectProjectMetadataPredefinedValueAudit actual) {
        if ((expected == null) || (actual == null)) {
            assertEquals("DirectProjectMetadataPredefinedValueAudit MissMatch.", expected, actual);
        }

        assertEquals("DirectProjectMetadataPredefinedValueAudit's actionDate MissMatch.",
                convertDateToString(expected.getActionDate()), convertDateToString(actual.getActionDate()));
        assertEquals("DirectProjectMetadataPredefinedValueAudit's actionUserId MissMatch.",
                expected.getActionUserId(), actual.getActionUserId());
        assertEquals("DirectProjectMetadataPredefinedValueAudit's auditActionTypeId MissMatch.",
                expected.getAuditActionTypeId(), actual.getAuditActionTypeId());
        assertEquals("DirectProjectMetadataPredefinedValueAudit's id MissMatch.",
                expected.getId(), actual.getId());
        assertEquals("DirectProjectMetadataPredefinedValueAudit's position MissMatch.",
                expected.getPosition(), actual.getPosition());
        assertEquals("DirectProjectMetadataPredefinedValueAudit's predefinedMetadataValue MissMatch.",
                expected.getPredefinedMetadataValue(), actual.getPredefinedMetadataValue());
        assertEquals("DirectProjectMetadataPredefinedValueAudit's projectMetadataKeyId MissMatch.",
                expected.getProjectMetadataKeyId(), actual.getProjectMetadataKeyId());
        assertEquals("DirectProjectMetadataPredefinedValueAudit's projectMetadataPredefinedValueId MissMatch.",
                expected.getProjectMetadataPredefinedValueId(), actual.getProjectMetadataPredefinedValueId());
    }

    /**
     * <p>
     * Asserts the equality of two <code>DirectProjectMetadataKey</code> entities.
     * </p>
     * @param expected the expected value.
     * @param actual the actual value.
     */
    private static void assertDirectProjectMetadataKey(
            DirectProjectMetadataKey expected, DirectProjectMetadataKey actual) {
        if ((expected == null) || (actual == null)) {
            assertEquals("DirectProjectMetadataKey MissMatch.", expected, actual);
        }

        assertEquals("DirectProjectMetadataKey's single MissMatch.",
                expected.isSingle(), actual.isSingle());
        assertEquals("DirectProjectMetadataKey's clientId MissMatch.",
                expected.getClientId(), actual.getClientId());
        assertEquals("DirectProjectMetadataKey's description MissMatch.",
                expected.getDescription(), actual.getDescription());
        assertEquals("DirectProjectMetadataKey's grouping MissMatch.",
                expected.getGrouping(), actual.getGrouping());
        assertEquals("DirectProjectMetadataKey's id MissMatch.",
                expected.getId(), actual.getId());
        assertEquals("DirectProjectMetadataKey's name MissMatch.",
                expected.getName(), actual.getName());

        if ((expected.getPredefinedValues() == null) || (actual.getPredefinedValues() == null)) {
            assertEquals("DirectProjectMetadataKey's predefinedValues MissMatch.",
                    expected.getPredefinedValues(), actual.getPredefinedValues());
        } else {
            assertEquals("DirectProjectMetadataKey's predefinedValues MissMatch.",
                    expected.getPredefinedValues().size(), actual.getPredefinedValues().size());
            for (DirectProjectMetadataPredefinedValue value : expected.getPredefinedValues()) {
                boolean find = false;
                for (DirectProjectMetadataPredefinedValue val2 : actual.getPredefinedValues()) {
                    if (value.getId() == val2.getId()) {
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    fail("DirectProjectMetadataKey's predefinedValues MissMatch.");
                }
            }
        }
    }

    /**
     * <p>
     * Asserts the equality of two <code>DirectProjectMetadataPredefinedValue</code> entities.
     * </p>
     * @param expected the expected value.
     * @param actual the actual value.
     */
    private static void assertDirectProjectMetadataPredefinedValue(DirectProjectMetadataPredefinedValue expected,
            DirectProjectMetadataPredefinedValue actual) {
        if ((expected == null) || (actual == null)) {
            assertEquals("DirectProjectMetadataPredefinedValue MissMatch.", expected, actual);
        }

        assertEquals("DirectProjectMetadataPredefinedValue's id MissMatch.",
                expected.getId(), actual.getId());
        assertEquals("DirectProjectMetadataPredefinedValue's position MissMatch.",
                expected.getPosition(), actual.getPosition());
        assertEquals("DirectProjectMetadataPredefinedValue's predefinedMetadataValue MissMatch.",
                expected.getPredefinedMetadataValue(), actual.getPredefinedMetadataValue());

        assertDirectProjectMetadataKey(expected.getProjectMetadataKey(), actual.getProjectMetadataKey());
    }

    /**
     * <p>
     * Asserts the equality of two <code>DirectProjectMetadata</code> entities.
     * </p>
     * @param expected the expected value.
     * @param actual the actual value.
     */
    private static void assertDirectProjectMetadata(DirectProjectMetadata expected, DirectProjectMetadata actual) {
        if ((expected == null) || (actual == null)) {
            assertEquals("DirectProjectMetadata MissMatch.", expected, actual);
        }

        assertEquals("DirectProjectMetadata's id MissMatch.",
                expected.getId(), actual.getId());
        assertEquals("DirectProjectMetadata's metadataValue MissMatch.",
                expected.getMetadataValue(), actual.getMetadataValue());
        assertEquals("DirectProjectMetadata's tcDirectProjectId MissMatch.",
                expected.getTcDirectProjectId(), actual.getTcDirectProjectId());

        assertDirectProjectMetadataKey(expected.getProjectMetadataKey(), actual.getProjectMetadataKey());
    }
}
