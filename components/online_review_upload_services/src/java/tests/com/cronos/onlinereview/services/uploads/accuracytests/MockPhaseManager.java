/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

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
 * A mock PhaseManager implementation for testing.
 * 
 * @author kshatriyan
 * @version 1.0
 */
public class MockPhaseManager implements PhaseManager {
    /**
     * Not used.
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
     *             not thrown
     */
    public Project getPhases(long arg0) throws PhaseManagementException {
        return new MockProject(new Date(), new DefaultWorkdays());
    }

    /**
     * Not used.
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
     * Not used.
     * 
     * @return always null
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        return null;
    }

    /**
     * Not used.
     * 
     * @return always null
     * @throws PhaseManagementException
     *             will not be thrown
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        return null;
    }

    /**
     * Not used.
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
     * Not used.
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
     * Not used.
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
     * Not used.
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
     * Not used.
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
     * Not used.
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
     * Not used.
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
     * Not used.
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
     * Not used.
     * 
     * @return always null
     */
    public PhaseHandler[] getAllHandlers() {
        return null;
    }

    /**
     * Not used.
     * 
     * @param arg0
     *            phase handler
     * @return always null
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
        return null;
    }

    /**
     * Not used.
     * 
     * @param arg0
     *            phase validator
     */
    public void setPhaseValidator(PhaseValidator arg0) {
    }

    /**
     * Not used.
     * 
     * @return always null
     */
    public PhaseValidator getPhaseValidator() {
        return null;
    }
}

/**
 * A mock Project class for testing.
 * 
 * @author cyberjag
 * @version 1.0
 */
class MockProject extends Project {

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
        Phase[] phases = new Phase[4];
        for (int i = 0; i < phases.length; i++) {
            phases[i] = new Phase(this, i + 1);
            phases[i].setPhaseStatus(PhaseStatus.OPEN);
        }
        phases[0].setPhaseType(new PhaseType(1, "Submission"));
        phases[1].setPhaseType(new PhaseType(2, "Screening"));
        phases[2].setPhaseType(new PhaseType(3, "Review"));
        phases[3].setPhaseType(new PhaseType(4, "Final Fix"));
        return phases;
    }
}
