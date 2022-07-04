/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests;

import com.topcoder.management.resource.failuretests.persistence.PersistenceResourceManagerFailureTest;
import com.topcoder.management.resource.failuretests.persistence.PersistenceResourceManagerFailureTest_InvalidSearchBundle;
import com.topcoder.management.resource.failuretests.search.NotificationFilterBuilderFailureTest;
import com.topcoder.management.resource.failuretests.search.NotificationTypeFilterBuilderFailureTest;
import com.topcoder.management.resource.failuretests.search.ResourceFilterBuilderFailureTest;
import com.topcoder.management.resource.failuretests.search.ResourceRoleFilterBuilderFailureTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.1
 * @since 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Suite all the failure tests.
     *
     * @return a suite of failure tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // main package
        suite.addTestSuite(NotificationFailureTest.class);
        suite.addTestSuite(NotificationTypeFailureTest.class);
        suite.addTestSuite(ResourceFailureTest.class);
        suite.addTestSuite(ResourceRoleFailureTest.class);

        // search sub package
        suite.addTestSuite(NotificationFilterBuilderFailureTest.class);
        suite.addTestSuite(NotificationTypeFilterBuilderFailureTest.class);
        suite.addTestSuite(ResourceRoleFilterBuilderFailureTest.class);
        suite.addTestSuite(ResourceFilterBuilderFailureTest.class);

        // persistence sub package
        suite.addTestSuite(PersistenceResourceManagerFailureTest.class);
        suite.addTestSuite(PersistenceResourceManagerFailureTest_InvalidSearchBundle.class);

        return suite;
    }

}
