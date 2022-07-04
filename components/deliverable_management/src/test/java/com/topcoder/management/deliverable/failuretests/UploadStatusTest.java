/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import junit.framework.TestCase;

import com.topcoder.management.deliverable.UploadStatus;

/**
 * Failure tests for class <code>UploadStatus</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class UploadStatusTest extends TestCase {

    /**
     * Test the constructor with id.
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     *
     */
    public void testConstructor1ZeroId() {
        try {
            new UploadStatus(0);
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
            new UploadStatus(-1);
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
            new UploadStatus(0, "name");
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
            new UploadStatus(-1, "name");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }
}
