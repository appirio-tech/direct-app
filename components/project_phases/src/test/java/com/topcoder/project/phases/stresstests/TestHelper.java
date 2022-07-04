/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.stresstests;

import java.util.Date;
import java.util.GregorianCalendar;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

/**
 * test helper of stress tests.
 *
 * @author King_Bette
 */
public class TestHelper {
    /**
     * <p>
     * Convenient method to retrieve a date with given year, month and day.
     * </p>
     *
     * @param year
     *            the year to specify.
     * @param month
     *            the month to specify.
     * @param day
     *            the day to specify.
     * @return the aggregated date.
     */
    public static Date getDate(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    /**
     * *
     * <p>
     * Build a project with linear dependency graph.
     * </p>
     * <p>
     * The graph is built in the shape that phase[i] dependends on phase[i - 1].
     * </p>
     *
     * @param count the number of phases.
     * @return a project with linear dependency graph.
     */
    public static Project getLinearDependencyGraphProject(int count) {
        Project project = new Project(getDate(2006, 7, 14), new MockWorkdays());
        Phase[] phases = new Phase[count];

        phases[0] = new Phase(project, 2 * 3600 * 1000);
        for (int i = 1; i < count; ++i) {
            phases[i] = new Phase(project, 2 * 3600 * 1000);
            phases[i].addDependency(new Dependency(phases[i - 1], phases[i], false, true, 0));
        }
        return project;
    }
    /**
     * <p>Build a project with binary tree dependency graph.</p>
     *
     * <p>The graph is built in the shape that phase[i] dependends on phase[(i + 1) / 2 - 1].</p>
     *
     * @param count the number of phases.
     * @return a project with binary tree dependency graph.
     */
    public static Project getBinaryTreeDependencyGraphProject(int count) {
        Project project = new Project(getDate(2006, 7, 14), new MockWorkdays());
        Phase[] phases = new Phase[count];

        phases[0] = new Phase(project, 2 * 3600 * 1000);
        for (int i = 1; i < count; ++i) {
            phases[i] = new Phase(project,  2 * 3600 * 1000);
            phases[i].addDependency(new Dependency(phases[(i + 1) / 2 -1], phases[i], false, true, 0));
        }
        return project;
    }
}
