/**
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.phase.db.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author waits
 * @version 1.1
 * 
 */
public class FailureTests extends TestCase {
    /**
     * Failure test suites.
     *
     * @return Test into JUnit
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(InformixPhasePersistenceFailureTests.class);
        suite.addTestSuite(UnmanagedTransactionInformixPhasePersistenceFailureTests.class);

        return suite;
    }
}
