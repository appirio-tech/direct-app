/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.TestsHelper;

/**
 * <p>
 * Unit tests for {@link ContestDTO} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ContestDTOUnitTests {
    /**
     * <p>
     * Represents the <code>ContestDTO</code> instance used in tests.
     * </p>
     */
    private ContestDTO instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ContestDTOUnitTests.class);
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
        instance = new ContestDTO();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestDTO()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ContestDTO();

        assertEquals("'contestId' should be correct.", 0L, TestsHelper.getField(instance, "contestId"));
        assertNull("'contestName' should be correct.", TestsHelper.getField(instance, "contestName"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getContestId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getContestId() {
        long value = 1L;
        instance.setContestId(value);

        assertEquals("'getContestId' should be correct.",
            value, instance.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestId(long contestId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setContestId() {
        long value = 1L;
        instance.setContestId(value);

        assertEquals("'setContestId' should be correct.",
            value, TestsHelper.getField(instance, "contestId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestName()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getContestName() {
        String value = "new_value";
        instance.setContestName(value);

        assertEquals("'getContestName' should be correct.",
            value, instance.getContestName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestName(String contestName)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setContestName() {
        String value = "new_value";
        instance.setContestName(value);

        assertEquals("'setContestName' should be correct.",
            value, TestsHelper.getField(instance, "contestName"));
    }
}