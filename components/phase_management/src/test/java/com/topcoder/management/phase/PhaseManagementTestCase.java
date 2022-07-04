/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Base class containing some helper members/methods for writing phase management test cases.
 *
 * @author RachaelLCook
 * @version 1.0
 */
public class PhaseManagementTestCase extends TestCase {
    /**
     * A sample phase type.
     */
    protected static final PhaseType PHASE_TYPE_ONE = new PhaseType(1, "one");

    /**
     * A sample project.
     */
    protected static final Project PROJECT =
        new Project(new Date(), new DefaultWorkdaysFactory().createWorkdaysInstance());

    /**
     * Another phase type.
     */
    static final PhaseType PHASE_TYPE_TWO = new PhaseType(2, "two");

    /**
     * Another sample project.
     */
    static final Project PROJECT_TWO = new Project(new Date(), new DefaultWorkdaysFactory().createWorkdaysInstance());

    /**
     * A sample phase.
     */
    static final Phase PHASE_ONE = new Phase(PROJECT, 1);

    /**
     * Another sample phase.
     */
    static final Phase PHASE_TWO = new Phase(PROJECT, 1);

    static {
        PHASE_ONE.setPhaseType(PHASE_TYPE_ONE);
        PHASE_ONE.setFixedStartDate(new Date(100));
        PHASE_TWO.setPhaseType(PHASE_TYPE_TWO);
        PHASE_TWO.setFixedStartDate(new Date(new Date().getTime() + 100000));
    }

    /**
     * A phase handler that always allows the operation to perform.
     */
    static final PhaseHandler ALWAYS_PERFORM = new PhaseHandler() {
            public boolean canPerform(Phase phase) { return true; }


            public void perform(Phase phase, String operator) { }
        };

    /**
     * A phase handler that never allows the operation to perform.
     */
    static final PhaseHandler NEVER_PERFORM = new PhaseHandler() {
            public boolean canPerform(Phase phase) { return false; }

            public void perform(Phase phase, String operator) { }
        };

    /**
     * Raises an exception if the two specified phase types are unequal.
     *
     * @param message message associated with the failure
     * @param one the first phase type
     * @param two second phase type
     */
    public static void assertEquals(String message, PhaseType one, PhaseType two) {
        assertEquals(message, one.getName(), two.getName());
        assertEquals(message, one.getId(), two.getId());
    }
}

