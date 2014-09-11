/*
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.phase.db;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Returns the test suite.
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(Demo.class);
        suite.addTestSuite(AbstractInformixPhasePersistenceTest.class);
        suite.addTestSuite(AbstractDbPhasePersistenceTest.class);
        suite.addTestSuite(InformixPhasePersistenceTest.class);
        suite.addTestSuite(UnmanagedTransactionInformixPhasePersistenceTest.class);
        //suite.addTestSuite(DemoV11Test.class);
        return suite;
    }

}
