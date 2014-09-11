/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.accuracytests.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.ReviewApplicationStatus;
import com.topcoder.management.review.application.accuracytests.BaseAccuracyTests;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;
import com.topcoder.management.review.application.impl.persistence.DatabaseReviewApplicationPersistence;

/**
 * <p>
 * Accuracy test for DatabaseReviewApplicationPersistence class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DatabaseReviewApplicationPersistenceAccuracyTests extends BaseAccuracyTests {
    /**
     * <p>
     * Represents the instance of DatabaseReviewApplicationPersistence used in test.
     * </p>
     */
    private DatabaseReviewApplicationPersistence instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DatabaseReviewApplicationPersistenceAccuracyTests.class);
    }

    /**
     * <p>
     * Sets up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();

        instance = new DatabaseReviewApplicationPersistence();
        instance.configure(getPersistenceConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE));
    }

    /**
     * <p>
     * Accuracy test for DatabaseReviewApplicationPersistence(). The instance should be created.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", new DatabaseReviewApplicationPersistence());
    }

    /**
     * <p>
     * Accuracy test for createApplication(ReviewApplication application). The application should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testCreateApplication() throws Exception {
        ReviewApplication application = getReviewApplication(1);
        ReviewApplication created = instance.createApplication(application);

        assertTrue("The id should be set.", created.getId() > 0);
        application.setId(created.getId());
        assertEntity(application, created);
        assertEquals("The createApplication is incorrect.", 1, count(getConnection(),
            "SELECT count(*) FROM review_application WHERE review_application_id = ? AND user_id=? "
                + "AND review_auction_id=? AND review_application_role_id=? "
                + "AND review_application_status_id=? AND create_date=?", created.getId(), application.getUserId(),
            application.getAuctionId(), application.getApplicationRoleId(), application.getStatus().getId(),
            new Timestamp(application.getCreateDate().getTime())));
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateApplication(ReviewApplication application)</code>.<br>
     * The application should be updated.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testUpdateApplication() throws Exception {
        ReviewApplication application = getReviewApplication(2);
        application.setId(1);
        instance.updateApplication(application);

        assertEquals("The createApplication is incorrect.", 1, count(getConnection(),
            "SELECT count(*) FROM review_application WHERE review_application_id = ? AND user_id=? "
                + "AND review_auction_id=? AND review_application_role_id=? "
                + "AND review_application_status_id=? AND create_date=?", application.getId(), application.getUserId(),
            application.getAuctionId(), application.getApplicationRoleId(), application.getStatus().getId(),
            new Timestamp(application.getCreateDate().getTime())));
    }

    /**
     * <p>
     * Accuracy test for getApplicationStatuses() method, all ReviewApplicationStatus should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetApplicationStatuses() throws Exception {
        List<ReviewApplicationStatus> list = instance.getApplicationStatuses();

        assertEntities(list, STATUSES);
        clearData();
        assertEquals("Should return empty list.", 0, instance.getApplicationStatuses().size());
    }
}