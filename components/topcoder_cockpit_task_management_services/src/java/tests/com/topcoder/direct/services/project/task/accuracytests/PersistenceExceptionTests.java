/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.PersistenceException;
import com.topcoder.direct.services.project.task.TaskManagementException;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;


/**
 * <p>Unit tests for {@link PersistenceException} class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class PersistenceExceptionTests extends TestCase {
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
    private PersistenceException exception;

    /**
     * <p>Adapter for earlier versions of JUnit.</p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PersistenceExceptionTests.class);
    }

    /**
     * <p>Accuracy tests for inheritance.</p>
     */
    public void testInheritence() {
        assertEquals("inheritance is wrong", TaskManagementException.class, PersistenceException.class.getSuperclass());
    }

    /**
     * Accuracy test for the constructor.
     */
    public void testCtor1() {
        exception = new PersistenceException(MESSAGE);
        assertNotNull("message is not set", exception.getMessage());
        assertEquals("message is not set", MESSAGE, exception.getMessage());
    }

    /**
     * Accuracy test for the constructor.
     */
    public void testCtor2() {
        exception = new PersistenceException(MESSAGE, CAUSE);
        assertNotNull("message is not set", exception.getMessage());
        assertEquals("message is not set", MESSAGE, exception.getMessage());
        assertNotNull("cause is not set", exception.getCause());
        assertEquals("cause is not set", CAUSE, exception.getCause());
    }
}
