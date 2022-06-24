/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.phase.db;

import com.topcoder.management.phase.PhasePersistence;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * This is the demonstration of component usage.
 *
 * @author kr00tki
 * @version 1.0
 */
public class Demo extends DbTestCase {

    /**
     * This is the demonstration of the component usage. It shows how the persistence class
     * can be created and shows the methods usage.
     *
     * @throws Exception to JUnit.
     */
    public void testDemo() throws Exception {
        // create the persistence instance using configuration
        PhasePersistence persistence = new InformixPhasePersistence(NAMESPACE);

        // create the persistence using factory and connection name
        persistence = new InformixPhasePersistence(getConnectionFactory(), CONNECTION_NAME, getIDGenerator());

        // if default connection should be used then the connection name should be null
        persistence = new InformixPhasePersistence(getConnectionFactory(), null, getIDGenerator());

        Phase phase = createPhase(1, 1, true);
        Phase phase2 = createPhase(2, 1, true);
        Phase phase3 = createPhase(3, 1, false);
        // create the phase in datastore
        persistence.createPhase(phase, "creation user");

        // create the array of phases in the datastore
        persistence.createPhases(new Phase[] {phase2, phase3}, "creation operator");

        // get the phase from database
        Phase retrieved = persistence.getPhase(phase.getId());

        // get the array of phases. Each phase will have its dependencies set.
        // if any phase not exists in the database, null will be returned in the place in result array
        Phase[] array = persistence.getPhases(new long[] {2, 6, 3});
        assertNull("Should be null.", array[1]);

        // update the phase
        phase.setLength(10);
        persistence.updatePhase(phase, "update operator");

        // update array of phases
        persistence.updatePhases(new Phase[] {phase2, phase3}, "update");

        // get phases for project
        Project project = persistence.getProjectPhases(1);

        // get array of projects
        Project[] projects = persistence.getProjectPhases(new long[] {1, 2});

        // delete phase
        persistence.deletePhase(retrieved);

        // delete phases
        persistence.deletePhases(new Phase[] {phase2, phase3});

        // get all phase types
        PhaseType[] types = persistence.getAllPhaseTypes();

        // get all phase statuses
        PhaseStatus[] statuses = persistence.getAllPhaseStatuses();

        // check if phase is new
        if (persistence.isNewPhase(phase)) {
            persistence.createPhase(phase, "create");
        }

        // check if dependency is new
        persistence.isNewDependency(new Dependency(phase2, phase3, true, false, 1));
    }

}
