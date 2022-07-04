/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;

/**
 * <p>
 * Accuracy test cases for <code>Submission</code>.
 * </p>
 *
 * @author skatou, FireIce
 * @version 1.1
 */
public class SubmissionAccuracyTests extends TestCase {
    /** The <code>UploadType</code> instance used in the tests. */
    private UploadType uploadType = null;

    /** The <code>UploadStatus</code> instance used in the tests. */
    private UploadStatus uploadStatus = null;

    /** The <code>Upload</code> instance used in the tests. */
    private Upload upload = null;

    /** The <code>SubmissionStatus</code> instance used in the tests. */
    private SubmissionStatus submissionStatus = null;

    /** The <code>SubmissionType</code> instance used in the tests. */
    private SubmissionType submissionType = null;

    /** The <code>Submission</code> instance to be tested. */
    private Submission submission = null;

    /**
     * Sets up the test environment. New instances of <code>Upload</code>, <code>UploadType</code>,
     * <code>UploadStatus</code>, <code>SubmissionStatus</code> and <code>Submission</code> are created. Fields are set
     * to valid values.
     */
    protected void setUp() {
        // initialize UploadType
        uploadType = new UploadType(32);
        uploadType.setName("upload type");
        uploadType.setDescription("upload type");
        uploadType.setCreationUser("creation");
        uploadType.setCreationTimestamp(new Date());
        uploadType.setModificationUser("modification");
        uploadType.setModificationTimestamp(new Date());

        // initialize UploadStatus
        uploadStatus = new UploadStatus(32);
        uploadStatus.setName("upload status");
        uploadStatus.setDescription("upload status");
        uploadStatus.setCreationUser("creation");
        uploadStatus.setCreationTimestamp(new Date());
        uploadStatus.setModificationUser("modification");
        uploadStatus.setModificationTimestamp(new Date());

        // initialize Upload
        upload = new Upload(32);
        upload.setCreationUser("creation");
        upload.setCreationTimestamp(new Date());
        upload.setModificationUser("modification");
        upload.setModificationTimestamp(new Date());
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(16);
        upload.setProject(32);
        upload.setParameter("parameter");

        // initialize SubmissionStatus
        submissionStatus = new SubmissionStatus(16);
        submissionStatus.setName("submission status");
        submissionStatus.setDescription("submission status");
        submissionStatus.setCreationUser("creation");
        submissionStatus.setCreationTimestamp(new Date());
        submissionStatus.setModificationUser("modification");
        submissionStatus.setModificationTimestamp(new Date());

        // initialize SubmissionType
        submissionType = new SubmissionType(18);
        submissionType.setName("submission type");
        submissionType.setDescription("submission type");
        submissionType.setCreationUser("creation");
        submissionType.setCreationTimestamp(new Date());
        submissionType.setModificationUser("modification");
        submissionType.setModificationTimestamp(new Date());

        // initialize Submission
        submission = new Submission();
        submission.setCreationUser("creation");
        submission.setCreationTimestamp(new Date());
        submission.setModificationUser("modification");
        submission.setModificationTimestamp(new Date());
    }

    /**
     * Tests default constructor.
     */
    public void testDefaultConstructor() {
        submission = new Submission();
        assertNotNull("Unable to instantiate Submission", submission);
        assertEquals("Id not set correctly", Submission.UNSET_ID, submission.getId());
        assertEquals("Creation user not set correctly", null, submission.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, submission.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, submission.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, submission.getModificationTimestamp());
        assertEquals("Unload not set correctly", null, submission.getUpload());
        assertEquals("SubmissionStatus not set correctly", null, submission.getSubmissionStatus());
        assertEquals("SubmissionType not set correctly", null, submission.getSubmissionType());
    }

    /**
     * Tests constructor with id.
     */
    public void testConstructorWithId() {
        long id = 2048;
        submission = new Submission(id);
        assertNotNull("Unable to instantiate Submission", submission);
        assertEquals("Id not set correctly", id, submission.getId());
        assertEquals("Creation user not set correctly", null, submission.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, submission.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, submission.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, submission.getModificationTimestamp());
        assertEquals("Unload not set correctly", null, submission.getUpload());
        assertEquals("SubmissionStatus not set correctly", null, submission.getSubmissionStatus());
        assertEquals("SubmissionType not set correctly", null, submission.getSubmissionType());
    }

    /**
     * Tests setUpload and getUpload methods.
     */
    public void testSetAndGetUpload() {
        submission.setUpload(upload);
        assertEquals("SetUpload or getUpload not functions correctly", upload, submission.getUpload());
        submission.setUpload(null);
        assertEquals("SetUpload or getUpload not functions correctly", null, submission.getUpload());
    }

    /**
     * Tests setSubmissionStatus and getSubmissionStatus methods.
     */
    public void testSetAndGetUploadStatus() {
        submission.setSubmissionStatus(submissionStatus);
        assertEquals("SetSubmissionStatus or getSubmissionStatus not functions correctly", submissionStatus, submission
                .getSubmissionStatus());
        submission.setSubmissionStatus(null);
        assertEquals("SetSubmissionStatus or getSubmissionStatus not functions correctly", null, submission
                .getSubmissionStatus());
    }

    /**
     * Tests setSubmissionType and getSubmissionType methods.
     *
     * @since 1.1
     */
    public void testSetAndGetSubmissionType() {
        submission.setSubmissionType(submissionType);
        assertEquals("SetSubmissionType or getSubmissionType not functions correctly", submissionType, submission
                .getSubmissionType());
        submission.setSubmissionType(null);
        assertEquals("SetSubmissionType or getSubmissionType not functions correctly", null, submission
                .getSubmissionType());
    }

    /**
     * Tests isValidToPersist method with unset id. False should be returned.
     */
    public void testIdValidPersistUnsetId() {
        submission.setUpload(upload);
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false, submission
                .isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with null upload. False should be returned.
     */
    public void testIdValidPersistNullUpload() {
        submission.setId(1024);
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false, submission
                .isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with null submission status. False should be returned.
     */
    public void testIdValidPersistNullSubmissionStatus() {
        submission.setId(1024);
        submission.setUpload(upload);
        submission.setSubmissionType(submissionType);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false, submission
                .isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with null submission type. False should be returned.
     */
    public void testIdValidPersistNullSubmissionType() {
        submission.setId(1024);
        submission.setUpload(upload);
        submission.setSubmissionStatus(submissionStatus);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false, submission
                .isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with invalid upload. False should be returned.
     */
    public void testIdValidPersistInvalidUpload() {
        submission.setId(1024);
        submission.setUpload(upload);
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        submission.getUpload().setParameter(null);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false, submission
                .isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with invalid submission status. False should be returned.
     */
    public void testIdValidPersistInvalidSubmissionStatus() {
        submission.setId(1024);
        submission.setUpload(upload);
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        submission.getSubmissionStatus().setName(null);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false, submission
                .isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with invalid submission type. False should be returned.
     *
     * @since 1.2
     */
    public void testIdValidPersistInvalidSubmissionType() {
        submission.setId(1024);
        submission.setUpload(upload);
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        submission.getSubmissionType().setName(null);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false, submission
                .isValidToPersist());
    }

    /**
     * Tests isValidToPersist method when the submission is valid. True should be returned.
     */
    public void testIsValidToPersistValid() {
        submission.setId(1024);
        submission.setUpload(upload);
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        assertEquals("IsValidToPersist not functions correctly, true should be returned.", true, submission
                .isValidToPersist());
    }
}
