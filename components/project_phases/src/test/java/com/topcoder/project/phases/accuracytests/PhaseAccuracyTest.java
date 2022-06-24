/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysUnitOfTime;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>Phase</code> class.
 *
 * @author mayi
 * @version 2.0
 */
public class PhaseAccuracyTest extends TestCase {
    /**
     * Represents the number of milliseconds in one minute.
     */
    private static final int MIN_MS = 60000;

    /**
     * A <code>Phase</code> instance to test against.
     */
    private Phase phase = null;

    /**
     * The Workdays instance required by Project class.
     */
    private Workdays workdays = null;

    /**
     * The Project instance required by Phase class.
     */
    private Project project = null;

    /**
     * The Phase instances used for accuracy test.
     * <p>These phases are all depend on this phase.</p>
     */
    private Phase[] phases = null;

    /**
     * The dependencies between <code>phases</code> and <code>phase</code>.
     */
    private Dependency[] dependencies = null;

    /**
     * Represents the startDate of the project.
     */
    private Date startDate = null;

    /**
     * Setup the environment.
     */
    protected void setUp() {
        this.startDate = new Date();

        //create the Workdays instance required by Project class
        this.workdays = new AccuracyTestWorkdays();

        //create the Project instance required by Phase class
        this.project = new Project(startDate, this.workdays);

        phase = new Phase(project, MIN_MS);

        this.phases = new Phase[3];
        this.dependencies = new Dependency[3];
        for (int i = 0; i < 3; i++) {
            this.phases[i] = new Phase(this.project, (i + 1) * MIN_MS);
            dependencies[i] = new Dependency(phases[i], phase, false, true, MIN_MS);
            this.phase.addDependency(dependencies[i]);
        }


    }

    /**
     * Test constructor.
     * <p>The parameters will be saved to corresponding fields.</p>
     */
    public void testConstructor() {
        phase = new Phase(project, 0);
        assertEquals("Invalid length.", 0, phase.getLength());
        assertEquals("Invlaid project.", project, phase.getProject());
    }

    /**
     * Test <code>getProject</code>.
     * <p>The value passed to constructor will be returned.</p>
     */
    public void testGetProject() {
        assertEquals("Cannot getProject.", project, phase.getProject());
    }

    /**
     * Test <code>getLength</code> after construction.
     * <p>The value passed to constructor will be returned.</p>
     */
    public void testGetLength() {
        assertEquals("Cannot getLength.", MIN_MS, phase.getLength());
    }

    /**
     * Test <code>setLength</code> with positive value.
     * <p>The value will be checked via getter</p>
     */
    public void testSetLength_Positive() {
        phase.setLength(12345L);
        assertEquals("Cannot setLength with positive value.", 12345L, phase.getLength());
    }

    /**
     * Test <code>setLength</code> with zero value.
     * <p>The value will be checked via getter</p>
     */
    public void testSetLength_Zero() {
        phase.setLength(0);
        assertEquals("Cannot setLength with zero value.", 0, phase.getLength());
    }

    /**
     * Test <code>getId</code> and <code>setId</code> with positive value.
     */
    public void testGetId_Positive() {
        phase.setId(1234567890L);
        assertEquals("Cannot set positive id.", 1234567890L, phase.getId());
    }

    /**
     * Test <code>getId</code> and <code>setId</code> with zero value.
     */
    public void testGetId_Zero() {
        phase.setId(0);
        assertEquals("Cannot set zero id.", 0, phase.getId());
    }

    /**
     * Test <code>getPhaseType</code> after construction.
     * <p>null should be returned.</p>
     */
    public void testGetPhaseType() {
        assertNull("Should return null.", phase.getPhaseType());
    }

    /**
     * Test <code>setPhaseType</code>.
     * <p>We will check it via <code>getPhaseType</code>.</p>
     */
    public void testSetPhaseType_Normal() {
        PhaseType phaseType = new PhaseType(1, "foo");
        phase.setPhaseType(phaseType);
        assertEquals("Cannot setPhaseType.", phaseType, phase.getPhaseType());
    }

    /**
     * Test <code>setPhaseType</code> with null.
     * <p>null is allowed.</p>
     */
    public void testSetPhaseType_Null() {
        phase.setPhaseType(new PhaseType(1, "foo"));
        phase.setPhaseType(null);
        assertNull("Cannot setPhaseType with null.", phase.getPhaseType());
    }

