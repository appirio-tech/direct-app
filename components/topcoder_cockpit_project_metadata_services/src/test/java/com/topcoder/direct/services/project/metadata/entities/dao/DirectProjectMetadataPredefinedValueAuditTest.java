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
 * Unit tests for the {@link DirectProjectMetadataPredefinedValueAudit}.
 * </p>
 *
 * @author  CaDenza
 * @version 1.0
 */
public class DirectProjectMetadataPredefinedValueAuditTest {
    /** Represents the position used to test again. */
    private final int positionValue = 1;

    /** Represents the predefinedMetadataValue used to test again. */
    private final String predefinedMetadataValueValue = "metadata";

    /** Represents the projectMetadataKeyId used to test again. */
    private final long projectMetadataKeyIdValue = 1;

    /** Represents the projectMetadataPredefinedValueId used to test again. */
    private final long projectMetadataPredefinedValueIdValue = 1;

    /** Represents the instance used to test again. */
    private DirectProjectMetadataPredefinedValueAudit testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new DirectProjectMetadataPredefinedValueAudit();
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
     * Accuracy test for {@link DirectProjectMetadataPredefinedValueAudit#DirectProjectMetadataPredefinedValueAudit()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataPredefinedValueAudit() throws Exception {
        new DirectProjectMetadataPredefinedValueAudit();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataPredefinedValueAudit#getPosition()}
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
     * Accuracy test {@link DirectProjectMetadataPredefinedValueAudit#setPosition(int)}.
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
     * Accuracy test for {@link DirectProjectMetadataPredefinedValueAudit#getPredefinedMetadataValue()}
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
     * Accuracy test {@link DirectProjectMetadataPredefinedValueAudit#setPredefinedMetadataValue(String)}.
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
     * Accuracy test for {@link DirectProjectMetadataPredefinedValueAudit#getProjectMetadataKeyId()}
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
     * Accuracy test {@link DirectProjectMetadataPredefinedValueAudit#setProjectMetadataKeyId(long)}.
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
     * Accuracy test for {@link DirectProjectMetadataPredefinedValueAudit#getProjectMetadataPredefinedValueId()}
     * </p>
     * <p>
     * The value of <code>projectMetadataPredefinedValueId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProjectMetadataPredefinedValueId() throws Exception {
        assertEquals("Initial value of projectMetadataPredefinedValueId must be 0.", 0,
                testInstance.getProjectMetadataPredefinedValueId());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataPredefinedValueAudit#setProjectMetadataPredefinedValueId(long)}.
     * </p>
     * <p>
     * The value of <code>projectMetadataPredefinedValueId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectMetadataPredefinedValueId() throws Exception {
        testInstance.setProjectMetadataPredefinedValueId(projectMetadataPredefinedValueIdValue);
        assertEquals("Property projectMetadataPredefinedValueId isn't set properly.",
                projectMetadataPredefinedValueIdValue, testInstance.getProjectMetadataPredefinedValueId());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>DirectProjectMetadataPredefinedValueAudit</code> subclasses
     * <code>AuditEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("DirectProjectMetadataPredefinedValueAudit does not subclass AuditEntity.",
            DirectProjectMetadataPredefinedValueAudit.class.getSuperclass() == AuditEntity.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataPredefinedValueAudit#toJSONObject()}.
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