/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.BaseUnitTests;
import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.ReviewApplicationManagerException;
import com.topcoder.management.review.application.ReviewApplicationStatus;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.ReviewAuctionType;
import com.topcoder.management.review.application.impl.persistence.DatabaseReviewAuctionPersistence;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * <p>
 * Unit tests for {@link ReviewApplicationManagerImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReviewApplicationManagerImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>ReviewApplicationManagerImpl</code> instance used in tests.
     * </p>
     */
    private ReviewApplicationManagerImpl instance;

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
     * Represents the auction persistence used in tests.
     * </p>
     */
    private ReviewAuctionPersistence auctionPersistence;

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
        return new JUnit4TestAdapter(ReviewApplicationManagerImplUnitTests.class);
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

        configuration = getConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        auctionPersistence = new DatabaseReviewAuctionPersistence();
        auctionPersistence.configure(configuration.getChild("persistenceConfig"));
        auctionTypes = auctionPersistence.getAuctionTypes();

        // Create Auction
        ReviewAuction auction = new ReviewAuction();
        auction.setProjectId(100000);
        auction.setAuctionType(auctionTypes.get(0));

        auction = auctionPersistence.createAuction(auction);
        long auctionId = auction.getId();

        instance = new ReviewApplicationManagerImpl(configuration);

        application = new ReviewApplication();
        application.setUserId(123);
        application.setAuctionId(auctionId);
        application.setApplicationRoleId(4);
        application.setStatus(new ReviewApplicationStatus(1, "Pending"));
        application.setCreateDate(new Date());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewApplicationManagerImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor1() {
        instance = new ReviewApplicationManagerImpl();

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'reviewApplicationSearchBundle' should be correct.",
            getField(instance, "reviewApplicationSearchBundle"));
        assertNull("'applicationStatuses' should be correct.",
            getField(instance, "applicationStatuses"));
        assertNull("'applicationStatusesMap' should be correct.",
            getField(instance, "applicationStatusesMap"));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewApplicationManagerImpl(String filePath,
     * String namespace)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor2() {
        instance = new ReviewApplicationManagerImpl(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME,
            ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'reviewApplicationSearchBundle' should be correct.",
            getField(instance, "reviewApplicationSearchBundle"));
        assertNull("'applicationStatuses' should be correct.",
            getField(instance, "applicationStatuses"));
        assertNull("'applicationStatusesMap' should be correct.",
            getField(instance, "applicationStatusesMap"));
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(String filePath, String namespace)</code>
     * with filePath is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_filePathNull() {
        new ReviewApplicationManagerImpl(null, ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(String filePath, String namespace)</code>
     * with filePath is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_filePathEmpty() {
        new ReviewApplicationManagerImpl(EMPTY_STRING, ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(String filePath, String namespace)</code>
     * with namespace is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_namespaceNull() {
        new ReviewApplicationManagerImpl(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME, null);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(String filePath, String namespace)</code>
     * with namespace is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_namespaceEmpty() {
        new ReviewApplicationManagerImpl(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME, EMPTY_STRING);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(String filePath, String namespace)</code>
     * with filePath is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor2_filePathInvalid1() {
        new ReviewApplicationManagerImpl(TEST_FILES, ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(String filePath, String namespace)</code>
     * with filePath is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor2_filePathInvalid2() {
        new ReviewApplicationManagerImpl(TEST_FILES + "SearchBundleManager.xml",
            ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject
     * configuration)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor3_1() {
        instance = new ReviewApplicationManagerImpl(configuration);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'reviewApplicationSearchBundle' should be correct.",
            getField(instance, "reviewApplicationSearchBundle"));
        assertNull("'applicationStatuses' should be correct.",
            getField(instance, "applicationStatuses"));
        assertNull("'applicationStatusesMap' should be correct.",
            getField(instance, "applicationStatusesMap"));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject
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

        instance = new ReviewApplicationManagerImpl(configuration);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'reviewApplicationSearchBundle' should be correct.",
            getField(instance, "reviewApplicationSearchBundle"));
        assertNull("'applicationStatuses' should be correct.",
            getField(instance, "applicationStatuses"));
        assertNull("'applicationStatusesMap' should be correct.",
            getField(instance, "applicationStatusesMap"));
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
     * with configuration is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor3_configurationNull() {
        configuration = null;

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
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

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
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

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
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

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
     * with 'reviewApplicationSearchBundleName' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_reviewApplicationSearchBundleNameMissing() throws Exception {
        configuration.removeProperty("reviewApplicationSearchBundleName");

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
     * with 'reviewApplicationSearchBundleName' is empty.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_reviewApplicationSearchBundleNameEmpty() throws Exception {
        configuration.setPropertyValue("reviewApplicationSearchBundleName", EMPTY_STRING);

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
     * with 'reviewApplicationSearchBundleName' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_reviewApplicationSearchBundleNameInvalid() throws Exception {
        configuration.setPropertyValue("reviewApplicationSearchBundleName", "reviewApplicationSearchBundleNameInvalid");

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
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

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
     * with 'objectFactoryConfig' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_objectFactoryConfigInvalid() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseReviewApplicationPersistence")
            .setPropertyValue("type", "invalid_class");

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
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

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
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

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
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

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
     * with failed to create an instance of LateDeliverablePersistence.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_CreateLateDeliverablePersistenceFailed1() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseReviewApplicationPersistence")
            .setPropertyValue("type", Integer.class.getName());

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ReviewApplicationManagerImpl(ConfigurationObject configuration)</code>
     * with failed to create an instance of LateDeliverablePersistence.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testCtor3_CreateLateDeliverablePersistenceFailed2() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseReviewApplicationPersistence")
            .setPropertyValue("type", String.class.getName());

        new ReviewApplicationManagerImpl(configuration);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createApplication(ReviewApplication application)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createApplication() throws Exception {
        ReviewApplication res = instance.createApplication(application);

        assertSame("'createApplication' should be correct.", application, res);
        assertTrue("'createApplication' should be correct.", res.getId() > 0);

        List<List<Object>> list = executeQuery(getConnection(), 6, "SELECT review_application_id, user_id,"
            + " review_auction_id, review_application_role_id, review_application_status_id, create_date"
            + " FROM review_application");

        assertEquals("'createApplication' should be correct.", 1, list.size());
        checkResult("createApplication", list.get(0), res.getId(), application.getUserId(), application.getAuctionId(),
            application.getApplicationRoleId(), application.getStatus().getId());
    }

    /**
     * <p>
     * Failure test for the method <code>createApplication(ReviewApplication application)</code>
     * with application is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createApplication_applicationNull() throws Exception {
        application = null;

        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>createApplication(ReviewApplication application)</code>
     * with application.getStatus() is null.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_createApplication_applicationStatusNull() throws Exception {
        application.setStatus(null);

        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>createApplication(ReviewApplication application)</code>
     * with application.getCreateDate() is null.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_createApplication_applicationCreateDateNull() throws Exception {
        application.setCreateDate(null);

        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>createApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_createApplication_Error1() throws Exception {
        application.setAuctionId(Long.MAX_VALUE);

        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>createApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_createApplication_Error2() throws Exception {
        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewApplicationManagerImpl(configuration);

        instance.createApplication(application);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateApplication(ReviewApplication application)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_updateApplication() throws Exception {
        application = instance.createApplication(application);

        ReviewAuction auction = new ReviewAuction();
        auction.setProjectId(100000);
        auction.setAuctionType(auctionTypes.get(1));

        auction = auctionPersistence.createAuction(auction);
        long auctionId = auction.getId();

        application.setUserId(456);
        application.setAuctionId(auctionId);
        application.setApplicationRoleId(5);
        application.setStatus(new ReviewApplicationStatus(2, "Cancelled"));
        application.setCreateDate(new Date());

        instance.updateApplication(application);

        List<List<Object>> list = executeQuery(getConnection(), 6, "SELECT review_application_id, user_id,"
            + " review_auction_id, review_application_role_id, review_application_status_id, create_date"
            + " FROM review_application");

        assertEquals("'updateApplication' should be correct.", 1, list.size());
        checkResult("updateApplication", list.get(0), application.getId(), application.getUserId(),
            application.getAuctionId(), application.getApplicationRoleId(), application.getStatus().getId());
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with application is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateApplication_applicationNull() throws Exception {
        application = null;

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with application.getStatus() is null.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_updateApplication_applicationStatusNull() throws Exception {
        application.setStatus(null);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with application.getCreateDate() is null.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_updateApplication_applicationCreateDateNull() throws Exception {
        application.setCreateDate(null);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with the application doesn't exist.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_updateApplication_applicationNotExist() throws Exception {
        application.setId(Long.MAX_VALUE);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_updateApplication_Error1() throws Exception {
        application = instance.createApplication(application);

        application.setAuctionId(Long.MAX_VALUE);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_updateApplication_Error2() throws Exception {
        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewApplicationManagerImpl(configuration);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchApplications_1() throws Exception {
        application = instance.createApplication(application);

        Filter filter = null;
        List<ReviewApplication> res = instance.searchApplications(filter);

        assertEquals("'searchApplications' should be correct.", 1, res.size());
        ReviewApplication reviewApplication = res.get(0);
        assertEquals("'searchApplications' should be correct.",
            application.getId(), reviewApplication.getId());
        assertEquals("'searchApplications' should be correct.",
            application.getUserId(), reviewApplication.getUserId());
        assertEquals("'searchApplications' should be correct.",
            application.getAuctionId(), reviewApplication.getAuctionId());
        assertEquals("'searchApplications' should be correct.",
            application.getApplicationRoleId(), reviewApplication.getApplicationRoleId());
        assertEquals("'searchApplications' should be correct.",
            application.getStatus().getId(), reviewApplication.getStatus().getId());
        assertNotNull("'searchApplications' should be correct.",
            reviewApplication.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchApplications_2() throws Exception {
        application = instance.createApplication(application);

        Filter filter = new EqualToFilter("id", application.getId());
        List<ReviewApplication> res = instance.searchApplications(filter);

        assertEquals("'searchApplications' should be correct.", 1, res.size());
        ReviewApplication reviewApplication = res.get(0);
        assertEquals("'searchApplications' should be correct.",
            application.getId(), reviewApplication.getId());
        assertEquals("'searchApplications' should be correct.",
            application.getUserId(), reviewApplication.getUserId());
        assertEquals("'searchApplications' should be correct.",
            application.getAuctionId(), reviewApplication.getAuctionId());
        assertEquals("'searchApplications' should be correct.",
            application.getApplicationRoleId(), reviewApplication.getApplicationRoleId());
        assertEquals("'searchApplications' should be correct.",
            application.getStatus().getId(), reviewApplication.getStatus().getId());
        assertNotNull("'searchApplications' should be correct.",
            reviewApplication.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchApplications_3() throws Exception {
        application = instance.createApplication(application);

        Filter filter = new EqualToFilter("userId", application.getUserId());
        List<ReviewApplication> res = instance.searchApplications(filter);

        assertEquals("'searchApplications' should be correct.", 1, res.size());
        ReviewApplication reviewApplication = res.get(0);
        assertEquals("'searchApplications' should be correct.",
            application.getId(), reviewApplication.getId());
        assertEquals("'searchApplications' should be correct.",
            application.getUserId(), reviewApplication.getUserId());
        assertEquals("'searchApplications' should be correct.",
            application.getAuctionId(), reviewApplication.getAuctionId());
        assertEquals("'searchApplications' should be correct.",
            application.getApplicationRoleId(), reviewApplication.getApplicationRoleId());
        assertEquals("'searchApplications' should be correct.",
            application.getStatus().getId(), reviewApplication.getStatus().getId());
        assertNotNull("'searchApplications' should be correct.",
            reviewApplication.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchApplications_4() throws Exception {
        application = instance.createApplication(application);

        Filter filter = new EqualToFilter("auctionId", application.getAuctionId());
        List<ReviewApplication> res = instance.searchApplications(filter);

        assertEquals("'searchApplications' should be correct.", 1, res.size());
        ReviewApplication reviewApplication = res.get(0);
        assertEquals("'searchApplications' should be correct.",
            application.getId(), reviewApplication.getId());
        assertEquals("'searchApplications' should be correct.",
            application.getUserId(), reviewApplication.getUserId());
        assertEquals("'searchApplications' should be correct.",
            application.getAuctionId(), reviewApplication.getAuctionId());
        assertEquals("'searchApplications' should be correct.",
            application.getApplicationRoleId(), reviewApplication.getApplicationRoleId());
        assertEquals("'searchApplications' should be correct.",
            application.getStatus().getId(), reviewApplication.getStatus().getId());
        assertNotNull("'searchApplications' should be correct.",
            reviewApplication.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchApplications_5() throws Exception {
        application = instance.createApplication(application);

        Filter filter = new EqualToFilter("applicationRoleId", application.getApplicationRoleId());
        List<ReviewApplication> res = instance.searchApplications(filter);

        assertEquals("'searchApplications' should be correct.", 1, res.size());
        ReviewApplication reviewApplication = res.get(0);
        assertEquals("'searchApplications' should be correct.",
            application.getId(), reviewApplication.getId());
        assertEquals("'searchApplications' should be correct.",
            application.getUserId(), reviewApplication.getUserId());
        assertEquals("'searchApplications' should be correct.",
            application.getAuctionId(), reviewApplication.getAuctionId());
        assertEquals("'searchApplications' should be correct.",
            application.getApplicationRoleId(), reviewApplication.getApplicationRoleId());
        assertEquals("'searchApplications' should be correct.",
            application.getStatus().getId(), reviewApplication.getStatus().getId());
        assertNotNull("'searchApplications' should be correct.",
            reviewApplication.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchApplications_6() throws Exception {
        application = instance.createApplication(application);

        Filter filter = new EqualToFilter("statusId", application.getStatus().getId());
        List<ReviewApplication> res = instance.searchApplications(filter);

        assertEquals("'searchApplications' should be correct.", 1, res.size());
        ReviewApplication reviewApplication = res.get(0);
        assertEquals("'searchApplications' should be correct.",
            application.getId(), reviewApplication.getId());
        assertEquals("'searchApplications' should be correct.",
            application.getUserId(), reviewApplication.getUserId());
        assertEquals("'searchApplications' should be correct.",
            application.getAuctionId(), reviewApplication.getAuctionId());
        assertEquals("'searchApplications' should be correct.",
            application.getApplicationRoleId(), reviewApplication.getApplicationRoleId());
        assertEquals("'searchApplications' should be correct.",
            application.getStatus().getId(), reviewApplication.getStatus().getId());
        assertNotNull("'searchApplications' should be correct.",
            reviewApplication.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchApplications_7() throws Exception {
        application = instance.createApplication(application);

        Filter filter = new EqualToFilter("createDate", new Timestamp(application.getCreateDate().getTime()));
        List<ReviewApplication> res = instance.searchApplications(filter);

        assertEquals("'searchApplications' should be correct.", 1, res.size());
        ReviewApplication reviewApplication = res.get(0);
        assertEquals("'searchApplications' should be correct.",
            application.getId(), reviewApplication.getId());
        assertEquals("'searchApplications' should be correct.",
            application.getUserId(), reviewApplication.getUserId());
        assertEquals("'searchApplications' should be correct.",
            application.getAuctionId(), reviewApplication.getAuctionId());
        assertEquals("'searchApplications' should be correct.",
            application.getApplicationRoleId(), reviewApplication.getApplicationRoleId());
        assertEquals("'searchApplications' should be correct.",
            application.getStatus().getId(), reviewApplication.getStatus().getId());
        assertNotNull("'searchApplications' should be correct.",
            reviewApplication.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_searchApplications_8() throws Exception {
        clearDB();

        Filter filter = null;
        List<ReviewApplication> res = instance.searchApplications(filter);

        assertEquals("'searchApplications' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>searchApplications(Filter filter)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_searchApplications_Error1() throws Exception {
        Filter filter = new NotFilter(new NullFilter("invalid_clomn"));
        instance.searchApplications(filter);
    }

    /**
     * <p>
     * Failure test for the method <code>searchApplications(Filter filter)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_searchApplications_Error2() throws Exception {
        instance.createApplication(application);

        configuration.setPropertyValue("searchBundleManagerNamespace",
            "ReviewApplicationManagement.SearchBuilderManager.Invalid1");
        instance = new ReviewApplicationManagerImpl(configuration);

        Filter filter = null;
        instance.searchApplications(filter);
    }

    /**
     * <p>
     * Failure test for the method <code>searchApplications(Filter filter)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_searchApplications_Error3() throws Exception {
        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewApplicationManagerImpl(configuration);

        Filter filter = null;
        instance.searchApplications(filter);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getApplicationStatuses()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getApplicationStatuses_1() throws Exception {
        List<ReviewApplicationStatus> res = instance.getApplicationStatuses();

        assertEquals("'getApplicationStatuses' should be correct.", 4, res.size());
        checkEntity("getApplicationStatuses", res.get(0), 1, "Pending");
        checkEntity("getApplicationStatuses", res.get(1), 2, "Cancelled");
        checkEntity("getApplicationStatuses", res.get(2), 3, "Approved");
        checkEntity("getApplicationStatuses", res.get(3), 4, "Rejected");
    }

    /**
     * <p>
     * Accuracy test for the method <code>getApplicationStatuses()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getApplicationStatuses_2() throws Exception {
        clearDB();

        List<ReviewApplicationStatus> res = instance.getApplicationStatuses();

        assertEquals("'getApplicationStatuses' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getApplicationStatuses()</code> with an error has occurred.<br>
     * <code>ReviewApplicationManagerException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagerException.class)
    public void test_getApplicationStatuses_Error() throws Exception {
        configuration.getChild("persistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ReviewApplicationManagerImpl(configuration);

        instance.getApplicationStatuses();
    }
}