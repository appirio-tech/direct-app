/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * 
 * @author snow01
 * 
 * @since Jira & Confluence User Sync Service
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Aggregates all accuracy tests together.
     * 
     * @return all accuracy tests in one suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(UserServiceAccuracyTests.class);
        return suite;
    }
}
