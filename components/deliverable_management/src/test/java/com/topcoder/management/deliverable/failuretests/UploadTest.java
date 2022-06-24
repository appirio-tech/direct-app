/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.Upload;

import junit.framework.TestCase;

/**
 * Failure tests for class <code>Upload</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class UploadTest extends TestCase {

    /**
     * Represents the upload to test.
     */
    private Upload upload;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        upload = new Upload();
    }

    /**
     * Test method for Upload(long).
     * In this case, the id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testUploadZeroId() {
        try {
            new Upload(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Upload(long).
     * In this case, the id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testUploadNegativeId() {
        try {
            new Upload(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }
    /**
     * Test method for setUploadType(com.topcoder.management.deliverable.UploadType).
     * Nothing should be thrown.
     */
    public void testSetUploadType() {
        try {
            upload.setUploadType(null);
        } catch (Exception e) {
            fail("Should not throw anything.");
        }
    }

    /**
     * Test method for setUploadStatus(com.topcoder.management.deliverable.UploadStatus).
     * Nothing should be thrown.
     */
    public void testSetUploadStatus() {
        try {
            upload.setUploadStatus(null);
        } catch (Exception e) {
            fail("Should not throw anything.");
        }
    }

    /**
     * Test method for setOwner(long).
     * In this case, the id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSetOwnerZeroId() {
        try {
            upload.setOwner(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setOwner(long).
     * In this case, the id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSetOwnerNegativeId() {
        try {
            upload.setOwner(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setProject(long).
     * In this case, the id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSetProjectZeroId() {
        try {
            upload.setProject(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setProject(long).
     * In this case, the id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSetProjectNegativeId() {
        try {
            upload.setProject(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setParameter(java.lang.String).
     * Nothing should be thrown.
     */
    public void testSetParameter() {
        try {
            upload.setParameter(null);
            upload.setParameter("");
            upload.setParameter(" ");
        } catch (Exception e) {
            fail("Should not throw anything.");
        }
    }

}
