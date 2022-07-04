/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import com.topcoder.date.workdays.Workdays;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test case for <code>Project</code>. This part test phases related methods, including: <code>addPhase</code>,
 * <code>removePhase</code>, <code>clearPhases</code>, <code>getAllPhases()</code>,
 * <code>getAllPhases(Comparator)</code> and <code>getInitialPhases</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class ProjectPhaseUnitTests extends TestCase {
    /**
     * Represents the phase length.
     */
    private static final long LENGTH = 24 * 3600 * 1000;

    /**
     * Represents the start date.
     */
    private Date startDate = new Date();

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
     * Failure test of <code>addPhase</code>.
     * <p>
     * With null phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testAddPhaseFailureWithNullPhase() {
        try {
            project.addPhase(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>addPhase</code>.
     * <p>
     * With phase doesn't belong to this project.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testAddPhaseFailureWithPhaseNotBlongThisProject() {
        try {
            Project anotherProject = new Project(startDate, workdays);
            Phase phase = new Phase(anotherProject, LENGTH);

            project.addPhase(phase);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>addPhase</code>.
     * <p>
     * With new phase.
     * </p>
     * <p>
     * Should add the new phase.
     * </p>
     */
    public void testAddPhaseAccuracyWithNewPhase() {
        project.setChanged(false);
        Phase[] phases = project.getAllPhases();
        assertEquals("The inner phases set should be empty.", 0, phases.length);

        // Note: Phase constructor will call addPahse
        Phase phase = new Phase(project, LENGTH);
        assertTrue("Should set the changed flag to true.", project.isChanged());

        phases = project.getAllPhases();
        assertEquals("Should add the new phase.", 1, phases.length);
    }

    /**
     * Accuracy test of <code>addPhase</code>.
     * <p>
     * With phase already exist.
     * </p>
     * <p>
     * Should do nothing.
     * </p>
     */
    public void testAddPhaseAccuracyWithPhaseAlreadyExist() {
        Phase phase = new Phase(project, LENGTH);
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the new phase.", 1, phases.length);

        project.addPhase(phase);
        phases = project.getAllPhases();
        assertEquals("Should do nothing.", 1, phases.length);
    }

    /**
     * Accuracy test of <code>addPhase</code>.
     * <p>
     * With phase has same length.
     * </p>
     * <p>
     * Should add the phase.
     * </p>
     */
    public void testAddPhaseAccuracyWithPhaseSameLength() {
        Phase phaseA = new Phase(project, LENGTH);
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the new phase.", 1, phases.length);

        Phase phaseB = new Phase(project, LENGTH);
        phases = project.getAllPhases();
        assertEquals("Should add the phase.", 2, phases.length);
    }

    /**
     * Failure test of <code>removePhase</code>.
     * <p>
     * With null phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testRemovePhaseFailureWithNullPhase() {
        try {
            project.removePhase(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>removePhase</code>.
     * <p>
     * With phase doesn't exist.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testRemovePhaseFailureWithPhaseNotExist() {
        try {
            Project anotherProject = new Project(startDate, workdays);
            Phase phase = new Phase(anotherProject, LENGTH);
            project.removePhase(phase);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>removePhase</code>.
     * <p>
     * With cyclic dependency exists.
     * phaseA -> phaseB, phaseC -> phaseD, phaseD -> phaseC.
     * </p>
     * <p>
     * Should throw CyclicDependencyException.
     * </p>
     */
    public void testRemovePhaseFailureWithCyclicDependencyExist() {
        try {
            Phase phaseA = new Phase(project, LENGTH);
            Phase phaseB = new Phase(project, LENGTH);
            Phase phaseC = new Phase(project, LENGTH);
            Phase phaseD = new Phase(project, LENGTH);

            // phaseA -> phaseB
            phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
            // phaseC -> phaseD
            phaseD.addDependency(new Dependency(phaseC, phaseD, false, true, 0));
            // phaseD -> phaseC
            phaseC.addDependency(new Dependency(phaseD, phaseC, false, true, 0));

            project.removePhase(phaseA);
            fail("Should throw CyclicDependencyException.");
        } catch (CyclicDependencyException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>removePhase</code>.
     * <p>
     * With phase without dependency.
     * </p>
     * <p>
     * Should remove the phase.
     * </p>
     */
    public void testRemovePhaseAccuracyWithPhaseNoDependency() {
        Phase phase = new Phase(project, LENGTH);
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the new phase.", 1, phases.length);

        project.setChanged(false);
        project.removePhase(phase);
        assertTrue("Should set the changed flag to true.", project.isChanged());

        phases = project.getAllPhases();
        assertEquals("Should remove the phase.", 0, phases.length);
    }

    /**
     * Accuracy test of <code>removePhase</code>.
     * <p>
     * With phaseA -> phaseB.
     * With phaseA removed.
     * </p>
     * <p>
     * Should remove the phaseA and phaseB.
     * </p>
     */
    public void testRemovePhaseAccuracyWithPhaseHasDependency1() {
        Phase phaseA = new Phase(project, LENGTH);
        Phase phaseB = new Phase(project, LENGTH);

        // phaseA -> phaseB
        phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the phases.", 2, phases.length);

        project.removePhase(phaseA);
        phases = project.getAllPhases();
        assertEquals("Should remove the phase and its dependencies.", 0, phases.length);
    }

    /**
     * Accuracy test of <code>removePhase</code>.
     * <p>
     * With phaseA -> phaseB.
     * With phaseB removed.
     * </p>
     * <p>
     * Should remove the phasB.
     * </p>
     */
    public void testRemovePhaseAccuracyWithPhaseHasDependency2() {
        Phase phaseA = new Phase(project, LENGTH);
        phaseA.setId(1);
        Phase phaseB = new Phase(project, LENGTH);
        phaseB.setId(2);

        // phaseA -> phaseB
        phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the phases.", 2, phases.length);

        project.removePhase(phaseB);
        phases = project.getAllPhases();
        assertEquals("Should remove the phase and its dependencies.", 1, phases.length);
        assertEquals("phaseA should exist.", 1, phases[0].getId());
    }

    /**
     * Accuracy test of <code>removePhase</code>.
     * <p>
     * With phaseA -> phaseB, phaseA -> phaseC, phaseC -> phaseB.
     * With phaseA removed.
     * </p>
     * <p>
     * Should remove the phaseA, phaseB and phaseC.
     * </p>
     */
    public void testRemovePhaseAccuracyWithPhaseHasDependency3() {
        Phase phaseA = new Phase(project, LENGTH);
        Phase phaseB = new Phase(project, LENGTH);
        Phase phaseC = new Phase(project, LENGTH);

        // phaseA -> phaseB
        phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
        // phaseC -> phaseB
        phaseB.addDependency(new Dependency(phaseC, phaseB, false, true, 0));
        // phaseA -> phaseC
        phaseC.addDependency(new Dependency(phaseA, phaseC, false, true, 0));

        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the phases.", 3, phases.length);

        project.removePhase(phaseA);

        phases = project.getAllPhases();
        assertEquals("Should remove the phase and its dependencies.", 0, phases.length);
    }

    /**
     * Accuracy test of <code>removePhase</code>.
     * <p>
     * With phaseA -> phaseB, phaseC has no dependency.
     * With phaseA removed.
     * </p>
     * <p>
     * Should remove the phaseA and phaseB.
     * </p>
     */
    public void testRemovePhaseAccuracyWithComplexSituation1() {
        Phase phaseA = new Phase(project, LENGTH);
        phaseA.setId(1);
        Phase phaseB = new Phase(project, LENGTH);
        phaseB.setId(2);
        Phase phaseC = new Phase(project, LENGTH);
        phaseC.setId(3);

        // phaseA -> phaseB
        phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the phases.", 3, phases.length);

        project.removePhase(phaseA);
        phases = project.getAllPhases();
        assertEquals("Should remove the phaseA and phaseB.", 1, phases.length);
        assertEquals("phaseC should exist.", 3, phases[0].getId());
    }

    /**
     * Accuracy test of <code>removePhase</code>.
     * <p>
     * With phaseA -> phaseB, phaseC has no dependency.
     * With phaseB removed.
     * </p>
     * <p>
     * Should remove phaseB.
     * </p>
     */
    public void testRemovePhaseAccuracyWithComplexSituation2() {
        Phase phaseA = new Phase(project, LENGTH);
        phaseA.setId(1);
        Phase phaseB = new Phase(project, LENGTH);
        phaseB.setId(2);
        Phase phaseC = new Phase(project, LENGTH);
        phaseC.setId(3);

        // phaseA -> phaseB
        phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the phases.", 3, phases.length);

        project.removePhase(phaseB);
        phases = project.getAllPhases();
        assertEquals("Should remove phaseB.", 2, phases.length);
        assertTrue("Should remove phaseB.", phases[0].getId() != 2);
        assertTrue("Should remove phaseB.", phases[1].getId() != 2);
    }

    /**
     * Accuracy test of <code>removePhase</code>.
     * <p>
     * With phaseA -> phaseB, phaseC has no dependency.
     * With phaseC removed.
     * </p>
     * <p>
     * Should remove phaseC.
     * </p>
     */
    public void testRemovePhaseAccuracyWithComplexSituation3() {
        Phase phaseA = new Phase(project, LENGTH);
        phaseA.setId(1);
        Phase phaseB = new Phase(project, LENGTH);
        phaseB.setId(2);
        Phase phaseC = new Phase(project, LENGTH);
        phaseC.setId(3);

        // phaseA -> phaseB
        phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the phases.", 3, phases.length);

        project.removePhase(phaseC);
        phases = project.getAllPhases();
        assertEquals("Should remove phaseC.", 2, phases.length);
        assertTrue("Should remove phaseC.", phases[0].getId() != 3);
        assertTrue("Should remove phaseC.", phases[1].getId() != 3);
    }

    /**
     * Accuracy test of <code>clearPhases</code>.
     * <p>
     * Should clear phases.
     * </p>
     */
    public void testClearPhasesAccuracy() {
        Phase phase = new Phase(project, LENGTH);
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the new phase.", 1, phases.length);

        project.setChanged(false);
        project.clearPhases();
        assertTrue("Should set the changed flag to true.", project.isChanged());

        phases = project.getAllPhases();
        assertEquals("Should clear phases.", 0, phases.length);
    }

    /**
     * Accuracy test of <code>getAllPhases()</code>.
     * <p>
     * With no phase exist.
     * </p>
     * <p>
     * Should return the empty phases array.
     * </p>
     */
    public void testGetAllPhasesAccuracyWithNoPhase() {
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the new phase.", 0, phases.length);
    }

    /**
     * Accuracy test of <code>getAllPhases()</code>.
     * <p>
     * With one phase exist.
     * </p>
     * <p>
     * Should return the proper phases array.
     * </p>
     */
    public void testGetAllPhasesAccuracyWithOnePhase() {
        Phase phase = new Phase(project, LENGTH);
        Phase[] phases = project.getAllPhases();
        assertEquals("Should add the new phase.", 1, phases.length);
    }

    /**
     * Failure test of <code>getAllPhases(Comparator)</code>.
     * <p>
     * With null comparator.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testGetAllPhasesFailureWithNullComparator() {
        try {
            project.getAllPhases(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>getAllPhases(Comparator)</code>.
     * <p>
     * With valid comparator.
     * </p>
     * <p>
     * Should return the proper phases array.
     * </p>
     */
    public void testGetAllPhasesAccuracyWithValidComparator() {
        Phase phaseA = new Phase(project, LENGTH);
        Phase phaseB = new Phase(project, LENGTH);

        Phase[] phases = project.getAllPhases(new PhaseDateComparator());
        assertEquals("Should return the proper phases array.", 2, phases.length);
    }

    /**
     * Accuracy test of <code>getInitialPhases</code>.
     * <p>
     * With no phase exist.
     * </p>
     * <p>
     * Should return the empty phases array.
     * </p>
     */
    public void testGetInitialPhasesAccuracyWithNoPhase() {
        Phase[] phases = project.getInitialPhases();
        assertEquals("Should return the empty phases array.", 0, phases.length);
    }

    /**
     * Accuracy test of <code>getInitialPhases</code>.
     * <p>
     * With one phase exist.
     * </p>
     * <p>
     * Should return proper phases array.
     * </p>
     */
    public void testGetInitialPhasesAccuracyWithOnePhase() {
        Phase phase = new Phase(project, LENGTH);
        Phase[] phases = project.getInitialPhases();
        assertEquals("Should return proper phases array.", 1, phases.length);
    }

    /**
     * Accuracy test of <code>getInitialPhases</code>.
     * <p>
     * With multiple phases exist.
     * </p>
     * <p>
     * Should return proper phases array.
     * </p>
     */
    public void testGetInitialPhasesAccuracyWithMutilplePhases() {
        Phase phaseA = new Phase(project, LENGTH);
        phaseA.setId(1);
        Phase phaseB = new Phase(project, LENGTH);
        phaseB.setId(2);
        Phase phaseC = new Phase(project, LENGTH);
        phaseC.setId(3);

        // phaseA -> phaseB
        phaseB.addDependency(new Dependency(phaseA, phaseB, false, true, 0));

        Phase[] phases = project.getInitialPhases();
        assertEquals("Should return proper phases array.", 2, phases.length);
        assertTrue("phaseB should not returned.", phases[0].getId() != 2);
        assertTrue("phaseB should not returned.", phases[1].getId() != 2);
    }

    /**
     * Accuracy test of <code>containsPhase</code>.
     * <p>
     * With null phase.
     * </p>
     * <p>
     * Should return false.
     * </p>
     */
    public void testContainsPhaseAccuracyWithNullPhase() {
        assertFalse("Should return false.", project.containsPhase(null));
    }

    /**
     * Accuracy test of <code>containsPhase</code>.
     * <p>
     * With phase exist in project.
     * </p>
     * <p>
     * Should return true.
     * </p>
     */
    public void testContainsPhaseAccuracyWithPhaseExist() {
        Phase phaseA = new Phase(project, LENGTH);
        assertTrue("Should return true.", project.containsPhase(phaseA));
    }

    /**
     * Accuracy test of <code>containsPhase</code>.
     * <p>
     * With phase not exist in project.
     * </p>
     * <p>
     * Should return false.
     * </p>
     */
    public void testContainsPhaseAccuracyWithPhaseNotExist() {
        Phase phaseA = new Phase(project, LENGTH);
        project.removePhase(phaseA);
        assertFalse("Should return false.", project.containsPhase(phaseA));
    }
}
