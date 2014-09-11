/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.model.AuditableEntity;
import com.topcoder.direct.services.project.task.model.TaskAttachment;

import junit.framework.TestCase;


/**
 * <p>Unit tests for <code>{@link TaskAttachment}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class TaskAttachmentTests extends TestCase {
    /** TaskAttachment instance to be used for the testing. */
    private TaskAttachment instance = null;

    /**
     * <p>Sets up the environment for the TestCase.</p>
     */
    public void setUp() {
        instance = new TaskAttachment();
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
        assertTrue("invalid inheritence.", TaskAttachment.class.getSuperclass() == AuditableEntity.class);
    }

    /**
     * <p>Accuracy test for {@link TaskAttachment#getFileName()}.</p>
     */
    public void test_getFileName() {
        assertEquals("Invalid default value.", null, instance.getFileName());
        instance.setFileName("tester");
        assertEquals("Invalid return value.", "tester", instance.getFileName());
    }

    /**
     * <p>Accuracy test for {@link TaskAttachment#setFileName()}.</p>
     */
    public void test_setFileName() {
        instance.setFileName("tester");
        assertEquals("Invalid value is set.", "tester", instance.getFileName());
        instance.setFileName(null);
        assertEquals("Invalid value is set.", null, instance.getFileName());
    }

    /**
     * <p>Accuracy test for {@link TaskAttachment#getMimeType()}.</p>
     */
    public void test_getMimeType() {
        assertEquals("Invalid default value.", null, instance.getMimeType());
        instance.setMimeType("tester");
        assertEquals("Invalid return value.", "tester", instance.getMimeType());
    }

    /**
     * <p>Accuracy test for {@link TaskAttachment#setMimeType()}.</p>
     */
    public void test_setMimeType() {
        instance.setMimeType("tester");
        assertEquals("Invalid value is set.", "tester", instance.getMimeType());
        instance.setMimeType(null);
        assertEquals("Invalid value is set.", null, instance.getMimeType());
    }

    /**
     * <p>Accuracy test for {@link TaskAttachment#getTaskId()}.</p>
     */
    public void test_getTaskId() {
        assertEquals("Invalid default value.", 0, instance.getTaskId());
        instance.setTaskId(2);
        assertEquals("Invalid return value.", 2, instance.getTaskId());
    }

    /**
     * <p>Accuracy test for {@link TaskAttachment#setTaskId()}.</p>
     */
    public void test_setTaskId() {
        instance.setTaskId(2);
        assertEquals("Invalid value is set.", 2, instance.getTaskId());
        instance.setTaskId(0);
        assertEquals("Invalid value is set.", 0, instance.getTaskId());
    }
}
