/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link ProjectPaymentAdjustment} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ProjectPaymentAdjustmentUnitTests {
    /**
     * <p>
     * Represents the <code>ProjectPaymentAdjustment</code> instance used in tests.
     * </p>
     */
    private ProjectPaymentAdjustment instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectPaymentAdjustmentUnitTests.class);
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
        instance = new ProjectPaymentAdjustment();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPaymentAdjustment()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ProjectPaymentAdjustment();

        assertNull("'projectId' should be correct.", BaseUnitTests.getField(instance, "projectId"));
        assertNull("'resourceRoleId' should be correct.", BaseUnitTests.getField(instance, "resourceRoleId"));
        assertNull("'fixedAmount' should be correct.", BaseUnitTests.getField(instance, "fixedAmount"));
        assertNull("'multiplier' should be correct.", BaseUnitTests.getField(instance, "multiplier"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getProjectId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectId() {
        Long value = 1L;
        instance.setProjectId(value);

        assertEquals("'getProjectId' should be correct.",
            value, instance.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectId(Long projectId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectId() {
        Long value = 1L;
        instance.setProjectId(value);

        assertEquals("'setProjectId' should be correct.",
            value, BaseUnitTests.getField(instance, "projectId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getResourceRoleId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getResourceRoleId() {
        Long value = 1L;
        instance.setResourceRoleId(value);

        assertEquals("'getResourceRoleId' should be correct.",
            value, instance.getResourceRoleId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setResourceRoleId(Long resourceRoleId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setResourceRoleId() {
        Long value = 1L;
        instance.setResourceRoleId(value);

        assertEquals("'setResourceRoleId' should be correct.",
            value, BaseUnitTests.getField(instance, "resourceRoleId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFixedAmount()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getFixedAmount() {
        BigDecimal value = new BigDecimal(1);
        instance.setFixedAmount(value);

        assertEquals("'getFixedAmount' should be correct.",
            value.doubleValue(), instance.getFixedAmount().doubleValue(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFixedAmount(BigDecimal fixedAmount)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setFixedAmount() {
        BigDecimal value = new BigDecimal(1);
        instance.setFixedAmount(value);

        assertEquals("'setFixedAmount' should be correct.",
            value.doubleValue(), ((BigDecimal) BaseUnitTests.getField(instance, "fixedAmount")).doubleValue(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getMultiplier()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getMultiplier() {
        Double value = 1.0D;
        instance.setMultiplier(value);

        assertEquals("'getMultiplier' should be correct.",
            value, instance.getMultiplier(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setMultiplier(double multiplier)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setMultiplier() {
        Double value = 1.0D;
        instance.setMultiplier(value);

        assertEquals("'setMultiplier' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "multiplier"), 0.001);
    }
}