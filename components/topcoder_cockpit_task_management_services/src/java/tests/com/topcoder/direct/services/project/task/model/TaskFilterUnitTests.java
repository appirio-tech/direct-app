/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.TestsHelper;

/**
 * <p>
 * Unit tests for {@link TaskFilter} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TaskFilterUnitTests {
    /**
     * <p>
     * Represents the <code>TaskFilter</code> instance used in tests.
     * </p>
     */
    private TaskFilter instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TaskFilterUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new TaskFilter();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>TaskFilter()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new TaskFilter();

        assertNull("'name' should be correct.", TestsHelper.getField(instance, "name"));
        assertNull("'assigneeId' should be correct.", TestsHelper.getField(instance, "assigneeId"));
        assertNull("'dueDateFrom' should be correct.", TestsHelper.getField(instance, "dueDateFrom"));
        assertNull("'dueDateTo' should be correct.", TestsHelper.getField(instance, "dueDateTo"));
        assertNull("'priorities' should be correct.", TestsHelper.getField(instance, "priorities"));
        assertNull("'statuses' should be correct.", TestsHelper.getField(instance, "statuses"));
        assertNull("'associatedToProjectMilestoneIds' should be correct.",
                TestsHelper.getField(instance, "associatedToProjectMilestoneIds"));
        assertNull("'associatedToContestIds' should be correct.",
                TestsHelper.getField(instance, "associatedToContestIds"));
        assertNull("'projectIds' should be correct.", TestsHelper.getField(instance, "projectIds"));
        assertNull("'isProjectActive' should be correct.", TestsHelper.getField(instance, "isProjectActive"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getName()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getName() {
        String value = "new_value";
        instance.setName(value);

        assertEquals("'getName' should be correct.",
            value, instance.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setName(String name)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setName() {
        String value = "new_value";
        instance.setName(value);

        assertEquals("'setName' should be correct.",
            value, TestsHelper.getField(instance, "name"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssigneeId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAssigneeId() {
        Long value = 1L;
        instance.setAssigneeId(value);

        assertEquals("'getAssigneeId' should be correct.",
            value, instance.getAssigneeId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssigneeId(Long assigneeId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssigneeId() {
        Long value = 1L;
        instance.setAssigneeId(value);

        assertEquals("'setAssigneeId' should be correct.",
            value, TestsHelper.getField(instance, "assigneeId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDueDateFrom()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getDueDateFrom() {
        Date value = new Date();
        instance.setDueDateFrom(value);

        assertSame("'getDueDateFrom' should be correct.",
            value, instance.getDueDateFrom());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDueDateFrom(Date dueDateFrom)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setDueDateFrom() {
        Date value = new Date();
        instance.setDueDateFrom(value);

        assertSame("'setDueDateFrom' should be correct.",
            value, TestsHelper.getField(instance, "dueDateFrom"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDueDateTo()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getDueDateTo() {
        Date value = new Date();
        instance.setDueDateTo(value);

        assertSame("'getDueDateTo' should be correct.",
            value, instance.getDueDateTo());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDueDateTo(Date dueDateTo)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setDueDateTo() {
        Date value = new Date();
        instance.setDueDateTo(value);

        assertSame("'setDueDateTo' should be correct.",
            value, TestsHelper.getField(instance, "dueDateTo"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPriorities()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPriorities() {
        List<TaskPriority> value = new ArrayList<TaskPriority>();
        instance.setPriorities(value);

        assertSame("'getPriorities' should be correct.",
            value, instance.getPriorities());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPriorities(List&lt;TaskPriority&gt; priorities)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPriorities() {
        List<TaskPriority> value = new ArrayList<TaskPriority>();
        instance.setPriorities(value);

        assertSame("'setPriorities' should be correct.",
            value, TestsHelper.getField(instance, "priorities"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getStatuses()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getStatuses() {
        List<TaskStatus> value = new ArrayList<TaskStatus>();
        instance.setStatuses(value);

        assertSame("'getStatuses' should be correct.",
            value, instance.getStatuses());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStatuses(List&lt;TaskStatus&gt; statuses)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setStatuses() {
        List<TaskStatus> value = new ArrayList<TaskStatus>();
        instance.setStatuses(value);

        assertSame("'setStatuses' should be correct.",
            value, TestsHelper.getField(instance, "statuses"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssociatedToProjectMilestoneIds()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAssociatedToProjectMilestoneIds() {
        List<Long> value = new ArrayList<Long>();
        instance.setAssociatedToProjectMilestoneIds(value);

        assertSame("'getAssociatedToProjectMilestoneIds' should be correct.",
            value, instance.getAssociatedToProjectMilestoneIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssociatedToProjectMilestoneIds(List&lt;Long&gt;
     * associatedToProjectMilestoneIds)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssociatedToProjectMilestoneIds() {
        List<Long> value = new ArrayList<Long>();
        instance.setAssociatedToProjectMilestoneIds(value);

        assertSame("'setAssociatedToProjectMilestoneIds' should be correct.",
            value, TestsHelper.getField(instance, "associatedToProjectMilestoneIds"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssociatedToContestIds()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAssociatedToContestIds() {
        List<Long> value = new ArrayList<Long>();
        instance.setAssociatedToContestIds(value);

        assertSame("'getAssociatedToContestIds' should be correct.",
            value, instance.getAssociatedToContestIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssociatedToContestIds(List&lt;Long&gt; associatedToContestIds)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssociatedToContestIds() {
        List<Long> value = new ArrayList<Long>();
        instance.setAssociatedToContestIds(value);

        assertSame("'setAssociatedToContestIds' should be correct.",
            value, TestsHelper.getField(instance, "associatedToContestIds"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectIds()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectIds() {
        List<Long> value = new ArrayList<Long>();
        instance.setProjectIds(value);

        assertSame("'getProjectIds' should be correct.",
            value, instance.getProjectIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectIds(List&lt;Long&gt; projectIds)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectIds() {
        List<Long> value = new ArrayList<Long>();
        instance.setProjectIds(value);

        assertSame("'setProjectIds' should be correct.",
            value, TestsHelper.getField(instance, "projectIds"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectActive()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectActive() {
        Boolean value = true;
        instance.setProjectActive(value);

        assertEquals("'getProjectActive' should be correct.",
            value, instance.getProjectActive());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectActive(Boolean isProjectActive)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectActive() {
        Boolean value = true;
        instance.setProjectActive(value);

        assertEquals("'setProjectActive' should be correct.",
            value, TestsHelper.getField(instance, "isProjectActive"));
    }
}