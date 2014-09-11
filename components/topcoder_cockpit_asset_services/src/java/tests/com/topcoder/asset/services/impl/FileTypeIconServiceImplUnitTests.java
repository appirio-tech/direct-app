/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;
import com.topcoder.asset.entities.FileTypeIcon;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link FileTypeIconServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class FileTypeIconServiceImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>FileTypeIconServiceImpl</code> instance used in tests.
     * </p>
     */
    private FileTypeIconServiceImpl instance;

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
        return new JUnit4TestAdapter(FileTypeIconServiceImplUnitTests.class);
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

        instance = new FileTypeIconServiceImpl();
        instance.setEntityManager(entityManager);
        instance.setLog(log);

        fileTypeIcon = new FileTypeIcon();
        fileTypeIcon.setFileType("fileType1");
        fileTypeIcon.setFileTypeCategory("fileTypeCategory1");
        fileTypeIcon.setIconPath("iconPath1");
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>FileTypeIconServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new FileTypeIconServiceImpl();

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
     * Accuracy test for the method <code>create(FileTypeIcon icon)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_create() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(fileTypeIcon);
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
     * Failure test for the method <code>create(FileTypeIcon icon)</code> with icon is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_iconNull() throws Exception {
        fileTypeIcon = null;

        instance.create(fileTypeIcon);
    }

    /**
     * <p>
     * Failure test for the method <code>create(FileTypeIcon icon)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_create_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        fileTypeIcon.setFileType(null);
        instance.create(fileTypeIcon);
    }

    /**
     * <p>
     * Failure test for the method <code>create(FileTypeIcon icon)</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_create_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.create(fileTypeIcon);
    }

    /**
     * <p>
     * Accuracy test for the method <code>update(FileTypeIcon icon)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_update() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(fileTypeIcon);
        entityManager.getTransaction().commit();
        entityManager.clear();

        fileTypeIcon.setFileType("new");
        entityManager.getTransaction().begin();
        instance.update(fileTypeIcon);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'update' should be correct.", fileTypeIcon.getId() > 0);

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
     * Failure test for the method <code>update(FileTypeIcon icon)</code> with icon is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_iconNull() throws Exception {
        fileTypeIcon = null;

        instance.update(fileTypeIcon);
    }

    /**
     * <p>
     * Failure test for the method <code>update(FileTypeIcon icon)</code> with icon.getId() is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_iconIdNegative() throws Exception {
        fileTypeIcon.setId(-1);

        instance.update(fileTypeIcon);
    }

    /**
     * <p>
     * Failure test for the method <code>update(FileTypeIcon icon)</code> with icon.getId() is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_iconIdZero() throws Exception {
        fileTypeIcon.setId(0);

        instance.update(fileTypeIcon);
    }

    /**
     * <p>
     * Failure test for the method <code>update(FileTypeIcon icon)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_update_EntityNotFound() throws Exception {
        fileTypeIcon.setId(Long.MAX_VALUE);

        instance.update(fileTypeIcon);
    }

    /**
     * <p>
     * Failure test for the method <code>update(FileTypeIcon icon)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_update_PersistenceError() throws Exception {
        fileTypeIcon.setId(1);

        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.update(fileTypeIcon);
    }

    /**
     * <p>
     * Failure test for the method <code>update(FileTypeIcon icon)</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_update_entityManagerClosed() throws Exception {
        fileTypeIcon.setId(1);

        entityManager.close();

        instance.update(fileTypeIcon);
    }

    /**
     * <p>
     * Accuracy test for the method <code>delete(long iconId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_delete() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(fileTypeIcon);
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        instance.delete(fileTypeIcon.getId());
        entityManager.getTransaction().commit();
        entityManager.clear();

        FileTypeIcon retrievedFileTypeIcon = entityManager.find(FileTypeIcon.class, fileTypeIcon.getId());

        assertNull("'delete' should be correct.", retrievedFileTypeIcon);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long iconId)</code> with iconId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_iconIdNegative() throws Exception {
        instance.delete(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long iconId)</code> with iconId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_iconIdZero() throws Exception {
        instance.delete(0);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long iconId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_delete_EntityNotFound() throws Exception {
        instance.delete(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long iconId)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_delete_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.delete(1);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long iconId)</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_delete_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.delete(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>get(long iconId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_get_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(fileTypeIcon);
        entityManager.getTransaction().commit();
        entityManager.clear();

        FileTypeIcon res = instance.get(fileTypeIcon.getId());

        assertEquals("'get' should be correct.",
            fileTypeIcon.getFileType(), res.getFileType());
        assertEquals("'get' should be correct.",
            fileTypeIcon.getFileTypeCategory(), res.getFileTypeCategory());
        assertEquals("'get' should be correct.",
            fileTypeIcon.getIconPath(), res.getIconPath());
    }

    /**
     * <p>
     * Accuracy test for the method <code>get(long iconId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_get_2() throws Exception {
        clearDB();

        FileTypeIcon res = instance.get(Long.MAX_VALUE);

        assertNull("'get' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long iconId)</code> with iconId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_get_iconIdNegative() throws Exception {
        instance.get(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long iconId)</code> with iconId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_get_iconIdZero() throws Exception {
        instance.get(0);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long iconId)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_get_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.get(1);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long iconId)</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_get_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.get(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAll()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAll_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(fileTypeIcon);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<FileTypeIcon> res = instance.getAll();

        assertEquals("'getAll' should be correct.", 1, res.size());

        FileTypeIcon retrievedFileTypeIcon = res.get(0);

        assertEquals("'getAll' should be correct.",
            fileTypeIcon.getFileType(), retrievedFileTypeIcon.getFileType());
        assertEquals("'getAll' should be correct.",
            fileTypeIcon.getFileTypeCategory(), retrievedFileTypeIcon.getFileTypeCategory());
        assertEquals("'getAll' should be correct.",
            fileTypeIcon.getIconPath(), retrievedFileTypeIcon.getIconPath());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAll()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAll_2() throws Exception {
        FileTypeIcon fileTypeIcon2 = new FileTypeIcon();
        fileTypeIcon2.setFileType("fileType2");
        fileTypeIcon2.setFileTypeCategory("fileTypeCategory2");
        fileTypeIcon2.setIconPath("iconPath2");

        entityManager.getTransaction().begin();
        instance.create(fileTypeIcon);
        instance.create(fileTypeIcon2);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<FileTypeIcon> res = instance.getAll();

        assertEquals("'getAll' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAll()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAll_3() throws Exception {
        clearDB();

        List<FileTypeIcon> res = instance.getAll();

        assertEquals("'getAll' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAll()</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getAll_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.getAll();
    }

    /**
     * <p>
     * Failure test for the method <code>getAll()</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getAll_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.getAll();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllFileTypeCategories()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAllFileTypeCategories_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(fileTypeIcon);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<String> res = instance.getAllFileTypeCategories();

        assertEquals("'getAllFileTypeCategories' should be correct.", 1, res.size());
        assertTrue("'getAllFileTypeCategories' should be correct.", res.contains(fileTypeIcon.getFileTypeCategory()));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllFileTypeCategories()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAllFileTypeCategories_2() throws Exception {
        FileTypeIcon fileTypeIcon2 = new FileTypeIcon();
        fileTypeIcon2.setFileType("fileType2");
        fileTypeIcon2.setFileTypeCategory("fileTypeCategory2");
        fileTypeIcon2.setIconPath("iconPath2");

        entityManager.getTransaction().begin();
        instance.create(fileTypeIcon);
        instance.create(fileTypeIcon2);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<String> res = instance.getAllFileTypeCategories();

        assertEquals("'getAllFileTypeCategories' should be correct.", 2, res.size());
        assertTrue("'getAllFileTypeCategories' should be correct.", res.contains(fileTypeIcon.getFileTypeCategory()));
        assertTrue("'getAllFileTypeCategories' should be correct.", res.contains(fileTypeIcon2.getFileTypeCategory()));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllFileTypeCategories()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAllFileTypeCategories_3() throws Exception {
        FileTypeIcon fileTypeIcon2 = new FileTypeIcon();
        fileTypeIcon2.setFileType("fileType2");
        fileTypeIcon2.setFileTypeCategory(fileTypeIcon.getFileTypeCategory());
        fileTypeIcon2.setIconPath("iconPath2");

        entityManager.getTransaction().begin();
        instance.create(fileTypeIcon);
        instance.create(fileTypeIcon2);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<String> res = instance.getAllFileTypeCategories();

        assertEquals("'getAllFileTypeCategories' should be correct.", 1, res.size());
        assertTrue("'getAllFileTypeCategories' should be correct.", res.contains(fileTypeIcon.getFileTypeCategory()));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllFileTypeCategories()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAllFileTypeCategories_4() throws Exception {
        clearDB();

        List<String> res = instance.getAllFileTypeCategories();

        assertEquals("'getAllFileTypeCategories' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAllFileTypeCategories()</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getAllFileTypeCategories_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.getAllFileTypeCategories();
    }

    /**
     * <p>
     * Failure test for the method <code>getAllFileTypeCategories()</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getAllFileTypeCategories_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.getAllFileTypeCategories();
    }
}