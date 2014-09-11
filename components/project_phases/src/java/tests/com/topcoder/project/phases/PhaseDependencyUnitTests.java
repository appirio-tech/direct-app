/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test case for <code>Project</code>. This part test dependency related methods, including:
 * <code>addDependency</code>, <code>removeDependency</code>, <code>clearDependencies</code> and
 * <code>getAllDependencies</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class PhaseDependencyUnitTests extends TestCase {
    /**
     * Represents the length of phase.
     */
    private static final long LENGTH = 24 * 3600 * 1000L;

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
        project = new Project(new Date(), new MyWorkdays());
        project.setChanged(false);
        phase = new Phase(project, LENGTH);
    }

    /**
     * Failure test of <code>addDependency</code>.
     * <p>
     * With null dependency.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testAddDependencyFailureWithNullDependency() {
        try {
            phase.addDependency(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>addDependency</code>.
     * <p>
     * With dependency's dependency not belong to current project.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testAddDependencyFailureWithDependencyNotBelong() {
        try {
            Project anotherProject = new Project(new Date(), new MyWorkdays());
            Phase dependency = new Phase(anotherProject, 24);
            phase.addDependency(new Dependency(dependency, phase, false, true, 0));
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>addDependency</code>.
     * <p>
     * With dependency's dependent is not current phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testAddDependencyFailureWithDependentNotThisPhase() {
        try {
            Phase dependency = new Phase(project, 24);
            Phase dependent = new Phase(project, 24);
            phase.addDependency(new Dependency(dependency, dependent, false, true, 0));
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>addDependency</code>.
     * <p>
     * With new dependency.
     * </p>
     * <p>
     * Should add the dependency.
     * </p>
     */
    public void testAddDependencyAccuracyWithNewDependency() {
        Phase dependency = new Phase(project, 24);
        phase.addDependency(new Dependency(dependency, phase, false, true, 0));
        Dependency[] dependencies = phase.getAllDependencies();
        assertEquals("Should add the dependency.", 1, dependencies.length);
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>addDependency</code>.
     * <p>
     * With dependency already exist.
     * </p>
     * <p>
     * Should do nothing.
     * </p>
     */
    public void testAddDependencyAccuracyWithDependencyAlreadyExist() {
        Phase dependencyPhase = new Phase(project, 24);
        Dependency dependency = new Dependency(dependencyPhase, phase, false, true, 0);
        phase.addDependency(dependency);
        Dependency[] dependencies = phase.getAllDependencies();
        assertEquals("Should add the dependency.", 1, dependencies.length);

        phase.addDependency(dependency);
        dependencies = phase.getAllDependencies();
        assertEquals("Should do nothing.", 1, dependencies.length);
    }

    /**
     * Failure test of <code>removeDependency</code>.
     * <p>
     * With null dependency.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testRemoveDependencyFailureWithNullDependency() {
        try {
            phase.removeDependency(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>removeDependency</code>.
     * <p>
     * With dependency's dependency not belong to current project.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testRemoveDependencyFailureWithDependencyNotBelong() {
        try {
            Project anotherProject = new Project(new Date(), new MyWorkdays());
            Phase dependency = new Phase(anotherProject, 24);
            phase.removeDependency(new Dependency(dependency, phase, false, true, 0));
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>removeDependency</code>.
     * <p>
     * With dependency's dependent is not current phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testRemoveDependencyFailureWithDependentNotThisPhase() {
        try {
            Phase dependency = new Phase(project, 24);
            Phase dependent = new Phase(project, 24);
            phase.removeDependency(new Dependency(dependency, dependent, false, true, 0));
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>removeDependency</code>.
     * <p>
     * With dependency not exist.
     * </p>
     * <p>
     * Should do nothing.
     * </p>
     */
    public void testRemoveDependencyAccuracyWithDependencyNotExist() {
        Phase dependencyPhase = new Phase(project, 24);
        Dependency dependency = new Dependency(dependencyPhase, phase, false, true, 0);

        project.setChanged(false);
        phase.removeDependency(dependency);
        assertFalse("Should do nothing.", project.isChanged());
    }

    /**
     * Accuracy test of <code>removeDependency</code>.
     * <p>
     * With dependency exist.
     * </p>
     * <p>
     * Should remove the dependency.
     * </p>
     */
    public void testRemoveDependencyAccuracyWithDependencyExist() {
        Phase dependencyPhase = new Phase(project, 24);
        Dependency dependency = new Dependency(dependencyPhase, phase, false, true, 0);
        phase.addDependency(dependency);
        Dependency[] dependencies = phase.getAllDependencies();
        assertEquals("Should add the dependency.", 1, dependencies.length);

        project.setChanged(false);
        phase.removeDependency(dependency);
        assertTrue("Should set the changed flag to true.", project.isChanged());
        dependencies = phase.getAllDependencies();
        assertEquals("Should remove the dependency.", 0, dependencies.length);
    }

    /**
     * Accuracy test of <code>clearDependencies</code>.
     * <p>
     * Should clear all dependencies.
     * </p>
     */
    public void testClearDependenciesAccuracy() {
        Phase dependencyPhase = new Phase(project, 24);
        Dependency dependency = new Dependency(dependencyPhase, phase, false, true, 0);
        phase.addDependency(dependency);
        Dependency[] dependencies = phase.getAllDependencies();
        assertEquals("Should add the dependency.", 1, dependencies.length);

        project.setChanged(false);
        phase.clearDependencies();
        assertTrue("Should set the changed flag to true.", project.isChanged());
        dependencies = phase.getAllDependencies();
        assertEquals("Should remove the dependency.", 0, dependencies.length);
    }

    /**
     * Accuracy test of <code>getAllDependencies</code>.
     * <p>
     * With no dependency exist.
     * </p>
     * <p>
     * Should return the empty dependencies array.
     * </p>
     */
    public void testGetAllDependenciesAccuracyWithDependencyNotExist() {
        Dependency[] dependencies = phase.getAllDependencies();
        assertEquals("Should return the empty dependencies array.", 0, dependencies.length);
    }

    /**
     * Accuracy test of <code>getAllDependencies</code>.
     * <p>
     * With dependency exist.
     * </p>
     * <p>
     * Should return the proper dependencies array.
     * </p>
     */
    public void testGetAllDependenciesAccuracyWithDependencyExist() {
        Phase dependencyPhase = new Phase(project, 24);
        Dependency dependency = new Dependency(dependencyPhase, phase, false, true, 0);
        phase.addDependency(dependency);
        Dependency[] dependencies = phase.getAllDependencies();
        assertEquals("Should return the proper dependencies array.", 1, dependencies.length);
        assertEquals("Should return the proper dependencies array.", dependency, dependencies[0]);
    }
}
