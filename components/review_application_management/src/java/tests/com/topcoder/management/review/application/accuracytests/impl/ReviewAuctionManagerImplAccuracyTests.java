/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.accuracytests.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.accuracytests.BaseAccuracyTests;
import com.topcoder.management.review.application.impl.ReviewAuctionManagerImpl;

/**
 * <p>
 * Accuracy test for ReviewAuctionManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewAuctionManagerImplAccuracyTests extends BaseAccuracyTests {
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
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewAuctionManagerImplAccuracyTests.class);
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

        configuration = getConfig(ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        instance = new ReviewAuctionManagerImpl(configuration);
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
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewAuctionManagerImpl(ConfigurationObject
     * configuration)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor3() {
        instance = new ReviewAuctionManagerImpl(configuration);
    }

    /**
     * <p>
     * Accuracy test for createAuction(ReviewAuction auction) method. The instance should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testCreateAuction() throws Exception {
        ReviewAuction auction = getReviewAuction(4);
        ReviewAuction created = instance.createAuction(auction);

        auction.setId(created.getId());
        assertEntity(auction, created);
        assertEquals("The createApplication is incorrect.", 1, count(getConnection(),
            "SELECT count(*) FROM review_auction WHERE project_id = ? AND review_auction_type_id=? "
                + "AND review_auction_id=?", auction.getProjectId(), auction.getAuctionType().getId(), created.getId()));
    }

    /**
     * <p>
     * Accuracy test for searchOpenAuctions(long auctionCategoryId). All matched result should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchOpenAuctions1_SpecReview() throws Exception {
        List<ReviewAuction> list = instance.searchOpenAuctions(2);

        assertEquals("The searchOpenAuctions is incorrect.", 2, list.size());
        ReviewAuction reviewAuction = list.get(0);
        assertEquals("The searchOpenAuctions is incorrect.", 3, reviewAuction.getId());
        assertEquals("The searchOpenAuctions is incorrect.", 3, reviewAuction.getProjectId());
        assertEquals("The searchOpenAuctions is incorrect.", "2010-11-25 13:00:00", toString(reviewAuction
            .getAssignmentDate()));
        assertEntity(AUCTION_TYPES.get(2), reviewAuction.getAuctionType());
        assertTrue("The searchOpenAuctions is incorrect.", reviewAuction.isOpen());
        List<Long> openPositions = reviewAuction.getOpenPositions();
        assertEquals("The searchOpenAuctions is incorrect.", 1, openPositions.size());
        assertTrue("The searchOpenAuctions is incorrect.", openPositions.contains(1L));

        reviewAuction = list.get(1);
        assertEquals("The searchOpenAuctions is incorrect.", 12, reviewAuction.getId());
        assertEquals("The searchOpenAuctions is incorrect.", 12, reviewAuction.getProjectId());
        assertEquals("The searchOpenAuctions is incorrect.", "2010-11-25 13:00:00", toString(reviewAuction
            .getAssignmentDate()));
        assertEntity(AUCTION_TYPES.get(2), reviewAuction.getAuctionType());
        assertTrue("The searchOpenAuctions is incorrect.", reviewAuction.isOpen());
        openPositions = reviewAuction.getOpenPositions();
        assertEquals("The searchOpenAuctions is incorrect.", 1, openPositions.size());
        assertTrue("The searchOpenAuctions is incorrect.", openPositions.contains(1L));
    }

    /**
     * <p>
     * Accuracy test for searchOpenAuctions(long auctionCategoryId). All matched result should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchOpenAuctions1_ContestReview() throws Exception {
        List<ReviewAuction> list = instance.searchOpenAuctions(1);

        assertEquals("The searchOpenAuctions is incorrect.", 3, list.size());
        ReviewAuction reviewAuction = list.get(0);
        assertEquals("The searchOpenAuctions is incorrect.", 5, reviewAuction.getId());
        assertEquals("The searchOpenAuctions is incorrect.", 5, reviewAuction.getProjectId());
        assertEntity(AUCTION_TYPES.get(0), reviewAuction.getAuctionType());
        assertEquals("The searchOpenAuctions is incorrect.", "2010-11-25 12:00:00", toString(reviewAuction
            .getAssignmentDate()));
        assertTrue("The searchOpenAuctions is incorrect.", reviewAuction.isOpen());
        List<Long> openPositions = reviewAuction.getOpenPositions();
        assertEquals("The searchOpenAuctions is incorrect.", 2, openPositions.size());
        assertEquals("The searchOpenAuctions is incorrect.", 1L, openPositions.get(0).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(1).longValue());

        reviewAuction = list.get(1);
        assertEquals("The searchOpenAuctions is incorrect.", 8, reviewAuction.getId());
        assertEquals("The searchOpenAuctions is incorrect.", 8, reviewAuction.getProjectId());
        assertEquals("The searchOpenAuctions is incorrect.", "2010-11-25 12:00:00", toString(reviewAuction
            .getAssignmentDate()));
        assertEntity(AUCTION_TYPES.get(1), reviewAuction.getAuctionType());
        assertTrue("The searchOpenAuctions is incorrect.", reviewAuction.isOpen());
        openPositions = reviewAuction.getOpenPositions();
        assertEquals("The searchOpenAuctions is incorrect.", 4, openPositions.size());
        assertEquals("The searchOpenAuctions is incorrect.", 1L, openPositions.get(0).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(1).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(2).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(3).longValue());

        reviewAuction = list.get(2);
        assertEquals("The searchOpenAuctions is incorrect.", 9, reviewAuction.getId());
        assertEquals("The searchOpenAuctions is incorrect.", 9, reviewAuction.getProjectId());
        assertEquals("The searchOpenAuctions is incorrect.", "2010-11-25 12:00:00", toString(reviewAuction
            .getAssignmentDate()));
        assertEntity(AUCTION_TYPES.get(1), reviewAuction.getAuctionType());
        assertTrue("The searchOpenAuctions is incorrect.", reviewAuction.isOpen());
        openPositions = reviewAuction.getOpenPositions();
        assertEquals("The searchOpenAuctions is incorrect.", 4, openPositions.size());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(0).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 1L, openPositions.get(1).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(2).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(3).longValue());
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
    public void testSearchOpenAuctions2_SpecReview() throws Exception {
        List<Long> projectCategoryIds = Arrays.asList(1L);
        List<ReviewAuction> list = instance.searchOpenAuctions(2, projectCategoryIds);

        assertEquals("The searchOpenAuctions is incorrect.", 1, list.size());
        ReviewAuction reviewAuction = list.get(0);
        assertEquals("The searchOpenAuctions is incorrect.", 3, reviewAuction.getId());
        assertEquals("The searchOpenAuctions is incorrect.", 3, reviewAuction.getProjectId());
        assertEquals("The searchOpenAuctions is incorrect.", "2010-11-25 13:00:00", toString(reviewAuction
            .getAssignmentDate()));
        assertEntity(AUCTION_TYPES.get(2), reviewAuction.getAuctionType());
        assertTrue("The searchOpenAuctions is incorrect.", reviewAuction.isOpen());
        List<Long> openPositions = reviewAuction.getOpenPositions();
        assertEquals("The searchOpenAuctions is incorrect.", 1, openPositions.size());
        assertTrue("The searchOpenAuctions is incorrect.", openPositions.contains(1L));

        projectCategoryIds = Arrays.asList(3L);
        assertEquals("The searchOpenAuctions is incorrect.", 0, instance.searchOpenAuctions(2, projectCategoryIds)
            .size());
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
    public void testSearchOpenAuctions2_ContestReview() throws Exception {
        List<Long> projectCategoryIds = Arrays.asList(1L);
        List<ReviewAuction> list = instance.searchOpenAuctions(1, projectCategoryIds);

        assertEquals("The searchOpenAuctions is incorrect.", 2, list.size());
        ReviewAuction reviewAuction = list.get(0);
        assertEquals("The searchOpenAuctions is incorrect.", 5, reviewAuction.getId());
        assertEquals("The searchOpenAuctions is incorrect.", 5, reviewAuction.getProjectId());
        assertEntity(AUCTION_TYPES.get(0), reviewAuction.getAuctionType());
        assertEquals("The searchOpenAuctions is incorrect.", "2010-11-25 12:00:00", toString(reviewAuction
            .getAssignmentDate()));
        assertTrue("The searchOpenAuctions is incorrect.", reviewAuction.isOpen());
        List<Long> openPositions = reviewAuction.getOpenPositions();
        assertEquals("The searchOpenAuctions is incorrect.", 2, openPositions.size());
        assertEquals("The searchOpenAuctions is incorrect.", 1L, openPositions.get(0).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(1).longValue());

        reviewAuction = list.get(1);
        assertEquals("The searchOpenAuctions is incorrect.", 9, reviewAuction.getId());
        assertEquals("The searchOpenAuctions is incorrect.", 9, reviewAuction.getProjectId());
        assertEquals("The searchOpenAuctions is incorrect.", "2010-11-25 12:00:00", toString(reviewAuction
            .getAssignmentDate()));
        assertEntity(AUCTION_TYPES.get(1), reviewAuction.getAuctionType());
        assertTrue("The searchOpenAuctions is incorrect.", reviewAuction.isOpen());
        openPositions = reviewAuction.getOpenPositions();
        assertEquals("The searchOpenAuctions is incorrect.", 4, openPositions.size());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(0).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 1L, openPositions.get(1).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(2).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(3).longValue());

        projectCategoryIds = Arrays.asList(3L, 4L);
        assertEquals("The searchOpenAuctions is incorrect.", 0, instance.searchOpenAuctions(1, projectCategoryIds)
            .size());
    }

    /**
     * <p>
     * Accuracy test for getAuction(long auctionId). The ReviewAuction should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetAuction() throws Exception {
        ReviewAuction reviewAuction = instance.getAuction(2);
        assertEquals("The searchOpenAuctions is incorrect.", 2, reviewAuction.getId());
        assertEquals("The searchOpenAuctions is incorrect.", "2010-11-25 12:00:00", toString(reviewAuction
            .getAssignmentDate()));
        assertEntity(AUCTION_TYPES.get(1), reviewAuction.getAuctionType());
        assertFalse("The searchOpenAuctions is incorrect.", reviewAuction.isOpen());
        List<Long> openPositions = reviewAuction.getOpenPositions();
        assertEquals("The searchOpenAuctions is incorrect.", 4, openPositions.size());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(0).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(1).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(2).longValue());
        assertEquals("The searchOpenAuctions is incorrect.", 0L, openPositions.get(3).longValue());

        reviewAuction = instance.getAuction(3);

        assertEquals("The getAuction is incorrect.", 3, reviewAuction.getId());
        assertEquals("The getAuction is incorrect.", "2010-11-25 13:00:00", toString(reviewAuction.getAssignmentDate()));
        assertEntity(AUCTION_TYPES.get(2), reviewAuction.getAuctionType());
        assertTrue("The getAuction is incorrect.", reviewAuction.isOpen());
        openPositions = reviewAuction.getOpenPositions();
        assertEquals("The getAssignedProjectResourceRoleIds is incorrect.", 1, openPositions.size());
        assertTrue("The getAssignedProjectResourceRoleIds is incorrect.", openPositions.contains(1L));

        reviewAuction = instance.getAuction(5);

        assertEquals("The getAuction is incorrect.", 5, reviewAuction.getId());
        assertEquals("The getAuction is incorrect.", "2010-11-25 12:00:00", toString(reviewAuction.getAssignmentDate()));
        assertEntity(AUCTION_TYPES.get(0), reviewAuction.getAuctionType());
        assertTrue("The getAuction is incorrect.", reviewAuction.isOpen());
        openPositions = reviewAuction.getOpenPositions();
        assertEquals("The getAssignedProjectResourceRoleIds is incorrect.", 2, openPositions.size());
        assertTrue("The getAssignedProjectResourceRoleIds is incorrect.", openPositions.contains(1L));
        assertTrue("The getAssignedProjectResourceRoleIds is incorrect.", openPositions.contains(0L));

        assertNull("The searchOpenAuctions is incorrect.", instance.getAuction(25));
    }

    /**
     * Converts the date to String.
     *
     * @param date
     *            the date to convert.
     * @return the date string.
     */
    private static String toString(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * <p>
     * Accuracy test for getAuctionCategories(). All records should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetAuctionCategories() throws Exception {
        assertEntities(AUCTION_CATEGORY, instance.getAuctionCategories());

        clearData();
        assertEntities(AUCTION_CATEGORY, instance.getAuctionCategories());
    }

    /**
     * <p>
     * Accuracy test for getAuctionCategories(). No records should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetAuctionCategories_Empty() throws Exception {
        clearData();

        assertEquals("Should return empty list.", 0, instance.getAuctionCategories().size());
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
    public void testGetAuctionTypes() throws Exception {
        assertEntities(AUCTION_TYPES, instance.getAuctionTypes());

        clearData();
        assertEntities(AUCTION_TYPES, instance.getAuctionTypes());
    }

    /**
     * <p>
     * Accuracy test for getAuctionTypes(). No records should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetAuctionTypes_Empty() throws Exception {
        clearData();

        assertEquals("Should return empty list.", 0, instance.getAuctionTypes().size());
    }
}