/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.accuracytests;

import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.management.phase.DefaultPhaseManager;
import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhasePersistence;
import com.topcoder.management.phase.PhaseValidator;
import com.topcoder.management.phase.validation.DefaultPhaseValidator;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>
 * Accuracy test fixture for DefaultPhaseManager class.
 * </p>
 * @aut\ Thinfox
 * @version 1.0
 */
public class DefaultPhaseManagerTests extends TestCase {
    /**
     * The configuration file used for tests.
     */
    private static final String CONFIG_FILE = "accuracy/config.xml";

    /**
     * The configuration namespace used for tests.
     */
    private static final String NAMESPACE = DefaultPhaseManager.class.getName();

    /**
     * The default PhaseValidator used for tests.
     */
    private PhaseValidator validator = null;

    /**
     * The default DefaultPhaseManager instance on which to perform tests.
     */
    private DefaultPhaseManager manager = null;

    /**
     * The default PhasePersistence instance used for tests.
     */
    private PhasePersistence persistence = null;

    /**
     * The default PhaseType instance used for tests.
     */
    private PhaseType type = null;

    /**
     * The default Project instance used for tests.
     */
    private Project project = null;

    /**
     * The default Phase instance used for tests.
     */
    private Phase phase = null;

    /**
     * The default IDGenerator instance used for tests.
     */
    private IDGenerator idGenerator = null;

    /**
     * Sets up the test environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIG_FILE);

        persistence = new MockPhasePersistence();
        idGenerator = new MockIDGenerator("test");
        manager = new DefaultPhaseManager(persistence, idGenerator);

        type = new PhaseType(1, "type");

        // Creating the Phase instance.
        Workdays workdays = new DefaultWorkdaysFactory().createWorkdaysInstance();
        project = new Project(new Date(), workdays);
        project.setId(1);

        phase = new Phase(project, 4);
        phase.setId(1);
        Date now = new Date();
        phase.setScheduledStartDate(now);
        phase.setScheduledEndDate(now);
        PhaseStatus status = new PhaseStatus(1, "status");
        phase.setPhaseStatus(status);
        phase.setPhaseType(type);
    }

    /**
     * Cleans up the test environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Tests the DefaultPhaseManager(String) constructor.
     * @throws Exception to JUnit
     */
    public void testCtor_String() throws Exception {
        manager = new DefaultPhaseManager(NAMESPACE);
        assertNotNull("Creation failed.", manager);
        assertTrue("DefaultPhaseManager should implement PhaseManager interface.",
            manager instanceof PhaseManager);
    }

    /**
     * Tests the DefaultPhaseManager(PhasePersistence, IDGenerator) constructor.
     */
    public void testCtor_PhasePersistentIDGenerator() {
        manager = new DefaultPhaseManager(persistence, idGenerator);
        assertNotNull("Creation failed.", manager);
        assertTrue("DefaultPhaseManager should implement PhaseManager interface.",
            manager instanceof PhaseManager);
    }

    /**
     * Tests the canCancel(Phase) method.
     * @throws Exception to JUnit
     */
    public void testCanCancel() throws Exception {
        assertTrue("The phase can be canceled.", manager.canCancel(phase));
        manager.registerHandler(new MockPhaseHandler(false), type, PhaseOperationEnum.CANCEL);
        assertFalse("The phase cannot be canceled.", manager.canCancel(phase));
        manager.registerHandler(new MockPhaseHandler(true), type, PhaseOperationEnum.CANCEL);
        assertTrue("The phase can be canceled.", manager.canCancel(phase));
    }

    /**
     * Tests the canStart(Phase) method.
     * @throws Exception to JUnit
     */
    public void testCanStart() throws Exception {
        manager.registerHandler(new MockPhaseHandler(false), type, PhaseOperationEnum.START);
        assertFalse("The phase cannot be started.", manager.canStart(phase));
        manager.registerHandler(new MockPhaseHandler(true), type, PhaseOperationEnum.START);
        assertTrue("The phase can be started.", manager.canStart(phase));
    }

