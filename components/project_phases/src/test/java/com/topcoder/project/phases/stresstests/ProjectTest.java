/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.stresstests;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

import junit.framework.TestCase;

/**
 * Stress test case of Project class.<br>
 * The test is performed against calculateEndDate, removePhase method.
 *
 * @author Administrator
 *
 */
public class ProjectTest extends TestCase {
    /**
     * Run calculateProjectDate 100 times.<br>
     * project is a linear dependency graph with 10 phases.
     */
    public void testCalculateEndDate1() {
        calculateEndDateWithLinearGraph(10, 100);
    }

    /**
     * Run calculateProjectDate 100 times.<br>
     * project is a linear dependency graph with 100 phases.
     */
    public void testCalculateEndDate2() {
        calculateEndDateWithLinearGraph(100, 100);
    }

    /**
     * Run calculateProjectDate 100 times.<br>
     * project is a binary tree dependency graph with 10 phases.
     */
    public void testCalculateEndDate3() {
        calculateEndDateWithLinearGraph(100, 10);
    }

    /**
     * Run calculateProjectDate 100 times.<br>
     * project is a binary tree dependency graph with 100 phases.
     */
    public void testCalculateEndDate4() {
        calculateEndDateWithLinearGraph(100, 100);
    }

    /**
     * Run removePhase 100 times.<br>
     * project is a linear dependency graph with 10 phases.
     *
     */
    public void testRemovePhase1() {
        removePhaseWithLinearGrahp(100, 10);
    }
    /**
     * Run removePhase 100 times.<br>
     * project is a linear dependency graph with 100 phases.
     *
     */
    public void testRemovePhase2() {
        removePhaseWithLinearGrahp(100, 100);
    }

    /**
     * Run removePhase 100 times.<br>
     * project is a binary tree dependency graph with 10 phases.
     *
     */
    public void testRemovePhase3() {
        removePhaseWithLinearGrahp(100, 10);
    }
    /**
     * Run removePhase 100 times.<br>
     * project is a binary tree dependency graph with 100 phases.
     *
     */
    public void testRemovePhase4() {
        removePhaseWithLinearGrahp(100, 100);
    }

    /**
     * Run calculateEndDate given times.<br>
     * project is a linear dependency graph with given phases.
     *
     * @param times
     *            times to run.
     * @param count
     *            the count of phases.
     */
    private void calculateEndDateWithLinearGraph(int times, int count) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Project project = TestHelper.getLinearDependencyGraphProject(count);
            project.calcEndDate();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("calculateEndDate with a linear dependency graph with " + count + " phases. " + times
            + " times takes " + (endTime - startTime) + "ms");
    }

    /**
     * Run calculateEndDate given times.<br>
     * project is a binary tree dependency graph with given phases.
     *
     * @param times
     *            times to run.
     * @param count
     *            the count of phases.
     */
    private void calculateEndDateWithBinaryTreeGraph(int times, int count) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Project project = TestHelper.getBinaryTreeDependencyGraphProject(count);
            project.calcEndDate();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("calculateEndDate with a binary tree dependency graph with " + count + " phases. "
            + times + " times takes " + (endTime - startTime) + "ms");
    }

    /**
     * Run removePhase given times.<br>
     * project is a linear dependency graph with given phases.
     *
     * @param times
     *            times to run.
     * @param count
     *            the count of phases.
     */
    private void removePhaseWithLinearGrahp(int times, int count) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Project project = TestHelper.getLinearDependencyGraphProject(count);
            Phase[] phases = project.getInitialPhases();
            assertEquals("length must be 1.", 1, phases.length);
            project.removePhase(phases[0]);
            assertEquals("length must be 0.", 0, project.getAllPhases().length);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("removePhase with a linear dependency graph with " + count + " phases. "
            + times + " times takes " + (endTime - startTime) + "ms");
    }
    /**
     * Run removePhase given times.<br>
     * project is a binary tree dependency graph with given phases.
     *
     * @param times
     *            times to run.
     * @param count
     *            the count of phases.
     */
    private void removePhaseWithBinaryTreeGrahp(int times, int count) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Project project = TestHelper.getBinaryTreeDependencyGraphProject(count);
            Phase[] phases = project.getInitialPhases();
            assertEquals("length must be 1.", 1, phases.length);
            project.removePhase(phases[0]);
            assertEquals("length must be 0.", 0, project.getAllPhases().length);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("removePhase with a binary tree dependency graph with " + count + " phases. "
            + times + " times takes " + (endTime - startTime) + "ms");
    }
}
