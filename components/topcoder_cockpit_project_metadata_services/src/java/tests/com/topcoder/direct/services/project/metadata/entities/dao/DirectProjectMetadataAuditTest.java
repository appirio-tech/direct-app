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
 * Unit tests for the {@link DirectProjectMetadataAudit}.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class DirectProjectMetadataAuditTest {
    /** Represents the metadataValue used to test again. */
    private final String metadataValueValue = "test";

    /** Represents the projectMetadataId used to test again. */
    private final long projectMetadataIdValue = Long.MAX_VALUE;

    /** Represents the projectMetadataKeyId used to test again. */
    private final long projectMetadataKeyIdValue = Long.MAX_VALUE;

    /** Represents the tcDirectProjectId used to test again. */
    private final long tcDirectProjectIdValue = Long.MAX_VALUE;

    /** Represents the instance used to test again. */
    private DirectProjectMetadataAudit testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new DirectProjectMetadataAudit();
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
     * Accuracy test for {@link DirectProjectMetadataAudit#DirectProjectMetadataAudit()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProjectMetadataAudit() throws Exception {
        new DirectProjectMetadataAudit();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataAudit#getMetadataValue()}
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
     * Accuracy test {@link DirectProjectMetadataAudit#setMetadataValue(String)}.
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
     * Accuracy test for {@link DirectProjectMetadataAudit#getProjectMetadataId()}
     * </p>
     * <p>
     * The value of <code>projectMetadataId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProjectMetadataId() throws Exception {
        assertEquals("Initial value of projectMetadataId must be 0.", 0, testInstance.getProjectMetadataId());
    }

    /**
     * <p>
     * Accuracy test {@link DirectProjectMetadataAudit#setProjectMetadataId(long)}.
     * </p>
     * <p>
     * The value of <code>projectMetadataId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectMetadataId() throws Exception {
        testInstance.setProjectMetadataId(projectMetadataIdValue);
        assertEquals("Property projectMetadataId isn't set properly.",
                projectMetadataIdValue, testInstance.getProjectMetadataId());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataAudit#getProjectMetadataKeyId()}
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
     * Accuracy test {@link DirectProjectMetadataAudit#setProjectMetadataKeyId(long)}.
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
     * Accuracy test for {@link DirectProjectMetadataAudit#getTcDirectProjectId()}
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
     * Accuracy test {@link DirectProjectMetadataAudit#setTcDirectProjectId(long)}.
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
     * Inheritance test, verifies <code>DirectProjectMetadataAudit</code> subclasses
     * <code>AuditEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("DirectProjectMetadataAudit does not subclass AuditEntity.",
            DirectProjectMetadataAudit.class.getSuperclass() == AuditEntity.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectMetadataAudit#toJSONObject()}.
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