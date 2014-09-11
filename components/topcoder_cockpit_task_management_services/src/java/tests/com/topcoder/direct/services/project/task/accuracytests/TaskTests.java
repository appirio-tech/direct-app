/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.model.BaseTaskEntity;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskAttachment;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>Unit tests for <code>{@link Task}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class TaskTests extends TestCase {
    /** Task instance to be used for the testing. */
    private Task instance = null;

    /**
     * <p>Sets up the environment for the TestCase.</p>
     */
    public void setUp() {
        instance = new Task();
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
        assertTrue("invalid inheritence.", Task.class.getSuperclass() == BaseTaskEntity.class);
    }

    /**
     * <p>Accuracy test for {@link Task#getStartDate()}.</p>
     */
    public void test_getStartDate() {
        assertEquals("Invalid default value.", null, instance.getStartDate());

        Date date = new Date();
        instance.setStartDate(date);
        assertEquals("Invalid return value.", date, instance.getStartDate());
    }

    /**
     * <p>Accuracy test for {@link Task#setStartDate()}.</p>
     */
    public void test_setStartDate() {
        Date date = new Date();
        instance.setStartDate(date);
        assertEquals("Invalid return value.", date, instance.getStartDate());
        instance.setStartDate(null);
        assertEquals("Invalid value is set.", null, instance.getStartDate());
    }

    /**
     * <p>Accuracy test for {@link Task#getDueDate()}.</p>
     */
    public void test_getDueDate() {
        assertEquals("Invalid default value.", null, instance.getDueDate());

        Date date = new Date();
        instance.setDueDate(date);
        assertEquals("Invalid return value.", date, instance.getDueDate());
    }

    /**
     * <p>Accuracy test for {@link Task#setDueDate()}.</p>
     */
    public void test_setDueDate() {
        Date date = new Date();
        instance.setDueDate(date);
        assertEquals("Invalid return value.", date, instance.getDueDate());
        instance.setDueDate(null);
        assertEquals("Invalid value is set.", null, instance.getDueDate());
    }

    /**
     * <p>Accuracy test for {@link Task#getAssignees()}.</p>
     */
    public void test_getAssignees() {
        assertEquals("Invalid default value.", null, instance.getAssignees());

        List<UserDTO> field = new ArrayList<UserDTO>();
        instance.setAssignees(field);
        assertEquals("Invalid return value.", field, instance.getAssignees());
    }

    /**
     * <p>Accuracy test for {@link Task#setAssignees()}.</p>
     */
    public void test_setAssignees() {
        List<UserDTO> field = new ArrayList<UserDTO>();
        instance.setAssignees(field);
        assertEquals("Invalid value is set.", field, instance.getAssignees());
        instance.setAssignees(null);
        assertEquals("Invalid value is set.", null, instance.getAssignees());
    }

    /**
     * <p>Accuracy test for {@link Task#getPriority()}.</p>
     */
    public void test_getPriority() {
        assertEquals("Invalid default value.", null, instance.getPriority());

        TaskPriority field = TaskPriority.HIGH;
        instance.setPriority(field);
        assertEquals("Invalid return value.", field, instance.getPriority());
    }

    /**
     * <p>Accuracy test for {@link Task#setPriority()}.</p>
     */
    public void test_setPriority() {
        TaskPriority field = TaskPriority.HIGH;
        instance.setPriority(field);
        assertEquals("Invalid value is set.", field, instance.getPriority());
        instance.setPriority(null);
        assertEquals("Invalid value is set.", null, instance.getPriority());
    }

    /**
     * <p>Accuracy test for {@link Task#getAttachments()}.</p>
     */
    public void test_getAttachments() {
        assertEquals("Invalid default value.", null, instance.getAttachments());

        List<TaskAttachment> field = new ArrayList<TaskAttachment>();
        instance.setAttachments(field);
        assertEquals("Invalid return value.", field, instance.getAttachments());
    }

    /**
     * <p>Accuracy test for {@link Task#setAttachments()}.</p>
     */
    public void test_setAttachments() {
        List<TaskAttachment> field = new ArrayList<TaskAttachment>();
        instance.setAttachments(field);
        assertEquals("Invalid value is set.", field, instance.getAttachments());
        instance.setAttachments(null);
        assertEquals("Invalid value is set.", null, instance.getAttachments());
    }

    /**
     * <p>Accuracy test for {@link Task#getStatus()}.</p>
     */
    public void test_getStatus() {
        assertEquals("Invalid default value.", null, instance.getStatus());

        TaskStatus field = TaskStatus.COMPLETED;
        instance.setStatus(field);
        assertEquals("Invalid return value.", field, instance.getStatus());
    }

    /**
     * <p>Accuracy test for {@link Task#setStatus()}.</p>
     */
    public void test_setStatus() {
        TaskStatus field = TaskStatus.COMPLETED;
        instance.setStatus(field);
        assertEquals("Invalid value is set.", field, instance.getStatus());
        instance.setStatus(null);
        assertEquals("Invalid value is set.", null, instance.getStatus());
    }

    /**
     * <p>Accuracy test for {@link Task#getTaskListId()}.</p>
     */
    public void test_getTaskListId() {
        assertEquals("Invalid default value.", 0, instance.getTaskListId());
        instance.setTaskListId(2);
        assertEquals("Invalid return value.", 2, instance.getTaskListId());
    }

    /**
     * <p>Accuracy test for {@link Task#setTaskListId()}.</p>
     */
    public void test_setTaskListId() {
        instance.setTaskListId(2);
        assertEquals("Invalid value is set.", 2, instance.getTaskListId());
        instance.setTaskListId(0);
        assertEquals("Invalid value is set.", 0, instance.getTaskListId());
    }
}
