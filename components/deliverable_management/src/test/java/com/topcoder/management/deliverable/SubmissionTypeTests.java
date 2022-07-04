/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link SubmissionType}</code> class.
 * </p>
 *
 * @author sparemax
 * @version 1.1
 * @since 1.1
 */
public class SubmissionTypeTests extends TestCase {
    /**
     * <p>
     * Represents the <code>SubmissionType</code> instance used in tests.
     * </p>
     */
    private SubmissionType instance;

    /**
     * <p>
     * Represents the id used in tests.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * Represents the name used in tests.
     * </p>
     */
    private String name = "name";

    /**
     * <p>
     * Accuracy test for the constructor <code>SubmissionType()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor1() {
        instance = new SubmissionType();

        assertEquals("'id' should be correct.", SubmissionType.UNSET_ID, instance.getId());
        assertNull("'name' should be correct.", instance.getName());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>SubmissionType(long id)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor2() {
        instance = new SubmissionType(id);

        assertEquals("'id' should be correct.", id, instance.getId());
        assertNull("'name' should be correct.", instance.getName());
    }

    /**
     * <p>
     * Failure test for the constructor <code>SubmissionType(long id)</code> with id is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor2_idZero() {
        id = 0;

        try {
            new SubmissionType(id);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the constructor <code>SubmissionType(long id)</code> with id is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor2_idNegative() {
        id = -1;

        try {
            new SubmissionType(id);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>SubmissionType(long id, String name)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor3() {
        instance = new SubmissionType(id, name);

        assertEquals("'id' should be correct.", id, instance.getId());
        assertEquals("'name' should be correct.", name, instance.getName());
    }

    /**
     * <p>
     * Failure test for the constructor <code>SubmissionType(long id, String name)</code> with id is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor3_idZero() {
        id = 0;

        try {
            new SubmissionType(id, name);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the constructor <code>SubmissionType(long id, String name)</code> with id is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor3_idNegative() {
        id = -1;

        try {
            new SubmissionType(id, name);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }
}
