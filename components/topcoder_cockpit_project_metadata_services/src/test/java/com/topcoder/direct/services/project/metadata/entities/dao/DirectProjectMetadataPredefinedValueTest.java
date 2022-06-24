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
 * Unit tests for the {@link DirectProjectMetadataPredefinedValue}.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class DirectProjectMetadataPredefinedValueTest {
    /** Represents the position used to test again. */
    private final int positionValue = 1;

    /** Represents the predefinedMetadataValue used to test again. */
    private final String predefinedMetadataValueValue = "metadata";

    /** Represents the projectMetadataKey used to test again. */
    private final DirectProjectMetadataKey projectMetadataKeyValue = new DirectProjectMetadataKey();

    /** Represents the instance used to test again. */
    private DirectProjectMetadataPredefinedValue testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new DirectProjectMetadataPredefinedValue();
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
     * Accuracy test for {@link DirectProjectMetadataPredefinedValue#DirectProjectMetadataPredefinedValue()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataPredefinedValue() throws Exception {
        new DirectProjectMetadataPredefinedValue();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataPredefinedValue#getPosition()}
     * </p>
     * <p>
     * The value of <code>position</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPosition() throws Exception {
        assertEquals("Initial value of position must be 0.", 0, testInstance.getPosition());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataPredefinedValue#setPosition(int)}.
     * </p>
     * <p>
     * The value of <code>position</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPosition() throws Exception {
        testInstance.setPosition(positionValue);
        assertEquals("Property position isn't set properly.", positionValue, testInstance.getPosition());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataPredefinedValue#getPredefinedMetadataValue()}
     * </p>
     * <p>
     * The value of <code>predefinedMetadataValue</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPredefinedMetadataValue() throws Exception {
        assertNull("Initial value of predefinedMetadataValue must be null.", testInstance.getPredefinedMetadataValue());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataPredefinedValue#setPredefinedMetadataValue(String)}.
     * </p>
     * <p>
     * The value of <code>predefinedMetadataValue</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPredefinedMetadataValue() throws Exception {
        testInstance.setPredefinedMetadataValue(predefinedMetadataValueValue);
        assertEquals("Property predefinedMetadataValue isn't set properly.",
                predefinedMetadataValueValue, testInstance.getPredefinedMetadataValue());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataPredefinedValue#getProjectMetadataKey()}
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
     * Accuracy test {@link DirectProjectMetadataPredefinedValue#setProjectMetadataKey(DirectProjectMetadataKey)}.
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
     * Inheritance test, verifies <code>DirectProjectMetadataPredefinedValue</code> subclasses
     * <code>IdentifiableEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("DirectProjectMetadataPredefinedValue does not subclass IdentifiableEntity.",
            DirectProjectMetadataPredefinedValue.class.getSuperclass() == IdentifiableEntity.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataPredefinedValue#toJSONObject()}.
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