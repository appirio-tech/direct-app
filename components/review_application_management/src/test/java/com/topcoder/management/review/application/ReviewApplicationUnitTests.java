/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link ReviewApplication} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReviewApplicationUnitTests {
    /**
     * <p>
     * Represents the <code>ReviewApplication</code> instance used in tests.
     * </p>
     */
    private ReviewApplication instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewApplicationUnitTests.class);
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
        instance = new ReviewApplication();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewApplication()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ReviewApplication();

        assertEquals("'id' should be correct.", 0L, BaseUnitTests.getField(instance, "id"));
        assertEquals("'userId' should be correct.", 0L, BaseUnitTests.getField(instance, "userId"));
        assertEquals("'auctionId' should be correct.", 0L, BaseUnitTests.getField(instance, "auctionId"));
        assertEquals("'applicationRoleId' should be correct.",
            0L, BaseUnitTests.getField(instance, "applicationRoleId"));
        assertNull("'status' should be correct.", BaseUnitTests.getField(instance, "status"));
        assertNull("'createDate' should be correct.", BaseUnitTests.getField(instance, "createDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getId() {
        long value = 1;
        instance.setId(value);

        assertEquals("'getId' should be correct.", value, instance.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setId(long id)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setId() {
        long value = 1;
        instance.setId(value);

        assertEquals("'setId' should be correct.",
            value, BaseUnitTests.getField(instance, "id"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getUserId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUserId() {
        long value = 1L;
        instance.setUserId(value);

        assertEquals("'getUserId' should be correct.",
            value, instance.getUserId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserId(long userId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUserId() {
        long value = 1L;
        instance.setUserId(value);

        assertEquals("'setUserId' should be correct.",
            value, BaseUnitTests.getField(instance, "userId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuctionId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAuctionId() {
        long value = 1L;
        instance.setAuctionId(value);

        assertEquals("'getAuctionId' should be correct.",
            value, instance.getAuctionId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAuctionId(long auctionId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAuctionId() {
        long value = 1L;
        instance.setAuctionId(value);

        assertEquals("'setAuctionId' should be correct.",
            value, BaseUnitTests.getField(instance, "auctionId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getApplicationRoleId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getApplicationRoleId() {
        long value = 1L;
        instance.setApplicationRoleId(value);

        assertEquals("'getApplicationRoleId' should be correct.",
            value, instance.getApplicationRoleId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setApplicationRoleId(long applicationRoleId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setApplicationRoleId() {
        long value = 1L;
        instance.setApplicationRoleId(value);

        assertEquals("'setApplicationRoleId' should be correct.",
            value, BaseUnitTests.getField(instance, "applicationRoleId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getStatus()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getStatus() {
        ReviewApplicationStatus value = new ReviewApplicationStatus(1, "name1");
        instance.setStatus(value);

        assertSame("'getStatus' should be correct.",
            value, instance.getStatus());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStatus(ReviewApplicationStatus status)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setStatus() {
        ReviewApplicationStatus value = new ReviewApplicationStatus(1, "name1");
        instance.setStatus(value);

        assertSame("'setStatus' should be correct.",
            value, BaseUnitTests.getField(instance, "status"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCreateDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getCreateDate() {
        Date value = new Date();
        instance.setCreateDate(value);

        assertSame("'getCreateDate' should be correct.",
            value, instance.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCreateDate(Date createDate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setCreateDate() {
        Date value = new Date();
        instance.setCreateDate(value);

        assertSame("'setCreateDate' should be correct.",
            value, BaseUnitTests.getField(instance, "createDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_1() {
        instance.setId(1);
        instance.setUserId(2);
        instance.setAuctionId(3);
        instance.setApplicationRoleId(4);
        ReviewApplicationStatus status = new ReviewApplicationStatus(5, "status1");
        instance.setStatus(status);
        Date date = new Date();
        instance.setCreateDate(date);
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains(instance.getClass().getName()));
        assertTrue("'toString' should be correct.", res.contains("id:1"));
        assertTrue("'toString' should be correct.", res.contains("userId:2"));
        assertTrue("'toString' should be correct.", res.contains("auctionId:3"));
        assertTrue("'toString' should be correct.", res.contains("applicationRoleId:4"));
        assertTrue("'toString' should be correct.", res.contains("status:" + status));
        assertTrue("'toString' should be correct.", res.contains("createDate:" + date));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        instance = new ReviewApplication();

        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains(instance.getClass().getName()));
        assertTrue("'toString' should be correct.", res.contains("id:0"));
        assertTrue("'toString' should be correct.", res.contains("userId:0"));
        assertTrue("'toString' should be correct.", res.contains("auctionId:0"));
        assertTrue("'toString' should be correct.", res.contains("applicationRoleId:0"));
        assertTrue("'toString' should be correct.", res.contains("status:null"));
        assertTrue("'toString' should be correct.", res.contains("createDate:null"));
    }
}