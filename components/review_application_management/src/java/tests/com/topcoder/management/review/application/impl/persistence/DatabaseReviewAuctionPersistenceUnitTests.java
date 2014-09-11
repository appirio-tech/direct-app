/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.BaseUnitTests;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.ReviewAuctionCategory;
import com.topcoder.management.review.application.ReviewAuctionType;
import com.topcoder.management.review.application.impl.ReviewAuctionManagerImpl;
import com.topcoder.management.review.application.impl.ReviewAuctionPersistenceException;

/**
 * <p>
 * Unit tests for {@link DatabaseReviewAuctionPersistence} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DatabaseReviewAuctionPersistenceUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>DatabaseReviewAuctionPersistence</code> instance used in tests.
     * </p>
     */
    private DatabaseReviewAuctionPersistence instance;

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
        return new JUnit4TestAdapter(DatabaseReviewAuctionPersistenceUnitTests.class);
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

        auctionTypes = instance.getAuctionTypes();

        auction = new ReviewAuction();
        auction.setProjectId(100000);
        auction.setAuctionType(auctionTypes.get(0));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DatabaseReviewAuctionPersistence()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DatabaseReviewAuctionPersistence();

        assertNull("'dbConnectionFactory' should be correct.",
            getField(instance, "dbConnectionFactory"));
        assertNull("'connectionName' should be correct.",
            getField(instance, "connectionName"));
        assertNull("'log' should be correct.",
            getField(instance, "log"));
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
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_createAuction_auctionAuctionTypeNull() throws Exception {
        auction.setAuctionType(null);

        instance.createAuction(auction);
    }

    /**
     * <p>
     * Failure test for the method <code>createAuction(ReviewAuction auction)</code>
     * with an error has occurred.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
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
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_createAuction_Error2() throws Exception {
        instance = new DatabaseReviewAuctionPersistence();

        instance.createAuction(auction);
    }

    /**
     * <p>
     * Failure test for the method <code>createAuction(ReviewAuction auction)</code>
     * with an error has occurred.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_createAuction_Error3() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.createAuction(auction);
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
        clearDB();

        List<ReviewAuctionType> res = instance.getAuctionTypes();

        assertEquals("'getAuctionTypes' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionTypes()</code> with an error has occurred.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_getAuctionTypes_Error() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

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
        clearDB();

        List<ReviewAuctionCategory> res = instance.getAuctionCategories();

        assertEquals("'getAuctionCategories' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionCategories()</code> with an error has occurred.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_getAuctionCategories_Error1() throws Exception {
        instance = new DatabaseReviewAuctionPersistence();

        instance.getAuctionCategories();
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionCategories()</code> with an error has occurred.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_getAuctionCategories_Error2() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.getAuctionCategories();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAssignedProjectResourceRoleIds_1() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(100000L);
        projectIds.add(100001L);
        Map<Long, List<Long>> res = instance.getAssignedProjectResourceRoleIds(projectIds);

        assertEquals("'getAssignedProjectResourceRoleIds' should be correct.", 2, res.size());
        List<Long> values = res.get(100000L);
        assertEquals("'getAssignedProjectResourceRoleIds' should be correct.", 2, values.size());
        assertTrue("'getAssignedProjectResourceRoleIds' should be correct.", values.contains(2L));
        assertTrue("'getAssignedProjectResourceRoleIds' should be correct.", values.contains(4L));
        values = res.get(100001L);
        assertEquals("'getAssignedProjectResourceRoleIds' should be correct.", 1, values.size());
        assertTrue("'getAssignedProjectResourceRoleIds' should be correct.", values.contains(2L));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAssignedProjectResourceRoleIds_2() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(100000L);
        Map<Long, List<Long>> res = instance.getAssignedProjectResourceRoleIds(projectIds);

        assertEquals("'getAssignedProjectResourceRoleIds' should be correct.", 1, res.size());
        List<Long> values = res.get(100000L);
        assertEquals("'getAssignedProjectResourceRoleIds' should be correct.", 2, values.size());
        assertTrue("'getAssignedProjectResourceRoleIds' should be correct.", values.contains(2L));
        assertTrue("'getAssignedProjectResourceRoleIds' should be correct.", values.contains(4L));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAssignedProjectResourceRoleIds_3() throws Exception {
        clearDB();

        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(10000L);
        projectIds.add(100001L);
        Map<Long, List<Long>> res = instance.getAssignedProjectResourceRoleIds(projectIds);

        assertEquals("'getAssignedProjectResourceRoleIds' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code> with
     * projectIds is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAssignedProjectResourceRoleIds_projectIdsNull() throws Exception {
        Set<Long> projectIds = null;

        instance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code> with
     * projectIds is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAssignedProjectResourceRoleIds_projectIdsEmpty() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();

        instance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code> with
     * projectIds contains null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAssignedProjectResourceRoleIds_projectIdsContainsNull() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(null);

        instance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code> with
     * projectIds contains negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAssignedProjectResourceRoleIds_projectIdsContainsNegative() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(-1L);

        instance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code> with
     * projectIds contains zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAssignedProjectResourceRoleIds_projectIdsContainsZero() throws Exception {
        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(0L);

        instance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code> with an
     * error has occurred.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_getAssignedProjectResourceRoleIds_Error1() throws Exception {
        instance = new DatabaseReviewAuctionPersistence();

        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(10000L);
        instance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Failure test for the method <code>getAssignedProjectResourceRoleIds(Set&lt;Long&gt; projectIds)</code> with an
     * error has occurred.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_getAssignedProjectResourceRoleIds_Error2() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        Set<Long> projectIds = new HashSet<Long>();
        projectIds.add(10000L);
        instance.getAssignedProjectResourceRoleIds(projectIds);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuctionCategoryId(long auctionId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAuctionCategoryId() throws Exception {
        auction = instance.createAuction(auction);
        long res = instance.getAuctionCategoryId(auction.getId());

        assertEquals("'getAuctionCategoryId' should be correct.",
            auction.getAuctionType().getAuctionCategory().getId(), res);
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionCategoryId(long auctionId)</code> with
     * auctionId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAuctionCategoryId_auctionIdNegative() throws Exception {
        instance.getAuctionCategoryId(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionCategoryId(long auctionId)</code> with
     * auctionId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAuctionCategoryId_auctionIdZero() throws Exception {
        instance.getAuctionCategoryId(0);
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionCategoryId(long auctionId)</code> with no auction category found.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_getAuctionCategoryId_NoAuctionCategory() throws Exception {
        instance.getAuctionCategoryId(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionCategoryId(long auctionId)</code> with an
     * error has occurred.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_getAuctionCategoryId_Error1() throws Exception {
        auction = instance.createAuction(auction);

        instance = new DatabaseReviewAuctionPersistence();

        instance.getAuctionCategoryId(auction.getId());
    }

    /**
     * <p>
     * Failure test for the method <code>getAuctionCategoryId(long auctionId)</code> with an
     * error has occurred.<br>
     * <code>ReviewAuctionPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewAuctionPersistenceException.class)
    public void test_getAuctionCategoryId_Error2() throws Exception {
        auction = instance.createAuction(auction);

        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.getAuctionCategoryId(auction.getId());
    }
}