/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.stresstests;

import com.topcoder.management.phase.PhasePersistence;
import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * Mock class for PhasePersistence, this class doesn't do any operation.
 * </p>
 *
 * @author still
 * @version 1.0
 */
public final class StressMockPhasePersistence implements PhasePersistence {
    /**
     * Empty constructor.
     * @param namespace not used.
     */
    public StressMockPhasePersistence(String namespace) {
    }

    /**
     * Empty mock method.
     * @param projectId not used.
     * @throws PhasePersistenceException in no circumstance.
     * @return null.
     */
    public Project getProjectPhases(long projectId) throws PhasePersistenceException {
        return null;
    }

    /**
     * Empty mock method.
     * @param projectIds not used.
     * @throws PhasePersistenceException in no circumstance.
     * @return null.
     */
    public Project[] getProjectPhases(long[] projectIds) throws PhasePersistenceException {
        return null;
    }

    /**
     * Empty mock method.
     * @throws PhasePersistenceException in no circumstance.
     * @return null.
     */
    public PhaseType[] getAllPhaseTypes() throws PhasePersistenceException {
        return null;
    }

    /**
     * Empty mock method.
     * @throws PhasePersistenceException in no circumstance.
     * @return null.
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhasePersistenceException {
        return null;
    }

    /**
     * Empty mock method.
     * @param phase not used.
     * @param operator not used.
     * @throws PhasePersistenceException in no circumstance.
     */
    public void createPhase(Phase phase, String operator) throws PhasePersistenceException {

    }

    /**
     * Empty mock method.
     * @param phases not used.
     * @param operator not used.
     * @throws PhasePersistenceException in no circumstance.
     */
    public void createPhases(Phase[] phases, String operator) throws PhasePersistenceException {

    }

    /**
     * Empty mock method.
     * @param phaseId not used
     * @throws PhasePersistenceException in no circumstance.
     * @return null.
     */
    public Phase getPhase(long phaseId) throws PhasePersistenceException {
        return null;
    }

    /**
     * Empty mock method.
     * @param phaseIds not used
     * @throws PhasePersistenceException in no circumstance.
     * @return null.
     */
    public Phase[] getPhases(long[] phaseIds) throws PhasePersistenceException {
        return null;
    }

    /**
     * Empty mock method.
     * @param phase not used.
     * @param operator not used.
     * @throws PhasePersistenceException in no circumstance.
     */
    public void updatePhase(Phase phase, String operator) throws PhasePersistenceException {

    }

    /**
     * Empty mock method.
     * @param phases not used.
     * @param operator not used.
     * @throws PhasePersistenceException in no circumstance.
     */
    public void updatePhases(Phase[] phases, String operator) throws PhasePersistenceException {


    }

    /**
     * Empty mock method.
     * @param phase not used.
     * @throws PhasePersistenceException in no circumstance.
     */
    public void deletePhase(Phase phase) throws PhasePersistenceException {

    }

    /**
     * Empty mock method.
     * @param phases not used.
     * @throws PhasePersistenceException in no circumstance.
     */
    public void deletePhases(Phase[] phases) throws PhasePersistenceException {

    }

    /**
     * Mock method of isNewPhase.
     * @param phase the phase.
     * @return whether phased id is zero.
     */
    public boolean isNewPhase(Phase phase) {
        return phase.getId() == 0;
    }

    /**
     * Mock method of isNewDependency.
     * @param dependency the dependency.
     * @return false.
     */
    public boolean isNewDependency(Dependency dependency) {
        // TODO Auto-generated method stub
        return false;
    }

}