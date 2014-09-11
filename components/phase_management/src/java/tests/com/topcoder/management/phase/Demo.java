/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Demonstrates the usage of the phase management component. Adapted from the component specification.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class Demo extends TestCase {
    /**
     * A sample ID generator which simply returns a sequence of increasing <code>longs</code>.
     */
    private class DemoIdGenerator extends NullIdGenerator {
        /**
         * Returns the next <code>long</code> in the sequence.
         *
         * @return the next <code>long</code> in the sequence
         */
        public long getNextID() {
            return getNext();
        }
    };

    /**
     * A sample phase validator that does nothing.
     */
    private class DemoPhaseValidator implements PhaseValidator {
        /**
         * Does nothing.
         *
         * @param phase the phase to validate
         */
        public void validate(Phase phase)  {
        }
    }

    /**
     * A sample phase handler that lets all operations perform and has no special actions.
     */
    private class DemoPhaseHandler implements PhaseHandler {
        /**
         * Returns <code>true</code>.
         *
         * @param phase the phase on which an operation is being performed
         * @return <code>true</code>
         */
        public boolean canPerform(Phase phase) {
            return true;
        }

        /**
         * Does nothing.
         *
         * @param phase the phase on which an operation is being performed
         * @param operator the operator
         */
        public void perform(Phase phase, String operator) {
        }
    }

    /**
     * A sample phase persistence class that does nothing.
     */
    private class DemoPhasePersistence extends NullPhasePersistence {
        /**
         * Does nothing.
         *
         * @param phase the phase to update
         * @param operator the operator
         */
        public void updatePhase(Phase phase, String operator) {
        }

        /**
         * Returns <code>true</code>.
         *
         * @param phase the phase to test for being new
         * @return <code>true</code>
         */
        public boolean isNewPhase(Phase phase) {
            return true;
        }

        /**
         * Does nothing.
         *
         * @param phases the phases to update
         * @param operator the operator
         */
        public void updatePhases(Phase[] phases, String operator) {
        }
    }

    /**
     * Demonstrates the usage of the phase management component. Adapted from the component specification.
     *
     * @throws Exception if an unexpected exception occurs while executing the demo
     */
    public void testdemo() throws Exception {
        // set up the config manager
        ConfigManager.getInstance().add("config.xml");

        // create a manager using configuration
        PhaseManager manager = new DefaultPhaseManager("test.default");

        // set up a simple project with a single phase
        final Project project = new Project(new Date(), new DefaultWorkdaysFactory().createWorkdaysInstance());
        final PhaseType phaseTypeOne = new PhaseType(1, "one");
        final Phase phaseOne = new Phase(project, 1);
        phaseOne.setPhaseType(phaseTypeOne);
        phaseOne.setFixedStartDate(new Date());
        phaseOne.setPhaseStatus(PhaseStatus.SCHEDULED);
        project.addPhase(phaseOne);

        // create some of the pluggable components
        DemoIdGenerator idgen = new DemoIdGenerator();
        DemoPhaseValidator validator = new DemoPhaseValidator();
        DemoPhaseHandler handler = new DemoPhaseHandler();

        PhasePersistence persistence = new DemoPhasePersistence() {
                public PhaseType[] getAllPhaseTypes() {
                    return new PhaseType[] {phaseTypeOne};
                }

                public PhaseStatus[] getAllPhaseStatuses() {
                    return new PhaseStatus[] {phaseOne.getPhaseStatus()};
                }

                public Project getProjectPhases(long projectId) {
                    return project;
                }
            };

        // create manager programmatically
        manager = new DefaultPhaseManager(persistence, idgen);

        // set the validator
        manager.setPhaseValidator(validator);

        // register a phase handler for dealing with canStart()
        manager.registerHandler(handler, phaseTypeOne, PhaseOperationEnum.START);

        //
        // do some operations

        // start
        if (manager.canStart(phaseOne)) {
            manager.start(phaseOne, "ivern");
        }

        // cancel
        if (manager.canCancel(phaseOne)) {
            manager.cancel(phaseOne, "mess");
        }

        // end
        if (manager.canEnd(phaseOne)) {
            manager.end(phaseOne, "RachaelLCook");
        }

        // get all phase types
        PhaseType[] alltypes = manager.getAllPhaseTypes();

        // get all phase statuses
        PhaseStatus[] allstatuses = manager.getAllPhaseStatuses();

        // update the project
        manager.updatePhases(project, "ivern");
    }
}
