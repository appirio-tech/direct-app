/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.UploadStatus;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test cases for <code>UploadStatus</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class UploadStatusAccuracyTests extends TestCase {
    /**
     * Tests default constructor.
     */
    public void testDefaultConstructor() {
        UploadStatus status = new UploadStatus();
        assertNotNull("Unable to instantiate UploadStatus", status);
        assertEquals("Id not set correctly", UploadStatus.UNSET_ID, status.getId());
        assertEquals("Name not set correctly", null, status.getName());
    }

    /**
     * Tests constructor with id.
     */
    public void testConstructorWithId() {
        long id = 8;
        UploadStatus status = new UploadStatus(id);
        assertNotNull("Unable to instantiate UploadStatus", status);
        assertEquals("Id not set correctly", id, status.getId());
        assertEquals("Name not set correctly", null, status.getName());
    }

    /**
     * Tests constructor with id and name.
     */
    public void testConstructorWithIdAndName() {
        long id = 16;
        String name = "name";
        UploadStatus status = new UploadStatus(id, name);
        assertNotNull("Unable to instantiate UploadStatus", status);
        assertEquals("Id not set correctly", id, status.getId());
        assertEquals("Name not set correctly", name, status.getName());
    }

    /**
     * Tests constructor with id and empty name.
     */
    public void testConstructorWithIdAndEmptyName() {
        long id = 16;
        UploadStatus status = new UploadStatus(id, "");
        assertNotNull("Unable to instantiate UploadStatus", status);
        assertEquals("Id not set correctly", id, status.getId());
        assertEquals("Name not set correctly", "", status.getName());
    }
}
