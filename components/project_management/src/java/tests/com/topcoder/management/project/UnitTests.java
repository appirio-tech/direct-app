/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author iamajia, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AuditableObjectTest.suite());
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(DefaultProjectValidatorTest.suite());
        suite.addTest(HelperTest.suite());
        suite.addTest(PersistenceExceptionTest.suite());
        suite.addTest(ProjectCategoryTest.suite());
        suite.addTest(ProjectFilterUtilityFailureTest.suite());
        suite.addTest(ProjectFilterUtilityAccuracyTest.suite());
        suite.addTest(ProjectStatusTest.suite());
        suite.addTest(ProjectTest.suite());
        suite.addTest(ProjectTypeTest.suite());
        suite.addTest(ValidationExceptionTest.suite());
        suite.addTest(ProjectManagerImplTest.suite());
        suite.addTestSuite(FileTypeUnitTests.class);
        suite.addTestSuite(PrizeTypeUnitTests.class);
        suite.addTestSuite(PrizeUnitTests.class);
        suite.addTestSuite(ProjectStudioSpecificationUnitTests.class);
        suite.addTest(DemoTest.suite());
        return suite;
    }

}
