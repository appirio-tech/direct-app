/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.BaseUnitTests;
import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.ReviewApplicationStatus;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.ReviewAuctionType;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;
import com.topcoder.management.review.application.impl.ReviewApplicationPersistenceException;

/**
 * <p>
 * Unit tests for {@link DatabaseReviewApplicationPersistence} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DatabaseReviewApplicationPersistenceUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>DatabaseReviewApplicationPersistence</code> instance used in tests.
     * </p>
     */
    private DatabaseReviewApplicationPersistence instance;

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
    private DatabaseReviewAuctionPersistence auctionPersistence;

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
        return new JUnit4TestAdapter(DatabaseReviewApplicationPersistenceUnitTests.class);
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

        auctionPersistence = new DatabaseReviewAuctionPersistence();
        auctionPersistence.configure(configuration);
        auctionTypes = auctionPersistence.getAuctionTypes();

        // Create Auction
        ReviewAuction auction = new ReviewAuction();
        auction.setProjectId(100000);
        auction.setAuctionType(auctionTypes.get(0));

        auction = auctionPersistence.createAuction(auction);
        long auctionId = auction.getId();

        instance = new DatabaseReviewApplicationPersistence();

        application = new ReviewApplication();
        application.setUserId(123);
        application.setAuctionId(auctionId);
        application.setApplicationRoleId(4);
        application.setStatus(new ReviewApplicationStatus(1, "Pending"));
        application.setCreateDate(new Date());

        instance.configure(configuration);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DatabaseReviewApplicationPersistence()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DatabaseReviewApplicationPersistence();

        assertNull("'dbConnectionFactory' should be correct.",
            getField(instance, "dbConnectionFactory"));
        assertNull("'connectionName' should be correct.",
            getField(instance, "connectionName"));
        assertNull("'log' should be correct.",
            getField(instance, "log"));
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
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_createApplication_applicationStatusNull() throws Exception {
        application.setStatus(null);

        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>createApplication(ReviewApplication application)</code>
     * with application.getCreateDate() is null.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_createApplication_applicationCreateDateNull() throws Exception {
        application.setCreateDate(null);

        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>createApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_createApplication_Error1() throws Exception {
        application.setAuctionId(Long.MAX_VALUE);

        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>createApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_createApplication_Error2() throws Exception {
        instance = new DatabaseReviewApplicationPersistence();

        instance.createApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>createApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_createApplication_Error3() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

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
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_updateApplication_applicationStatusNull() throws Exception {
        application.setStatus(null);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with application.getCreateDate() is null.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_updateApplication_applicationCreateDateNull() throws Exception {
        application.setCreateDate(null);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with the application doesn't exist.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_updateApplication_applicationNotExist() throws Exception {
        application.setId(Long.MAX_VALUE);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_updateApplication_Error1() throws Exception {
        application = instance.createApplication(application);

        application.setAuctionId(Long.MAX_VALUE);

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_updateApplication_Error2() throws Exception {
        instance = new DatabaseReviewApplicationPersistence();

        instance.updateApplication(application);
    }

    /**
     * <p>
     * Failure test for the method <code>updateApplication(ReviewApplication application)</code>
     * with an error has occurred.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_updateApplication_Error3() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.updateApplication(application);
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
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_getApplicationStatuses_Error1() throws Exception {
        instance = new DatabaseReviewApplicationPersistence();

        instance.getApplicationStatuses();
    }

    /**
     * <p>
     * Failure test for the method <code>getApplicationStatuses()</code> with an error has occurred.<br>
     * <code>ReviewApplicationPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationPersistenceException.class)
    public void test_getApplicationStatuses_Error2() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.getApplicationStatuses();
    }
}