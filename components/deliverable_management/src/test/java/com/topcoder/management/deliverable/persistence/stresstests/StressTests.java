/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.deliverable.persistence.sql.stresstests.SqlDeliverablePersistenceStressTest;
import com.topcoder.management.deliverable.persistence.sql.stresstests.SqlUploadPersistenceStressTest;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * @author kinfkong
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Returns the tests of the stress tests.
     * @return the tests of the stress tests.
     */
    public static Test suite() {
        /**
         * the suite
         */
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(SqlDeliverablePersistenceStressTest.class);

        suite.addTestSuite(SqlUploadPersistenceStressTest.class);

        return suite;
    }
}
