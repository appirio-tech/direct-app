/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

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
     * Aggregates all Failure test cases.
     *
     * @return the aggregated Failure test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ProjectTypeFailureTests.class);
        suite.addTestSuite(ProjectCategoryFailureTests.class);
        suite.addTestSuite(ProjectStatusFailureTests.class);
        suite.addTestSuite(ProjectPropertyTypeFailureTests.class);
        suite.addTestSuite(ProjectFilterUtilityFailureTests.class);
        suite.addTestSuite(ProjectFailureTests.class);
        suite.addTestSuite(AuditableObjectFailureTests.class);
        suite.addTestSuite(ProjectManagerImplFailureTests.class);
        suite.addTestSuite(DefaultProjectValidatorFailureTests.class);
        return suite;
    }
}
