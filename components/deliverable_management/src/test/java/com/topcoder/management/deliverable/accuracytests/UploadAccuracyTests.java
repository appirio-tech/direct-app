/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Accuracy test cases for <code>Upload</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class UploadAccuracyTests extends TestCase {
    /** The <code>UploadType</code> instance used in the tests. */
    private UploadType type = null;

    /** The <code>UploadStatus</code> instance used in the tests. */
    private UploadStatus status = null;

    /** The <code>Upload</code> instance to be test. */
    private Upload upload = null;

    /**
     * Sets up the test environment. New instances of <code>Upload</code>, <code>UploadType</code> and
     * <code>UploadStatus</code> are created. Fields are set to valid values.
     */
    protected void setUp() {
        // initialize Upload
        upload = new Upload();
        upload.setCreationUser("creation");
        upload.setCreationTimestamp(new Date());
        upload.setModificationUser("modification");
        upload.setModificationTimestamp(new Date());

        // initialize UploadType
        type = new UploadType(32);
        type.setName("type");
        type.setDescription("type");
        type.setCreationUser("creation");
        type.setCreationTimestamp(new Date());
        type.setModificationUser("modification");
        type.setModificationTimestamp(new Date());

        // initialize UploadStatus
        status = new UploadStatus(32);
        status.setName("status");
        status.setDescription("status");
        status.setCreationUser("creation");
        status.setCreationTimestamp(new Date());
        status.setModificationUser("modification");
        status.setModificationTimestamp(new Date());
    }

    /**
     * Verifies public fields are set correctly.
     */
    public void testPublicFieldValues() {
        assertEquals("UNSET_OWNER not set correctly", -1, Upload.UNSET_OWNER);
        assertEquals("UNSET_PROJECT not set correctly", -1, Upload.UNSET_PROJECT);
    }

    /**
     * Tests default constructor.
     */
    public void testDefaultConstructor() {
        upload = new Upload();
        assertNotNull("Unable to instantiate Upload", upload);
        assertEquals("Id not set correctly", Upload.UNSET_ID, upload.getId());
        assertEquals("Creation user not set correctly", null, upload.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, upload.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, upload.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, upload.getModificationTimestamp());
        assertEquals("UnloadType not set correctly", null, upload.getUploadType());
        assertEquals("UnloadStatus not set correctly", null, upload.getUploadStatus());
        assertEquals("Owner not set correctly", Upload.UNSET_OWNER, upload.getOwner());
        assertEquals("Project not set correctly", Upload.UNSET_PROJECT, upload.getProject());
        assertEquals("Parameter not set correctly", null, upload.getParameter());
    }

    /**
     * Tests constructor with id.
     */
    public void testConstructorWithId() {
        long id = 512;
        upload = new Upload(id);
        assertNotNull("Unable to instantiate Upload", upload);
        assertEquals("Id not set correctly", id, upload.getId());
        assertEquals("Creation user not set correctly", null, upload.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, upload.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, upload.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, upload.getModificationTimestamp());
        assertEquals("UnloadType not set correctly", null, upload.getUploadType());
        assertEquals("UnloadStatus not set correctly", null, upload.getUploadStatus());
        assertEquals("Owner not set correctly", Upload.UNSET_OWNER, upload.getOwner());
        assertEquals("Project not set correctly", Upload.UNSET_PROJECT, upload.getProject());
        assertEquals("Parameter not set correctly", null, upload.getParameter());
    }

    /**
     * Tests setUploadType and getUploadType methods.
     */
    public void testSetAndGetUploadType() {
        upload.setUploadType(type);
        assertEquals("SetUploadType or getUploadType not functions correctly", type, upload.getUploadType());
        upload.setUploadType(null);
        assertEquals("SetUploadType or getUploadType not functions correctly", null, upload.getUploadType());
    }

    /**
     * Tests setUploadStatus and getUploadStatus methods.
     */
    public void testSetAndGetUploadStatus() {
        upload.setUploadStatus(status);
        assertEquals("SetUploadStatus or getUploadStatus not functions correctly", status, upload.getUploadStatus());
        upload.setUploadStatus(null);
        assertEquals("SetUploadStatus or getUploadStatus not functions correctly", null, upload.getUploadStatus());
    }

    /**
     * Tests setOwner and getOwner methods.
     */
    public void testSetAndGetOwner() {
        long owner = 2048;
        upload.setOwner(owner);
        assertEquals("SetOwner or getOwner not functions correctly", owner, upload.getOwner());
    }

    /**
     * Tests setProject and getProject methods.
     */
    public void testSetAndGetProject() {
        long project = 32;
        upload.setProject(project);
        assertEquals("SetProject or getProject not functions correctly", project, upload.getProject());
    }

    /**
     * Tests setParameter and getParameter methods.
     */
    public void testSetAndGetParameter() {
        String parameter = "parameter";
        upload.setParameter(parameter);
        assertEquals("SetParameter or getParameter not functions correctly", parameter, upload.getParameter());
        upload.setParameter(null);
        assertEquals("SetParameter or getParameter not functions correctly", null, upload.getParameter());
        upload.setParameter("");
        assertEquals("SetParameter or getParameter not functions correctly", "", upload.getParameter());
    }

    /**
     * Tests isValidToPersist method with null upload type. False should be returned.
     */
    public void testIdValidPersistNullUploadType() {
        upload.setId(32);
        upload.setUploadStatus(status);
        upload.setOwner(16);
        upload.setProject(32);
        upload.setParameter("parameter");
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            upload.isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with unset id. False should be returned.
     */
    public void testIdValidPersistUnsetId() {
        upload.setUploadType(type);
        upload.setUploadStatus(status);
        upload.setOwner(16);
        upload.setProject(32);
        upload.setParameter("parameter");
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            upload.isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with null upload status. False should be returned.
     */
    public void testIdValidPersistNullUploadstatus() {
        upload.setId(32);
        upload.setUploadType(type);
        upload.setOwner(16);
        upload.setProject(32);
        upload.setParameter("parameter");
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            upload.isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with unset owner. False should be returned.
     */
    public void testIdValidPersistUnsetOwner() {
        upload.setId(32);
        upload.setUploadType(type);
        upload.setUploadStatus(status);
        upload.setProject(32);
        upload.setParameter("parameter");
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            upload.isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with unset project. False should be returned.
     */
    public void testIdValidPersistUnsetProject() {
        upload.setId(32);
        upload.setUploadType(type);
        upload.setUploadStatus(status);
        upload.setOwner(16);
        upload.setParameter("parameter");
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            upload.isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with null parameter. False should be returned.
     */
    public void testIdValidPersistNullParameter() {
        upload.setId(32);
        upload.setUploadType(type);
        upload.setUploadStatus(status);
        upload.setOwner(16);
        upload.setProject(32);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            upload.isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with invalid upload type. False should be returned.
     */
    public void testIdValidPersistInvalidUploadType() {
        upload.setId(32);
        upload.setUploadType(type);
        upload.setUploadStatus(status);
        upload.setOwner(16);
        upload.setProject(32);
        upload.setParameter("parameter");
        upload.getUploadType().setCreationTimestamp(null);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            upload.isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with invalid upload status. False should be returned.
     */
    public void testIdValidPersistInvalidUploadStatus() {
        upload.setId(32);
        upload.setUploadType(type);
        upload.setUploadStatus(status);
        upload.setOwner(16);
        upload.setProject(32);
        upload.setParameter("parameter");
        upload.getUploadStatus().setModificationTimestamp(null);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            upload.isValidToPersist());
    }

    /**
     * Tests isValidToPersist method when the upload is valid. True should be returned.
     */
    public void testIsValidToPersistValid() {
        upload.setId(32);
        upload.setUploadType(type);
        upload.setUploadStatus(status);
        upload.setOwner(16);
        upload.setProject(32);
        upload.setParameter("parameter");
        assertEquals("IsValidToPersist not functions correctly, true should be returned.", true,
            upload.isValidToPersist());
    }
}
