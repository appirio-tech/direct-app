/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import java.util.Map;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>This is a persistence contract for phase persistence operations.  This is broken down into APIs that deal
 * with basic CRUD of phase management (creation, update, deletion) for both individual phasees and groups of
 * related phases. The interface also includes methods that allow for retrieval of all phases linked to a
 * particular project or projects.</p>
 *
 * <p>This class works in conjunction with {@link PhaseManager PhaseManager} to insulate the end user from the
 * details of phase persistence.</p>
 *
 * <p>Implementations should strive to ensure thread safety for operations. For example, database-centric
 * implementations should utilize database connections local to each method invocation so that there are no issues
 * with multiple threads making simultaneous method calls.</p>
 *
 * <p>Implementations should provide a constructor that accepts a single <code>String</code> argument representing
 * the name of a {@link com.topcoder.util.config.ConfigManager ConfigManager} namespace. The implementation should
 * initialize itself based on the specified namespace. This constructor will be used by the {@link PhaseManager
 * PhaseManager} to instantiate the persistence object when using the configuration manager.</p>
 *
 * <p>The configuration parameters are as follows.</p>
 *
 * <ul>
 *   <li><code>connectionName</code> - the name of the connection (optional)</li>
 *   <li><code>ConnectionFactory.className</code> - the name of the connection factory implementation</li>
 *   <li><code>ConnectionFactory.namespace</code> - the namespace to use to instantiate the connection factory</li>
 * </ul>
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public interface PhasePersistence {
    /**
     * Returns the <code>Project</code> associated with the specified ID. If no such project exists, returns
     * <code>null</code>.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store
     *
     * @param projectId the ID of the project
     * @return the project, or <code>null</code> if no such project exists
     */
    Project getProjectPhases(long projectId) throws PhasePersistenceException;

    /**
     * Returns an array of project instances associated with the specified project IDs. If any if the projects in
     * the input array cannot be found then a <code>null</code> is returned in the corresponding slot in the
     * returned array. The values in the returned array correspond to the IDs in the same positions in the input
     * array.
     *
     * @param projectIds the project IDs
     * @return the projects associated with the specified IDs (in order); may contain <code>nulls</code> for
     *   non-existent projects
     * @throws PhasePersistenceException if an error occurs accessing the persistent store
     * @throws IllegalArgumentException if the array input is null
     */
    Project[] getProjectPhases(long[] projectIds) throws PhasePersistenceException;

    /**
     * Returns all of the phase types in the persistent storage.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store
     * @return an array of all the PhaseTypes available at this time
     */
    PhaseType[] getAllPhaseTypes() throws PhasePersistenceException;

    /**
     * Returns all of the phase status in the persistent storage.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store
     * @return an array of all PhaseStatus available at this time
     */
    PhaseStatus[] getAllPhaseStatuses() throws PhasePersistenceException;

    /**
     * Creates a new phase in persistence. If the phase already exists then an exception will be thrown.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store, or the phase already
     *   exists
     * @throws IllegalArgumentException if <code>phase</code> or <code>operator</code> is <code>null</code> or the
     *   empty string
     * @param phase the phase to create
     * @param operator the creating user
     */
    void createPhase(Phase phase, String operator) throws PhasePersistenceException;

    /**
     * Similar to {@link #createPhase(Phase,String) createPhase(Phase,String)}, except creates multiple phases at once.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store, or a phase already exists
     * @throws IllegalArgumentException if <code>phases</code> or <code>operator</code> is <code>null</code> or the
     *   empty string
     * @param phases the phases to create
     * @param operator the creating user
     */
    void createPhases(Phase[] phases, String operator) throws PhasePersistenceException;

    /**
     * Returns the phase associated with the specified ID, or <code>null</code> if no such phase exists.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store
     *
     * @param phaseId the ID of the phase
     * @return the phase associated with the specified ID, or <code>null</code> if no such phase exists
     */
    Phase getPhase(long phaseId) throws PhasePersistenceException;

    /**
     * Similar to {@link #getPhase(long) getPhase(long)}, except looks up multiple phases simultaneously. Phases in
     * the returned array correspond to the IDs in the same position in the input array. If any phase does not
     * exist, a <code>null</code> will be entered in the return array.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store
     *
     * @param phaseIds the IDs of the phases
     * @return the phases associated with the specified IDs, or <code>null</code> if no such phase exists
     */
    Phase[] getPhases(long[] phaseIds) throws PhasePersistenceException;

    /**
     * Update the specified <code>Phase</code> in the persistent store.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store, or the phase does not
     *   exist in the persistent store
     * @throws IllegalArgumentException if <code>phase</code> or <code>operator</code> is <code>null</code> or the
     *   empty string
     *
     * @param phase the phase to update
     * @param operator the operator doing the updating
     */
    void updatePhase(Phase phase, String operator) throws PhasePersistenceException;

    /**
     * Similar to {@link #updatePhase(Phase,String) updatePhase(Phase,String)}, except updates multiple phases
     * simultaneously.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store, or a phase does not exist
     *   in the persistent store
     * @throws IllegalArgumentException if <code>phases</code> or <code>operator</code> is <code>null</code> or the
     *   empty string
     *
     * @param phases the phases to update
     * @param operator the operator doing the updating
     */
    void updatePhases(Phase[] phases, String operator) throws PhasePersistenceException;

    /**
     * Deletes the specified phase from the persistent storage. If the phase does not exist, do nothing.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store
     * @throws IllegalArgumentException if <code>phase</code> is <code>null</code>
     *
     * @param phase the phase to delete
     */
    void deletePhase(Phase phase) throws PhasePersistenceException;

    /**
     * Similar to {@link #deletePhase(Phase) deletePhase(Phase)}, except deletes multiple phases simultaneously.
     *
     * @throws PhasePersistenceException if an error occurs accessing the persistent store
     * @throws IllegalArgumentException if <code>phases</code> is <code>null</code>
     *
     * @param phases the phases to delete
     */
    void deletePhases(Phase[] phases) throws PhasePersistenceException;

    /**
     * Tests if the specified phase is a new phase (needs its ID set).
     *
     * @throws IllegalArgumentException if phase is <code>null</code>
     *
     * @param phase the phase to check
     * @return <code>true</code> is the phase is new; <code>false</code> otherwise
     */
    boolean isNewPhase(Phase phase);

    /**
     * Tests if the specified dependency is a new dependency (needs its ID set).
     *
     * @throws IllegalArgumentException if dependency is <code>null</code>
     *
     * @param dependency the dependency to check
     * @return <code>true</code> if the dependency is new; <code>false</code> otherwise
     */
    boolean isNewDependency(Dependency dependency);

    /**
     * This method selects all the dependencies for phases.
     * @param conn the database connection.
     * @param phases the map of already retrieved phases.
     * @param projectIds all the project ids.
     * @throws PhasePersistenceException if the phase dependencies cannot be
     *             filled.
     */
    public void fillDependencies(Map phases, long[] projectIds)
        throws PhasePersistenceException;
}
