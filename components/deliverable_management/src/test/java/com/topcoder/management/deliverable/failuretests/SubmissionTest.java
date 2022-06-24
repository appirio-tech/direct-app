/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.Submission;

import junit.framework.TestCase;

/**
 * Failure tests for class <code>Submission</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class SubmissionTest extends TestCase {

    /**
     * Represents the submission to test.
     */
    private Submission sub;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        sub = new Submission();
    }

    /**
     * Test method for Submission(long).
     * In this case, the id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSubmissionZeroId() {
        try {
            new Submission(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Submission(long).
     * In this case, the id is negtive.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSubmissionNegativeId() {
        try {
            new Submission(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setUpload(com.topcoder.management.deliverable.Upload).
     * Nothing should be thrown.
     */
    public void testSetUpload() {
        try {
            sub.setUpload(null);
        } catch (Exception e) {
            fail("Should not throw anything.");
        }
    }

    /**
     * Test method for setSubmissionStatus(com.topcoder.management.deliverable.SubmissionStatus).
     * Nothing should be thrown.
     */
    public void testSetSubmissionStatus() {
        try {
            sub.setSubmissionStatus(null);
        } catch (Exception e) {
            fail("Should not throw anything.");
        }
    }

}
