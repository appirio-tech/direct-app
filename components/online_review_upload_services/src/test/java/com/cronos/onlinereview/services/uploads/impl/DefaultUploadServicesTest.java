/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.InvalidProjectException;
import com.cronos.onlinereview.services.uploads.InvalidProjectPhaseException;
import com.cronos.onlinereview.services.uploads.InvalidSubmissionException;
import com.cronos.onlinereview.services.uploads.InvalidSubmissionStatusException;
import com.cronos.onlinereview.services.uploads.InvalidUserException;
import com.cronos.onlinereview.services.uploads.ManagersProvider;
import com.cronos.onlinereview.services.uploads.PersistenceException;
import com.cronos.onlinereview.services.uploads.TestHelper;
import com.cronos.onlinereview.services.uploads.UploadServicesException;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;

/**
 * <p>
 * Tests the functionality of <code>{@link DefaultUploadServices}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class DefaultUploadServicesTest extends TestCase {
    /**
     * <p>
     * Represents the <code>DefaultUploadServices</code> to test.
     * </p>
     */
    private DefaultUploadServices defaultUploadServices;

    /**
     * Represents the mock resource manager.
     */
    private MockResourceManager resourceManager;

    /**
     * Represents the mock screening manager.
     */
    private MockScreeningManager screeningManager;

    /**
     * Represents the mock upload manager.
     */
    private MockUploadManager uploadManager;

    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>DefaultUploadServicesTest</code>.
     */
    public static Test suite() {
        return new TestSuite(DefaultUploadServicesTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfigs("config.xml");
        defaultUploadServices = new DefaultUploadServices();
        ManagersProvider managersProvider = (ManagersProvider) TestHelper.getFieldValue(defaultUploadServices,
                "managersProvider");
        resourceManager = (MockResourceManager) managersProvider.getResourceManager();
        screeningManager = (MockScreeningManager) managersProvider.getScreeningManager();
        uploadManager = (MockUploadManager) managersProvider.getUploadManager();

    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        TestHelper.releaseConfigs();
        defaultUploadServices = null;
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadServices#DefaultUploadServices(ManagersProvider managersProvider)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadServices_accuracy_1() throws Exception {
        ManagersProvider managersProvider = new DefaultManagersProvider();
        defaultUploadServices = new DefaultUploadServices(managersProvider);
        assertNotNull("Failed to create DefaultUploadServices", defaultUploadServices);
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#DefaultUploadServices(ManagersProvider managersProvider)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * ManagersProvider is null.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     */
    public void testDefaultUploadServices_failure_1() {
        try {
            new DefaultUploadServices((ManagersProvider) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultUploadServices#DefaultUploadServices()}</code> constructor. Creates
     * an instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     */
    public void testDefaultUploadServices_accuracy_2() {
        assertNotNull("Failed to create DefaultUploadServices", defaultUploadServices);
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadServices#DefaultUploadServices()}</code> constructor.
     * </p>
     *
     * <p>
     * No namespace available.
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadServices_failure_2() throws Exception {
        TestHelper.releaseConfigs();
        try {
            new DefaultUploadServices();
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultUploadServices#DefaultUploadServices(String namespace)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadServices_accuracy_3() throws Exception {
        defaultUploadServices = new DefaultUploadServices(
                "com.cronos.onlinereview.services.uploads.impl.DefaultUploadServices");
        assertNotNull("Failed to create DefaultUploadServices", defaultUploadServices);
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadServices#DefaultUploadServices(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * namespace is null.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadServices_failure_3() throws Exception {
        try {
            new DefaultUploadServices((String) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadServices#DefaultUploadServices(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * namespace is empty.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadServices_failure_4() throws Exception {
        try {
            new DefaultUploadServices("");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadServices#DefaultUploadServices(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * Wrong namespace
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     */
    public void testDefaultUploadServices_failure_5() {
        try {
            new DefaultUploadServices("wrong namespace");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadServices#DefaultUploadServices(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * Wrong managersProviderIdentifier
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadServices_failure_6() throws Exception {
        TestHelper.loadConfigs("config_man_invalid.xml");
        try {
            new DefaultUploadServices("com.cronos.onlinereview.services.uploads.impl.DefaultUploadServices");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultUploadServices#DefaultUploadServices()}</code> constructor. Creates
     * an instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     */
    public void testDefaultUploadServices_accuracy_4() {
        assertNotNull("Failed to create DefaultUploadServices", defaultUploadServices);
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects the upload to happen without any error.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_accuracy() throws Exception {
        defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");

        // verify upload persistence
        Upload upload = uploadManager.getCreatedUpload();
        assertEquals("uploadSubmission failed", upload.getUploadStatus().getName(), "Active");
        assertEquals("uploadSubmission failed", upload.getUploadType().getName(), "Submission");
        assertEquals("uploadSubmission failed", upload.getOwner(), TestHelper.USER_ID);
        assertEquals("uploadSubmission failed", upload.getProject(), TestHelper.PROJECT_ID);
        assertEquals("uploadSubmission failed", upload.getParameter(), "test_file.jar");
        assertEquals("uploadSubmission failed", TestHelper.USER_ID + "", uploadManager.getCreatedUploadUserId());

        // verify submission
        Submission submission = uploadManager.getCreatedSubmission();
        assertEquals("uploadSubmission failed", submission.getSubmissionStatus().getName(), "Active");
        assertEquals("uploadSubmission failed", TestHelper.USER_ID + "", uploadManager
                .getCreatedSubmissionUserId());

        assertEquals("uploadSubmission failed", TestHelper.USER_ID + "", resourceManager.getUpdateResourceUserId());

        // verify screening initiation
        assertEquals("uploadSubmission failed", screeningManager.getSubmissionId(), TestHelper.SUBMISSION_ID);
        assertEquals("uploadSubmission failed", TestHelper.USER_ID + "", screeningManager.getUserId());

        // verify previous submissions are marked for deletion
        Submission submissions = uploadManager.searchSubmissions(null)[0];
        assertNotNull("uploadSubmission failed", submissions.getSubmissionStatus());
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * projectId is negative
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_1() throws Exception {
        try {
            defaultUploadServices.uploadSubmission(-1, TestHelper.USER_ID, "test_file.jar");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * userId is negative
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_2() throws Exception {
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, -1, "test_file.jar");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * filename is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_3() throws Exception {
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * filename is empty
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_4() throws Exception {
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * project does not exist.
     * </p>
     *
     * <p>
     * Expects <code>InvalidProjectException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_5() throws Exception {
        MockProjectManager.setState(-1);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidProjectException.");
        } catch (InvalidProjectException e) {
            // expect
            MockProjectManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * ProjectPhase already closed
     * </p>
     *
     * <p>
     * Expects <code>InvalidProjectPhaseException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_6() throws Exception {
        MockProject.setState(1);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidProjectPhaseException.");
        } catch (InvalidProjectPhaseException e) {
            // expect
            MockProject.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * submitterRoleId is not present in ResourceRole.
     * </p>
     *
     * <p>
     * Expects <code>InvalidUserException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_7() throws Exception {
        MockResourceManager.setState(-1);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidUserException.");
        } catch (InvalidUserException e) {
            // expect
            MockResourceManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * No resource available.
     * </p>
     *
     * <p>
     * Expects <code>InvalidUserException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_8() throws Exception {
        MockResourceManager.setState(1);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidUserException.");
        } catch (InvalidUserException e) {
            // expect
            MockResourceManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects <code>PersistenceException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_9() throws Exception {
        MockUploadManager.setThrowError(true);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect PersistenceException.");
        } catch (PersistenceException e) {
            // expect
            MockUploadManager.setThrowError(false);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects <code>UploadServicesException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_10() throws Exception {
        MockPhaseManager.setThrowError(true);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect UploadServicesException.");
        } catch (UploadServicesException e) {
            // expect
            MockPhaseManager.setThrowError(false);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * No project phases available.
     * </p>
     *
     * <p>
     * Expects <code>InvalidProjectException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_11() throws Exception {
        MockProject.setState(2);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidProjectException.");
        } catch (InvalidProjectException e) {
            // expect
            MockProject.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Error in persisting Upload
     * </p>
     *
     * <p>
     * Expects <code>PersistenceException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_12() throws Exception {
        MockUploadManager.setThrowOnCreateError(true);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect PersistenceException.");
        } catch (PersistenceException e) {
            // expect
            MockUploadManager.setThrowOnCreateError(false);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Error in initiating screening.
     * </p>
     *
     * <p>
     * Expects <code>PersistenceException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_13() throws Exception {
        MockScreeningManager.setState(1);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect PersistenceException.");
        } catch (PersistenceException e) {
            // expect
            MockScreeningManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Error in initiating screening.
     * </p>
     *
     * <p>
     * Expects <code>UploadServicesException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_14() throws Exception {
        MockScreeningManager.setState(2);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect UploadServicesException.");
        } catch (UploadServicesException e) {
            // expect
            MockScreeningManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadSubmission(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Error in retrieving project.
     * </p>
     *
     * <p>
     * Expects <code>PersistenceException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_15() throws Exception {
        MockProjectManager.setThrowError(true);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect PersistenceException.");
        } catch (PersistenceException e) {
            // expect
            MockProjectManager.setThrowError(false);
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects the upload to happen without any error.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_accuracy() throws Exception {
        defaultUploadServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");

        // verify upload persistence
        Upload upload = uploadManager.getCreatedUpload();
        assertEquals("uploadFinalFix failed", upload.getUploadStatus().getName(), "Active");
        assertEquals("uploadFinalFix failed", upload.getUploadType().getName(), "Final Fix");
        assertEquals("uploadFinalFix failed", upload.getOwner(), TestHelper.USER_ID);
        assertEquals("uploadFinalFix failed", upload.getProject(), TestHelper.PROJECT_ID);
        assertEquals("uploadFinalFix failed", upload.getParameter(), "test_file.jar");
        assertEquals("uploadFinalFix failed", TestHelper.USER_ID + "", uploadManager.getCreatedUploadUserId());

        // verify screening is not initiated
        assertEquals("uploadFinalFix failed", screeningManager.getSubmissionId(), -1);
        assertEquals("uploadFinalFix failed", screeningManager.getUserId(), null);

        // verify previous submissions are marked for deletion
        Submission submissions = uploadManager.searchSubmissions(null)[0];
        assertEquals("uploadFinalFix failed", submissions.getSubmissionStatus().getName(), "Deleted");
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * projectId is negative
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_1() throws Exception {
        try {
            defaultUploadServices.uploadFinalFix(-1, TestHelper.USER_ID, "test_file.jar");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * userId is negative
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_2() throws Exception {
        try {
            defaultUploadServices.uploadFinalFix(TestHelper.PROJECT_ID, -1, "test_file.jar");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * filename is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_3() throws Exception {
        try {
            defaultUploadServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * filename is empty
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_4() throws Exception {
        try {
            defaultUploadServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * project does not exist.
     * </p>
     *
     * <p>
     * Expects <code>InvalidProjectException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_5() throws Exception {
        MockProjectManager.setState(-1);
        try {
            defaultUploadServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidProjectException.");
        } catch (InvalidProjectException e) {
            // expect
            MockProjectManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * ProjectPhase already closed.
     * </p>
     *
     * <p>
     * Expects <code>InvalidProjectPhaseException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_6() throws Exception {
        MockProject.setState(1);
        try {
            defaultUploadServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidProjectPhaseException.");
        } catch (InvalidProjectPhaseException e) {
            // expect
            MockProject.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * submitterRoleId is not present in ResourceRole.
     * </p>
     *
     * <p>
     * Expects <code>InvalidUserException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_7() throws Exception {
        MockResourceManager.setState(-1);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidUserException.");
        } catch (InvalidUserException e) {
            // expect
            MockResourceManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * No resource available.
     * </p>
     *
     * <p>
     * Expects <code>InvalidUserException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_8() throws Exception {
        MockResourceManager.setState(1);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidUserException.");
        } catch (InvalidUserException e) {
            // expect
            MockResourceManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects <code>PersistenceException</code>.
     * </p>
     *
     * @throws Exception
     *             if any exception occurs
     */
    public void testUploadFinalFix_failure_9() throws Exception {
        MockUploadManager.setThrowError(true);
        try {
            defaultUploadServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect PersistenceException.");
        } catch (PersistenceException e) {
            // expect
            MockUploadManager.setThrowError(false);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     *
     * <p>
     * Expects <code>UploadServicesException</code>.
     * </p>
     *
     */
    public void testUploadFinalFix_failure_10() {
        MockPhaseManager.setThrowError(true);
        try {
            defaultUploadServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect UploadServicesException.");
        } catch (UploadServicesException e) {
            // expect
            MockPhaseManager.setThrowError(false);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * No project phase available.
     * </p>
     *
     * <p>
     * Expects <code>InvalidProjectException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_11() throws Exception {
        MockProject.setState(2);
        try {
            defaultUploadServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidProjectException.");
        } catch (InvalidProjectException e) {
            // expect
            MockProject.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadFinalFix(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Error in persisting Upload
     * </p>
     *
     * <p>
     * Expects <code>PersistenceException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_12() throws Exception {
        MockUploadManager.setThrowOnCreateError(true);
        try {
            defaultUploadServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect PersistenceException.");
        } catch (PersistenceException e) {
            // expect
            MockUploadManager.setThrowOnCreateError(false);
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects the upload to happen without any error.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_accuracy() throws Exception {
        defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");

        // verify upload persistence
        Upload upload = uploadManager.getCreatedUpload();
        assertEquals("uploadTestCases failed", upload.getUploadStatus().getName(), "Active");
        assertEquals("uploadTestCases failed", upload.getUploadType().getName(), "Review");
        assertEquals("uploadTestCases failed", upload.getOwner(), TestHelper.USER_ID);
        assertEquals("uploadTestCases failed", upload.getProject(), TestHelper.PROJECT_ID);
        assertEquals("uploadTestCases failed", upload.getParameter(), "test_file.jar");
        assertEquals("uploadTestCases failed", TestHelper.USER_ID + "", uploadManager.getCreatedUploadUserId());

        // verify previous submissions are marked for deletion
        Submission submissions = uploadManager.searchSubmissions(null)[0];
        assertEquals("uploadTestCases failed", submissions.getSubmissionStatus().getName(), "Deleted");
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * projectId is negative
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_1() throws Exception {
        try {
            defaultUploadServices.uploadTestCases(-1, TestHelper.USER_ID, "test_file.jar");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * userId is negative
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_2() throws Exception {
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, -1, "test_file.jar");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * filename is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_3() throws Exception {
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * filename is empty
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_4() throws Exception {
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * project does not exist
     * </p>
     *
     * <p>
     * Expects <code>InvalidProjectException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_5() throws Exception {
        MockProjectManager.setState(-1);
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidProjectException.");
        } catch (InvalidProjectException e) {
            // expect
            MockProjectManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * ProjectPhase already closed
     * </p>
     *
     * <p>
     * Expects <code>InvalidProjectPhaseException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_6() throws Exception {
        MockProject.setState(1);
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidProjectPhaseException.");
        } catch (InvalidProjectPhaseException e) {
            // expect
            MockProject.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * submitterRoleId is not present in ResourceRole.
     * </p>
     *
     * <p>
     * Expects <code>InvalidUserException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_7() throws Exception {
        MockResourceManager.setState(-1);
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidUserException.");
        } catch (InvalidUserException e) {
            // expect
            MockResourceManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * No resource available.
     * </p>
     *
     * <p>
     * Expects <code>InvalidUserException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_8() throws Exception {
        MockResourceManager.setState(1);
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidUserException.");
        } catch (InvalidUserException e) {
            // expect
            MockResourceManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects <code>PersistenceException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_9() throws Exception {
        MockUploadManager.setThrowError(true);
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect PersistenceException.");
        } catch (PersistenceException e) {
            // expect
            MockUploadManager.setThrowError(false);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects <code>UploadServicesException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_10() throws Exception {
        MockPhaseManager.setThrowError(true);
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect UploadServicesException.");
        } catch (UploadServicesException e) {
            // expect
            MockPhaseManager.setThrowError(false);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * No Project phase available
     * </p>
     *
     * <p>
     * Expects <code>InvalidProjectException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_11() throws Exception {
        MockProject.setState(2);
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect InvalidProjectException.");
        } catch (InvalidProjectException e) {
            // expect
            MockProject.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#uploadTestCases(long projectId, long userId, String filename)}</code>
     * method.
     * </p>
     *
     * <p>
     * Error in persisting Upload.
     * </p>
     *
     * <p>
     * Expects <code>PersistenceException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_12() throws Exception {
        MockUploadManager.setThrowOnCreateError(true);
        try {
            defaultUploadServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "test_file.jar");
            fail("Expect PersistenceException.");
        } catch (PersistenceException e) {
            // expect
            MockUploadManager.setThrowOnCreateError(false);
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadServices#setSubmissionStatus(long submissionId, long submissionStatusId,
     * String operator)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects the set submission to happen without any error.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetSubmissionStatus_accuracy() throws Exception {
        defaultUploadServices.setSubmissionStatus(TestHelper.SUBMISSION_ID, TestHelper.SUBMISSION_STATUS_ID, ""
                + TestHelper.USER_ID);
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#setSubmissionStatus(long submissionId, long submissionStatusId,
     * String operator)}</code>
     * method.
     * </p>
     *
     * <p>
     * submissionId is negative
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetSubmissionStatus_failure_1() throws Exception {
        try {
            defaultUploadServices
                    .setSubmissionStatus(-1, TestHelper.SUBMISSION_STATUS_ID, "" + TestHelper.USER_ID);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#setSubmissionStatus(long submissionId, long submissionStatusId,
     * String operator)}</code>
     * method.
     * </p>
     *
     * <p>
     * submissionStatusId is negative
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetSubmissionStatus_failure_2() throws Exception {
        try {
            defaultUploadServices.setSubmissionStatus(TestHelper.SUBMISSION_ID, -1, "" + TestHelper.USER_ID);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#setSubmissionStatus(long submissionId, long submissionStatusId,
     * String operator)}</code>
     * method.
     * </p>
     *
     * <p>
     * operator is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetSubmissionStatus_failure_3() throws Exception {
        try {
            defaultUploadServices.setSubmissionStatus(TestHelper.SUBMISSION_ID, TestHelper.SUBMISSION_STATUS_ID,
                    null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadServices#setSubmissionStatus(long submissionId, long submissionStatusId,
     * String operator)}</code>
     * method.
     * </p>
     *
     * <p>
     * operator is empty
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetSubmissionStatus_failure_4() throws Exception {
        try {
            defaultUploadServices.setSubmissionStatus(TestHelper.SUBMISSION_ID, TestHelper.SUBMISSION_STATUS_ID,
                    "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadServices#setSubmissionStatus(long submissionId,
     * long submissionStatusId, String operator)}</code>
     * method.
     * </p>
     *
     * <p>
     * No submission available for the given id.
     * </p>
     *
     * <p>
     * Expects <code>InvalidSubmissionException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetSubmissionStatus_failure_5() throws Exception {
        MockUploadManager.setState(-1);
        try {
            defaultUploadServices.setSubmissionStatus(TestHelper.SUBMISSION_ID, TestHelper.SUBMISSION_STATUS_ID,
                    "" + TestHelper.USER_ID);
            fail("Expect InvalidSubmissionException.");
        } catch (InvalidSubmissionException e) {
            // expect
            MockUploadManager.setState(0);
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadServices#setSubmissionStatus(long submissionId,
     * long submissionStatusId, String operator)}</code>
     * method.
     * </p>
     *
     * <p>
     * No submission status available for the given id.
     * </p>
     *
     * <p>
     * Expects <code>InvalidSubmissionStatusException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetSubmissionStatus_failure_6() throws Exception {
        try {
            defaultUploadServices.setSubmissionStatus(TestHelper.SUBMISSION_ID, 10, "" + TestHelper.USER_ID);
            fail("Expect InvalidSubmissionStatusException.");
        } catch (InvalidSubmissionStatusException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadServices#setSubmissionStatus(long submissionId, long
     * submissionStatusId, String operator)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects <code>PersistenceException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetSubmissionStatus_failure_7() throws Exception {
        MockUploadManager.setThrowError(true);
        try {
            defaultUploadServices.setSubmissionStatus(TestHelper.SUBMISSION_ID, TestHelper.SUBMISSION_STATUS_ID,
                    "" + TestHelper.USER_ID);
            fail("Expect PersistenceException.");
        } catch (PersistenceException e) {
            // expect
            MockUploadManager.setThrowError(false);
        }
    }
}
