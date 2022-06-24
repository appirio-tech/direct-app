/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link AuditRecord} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AuditRecordUnitTests {
    /**
     * <p>
     * Represents the <code>AuditRecord</code> instance used in tests.
     * </p>
     */
    private AuditRecord instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AuditRecordUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new AuditRecord();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AuditRecord()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new AuditRecord();

        assertEquals("'id' should be correct.", 0L, BaseUnitTests.getField(instance, "id"));
        assertNull("'timestamp' should be correct.", BaseUnitTests.getField(instance, "timestamp"));
        assertEquals("'userId' should be correct.", 0L, BaseUnitTests.getField(instance, "userId"));
        assertNull("'action' should be correct.", BaseUnitTests.getField(instance, "action"));
        assertNull("'entityType' should be correct.", BaseUnitTests.getField(instance, "entityType"));
        assertEquals("'entityId' should be correct.", 0L, BaseUnitTests.getField(instance, "entityId"));
        assertNull("'oldValue' should be correct.", BaseUnitTests.getField(instance, "oldValue"));
        assertNull("'newValue' should be correct.", BaseUnitTests.getField(instance, "newValue"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getId() {
        long value = 1L;
        instance.setId(value);

        assertEquals("'getId' should be correct.",
            value, instance.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setId(long id)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setId() {
        long value = 1L;
        instance.setId(value);

        assertEquals("'setId' should be correct.",
            value, BaseUnitTests.getField(instance, "id"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTimestamp()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getTimestamp() {
        Date value = new Date();
        instance.setTimestamp(value);

        assertSame("'getTimestamp' should be correct.",
            value, instance.getTimestamp());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTimestamp(Date timestamp)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setTimestamp() {
        Date value = new Date();
        instance.setTimestamp(value);

        assertSame("'setTimestamp' should be correct.",
            value, BaseUnitTests.getField(instance, "timestamp"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUserId() {
        long value = 1L;
        instance.setUserId(value);

        assertEquals("'getUserId' should be correct.",
            value, instance.getUserId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserId(long userId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUserId() {
        long value = 1L;
        instance.setUserId(value);

        assertEquals("'setUserId' should be correct.",
            value, BaseUnitTests.getField(instance, "userId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAction()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAction() {
        String value = "new_value";
        instance.setAction(value);

        assertEquals("'getAction' should be correct.",
            value, instance.getAction());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAction(String action)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAction() {
        String value = "new_value";
        instance.setAction(value);

        assertEquals("'setAction' should be correct.",
            value, BaseUnitTests.getField(instance, "action"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEntityType()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getEntityType() {
        String value = "new_value";
        instance.setEntityType(value);

        assertEquals("'getEntityType' should be correct.",
            value, instance.getEntityType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEntityType(String entityType)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setEntityType() {
        String value = "new_value";
        instance.setEntityType(value);

        assertEquals("'setEntityType' should be correct.",
            value, BaseUnitTests.getField(instance, "entityType"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEntityId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getEntityId() {
        long value = 1L;
        instance.setEntityId(value);

        assertEquals("'getEntityId' should be correct.",
            value, instance.getEntityId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEntityId(long entityId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setEntityId() {
        long value = 1L;
        instance.setEntityId(value);

        assertEquals("'setEntityId' should be correct.",
            value, BaseUnitTests.getField(instance, "entityId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getOldValue()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getOldValue() {
        String value = "new_value";
        instance.setOldValue(value);

        assertEquals("'getOldValue' should be correct.",
            value, instance.getOldValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setOldValue(String oldValue)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setOldValue() {
        String value = "new_value";
        instance.setOldValue(value);

        assertEquals("'setOldValue' should be correct.",
            value, BaseUnitTests.getField(instance, "oldValue"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getNewValue()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getNewValue() {
        String value = "new_value";
        instance.setNewValue(value);

        assertEquals("'getNewValue' should be correct.",
            value, instance.getNewValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setNewValue(String newValue)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setNewValue() {
        String value = "new_value";
        instance.setNewValue(value);

        assertEquals("'setNewValue' should be correct.",
            value, BaseUnitTests.getField(instance, "newValue"));
    }
}