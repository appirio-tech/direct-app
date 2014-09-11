/*
 * Copyright (C) 2006,2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import junit.framework.TestCase;

/**
 * Unit test for Upload.
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>Added test cases for getDescription and setDescription methods</li>
 * </ul>
 * </p>
 *
 * @author singlewood
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class UploadTests extends TestCase {

    /**
     * The test Upload instance.
     */
    private Upload upload = null;

    /**
     * The test UploadType instance.
     */
    private UploadType uploadType = null;

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
        uploadType = DeliverableTestHelper.getValidToPersistUploadType();
        uploadType.setId(1);
        uploadStatus = DeliverableTestHelper.getValidToPersistUploadStatus();
        uploadStatus.setId(1);
        upload = DeliverableTestHelper.getValidToPersistUpload();
    }

    /**
     * Clean the config.
     *
     * @throws Exception exception to JUnit.
     */
    public void tearDown() throws Exception {
        upload = null;
    }

    /**
     * The default constructor should set id to UNSET_ID. So check if id is set properly. No
     * exception should be thrown.
     */
    public void testConstructor1_Accuracy1() {
        assertEquals("the constructor doesn't set id properly", Upload.UNSET_ID, upload.getId());
    }

    /**
     * Test constructor2 with invalid parameters. The argument will be set to 0.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor2_InvalidLong1() {
        try {
            new Upload(0);
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
            new Upload(-2);
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
        upload = new Upload(123);
        assertEquals("constructor doesn't work properly.", 123, upload.getId());
    }

    /**
     * Test the behavior of setUploadStatus. Set the uploadStatus field, see if the getUploadStatus
     * method can get the correct value. No exception should be thrown.
     */
    public void testSetUploadStatus_Accuracy1() {
        upload.setUploadStatus(uploadStatus);
        assertEquals("uploadStatus is not set properly.", uploadStatus, upload.getUploadStatus());
    }

    /**
     * Test the behavior of getUploadStatus. Set the uploadStatus field, see if the getUploadStatus
     * method can get the correct value. No exception should be thrown.
     */
    public void testGetUploadStatus_Accuracy1() {
        upload.setUploadStatus(uploadStatus);
        assertEquals("getUploadStatus doesn't work properly.", uploadStatus, upload.getUploadStatus());
    }

    /**
     * Test the behavior of setUploadType. Set the uploadType field, see if the getUploadType
     * method can get the correct value. No exception should be thrown.
     */
    public void testSetUploadType_Accuracy1() {
        upload.setUploadType(uploadType);
        assertEquals("uploadType is not set properly.", uploadType, upload.getUploadType());
    }

    /**
     * Test the behavior of getUploadType. Set the uploadType field, see if the getUploadType
     * method can get the correct value. No exception should be thrown.
     */
    public void testGetUploadType_Accuracy1() {
        upload.setUploadType(uploadType);
        assertEquals("getUploadType doesn't work properly.", uploadType, upload.getUploadType());
    }

    /**
     * Test the behavior of setOwner. Set the owner field with 0. IllegalArgumentException should be thrown.
     */
    public void testSetOwner_Invalid1() {
        try {
            upload.setOwner(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test the behavior of setOwner. Set the owner field, see if the getOwner
     * method can get the correct value. No exception should be thrown.
     */
    public void testSetOwner_Accuracy1() {
        upload.setOwner(1);
        assertEquals("owner is not set properly.", 1, upload.getOwner());
    }

    /**
     * Test the behavior of getOwner. Set the owner field, see if the getOwner
     * method can get the correct value. No exception should be thrown.
     */
    public void testGetOwner_Accuracy1() {
        upload.setOwner(2);
        assertEquals("getOwner doesn't work properly.", 2, upload.getOwner());
    }

    /**
     * Test the behavior of setProject. Set the project field with 0. IllegalArgumentException should be thrown.
     */
    public void testSetProject_Invalid1() {
        try {
            upload.setProject(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test the behavior of setProject. Set the project field, see if the getProject
     * method can get the correct value. No exception should be thrown.
     */
    public void testSetProject_Accuracy1() {
        upload.setProject(1);
        assertEquals("project is not set properly.", 1, upload.getProject());
    }

    /**
     * Test the behavior of getProject. Set the project field, see if the getProject
     * method can get the correct value. No exception should be thrown.
     */
    public void testGetProject_Accuracy1() {
        upload.setProject(2);
        assertEquals("getProject doesn't work properly.", 2, upload.getProject());
    }

    /**
     * Test the behavior of setParameter. Set the parameter field, see if the getParameter
     * method can get the correct value. No exception should be thrown.
     */
    public void testSetParameter_Accuracy1() {
        upload.setParameter("parameter");
        assertEquals("parameter is not set properly.", "parameter", upload.getParameter());
    }

    /**
     * Test the behavior of getParameter. Set the parameter field, see if the getParameter
     * method can get the correct value. No exception should be thrown.
     */
    public void testGetParameter_Accuracy1() {
        upload.setParameter("parameter");
        assertEquals("getParameter doesn't work properly.", "parameter", upload.getParameter());
    }

    /**
     * Test the behavior of setDescription. Set the description field, see if the getDescription method can get the
     * correct value. No exception should be thrown.
     *
     * @since 1.2
     */
    public void testSetDescription_Accuracy1() {
        upload.setDescription("description");
        assertEquals("description is not set properly.", "description", upload.getDescription());
    }

    /**
     * Test the behavior of getDescription. Set the description field, see if the getDescription method can get the
     * correct value. No exception should be thrown.
     *
     * @since 1.2
     */
    public void testGetDescription_Accuracy1() {
        upload.setDescription("description");
        assertEquals("getDescription doesn't work properly.", "description", upload.getDescription());
    }

    /**
     * Test the behavior of isValidToPersist. Set the uploadType field to null, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy1() {
        upload.setUploadType(null);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(1);
        upload.setParameter("parameter");
        upload.setProject(1);
        upload.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, upload.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set the uploadStatus field to null, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy2() {
        upload.setUploadType(uploadType);
        upload.setUploadStatus(null);
        upload.setOwner(1);
        upload.setParameter("parameter");
        upload.setProject(1);
        upload.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, upload.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Do not set id field, see if the isValidToPersist
     * returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy3() {
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(1);
        upload.setParameter("parameter");
        upload.setProject(1);
        assertEquals("isValidToPersist doesn't work properly.", false, upload.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Do not set owner field, see if the isValidToPersist
     * returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy4() {
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setParameter("parameter");
        upload.setProject(1);
        assertEquals("isValidToPersist doesn't work properly.", false, upload.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Do not set project field, see if the isValidToPersist
     * returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy5() {
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(1);
        upload.setParameter("parameter");
        assertEquals("isValidToPersist doesn't work properly.", false, upload.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set the parameter field to null, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy6() {
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(1);
        upload.setParameter(null);
        upload.setProject(1);
        upload.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, upload.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set super.isValidToPersist() to false, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy7() {
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(1);
        upload.setParameter("parameter");
        upload.setProject(1);
        upload.setId(1);
        upload.setCreationTimestamp(null);
        assertEquals("isValidToPersist doesn't work properly.", false, upload.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set uploadType.isValidToPersist() to false, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy8() {
        uploadType.setDescription(null);
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(1);
        upload.setParameter("parameter");
        upload.setProject(1);
        upload.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, upload.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set uploadStatus.isValidToPersist() to false, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy9() {
        uploadStatus.setDescription(null);
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(1);
        upload.setParameter("parameter");
        upload.setProject(1);
        upload.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, upload.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set all the field with non-null values, see if the
     * isValidToPersist returns true. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy10() {
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(1);
        upload.setParameter("parameter");
        upload.setProject(1);
        upload.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", true, upload.isValidToPersist());
    }

}
