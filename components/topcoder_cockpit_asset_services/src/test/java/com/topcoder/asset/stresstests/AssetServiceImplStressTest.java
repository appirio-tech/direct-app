/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.stresstests;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.services.impl.AssetServiceImpl;

/**
 * Stress test for AssetServiceImpl.
 * 
 * @author progloco
 * @version 1.0
 * 
 */
public class AssetServiceImplStressTest {

    /**
     * initialize the context.
     */
    public static final ApplicationContext APP_CONTEXT = new ClassPathXmlApplicationContext(
            "beans.xml");;
    /**
     * loop times.
     */
    private static final int TIMES = 100;

    /** Instance to test. */
    private AssetServiceImpl instance;

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
        instance = new AssetServiceImpl();
        entityManager = Persistence.createEntityManagerFactory(
                "persistenceUnit").createEntityManager();
        instance.setEntityManager(entityManager);
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
        return new JUnit4TestAdapter(AssetServiceImplStressTest.class);
    }

    /**
     * Stress test for createAsset method.
     * 
     * @throws Exception to JUnit.
     */
    @Test
    public void testCreateAsset() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            Asset asset = getAsset();
            asset.setCategories(null);
            entityManager.getTransaction().begin();
            instance.createAsset(1, asset);
            entityManager.getTransaction().commit();
        }
        System.out.println("Create " + TIMES + " asset cost "
                + (System.currentTimeMillis() - start) + " milliseconds");
        entityManager.clear();

    }

    /**
     * Stress test for updateAsset method.
     * 
     * @throws Exception to JUnit.
     */
    @Test
    public void testUpdateAsset() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            Asset asset = getAsset();
            asset.setCategories(null);
            entityManager.getTransaction().begin();
            instance.createAsset(1, asset);
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            asset.setName("name2");
            instance.updateAsset(1, asset);
            entityManager.getTransaction().commit();
        }
        System.out.println("Update " + TIMES + " asset cost "
                + (System.currentTimeMillis() - start) + " milliseconds");
        entityManager.clear();

    }

    /**
     * Stress test for deleteAsset method.
     * 
     * @throws Exception to JUnit.
     */
    @Test
    public void testDeleteAsset() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            Asset asset = getAsset();
            asset.setCategories(null);
            entityManager.getTransaction().begin();
            instance.createAsset(1, asset);
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            instance.deleteAsset(1, asset.getId());
            entityManager.getTransaction().commit();
        }
        System.out.println("Delete " + TIMES + " asset cost "
                + (System.currentTimeMillis() - start) + " milliseconds");
        entityManager.clear();

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
}
