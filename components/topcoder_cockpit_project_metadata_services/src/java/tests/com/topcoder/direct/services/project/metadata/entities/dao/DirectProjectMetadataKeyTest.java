/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link DirectProjectMetadataKey}.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class DirectProjectMetadataKeyTest {
    /** Represents the clientId used to test again. */
    private final Long clientIdValue = Long.MAX_VALUE;

    /** Represents the description used to test again. */
    private final String descriptionValue = "description";

    /** Represents the grouping used to test again. */
    private final Boolean groupingValue = Boolean.FALSE;

    /** Represents the name used to test again. */
    private final String nameValue = "name";

    /** Represents the predefinedValues used to test again. */
    private final List<DirectProjectMetadataPredefinedValue> predefinedValuesValue =
        new ArrayList<DirectProjectMetadataPredefinedValue>();

    /** Represents the single used to test again. */
    private final boolean singleValue = true;

    /** Represents the instance used to test again. */
    private DirectProjectMetadataKey testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new DirectProjectMetadataKey();
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
     * Accuracy test for {@link DirectProjectMetadataKey#DirectProjectMetadataKey()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataKey() throws Exception {
        new DirectProjectMetadataKey();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataKey#getClientId()}
     * </p>
     * <p>
     * The value of <code>clientId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getClientId() throws Exception {
        assertNull("Initial value of clientId must be null.", testInstance.getClientId());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataKey#setClientId(Long)}.
     * </p>
     * <p>
     * The value of <code>clientId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setClientId() throws Exception {
        testInstance.setClientId(clientIdValue);
        assertEquals("Property clientId isn't set properly.", clientIdValue, testInstance.getClientId());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataKey#getDescription()}
     * </p>
     * <p>
     * The value of <code>description</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getDescription() throws Exception {
        assertNull("Initial value of description must be null.", testInstance.getDescription());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataKey#setDescription(String)}.
     * </p>
     * <p>
     * The value of <code>description</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setDescription() throws Exception {
        testInstance.setDescription(descriptionValue);
        assertEquals("Property description isn't set properly.", descriptionValue, testInstance.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataKey#getGrouping()}
     * </p>
     * <p>
     * The value of <code>grouping</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getGrouping() throws Exception {
        assertNull("Initial value of grouping must be null.", testInstance.getGrouping());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataKey#setGrouping(Boolean)}.
     * </p>
     * <p>
     * The value of <code>grouping</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setGrouping() throws Exception {
        testInstance.setGrouping(groupingValue);
        assertEquals("Property grouping isn't set properly.", groupingValue, testInstance.getGrouping());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataKey#getName()}
     * </p>
     * <p>
     * The value of <code>name</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getName() throws Exception {
        assertNull("Initial value of name must be null.", testInstance.getName());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataKey#setName(String)}.
     * </p>
     * <p>
     * The value of <code>name</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setName() throws Exception {
        testInstance.setName(nameValue);
        assertEquals("Property name isn't set properly.", nameValue, testInstance.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataKey#getPredefinedValues()}
     * </p>
     * <p>
     * The value of <code>predefinedValues</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPredefinedValues() throws Exception {
        assertNull("Initial value of predefinedValues must be null.", testInstance.getPredefinedValues());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataKey#setPredefinedValues(List)}.
     * </p>
     * <p>
     * The value of <code>predefinedValues</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPredefinedValues() throws Exception {
        testInstance.setPredefinedValues(predefinedValuesValue);
        assertEquals("Property predefinedValues isn't set properly.",
                predefinedValuesValue, testInstance.getPredefinedValues());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataKey#isSingle()}
     * </p>
     * <p>
     * The value of <code>single</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_isSingle() throws Exception {
        assertFalse("Initial value of single must be false.", testInstance.isSingle());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataKey#setSingle(boolean)}.
     * </p>
     * <p>
     * The value of <code>single</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setSingle() throws Exception {
        testInstance.setSingle(singleValue);
        assertEquals("Property single isn't set properly.", singleValue, testInstance.isSingle());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>DirectProjectMetadataKey</code> subclasses
     * <code>IdentifiableEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("DirectProjectMetadataKey does not subclass IdentifiableEntity.",
            DirectProjectMetadataKey.class.getSuperclass() == IdentifiableEntity.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataKey#toJSONObject()}.
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