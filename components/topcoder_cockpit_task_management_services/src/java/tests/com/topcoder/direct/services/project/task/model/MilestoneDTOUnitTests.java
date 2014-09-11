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
 * Unit tests for {@link MilestoneDTO} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class MilestoneDTOUnitTests {
    /**
     * <p>
     * Represents the <code>MilestoneDTO</code> instance used in tests.
     * </p>
     */
    private MilestoneDTO instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MilestoneDTOUnitTests.class);
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
        instance = new MilestoneDTO();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>MilestoneDTO()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MilestoneDTO();

        assertEquals("'milestoneId' should be correct.", 0L, TestsHelper.getField(instance, "milestoneId"));
        assertNull("'milestoneName' should be correct.", TestsHelper.getField(instance, "milestoneName"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getMilestoneId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getMilestoneId() {
        long value = 1L;
        instance.setMilestoneId(value);

        assertEquals("'getMilestoneId' should be correct.",
            value, instance.getMilestoneId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setMilestoneId(long milestoneId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setMilestoneId() {
        long value = 1L;
        instance.setMilestoneId(value);

        assertEquals("'setMilestoneId' should be correct.",
            value, TestsHelper.getField(instance, "milestoneId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getMilestoneName()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getMilestoneName() {
        String value = "new_value";
        instance.setMilestoneName(value);

        assertEquals("'getMilestoneName' should be correct.",
            value, instance.getMilestoneName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setMilestoneName(String milestoneName)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setMilestoneName() {
        String value = "new_value";
        instance.setMilestoneName(value);

        assertEquals("'setMilestoneName' should be correct.",
            value, TestsHelper.getField(instance, "milestoneName"));
    }
}