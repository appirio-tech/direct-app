/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.resource.persistence.PersistenceResourceManagerTest;
import com.topcoder.management.resource.persistence.ResourcePersistenceExceptionTest;
import com.topcoder.management.resource.search.NotificationFilterBuilderTest;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilderTest;
import com.topcoder.management.resource.search.ResourceFilterBuilderTest;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilderTest;

/**
 * This test case aggregates all Unit test cases.
 *
 * @author kinfkong
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Tests suites.
     *
     * @return returns the unit test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Unit tests for the package: com.topcoder.management.resource
        suite.addTestSuite(AuditableResourceStructureTest.class);
        suite.addTestSuite(IdAlreadySetExceptionTest.class);
        suite.addTestSuite(NotificationTest.class);
        suite.addTestSuite(NotificationTypeTest.class);
        suite.addTestSuite(ResourceRoleTest.class);
        suite.addTestSuite(ResourceTest.class);

        // Unit tests for the package: com.topcoder.management.resource.persistence
        suite.addTestSuite(ResourcePersistenceExceptionTest.class);
        suite.addTestSuite(PersistenceResourceManagerTest.class);

        // Unit tests for the package: com.topcoder.management.resource.search
        suite.addTestSuite(NotificationFilterBuilderTest.class);
        suite.addTestSuite(NotificationTypeFilterBuilderTest.class);
        suite.addTestSuite(ResourceFilterBuilderTest.class);
        suite.addTestSuite(ResourceRoleFilterBuilderTest.class);

        // Unit tests for the demo
        suite.addTestSuite(Demo.class);

        // Unit tests for the helper
        suite.addTestSuite(HelperTest.class);

        return suite;
    }

}
