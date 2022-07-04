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
 * Unit tests for the {@link MetadataKeyIdValueFilter}.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class MetadataKeyIdValueFilterTest {
    /** Represents the metadataValue used to test again. */
    private final String metadataValueValue = "metadata";

    /** Represents the metadataValueOperator used to test again. */
    private final MetadataValueOperator metadataValueOperatorValue = MetadataValueOperator.LIKE;

    /** Represents the projectMetadataKeyId used to test again. */
    private final long projectMetadataKeyIdValue = 1;

    /** Represents the instance used to test again. */
    private MetadataKeyIdValueFilter testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new MetadataKeyIdValueFilter();
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
     * Accuracy test for {@link MetadataKeyIdValueFilter#MetadataKeyIdValueFilter()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testMetadataKeyIdValueFilter() throws Exception {
        new MetadataKeyIdValueFilter();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link MetadataKeyIdValueFilter#getMetadataValue()}
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
     * Accuracy test {@link MetadataKeyIdValueFilter#setMetadataValue(String)}.
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
     * Accuracy test for {@link MetadataKeyIdValueFilter#getMetadataValueOperator()}
     * </p>
     * <p>
     * The value of <code>metadataValueOperator</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getMetadataValueOperator() throws Exception {
        assertNull("Initial value of metadataValueOperator must be null.", testInstance.getMetadataValueOperator());
    }

    /**
     * <p>
     * Accuracy test {@link MetadataKeyIdValueFilter#setMetadataValueOperator(MetadataValueOperator)}.
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
     * Accuracy test for {@link MetadataKeyIdValueFilter#getProjectMetadataKeyId()}
     * </p>
     * <p>
     * The value of <code>projectMetadataKeyId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProjectMetadataKeyId() throws Exception {
        assertEquals("Initial value of projectMetadataKeyId must be 0.", 0, testInstance.getProjectMetadataKeyId());
    }

    /**
     * <p>
     * Accuracy test {@link MetadataKeyIdValueFilter#setProjectMetadataKeyId(long)}.
     * </p>
     * <p>
     * The value of <code>projectMetadataKeyId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectMetadataKeyId() throws Exception {
        testInstance.setProjectMetadataKeyId(projectMetadataKeyIdValue);
        assertEquals("Property projectMetadataKeyId isn't set properly.",
                projectMetadataKeyIdValue, testInstance.getProjectMetadataKeyId());
    }

    /**
     * <p>
     * Accuracy test for {@link MetadataKeyIdValueFilter#toJSONString()}.
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