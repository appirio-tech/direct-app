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
 * Unit tests for {@link PrizeType}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class PrizeTypeUnitTests extends TestCase {
    /**
     * Represents {@link PrizeType} instance for tests.
     */
    private PrizeType instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new PrizeType();
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
     * Accuracy test for {@link PrizeType#PrizeType()}.
     */
    @Test
    public void test_ctor() {
        assertNotNull("Unable to instantiate this object", instance);
        assertTrue("Should be true", instance instanceof Serializable);
    }

    /**
     * Accuracy test for {@link PrizeType#getId()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getId() {
        assertEquals("Should be 0", 0, instance.getId());
    }

    /**
     * Accuracy test for {@link PrizeType#setId(String)} method. The method should set the field value correctly.
     */
    @Test
    public void test_setId() {
        int value = 2;
        instance.setId(value);
        assertEquals("Should be the same", value, instance.getId());
    }

    /**
     * Failure test for {@link PrizeType#setId(String)} method. If the argument is non positive,
     * IllegalArgumentException is expected.
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
     * Accuracy test for {@link PrizeType#getDescription()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getDescription() {
        assertEquals("Should be null", null, instance.getDescription());
    }

    /**
     * Accuracy test for {@link PrizeType#setDescription(String)} method. The method should set the field value
     * correctly.
     */
    @Test
    public void test_setDescription() {
        String value = " newValue";
        instance.setDescription(value);
        assertEquals("Should be the same", value, instance.getDescription());
    }

    /**
     * Failure test for {@link PrizeType#setDescription(String)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setDescription_failure1() {
        try {
            instance.setDescription(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link PrizeType#setDescription(String)} method. If the argument is empty,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setDescription_failure2() {
        try {
            instance.setDescription(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

}
