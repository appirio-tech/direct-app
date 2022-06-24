/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import junit.framework.TestCase;

/**
 * Unit test case for <code>PhaseType</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class PhaseTypeUnitTests extends TestCase {
    /**
     * Represents the id.
     */
    private static final long ID = 7L;

    /**
     * Represents another id.
     */
    private static final long ANOTHER_ID = 8L;

    /**
     * Represents the name.
     */
    private static final String NAME = "TC";

    /**
     * Represents another name.
     */
    private static final String ANOTHER_NAME = "topcoder";

    /**
     * A <code>PhaseType</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private PhaseType type;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        type = new PhaseType(ID, NAME);
    }

    /**
     * Failure test of constructor.
     * <p>
     * With negative id.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testConstructorFailureWithNegativeId() {
        try {
            new PhaseType(-1, NAME);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of constructor.
     * <p>
     * With null name.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testConstructorFailureWithNullName() {
        try {
            new PhaseType(ID, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of constructor.
     * <p>
     * With valid id and name.
     * </p>
     * <p>
     * Should create the instance successfully.
     * </p>
     */
    public void testConstructorAccuracy() {
        assertNotNull("Should create the instance successfully.", type);
    }

    /**
     * Accuracy test of <code>getId</code>.
     * <p>
     * With valid id.
     * </p>
     * <p>
     * Should return the proper id.
     * </p>
     */
    public void testGetIdAccuracy() {
        assertEquals("Should return the proper id.", ID, type.getId());
    }

    /**
     * Failure test of <code>setId</code>.
     * <p>
     * With negative id.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetIdFailureWithNegativeId() {
        try {
            type.setId(-1);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>setId</code>.
     * <p>
     * With valid id.
     * </p>
     * <p>
     * Should set the id.
     * </p>
     */
    public void testSetIdAccuracy() {
        type.setId(ANOTHER_ID);
        assertEquals("Should set the id.", ANOTHER_ID, type.getId());
    }

    /**
     * Failure test of <code>setName</code>.
     * <p>
     * With null name.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetNameFailureWithNullName() {
        try {
            type.setName(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>setName</code>.
     * <p>
     * With non empty valid name.
     * </p>
     * <p>
     * Should set the name.
     * </p>
     */
    public void testSetNameAccuracyWithNonEmptyString() {
        type.setName(ANOTHER_NAME);
        assertEquals("Should set the name.", ANOTHER_NAME, type.getName());
    }

    /**
     * Accuracy test of <code>setName</code>.
     * <p>
     * With empty valid name.
     * </p>
     * <p>
     * Should set the name.
     * </p>
     */
    public void testSetNameAccuracyWithEmptyString() {
        type.setName("");
        assertEquals("Should set the name.", "", type.getName());
    }
}
