/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * Initialize the AccuracyTests to test.
     * </p>
     * @return a TestSuite for AccuracyTests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // add each search filter accuracy test here.
        suite.addTest(NotificationFilterBuilderAccuracyTest.suite());
        suite.addTest(NotificationTypeFilterBuilderAccuracyTest.suite());
        suite.addTest(ResourceFilterBuilderAccuracyTest.suite());
        suite.addTest(ResourceRoleFilterBuilderAccuracyTest.suite());

        // add each exception accuracy test here.
        suite.addTest(ResourcePersistenceExceptionAccuracyTest.suite());
        suite.addTest(IdAlreadySetExceptionAccuracyTest.suite());

        // add data classes accuracy test here.
        suite.addTest(AuditableResourceStructureAccuracyTest.suite());
        suite.addTest(NotificationTypeAccuracyTest.suite());
        suite.addTest(NotificationAccuracyTest.suite());
        suite.addTest(ResourceRoleAccuracyTest.suite());
        suite.addTest(ResourceAccuracyTest.suite());

        // add persistence resource manager accuracy test here.
        suite.addTest(PersistenceResourceManagerAccuracyTest.suite());

        return suite;
    }
}
