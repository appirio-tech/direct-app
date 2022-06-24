/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import com.topcoder.date.workdays.Workdays;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test case for <code>Project</code>. This part test basic methods, including: <code>constructor</code>,
 * <code>getWorkdays</code>, <code>getStartDate</code>, <code>setStartDate</code>, <code>getId</code>,
 * <code>setId</code>, <code>isChanged</code> and <code>setChanged</code>. Other methods test please refer to
 * <code>ProjectPhaseUnitTests</code> and <code>ProjectDateUnitTests</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class ProjectBasicUnitTests extends TestCase {
    /**
     * Represents the start date.
     */
    private Date startDate = new Date(7 * 60 * 60 * 1000);

    /**
     * Represents the work days.
     */
    private Workdays workdays = new MyWorkdays();

    /**
     * A <code>Project</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Project project;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        project = new Project(startDate, workdays);
    }

    /**
     * Failure test of constructor.
     * <p>
     * With null start date.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testConstructorFailureWithNullStartDate() {
        try {
            new Project(null, workdays);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of constructor.
     * <p>
     * With null work days.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testConstructorFailureWithNullWorkDays() {
        try {
            new Project(startDate, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of constructor.
     * <p>
     * With valid start date and work days.
     * </p>
     * <p>
     * Should create the instance successfully.
     * </p>
     */
    public void testConstructorAccuracy() {
        assertNotNull("Should create the instance successfully.", project);
    }

    /**
     * Accuracy test of <code>getWorkdays</code>.
     * <p>
     * Should return the proper workdays.
     * </p>
     */
    public void testGetWorkdaysAccuracy() {
        assertEquals("Should return the proper workdays.", workdays, project.getWorkdays());
    }

    /**
     * Accuracy test of <code>getStartDate</code>.
     * <p>
     * With valid start date.
     * </p>
     * <p>
     * Should return the proper start date.
     * </p>
     */
    public void testGetStartDateAccuracy() {
        assertEquals("Should return the proper start date.", startDate, project.getStartDate());
    }

    /**
     * Failure test of <code>setStartDate</code>.
     * <p>
     * With null start date.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetStartDateFailureWithNullStartDate() {
        try {
            project.setStartDate(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>setStartDate</code>.
     * <p>
     * With same start date.
     * </p>
     * <p>
     * Should do nothing.
     * </p>
     */
    public void testSetStartDateAccuracyWithSameStartDate() {
        project.setChanged(false);
        Date date = new Date(7 * 60 * 60 * 1000);
        project.setStartDate(date);
        assertEquals("Should do nothing", startDate, project.getStartDate());
        assertFalse("Should do nothing.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setStartDate</code>.
     * <p>
     * With different start date.
     * </p>
     * <p>
     * Should set the start date.
     * </p>
     */
    public void testSetStartDateAccuracyWithDifferentStartDate() {
        project.setChanged(false);
        Date date = new Date(8 * 60 * 60 * 1000);
        project.setStartDate(date);
        assertEquals("Should set the start date.", date, project.getStartDate());
        assertTrue("Should do nothing.", project.isChanged());
    }

    /**
     * Accuracy test of <code>getId</code>.
     * <p>
     * With default project id.
     * </p>
     * <p>
     * Should return the proper id.
     * </p>
     */
    public void testGetIdAccuracyWithDefault() {
        assertEquals("Should return the proper id.", 0, project.getId());
    }

    /**
     * Failure test of <code>setId</code>.
     * <p>
     * With negative id.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetIdFailureWithNegativeId() {
        try {
            project.setId(-1);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>setId</code>.
     * <p>
     * With zero id.
     * </p>
     * <p>
     * Should set the id to zero.
     * </p>
     */
    public void testSetIdAccuracyZero() {
        project.setId(0);
        assertEquals("Should set the id to zero.", 0, project.getId());
    }

    /**
     * Accuracy test of <code>setId</code>.
     * <p>
     * With positive id.
     * </p>
     * <p>
     * Should set the id to positive.
     * </p>
     */
    public void testSetIdAccuracyPositive() {
        project.setId(1);
        assertEquals("Should set the id.", 1, project.getId());
    }

    /**
     * Accuracy test of <code>isChanged</code>.
     * <p>
     * With default value.
     * </p>
     * <p>
     * Should return true.
     * </p>
     */
    public void testIsChangedAccuracyWithDefault() {
        assertTrue("Should return false.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setChanged</code>.
     * <p>
     * With false.
     * </p>
     * <p>
     * Should set the changed flag to false.
     * </p>
     */
    public void testSetChangedAccuracyWithFalse() {
        project.setChanged(false);
        assertFalse("Should set the changed flag to false.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setChanged</code>.
     * <p>
     * With true.
     * </p>
     * <p>
     * Should set the changed flag to true.
     * </p>
     */
    public void testSetChangedAccuracyWithTrue() {
        project.setChanged(true);
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }
}
