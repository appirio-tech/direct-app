/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import junit.framework.TestCase;

/**
 * Unit test for UploadStatus.
 *
 * @author singlewood
 * @version 1.0
 */
public class UploadStatusTests extends TestCase {

    /**
     * The test UploadStatus instance.
     */
    private UploadStatus uploadStatus = null;

    /**
     * Create the test instance.
     *
     * @throws Exception exception to JUnit.
     */
    public void setUp() throws Exception {
        uploadStatus = new UploadStatus();
    }

    /**
     * Clean the config.
     *
     * @throws Exception exception to JUnit.
     */
    public void tearDown() throws Exception {
        uploadStatus = null;
    }

    /**
     * The default constructor should set id to UNSET_ID. So check if id is set properly. No
     * exception should be thrown.
     */
    public void testConstructor1_Accuracy1() {
        assertEquals("the constructor doesn't set id properly", UploadStatus.UNSET_ID,
                uploadStatus.getId());
    }

    /**
     * The default constructor should set name to null. So check if name is set properly. No
     * exception should be thrown.
     */
    public void testConstructor1_Accuracy2() {
        assertEquals("the constructor doesn't set name properly", null, uploadStatus.getName());
    }

    /**
     * Test constructor2 with invalid parameters. The argument will be set to 0.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor2_InvalidLong1() {
        try {
            new NamedDeliverableStructureExtends(0);
            fail("IllegalArgumentException should be thrown because of the null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor2 with invalid parameters. The argument will be set to -2.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor2_InvalidLong2() {
        try {
            new NamedDeliverableStructureExtends(-2);
            fail("IllegalArgumentException should be thrown because of invalid parameters.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor2 with valid parameter. Check if the constructor set the id fields properly.
     * No exception should be thrown.
     */
    public void testConstructor2_Accuracy1() {
        uploadStatus = new UploadStatus(123);
        assertEquals("constructor doesn't work properly.", 123, uploadStatus.getId());
    }

    /**
     * Test constructor3 with invalid parameters. The first argument will be set to 0.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor3_InvalidLong1() {
        try {
            new UploadStatus(0, null);
            fail("IllegalArgumentException should be thrown because of the null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor3 with invalid parameters. The first argument will be set to -3.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor3_InvalidLong2() {
        try {
            new UploadStatus(-3, null);
            fail("IllegalArgumentException should be thrown because of invalid parameters.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor3 with valid parameter. Check if the constructor set the name fields properly.
     * No exception should be thrown.
     */
    public void testConstructor3_Accuracy1() {
        uploadStatus = new UploadStatus(133, "name");
        assertEquals("constructor doesn't work properly.", "name", uploadStatus.getName());
    }

}
