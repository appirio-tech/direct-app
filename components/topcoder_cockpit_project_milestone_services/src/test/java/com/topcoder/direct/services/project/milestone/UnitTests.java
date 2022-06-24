/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.direct.services.project.milestone.hibernate.HibernateMilestoneServiceTest;
import com.topcoder.direct.services.project.milestone.hibernate.HibernateResponsiblePersonServiceTest;
import com.topcoder.direct.services.project.milestone.model.IdentifiableEntityTest;
import com.topcoder.direct.services.project.milestone.model.MilestoneTest;
import com.topcoder.direct.services.project.milestone.model.NamedEntityTest;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePersonTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * The Unit test suites.
     *
     * @return the test suites.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(EntityNotFoundExceptionTest.suite());
        suite.addTest(ProjectMilestoneManagementConfigurationExceptionTest.suite());
        suite.addTest(ProjectMilestoneManagementExceptionTest.suite());

        suite.addTest(HibernateMilestoneServiceTest.suite());
        suite.addTest(HibernateResponsiblePersonServiceTest.suite());

        suite.addTest(IdentifiableEntityTest.suite());
        suite.addTest(MilestoneTest.suite());
        suite.addTest(NamedEntityTest.suite());
        suite.addTest(ResponsiblePersonTest.suite());

        suite.addTest(DemoAPITest.suite());

        return suite;
    }

}
