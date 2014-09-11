/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.ManagersProvider;
import com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider;
import com.cronos.onlinereview.services.uploads.impl.DefaultUploadServices;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.resource.Resource;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link DefaultUploadServices} class.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class DefaultUploadServicesAccuracyTest extends TestCase {

    /**
     * DefaultUploadServices instance to be used for the testing.
     */
    private DefaultUploadServices defaultUploadServices = null;

    /**
     * Used for testing.
     */
    private MockResourceManager resourceManager = null;


    /**
     * Used for testing.
     */
    private MockUploadManager uploadManager = null;

    /**
     * Used for testing.
     */
    private MockScreeningManager screeningManager = null;


    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        AccuracyHelper.loadConfig();
        defaultUploadServices = new DefaultUploadServices();
        ManagersProvider managersProvider = (ManagersProvider) AccuracyHelper.getFieldValue(defaultUploadServices,
                "managersProvider");
        resourceManager = (MockResourceManager) managersProvider.getResourceManager();
        screeningManager = (MockScreeningManager) managersProvider.getScreeningManager();
        uploadManager = (MockUploadManager) managersProvider.getUploadManager();
    }

    /**
     * <p>
     * Tears down the environment after the TestCase.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        AccuracyHelper.release();
        defaultUploadServices = null;
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultUploadServicesAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadServices#DefaultUploadServices(ManagersProvider)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_DefaultUploadServices() throws Exception {
        DefaultUploadServices services = new DefaultUploadServices(new DefaultManagersProvider());
        // check for null
        assertNotNull("DefaultUploadServices creation failed", services);
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadServices#DefaultUploadServices()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_DefaultUploadServices1() throws Exception {
        // check for null
        assertNotNull("DefaultUploadServices creation failed", defaultUploadServices);
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadServices#DefaultUploadServices(String)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_DefaultUploadServices2() throws Exception {
        DefaultUploadServices defaultUploadServices = new DefaultUploadServices(DefaultUploadServices.class
                .getName());
        // check for null
        assertNotNull("DefaultUploadServices creation failed", defaultUploadServices);
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadServices#uploadSubmission(long, long, String)} method.
     * </p>
     * <p>
     * Checks whether all the proper methods are called with valid values.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_uploadSubmission() throws Exception {
        assertEquals("uploadSubmission failed", defaultUploadServices.uploadSubmission(AccuracyHelper.PROJECT_ID, AccuracyHelper.USER_ID,
                AccuracyHelper.FILE_NAME), AccuracyHelper.SUBMISSION_ID);

        // verify upload persistence
        Upload upload = uploadManager.getCreatedUpload();
        assertEquals("uploadSubmission failed", upload.getUploadStatus().getName(), "Active");
        assertEquals("uploadSubmission failed", upload.getUploadType().getName(), "Submission");
        assertEquals("uploadSubmission failed", upload.getOwner(), AccuracyHelper.USER_ID);
        assertEquals("uploadSubmission failed", upload.getProject(), AccuracyHelper.PROJECT_ID);
        assertEquals("uploadSubmission failed", upload.getParameter(), AccuracyHelper.FILE_NAME);
        assertEquals("uploadSubmission failed", AccuracyHelper.USER_ID+"", uploadManager.getCreatedUploadUserId());

        // verify submission
        Submission submission = uploadManager.getCreatedSubmission();
        assertEquals("uploadSubmission failed", submission.getSubmissionStatus().getName(), "Active");
        assertEquals("uploadSubmission failed", AccuracyHelper.USER_ID+"", uploadManager.getCreatedSubmissionUserId());

        // verify the resource is added
        Resource resource = resourceManager.getUpdateResource();

        assertEquals("uploadSubmission failed", AccuracyHelper.USER_ID+"", resourceManager.getUpdateResourceUserId());

        // verify screening initiation
        assertEquals("uploadSubmission failed", screeningManager.getSubmissionId(), AccuracyHelper.SUBMISSION_ID);
        assertEquals("uploadSubmission failed", AccuracyHelper.USER_ID+"", screeningManager.getUserId());

        // verify previous submissions are not marked for deletion
        Submission submissions = uploadManager.searchSubmissions(null)[0];
        assertNull("uploadSubmission failed", submissions.getSubmissionStatus());
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadServices#uploadFinalFix(long, long, String)} method.
     * </p>
     * <p>
     * Checks whether all the proper methods are called with valid values.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_uploadFinalFix() throws Exception {
        assertEquals("uploadFinalFix failed", defaultUploadServices.uploadFinalFix(AccuracyHelper.PROJECT_ID, AccuracyHelper.USER_ID,
                AccuracyHelper.FILE_NAME), -1);

        // verify upload persistence
        Upload upload = uploadManager.getCreatedUpload();
        assertEquals("uploadFinalFix failed", upload.getUploadStatus().getName(), "Active");
        assertEquals("uploadFinalFix failed", upload.getUploadType().getName(), "Final Fix");
        assertEquals("uploadFinalFix failed", upload.getOwner(), AccuracyHelper.USER_ID);
        assertEquals("uploadFinalFix failed", upload.getProject(), AccuracyHelper.PROJECT_ID);
        assertEquals("uploadFinalFix failed", upload.getParameter(), AccuracyHelper.FILE_NAME);
        assertEquals("uploadFinalFix failed", AccuracyHelper.USER_ID+"", uploadManager.getCreatedUploadUserId());

        // verify screening is not initiated
        assertEquals("uploadFinalFix failed", screeningManager.getSubmissionId(), -1);
        assertEquals("uploadFinalFix failed", screeningManager.getUserId(), null);

        // verify previous submissions are marked for deletion
        Submission submissions = uploadManager.searchSubmissions(null)[0];
        assertEquals("uploadFinalFix failed", submissions.getSubmissionStatus().getName(), "Deleted");
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadServices#uploadTestCases(long, long, String)} method.
     * </p>
     * <p>
     * Checks whether all the proper methods are called with valid values.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_uploadTestCases() throws Exception {
        assertEquals("uploadTestCases failed", defaultUploadServices.uploadTestCases(AccuracyHelper.PROJECT_ID, AccuracyHelper.USER_ID,
                AccuracyHelper.FILE_NAME), -1);

        // verify upload persistence
        Upload upload = uploadManager.getCreatedUpload();
        assertEquals("uploadTestCases failed", upload.getUploadStatus().getName(), "Active");
        assertEquals("uploadTestCases failed", upload.getUploadType().getName(), "Review");
        assertEquals("uploadTestCases failed", upload.getOwner(), AccuracyHelper.USER_ID);
        assertEquals("uploadTestCases failed", upload.getProject(), AccuracyHelper.PROJECT_ID);
        assertEquals("uploadTestCases failed", upload.getParameter(), AccuracyHelper.FILE_NAME);
        assertEquals("uploadTestCases failed", AccuracyHelper.USER_ID+"", uploadManager.getCreatedUploadUserId());

        // verify previous submissions are marked for deletion
        Submission submissions = uploadManager.searchSubmissions(null)[0];
        assertEquals("uploadTestCases failed", submissions.getSubmissionStatus().getName(), "Deleted");
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadServices#setSubmissionStatus(long, long, String)} method.
     * </p>
     * <p>
     * Checks whether all the proper methods are called with valid values.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_setSubmissionStatus() throws Exception {
         defaultUploadServices.setSubmissionStatus(AccuracyHelper.SUBMISSION_ID, AccuracyHelper.SUBMISSION_STATUS_ID, "operator");
         Submission submission = uploadManager.getUpdatedSubmission();
         assertEquals("setSubmissionStatus failed", submission.getSubmissionStatus().getId(), AccuracyHelper.SUBMISSION_STATUS_ID);
         assertEquals("setSubmissionStatus failed", submission.getId(), AccuracyHelper.SUBMISSION_ID);
         assertEquals("setSubmissionStatus failed", "operator", uploadManager.getUpdatedSubmissionUserId());
    }
}
