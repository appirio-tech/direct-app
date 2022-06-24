/*
 * Copyright (C) 2007 - 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author urtks, fuyun, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(InformixProjectPersistenceTest.suite());
        suite.addTest(DemoTest.suite());
        suite.addTestSuite(InformixProjectPersistenceV11Test.class);
        suite.addTestSuite(UnmanagedTransactionInformixProjectPersistenceTest.class);
        suite.addTestSuite(HelperTest.class);

        // add new test cases in version 1.2
        suite.addTestSuite(AbstractInformixProjectPersistenceUnitTests.class);
        suite.addTestSuite(DemoV12Test.class);

        return suite;
    }
}
