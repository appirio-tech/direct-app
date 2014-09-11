/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */



package com.topcoder.management.deliverable.persistence.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(SqlDeliverablePersistenceAccuracyTest.class);
        suite.addTestSuite(SqlUploadPersistenceAccuracyTest.class);
        suite.addTestSuite(LogMessageAccuracyTests.class);

        return suite;
    }
}