    /**
     * Tests the canEnd(Phase) method.
     * @throws Exception to JUnit
     */
    public void testCanEnd() throws Exception {
        manager.registerHandler(new MockPhaseHandler(false), type, PhaseOperationEnum.END);
        assertFalse("The phase cannot be ended.", manager.canEnd(phase));
        manager.registerHandler(new MockPhaseHandler(true), type, PhaseOperationEnum.END);
        assertTrue("The phase can be ended.", manager.canEnd(phase));
    }

    /**
     * Tests the start(Phase, String) method.
     * @throws Exception to JUnit.
     */
    public void testStart() throws Exception {
        manager.registerHandler(new MockPhaseHandler(true), type, PhaseOperationEnum.START);
        Date date = new Date(0);
        phase.setActualStartDate(date);
        manager.start(phase, "user");
        assertEquals("Phase status shoud be OPEN", PhaseStatus.OPEN.getId(), phase.getPhaseStatus().getId());
        assertTrue("The actual start date should be updated.", phase.getActualStartDate().after(date));
    }

    /**
     * Tests the end(Phase, String) method.
     * @throws Exception to JUnit.
     */
    public void testEnd() throws Exception {
        manager.registerHandler(new MockPhaseHandler(true), type, PhaseOperationEnum.END);
        Date date = new Date(0);
        phase.setActualEndDate(date);
        manager.end(phase, "user");
        assertEquals("Phase status shoud be CLOSE", PhaseStatus.CLOSED.getId(), phase.getPhaseStatus().getId());
        assertTrue("The actual end date should be updated.", phase.getActualEndDate().after(date));
    }

    /**
     * Tests the cancel(Phase, String) method.
     * @throws Exception to JUnit.
     */
    public void testCancel() throws Exception {
        manager.registerHandler(new MockPhaseHandler(true), type, PhaseOperationEnum.CANCEL);
        Date date = new Date(0);
        phase.setActualEndDate(date);
        manager.cancel(phase, "user");
        assertEquals("Phase status shoud be CLOSE", PhaseStatus.CLOSED.getId(), phase.getPhaseStatus().getId());
        assertTrue("The actual end date should be updated.", phase.getActualEndDate().after(date));
    }

    /**
     * Tests the getAllPhaseTypes() method.
     * @throws Exception to JUnit.
     */
    public void testGetAllPhaseTypes() throws Exception {
        assertNull("Should return null from MockPhasePersistence.", manager.getAllPhaseTypes());
    }

    /**
     * Tests the getAllPhaseStatuses() method.
     * @throws Exception to JUnit.
     */
    public void testGetAllPhaseStatuses() throws Exception {
        assertNull("Should return null from MockPhasePersistence.", manager.getAllPhaseStatuses());
    }

    /**
     * Tests the getPhases(long) method.
     * @throws Exception to JUnit.
     */
    public void testGetPhases() throws Exception {
        assertNull("Should return null from MockPhasePersistence.", manager.getPhases(1));
    }

    /**
     * Tests the getPhases(long[]) method.
     * @throws Exception to JUnit.
     */
    public void testGetPhasesArr() throws Exception {
        assertNull("Should return null from MockPhasePersistence.", manager.getPhases(new long[0]));
    }

    /**
     * Tests the getAllHandlers() method.
     */
    public void testGetAllHandlers() {
        assertEquals("Should return an empty array.", 0, manager.getAllHandlers().length);
        PhaseHandler handler = new MockPhaseHandler(false);
        manager.registerHandler(handler, type, PhaseOperationEnum.START);
        manager.registerHandler(handler, type, PhaseOperationEnum.END);
        PhaseHandler[] handlers = manager.getAllHandlers();
        assertEquals("Incorrect count of handlers.", 1, handlers.length);
        assertEquals("Incorrect handler.", handler, handlers[0]);
    }

