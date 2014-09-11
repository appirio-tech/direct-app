/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.model.BaseTaskEntity;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.UserDTO;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Unit tests for <code>{@link TaskList}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class TaskListTests extends TestCase {
    /** TaskList instance to be used for the testing. */
    private TaskList instance = null;

    /**
     * <p>Sets up the environment for the TestCase.</p>
     */
    public void setUp() {
        instance = new TaskList();
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
        assertTrue("invalid inheritence.", TaskList.class.getSuperclass() == BaseTaskEntity.class);
    }

    /**
     * <p>Accuracy test for {@link TaskList#getPermittedUsers()}.</p>
     */
    public void test_getPermittedUsers() {
        assertEquals("Invalid default value.", null, instance.getPermittedUsers());

        List<UserDTO> field = new ArrayList<UserDTO>();
        instance.setPermittedUsers(field);
        assertEquals("Invalid return value.", field, instance.getPermittedUsers());
    }

    /**
     * <p>Accuracy test for {@link TaskList#setPermittedUsers()}.</p>
     */
    public void test_setPermittedUsers() {
        List<UserDTO> field = new ArrayList<UserDTO>();
        instance.setPermittedUsers(field);
        assertEquals("Invalid value is set.", field, instance.getPermittedUsers());
        instance.setPermittedUsers(null);
        assertEquals("Invalid value is set.", null, instance.getPermittedUsers());
    }

    /**
     * <p>Accuracy test for {@link TaskList#getProjectId()}.</p>
     */
    public void test_getProjectId() {
        assertEquals("Invalid default value.", 0, instance.getProjectId());
        instance.setProjectId(2);
        assertEquals("Invalid return value.", 2, instance.getProjectId());
    }

    /**
     * <p>Accuracy test for {@link TaskList#setProjectId()}.</p>
     */
    public void test_setProjectId() {
        instance.setProjectId(2);
        assertEquals("Invalid value is set.", 2, instance.getProjectId());
        instance.setProjectId(0);
        assertEquals("Invalid value is set.", 0, instance.getProjectId());
    }

    /**
     * <p>Accuracy test for {@link TaskList#getNumberOfCompletedTasks()}.</p>
     */
    public void test_getNumberOfCompletedTasks() {
        assertEquals("Invalid default value.", 0, instance.getNumberOfCompletedTasks());
        instance.setNumberOfCompletedTasks(2);
        assertEquals("Invalid return value.", 2, instance.getNumberOfCompletedTasks());
    }

    /**
     * <p>Accuracy test for {@link TaskList#setNumberOfCompletedTasks()}.</p>
     */
    public void test_setNumberOfCompletedTasks() {
        instance.setNumberOfCompletedTasks(2);
        assertEquals("Invalid value is set.", 2, instance.getNumberOfCompletedTasks());
        instance.setNumberOfCompletedTasks(0);
        assertEquals("Invalid value is set.", 0, instance.getNumberOfCompletedTasks());
    }

    /**
     * <p>Accuracy test for {@link TaskList#getNumberOfAllTasks()}.</p>
     */
    public void test_getNumberOfAllTasks() {
        assertEquals("Invalid default value.", 0, instance.getNumberOfAllTasks());
        instance.setNumberOfAllTasks(2);
        assertEquals("Invalid return value.", 2, instance.getNumberOfAllTasks());
    }

    /**
     * <p>Accuracy test for {@link TaskList#setNumberOfAllTasks()}.</p>
     */
    public void test_setNumberOfAllTasks() {
        instance.setNumberOfAllTasks(2);
        assertEquals("Invalid value is set.", 2, instance.getNumberOfAllTasks());
        instance.setNumberOfAllTasks(0);
        assertEquals("Invalid value is set.", 0, instance.getNumberOfAllTasks());
    }

    /**
     * <p>Accuracy test for {@link TaskList#getTasks()}.</p>
     */
    public void test_getTasks() {
        assertEquals("Invalid default value.", null, instance.getTasks());

        List<Task> field = new ArrayList<Task>();
        instance.setTasks(field);
        assertEquals("Invalid return value.", field, instance.getTasks());
    }

    /**
     * <p>Accuracy test for {@link TaskList#setTasks()}.</p>
     */
    public void test_setTasks() {
        List<Task> field = new ArrayList<Task>();
        instance.setTasks(field);
        assertEquals("Invalid value is set.", field, instance.getTasks());
        instance.setTasks(null);
        assertEquals("Invalid value is set.", null, instance.getTasks());
    }

    /**
     * <p>Accuracy test for {@link TaskList#getIsActive()}.</p>
     */
    public void test_getIsActive() {
        assertEquals("Invalid default value.", false, instance.isActive());
        instance.setActive(true);
        assertEquals("Invalid return value.", true, instance.isActive());
    }

    /**
     * <p>Accuracy test for {@link TaskList#setIsActive()}.</p>
     */
    public void test_setIsActive() {
        instance.setActive(true);
        assertEquals("Invalid return value.", true, instance.isActive());
        instance.setActive(false);
        assertEquals("Invalid value is set.", false, instance.isActive());
    }

    /**
     * <p>Accuracy test for {@link TaskList#getIsDefault()}.</p>
     */
    public void test_getIsDefault() {
        assertEquals("Invalid default value.", false, instance.isDefault());
        instance.setDefault(true);
        assertEquals("Invalid return value.", true, instance.isDefault());
    }

    /**
     * <p>Accuracy test for {@link TaskList#setIsDefault()}.</p>
     */
    public void test_setIsDefault() {
        instance.setDefault(true);
        assertEquals("Invalid return value.", true, instance.isDefault());
        instance.setDefault(false);
        assertEquals("Invalid value is set.", false, instance.isDefault());
    }
}
