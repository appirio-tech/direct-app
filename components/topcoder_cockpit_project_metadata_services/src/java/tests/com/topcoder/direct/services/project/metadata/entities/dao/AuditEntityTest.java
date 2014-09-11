/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link AuditEntity}.
 * </p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class AuditEntityTest {
    /** Represents the actionDate used to test again. */
    private final Date actionDateValue = new Date();

    /** Represents the actionUserId used to test again. */
    private final long actionUserIdValue = Long.MAX_VALUE;

    /** Represents the auditActionTypeId used to test again. */
    private final int auditActionTypeIdValue = Integer.MAX_VALUE;

    /** Represents the instance used to test again. */
    private AuditEntity testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new MockAuditEntity();
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
     * Accuracy test for {@link AuditEntity#AuditEntity()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testAuditEntity() throws Exception {
        new MockAuditEntity();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link AuditEntity#getActionDate()}
     * </p>
     * <p>
     * The value of <code>actionDate</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getActionDate() throws Exception {
        assertNull("Initial value of actionDate must be null.", testInstance.getActionDate());
    }

    /**
     * <p>
     * Accuracy test {@link AuditEntity#setActionDate(Date)}.
     * </p>
     * <p>
     * The value of <code>actionDate</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setActionDate() throws Exception {
        testInstance.setActionDate(actionDateValue);
        assertEquals("Property actionDate isn't set properly.", actionDateValue, testInstance.getActionDate());
    }

    /**
     * <p>
     * Accuracy test for {@link AuditEntity#getActionUserId()}
     * </p>
     * <p>
     * The value of <code>actionUserId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getActionUserId() throws Exception {
        assertEquals("Initial value of actionUserId must be 0.", 0, testInstance.getActionUserId());
    }

    /**
     * <p>
     * Accuracy test {@link AuditEntity#setActionUserId(long)}.
     * </p>
     * <p>
     * The value of <code>actionUserId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setActionUserId() throws Exception {
        testInstance.setActionUserId(actionUserIdValue);
        assertEquals("Property actionUserId isn't set properly.", actionUserIdValue, testInstance.getActionUserId());
    }

    /**
     * <p>
     * Accuracy test for {@link AuditEntity#getAuditActionTypeId()}
     * </p>
     * <p>
     * The value of <code>auditActionTypeId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAuditActionTypeId() throws Exception {
        assertEquals("Initial value of auditActionTypeId must be 0.", 0, testInstance.getAuditActionTypeId());
    }

    /**
     * <p>
     * Accuracy test {@link AuditEntity#setAuditActionTypeId(int)}.
     * </p>
     * <p>
     * The value of <code>auditActionTypeId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAuditActionTypeId() throws Exception {
        testInstance.setAuditActionTypeId(auditActionTypeIdValue);
        assertEquals("Property auditActionTypeId isn't set properly.",
                auditActionTypeIdValue, testInstance.getAuditActionTypeId());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>AuditEntity</code> subclasses
     * <code>IdentifiableEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("AuditEntity does not subclass IdentifiableEntity.",
            AuditEntity.class.getSuperclass() == IdentifiableEntity.class);
    }

    /**
     * <p>
     * Accuracy test for {@link AuditEntity#toJSONObject()}.
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