    /**
     * Tests the getPhaseValidator() method.
     */
    public void testGetPhaseValidator() {
        PhaseValidator validator = new DefaultPhaseValidator();
        assertTrue("Should return an instance of DefaultPhaseValidator.",
            manager.getPhaseValidator() instanceof DefaultPhaseValidator);
        manager.setPhaseValidator(validator);
        assertEquals("Incorrect PhaseValidator.", validator, manager.getPhaseValidator());
    }

    /**
     * Tests the setPhaseValidator() method.
     */
    public void testSetPhaseValidator() {
        PhaseValidator validator = new DefaultPhaseValidator();
        manager.setPhaseValidator(validator);
        assertEquals("Incorrect PhaseValidator.", validator, manager.getPhaseValidator());
    }

    /**
     * Tests the registerHandler(PhaseHandler, PhaseType, PhaseOperationEnum) method.
     */
    public void testRegisterHandler() {
        PhaseHandler handler = new MockPhaseHandler(false);
        manager.registerHandler(handler, type, PhaseOperationEnum.START);
        PhaseHandler[] handlers = manager.getAllHandlers();
        assertEquals("Incorrect count of handlers.", 1, handlers.length);
        assertEquals("Incorrect handler.", handler, handlers[0]);
        HandlerRegistryInfo[] infos = manager.getHandlerRegistrationInfo(handler);
        assertEquals("Incorrect count of infos.", 1, infos.length);
        assertEquals("Incorrect registry info.", type, infos[0].getType());
        assertEquals("Incorrect registry info.", PhaseOperationEnum.START, infos[0].getOperation());

        manager.registerHandler(handler, type, PhaseOperationEnum.END);
        infos = manager.getHandlerRegistrationInfo(handler);
        assertEquals("Incorrect count of infos.", 2, infos.length);
    }

    /**
     * Tests the unregisterHandler(PhaseType, PhaseOperationEnum) method.
     */
    public void testUnregisterHandler() {
        PhaseHandler handler = new MockPhaseHandler(false);
        manager.registerHandler(handler, type, PhaseOperationEnum.START);
        manager.registerHandler(handler, type, PhaseOperationEnum.END);

        manager.unregisterHandler(type, PhaseOperationEnum.END);
        HandlerRegistryInfo[] infos = manager.getHandlerRegistrationInfo(handler);
        PhaseHandler[] handlers = manager.getAllHandlers();
        assertEquals("Incorrect count of handlers.", 1, handlers.length);
        assertEquals("Incorrect handler.", handler, handlers[0]);
        assertEquals("Incorrect count of infos.", 1, infos.length);
        assertEquals("Incorrect registry info.", type, infos[0].getType());
        assertEquals("Incorrect registry info.", PhaseOperationEnum.START, infos[0].getOperation());
    }

    /**
     * Tests the getHandlerRegistrationInfo(PhaseType, PhaseOperationEnum) method.
     */
    public void testHandlerRegistratioinInfo() {
        PhaseHandler handler = new MockPhaseHandler(false);
        manager.registerHandler(handler, type, PhaseOperationEnum.START);
        HandlerRegistryInfo[] infos = manager.getHandlerRegistrationInfo(handler);
        assertEquals("Incorrect count of infos.", 1, infos.length);
        assertEquals("Incorrect registry info.", type, infos[0].getType());
        assertEquals("Incorrect registry info.", PhaseOperationEnum.START, infos[0].getOperation());

        manager.registerHandler(handler, type, PhaseOperationEnum.END);
        infos = manager.getHandlerRegistrationInfo(handler);
        assertEquals("Incorrect count of infos.", 2, infos.length);
    }

    /**
     * Tests that updatePhases(Prihect, String) method.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhasesValidation() throws Exception {
        try {
            manager.updatePhases(project, "user");
        } catch (NullPointerException e) {
            // ignore
        }
    }
}
