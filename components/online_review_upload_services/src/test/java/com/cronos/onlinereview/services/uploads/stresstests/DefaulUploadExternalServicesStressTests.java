/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.stresstests;

import java.io.File;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import junit.framework.TestCase;

import com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices;

/**
 * <p>
 * This is the stress test fixture for <code>DefaultUploadExternalServices</code> class.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class DefaulUploadExternalServicesStressTests extends TestCase {
    /**
     * The instance of <code>DefaultUploadExternalServices</code> on which to perform tests.
     */
    private DefaultUploadExternalServices services;

    /**
     * Sets up the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        StressTestHelper.loadConfig("config.xml");
        services = new DefaultUploadExternalServices();
    }

    /**
     * Cleans up the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        clearFiles();
        StressTestHelper.clearConfig();
    }

    /**
     * <p>
     * Test method for <code>uploadSubmission</code>.
     * </p>
     * @throws Exception Exception to JUnit.
     */
    public void testUploadSubmission() throws Exception {
        FileDataSource dataSource = new FileDataSource("test_files/sample.jar");
        DataHandler dataHandler = new DataHandler(dataSource);

        Date startTime = new Date();
        for (int i = 0; i < 100; ++i) {
            services.uploadSubmission(30, 600, "submission.jar", dataHandler);
        }

        Date endTime = new Date();

        System.out.println("Executing uploadSubmission for 100 times takes "
            + (endTime.getTime() - startTime.getTime()) + " milliseconds");
    }

    /**
     * <p>
     * Test method for <code>uploadFinalFix</code>.
     * </p>
     * @throws Exception Exception to JUnit.
     */
    public void testUploadFinalFix() throws Exception {
        FileDataSource dataSource = new FileDataSource("test_files/sample.jar");
        DataHandler dataHandler = new DataHandler(dataSource);

        Date startTime = new Date();
        for (int i = 0; i < 100; ++i) {
            services.uploadFinalFix(30, 600, "finalfix.jar", dataHandler);
        }

        Date endTime = new Date();

        System.out.println("Executing uploadFinalFix for 100 times takes "
            + (endTime.getTime() - startTime.getTime()) + " milliseconds");
    }

    /**
     * <p>
     * Test method for <code>uploadTestCases</code>.
     * </p>
     * @throws Exception Exception to JUnit.
     */
    public void testUploadTestCases() throws Exception {
        FileDataSource dataSource = new FileDataSource("test_files/sample.jar");
        DataHandler dataHandler = new DataHandler(dataSource);

        Date startTime = new Date();
        for (int i = 0; i < 100; ++i) {
            services.uploadTestCases(30, 600, "testcases.jar", dataHandler);
        }

        Date endTime = new Date();

        System.out.println("Executing uploadTestCases for 100 times takes "
            + (endTime.getTime() - startTime.getTime()) + " milliseconds");
    }

    /**
     * <p>
     * Test method for <code>setSubmissionStatus</code>.
     * </p>
     * @throws Exception Exception to JUnit.
     */
    public void testSetSubmissionStatus() throws Exception {
        Date startTime = new Date();
        for (int i = 0; i < 100; ++i) {
            services.setSubmissionStatus(100 + i, 1, "tc");
        }

        Date endTime = new Date();

        System.out.println("Executing setSubmissionStatus for 100 times takes "
            + (endTime.getTime() - startTime.getTime()) + " milliseconds");
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
