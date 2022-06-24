/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all accuracy test cases.
 *
 * @author Savior
 * @version 1.1
 */
public class AccuracyTests extends TestCase {
    /**
     * Aggregates all accuracy test cases.
     *
     * @return Test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AbstractDbPhasePersistenceTest.class);
        suite.addTestSuite(InformixPhasePersistenceTest.class);
        suite.addTestSuite(UnmanagedTransactionInformixPhasePersistenceTest.class);

        return suite;
    }
}
