/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link ProjectPayment} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ProjectPaymentUnitTests {
    /**
     * <p>
     * Represents the <code>ProjectPayment</code> instance used in tests.
     * </p>
     */
    private ProjectPayment instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectPaymentUnitTests.class);
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
        instance = new ProjectPayment();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPayment()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ProjectPayment();

        assertNull("'projectPaymentId' should be correct.", BaseUnitTests.getField(instance, "projectPaymentId"));
        assertNull("'projectPaymentType' should be correct.", BaseUnitTests.getField(instance, "projectPaymentType"));
        assertNull("'resourceId' should be correct.", BaseUnitTests.getField(instance, "resourceId"));
        assertNull("'submissionId' should be correct.", BaseUnitTests.getField(instance, "submissionId"));
        assertNull("'amount' should be correct.", BaseUnitTests.getField(instance, "amount"));
        assertNull("'pactsPaymentId' should be correct.", BaseUnitTests.getField(instance, "pactsPaymentId"));
        assertNull("'createDate' should be correct.", BaseUnitTests.getField(instance, "createDate"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getProjectPaymentId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectPaymentId() {
        Long value = 1L;
        instance.setProjectPaymentId(value);

        assertEquals("'getProjectPaymentId' should be correct.",
            value, instance.getProjectPaymentId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectPaymentId(Long projectPaymentId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectPaymentId() {
        Long value = 1L;
        instance.setProjectPaymentId(value);

        assertEquals("'setProjectPaymentId' should be correct.",
            value, BaseUnitTests.getField(instance, "projectPaymentId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectPaymentType()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectPaymentType() {
        ProjectPaymentType value = new ProjectPaymentType();
        instance.setProjectPaymentType(value);

        assertSame("'getProjectPaymentType' should be correct.",
            value, instance.getProjectPaymentType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectPaymentType(ProjectPaymentType projectPaymentType)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectPaymentType() {
        ProjectPaymentType value = new ProjectPaymentType();
        instance.setProjectPaymentType(value);

        assertSame("'setProjectPaymentType' should be correct.",
            value, BaseUnitTests.getField(instance, "projectPaymentType"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getResourceId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getResourceId() {
        Long value = 1L;
        instance.setResourceId(value);

        assertEquals("'getResourceId' should be correct.",
            value, instance.getResourceId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setResourceId(Long resourceId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setResourceId() {
        Long value = 1L;
        instance.setResourceId(value);

        assertEquals("'setResourceId' should be correct.",
            value, BaseUnitTests.getField(instance, "resourceId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubmissionId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getSubmissionId() {
        Long value = 1L;
        instance.setSubmissionId(value);

        assertEquals("'getSubmissionId' should be correct.",
            value, instance.getSubmissionId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionId(Long submissionId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setSubmissionId() {
        Long value = 1L;
        instance.setSubmissionId(value);

        assertEquals("'setSubmissionId' should be correct.",
            value, BaseUnitTests.getField(instance, "submissionId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAmount()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAmount() {
        BigDecimal value = new BigDecimal(1);
        instance.setAmount(value);

        assertEquals("'getAmount' should be correct.",
            value.doubleValue(), instance.getAmount().doubleValue(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAmount(BigDecimal amount)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAmount() {
        BigDecimal value = new BigDecimal(1);
        instance.setAmount(value);

        assertEquals("'setAmount' should be correct.",
            value.doubleValue(), ((BigDecimal) BaseUnitTests.getField(instance, "amount")).doubleValue(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPactsPaymentId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPactsPaymentId() {
        Long value = 1L;
        instance.setPactsPaymentId(value);

        assertEquals("'getPactsPaymentId' should be correct.",
            value, instance.getPactsPaymentId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPactsPaymentId(Long pactsPaymentId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPactsPaymentId() {
        Long value = 1L;
        instance.setPactsPaymentId(value);

        assertEquals("'setPactsPaymentId' should be correct.",
            value, BaseUnitTests.getField(instance, "pactsPaymentId"));
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
}