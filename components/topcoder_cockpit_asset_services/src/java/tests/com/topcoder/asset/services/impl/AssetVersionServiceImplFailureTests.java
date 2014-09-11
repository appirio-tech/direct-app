/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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

import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;

/**
 * <p>
 * Failure test cases for AssetVersionServiceImpl.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class AssetVersionServiceImplFailureTests extends TestCase {
    
    /**
     * <p>
     * The ApplicationContext instance for testing.
     * </p>
     */
    private static ApplicationContext context = new ClassPathXmlApplicationContext("failure/beans.xml");

    /**
     * <p>
     * The AssetVersionServiceImpl instance for testing.
     * </p>
     */
    private AssetVersionServiceImpl instance;

    /**
     * <p>
     * The EntityManager instance for testing.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * The AssetVersion instance for testing.
     * </p>
     */
    private AssetVersion assetVersion;

    /**
     * <p>
     * The AssetVersion list for testing.
     * </p>
     */
    private List<AssetVersion> assetVersions;

    /**
     * <p>
     * The Long list for testing.
     * </p>
     */
    private List<Long> assetVersionIds;

    /**
     * <p>
     * The String list for testing.
     * </p>
     */
    private List<String> imageTypes;

    /**
     * <p>
     * The OutputStream instance for testing.
     * </p>
     */
    private OutputStream output;
    
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

        instance = new AssetVersionServiceImpl();

        imageTypes = new ArrayList<String>();
        imageTypes.add("jpg");

        instance.setBasePath("test_files");
        instance.setEntityManager(entityManager);
        instance.setImageTypes(imageTypes);
        instance.setPreviewImageHeight(8);
        instance.setPreviewImageWidth(10);

        assetVersion = new AssetVersion();
        assetVersion.setAssetId(20);
        assetVersion.setId(28);
        assetVersion.setFilePath("filePath");

        assetVersions = new ArrayList<AssetVersion>();
        assetVersions.add(assetVersion);

        assetVersionIds = new ArrayList<Long>();
        assetVersionIds.add(5L);

        output = new ByteArrayOutputStream();
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
        factory.close();
        factory = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AssetVersionServiceImplFailureTests.class);
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#deleteAssetVersion(long,long) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAssetVersion_ZerouserId() throws Exception {
        try {
            instance.deleteAssetVersion(0, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#deleteAssetVersion(long,long) for failure.
     * It tests the case that when assetVersionId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAssetVersion_ZeroassetVersionId() throws Exception {
        try {
            instance.deleteAssetVersion(1, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#deleteAssetVersion(long,long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDeleteAssetVersion_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.deleteAssetVersion(1, 1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#deleteAssetVersion(long,long) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testDeleteAssetVersion_ServiceException() {
        entityManager.close();
        try {
            instance.deleteAssetVersion(1, 1);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#createAssetVersion(long,AssetVersion,File,boolean) for failure.
     * It tests the case that when assetVersion is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAssetVersion_NullAssetVersion() throws Exception {
        try {
            instance.createAssetVersion(1, null, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#createAssetVersion(long,AssetVersion,File,boolean) for failure.
     * It tests the case that when assetVersion.assetId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAssetVersion_ZeroAssetID() throws Exception {
        assetVersion.setAssetId(0);
        try {
            instance.createAssetVersion(1, assetVersion, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#createAssetVersion(long,AssetVersion,File,boolean) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAssetVersion_ZeroId() throws Exception {
        try {
            instance.createAssetVersion(0, assetVersion, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#createAssetVersion(long,AssetVersion,File,boolean) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAssetVersion_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.createAssetVersion(1, assetVersion, null, true);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#createAssetVersion(long,AssetVersion,File,boolean) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testCreateAssetVersion_ServiceException() {
        entityManager.close();
        try {
            instance.createAssetVersion(1, assetVersion, null, true);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersion(long,AssetVersion) for failure.
     * It tests the case that when assetVersion is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersion_NullAssetVersion() throws Exception {
        try {
            instance.updateAssetVersion(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersion(long,AssetVersion) for failure.
     * It tests the case that when assetVersion.id is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersion_ZeroAssetVersionID() throws Exception {
        assetVersion.setId(0);
        try {
            instance.updateAssetVersion(1, assetVersion);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersion(long,AssetVersion) for failure.
     * It tests the case that when assetVersion.assetId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersion_ZeroAssetVersionAssetId() throws Exception {
        assetVersion.setAssetId(0);
        try {
            instance.updateAssetVersion(1, assetVersion);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersion(long,AssetVersion) for failure.
     * It tests the case that when assetVersion.filePath is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersion_NullFilePath() throws Exception {
        assetVersion.setFilePath(null);
        try {
            instance.updateAssetVersion(1, assetVersion);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersion(long,AssetVersion) for failure.
     * It tests the case that when assetVersion.filePath is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersion_EmptyFilePath() throws Exception {
        assetVersion.setFilePath(" ");
        try {
            instance.updateAssetVersion(1, assetVersion);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersion(long,AssetVersion) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersion_ZeroUserId() throws Exception {
        try {
            instance.updateAssetVersion(0, assetVersion);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersion(long,AssetVersion) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersion_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.updateAssetVersion(1, assetVersion);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersion(long,AssetVersion) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testUpdateAssetVersion_ServiceException() {
        entityManager.close();
        try {
            instance.updateAssetVersion(1, assetVersion);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#getAssetVersion(long) for failure.
     * It tests the case that when assetVersionId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAssetVersion_ZeroId() throws Exception {
        try {
            instance.getAssetVersion(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#getAssetVersion(long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAssetVersion_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.getAssetVersion(1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#getAssetVersion(long) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testGetAssetVersion_ServiceException() {
        entityManager.close();
        try {
            instance.getAssetVersion(1);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#getAssetVersionsOfAsset(long) for failure.
     * It tests the case that when assetId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAssetVersionsOfAsset_ZeroId() throws Exception {
        try {
            instance.getAssetVersionsOfAsset(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#getAssetVersionsOfAsset(long) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAssetVersionsOfAsset_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.getAssetVersionsOfAsset(1);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#getAssetVersionsOfAsset(long) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testGetAssetVersionsOfAsset_ServiceException() {
        entityManager.close();
        try {
            instance.getAssetVersionsOfAsset(1);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersions(long,List) for failure.
     * It tests the case that when userId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersions_ZerouserId() throws Exception {
        try {
            instance.updateAssetVersions(0, assetVersions);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersions(long,List) for failure.
     * It tests the case that when assetVersions is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersions_NullAssetVersions() throws Exception {
        try {
            instance.updateAssetVersions(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersions(long,List) for failure.
     * It tests the case that when assetVersions contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAssetVersions_NullInAssetVersions() throws Exception {
        assetVersions.add(null);
        try {
            instance.updateAssetVersions(1, assetVersions);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#updateAssetVersions(long,List) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testUpdateAssetVersions_ServiceException() {
        entityManager.close();
        try {
            instance.updateAssetVersions(1, assetVersions);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#batchGetAssetVersionContents(List,OutputStream) for failure.
     * It tests the case that when assetVersionIds is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testBatchGetAssetVersionContents_NullAssetVersionIds() throws Exception {
        try {
            instance.batchGetAssetVersionContents(null, output);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#batchGetAssetVersionContents(List,OutputStream) for failure.
     * It tests the case that when assetVersionIds contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testBatchGetAssetVersionContents_NullInAssetVersionIds() throws Exception {
        assetVersionIds.add(null);
        try {
            instance.batchGetAssetVersionContents(assetVersionIds, output);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#batchGetAssetVersionContents(List,OutputStream) for failure.
     * It tests the case that when output is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testBatchGetAssetVersionContents_NullOutput() throws Exception {
        try {
            instance.batchGetAssetVersionContents(assetVersionIds, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#batchGetAssetVersionContents(List,OutputStream) for failure.
     * Expects PersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testBatchGetAssetVersionContents_PersistenceException() throws Exception {
        instance.setEntityManager((EntityManager) context.getBean("entityManagerInvalid"));
        try {
            instance.batchGetAssetVersionContents(assetVersionIds, output);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#batchGetAssetVersionContents(List,OutputStream) for failure.
     * Expects ServiceException.
     * </p>
     */
    public void testBatchGetAssetVersionContents_ServiceException() {
        entityManager.close();
        try {
            instance.batchGetAssetVersionContents(assetVersionIds, output);
            fail("ServiceException expected.");
        } catch (ServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#checkInit() for failure.
     * It tests the case that when basePath is null and expects AssetConfigurationException.
     * </p>
     */
    public void testCheckInit_Failure1() {
        instance.setBasePath(null);
        try {
            instance.checkInit();
            fail("AssetConfigurationException expected.");
        } catch (AssetConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#checkInit() for failure.
     * It tests the case that when basePath is empty and expects AssetConfigurationException.
     * </p>
     */
    public void testCheckInit_Failure2() {
        instance.setBasePath(" ");
        try {
            instance.checkInit();
            fail("AssetConfigurationException expected.");
        } catch (AssetConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#checkInit() for failure.
     * It tests the case that when imageTypes is null and expects AssetConfigurationException.
     * </p>
     */
    public void testCheckInit_Failure3() {
        instance.setImageTypes(null);
        try {
            instance.checkInit();
            fail("AssetConfigurationException expected.");
        } catch (AssetConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#checkInit() for failure.
     * It tests the case that when imageTypes contains null and expects AssetConfigurationException.
     * </p>
     */
    public void testCheckInit_Failure4() {
        imageTypes.add(null);
        instance.setImageTypes(imageTypes);
        try {
            instance.checkInit();
            fail("AssetConfigurationException expected.");
        } catch (AssetConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#checkInit() for failure.
     * It tests the case that when imageTypes contains empty and expects AssetConfigurationException.
     * </p>
     */
    public void testCheckInit_Failure5() {
        imageTypes.add(" ");
        instance.setImageTypes(imageTypes);
        try {
            instance.checkInit();
            fail("AssetConfigurationException expected.");
        } catch (AssetConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#checkInit() for failure.
     * It tests the case that when previewImageHeight is zero and expects AssetConfigurationException.
     * </p>
     */
    public void testCheckInit_Failure6() {
        instance.setPreviewImageHeight(0);
        try {
            instance.checkInit();
            fail("AssetConfigurationException expected.");
        } catch (AssetConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AssetVersionServiceImpl#checkInit() for failure.
     * It tests the case that when previewImageWidth is zero and expects AssetConfigurationException.
     * </p>
     */
    public void testCheckInit_Failure7() {
        instance.setPreviewImageWidth(0);
        try {
            instance.checkInit();
            fail("AssetConfigurationException expected.");
        } catch (AssetConfigurationException e) {
            //good
        }
    }

}