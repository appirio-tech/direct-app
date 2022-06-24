/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

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
 * Mock for testing!
 *
 * @author onsky
 * @version 1.0
  */
public class MockPhaseManager implements PhaseManager {
    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public boolean canCancel(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public boolean canEnd(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public boolean canStart(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     * @param arg1 Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public void cancel(Phase arg0, String arg1) throws PhaseManagementException {
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     * @param arg1 Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public void end(Phase arg0, String arg1) throws PhaseManagementException {
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     */
    public PhaseHandler[] getAllHandlers() {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     */
    public PhaseValidator getPhaseValidator() {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public Project getPhases(long arg0) throws PhaseManagementException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public Project[] getPhases(long[] arg0) throws PhaseManagementException {
        return new Project[] {  };
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     * @param arg1 Mock for testing!
     * @param arg2 Mock for testing!
     */
    public void registerHandler(PhaseHandler arg0, PhaseType arg1, PhaseOperationEnum arg2) {
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     */
    public void setPhaseValidator(PhaseValidator arg0) {
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     * @param arg1 Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public void start(Phase arg0, String arg1) throws PhaseManagementException {
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     * @param arg1 Mock for testing!
     *
     * @return Mock for testing!
     */
    public PhaseHandler unregisterHandler(PhaseType arg0, PhaseOperationEnum arg1) {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     * @param arg1 Mock for testing!
     *
     * @throws PhaseManagementException Mock for testing!
     */
    public void updatePhases(Project arg0, String arg1)
        throws PhaseManagementException {
    }
}
