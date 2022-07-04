/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import java.util.Date;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>Dependency</code> class.
 *
 * @author mayi
 * @version 2.0
 */
public class DependencyAccuracyTest extends TestCase {
    /**
     * Represents the initial dependentStart value of dependencyObj.
     */
    private static final boolean DEPENDENT_START = true;

    /**
     * Represents the initial dependencyStart value of dependencyObj.
     */
    private static final boolean DEPENDENCY_START = false;

    /**
     * Represents the initial lagTime value of dependencyObj.
     */
    private static final long LAG_TIME = 1234567890L;

    /**
     * A <code>Dependency</code> instance to test against.
     */
    private Dependency dependencyObj = null;

    /**
     * Represents a <code>Phase</code> instance used as dependency.
     */
    private Phase dependencyPhase = null;

    /**
     * Represents a <code>Phase</code> instance used as dependent.
     */
    private Phase dependentPhase = null;

    /**
     * Represents a <code>Project</code> instance used to create phases.
     */
    private Project project = null;

    /**
     * Set up. Create all the instances which are used to test.
     */
    protected void setUp() {
        project = new Project(new Date(), new AccuracyTestWorkdays());
        dependentPhase = new Phase(project, 123);
        dependencyPhase = new Phase(project , 345);

        dependencyObj = new Dependency(dependencyPhase, dependentPhase,
                DEPENDENCY_START, DEPENDENT_START, LAG_TIME);
    }

    /**
     * Test constructor with following parameter.
     * (dependencyPhase, dependentPhase, false, true, 0)
     */
    public void testConstructor1() {
        dependencyObj = new Dependency(dependencyPhase, dependentPhase, false, true, 0);
        assertEquals("Failed to set dependencyPhase.",
                dependencyPhase, dependencyObj.getDependency());
        assertEquals("Failed to set dependentPhase.",
                dependentPhase, dependencyObj.getDependent());
        assertEquals("Failed to set dependencyStart.",
                false, dependencyObj.isDependencyStart());
        assertEquals("Failed to set dependentStart.",
                true, dependencyObj.isDependentStart());
        assertEquals("Failed to set lagTime.",
                0, dependencyObj.getLagTime());
    }


    /**
     * Test constructor with following parameter.
     * (dependentPhase, dependencyPhase, true, false, -1)
     */
    public void testConstructor2() {
        dependencyObj = new Dependency(dependentPhase, dependencyPhase, true, false, -1);
        assertEquals("Failed to set dependencyPhase.",
                dependentPhase, dependencyObj.getDependency());
        assertEquals("Failed to set dependentPhase.",
                dependencyPhase, dependencyObj.getDependent());
        assertEquals("Failed to set dependencyStart.",
                true, dependencyObj.isDependencyStart());
        assertEquals("Failed to set dependentStart.",
                false, dependencyObj.isDependentStart());
        assertEquals("Failed to set lagTime.",
                -1, dependencyObj.getLagTime());
    }

    /**
     * Test <code>setDependency</code> and <code>getDependency</code> with new value.
     * <p>The value should be set properly.</p>
     */
    public void testSetGetDependency_NewValue() {
        Phase phase = new Phase(project, 123);
        dependencyObj.setDependency(phase);

        assertEquals("Failed to set/get dependency.",
                phase, dependencyObj.getDependency());
    }

    /**
     * Test <code>setDependency</code> and <code>getDependency</code> with old value.
     * <p>The value should be set properly.</p>
     */
    public void testSetGetDependency_OldValue() {
        dependencyObj.setDependency(dependencyPhase);

        assertEquals("Failed to set/get dependency.",
                dependencyPhase, dependencyObj.getDependency());
    }

    /**
     * Test <code>getDependency</code> after construction.
     * <p>The value passed to constructor should be returned.</p>
     */
    public void testGetDependency() {
        assertEquals("Failed to get dependency.",
                dependencyPhase, dependencyObj.getDependency());
    }

