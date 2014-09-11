/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
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
 * Unit tests for {@link BaseTaskEntity} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class BaseTaskEntityUnitTests {
    /**
     * <p>
     * Represents the <code>BaseTaskEntity</code> instance used in tests.
     * </p>
     */
    private BaseTaskEntity instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseTaskEntityUnitTests.class);
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
        instance = new MockBaseTaskEntity();
    }

    /**
     * <p>
     * <code>BaseTaskEntity</code> should be subclass of <code>AuditableEntity</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("BaseTaskEntity should be subclass of AuditableEntity.",
            BaseTaskEntity.class.getSuperclass() == AuditableEntity.class);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseTaskEntity()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockBaseTaskEntity();

        assertNull("'name' should be correct.", TestsHelper.getField(instance, "name"));
        assertNull("'notes' should be correct.", TestsHelper.getField(instance, "notes"));
        assertNull("'associatedToProjectMilestones' should be correct.",
                TestsHelper.getField(instance, "associatedToProjectMilestones"));
        assertNull("'associatedToContests' should be correct.", TestsHelper.getField(instance, "associatedToContests"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getName()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getName() {
        String value = "new_value";
        instance.setName(value);

        assertEquals("'getName' should be correct.",
            value, instance.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setName(String name)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setName() {
        String value = "new_value";
        instance.setName(value);

        assertEquals("'setName' should be correct.",
            value, TestsHelper.getField(instance, "name"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getNotes()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getNotes() {
        String value = "new_value";
        instance.setNotes(value);

        assertEquals("'getNotes' should be correct.",
            value, instance.getNotes());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setNotes(String notes)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setNotes() {
        String value = "new_value";
        instance.setNotes(value);

        assertEquals("'setNotes' should be correct.",
            value, TestsHelper.getField(instance, "notes"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssociatedToProjectMilestones()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAssociatedToProjectMilestones() {
        List<MilestoneDTO> value = new ArrayList<MilestoneDTO>();
        instance.setAssociatedToProjectMilestones(value);

        assertSame("'getAssociatedToProjectMilestones' should be correct.",
            value, instance.getAssociatedToProjectMilestones());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssociatedToProjectMilestones(List&lt;MilestoneDTO&gt;
     * associatedToProjectMilestones)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssociatedToProjectMilestones() {
        List<MilestoneDTO> value = new ArrayList<MilestoneDTO>();
        instance.setAssociatedToProjectMilestones(value);

        assertSame("'setAssociatedToProjectMilestones' should be correct.",
            value, TestsHelper.getField(instance, "associatedToProjectMilestones"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssociatedToContests()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAssociatedToContests() {
        List<ContestDTO> value = new ArrayList<ContestDTO>();
        instance.setAssociatedToContests(value);

        assertSame("'getAssociatedToContests' should be correct.",
            value, instance.getAssociatedToContests());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssociatedToContests(List&lt;ContestDTO&gt;
     * associatedToContests)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssociatedToContests() {
        List<ContestDTO> value = new ArrayList<ContestDTO>();
        instance.setAssociatedToContests(value);

        assertSame("'setAssociatedToContests' should be correct.",
            value, TestsHelper.getField(instance, "associatedToContests"));
    }
}