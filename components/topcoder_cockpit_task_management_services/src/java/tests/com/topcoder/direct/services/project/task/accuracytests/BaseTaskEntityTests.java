/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.model.AuditableEntity;
import com.topcoder.direct.services.project.task.model.BaseTaskEntity;
import com.topcoder.direct.services.project.task.model.ContestDTO;
import com.topcoder.direct.services.project.task.model.MilestoneDTO;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Unit tests for <code>{@link BaseTaskEntity}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class BaseTaskEntityTests extends TestCase {
    /** BaseTaskEntity instance to be used for the testing. */
    private MockBaseTaskEntity instance = null;

    /**
     * <p>Sets up the environment for the TestCase.</p>
     */
    public void setUp() {
        instance = new MockBaseTaskEntity();
    }

    /**
     * <p>Tears down the environment after the TestCase.</p>
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * <p>Accuracy test for the constructor.</p>
     */
    public void test_ctor() {
        assertNotNull("instance should be created.", instance);
    }

    /**
     * <p>Accuracy test for the inheritence.</p>
     */
    public void test_inheritence() {
        assertTrue("invalid inheritence.", BaseTaskEntity.class.getSuperclass() == AuditableEntity.class);
    }

    /**
     * <p>Accuracy test for {@link BaseTaskEntity#getName()}.</p>
     */
    public void test_getName() {
        assertEquals("Invalid default value.", null, instance.getName());
        instance.setName("tester");
        assertEquals("Invalid return value.", "tester", instance.getName());
    }

    /**
     * <p>Accuracy test for {@link BaseTaskEntity#setName()}.</p>
     */
    public void test_setName() {
        instance.setName("tester");
        assertEquals("Invalid value is set.", "tester", instance.getName());
        instance.setName(null);
        assertEquals("Invalid value is set.", null, instance.getName());
    }

    /**
     * <p>Accuracy test for {@link BaseTaskEntity#getNotes()}.</p>
     */
    public void test_getNotes() {
        assertEquals("Invalid default value.", null, instance.getNotes());
        instance.setNotes("tester");
        assertEquals("Invalid return value.", "tester", instance.getNotes());
    }

    /**
     * <p>Accuracy test for {@link BaseTaskEntity#setNotes()}.</p>
     */
    public void test_setNotes() {
        instance.setNotes("tester");
        assertEquals("Invalid value is set.", "tester", instance.getNotes());
        instance.setNotes(null);
        assertEquals("Invalid value is set.", null, instance.getNotes());
    }

    /**
     * <p>Accuracy test for {@link BaseTaskEntity#getAssociatedToProjectMilestones()}.</p>
     */
    public void test_getAssociatedToProjectMilestones() {
        assertEquals("Invalid default value.", null, instance.getAssociatedToProjectMilestones());

        List<MilestoneDTO> field = new ArrayList<MilestoneDTO>();
        instance.setAssociatedToProjectMilestones(field);
        assertEquals("Invalid return value.", field, instance.getAssociatedToProjectMilestones());
    }

    /**
     * <p>Accuracy test for {@link BaseTaskEntity#setAssociatedToProjectMilestones()}.</p>
     */
    public void test_setAssociatedToProjectMilestones() {
        List<MilestoneDTO> field = new ArrayList<MilestoneDTO>();
        instance.setAssociatedToProjectMilestones(field);
        assertEquals("Invalid return value.", field, instance.getAssociatedToProjectMilestones());
        instance.setAssociatedToProjectMilestones(null);
        assertEquals("Invalid value is set.", null, instance.getAssociatedToProjectMilestones());
    }

    /**
     * <p>Accuracy test for {@link BaseTaskEntity#getAssociatedToContests()}.</p>
     */
    public void test_getAssociatedToContests() {
        assertEquals("Invalid default value.", null, instance.getAssociatedToContests());

        List<ContestDTO> field = new ArrayList<ContestDTO>();
        instance.setAssociatedToContests(field);
        assertEquals("Invalid return value.", field, instance.getAssociatedToContests());
    }

    /**
     * <p>Accuracy test for {@link BaseTaskEntity#setAssociatedToContests()}.</p>
     */
    public void test_setAssociatedToContests() {
        List<ContestDTO> field = new ArrayList<ContestDTO>();
        instance.setAssociatedToContests(field);
        assertEquals("Invalid value is set.", field, instance.getAssociatedToContests());
        instance.setAssociatedToContests(null);
        assertEquals("Invalid value is set.", null, instance.getAssociatedToContests());
    }
}
