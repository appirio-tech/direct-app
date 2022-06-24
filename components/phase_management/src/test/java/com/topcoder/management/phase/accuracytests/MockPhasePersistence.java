/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.accuracytests;

import com.topcoder.management.phase.PhasePersistence;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * A mock implementation of PhasePersistence used only for test purpose.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class MockPhasePersistence implements PhasePersistence {
    /** Used by some subclasses to flag whether the correct method has been invoked. */
    private boolean passed = false;

    /**
     * Empty default constructor.
     */
    public MockPhasePersistence() {
    }

    /**
     * Empty constructor with namespace.
     * @param namespace the configuration namespace
     */
    public MockPhasePersistence(String namespace) {
    }

    /**
     * Returns the <code>Project</code> associated with the specified ID. If no such project exists, returns
     * <code>null</code>.
     * @param projectId the ID of the project
     * @return the project, or <code>null</code> if no such project exists
     */
    public Project getProjectPhases(long projectId) {
        return null;
    }

    /**
     * Returns an array of project instances associated with the specified project IDs. If any if the projects in the
     * input array cannot be found then a <code>null</code> is returned in the corresponding slot in the returned
     * array. The values in the returned array correspond to the IDs in the same positions in the input array.
     * @param projectIds the project IDs
     * @return always null;
     */
    public Project[] getProjectPhases(long[] projectIds) {
        return null;
    }

    /**
     * Returns all of the phase types in the persistent storage.
     * @return an array of all the PhaseTypes available at this time
     */
    public PhaseType[] getAllPhaseTypes() {
        return null;
    }

    /**
     * Returns all of the phase status in the persistent storage.
     * @return an array of all PhaseStatus available at this time
     */
    public PhaseStatus[] getAllPhaseStatuses() {
        return null;
    }

    /**
     * Creates a new phase in persistence. If the phase already exists then an exception will be thrown.
     * @param phase the phase to create
     * @param operator the creating user
     */
    public void createPhase(Phase phase, String operator) {
    }

    /**
     * Similar to {@link #createPhase(Phase,String) createPhase(Phase,String)}, except creates multiple phases at
     * once.
     * @param phases the phases to create
     * @param operator the creating user
     */
    public void createPhases(Phase[] phases, String operator) {
    }

    /**
     * Returns the phase associated with the specified ID, or <code>null</code> if no such phase exists.
     * @param phaseId the ID of the phase
     * @return the phase associated with the specified ID, or <code>null</code> if no such phase exists
     */
    public Phase getPhase(long phaseId) {
        return null;
    }

    /**
     * Similar to {@link #getPhase(long) getPhase(long)}, except looks up multiple phases simultaneously. Phases in
     * the returned array correspond to the IDs in the same position in the input array. If any phase does not exist,
     * a <code>null</code> will be entered in the return array.
     * @param phaseIds the IDs of the phases
     * @return the phases associated with the specified IDs, or <code>null</code> if no such phase exists
     */
    public Phase[] getPhases(long[] phaseIds) {
        return null;
    }

    /**
     * Update the specified <code>Phase</code> in the persistent store.
     * @param phase the phase to update
     * @param operator the operator doing the updating
     */
    public void updatePhase(Phase phase, String operator) {
    }

    /**
     * Similar to {@link #updatePhase(Phase,String) updatePhase(Phase,String)}, except updates multiple phases
     * simultaneously.
     * @param phases the phases to update
     * @param operator the operator doing the updating
     */
    public void updatePhases(Phase[] phases, String operator) {
    }

    /**
     * Deletes the specified phase from the persistent storage. If the phase does not exist, do nothing.
     * @param phase the phase to delete
     */
    public void deletePhase(Phase phase) {
    }

    /**
     * Simlar to {@link #deletePhase(Phase) deletePhase(Phase)}, except deletes multiple phases simultaneously.
     * @param phases the phases to delete
     */
    public void deletePhases(Phase[] phases) {
    }

    /**
     * Tests if the specified phase is a new phase (needs its ID set).
     * @param phase the phase to check
     * @return <code>true</code> is the phase is new; <code>false</code> otherwise
     */
    public boolean isNewPhase(Phase phase) {
        return false;
    }

    /**
     * Tests if the specified dependency is a new dependency (needs its ID set).
     * @param dependency the dependency to check
     * @return <code>true</code> if the dependency is new; <code>false</code> otherwise
     */
    public boolean isNewDependency(Dependency dependency) {
        return false;
    }
}