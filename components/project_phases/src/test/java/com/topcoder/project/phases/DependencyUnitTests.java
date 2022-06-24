/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test case for <code>PPhaseDateComparator</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class DependencyUnitTests extends TestCase {
    /**
     * Represents the dependentStart flag, it set to true.
     */
    private static final boolean DEPENDENT_START = true;

    /**
     * Represents the dependencyStart flag, it set to false.
     */
    private static final boolean DEPENDENCY_START = false;

    /**
     * Represents the lag time.
     */
    private static final long LAG_TIME = 10L;

    /**
     * Represents the project instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Project project;

    /**
     * Represents the dependency phase instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Phase dependencyPhase;

    /**
     * Represents the dependent phase instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Phase dependentPhase;

    /**
     * A <code>Dependency</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Dependency dependency;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        project = new Project(new Date(), new MyWorkdays());
        dependencyPhase = new Phase(project, 12 * 3600 * 1000);
        dependentPhase = new Phase(project, 24 * 3600 * 1000);

        // dependent phase starts after dependency phase ends
        dependency = new Dependency(dependencyPhase, dependentPhase, DEPENDENCY_START, DEPENDENT_START, LAG_TIME);
    }

    /**
     * Failure test of constructor.
     * <p>
     * With null dependency phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testConstructorFailureWithNullDependencyPhase() {
        try {
            new Dependency(null, dependentPhase, DEPENDENCY_START, DEPENDENT_START, LAG_TIME);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of constructor.
     * <p>
     * With null dependent phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testConstructorFailureWithNullDependentPhase() {
        try {
            new Dependency(dependencyPhase, null, DEPENDENCY_START, DEPENDENT_START, LAG_TIME);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of constructor.
     * <p>
     * With same instance of dependency instance and dependent phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testConstructorFailureWithSameInstance() {
        try {
            new Dependency(dependencyPhase, dependencyPhase, DEPENDENCY_START, DEPENDENT_START, LAG_TIME);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of constructor.
     * <p>
     * Should create the instance successfully.
     * </p>
     */
    public void testConstructorAccuracy() {
        assertNotNull("Should create the instance successfully.", dependency);
    }

    /**
     * Accuracy test of <code>getDependency</code>.
     * <p>
     * Should return the proper dependency phase.
     * </p>
     */
    public void testGetDependencyAccuracy() {
        assertEquals("Should return the proper dependency phase.", dependencyPhase, dependency.getDependency());
    }

    /**
     * Failure test of <code>setDependency</code>.
     * <p>
     * With null dependency phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetDependencyFailureWithNullDependencyPhase() {
        try {
            dependency.setDependency(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>setDependency</code>.
     * <p>
     * With same instance as dependent phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetDependencyFailureWithSameInstance() {
        try {
            dependency.setDependency(dependentPhase);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>setDependency</code>.
     * <p>
     * Should set the dependency phase.
     * </p>
     */
    public void testSetDependencyAccuracy() {
        project.setChanged(false);
        Phase phase = new Phase(project, 24);
        dependency.setDependency(phase);
        assertEquals("Should set the dependency phase.", phase, dependency.getDependency());
        assertTrue("Should set the changed flag of project to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>getDependent</code>.
     * <p>
     * Should return the proper dependent phase.
     * </p>
     */
    public void testGetDependentAccuracy() {
        assertEquals("Should return the proper dependent phase.", dependentPhase, dependency.getDependent());
    }

    /**
     * Failure test of <code>setDependent</code>.
     * <p>
     * With null dependent phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetDependentFailureWithNullDependentPhase() {
        try {
            dependency.setDependent(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>setDependent</code>.
     * <p>
     * With same instance as dependency phase.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetDependentFailureWithSameInstance() {
        try {
            dependency.setDependent(dependencyPhase);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>setDependent</code>.
     * <p>
     * Should set the dependent phase.
     * </p>
     */
    public void testSetDependentAccuracy() {
        project.setChanged(false);
        Phase phase = new Phase(project, 24);
        dependency.setDependent(phase);
        assertEquals("Should set the dependent phase.", phase, dependency.getDependent());
        assertTrue("Should set the changed flag of project to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>isDependencyStart</code>.
     * <p>
     * Should return the proper value.
     * </p>
     */
    public void testIsDependencyStartAccuracy() {
        assertEquals("Should return the proper value.", DEPENDENCY_START, dependency.isDependencyStart());
    }

    /**
     * Accuracy test of <code>setDependencyStart</code>.
     * <p>
     * With false value.
     * </p>
     * <p>
     * Should set the dependencyStart flag to false.
     * </p>
     */
    public void testSetDependencyStartAccuracyWithFalse() {
        project.setChanged(false);
        dependency.setDependencyStart(false);
        assertFalse("Should set the dependencyStart flag to false.", dependency.isDependencyStart());
        assertTrue("Should set the changed flag of project to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setDependencyStart</code>.
     * <p>
     * With true value.
     * </p>
     * <p>
     * Should set the dependencyStart flag to true.
     * </p>
     */
    public void testSetDependencyStartAccuracyWithTrue() {
        project.setChanged(false);
        dependency.setDependencyStart(true);
        assertTrue("Should set the dependencyStart flag to true.", dependency.isDependencyStart());
        assertTrue("Should set the changed flag of project to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>isDependentStart</code>.
     * <p>
     * Should return the proper value.
     * </p>
     */
    public void testIsDependentStartAccuracy() {
        assertEquals("Should return the proper value.", DEPENDENT_START, dependency.isDependentStart());
    }

    /**
     * Accuracy test of <code>setDependentStart</code>.
     * <p>
     * With false value.
     * </p>
     * <p>
     * Should set the dependentStart flag to false.
     * </p>
     */
    public void testSetDependentStartAccuracyWithFalse() {
        project.setChanged(false);
        dependency.setDependentStart(false);
        assertFalse("Should set the dependentStart flag to false.", dependency.isDependentStart());
        assertTrue("Should set the changed flag of project to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setDependentStart</code>.
     * <p>
     * With true value.
     * </p>
     * <p>
     * Should set the dependentStart flag to true.
     * </p>
     */
    public void testSetDependentStartAccuracyWithTrue() {
        project.setChanged(false);
        dependency.setDependentStart(true);
        assertTrue("Should set the dependentStart flag to true.", dependency.isDependentStart());
        assertTrue("Should set the changed flag of project to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>getLagTime</code>.
     * <p>
     * Should return the proper value.
     * </p>
     */
    public void testGetLagTimeAccuracy() {
        assertEquals("Should return the proper value.", LAG_TIME, dependency.getLagTime());
    }

    /**
     * Accuracy test of <code>setLagTime</code>.
     * <p>
     * With negative value.
     * </p>
     * <p>
     * Should set the lag time.
     * </p>
     */
    public void testSetLagTimeAccuracyWithNegative() {
        project.setChanged(false);
        dependency.setLagTime(-1);
        assertEquals("Should set the lag time.", -1, dependency.getLagTime());
        assertTrue("Should set the changed flag of project to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setLagTime</code>.
     * <p>
     * With positive value.
     * </p>
     * <p>
     * Should set the lag time.
     * </p>
     */
    public void testSetLagTimeAccuracyWithPositive() {
        project.setChanged(false);
        dependency.setLagTime(1);
        assertEquals("Should set the lag time.", 1, dependency.getLagTime());
        assertTrue("Should set the changed flag of project to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setLagTime</code>.
     * <p>
     * With zero.
     * </p>
     * <p>
     * Should set the lag time.
     * </p>
     */
    public void testSetLagTimeAccuracyWithZero() {
        project.setChanged(false);
        dependency.setLagTime(0);
        assertEquals("Should set the lag time.", 0, dependency.getLagTime());
        assertTrue("Should set the changed flag of project to true.", project.isChanged());
    }
}
