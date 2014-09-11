/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import com.topcoder.management.project.FileType;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test for <code>MimeType</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public class MimeTypeTests extends TestCase {

    /**
     * Represents the <code>MimeType</code> instance for testing.
     */
    private MimeType mimeType;

    /**
     * Set up the testing environment.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        mimeType = new MimeType();
    }

    /**
     * Tear down the testing environment.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        mimeType = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>MimeType()</code> constructor.
     * </p>
     * <p>
     * Instance should be created always.
     * </p>
     */
    public void testCtor1() {
        mimeType = new MimeType();

        assertNotNull("instance should be created", mimeType);
        assertEquals("the id field should be default to UNSET_ID.", MimeType.UNSET_ID, mimeType.getId());
    }

    /**
     * <p>
     * Tests the <code>MimeType(long)</code> constructor.
     * </p>
     * <p>
     * If the id is negative, should throw IllegalArgumentException.
     * </p>
     */
    public void testCtor2_id_negative() {
        try {
            mimeType = new MimeType(-1);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>MimeType(long)</code> constructor.
     * </p>
     * <p>
     * If the id is zero, should throw IllegalArgumentException.
     * </p>
     */
    public void testCtor2_id_zero() {
        try {
            mimeType = new MimeType(0);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>MimeType(long)</code> constructor.
     * </p>
     * <p>
     * If the id is positive, the internal id field should be set.
     * </p>
     */
    public void testCtor2_id_positive() {
        mimeType = new MimeType(1);

        assertEquals("the id field is not set.", 1, mimeType.getId());
    }

    /**
     * <p>
     * Test the <code>setDescription(String)</code> method.
     * </p>
     * <p>
     * Set the description field, see if the getDescription method can get the correct value. No exception should be
     * thrown.
     * </p>
     */
    public void testSetDescription_Accuracy() {
        mimeType.setDescription("description");
        assertEquals("description is not set properly.", "description", mimeType.getDescription());
    }

    /**
     * <p>
     * Test the <code>getDescription()</code> method.
     * </p>
     * <p>
     * Set the description field, see if the getDescription method can get the correct value. No exception should be
     * thrown.
     * </p>
     */
    public void testGetDescription_Accuracy() {
        mimeType.setDescription("description");
        assertEquals("getDescription doesn't work properly.", "description", mimeType.getDescription());
    }

    /**
     * <p>
     * Test the <code>setFileType(FileType)</code> method.
     * </p>
     * <p>
     * Set the fileType field, see if the getFileType method can get the correct value. No exception should be thrown.
     * </p>
     */
    public void testSetFileType_Accuracy() {
        FileType fileType = new FileType();
        mimeType.setFileType(fileType);
        assertSame("fileType is not set properly.", fileType, mimeType.getFileType());
    }

    /**
     * <p>
     * Test the <code>getFileType()</code> method.
     * </p>
     * <p>
     * Set the fileType field, see if the getFileType method can get the correct value. No exception should be thrown.
     * </p>
     */
    public void testGetFileType_Accuracy() {
        FileType fileType = new FileType();
        mimeType.setFileType(fileType);
        assertSame("getFileType doesn't work properly.", fileType, mimeType.getFileType());
    }

}
