/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import java.util.Map;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>This is a contract for managing phase data for project(s). This interface provides functionality for
 * manipulating phases with pluggable support for persistence CRUD (creation, update, deletion). It manages
 * connections, ID generation, and phase handler registration.</p>
 *
 * <p>There is no requirement to make this thread-safe. The assumption is that each thread will have its own instance
 * of a PhaseManager implementation.</p>
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public interface PhaseManager {
    /**
     * Synchronizes the current state of the specified project with persistent storage. This method first validates
     * all of the phases in the project, then generates IDs for any new phases. Finally, the phases of the
     * specified input project are compared to the phases already in the database. If any new phases are
     * encountered, they are added to the persistent store via {@link PhasePersistence#createPhases
     * createPhases}. If any phases are missing from the input, they are deleted using {@link
     * PhasePersistence#deletePhases deletePhases}. All other phases are updated using {@link
     * PhasePersistence#updatePhases updatePhases}.
     *
     * @throws IllegalArgumentException if either argument is <code>null</code> or the empty string
     * @throws PhaseManagementException if a phase fails validation, orif an error occurs while persisting the updates
     *   or generating the IDs
     *
     * @param project project for which to update phases
     * @param operator the operator performing the action
     */
    void updatePhases(Project project, String operator) throws PhaseManagementException;

    /**
     * Returns the <code>Project</code> corresponding to the specified ID. If no such project exists, returns
     * <code>null</code>.
     *
     * @throws PhaseManagementException if an error occurred querying the project from the persistent store
     *
     * @param project id of the project to fetch
     * @return the project corresponding to the specified ID, or <code>null</code> if no such project exists
     */
    Project getPhases(long project) throws PhaseManagementException;

    /**
     * Similar to {@link #getPhases(long) getPhases(long)}, except this method queries multiple projects in one
     * call.  Indices in the returned array correspond to indices in the input array. If a specified project
     * cannot be found, a <code>null</code> will be returned in the corresponding array position.
     *
     * @throws PhaseManagementException if an error occurred querying the projects from the persistent store
     * @throws IllegalArgumentException if <code>projects</code> is <code>null</code>
     *
     * @param projects the project IDs to look up
     * @return the <code>Project</code> instances corresponding to the specified project IDs
     */
    Project[] getPhases(long[] projects) throws PhaseManagementException;

    /**
     * Returns an array of all phase types by calling the {@link PhasePersistence#getAllPhaseStatuses
     * getAllPhaseTypes} method of this manager's configured persistence object.
     *
     * @throws PhaseManagementException if an error occurred retrieiving the types from persistent storage
     *
     * @return an array of all the phase types
     */
    PhaseType[] getAllPhaseTypes() throws PhaseManagementException;

    /**
     * Returns an array of all phase statuses by calling the {@link PhasePersistence#getAllPhaseStatuses
     * getAllPhaseStatuses} method of this manager's configured persistence object.
     *
     * @throws PhaseManagementException if an error occurred retrieiving the statuses from persistent storage
     * @return an array of all the phase statuses
     */
    PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException;

    /**
     * Determines whether it is possible to start the specified phase. If a {@link PhaseHandler phase handler} has
     * been registered for the start operation of the given phase type, its {@link PhaseHandler#canPerform
     * canPerform} method will be called to determine whether the phase can be started. If no hander is registered,
     * this method returns <code>true</code> if the phase's {@link Phase#calcStartDate start date} is less than or
     * equal to the current date.
     *
     * @throws IllegalArgumentException if phase is <code>null</code>
     * @throws PhaseManagementException if an error occurs while accessing persistent storage
     *
     * @param phase phase to test for starting
     * @return <code>true</code> if the specified phase can be started; <code>false</code> otherwise
     */
    boolean canStart(Phase phase) throws PhaseManagementException;

    /**
     * Starts the specified phase. If a {@link PhaseHandler phase handler} is set for the start operation of the
     * phase's type, the handler's {@link PhaseHandler#perform perform} method is invoked first. Next, the phase's
     * status is set to {@link PhaseStatus#OPEN OPEN} and the phase's actual start date is set to the current
     * date. Finally, the changes are persisted by delegating to the configured phase persistence object.
     *
     * @throws PhaseManagementException if an error occurs while persisting the change
     * @throws IllegalArgumentException if either argument is <code>null</code> or an empty string
     * @param phase the phase to start
     * @param operator the operator starting the phase
     */
    void start(Phase phase, String operator) throws PhaseManagementException;

    /**
     * Determines whether it is possible to end the specified phase. If a {@link PhaseHandler phase handler} has
     * been registered for the end operation of the given phase type, its {@link PhaseHandler#canPerform
     * canPerform} method will be called to determine whether the phase can be ended. If no hander is registered,
     * this method returns <code>true</code> if the phase's {@link Phase#calcEndDate end date} is less than or
     * equal to the current date.
     *
     * @throws IllegalArgumentException if phase is <code>null</code>
     * @throws PhaseManagementException if an error occurs while accessing persistent storage
     *
     * @param phase phase to test for ending
     * @return <code>true</code> if the specified phase can be ended; <code>false</code> otherwise
     */
    boolean canEnd(Phase phase) throws PhaseManagementException;

    /**
     * Ends the specified phase. If a {@link PhaseHandler phase handler} is set for the end operation of the
     * phase's type, the handler's {@link PhaseHandler#perform perform} method is invoked first. Next, the phase's
     * status is set to {@link PhaseStatusEnum#CLOSED CLOSED} and the phase's actual end date is set to the current
     * date. Finally, the changes are persisted by delegating to the configured phase persistence object.
     *
     * @throws PhaseManagementException if an error occurs while persisting the change
     * @throws IllegalArgumentException if either argument is <code>null</code> or an empty string
     * @param phase the phase to end
     * @param operator the operator ending the phase
     */
    void end(Phase phase, String operator) throws PhaseManagementException;

    /**
     * Determines whether it is possible to cancel the specified phase. If a {@link PhaseHandler phase handler} has
     * been registered for the cancel operation of the given phase type, its {@link PhaseHandler#canPerform canPerform}
     * method will be called to determine whether the phase can be cancelled. If no hander is registered, this method
     * returns <code>true</code>.
     *
     * @throws PhaseManagementException if an error occurs while accessing persistent storage
     * @throws IllegalArgumentException if phase is <code>null</code>
     * @param phase phase to test for cancellation
     * @return <code>true</code> if the phase can be cancelled; <code>false</code> otherwise
     */
    boolean canCancel(Phase phase) throws PhaseManagementException;

    /**
     * Cancels the specified phase. If a {@link PhaseHandler phase handler} is set for the cancel operation of the
     * phase's type, the handler's {@link PhaseHandler#perform perform} method is invoked first. Next, the phase's
     * status is set to {@link PhaseStatusEnum#CLOSED CLOSED} and the phase's actual end date is set to the current
     * date. Finally, the changes are persisted by delegating to the configured phase persistence object.
     *
     * @throws PhaseManagementException if an error occurs while persisting the change
     * @throws IllegalArgumentException if either argument is <code>null</code> or an empty string
     *
     * @param phase the phase to cancel
     * @param operator the operator cancelling the phase
     */
    void cancel(Phase phase, String operator) throws PhaseManagementException;

    /**
     * <p>Registers a custom handler for the specified phase type and operation. If present, handlers override the
     * default behavior for determining whether a given operation can be performed on a given phase. If a handler
     * already exists for the specified type/operation combination, it will be replaced by the specified
     * handler.</p>
     *
     * <p>Note that <code>type</code> is stored in the registry by reference (rather than copied) so the caller
     * should take care not to subsequently modify the type. Doing so may cause the registry to become
     * inconsistent.</p>
     *
     * @throws IllegalArgumentException if any argument is null
     *
     * @param handler the handler
     * @param type the phase type to associate with the handler
     * @param operation the operation to associate with the handler
     */
    void registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum operation);

    /**
     * Unregisters the handler (if any) associated with the specified phase type and operation and returns a
     * refernce to the handler. Returns <code>null</code> if no handler is associated with the specified
     * type/operation combination.
     *
     * @throws IllegalArgumentException if either argument is <code>null</code>
     *
     * @param type the phase type associated with the handler to unregister
     * @param operation the operation associated with the handler to unregister
     * @return the previously registered handler, or <code>null</code> if no handler was registered
     */
    PhaseHandler unregisterHandler(PhaseType type, PhaseOperationEnum operation);

    /**
     * Returns an array of all the currently registered phase handlers. If a handler is registered more than one
     * (for different phase/operation combinations), it will appear only once in the array.
     *
     * @return all of the currently registered phase handlers
     */
    PhaseHandler[] getAllHandlers();

    /**
     * Returns the phase type(s) and operation(s) associated with the specified handler in the handler
     * registry. Returns an empty array if the handler is not registered.
     *
     * @throws IllegalArgumentException if <code>handler</code> is <code>null</code>
     * @param handler handler of interest
     * @return the registration entries associated with the handler
     */
    HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler handler);

    /**
     * Sets the current phase validator for this manager.
     *
     * @throws IllegalArgumentException if the validator is null
     * @param phaseValidator the validator to use for this manager
     */
    void setPhaseValidator(PhaseValidator phaseValidator);

    /**
     * Returns the current phase validator. If no phase validator has been configured or set for this manager, an
     * instance of <code>DefaultPhaseValidator</code> will be used instead.
     *
     * @return the current phase validator
     */
    PhaseValidator getPhaseValidator();

    /**
     * This method selects all the dependencies for phases.
     * @param conn the database connection.
     * @param phases the map of already retrieved phases.
     * @param projectIds all the project ids.
     * @throws PhaseManagementException if database error occurs.
     */
    public void fillDependencies(Map phases, long[] projectIds)
        throws  PhaseManagementException;
}


