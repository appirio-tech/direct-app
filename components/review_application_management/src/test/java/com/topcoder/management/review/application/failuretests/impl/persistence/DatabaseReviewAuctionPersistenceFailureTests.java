/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.failuretests.impl.persistence;

import java.util.HashSet;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.accuracytests.BaseAccuracyTests;
import com.topcoder.management.review.application.impl.ReviewAuctionManagerImpl;
import com.topcoder.management.review.application.impl.ReviewAuctionPersistenceException;
import com.topcoder.management.review.application.impl.persistence.DatabaseReviewAuctionPersistence;

/**
 * <p>
 * Failure test for DatabaseReviewAuctionPersistence class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DatabaseReviewAuctionPersistenceFailureTests extends BaseAccuracyTests {
    /**
     * <p>
     * Represents the instance of DatabaseReviewAuctionPersistence used in test.
     * </p>
     */
    private DatabaseReviewAuctionPersistence instance;

    /**
     * <p>
     * Represents the invalid instance of DatabaseReviewAuctionPersistence used in test.
     * </p>
     */
    private DatabaseReviewAuctionPersistence invalidInstance;

    /**
     * <p>
     * Represents Configuration Object used in test.
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
        return new JUnit4TestAdapter(DatabaseReviewAuctionPersistenceFailureTests.class);
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

        configuration = getConfig(ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE).getChild("persistenceConfig");

        instance = new DatabaseReviewAuctionPersistence();
        instance.configure(configuration);

        ConfigurationObject invalidConfiguration = (ConfigurationObject) configuration.clone();

        invalidConfiguration.setPropertyValue("connectionName", "invalid");
        invalidInstance = new DatabaseReviewAuctionPersistence();
        invalidInstance.configure(invalidConfiguration);
    }

    /**
     * <p>
     * Failure test for createAuction(ReviewAuction auction). When auction is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateAuction_AuctionIsNull() throws Exception {
        instance.createAuction(null);
    }

    /**
     * <p>
     * Failure test for createAuction(ReviewAuction auction). When auction's type is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void testCreateAuction_TypeIsNull() throws Exception {
        ReviewAuction auction = getReviewAuction(1);
        auction.setAuctionType(null);
        instance.createAuction(auction);
    }

    /**
     * <p>
     * Failure test for createAuction(ReviewAuction auction). When connection failed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void testCreateAuction_ConnectionFailed() throws Exception {
        invalidInstance.createAuction(getReviewAuction(1));
    }

    /**
     * <p>
     * Failure test for getAuctionTypes(). When connection failed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void testGetAuctionTypes_ConnectionFailed() throws Exception {
        invalidInstance.getAuctionTypes();
    }

    /**
     * <p>
     * Failure test for getAuctionCategories(). When connection failed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void testGetAuctionCategories_ConnectionFailed() throws Exception {
        invalidInstance.getAuctionCategories();
    }

    /**
     * <p>
     * Failure test for getAssignedProjectResourceRoleIds(Set{Long} projectIds). When projectIds is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAssignedProjectResourceRoleIds_ProjectIdsIsNull() throws Exception {
        instance.getAssignedProjectResourceRoleIds(null);
    }

    /**
     * <p>
     * Failure test for getAssignedProjectResourceRoleIds(Set{Long} projectIds). When projectIds is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAssignedProjectResourceRoleIds_ProjectIdsIsEmpty() throws Exception {
        instance.getAssignedProjectResourceRoleIds(new HashSet<Long>());
    }

    /**
     * <p>
     * Failure test for getAssignedProjectResourceRoleIds(Set{Long} projectIds). When projectIds contains null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAssignedProjectResourceRoleIds_ProjectIdsContainsNull() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(null);
        instance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Failure test for getAssignedProjectResourceRoleIds(Set{Long} projectIds). When projectIds contains zero.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAssignedProjectResourceRoleIds_ProjectIdsContainsZero() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(0L);
        instance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Failure test for getAssignedProjectResourceRoleIds(Set{Long} projectIds). When connection failed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void testGetAssignedProjectResourceRoleIds_ConnectionFailed() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(1L);
        invalidInstance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Failure test for getAuctionCategoryId(long auctionId). When auctionId is zero.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAuctionCategoryId_AuctionIdIsZero() throws Exception {
        instance.getAuctionCategoryId(0);
    }

    /**
     * <p>
     * Failure test for getAuctionCategoryId(long auctionId). When connection failed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void testGetAuctionCategoryId_ConnectionFailed() throws Exception {
        invalidInstance.getAuctionCategoryId(1);
    }

    /**
     * <p>
     * Failure test for configure(ConfigurationObject configuration), when configuration is null.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConfigure_ConfiguratinIsNull() {
        instance.configure(null);
    }

    /**
     * <p>
     * Failure test for configure(ConfigurationObject configuration), when dbConnectionFactoryConfig is missed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testConfigure_DbConnectionFactoryConfigIsMissed() throws Exception {
        configuration.removeChild("dbConnectionFactoryConfig");
        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for configure(ConfigurationObject configuration), when dbConnectionFactoryConfig is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testConfigure_DbConnectionFactoryConfigIsInvalid() throws Exception {
        configuration.addChild(new DefaultConfigurationObject("dbConnectionFactoryConfig"));
        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for configure(ConfigurationObject configuration), when connectionName is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void testConfigure_ConnectionNameIsEmpty() throws Exception {
        configuration.setPropertyValue("connectionName", "   ");
        instance.configure(configuration);
    }
}