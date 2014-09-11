/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.failuretests;

import java.util.Date;

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

/**
 * A mock PhaseManager implementation for testing purpose.
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
     * A flag to indicate whether to throw error.
     */
    private static boolean throwError = false;

    /**
     * Not implemented.
     *
     * @param arg0
     *            project
     * @param arg1
     *            description
     * @throws PhaseManagementException
     *             not thrown
     */
    public void updatePhases(Project arg0, String arg1) throws PhaseManagementException {
    }

    /**
     * A mock implementation. Will throw exception if is throwError is true.
     *
     * @param arg0
     *            id
     * @return project or null based on state
     * @throws PhaseManagementException
     *             if throwError is set
     */
    public Project getPhases(long arg0) throws PhaseManagementException {
        if (isThrowError()) {
            throw new PhaseManagementException("Mock");
        }
        if (getState() == 0) {
            return new MockProject(new Date(), new DefaultWorkdays());
        }
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            project id
     * @return always null
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public Project[] getPhases(long[] arg0) throws PhaseManagementException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            phase
     * @return always false
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public boolean canStart(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            phase
     * @param arg1
     *            desc
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public void start(Phase arg0, String arg1) throws PhaseManagementException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            phase
     * @return always false
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public boolean canEnd(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            phase
     * @param arg1
     *            desc
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public void end(Phase arg0, String arg1) throws PhaseManagementException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            phase
     * @return always false
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public boolean canCancel(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            phase
     * @param arg1
     *            desc
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public void cancel(Phase arg0, String arg1) throws PhaseManagementException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            project id
     * @param arg1
     *            phase type
     * @param arg2
     *            operation
     */
    public void registerHandler(PhaseHandler arg0, PhaseType arg1, PhaseOperationEnum arg2) {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            phase
     * @param arg1
     *            operation
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
     * @param arg0
     *            phase handler
     * @return always null
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            phase validator
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
     * @param state
     *            the state to set
     */
    static void setState(int state) {
        MockPhaseManager.state = state;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    static int getState() {
        return state;
    }

    /**
     * Sets the throwError.
     *
     * @param throwError
     *            the throwError to set
     */
    static void setThrowError(boolean throwError) {
        MockPhaseManager.throwError = throwError;
    }

    /**
     * Gets the throwError.
     *
     * @return the throwError
     */
    static boolean isThrowError() {
        return throwError;
    }

}

/**
 * A mock Project class for testing purpose.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
class MockProject extends Project {

    /**
     * A static state variable for the mock.
     */
    private static int state = 0;

    /**
     * Default constructor.
     *
     * @param arg0
     *            date
     * @param arg1
     *            workdays
     */
    public MockProject(Date arg0, Workdays arg1) {
        super(arg0, arg1);
    }

    /**
     * Gets the Phases based on the state.
     *
     * @return phases or an empty array
     */
    public Phase[] getAllPhases() {
        if (getState() == 2) {
            return new Phase[0];
        }
        Phase[] phases = new Phase[4];
        for (int i = 0; i < phases.length; i++) {
            phases[i] = new Phase(this, i + 1);
            if (getState() == 1) {
                phases[i].setPhaseStatus(PhaseStatus.CLOSED);
            } else {
                phases[i].setPhaseStatus(PhaseStatus.OPEN);
            }
        }
        phases[0].setPhaseType(new PhaseType(1, "Submission"));
        phases[2].setPhaseType(new PhaseType(3, "Review"));
        phases[3].setPhaseType(new PhaseType(4, "Final Fix"));
        return phases;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the state to set
     */
    static void setState(int state) {
        MockProject.state = state;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    static int getState() {
        return state;
    }
}
