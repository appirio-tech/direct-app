/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Return all failure test cases.
     * @return all failure test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ProjectServiceBeanFailureTests.class);

        return suite;
    }

}
