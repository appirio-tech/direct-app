/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import javax.activation.DataHandler;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.UploadServices;
import com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices;
import com.cronos.onlinereview.services.uploads.impl.DefaultUploadServices;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link DefaultUploadExternalServices} class.
 * </p>
 * 
 * @author kshatriyan
 * @version 1.0
 */
public class DefaultUploadExternalServicesAccuracyTest extends TestCase {

    /**
     * DefaultUploadExternalServices instance to be used for the testing.
     */
    private DefaultUploadExternalServices defaultUploadExternalServices = null;

    /**
     * Used for testing.
     */
    private MockUploadServices mockUploadServices = null;

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
        defaultUploadExternalServices = new DefaultUploadExternalServices();
        mockUploadServices = (MockUploadServices) AccuracyHelper.getFieldValue(defaultUploadExternalServices,
                "uploadServices");
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
        defaultUploadExternalServices = null;
        mockUploadServices = null;
        File file = new File(AccuracyHelper.TEST_FILES + "test/accuracy_output.jar");
        if(file.exists()) {
            file.delete();
        }
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * 
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultUploadExternalServicesAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadExternalServices#DefaultUploadExternalServices()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_DefaultUploadExternalServices() throws Exception {
        // check for null
        assertNotNull("DefaultUploadExternalServices creation failed", defaultUploadExternalServices);
        // check the variable initialization
        assertNotNull("DefaultUploadExternalServices creation failed", AccuracyHelper.getFieldValue(
                defaultUploadExternalServices, "uploadServices"));
        assertNotNull("DefaultUploadExternalServices creation failed", AccuracyHelper.getFieldValue(
                defaultUploadExternalServices, "filenamePattern"));
        assertNotNull("DefaultUploadExternalServices creation failed", AccuracyHelper.getFieldValue(
                defaultUploadExternalServices, "fileStorageLocation"));
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadExternalServices#DefaultUploadExternalServices(String)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_DefaultUploadExternalServices1() throws Exception {
        DefaultUploadExternalServices defaultUploadExternalServices = new DefaultUploadExternalServices(
                DefaultUploadExternalServices.class.getName());
        // check for null
        assertNotNull("DefaultUploadExternalServices creation failed", defaultUploadExternalServices);
        // check the variable initialization
        assertNotNull("DefaultUploadExternalServices creation failed", AccuracyHelper.getFieldValue(
                defaultUploadExternalServices, "uploadServices"));
        assertNotNull("DefaultUploadExternalServices creation failed", AccuracyHelper.getFieldValue(
                defaultUploadExternalServices, "filenamePattern"));
        assertNotNull("DefaultUploadExternalServices creation failed", AccuracyHelper.getFieldValue(
                defaultUploadExternalServices, "fileStorageLocation"));
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadExternalServices#DefaultUploadExternalServices(UploadServices, String, String)}
     * constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_DefaultUploadExternalServices2() throws Exception {
        DefaultUploadExternalServices defaultUploadExternalServices = new DefaultUploadExternalServices(
                new DefaultUploadServices(), "accuracy_new_{0}", "test_files");
        // check for null
        assertNotNull("DefaultUploadExternalServices creation failed", defaultUploadExternalServices);
        // check the variable initialization
        assertNotNull("DefaultUploadExternalServices creation failed", AccuracyHelper.getFieldValue(
                defaultUploadExternalServices, "uploadServices"));
        assertEquals("DefaultUploadExternalServices creation failed", AccuracyHelper.getFieldValue(
                defaultUploadExternalServices, "filenamePattern"), "accuracy_new_{0}");
        assertEquals("DefaultUploadExternalServices creation failed", AccuracyHelper.getFieldValue(
                defaultUploadExternalServices, "fileStorageLocation"), "test_files");
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadExternalServices#uploadSubmission(long, long, String, DataHandler)}
     * method.
     * </p>
     * <p>
     * Checks whether the submission is uploaded to the given location and the default upload services upload
     * submission method is called.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_uploadSubmission() throws Exception {
        defaultUploadExternalServices.uploadSubmission(AccuracyHelper.PROJECT_ID, AccuracyHelper.USER_ID,
                AccuracyHelper.FILE_NAME, AccuracyHelper.getDataHandler());
        checkFile();
        File file = new File(AccuracyHelper.TEST_FILES + "test/accuracy_output.jar");
        assertEquals("uploadSubmission failed", mockUploadServices.getUploadSubmission(), MessageFormat.format(
                "{0}:{1}:{2}", new Object[] {AccuracyHelper.PROJECT_ID, AccuracyHelper.USER_ID,
                        file.getAbsolutePath() }));
        file.delete();
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadExternalServices#uploadFinalFix(long, long, String, DataHandler)}
     * method.
     * </p>
     * <p>
     * Checks whether the submission is uploaded to the given location and the default upload services upload final
     * fix method is called.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_uploadFinalFix() throws Exception {
        defaultUploadExternalServices.uploadFinalFix(AccuracyHelper.PROJECT_ID, AccuracyHelper.USER_ID,
                AccuracyHelper.FILE_NAME, AccuracyHelper.getDataHandler());
        checkFile();
        File file = new File(AccuracyHelper.TEST_FILES + "test/accuracy_output.jar");
        assertEquals("uploadFinalFix failed", mockUploadServices.getUploadFinalFix(), MessageFormat.format(
                "{0}:{1}:{2}", new Object[] {AccuracyHelper.PROJECT_ID, AccuracyHelper.USER_ID,
                        file.getAbsolutePath() }));
        file.delete();
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadExternalServices#uploadTestCases(long, long, String, DataHandler)}
     * method.
     * </p>
     * <p>
     * Checks whether the test cases is uploaded to the given location and the default upload services upload test
     * cases method is called.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_uploadTestCases() throws Exception {
        defaultUploadExternalServices.uploadTestCases(AccuracyHelper.PROJECT_ID, AccuracyHelper.USER_ID,
                AccuracyHelper.FILE_NAME, AccuracyHelper.getDataHandler());
        checkFile();
        File file = new File(AccuracyHelper.TEST_FILES + "test/accuracy_output.jar");
        assertEquals("uploadTestCases failed", mockUploadServices.getUploadTestCases(), MessageFormat.format(
                "{0}:{1}:{2}", new Object[] {AccuracyHelper.PROJECT_ID, AccuracyHelper.USER_ID,
                        file.getAbsolutePath() }));
        file.delete();
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultUploadExternalServices#setSubmissionStatus(long, long, String)} method.
     * </p>
     * <p>
     * Checks whether the corresponding setSubmissionStatus method is called.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_setSubmissionStatus() throws Exception {
        defaultUploadExternalServices.setSubmissionStatus(1, 1, "operator");
        assertEquals("setSubmissionStatus failed", mockUploadServices.getSetSubmissionStatus(), "1:1:operator");
    }

    /**
     * Checks whether the written file is same as the input
     * 
     * @throws Exception
     *             to Junit.
     */
    private void checkFile() throws Exception {
        File file = new File(AccuracyHelper.TEST_FILES + "test/accuracy_output.jar");
        assertTrue("file does not exist", file.exists());
        String output = getFileAsString(file);
        String input = getFileAsString(new File(AccuracyHelper.TEST_FILES + "test/input.jar"));
        assertEquals("File not written properly.", output, input);
    }

    /**
     * <p>
     * Gets the file content as string.
     * </p>
     * 
     * @param file
     *            The file to get its content.
     * @return The content of file.
     * @throws Exception
     *             to JUnit
     */
    private String getFileAsString(File file) throws Exception {
        StringBuffer buf = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s = null;
        while ((s = in.readLine()) != null) {
            buf.append(s);
        }
        in.close();
        return buf.toString();
    }

}
