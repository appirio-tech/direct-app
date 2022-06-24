/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * Simple implementation of PhasePersistence. All methods throw PhasePersistenceException. Any methods that are
 * expected to be invoked by a particular test should be overriden in a specialized subclass.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class NullPhasePersistence implements PhasePersistence {
    /**
     * Used by some subclasses to flag whether the correct method has been invoked.
     */
    private boolean passed = false;

    /**
     * Does nothing.
     */
    public NullPhasePersistence() { }

    /**
     * Does nothing.
     *
     * @param namespace the configuration namespace
     */
    public NullPhasePersistence(String namespace) { }

    /**
     * Returns whether or not the test should pass.
     *
     * @return whether or not the test should pass
     */
    boolean getPassed() {
        return passed;
    }

    /**
     * Sets whether or not the test should pass.
     *
     * @param passed whether or not the test should pass
     */
    void setPassed(boolean passed) {
        this.passed = passed;
    }

/**
 * Returns the <code>Project</code> associated with the specified ID. If no such project exists, returns
 * <code>null</code>.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store
 *
 * @param projectId the ID of the project
 * @return the project, or <code>null</code> if no such project exists
 */
    public Project getProjectPhases(long projectId) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to getProjectPhases");
    }

/**
 * Returns an array of project instances associated with the specified project IDs. If any if the projects in the
 * input array cannot be found then a <code>null</code> is returned in the corresponding slot in the returned
 * array. The values in the returned array correspond to the IDs in the same positions in the input array.
 *
 * @param projectIds the project IDs
 * @return the projects associated with the specified IDs (in order); may contain <code>nulls</code> for
 *   non-existent projects
 * @throws PhasePersistenceException if an error occurs accessing the persistent store
 * @throws IllegalArgumentException if the array input is null
 */
    public Project[] getProjectPhases(long[] projectIds) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to getProjectPhases");
    }

/**
 * Returns all of the phase types in the persistent storage.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store
 * @return an array of all the PhaseTypes available at this time
 */
    public PhaseType[] getAllPhaseTypes() throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to getAllPhaseTypes");
    }

/**
 * Returns all of the phase status in the persistent storage.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store
 * @return an array of all PhaseStatus available at this time
 */
    public PhaseStatus[] getAllPhaseStatuses() throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to getAllPhaseStatuses");
    }

/**
 * Creates a new phase in persistence. If the phase already exists then an exception will be thrown.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store, or the phase already exists
 * @throws IllegalArgumentException if <code>phase</code> or <code>operator</code> is <code>null</code> or the
 *   empty string
 * @param phase the phase to create
 * @param operator the creating user
 */
    public void createPhase(Phase phase, String operator) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to createPhase");
    }

/**
 * Similar to {@link #createPhase(Phase,String) createPhase(Phase,String)}, except creates multiple phases at once.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store, or a phase already exists
 * @throws IllegalArgumentException if <code>phases</code> or <code>operator</code> is <code>null</code> or the
 *   empty string
 * @param phases the phases to create
 * @param operator the creating user
 */
    public void createPhases(Phase[] phases, String operator) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to createPhases");
    }

/**
 * Returns the phase associated with the specified ID, or <code>null</code> if no such phase exists.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store
 *
 * @param phaseId the ID of the phase
 * @return the phase associated with the specified ID, or <code>null</code> if no such phase exists
 */
    public Phase getPhase(long phaseId) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to getPhase");
    }

/**
 * Similar to {@link #getPhase(long) getPhase(long)}, except looks up multiple phases simultaneously. Phases in the
 * returned array correspond to the IDs in the same position in the input array. If any phase does not exist, a
 * <code>null</code> will be entered in the return array.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store
 *
 * @param phaseIds the IDs of the phases
 * @return the phases associated with the specified IDs, or <code>null</code> if no such phase exists
 */
    public Phase[] getPhases(long[] phaseIds) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to getPhases");
    }

/**
 * Update the specified <code>Phase</code> in the persistent store.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store, or the phase does not exist
 *   in the persistent store
 * @throws IllegalArgumentException if <code>phase</code> or <code>operator</code> is <code>null</code> or the
 *   empty string
 *
 * @param phase the phase to update
 * @param operator the operator doing the updating
 */
    public void updatePhase(Phase phase, String operator) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to updatePhase");
    }

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
    public void updatePhases(Phase[] phases, String operator) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to updatePhases");
    }

/**
 * Deletes the specified phase from the persistent storage. If the phase does not exist, do nothing.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store
 * @throws IllegalArgumentException if <code>phase</code> is <code>null</code>
 *
 * @param phase the phase to delete
 */
    public void deletePhase(Phase phase) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to deletePhase");
    }

/**
 * Simlar to {@link #deletePhase(Phase) deletePhase(Phase)}, except deletes multiple phases simultaneously.
 *
 * @throws PhasePersistenceException if an error occurs accessing the persistent store
 * @throws IllegalArgumentException if <code>phases</code> is <code>null</code>
 *
 * @param phases the phases to delete
 */
    public void deletePhases(Phase[] phases) throws PhasePersistenceException {
        throw new PhasePersistenceException("unexpected call to deletePhases");
    }

/**
 * Tests if the specified phase is a new phase (needs its ID set).
 *
 * @throws IllegalArgumentException if phase is <code>null</code>
 *
 * @param phase the phase to check
 * @return <code>true</code> is the phase is new; <code>false</code> otherwise
 */
    public boolean isNewPhase(Phase phase) {
        throw new RuntimeException("unexpected call to isNewPhase");
    }

/**
 * Tests if the specified dependency is a new dependency (needs its ID set).
 *
 * @throws IllegalArgumentException if dependency is <code>null</code>
 *
 * @param dependency the dependency to check
 * @return <code>true</code> if the dependency is new; <code>false</code> otherwise
 */
    public boolean isNewDependency(Dependency dependency) {
        throw new RuntimeException("unexpected call to isNewDependency");
    }
}
