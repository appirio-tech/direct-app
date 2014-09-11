/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.SubmissionType;

import junit.framework.TestCase;

/**
 * <p>
 * Failure tests for SubmissionType class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class SubmissionTypeTests extends TestCase {
    /**
     * Test SubmissionType(long id). When id is zero.
     */
    public void testCtor2_IdIsZero() {
        try {
            new SubmissionType(0);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test SubmissionType(long id). When id is negative.
     */
    public void testCtor2_IdIsNegative() {
        try {
            new SubmissionType(-1);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test SubmissionType(long id, String name). When id is zero.
     */
    public void testCtor3_IdIsZero() {
        try {
            new SubmissionType(0, "a");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test SubmissionType(long id, String name). When id is negative.
     */
    public void testCtor3_IdIsNegative() {
        try {
            new SubmissionType(-1, "a");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }
}