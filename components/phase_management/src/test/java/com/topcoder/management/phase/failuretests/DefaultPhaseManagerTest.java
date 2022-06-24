/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.topcoder.management.phase.failuretests;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.phase.ConfigurationException;
import com.topcoder.management.phase.DefaultPhaseManager;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * Failure tests for <code>DefaultPhaseManager</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DefaultPhaseManagerTest extends FailureTestBase {

    /**
     * The project used in test.
     */
    private static final Project PROJ = new Project(new Date(), new DefaultWorkdays());

    /**
     * The phase used in test.
     */
    private static final Phase PHASE = new Phase(PROJ, 1);

    /**
     * The phase type used in test.
     */
    private static final PhaseType PHASE_TYPE = new PhaseType(1, "test");

    /**
     * The phase handler used in test.
     */
    private static final PhaseHandler HANDLER = new MockPhaseHandler();

    /**
     * The manager to test.
     */
    private DefaultPhaseManager manager;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        manager = new DefaultPhaseManager(new MockPhasePersistenceForFailure("failure"),
                IDGeneratorFactory.getIDGenerator("phase_id_seq"));
    }

    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for DefaultPhaseManager(java.lang.String).
     * The namespace is null.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testDefaultPhaseManager1NullNS() throws Exception {
        try {
            new DefaultPhaseManager(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DefaultPhaseManager(java.lang.String).
     * The namespace is empty.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testDefaultPhaseManager1EmptyNS() throws Exception {
        try {
            new DefaultPhaseManager(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DefaultPhaseManager(java.lang.String).
     * The persistence class is missed.
     * Expected : {@link ConfigurationException}.
     * @throws Exception to JUnit
     */
    public void testDefaultPhaseManager1MissPersistenceClass() throws Exception {
        try {
            new DefaultPhaseManager("MissingPhasePersistenceClassName");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for DefaultPhaseManager(java.lang.String).
     * The persistence class is invalid.
     * Expected : {@link ConfigurationException}.
     * @throws Exception to JUnit
     */
    public void testDefaultPhaseManager1InvalidPersistenceClass() throws Exception {
        try {
            new DefaultPhaseManager("InvalidPhasePersistenceClassName");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for DefaultPhaseManager(java.lang.String).
     * The id generator class is missed.
     * Expected : {@link ConfigurationException}.
     * @throws Exception to JUnit
     */
    public void testDefaultPhaseManager1MissIdGeneratorInfo() throws Exception {
        try {
            new DefaultPhaseManager("MissingIdgeneratorSequenceName");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for DefaultPhaseManager(java.lang.String).
     * The id generator class is invalid.
     * Expected : {@link ConfigurationException}.
     * @throws Exception to JUnit
     */
    public void testDefaultPhaseManager1InvalidIdGeneratorInfo() throws Exception {
        try {
            new DefaultPhaseManager("InvalidIdgeneratorSequenceName");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for DefaultPhaseManager(PhasePersistence, IDGenerator).
     * Persistence is null.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testDefaultPhaseManager2NullPersistence() throws Exception {
        try {
            new DefaultPhaseManager(null, IDGeneratorFactory.getIDGenerator("phase_id_seq"));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DefaultPhaseManager(PhasePersistence, IDGenerator).
     * IdGenerator is null.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testDefaultPhaseManager2NullIDGenerator() throws Exception {
        try {
            new DefaultPhaseManager(new MockPhasePersistenceForFailure("failure"), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updatePhases(com.topcoder.project.phases.Project, java.lang.String).
     * The project is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testUpdatePhasesNullProject() throws Exception {
        try {
            manager.updatePhases(null, "operator");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updatePhases(com.topcoder.project.phases.Project, java.lang.String).
     * The operator is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testUpdatePhasesNullOperator() throws Exception {
        try {
            manager.updatePhases(new Project(new Date(), new DefaultWorkdays()), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updatePhases(com.topcoder.project.phases.Project, java.lang.String).
     * The operator is empty.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testUpdatePhasesEmptyOperator() throws Exception {
        try {
            manager.updatePhases(new Project(new Date(), new DefaultWorkdays()), " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.topcoder.management.phase.DefaultPhaseManager#getPhases(long[])}.
     * The array is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testGetPhasesLongArray() throws Exception {
        try {
            manager.getPhases(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for canStart(com.topcoder.project.phases.Phase).
     * The phase is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testCanStart() throws Exception {
        try {
            manager.canStart(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for start(com.topcoder.project.phases.Phase, java.lang.String).
     * The phase is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testStartNullPhase() throws Exception {
        try {
            manager.start(null, "operator");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for start(com.topcoder.project.phases.Phase, java.lang.String).
     * The operator is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testStartNullOperator() throws Exception {
        try {
            manager.start(PHASE, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for start(com.topcoder.project.phases.Phase, java.lang.String).
     * The operator is empty.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testStartEmptyOperator() throws Exception {
        try {
            manager.start(PHASE, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for canEnd(com.topcoder.project.phases.Phase).
     * The phase is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testCanEnd() throws Exception {
        try {
            manager.canEnd(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for end(com.topcoder.project.phases.Phase, java.lang.String).
     * The phase is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testEnd() throws Exception {
        try {
            manager.end(null, "operator");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for end(com.topcoder.project.phases.Phase, java.lang.String).
     * The operator is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testEndNullOperator() throws Exception {
        try {
            manager.end(PHASE, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for end(com.topcoder.project.phases.Phase, java.lang.String).
     * The operator is empty.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testEndEmptyOperator() throws Exception {
        try {
            manager.end(PHASE, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for canCancel(com.topcoder.project.phases.Phase).
     * The phase is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testCanCancel() throws Exception {
        try {
            manager.canCancel(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for cancel(com.topcoder.project.phases.Phase, java.lang.String).
     * The operator is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testcancelNullOperator() throws Exception {
        try {
            manager.cancel(PHASE, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for cancel(com.topcoder.project.phases.Phase, java.lang.String).
     * The operator is empty.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testcancelEmptyOperator() throws Exception {
        try {
            manager.cancel(PHASE, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for registerHandler(PhaseHandler, PhaseType, PhaseOperationEnum).
     * The handler is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testRegisterHandler() {
        try {
            manager.registerHandler(null, PHASE_TYPE, PhaseOperationEnum.START);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for registerHandler(PhaseHandler, PhaseType, PhaseOperationEnum).
     * The handler is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testRegisterHandlerNullType() {
        try {
            manager.registerHandler(HANDLER, null, PhaseOperationEnum.START);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for registerHandler(PhaseHandler, PhaseType, PhaseOperationEnum).
     * The operation is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testRegisterHandlerNullOP() {
        try {
            manager.registerHandler(HANDLER, PHASE_TYPE, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for unregisterHandler(PhaseType, PhaseOperationEnum).
     * The handler is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testUnregisterHandlerNullType() {
        try {
            manager.unregisterHandler(null, PhaseOperationEnum.START);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for unregisterHandler(PhaseType, PhaseOperationEnum).
     * The operation is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testUnregisterHandlerNullOP() {
        try {
            manager.unregisterHandler(PHASE_TYPE, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for getHandlerRegistrationInfo(com.topcoder.management.phase.PhaseHandler).
     * The handler is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testGetHandlerRegistrationInfo() {
        try {
            manager.getHandlerRegistrationInfo(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setPhaseValidator(com.topcoder.management.phase.PhaseValidator).
     * Validator is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSetPhaseValidator() {
        try {
            manager.setPhaseValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }
}
