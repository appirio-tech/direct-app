/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import junit.framework.TestCase;

/**
 * Unit test case for <code>Phase</code>. This part test the date calculation methods, including
 * <code>calcEndDate()</code> , <code>calcStartDate()</code>, <code>calcEndDate(Set, Map, Map)</code> and
 * <code>calcStartDate(Set, Map, Map)</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class PhaseDateUnitTests extends TestCase {
    /**
     * Represents the length of phase.
     */
    private static final long LENGTH = 24 * 3600 * 1000L;

    /**
     * Represents the start date of project.
     */
    private Date projectStartDate = new Date(0);

    /**
     * Represents the <code>Project</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Project project;

    /**
     * A <code>Phase</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Phase phase;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        project = new Project(projectStartDate, new MyWorkdays());
        project.setChanged(false);

        phase = new Phase(project, LENGTH);
    }

    /**
     * Failure test of <code>calcEndDate(Set, Map, Map)</code>.
     * <p>
     * With cyclic dependency exists.
     * </p>
     * <p>
     * Should throw CyclicDependencyException.
     * </p>
     */
    public void testCalcEndDateFailureWithCyclicDependency() {
        try {
            Phase phaseA = new Phase(project, LENGTH);
            Phase phaseB = new Phase(project, LENGTH);

            // phaseA => phaseB => phase => phaseA
            phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
            phase.addDependency(new Dependency(phaseB, phase, false, true, 0));
            phaseA.addDependency(new Dependency(phase, phaseA, false, true, 0));

            phase.calcEndDate(new HashSet(), new HashMap(), new HashMap());
            fail("Should throw CyclicDependencyException.");
        } catch (CyclicDependencyException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>calcEndDate(Set, Map, Map)</code>.
     * <p>
     * With actual end date exist.
     * </p>
     * <p>
     * Should return the actual end date.
     * </p>
     */
    public void testCalcEndDateAccuracyWithActualEndDate() {
        Date date = new Date();
        phase.setActualEndDate(date);
        assertEquals("Should return the actual end date.", date,
                phase.calcEndDate(new HashSet(), new HashMap(), new HashMap()));
    }

    /**
     * Accuracy test of <code>calcEndDate(Set, Map, Map)</code>.
     * <p>
     * With cached end date exist.
     * </p>
     * <p>
     * Should return the cached end date.
     * </p>
     */
    public void testCalcEndDateAccuracyWithCachedEndDate() {
        Date date = new Date();
        Map endDateCache = new HashMap();
        endDateCache.put(phase, date);
        assertEquals("Should return the cached end date.", date,
                phase.calcEndDate(new HashSet(), new HashMap(), endDateCache));
    }

    /**
     * Accuracy test of <code>calcEndDate(Set, Map, Map)</code>.
     * <p>
     * With phase start date plus length is the latest date.
     * </p>
     * <p>
     * Should return the phase start date plus length.
     * </p>
     */
    public void testCalcEndDateAccuracyWithPhaseStartDate() {
        Date date = new Date();
        phase.setActualStartDate(date);

        Date endDate = new Date(phase.calcStartDate().getTime() + LENGTH);
        assertEquals("Should return the phase start date plus length.", endDate,
                phase.calcEndDate(new HashSet(), new HashMap(), new HashMap()));
    }

    /**
     * Accuracy test of <code>calcEndDate(Set, Map, Map)</code>.
     * <p>
     * With end dependency exist, phase ends after dependency ends.
     * </p>
     * <p>
     * Should return the dependency end date plus lag time.
     * </p>
     */
    public void testCalcEndDateAccuracyWithPhaseEndsAfterDependencyEnds() {
        Date date = new Date(phase.calcStartDate().getTime() + LENGTH);
        Phase dependency = new Phase(project, LENGTH);
        dependency.setActualEndDate(date);

        // phase ends after dependency phase ends, the lag time is LENGTH
        phase.addDependency(new Dependency(dependency, phase, false, false, LENGTH));

        Date endDate = new Date(date.getTime() + LENGTH);
        assertEquals(" Should return the dependency end date plus lag time.", endDate,
                phase.calcEndDate(new HashSet(), new HashMap(), new HashMap()));
    }

    /**
     * Accuracy test of <code>calcEndDate(Set, Map, Map)</code>.
     * <p>
     * With end dependency exist, phase ends after dependency starts.
     * </p>
     * <p>
     * Should return the dependency start date plus lag time.
     * </p>
     */
    public void testCalcEndDateAccuracyWithPhaseEndsAfterDependencyStarts() {
        Date date = new Date(phase.calcStartDate().getTime() + LENGTH);
        Phase dependency = new Phase(project, LENGTH);
        dependency.setActualStartDate(date);

        // phase ends after dependency phase starts, the lag time is LENGTH
        phase.addDependency(new Dependency(dependency, phase, true, false, LENGTH));

        Date endDate = new Date(date.getTime() + LENGTH);
        assertEquals(" Should return the dependency start date plus lag time.", endDate,
                phase.calcEndDate(new HashSet(), new HashMap(), new HashMap()));
    }

    /**
     * Failure test of <code>calcStartDate(Set, Map, Map)</code>.
     * <p>
     * With cyclic dependency exists.
     * </p>
     * <p>
     * Should throw CyclicDependencyException.
     * </p>
     */
    public void testCalcStartDateFailureWithCyclicDependency() {
        try {
            Phase phaseA = new Phase(project, LENGTH);
            Phase phaseB = new Phase(project, LENGTH);

            // phaseA => phaseB => phase => phaseA
            phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
            phase.addDependency(new Dependency(phaseB, phase, false, true, 0));
            phaseA.addDependency(new Dependency(phase, phaseA, false, true, 0));

            phase.calcStartDate(new HashSet(), new HashMap(), new HashMap());
            fail("Should throw CyclicDependencyException.");
        } catch (CyclicDependencyException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>calcStartDate(Set, Map, Map)</code>.
     * <p>
     * With actual start date exist.
     * </p>
     * <p>
     * Should return the actual start date.
     * </p>
     */
    public void testCalcStartDateAccuracyWithActualStartDate() {
        Date date = new Date();
        phase.setActualStartDate(date);
        assertEquals("Should return the actual start date.", date,
                phase.calcStartDate(new HashSet(), new HashMap(), new HashMap()));
    }

    /**
     * Accuracy test of <code>calcStartDate(Set, Map, Map)</code>.
     * <p>
     * With cached start date exist.
     * </p>
     * <p>
     * Should return the cached start date.
     * </p>
     */
    public void testCalcStartDateAccuracyWithCachedStartDate() {
        Date date = new Date();
        Map startDateCache = new HashMap();
        startDateCache.put(phase, date);
        assertEquals("Should return the cached start date.", date,
                phase.calcStartDate(new HashSet(), startDateCache, new HashMap()));
    }

    /**
     * Accuracy test of <code>calcStartDate(Set, Map, Map)</code>.
     * <p>
     * With project start date is the latest date.
     * </p>
     * <p>
     * Should return the project start date.
     * </p>
     */
    public void testCalcStartDateAccuracyWithProjectStartDate() {
        assertEquals("Should return the project start date.", projectStartDate,
                phase.calcStartDate(new HashSet(), new HashMap(), new HashMap()));
    }

    /**
     * Accuracy test of <code>calcStartDate(Set, Map, Map)</code>.
     * <p>
     * With fixed start date is the latest date.
     * </p>
     * <p>
     * Should return the fixed start date.
     * </p>
     */
    public void testCalcStartDateAccuracyWithFixedStartDate() {
        Date date = new Date(LENGTH);
        phase.setFixedStartDate(date);

        assertEquals("Should return the fixed start date.", date,
                phase.calcStartDate(new HashSet(), new HashMap(), new HashMap()));
    }

    /**
     * Accuracy test of <code>calcStartDate(Set, Map, Map)</code>.
     * <p>
     * With start dependency exist, phase starts after dependency starts.
     * </p>
     * <p>
     * Should return the dependency start date plus lag time.
     * </p>
     */
    public void testCalcStartDateAccuracyWithPhaseStartsAfterDependencyStarts() {
        Date date = new Date(LENGTH);
        Phase dependency = new Phase(project, LENGTH);
        dependency.setActualStartDate(date);

        // phase starts after dependency phase starts, the lag time is LENGTH
        phase.addDependency(new Dependency(dependency, phase, true, true, LENGTH));

        Date startDate = new Date(date.getTime() + LENGTH);
        assertEquals("Should return the dependency start date plus lag time.", startDate,
                phase.calcStartDate(new HashSet(), new HashMap(), new HashMap()));
    }

    /**
     * Accuracy test of <code>calcStartDate(Set, Map, Map)</code>.
     * <p>
     * With start dependency exist, phase starts after dependency ends.
     * </p>
     * <p>
     * Should return the dependency end date plus lag time.
     * </p>
     */
    public void testCalcStartDateAccuracyWithPhaseStartsAfterDependencyEnds() {
        Date date = new Date(LENGTH);
        Phase dependency = new Phase(project, LENGTH);
        dependency.setActualEndDate(date);

        // phase starts after dependency phase starts, the lag time is LENGTH
        phase.addDependency(new Dependency(dependency, phase, false, true, LENGTH));

        Date startDate = new Date(date.getTime() + LENGTH);
        assertEquals(" Should return the dependency start date plus lag time.", startDate,
                phase.calcStartDate(new HashSet(), new HashMap(), new HashMap()));
    }

    /**
     * Accuracy test of <code>calcEndDate()</code>.
     * <p>
     * Should return the proper end date of phase.
     * </p>
     */
    public void testCalcEndDateAccuracyWithNonArgument() {
        Date endDate = new Date(LENGTH);

        assertEquals("Should return the proper end date of phase.", endDate, phase.calcEndDate());
    }

    /**
     * Accuracy test of <code>calcStartDate()</code>.
     * <p>
     * Should return the proper start date of phase.
     * </p>
     */
    public void testCalcStartDateAccuracyWithNonArgument() {
        assertEquals("Should return the proper start date of phase.", projectStartDate, phase.calcStartDate());
    }
}
