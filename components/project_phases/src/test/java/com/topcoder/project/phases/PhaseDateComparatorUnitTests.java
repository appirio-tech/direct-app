/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test case for <code>PhaseDateComparator</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class PhaseDateComparatorUnitTests extends TestCase {
    /**
     * Represents the <code>Phase</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Phase phase1;

    /**
     * Represents the <code>Phase</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Phase phase2;

    /**
     * A <code>PhaseDateComparator</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private PhaseDateComparator comparator;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        comparator = new PhaseDateComparator();

        Project project = new Project(new Date(), new MyWorkdays());
        phase1 = new Phase(project, 12 * 3600 * 1000);
        phase2 = new Phase(project, 24 * 3600 * 1000);
    }

    /**
     * Accuracy test of constructor.
     * <p>
     * Should create the instance successfully.
     * </p>
     */
    public void testConstructorAccuracy() {
        assertNotNull("Should create the instance successfully.", comparator);
    }

    /**
     * Failure test of <code>compare</code>.
     * <p>
     * With null object parameter.
     * </p>
     * <p>
     * Should throw ClassCastException.
     * </p>
     */
    public void testCompareFailureWithNullObject1() {
        try {
            comparator.compare(null, phase2);
            fail("Should throw ClassCastException.");
        } catch (ClassCastException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>compare</code>.
     * <p>
     * With null object parameter.
     * </p>
     * <p>
     * Should throw ClassCastException.
     * </p>
     */
    public void testCompareFailureWithNullObject2() {
        try {
            comparator.compare(phase1, null);
            fail("Should throw ClassCastException.");
        } catch (ClassCastException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>compare</code>.
     * <p>
     * With non Phase object parameter.
     * </p>
     * <p>
     * Should throw ClassCastException.
     * </p>
     */
    public void testCompareFailureWithNonPhase1() {
        try {
            comparator.compare(new Object(), phase2);
            fail("Should throw ClassCastException.");
        } catch (ClassCastException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>compare</code>.
     * <p>
     * With non Phase object parameter.
     * </p>
     * <p>
     * Should throw ClassCastException.
     * </p>
     */
    public void testCompareFailureWithNonPhase2() {
        try {
            comparator.compare(phase1, new Object());
            fail("Should throw ClassCastException.");
        } catch (ClassCastException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>compare</code>.
     * <p>
     * With phase1's start date before phase2's.
     * </p>
     * <p>
     * Should return negative value.
     * </p>
     */
    public void testCompareAccuracy1() {
        phase1.setActualStartDate(new Date(2000000));
        phase2.setActualStartDate(new Date(5000000));

        assertTrue("Should return negative value.", comparator.compare(phase1, phase2) < 0);
    }

    /**
     * Accuracy test of <code>compare</code>.
     * <p>
     * With phase1's start date after phase2's.
     * </p>
     * <p>
     * Should return positive value.
     * </p>
     */
    public void testCompareAccuracy2() {
        phase1.setActualStartDate(new Date(5000000));
        phase2.setActualStartDate(new Date(2000000));

        assertTrue("Should return positive value.", comparator.compare(phase1, phase2) > 0);
    }

    /**
     * Accuracy test of <code>compare</code>.
     * <p>
     * With same start date, but phase1's start date before phase2's.
     * </p>
     * <p>
     * Should return negative value.
     * </p>
     */
    public void testCompareAccuracy3() {
        phase1.setActualStartDate(new Date(2000000));
        phase2.setActualStartDate(new Date(2000000));
        phase1.setActualEndDate(new Date(4000000));
        phase2.setActualEndDate(new Date(6000000));

        assertTrue("Should return negative value.", comparator.compare(phase1, phase2) < 0);
    }

    /**
     * Accuracy test of <code>compare</code>.
     * <p>
     * With same start date, but phase1's start date after phase2's.
     * </p>
     * <p>
     * Should return positive value.
     * </p>
     */
    public void testCompareAccuracy4() {
        phase1.setActualStartDate(new Date(2000000));
        phase2.setActualStartDate(new Date(2000000));
        phase1.setActualEndDate(new Date(6000000));
        phase2.setActualEndDate(new Date(4000000));

        assertTrue("Should return positive value.", comparator.compare(phase1, phase2) > 0);
    }

    /**
     * Accuracy test of <code>compare</code>.
     * <p>
     * With same start date and end date.
     * </p>
     * <p>
     * Should return zero.
     * </p>
     */
    public void testCompareAccuracy5() {
        phase1.setActualStartDate(new Date(2000000));
        phase2.setActualStartDate(new Date(2000000));
        phase1.setActualEndDate(new Date(6000000));
        phase2.setActualEndDate(new Date(6000000));

        assertTrue("Should return zero.", comparator.compare(phase1, phase2) == 0);
    }
}
