/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author King_Bette
 * @version 1.1
 */
public class FailureTests extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AbstractResourcePersistenceFailureTest.suite());
        suite.addTest(SqlResourcePersistenceFailureTest.suite());
        suite.addTest(UnmanagedTransactionResourcePersistenceFailureTest.suite());
        return suite;
    }

}
