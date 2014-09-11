/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.model.ContestDTO;

import junit.framework.TestCase;


/**
 * <p>Unit tests for <code>{@link ContestDTO}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class ContestDTOTests extends TestCase {
    /** ContestDTO instance to be used for the testing. */
    private ContestDTO instance = null;

    /**
     * <p>Sets up the environment for the TestCase.</p>
     */
    public void setUp() {
        instance = new ContestDTO();
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
        assertTrue("invalid inheritence.", ContestDTO.class.getSuperclass() == Object.class);
    }

    /**
     * <p>Accuracy test for {@link ContestDTO#getContestId()}.</p>
     */
    public void test_getContestId() {
        assertEquals("Invalid default value.", 0, instance.getContestId());
        instance.setContestId(2);
        assertEquals("Invalid return value.", 2, instance.getContestId());
    }

    /**
     * <p>Accuracy test for {@link ContestDTO#setContestId()}.</p>
     */
    public void test_setContestId() {
        instance.setContestId(2);
        assertEquals("Invalid value is set.", 2, instance.getContestId());
        instance.setContestId(0);
        assertEquals("Invalid value is set.", 0, instance.getContestId());
    }

    /**
     * <p>Accuracy test for {@link ContestDTO#getContestName()}.</p>
     */
    public void test_getContestName() {
        assertEquals("Invalid default value.", null, instance.getContestName());
        instance.setContestName("tester");
        assertEquals("Invalid return value.", "tester", instance.getContestName());
    }

    /**
     * <p>Accuracy test for {@link ContestDTO#setContestName()}.</p>
     */
    public void test_setContestName() {
        instance.setContestName("tester");
        assertEquals("Invalid value is set.", "tester", instance.getContestName());
        instance.setContestName(null);
        assertEquals("Invalid value is set.", null, instance.getContestName());
    }
}