    /**
     * Test <code>getPhaseStatus</code> after construction.
     * <p>null should be returned.</p>
     */
    public void testGetPhaseStatus() {
        assertNull("Should return null.", phase.getPhaseStatus());
    }

    /**
     * Test <code>setPhaseStatus</code>.
     * <p>We will check it via <code>getPhaseStatus</code>.</p>
     */
    public void testSetPhaseStatus_Normal() {
        phase.setPhaseStatus(PhaseStatus.CLOSED);
        assertEquals("Cannot setPhaseStatus.", PhaseStatus.CLOSED, phase.getPhaseStatus());
    }

    /**
     * Test <code>setPhaseStatus</code> with null.
     * <p>null is allowed.</p>
     */
    public void testSetPhaseStatus_Null() {
        phase.setPhaseStatus(PhaseStatus.OPEN);
        phase.setPhaseStatus(null);
        assertNull("Cannot setPhaseStatus with null.", phase.getPhaseStatus());
    }

    /**
     * Test <code>getFixedStartDate</code> after construction.
     * <p>null should be returned.</p>
     */
    public void testGetFixedStartDate() {
        assertNull("Should return null.", phase.getFixedStartDate());
    }

    /**
     * Test <code>setFixedStartDate</code>.
     * <p>We will check it via <code>getFixedStartDate</code>.</p>
     */
    public void testSetFixedStartDate_Normal() {
        Date fixedDate = new Date();
        phase.setFixedStartDate(fixedDate);
        assertEquals("Cannot setFixedStartDate.", fixedDate, phase.getFixedStartDate());
    }

    /**
     * Test <code>setFixedStartDate</code> with null.
     * <p>null is allowed.</p>
     */
    public void testSetFixedStartDate_Null() {
        phase.setFixedStartDate(new Date());
        phase.setFixedStartDate(null);
        assertNull("Cannot setFixedStartDate with null.", phase.getFixedStartDate());
    }

    /**
     * Test <code>getScheduledStartDate</code> after construction.
     * <p>null should be returned.</p>
     */
    public void testGetScheduledStartDate() {
        assertNull("Should return null.", phase.getScheduledStartDate());
    }

    /**
     * Test <code>setScheduledStartDate</code>.
     * <p>We will check it via <code>getScheduledStartDate</code>.</p>
     */
    public void testSetScheduledStartDate_Normal() {
        Date date = new Date();
        phase.setScheduledEndDate(date);
        phase.setScheduledStartDate(date);
        assertEquals("Cannot setScheduledStartDate.", date, phase.getScheduledStartDate());
    }

    /**
     * Test <code>setScheduledStartDate</code> with null.
     * <p>null is allowed.</p>
     */
    public void testSetScheduledStartDate_Null() {
        Date date = new Date();
        phase.setScheduledStartDate(date);
        assertEquals("Cannot setScheduledStartDate.", date, phase.getScheduledStartDate());

        phase.setScheduledStartDate(null);
        assertNull("Cannot setScheduledStartDate with null.", phase.getScheduledStartDate());
    }

    /**
     * Test <code>getScheduledEndDate</code> after construction.
     * <p>null should be returned.</p>
     */
    public void testGetScheduledEndDate() {
        assertNull("Should return null.", phase.getScheduledEndDate());
    }

    /**
     * Test <code>setScheduledEndDate</code>.
     * <p>We will check it via <code>getScheduledEndDate</code>.</p>
     */
    public void testSetScheduledEndDate_Normal() {
        Date date = new Date();
        phase.setScheduledStartDate(date);
        phase.setScheduledEndDate(date);
        assertEquals("Cannot setScheduledEndDate.", date, phase.getScheduledEndDate());
    }

    /**
     * Test <code>setScheduledEndDate</code> with null.
     * <p>null is allowed.</p>
     */
    public void testSetScheduledEndDate_Null() {
        Date date = new Date();
        phase.setScheduledEndDate(date);
        assertEquals("Cannot setScheduledEndDate.", date, phase.getScheduledEndDate());

        phase.setScheduledEndDate(null);
        assertNull("Cannot setScheduledEndDate with null.", phase.getScheduledEndDate());
    }

    /**
     * Test <code>getActualStartDate</code> after construction.
     * <p>null should be returned.</p>
     */
    public void testGetActualStartDate() {
        assertNull("Should return null.", phase.getActualStartDate());
    }

