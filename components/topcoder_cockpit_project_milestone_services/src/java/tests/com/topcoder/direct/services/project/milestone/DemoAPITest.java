/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.hibernate.BaseUnitTest;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.direct.services.project.milestone.model.SortOrder;

/**
 * <p>
 * Demo API tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoAPITest extends BaseUnitTest {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DemoAPITest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * The Demo API.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoAPI() throws Exception {
        // ResponsiblePersonService
        ResponsiblePersonService responsiblePersonService = (ResponsiblePersonService) APP_CONTEXT
            .getBean("responsiblePersonService");

        List<ResponsiblePerson> people = responsiblePersonService.getAllResponsiblePeople(1);



        // MilestoneService
        MilestoneService milestoneService = (MilestoneService) APP_CONTEXT.getBean("milestoneService");

        // Add
        Milestone milestone1 = createMilestone(1);
        long id1 = milestoneService.add(milestone1);

        // Add List
        Milestone milestone2 = createMilestone(2);
        milestone2.setDueDate(new Date(System.currentTimeMillis() + 1000 * 3600 * 24));
        milestone2.setCompleted(false);
        Milestone milestone3 = createMilestone(3);
        milestone3.setCompleted(false);

        List<Milestone> milestones = Arrays.asList(milestone2, milestone3);

        milestoneService.add(milestones);

        // Get
        Milestone oneResult = milestoneService.get(id1);

        // Get List
        List<Long> ids = Arrays.asList(milestone2.getId(), milestone3.getId());
        List<Milestone> listResult = milestoneService.get(ids);

        // Update
        milestone1.setName("new_name1");
        milestoneService.update(milestone1);

        // Update List
        milestone2.setName("new_name2");
        milestone3.setName("new_name3");
        milestoneService.update(milestones);

        // Delete
        milestoneService.delete(id1);

        // Delete List
        milestoneService.delete(ids);

        // Get All
        List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        requestedStatuses.add(MilestoneStatus.OVERDUE);
        requestedStatuses.add(MilestoneStatus.UPCOMING);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        List<Milestone> result = milestoneService.getAll(1, requestedStatuses, SortOrder.ASCENDING);

        // Get All In Month
        result = milestoneService.getAllInMonth(1, 12, 2011, requestedStatuses);
    }

}
