/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.dao.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author cyberjag
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
        suite.addTestSuite(ClientDAOAccuracyTests.class);
        suite.addTestSuite(ClientStatusDAOAccuracyTests.class);
        suite.addTestSuite(CompanyDAOAccuracyTests.class);
        suite.addTestSuite(ProjectDAOAccuracyTests.class);
        suite.addTestSuite(ProjectStatusDAOAccuracyTests.class);
        return suite;
    }
}
