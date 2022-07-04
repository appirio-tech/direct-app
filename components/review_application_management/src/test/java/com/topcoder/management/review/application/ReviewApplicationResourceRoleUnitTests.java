/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link ReviewApplicationResourceRole} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReviewApplicationResourceRoleUnitTests {
    /**
     * <p>
     * Represents the <code>ReviewApplicationResourceRole</code> instance used in tests.
     * </p>
     */
    private ReviewApplicationResourceRole instance;

    /**
     * <p>
     * Represents the resource role id used in tests.
     * </p>
     */
    private long resourceRoleId = 1;

    /**
     * <p>
     * Represents the resource role id used in tests.
     * </p>
     */
    private boolean uniqueRole = true;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewApplicationResourceRoleUnitTests.class);
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
        instance = new ReviewApplicationResourceRole(resourceRoleId, uniqueRole);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewApplicationResourceRole(long resourceRoleId,
     * boolean uniqueRole)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ReviewApplicationResourceRole(resourceRoleId, uniqueRole);

        assertEquals("'resourceRoleId' should be correct.",
            resourceRoleId, BaseUnitTests.getField(instance, "resourceRoleId"));
        assertEquals("'uniqueRole' should be correct.", uniqueRole, BaseUnitTests.getField(instance, "uniqueRole"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getResourceRoleId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getResourceRoleId() {
        assertEquals("'getResourceRoleId' should be correct.",
            resourceRoleId, instance.getResourceRoleId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>isUniqueRole()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_isUniqueRole() {
        assertTrue("'isUniqueRole' should be correct.", instance.isUniqueRole());
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
        assertTrue("'toString' should be correct.", res.contains("resourceRoleId:" + resourceRoleId));
        assertTrue("'toString' should be correct.", res.contains("uniqueRole:" + uniqueRole));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        instance = new ReviewApplicationResourceRole(0, false);

        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains(instance.getClass().getName()));
        assertTrue("'toString' should be correct.", res.contains("resourceRoleId:0"));
        assertTrue("'toString' should be correct.", res.contains("uniqueRole:false"));
    }
}