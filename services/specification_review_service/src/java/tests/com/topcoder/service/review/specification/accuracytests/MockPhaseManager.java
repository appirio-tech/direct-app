/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhaseValidator;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A mock PhaseManager implementation for testing purpose. This class is borrowed from the
 * developer, since we don't need to reinvent the roll.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPhaseManager implements PhaseManager {
    /**
     * A static state variable for the mock.
     */
    private static int state = 0;

    /**
     * The map to holds the phases.
     */
    private static Map<String, Project> phases = new HashMap<String, Project>();

    /**
     * A mock implementation.
     *
     * @param arg0 project
     * @param arg1 operator
     * @throws PhaseManagementException if arg1 equals to &quot;9999&quot;.
     */
    public void updatePhases(Project arg0, String arg1) throws PhaseManagementException {
        if ("9999".equals(arg1)) {
            throw new PhaseManagementException("mock");
        }
        phases.put(arg1, arg0);
    }

    /**
     * Gets the phases.
     *
     * @return phases map.
     */
    public static Map<String, Project> getPhases() {
        return phases;
    }

    /**
     * A mock implementation. Will throw exception if is throwError is true.
     *
     * @param arg0 id
     *
     * @return project or null based on state
     *
     * @throws PhaseManagementException if arg0 is 1000.
     */
    public Project getPhases(long arg0) throws PhaseManagementException {
        if (arg0 == 1000) {
            throw new PhaseManagementException("Mock");
        }

        if (getState() == 0) {
            return new MockProject(new Date(), new DefaultWorkdays(), arg0);
        }

        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0 project id
     *
     * @return always null
     */
    public Project[] getPhases(long[] arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     */
    public PhaseType[] getAllPhaseTypes() {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     */
    public PhaseStatus[] getAllPhaseStatuses() {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0 phase
     *
     * @return always false
     */
    public boolean canStart(Phase arg0) {
        return false;
    }

    /**
     * Not implemented.
     *
     * @param arg0 phase
     * @param arg1 desc
     */
    public void start(Phase arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 phase
     *
     * @return always false
     */
    public boolean canEnd(Phase arg0) {
        return false;
    }

    /**
     * Not implemented.
     *
     * @param arg0 phase
     * @param arg1 desc
     */
    public void end(Phase arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 phase
     *
     * @return always false
     */
    public boolean canCancel(Phase arg0) {
        return false;
    }

    /**
     * Not implemented.
     *
     * @param arg0 phase
     * @param arg1 desc
     */
    public void cancel(Phase arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 project id
     * @param arg1 phase type
     * @param arg2 operation
     */
    public void registerHandler(PhaseHandler arg0, PhaseType arg1, PhaseOperationEnum arg2) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 phase
     * @param arg1 operation
     *
     * @return always null
     */
    public PhaseHandler unregisterHandler(PhaseType arg0, PhaseOperationEnum arg1) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     */
    public PhaseHandler[] getAllHandlers() {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0 phase handler
     *
     * @return always null
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0 phase validator
     */
    public void setPhaseValidator(PhaseValidator arg0) {
    }

    /**
     * Not implemented.
     *
     * @return always null
     */
    public PhaseValidator getPhaseValidator() {
        return null;
    }

    /**
     * Sets the state.
     *
     * @param state the state to set
     */
    public static void setState(int state) {
        MockPhaseManager.state = state;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public static int getState() {
        return state;
    }

    /**
     * A mock Project class for testing purpose.
     *
     * @author TCDEVELOPER
     * @version 1.0
     */
    private class MockProject extends Project {
        /**
         * Generated serial version UID.
         */
        private static final long serialVersionUID = 4162736949773716674L;

        /**
         * A static state variable for the mock.
         */
        private final long state;

        /**
         * Default constructor.
         *
         * @param arg0 date
         * @param arg1 workdays
         * @param state the flag to set the phase status.
         */
        public MockProject(Date arg0, Workdays arg1, long state) {
            super(arg0, arg1);
            this.state = state;
        }

        /**
         * Gets the Phases based on the state.
         *
         * @return phases or an empty array
         */
        public Phase[] getAllPhases() {
            Phase[] result = new Phase[5];

            for (int i = 0; i < result.length; i++) {
                result[i] = new Phase(this, i + 1);
                result[i].setPhaseStatus(PhaseStatus.CLOSED);
            }

            result[0].setPhaseType(new PhaseType(1, "Submission"));

            result[1].setPhaseType(new PhaseType(2, "Review"));
            result[2].setPhaseType(new PhaseType(3, "Final Fix"));

            result[3].setPhaseType(new PhaseType(4, "Specification Review"));

            if (state == 2) {
                result[3].setPhaseStatus(PhaseStatus.OPEN);
            }

            if (state == 5) {
                result[4].setPhaseType(new PhaseType(5, "Appeal"));
            } else {
                result[4].setPhaseType(new PhaseType(5, "Specification Submission"));
            }

            if (state == 1) {
                result[4].setPhaseStatus(PhaseStatus.OPEN);
            }

            if (state == 3) {
                result[0].setPhaseStatus(PhaseStatus.OPEN);
            }

            return result;
        }
    }
}
