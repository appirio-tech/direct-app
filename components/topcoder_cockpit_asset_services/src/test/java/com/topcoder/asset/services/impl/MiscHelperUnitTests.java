/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;
import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.FileTypeIcon;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link MiscHelper} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class MiscHelperUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the entity manager used in tests.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * The log.
     */
    private Log log = LogManager.getLog(getClass().getName());

    /**
     * <p>
     * Represents the file type icon used in tests.
     * </p>
     */
    private FileTypeIcon fileTypeIcon;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MiscHelperUnitTests.class);
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

        fileTypeIcon = new FileTypeIcon();
        fileTypeIcon.setFileType("fileType1");
        fileTypeIcon.setFileTypeCategory("fileTypeCategory1");
        fileTypeIcon.setIconPath("iconPath1");
    }

    /**
     * <p>
     * Tests accuracy of <code>create(Log log, String signature, EntityManager entityManager, Object entity,
     * String name)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_create() throws Exception {
        entityManager.getTransaction().begin();
        MiscHelper.create(log, "signature", entityManager, fileTypeIcon, "fileTypeIcon");
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'create' should be correct.", fileTypeIcon.getId() > 0);

        FileTypeIcon retrievedFileTypeIcon = entityManager.find(FileTypeIcon.class, fileTypeIcon.getId());

        assertEquals("'create' should be correct.",
            fileTypeIcon.getFileType(), retrievedFileTypeIcon.getFileType());
        assertEquals("'create' should be correct.",
            fileTypeIcon.getFileTypeCategory(), retrievedFileTypeIcon.getFileTypeCategory());
        assertEquals("'create' should be correct.",
            fileTypeIcon.getIconPath(), retrievedFileTypeIcon.getIconPath());
    }

    /**
     * <p>
     * Tests accuracy of <code>update(Log log, String signature, EntityManager entityManager, Object entity,
     * String name)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_update() throws Exception {
        entityManager.getTransaction().begin();
        MiscHelper.create(log, "signature", entityManager, fileTypeIcon, "fileTypeIcon");
        entityManager.getTransaction().commit();
        entityManager.clear();

        fileTypeIcon.setFileType("new");
        entityManager.getTransaction().begin();
        MiscHelper.update(log, "signature", entityManager, fileTypeIcon, "fileTypeIcon");
        entityManager.getTransaction().commit();
        entityManager.clear();

        FileTypeIcon retrievedFileTypeIcon = entityManager.find(FileTypeIcon.class, fileTypeIcon.getId());

        assertEquals("'update' should be correct.",
            fileTypeIcon.getFileType(), retrievedFileTypeIcon.getFileType());
        assertEquals("'update' should be correct.",
            fileTypeIcon.getFileTypeCategory(), retrievedFileTypeIcon.getFileTypeCategory());
        assertEquals("'update' should be correct.",
            fileTypeIcon.getIconPath(), retrievedFileTypeIcon.getIconPath());
    }

    /**
     * <p>
     * Tests accuracy of <code>delete(Log log, String signature, EntityManager entityManager, Class&lt;T&gt; entityType,
     * long entityId, String name)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_delete() throws Exception {
        entityManager.getTransaction().begin();
        MiscHelper.create(log, "signature", entityManager, fileTypeIcon, "fileTypeIcon");
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        MiscHelper.delete(log, "signature", entityManager, FileTypeIcon.class, fileTypeIcon.getId(), "fileTypeIcon");
        entityManager.getTransaction().commit();
        entityManager.clear();

        FileTypeIcon retrievedFileTypeIcon = entityManager.find(FileTypeIcon.class, fileTypeIcon.getId());

        assertNull("'delete' should be correct.", retrievedFileTypeIcon);
    }

    /**
     * <p>
     * Tests accuracy of <code>get(Log log, String signature, EntityManager entityManager, Class&lt;T&gt; entityType,
     * long entityId, String name)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_get() throws Exception {
        entityManager.getTransaction().begin();
        MiscHelper.create(log, "signature", entityManager, fileTypeIcon, "fileTypeIcon");
        entityManager.getTransaction().commit();
        entityManager.clear();

        FileTypeIcon res = MiscHelper.get(log, "signature", entityManager, FileTypeIcon.class, fileTypeIcon.getId(),
            "fileTypeIcon");

        assertEquals("'get' should be correct.",
            fileTypeIcon.getFileType(), res.getFileType());
        assertEquals("'get' should be correct.",
            fileTypeIcon.getFileTypeCategory(), res.getFileTypeCategory());
        assertEquals("'get' should be correct.",
            fileTypeIcon.getIconPath(), res.getIconPath());
    }

    /**
     * <p>
     * Tests accuracy of <code>getEntities(Log log, String signature, EntityManager entityManager, String str,
     * Long projectId)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getEntities() throws Exception {
        entityManager.getTransaction().begin();
        MiscHelper.create(log, "signature", entityManager, fileTypeIcon, "fileTypeIcon");
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<FileTypeIcon> res = MiscHelper.getEntities(log, "signature", entityManager,
            "SELECT e FROM FileTypeIcon e", null);

        assertEquals("'getEntities' should be correct.",
            1, res.size());
        FileTypeIcon retrievedFileTypeIcon = res.get(0);

        assertEquals("'getEntities' should be correct.",
            fileTypeIcon.getFileType(), retrievedFileTypeIcon.getFileType());
        assertEquals("'getEntities' should be correct.",
            fileTypeIcon.getFileTypeCategory(), retrievedFileTypeIcon.getFileTypeCategory());
        assertEquals("'getEntities' should be correct.",
            fileTypeIcon.getIconPath(), retrievedFileTypeIcon.getIconPath());
    }

    /**
     * <p>
     * Tests accuracy of <code>getEntity(Log log, String signature, String name, EntityManager entityManager,
     * Class&lt;T&gt; type, long id)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getEntity() throws Exception {
        Asset res = MiscHelper.getEntity(log, "signature", "Asset", entityManager, Asset.class, 1);

        assertEquals("'getEntity' should be correct.", 1, res.getId());
    }

    /**
     * <p>
     * Tests accuracy of <code>toString(Object obj)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_toString2() throws Exception {
        String res = MiscHelper.toString(1);

        assertEquals("'toString' should be correct.", "1", res);
    }
}
