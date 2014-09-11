/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link CompositeFilter}.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class CompositeFilterTest {
    /** Represents the compositeOperator used to test again. */
    private final CompositeOperator compositeOperatorValue = CompositeOperator.AND;

    /** Represents the projectFilters used to test again. */
    private final List<DirectProjectFilter> projectFiltersValue =
        new ArrayList<DirectProjectFilter>();

    /** Represents the instance used to test again. */
    private CompositeFilter testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new CompositeFilter();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link CompositeFilter#CompositeFilter()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCompositeFilter() throws Exception {
        new CompositeFilter();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link CompositeFilter#getCompositeOperator()}
     * </p>
     * <p>
     * The value of <code>compositeOperator</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getCompositeOperator() throws Exception {
        assertNull("Initial value of compositeOperator must be null.", testInstance.getCompositeOperator());
    }

    /**
     * <p>
     * Accuracy test {@link CompositeFilter#setCompositeOperator(CompositeOperator)}.
     * </p>
     * <p>
     * The value of <code>compositeOperator</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCompositeOperator() throws Exception {
        testInstance.setCompositeOperator(compositeOperatorValue);
        assertEquals("Property compositeOperator isn't set properly.",
                compositeOperatorValue, testInstance.getCompositeOperator());
    }

    /**
     * <p>
     * Accuracy test for {@link CompositeFilter#getProjectFilters()}
     * </p>
     * <p>
     * The value of <code>projectFilters</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProjectFilters() throws Exception {
        assertNull("Initial value of projectFilters must be null.", testInstance.getProjectFilters());
    }

    /**
     * <p>
     * Accuracy test {@link CompositeFilter#setProjectFilters(List)}.
     * </p>
     * <p>
     * The value of <code>projectFilters</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectFilters() throws Exception {
        testInstance.setProjectFilters(projectFiltersValue);
        assertEquals("Property projectFilters isn't set properly.",
                projectFiltersValue, testInstance.getProjectFilters());
    }

    /**
     * <p>
     * Accuracy test for {@link CompositeFilter#toJSONString()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_toJSONString() throws Exception {
        assertNotNull("Fail construct JSON", testInstance.toJSONString());
    }
}