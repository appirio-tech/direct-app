/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import junit.framework.TestCase;

import java.util.Calendar;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysUnitOfTime;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseDateComparator;

/**
 * Accuracy test for PhaseDateComparator class.
 *
 * @author XuChuan, mayi
 * @version 2.0
 * @since 1.0
 */
public class PhaseDateComparatorAccuracyTest extends TestCase {

    /**
     * Represents milliseconds in one minute.
     */
    private static final long MIN_MS = 60000;

    /**
     * The PhaseDateComparator instance used for accuracy test.
     */
    private PhaseDateComparator comp = null;

    /**
     * The Phase instances used for accuracy test.
     */
    private Phase[] phases = null;

    /**
     * Setup the environment.
     */
    protected void setUp() {
        this.comp = new PhaseDateComparator();

        //create a Calendar instance used to create different dates
        Calendar calendar = Calendar.getInstance();

        //create a Workdays instance required by Project class
        Workdays workdays = new AccuracyTestWorkdays();

        //create a Project instance required by Phase class
        Project prj = new Project(calendar.getTime(), workdays);

        this.phases = new Phase[4];
        this.phases[0] = new Phase(prj, 10 * MIN_MS);
        this.phases[1] = new Phase(prj, 20 * MIN_MS);
        this.phases[2] = new Phase(prj, 5 * MIN_MS);
        this.phases[3] = new Phase(prj, 5 * MIN_MS);
    }

    /**
     * Test PhaseDateComparator#compare(Object obj1, Object obj2) with different startDate. A negative integer should
     * be returned.
     */
    public void testCompare1() {
        phases[1].addDependency(new Dependency(
                phases[0], phases[1], true, true, 10 * MIN_MS));
        phases[2].addDependency(new Dependency(
                phases[0], phases[2], true, true, 15 * MIN_MS));

        assertTrue("test1", this.comp.compare(this.phases[0], this.phases[2]) < 0);
        assertTrue("test2", this.comp.compare(this.phases[1], this.phases[2]) < 0);
    }

    /**
     * Test PhaseDateComparator#compare(Object obj1, Object obj2) with different startDate. A positive integer should
     * be returned.
     */
    public void testCompare2() {
        phases[1].addDependency(new Dependency(
                phases[0], phases[1], true, true, 10 * MIN_MS));
        phases[2].addDependency(new Dependency(
                phases[0], phases[2], true, true, 15 * MIN_MS));

        assertTrue("test1", this.comp.compare(this.phases[2], this.phases[0]) > 0);
        assertTrue("test2", this.comp.compare(this.phases[2], this.phases[1]) > 0);
    }

    /**
     * Test PhaseDateComparator#compare(Object obj1, Object obj2) with same startDate but different endDate. A negative
     * integer should be returned.
     */
    public void testCompare3() {
        com.topcoder.date.workdays.Workdays w = new AccuracyTestWorkdays();
        w.add(this.phases[0].calcStartDate(), WorkdaysUnitOfTime.HOURS, (int) phases[0].getLength());
        w.add(this.phases[1].calcStartDate(), WorkdaysUnitOfTime.HOURS, (int) phases[1].getLength());
        assertTrue("test1", this.comp.compare(this.phases[0], this.phases[1]) < 0);
    }

    /**
     * Test PhaseDateComparator#compare(Object obj1, Object obj2) with same startDate but different endDate. A positive
     * integer should be returned.
     */
    public void testCompare4() {
        assertTrue("test1", this.comp.compare(this.phases[1], this.phases[0]) > 0);
    }

    /**
     * Test PhaseDateComparator#compare(Object obj1, Object obj2) with same startDate and same endDate. Zero should be
     * returned.
     */
    public void testCompare5() {
        this.comp.compare(this.phases[2], this.phases[3]);
    }
}