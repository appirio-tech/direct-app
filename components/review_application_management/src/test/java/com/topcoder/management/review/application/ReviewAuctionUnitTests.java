/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link ReviewAuction} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReviewAuctionUnitTests {
    /**
     * <p>
     * Represents the <code>ReviewAuction</code> instance used in tests.
     * </p>
     */
    private ReviewAuction instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewAuctionUnitTests.class);
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
        instance = new ReviewAuction();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewAuction()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ReviewAuction();

        assertEquals("'id' should be correct.", 0L, BaseUnitTests.getField(instance, "id"));
        assertEquals("'projectId' should be correct.", 0L, BaseUnitTests.getField(instance, "projectId"));
        assertNull("'auctionType' should be correct.", BaseUnitTests.getField(instance, "auctionType"));
        assertFalse("'open' should be correct.", (Boolean) BaseUnitTests.getField(instance, "open"));
        assertNull("'openPositions' should be correct.", BaseUnitTests.getField(instance, "openPositions"));
        assertNull("'assignmentDate' should be correct.", BaseUnitTests.getField(instance, "assignmentDate"));
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
     * Accuracy test for the method <code>getProjectId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectId() {
        long value = 1L;
        instance.setProjectId(value);

        assertEquals("'getProjectId' should be correct.",
            value, instance.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectId(long projectId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectId() {
        long value = 1L;
        instance.setProjectId(value);

        assertEquals("'setProjectId' should be correct.",
            value, BaseUnitTests.getField(instance, "projectId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuctionType()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAuctionType() {
        List<ReviewApplicationRole> applicationRoles = Arrays.asList(new ReviewApplicationRole(1, "name1", 1, Arrays
            .asList(new ReviewApplicationResourceRole(1, true))));
        ReviewAuctionType value = new ReviewAuctionType(1, "name1", new ReviewAuctionCategory(1, "name1"),
            applicationRoles);
        instance.setAuctionType(value);

        assertSame("'getAuctionType' should be correct.", value, instance.getAuctionType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAuctionType(ReviewAuctionType auctionType)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAuctionType() {
        List<ReviewApplicationRole> applicationRoles = Arrays.asList(new ReviewApplicationRole(1, "name1", 1, Arrays
            .asList(new ReviewApplicationResourceRole(1, true))));
        ReviewAuctionType value = new ReviewAuctionType(1, "name1", new ReviewAuctionCategory(1, "name1"),
            applicationRoles);
        instance.setAuctionType(value);

        assertSame("'setAuctionType' should be correct.",
            value, BaseUnitTests.getField(instance, "auctionType"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>isOpen()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_isOpen() {
        boolean value = true;
        instance.setOpen(value);

        assertTrue("'isOpen' should be correct.", instance.isOpen());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setOpen(boolean open)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setOpen() {
        boolean value = true;
        instance.setOpen(value);

        assertTrue("'setOpen' should be correct.",
            (Boolean) BaseUnitTests.getField(instance, "open"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getOpenPositions()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getOpenPositions() {
        List<Long> value = new ArrayList<Long>();
        instance.setOpenPositions(value);

        assertSame("'getOpenPositions' should be correct.",
            value, instance.getOpenPositions());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setOpenPositions(List&lt;Long&gt; openPositions)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setOpenPositions() {
        List<Long> value = new ArrayList<Long>();
        instance.setOpenPositions(value);

        assertSame("'setOpenPositions' should be correct.",
            value, BaseUnitTests.getField(instance, "openPositions"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssignmentDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAssignmentDate() {
        Date value = new Date();
        instance.setAssignmentDate(value);

        assertSame("'getAssignmentDate' should be correct.",
            value, instance.getAssignmentDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssignmentDate(Date assignmentDate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssignmentDate() {
        Date value = new Date();
        instance.setAssignmentDate(value);

        assertSame("'setAssignmentDate' should be correct.",
            value, BaseUnitTests.getField(instance, "assignmentDate"));
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
        instance.setProjectId(2);
        ReviewAuctionType auctionType = new ReviewAuctionType(3, "type1", null, null);
        instance.setAuctionType(auctionType);
        instance.setOpen(true);
        List<Long> openPositions = Arrays.asList(1L, 2L);
        instance.setOpenPositions(openPositions);
        Date date = new Date();
        instance.setAssignmentDate(date);
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains(instance.getClass().getName()));
        assertTrue("'toString' should be correct.", res.contains("id:1"));
        assertTrue("'toString' should be correct.", res.contains("projectId:2"));
        assertTrue("'toString' should be correct.", res.contains("auctionType:" + auctionType));
        assertTrue("'toString' should be correct.", res.contains("open:true"));
        assertTrue("'toString' should be correct.", res.contains("openPositions:" + openPositions));
        assertTrue("'toString' should be correct.", res.contains("assignmentDate:" + date));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        instance = new ReviewAuction();

        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains(instance.getClass().getName()));
        assertTrue("'toString' should be correct.", res.contains("id:0"));
        assertTrue("'toString' should be correct.", res.contains("projectId:0"));
        assertTrue("'toString' should be correct.", res.contains("auctionType:null"));
        assertTrue("'toString' should be correct.", res.contains("open:false"));
        assertTrue("'toString' should be correct.", res.contains("openPositions:null"));
        assertTrue("'toString' should be correct.", res.contains("assignmentDate:null"));
    }
}