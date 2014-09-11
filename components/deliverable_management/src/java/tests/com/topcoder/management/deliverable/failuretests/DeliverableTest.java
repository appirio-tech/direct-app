/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.Deliverable;

import junit.framework.TestCase;

/**
 * Failure tests for class <code>Deliverable</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DeliverableTest extends TestCase {

    /**
     * Represents the deliverable to test.
     */
    private Deliverable del;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        del = new Deliverable(1, 1, 1, new Long(1), false);
    }

    /**
     * Test method for Deliverable(long, long, long, java.lang.Long, boolean).
     * In this case, the project is zero.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDeliverableZeroProject() {
        try {
            new Deliverable(0, 1, 1, new Long(1), false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Deliverable(long, long, long, java.lang.Long, boolean).
     * In this case, the project is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDeliverableNegativeProject() {
        try {
            new Deliverable(-1, 1, 1, new Long(1), false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Deliverable(long, long, long, java.lang.Long, boolean).
     * In this case, the phase is zero.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDeliverableZeroPhase() {
        try {
            new Deliverable(1, 0, 1, new Long(1), false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Deliverable(long, long, long, java.lang.Long, boolean).
     * In this case, the phase is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDeliverableNegativePhase() {
        try {
            new Deliverable(1, -1, 1, new Long(1), false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Deliverable(long, long, long, java.lang.Long, boolean).
     * In this case, the resource is zero.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDeliverableZeroResource() {
        try {
            new Deliverable(1, 1, 0, new Long(1), false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Deliverable(long, long, long, java.lang.Long, boolean).
     * In this case, the resource is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDeliverableNegativeResource() {
        try {
            new Deliverable(1, 1, -1, new Long(1), false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Deliverable(long, long, long, java.lang.Long, boolean).
     * In this case, the submission is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDeliverableNullSubmission() {
        try {
            new Deliverable(1, 1, 0, null, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Deliverable(long, long, long, java.lang.Long, boolean).
     * In this case, the submission is zero.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDeliverableZeroSubmission() {
        try {
            new Deliverable(1, 1, 0, new Long(0), false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Deliverable(long, long, long, java.lang.Long, boolean).
     * In this case, the submission is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDeliverableNegativeSubmission() {
        try {
            new Deliverable(1, 1, -1, new Long(-1), false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setCompletionDate(java.util.Date).
     */
    public void testSetCompletionDate() {
        try {
            del.setCompletionDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
