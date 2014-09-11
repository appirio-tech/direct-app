/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import junit.framework.TestCase;

import com.topcoder.direct.services.project.task.model.IdentifiableEntity;
import com.topcoder.direct.services.project.task.model.Task;


/**
 * <p>Unit tests for <code>{@link IdentifiableEntity}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class IdentifiableEntityTests extends TestCase {
    /** IdentifiableEntity instance to be used for the testing. */
    private MockIdentifiableEntity instance = null;

    /**
     * <p>Sets up the environment for the TestCase.</p>
     */
    public void setUp() {
        instance = new MockIdentifiableEntity();
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
        assertTrue("invalid inheritence.", IdentifiableEntity.class.getSuperclass() == Object.class);
    }

    /**
     * <p>Accuracy test for {@link IdentifiableEntity#getId()}.</p>
     */
    public void test_getId() {
        assertEquals("Invalid default value.", 0, instance.getId());
        instance.setId(2);
        assertEquals("Invalid return value.", 2, instance.getId());
    }

    /**
     * <p>Accuracy test for {@link IdentifiableEntity#setId()}.</p>
     */
    public void test_setId() {
        instance.setId(2);
        assertEquals("Invalid value is set.", 2, instance.getId());
        instance.setId(0);
        assertEquals("Invalid value is set.", 0, instance.getId());
    }

    /**
     * <p>Accuracy test for {@link IdentifiableEntity#equals()}.</p>
     */
    public void test_equals() {
        assertEquals("invalid return value", true, instance.equals(instance));
        assertEquals("invalid return value", false, instance.equals(null));
        instance.setId(2);
        MockIdentifiableEntity instance2 = new MockIdentifiableEntity();
        instance2.setId(2);
        assertEquals("invalid return value", true, instance.equals(instance2));
        Task task = new Task();
        task.setId(2);
        assertEquals("invalid return value", false, instance.equals(task));
    }

    /**
     * <p>Accuracy test for {@link IdentifiableEntity#hashCode()}.</p>
     */
    public void test_hashCode() {
        long hashCode = instance.hashCode();
        assertTrue("invalid return value", hashCode > 0);
        instance.setId(2);
        assertTrue("invalid return value", hashCode != instance.hashCode());
    }
}
