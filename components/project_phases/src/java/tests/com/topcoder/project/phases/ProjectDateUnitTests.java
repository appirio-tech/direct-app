/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import com.topcoder.date.workdays.Workdays;

import java.lang.reflect.Field;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test case for <code>Project</code>. This part test project date related methods, including:
 * <code>calcEndDate</code> and <code>calculateProjectDate</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class ProjectDateUnitTests extends TestCase {
    /**
     * Represents the phase length.
     */
    private static final long LENGTH = 24 * 3600 * 1000;

    /**
     * Represents the start date.
     */
    private Date startDate = new Date(0);

    /**
     * Represents the work days.
     */
    private Workdays workdays = new MyWorkdays();

    /**
     * Represents the <code>Phase</code> instance used for testing.
     */
    private Phase phase;

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
     * Accuracy test of <code>calculateProjectDate</code>.
     * <p>
     * With false changed flag.
     * </p>
     * <p>
     * Should do nothing.
     * </p>
     */
    public void testCalculateProjectDateAccuracyWithFalseChanged() {
        phase = new Phase(project, LENGTH);

        project.setChanged(false);
        project.calculateProjectDate();

        checkFieldValue("cachedStartDate", null);
        checkFieldValue("cachedEndDate", null);
    }

    /**
     * Accuracy test of <code>calculateProjectDate</code>.
     * <p>
     * With true changed flag and valid phase.
     * </p>
     * <p>
     * Should calculate the start and end date of phases.
     * </p>
     */
    public void testCalculateProjectDateAccuracyWithTrueChanged() {
        phase = new Phase(project, LENGTH);
        Date endDate = new Date(LENGTH);

        project.calculateProjectDate();
        assertFalse("Should set the changed flag to false.", project.isChanged());

        checkFieldValue("cachedStartDate", startDate);
        checkFieldValue("cachedEndDate", endDate);
    }

    /**
     * Failure test of <code>calculateProjectDate</code>.
     * <p>
     * With cyclic dependency exists.
     * </p>
     * <p>
     * Should throw CyclicDependencyException.
     * </p>
     */
    public void testCalculateProjectDateFailureWithCyclicDependency() {
        try {
            Phase phaseA = new Phase(project, LENGTH);
            Phase phaseB = new Phase(project, LENGTH);
            Phase phaseC = new Phase(project, LENGTH);

            // phaseA => phaseB => phaseC => phaseA
            phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
            phaseC.addDependency(new Dependency(phaseB, phaseC, false, true, 0));
            phaseA.addDependency(new Dependency(phaseC, phaseA, false, true, 0));

            project.calculateProjectDate();
            fail("Should throw CyclicDependencyException.");
        } catch (CyclicDependencyException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>calcEndDate</code>.
     * <p>
     * With no phase exist in project.
     * </p>
     * <p>
     * Should return the project start date.
     * </p>
     */
    public void testCalcEndDateAccuracyWithNoPhase() {
        assertEquals("Should return the project start date.", startDate, project.calcEndDate());
    }

    /**
     * Accuracy test of <code>calcEndDate</code>.
     * <p>
     * With a single phase exist in project.
     * </p>
     * <p>
     * Should return the end date of project.
     * </p>
     */
    public void testCalcEndDateAccuracyWithSinglePhase() {
        phase = new Phase(project, LENGTH);
        Date endDate = new Date(LENGTH);

        assertEquals("Should return the end date of project.", endDate, project.calcEndDate());
    }

    /**
     * Accuracy test of <code>calcEndDate</code>.
     * <p>
     * With multiple phases exist in project.
     * </p>
     * <p>
     * Should return the end date of the last phase.
     * </p>
     */
    public void testCalcEndDateAccuracyWithMultiplePhases() {
        Phase phaseA = new Phase(project, LENGTH);
        Phase phaseB = new Phase(project, 2 * LENGTH);
        Date endDate = new Date(2 * LENGTH);

        assertEquals("Should return the end date of the last phase.", endDate, project.calcEndDate());
    }

    /**
     * Check the specified field's value is the same as expected.
     *
     * @param name
     *            the name of the field.
     * @param expected
     *            the expected field value.
     */
    private void checkFieldValue(String name, Object expected) {
        try {
            Field field = Phase.class.getDeclaredField(name);
            field.setAccessible(true);
            assertEquals("The value of " + name + " is invalid.", expected, field.get(phase));
        } catch (NoSuchFieldException e) {
            // Just ignore
        } catch (IllegalAccessException e) {
            // Just ignore
        }
    }
}
