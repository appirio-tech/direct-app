/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link TaskPriority} enumeration.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TaskPriorityUnitTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TaskPriorityUnitTests.class);
    }

    /**
     * <p>
     * Accuracy test for the constant <code>HIGH</code>.<br>
     * The value should be correct.
     * </p>
     */
    @Test
    public void test_HIGH() {
        assertEquals("'HIGH' should be correct.", "HIGH", TaskPriority.HIGH.toString());
    }

    /**
     * <p>
     * Accuracy test for the constant <code>LOW</code>.<br>
     * The value should be correct.
     * </p>
     */
    @Test
    public void test_LOW() {
        assertEquals("'LOW' should be correct.", "LOW", TaskPriority.LOW.toString());
    }

    /**
     * <p>
     * Accuracy test for the constant <code>NORMAL</code>.<br>
     * The value should be correct.
     * </p>
     */
    @Test
    public void test_NORMAL() {
        assertEquals("'NORMAL' should be correct.", "NORMAL", TaskPriority.NORMAL.toString());
    }
}
