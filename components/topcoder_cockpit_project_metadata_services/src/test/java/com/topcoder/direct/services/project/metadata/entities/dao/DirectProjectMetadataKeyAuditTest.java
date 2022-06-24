/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link DirectProjectMetadataKeyAudit}.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class DirectProjectMetadataKeyAuditTest {
    /** Represents the clientId used to test again. */
    private final Long clientIdValue = Long.MAX_VALUE;

    /** Represents the description used to test again. */
    private final String descriptionValue = "description";

    /** Represents the grouping used to test again. */
    private final Boolean groupingValue = Boolean.FALSE;

    /** Represents the name used to test again. */
    private final String nameValue = "name";

    /** Represents the projectMetadataKeyId used to test again. */
    private final long projectMetadataKeyIdValue = Long.MIN_VALUE;

    /** Represents the single used to test again. */
    private final boolean singleValue = true;

    /** Represents the instance used to test again. */
    private DirectProjectMetadataKeyAudit testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new DirectProjectMetadataKeyAudit();
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
     * Accuracy test for {@link DirectProjectMetadataKeyAudit#DirectProjectMetadataKeyAudit()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataKeyAudit() throws Exception {
        new DirectProjectMetadataKeyAudit();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataKeyAudit#getClientId()}
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
     * Accuracy test {@link DirectProjectMetadataKeyAudit#setClientId(Long)}.
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
     * Accuracy test for {@link DirectProjectMetadataKeyAudit#getDescription()}
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
     * Accuracy test {@link DirectProjectMetadataKeyAudit#setDescription(String)}.
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
     * Accuracy test for {@link DirectProjectMetadataKeyAudit#getGrouping()}
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
     * Accuracy test {@link DirectProjectMetadataKeyAudit#setGrouping(Boolean)}.
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
     * Accuracy test for {@link DirectProjectMetadataKeyAudit#getName()}
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
     * Accuracy test {@link DirectProjectMetadataKeyAudit#setName(String)}.
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
     * Accuracy test for {@link DirectProjectMetadataKeyAudit#getProjectMetadataKeyId()}
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
     * Accuracy test {@link DirectProjectMetadataKeyAudit#setProjectMetadataKeyId(long)}.
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
     * Accuracy test for {@link DirectProjectMetadataKeyAudit#isSingle()}
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
     * Accuracy test {@link DirectProjectMetadataKeyAudit#setSingle(boolean)}.
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
     * Inheritance test, verifies <code>DirectProjectMetadataKeyAudit</code> subclasses
     * <code>AuditEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("DirectProjectMetadataKeyAudit does not subclass AuditEntity.",
            DirectProjectMetadataKeyAudit.class.getSuperclass() == AuditEntity.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataKeyAudit#toJSONObject()}.
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