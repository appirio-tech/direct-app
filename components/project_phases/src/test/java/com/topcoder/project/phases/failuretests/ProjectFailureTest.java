/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.failuretests;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>Project</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class ProjectFailureTest extends TestCase {
    /**
     * <p>
     * The start date of project.
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * The work days o fthe project.
     * </p>
     */
    private Workdays workdays;

    /**
     * <p>
     * A project for test.
     * </p>
     */
    private Project tester;

    /**
     * <p>
     * Prepare for each test.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2006, 6, 10);
        startDate = calendar.getTime();
        workdays = new MockWorkDays();
        tester = new Project(startDate, workdays);
    }

    /**
     * <p>
     * Test ctor Project(Date startDate, Workdays workdays),
     * when startDate is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_StartDateIsNull() {
        try {
            new Project(null, workdays);
            fail("when startDate is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor Project(Date startDate, Workdays workdays),
     * when workdays is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_WorkdaysIsNull() {
        try {
            new Project(startDate, null);
            fail("when workdays is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addPhase(Phase phase),
     * when phase is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testAddPhase_PhaseIsNull() {
        try {
            tester.addPhase(null);
            fail("when phase is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addPhase(Phase phase),
     * when phase is not of this project, IllegalArgumentException is expected.
     * </p>
     */
    public void testAddPhase_PhaseIsNotOfProject() {
        Project another = new Project(new Date(), workdays);
        Phase phase = new Phase(another, 1000);
        try {
            tester.addPhase(phase);
            fail("when phase is not of this project, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test removePhase(Phase phase),
     * when phase is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testRemovePhase_PhaseIsNull() {
        try {
            tester.removePhase(null);
            fail("when phase is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test removePhase(Phase phase),
     * when phase is not of this project, IllegalArgumentException is expected.
     * </p>
     */
    public void testRemovePhase_PhaseIsNotOfProject() {
        Project another = new Project(new Date(), workdays);
        Phase phase = new Phase(another, 1000);
        try {
            tester.removePhase(phase);
            fail("when phase is not of this project, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test removePhase(Phase phase),
     * when phase is not existed, IllegalArgumentException is expected.
     * </p>
     */
    public void testRemovePhase_PhaseNotExisted() {
        Phase phase = new Phase(tester, 1000);
        tester.removePhase(phase);
        try {
            tester.removePhase(phase);
            fail("when phase is not existed, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setStartDate(Date startDate),
     * when startDate is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetStartDate_StartDateIsNull() {
        try {
            tester.setStartDate(null);
            fail("when startDate is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test getAllPhases(Comparator comparator),
     * when comparator is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testGetAllPhases_ComparatorIsNull() {
        try {
            tester.getAllPhases(null);
            fail("when comparator is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test getAllPhases(Comparator comparator),
     * when the comparator cannot compare the phases, ClassCastException is expected.
     * </p>
     */
    public void testGetAllPhases_ComparatorTypeError() {
        new Phase(tester, 1000);
        new Phase(tester, 2000);
        try {
            tester.getAllPhases(new Comparator() {
                public int compare(Object arg0, Object arg1) {
                    throw new ClassCastException();
                }
            });
            fail("when the comparator cannot compare the phases, ClassCastException is expected.");
        } catch (ClassCastException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setId(long id),
     * when id is negative, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetId_IdIsNegative() {
        try {
            tester.setId(-1);
            fail("when id is negative, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
