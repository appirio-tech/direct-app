/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(JPATaskListServiceFailureTests.suite());
        suite.addTest(BaseJPAServiceFailureTests.suite());
        suite.addTest(EmailEngineNotificationServiceFailureTests.suite());
        suite.addTest(JPATaskServiceFailureTests.suite());

        return suite;
    }
}