    /**
     * Test <code>setActualStartDate</code>.
     * <p>We will check it via <code>getActualStartDate</code>.</p>
     */
    public void testSetActualStartDate_Normal() {
        Date date = new Date();
        phase.setActualEndDate(date);
        phase.setActualStartDate(date);
        assertEquals("Cannot setActualStartDate.", date, phase.getActualStartDate());
    }

    /**
     * Test <code>setActualStartDate</code> with null.
     * <p>null is allowed.</p>
     */
    public void testSetActualStartDate_Null() {
        Date date = new Date();
        phase.setActualStartDate(date);
        assertEquals("Cannot setActualStartDate.", date, phase.getActualStartDate());

        phase.setActualStartDate(null);
        assertNull("Cannot setActualStartDate with null.", phase.getActualStartDate());
    }

    /**
     * Test <code>getActualEndDate</code> after construction.
     * <p>null should be returned.</p>
     */
    public void testGetActualEndDate() {
        assertNull("Should return null.", phase.getActualEndDate());
    }

    /**
     * Test <code>setActualEndDate</code>.
     * <p>We will check it via <code>getActualEndDate</code>.</p>
     */
    public void testSetActualEndDate_Normal() {
        Date date = new Date();
        phase.setActualStartDate(date);
        phase.setActualEndDate(date);
        assertEquals("Cannot setActualEndDate.", date, phase.getActualEndDate());
    }

    /**
     * Test <code>setActualEndDate</code> with null.
     * <p>null is allowed.</p>
     */
    public void testSetActualEndDate_Null() {
        Date date = new Date();
        phase.setActualEndDate(date);
        assertEquals("Cannot setActualEndDate.", date, phase.getActualEndDate());

        phase.setActualEndDate(null);
        assertNull("Cannot setActualEndDate with null.", phase.getActualEndDate());
    }

    /**
     * Test <code>addDependency</code> with un-existing dependency.
     * <p>
     * A new dependency should be added.
     * </p>
     */
    public void testAddDependency_UnExisting() {
        Dependency dependency = new Dependency(phases[0], phase, true, false, 10);
        phase.addDependency(dependency);

        assertEquals("Cannot add un-existing dependency.",
                dependencies.length + 1, phase.getAllDependencies().length);
    }

    /**
     * Test <code>addDependency</code> with existing dependency.
     * <p>
     * It will be ignored.
     * </p>
     */
    public void testAddDependency_Existing() {
        phase.addDependency(dependencies[0]);

        assertEquals("Shouldn't add existing dependency.",
                dependencies.length, phase.getAllDependencies().length);
    }

    /**
     * Test <code>addDependency</code> with cycle.
     * <p>
     * CyclicDependencyException shouldn't be thrown.
     * </p>
     */
    public void testAddDependency_Cycle() {
        Dependency dependency = new Dependency(phase, phases[0], true, false, 10);
        phases[0].addDependency(dependency);
        assertEquals("Dependency should be added even with a cycle.",
                1, phases[0].getAllDependencies().length);
    }

    /**
     * Test <code>removeDependency</code> with un-existing dependency.
     * <p>Nothing should happen.</p>
     */
    public void testRemoveDependency_UnExisting() {
        Dependency dependency = new Dependency(phases[0], phase, true, false, 10);
        phase.removeDependency(dependency);
        assertEquals("Shouldn't remove un-existing dependency.",
                phases.length, phase.getAllDependencies().length);
    }

    /**
     * Test <code>removeDependency</code> with existing dependency.
     * <p>The dependency should be removed.</p>
     */
    public void testRemoveDependency_Existing() {
        phase.removeDependency(dependencies[1]);
        assertEquals("Failed to remove existing dependency.",
                phases.length - 1, phase.getAllDependencies().length);
    }

    /**
     * Test <code>clearDependencies</code>.
     * <p>All the dependencies should be removed.</p>
     */
    public void testClearDependencies() {
        phase.clearDependencies();
        assertEquals("Failed to clearDependencies.",
                0, phase.getAllDependencies().length);
    }

    /**
     * Test <code>getAllDependencies</code> after construction.
     * <p>An emtpy array should be returned.</p>
     */
    public void testGetAllDependencies_Empty() {
        phase = new Phase(project, 100);
        assertEquals("Should return empty array.", 0, phase.getAllDependencies().length);
    }

    /**
     * Test <code>getAllDependencies</code> in normal condition.
     * <p>An array of all dependencies should be returned.</p>
     */
    public void testGetAllDependencies_Normal() {
        List allDependencies = Arrays.asList(phase.getAllDependencies());
        assertEquals("Should return all dependencies.",
                dependencies.length, allDependencies.size());

        for (int i = 0; i < dependencies.length; ++i) {
            assertTrue("Failed to return dependency:" + i,
                    allDependencies.indexOf(dependencies[i]) >= 0);
        }
    }

