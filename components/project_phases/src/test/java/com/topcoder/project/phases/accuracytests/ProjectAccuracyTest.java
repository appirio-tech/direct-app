/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysUnitOfTime;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.PhaseDateComparator;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.Phase;

/**
 * Accuracy test for <code>Project</code> class.
 *
 * @author XuChuan, mayi
 * @version 2.0
 * @since 1.0
 */
public class ProjectAccuracyTest extends TestCase {
    /**
     * Represents the number of milliseconds in one minute.
     */
    private static final long MIN_MS = 60000;

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
     */
    private Phase[] phases = null;

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

        this.phases = new Phase[10];
        for (int i = 0; i < 10; i++) {
            this.phases[i] = new Phase(this.project, (i + 1) * MIN_MS);
        }
    }

    /**
     * Test Project#Project(Date startDate, Workdays workdays).
     * <p>All member variables should be initialized.</p>
     */
    public void testConstructor() {
        project = new Project(startDate, workdays);

        assertEquals("Failed to initialize workdays.", workdays, project.getWorkdays());
        assertEquals("Failed to initialize startDate.", startDate, project.getStartDate());
    }

    /**
     * Test <code>getId()</code>.
     */
    public void testGetId() {
        project.setId(1234567890L);
        assertEquals("Failed to getId.", 1234567890L, project.getId());
    }

    /**
     * Test <code>setId()</code> with negative value.
     * <p>The value will be set successfully.</p>
     */
    public void testSetId_Positive() {
        project.setId(1);
        assertEquals("failed to set positive id.", 1, project.getId());
    }

    /**
     * Test <code>setId()</code> with zero value.
     * <p>The value will be set successfully.</p>
     */
    public void testSetId_Zero() {
        project.setId(0);
        assertEquals("failed to set zero id.", 0, project.getId());
    }

    /**
     * Test <code>addPhase()</code> with a un-existing Phase.
     * <p>A new phase will be added.</p>
     */
    public void testAddPhase_UnExisting() {
        Phase phase = new Phase(project, 123);
        project.addPhase(phase);

        Phase[] tmpPhases = project.getAllPhases();
        assertEquals("Cannot add un-existing phase.",
                phases.length + 1, tmpPhases.length);
    }

    /**
     * Test <code>addPhase()</code> with an existing Phase.
     * <p>The phase will be ignored.</p>
     */
    public void testAddPhase_Existing() {
        project.addPhase(phases[2]);

        Phase[] tmpPhases = project.getAllPhases();
        assertEquals("Shouldn't add existing phase.", phases.length, tmpPhases.length);
    }

    /**
     * Test <code>Project#calcEndDate()</code>.
     * <p>It is a simple case. No dependency is defined.</p>
     */
    public void testCalcEndDate1() {
        Date expected = workdays.add(startDate, WorkdaysUnitOfTime.MINUTES, phases.length);

        assertEquals("Failed to calcEndDate.", expected, project.calcEndDate());
    }

    /**
     * Test <code>Prject#calcEndDate()</code>.
     * <p>In this case, no phase is added to this project. The startDate should be returned.</p>
     */
    public void testCalcEndDate2() {
        project = new Project(startDate, workdays);
        assertEquals("Failed to calcEndDate.", startDate, project.calcEndDate());
    }

    /**
     * Test <code>Prject#calcEndDate()</code>.
     * <p>In this case, the dependencies is like a chain.</p>
     */
    public void testCalcEndDate3() {
        long mins = phases[phases.length - 1].getLength() / MIN_MS;
        for (int i = 0; i < phases.length - 1; ++i) {
            Dependency dependency = new Dependency(phases[i], phases[i + 1], false, true, MIN_MS);
            phases[i + 1].addDependency(dependency);
            mins += phases[i].getLength() / MIN_MS;
            mins += 1;
        }

        Date expected = workdays.add(startDate, WorkdaysUnitOfTime.MINUTES, (int) mins);
        assertEquals("Failed to calcEndDate.", expected, project.calcEndDate());
    }

    /**
     * Test Project#clearPhases().
     * <p>All the phases should be removed.</p>
     */
    public void testClearPhases() {
        this.project.clearPhases();

        assertEquals("Cannot clear phases.", 0, project.getAllPhases().length);
    }

    /**
     * Test Project#getAllPhases().
     * <p>An array of phases will be returned. And they will be sorted with
     * <code>PhaseDateComparator</code>.</p>
     */
    public void testGetAllPhases1() {
        Phase[] allPhases = project.getAllPhases();

        assertEquals("Cannot getAllPhases", phases.length, allPhases.length);

        // check the order
        for (int i = 0; i < phases.length; ++i) {
            assertEquals("Order is invalid in position: " + i,
                    phases[i], allPhases[i]);
        }
    }

    /**
     * Test Project#getAllPhases().
     * <p>An array of phases will be returned. And they will be sorted with
     * <code>PhaseDateComparator</code>.</p>
     */
    public void testGetAllPhases2() {
        // add a dependency to swap phases[8] and phases[9]
        Dependency dependecy = new Dependency(phases[9], phases[8], false, true, MIN_MS);
        phases[8].addDependency(dependecy);

        // get all the phases.
        Phase[] allPhases = project.getAllPhases();
        assertEquals("Cannot getAllPhases", phases.length, allPhases.length);

        // check the order
        for (int i = 0; i < 8; ++i) {
            assertEquals("Order is invalid in position: " + i,
                    phases[i], allPhases[i]);
        }
        assertEquals("Element in position 8 is invalid.",
                phases[9], allPhases[8]);
        assertEquals("Element in position 9 is invalid.",
                phases[8], allPhases[9]);
    }

    /**
     * Test <code>getAllPhases(Comaparator)</code>.
     * <p>The given sort criteria will be used.</p>.
     */
    public void testGetAllPhases_Comparator() {
        Phase[] allPhases = project.getAllPhases(
                new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return new PhaseDateComparator().compare(o2, o1);
                    }
                });

        assertEquals("Cannot getAllPhases", phases.length, allPhases.length);

        // check the order
        for (int i = 0; i < phases.length; ++i) {
            assertEquals("Order is invalid in position: " + i,
                    phases[9 - i], allPhases[i]);
        }
    }

    /**
     * Test Project#removePhase(Phase phase).
     * <p>A final phase is removed and nothing else should happend.</p>
     */
    public void testRemovePhase1() {
        project.removePhase(this.phases[0]);
        assertEquals("Failed to remove final phase.",
                phases.length - 1, project.getAllPhases().length);
    }

    /**
     * Test Project#removePhase(Phase phase).
     * <p>An initial phase is removed and all phases which depends on it should be
     * removed.</p>
     */
    public void testRemovePhase2() {
        // add some dependency
        Dependency dependency = new Dependency(phases[5], phases[6], true, false, 1000);
        phases[6].addDependency(dependency);

        dependency = new Dependency(phases[6], phases[7], false, false, 1000);
        phases[7].addDependency(dependency);

        dependency = new Dependency(phases[6], phases[0], true, true, 1000);
        phases[0].addDependency(dependency);

        // remove the phase
        project.removePhase(phases[5]);
        assertEquals("Should removed all dependent phases.",
                phases.length - 4, project.getAllPhases().length);
    }

    /**
     * Test Project#setStartDate().
     * <p>All affected start date should be adjusted.</p>
     */
    public void testSetStartDate() {
        startDate = new Date();
        project.setStartDate(startDate);
        assertEquals("Failed to setStartDate.", startDate, project.getStartDate());
    }

    /**
     * Test <code>getStartDate()</code> after construction.
     * <p>The value passed to constructor should be returned.</p>
     */
    public void testGetStartDate() {
        assertEquals("failed to getStartDate", startDate, project.getStartDate());
    }

    /**
     * Test Project#getInitialPhases().
     * <p>All initial phases should be returned.</p>
     */
    public void testGetInitialPhases1() {
        //add dependencies
        for (int i = 0; i < 5; i++) {
            Dependency dependency = new Dependency(phases[i], phases[i + 5], true, false, 10);
            phases[i + 5].addDependency(dependency);
        }

        List initPhases = Arrays.asList(project.getInitialPhases());
        assertEquals("Failed to get all initialPhases", 5, initPhases.size());
        for (int i = 0; i < 5; i++) {
            assertTrue("test" + i, initPhases.contains(phases[i]));
        }
    }

    /**
     * Test Project#getInitialPhases().
     * <p>In this case all the phases are initial phase.</p>
     */
    public void testGetInitialPhases2() {
        List initPhases = Arrays.asList(project.getInitialPhases());
        assertEquals("Failed to get all initialPhases", 10, initPhases.size());
        for (int i = 0; i < 10; i++) {
            assertTrue("test" + i, initPhases.contains(phases[i]));
        }
    }

    /**
     * Test <code>getWorkdays</code>.
     * <p>The value passed to constructor should be returned.</p>
     */
    public void testGetWorkDays() {
        assertEquals("Failed to getWorkdays.", workdays, project.getWorkdays());
    }

}