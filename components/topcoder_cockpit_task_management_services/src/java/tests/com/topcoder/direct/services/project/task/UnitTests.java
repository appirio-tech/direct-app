/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

import com.topcoder.direct.services.project.task.impl.BaseJPAServiceTests;
import com.topcoder.direct.services.project.task.impl.EmailEngineNotificationServiceTests;
import com.topcoder.direct.services.project.task.impl.JPATaskListServiceTests;
import com.topcoder.direct.services.project.task.impl.JPATaskServiceTests;
import com.topcoder.direct.services.project.task.impl.ServiceHelperTests;
import com.topcoder.direct.services.project.task.model.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all the unit tests.
     * </p>
     * @return all the unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(DemoTests.suite());
        suite.addTest(NotificationExceptionTests.suite());

        suite.addTest(BaseJPAServiceTests.suite());
        suite.addTest(EmailEngineNotificationServiceTests.suite());
        suite.addTest(JPATaskListServiceTests.suite());
        suite.addTest(JPATaskServiceTests.suite());
        suite.addTest(ServiceHelperTests.suite());

        suite.addTest(Demo.suite());

        suite.addTest(TaskPriorityUnitTests.suite());
        suite.addTest(TaskStatusUnitTests.suite());

        suite.addTest(AuditableEntityUnitTests.suite());
        suite.addTest(BaseTaskEntityUnitTests.suite());
        suite.addTest(ContestDTOUnitTests.suite());
        suite.addTest(IdentifiableEntityUnitTests.suite());
        suite.addTest(MilestoneDTOUnitTests.suite());
        suite.addTest(TaskAttachmentUnitTests.suite());
        suite.addTest(TaskFilterUnitTests.suite());
        suite.addTest(TaskListUnitTests.suite());
        suite.addTest(TaskUnitTests.suite());
        suite.addTest(UserDTOUnitTests.suite());

        // Exceptions
        suite.addTest(TaskManagementConfigurationExceptionUnitTests.suite());
        suite.addTest(TaskManagementExceptionUnitTests.suite());
        suite.addTest(PersistenceExceptionUnitTests.suite());
        suite.addTest(PermissionExceptionUnitTests.suite());
        suite.addTest(EntityNotFoundExceptionUnitTests.suite());

        return suite;
    }

}
