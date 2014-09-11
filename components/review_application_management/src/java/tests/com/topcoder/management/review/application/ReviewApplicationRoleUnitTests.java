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
 * Unit tests for {@link ReviewApplicationRole} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReviewApplicationRoleUnitTests {
    /**
     * <p>
     * Represents the <code>ReviewApplicationRole</code> instance used in tests.
     * </p>
     */
    private ReviewApplicationRole instance;

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
     * Represents the positions used in tests.
     * </p>
     */
    private long positions = 2;

    /**
     * <p>
     * Represents the resource roles used in tests.
     * </p>
     */
    private List<ReviewApplicationResourceRole> resourceRoles;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewApplicationRoleUnitTests.class);
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
        resourceRoles = Arrays.asList(new ReviewApplicationResourceRole(1, false), new ReviewApplicationResourceRole(2,
            true));

        instance = new ReviewApplicationRole(id, name, positions, resourceRoles);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewApplicationRole(long id, String name, long positions,
     * List&lt;ReviewApplicationResourceRole&gt; resourceRoles)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ReviewApplicationRole(id, name, positions, resourceRoles);

        assertEquals("'id' should be correct.", id, BaseUnitTests.getField(instance, "id"));
        assertEquals("'name' should be correct.", name, BaseUnitTests.getField(instance, "name"));
        assertEquals("'positions' should be correct.", positions, BaseUnitTests.getField(instance, "positions"));
        assertSame("'resourceRoles' should be correct.",
            resourceRoles, BaseUnitTests.getField(instance, "resourceRoles"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getPositions()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPositions() {
        assertEquals("'getPositions' should be correct.",
            positions, instance.getPositions());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getResourceRoles()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getResourceRoles() {
        assertSame("'getResourceRoles' should be correct.",
            resourceRoles, instance.getResourceRoles());
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
        assertTrue("'toString' should be correct.", res.contains("positions:" + positions));
        assertTrue("'toString' should be correct.", res.contains("resourceRoles:" + resourceRoles));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        instance = new ReviewApplicationRole(0, null, 0, null);

        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains(instance.getClass().getName()));
        assertTrue("'toString' should be correct.", res.contains("id:0"));
        assertTrue("'toString' should be correct.", res.contains("name:null"));
        assertTrue("'toString' should be correct.", res.contains("positions:0"));
        assertTrue("'toString' should be correct.", res.contains("resourceRoles:null"));
    }
}