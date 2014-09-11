/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import junit.framework.TestCase;

/**
 * Unit test case for <code>PhaseStatus</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class PhaseStatusUnitTests extends TestCase {
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
     * A <code>PhaseStatus</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private PhaseStatus status;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        status = new PhaseStatus(ID, NAME);
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
            new PhaseStatus(-1, NAME);
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
            new PhaseStatus(ID, null);
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
        assertNotNull("Should create the instance successfully.", status);
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
        assertEquals("Should return the proper id.", ID, status.getId());
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
            status.setId(-1);
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
        status.setId(ANOTHER_ID);
        assertEquals("Should set the id.", ANOTHER_ID, status.getId());
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
    public void testSetNameFailureWithNegativeName() {
        try {
            status.setName(null);
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
        status.setName(ANOTHER_NAME);
        assertEquals("Should set the name.", ANOTHER_NAME, status.getName());
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
        status.setName("");
        assertEquals("Should set the name.", "", status.getName());
    }

    /**
     * Accuracy test of <code>SCHEDULED</code> build in status.
     * <p>
     * Should get the proper id and name.
     * </p>
     */
    public void testSCHEDULEDAccuracy() {
        assertEquals("Should get the proper id.", 1, PhaseStatus.SCHEDULED.getId());
        assertEquals("Should get the proper name.", "Scheduled", PhaseStatus.SCHEDULED.getName());
    }

    /**
     * Accuracy test of <code>OPEN</code> build in status.
     * <p>
     * Should get the proper id and name.
     * </p>
     */
    public void testOPENAccuracy() {
        assertEquals("Should get the proper id.", 2, PhaseStatus.OPEN.getId());
        assertEquals("Should get the proper name.", "Open", PhaseStatus.OPEN.getName());
    }

    /**
     * Accuracy test of <code>CLOSE</code> build in status.
     * <p>
     * Should get the proper id and name.
     * </p>
     */
    public void testCLOSEAccuracy() {
        assertEquals("Should get the proper id.", 3, PhaseStatus.CLOSED.getId());
        assertEquals("Should get the proper name.", "Closed", PhaseStatus.CLOSED.getName());
    }
}