    /**
     * Test <code>setDependent</code> and <code>getDependent</code> with new value.
     * <p>The value should be set properly.</p>
     */
    public void testSetGetDependent_NewValue() {
        Phase phase = new Phase(project, 123);
        dependencyObj.setDependent(phase);

        assertEquals("Failed to set/get Dependent.",
                phase, dependencyObj.getDependent());
    }

    /**
     * Test <code>setDependent</code> and <code>getDependent</code> with old value.
     * <p>The value should be set properly.</p>
     */
    public void testSetGetDependent_OldValue() {
        dependencyObj.setDependent(dependentPhase);

        assertEquals("Failed to set/get dependent.",
                dependentPhase, dependencyObj.getDependent());
    }

    /**
     * Test <code>getDependent</code> after construction.
     * <p>The value passed to constructor should be returned.</p>
     */
    public void testGetDependent() {
        assertEquals("Failed to get Dependent.",
                dependentPhase, dependencyObj.getDependent());
    }

    /**
     * Test <code>setDependencyStart</code> and <code>isDependencyStart</code>
     * with false.
     */
    public void testSetDependencyStart_False() {
        dependencyObj.setDependencyStart(false);
        assertEquals("failed to set/get DependencyStart.",
                false, dependencyObj.isDependencyStart());
    }

    /**
     * Test <code>setDependencyStart</code> and <code>isDependencyStart</code>
     * with true.
     */
    public void testSetDependencyStart_True() {
        dependencyObj.setDependencyStart(true);
        assertEquals("failed to set/get DependencyStart.",
                true, dependencyObj.isDependencyStart());
    }

    /**
     * Test <code>isDependencyStart</code> after construction.
     * <p>The value passed to constructor will be returned.</p>
     */
    public void testIsDependencyStart() {
        assertEquals("failed to get isDependencyStart.",
                DEPENDENCY_START, dependencyObj.isDependencyStart());
    }

    /**
     * Test <code>setDependentStart</code> and <code>isDependentStart</code>
     * with false.
     */
    public void testSetDependentStart_False() {
        dependencyObj.setDependentStart(false);
        assertEquals("failed to set/get DependentStart.",
                false, dependencyObj.isDependentStart());
    }

    /**
     * Test <code>setDependentStart</code> and <code>isDependentStart</code>
     * with true.
     */
    public void testSetDependentStart_True() {
        dependencyObj.setDependentStart(true);
        assertEquals("failed to set/get DependentStart.",
                true, dependencyObj.isDependentStart());
    }

    /**
     * Test <code>isDependentStart</code> after construction.
     * <p>The value passed to constructor will be returned.</p>
     */
    public void testIsDependentStart() {
        assertEquals("failed to get isDependentStart.",
                DEPENDENT_START, dependencyObj.isDependentStart());
    }

    /**
     * Test <code>setLagTime</code> and <code>getLagtime</code> with positive value.
     * <p>The value should be set properly.</p>
     */
    public void testSetGetLagTime_Positive() {
        dependencyObj.setLagTime(1);

        assertEquals("Failed to set/get LagTime.",
                1, dependencyObj.getLagTime());
    }

    /**
     * Test <code>setLagTime</code> and <code>getLagtime</code> with zero value.
     * <p>The value should be set properly.</p>
     */
    public void testSetGetLagTime_ZERO() {
        dependencyObj.setLagTime(0);

        assertEquals("Failed to set/get LagTime.",
                0, dependencyObj.getLagTime());
    }

    /**
     * Test <code>setLagTime</code> and <code>getLagtime</code> with negative value.
     * <p>The value should be set properly.</p>
     */
    public void testSetGetLagTime_Negative() {
        dependencyObj.setLagTime(-1);

        assertEquals("Failed to set/get LagTime.",
                -1, dependencyObj.getLagTime());
    }

    /**
     * Test <code>getLagTime</code> after construction.
     * <p>The value passed to constructor should be returned.</p>
     */
    public void testGetLagTime() {
        assertEquals("Failed to get Dependent.",
                LAG_TIME, dependencyObj.getLagTime());
    }

}