    /**
     * Test <code>calcEndDate</code> if it is an initial phase.
     * <p>The end date will be project start date adding phase length.</p>
     */
    public void testCalcEndDate1() {
        phase = new Phase(project, 1234567L);
        Date expected = workdays.add(
                project.getStartDate(), WorkdaysUnitOfTime.MINUTES, (int) phase.getLength() / MIN_MS);
        assertEquals("Failed to calcEndDate.", expected, phase.calcEndDate());
    }

    /**
     * Test <code>calcEndDate</code> if fixedStartDate is defined.
     * <p>The fixed end date will be returned.</p>
     */
    public void testCalcEndDate2() {
        Date date = new Date(new Date().getTime() + 100 * MIN_MS);
        phase.setFixedStartDate(date);
        Date expected = workdays.add(
                date, WorkdaysUnitOfTime.MINUTES, (int) phase.getLength() / MIN_MS);
        assertEquals("Failed to calcEndDate.", expected, phase.calcEndDate());
    }

    /**
     * Test <code>calcEndDate</code> if its end time depends other phase's start time.
     */
    public void testCalcEndDate3() {
        Date date = new Date(new Date().getTime() + MIN_MS * 10);
        phases[0].setFixedStartDate(date);
        phase = new Phase(project, 10 * MIN_MS);
        phase.addDependency(new Dependency(
                phases[0], phase, true, false, 100 * MIN_MS));

        Date expected = workdays.add(date, WorkdaysUnitOfTime.MINUTES, 100);
        assertEquals("Failed to calcEndDate.", expected, phase.calcEndDate());
    }

    /**
     * Test <code>calcEndDate</code> if its end time depends other phase's end time.
     */
    public void testCalcEndDate4() {
        Date date = new Date(new Date().getTime() + MIN_MS * 10);
        phases[0].setFixedStartDate(date);
        phase = new Phase(project, 10 * MIN_MS);
        phase.addDependency(new Dependency(
                phases[0], phase, false, false, 100 * MIN_MS));

        Date expected = workdays.add(date, WorkdaysUnitOfTime.MINUTES,
                100 + (int) (phases[0].getLength() / MIN_MS));
        assertEquals("Failed to calcEndDate.", expected, phase.calcEndDate());
    }

    /**
     * Test <code>calcEndDate</code> if it is an initial phase.
     * <p>The end date will be project start date.</p>
     */
    public void testCalcStartDate1() {
        Date expected = project.getStartDate();
        assertEquals("Failed to calcStartDate.", expected, phases[0].calcStartDate());
    }

    /**
     * Test <code>calcStartDate</code> if fixedStartDate is defined.
     * <p>The fixed start date will be returned.</p>
     */
    public void testCalcStartDate2() {
        Date date = new Date(new Date().getTime() + 77 * MIN_MS);
        phase.setFixedStartDate(date);
        assertEquals("Failed to calcStartDate.", date, phase.calcStartDate());
    }


    /**
     * Test <code>calcStartDate</code> if its startTime depends other phase's start time.
     */
    public void testCalcStartDate3() {
        Date date = new Date(new Date().getTime() + MIN_MS * 100);
        phases[0].setFixedStartDate(date);
        phase = new Phase(project, 10 * MIN_MS);
        phase.addDependency(new Dependency(
                phases[0], phase, true, true, 15 * MIN_MS));

        Date expected = workdays.add(date, WorkdaysUnitOfTime.MINUTES, 15);
        assertEquals("Failed to calcStartDate.", expected, phase.calcStartDate());
    }

    /**
     * Test <code>calcStartDate</code> if its startTime depends other phase's end time.
     */
    public void testCalcStartDate4() {
        Date date = new Date(new Date().getTime() + MIN_MS * 100);
        phases[0].setFixedStartDate(date);
        phase = new Phase(project, 10 * MIN_MS);
        phase.addDependency(new Dependency(
                phases[0], phase, false, true, 19 * MIN_MS));

        Date expected = workdays.add(date, WorkdaysUnitOfTime.MINUTES,
                19 + (int) (phases[0].getLength() / MIN_MS));
        assertEquals("Failed to calcStartDate.", expected, phase.calcStartDate());
    }
}
