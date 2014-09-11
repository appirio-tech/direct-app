/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.stresstests;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.User;
import com.topcoder.asset.services.impl.AssetVersionServiceImpl;

/**
 * Stress test for AssetVersionServiceImpl.
 * 
 * @author progloco
 * @version 1.0
 * 
 */
public class AssetVersionServiceImplStressTest {
    /**
     * initialize the context.
     */
    public static final ApplicationContext APP_CONTEXT = new ClassPathXmlApplicationContext(
            "beans.xml");
    /**
     * loop times.
     */
    private static final int TIMES = 100;

    /** Instance to test. */
    private AssetVersionServiceImpl instance;

    /**
     * <p>
     * Represents the entity manager used in tests.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * Set up the test environment.
     * 
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new AssetVersionServiceImpl();
        entityManager = Persistence.createEntityManagerFactory(
                "persistenceUnit").createEntityManager();
        instance.setEntityManager(entityManager);
        instance.setImageTypes(Arrays.asList("png"));
        instance.setPreviewImageHeight(100);
        instance.setPreviewImageWidth(40);
        TestHelper.clearOutput();
        TestHelper.clearDB(entityManager);
        TestHelper.loadDB(entityManager);
    }

    /**
     * Tear down the test environment.
     * 
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        TestHelper.clearOutput();
        TestHelper.clearDB(entityManager);
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        entityManager = null;
        instance = null;
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     * 
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetVersionServiceImplStressTest.class);
    }

    /**
     * Stress test for createAssetVersion method.
     * 
     * @throws Exception to JUnit.
     */
    @Test
    public void testCreateAssetVersion() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            AssetVersion assetVersion = getAssetVersion();
            entityManager.getTransaction().begin();
            instance.createAssetVersion(1, assetVersion, null, false);
            entityManager.getTransaction().commit();
        }

        System.out.println("Create " + TIMES + " asset version cost "
                + (System.currentTimeMillis() - start) + " milliseconds");
        entityManager.clear();

    }

    /**
     * Stress test for updateAsset method.
     * 
     * @throws Exception to JUnit.
     */
    @Test
    public void testUpdateAssetVersion() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            AssetVersion assetVersion = getAssetVersion();
            entityManager.getTransaction().begin();
            instance.createAssetVersion(1, assetVersion, null, false);
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            assetVersion.setVersion("version2");
            instance.updateAssetVersion(1, assetVersion);
            entityManager.getTransaction().commit();
        }
        System.out.println("Update " + TIMES + " asset version cost "
                + (System.currentTimeMillis() - start) + " milliseconds");
        entityManager.clear();

    }

    /**
     * Stress test for deleteAsset method.
     * 
     * @throws Exception to JUnit.
     */
    @Test
    public void testDeleteAssetVersion() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            AssetVersion assetVersion = getAssetVersion();
            entityManager.getTransaction().begin();
            instance.createAssetVersion(1, assetVersion, null, false);
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            instance.deleteAssetVersion(1, assetVersion.getId());
            entityManager.getTransaction().commit();
        }
        System.out.println("Delete " + TIMES + " asset version cost "
                + (System.currentTimeMillis() - start) + " milliseconds");
        entityManager.clear();

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
        assetVersion.setFileName("img.png");
        assetVersion.setFileType("png");
        assetVersion.setFileSizeBytes(57527);
        assetVersion.setFilePath(TestHelper.TEST_FILES);

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
