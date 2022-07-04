/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.SubmissionStatus;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test cases for <code>SubmissionStatus</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class SubmissionStatusAccuracyTests extends TestCase {
    /**
     * Tests default constructor.
     */
    public void testDefaultConstructor() {
        SubmissionStatus status = new SubmissionStatus();
        assertNotNull("Unable to instantiate SubmissionStatus", status);
        assertEquals("Id not set correctly", SubmissionStatus.UNSET_ID, status.getId());
        assertEquals("Name not set correctly", null, status.getName());
    }

    /**
     * Tests constructor with id.
     */
    public void testConstructorWithId() {
        long id = 8;
        SubmissionStatus status = new SubmissionStatus(id);
        assertNotNull("Unable to instantiate SubmissionStatus", status);
        assertEquals("Id not set correctly", id, status.getId());
        assertEquals("Name not set correctly", null, status.getName());
    }

    /**
     * Tests constructor with id and name.
     */
    public void testConstructorWithIdAndName() {
        long id = 16;
        String name = "name";
        SubmissionStatus status = new SubmissionStatus(id, name);
        assertNotNull("Unable to instantiate SubmissionStatus", status);
        assertEquals("Id not set correctly", id, status.getId());
        assertEquals("Name not set correctly", name, status.getName());
    }

    /**
     * Tests constructor with id and empty name.
     */
    public void testConstructorWithIdAndEmptyName() {
        long id = 16;
        SubmissionStatus status = new SubmissionStatus(id, "");
        assertNotNull("Unable to instantiate SubmissionStatus", status);
        assertEquals("Id not set correctly", id, status.getId());
        assertEquals("Name not set correctly", "", status.getName());
    }
}
