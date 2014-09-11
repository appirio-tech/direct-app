/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.accuracytests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.services.impl.AssetServiceImpl;
import com.topcoder.asset.services.impl.AssetVersionServiceImpl;
import com.topcoder.util.log.log4j.Log4jLogFactory;

/**
 * <p>
 * Accuracy unit test for {@link AssetServiceImpl}.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class AssetServiceImplAccuracyTests {
    /**
     * <p>
     * Represents the AssetServiceImpl instance to test.
     * </p>
     */
    private AssetServiceImpl impl;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetServiceImplAccuracyTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        impl = new AssetServiceImpl();
        impl.setLog(new Log4jLogFactory().createLog("test"));
        EntityManager entityManager = AccuracyHelper.getConnection();
		impl.setEntityManager(entityManager);
		
		AssetVersionServiceImpl assetVersionService = new AssetVersionServiceImpl();
		assetVersionService.setLog(new Log4jLogFactory().createLog("test"));
        assetVersionService.setEntityManager(entityManager);
        assetVersionService.setBasePath("test_files/acc/");
        assetVersionService.setImageTypes(Arrays.asList("jpg", "png", "gif"));
        assetVersionService.setPreviewImageHeight(800);
        assetVersionService.setPreviewImageWidth(600);
		impl.setAssetVersionService(assetVersionService);
        AccuracyHelper.clearDB();
        AccuracyHelper.prepareDB();
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
        AccuracyHelper.clearDB();
    }

    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#AssetServiceImpl()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_AssetServiceImpl() throws Exception {
        impl = new AssetServiceImpl();
    }
    

    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#createAsset() and getAsset()} with null current version.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_createAsset_withoutCurrentVersion() throws Exception {
		Asset asset = new Asset();
		asset.setName("assertcreated");
		AssetVersion currentVersion = new AssetVersion();
		currentVersion.setId(1);
		//asset.setCurrentVersion(currentVersion );
		asset.setContainerType("ct1");
		asset.setContainerId(1);
		List<Category> categories = new ArrayList<Category>();
		Category cate1 = new Category();
		cate1.setId(1);
		categories.add(cate1 );
		asset.setCategories(categories );
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAsset(1, asset);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		long assetId = asset.getId();
		assertTrue(assetId > 0);
		Asset got = impl.getAsset(assetId);
		assertEquals(got.getName(), asset.getName());
		assertEquals(got.getId(), asset.getId());
		assertEquals(got.getContainerId(), asset.getContainerId());
		assertEquals(got.getContainerType(), asset.getContainerType());
		assertEquals(got.getCurrentVersion(), null);
		assertEquals(got.getCategories().size(), 1);
		assertEquals(got.getCategories().get(0).getId(), 1);
    }
    
    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#createAsset() and getAsset()} with non-null current version.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_createAsset_withCurrentVersion() throws Exception {
		Asset asset = new Asset();
		asset.setName("assertcreated");
		AssetVersion currentVersion = new AssetVersion();
		currentVersion.setId(1);
		
		asset.setCurrentVersion(currentVersion );
		asset.setContainerType("ct1");
		asset.setContainerId(1);
		List<Category> categories = new ArrayList<Category>();
		Category cate1 = new Category();
		cate1.setId(1);
		categories.add(cate1 );
		asset.setCategories(categories );
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAsset(1, asset);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		currentVersion.setAssetId(asset.getId());
		long assetId = asset.getId();
		assertTrue(assetId > 0);
		Asset got = impl.getAsset(assetId);
		assertEquals(got.getName(), asset.getName());
		assertEquals(got.getId(), asset.getId());
		assertEquals(got.getContainerId(), asset.getContainerId());
		assertEquals(got.getContainerType(), asset.getContainerType());
		assertEquals(got.getCurrentVersion().getId(), asset.getCurrentVersion().getId());
		
		//the asset version's asset id should be updated
		assertEquals(got.getCurrentVersion().getAssetId(), asset.getId());
		
		assertEquals(got.getCategories().size(), 1);
		assertEquals(got.getCategories().get(0).getId(), 1);
    }
    


    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#getAsset()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getAsset1() throws Exception {
		Asset asset = new Asset();
		asset.setName("assertcreated");
		AssetVersion currentVersion = new AssetVersion();
		currentVersion.setId(1);
		//asset.setCurrentVersion(currentVersion );
		asset.setContainerType("ct1");
		asset.setContainerId(1);
		List<Category> categories = new ArrayList<Category>();
		Category cate1 = new Category();
		cate1.setId(1);
		categories.add(cate1 );
		asset.setCategories(categories );
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAsset(1, asset);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		long assetId = asset.getId();
		assertTrue(assetId > 0);
		Asset got = impl.getAsset(assetId);
		assertEquals(got.getName(), asset.getName());
		assertEquals(got.getId(), asset.getId());
		assertEquals(got.getContainerId(), asset.getContainerId());
		assertEquals(got.getContainerType(), asset.getContainerType());
		assertEquals(got.getCurrentVersion(), null);
		assertEquals(got.getCategories().size(), 1);
		assertEquals(got.getCategories().get(0).getId(), 1);
    }
    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#getAsset()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getAsset2() throws Exception {
		Asset got = impl.getAsset(100000);
		assertNull(got);
    }
    
    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#updateAsset() and getAsset()} with the current version changed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_updateAsset() throws Exception {
		Asset asset = new Asset();
		asset.setName("assertcreated");
		AssetVersion currentVersion = new AssetVersion();
		currentVersion.setId(1);
		asset.setCurrentVersion(currentVersion );
		asset.setContainerType("ct1");
		asset.setContainerId(1);
		List<Category> categories = new ArrayList<Category>();
		Category cate1 = new Category();
		cate1.setId(1);
		categories.add(cate1 );
		asset.setCategories(categories );
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAsset(1, asset);
		AccuracyHelper.getConnection().getTransaction().commit();
		

		asset.setName("newassertcreated");
		AssetVersion newcurrentVersion = new AssetVersion();
		newcurrentVersion.setId(2);
		asset.setCurrentVersion(newcurrentVersion );
		asset.setContainerType("ct2");
		asset.setContainerId(2);
		categories = new ArrayList<Category>();
		Category cate2 = new Category();
		cate2.setId(2);
		categories.add(cate2);
		asset.setCategories(categories );

		AccuracyHelper.getConnection().getTransaction().begin();
		impl.updateAsset(1, asset);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		
		long assetId = asset.getId();
		newcurrentVersion.setAssetId(assetId);
		assertTrue(assetId > 0);
		Asset got = impl.getAsset(assetId);
		assertEquals(got.getName(), asset.getName());
		assertEquals(got.getId(), asset.getId());
		assertEquals(got.getContainerId(), asset.getContainerId());
		assertEquals(got.getContainerType(), asset.getContainerType());
		assertEquals(got.getCurrentVersion().getId(), asset.getCurrentVersion().getId());
		
		//the asset version's asset id should be updated
		assertEquals(got.getCurrentVersion().getAssetId(), asset.getId());
		
		assertEquals(got.getCategories().size(), 1);
		assertEquals(got.getCategories().get(0).getId(), 2);
    }
    
    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#deleteAsset()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_deleteAsset1() throws Exception {
		Asset asset = new Asset();
		asset.setName("assertcreated");
		AssetVersion currentVersion = new AssetVersion();
		currentVersion.setId(1);
		//asset.setCurrentVersion(currentVersion );
		asset.setContainerType("ct1");
		asset.setContainerId(1);
		List<Category> categories = new ArrayList<Category>();
		Category cate1 = new Category();
		cate1.setId(1);
		categories.add(cate1 );
		asset.setCategories(categories );
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAsset(1, asset);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		long assetId = asset.getId();
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.deleteAsset(1,assetId);
		AccuracyHelper.getConnection().getTransaction().commit();
		

		Asset got = impl.getAsset(assetId);
		assertNull(got);
    }
    

    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#createAssets() }.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_createAssets() throws Exception {
		Asset asset = new Asset();
		asset.setName("assertcreated");
		AssetVersion currentVersion = new AssetVersion();
		currentVersion.setId(1);
		
		asset.setCurrentVersion(currentVersion );
		asset.setContainerType("ct1");
		asset.setContainerId(1);
		List<Category> categories = new ArrayList<Category>();
		Category cate1 = new Category();
		cate1.setId(1);
		categories.add(cate1 );
		asset.setCategories(categories );
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAssets(1, Arrays.asList(asset));
		AccuracyHelper.getConnection().getTransaction().commit();
		
		currentVersion.setAssetId(asset.getId());
		long assetId = asset.getId();
		assertTrue(assetId > 0);
		Asset got = impl.getAsset(assetId);
		assertEquals(got.getName(), asset.getName());
		assertEquals(got.getId(), asset.getId());
		assertEquals(got.getContainerId(), asset.getContainerId());
		assertEquals(got.getContainerType(), asset.getContainerType());
		assertEquals(got.getCurrentVersion().getId(), asset.getCurrentVersion().getId());
		
		//the asset version's asset id should be updated
		assertEquals(got.getCurrentVersion().getAssetId(), asset.getId());
		
		assertEquals(got.getCategories().size(), 1);
		assertEquals(got.getCategories().get(0).getId(), 1);
    }
    
    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#updateAssets()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_updateAssets() throws Exception {
		Asset asset = new Asset();
		asset.setName("assertcreated");
		AssetVersion currentVersion = new AssetVersion();
		currentVersion.setId(1);
		asset.setCurrentVersion(currentVersion );
		asset.setContainerType("ct1");
		asset.setContainerId(1);
		List<Category> categories = new ArrayList<Category>();
		Category cate1 = new Category();
		cate1.setId(1);
		categories.add(cate1 );
		asset.setCategories(categories );
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAsset(1, asset);
		AccuracyHelper.getConnection().getTransaction().commit();
		

		asset.setName("newassertcreated");
		AssetVersion newcurrentVersion = new AssetVersion();
		newcurrentVersion.setId(2);
		asset.setCurrentVersion(newcurrentVersion );
		asset.setContainerType("ct2");
		asset.setContainerId(2);
		categories = new ArrayList<Category>();
		Category cate2 = new Category();
		cate2.setId(2);
		categories.add(cate2);
		asset.setCategories(categories );

		AccuracyHelper.getConnection().getTransaction().begin();
		impl.updateAssets(1, Arrays.asList(asset));
		AccuracyHelper.getConnection().getTransaction().commit();
		
		
		long assetId = asset.getId();
		newcurrentVersion.setAssetId(assetId);
		assertTrue(assetId > 0);
		Asset got = impl.getAsset(assetId);
		assertEquals(got.getName(), asset.getName());
		assertEquals(got.getId(), asset.getId());
		assertEquals(got.getContainerId(), asset.getContainerId());
		assertEquals(got.getContainerType(), asset.getContainerType());
		assertEquals(got.getCurrentVersion().getId(), asset.getCurrentVersion().getId());
		
		//the asset version's asset id should be updated
		assertEquals(got.getCurrentVersion().getAssetId(), asset.getId());
		
		assertEquals(got.getCategories().size(), 1);
		assertEquals(got.getCategories().get(0).getId(), 2);
    }
    
    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#deleteAsset()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_deleteAssets1() throws Exception {
		Asset asset = new Asset();
		asset.setName("assertcreated");
		AssetVersion currentVersion = new AssetVersion();
		currentVersion.setId(1);
		//asset.setCurrentVersion(currentVersion );
		asset.setContainerType("ct1");
		asset.setContainerId(1);
		List<Category> categories = new ArrayList<Category>();
		Category cate1 = new Category();
		cate1.setId(1);
		categories.add(cate1 );
		asset.setCategories(categories );
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAsset(1, asset);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		long assetId = asset.getId();
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.deleteAssets(1,Arrays.asList(assetId));
		AccuracyHelper.getConnection().getTransaction().commit();
		

		Asset got = impl.getAsset(assetId);
		assertNull(got);
    }

}
