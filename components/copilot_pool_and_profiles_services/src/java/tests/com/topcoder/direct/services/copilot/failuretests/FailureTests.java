/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Aggregate all failure cases.
     *
     * @return the cases suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(CopilotProfileDAOImplFailureTests.suite());
        suite.addTest(UtilityDAOImplFailureTests.suite());
        suite.addTest(CopilotProjectDAOImplFailureTests.suite());
        suite.addTest(CopilotProjectPlanDAOImplFailureTests.suite());
        suite.addTest(GenericDAOImplFailureTests.suite());
        suite.addTest(LookupDAOImplFailureTests.suite());

        return suite;
    }
}
