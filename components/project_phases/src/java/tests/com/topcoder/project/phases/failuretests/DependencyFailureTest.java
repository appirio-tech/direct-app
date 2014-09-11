/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.failuretests;

import java.util.Date;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>Dependency</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class DependencyFailureTest extends TestCase {
    /**
     * <p>
     * Represents the dependency phase for Dependency.
     * </p>
     */
    private Phase dependency;

    /**
     * <p>
     * Represents the dependent phase for Dependency.
     * </p>
     */
    private Phase dependent;

    /**
     * <p>
     * Represents the dependency start/end flag for Dependency.
     * </p>
     */
    private boolean dependencyStart = false;

    /**
     * <p>
     * Represents the dependent start/end flag for Dependency.
     * </p>
     */
    private boolean dependentStart = false;

    /**
     * <p>
     * Represents the lag time in milliseconds for Dependency.
     * </p>
     */
    private long lagTime = 1000;

    /**
     * <p>
     * An instance of <code>Dependency</code> to test.
     * </p>
     */
    private Dependency tester;

    /**
     * <p>
     * Create an instance of <code>Dependency</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        Project project = new Project(new Date(), new MockWorkDays());
        dependency = new Phase(project, 10000);
        dependent = new Phase(project, 20000);
        tester = new Dependency(dependency, dependent, dependencyStart, dependentStart, lagTime);
    }

    /**
     * <p>
     * Test ctor Dependency(Phase dependency, Phase dependent, boolean dependencyStart,
     * boolean dependentStart, long lagTime),
     * when dependency is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_DependencyIsNull() {
        try {
            new Dependency(null, dependent, dependencyStart, dependentStart, lagTime);
            fail("when dependency is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor Dependency(Phase dependency, Phase dependent, boolean dependencyStart,
     * boolean dependentStart, long lagTime),
     * when dependent is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_DependentIsNull() {
        try {
            new Dependency(dependency, null, dependencyStart, dependentStart, lagTime);
            fail("when dependent is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor Dependency(Phase dependency, Phase dependent, boolean dependencyStart,
     * boolean dependentStart, long lagTime),
     * when dependency and dependent are same, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_DependencyAndDependentAreSame() {
        try {
            new Dependency(dependency, dependency, dependencyStart, dependentStart, lagTime);
            fail("when dependency and dependent are same, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setDependency(Phase dependency),
     * when dependency is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDependency_DependencyIsNull() {
        try {
            tester.setDependency(null);
            fail("when dependency is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setDependency(Phase dependency),
     * when dependency are set to same instance with dependent, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDependency_DependencyAndDependentAreSame() {
        try {
            tester.setDependency(dependent);
            fail("when dependency are set to same instance with dependent, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setDependent(Phase dependent),
     * when dependent is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDependent_DependentIsNull() {
        try {
            tester.setDependency(null);
            fail("when dependent is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setDependent(Phase dependent),
     * when dependent are set to same instance with dependency, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDependent_DependentAndDependencyAreSame() {
        try {
            tester.setDependency(dependent);
            fail("when dependent are set to same instance with dependency, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
