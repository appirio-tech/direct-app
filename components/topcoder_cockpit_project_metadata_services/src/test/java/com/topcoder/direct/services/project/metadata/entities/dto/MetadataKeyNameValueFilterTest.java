/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link MetadataKeyNameValueFilter}.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class MetadataKeyNameValueFilterTest {
    /** Represents the metadataValue used to test again. */
    private final String metadataValueValue = "metadata";

    /** Represents the metadataValueOperator used to test again. */
    private final MetadataValueOperator metadataValueOperatorValue = MetadataValueOperator.EQUALS;

    /** Represents the projectMetadataKeyName used to test again. */
    private final String projectMetadataKeyNameValue = "project-metadata";

    /** Represents the instance used to test again. */
    private MetadataKeyNameValueFilter testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new MetadataKeyNameValueFilter();
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
     * Accuracy test for {@link MetadataKeyNameValueFilter#MetadataKeyNameValueFilter()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testMetadataKeyNameValueFilter() throws Exception {
        new MetadataKeyNameValueFilter();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link MetadataKeyNameValueFilter#getMetadataValue()}
     * </p>
     * <p>
     * The value of <code>metadataValue</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMetadataValue() throws Exception {
        assertNull("Initial value of metadataValue must be null.", testInstance.getMetadataValue());
    }

    /**
     * <p>
     * Accuracy test {@link MetadataKeyNameValueFilter#setMetadataValue(String)}.
     * </p>
     * <p>
     * The value of <code>metadataValue</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setMetadataValue() throws Exception {
        testInstance.setMetadataValue(metadataValueValue);
        assertEquals("Property metadataValue isn't set properly.", metadataValueValue, testInstance.getMetadataValue());
    }

    /**
     * <p>
     * Accuracy test for {@link MetadataKeyNameValueFilter#getMetadataValueOperator()}
     * </p>
     * <p>
     * The value of <code>metadataValueOperator</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMetadataValueOperator() throws Exception {
        assertNull("Initial value of metadataValueOperator must be null", testInstance.getMetadataValueOperator());
    }

    /**
     * <p>
     * Accuracy test {@link MetadataKeyNameValueFilter#setMetadataValueOperator(MetadataValueOperator)}.
     * </p>
     * <p>
     * The value of <code>metadataValueOperator</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setMetadataValueOperator() throws Exception {
        testInstance.setMetadataValueOperator(metadataValueOperatorValue);
        assertEquals("Property metadataValueOperator isn't set properly.",
                metadataValueOperatorValue, testInstance.getMetadataValueOperator());
    }

    /**
     * <p>
     * Accuracy test for {@link MetadataKeyNameValueFilter#getProjectMetadataKeyName()}
     * </p>
     * <p>
     * The value of <code>projectMetadataKeyName</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProjectMetadataKeyName() throws Exception {
        assertNull("Initial value of projectMetadataKeyName must be null.", testInstance.getProjectMetadataKeyName());
    }

    /**
     * <p>
     * Accuracy test {@link MetadataKeyNameValueFilter#setProjectMetadataKeyName(String)}.
     * </p>
     * <p>
     * The value of <code>projectMetadataKeyName</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectMetadataKeyName() throws Exception {
        testInstance.setProjectMetadataKeyName(projectMetadataKeyNameValue);
        assertEquals("Property projectMetadataKeyName isn't set properly.",
                projectMetadataKeyNameValue, testInstance.getProjectMetadataKeyName());
    }

    /**
     * <p>
     * Accuracy test for {@link MetadataKeyNameValueFilter#toJSONString()}.
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