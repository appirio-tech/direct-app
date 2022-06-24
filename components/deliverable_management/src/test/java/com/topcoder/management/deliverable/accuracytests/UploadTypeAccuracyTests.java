/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.UploadType;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test cases for <code>UploadType</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class UploadTypeAccuracyTests extends TestCase {
    /**
     * Tests default constructor.
     */
    public void testDefaultConstructor() {
        UploadType type = new UploadType();
        assertNotNull("Unable to instantiate UploadType", type);
        assertEquals("Id not set correctly", UploadType.UNSET_ID, type.getId());
        assertEquals("Name not set correctly", null, type.getName());
    }

    /**
     * Tests constructor with id.
     */
    public void testConstructorWithId() {
        long id = 8;
        UploadType type = new UploadType(id);
        assertNotNull("Unable to instantiate UploadType", type);
        assertEquals("Id not set correctly", id, type.getId());
        assertEquals("Name not set correctly", null, type.getName());
    }

    /**
     * Tests constructor with id and name.
     */
    public void testConstructorWithIdAndName() {
        long id = 16;
        String name = "name";
        UploadType type = new UploadType(id, name);
        assertNotNull("Unable to instantiate UploadType", type);
        assertEquals("Id not set correctly", id, type.getId());
        assertEquals("Name not set correctly", name, type.getName());
    }

    /**
     * Tests constructor with id and empty name.
     */
    public void testConstructorWithIdAndEmptyName() {
        long id = 16;
        UploadType type = new UploadType(id, "");
        assertNotNull("Unable to instantiate UploadType", type);
        assertEquals("Id not set correctly", id, type.getId());
        assertEquals("Name not set correctly", "", type.getName());
    }
}
