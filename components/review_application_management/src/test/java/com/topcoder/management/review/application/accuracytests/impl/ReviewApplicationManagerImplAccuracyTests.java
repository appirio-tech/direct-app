/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.accuracytests.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.ReviewApplicationStatus;
import com.topcoder.management.review.application.accuracytests.BaseAccuracyTests;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

/**
 * <p>
 * Accuracy test for ReviewApplicationManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewApplicationManagerImplAccuracyTests extends BaseAccuracyTests {
    /**
     * <p>
     * Represents the <code>ReviewApplicationManagerImpl</code> instance used in tests.
     * </p>
     */
    private ReviewApplicationManagerImpl instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewApplicationManagerImplAccuracyTests.class);
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
        instance = new ReviewApplicationManagerImpl(getConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE));
    }

    /**
     * <p>
     * Accuracy test for ReviewApplicationManagerImpl(). The instance should be created.
     * </p>
     */
    @Test
    public void testCtor1() {
        assertNotNull("The instance should be created", new ReviewApplicationManagerImpl());
    }

    /**
     * <p>
     * Accuracy test for ReviewApplicationManagerImpl(String filePath, String namespace). The instance should be
     * created.
     * </p>
     */
    @Test
    public void testCtor2() {
        assertNotNull("The instance should be created",
            new ReviewApplicationManagerImpl(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME,
                ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE));
    }

    /**
     * <p>
     * Accuracy test for ReviewApplicationManagerImpl(ConfigurationObject configuration). The instance should be
     * created.
     * </p>
     */
    @Test
    public void testCtor3() {
        assertNotNull("The instance should be created", instance);
    }

    /**
     * <p>
     * Accuracy test for createApplication(ReviewApplication application) method. The ReviewApplication should be
     * created.
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
     * Accuracy test for updateApplication(ReviewApplication application) method. The ReviewApplication should be
     * updated.
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
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The matched list should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchApplications_FilterIsNull() throws Exception {
        List<ReviewApplication> list = instance.searchApplications(null);

        assertEquals("The searchApplications is incorrect.", 6, list.size());
        ReviewApplication reviewApplication = list.get(0);
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 101, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(0), reviewApplication.getCreateDate());

        reviewApplication = list.get(1);
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 102, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(1), reviewApplication.getCreateDate());

        reviewApplication = list.get(2);
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 103, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(2), reviewApplication.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>searchApplications(Filter filter)</code>.<br>
     * The matched list should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchApplications_Id() throws Exception {
        List<ReviewApplication> list = instance.searchApplications(new EqualToFilter("id", 25));

        assertEquals("The searchApplications is incorrect.", 0, list.size());
        list = instance.searchApplications(new EqualToFilter("id", 3));
        assertEquals("The searchApplications is incorrect.", 1, list.size());

        ReviewApplication reviewApplication = list.get(0);
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 103, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(2), reviewApplication.getCreateDate());

        list = instance.searchApplications(new GreaterThanFilter("id", 1));
        assertEquals("The searchApplications is incorrect.", 5, list.size());
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
    public void testSearchApplications_UserId() throws Exception {
        Filter filter = new EqualToFilter("userId", 103);
        List<ReviewApplication> list = instance.searchApplications(filter);
        assertEquals("The searchApplications is incorrect.", 1, list.size());

        ReviewApplication reviewApplication = list.get(0);
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 103, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(2), reviewApplication.getCreateDate());

        filter = new OrFilter(filter, new LessThanFilter("applicationRoleId", 4));
        list = instance.searchApplications(filter);
        assertEquals("The searchApplications is incorrect.", 3, list.size());

        reviewApplication = list.get(0);
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 101, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(0), reviewApplication.getCreateDate());

        reviewApplication = list.get(1);
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 102, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(1), reviewApplication.getCreateDate());
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
    public void testSearchApplications_AuctionId() throws Exception {
        Filter filter = new EqualToFilter("auctionId", 3);
        List<ReviewApplication> list = instance.searchApplications(filter);
        assertEquals("The searchApplications is incorrect.", 2, list.size());

        ReviewApplication reviewApplication = list.get(0);
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 103, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(2), reviewApplication.getCreateDate());

        reviewApplication = list.get(1);
        assertEquals("The searchApplications is incorrect.", 4, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 104, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 4, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(0), reviewApplication.getCreateDate());

        filter = new AndFilter(filter, new LessThanFilter("applicationRoleId", 4));
        list = instance.searchApplications(filter);
        assertEquals("The searchApplications is incorrect.", 1, list.size());

        reviewApplication = list.get(0);
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 103, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 3, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(2), reviewApplication.getCreateDate());

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
    public void testSearchApplications_StatusId() throws Exception {
        Filter filter = new NotFilter(new EqualToFilter("statusId", 3));
        List<ReviewApplication> list = instance.searchApplications(filter);
        assertEquals("The searchApplications is incorrect.", 4, list.size());

        ReviewApplication reviewApplication = list.get(0);
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 101, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(0), reviewApplication.getCreateDate());

        reviewApplication = list.get(1);
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 102, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 2, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(1), reviewApplication.getCreateDate());

        filter = new AndFilter(filter, new LessThanFilter("createDate", new Timestamp(getDate(1).getTime())));
        list = instance.searchApplications(filter);
        assertEquals("The searchApplications is incorrect.", 1, list.size());
        reviewApplication = list.get(0);
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getId());
        assertEquals("The searchApplications is incorrect.", 101, reviewApplication.getUserId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getAuctionId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getApplicationRoleId());
        assertEquals("The searchApplications is incorrect.", 1, reviewApplication.getStatus().getId());
        assertEquals("The searchApplications is incorrect.", getDate(0), reviewApplication.getCreateDate());
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

        // The data should be cached
        assertEntities(list, STATUSES);
    }

    /**
     * <p>
     * Accuracy test for getApplicationStatuses() method, when no data existed, return empty list.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetApplicationStatuses_Empty() throws Exception {
        clearData();
        assertEquals("Should return empty list.", 0, instance.getApplicationStatuses().size());
    }

    /**
     * Gets the date.
     *
     * @param ms
     *            the millisecond
     * @return the date.
     */
    private static Date getDate(int ms) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2006);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, ms);
        return cal.getTime();
    }
}