/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.phase.DefaultPhaseManager;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhaseStatusEnum;
import com.topcoder.management.phase.PhaseValidationException;
import com.topcoder.management.phase.PhaseValidator;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * Stress tests for DefaultPhaseManager.
 * </p>
 *
 * @author still
 * @version 1.0
 */
public class DefaultPhaseManagerStressTest extends TestCase {
    /** The number of times each method will be run. */
    private static final int RUN_TIMES = 1000;
    /** The number of handles will be registered. */
    private static final int DATA_COUNT = 1000;
    /** The DefaultPhaseManager instance to be used in this test. */
    private DefaultPhaseManager manager;

    /**
     * Test suite of DefaultPhaseManagerStressTest.
     *
     * @return Test suite of DefaultPhaseManagerStressTest.
     */
    public static Test suite() {
        return new TestSuite(DefaultPhaseManagerStressTest.class);
    }

    /**
     * Initialization for all tests here.
     * @throws Exception to Junit.
     */
    protected void setUp() throws Exception {
        manager = new DefaultPhaseManager(new StressMockPhasePersistence(""),
                StressMockIDGenerator.DEFAULT);
        manager.setPhaseValidator(new StressMockPhaseValidator());
        Helper.loadConfig();
    }
    /**
     * <p>Clear the test environment.</p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        Helper.unloadConfig();
    }

    /**
     * <p>Stress test for DefaultPhaseManager#DefaultPhaseManager(String).</p>
     *
     * @throws Exception to junit.
     */
    public void testCtor_namespace() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertNotNull("Failed to create DefaultPhaseManager.",
                new DefaultPhaseManager("stress_test_ns"));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing DefaultPhaseManager(String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * <p>Stress test for DefaultPhaseManager#DefaultPhaseManager(PhasePersistence, IDGenerator).</p>
     *
     */
    public void testCtor() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertNotNull("Failed to create DefaultPhaseManager.",
                new DefaultPhaseManager(new StressMockPhasePersistence(""),
                    StressMockIDGenerator.DEFAULT));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing DefaultPhaseManager(PhasePersistence, IDGenerator) for " + RUN_TIMES
            + " times costs " + (end - start) + "ms");
    }

    /**
     * <p>Stress test for DefaultPhaseManager#updatePhases(Project, String).</p>
     *
     * @throws Exception to junit.
     */
    public void testUpdatePhases() throws Exception {
        Project project = new StressMockProject();
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.updatePhases(project, "iven");
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing updatePhases(Project, String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * <p>Stress test for DefaultPhaseManager#canStart(Phase).</p>
     *
     * @throws Exception to junit.
     */
    public void testCanStart() throws Exception {
        Phase phase = new StressMockPhase();
        PhaseType type1 = new PhaseType(101, "type101");
        PhaseType type2 = new PhaseType(102, "type102");
        manager.registerHandler(new StressMockPhaseHandler(true), type1, PhaseOperationEnum.START);
        manager.registerHandler(new StressMockPhaseHandler(false), type2, PhaseOperationEnum.START);
        phase.setPhaseType(type1);
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertTrue("Should return true.", manager.canStart(phase));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing canStart(Phase) which returns true for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
        phase.setPhaseType(type2);
        start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertFalse("Should return false.", manager.canStart(phase));
        }
        end = System.currentTimeMillis();
        System.out.println("Testing canStart(Phase) which returns false for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * <p>Stress test for DefaultPhaseManager#canStart(Phase).</p>
     *
     * @throws Exception to junit.
     */
    public void testCanStart_NonHandle() throws Exception {
        Phase phase = new StressMockPhase();
        phase.setPhaseType(new PhaseType(101, "type101"));
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.canStart(phase);
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing canStart(Phase) which has no corresponding handles for " + RUN_TIMES
            + " times costs " + (end - start) + "ms");
    }

    /**
     * <p>Stress test for DefaultPhaseManager#start(Phase, String).</p>
     *
     * @throws Exception to junit.
     */
    public void testStart() throws Exception {
        Phase phase = new StressMockPhase();
        PhaseStatus status = new PhaseStatus(101, "status101");
        phase.setPhaseStatus(status);
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            status.setId(101);
            manager.start(phase, "iven");
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing start(Phase, String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
    /**
     * <p>Stress test for DefaultPhaseManager#canEnd(Phase).</p>
     *
     * @throws Exception to junit.
     */
    public void testCanEnd() throws Exception {
        Phase phase = new StressMockPhase();

        PhaseType type1 = new PhaseType(101, "type101");
        PhaseType type2 = new PhaseType(102, "type102");

        manager.registerHandler(new StressMockPhaseHandler(true), type1, PhaseOperationEnum.END);
        manager.registerHandler(new StressMockPhaseHandler(false), type2, PhaseOperationEnum.END);
        phase.setPhaseType(type1);
        for (int i = 0; i < RUN_TIMES; i++) {
            assertTrue("Should return true.", manager.canEnd(phase));
        }
        phase.setPhaseType(type2);
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertFalse("Should return false.", manager.canEnd(phase));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing canEnd(Phase) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
    /**
     * <p>Stress test for DefaultPhaseManager#canEnd(Phase).</p>
     *
     * @throws Exception to junit.
     */
    public void testCanEnd_NonHandle() throws Exception {
        Phase phase = new StressMockPhase();
        phase.setPhaseType(new PhaseType(101, "type101"));
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.canEnd(phase);
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing canEnd(Phase) which has no corresponding handles for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * <p>Stress test for DefaultPhaseManager#end(Phase, String).</p>
     *
     * @throws Exception to junit.
     */
    public void testEnd() throws Exception {
        Phase phase = new StressMockPhase();
        PhaseStatus status = new PhaseStatus(101, "status101");
        phase.setPhaseStatus(status);
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            status.setId(101);
            manager.end(phase, "iven");
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing end(Phase, String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * <p>Stress test for DefaultPhaseManager#canCancel(Phase).</p>
     *
     * @throws Exception to junit.
     */
    public void testCanCancel() throws Exception {
        Phase phase = new StressMockPhase();

        PhaseType type1 = new PhaseType(101, "type101");
        PhaseType type2 = new PhaseType(102, "type102");
        manager.registerHandler(new StressMockPhaseHandler(true), type1, PhaseOperationEnum.CANCEL);
        manager.registerHandler(new StressMockPhaseHandler(false), type2, PhaseOperationEnum.CANCEL);
        phase.setPhaseType(type1);
        for (int i = 0; i < RUN_TIMES; i++) {
            assertTrue("Should return true.", manager.canCancel(phase));
        }
        phase.setPhaseType(type2);
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertFalse("Should return false.", manager.canCancel(phase));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing canCancel(Phase) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
    /**
     * <p>Stress test for DefaultPhaseManager#canCancel(Phase).</p>
     *
     * @throws Exception to junit.
     */
    public void testCanCancel_NonHandle() throws Exception {
        Phase phase = new StressMockPhase();
        phase.setPhaseType(new PhaseType(101, "type101"));
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertTrue("Should return true.", manager.canCancel(phase));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing canCancel(Phase) which has no corresponding handles for " + RUN_TIMES
            + " times costs " + (end - start) + "ms");
    }
    /**
     * <p>Stress test for DefaultPhaseManager#cancel(Phase, String).</p>
     *
     * @throws Exception to junit.
     */
    public void testCancel() throws Exception {
        Phase phase = new StressMockPhase();
        PhaseStatus status = new PhaseStatus(101, "status101");
        phase.setPhaseStatus(status);
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            status.setId(101);
            manager.cancel(phase, "iven");
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing cancel(Phase, String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
    /**
     * <p>
     * Stress test for DefaultPhaseManager#registerHandler(PhaseHandler, PhaseType, PhaseOperationEnum)
     * and DefaultPhaseManager#unregisterHandler(PhaseType, PhaseOperationEnum).
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testRegisterAndUnregisterHandler() throws Exception {
        PhaseType type1 = new PhaseType(101, "type101");
        PhaseType type2 = new PhaseType(101, "type102");
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.registerHandler(new StressMockPhaseHandler(true), type1, PhaseOperationEnum.START);
            manager.registerHandler(new StressMockPhaseHandler(false), type2, PhaseOperationEnum.END);
            assertTrue("Should return true.", manager.unregisterHandler(type1, PhaseOperationEnum.START).canPerform(
                 null));
            assertFalse("Should return false.", manager.unregisterHandler(type2, PhaseOperationEnum.END).canPerform(
                 null));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing registerHandler and unregisterHandler for " + (RUN_TIMES + RUN_TIMES)
                + " times costs " + (end - start) + "ms");
        assertEquals("Should have no handle left.", manager.getAllHandlers().length, 0);

    }
    /**
     * <p>Stress test for DefaultPhaseManager#testGetAllHandlers().</p>
     *
     * @throws Exception to junit.
     */
    public void testGetAllHandlers() throws Exception {
        for (int i = 0; i < DATA_COUNT; i++) {
            manager.registerHandler(new StressMockPhaseHandler(true),
                new PhaseType(i + 100, "phasetype"), PhaseOperationEnum.START);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertEquals("Should get " + DATA_COUNT + " handles.", manager.getAllHandlers().length, DATA_COUNT);
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing getAllHandlers() for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * <p>Stress test for DefaultPhaseManager#getHandlerRegistrationInfo(PhaseHandle).</p>
     *
     */
    public void testGetHandlerRegistrationInfo() {
        PhaseHandler handle = new StressMockPhaseHandler(true);
        for (int i = 0; i < DATA_COUNT; i++) {
            manager.registerHandler(handle,
                new PhaseType(i + 100, "phasetype"), PhaseOperationEnum.START);
        }

        for (int i = 0; i < RUN_TIMES; i++) {
            assertEquals("Should return " + DATA_COUNT + " handle registration informations.",
                manager.getHandlerRegistrationInfo(handle).length, DATA_COUNT);
        }
    }
    /**
     * <p>
     * Mock class for PhaseValidator, this class doesn't do any validation.
     * </p>
     *
     * @author still
     * @version 1.0
     */
    private class StressMockPhaseValidator implements PhaseValidator {

        /**
         * <p>
         * Validates the input phase. This mock method doesn't do any validation.
         * </p>
         *
         * @param phase
         *            phase to validate
         * @throws PhaseValidationException
         *             if validation fails
         */
        public void validate(Phase phase) throws PhaseValidationException {
            // empty validate method
        }

    }
    /**
     * <p>
     * Mock class for PhaseHandler, this class doesn't do any operation.
     * </p>
     *
     * @author still
     * @version 1.0
     */
    private class StressMockPhaseHandler implements PhaseHandler {
        /** the boolean value canPerform will return. */
        private boolean canPerform;

        /**
         * Default constructor performs canPerform initialization.
         *
         * @param canPerform the boolean value to be set to this.canPerform.
         */
        public StressMockPhaseHandler(boolean canPerform) {
            this.canPerform = canPerform;
        }

        /**
         * Returns the value of canPerform which is initialized in default constructor.
         * @param phase not used
         * @return the value of canPerform.
         */
        public boolean canPerform(Phase phase) {
            return canPerform;
        }

        /**
         * Empty mock method doesn't do any operation.
         * @param phase not used.
         * @param operator not used.
         */
        public void perform(Phase phase, String operator) {
            // empty perform method
        }
    }

}
