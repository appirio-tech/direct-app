/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user;

import com.topcoder.service.user.accuracytests.AccuracyTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * 
 * @author snow01
 * 
 * @since Jira & Confluence User Sync Service
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>
     * This test case aggregates all tests in and child package.
     * </p>
     * 
     * @return the test suite for the component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        return suite;
    }

}
