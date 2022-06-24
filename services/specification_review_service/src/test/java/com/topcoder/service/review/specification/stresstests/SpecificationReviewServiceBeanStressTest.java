/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.stresstests;

import com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices;

import com.topcoder.project.service.ejb.ProjectServicesBean;

import com.topcoder.security.TCSubject;

import com.topcoder.service.review.specification.ejb.SpecificationReviewServiceBean;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * <p>Stress test case of {@link SpecificationReviewServiceBean}.</p>
 *
 * @author onsky
 * @version 1.0
 */
public class SpecificationReviewServiceBeanStressTest extends BaseStressTest {
    /**
     * <p>The SpecificationReviewServiceBean instance to test.</p>
     */
    private SpecificationReviewServiceBean instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    public void setUp() throws Exception {
        super.setUp();
        instance = new SpecificationReviewServiceBean();
        setPrivateField(SpecificationReviewServiceBean.class, instance, "projectServices", new ProjectServicesBean());
        setPrivateField(SpecificationReviewServiceBean.class, instance, "mockSubmissionFilePath",
            "test_files/mock_submission/");
        setPrivateField(SpecificationReviewServiceBean.class, instance, "mockSubmissionFileName", "mock_rs.rtf");
        setPrivateField(SpecificationReviewServiceBean.class, instance, "uploadManager", new MockUploadManager());
        setPrivateField(SpecificationReviewServiceBean.class, instance, "resourceManager", new MockResourceManager());
        setPrivateField(SpecificationReviewServiceBean.class, instance, "uploadExternalServices",
            new DefaultUploadExternalServices());
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>Creates a test suite for the tests in this test case.</p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(SpecificationReviewServiceBeanStressTest.class);

        return suite;
    }

    /**
     * <p>Stress test for method SpecificationReviewServiceBean#scheduleSpecificationReview().</p>
     *
     * @throws Throwable to junit
     */
    public void test_scheduleSpecificationReview() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new Thread() {
                        public void run() {
                            try {
                                instance.scheduleSpecificationReview(new TCSubject(null, 1001L), 1, new Date());
                            } catch (Throwable e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run scheduleSpecificationReview for " + testCount + " times takes " +
            (new Date().getTime() - start) + "ms");
    }

    /**
     * <p>Stress test for method SpecificationReviewServiceBean#submitSpecificationAsFile().</p>
     *
     * @throws Throwable to junit
     */
    public void test_submitSpecificationAsFile() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new Thread() {
                        public void run() {
                            try {
                                instance.submitSpecificationAsFile(new TCSubject(null, 1001L), 1, "test_files/stress/test_file.doc");
                            } catch (Throwable e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run submitSpecificationAsFile for " + testCount + " times takes " +
            (new Date().getTime() - start) + "ms");
    }

    /**
     * <p>Stress test for method SpecificationReviewServiceBean#submitSpecificationAsString().</p>
     *
     * @throws Throwable to junit
     */
    public void test_submitSpecificationAsString() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new Thread() {
                        public void run() {
                            try {
                                instance.submitSpecificationAsString(new TCSubject(null, 1001L), 1, "this is a test");
                            } catch (Throwable e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run submitSpecificationAsString for " + testCount + " times takes " +
            (new Date().getTime() - start) + "ms");
    }

    /**
     * <p>Stress test for method SpecificationReviewServiceBean#getSpecificationReviewStatus().</p>
     *
     * @throws Throwable to junit
     */
    public void test_getSpecificationReviewStatus() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new Thread() {
                        public void run() {
                            try {
                                instance.getSpecificationReviewStatus(new TCSubject(null, 1001L), 1);
                            } catch (Throwable e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run getSpecificationReviewStatus for " + testCount + " times takes " +
            (new Date().getTime() - start) + "ms");
    }
}
