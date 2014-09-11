/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.SubmissionType;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test cases for <code>SubmissionType</code>.
 * </p>
 *
 * @author FireIce
 * @version 1.1
 * @since 1.1
 */
public class SubmissionTypeAccuracyTests extends TestCase {
    /**
     * Tests default constructor.
     */
    public void testDefaultConstructor() {
        SubmissionType status = new SubmissionType();
        assertNotNull("Unable to instantiate SubmissionType", status);
        assertEquals("Id not set correctly", SubmissionType.UNSET_ID, status.getId());
        assertEquals("Name not set correctly", null, status.getName());
    }

    /**
     * Tests constructor with id.
     */
    public void testConstructorWithId() {
        long id = 8;
        SubmissionType status = new SubmissionType(id);
        assertNotNull("Unable to instantiate SubmissionType", status);
        assertEquals("Id not set correctly", id, status.getId());
        assertEquals("Name not set correctly", null, status.getName());
    }

    /**
     * Tests constructor with id and name.
     */
    public void testConstructorWithIdAndName() {
        long id = 16;
        String name = "name";
        SubmissionType status = new SubmissionType(id, name);
        assertNotNull("Unable to instantiate SubmissionType", status);
        assertEquals("Id not set correctly", id, status.getId());
        assertEquals("Name not set correctly", name, status.getName());
    }

    /**
     * Tests constructor with id and empty name.
     */
    public void testConstructorWithIdAndEmptyName() {
        long id = 16;
        SubmissionType status = new SubmissionType(id, "");
        assertNotNull("Unable to instantiate SubmissionType", status);
        assertEquals("Id not set correctly", id, status.getId());
        assertEquals("Name not set correctly", "", status.getName());
    }
}
