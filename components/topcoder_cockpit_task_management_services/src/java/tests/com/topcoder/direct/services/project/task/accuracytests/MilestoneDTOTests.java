/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.model.MilestoneDTO;

import junit.framework.TestCase;


/**
 * <p>Unit tests for <code>{@link MilestoneDTO}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class MilestoneDTOTests extends TestCase {
    /** MilestoneDTO instance to be used for the testing. */
    private MilestoneDTO instance = null;

    /**
     * <p>Sets up the environment for the TestCase.</p>
     */
    public void setUp() {
        instance = new MilestoneDTO();
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
        assertTrue("invalid inheritence.", MilestoneDTO.class.getSuperclass() == Object.class);
    }

    /**
     * <p>Accuracy test for {@link MilestoneDTO#getMilestoneId()}.</p>
     */
    public void test_getMilestoneId() {
        assertEquals("Invalid default value.", 0, instance.getMilestoneId());
        instance.setMilestoneId(2);
        assertEquals("Invalid return value.", 2, instance.getMilestoneId());
    }

    /**
     * <p>Accuracy test for {@link MilestoneDTO#setMilestoneId()}.</p>
     */
    public void test_setMilestoneId() {
        instance.setMilestoneId(2);
        assertEquals("Invalid value is set.", 2, instance.getMilestoneId());
        instance.setMilestoneId(0);
        assertEquals("Invalid value is set.", 0, instance.getMilestoneId());
    }

    /**
     * <p>Accuracy test for {@link MilestoneDTO#getMilestoneName()}.</p>
     */
    public void test_getMilestoneName() {
        assertEquals("Invalid default value.", null, instance.getMilestoneName());
        instance.setMilestoneName("tester");
        assertEquals("Invalid return value.", "tester", instance.getMilestoneName());
    }

    /**
     * <p>Accuracy test for {@link MilestoneDTO#setMilestoneName()}.</p>
     */
    public void test_setMilestoneName() {
        instance.setMilestoneName("tester");
        assertEquals("Invalid value is set.", "tester", instance.getMilestoneName());
        instance.setMilestoneName(null);
        assertEquals("Invalid value is set.", null, instance.getMilestoneName());
    }
}
