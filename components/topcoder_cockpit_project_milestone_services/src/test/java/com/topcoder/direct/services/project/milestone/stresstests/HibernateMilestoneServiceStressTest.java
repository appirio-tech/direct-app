/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;

/**
 * <p>
 * Stress tests for HibernateMilestoneService class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class HibernateMilestoneServiceStressTest extends BaseStressTest {
    /**
     * <p>
     * Represents the milestoneService for testing.
     * </p>
     */
    @Autowired
    private MilestoneService instance;

    /**
     * <p>
     * Represents the last error.
     * </p>
     */
    private Throwable lastError;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(HibernateMilestoneServiceStressTest.class);
    }

    /**
     * <p>
     * Stress test for {@link MilestoneService#add(Milestone)}, {@link MilestoneService#delete(long)},
     * {@link MilestoneService#update(Milestone)}, {@link MilestoneService#delete(long)}.
     * </p>
     *
     * @throws Throwable
     *             to JUnit
     */
    @Test
    public void test_CRUD_Milestone() throws Throwable {
        Thread[] threads = new Thread[(int) NUMBER];
        lastError = null;
        for (int i = 0; i < NUMBER; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    try {
                        Milestone milestone = new Milestone();
                        milestone.setDescription("test");
                        milestone.setName("test");
                        milestone.setDueDate(new Date());
                        // add the milestone
                        long id = instance.add(milestone);
                        Assert.assertTrue("fail to add", id > 0);
                        // update the milestone
                        milestone.setName("update");
                        instance.update(milestone);
                        Assert.assertEquals("fail to update", "update", instance.get(id).getName());
                        // delete the milestone
                        instance.delete(id);
                        Assert.assertNull("fail to delete", instance.get(id));
                    } catch (Throwable e) {
                        lastError = e;
                    }
                }
            });
        }

        start();

        // start the threads
        for (int i = 0; i < NUMBER; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NUMBER; i++) {
            threads[i].join();
        }
        // throw the last error
        if (lastError != null) {
            throw lastError;
        }
        printResult("milestoneService#add(Milestone)_update(Milestone)_delete(long)_get(long)", NUMBER);
    }

    /**
     * <p>
     * Stress test for {@link MilestoneService#add(List)} method {@link MilestoneService#update(List)},
     * {@link MilestoneService#get(List)} and {@link MilestoneService#delete(List)} method.
     * </p>
     *
     * @throws Throwable
     *             to JUnit
     */
    @Test
    @Transactional
    public void test_CRUD_Milestones() throws Throwable {
        Thread[] threads = new Thread[(int) NUMBER];
        lastError = null;
        for (int i = 0; i < NUMBER; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    try {
                        Milestone milestone = new Milestone();
                        milestone.setDescription("test");
                        milestone.setName("test");
                        milestone.setDueDate(new Date());
                        List<Milestone> list = new ArrayList<Milestone>();
                        list.add(milestone);
                        // add the milestones
                        instance.add(list);
                        List<Long> ids = new ArrayList<Long>();
                        ids.add(milestone.getId());
                        Assert.assertEquals("fail to add", 1, instance.get(ids).size());
                        // update the milestones
                        milestone.setName("update");
                        instance.update(list);
                        Assert.assertEquals("fail to update", "update", instance.get(ids).get(0).getName());
                        // delete the milestones
                        instance.delete(ids);
                        Assert.assertEquals("fail to delete", null, instance.get(ids).get(0));
                    } catch (Throwable e) {
                        lastError = e;
                    }
                }
            });
        }
        // start the threads
        for (int i = 0; i < NUMBER; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NUMBER; i++) {
            threads[i].join();
        }
        // throw the last error
        if (lastError != null) {
            throw lastError;
        }
        printResult("milestoneService#add(List<Milestone>)_update(List<Milestone>)_delete(List<Long>)_get(List<Long>)",
                NUMBER);
    }

    /**
     * <p>
     * Stress test for {@link MilestoneService#getAll(long, List, SortOrder)} and
     * {@link MilestoneService#getAllInMonth(long, int, int, List)} method.
     *
     * @throws Throwable
     *             to JUnit
     */
    @Test
    public void test_getAll_getAllInMonth() throws Throwable {
        Thread[] threads = new Thread[(int) NUMBER];
        lastError = null;
        Milestone milestone = new Milestone();
        milestone.setDescription("test");
        milestone.setName("test");
        milestone.setDueDate(new Date());
        milestone.setProjectId(1);
        instance.add(milestone);

        for (int i = 0; i < NUMBER; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    try {
                        List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
                        requestedStatuses.add(MilestoneStatus.OVERDUE);
                        requestedStatuses.add(MilestoneStatus.UPCOMING);
                        requestedStatuses.add(MilestoneStatus.COMPLETED);
                        // get all milestones
                        List<Milestone> list = instance.getAll(1, requestedStatuses, SortOrder.DESCENDING);
                        Assert.assertEquals("fail to get all", 1, list.size());
                        // get all milestones in special month
                        list = instance.getAllInMonth(1, 12, 2011, requestedStatuses);
                        Assert.assertEquals("fail to ge in month", 1, list.size());
                    } catch (Throwable e) {
                        lastError = e;
                    }

                }
            });
        }
        // start the threads
        for (int i = 0; i < NUMBER; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NUMBER; i++) {
            threads[i].join();
        }
        // throw the last error
        if (lastError != null) {
            throw lastError;
        }
        printResult("MilestoneService#getAll_getAllInMonth)", NUMBER);
    }
}
