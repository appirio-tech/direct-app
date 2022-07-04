/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link Prize}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class PrizeUnitTests extends TestCase {
    /**
     * Represents {@link Prize} instance for tests.
     */
    private Prize instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new Prize();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for {@link Prize#Prize()}.
     */
    @Test
    public void test_ctor() {
        assertNotNull("Unable to instantiate this object", instance);
        assertTrue("Should be true", instance instanceof Serializable);
    }

    /**
     * Accuracy test for {@link Prize#getId()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getId() {
        assertEquals("Should be 0", 0, instance.getId());
    }

    /**
     * Accuracy test for {@link Prize#setId(String)} method. The method should set the field value correctly.
     */
    @Test
    public void test_setId() {
        int value = 2;
        instance.setId(value);
        assertEquals("Should be the same", value, instance.getId());
    }

    /**
     * Failure test for {@link Prize#setId(String)} method. If the argument is non positive, IllegalArgumentException is
     * expected.
     */
    @Test
    public void test_setId_failure1() {
        try {
            instance.setId(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
        try {
            instance.setId(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link Prize#getNumberOfSubmissions()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getNumberOfSubmissions() {
        assertEquals("Should be 1", 1, instance.getNumberOfSubmissions());
    }

    /**
     * Accuracy test for {@link Prize#setNumberOfSubmissions(int)} method. The method should set the field value
     * correctly.
     */
    @Test
    public void test_setNumberOfSubmissions() {
        int value = 2;
        instance.setNumberOfSubmissions(value);
        assertEquals("Should be the same", value, instance.getNumberOfSubmissions());
    }

    /**
     * Accuracy test for {@link Prize#getPrizeType()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getPrizeType() {
        assertEquals("Should be null", null, instance.getPrizeType());
    }

    /**
     * Accuracy test for {@link Prize#setPrizeType(PrizeType)} method. The method should set the field value correctly.
     */
    @Test
    public void test_setPrizeType() {
        PrizeType value = new PrizeType();
        instance.setPrizeType(value);
        assertEquals("Should be the same", value, instance.getPrizeType());
    }

    /**
     * Failure test for {@link PrizeType#setPrizeType(PrizeType)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setPrizeType_failure1() {
        try {
            instance.setPrizeType(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link Prize#getPrizeAmount()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getPrizeAmount() {
        assertEquals("Should be 0", 0, instance.getPrizeAmount(), 0.001);
    }

    /**
     * Accuracy test for {@link Prize#setPrizeAmount(double)} method. The method should set the field value correctly.
     */
    @Test
    public void test_setPrizeAmount() {
        int value = 2;
        instance.setPrizeAmount(value);
        assertEquals("Should be the same", value, instance.getPrizeAmount(), 0.001);
    }

    /**
     * Accuracy test for {@link Prize#getPlace()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getPlace() {
        assertEquals("Should be 0", 0, instance.getPlace());
    }

    /**
     * Accuracy test for {@link Prize#setPlace(int)} method. The method should set the field value correctly.
     */
    @Test
    public void test_setPlace() {
        int value = 2;
        instance.setPlace(value);
        assertEquals("Should be the same", value, instance.getPlace());
    }
}
