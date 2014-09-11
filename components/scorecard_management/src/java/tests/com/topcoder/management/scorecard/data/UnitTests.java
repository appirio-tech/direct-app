/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public class UnitTests extends TestCase {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Creates a test suite for the unit tests in this component.
     *
     * @return  A TestSuite for the unit tests in this component.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(Demo.suite());
        suite.addTest(GroupTest.suite());
        suite.addTest(QuestionTest.suite());
        suite.addTest(QuestionTypeTest.suite());
        suite.addTest(ScorecardEditorTest.suite());
        suite.addTest(ScorecardStatusTest.suite());
        suite.addTest(ScorecardTest.suite());
        suite.addTest(ScorecardTypeTest.suite());
        suite.addTest(SectionTest.suite());
        suite.addTest(UtilTest.suite());

        return suite;
    }
}
