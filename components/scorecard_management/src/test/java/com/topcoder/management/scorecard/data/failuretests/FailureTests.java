/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all Failure test cases.
     * </p>
     *
     * @return  all Failure test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // tests for package com.topcoder.management.scorecard.data
        suite.addTest(WeightedScorecardStructureFailureTests.suite());
        suite.addTest(ScorecardStatusFailureTests.suite());
        suite.addTest(QuestionTypeFailureTests.suite());
        suite.addTest(ScorecardFailureTests.suite());
        suite.addTest(ScorecardEditorFailureTests.suite());
        suite.addTest(QuestionFailureTests.suite());
        suite.addTest(GroupFailureTests.suite());
        suite.addTest(NamedScorecardStructureFailureTests.suite());
        suite.addTest(ScorecardTypeFailureTests.suite());
        suite.addTest(SectionFailureTests.suite());
        return suite;
    }

}
