/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.failuretests.impl.persistence;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.accuracytests.BaseAccuracyTests;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;
import com.topcoder.management.review.application.impl.ReviewApplicationPersistenceException;
import com.topcoder.management.review.application.impl.persistence.DatabaseReviewApplicationPersistence;

/**
 * <p>
 * Failure test for DatabaseReviewApplicationPersistence class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DatabaseReviewApplicationPersistenceFailureTests extends BaseAccuracyTests {
    /**
     * <p>
     * Represents the instance of DatabaseReviewApplicationPersistence used in test.
     * </p>
     */
    private DatabaseReviewApplicationPersistence instance;

    /**
     * <p>
     * Represents the invalid instance of DatabaseReviewApplicationPersistence used in test.
     * </p>
     */
    private DatabaseReviewApplicationPersistence invalidInstance;

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
        return new JUnit4TestAdapter(DatabaseReviewApplicationPersistenceFailureTests.class);
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

        configuration = getConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE).getChild("persistenceConfig");

        instance = new DatabaseReviewApplicationPersistence();
        instance.configure(configuration);

        ConfigurationObject invalidConfiguration = (ConfigurationObject) configuration.clone();

        invalidConfiguration.setPropertyValue("connectionName", "invalid");
        invalidInstance = new DatabaseReviewApplicationPersistence();
        invalidInstance.configure(invalidConfiguration);
    }

    /**
     * <p>
     * Failure test for createApplication(ReviewApplication application), when application is null.
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
     * Failure test for createApplication(ReviewApplication application), when application's status is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void testCreateApplication_StatusIsNull() throws Exception {
        ReviewApplication application = getReviewApplication(6);
        application.setStatus(null);

        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for createApplication(ReviewApplication application), when application's createDate is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void testCreateApplication_CreateDateIsNull() throws Exception {
        ReviewApplication application = getReviewApplication(6);
        application.setCreateDate(null);
        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for createApplication(ReviewApplication application), when connection failed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void testCreateApplication_ConnectionFaiiled() throws Exception {
        ReviewApplication application = getReviewApplication(1);
        invalidInstance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for updateApplication(ReviewApplication application), when application is null.
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
     * Failure test for updateApplication(ReviewApplication application), when application's status is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void testUpdateApplication_StatusIsNull() throws Exception {
        ReviewApplication application = getReviewApplication(1);
        application.setStatus(null);
        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for updateApplication(ReviewApplication application), when application's createDate is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void testUpdateApplication_CreateDateIsNull() throws Exception {
        ReviewApplication application = getReviewApplication(1);
        application.setCreateDate(null);
        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for updateApplication(ReviewApplication application), when the entity not found.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void testUpdateApplication_NotFound() throws Exception {
        ReviewApplication application = getReviewApplication(100);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for updateApplication(ReviewApplication application), when connection failed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void testUpdateApplication_ConnectionFailed() throws Exception {
        invalidInstance.updateApplication(getReviewApplication(1));
    }

    /**
     * <p>
     * Failure test for getApplicationStatuses(), when connection failed.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void testGetApplicationStatuses_ConnectionFailed() throws Exception {
        invalidInstance.getApplicationStatuses();
    }
}