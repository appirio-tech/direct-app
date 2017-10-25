/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade;

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

        suite.addTest(com.topcoder.service.usersync.AllTests.suite());

        return suite;
    }
}
