/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.io.Serializable;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of ResponseSeverity. Tests the class for proper behavior.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class ResponseSeverityTests extends TestCase {
    /**
     * <p>
     * The name used for testing.
     * </p>
     */
    private static final String NAME = "test NAME";

    /**
     * <p>
     * The id used for testing.
     * </p>
     */
    private static final long ID = 123;

    /**
     * <p>
     * An instance of <code>ResponseSeverity</code> which is tested.
     * </p>
     */
    private ResponseSeverity target = null;

    /**
     * <p>
     * setUp() routine. Creates test ResponseSeverity instance.
     * </p>
     */
    protected void setUp() {
        this.target = new ResponseSeverity();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ResponseSeverity implements Serializable.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ResponseSeverity does not implement Serializable.",
                target instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ResponseSeverity()</code> Create for proper behavior.
     * </p>
     */
    public void testCreate_accuracy() {
        assertNotNull("Failed to initialize ResponseSeverity object.", target);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setId(long)</code> for proper behavior. Verify that id is
     * saved as is.
     * </p>
     */
    public void testSetId_accuracy() {
        target.setId(ID);
        assertEquals("id", ID, target.getId());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setId(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid id.
     * </p>
     */
    public void testSetId_1_failure() {
        try {
            target.setId(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setId(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid id.
     * </p>
     */
    public void testSetId_2_failure() {
        try {
            target.setId(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getId()</code> for proper behavior. Verify that id is
     * returned as is.
     * </p>
     */
    public void testGetId_accuracy() {
        target.setId(ID);
        assertEquals("id", ID, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setName(String)</code> for proper behavior. Verify that name
     * is saved as is.
     * </p>
     */
    public void testSetName_accuracy() {
        target.setName(NAME);
        assertEquals("name", NAME, target.getName());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setName(String)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid name.
     * </p>
     */
    public void testSetName_1_failure() {
        try {
            target.setName(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setName(String)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid name.
     * </p>
     */
    public void testSetName_2_failure() {
        try {
            target.setName("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getName()</code> for proper behavior. Verify that name is
     * returned as is.
     * </p>
     */
    public void testGetName_accuracy() {
        target.setName(NAME);
        assertEquals("name", NAME, target.getName());
    }
}
