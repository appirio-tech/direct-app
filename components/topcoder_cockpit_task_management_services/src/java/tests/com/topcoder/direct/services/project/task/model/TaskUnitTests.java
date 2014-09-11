/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.TestsHelper;

/**
 * <p>
 * Unit tests for {@link Task} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TaskUnitTests {
    /**
     * <p>
     * Represents the <code>Task</code> instance used in tests.
     * </p>
     */
    private Task instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TaskUnitTests.class);
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
        instance = new Task();
    }

    /**
     * <p>
     * <code>TaskList</code> should be subclass of <code>BaseTaskEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("TaskList should be subclass of BaseTaskEntity.",
            TaskList.class.getSuperclass() == BaseTaskEntity.class);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>Task()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new Task();

        assertNull("'startDate' should be correct.", TestsHelper.getField(instance, "startDate"));
        assertNull("'dueDate' should be correct.", TestsHelper.getField(instance, "dueDate"));
        assertNull("'assignees' should be correct.", TestsHelper.getField(instance, "assignees"));
        assertNull("'priority' should be correct.", TestsHelper.getField(instance, "priority"));
        assertNull("'attachments' should be correct.", TestsHelper.getField(instance, "attachments"));
        assertNull("'status' should be correct.", TestsHelper.getField(instance, "status"));
        assertEquals("'taskListId' should be correct.", 0L, TestsHelper.getField(instance, "taskListId"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getStartDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getStartDate() {
        Date value = new Date();
        instance.setStartDate(value);

        assertSame("'getStartDate' should be correct.",
            value, instance.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStartDate(Date startDate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setStartDate() {
        Date value = new Date();
        instance.setStartDate(value);

        assertSame("'setStartDate' should be correct.",
            value, TestsHelper.getField(instance, "startDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDueDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getDueDate() {
        Date value = new Date();
        instance.setDueDate(value);

        assertSame("'getDueDate' should be correct.",
            value, instance.getDueDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDueDate(Date dueDate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setDueDate() {
        Date value = new Date();
        instance.setDueDate(value);

        assertSame("'setDueDate' should be correct.",
            value, TestsHelper.getField(instance, "dueDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssignees()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAssignees() {
        List<UserDTO> value = new ArrayList<UserDTO>();
        instance.setAssignees(value);

        assertSame("'getAssignees' should be correct.",
            value, instance.getAssignees());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssignees(List&lt;UserDTO&gt; assignees)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssignees() {
        List<UserDTO> value = new ArrayList<UserDTO>();
        instance.setAssignees(value);

        assertSame("'setAssignees' should be correct.",
            value, TestsHelper.getField(instance, "assignees"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPriority()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPriority() {
        TaskPriority value = TaskPriority.HIGH;
        instance.setPriority(value);

        assertEquals("'getPriority' should be correct.",
            value, instance.getPriority());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPriority(TaskPriority priority)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPriority() {
        TaskPriority value = TaskPriority.HIGH;
        instance.setPriority(value);

        assertEquals("'setPriority' should be correct.",
            value, TestsHelper.getField(instance, "priority"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAttachments()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAttachments() {
        List<TaskAttachment> value = new ArrayList<TaskAttachment>();
        instance.setAttachments(value);

        assertSame("'getAttachments' should be correct.",
            value, instance.getAttachments());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAttachments(List&lt;TaskAttachment&gt; attachments)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAttachments() {
        List<TaskAttachment> value = new ArrayList<TaskAttachment>();
        instance.setAttachments(value);

        assertSame("'setAttachments' should be correct.",
            value, TestsHelper.getField(instance, "attachments"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getStatus()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getStatus() {
        TaskStatus value = TaskStatus.COMPLETED;
        instance.setStatus(value);

        assertEquals("'getStatus' should be correct.",
            value, instance.getStatus());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStatus(TaskStatus status)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setStatus() {
        TaskStatus value = TaskStatus.COMPLETED;
        instance.setStatus(value);

        assertEquals("'setStatus' should be correct.",
            value, TestsHelper.getField(instance, "status"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTaskListId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getTaskListId() {
        long value = 1L;
        instance.setTaskListId(value);

        assertEquals("'getTaskListId' should be correct.",
            value, instance.getTaskListId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTaskListId(long taskListId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setTaskListId() {
        long value = 1L;
        instance.setTaskListId(value);

        assertEquals("'setTaskListId' should be correct.",
            value, TestsHelper.getField(instance, "taskListId"));
    }
}