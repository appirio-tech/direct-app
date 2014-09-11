/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.TestsHelper;

/**
 * <p>
 * Unit tests for {@link IdentifiableEntity} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class IdentifiableEntityUnitTests {
    /**
     * <p>
     * Represents the <code>IdentifiableEntity</code> instance used in tests.
     * </p>
     */
    private IdentifiableEntity instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(IdentifiableEntityUnitTests.class);
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
        instance = new MockIdentifiableEntity();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>IdentifiableEntity()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockIdentifiableEntity();

        assertEquals("'id' should be correct.", 0L, TestsHelper.getField(instance, "id"));
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
            value, TestsHelper.getField(instance, "id"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>equals(Object obj)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_equals_True1() {
        instance.setId(1);

        Object obj = instance;

        assertTrue("'equals' should be correct.", instance.equals(obj));
    }

    /**
     * <p>
     * Accuracy test for the method <code>equals(Object obj)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_equals_True2() {
        instance.setId(1);

        IdentifiableEntity obj = new MockIdentifiableEntity();
        obj.setId(1);

        assertTrue("'equals' should be correct.", instance.equals(obj));
    }

    /**
     * <p>
     * Accuracy test for the method <code>equals(Object obj)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_equals_False1() {
        instance.setId(1);

        IdentifiableEntity obj = null;

        assertFalse("'equals' should be correct.", instance.equals(obj));
    }

    /**
     * <p>
     * Accuracy test for the method <code>equals(Object obj)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_equals_False2() {
        instance.setId(1);

        IdentifiableEntity obj = new MockAuditableEntity();
        obj.setId(1);

        assertFalse("'equals' should be correct.", instance.equals(obj));
    }

    /**
     * <p>
     * Accuracy test for the method <code>equals(Object obj)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_equals_False3() {
        instance.setId(1);

        IdentifiableEntity obj = new MockIdentifiableEntity();
        obj.setId(2);

        assertFalse("'equals' should be correct.", instance.equals(obj));
    }

    /**
     * <p>
     * Accuracy test for the method <code>equals(Object obj)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_equals_False4() {
        instance.setId(1);

        Object obj = new Object();

        assertFalse("'equals' should be correct.", instance.equals(obj));
    }

    /**
     * <p>
     * Accuracy test for the method <code>hashCode()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_hashCode_1() {
        instance.setId(1);

        IdentifiableEntity obj = new MockIdentifiableEntity();
        obj.setId(1);

        assertTrue("'hashCode' should be correct.", obj.hashCode() == instance.hashCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>hashCode()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_hashCode_2() {
        instance.setId(1);

        IdentifiableEntity obj = new MockIdentifiableEntity();
        obj.setId(2);

        assertFalse("'hashCode' should be correct.", obj.hashCode() == instance.hashCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>hashCode()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_hashCode_3() {
        instance.setId(1);

        IdentifiableEntity obj = new MockAuditableEntity();
        obj.setId(1);

        assertFalse("'hashCode' should be correct.", obj.hashCode() == instance.hashCode());
    }
}