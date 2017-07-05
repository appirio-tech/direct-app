/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link ProjectPaymentType} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ProjectPaymentTypeUnitTests {
    /**
     * <p>
     * Represents the <code>ProjectPaymentType</code> instance used in tests.
     * </p>
     */
    private ProjectPaymentType instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectPaymentTypeUnitTests.class);
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
        instance = new ProjectPaymentType();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPaymentType()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ProjectPaymentType();

        assertNull("'projectPaymentTypeId' should be correct.",
            BaseUnitTests.getField(instance, "projectPaymentTypeId"));
        assertNull("'name' should be correct.", BaseUnitTests.getField(instance, "name"));
        assertFalse("'mergeable' should be correct.", (Boolean) BaseUnitTests.getField(instance, "mergeable"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getProjectPaymentTypeId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectPaymentTypeId() {
        Long value = 1L;
        instance.setProjectPaymentTypeId(value);

        assertEquals("'getProjectPaymentTypeId' should be correct.",
            value, instance.getProjectPaymentTypeId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectPaymentTypeId(Long projectPaymentTypeId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectPaymentTypeId() {
        Long value = 1L;
        instance.setProjectPaymentTypeId(value);

        assertEquals("'setProjectPaymentTypeId' should be correct.",
            value, BaseUnitTests.getField(instance, "projectPaymentTypeId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getName()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getName() {
        String value = "new_value";
        instance.setName(value);

        assertEquals("'getName' should be correct.",
            value, instance.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setName(String name)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setName() {
        String value = "new_value";
        instance.setName(value);

        assertEquals("'setName' should be correct.",
            value, BaseUnitTests.getField(instance, "name"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>isMergeable()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_isMergeable() {
        boolean value = true;
        instance.setMergeable(value);

        assertTrue("'isMergeable' should be correct.", instance.isMergeable());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setMergeable(boolean mergeable)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setMergeable() {
        boolean value = true;
        instance.setMergeable(value);

        assertTrue("'setMergeable' should be correct.",
            (Boolean) BaseUnitTests.getField(instance, "mergeable"));
    }
}