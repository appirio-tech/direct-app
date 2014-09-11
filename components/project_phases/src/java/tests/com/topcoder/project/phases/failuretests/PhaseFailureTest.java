/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.failuretests;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.topcoder.project.phases.CyclicDependencyException;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>Phase</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class PhaseFailureTest extends TestCase {
    /**
     * <p>
     * A project instanced owned the phase.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * An instance of Phase to test.
     * </p>
     */
    private Phase tester;

    /**
     * <p>
     * A start date.
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * An end date.
     * </p>
     */
    private Date endDate;

    /**
     * <p>
     * Prepare for each test case.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        startDate = new Date(1000);
        endDate = new Date(2000);
        project = new Project(new Date(), new MockWorkDays());
        tester = new Phase(project, 1000 * 3600 * 24);
    }

    /**
     * <p>
     * Test ctor Phase(Project project, long length),
     * when project is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_ProjectIsNull() {
        try {
            new Phase(null, 1000);
            fail("when project is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor Phase(Project project, long length),
     * when length is negative, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_LengthIsNegative() {
        try {
            new Phase(project, -1);
            fail("when length is negative, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addDependency(Dependency dependency),
     * when the dependency is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testAddDependency_DependencyIsNull() {
        try {
            tester.addDependency(null);
            fail("when the dependency is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addDependency(Dependency dependency),
     * when the dependency does not exist in the project, IllegalArgumentException is expected.
     * </p>
     */
    public void testAddDependency_DependencyNotExistedInProject() {
        Project another = new Project(new Date(), new MockWorkDays());
        Phase phase = new Phase(another, 100);
        Dependency dependency = new Dependency(phase, tester, false, false, 100);
        try {
            tester.addDependency(dependency);
            fail("when the dependency does not exist in the project, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addDependency(Dependency dependency),
     * when dependent is not this phase, IllegalArgumentException is expected.
     * </p>
     */
    public void testAddDependency_DependentNotCorrect() {
        Phase phase = new Phase(project, 100);
        Dependency dependency = new Dependency(tester, phase, false, false, 100);
        try {
            tester.addDependency(dependency);
            fail("when dependent is not this phase, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addDependency(Dependency dependency),
     * when dependency not belong to the project, IllegalArgumentException is expected.
     * </p>
     */
    public void testAddDependency_DependencyNotBelongToProject() {
        Phase phase = new Phase(project, 100);
        Dependency dependency = new Dependency(phase, tester, false, false, 100);
        project.removePhase(phase);
        try {
            tester.addDependency(dependency);
            fail("when dependency not belong to the project, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test removeDependency(Dependency dependency),
     * when the dependency is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testRemoveDependency_DependencyIsNull() {
        try {
            tester.removeDependency(null);
            fail("when the dependency is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test removeDependency(Dependency dependency),
     * when the dependency does not exist in the project, IllegalArgumentException is expected.
     * </p>
     */
    public void testRemoveDependency_DependencyNotExistedInProject() {
        Project another = new Project(new Date(), new MockWorkDays());
        Phase phase = new Phase(another, 100);
        Dependency dependency = new Dependency(phase, tester, false, false, 100);
        try {
            tester.removeDependency(dependency);
            fail("when the dependency is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test removeDependency(Dependency dependency),
     * when dependent is not this phase, IllegalArgumentException is expected.
     * </p>
     */
    public void testRemoveDependency_DependentNotCorrect() {
        Phase phase = new Phase(project, 100);
        Dependency dependency = new Dependency(tester, phase, false, false, 100);
        try {
            tester.removeDependency(dependency);
            fail("when dependent is not this phase, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setLength(long length),
     * when length is negative, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetLength_lengthIsNegative() {
        try {
            tester.setLength(-1);
            fail("when length is negative, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setId(long id),
     * when id is negative, IllegalArgumentException is expected.
     * </p>
     *
     */
    public void testSetId_IdIsNegative() {
        try {
            tester.setId(-1);
            fail("when id is negative, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setActualStartDate(Date actualStartDate),
     * when  <code>actualEndDate</code> and <code>actualStartDate</code> are not <code>null</code>,
     * but <code>actualEndDate</code> is before <code>actualStartDate</code>, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetActualStartDate_ActuralStartDateAfterActualEndDate() {
        tester.setActualEndDate(startDate);
        try {
            tester.setActualStartDate(endDate);
            fail("when actualEndDate and actualStartDate are not null, but actualEndDate is before actualStartDate," +
                " IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setActualEndDate(Date actualEndDate),
     * when if <code>actualEndDate</code> and <code>actualStartDate</code> are not <code>null</code>,
     * but <code>actualEndDate</code> is before <code>actualStartDate</code>., IllegalArgumentException is expected.
     * </p>
     */
    public void testSetActualEndDate_ActualEndDateBeforeActualStartDate() {
        tester.setActualStartDate(endDate);
        try {
            tester.setActualEndDate(startDate);
            fail("when actualEndDate and actualStartDate are not null, but actualEndDate is before actualStartDate," +
                " IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setScheduledEndDate(Date scheduledEndDate),
     * when <code>scheduledEndDate</code> and <code>scheduledStartDate</code> are not <code>null</code>,
     * but <code>scheduledEndDate</code> is before <code>scheduledStartDate</code>,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetScheduledEndDate_ScheduledEndDateBeforeScheduledStartDate() {
        tester.setScheduledStartDate(endDate);
        try {
            tester.setScheduledEndDate(startDate);
            fail("when scheduledEndDate and scheduledStartDate are not null, but scheduledEndDate is " +
                "before scheduledStartDate, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setScheduledStartDate(Date scheduledStartDate),
     * when <code>scheduledEndDate</code> and <code>scheduledStartDate</code> are not <code>null</code>,
     * but <code>scheduledEndDate</code> is before <code>scheduledStartDate</code>,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetScheduledStartDate_ScheduledStartDateAfterScheduledEndDate() {
        tester.setScheduledEndDate(startDate);
        try {
            tester.setScheduledStartDate(endDate);
            fail("when scheduledEndDate and scheduledStartDate are not null, but scheduledEndDate is " +
                "before scheduledStartDate, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test calcEndDate(Set visited, Map startDateCache, Map endDateCache),
     * when cyclic dependecny existed, CyclicDependencyException is expected.
     * </p>
     */
    public void testCalcEndDate_CyclicDependency() {
        createCyclicDependency();
        try {
            tester.calcEndDate();
            fail("when cyclic dependecny existed, CyclicDependencyException is expected.");
        } catch (CyclicDependencyException e) {
            // good
        }
    }

    /**
     * <p>
     * Test calcStartDate(Set visited, Map startDateCache, Map endDateCache),
     * when cyclic dependecny existed, CyclicDependencyException is expected.
     * </p>
     */
    public void testCalcStartDate_CyclicDependency() {
        createCyclicDependency();
        try {
            tester.calcStartDate();
            fail("when cyclic dependecny existed, CyclicDependencyException is expected.");
        } catch (CyclicDependencyException e) {
            // good
        }
    }

    /**
     * <p>
     * Create a cyclic dependency.
     * </p>
     */
    private void createCyclicDependency() {
        Phase phase1 = new Phase(this.project, 3 * 3600 * 1000);
        Phase phase2 = new Phase(this.project, 3 * 3600 * 1000);
        Dependency dependency1 = new Dependency(phase1, tester, false, true, 0);
        Dependency dependency2 = new Dependency(phase2, phase1, false, true, 0);
        Dependency dependency3 = new Dependency(tester, phase2, false, true,0);

        tester.addDependency(dependency1);
        phase1.addDependency(dependency2);
        phase2.addDependency(dependency3);
    }
}
