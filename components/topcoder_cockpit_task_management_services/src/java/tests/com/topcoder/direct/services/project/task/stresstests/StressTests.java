/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This is the stress tests for this component.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     * 
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTestSuite(EmailEngineNotificationServiceStressTests.class);
        suite.addTestSuite(JPATaskListServiceStressTests.class);
        suite.addTestSuite(JPATaskServiceStressTests.class);
        return suite;
    }

}
