/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetSearchCriteria;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;
import com.topcoder.asset.services.AssetVersionService;

/**
 * <p>
 * Failure test cases for AssetServiceImpl.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class AssetServiceImplFailureTests extends TestCase {
    /**
     * <p>
     * The ApplicationContext instance for testing.
     * </p>
     */
    private static ApplicationContext context = new ClassPathXmlApplicationContext("failure/beans.xml");

    /**
     * <p>
     * The AssetServiceImpl instance for testing.
     * </p>
     */
    private AssetServiceImpl instance;

    /**
     * <p>
     * The EntityManager instance for testing.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * The Asset instance for testing.
     * </p>
     */
    private Asset asset;

    /**
     * <p>
     * The AssetSearchCriteria instance for testing.
     * </p>
     */
    private AssetSearchCriteria criteria;

    /**
     * <p>
     * The Asset list for testing.
     * </p>
     */
    private List<Asset> assets;

    /**
     * <p>
     * The Long list for testing.
     * </p>
     */
    private List<Long> assetIds;
    
    /**
     * <p>
     * Represents the <code>EntityManagerFactory</code> for tests.
     * </p>
     */
    private EntityManagerFactory factory;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        factory = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = factory.createEntityManager();
        entityManager = factory.createEntityManager();

        instance = new AssetServiceImpl();

        AssetVersionService assetVersionService = new AssetVersionServiceImpl();
        instance.setAssetVersionService(assetVersionService);
        instance.setEntityManager(entityManager);

        asset = new Asset();
        List<Category> categories = new ArrayList<Category>();
        Category category = new Category();
        category.setId(800);
        categories.add(category);
        asset.setCategories(categories);
        asset.setId(20);

        criteria = new AssetSearchCriteria();
        criteria.setPage(5);
        criteria.setPageSize(10);
        criteria.setUser("user");

        assets = new ArrayList<Asset>();
        assets.add(asset);

        assetIds = new ArrayList<Long>();
        assetIds.add(8L);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        entityManager = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AssetServiceImplFailureTests.class);
    }

    /**
     * <p>
     * Tests AssetServiceImpl#createAsset(long,Asset) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAsset_ZeroUserId() throws Exception {
        try {
            instance.createAsset(0, asset);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#createAsset(long,Asset) for failure.
     * It tests the case that when asset is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAsset_NullAsset() throws Exception {
        try {
            instance.createAsset(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#createAsset(long,Asset) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAsset_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.createAsset(1, asset);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#createAsset(long,Asset) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testCreateAsset_ServiceException() {
        entityManager.close();
        try {
            instance.createAsset(1, asset);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#updateAsset(long,Asset) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAsset_ZeroUserId() throws Exception {
        try {
            instance.updateAsset(0, asset);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#updateAsset(long,Asset) for failure.
     * It tests the case that when asset is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAsset_NullAsset() throws Exception {
        try {
            instance.updateAsset(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#updateAsset(long,Asset) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAsset_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.updateAsset(1, asset);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#updateAsset(long,Asset) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testUpdateAsset_ServiceException() {
        entityManager.close();
        try {
            instance.updateAsset(1, asset);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#deleteAsset(long,long) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAsset_Failure1() throws Exception {
        try {
            instance.deleteAsset(0, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#deleteAsset(long,long) for failure.
     * It tests the case that when assetId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAsset_Failure2() throws Exception {
        try {
            instance.deleteAsset(1, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#deleteAsset(long,long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAsset_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.deleteAsset(1, 1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#deleteAsset(long,long) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testDeleteAsset_ServiceException() {
        entityManager.close();
        try {
            instance.deleteAsset(1, 1);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#getAsset(long) for failure.
     * It tests the case that when assetId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAsset_Fail() throws Exception {
        try {
            instance.getAsset(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#getAsset(long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAsset_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.getAsset(1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#getAsset(long) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testGetAsset_ServiceException() {
        entityManager.close();
        try {
            instance.getAsset(1);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#searchAssets(AssetSearchCriteria) for failure.
     * It tests the case that when criteria is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSearchAssets_NullCriteria() throws Exception {
        try {
            instance.searchAssets(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#searchAssets(AssetSearchCriteria) for failure.
     * It tests the case that when page is negative and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSearchAssets_NegativePage() throws Exception {
        criteria.setPage(-1);
        try {
            instance.searchAssets(criteria);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#searchAssets(AssetSearchCriteria) for failure.
     * It tests the case that when criteria.page &gt; 0 and criteria.pageSize &lt;=0
     * and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSearchAssets_NegativePageSize() throws Exception {
        criteria.setPageSize(-1);
        try {
            instance.searchAssets(criteria);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#searchAssets(AssetSearchCriteria) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSearchAssets_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.searchAssets(criteria);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#searchAssets(AssetSearchCriteria) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testSearchAssets_ServiceException() {
        entityManager.close();
        try {
            instance.searchAssets(criteria);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#createAssets(long,List) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAssets_ZeroId() throws Exception {
        try {
            instance.createAssets(0, assets);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#createAssets(long,List) for failure.
     * It tests the case that when assets is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAssets_NullAssets() throws Exception {
        try {
            instance.createAssets(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#createAssets(long,List) for failure.
     * It tests the case that when assets contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAssets_NullInAssets() throws Exception {
        assets.add(null);
        try {
            instance.createAssets(1, assets);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#createAssets(long,List) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testCreateAssets_ServiceException() {
        entityManager.close();
        try {
            instance.createAssets(1, assets);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#deleteAssets(long,List) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAssets_ZeroId() throws Exception {
        try {
            instance.deleteAssets(0, assetIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#deleteAssets(long,List) for failure.
     * It tests the case that when assetIds is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAssets_NullAssetIds() throws Exception {
        try {
            instance.deleteAssets(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#deleteAssets(long,List) for failure.
     * It tests the case that when assetIds contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAssets_NullInAssetIds() throws Exception {
        assetIds.add(null);
        try {
            instance.deleteAssets(1, assetIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#deleteAssets(long,List) for failure.
     * It tests the case that when assetIds contains negative element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAssets_NegativeInAssetIds() throws Exception {
        assetIds.add(-1L);
        try {
            instance.deleteAssets(1, assetIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#deleteAssets(long,List) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testDeleteAssets_ServiceException() {
        entityManager.close();
        try {
            instance.deleteAssets(1, assetIds);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#updateAssets(long,List) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssets_ZeroId() throws Exception {
        try {
            instance.updateAssets(0, assets);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#updateAssets(long,List) for failure.
     * It tests the case that when assets is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssets_NullAssets() throws Exception {
        try {
            instance.updateAssets(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#updateAssets(long,List) for failure.
     * It tests the case that when assets contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssets_NullInAssets() throws Exception {
        assets.add(null);
        try {
            instance.updateAssets(1, assets);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#updateAssets(long,List) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testUpdateAssets_ServiceException() {
        entityManager.close();
        try {
            instance.updateAssets(1, assets);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetServiceImpl#checkInit() for failure.
     * It tests the case that when assetVersionService is null and expects AssetConfigurationException.
     * </p>
     */
    public void testCheckInit_Failure() {
        instance.setAssetVersionService(null);
        try {
            instance.checkInit();
            fail("AssetConfigurationException expected.");
        } catch (AssetConfigurationException e) {
            //good
        }
    }

}