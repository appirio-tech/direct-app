/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.util.idgenerator.IDGenerator;

import com.topcoder.util.config.ConfigManager;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;

import java.util.Date;
import java.util.Iterator;

/**
 * Test suite for DefaultPhaseManager.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class DefaultPhaseManagerTests extends PhaseManagementTestCase {
    /**
     * A phase validator that does nothing.
     */
    private final PhaseValidator simpleValidator = new PhaseValidator() {
        public void validate(Phase phase) {
        }
    };

    /**
     * A phase handler that does nothing.
     */
    private final PhaseHandler handler1 = new NullPhaseHandler();

    /**
     * A phase handler that does nothing.
     */
    private final PhaseHandler handler2 = new NullPhaseHandler();

    /**
     * A phase handler that does nothing.
     */
    private final PhaseHandler handler3 = new NullPhaseHandler();

    /**
     * A phase handler that does nothing.
     */
    private final PhaseHandler handler4 = new NullPhaseHandler();

    /**
     * Pre-test configuration. This method loads the test configuration file into the ConfigManager.
     *
     * @throws Exception if an error occurs while setting up
     */
    protected void setUp() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        manager.add("config.xml");
    }

    /**
     * Post-test cleanup. This method removes all namespaces from the ConfigManager.
     *
     * @throws Exception if an error occurs while cleaning up
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Tests invalid arguments to setPhaseValidator.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testsetPhaseValidator() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.setPhaseValidator(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        // the successful case is tested as part of testgetPhaseValidator
    }

    /**
     * Tests that setPhaseValidator sets the validator correctly.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetPhaseValidator() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        manager.setPhaseValidator(simpleValidator);
        assertEquals("phase validator should equal the set value", manager.getPhaseValidator(), simpleValidator);
    }

    /**
     * Tests the invalid arguments to registerHandler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testregisterHandlerExceptions() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.registerHandler(handler1, PHASE_TYPE_ONE, null);
            fail("registerHandler should check for null args");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.registerHandler(handler1, null, PhaseOperationEnum.START);
            fail("registerHandler should check for null args");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.registerHandler(null, PHASE_TYPE_ONE, PhaseOperationEnum.START);
            fail("registerHandler should check for null args");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests successful operation of registerHandler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testregisterHandler() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        manager.unregisterHandler(new PhaseType(1, "type1"), PhaseOperationEnum.START);

        // register several handlers
        manager.registerHandler(handler1, PHASE_TYPE_ONE, PhaseOperationEnum.START);
        manager.registerHandler(handler2, PHASE_TYPE_ONE, PhaseOperationEnum.END);
        manager.registerHandler(handler3, PHASE_TYPE_TWO, PhaseOperationEnum.START);

        // make sure each handler is associated with the expected registry info
        HandlerRegistryInfo[] hri1 = manager.getHandlerRegistrationInfo(handler1);
        assertEquals("registerHandler failed for handler1", hri1.length, 1);
        assertEquals("registerHandler failed for handler1", hri1[0].getType(), PHASE_TYPE_ONE);
        assertEquals("registerHandler failed for handler1", hri1[0].getOperation(), PhaseOperationEnum.START);

        // make sure each handler is associated with the expected registry info
        HandlerRegistryInfo[] hri2 = manager.getHandlerRegistrationInfo(handler2);
        assertEquals("registerHandler failed for handler2", hri2.length, 1);
        assertEquals("registerHandler failed for handler2", hri2[0].getType(), PHASE_TYPE_ONE);
        assertEquals("registerHandler failed for handler2", hri2[0].getOperation(), PhaseOperationEnum.END);

        // make sure each handler is associated with the expected registry info
        HandlerRegistryInfo[] hri3 = manager.getHandlerRegistrationInfo(handler3);
        assertEquals("registerHandler failed for handler3", hri3.length, 1);
        assertEquals("registerHandler failed for handler3", hri3[0].getType(), PHASE_TYPE_TWO);
        assertEquals("registerHandler failed for handler3", hri3[0].getOperation(), PhaseOperationEnum.START);

        // test that calling with the same args replaces the previous handler
        manager.registerHandler(handler4, PHASE_TYPE_ONE, PhaseOperationEnum.START);
        HandlerRegistryInfo[] hri4 = manager.getHandlerRegistrationInfo(handler4);
        assertEquals("registerHandler failed for handler4", hri4.length, 1);
        assertEquals("registerHandler failed for handler4", hri4[0].getType(), PHASE_TYPE_ONE);
        assertEquals("registerHandler failed for handler4", hri4[0].getOperation(), PhaseOperationEnum.START);

        assertEquals("registerHandler didn't replace previous handler",
                     manager.getHandlerRegistrationInfo(handler1).length, 0);

        // test a handler registered for multiple phase/operator combos
        manager.registerHandler(handler4, PHASE_TYPE_ONE, PhaseOperationEnum.END);
        HandlerRegistryInfo[] hri5 = manager.getHandlerRegistrationInfo(handler4);
        assertEquals("registerHandler failed for handler4", hri5.length, 2);

    }

    /**
     * Tests getHandlerRegistrationInfo for a handler that isn't registered.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetHandlerRegistrationInfo() throws Exception {
        // this was mostly tested in testregisterHandler, so here we just check for the null case
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");
        manager.registerHandler(handler1, PHASE_TYPE_ONE, PhaseOperationEnum.START);

        assertEquals("getHandlerRegistrationInfo should return null",
                     manager.getHandlerRegistrationInfo(handler2).length, 0);
    }

    /**
     * Tests that getAllHandlers returns an empty array when there are no handlers.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetAllHandlersEmpty() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");
        PhaseHandler[] handlers = manager.getAllHandlers();
        assertNotNull("getAllHandlers should not return null", handlers);
        assertEquals("getAllHandlers should return empty array", 1, handlers.length);
    }

    /**
     * Tests that getAllHandlers returns the correct values.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetAllHandlers() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");
        manager.registerHandler(handler1, PHASE_TYPE_ONE, PhaseOperationEnum.START);
        manager.registerHandler(handler2, PHASE_TYPE_ONE, PhaseOperationEnum.END);
        manager.registerHandler(handler3, PHASE_TYPE_ONE, PhaseOperationEnum.CANCEL);

        PhaseHandler[] handlers = manager.getAllHandlers();
        assertEquals("getAllHandlers should return 3 handlers", handlers.length, 3);

        // the handlers could be returned in any order
        boolean found = false;
        for (int i = 0; (i < 3) && !found; ++i) {
            if (handlers[i] == handler1) {
                found = true;
            }
        }
        if (!found) {
            fail("getAllHandlers did not return handler1");
        }

        found = false;
        for (int i = 0; (i < 3) && !found; ++i) {
            if (handlers[i] == handler2) {
                found = true;
            }
        }
        if (!found) {
            fail("getAllHandlers did not return handler2");
        }

        found = false;
        for (int i = 0; (i < 3) && !found; ++i) {
            if (handlers[i] == handler3) {
                found = true;
            }
        }
        if (!found) {
            fail("getAllHandlers did not return handler3");
        }
    }

    /**
     * Tests invalid arguments to unregisterHandler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testunregisterHandlerExceptions() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");
        try {
            manager.unregisterHandler(PHASE_TYPE_ONE, null);
            fail("unregisterHandler should check for null arg");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.unregisterHandler(null, PhaseOperationEnum.START);
            fail("unregisterHandler should check for null arg");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests normal operation of unregisterHandler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testunregisterHandler() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        manager.registerHandler(handler1, PHASE_TYPE_ONE, PhaseOperationEnum.START);
        manager.registerHandler(handler2, PHASE_TYPE_ONE, PhaseOperationEnum.END);

        assertEquals("unregisterHandler failed for handler1", handler1,
                     manager.unregisterHandler(PHASE_TYPE_ONE, PhaseOperationEnum.START));
        assertNull("unregisterHandler should return null for handler that's already remove",
                   manager.unregisterHandler(PHASE_TYPE_ONE, PhaseOperationEnum.START));
    }

    /**
     * Tests invalid arguments to canCancel.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcanCancelException() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.canCancel(null);
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests normal operation of canCancel.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcanCancelNoHandler() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");
        assertTrue("canCancel should return true", manager.canCancel(PHASE_ONE));
    }

    /**
     * Tests that canCancel invokes the canPerform method of the phase handler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcanCancelHandler() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");
        manager.registerHandler(ALWAYS_PERFORM, PHASE_TYPE_ONE, PhaseOperationEnum.CANCEL);
        manager.registerHandler(NEVER_PERFORM, PHASE_TYPE_TWO, PhaseOperationEnum.CANCEL);

        assertTrue("canCancel should return true for PHASE_ONE", manager.canCancel(PHASE_ONE));
        assertFalse("canCancel should return false for PHASE_TWO", manager.canCancel(PHASE_TWO));

    }

    /**
     * Tests invalid arguments to canEnd.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcanEndException() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.canEnd(null);
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests normal operation of canEnd.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcanEndNoHandler() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        assertTrue("canEnd should return true for PHASE_ONE", manager.canEnd(PHASE_ONE));
        assertFalse("canEnd should return false for PHASE_TWO", manager.canEnd(PHASE_TWO));
    }

    /**
     * Tests that canEnd calls the canPerform method of the phase handler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcanEndHandler() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");
        manager.registerHandler(NEVER_PERFORM, PHASE_TYPE_ONE, PhaseOperationEnum.END);
        manager.registerHandler(ALWAYS_PERFORM, PHASE_TYPE_TWO, PhaseOperationEnum.END);

        assertFalse("canEnd should return false for PHASE_ONE", manager.canEnd(PHASE_ONE));
        assertTrue("canEnd should return true for PHASE_TWO", manager.canEnd(PHASE_TWO));

    }

    /**
     * Tests invalid arguments to canStart.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcanStartException() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.canStart(null);
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests normal operation of canStart.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcanStartNoHandler() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        assertTrue("canStart should return true for PHASE_ONE", manager.canStart(PHASE_ONE));
        assertFalse("canStart should return false for PHASE_TWO", manager.canStart(PHASE_TWO));
    }

    /**
     * Tests that canStart calls the canPerform method of the phase handler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcanStartHandler() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");
        manager.registerHandler(NEVER_PERFORM, PHASE_TYPE_ONE, PhaseOperationEnum.START);
        manager.registerHandler(ALWAYS_PERFORM, PHASE_TYPE_TWO, PhaseOperationEnum.START);

        assertFalse("canStart should return false for PHASE_ONE", manager.canStart(PHASE_ONE));
        assertTrue("canStart should return true for PHASE_TWO", manager.canStart(PHASE_TWO));

    }

    /**
     * Tests invalid arguments to start.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void teststartExceptions() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.start(PHASE_ONE, null);
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.start(null, "hi");
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.start(PHASE_ONE, "");
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.start(PHASE_ONE, "yo");
            fail("should throw PhaseManagementException");
        } catch (PhaseManagementException ex) {
            // pass
        }
    }

    /**
     * Tests that start makes the correct calls to persistence.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void teststartPersistence() throws Exception {
        final Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);
        final String operator = "wow";

        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase thephase, String theoperator) {
                    if (phase != thephase || !theoperator.equals(operator)) {
                        throw new RuntimeException("unexpected arguments to updatePhase");
                    }
                    setPassed(true);
                }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());
        manager.start(phase, operator);

        assertTrue("should have called PhasePersistence#updatePhase", persistence.getPassed());
    }

    /**
     * Tests that start sets the actual start date.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void teststartSetStartDate() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase phase, String operator) { }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());

        Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);

        manager.start(phase, "yay");
        assertNotNull("start should have set start date", phase.getActualStartDate());
    }

    /**
     * Tests that start sets the phase status to OPEN.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void teststartSetStatus() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase phase, String operator) { }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());

        Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);

        manager.start(phase, "yo");
        assertEquals("start should have set phase status to open",
                     phase.getPhaseStatus().getId(), PhaseStatus.OPEN.getId());
    }

    /**
     * Tests that start invokes the perform method of the phase handler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void teststartHandler() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase phase, String operator) { }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());

        final Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);
        final String operator = "hello";

        final NullPhaseHandler handler = new NullPhaseHandler() {
                public void perform(Phase thephase, String theoperator) {
                    if (thephase != phase || !theoperator.equals(operator)) {
                        throw new RuntimeException("perform called with unexpected arguments");
                    }
                    setPassed(true);
                }
            };

        manager.registerHandler(handler, PHASE_TYPE_ONE, PhaseOperationEnum.START);

        manager.start(phase, operator);
        assertTrue("should have called handler.perform", handler.getPassed());
    }

    /**
     * Tests invalid arguments to end.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testendExceptions() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.end(PHASE_ONE, null);
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.end(null, "hi");
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.end(PHASE_ONE, "");
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.end(PHASE_ONE, "yo");
            fail("should throw PhaseManagementException");
        } catch (PhaseManagementException ex) {
            // pass
        }
    }

    /**
     * Tests that end invokes the appropriate persistence methods.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testendPersistence() throws Exception {
        final Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);
        final String operator = "wow";

        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase thephase, String theoperator) {
                    if (phase != thephase || !theoperator.equals(operator)) {
                        throw new RuntimeException("unexpected arguments to updatePhase");
                    }
                    setPassed(true);
                }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());
        manager.end(phase, operator);

        assertTrue("should have called PhasePersistence#updatePhase", persistence.getPassed());
    }

    /**
     * Tests that end sets the actual end date.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testendSetEndDate() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase phase, String operator) { }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());

        Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);

        manager.end(phase, "yay");
        assertNotNull("end should have set end date", phase.getActualEndDate());
    }

    /**
     * Tests that end sets the phase status to CLOSE.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testendSetStatus() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase phase, String operator) { }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());

        Phase phase = new Phase(PROJECT, 1);
        PhaseStatus open = new PhaseStatus(PhaseStatusEnum.OPEN.getId(), PhaseStatusEnum.OPEN.getName());
        phase.setPhaseStatus(open);
        phase.setPhaseType(PHASE_TYPE_ONE);

        manager.end(phase, "yo");
        assertEquals("end should have set phase status to close",
                     phase.getPhaseStatus().getId(), PhaseStatus.CLOSED.getId());
        assertTrue("the phase status should be set to a new object", open != phase.getPhaseStatus());
    }

    /**
     * Tests that end invokes the perform method of the phase handler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testendHandler() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase phase, String operator) { }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());

        final Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);
        final String operator = "hello";

        final NullPhaseHandler handler = new NullPhaseHandler() {
                public void perform(Phase thephase, String theoperator) {
                    if (thephase != phase || !theoperator.equals(operator)) {
                        throw new RuntimeException("perform called with unexpected arguments");
                    }
                    setPassed(true);
                }
            };

        manager.registerHandler(handler, PHASE_TYPE_ONE, PhaseOperationEnum.END);

        manager.end(phase, operator);
        assertTrue("should have called handler.perform", handler.getPassed());
    }

    /**
     * Tests invalid arguments to cancel.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcancelExceptions() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.cancel(PHASE_ONE, null);
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.cancel(null, "hi");
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.cancel(PHASE_ONE, "");
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.cancel(PHASE_ONE, "yo");
            fail("should throw PhaseManagementException");
        } catch (PhaseManagementException ex) {
            // pass
        }
    }

    /**
     * Tests that cancel invokes the appropriate persistence methods.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcancelPersistence() throws Exception {
        final Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);
        final String operator = "wow";

        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase thephase, String theoperator) {
                    if (phase != thephase || !theoperator.equals(operator)) {
                        throw new RuntimeException("unexpected arguments to updatePhase");
                    }
                    setPassed(true);
                }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());
        manager.cancel(phase, operator);

        assertTrue("should have called PhasePersistence#updatePhase", persistence.getPassed());
    }

    /**
     * Tests that cancel sets the actual end date.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcancelSetCancelDate() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase phase, String operator) { }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());

        Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);

        manager.cancel(phase, "yay");
        assertNotNull("cancel should have set end date", phase.getActualEndDate());
    }

    /**
     * Tests that cancel sets the phase status to CLOSE.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcancelSetStatus() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase phase, String operator) { }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());

        Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);

        manager.cancel(phase, "yo");
        assertEquals("cancel should have set phase status to close",
                     phase.getPhaseStatus().getId(), PhaseStatus.CLOSED.getId());
    }

    /**
     * Tests that cancel invokes the perform method of the phase handler.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testcancelHandler() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public void updatePhase(Phase phase, String operator) { }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());

        final Phase phase = new Phase(PROJECT, 1);
        phase.setPhaseType(PHASE_TYPE_ONE);
        final String operator = "hello";

        final NullPhaseHandler handler = new NullPhaseHandler() {
                public void perform(Phase thephase, String theoperator) {
                    if (thephase != phase || !theoperator.equals(operator)) {
                        throw new RuntimeException("perform called with unexpected arguments");
                    }
                    setPassed(true);
                }
            };

        manager.registerHandler(handler, PHASE_TYPE_ONE, PhaseOperationEnum.CANCEL);

        manager.cancel(phase, operator);
        assertTrue("should have called handler.perform", handler.getPassed());
    }

    /**
     * Tests that persistence exceptions are handled by getAllPhaseStatuses.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetAllPhaseStatusesException() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.getAllPhaseStatuses();
            fail("should have thrown PhaseManagementException");
        } catch (PhaseManagementException ex) {
            // pass
        }
    }

    /**
     * Tests normal operation of getAllPhaseStatuses.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetAllPhaseStatuses() throws Exception {
        final PhaseStatus[] statuses =
            new PhaseStatus[] {PhaseStatus.SCHEDULED, PhaseStatus.OPEN, PhaseStatus.CLOSED};

        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public PhaseStatus[] getAllPhaseStatuses() {
                    return statuses;
                }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());
        assertEquals("bad return value from getAllPhaseStatuses", statuses, manager.getAllPhaseStatuses());
    }

    /**
     * Tests that persistence exceptions are handled properly by getAllPhaseTypes.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetAllPhaseTypesException() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.getAllPhaseTypes();
            fail("should have thrown PhaseManagementException");
        } catch (PhaseManagementException ex) {
            // pass
        }
    }

    /**
     * Tests normal operation of getAllPhaseTypes.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetAllPhaseTypes() throws Exception {
        final PhaseType[] types = new PhaseType[] {PHASE_TYPE_ONE, PHASE_TYPE_TWO};

        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public PhaseType[] getAllPhaseTypes() {
                    return types;
                }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());
        assertEquals("bad return value from getAllPhaseTypes", types, manager.getAllPhaseTypes());
    }

    /**
     * Tests that persistence exceptions are handled properly by getPhases.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetPhasesExceptions() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.getPhases(1);
            fail("should have thrown PhaseManagementException");
        } catch (PhaseManagementException ex) {
            // pass
        }
    }

    /**
     * Tests normal operation of getPhases.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetPhases() throws Exception {
        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public Project getProjectPhases(long id) {
                    return PROJECT;
                }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());
        assertEquals("bad return value from getPhases", PROJECT, manager.getPhases(1));
    }

    /**
     * Tests invalid arguments to getPhases(long[]).
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetPhases2Exceptions() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.getPhases(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.getPhases(new long[] {1, 2, 3});
            fail("should have thrown PhaseManagementException");
        } catch (PhaseManagementException ex) {
            // pass
        }
    }

    /**
     * Tests normal operation of getPhases(long[]).
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testgetPhases2() throws Exception {
        final Project[] projects = new Project[] {PROJECT, PROJECT_TWO};

        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public Project[] getProjectPhases(long[] ids) {
                    return projects;
                }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, new NullIdGenerator());
        assertEquals("bad return value from getPhases", projects, manager.getPhases(new long[] {1, 2}));
    }

    /**
     * Tests invalid arguments to the constructor.
     */
    public void testCtorExceptions() {
        try {
            new DefaultPhaseManager(new NullPhasePersistence(), null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            new DefaultPhaseManager(null, new NullIdGenerator());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests invalid arguments to updatePhases.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testupdatePhasesArguments() throws Exception {
        DefaultPhaseManager manager = new DefaultPhaseManager("test.default");

        try {
            manager.updatePhases(PROJECT, null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.updatePhases(null, "yo");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            manager.updatePhases(PROJECT, "");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that updatePhases performs the appropriate persistence operations and sets the phase IDs.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testupdatePhases() throws Exception {
        final Project project = new Project(new Date(), new DefaultWorkdaysFactory().createWorkdaysInstance());
        final Phase[] phases = new Phase[] {new Phase(project, 1), new Phase(project, 2)};
        final String operator = "op";

        for (int i = 0; i < phases.length; ++i) {
            phases[i].setPhaseStatus(PhaseStatus.OPEN);
            phases[i].setScheduledStartDate(new Date());
            phases[i].setScheduledEndDate(new Date());
            phases[i].setPhaseType(PHASE_TYPE_ONE);
            project.addPhase(phases[i]);
        }

        final NullPhasePersistence persistence = new NullPhasePersistence() {
                public boolean isNewPhase(Phase phase) {
                    return true;
                }

                public Project getProjectPhases(long id) {
                    return new Project(new Date(), new DefaultWorkdaysFactory().createWorkdaysInstance());
                }

                public void createPhases(Phase[] newphases, String newoperator) {
                    if (newphases.length != 2 || newoperator != operator) {
                        throw new RuntimeException("invalid arguments to createPhases");
                    }
                    setPassed(true);
                }
            };

        final IDGenerator idgen = new NullIdGenerator() {
                public long getNextID() { return getNext(); }
            };

        DefaultPhaseManager manager = new DefaultPhaseManager(persistence, idgen);
        manager.updatePhases(project, operator);

        assertTrue("should have called createPhases", persistence.getPassed());
        assertTrue("should have set phase IDs", phases[0].getId() >= 2 && phases[0].getId() <= 3);
        assertTrue("should have set phase IDs", phases[1].getId() >= 2 && phases[1].getId() <= 3);
    }

    /**
     * A specialization of <code>NullPhasePersistence</code> used to test the updatePhases method.
     */
    private class UpdatePhasePersistence extends NullPhasePersistence {
        /**
         * The name of the operator.
         */
        private final String operator = "op";

        /**
         * Tracks whether create, update, and delete were called.
         */
        private final boolean[] methodCalls = new boolean[3];

        /**
         * The project.
         */
        private final Project project = new Project(new Date(), new DefaultWorkdaysFactory().createWorkdaysInstance());

        /**
         * A sample phase to be created.
         */
        private final Phase createMe = new Phase(project, 1);

        /**
         * A sample phase to be deleted.
         */
        private final Phase deleteMe = new Phase(project, 1);

        /**
         * A sample phase to be updated.
         */
        private final Phase updateMe = new Phase(project, 1);

        /**
         * Tests if the specified phase is a new phase (needs its ID set).
         *
         * @throws IllegalArgumentException if phase is <code>null</code>
         *
         * @param phase the phase to check
         * @return <code>true</code> is the phase is new; <code>false</code> otherwise
         */
        public boolean isNewPhase(Phase phase) {
            return true;
        }

        /**
         * Returns the <code>Project</code> associated with the specified ID. If no such project exists, returns
         * <code>null</code>.
         *
         * @param id the ID of the project
         * @return the project, or <code>null</code> if no such project exists
         */
        public Project getProjectPhases(long id) {
            Project p = new Project(new Date(), new DefaultWorkdaysFactory().createWorkdaysInstance());
            p.addPhase(updateMe);
            p.addPhase(deleteMe);

            return p;
        }

        /**
         * Similar to {@link #createPhase(Phase,String) createPhase(Phase,String)}, except creates multiple phases
         * at once.
         *
         * @throws IllegalArgumentException if <code>phases</code> or <code>operator</code> is <code>null</code> or
         *   the empty string
         * @param newphases the phases to create
         * @param newoperator the creating user
         */
        public void createPhases(Phase[] newphases, String newoperator) {
            if (newphases.length != 1 || newoperator != operator || newphases[0] != createMe) {
                throw new RuntimeException("bad arguments to createPhases");
            }
            methodCalls[0] = true;
        }

        /**
         * Similar to {@link #updatePhase(Phase,String) updatePhase(Phase,String)}, except updates multiple phases
         * simultaneously.
         *
         * @throws IllegalArgumentException if <code>phases</code> or <code>operator</code> is <code>null</code> or
         *   the empty string
         *
         * @param newphases the phases to update
         * @param newoperator the operator doing the updating
         */
        public void updatePhases(Phase[] newphases, String newoperator) {
            if (newphases.length != 1 || newoperator != operator || newphases[0] != updateMe) {
                throw new RuntimeException("bad arguments to updatePhases");
            }
            methodCalls[1] = true;
        }

        /**
         * Simlar to {@link #deletePhase(Phase) deletePhase(Phase)}, except deletes multiple phases simultaneously.
         *
         * @throws IllegalArgumentException if <code>phases</code> is <code>null</code>
         *
         * @param newphases the phases to delete
         */
        public void deletePhases(Phase[] newphases) {
            if (newphases.length != 1 || newphases[0] != deleteMe) {
                throw new RuntimeException("bad arguments to deletePhases");
            }
            methodCalls[2] = true;
        }
    }

    /**
     * Tests invalid arguments to the namespace constructor.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testNamespaceCtorArguments() throws Exception {
        try {
            new DefaultPhaseManager(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            new DefaultPhaseManager("");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the namespace constructor when the namespace does not exist.
     */
    public void testNamespaceCtorBadNamespace() {
        try {
            new DefaultPhaseManager("does.not.exist");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests the namespace constructor when the persistence class is missing or cannot be instantiated.
     */
    public void testNamespaceCtorBadPersistence() {
        try {
            new DefaultPhaseManager("test.bad.persistence");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }

        try {
            new DefaultPhaseManager("test.invalid.persistence");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests the namespace constructor when the validator class is missing or cannot be instantiated.
     */
    public void testNamespaceCtorBadValidator() {
        try {
            new DefaultPhaseManager("test.bad.validator");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }

        try {
            new DefaultPhaseManager("test.invalid.validator");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the namespace constructor sets the phase validator appropriately.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void testNamespaceCtorValidator() throws Exception {
        assertEquals("validator class should be TestValidator",
                     new DefaultPhaseManager("test.validator").getPhaseValidator().getClass().getName(),
                     "com.topcoder.management.phase.TestValidator");
    }

    /**
     * Tests the namespace constructor when the ID generator class is missing or cannot be instantiated.
     */
    public void testNamespaceCtorBadIDgenerator() {
        try {
            new DefaultPhaseManager("test.bad.generator");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }

        try {
            new DefaultPhaseManager("test.invalid.generator");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

}
