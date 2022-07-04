/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;
import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.User;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link AssetVersionServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AssetVersionServiceImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>AssetVersionServiceImpl</code> instance used in tests.
     * </p>
     */
    private AssetVersionServiceImpl instance;

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
     * Represents the base path used in tests.
     * </p>
     */
    private String basePath = TEST_FILES + "cockpit";

    /**
     * <p>
     * Represents the image types used in tests.
     * </p>
     */
    private List<String> imageTypes;

    /**
     * <p>
     * Represents the preview image width used in tests.
     * </p>
     */
    private int previewImageWidth = 10;

    /**
     * <p>
     * Represents the preview image height used in tests.
     * </p>
     */
    private int previewImageHeight = 20;

    /**
     * <p>
     * Represents the user id used in tests.
     * </p>
     */
    private long userId = 1;

    /**
     * <p>
     * Represents the asset version used in tests.
     * </p>
     */
    private AssetVersion assetVersion;

    /**
     * <p>
     * Represents the file used in tests.
     * </p>
     */
    private File file;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetVersionServiceImplUnitTests.class);
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

        imageTypes = new ArrayList<String>();
        imageTypes.add("jpg");
        imageTypes.add("png");
        imageTypes.add("bmp");

        instance = new AssetVersionServiceImpl();
        instance.setEntityManager(entityManager);
        instance.setLog(log);
        instance.setBasePath(basePath);
        instance.setImageTypes(imageTypes);
        instance.setPreviewImageWidth(previewImageWidth);
        instance.setPreviewImageHeight(previewImageHeight);

        assetVersion = new AssetVersion();
        assetVersion.setVersion("Version 1.0");
        assetVersion.setAssetId(1);
        assetVersion.setFileName("erd.png");
        assetVersion.setFileType("png");
        assetVersion.setFileSizeBytes(57527);

        User uploader = new User();
        uploader.setId(1);
        assetVersion.setUploader(uploader);
        assetVersion.setUploadTime(new Date());
        assetVersion.setDescription("The description.");
        assetVersion.setFilePath("filePath");
        assetVersion.setPreviewImagePath("previewImagePath");

        file = new File(TEST_FILES + "erd.png");
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AssetVersionServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new AssetVersionServiceImpl();

        assertNull("'entityManager' should be correct.", getField(instance, "entityManager"));
        assertNull("'log' should be correct.", getField(instance, "log"));

        assertNull("'basePath' should be correct.", getField(instance, "basePath"));
        assertNull("'imageTypes' should be correct.", getField(instance, "imageTypes"));
        assertEquals("'previewImageWidth' should be correct.",
            0, getField(instance, "previewImageWidth"));
        assertEquals("'previewImageHeight' should be correct.",
            0, getField(instance, "previewImageHeight"));
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

        assertNotNull("'checkInit' should be correct.", getField(instance, "basePath"));
        assertNotNull("'checkInit' should be correct.", getField(instance, "imageTypes"));
        assertEquals("'checkInit' should be correct.",
            previewImageWidth, getField(instance, "previewImageWidth"));
        assertEquals("'checkInit' should be correct.",
            previewImageHeight, getField(instance, "previewImageHeight"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_checkInit_2() {
        imageTypes.clear();

        instance.setLog(null);
        instance.setImageTypes(imageTypes);

        instance.checkInit();

        assertNotNull("'checkInit' should be correct.", getField(instance, "entityManager"));
        assertNull("'checkInit' should be correct.", getField(instance, "log"));

        assertNotNull("'checkInit' should be correct.", getField(instance, "basePath"));
        assertNotNull("'checkInit' should be correct.", getField(instance, "imageTypes"));
        assertEquals("'checkInit' should be correct.",
            previewImageWidth, getField(instance, "previewImageWidth"));
        assertEquals("'checkInit' should be correct.",
            previewImageHeight, getField(instance, "previewImageHeight"));
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
     * Failure test for the method <code>checkInit()</code>
     * with basePath is null.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_basePathNull() {
        instance.setBasePath(null);

        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with basePath is empty.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_basePathEmpty() {
        instance.setBasePath(EMPTY_STRING);

        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with imageTypes is null.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_imageTypesNull() {
        instance.setImageTypes(null);

        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with imageTypes contains null.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_imageTypesContainsNull() {
        imageTypes.add(null);
        instance.setImageTypes(imageTypes);

        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with imageTypes contains empty.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_imageTypesContainsEmpty() {
        imageTypes.add(EMPTY_STRING);
        instance.setImageTypes(imageTypes);

        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with previewImageWidth is negative.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_previewImageWidthNegative() {
        instance.setPreviewImageWidth(-1);

        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with previewImageWidth is zero.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_previewImageWidthZero() {
        instance.setPreviewImageWidth(0);

        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with previewImageHeight is negative.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_previewImageHeightNegative() {
        instance.setPreviewImageHeight(-1);

        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with previewImageHeight is zero.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_previewImageHeightZero() {
        instance.setPreviewImageHeight(0);

        instance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createAssetVersion_1() throws Exception {
        file = null;

        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, false);
        entityManager.getTransaction().commit();

        assertTrue("'createAssetVersion' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'createAssetVersion' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());

        assertFalse("'createAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getFilePath()).exists());
        assertFalse("'createAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getPreviewImagePath()).exists());

        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());
        assertNull("'createAssetVersion' should be correct.",
            retrievedAsset.getCurrentVersion());

        checkAuditRecord("createAssetVersion", userId, "Create Asset Version", "AssetVersion", assetVersion.getId(),
            true, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createAssetVersion_2() throws Exception {
        file = null;

        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();

        assertTrue("'createAssetVersion' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'createAssetVersion' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());

        assertFalse("'createAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getFilePath()).exists());
        assertFalse("'createAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getPreviewImagePath()).exists());

        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getId(), retrievedAsset.getCurrentVersion().getId());

        checkAuditRecord("createAssetVersion", userId, "Create Asset Version", "AssetVersion", assetVersion.getId(),
            true, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createAssetVersion_3() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();

        assertTrue("'createAssetVersion' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'createAssetVersion' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());

        assertTrue("'createAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getFilePath()).exists());
        assertTrue("'createAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getPreviewImagePath()).exists());

        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getId(), retrievedAsset.getCurrentVersion().getId());

        checkAuditRecord("createAssetVersion", userId, "Create Asset Version", "AssetVersion", assetVersion.getId(),
            true, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createAssetVersion_4() throws Exception {
        file = new File(TEST_FILES + "data");

        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();

        assertTrue("'createAssetVersion' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'createAssetVersion' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());

        assertTrue("'createAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getFilePath()).exists());
        assertFalse("'createAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getPreviewImagePath()).exists());

        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());
        assertEquals("'createAssetVersion' should be correct.",
            assetVersion.getId(), retrievedAsset.getCurrentVersion().getId());

        checkAuditRecord("createAssetVersion", userId, "Create Asset Version", "AssetVersion", assetVersion.getId(),
            true, false);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code> with userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAssetVersion_userIdNegative() throws Exception {
        userId = -1;

        instance.createAssetVersion(userId, assetVersion, file, true);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAssetVersion_userIdZero() throws Exception {
        userId = 0;

        instance.createAssetVersion(userId, assetVersion, file, true);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code> with assetVersion is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAssetVersion_assetVersionNull() throws Exception {
        assetVersion = null;

        instance.createAssetVersion(userId, assetVersion, file, true);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code> with assetVersion.getAssetId() is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAssetVersion_assetVersionAssetIdNegative() throws Exception {
        assetVersion.setAssetId(-1);

        instance.createAssetVersion(userId, assetVersion, file, true);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code> with assetVersion.getAssetId() is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAssetVersion_assetVersionAssetIdZero() throws Exception {
        assetVersion.setAssetId(0);

        instance.createAssetVersion(userId, assetVersion, file, true);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code> with assetVersion.getAssetId() is invalid.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_createAssetVersion_assetVersionAssetIdInvalid() throws Exception {
        assetVersion.setAssetId(Long.MAX_VALUE);

        instance.createAssetVersion(userId, assetVersion, file, true);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_createAssetVersion_PersistenceError1() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.createAssetVersion(userId, assetVersion, file, true);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_createAssetVersion_PersistenceError2() throws Exception {
        file = new File(TEST_FILES + "not_exist");

        instance.createAssetVersion(userId, assetVersion, file, true);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssetVersion(long userId, AssetVersion assetVersion,
     * File file, boolean currentVersion)</code> with entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_createAssetVersion_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.createAssetVersion(userId, assetVersion, file, true);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAssetVersion_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setDescription("new description");
        entityManager.getTransaction().begin();
        instance.updateAssetVersion(userId, assetVersion);
        entityManager.getTransaction().commit();

        assertTrue("'updateAssetVersion' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'updateAssetVersion' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());

        assertTrue("'updateAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getFilePath()).exists());
        assertTrue("'updateAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getPreviewImagePath()).exists());

        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getId(), retrievedAsset.getCurrentVersion().getId());

        checkAuditRecord("updateAssetVersion", userId, "Update Asset Version", "AssetVersion", assetVersion.getId(),
            false, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAssetVersion_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setDescription("new description");
        assetVersion.setFilePath(TEST_FILES + "cockpit" + File.separator + "erd.png");
        assetVersion.setPreviewImagePath(TEST_FILES + "cockpit" + File.separator + "preview_erd.png");
        entityManager.getTransaction().begin();
        instance.updateAssetVersion(userId, assetVersion);
        entityManager.getTransaction().commit();

        assertTrue("'updateAssetVersion' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'updateAssetVersion' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());

        assertTrue("'updateAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getFilePath()).exists());
        assertTrue("'updateAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getPreviewImagePath()).exists());

        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getId(), retrievedAsset.getCurrentVersion().getId());

        checkAuditRecord("updateAssetVersion", userId, "Update Asset Version", "AssetVersion", assetVersion.getId(),
            false, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAssetVersion_3() throws Exception {
        file = new File(TEST_FILES + "data");
        assetVersion.setFileName("data");

        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setDescription("new description");
        assetVersion.setPreviewImagePath(null);
        entityManager.getTransaction().begin();
        instance.updateAssetVersion(userId, assetVersion);
        entityManager.getTransaction().commit();

        assertTrue("'updateAssetVersion' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'updateAssetVersion' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());

        assertTrue("'updateAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getFilePath()).exists());

        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getId(), retrievedAsset.getCurrentVersion().getId());

        checkAuditRecord("updateAssetVersion", userId, "Update Asset Version", "AssetVersion", assetVersion.getId(),
            false, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAssetVersion_4() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setFilePath(TEST_FILES + "erd.png");
        assetVersion.setPreviewImagePath(null);
        entityManager.getTransaction().begin();
        instance.updateAssetVersion(userId, assetVersion);
        entityManager.getTransaction().commit();

        assertTrue("'updateAssetVersion' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'updateAssetVersion' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());

        assertTrue("'updateAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getFilePath()).exists());
        assertTrue("'updateAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getPreviewImagePath()).exists());

        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getId(), retrievedAsset.getCurrentVersion().getId());

        checkAuditRecord("updateAssetVersion", userId, "Update Asset Version", "AssetVersion", assetVersion.getId(),
            false, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAssetVersion_5() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setFilePath(TEST_FILES + "erd.png");
        assetVersion.setPreviewImagePath(EMPTY_STRING);
        entityManager.getTransaction().begin();
        instance.updateAssetVersion(userId, assetVersion);
        entityManager.getTransaction().commit();

        assertTrue("'updateAssetVersion' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'updateAssetVersion' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());

        assertTrue("'updateAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getFilePath()).exists());
        assertTrue("'updateAssetVersion' should be correct.",
            new File(retrievedAssetVersion.getPreviewImagePath()).exists());

        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());
        assertEquals("'updateAssetVersion' should be correct.",
            assetVersion.getId(), retrievedAsset.getCurrentVersion().getId());

        checkAuditRecord("updateAssetVersion", userId, "Update Asset Version", "AssetVersion", assetVersion.getId(),
            false, false);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersion_userIdNegative() throws Exception {
        userId = -1;

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersion_userIdZero() throws Exception {
        userId = 0;

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * assetVersion is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersion_assetVersionNull() throws Exception {
        assetVersion = null;

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * assetVersion.getId() is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersion_assetVersionIdNegative() throws Exception {
        assetVersion.setId(-1);

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * assetVersion.getId() is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersion_assetVersionIdZero() throws Exception {
        assetVersion.setId(0);

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * assetVersion.getAssetId() is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersion_assetVersionAssetIdNegative() throws Exception {
        assetVersion.setId(1);
        assetVersion.setAssetId(-1);

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * assetVersion.getAssetId() is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersion_assetVersionAssetIdZero() throws Exception {
        assetVersion.setId(1);
        assetVersion.setAssetId(0);

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * assetVersion.getFilePath() is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersion_assetVersionFilePathNull() throws Exception {
        assetVersion.setId(1);
        assetVersion.setFilePath(null);

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * assetVersion.getFilePath() is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersion_assetVersionFilePathEmpty() throws Exception {
        assetVersion.setId(1);
        assetVersion.setFilePath(EMPTY_STRING);

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * assetVersion.getAssetId() is invalid.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_updateAssetVersion_assetVersionAssetIdInvalid() throws Exception {
        assetVersion.setId(1);
        assetVersion.setAssetId(Long.MAX_VALUE);

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateAssetVersion_EntityNotFound() throws Exception {
        assetVersion.setId(Long.MAX_VALUE);

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_updateAssetVersion_PersistenceError1() throws Exception {
        assetVersion.setId(1);

        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_updateAssetVersion_PersistenceError2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setFilePath(TEST_FILES);

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersion(long userId, AssetVersion assetVersion)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_updateAssetVersion_entityManagerClosed() throws Exception {
        assetVersion.setId(1);
        entityManager.close();

        instance.updateAssetVersion(userId, assetVersion);
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteAssetVersion(long userId, long assetVersionId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_deleteAssetVersion_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        instance.deleteAssetVersion(userId, assetVersion.getId());
        entityManager.getTransaction().commit();
        entityManager.clear();

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());
        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());

        assertNull("'deleteAssetVersion' should be correct.", retrievedAssetVersion);
        assertNull("'deleteAssetVersion' should be correct.", retrievedAsset.getCurrentVersion());

        checkAuditRecord("deleteAssetVersion", userId, "Delete Asset Version", "AssetVersion", assetVersion.getId(),
            false, true);
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteAssetVersion(long userId, long assetVersionId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_deleteAssetVersion_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, false);
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        instance.deleteAssetVersion(userId, assetVersion.getId());
        entityManager.getTransaction().commit();
        entityManager.clear();

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());
        Asset retrievedAsset = entityManager.find(Asset.class, assetVersion.getAssetId());

        assertNull("'deleteAssetVersion' should be correct.", retrievedAssetVersion);
        assertNull("'deleteAssetVersion' should be correct.", retrievedAsset.getCurrentVersion());

        checkAuditRecord("deleteAssetVersion", userId, "Delete Asset Version", "AssetVersion", assetVersion.getId(),
            false, true);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssetVersion(long userId, long assetVersionId)</code> with
     * userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssetVersion_userIdNegative() throws Exception {
        userId = -1;

        instance.deleteAssetVersion(userId, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssetVersion(long userId, long assetVersionId)</code> with
     * userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssetVersion_userIdZero() throws Exception {
        userId = 0;

        instance.deleteAssetVersion(userId, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssetVersion(long userId, long assetVersionId)</code> with
     * assetVersionId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssetVersion_assetVersionIdNegative() throws Exception {
        instance.deleteAssetVersion(userId, -1);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssetVersion(long userId, long assetVersionId)</code> with
     * assetVersionId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssetVersion_assetVersionIdZero() throws Exception {
        instance.deleteAssetVersion(userId, 0);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssetVersion(long userId, long assetVersionId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_deleteAssetVersion_EntityNotFound() throws Exception {
        instance.deleteAssetVersion(userId, Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssetVersion(long userId, long assetVersionId)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_deleteAssetVersion_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.deleteAssetVersion(userId, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssetVersion(long userId, long assetVersionId)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_deleteAssetVersion_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.deleteAssetVersion(userId, 1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssetVersion(long assetVersionId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAssetVersion_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        AssetVersion res = instance.getAssetVersion(assetVersion.getId());

        assertEquals("'getAssetVersion' should be correct.",
            assetVersion.getVersion(), res.getVersion());
        assertEquals("'getAssetVersion' should be correct.",
            assetVersion.getFileName(), res.getFileName());
        assertEquals("'getAssetVersion' should be correct.",
            assetVersion.getFileType(), res.getFileType());
        assertEquals("'getAssetVersion' should be correct.",
            assetVersion.getFileSizeBytes(), res.getFileSizeBytes());
        assertEquals("'getAssetVersion' should be correct.",
            assetVersion.getUploader().getId(), res.getUploader().getId());
        assertNotNull("'getAssetVersion' should be correct.",
            res.getUploadTime());
        assertEquals("'getAssetVersion' should be correct.",
            assetVersion.getDescription(), res.getDescription());
        assertEquals("'getAssetVersion' should be correct.",
            assetVersion.getAssetId(), res.getAssetId());
        assertEquals("'getAssetVersion' should be correct.",
            assetVersion.getFilePath(), res.getFilePath());
        assertEquals("'getAssetVersion' should be correct.",
            assetVersion.getPreviewImagePath(), res.getPreviewImagePath());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssetVersion(long assetVersionId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAssetVersion_2() throws Exception {
        clearDB();

        AssetVersion res = instance.getAssetVersion(Long.MAX_VALUE);

        assertNull("'getAssetVersion' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssetVersion(long assetVersionId)</code> with
     * assetVersionId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAssetVersion_assetVersionIdNegative() throws Exception {
        instance.getAssetVersion(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssetVersion(long assetVersionId)</code> with
     * assetVersionId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAssetVersion_assetVersionIdZero() throws Exception {
        instance.getAssetVersion(0);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssetVersion(long assetVersionId)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getAssetVersion_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.getAssetVersion(1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssetVersion(long assetVersionId)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_getAssetVersion_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.getAssetVersion(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssetVersionsOfAsset(long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAssetVersionsOfAsset_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<AssetVersion> res = instance.getAssetVersionsOfAsset(assetVersion.getAssetId());
        assertEquals("'getAssetVersionsOfAsset' should be correct.", 1, res.size());

        AssetVersion retrievedAssetVersion = res.get(0);

        assertEquals("'getAssetVersionsOfAsset' should be correct.",
            assetVersion.getVersion(), retrievedAssetVersion.getVersion());
        assertEquals("'getAssetVersionsOfAsset' should be correct.",
            assetVersion.getFileName(), retrievedAssetVersion.getFileName());
        assertEquals("'getAssetVersionsOfAsset' should be correct.",
            assetVersion.getFileType(), retrievedAssetVersion.getFileType());
        assertEquals("'getAssetVersionsOfAsset' should be correct.",
            assetVersion.getFileSizeBytes(), retrievedAssetVersion.getFileSizeBytes());
        assertEquals("'getAssetVersionsOfAsset' should be correct.",
            assetVersion.getUploader().getId(), retrievedAssetVersion.getUploader().getId());
        assertNotNull("'getAssetVersionsOfAsset' should be correct.",
            retrievedAssetVersion.getUploadTime());
        assertEquals("'getAssetVersionsOfAsset' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());
        assertEquals("'getAssetVersionsOfAsset' should be correct.",
            assetVersion.getAssetId(), retrievedAssetVersion.getAssetId());
        assertEquals("'getAssetVersionsOfAsset' should be correct.",
            assetVersion.getFilePath(), retrievedAssetVersion.getFilePath());
        assertEquals("'getAssetVersionsOfAsset' should be correct.",
            assetVersion.getPreviewImagePath(), retrievedAssetVersion.getPreviewImagePath());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssetVersionsOfAsset(long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAssetVersionsOfAsset_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setId(0);
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, null, false);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<AssetVersion> res = instance.getAssetVersionsOfAsset(assetVersion.getAssetId());
        assertEquals("'getAssetVersionsOfAsset' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssetVersionsOfAsset(long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAssetVersionsOfAsset_3() throws Exception {
        List<AssetVersion> res = instance.getAssetVersionsOfAsset(1);

        assertEquals("'getAssetVersionsOfAsset' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAssetVersionsOfAsset(long assetId)</code> with
     * assetId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAssetVersionsOfAsset_assetIdNegative() throws Exception {
        instance.getAssetVersionsOfAsset(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssetVersionsOfAsset(long assetId)</code> with
     * assetId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAssetVersionsOfAsset_assetIdZero() throws Exception {
        instance.getAssetVersionsOfAsset(0);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssetVersionsOfAsset(long assetId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_getAssetVersionsOfAsset_EntityNotFound() throws Exception {
        instance.getAssetVersionsOfAsset(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssetVersionsOfAsset(long assetId)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getAssetVersionsOfAsset_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.getAssetVersionsOfAsset(1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssetVersionsOfAsset(long assetId)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_getAssetVersionsOfAsset_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.getAssetVersionsOfAsset(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAssetVersions_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setDescription("new description");
        entityManager.getTransaction().begin();
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
        entityManager.getTransaction().commit();

        AssetVersion retrievedAssetVersion = entityManager.find(AssetVersion.class, assetVersion.getId());

        assertEquals("'updateAssetVersions' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion.getDescription());

        checkAuditRecord("updateAssetVersions", userId, "Update Asset Version", "AssetVersion", assetVersion.getId(),
            false, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAssetVersions_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        AssetVersion assetVersion2 = entityManager.find(AssetVersion.class, assetVersion.getId());
        entityManager.clear();

        assetVersion2.setId(0);
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion2, null, false);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setDescription("new description 1");
        assetVersion2.setDescription("new description 2");
        entityManager.getTransaction().begin();
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion, assetVersion2));
        entityManager.getTransaction().commit();

        assertTrue("'updateAssetVersions' should be correct.", assetVersion.getId() > 0);

        AssetVersion retrievedAssetVersion1 = entityManager.find(AssetVersion.class, assetVersion.getId());
        AssetVersion retrievedAssetVersion2 = entityManager.find(AssetVersion.class, assetVersion2.getId());

        assertEquals("'updateAssetVersions' should be correct.",
            assetVersion.getDescription(), retrievedAssetVersion1.getDescription());
        assertEquals("'updateAssetVersions' should be correct.",
            assetVersion2.getDescription(), retrievedAssetVersion2.getDescription());
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_userIdNegative() throws Exception {
        userId = -1;

        assetVersion.setId(1);
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_userIdZero() throws Exception {
        userId = 0;

        assetVersion.setId(1);
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with assetVersions is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_assetVersionsNull() throws Exception {
        instance.updateAssetVersions(userId, null);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with assetVersions contains null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_assetVersionsContainsNull() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        instance.updateAssetVersions(userId, Arrays.asList(assetVersion, null));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with assetVersions[i].getId() is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_IdNegative() throws Exception {
        assetVersion.setId(-1);
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with assetVersions[i].getId() is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_IdZero() throws Exception {
        assetVersion.setId(0);
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with assetVersions[i].getAssetId() is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_AssetIdNegative() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setAssetId(-1);
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with assetVersions[i].getAssetId() is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_AssetIdZero() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setAssetId(0);
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with assetVersions[i].getFilePath() is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_FilePathNull() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setFilePath(null);
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with assetVersions[i].getFilePath() is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssetVersions_FilePathEmpty() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setFilePath(EMPTY_STRING);
        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateAssetVersions_EntityNotFound() throws Exception {
        assetVersion.setId(Long.MAX_VALUE);

        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_updateAssetVersions_PersistenceError1() throws Exception {
        assetVersion.setId(1);

        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_updateAssetVersions_PersistenceError2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setFilePath(TEST_FILES);

        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssetVersions(long userId,
     * List&lt;AssetVersion&gt; assetVersions)</code> with entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_updateAssetVersions_entityManagerClosed() throws Exception {
        assetVersion.setId(1);
        entityManager.close();

        instance.updateAssetVersions(userId, Arrays.asList(assetVersion));
    }

    /**
     * <p>
     * Accuracy test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_batchGetAssetVersionContents_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        instance.batchGetAssetVersionContents(Arrays.asList(assetVersion.getId()), output);

        assertTrue("'batchGetAssetVersionContents' should be correct.",
            output.toByteArray().length > 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_batchGetAssetVersionContents_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        AssetVersion assetVersion2 = entityManager.find(AssetVersion.class, assetVersion.getId());
        entityManager.clear();

        assetVersion2.setId(0);
        assetVersion2.setFilePath(TEST_FILES + "data");
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion2, null, false);
        entityManager.getTransaction().commit();
        entityManager.clear();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        instance.batchGetAssetVersionContents(Arrays.asList(assetVersion.getId(), assetVersion2.getId()), output);

        assertTrue("'batchGetAssetVersionContents' should be correct.",
            output.toByteArray().length > 0);
    }

    /**
     * <p>
     * Failure test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code> with assetVersionIds is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_batchGetAssetVersionContents_assetVersionIdsNull() throws Exception {
        OutputStream output = new ByteArrayOutputStream();
        instance.batchGetAssetVersionContents(null,  output);
    }

    /**
     * <p>
     * Failure test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code> with assetVersionIds contains null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_batchGetAssetVersionContents_assetVersionIdsContainsNull() throws Exception {
        OutputStream output = new ByteArrayOutputStream();
        instance.batchGetAssetVersionContents(Arrays.asList(1L, null),  output);
    }

    /**
     * <p>
     * Failure test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code> with assetVersionIds contains negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_batchGetAssetVersionContents_assetVersionIdsContainsNegative() throws Exception {
        OutputStream output = new ByteArrayOutputStream();
        instance.batchGetAssetVersionContents(Arrays.asList(-1L),  output);
    }

    /**
     * <p>
     * Failure test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code> with assetVersionIds contains zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_batchGetAssetVersionContents_assetVersionIdsContainsZero() throws Exception {
        OutputStream output = new ByteArrayOutputStream();
        instance.batchGetAssetVersionContents(Arrays.asList(0L),  output);
    }

    /**
     * <p>
     * Failure test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code> with output is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_batchGetAssetVersionContents_outputNull() throws Exception {
        instance.batchGetAssetVersionContents(Arrays.asList(1L),  null);
    }

    /**
     * <p>
     * Failure test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code> with the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_batchGetAssetVersionContents_EntityNotFound() throws Exception {
        OutputStream output = new ByteArrayOutputStream();
        instance.batchGetAssetVersionContents(Arrays.asList(Long.MAX_VALUE), output);
    }

    /**
     * <p>
     * Failure test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_batchGetAssetVersionContents_PersistenceError1() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        OutputStream output = new ByteArrayOutputStream();
        instance.batchGetAssetVersionContents(Arrays.asList(1L), output);
    }

    /**
     * <p>
     * Failure test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_batchGetAssetVersionContents_PersistenceError2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAssetVersion(userId, assetVersion, file, true);
        entityManager.getTransaction().commit();
        entityManager.clear();

        File temp = new File(TEST_FILES + "temp.zip");
        temp.createNewFile();
        try {
            OutputStream output = new FileOutputStream(temp);
            output.close();
            instance.batchGetAssetVersionContents(Arrays.asList(assetVersion.getId()), output);
        } finally {
            temp.delete();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>batchGetAssetVersionContents(List&lt;Long&gt; assetVersionIds,
     * OutputStream output)</code> with entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_batchGetAssetVersionContents_entityManagerClosed() throws Exception {
        entityManager.close();

        OutputStream output = new ByteArrayOutputStream();
        instance.batchGetAssetVersionContents(Arrays.asList(1L), output);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setBasePath(String basePath)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setBasePath() {
        instance.setBasePath(basePath);

        assertEquals("'setBasePath' should be correct.",
            basePath, getField(instance, "basePath"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setImageTypes(List&lt;String&gt; imageTypes)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setImageTypes() {
        instance.setImageTypes(imageTypes);

        assertSame("'setImageTypes' should be correct.",
            imageTypes, getField(instance, "imageTypes"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPreviewImageWidth(int previewImageWidth)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPreviewImageWidth() {
        instance.setPreviewImageWidth(previewImageWidth);

        assertEquals("'setPreviewImageWidth' should be correct.",
            previewImageWidth, getField(instance, "previewImageWidth"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPreviewImageWidth(int previewImageHeight)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPreviewImageHeight() {
        instance.setPreviewImageHeight(previewImageHeight);

        assertEquals("'setPreviewImageHeight' should be correct.",
            previewImageHeight, getField(instance, "previewImageHeight"));
    }
}