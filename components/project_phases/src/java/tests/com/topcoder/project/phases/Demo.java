/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Demonstration for Project Phases Component 2.0.
 *
 * @author littlebull
 * @version 2.0
 */
public class Demo extends TestCase {
    /**
     * This method uses a simplified TopCoder Component Development project to demonstrate the usage of the Project
     * Phases Component.
     */
    public void testDemoTCProject() {
        // 1) Create a simplified TC project with submission, review and fix phases
        Date projectStartDate = new Date();
        Project project = new Project(projectStartDate, new MyWorkdays());
        Phase submission = new Phase(project, 7 * 24 * 3600 * 1000);
        Phase review = new Phase(project, 7 * 24 * 3600 * 1000);

        // 2) Assign various attributes to the project and phases
        project.clearAttributes();
        project.setAttribute("Project Name", "Project Phases Version 2.0");
        project.setAttribute("Project Manager", "PM");
        review.setAttribute("Initial Submissions", new Integer(8));

        // 3) Build up the dependency graph.
        // review phase will start immediately after submission phase ends
        review.addDependency(new Dependency(submission, review, false, true, 0));

        // 4) Set the scheduled start and end date of review phase
        review.setScheduledStartDate(new Date(projectStartDate.getTime() + 7 * 24 * 3600 * 1000));
        review.setScheduledEndDate(new Date(projectStartDate.getTime() + 14 * 24 * 3600 * 1000));

        // 5) Submission end date is changed! PM decides to give review phase an early start
        submission.setActualEndDate(new Date(projectStartDate.getTime() + 6 * 24 * 3600 * 1000));
        review.setLength(review.getLength() + 24 * 3600 * 1000);

        // 6) Some submissions drop out after review.
        review.setAttribute("Passing Submissions", new Integer(
                ((Integer) review.getAttribute("Initial Submissions")).intValue() - 1));

        // 7) Sorry, fix phase is forgotten. Anyway we can fix.
        Phase fix = new Phase(project, 7 * 24 * 3600 * 1000);
        fix.addDependency(new Dependency(review, fix, false, true, 0));

        // 8) PM wonders when the project could be done.
        Date projectEndDate = project.calcEndDate();

        // 9) With this probably we could demonstrate the timeline onto website in order.
        Phase[] projectPhases = project.getAllPhases();

        // 10) It is amazing but looks like this component does not need final fix :-)
        project.removePhase(fix);
    }

    /**
     * This method demonstrate the calculation of the component.
     * <p>
     * Here is the visual representation of the AOE (Activity-on-Edge) graph used.
     * </p>
     *
     *                  C(2)
     *               o ***** o
     *            *           *
     *         * A(7)     F(5) *
     *      *                   *    H(2)
     * SOURCE ****************** o ****** SINK
     *       *       E(10)             *
     *        *                      * G(8)
     *         * B(6)             *
     *          *     D(4)     *
     *           o ******** o
     */
    public void testDemoProjectPhasesCalculation() {
        Date projectStartDate = new Date(0);
        Project project = new Project(projectStartDate, new MyWorkdays());
        long day = 24 * 3600 * 1000;

        // 1) Building up the dependency graph.
        Phase phaseA = new Phase(project, 7 * day);
        Phase phaseB = new Phase(project, 6 * day);
        Phase phaseC = new Phase(project, 2 * day);
        Phase phaseD = new Phase(project, 4 * day);
        Phase phaseE = new Phase(project, 10 * day);
        Phase phaseF = new Phase(project, 5 * day);
        Phase phaseG = new Phase(project, 8 * day);
        Phase phaseH = new Phase(project, 2 * day);
        // phaseA -> phaseC
        phaseC.addDependency(new Dependency(phaseA, phaseC, false, true, 0));
        // phaseC -> phaseF
        phaseF.addDependency(new Dependency(phaseC, phaseF, false, true, 0));
        // phaseF -> phaseH
        phaseH.addDependency(new Dependency(phaseF, phaseH, false, true, 0));
        // phaseB -> phaseD
        phaseD.addDependency(new Dependency(phaseB, phaseD, false, true, 0));
        // phaseD -> phaseG
        phaseG.addDependency(new Dependency(phaseD, phaseG, false, true, 0));
        // phaseE -> phaseH
        phaseH.addDependency(new Dependency(phaseE, phaseH, false, true, 0));

        // 2) There are two path to project H - ACFH (ETA 14) and EH (ETA 10).
        assertEquals("Should calculate the correct start date.", 14 * day, phaseH.calcStartDate().getTime());

        // 3) The key path of the AOE network is BDG (18 days).
        assertEquals("Incorrect key path length.", 18 * day, project.calcEndDate().getTime());

        // 4) There are three initial phases A B E (connected with SOURCE).
        Phase[] initialPhases = project.getInitialPhases();
        assertEquals("Should return the proper initial phases.", 3, initialPhases.length);

        // 5) Edge F can be manified with 2 days. Otherwise it becomes the key path.
        phaseF.setLength((5 + 2) * day);
        assertEquals("Key path should not have been affected.", 18 * day, project.calcEndDate().getTime());
        phaseF.setLength((5 + 3) * day);
        assertEquals("Key path should have been affected.", 19 * day, project.calcEndDate().getTime());
        phaseF.setLength(5 * day);

        // 6) When we remove C we also remove F and H.
        project.removePhase(phaseC);
        Phase[] phases = project.getAllPhases();
        assertEquals("Return phases array contains incorrect number of phases.", 5, phases.length);
    }

    /**
     * This method demonstrate the new features of <code>Project</code> and <code>Phase</code>.
     */
    public void testDemoNewFeatures() {
        // Set project id
        Project project = new Project(new Date(), new MyWorkdays());
        project.setId(100);
        assertEquals("Should set the project id.", 100, project.getId());

        // Set phase id
        Phase phase = new Phase(project, 24 * 3600 * 1000);
        phase.setId(50);
        assertEquals("Should set the phase id.", 50, phase.getId());

        // Set phase type
        PhaseType reviewPhaseType =  new PhaseType(1, "review");
        phase.setPhaseType(reviewPhaseType);
        assertEquals("Should set the phase type.", reviewPhaseType, phase.getPhaseType());

        // Set phase type
        phase.setPhaseStatus(PhaseStatus.OPEN);
        assertEquals("Should set the phase status.", PhaseStatus.OPEN, phase.getPhaseStatus());
    }
}
