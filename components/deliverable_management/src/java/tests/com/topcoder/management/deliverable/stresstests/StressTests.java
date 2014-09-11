/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */



package com.topcoder.management.deliverable.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author morehappiness
 * @version 1.1
 */
public class StressTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(StressTestPersistenceUploadManager.class);

        suite.addTestSuite(StressTestPersistenceDeliverableManager.class);

        return suite;
    }
}
