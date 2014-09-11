/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.TaskManagementException;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;


/**
 * <p>Unit tests for {@link TaskManagementException} class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class TaskManagementExceptionTests extends TestCase {
    /**
     * <p>This represents the error message.</p>
     */
    private static final String MESSAGE = "message";

    /**
     * <p>This represents the error cause.</p>
     */
    private static final Throwable CAUSE = new Exception("cause");

    /**
     * <p>A instance used for test.</p>
     */
    private TaskManagementException exception;

    /**
     * <p>Adapter for earlier versions of JUnit.</p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TaskManagementExceptionTests.class);
    }

    /**
     * <p>Accuracy tests for inheritance.</p>
     */
    public void testInheritence() {
        assertEquals("inheritance is wrong", Exception.class, TaskManagementException.class.getSuperclass());
    }

    /**
     * Accuracy test for the constructor.
     */
    public void testCtor1() {
        exception = new TaskManagementException(MESSAGE);
        assertNotNull("message is not set", exception.getMessage());
        assertEquals("message is not set", MESSAGE, exception.getMessage());
    }

    /**
     * Accuracy test for the constructor.
     */
    public void testCtor2() {
        exception = new TaskManagementException(MESSAGE, CAUSE);
        assertNotNull("message is not set", exception.getMessage());
        assertEquals("message is not set", MESSAGE, exception.getMessage());
        assertNotNull("cause is not set", exception.getCause());
        assertEquals("cause is not set", CAUSE, exception.getCause());
    }
}
