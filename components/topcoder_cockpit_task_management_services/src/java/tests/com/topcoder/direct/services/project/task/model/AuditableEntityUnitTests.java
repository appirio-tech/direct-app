/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.TestsHelper;

/**
 * <p>
 * Unit tests for {@link AuditableEntity} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AuditableEntityUnitTests {
    /**
     * <p>
     * Represents the <code>AuditableEntity</code> instance used in tests.
     * </p>
     */
    private AuditableEntity instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AuditableEntityUnitTests.class);
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
        instance = new MockAuditableEntity();
    }

    /**
     * <p>
     * <code>AuditableEntity</code> should be subclass of <code>IdentifiableEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("AuditableEntity should be subclass of IdentifiableEntity.",
            AuditableEntity.class.getSuperclass() == IdentifiableEntity.class);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AuditableEntity()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockAuditableEntity();

        assertNull("'createdDate' should be correct.", TestsHelper.getField(instance, "createdDate"));
        assertNull("'createdBy' should be correct.", TestsHelper.getField(instance, "createdBy"));
        assertNull("'lastModifiedDate' should be correct.", TestsHelper.getField(instance, "lastModifiedDate"));
        assertNull("'lastModifiedBy' should be correct.", TestsHelper.getField(instance, "lastModifiedBy"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getCreatedDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getCreatedDate() {
        Date value = new Date();
        instance.setCreatedDate(value);

        assertSame("'getCreatedDate' should be correct.",
            value, instance.getCreatedDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCreatedDate(Date createdDate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setCreatedDate() {
        Date value = new Date();
        instance.setCreatedDate(value);

        assertSame("'setCreatedDate' should be correct.",
            value, TestsHelper.getField(instance, "createdDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCreatedBy()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getCreatedBy() {
        String value = "new_value";
        instance.setCreatedBy(value);

        assertEquals("'getCreatedBy' should be correct.",
            value, instance.getCreatedBy());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCreatedBy(String createdBy)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setCreatedBy() {
        String value = "new_value";
        instance.setCreatedBy(value);

        assertEquals("'setCreatedBy' should be correct.",
            value, TestsHelper.getField(instance, "createdBy"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLastModifiedDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getLastModifiedDate() {
        Date value = new Date();
        instance.setLastModifiedDate(value);

        assertSame("'getLastModifiedDate' should be correct.",
            value, instance.getLastModifiedDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLastModifiedDate(Date lastModifiedDate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setLastModifiedDate() {
        Date value = new Date();
        instance.setLastModifiedDate(value);

        assertSame("'setLastModifiedDate' should be correct.",
            value, TestsHelper.getField(instance, "lastModifiedDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLastModifiedBy()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getLastModifiedBy() {
        String value = "new_value";
        instance.setLastModifiedBy(value);

        assertEquals("'getLastModifiedBy' should be correct.",
            value, instance.getLastModifiedBy());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLastModifiedBy(String lastModifiedBy)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setLastModifiedBy() {
        String value = "new_value";
        instance.setLastModifiedBy(value);

        assertEquals("'setLastModifiedBy' should be correct.",
            value, TestsHelper.getField(instance, "lastModifiedBy"));
    }
}