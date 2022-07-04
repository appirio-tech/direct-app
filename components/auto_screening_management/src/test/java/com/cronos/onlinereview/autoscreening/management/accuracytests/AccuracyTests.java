/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
	
    /**
     * <p>Aggregates all Accuracy test cases.</p>
     *
     * @return Test instance aggregating all accuracy test cases.
     */
    public static Test suite() {
    	
        final TestSuite suite = new TestSuite();
        
        // Accuracy Tests of Exceptions.
        suite.addTestSuite(ScreeningTaskDoesNotExistExceptionAccuracyTest.class);
        suite.addTestSuite(ScreeningTaskAlreadyExistsExceptionAccuracyTest.class);
        suite.addTestSuite(ConfigurationExceptionAccuracyTest.class);
        suite.addTestSuite(PersistenceExceptionAccurayTest.class);
        suite.addTestSuite(ScreeningManagementExceptionAccurayTest.class);
        
        // Managerment classes test.
        suite.addTestSuite(ResponseSeverityAccuracyTest.class);
        suite.addTestSuite(ScreeningResponseAccuracyTest.class);
        suite.addTestSuite(ScreeningStatusAccuracyTest.class);
        suite.addTestSuite(ScreeningResultAccuracyTest.class);
        suite.addTestSuite(ScreeningTaskAccuracyTest.class);
        suite.addTestSuite(ScreeningManagerFactoryAccuracyTest.class);
        
        // db classes test.
        suite.addTestSuite(DefaultDbScreeningManagerAccuracyTest.class);
        
        return suite;
    }
}
