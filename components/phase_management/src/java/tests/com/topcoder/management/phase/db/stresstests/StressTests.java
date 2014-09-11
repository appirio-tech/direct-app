/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all stress test cases.</p>
 *
 * @author 
 * @version 1.1
 */
public class StressTests extends TestCase {
    /**
     * <p>The method aggregates all stress test cases.</p>
     *
     * @return the test suite of the stress test.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(InformixPhasePersistenceStressTest.class);
        suite.addTestSuite(AbstractInformixPhasePersistenceStressTest.class);
        suite.addTestSuite(UnmanagedTransactionInformixPhasePersistenceStressTest.class);

        return suite;
    }
}
