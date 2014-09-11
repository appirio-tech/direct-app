/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.TestsHelper;

/**
 * <p>
 * Unit tests for {@link TaskList} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TaskListUnitTests {
    /**
     * <p>
     * Represents the <code>TaskList</code> instance used in tests.
     * </p>
     */
    private TaskList instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TaskListUnitTests.class);
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
        instance = new TaskList();
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
     * Accuracy test for the constructor <code>TaskList()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new TaskList();

        assertNull("'permittedUsers' should be correct.", TestsHelper.getField(instance, "permittedUsers"));
        assertEquals("'projectId' should be correct.", 0L, TestsHelper.getField(instance, "projectId"));
        assertEquals("'numberOfCompletedTasks' should be correct.",
                0, TestsHelper.getField(instance, "numberOfCompletedTasks"));
        assertEquals("'numberOfAllTasks' should be correct.", 0, TestsHelper.getField(instance, "numberOfAllTasks"));
        assertNull("'tasks' should be correct.", TestsHelper.getField(instance, "tasks"));
        assertFalse("'isActive' should be correct.", (Boolean) TestsHelper.getField(instance, "isActive"));
        assertFalse("'isDefault' should be correct.", (Boolean) TestsHelper.getField(instance, "isDefault"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getPermittedUsers()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPermittedUsers() {
        List<UserDTO> value = new ArrayList<UserDTO>();
        instance.setPermittedUsers(value);

        assertSame("'getPermittedUsers' should be correct.",
            value, instance.getPermittedUsers());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPermittedUsers(List&lt;UserDTO&gt; permittedUsers)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPermittedUsers() {
        List<UserDTO> value = new ArrayList<UserDTO>();
        instance.setPermittedUsers(value);

        assertSame("'setPermittedUsers' should be correct.",
            value, TestsHelper.getField(instance, "permittedUsers"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectId() {
        long value = 1L;
        instance.setProjectId(value);

        assertEquals("'getProjectId' should be correct.",
            value, instance.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectId(long projectId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectId() {
        long value = 1L;
        instance.setProjectId(value);

        assertEquals("'setProjectId' should be correct.",
            value, TestsHelper.getField(instance, "projectId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getNumberOfCompletedTasks()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getNumberOfCompletedTasks() {
        int value = 1;
        instance.setNumberOfCompletedTasks(value);

        assertEquals("'getNumberOfCompletedTasks' should be correct.",
            value, instance.getNumberOfCompletedTasks());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setNumberOfCompletedTasks(int numberOfCompletedTasks)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setNumberOfCompletedTasks() {
        int value = 1;
        instance.setNumberOfCompletedTasks(value);

        assertEquals("'setNumberOfCompletedTasks' should be correct.",
            value, TestsHelper.getField(instance, "numberOfCompletedTasks"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getNumberOfAllTasks()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getNumberOfAllTasks() {
        int value = 1;
        instance.setNumberOfAllTasks(value);

        assertEquals("'getNumberOfAllTasks' should be correct.",
            value, instance.getNumberOfAllTasks());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setNumberOfAllTasks(int numberOfAllTasks)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setNumberOfAllTasks() {
        int value = 1;
        instance.setNumberOfAllTasks(value);

        assertEquals("'setNumberOfAllTasks' should be correct.",
            value, TestsHelper.getField(instance, "numberOfAllTasks"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTasks()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getTasks() {
        List<Task> value = new ArrayList<Task>();
        instance.setTasks(value);

        assertSame("'getTasks' should be correct.",
            value, instance.getTasks());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTasks(List&lt;Task&gt; tasks)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setTasks() {
        List<Task> value = new ArrayList<Task>();
        instance.setTasks(value);

        assertSame("'setTasks' should be correct.",
            value, TestsHelper.getField(instance, "tasks"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>isActive()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_isActive() {
        boolean value = true;
        instance.setActive(value);

        assertTrue("'isActive' should be correct.", instance.isActive());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setActive(boolean isActive)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setActive() {
        boolean value = true;
        instance.setActive(value);

        assertTrue("'setActive' should be correct.",
            (Boolean) TestsHelper.getField(instance, "isActive"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>isDefault()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_isDefault() {
        boolean value = true;
        instance.setDefault(value);

        assertTrue("'isDefault' should be correct.", instance.isDefault());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDefault(boolean isDefault)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setDefault() {
        boolean value = true;
        instance.setDefault(value);

        assertTrue("'setDefault' should be correct.",
            (Boolean) TestsHelper.getField(instance, "isDefault"));
    }
}