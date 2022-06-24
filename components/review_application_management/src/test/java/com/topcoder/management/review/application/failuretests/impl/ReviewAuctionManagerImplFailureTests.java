/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.failuretests.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.ReviewAuctionManagerException;
import com.topcoder.management.review.application.accuracytests.BaseAccuracyTests;
import com.topcoder.management.review.application.impl.ReviewAuctionManagerImpl;

/**
 * <p>
 * Failure test for ReviewAuctionManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewAuctionManagerImplFailureTests extends BaseAccuracyTests {
    /**
     * <p>
     * Represents the instance of ReviewAuctionManagerImpl used in test.
     * </p>
     */
    private ReviewAuctionManagerImpl instance;

    /**
     * <p>
     * Represents the invalid instance of ReviewApplicationManagerImpl used in test.
     * </p>
     */
    private ReviewAuctionManagerImpl invalidInstance;

    /**
     * <p>
     * Represents the configuration used in tests.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewAuctionManagerImplFailureTests.class);
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
        instance = new ReviewAuctionManagerImpl();

        configuration = getConfig(ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
        ConfigurationObject invalidConfiguration = (ConfigurationObject) configuration.clone();

        invalidConfiguration.getChild("persistenceConfig").setPropertyValue("connectionName", "invalid");
        invalidInstance = new ReviewAuctionManagerImpl(invalidConfiguration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(String filePath, String namespace). When filePath is null.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_FilePathIsNull() {
        new ReviewAuctionManagerImpl(null, ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(String filePath, String namespace). When filePath is empty.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_FilePathIsEmpty() {
        new ReviewAuctionManagerImpl(" ", ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(String filePath, String namespace). When namespace is null.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_NamespaceIsNull() {
        new ReviewAuctionManagerImpl(ReviewAuctionManagerImpl.DEFAULT_CONFIG_FILENAME, null);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(String filePath, String namespace). When namespace is empty.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_NamespaceIsEmpty() {
        new ReviewAuctionManagerImpl(ReviewAuctionManagerImpl.DEFAULT_CONFIG_FILENAME, " ");
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(String filePath, String namespace). When filePath is not existed.
     * </p>
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor2_filePathIsNotExisted() {
        new ReviewAuctionManagerImpl(FAILURE_TEST_FILES + "notexisted",
            ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When configuration is null.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor3_ConfigurationIsNull() {
        new ReviewAuctionManagerImpl(null);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When searchBundleManagerNamespace
     * is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SearchBundleManagerNamespaceIsMissed() throws Exception {
        configuration.removeProperty("searchBundleManagerNamespace");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When searchBundleManagerNamespace
     * is empty.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SearchBundleManagerNamespaceIsEmpty() throws Exception {
        configuration.setPropertyValue("searchBundleManagerNamespace", " ");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When
     * specReviewAuctionSearchBundleName is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SpecReviewAuctionSearchBundleNameIsMissecd() throws Exception {
        configuration.removeProperty("specReviewAuctionSearchBundleName");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When
     * specReviewAuctionSearchBundleName is empty.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SpecReviewAuctionSearchBundleNameIsEmpty() throws Exception {
        configuration.setPropertyValue("specReviewAuctionSearchBundleName", " ");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When
     * contestReviewAuctionSearchBundleName is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ContestReviewAuctionSearchBundleNameIsMissed() throws Exception {
        configuration.removeProperty("contestReviewAuctionSearchBundleName");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When
     * contestReviewAuctionSearchBundleName is empty.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionSearchBundleNameIsEmpty() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionSearchBundleName", "  ");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When
     * contestReviewAuctionSearchBundleName is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ContestReviewAuctionSearchBundleNameIsInvalid() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionSearchBundleName", "invalid");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When contestReviewAuctionCategoryId
     * is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ContestReviewAuctionCategoryIdIdIsMissed() throws Exception {
        configuration.removeProperty("contestReviewAuctionCategoryId");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When contestReviewAuctionCategoryId
     * is empty.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ContestReviewAuctionCategoryIdIsEmpty() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionCategoryId", " ");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When contestReviewAuctionCategoryId
     * is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionCategoryIdIsInvalid1() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionCategoryId", "0");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When contestReviewAuctionCategoryId
     * is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionCategoryIdIsInvalid2() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionCategoryId", "a");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When specReviewAuctionCategoryId is
     * missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SpecReviewAuctionCategoryIdIsMissed() throws Exception {
        configuration.removeProperty("specReviewAuctionCategoryId");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When specReviewAuctionCategoryId is
     * empty.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SpecReviewAuctionCategoryIdIsEmpty() throws Exception {
        configuration.setPropertyValue("specReviewAuctionCategoryId", " ");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When specReviewAuctionCategoryId is
     * invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SpecReviewAuctionCategoryIdIsInvalid1() throws Exception {
        configuration.setPropertyValue("specReviewAuctionCategoryId", "0");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When specReviewAuctionCategoryId is
     * invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SpecReviewAuctionCategoryIdIsInvalid2() throws Exception {
        configuration.setPropertyValue("specReviewAuctionCategoryId", "a");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When objectFactoryConfig is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ObjectFactoryConfigIsMissed() throws Exception {
        configuration.removeChild("objectFactoryConfig");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When objectFactoryConfig is
     * invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ObjectFactoryConfigIsInvalid() throws Exception {
        configuration.addChild(new DefaultConfigurationObject("objectFactoryConfig"));
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When persistenceKey is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_PersistenceKeyIsMissed() throws Exception {
        configuration.removeProperty("persistenceKey");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When persistenceKey is empty.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_PersistenceKeyIsEmpty() throws Exception {
        configuration.setPropertyValue("persistenceKey", "  ");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When persistenceConfig is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_PersistenceConfigIsMissed() throws Exception {
        configuration.removeChild("persistenceConfig");
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewAuctionManagerImpl(ConfigurationObject configuration). When persistenceConfig is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_PersistenceConfigIsInvalid() throws Exception {
        configuration.addChild(new DefaultConfigurationObject("persistenceConfig"));
        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for createAuction(ReviewAuction auction) method. When auction is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateAuction_auctionNull() throws Exception {
        instance.createAuction(null);
    }

    /**
     * <p>
     * Failure test for createAuction(ReviewAuction auction) method. When failed to create connection.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void testCreateAuction_ConnectionFailied() throws Exception {
        invalidInstance.createAuction(getReviewAuction(1));
    }

    /**
     * <p>
     * Failure test for searchOpenAuctions(long auctionCategoryId) method. When auctionCategoryId is zero.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchOpenAuctions1_AuctionCategoryIsZero() throws Exception {
        instance.searchOpenAuctions(0);
    }

    /**
     * <p>
     * Failure test for searchOpenAuctions(long auctionCategoryId) method. When auctionCategoryId is zero.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void testSearchOpenAuctions1_ConnectionFailed() throws Exception {
        invalidInstance.searchOpenAuctions(1);
    }

    /**
     * <p>
     * Failure test for searchOpenAuctions(long auctionCategoryId, List{Long} projectCategoryIds). When
     * auctionCategoryId is zero.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchOpenAuctions_AuctionCategoryIsZero() throws Exception {
        instance.searchOpenAuctions(0, null);
    }

    /**
     * <p>
     * Failure test for searchOpenAuctions(long auctionCategoryId, List{Long} projectCategoryIds). When
     * projectCategoryIds is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchOpenAuctions_ProjectCategoryIdsIsEmpty() throws Exception {
        instance.searchOpenAuctions(1, new ArrayList<Long>());
    }

    /**
     * <p>
     * Failure test for searchOpenAuctions(long auctionCategoryId, List{Long} projectCategoryIds). When
     * projectCategoryIds is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchOpenAuctions_ProjectCategoryIdsContainsNull() throws Exception {
        List<Long> projectCategoryIds = new ArrayList<Long>();
        projectCategoryIds.add(null);
        instance.searchOpenAuctions(1, projectCategoryIds);
    }

    /**
     * <p>
     * Failure test for searchOpenAuctions(long auctionCategoryId, List{Long} projectCategoryIds). When
     * projectCategoryIds is zero.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchOpenAuctions_ProjectCategoryIdsContainsZeror() throws Exception {
        List<Long> projectCategoryIds = new ArrayList<Long>();
        projectCategoryIds.add(0L);
        instance.searchOpenAuctions(1, projectCategoryIds);
    }

    /**
     * <p>
     * Failure test for searchOpenAuctions(long auctionCategoryId, List{Long} projectCategoryIds). When connection
     * failed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void testSearchOpenAuctions_ConnectionFailed() throws Exception {
        invalidInstance.searchOpenAuctions(1, null);
    }

    /**
     * <p>
     * Failure test for getAuction(long auctionId), when auctionId is zero.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAuction_auctionIdIsZero() throws Exception {
        instance.getAuction(0);
    }

    /**
     * <p>
     * Failure test for getAuction(long auctionId), when failed to create connection.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void testGetAuction_InvalidConnection() throws Exception {
        invalidInstance.getAuction(1);
    }

    /**
     * <p>
     * Failure test for getAuctionTypes(), when failed to create connection.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void testGetAuctionTypes_InvalidConnection() throws Exception {
        invalidInstance.getAuctionTypes();
    }

    /**
     * <p>
     * Failure test for getAuctionCategories(), when failed to create connection.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void testGetAuctionCategories_InvalidConnection() throws Exception {
        invalidInstance.getAuctionCategories();
    }
}