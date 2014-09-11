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
 * Unit tests for the {@link DirectProjectMetadataDTO}.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class DirectProjectMetadataDTOTest {
    /** Represents the metadataValue used to test again. */
    private final String metadataValueValue = "metadata";

    /** Represents the projectMetadataKeyId used to test again. */
    private final long projectMetadataKeyIdValue = 1;

    /** Represents the instance used to test again. */
    private DirectProjectMetadataDTO testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new DirectProjectMetadataDTO();
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
     * Accuracy test for {@link DirectProjectMetadataDTO#DirectProjectMetadataDTO()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataDTO() throws Exception {
        new DirectProjectMetadataDTO();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataDTO#getMetadataValue()}
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
     * Accuracy test {@link DirectProjectMetadataDTO#setMetadataValue(String)}.
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
     * Accuracy test for {@link DirectProjectMetadataDTO#getProjectMetadataKeyId()}
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
     * Accuracy test {@link DirectProjectMetadataDTO#setProjectMetadataKeyId(long)}.
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
     * Accuracy test for {@link DirectProjectMetadataDTO#toJSONString()}.
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