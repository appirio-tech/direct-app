/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.impl;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.TestHelper;
import com.cronos.onlinereview.services.uploads.UploadServicesException;

/**
 * <p>
 * Tests the functionality of <code>{@link DefaultUploadExternalServices}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class DefaultUploadExternalServicesTest extends TestCase {
    /**
     * <p>
     * Represents the <code>DefaultUploadExternalServices</code> to test.
     * </p>
     */
    private DefaultUploadExternalServices defaultUploadExternalServices;

    /**
     * <p>
     * Integrates all tests in this class.
     *
     * @return Test suite of all tests of <code>DefaultUploadExternalServicesTest</code>.
     */
    public static Test suite() {
        return new TestSuite(DefaultUploadExternalServicesTest.class);
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
        defaultUploadExternalServices = new DefaultUploadExternalServices();
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
        clearFiles();
        TestHelper.releaseConfigs();
        defaultUploadExternalServices = null;
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices()}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     */
    public void testDefaultUploadExternalServices_accuracy_1() {
        assertNotNull("Failed to create DefaultUploadExternalServices", defaultUploadExternalServices);
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices()}</code>
     * constructor.
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
    public void testDefaultUploadExternalServices_failure_1() throws Exception {
        TestHelper.releaseConfigs();
        try {
            new DefaultUploadExternalServices();
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices(String namespace)}</code>
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
    public void testDefaultUploadExternalServices_accuracy_2() throws Exception {
        defaultUploadExternalServices = new DefaultUploadExternalServices(
                "com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices");
        assertNotNull("Failed to create DefaultUploadExternalServices", defaultUploadExternalServices);
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * namespace is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadExternalServices_failure_2() throws Exception {
        try {
            new DefaultUploadExternalServices(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * namespace is empty
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadExternalServices_failure_3() throws Exception {
        try {
            new DefaultUploadExternalServices(" ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * wrong namespace
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     */
    public void testDefaultUploadExternalServices_failure_4() {
        try {
            new DefaultUploadExternalServices("wrong namespace");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * uploadServicesIdentifier invalid
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadExternalServices_failure_5() throws Exception {
        TestHelper.loadConfigs("config_ups_invalid.xml");
        try {
            new DefaultUploadExternalServices(
                    "com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices(
     * com.cronos.onlinereview.services.uploads.UploadServices, String, String)}</code>
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
    public void testDefaultUploadExternalServices_accuracy_3() throws Exception {
        defaultUploadExternalServices = new DefaultUploadExternalServices(new DefaultUploadServices(), null,
                "temp");
        assertNotNull("Failed to create DefaultUploadExternalServices", defaultUploadExternalServices);
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices(
     * com.cronos.onlinereview.services.uploads.UploadServices, String, String)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * uploadServices is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadExternalServices_failure_6() throws Exception {
        try {
            new DefaultUploadExternalServices(null, null, "temp");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices(
     * com.cronos.onlinereview.services.uploads.UploadServices, String, String)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * fileStorageLocation is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadExternalServices_failure_7() throws Exception {
        try {
            new DefaultUploadExternalServices(new DefaultUploadServices(), null, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadExternalServices#DefaultUploadExternalServices(
     * com.cronos.onlinereview.services.uploads.UploadServices, String, String)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * fileStorageLocation is empty
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultUploadExternalServices_failure_8() throws Exception {
        try {
            new DefaultUploadExternalServices(new DefaultUploadServices(), null, " ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadExternalServices#uploadSubmission(long projectId, long userId, String filename,
     * DataHandler submission)}</code>
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
        File toUpload = new File(TestHelper.TEST_FILES + "sample.jar");
        FileDataSource dataSource = new FileDataSource(toUpload);
        DataHandler dataHandler = new DataHandler(dataSource);
        defaultUploadExternalServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "upload.jar",
                dataHandler);
        File uploadedDir = new File(TestHelper.TEST_FILES + "upload" + File.separator);
        File uploaded = uploadedDir.listFiles()[0];
        assertEquals("Failed to upload submission", toUpload.length(), uploaded.length());
    }

    /**
     *
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadSubmission(long projectId, long userId, String filename,
     * DataHandler submission)}</code>
     * method.
     * </p>
     * <p>
     * projectId is negative
     * </p>
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_1() throws Exception {
        try {
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadSubmission(-1, TestHelper.USER_ID, "upload.jar", dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     *
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadSubmission(long projectId, long userId, String filename,
     * DataHandler submission)}</code>
     * method.
     * </p>
     * <p>
     * userId is negative
     * </p>
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_2() throws Exception {
        try {
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadSubmission(TestHelper.PROJECT_ID, -1, "upload.jar", dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     *
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadSubmission(long projectId, long userId, String filename,
     * DataHandler submission)}</code>
     * method.
     * </p>
     * <p>
     * filename is null
     * </p>
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_3() throws Exception {
        try {
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, null,
                    dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     *
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadSubmission(long projectId, long userId, String filename,
     * DataHandler submission)}</code>
     * method.
     * </p>
     * <p>
     * filename is empty
     * </p>
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_4() throws Exception {
        try {
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID, "",
                    dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     *
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadSubmission(long projectId, long userId, String filename,
     * DataHandler submission)}</code>
     * method.
     * </p>
     * <p>
     * submission is null
     * </p>
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_5() throws Exception {
        try {
            defaultUploadExternalServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID,
                    "upload.jar", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadSubmission(long projectId, long userId, String filename,
     * DataHandler submission)}</code>
     * method.
     * </p>
     *
     * <p>
     * DataSource is not of type FileDataSource.
     * </p>
     *
     * <p>
     * Expects <code>RemoteException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadSubmission_failure_6() throws Exception {
        try {
            URL url = new URL("file://somefile");
            DataHandler dataHandler = new DataHandler(url);
            defaultUploadExternalServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID,
                    "upload.jar", dataHandler);
            fail("Expect RemoteException.");
        } catch (RemoteException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadSubmission(long projectId, long userId, String filename,
     * DataHandler submission)}</code>
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
    public void testUploadSubmission_failure_7() throws Exception {
        MockProjectManager.setState(-1);
        try {
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadSubmission(TestHelper.PROJECT_ID, TestHelper.USER_ID,
                    "upload.jar", dataHandler);
            fail("Expect UploadServicesException.");
        } catch (UploadServicesException e) {
            // expect
            MockProjectManager.setState(0);
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadExternalServices#uploadFinalFix(long projectId, long userId, String filename,
     * DataHandler finalFix)}</code>
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
        File toUpload = new File(TestHelper.TEST_FILES + "sample.jar");
        FileDataSource dataSource = new FileDataSource(toUpload);
        DataHandler dataHandler = new DataHandler(dataSource);
        defaultUploadExternalServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "upload.jar",
                dataHandler);
        File uploadedDir = new File(TestHelper.TEST_FILES + "upload" + File.separator);
        File uploaded = uploadedDir.listFiles()[0];
        assertEquals("Failed to upload final fix", toUpload.length(), uploaded.length());
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadFinalFix(long projectId, long userId, String filename,
     *  DataHandler finalFix)}</code>
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
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadFinalFix(-1, TestHelper.USER_ID, "upload.jar", dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadFinalFix(long projectId, long userId, String filename,
     *  DataHandler finalFix)}</code>
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
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadFinalFix(TestHelper.PROJECT_ID, -1, "upload.jar", dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadFinalFix(long projectId, long userId, String filename,
     *  DataHandler finalFix)}</code>
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
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, null,
                    dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadFinalFix(long projectId, long userId, String filename,
     *  DataHandler finalFix)}</code>
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
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "",
                    dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadFinalFix(long projectId, long userId, String filename,
     *  DataHandler finalFix)}</code>
     * method.
     * </p>
     *
     * <p>
     * finalFix is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_5() throws Exception {
        try {
            defaultUploadExternalServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "upload.jar",
                    null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadFinalFix(long projectId, long userId, String filename,
     * DataHandler finalFix)}</code>
     * method.
     * </p>
     *
     * <p>
     * DataSource is not of type FileDataSource.
     * </p>
     *
     * <p>
     * Expects <code>RemoteException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadFinalFix_failure_6() throws Exception {
        try {
            URL url = new URL("file://somefile");
            DataHandler dataHandler = new DataHandler(url);
            defaultUploadExternalServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "upload.jar",
                    dataHandler);
            fail("Expect RemoteException.");
        } catch (RemoteException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadFinalFix(long projectId, long userId, String filename,
     * DataHandler finalFix)}</code>
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
    public void testUploadFinalFix_failure_7() throws Exception {
        MockProjectManager.setState(-1);
        try {
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadFinalFix(TestHelper.PROJECT_ID, TestHelper.USER_ID, "upload.jar",
                    dataHandler);
            fail("Expect UploadServicesException.");
        } catch (UploadServicesException e) {
            // expect
            MockProjectManager.setState(0);
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadExternalServices#uploadTestCases(long projectId, long userId, String filename,
     *  DataHandler testCases)}</code>
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
        File toUpload = new File(TestHelper.TEST_FILES + "sample.jar");
        FileDataSource dataSource = new FileDataSource(toUpload);
        DataHandler dataHandler = new DataHandler(dataSource);
        defaultUploadExternalServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "upload.jar",
                dataHandler);
        File uploadedDir = new File(TestHelper.TEST_FILES + "upload" + File.separator);
        File uploaded = uploadedDir.listFiles()[0];
        assertEquals("Failed to upload test case", toUpload.length(), uploaded.length());
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadTestCases(long projectId, long userId, String filename,
     * DataHandler testCases)}</code>
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
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadTestCases(-1, TestHelper.USER_ID, "upload.jar", dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadTestCases(long projectId, long userId, String filename,
     * DataHandler testCases)}</code>
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
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadTestCases(TestHelper.PROJECT_ID, -1, "upload.jar", dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadTestCases(long projectId, long userId, String filename,
     * DataHandler testCases)}</code>
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
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, null,
                    dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadTestCases(long projectId, long userId, String filename,
     * DataHandler testCases)}</code>
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
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "",
                    dataHandler);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadTestCases(long projectId, long userId, String filename,
     * DataHandler testCases)}</code>
     * method.
     * </p>
     *
     * <p>
     * testCases is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_5() throws Exception {
        try {
            defaultUploadExternalServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "upload.jar",
                    null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#uploadTestCases(long projectId, long userId, String filename,
     * DataHandler testCases)}</code>
     * method.
     * </p>
     *
     * <p>
     * DataSource is not of type FileDataSource.
     * </p>
     *
     * <p>
     * Expects <code>RemoteException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_6() throws Exception {
        try {
            URL url = new URL("file://somefile");
            DataHandler dataHandler = new DataHandler(url);
            defaultUploadExternalServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "upload.jar",
                    dataHandler);
            fail("Expect RemoteException.");
        } catch (RemoteException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultUploadExternalServices#uploadTestCases(long projectId, long userId,
     * String filename, DataHandler testCases)}</code>
     * method.
     * </p>
     *
     * <p>
     * Error in UploadServices
     * </p>
     *
     * <p>
     * Expects <code>UploadServicesException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUploadTestCases_failure_7() throws Exception {
        MockProjectManager.setState(-1);
        try {
            FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);
            defaultUploadExternalServices.uploadTestCases(TestHelper.PROJECT_ID, TestHelper.USER_ID, "upload.jar",
                    dataHandler);
            fail("Expect UploadServicesException.");
        } catch (UploadServicesException e) {
            // expect
            MockProjectManager.setState(0);
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultUploadExternalServices#setSubmissionStatus(long submissionId, long submissionStatusId,
     * String operator)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects the set submission status to happen without any error.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetSubmissionStatus_accuracy() throws Exception {
        defaultUploadExternalServices.setSubmissionStatus(TestHelper.SUBMISSION_ID,
                TestHelper.SUBMISSION_STATUS_ID, "" + TestHelper.USER_ID);
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#setSubmissionStatus(long submissionId, long submissionStatusId,
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
            defaultUploadExternalServices.setSubmissionStatus(-1, TestHelper.SUBMISSION_STATUS_ID, ""
                    + TestHelper.USER_ID);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#setSubmissionStatus(long submissionId, long submissionStatusId,
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
            defaultUploadExternalServices.setSubmissionStatus(TestHelper.SUBMISSION_ID, -1, ""
                    + TestHelper.USER_ID);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#setSubmissionStatus(long submissionId, long submissionStatusId,
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
            defaultUploadExternalServices.setSubmissionStatus(TestHelper.SUBMISSION_ID,
                    TestHelper.SUBMISSION_STATUS_ID, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultUploadExternalServices#setSubmissionStatus(long submissionId, long submissionStatusId,
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
            defaultUploadExternalServices.setSubmissionStatus(TestHelper.SUBMISSION_ID,
                    TestHelper.SUBMISSION_STATUS_ID, "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Clear all the files created using tesing.
     */
    private void clearFiles() {
        File file = new File("test_files/upload");
        File[] files = file.listFiles();
        for (File delFile : files) {
            delFile.delete();
        }
    }
}
