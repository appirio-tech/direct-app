/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.direct.services.project.task.model.TaskFilter;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;

/**
 * <p>
 * Unit tests for <code>{@link TaskFilter}</code> class.
 * </p>
 * 
 * @author gjw99
 * @version 1.0
 */
public class TaskFilterTests extends TestCase {
    /** TaskFilter instance to be used for the testing. */
    private TaskFilter instance = null;

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     */
    @Override
    public void setUp() {
        instance = new TaskFilter();
    }

    /**
     * <p>
     * Tears down the environment after the TestCase.
     * </p>
     */
    @Override
    public void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     */
    public void test_ctor() {
        assertNotNull("instance should be created.", instance);
    }

    /**
     * <p>
     * Accuracy test for the inheritence.
     * </p>
     */
    public void test_inheritence() {
        assertTrue("invalid inheritence.", TaskFilter.class.getSuperclass() == Object.class);
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getName()}.
     * </p>
     */
    public void test_getName() {
        assertEquals("Invalid default value.", null, instance.getName());
        instance.setName("tester");
        assertEquals("Invalid return value.", "tester", instance.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setName()}.
     * </p>
     */
    public void test_setName() {
        instance.setName("tester");
        assertEquals("Invalid value is set.", "tester", instance.getName());
        instance.setName(null);
        assertEquals("Invalid value is set.", null, instance.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getAssigneeId()}.
     * </p>
     */
    public void test_getAssigneeId() {
        assertEquals("Invalid default value.", null, instance.getAssigneeId());

        Long field = new Long(2);
        instance.setAssigneeId(field);
        assertEquals("Invalid return value.", field, instance.getAssigneeId());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setAssigneeId()}.
     * </p>
     */
    public void test_setAssigneeId() {
        Long field = new Long(2);
        instance.setAssigneeId(field);
        assertEquals("Invalid value is set.", field, instance.getAssigneeId());
        instance.setAssigneeId(null);
        assertEquals("Invalid value is set.", null, instance.getAssigneeId());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getDueDateFrom()}.
     * </p>
     */
    public void test_getDueDateFrom() {
        assertEquals("Invalid default value.", null, instance.getDueDateFrom());

        Date date = new Date();
        instance.setDueDateFrom(date);
        assertEquals("Invalid return value.", date, instance.getDueDateFrom());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setDueDateFrom()}.
     * </p>
     */
    public void test_setDueDateFrom() {
        Date date = new Date();
        instance.setDueDateFrom(date);
        assertEquals("Invalid return value.", date, instance.getDueDateFrom());
        instance.setDueDateFrom(null);
        assertEquals("Invalid value is set.", null, instance.getDueDateFrom());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getDueDateTo()}.
     * </p>
     */
    public void test_getDueDateTo() {
        assertEquals("Invalid default value.", null, instance.getDueDateTo());

        Date date = new Date();
        instance.setDueDateTo(date);
        assertEquals("Invalid return value.", date, instance.getDueDateTo());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setDueDateTo()}.
     * </p>
     */
    public void test_setDueDateTo() {
        Date date = new Date();
        instance.setDueDateTo(date);
        assertEquals("Invalid return value.", date, instance.getDueDateTo());
        instance.setDueDateTo(null);
        assertEquals("Invalid value is set.", null, instance.getDueDateTo());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getPriorities()}.
     * </p>
     */
    public void test_getPriorities() {
        assertEquals("Invalid default value.", null, instance.getPriorities());

        List<TaskPriority> field = new ArrayList<TaskPriority>();
        instance.setPriorities(field);
        assertEquals("Invalid return value.", field, instance.getPriorities());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setPriorities()}.
     * </p>
     */
    public void test_setPriorities() {
        List<TaskPriority> field = new ArrayList<TaskPriority>();
        instance.setPriorities(field);
        assertEquals("Invalid value is set.", field, instance.getPriorities());
        instance.setPriorities(null);
        assertEquals("Invalid value is set.", null, instance.getPriorities());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getStatuses()}.
     * </p>
     */
    public void test_getStatuses() {
        assertEquals("Invalid default value.", null, instance.getStatuses());

        List<TaskStatus> field = new ArrayList<TaskStatus>();
        instance.setStatuses(field);
        assertEquals("Invalid return value.", field, instance.getStatuses());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setStatuses()}.
     * </p>
     */
    public void test_setStatuses() {
        List<TaskStatus> field = new ArrayList<TaskStatus>();
        instance.setStatuses(field);
        assertEquals("Invalid value is set.", field, instance.getStatuses());
        instance.setStatuses(null);
        assertEquals("Invalid value is set.", null, instance.getStatuses());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getAssociatedToProjectMilestoneIds()}
     * .
     * </p>
     */
    public void test_getAssociatedToProjectMilestoneIds() {
        assertEquals("Invalid default value.", null, instance.getAssociatedToProjectMilestoneIds());

        List<Long> field = new ArrayList<Long>();
        instance.setAssociatedToProjectMilestoneIds(field);
        assertEquals("Invalid return value.", field, instance.getAssociatedToProjectMilestoneIds());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setAssociatedToProjectMilestoneIds()}
     * .
     * </p>
     */
    public void test_setAssociatedToProjectMilestoneIds() {
        List<Long> field = new ArrayList<Long>();
        instance.setAssociatedToProjectMilestoneIds(field);
        assertEquals("Invalid value is set.", field, instance.getAssociatedToProjectMilestoneIds());
        instance.setAssociatedToProjectMilestoneIds(null);
        assertEquals("Invalid value is set.", null, instance.getAssociatedToProjectMilestoneIds());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getAssociatedToContestIds()}.
     * </p>
     */
    public void test_getAssociatedToContestIds() {
        assertEquals("Invalid default value.", null, instance.getAssociatedToContestIds());

        List<Long> field = new ArrayList<Long>();
        instance.setAssociatedToContestIds(field);
        assertEquals("Invalid return value.", field, instance.getAssociatedToContestIds());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setAssociatedToContestIds()}.
     * </p>
     */
    public void test_setAssociatedToContestIds() {
        List<Long> field = new ArrayList<Long>();
        instance.setAssociatedToContestIds(field);
        assertEquals("Invalid value is set.", field, instance.getAssociatedToContestIds());
        instance.setAssociatedToContestIds(null);
        assertEquals("Invalid value is set.", null, instance.getAssociatedToContestIds());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getProjectIds()}.
     * </p>
     */
    public void test_getProjectIds() {
        assertEquals("Invalid default value.", null, instance.getProjectIds());

        List<Long> field = new ArrayList<Long>();
        instance.setProjectIds(field);
        assertEquals("Invalid return value.", field, instance.getProjectIds());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setProjectIds()}.
     * </p>
     */
    public void test_setProjectIds() {
        List<Long> field = new ArrayList<Long>();
        instance.setProjectIds(field);
        assertEquals("Invalid value is set.", field, instance.getProjectIds());
        instance.setProjectIds(null);
        assertEquals("Invalid value is set.", null, instance.getProjectIds());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#getIsProjectActive()}.
     * </p>
     */
    public void test_isProjectActive() {
        assertEquals("Invalid default value.", null, instance.getProjectActive());
        instance.setProjectActive(Boolean.TRUE);
        assertEquals("Invalid return value.", Boolean.TRUE, instance.getProjectActive());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskFilter#setIsProjectActive()}.
     * </p>
     */
    public void test_setProjectActive() {
        instance.setProjectActive(Boolean.TRUE);
        assertEquals("Invalid return value.", Boolean.TRUE, instance.getProjectActive());
        instance.setProjectActive(null);
        assertEquals("Invalid value is set.", null, instance.getProjectActive());
    }
}
