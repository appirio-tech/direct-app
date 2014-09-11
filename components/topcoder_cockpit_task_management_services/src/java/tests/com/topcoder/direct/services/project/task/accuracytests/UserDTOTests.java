/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.model.UserDTO;

import junit.framework.TestCase;


/**
 * <p>Unit tests for <code>{@link UserDTO}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class UserDTOTests extends TestCase {
    /** UserDTO instance to be used for the testing. */
    private UserDTO instance = null;

    /**
     * <p>Sets up the environment for the TestCase.</p>
     */
    public void setUp() {
        instance = new UserDTO();
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
        assertTrue("invalid inheritence.", UserDTO.class.getSuperclass() == Object.class);
    }

    /**
     * <p>Accuracy test for {@link UserDTO#getUserId()}.</p>
     */
    public void test_getUserId() {
        assertEquals("Invalid default value.", 0, instance.getUserId());
        instance.setUserId(2);
        assertEquals("Invalid return value.", 2, instance.getUserId());
    }

    /**
     * <p>Accuracy test for {@link UserDTO#setUserId()}.</p>
     */
    public void test_setUserId() {
        instance.setUserId(2);
        assertEquals("Invalid value is set.", 2, instance.getUserId());
        instance.setUserId(0);
        assertEquals("Invalid value is set.", 0, instance.getUserId());
    }

    /**
     * <p>Accuracy test for {@link UserDTO#getHandle()}.</p>
     */
    public void test_getHandle() {
        assertEquals("Invalid default value.", null, instance.getHandle());
        instance.setHandle("tester");
        assertEquals("Invalid return value.", "tester", instance.getHandle());
    }

    /**
     * <p>Accuracy test for {@link UserDTO#setHandle()}.</p>
     */
    public void test_setHandle() {
        instance.setHandle("tester");
        assertEquals("Invalid value is set.", "tester", instance.getHandle());
        instance.setHandle(null);
        assertEquals("Invalid value is set.", null, instance.getHandle());
    }
}
