/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link DirectProjectMetadata}.
 * </p>
 *
 * @author  CaDenza
 * @version 1.0
 */
public class DirectProjectMetadataTest {
    /** Represents the metadataValue used to test again. */
    private final String metadataValueValue = "metadata";

    /** Represents the projectMetadataKey used to test again. */
    private final DirectProjectMetadataKey projectMetadataKeyValue =
        new DirectProjectMetadataKey();

    /** Represents the tcDirectProjectId used to test again. */
    private final long tcDirectProjectIdValue = 1;

    /** Represents the instance used to test again. */
    private DirectProjectMetadata testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new DirectProjectMetadata();
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
     * Accuracy test for {@link DirectProjectMetadata#DirectProjectMetadata()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadata() throws Exception {
        new DirectProjectMetadata();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadata#getMetadataValue()}
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
     * Accuracy test {@link DirectProjectMetadata#setMetadataValue(String)}.
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
     * Accuracy test for {@link DirectProjectMetadata#getProjectMetadataKey()}
     * </p>
     * <p>
     * The value of <code>projectMetadataKey</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProjectMetadataKey() throws Exception {
        assertNull("Initial value of projectMetadataKey must be null.", testInstance.getProjectMetadataKey());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadata#setProjectMetadataKey(DirectProjectMetadataKey)}.
     * </p>
     * <p>
     * The value of <code>projectMetadataKey</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectMetadataKey() throws Exception {
        testInstance.setProjectMetadataKey(projectMetadataKeyValue);
        assertEquals("Property projectMetadataKey isn't set properly.",
                projectMetadataKeyValue, testInstance.getProjectMetadataKey());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadata#getTcDirectProjectId()}
     * </p>
     * <p>
     * The value of <code>tcDirectProjectId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getTcDirectProjectId() throws Exception {
        assertEquals("Initial value of tcDirectProjectId must be 0.", 0, testInstance.getTcDirectProjectId());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadata#setTcDirectProjectId(long)}.
     * </p>
     * <p>
     * The value of <code>tcDirectProjectId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setTcDirectProjectId() throws Exception {
        testInstance.setTcDirectProjectId(tcDirectProjectIdValue);
        assertEquals("Property tcDirectProjectId isn't set properly.",
                tcDirectProjectIdValue, testInstance.getTcDirectProjectId());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>DirectProjectMetadata</code> subclasses
     * <code>IdentifiableEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("DirectProjectMetadata does not subclass IdentifiableEntity.",
            DirectProjectMetadata.class.getSuperclass() == IdentifiableEntity.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadata#toJSONObject()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_toJSONObject() throws Exception {
        assertNotNull("Fail construct JSON", testInstance.toJSONString());
    }
}