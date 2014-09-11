/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.topcoder.management.phase.failuretests;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;

import com.topcoder.management.phase.PhasePersistence;
import com.topcoder.management.phase.PhasePersistenceException;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import java.util.Date;


/**
 * This class is the mock implementation of PhasePersistence interface
 * for failure tests.
 *
 * @author assistant
 * @version 1.0
 */
public class MockPhasePersistenceForFailure implements PhasePersistence {

    /**
     * Create MockPhasePersistence instance.
     *
     * @param namespace the namespace used to load configurable file.
     */
    public MockPhasePersistenceForFailure(String namespace) {
        // do nothing here.
    }

    /**
     * Will return project instance for the given id. If the project can not be found
     * then a null is returned.
     *
     * @param projectId project id to find by
     *
     * @return Project for this id
     *
     * @throws PhasePersistenceException if there are any persistence issues. We shoudl
     *         wrap the cause in this.
     */
    public Project getProjectPhases(long projectId) throws PhasePersistenceException {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        project.setId(projectId);

        return project;
    }

    /**
     * Will return an array of project instances for the given input array of project
     * ids. If the project can not be found then a null is returned in the return array.
     * returns are positional thus id at index 2 of input is represented at index 2 of
     * output.
     *
     * @param projectIds an array of project ids
     *
     * @return and array of Projects for the ids
     * @throws PhasePersistenceException any error
     */
    public Project[] getProjectPhases(long[] projectIds)
        throws PhasePersistenceException {
        Workdays workdays = new DefaultWorkdays();
        Project p1 = new Project(new Date(), workdays);
        p1.setId(projectIds[0]);

        Project p2 = new Project(new Date(), workdays);
        p2.setId(projectIds[1]);

        return new Project[] { p1, p2 };
    }

    /**
     * Will return all the Phasetype(s) in the storage.
     *
     * @return all available phase types
     * @throws PhasePersistenceException any error
     */
    public PhaseType[] getAllPhaseTypes() throws PhasePersistenceException {
        PhaseType type1 = new PhaseType(111, "type1");
        PhaseType type2 = new PhaseType(222, "type2");

        return new PhaseType[] { type1, type2 };
    }

    /**
     * Will return all the PhaseStatus(s) in the storage.
     *
     * @return all available phase types
     * @throws PhasePersistenceException any error
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhasePersistenceException {
        PhaseStatus status1 = new PhaseStatus(111, "status1");
        PhaseStatus status2 = new PhaseStatus(222, "status2");

        return new PhaseStatus[] { status1, status2 };
    }

    /**
     * Dummy method.
     *
     * @param phase phase
     * @param operator operator
     * @throws PhasePersistenceException any error
     */
    public void createPhase(Phase phase, String operator)
        throws PhasePersistenceException {
    }

    /**
     * Dummy method.
     *
     * @param phases phase
     * @param operator operator
     * @throws PhasePersistenceException any error
     */
    public void createPhases(Phase[] phases, String operator)
        throws PhasePersistenceException {
    }

    /**
     * Dummy method.
     *
     * @param phaseId phaseId
     *
     * @return null
     * @throws PhasePersistenceException any error
     */
    public Phase getPhase(long phaseId) throws PhasePersistenceException {
        return null;
    }

    /**
     * Dummy method.
     *
     * @param phaseIds phaseIds
     *
     * @return null
     * @throws PhasePersistenceException any error
     */
    public Phase[] getPhases(long[] phaseIds) throws PhasePersistenceException {
        return null;
    }

    /**
     * Dummy method.
     *
     * @param phase phase
     * @param operator operator
     * @throws PhasePersistenceException any error
     */
    public void updatePhase(Phase phase, String operator)
        throws PhasePersistenceException {
    }

    /**
     * Dummy method.
     *
     * @param phases phases
     * @param operator operator
     * @throws PhasePersistenceException any error
     */
    public void updatePhases(Phase[] phases, String operator)
        throws PhasePersistenceException {
    }

    /**
     * Dummy method.
     *
     * @param phase phase
     * @throws PhasePersistenceException any error
     */
    public void deletePhase(Phase phase) throws PhasePersistenceException {
    }

    /**
     * Dummy method.
     *
     * @param phases phases
     * @throws PhasePersistenceException any error
     */
    public void deletePhases(Phase[] phases) throws PhasePersistenceException {
    }

    /**
     * Dummy method.
     *
     * @param phase phase
     *
     * @return false
     */
    public boolean isNewPhase(Phase phase) {
        return false;
    }

    /**
     * Dummy method.
     *
     * @param dependency dependency
     *
     * @return false
     */
    public boolean isNewDependency(Dependency dependency) {
        return false;
    }
}
