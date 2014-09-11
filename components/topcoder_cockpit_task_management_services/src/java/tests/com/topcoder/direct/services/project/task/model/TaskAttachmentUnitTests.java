/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.TestsHelper;

/**
 * <p>
 * Unit tests for {@link TaskAttachment} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TaskAttachmentUnitTests {
    /**
     * <p>
     * Represents the <code>TaskAttachment</code> instance used in tests.
     * </p>
     */
    private TaskAttachment instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TaskAttachmentUnitTests.class);
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
        instance = new TaskAttachment();
    }

    /**
     * <p>
     * <code>TaskAttachment</code> should be subclass of <code>AuditableEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("TaskAttachment should be subclass of AuditableEntity.",
            TaskAttachment.class.getSuperclass() == AuditableEntity.class);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>TaskAttachment()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new TaskAttachment();

        assertNull("'fileName' should be correct.", TestsHelper.getField(instance, "fileName"));
        assertNull("'mimeType' should be correct.", TestsHelper.getField(instance, "mimeType"));
        assertEquals("'taskId' should be correct.", 0L, TestsHelper.getField(instance, "taskId"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getFileName()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getFileName() {
        String value = "new_value";
        instance.setFileName(value);

        assertEquals("'getFileName' should be correct.",
            value, instance.getFileName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFileName(String fileName)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setFileName() {
        String value = "new_value";
        instance.setFileName(value);

        assertEquals("'setFileName' should be correct.",
            value, TestsHelper.getField(instance, "fileName"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getMimeType()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getMimeType() {
        String value = "new_value";
        instance.setMimeType(value);

        assertEquals("'getMimeType' should be correct.",
            value, instance.getMimeType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setMimeType(String mimeType)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setMimeType() {
        String value = "new_value";
        instance.setMimeType(value);

        assertEquals("'setMimeType' should be correct.",
            value, TestsHelper.getField(instance, "mimeType"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTaskId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getTaskId() {
        long value = 1L;
        instance.setTaskId(value);

        assertEquals("'getTaskId' should be correct.",
            value, instance.getTaskId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTaskId(long taskId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setTaskId() {
        long value = 1L;
        instance.setTaskId(value);

        assertEquals("'setTaskId' should be correct.",
            value, TestsHelper.getField(instance, "taskId"));
    }
}