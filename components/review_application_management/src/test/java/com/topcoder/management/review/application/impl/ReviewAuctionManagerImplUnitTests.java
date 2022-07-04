/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.BaseUnitTests;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.ReviewAuctionCategory;
import com.topcoder.management.review.application.ReviewAuctionManagerException;
import com.topcoder.management.review.application.ReviewAuctionType;

/**
 * <p>
 * Unit tests for {@link ReviewAuctionManagerImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReviewAuctionManagerImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>ReviewAuctionManagerImpl</code> instance used in tests.
     * </p>
     */
    private ReviewAuctionManagerImpl instance;

    /**
     * <p>
     * Represents the configuration used in tests.
     * </p>
     */
    private ConfigurationObject configuration;


    /**
     * <p>
     * Represents the auction used in tests.
     * </p>
     */
    private ReviewAuction auction;

    /**
     * <p>
     * Represents the auction types used in tests.
     * </p>
     */
    private List<ReviewAuctionType> auctionTypes;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewAuctionManagerImplUnitTests.class);
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

        configuration = getConfig(ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        instance = new ReviewAuctionManagerImpl(configuration);

        auctionTypes = instance.getAuctionTypes();

        auction = new ReviewAuction();
        auction.setProjectId(100000);
        auction.setAuctionType(auctionTypes.get(0));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewAuctionManagerImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor1() {
        instance = new ReviewAuctionManagerImpl();

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'specReviewAuctionSearchBundle' should be correct.",
            getField(instance, "specReviewAuctionSearchBundle"));
        assertNotNull("'contestReviewAuctionSearchBundle' should be correct.",
            getField(instance, "contestReviewAuctionSearchBundle"));
        assertEquals("'contestReviewAuctionCategoryId' should be correct.",
            1L, getField(instance, "contestReviewAuctionCategoryId"));
        assertEquals("'specReviewAuctionCategoryId' should be correct.",
            2L, getField(instance, "specReviewAuctionCategoryId"));
        assertNull("'auctionTypes' should be correct.",
            getField(instance, "auctionTypes"));
        assertNull("'auctionCategories' should be correct.",
            getField(instance, "auctionCategories"));
        assertNull("'auctionTypesMap' should be correct.",
            getField(instance, "auctionTypesMap"));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewAuctionManagerImpl(String filePath,
     * String namespace)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor2() {
        instance = new ReviewAuctionManagerImpl(ReviewAuctionManagerImpl.DEFAULT_CONFIG_FILENAME,
            ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'specReviewAuctionSearchBundle' should be correct.",
            getField(instance, "specReviewAuctionSearchBundle"));
        assertNotNull("'contestReviewAuctionSearchBundle' should be correct.",
            getField(instance, "contestReviewAuctionSearchBundle"));
        assertEquals("'contestReviewAuctionCategoryId' should be correct.",
            1L, getField(instance, "contestReviewAuctionCategoryId"));
        assertEquals("'specReviewAuctionCategoryId' should be correct.",
            2L, getField(instance, "specReviewAuctionCategoryId"));
        assertNull("'auctionTypes' should be correct.",
            getField(instance, "auctionTypes"));
        assertNull("'auctionCategories' should be correct.",
            getField(instance, "auctionCategories"));
        assertNull("'auctionTypesMap' should be correct.",
            getField(instance, "auctionTypesMap"));
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(String filePath, String namespace)</code>
     * with filePath is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_filePathNull() {
        new ReviewAuctionManagerImpl(null, ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(String filePath, String namespace)</code>
     * with filePath is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_filePathEmpty() {
        new ReviewAuctionManagerImpl(EMPTY_STRING, ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(String filePath, String namespace)</code>
     * with namespace is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_namespaceNull() {
        new ReviewAuctionManagerImpl(ReviewAuctionManagerImpl.DEFAULT_CONFIG_FILENAME, null);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(String filePath, String namespace)</code>
     * with namespace is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_namespaceEmpty() {
        new ReviewAuctionManagerImpl(ReviewAuctionManagerImpl.DEFAULT_CONFIG_FILENAME, EMPTY_STRING);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(String filePath, String namespace)</code>
     * with filePath is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor2_filePathInvalid1() {
        new ReviewAuctionManagerImpl(TEST_FILES, ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(String filePath, String namespace)</code>
     * with filePath is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor2_filePathInvalid2() {
        new ReviewAuctionManagerImpl(TEST_FILES + "SearchBundleManager.xml",
            ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject
     * configuration)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor3_1() {
        instance = new ReviewAuctionManagerImpl(configuration);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'specReviewAuctionSearchBundle' should be correct.",
            getField(instance, "specReviewAuctionSearchBundle"));
        assertNotNull("'contestReviewAuctionSearchBundle' should be correct.",
            getField(instance, "contestReviewAuctionSearchBundle"));
        assertEquals("'contestReviewAuctionCategoryId' should be correct.",
            1L, getField(instance, "contestReviewAuctionCategoryId"));
        assertEquals("'specReviewAuctionCategoryId' should be correct.",
            2L, getField(instance, "specReviewAuctionCategoryId"));
        assertNull("'auctionTypes' should be correct.",
            getField(instance, "auctionTypes"));
        assertNull("'auctionCategories' should be correct.",
            getField(instance, "auctionCategories"));
        assertNull("'auctionTypesMap' should be correct.",
            getField(instance, "auctionTypesMap"));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject
     * configuration)</code>.<br>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test
    public void testCtor3_2() throws Exception {
        configuration.removeProperty("loggerName");

        instance = new ReviewAuctionManagerImpl(configuration);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'specReviewAuctionSearchBundle' should be correct.",
            getField(instance, "specReviewAuctionSearchBundle"));
        assertNotNull("'contestReviewAuctionSearchBundle' should be correct.",
            getField(instance, "contestReviewAuctionSearchBundle"));
        assertEquals("'contestReviewAuctionCategoryId' should be correct.",
            1L, getField(instance, "contestReviewAuctionCategoryId"));
        assertEquals("'specReviewAuctionCategoryId' should be correct.",
            2L, getField(instance, "specReviewAuctionCategoryId"));
        assertNull("'auctionTypes' should be correct.",
            getField(instance, "auctionTypes"));
        assertNull("'auctionCategories' should be correct.",
            getField(instance, "auctionCategories"));
        assertNull("'auctionTypesMap' should be correct.",
            getField(instance, "auctionTypesMap"));
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with configuration is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor3_configurationNull() {
        configuration = null;

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'searchBundleManagerNamespace' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_searchBundleManagerNamespaceMissing() throws Exception {
        configuration.removeProperty("searchBundleManagerNamespace");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'searchBundleManagerNamespace' is empty.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_searchBundleManagerNamespaceEmpty() throws Exception {
        configuration.setPropertyValue("searchBundleManagerNamespace", EMPTY_STRING);

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'searchBundleManagerNamespace' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_searchBundleManagerNamespaceInvalid() throws Exception {
        configuration.setPropertyValue("searchBundleManagerNamespace", "invalid.searchBundleManagerNamespace");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'specReviewAuctionSearchBundleName' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_specReviewAuctionSearchBundleNameMissing() throws Exception {
        configuration.removeProperty("specReviewAuctionSearchBundleName");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'specReviewAuctionSearchBundleName' is empty.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_specReviewAuctionSearchBundleNameEmpty() throws Exception {
        configuration.setPropertyValue("specReviewAuctionSearchBundleName", EMPTY_STRING);

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'specReviewAuctionSearchBundleName' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_specReviewAuctionSearchBundleNameInvalid() throws Exception {
        configuration.setPropertyValue("specReviewAuctionSearchBundleName", "specReviewAuctionSearchBundleNameInvalid");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'contestReviewAuctionSearchBundleName' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionSearchBundleNameMissing() throws Exception {
        configuration.removeProperty("contestReviewAuctionSearchBundleName");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'contestReviewAuctionSearchBundleName' is empty.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionSearchBundleNameEmpty() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionSearchBundleName", EMPTY_STRING);

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'contestReviewAuctionSearchBundleName' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionSearchBundleNameInvalid() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionSearchBundleName",
            "contestReviewAuctionSearchBundleNameInvalid");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'contestReviewAuctionCategoryId' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionCategoryIdMissing() throws Exception {
        configuration.removeProperty("contestReviewAuctionCategoryId");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'contestReviewAuctionCategoryId' is empty.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionCategoryIdEmpty() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionCategoryId", EMPTY_STRING);

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'contestReviewAuctionCategoryId' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionCategoryIdInvalid() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionCategoryId", "invalid_num");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code> with
     * 'contestReviewAuctionCategoryId' is negative.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionCategoryIdNegative() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionCategoryId", "-1");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code> with
     * 'contestReviewAuctionCategoryId' is zero.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_contestReviewAuctionCategoryIdZero() throws Exception {
        configuration.setPropertyValue("contestReviewAuctionCategoryId", "0");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'specReviewAuctionCategoryId' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_specReviewAuctionCategoryIdMissing() throws Exception {
        configuration.removeProperty("specReviewAuctionCategoryId");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'specReviewAuctionCategoryId' is empty.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_specReviewAuctionCategoryIdEmpty() throws Exception {
        configuration.setPropertyValue("specReviewAuctionCategoryId", EMPTY_STRING);

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'specReviewAuctionCategoryId' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_specReviewAuctionCategoryIdInvalid() throws Exception {
        configuration.setPropertyValue("specReviewAuctionCategoryId", "invalid_num");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code> with
     * 'specReviewAuctionCategoryId' is negative.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_specReviewAuctionCategoryIdNegative() throws Exception {
        configuration.setPropertyValue("specReviewAuctionCategoryId", "-1");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code> with
     * 'specReviewAuctionCategoryId' is zero.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_specReviewAuctionCategoryIdZero() throws Exception {
        configuration.setPropertyValue("specReviewAuctionCategoryId", "0");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'objectFactoryConfig' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_objectFactoryConfigMissing() throws Exception {
        configuration.removeChild("objectFactoryConfig");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'objectFactoryConfig' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_objectFactoryConfigInvalid() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseReviewAuctionPersistence")
            .setPropertyValue("type", "invalid_class");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'persistenceKey' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_persistenceKeyMissing() throws Exception {
        configuration.removeProperty("persistenceKey");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'persistenceKey' is empty.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_persistenceKeyEmpty() throws Exception {
        configuration.setPropertyValue("persistenceKey", EMPTY_STRING);

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with 'persistenceConfig' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_persistenceConfigMissing() throws Exception {
        configuration.removeChild("persistenceConfig");

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with failed to create an instance of LateDeliverablePersistence.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_CreateLateDeliverablePersistenceFailed1() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseReviewAuctionPersistence")
            .setPropertyValue("type", Integer.class.getName());

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject configuration)</code>
     * with failed to create an instance of LateDeliverablePersistence.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_CreateLateDeliverablePersistenceFailed2() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseReviewAuctionPersistence")
            .setPropertyValue("type", String.class.getName());

        new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createAuction(ReviewAuction auction)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createAuction() throws Exception {
        ReviewAuction res = instance.createAuction(auction);

        assertSame("'createAuction' should be correct.", auction, res);
        assertTrue("'createAuction' should be correct.", res.getId() > 0);

        List<List<Object>> list = executeQuery(getConnection(), 3, "SELECT review_auction_id, project_id,"
            + " review_auction_type_id FROM review_auction");

        assertEquals("'createAuction' should be correct.", 1, list.size());
        checkResult("createAuction", list.get(0), res.getId(), auction.getProjectId(),
            auction.getAuctionType().getId());
    }

    /**
     * <p>
     * Failure test for the method <code>createAuction(ReviewAuction auction)</code>
     * with auction is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createAuction_auctionNull() throws Exception {
        auction = null;

        instance.createAuction(auction);
    }

    /**
     * <p>
     * Failure test for the method <code>createAuction(ReviewAuction auction)</code>
     * with auction.getAuctionType() is null.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_createAuction_auctionAuctionTypeNull() throws Exception {
        auction.setAuctionType(null);

        instance.createAuction(auction);
    }

    /**
     * <p>
     * Failure test for the method <code>createAuction(ReviewAuction auction)</code>
     * with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_createAuction_Error1() throws Exception {
        ReviewAuctionType auctionType = auction.getAuctionType();
        auction.setAuctionType(new ReviewAuctionType(Long.MAX_VALUE, auctionType.getName(), auctionType
            .getAuctionCategory(), auctionType.getApplicationRoles()));

        instance.createAuction(auction);
    }

    /**
     * <p>
     * Failure test for the method <code>createAuction(ReviewAuction auction)</code>
     * with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_createAuction_Error2() throws Exception {
        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.createAuction(auction);
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchOpenAuctions(long auctionCategoryId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchOpenAuctions1_1() throws Exception {
        instance.createAuction(auction);

        List<ReviewAuction> res = instance.searchOpenAuctions(1);

        assertEquals("'searchOpenAuctions' should be correct.", 1, res.size());

        ReviewAuction reviewAuction = res.get(0);
        assertEquals("'searchOpenAuctions' should be correct.",
            auction.getId(), reviewAuction.getId());
        assertEquals("'searchOpenAuctions' should be correct.",
            auction.getProjectId(), reviewAuction.getProjectId());
        assertEquals("'searchOpenAuctions' should be correct.",
            auction.getAuctionType().getId(), reviewAuction.getAuctionType().getId());

        assertTrue("'searchOpenAuctions' should be correct.",
            reviewAuction.isOpen());
        List<Long> openPositions = reviewAuction.getOpenPositions();
        assertEquals("'searchOpenAuctions' should be correct.",
            2, openPositions.size());
        assertEquals("'searchOpenAuctions' should be correct.",
            0L, openPositions.get(0).longValue());
        assertEquals("'searchOpenAuctions' should be correct.",
            2L, openPositions.get(1).longValue());
        assertNotNull("'searchOpenAuctions' should be correct.",
            reviewAuction.getAssignmentDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchOpenAuctions(long auctionCategoryId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchOpenAuctions1_2() throws Exception {
        auction = new ReviewAuction();
        auction.setProjectId(100001);
        auction.setAuctionType(auctionTypes.get(2));

        instance.createAuction(auction);

        List<ReviewAuction> res = instance.searchOpenAuctions(2);

        assertEquals("'searchOpenAuctions' should be correct.", 1, res.size());

        ReviewAuction reviewAuction = res.get(0);
        assertEquals("'searchOpenAuctions' should be correct.",
            auction.getId(), reviewAuction.getId());
        assertEquals("'searchOpenAuctions' should be correct.",
            auction.getProjectId(), reviewAuction.getProjectId());
        assertEquals("'searchOpenAuctions' should be correct.",
            auction.getAuctionType().getId(), reviewAuction.getAuctionType().getId());

        assertTrue("'searchOpenAuctions' should be correct.",
            reviewAuction.isOpen());
        List<Long> openPositions = reviewAuction.getOpenPositions();
        assertEquals("'searchOpenAuctions' should be correct.",
            1, openPositions.size());
        assertEquals("'searchOpenAuctions' should be correct.",
            1L, openPositions.get(0).longValue());
        assertNotNull("'searchOpenAuctions' should be correct.",
            reviewAuction.getAssignmentDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchOpenAuctions(long auctionCategoryId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchOpenAuctions1_3() throws Exception {
        instance.createAuction(auction);

        ReviewAuction auction2 = new ReviewAuction();
        auction2.setProjectId(100000);
        auction2.setAuctionType(auctionTypes.get(1));

        instance.createAuction(auction2);

        List<ReviewAuction> res = instance.searchOpenAuctions(1);

        assertEquals("'searchOpenAuctions' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchOpenAuctions(long auctionCategoryId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchOpenAuctions1_4() throws Exception {
        auction.setProjectId(100003);
        instance.createAuction(auction);

        List<ReviewAuction> res = instance.searchOpenAuctions(1);

        assertEquals("'searchOpenAuctions' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchOpenAuctions(long auctionCategoryId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchOpenAuctions1_5() throws Exception {
        clearDB();

        List<ReviewAuction> res = instance.searchOpenAuctions(1);

        assertEquals("'searchOpenAuctions' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId)</code> with auctionCategoryId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchOpenAuctions1_auctionCategoryIdNegative() throws Exception {
        instance.searchOpenAuctions(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId)</code> with auctionCategoryId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchOpenAuctions1_auctionCategoryIdZero() throws Exception {
        instance.searchOpenAuctions(0);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId)</code> with an error has
     * occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_searchOpenAuctions1_Error1() throws Exception {
        instance.searchOpenAuctions(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId)</code> with an error has
     * occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_searchOpenAuctions1_Error2() throws Exception {
        instance.createAuction(auction);

        configuration.setPropertyValue("searchBundleManagerNamespace",
            "ReviewApplicationManagement.SearchBuilderManager.Invalid1");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.searchOpenAuctions(1);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId)</code> with an error has
     * occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_searchOpenAuctions1_Error3() throws Exception {
        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.searchOpenAuctions(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchOpenAuctions2_1() throws Exception {
        instance.createAuction(auction);

        List<Long> projectCategoryIds = new ArrayList<Long>();
        projectCategoryIds.add(1L);
        List<ReviewAuction> res = instance.searchOpenAuctions(1, projectCategoryIds);

        assertEquals("'searchOpenAuctions' should be correct.", 1, res.size());

        ReviewAuction reviewAuction = res.get(0);
        assertEquals("'searchOpenAuctions' should be correct.",
            auction.getId(), reviewAuction.getId());
        assertEquals("'searchOpenAuctions' should be correct.",
            auction.getProjectId(), reviewAuction.getProjectId());
        assertEquals("'searchOpenAuctions' should be correct.",
            auction.getAuctionType().getId(), reviewAuction.getAuctionType().getId());

        assertTrue("'searchOpenAuctions' should be correct.",
            reviewAuction.isOpen());
        List<Long> openPositions = reviewAuction.getOpenPositions();
        assertEquals("'searchOpenAuctions' should be correct.",
            2, openPositions.size());
        assertEquals("'searchOpenAuctions' should be correct.",
            0L, openPositions.get(0).longValue());
        assertEquals("'searchOpenAuctions' should be correct.",
            2L, openPositions.get(1).longValue());
        assertNotNull("'searchOpenAuctions' should be correct.",
            reviewAuction.getAssignmentDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchOpenAuctions2_2() throws Exception {
        instance.createAuction(auction);

        ReviewAuction auction2 = new ReviewAuction();
        auction2.setProjectId(100000);
        auction2.setAuctionType(auctionTypes.get(1));

        instance.createAuction(auction2);

        List<Long> projectCategoryIds = new ArrayList<Long>();
        projectCategoryIds.add(1L);
        List<ReviewAuction> res = instance.searchOpenAuctions(1, projectCategoryIds);

        assertEquals("'searchOpenAuctions' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchOpenAuctions2_3() throws Exception {
        clearDB();

        List<Long> projectCategoryIds = null;
        List<ReviewAuction> res = instance.searchOpenAuctions(1, projectCategoryIds);

        assertEquals("'searchOpenAuctions' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with auctionCategoryId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchOpenAuctions2_auctionCategoryIdNegative() throws Exception {
        instance.searchOpenAuctions(-1, null);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with auctionCategoryId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchOpenAuctions2_auctionCategoryIdZero() throws Exception {
        instance.searchOpenAuctions(0, null);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with projectCategoryIds is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchOpenAuctions2_projectCategoryIdsEmpty() throws Exception {
        List<Long> projectCategoryIds = new ArrayList<Long>();
        instance.searchOpenAuctions(1, projectCategoryIds);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with projectCategoryIds contains null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchOpenAuctions2_projectCategoryIdsContainsNull() throws Exception {
        List<Long> projectCategoryIds = new ArrayList<Long>();
        projectCategoryIds.add(null);
        instance.searchOpenAuctions(1, projectCategoryIds);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with projectCategoryIds contains negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchOpenAuctions2_projectCategoryIdsContainsNegative() throws Exception {
        List<Long> projectCategoryIds = new ArrayList<Long>();
        projectCategoryIds.add(-1L);
        instance.searchOpenAuctions(1, projectCategoryIds);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with projectCategoryIds contains zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_searchOpenAuctions2_projectCategoryIdsContainsZero() throws Exception {
        List<Long> projectCategoryIds = new ArrayList<Long>();
        projectCategoryIds.add(0L);
        instance.searchOpenAuctions(1, projectCategoryIds);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_searchOpenAuctions2_Error1() throws Exception {
        instance.searchOpenAuctions(Long.MAX_VALUE, null);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_searchOpenAuctions2_Error2() throws Exception {
        instance.createAuction(auction);

        configuration.setPropertyValue("searchBundleManagerNamespace",
            "ReviewApplicationManagement.SearchBuilderManager.Invalid1");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.searchOpenAuctions(1, null);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_searchOpenAuctions2_Error3() throws Exception {
        instance.createAuction(auction);

        configuration.setPropertyValue("searchBundleManagerNamespace",
            "ReviewApplicationManagement.SearchBuilderManager.Invalid1");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.searchOpenAuctions(2, null);
    }

    /**
     * <p>
     * Failure test for the method <code>searchOpenAuctions(long auctionCategoryId,
     * List&lt;Long&gt; projectCategoryIds)</code> with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_searchOpenAuctions2_Error4() throws Exception {
        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.searchOpenAuctions(1, null);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuction(long auctionId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuction_1() throws Exception {
        instance.createAuction(auction);
        ReviewAuction res = instance.getAuction(auction.getId());

        assertEquals("'getAuction' should be correct.",
            auction.getId(), res.getId());
        assertEquals("'getAuction' should be correct.",
            auction.getProjectId(), res.getProjectId());
        assertEquals("'getAuction' should be correct.",
            auction.getAuctionType().getId(), res.getAuctionType().getId());

        assertTrue("'searchOpenAuctions' should be correct.",
            res.isOpen());
        List<Long> openPositions = res.getOpenPositions();
        assertEquals("'searchOpenAuctions' should be correct.",
            2, openPositions.size());
        assertEquals("'searchOpenAuctions' should be correct.",
            0L, openPositions.get(0).longValue());
        assertEquals("'searchOpenAuctions' should be correct.",
            2L, openPositions.get(1).longValue());
        assertNotNull("'searchOpenAuctions' should be correct.",
            res.getAssignmentDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuction(long auctionId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuction_2() throws Exception {
        auction.setProjectId(100002);
        instance.createAuction(auction);
        ReviewAuction res = instance.getAuction(auction.getId());

        assertEquals("'getAuction' should be correct.",
            auction.getId(), res.getId());
        assertEquals("'getAuction' should be correct.",
            auction.getProjectId(), res.getProjectId());
        assertEquals("'getAuction' should be correct.",
            auction.getAuctionType().getId(), res.getAuctionType().getId());

        assertFalse("'getAuction' should be correct.",
            res.isOpen());
        List<Long> openPositions = res.getOpenPositions();
        assertEquals("'getAuction' should be correct.",
            2, openPositions.size());
        assertEquals("'getAuction' should be correct.",
            0L, openPositions.get(0).longValue());
        assertEquals("'getAuction' should be correct.",
            0L, openPositions.get(1).longValue());
        assertNotNull("'getAuction' should be correct.",
            res.getAssignmentDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuction(long auctionId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuction_3() throws Exception {
        auction.setProjectId(100003);
        instance.createAuction(auction);
        ReviewAuction res = instance.getAuction(auction.getId());

        assertEquals("'getAuction' should be correct.",
            auction.getId(), res.getId());
        assertEquals("'getAuction' should be correct.",
            auction.getProjectId(), res.getProjectId());
        assertEquals("'getAuction' should be correct.",
            auction.getAuctionType().getId(), res.getAuctionType().getId());

        assertFalse("'getAuction' should be correct.",
            res.isOpen());
        List<Long> openPositions = res.getOpenPositions();
        assertEquals("'getAuction' should be correct.",
            2, openPositions.size());
        assertEquals("'getAuction' should be correct.",
            0L, openPositions.get(0).longValue());
        assertEquals("'getAuction' should be correct.",
            0L, openPositions.get(1).longValue());
        assertNotNull("'getAuction' should be correct.",
            res.getAssignmentDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuction(long auctionId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuction_4() throws Exception {
        clearDB();

        ReviewAuction res = instance.getAuction(Long.MAX_VALUE);

        assertNull("'getAuction' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>getAuction(long auctionId)</code> with auctionId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAuction_auctionIdNegative() throws Exception {
        instance.getAuction(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAuction(long auctionId)</code> with auctionId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAuction_auctionIdZero() throws Exception {
        instance.getAuction(0);
    }

    /**
     * <p>
     * Failure test for the method <code>getAuction(long auctionId)</code> with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_getAuction_Error1() throws Exception {
        instance.createAuction(auction);

        configuration.setPropertyValue("searchBundleManagerNamespace",
            "ReviewApplicationManagement.SearchBuilderManager.Invalid1");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.getAuction(auction.getId());
    }

    /**
     * <p>
     * Failure test for the method <code>getAuction(long auctionId)</code> with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_getAuction_Error2() throws Exception {
        instance.createAuction(auction);

        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.getAuction(auction.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuctionTypes()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuctionTypes_1() throws Exception {
        List<ReviewAuctionType> res = instance.getAuctionTypes();

        checkAuctionTypes("getAuctionTypes", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuctionTypes()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuctionTypes_2() throws Exception {
        instance = new ReviewAuctionManagerImpl(configuration);

        clearDB();

        List<ReviewAuctionType> res = instance.getAuctionTypes();

        assertEquals("'getAuctionTypes' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionTypes()</code> with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_getAuctionTypes_Error() throws Exception {
        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.getAuctionTypes();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuctionCategories()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuctionCategories_1() throws Exception {
        List<ReviewAuctionCategory> res = instance.getAuctionCategories();

        assertEquals("'getAuctionCategories' should be correct.", 2, res.size());
        checkEntity("getAuctionCategories", res.get(0), 1, "Contest Review");
        checkEntity("getAuctionCategories", res.get(1), 2, "Specification Review");
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuctionCategories()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuctionCategories_2() throws Exception {
        instance.getAuctionCategories();

        List<ReviewAuctionCategory> res = instance.getAuctionCategories();

        assertEquals("'getAuctionCategories' should be correct.", 2, res.size());
        checkEntity("getAuctionCategories", res.get(0), 1, "Contest Review");
        checkEntity("getAuctionCategories", res.get(1), 2, "Specification Review");
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuctionCategories()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuctionCategories_3() throws Exception {
        instance = new ReviewAuctionManagerImpl(configuration);

        clearDB();

        List<ReviewAuctionCategory> res = instance.getAuctionCategories();

        assertEquals("'getAuctionCategories' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionCategories()</code> with an error has occurred.<br>
     * <code>ReviewAuctionManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionManagerException.class)
    public void test_getAuctionCategories_Error() throws Exception {
        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewAuctionManagerImpl(configuration);

        instance.getAuctionCategories();
    }
}