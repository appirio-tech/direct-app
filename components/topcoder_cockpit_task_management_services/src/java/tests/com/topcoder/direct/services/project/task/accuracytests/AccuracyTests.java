/**
 *
 * Copyright (c) 2013, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.services.project.task.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.JUnit4TestAdapter;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author amazingpig
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(EmailEngineNotificationServiceAccTests.class);
        suite.addTestSuite(JPATaskServiceAccTests.class);
        suite.addTestSuite(JPATaskListServiceAccTests.class);

        suite.addTest(new JUnit4TestAdapter(MilestoneDTOTests.class));
        suite.addTest(new JUnit4TestAdapter(IdentifiableEntityTests.class));
        suite.addTest(new JUnit4TestAdapter(ContestDTOTests.class));
        suite.addTest(new JUnit4TestAdapter(BaseTaskEntityTests.class));
        suite.addTest(new JUnit4TestAdapter(AuditableEntityTests.class));
        suite.addTest(new JUnit4TestAdapter(TaskAttachmentTests.class));
        suite.addTest(new JUnit4TestAdapter(TaskFilterTests.class));
        suite.addTest(new JUnit4TestAdapter(TaskListTests.class));
        suite.addTest(new JUnit4TestAdapter(TaskTests.class));
        suite.addTest(new JUnit4TestAdapter(UserDTOTests.class));
        suite.addTest(new JUnit4TestAdapter(EntityNotFoundExceptionTests.class));
        suite.addTest(new JUnit4TestAdapter(PermissionExceptionTests.class));
        suite.addTest(new JUnit4TestAdapter(PersistenceExceptionTests.class));
        suite.addTest(new JUnit4TestAdapter(TaskManagementConfigurationExceptionTests.class));
        suite.addTest(new JUnit4TestAdapter(TaskManagementExceptionTests.class));
        return suite;
    }

}
