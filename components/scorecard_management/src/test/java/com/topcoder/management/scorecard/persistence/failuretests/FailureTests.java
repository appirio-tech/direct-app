/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.persistence.failuretests;

import com.topcoder.management.scorecard.persistence.InformixGroupPersistenceFailureTest;
import com.topcoder.management.scorecard.persistence.InformixQuestionPersistenceFailureTest;
import com.topcoder.management.scorecard.persistence.InformixSectionPersistenceFailureTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Aggregate the stress tests.
     *
     * @return Test instance
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(InformixScorecardPersistenceFailureTest.class);
        suite.addTestSuite(InformixSectionPersistenceFailureTest.class);
        suite.addTestSuite(InformixQuestionPersistenceFailureTest.class);
        suite.addTestSuite(InformixGroupPersistenceFailureTest.class);

        return suite;
    }
}
