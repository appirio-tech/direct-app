/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.accuracy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.direct.services.project.milestone.model.SortOrder;

/**
 * <p>
 * Accuracy unit tests for class implementation of {@link MilestoneService}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class MilestoneServiceImplUnitTests extends BaseAccuracyTest {
    /**
     * <p>
     * private MilestoneService instance for testing.
     * </p>
     */
    private MilestoneService instance;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(MilestoneServiceImplUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = (MilestoneService) APP_CONTEXT.getBean("milestoneService");
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for constructor.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(instance);
    }

    /**
     * <p>
     * Accuracy test for {@link MilestoneService#add(Milestone)} and {@link MilestoneService#get(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testAddAndGet() throws Exception {
        Milestone milestone = createMilestone(1);
        instance.add(milestone);
        long id = milestone.getId();
        assertTrue(id > 0);
        Milestone getMilestone = instance.get(id);
        compareMilestone(milestone, getMilestone);
    }

    /**
     * <p>
     * Accuracy test for {@link MilestoneService#add(java.util.List)} and {@link MilestoneService#get(java.util.List)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testAddManyAndGetMany() throws Exception {
        Milestone milestone1 = createMilestone(1);
        Milestone milestone2 = createMilestone(2);
        instance.add(Arrays.asList(milestone1, milestone2));
        List<Milestone> list = instance.get(Arrays.asList(milestone1.getId(), milestone2.getId()));
        assertEquals(2, list.size());
        compareMilestone(milestone1, list.get(0));
        compareMilestone(milestone2, list.get(1));
    }

    /**
     * <p>
     * Accuracy test for {@link MilestoneService#update(Milestone)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testUpdate() throws Exception {
        Milestone milestone = createMilestone(1);
        instance.add(milestone);
        milestone.setDescription("new");
        instance.update(milestone);
        assertEquals("new", instance.get(milestone.getId()).getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestoneService#update(List)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testUpdateMany() throws Exception {
        Milestone milestone1 = createMilestone(1);
        Milestone milestone2 = createMilestone(2);
        instance.add(milestone1);
        instance.add(milestone2);
        milestone1.setDescription("new1");
        milestone2.setDescription("new2");
        instance.update(Arrays.asList(milestone1, milestone2));
        assertEquals("new1", instance.get(milestone1.getId()).getDescription());
        assertEquals("new2", instance.get(milestone2.getId()).getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestoneService#delete(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDelete() throws Exception {
        Milestone milestone = createMilestone(1);
        instance.add(milestone);
        instance.delete(milestone.getId());
        assertNull(instance.get(milestone.getId()));
    }

    /**
     * <p>
     * Accuracy test for {@link MilestoneService#delete(List)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDeleteMany() throws Exception {
        Milestone milestone1 = createMilestone(1);
        Milestone milestone2 = createMilestone(2);
        instance.add(Arrays.asList(milestone1, milestone2));
        instance.delete(Arrays.asList(milestone1.getId(), milestone2.getId()));
        assertEquals(2, instance.get(Arrays.asList(milestone1.getId(), milestone2.getId())).size());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestoneService#getAll(long, List, SortOrder)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetAll() throws Exception {
        Milestone milestone1 = createMilestone(1);
        Milestone milestone2 = createMilestone(1);
        Milestone milestone3 = createMilestone(1);
        instance.add(Arrays.asList(milestone1, milestone2, milestone3, createMilestone(2)));
        List<Milestone> list = instance.getAll(
                1, Arrays.asList(MilestoneStatus.COMPLETED), SortOrder.ASCENDING);
        assertEquals(3, list.size());
        compareMilestone(milestone1, list.get(0));
        compareMilestone(milestone2, list.get(1));
        compareMilestone(milestone3, list.get(2));
    }

    /**
     * <p>
     * Accuracy test for {@link MilestoneService#getAllInMonth(long, int, int, List)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetAllMonth() throws Exception {
        Milestone milestone1 = createMilestone(1);
        Milestone milestone2 = createMilestone(1);
        Milestone milestone3 = createMilestone(1);
        instance.add(Arrays.asList(milestone1, milestone2, milestone3, createMilestone(2)));
        List<Milestone> list = instance.getAllInMonth(1, 12, 2011, Arrays.asList(MilestoneStatus.COMPLETED));
        assertEquals(3, list.size());
        compareMilestone(milestone1, list.get(0));
        compareMilestone(milestone2, list.get(1));
        compareMilestone(milestone3, list.get(2));
    }

    /**
     * <p>
     * Compare two Milestone entity.
     * </p>
     *
     * @param milestone the Milestone to compare
     * @param getMilestone the Milestone to be compared
     */
    private void compareMilestone(Milestone milestone, Milestone getMilestone) {
        assertEquals(milestone.getDescription(), getMilestone.getDescription());
        assertEquals(milestone.getDueDate(), getMilestone.getDueDate());
        assertEquals(milestone.getName(), getMilestone.getName());
        assertEquals(milestone.getProjectId(), getMilestone.getProjectId());
        assertEquals(milestone.getOwners().size(), getMilestone.getOwners().size());
        assertNotNull(getMilestone.getStatus());
        for (int i = 0; i < milestone.getOwners().size(); i++) {
            assertEquals(milestone.getOwners().get(i).getName(), getMilestone.getOwners().get(i).getName());
            assertEquals(milestone.getOwners().get(i).getUserId(), getMilestone.getOwners().get(i).getUserId());
        }
    }

    /**
     * Create the milestone instance.
     *
     * @param id the id used to construct field value
     *
     * @return the milestone instance
     */
    private Milestone createMilestone(int id) {
        Milestone milestone = new Milestone();
        milestone.setCompleted(true);
        milestone.setDescription("description" + id);
        milestone.setDueDate(new Date());
        milestone.setName("name" + id);
        milestone.setSendNotifications(true);
        milestone.setProjectId(id);
        milestone.setOwners(new ArrayList<ResponsiblePerson>());
        milestone.getOwners().add(new ResponsiblePerson());
        milestone.getOwners().get(0).setName("person1");
        milestone.getOwners().get(0).setUserId(1);
        milestone.getOwners().add(new ResponsiblePerson());
        milestone.getOwners().get(1).setName("person2");
        milestone.getOwners().get(1).setUserId(2);
        return milestone;
    }
}
