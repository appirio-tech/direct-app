/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.SubmissionStatus;

import junit.framework.TestCase;

/**
 * Failure tests for class <code>SubmissionStatus</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class SubmissionStatusTest extends TestCase {

    /**
     * Test the constructor with id.
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     *
     */
    public void testConstructor1ZeroId() {
        try {
            new SubmissionStatus(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test the constructor with id.
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     *
     */
    public void testConstructor1NegativeId() {
        try {
            new SubmissionStatus(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test the constructor with id and name.
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     *
     */
    public void testConstructor2ZeroId() {
        try {
            new SubmissionStatus(0, "name");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test the constructor with id and name.
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     *
     */
    public void testConstructor2NegativeId() {
        try {
            new SubmissionStatus(-1, "name");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
