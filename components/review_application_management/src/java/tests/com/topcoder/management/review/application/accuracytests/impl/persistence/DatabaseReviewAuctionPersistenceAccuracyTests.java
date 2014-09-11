/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.accuracytests.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.accuracytests.BaseAccuracyTests;
import com.topcoder.management.review.application.impl.ReviewAuctionManagerImpl;
import com.topcoder.management.review.application.impl.persistence.DatabaseReviewAuctionPersistence;

/**
 * <p>
 * Accuracy test for DatabaseReviewAuctionPersistence class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DatabaseReviewAuctionPersistenceAccuracyTests extends BaseAccuracyTests {
    /**
     * <p>
     * Represents the instance of DatabaseReviewAuctionPersistence used in test.
     * </p>
     */
    private DatabaseReviewAuctionPersistence instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DatabaseReviewAuctionPersistenceAccuracyTests.class);
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

        instance = new DatabaseReviewAuctionPersistence();
        instance.configure(getPersistenceConfig(ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE));
    }

    /**
     * <p>
     * Accuracy test for DatabaseReviewAuctionPersistence(). The instance should be created.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", new DatabaseReviewAuctionPersistence());
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
     * Accuracy test for getAuctionTypes() method. All ReviewAuctionTypeS should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetAuctionTypes() throws Exception {
        assertReviewAuctionTypes(AUCTION_TYPES, instance.getAuctionTypes(), true);
        execute("DELETE FROM review_application_role_resource_role_xref");

        assertReviewAuctionTypes(AUCTION_TYPES, instance.getAuctionTypes(), false);
        clearData();
        assertEquals("Should return empty list.", 0, instance.getAuctionTypes().size());
    }

    /**
     * <p>
     * Accuracy test for getAuctionCategories() method.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetAuctionCategories() throws Exception {
        assertEntities(AUCTION_CATEGORY, instance.getAuctionCategories());

        clearData();
        assertEquals("Should return empty list.", 0, instance.getAuctionTypes().size());
    }

    /**
     * <p>
     * Accuracy test for getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds) method. All matched record should
     * be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetAssignedProjectResourceRoleIds() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(2L);
        Map<Long, List<Long>> list = instance.getAssignedProjectResourceRoleIds(projectIds);
        assertEquals("The getAssignedProjectResourceRoleIds is incorrect.", 0, list.size());

        projectIds.add(2L);
        projectIds.add(5L);
        projectIds.add(8L);
        projectIds.add(9L);
        list = instance.getAssignedProjectResourceRoleIds(projectIds);

        assertEquals("The getAssignedProjectResourceRoleIds is incorrect.", 2, list.size());
        List<Long> values = list.get(8L);
        assertEquals("The getAssignedProjectResourceRoleIds is incorrect.", 1, values.size());
        assertTrue("The getAssignedProjectResourceRoleIds is incorrect.", values.contains(5L));
        values = list.get(9L);
        assertEquals("The getAssignedProjectResourceRoleIds is incorrect.", 1, values.size());
        assertTrue("The getAssignedProjectResourceRoleIds is incorrect.", values.contains(6L));

        projectIds.clear();
        projectIds.add(1L);
        list = instance.getAssignedProjectResourceRoleIds(projectIds);
        assertEquals("The getAssignedProjectResourceRoleIds is incorrect.", 1, list.size());
        values = list.get(1L);
        assertEquals("The getAssignedProjectResourceRoleIds is incorrect.", 9, values.size());
    }

    /**
     * <p>
     * Accuracy test for getAuctionCategoryId(long auctionId) method. The the auction category ID should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetAuctionCategoryId() throws Exception {
        assertEquals("The auction category id is incorrect.", 1, instance.getAuctionCategoryId(1));
        assertEquals("The auction category id is incorrect.", 2, instance.getAuctionCategoryId(3));
    }
}