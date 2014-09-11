/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link ReviewAuctionType} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReviewAuctionTypeUnitTests {
    /**
     * <p>
     * Represents the <code>ReviewAuctionType</code> instance used in tests.
     * </p>
     */
    private ReviewAuctionType instance;

    /**
     * <p>
     * Represents the id used in tests.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * Represents the name used in tests.
     * </p>
     */
    private String name = "name";

    /**
     * <p>
     * Represents the auction category used in tests.
     * </p>
     */
    private ReviewAuctionCategory auctionCategory;

    /**
     * <p>
     * Represents the application roles used in tests.
     * </p>
     */
    private List<ReviewApplicationRole> applicationRoles;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewAuctionTypeUnitTests.class);
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
        auctionCategory = new ReviewAuctionCategory(1, "name1");
        applicationRoles = Arrays.asList(
            new ReviewApplicationRole(1, "name1", 1, Arrays.asList(new ReviewApplicationResourceRole(1, false))),
            new ReviewApplicationRole(2, "name2", 2, Arrays.asList(new ReviewApplicationResourceRole(2, true))));

        instance = new ReviewAuctionType(id, name, auctionCategory, applicationRoles);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewAuctionType(long id, String name,
     * ReviewAuctionCategory auctionCategory, List&lt;ReviewApplicationRole&gt; applicationRoles)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ReviewAuctionType(id, name, auctionCategory, applicationRoles);

        assertEquals("'id' should be correct.", id, BaseUnitTests.getField(instance, "id"));
        assertEquals("'name' should be correct.", name, BaseUnitTests.getField(instance, "name"));
        assertSame("'auctionCategory' should be correct.",
            auctionCategory, BaseUnitTests.getField(instance, "auctionCategory"));
        assertSame("'applicationRoles' should be correct.",
            applicationRoles, BaseUnitTests.getField(instance, "applicationRoles"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuctionCategory()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAuctionCategory() {
        assertSame("'getAuctionCategory' should be correct.",
            auctionCategory, instance.getAuctionCategory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getApplicationRoles()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getApplicationRoles() {
        assertSame("'getApplicationRoles' should be correct.",
            applicationRoles, instance.getApplicationRoles());
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_1() {
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains(instance.getClass().getName()));
        assertTrue("'toString' should be correct.", res.contains("id:" + id));
        assertTrue("'toString' should be correct.", res.contains("name:" + name));
        assertTrue("'toString' should be correct.", res.contains("auctionCategory:" + auctionCategory));
        assertTrue("'toString' should be correct.", res.contains("applicationRoles:" + applicationRoles));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        instance = new ReviewAuctionType(0, null, null, null);

        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains(instance.getClass().getName()));
        assertTrue("'toString' should be correct.", res.contains("id:0"));
        assertTrue("'toString' should be correct.", res.contains("name:null"));
        assertTrue("'toString' should be correct.", res.contains("auctionCategory:null"));
        assertTrue("'toString' should be correct.", res.contains("applicationRoles:null"));
    }
}