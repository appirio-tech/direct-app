/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;
import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetSearchCriteria;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.entities.User;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link AssetServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AssetServiceImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>AssetServiceImpl</code> instance used in tests.
     * </p>
     */
    private AssetServiceImpl instance;

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
     * Represents the asset version service used in tests.
     * </p>
     */
    private AssetVersionServiceImpl assetVersionService;

    /**
     * <p>
     * Represents the user id used in tests.
     * </p>
     */
    private long userId = 1;

    /**
     * <p>
     * Represents the asset used in tests.
     * </p>
     */
    private Asset asset;

    /**
     * <p>
     * Represents the asset version used in tests.
     * </p>
     */
    private AssetVersion assetVersion;

    /**
     * <p>
     * Represents the criteria used in tests.
     * </p>
     */
    private AssetSearchCriteria criteria;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetServiceImplUnitTests.class);
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

        assetVersionService = new AssetVersionServiceImpl();
        assetVersionService.setEntityManager(entityManager);
        assetVersionService.setLog(log);
        assetVersionService.setBasePath(basePath);
        assetVersionService.setImageTypes(imageTypes);
        assetVersionService.setPreviewImageWidth(previewImageWidth);
        assetVersionService.setPreviewImageHeight(previewImageHeight);

        instance = new AssetServiceImpl();
        instance.setEntityManager(entityManager);
        instance.setLog(log);
        instance.setAssetVersionService(assetVersionService);

        assetVersion = getAssetVersion();
        asset = getAsset();

        criteria = new AssetSearchCriteria();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AssetServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new AssetServiceImpl();

        assertNull("'entityManager' should be correct.", getField(instance, "entityManager"));
        assertNull("'log' should be correct.", getField(instance, "log"));

        assertNull("'basePath' should be correct.", getField(instance, "assetVersionService"));
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

        assertNotNull("'checkInit' should be correct.", getField(instance, "assetVersionService"));
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

        instance.checkInit();

        assertNotNull("'checkInit' should be correct.", getField(instance, "entityManager"));
        assertNull("'checkInit' should be correct.", getField(instance, "log"));

        assertNotNull("'checkInit' should be correct.", getField(instance, "assetVersionService"));
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
     * with assetVersionService is null.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_assetVersionServiceNull() {
        instance.setAssetVersionService(null);

        instance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAsset(long userId, Asset asset)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createAsset_1() throws Exception {
        asset.setCategories(null);

        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'createAsset' should be correct.", asset.getId() > 0);

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());

        assertEquals("'createAsset' should be correct.",
            asset.getName(), retrievedAsset.getName());
        assertNull("'createAsset' should be correct.",
            retrievedAsset.getCurrentVersion());
        assertEquals("'createAsset' should be correct.",
            asset.getContainerType(), retrievedAsset.getContainerType());
        assertEquals("'createAsset' should be correct.",
            asset.getContainerId(), retrievedAsset.getContainerId());
        assertEquals("'createAsset' should be correct.",
            asset.isPublic(), retrievedAsset.isPublic());
        assertEquals("'createAsset' should be correct.",
            0, retrievedAsset.getCategories().size());

        checkAuditRecord("createAsset", userId, "Create Asset", "Asset", asset.getId(), true, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAsset(long userId, Asset asset)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createAsset_2() throws Exception {
        entityManager.getTransaction().begin();
        assetVersionService.createAssetVersion(userId, assetVersion, null, false);
        entityManager.getTransaction().commit();

        asset.setCurrentVersion(assetVersion);

        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'createAsset' should be correct.", asset.getId() > 0);

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());

        assertEquals("'createAsset' should be correct.",
            asset.getName(), retrievedAsset.getName());

        AssetVersion currentVersion = retrievedAsset.getCurrentVersion();
        assertEquals("'createAsset' should be correct.", assetVersion.getId(), currentVersion.getId());
        assertEquals("'createAsset' should be correct.", asset.getId(), currentVersion.getAssetId());

        assertEquals("'createAsset' should be correct.",
            asset.getContainerType(), retrievedAsset.getContainerType());
        assertEquals("'createAsset' should be correct.",
            asset.getContainerId(), retrievedAsset.getContainerId());
        assertEquals("'createAsset' should be correct.",
            asset.isPublic(), retrievedAsset.isPublic());
        assertEquals("'createAsset' should be correct.",
            asset.getCategories().size(), retrievedAsset.getCategories().size());

        checkAuditRecord("createAsset", userId, "Create Asset", "Asset", asset.getId(), true, false);
    }

    /**
     * <p>
     * Failure test for the method <code>createAsset(long userId, Asset asset)</code> with userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAsset_userIdNegative() throws Exception {
        userId = -1;

        instance.createAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>createAsset(long userId, Asset asset)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAsset_userIdZero() throws Exception {
        userId = 0;

        instance.createAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>createAsset(long userId, Asset asset)</code> with asset is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAsset_assetNull() throws Exception {
        asset = null;

        instance.createAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>createAsset(long userId, Asset asset)</code> with
     * asset.getCurrentVersion().getId() is invalid.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_createAsset_currentVersionIdInvalid() throws Exception {
        assetVersion.setId(Long.MAX_VALUE);
        asset.setCurrentVersion(assetVersion);

        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure test for the method <code>createAsset(long userId, Asset asset)</code> with
     * category id is invalid.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_createAsset_categoryIdInvalid() throws Exception {
        asset.getCategories().get(0).setId(Long.MAX_VALUE);

        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure test for the method <code>createAsset(long userId, Asset asset)</code> with a persistence error has
     * occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_createAsset_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.createAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>createAsset(long userId, Asset asset)</code> with entity manager has been
     * closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_createAsset_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.createAsset(userId, asset);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAsset(long userId, Asset asset)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAsset_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setAssetId(asset.getId());
        entityManager.getTransaction().begin();
        assetVersionService.createAssetVersion(userId, assetVersion, null, false);
        entityManager.getTransaction().commit();

        asset.setName("new name");
        asset.setCurrentVersion(assetVersion);
        asset.getCategories().remove(0);
        entityManager.getTransaction().begin();
        instance.updateAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'updateAsset' should be correct.", asset.getId() > 0);

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());

        assertEquals("'updateAsset' should be correct.",
            asset.getName(), retrievedAsset.getName());
        assertEquals("'updateAsset' should be correct.",
            assetVersion.getId(), retrievedAsset.getCurrentVersion().getId());
        assertEquals("'updateAsset' should be correct.",
            asset.getContainerType(), retrievedAsset.getContainerType());
        assertEquals("'updateAsset' should be correct.",
            asset.getContainerId(), retrievedAsset.getContainerId());
        assertEquals("'updateAsset' should be correct.",
            asset.isPublic(), retrievedAsset.isPublic());
        assertEquals("'updateAsset' should be correct.",
            asset.getCategories().size(), retrievedAsset.getCategories().size());

        checkAuditRecord("updateAsset", userId, "Update Asset", "Asset", asset.getId(),
            false, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAsset(long userId, Asset asset)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAsset_2() throws Exception {
        entityManager.getTransaction().begin();
        assetVersionService.createAssetVersion(userId, assetVersion, null, false);
        entityManager.getTransaction().commit();

        asset.setCurrentVersion(assetVersion);

        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        asset.setCurrentVersion(null);
        asset.setCategories(null);
        entityManager.getTransaction().begin();
        instance.updateAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'updateAsset' should be correct.", asset.getId() > 0);

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());

        assertEquals("'updateAsset' should be correct.",
            asset.getName(), retrievedAsset.getName());
        assertNull("'updateAsset' should be correct.",
            retrievedAsset.getCurrentVersion());
        assertEquals("'updateAsset' should be correct.",
            asset.getContainerType(), retrievedAsset.getContainerType());
        assertEquals("'updateAsset' should be correct.",
            asset.getContainerId(), retrievedAsset.getContainerId());
        assertEquals("'updateAsset' should be correct.",
            asset.isPublic(), retrievedAsset.isPublic());
        assertEquals("'updateAsset' should be correct.",
            0, retrievedAsset.getCategories().size());

        checkAuditRecord("updateAsset", userId, "Update Asset", "Asset", asset.getId(),
            false, false);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAsset_userIdNegative() throws Exception {
        userId = -1;

        instance.updateAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAsset_userIdZero() throws Exception {
        userId = 0;

        instance.updateAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with
     * asset is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAsset_assetNull() throws Exception {
        asset = null;

        instance.updateAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with
     * asset.getId() is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAsset_assetIdNegative() throws Exception {
        asset.setId(-1);

        instance.updateAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with
     * asset.getId() is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAsset_assetIdZero() throws Exception {
        asset.setId(0);

        instance.updateAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with
     * asset.getCurrentVersion().getId() is invalid.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateAsset_currentVersionIdInvalid() throws Exception {
        assetVersion.setId(Long.MAX_VALUE);
        asset.setId(1);
        asset.setCurrentVersion(assetVersion);

        entityManager.getTransaction().begin();
        instance.updateAsset(userId, asset);
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with
     * category id is invalid.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateAsset_categoryIdInvalid() throws Exception {
        asset.setId(1);
        asset.getCategories().get(0).setId(Long.MAX_VALUE);

        entityManager.getTransaction().begin();
        instance.updateAsset(userId, asset);
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateAsset_EntityNotFound() throws Exception {
        asset.setId(Long.MAX_VALUE);

        instance.updateAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_updateAsset_PersistenceError() throws Exception {
        asset.setId(1);

        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.updateAsset(userId, asset);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAsset(long userId, Asset asset)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_updateAsset_entityManagerClosed() throws Exception {
        asset.setId(1);
        entityManager.close();

        instance.updateAsset(userId, asset);
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteAsset(long userId, long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_deleteAsset_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        instance.deleteAsset(userId, asset.getId());
        entityManager.getTransaction().commit();
        entityManager.clear();

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());

        assertNull("'deleteAsset' should be correct.", retrievedAsset);

        checkAuditRecord("deleteAsset", userId, "Delete Asset", "Asset", asset.getId(),
            false, true);
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteAsset(long userId, long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_deleteAsset_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assetVersion.setAssetId(asset.getId());
        entityManager.getTransaction().begin();
        assetVersionService.createAssetVersion(userId, assetVersion, null, false);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        instance.deleteAsset(userId, asset.getId());
        entityManager.getTransaction().commit();
        entityManager.clear();

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());
        Asset retrievedAssetVersion = entityManager.find(Asset.class, assetVersion.getId());

        assertNull("'deleteAsset' should be correct.", retrievedAsset);
        assertNull("'deleteAsset' should be correct.", retrievedAssetVersion);

        checkAuditRecord("deleteAsset", userId, "Delete Asset", "Asset", asset.getId(), false, true);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAsset(long userId, long assetId)</code> with userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAsset_userIdNegative() throws Exception {
        userId = -1;

        instance.deleteAsset(userId, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAsset(long userId, long assetId)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAsset_userIdZero() throws Exception {
        userId = 0;

        instance.deleteAsset(userId, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAsset(long userId, long assetId)</code> with
     * assetId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAsset_assetIdNegative() throws Exception {
        instance.deleteAsset(userId, -1);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAsset(long userId, long assetId)</code> with
     * assetId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAsset_assetIdZero() throws Exception {
        instance.deleteAsset(userId, 0);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAsset(long userId, long assetId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_deleteAsset_EntityNotFound() throws Exception {
        instance.deleteAsset(userId, Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAsset(long userId, long assetId)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_deleteAsset_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.deleteAsset(userId, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAsset(long userId, long assetId)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_deleteAsset_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.deleteAsset(userId, 1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAsset(long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAsset_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Asset res = instance.getAsset(asset.getId());

        assertEquals("'getAsset' should be correct.", asset.getName(), res.getName());
        assertNull("'getAsset' should be correct.", res.getCurrentVersion());
        assertEquals("'getAsset' should be correct.", asset.getContainerType(), res.getContainerType());
        assertEquals("'getAsset' should be correct.", asset.getContainerId(), res.getContainerId());
        assertEquals("'getAsset' should be correct.", asset.isPublic(), res.isPublic());
        assertEquals("'getAsset' should be correct.", asset.getCategories().size(), res.getCategories().size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAsset(long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAsset_2() throws Exception {
        clearDB();

        Asset res = instance.getAsset(Long.MAX_VALUE);

        assertNull("'getAsset' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>getAsset(long assetId)</code> with
     * assetId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAsset_assetIdNegative() throws Exception {
        instance.getAsset(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAsset(long assetId)</code> with
     * assetId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAsset_assetIdZero() throws Exception {
        instance.getAsset(0);
    }

    /**
     * <p>
     * Failure test for the method <code>getAsset(long assetId)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getAsset_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.getAsset(1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAsset(long assetId)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_getAsset_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.getAsset(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchAssets(AssetSearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchAssets_1() throws Exception {
        clearDB();

        PagedResult<Asset> res = instance.searchAssets(criteria);

        assertEquals("'searchAssets' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'searchAssets' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'searchAssets' should be correct.", 0, res.getTotalPages());

        List<Asset> records = res.getRecords();
        assertEquals("'searchAssets' should be correct.", 0, records.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchAssets(AssetSearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchAssets_2() throws Exception {
        PagedResult<Asset> res = instance.searchAssets(criteria);

        assertEquals("'searchAssets' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'searchAssets' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'searchAssets' should be correct.", 1, res.getTotalPages());

        List<Asset> records = res.getRecords();
        assertEquals("'searchAssets' should be correct.", 1, records.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchAssets(AssetSearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchAssets_3() throws Exception {
        asset.setCategories(null);

        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        criteria.setAscending(true);
        criteria.setSortingColumn("name");

        PagedResult<Asset> res = instance.searchAssets(criteria);

        assertEquals("'searchAssets' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'searchAssets' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'searchAssets' should be correct.", 1, res.getTotalPages());

        List<Asset> records = res.getRecords();
        assertEquals("'searchAssets' should be correct.", 2, records.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchAssets(AssetSearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchAssets_4() throws Exception {
        entityManager.getTransaction().begin();
        assetVersionService.createAssetVersion(userId, assetVersion, null, false);
        entityManager.getTransaction().commit();

        asset.setCurrentVersion(assetVersion);
        asset.setCategories(null);

        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        criteria.setUser("user1");
        criteria.setSortingColumn("name");

        PagedResult<Asset> res = instance.searchAssets(criteria);

        assertEquals("'searchAssets' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'searchAssets' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'searchAssets' should be correct.", 1, res.getTotalPages());

        List<Asset> records = res.getRecords();
        assertEquals("'searchAssets' should be correct.", 2, records.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchAssets(AssetSearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchAssets_5() throws Exception {
        clearDB();

        criteria.setPage(1);
        criteria.setPageSize(1);
        PagedResult<Asset> res = instance.searchAssets(criteria);

        assertEquals("'searchAssets' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'searchAssets' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'searchAssets' should be correct.", 0, res.getTotalPages());

        List<Asset> records = res.getRecords();
        assertEquals("'searchAssets' should be correct.", 0, records.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchAssets(AssetSearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchAssets_6() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        criteria.setPage(1);
        criteria.setPageSize(1);
        PagedResult<Asset> res = instance.searchAssets(criteria);

        assertEquals("'searchAssets' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'searchAssets' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'searchAssets' should be correct.", 2, res.getTotalPages());

        List<Asset> records = res.getRecords();
        assertEquals("'searchAssets' should be correct.", 1, records.size());
    }
    /**
     * <p>
     * Accuracy test for the method <code>searchAssets(AssetSearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchAssets_7() throws Exception {
        entityManager.getTransaction().begin();
        assetVersionService.createAssetVersion(userId, assetVersion, null, false);
        entityManager.getTransaction().commit();

        asset.setCurrentVersion(assetVersion);

        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        criteria.setName("design");
        criteria.setContainerType(asset.getContainerType());
        criteria.setContainerId(asset.getContainerId());

        criteria.setVersion(assetVersion.getVersion());
        criteria.setFileName(assetVersion.getFileName());
        criteria.setUploaders(Arrays.asList("user1"));
        Calendar startTimeCal = Calendar.getInstance();
        startTimeCal.add(Calendar.DATE, -10);
        criteria.setStartTime(startTimeCal.getTime());
        Calendar endTimeCal = Calendar.getInstance();
        endTimeCal.add(Calendar.DATE, 10);
        criteria.setEndTime(endTimeCal.getTime());

        criteria.setCategories(Arrays.asList("Category1"));

        PagedResult<Asset> res = instance.searchAssets(criteria);

        assertEquals("'searchAssets' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'searchAssets' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'searchAssets' should be correct.", 1, res.getTotalPages());

        List<Asset> records = res.getRecords();
        assertEquals("'searchAssets' should be correct.", 1, records.size());
        assertEquals("'searchAssets' should be correct.", asset.getId(), records.get(0).getId());
    }

    /**
     * <p>
     * Failure test for the method <code>searchAssets(AssetSearchCriteria criteria)</code> with
     * criteria is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchAssets_criteriaNull() throws Exception {
        criteria = null;

        instance.searchAssets(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>searchAssets(AssetSearchCriteria criteria)</code> with
     * criteria.page is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchAssets_criteriaPageNegative() throws Exception {
        criteria.setPage(-1);

        instance.searchAssets(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>searchAssets(AssetSearchCriteria criteria)</code> with
     * criteria.pageSize is negative when criteria.page is positive.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchAssets_criteriaPageSizeNegative() throws Exception {
        criteria.setPage(1);
        criteria.setPageSize(-1);

        instance.searchAssets(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>searchAssets(AssetSearchCriteria criteria)</code> with
     * criteria.pageSize is zero when criteria.page is positive.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchAssets_criteriaPageSizeZero() throws Exception {
        criteria.setPage(1);
        criteria.setPageSize(0);

        instance.searchAssets(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>searchAssets(AssetSearchCriteria criteria)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_searchAssets_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.searchAssets(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>searchAssets(AssetSearchCriteria criteria)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_searchAssets_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.searchAssets(criteria);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createAssets_1() throws Exception {
        asset.setCategories(null);

        entityManager.getTransaction().begin();
        instance.createAssets(userId, Arrays.asList(asset));
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'createAssets' should be correct.", asset.getId() > 0);

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());

        assertEquals("'createAssets' should be correct.",
            asset.getName(), retrievedAsset.getName());
        assertNull("'createAssets' should be correct.",
            retrievedAsset.getCurrentVersion());
        assertEquals("'createAssets' should be correct.",
            asset.getContainerType(), retrievedAsset.getContainerType());
        assertEquals("'createAssets' should be correct.",
            asset.getContainerId(), retrievedAsset.getContainerId());
        assertEquals("'createAssets' should be correct.",
            asset.isPublic(), retrievedAsset.isPublic());
        assertEquals("'createAssets' should be correct.",
            0, retrievedAsset.getCategories().size());

        checkAuditRecord("createAssets", userId, "Create Asset", "Asset", asset.getId(), true, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createAssets_2() throws Exception {
        Asset asset2 = new Asset();
        asset2.setName("design document2");
        asset2.setContainerType("containerType2");
        asset2.setContainerId(2);

        entityManager.getTransaction().begin();
        instance.createAssets(userId, Arrays.asList(asset, asset2));
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'createAssets' should be correct.", asset.getId() > 0);
        assertTrue("'createAssets' should be correct.", asset2.getId() > 0);

        checkAuditRecord("createAssets", userId, "Create Asset", "Asset", asset2.getId(), true, false);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code> with userId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAssets_userIdNegative() throws Exception {
        userId = -1;

        instance.createAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Failure test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code> with userId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAssets_userIdZero() throws Exception {
        userId = 0;

        instance.createAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Failure test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code> with assets is
     * null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAssets_assetsNull() throws Exception {
        instance.createAssets(userId, null);
    }

    /**
     * <p>
     * Failure test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code> with assets contains
     * null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAssets_assetsContainsNull() throws Exception {
        instance.createAssets(userId, Arrays.asList(asset, null));
    }

    /**
     * <p>
     * Failure test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code> with
     * asset.getCurrentVersion().getId() is invalid.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_createAssets_currentVersionIdInvalid() throws Exception {
        assetVersion.setId(Long.MAX_VALUE);
        asset.setCurrentVersion(assetVersion);

        entityManager.getTransaction().begin();
        instance.createAssets(userId, Arrays.asList(asset));
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code> with category id
     * is invalid.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_createAssets_categoryIdInvalid() throws Exception {
        asset.getCategories().get(0).setId(Long.MAX_VALUE);

        entityManager.getTransaction().begin();
        instance.createAssets(userId, Arrays.asList(asset));
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_createAssets_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.createAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Failure test for the method <code>createAssets(long userId, List&lt;Asset&gt; assets)</code> with entity
     * manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_createAssets_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.createAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAssets_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        asset.setName("new name");
        entityManager.getTransaction().begin();
        instance.updateAssets(userId, Arrays.asList(asset));
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'updateAssets' should be correct.", asset.getId() > 0);

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());

        assertEquals("'updateAssets' should be correct.",
            asset.getName(), retrievedAsset.getName());

        checkAuditRecord("updateAssets", userId, "Update Asset", "Asset", asset.getId(),
            false, false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateAssets_2() throws Exception {
        Asset asset2 = new Asset();
        asset2.setName("design document2");
        asset2.setContainerType("containerType2");
        asset2.setContainerId(2);

        entityManager.getTransaction().begin();
        instance.createAssets(userId, Arrays.asList(asset, asset2));
        entityManager.getTransaction().commit();
        entityManager.clear();

        asset.setName("new name 1");
        asset2.setName("new name 2");
        entityManager.getTransaction().begin();
        instance.updateAssets(userId, Arrays.asList(asset, asset2));
        entityManager.getTransaction().commit();
        entityManager.clear();

        Asset retrievedAsset1 = entityManager.find(Asset.class, asset.getId());
        Asset retrievedAsset2 = entityManager.find(Asset.class, asset2.getId());

        assertEquals("'updateAssets' should be correct.", asset.getName(), retrievedAsset1.getName());
        assertEquals("'updateAssets' should be correct.", asset2.getName(), retrievedAsset2.getName());

        checkAuditRecord("updateAssets", userId, "Update Asset", "Asset", asset2.getId(), false, false);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with userId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssets_userIdNegative() throws Exception {
        userId = -1;

        asset.setId(1);
        instance.updateAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with userId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssets_userIdZero() throws Exception {
        userId = 0;

        asset.setId(1);
        instance.updateAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with assets is
     * null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssets_assetsNull() throws Exception {
        instance.updateAssets(userId, null);
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with assets contains
     * null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssets_assetsContainsNull() throws Exception {
        asset.setId(1);

        instance.updateAssets(userId, Arrays.asList(asset, null));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with
     * asset.getId() is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssets_assetIdNegative() throws Exception {
        asset.setId(-1);

        instance.updateAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with
     * asset.getId() is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateAssets_assetIdZero() throws Exception {
        asset.setId(0);

        instance.updateAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with
     * asset.getCurrentVersion().getId() is invalid.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateAssets_currentVersionIdInvalid() throws Exception {
        assetVersion.setId(Long.MAX_VALUE);
        asset.setId(1);
        asset.setCurrentVersion(assetVersion);

        entityManager.getTransaction().begin();
        instance.updateAssets(userId, Arrays.asList(asset));
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with
     * category id is invalid.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateAssets_categoryIdInvalid() throws Exception {
        asset.setId(1);
        asset.getCategories().get(0).setId(Long.MAX_VALUE);

        entityManager.getTransaction().begin();
        instance.updateAssets(userId, Arrays.asList(asset));
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_updateAssets_EntityNotFound() throws Exception {
        asset.setId(Long.MAX_VALUE);

        instance.updateAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_updateAssets_PersistenceError() throws Exception {
        asset.setId(1);

        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.updateAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Failure test for the method <code>updateAssets(long userId, List&lt;Asset&gt; assets)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_updateAssets_entityManagerClosed() throws Exception {
        asset.setId(1);
        entityManager.close();

        instance.updateAssets(userId, Arrays.asList(asset));
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_deleteAssets_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createAsset(userId, asset);
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        instance.deleteAssets(userId, Arrays.asList(asset.getId()));
        entityManager.getTransaction().commit();
        entityManager.clear();

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());

        assertNull("'deleteAssets' should be correct.", retrievedAsset);

        checkAuditRecord("deleteAssets", userId, "Delete Asset", "Asset", asset.getId(),
            false, true);
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_deleteAssets_2() throws Exception {
        Asset asset2 = new Asset();
        asset2.setName("design document2");
        asset2.setContainerType("containerType2");
        asset2.setContainerId(2);

        entityManager.getTransaction().begin();
        instance.createAssets(userId, Arrays.asList(asset, asset2));
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        instance.deleteAssets(userId, Arrays.asList(asset.getId(), asset2.getId()));
        entityManager.getTransaction().commit();
        entityManager.clear();

        Asset retrievedAsset = entityManager.find(Asset.class, asset.getId());
        Asset retrievedAssetVersion = entityManager.find(Asset.class, assetVersion.getId());

        assertNull("'deleteAssets' should be correct.", retrievedAsset);
        assertNull("'deleteAssets' should be correct.", retrievedAssetVersion);

        checkAuditRecord("deleteAssets", userId, "Delete Asset", "Asset", asset2.getId(), false, true);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code> with userId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssets_userIdNegative() throws Exception {
        userId = -1;

        instance.deleteAssets(userId, Arrays.asList(1L));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code> with userId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssets_userIdZero() throws Exception {
        userId = 0;

        instance.deleteAssets(userId, Arrays.asList(1L));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code> with
     * assetIds is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssets_assetIdsNull() throws Exception {
        instance.deleteAssets(userId, null);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code> with
     * assetIds contains null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssets_assetIdsContainsNull() throws Exception {
        instance.deleteAssets(userId, Arrays.asList(1l, null));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code> with
     * assetIds contains negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssets_assetIdsContainsNegative() throws Exception {
        instance.deleteAssets(userId, Arrays.asList(-1L));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code> with
     * assetId contains zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteAssets_assetIdContainsZero() throws Exception {
        instance.deleteAssets(userId, Arrays.asList(0L));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_deleteAssets_EntityNotFound() throws Exception {
        instance.deleteAssets(userId, Arrays.asList(Long.MAX_VALUE));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code> with a
     * persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_deleteAssets_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.deleteAssets(userId, Arrays.asList(1L));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteAssets(long userId, List&lt;Long&gt; assetIds)</code> with
     * entity manager has been closed.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_deleteAssets_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.deleteAssets(userId, Arrays.asList(1L));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssetVersionService(AssetVersionService assetVersionService)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssetVersionService() {
        instance.setAssetVersionService(assetVersionService);

        assertSame("'setAssetVersionService' should be correct.",
            assetVersionService, getField(instance, "assetVersionService"));
    }

    /**
     * Creates an instance of Asset.
     *
     * @return the Asset instance
     */
    private static Asset getAsset() {
        List<Category> categories = new ArrayList<Category>();
        Category category1 = new Category();
        category1.setId(100);
        categories.add(category1);

        Category category2 = new Category();
        category2.setId(101);
        categories.add(category2);

        Asset asset = new Asset();
        asset.setName("design document");
        asset.setCategories(categories);
        asset.setContainerType("containerType1");
        asset.setContainerId(1);
        asset.setPublic(true);

        return asset;
    }

    /**
     * Creates an instance of AssetVersion.
     *
     * @return the AssetVersion instance
     */
    private static AssetVersion getAssetVersion() {
        AssetVersion assetVersion = new AssetVersion();
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

        return assetVersion;
    }
}