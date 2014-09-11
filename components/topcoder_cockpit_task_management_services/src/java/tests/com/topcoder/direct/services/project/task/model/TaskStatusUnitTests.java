/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link TaskStatus} enumeration.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TaskStatusUnitTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TaskStatusUnitTests.class);
    }

    /**
     * <p>
     * Accuracy test for the constant <code>NOT_STARTED</code>.<br>
     * The value should be correct.
     * </p>
     */
    @Test
    public void test_NOT_STARTED() {
        assertEquals("'NOT_STARTED' should be correct.", "NOT_STARTED", TaskStatus.NOT_STARTED.toString());
    }

    /**
     * <p>
     * Accuracy test for the constant <code>IN_PROGRESS</code>.<br>
     * The value should be correct.
     * </p>
     */
    @Test
    public void test_IN_PROGRESS() {
        assertEquals("'IN_PROGRESS' should be correct.", "IN_PROGRESS", TaskStatus.IN_PROGRESS.toString());
    }

    /**
     * <p>
     * Accuracy test for the constant <code>WAIT_ON_DEPENDENCY</code>.<br>
     * The value should be correct.
     * </p>
     */
    @Test
    public void test_WAIT_ON_DEPENDENCY() {
        assertEquals("'WAIT_ON_DEPENDENCY' should be correct.", "WAIT_ON_DEPENDENCY",
                TaskStatus.WAIT_ON_DEPENDENCY.toString());
    }

    /**
     * <p>
     * Accuracy test for the constant <code>COMPLETED</code>.<br>
     * The value should be correct.
     * </p>
     */
    @Test
    public void test_COMPLETED() {
        assertEquals("'COMPLETED' should be correct.", "COMPLETED", TaskStatus.COMPLETED.toString());
    }
}
