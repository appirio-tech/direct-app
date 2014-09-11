/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.failuretests.impl;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.ReviewApplicationManagerException;
import com.topcoder.management.review.application.accuracytests.BaseAccuracyTests;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;
import com.topcoder.search.builder.filter.EqualToFilter;

/**
 * <p>
 * Failure test for ReviewApplicationManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewApplicationManagerImplFailureTests extends BaseAccuracyTests {
    /**
     * <p>
     * Represents the instance of ReviewApplicationManagerImpl used in test.
     * </p>
     */
    private ReviewApplicationManagerImpl instance;

    /**
     * <p>
     * Represents the invalid instance of ReviewApplicationManagerImpl used in test.
     * </p>
     */
    private ReviewApplicationManagerImpl invalidInstance;

    /**
     * <p>
     * Represents the configuration used in tests.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Represents the application used in tests.
     * </p>
     */
    private ReviewApplication application;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewApplicationManagerImplFailureTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        application = getReviewApplication(1);
        instance = new ReviewApplicationManagerImpl();

        configuration = getConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
        ConfigurationObject invalidConfiguration = (ConfigurationObject) configuration.clone();

        invalidConfiguration.getChild("persistenceConfig").setPropertyValue("connectionName", "invalid");
        invalidInstance = new ReviewApplicationManagerImpl(invalidConfiguration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(String filePath, String namespace). When filePath is null.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_FilePathIsNull() {
        new ReviewApplicationManagerImpl(null, ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(String filePath, String namespace). When filePath is empty.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_FilePathIsEmpty() {
        new ReviewApplicationManagerImpl(" ", ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(String filePath, String namespace). When namespace is null.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_NamespaceIsNull() {
        new ReviewApplicationManagerImpl(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME, null);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(String filePath, String namespace). When namespace is empty.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_NamespaceIsEmpty() {
        new ReviewApplicationManagerImpl(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME, " ");
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(String filePath, String namespace). When filePath is not existed.
     * </p>
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor2_FilePathIsNotExisted() {
        new ReviewApplicationManagerImpl(FAILURE_TEST_FILES + "notexisted",
            ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(String filePath, String namespace). When filePath is invlaid.
     * </p>
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor2_FilePathIsInvalid() {
        new ReviewApplicationManagerImpl(FAILURE_TEST_FILES + "empty.txt",
            ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(String filePath, String namespace). When namespace is not existed.
     * </p>
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor2_NamespaceIsNotExisted() {
        new ReviewApplicationManagerImpl(FAILURE_TEST_FILES + "ReviewApplicationManagement.xml", "a");
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When configuration is null.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor3_ConfigurationIsNull() {
        new ReviewApplicationManagerImpl(null);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When
     * searchBundleManagerNamespace is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SearchBundleManagerNamespaceIsMissed() throws Exception {
        configuration.removeProperty("searchBundleManagerNamespace");
        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When
     * searchBundleManagerNamespace is empty.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SearchBundleManagerNamespaceIsEmpty() throws Exception {
        configuration.setPropertyValue("searchBundleManagerNamespace", "  ");

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When
     * searchBundleManagerNamespace is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_SearchBundleManagerNamespaceIsInvalid() throws Exception {
        configuration.setPropertyValue("searchBundleManagerNamespace", "invalid");

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When
     * reviewApplicationSearchBundleName is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ReviewApplicationSearchBundleNameIsMissed() throws Exception {
        configuration.removeProperty("reviewApplicationSearchBundleName");

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When
     * reviewApplicationSearchBundleName is empty.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ReviewApplicationSearchBundleNameEmpty() throws Exception {
        configuration.setPropertyValue("reviewApplicationSearchBundleName", " ");

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When
     * reviewApplicationSearchBundleName is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ReviewApplicationSearchBundleNameIsInvalid() throws Exception {
        configuration.setPropertyValue("reviewApplicationSearchBundleName", "invalid");
        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When objectFactoryConfig is
     * missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ObjectFactoryConfigIsMidded() throws Exception {
        configuration.removeChild("objectFactoryConfig");
        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When objectFactoryConfig is
     * invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_ObjectFactoryConfigIsInvalid() throws Exception {
        configuration.removeChild("objectFactoryConfig");
        configuration.addChild(new DefaultConfigurationObject("objectFactoryConfig"));
        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When persistenceKey is missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_PersistenceKeyIsMissed() throws Exception {
        configuration.removeProperty("persistenceKey");

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When persistenceKey is empty.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_PersistenceKeyIsEmpty() throws Exception {
        configuration.setPropertyValue("persistenceKey", "  ");

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When persistenceConfig is
     * missed.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_persistenceConfigMissing() throws Exception {
        configuration.removeChild("persistenceConfig");
        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for ReviewApplicationManagerImpl(ConfigurationObject configuration). When persistenceConfig is
     * invalid.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_PersistenceConfigIsInvalid() throws Exception {
        configuration.removeChild("objectFactoryConfig");
        configuration.addChild(new DefaultConfigurationObject("objectFactoryConfig"));
        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for createApplication(ReviewApplication application). When application is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateApplication_ApplicationIsNull() throws Exception {
        instance.createApplication(null);
    }

    /**
     * <p>
     * Failure test for createApplication(ReviewApplication application). When connection is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void testCreateApplication_InvalidConnection() throws Exception {
        invalidInstance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for createApplication(ReviewApplication application). When failed to create.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void testCreateApplication_Failed() throws Exception {
        application.setAuctionId(9999);
        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for updateApplication(ReviewApplication application). When application is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateApplication_ApplicationIsNull() throws Exception {
        instance.updateApplication(null);
    }

    /**
     * <p>
     * Failure test for updateApplication(ReviewApplication application), when failed to create connection.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void testUpdateApplication_InvalidConnection() throws Exception {
        invalidInstance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for searchApplications(Filter filter). When filter is invalid, throws
     * ReviewApplicationManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void testSearchApplications_ColumnNotExisted() throws Exception {
        instance.searchApplications(new EqualToFilter("x", 1));
    }

    /**
     * <p>
     * Failure test for searchApplications(Filter filter). When filter is invalid, throws
     * ReviewApplicationManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void testSearchApplications_ColumnValueIsInvalid() throws Exception {
        instance.searchApplications(new EqualToFilter("userId", "abc"));
    }

    /**
     * <p>
     * Failure test for searchApplications(Filter filter). when failed to create connection.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void testSearchApplications_InvalidConnection() throws Exception {
        invalidInstance.searchApplications(null);
    }

    /**
     * <p>
     * Failure test for getApplicationStatuses(), when failed to create connection.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void testGetApplicationStatuses_InvalidConnection() throws Exception {
        invalidInstance.getApplicationStatuses();
    }
}