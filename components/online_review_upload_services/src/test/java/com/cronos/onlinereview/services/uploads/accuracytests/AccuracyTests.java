/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * 
 * @author kshatriyan
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * Aggregates all Accuracy test cases.
     * 
     * @return aggregates all Accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ConfigurationExceptionAccuracyTest.suite());
        suite.addTest(DefaultManagersProviderAccuracyTest.suite());
        suite.addTest(DefaultUploadExternalServicesAccuracyTest.suite());
        suite.addTest(DefaultUploadServicesAccuracyTest.suite());
        suite.addTest(InvalidProjectExceptionAccuracyTest.suite());
        suite.addTest(InvalidProjectPhaseExceptionAccuracyTest.suite());
        suite.addTest(InvalidSubmissionExceptionAccuracyTest.suite());
        suite.addTest(InvalidSubmissionStatusExceptionAccuracyTest.suite());
        suite.addTest(InvalidUserExceptionAccuracyTest.suite());
        suite.addTest(PersistenceExceptionAccuracyTest.suite());
        suite.addTest(UploadServicesExceptionAccuracyTest.suite());
        return suite;
    }

}
