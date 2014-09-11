/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
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
 * <p>
 * Mock implementation of PhaseManager used for accuracy test cases.
 * </p>
 * 
 * @author kshatriyan
 * @version 1.0
 */
public class MockPhaseManager implements PhaseManager {

    /**
     * Not implemented. Returns false always.
     * 
     * @param arg0
     *            Phase parameter
     * @return false.
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public boolean canCancel(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Not implemented. Returns false always.
     * 
     * @param arg0
     *            Phase parameter
     * @return false.
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public boolean canEnd(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Not implemented. Returns false always.
     * 
     * @param arg0
     *            Phase parameter
     * @return false.
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public boolean canStart(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Not implemented.
     * 
     * @param arg0
     *            Phase parameter
     * @param arg1
     *            String parameter
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public void cancel(Phase arg0, String arg1) throws PhaseManagementException {
        // empty
    }

    /**
     * Not implemented.
     * 
     * @param arg0
     *            Phase parameter
     * @param arg1
     *            String parameter
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public void end(Phase arg0, String arg1) throws PhaseManagementException {
        // empty

    }

    /**
     * Not implemented. Return null always.
     * 
     * @return null.
     */
    public PhaseHandler[] getAllHandlers() {
        return null;
    }

    /**
     * Not implemented. Return null always.
     * 
     * @return null.
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        return null;
    }

    /**
     * Not implemented. Return null always.
     * 
     * @return null.
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        return null;
    }

    /**
     * Not implemented. Return null always.
     * 
     * @param arg0
     *            PhaseHandler parameter.
     * @return null.
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
        return null;
    }

    /**
     * Not implemented. Return null always.
     * 
     * @return null.
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public PhaseValidator getPhaseValidator() {
        return null;
    }

    /**
     * Returns a new Project if the id is 1. False otherwise.
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public Project getPhases(long arg0) throws PhaseManagementException {
        if (arg0 == 1 || arg0 == 2) {
            return new Project(new Date(), new DefaultWorkdays());
        }
        return null;
    }

    /**
     * Not implemented. Return null always.
     * 
     * @param arg0
     *            array of ids.
     * @return null.
     * 
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public Project[] getPhases(long[] arg0) throws PhaseManagementException {
        return null;
    }

    /**
     * Not implemented.
     * 
     * @param arg0
     *            PhaseHandler parameter.
     * @param arg1
     *            PhaseType parameter.
     * @param arg2
     *            PhaseOperationEnum parameter.
     */
    public void registerHandler(PhaseHandler arg0, PhaseType arg1, PhaseOperationEnum arg2) {
        // empty
    }

    /**
     * Not implemented.
     * 
     * @param arg0
     *            PhaseValidator argument.
     */
    public void setPhaseValidator(PhaseValidator arg0) {
        // empty
    }

    /**
     * Not implemented.
     * 
     * @param arg0
     *            Phase parameter.
     * @param arg1
     *            String parameter.
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public void start(Phase arg0, String arg1) throws PhaseManagementException {
        // empty
    }

    /**
     * Not implemented.
     * 
     * @param arg0
     *            PhaseType parameter.
     * @param arg1
     *            PhaseOperationEnum parameter.
     * 
     * @return null always.
     */
    public PhaseHandler unregisterHandler(PhaseType arg0, PhaseOperationEnum arg1) {
        return null;
    }

    /**
     * Not implemented.
     * 
     * @param arg0
     *            Project parameter.
     * @param arg1
     *            String parameter.
     * @throws PhaseManagementException
     *             Not thrown.
     */
    public void updatePhases(Project arg0, String arg1) throws PhaseManagementException {

    }

}