/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

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
 * This is a mock implementation of {@link PhaseManager}.
 * </p>
 *
 * @author stylecheck
 * @version 1.1
 * @since 1.0
 */
public class MockPhaseManager implements PhaseManager {

    /**
     * Constructs an instance.
     */
    public MockPhaseManager() {
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param project
     *            project for which to update phases
     * @param operator
     *            the operator performing the action
     * @throws IllegalArgumentException
     *             if either argument is <code>null</code> or the empty string
     * @throws PhaseManagementException
     *             if a phase fails validation, or if an error occurs while persisting the updates or generating the
     *             IDs
     */
    public void updatePhases(Project project, String operator) throws PhaseManagementException {
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param project
     *            id of the project to fetch
     * @return the project corresponding to the specified ID, or <code>null</code> if no such project exists
     * @throws PhaseManagementException
     *             if an error occurred querying the project from the persistent store
     */
    public Project getPhases(long project) throws PhaseManagementException {
        Project projectobj = new Project(new Date(), new DefaultWorkdays());
        projectobj.setId(project);
        return projectobj;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param projects
     *            the project IDs to look up
     * @throws PhaseManagementException
     *             if an error occurred querying the projects from the persistent store
     * @throws IllegalArgumentException
     *             if <code>projects</code> is <code>null</code>
     * @return the <code>Project</code> instances corresponding to the specified project IDs
     */
    public Project[] getPhases(long[] projects) throws PhaseManagementException {
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(1);
        Phase phase = new Phase(project, 1000000);
        phase.setId(1);
        phase.setPhaseStatus(PhaseStatus.OPEN);

        project.addPhase(phase);

        return new Project[] {project};
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @throws PhaseManagementException
     *             if an error occurred retrieving the types from persistent storage
     * @return an array of all the phase types
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @throws PhaseManagementException
     *             if an error occurred retrieving the statuses from persistent storage
     * @return an array of all the phase statuses
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param phase
     *            phase to test for starting
     * @throws IllegalArgumentException
     *             if phase is <code>null</code>
     * @throws PhaseManagementException
     *             if an error occurs while accessing persistent storage
     * @return <code>true</code> if the specified phase can be started; <code>false</code> otherwise
     */
    public boolean canStart(Phase phase) throws PhaseManagementException {
        return false;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param phase
     *            the phase to start
     * @param operator
     *            the operator starting the phase
     * @throws PhaseManagementException
     *             if an error occurs while persisting the change
     * @throws IllegalArgumentException
     *             if either argument is <code>null</code> or an empty string
     */
    public void start(Phase phase, String operator) throws PhaseManagementException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param phase
     *            phase to test for ending
     * @throws IllegalArgumentException
     *             if phase is <code>null</code>
     * @throws PhaseManagementException
     *             if an error occurs while accessing persistent storage
     * @return <code>true</code> if the specified phase can be ended; <code>false</code> otherwise
     */
    public boolean canEnd(Phase phase) throws PhaseManagementException {
        return false;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param phase
     *            the phase to end
     * @param operator
     *            the operator ending the phase
     * @throws PhaseManagementException
     *             if an error occurs while persisting the change
     * @throws IllegalArgumentException
     *             if either argument is <code>null</code> or an empty string
     */
    public void end(Phase phase, String operator) throws PhaseManagementException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param phase
     *            phase to test for cancellation
     * @throws PhaseManagementException
     *             if an error occurs while accessing persistent storage
     * @throws IllegalArgumentException
     *             if phase is <code>null</code>
     * @return <code>true</code> if the phase can be canceled; <code>false</code> otherwise
     */
    public boolean canCancel(Phase phase) throws PhaseManagementException {
        return false;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param phase
     *            the phase to cancel
     * @param operator
     *            the operator canceling the phase
     * @throws PhaseManagementException
     *             if an error occurs while persisting the change
     * @throws IllegalArgumentException
     *             if either argument is <code>null</code> or an empty string
     */
    public void cancel(Phase phase, String operator) throws PhaseManagementException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param handler
     *            the handler
     * @param type
     *            the phase type to associate with the handler
     * @param operation
     *            the operation to associate with the handler
     * @throws IllegalArgumentException
     *             if any argument is null
     */
    public void registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum operation) {
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param type
     *            the phase type associated with the handler to unregister
     * @param operation
     *            the operation associated with the handler to unregister
     * @return the previously registered handler, or <code>null</code> if no handler was registered
     * @throws IllegalArgumentException
     *             if either argument is <code>null</code>
     */
    public PhaseHandler unregisterHandler(PhaseType type, PhaseOperationEnum operation) {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @return all of the currently registered phase handlers
     */
    public PhaseHandler[] getAllHandlers() {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param handler
     *            handler of interest
     * @return the registration entries associated with the handler
     * @throws IllegalArgumentException
     *             if <code>handler</code> is <code>null</code>
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler handler) {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @param phaseValidator
     *            the validator to use for this manager
     * @throws IllegalArgumentException
     *             if the validator is null
     */
    public void setPhaseValidator(PhaseValidator phaseValidator) {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     *
     * @return the current phase validator
     */
    public PhaseValidator getPhaseValidator() {
        return null;
    }

}
