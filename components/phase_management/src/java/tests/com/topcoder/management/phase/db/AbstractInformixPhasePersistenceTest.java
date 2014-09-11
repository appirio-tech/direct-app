/*
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.phase.db;

import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.phase.ConfigurationException;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * Unit tests for the <code>AbstraceInformixPhasePersistence</code> class
 * using the <code>InformixPhasePersistence</code>.
 * </p>
 * <p>
 * <b>Version 1.1 change:</b> Most test cases are from the 1.0 version of
 * <code>InformixPhasePersistenceTest</code>, some test cases are updated because the class
 * has been changed in version 1.1. This test cases class covers the constructor
 * of <code>AbstractDbPhasePersistence</code>.
 * </p>
 * @author kr00tki, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class AbstractInformixPhasePersistenceTest extends DbTestCase {
    /**
     * The creation user constant.
     */
    private static final String CREATION_USER = "creator";

    /**
     * The modification user constant.
     */
    private static final String MODIFICATION_USER = "modificator";

    /**
     * The InformixPhasePersistence instance to test on.
     */
    private InformixPhasePersistence persistence = null;

    /**
     * Sets up test test environment. Most of the work is made in base class.
     * Here only new InformixPhasePersistence is created.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new InformixPhasePersistence(getConnectionFactory(),
                CONNECTION_NAME, getIDGenerator());        
        persistence.setUserManualCommit(true);
        
    }

    /**
     * <p>
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(String)}
     * constructor accuracy. Checks if the fields are properly initialized.
     * </p>
     * <p>
     * <b>Version 1.1 change:</b> The connectionName is not in this class, so
     * the assertion for that field is removed.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructorString() throws Exception {
        persistence = new InformixPhasePersistence(NAMESPACE);
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(String)}
     * constructor failure. Checks if {@link IllegalArgumentException} is thrown
     * when the <code>namespace</code> is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testConstructorString_Null() throws Exception {
        try {
            new InformixPhasePersistence(null);
            fail("Null namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(String)}
     * constructor failure. Checks if {@link IllegalArgumentException} is thrown
     * when the <code>namespace</code> is empty.
     * @throws Exception to JUnit.
     */
    public void testConstructorString_Empty() throws Exception {
        try {
            new InformixPhasePersistence(" ");
            fail("Empty namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(String)}
     * constructor failure. Checks if the {@link ConfigurationException} is
     * thrown on config error - missing classname property.
     */
    public void testConstructorString_NoClassNameProperty() {
        try {
            new InformixPhasePersistence(NAMESPACE + ".NoClassName");
            fail("Missing class name, CE expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(String)}
     * constructor failure. Checks if the {@link ConfigurationException} is
     * thrown on config error - missing namespace property.
     */
    public void testConstructorString_NoNamepsaceProperty() {
        try {
            new InformixPhasePersistence(NAMESPACE + ".NoNamespace");
            fail("Missing namespace name, CE expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(String)}
     * constructor failure. Checks if the {@link ConfigurationException} is
     * thrown on config error - namespace value is empty.
     */
    public void testConstructorString_EmptyNamepsaceProperty() {
        try {
            new InformixPhasePersistence(NAMESPACE + ".EmptyNamespace");
            fail("Empty namespace name, CE expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(String)}
     * constructor failure. Checks if the {@link ConfigurationException} is
     * thrown on config error - classname is empty.
     */
    public void testConstructorString_EmptyClassnameProperty() {
        try {
            new InformixPhasePersistence(NAMESPACE + ".EmptyClassName");
            fail("Empty class name, CE expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(String)}
     * constructor failure. Checks if the {@link ConfigurationException} is
     * thrown on config error - empty connection name.
     */
    public void testConstructorString_EmptyConnectionName() {
        try {
            new InformixPhasePersistence(NAMESPACE + ".EmptyConnectionName");
            fail("Empty connection name, CE expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(String)}
     * constructor failure. Checks if the {@link ConfigurationException} is
     * thrown on config error - invalid classname.
     */
    public void testConstructorString_InvalidClass() {
        try {
            new InformixPhasePersistence(NAMESPACE + ".InvalidClassName");
            fail("invalid class name, CE expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(DBConnectionFactory, String, IDGenerator)}
     * constructor accuracy. Checks if all internal fields are properly
     * initialized.
     * </p>
     * <p>
     * <b>Version 1.1 change:</b> The connectionFactory and connectionName is
     * not in this class, so the assertion is removed.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructorDBConnectionFactoryString() throws Exception {
        DBConnectionFactory factory = getConnectionFactory();
        persistence = new InformixPhasePersistence(factory, CONNECTION_NAME,
                getIDGenerator());
    }

    /**
     * <p>
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(DBConnectionFactory, String, IDGenerator)}
     * constructor accuracy. Checks if the <code>null</code> connectionName is
     * allowed.
     * </p>
     * <p>
     * <b>Version 1.1 change:</b> The connectionFactory and connectionName is
     * not in this class, so the assertion is removed.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructorDBConnectionFactoryString_NullName()
        throws Exception {
        persistence = new InformixPhasePersistence(getConnectionFactory(),
                null, getIDGenerator());
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(DBConnectionFactory, String, IDGenerator)}
     * constructor failure. Checks if the {@link IllegalArgumentException} is
     * thrown when the connectionFactory is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testConstructorDBConnectionFactoryString_NullFactory()
        throws Exception {
        try {
            new InformixPhasePersistence(null, null, getIDGenerator());
            fail("Null factory, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(DBConnectionFactory, String, IDGenerator)}
     * constructor failure. Checks if the {@link IllegalArgumentException} is
     * thrown when the connectionName is empty.
     * @throws Exception to JUnit.
     */
    public void testConstructorDBConnectionFactoryString_EmptyName()
        throws Exception {
        try {
            new InformixPhasePersistence(getConnectionFactory(), " ",
                    getIDGenerator());
            fail("Empty connection name, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the
     * {@link InformixPhasePersistence#InformixPhasePersistence(DBConnectionFactory, String, IDGenerator)}
     * constructor failure. Checks if the {@link IllegalArgumentException} is
     * thrown when the connectionName is empty.
     * @throws Exception to JUnit.
     */
    public void testConstructorDBConnectionFactoryString_NullGenerator()
        throws Exception {
        try {
            new InformixPhasePersistence(getConnectionFactory(), "x", null);
            fail("Null Id generator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#getProjectPhases(long)} method
     * accuracy. Checks if correct phases for each project are retrieved.
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhasesLong() throws Exception {
        // create test phases and store them
        Phase[] phases = new Phase[] {createPhase(1, 1, true),
                createPhase(2, 1, true), createPhase(3, 2, true) };
        phases[0].setAttribute(CRITERIA, "value");
        phases[0].setAttribute(CRITERIA1, "value2");
        phases[1].setAttribute(CRITERIA3, "value3");
        persistence.createPhases(phases, CREATION_USER);

        // get the phases for project with id = 0
        Project project = persistence.getProjectPhases(1);
        assertNotNull("Project should exists.", project);
        assertEquals("Should have 2 phases.", 2, project.getAllPhases().length);
        assertEquals("Incorrect project start date.", phases[0]
                .getScheduledStartDate(), project.getStartDate());

        if (project.getAllPhases()[0].getId() == phases[0].getId()) {
            assertEquals(phases[0], project.getAllPhases()[0]);
            assertEquals(phases[1], project.getAllPhases()[1]);
        }
        if (project.getAllPhases()[0].getId() == phases[1].getId()) {
            assertEquals(phases[0], project.getAllPhases()[1]);
            assertEquals(phases[1], project.getAllPhases()[0]);
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#getProjectPhases(long)} method
     * accuracy. Checks if null value is returned when the project not exists.
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhases_NoSuchProject() throws Exception {
        assertNull("Project not exists.", persistence.getProjectPhases(-10));
    }

    /**
     * Tests the {@link InformixPhasePersistence#getProjectPhases(long)} method
     * accuracy. Checks if empty project (without any phase) is retrieved.
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhases_ProjectWithoutPhases() throws Exception {
        Project project = persistence.getProjectPhases(5);
        assertNotNull("Project should exist", project);
        assertEquals("Should have no phases", 0, project.getAllPhases().length);
    }

    /**
     * Tests the {@link InformixPhasePersistence#getProjectPhases(long[])}
     * method accuracy. Checks if all requested Projects are returned and if the
     * null is returned for projects that do not exists.
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhases() throws Exception {
        // create test phases and store them
        Phase[] phases = new Phase[] {createPhase(1, 1, true),
                createPhase(2, 1, true), createPhase(3, 2, true) };
        persistence.createPhases(phases, CREATION_USER);

        Project[] projects = persistence.getProjectPhases(new long[] {1, 2, 3,
            6 });
        assertEquals("Should have 4 projects", 4, projects.length);
        assertEquals("Should have 2 phases", 2,
                projects[0].getAllPhases().length);
        assertEquals("Should have 1 phase", 1,
                projects[1].getAllPhases().length);
        assertEquals("Should have 0 phases", 0,
                projects[2].getAllPhases().length);
        assertNull("Should be null.", projects[3]);
    }

    /**
     * Tests the {@link InformixPhasePersistence#getProjectPhases(long[])}
     * method accuracy. Checks if empty array is returned on empty input array.
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhasesLongArray_EmptyArray() throws Exception {
        assertEquals("Should return empty array", 0, persistence
                .getProjectPhases(new long[0]).length);
    }

    /**
     * Tests the {@link InformixPhasePersistence#getProjectPhases(long[])}
     * method failure. Checks if {@link IllegalArgumentException} is thrown when
     * the <code>array</code> is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhasesLongArray_NullArray() throws Exception {
        try {
            persistence.getProjectPhases(null);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#getAllPhaseTypes()} method
     * accuracy. Checks if all predefined phase types are returned.
     * @throws Exception to JUnit.
     */
    public void testGetAllPhaseTypes() throws Exception {
        PhaseType[] types = persistence.getAllPhaseTypes();
        assertEquals("Should have 3 types", 3, types.length);
        assertEquals("Incorrect type id.", 1, types[0].getId());
        assertEquals("Incorrect type name.", "phase_type_1", types[0].getName());

        assertEquals("Incorrect type id.", 2, types[1].getId());
        assertEquals("Incorrect type name.", "phase_type_2", types[1].getName());
        assertEquals("Incorrect type id.", 3, types[2].getId());
        assertEquals("Incorrect type name.", "phase_type_3", types[2].getName());
    }

    /**
     * Tests the {@link InformixPhasePersistence#getAllPhaseStatuses()} method
     * accuracy. Checks if all pre-loaded phase statuses are returned.
     * @throws Exception to JUnit.
     */
    public void testGetAllPhaseStatuses() throws Exception {
        PhaseStatus[] statuses = persistence.getAllPhaseStatuses();
        assertEquals("Should have 3 statuses", 3, statuses.length);
        assertEquals("Incorrect type id.", 1, statuses[0].getId());
        assertEquals("Incorrect type name.", "phase_status_1", statuses[0]
                .getName());

        assertEquals("Incorrect type id.", 2, statuses[1].getId());
        assertEquals("Incorrect type name.", "phase_status_2", statuses[1]
                .getName());
        assertEquals("Incorrect type id.", 3, statuses[2].getId());
        assertEquals("Incorrect type name.", "phase_status_3", statuses[2]
                .getName());
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhase(Phase, String)}
     * method accuracy. Checks if the phase, its dependencies and criteria are
     * properly inserted into database.
     * @throws Exception to JUnit.
     */
    public void testCreatePhase() throws Exception {
        Phase phase1 = createPhase(1, 1, true);
        Phase phase2 = createPhase(2, 1, true);
        phase2.addDependency(new Dependency(phase1, phase2, true, false, -100));
        Phase phase3 = createPhase(3, 1, true);
        phase3.addDependency(new Dependency(phase1, phase3, false, false, 100));
        phase3.addDependency(new Dependency(phase2, phase3, true, true, 0));

        phase3.setAttribute(CRITERIA, "value1");
        phase3.setAttribute(CRITERIA1, "value2");
        phase1.setAttribute(CRITERIA3, "value3");

        persistence.createPhase(phase1, CREATION_USER);
        persistence.createPhase(phase2, CREATION_USER);
        persistence.createPhase(phase3, CREATION_USER);

        assertDatabase(phase1, CREATION_USER);
        assertDatabase(phase2, CREATION_USER);
        assertDatabase(phase3, CREATION_USER);
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhase(Phase, String)}
     * method accuracy. Checks if phase is created with only required field. No
     * exception should be thrown on null optional values.
     * @throws Exception to JUnit.
     */
    public void testCreatePhase_OnlyRequired() throws Exception {
        Phase phase = createPhase(1, 1, false);

        persistence.createPhase(phase, CREATION_USER);
        assertDatabase(phase, CREATION_USER);
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhase(Phase, String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>phase</code> is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testCreatePhase_Null() throws Exception {
        try {
            persistence.createPhase(null, CREATION_USER);
            fail("Null phase, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhase(Phase, String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>operator</code> is null.
     * @throws Exception to JUnit.
     */
    public void testCreatePhase_NullOperator() throws Exception {
        try {
            persistence.createPhase(createPhase(1, 1, true), null);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhase(Phase, String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>operator</code> is empty.
     * @throws Exception to JUnit.
     */
    public void testCreatePhase_EmptyOperator() throws Exception {
        try {
            persistence.createPhase(createPhase(1, 1, true), " ");
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhases(Phase[], String)}
     * method accuracy. Checks if all the phases are properly created in the
     * database.
     * @throws Exception to JUnit.
     */
    public void testCreatePhases() throws Exception {
        Phase[] phases = new Phase[7];
        for (int i = 0; i < phases.length; i++) {
            phases[i] = createPhase(i, 1, i % 2 == 0);
        }

        phases[0].addDependency(new Dependency(phases[1], phases[0], true,
                false, 0));
        phases[0].addDependency(new Dependency(phases[2], phases[0], true,
                false, 0));
        phases[1].addDependency(new Dependency(phases[3], phases[1], true,
                false, 0));
        phases[1].addDependency(new Dependency(phases[4], phases[1], true,
                false, 0));
        phases[2].addDependency(new Dependency(phases[5], phases[2], true,
                false, 0));
        phases[4].addDependency(new Dependency(phases[6], phases[4], true,
                false, 0));
        phases[5].addDependency(new Dependency(phases[6], phases[5], true,
                false, 0));

        persistence.createPhases(phases, CREATION_USER);
        for (int i = 0; i < phases.length; i++) {
            assertDatabase(phases[i], CREATION_USER);
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhases(Phase[], String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>phases</code> is null.
     * @throws Exception to JUnit.
     */
    public void testCreatePhases_NullArray() throws Exception {
        try {
            persistence.createPhases(null, CREATION_USER);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhases(Phase[], String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>operator</code> is null.
     * @throws Exception to JUnit.
     */
    public void testCreatePhases_NullOperator() throws Exception {
        try {
            persistence.createPhases(new Phase[0], null);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhases(Phase[], String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>operator</code> is empty.
     * @throws Exception to JUnit.
     */
    public void testCreatePhases_EmptyOperator() throws Exception {
        try {
            persistence.createPhases(new Phase[0], " ");
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#createPhases(Phase[], String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>phases</code> array contains null.
     * @throws Exception to JUnit.
     */
    public void testCreatePhases_ArrayWithNull() throws Exception {
        try {
            persistence.createPhases(new Phase[] {null}, CREATION_USER);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#getPhase(long)} method
     * accuracy. Checks if expected phase is retrieved from the database.
     * @throws Exception to JUnit.
     */
    public void testGetPhase() throws Exception {
        Phase phase1 = createPhase(1, 1, true);
        phase1.setAttribute(CRITERIA, "value1");
        Phase phase2 = createPhase(2, 1, true);
        phase2.addDependency(new Dependency(phase1, phase2, true, false, -100));

        persistence.createPhases(new Phase[] {phase1, phase2}, CREATION_USER);

        Phase retrieved = persistence.getPhase(phase1.getId());
        assertEquals(phase1, retrieved);
        retrieved = persistence.getPhase(phase2.getId());
        assertEquals(phase2, retrieved);
    }

    /**
     * Tests the {@link InformixPhasePersistence#getPhase(long)} method
     * accuracy. Checks of correct phases are retrieved from the database with
     * their dependencies and criteria.
     * @throws Exception to JUnit.
     */
    public void testGetPhaseWithCriteria() throws Exception {
        Phase phase1 = createPhase(1, 1, true);
        Phase phase2 = createPhase(2, 1, true);
        phase2.addDependency(new Dependency(phase1, phase2, true, false, -100));
        phase1.setAttribute(CRITERIA, "value1");
        phase2.setAttribute(CRITERIA, "value1");

        persistence.createPhases(new Phase[] {phase1, phase2 }, CREATION_USER);

        Phase retrieved = persistence.getPhase(phase1.getId());
        assertEquals(phase1, retrieved);
        retrieved = persistence.getPhase(phase2.getId());
        assertEquals(phase2, retrieved);
    }

    /**
     * Tests the {@link InformixPhasePersistence#getPhase(long)} method
     * accuracy. Checks if null is returned when the phase with given id doesn't
     * exists.
     * @throws Exception to JUnit.
     */
    public void testGetPhase_NoSuchPhase() throws Exception {
        assertNull("Phase not exists", persistence.getPhase(10));
    }

    /**
     * Tests the {@link InformixPhasePersistence#getPhases(long[])} method
     * accuracy. Checks if all expected phases are retrieved.
     * @throws Exception to JUnit.
     */
    public void testGetPhases() throws Exception {
        Phase[] phases = new Phase[20];
        for (int i = 0; i < phases.length; i++) {
            phases[i] = createPhase(i, 1, i % 2 == 0);
            for (int j = 0; j < i; j++) {
                phases[i].addDependency(new Dependency(phases[j], phases[i],
                        false, true, i));
            }
        }

        persistence.createPhases(phases, CREATION_USER);

        for (int i = 0; i < phases.length; i++) {
            assertEquals(phases[i], persistence.getPhase(phases[i].getId()));
        }

    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhase(Phase, String)}
     * method accuracy. Checks if the phase data are updated in the database.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhase() throws Exception {
        Phase phase = createPhase(1, 1, false);
        Phase dep = createPhase(2, 1, false);
        phase.setAttribute(CRITERIA, "value");

        persistence.createPhase(phase, CREATION_USER);
        persistence.createPhase(dep, CREATION_USER);
        assertDatabase(phase, CREATION_USER);

        phase.setActualEndDate(new Date());
        phase.setFixedStartDate(new Date());
        phase.addDependency(new Dependency(dep, phase, false, false, 0));
        phase.setAttribute(CRITERIA, "value1");
        phase.setAttribute(CRITERIA3, "value3");

        persistence.updatePhase(phase, MODIFICATION_USER);
        assertDatabase(phase, MODIFICATION_USER);
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhase(Phase, String)}
     * method accuracy. Checks if the phase data are updated in the database.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhase_Dependencies() throws Exception {
        Phase phase = createPhase(1, 1, false);
        Phase phase1 = createPhase(2, 1, false);
        Phase phase2 = createPhase(2, 1, false);
        phase.setAttribute(CRITERIA, "value");

        Dependency dep1 = new Dependency(phase1, phase, false, false, 0);
        Dependency dep2 = new Dependency(phase2, phase, false, true, 10);

        phase.addDependency(dep1);
        phase.addDependency(dep2);

        persistence.createPhases(new Phase[] {phase, phase1, phase2 },
                CREATION_USER);
        assertDatabase(phase, CREATION_USER);

        phase.setActualEndDate(new Date());
        phase.setFixedStartDate(new Date());

        phase.setAttribute(CRITERIA, "value1");
        phase.setAttribute(CRITERIA3, "value3");

        phase.removeDependency(dep2);
        dep1.setDependentStart(true);
        dep1.setLagTime(99988);

        persistence.updatePhase(phase, MODIFICATION_USER);
        assertDatabase(phase, MODIFICATION_USER);
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhase(Phase, String)}
     * method accuracy. Checks if the new phase will be stored in the database.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhase_Create() throws Exception {
        Phase phase = createPhase(0, 1, false);
        Phase dep = createPhase(2, 1, false);
        phase.addDependency(new Dependency(dep, phase, false, false, 0));

        persistence.createPhase(dep, CREATION_USER);
        assertNotExist(phase);

        persistence.updatePhase(phase, MODIFICATION_USER);
        assertDatabase(phase, MODIFICATION_USER);
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhase(Phase, String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>phase</code> is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhase_NullPhase() throws Exception {
        try {
            persistence.updatePhase(null, MODIFICATION_USER);
            fail("Null phase, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhase(Phase, String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>operator</code> is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhase_NullOperator() throws Exception {
        try {
            persistence.updatePhase(createPhase(1, 1, true), null);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhase(Phase, String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>operator</code> is <code>empty</code>.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhase_EmptyOperator() throws Exception {
        try {
            persistence.updatePhase(createPhase(1, 1, true), " ");
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhases(Phase[], String)}
     * method accuracy. Checks if all the phases and their depedencies are
     * updated correctly.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhases() throws Exception {
        Phase[] phases = new Phase[7];
        for (int i = 0; i < phases.length; i++) {
            phases[i] = createPhase(i, 1, false);
        }

        phases[6].addDependency(new Dependency(phases[1], phases[6], false,
                false, 1));
        persistence.createPhases(phases, CREATION_USER);

        for (int i = 0; i < phases.length; i++) {
            assertDatabase(phases[i], CREATION_USER);
        }

        for (int i = 0; i < phases.length; i++) {
            phases[i].setLength(i);
            phases[i].setActualStartDate(new Date());
            if (i > i) {
                phases[i].addDependency(new Dependency(phases[i - 1],
                        phases[i], false, false, 1));
            }
        }

        phases[6].clearDependencies();
        persistence.updatePhases(phases, MODIFICATION_USER);

        for (int i = 0; i < phases.length; i++) {
            assertDatabase(phases[i], MODIFICATION_USER);
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhases(Phase[], String)}
     * method accuracy. Checks if all the phases will be updated in the
     * persistence.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhases2() throws Exception {
        Phase[] phases = new Phase[20];
        for (int i = 0; i < phases.length; i++) {
            phases[i] = createPhase(i, 1, i % 2 == 0);
            for (int j = 0; j < i; j++) {
                phases[i].addDependency(new Dependency(phases[j], phases[i],
                        false, true, i));
            }
        }

        persistence.createPhases(phases, CREATION_USER);

        for (int i = 0; i < phases.length; i++) {
            assertEquals(phases[i], persistence.getPhase(phases[i].getId()));
        }

        for (int i = 0; i < phases.length; i++) {
            phases[i].setFixedStartDate(new Date());
            for (int j = 0; j < i; j++) {
                phases[i].getAllDependencies()[0].setLagTime(1);
                phases[i].getAllDependencies()[0].setDependencyStart(i % 2 == 1);
            }
        }

        persistence.updatePhases(phases, CREATION_USER);

        for (int i = 0; i < phases.length; i++) {
            assertEquals(phases[i], persistence.getPhase(phases[i].getId()));
        }

    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhases(Phase[], String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>phases</code> is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhases_NullArray() throws Exception {
        try {
            persistence.updatePhases(null, MODIFICATION_USER);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhases(Phase[], String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>phases</code> contains <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhases_ArrayWithNull() throws Exception {
        try {
            persistence.updatePhases(new Phase[] {null }, MODIFICATION_USER);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhases(Phase[], String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>operator</code> is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhases_NullOperator() throws Exception {
        try {
            persistence.updatePhases(new Phase[] {createPhase(1, 1, false) },
                    null);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#updatePhases(Phase[], String)}
     * method failure. Checks if the {@link IllegalArgumentException} is thrown
     * when the <code>operator</code> is <code>empty</code>.
     * @throws Exception to JUnit.
     */
    public void testUpdatePhases_EmptyOperator() throws Exception {
        try {
            persistence.updatePhases(new Phase[] {createPhase(1, 1, false) },
                    " ");
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#deletePhase(Phase)} method
     * accuracy. Checks if the phase and their dependencies are removed from the
     * database.
     * @throws Exception to JUnit.
     */
    public void testDeletePhase() throws Exception {
        Phase phase1 = createPhase(1, 1, true);
        Phase phase2 = createPhase(2, 1, true);
        phase2.addDependency(new Dependency(phase1, phase2, true, false, -100));

        persistence.createPhase(phase1, CREATION_USER);
        persistence.createPhase(phase2, CREATION_USER);
        assertDatabase(phase2, CREATION_USER);

        // delete phase
        persistence.deletePhase(phase2);
        assertNotExist(phase2);
    }

    /**
     * Tests the {@link InformixPhasePersistence#deletePhase(Phase)} method
     * failure. Checks if the {@link IllegalArgumentException} is thrown when
     * the <code>phase</code> is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testDeletePhase_NullPhase() throws Exception {
        try {
            persistence.deletePhase(null);
            fail("Null phase, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#deletePhases(Phase[])} method
     * accuracy. Checks if all the phases, dependencies and criteria are removed.
     * @throws Exception to JUnit.
     */
    public void testDeletePhases() throws Exception {
        Phase[] phases = new Phase[7];
        for (int i = 0; i < phases.length; i++) {
            phases[i] = createPhase(i, 1, true);
        }

        phases[0].addDependency(new Dependency(phases[1], phases[0], true,
                false, 0));
        phases[1].addDependency(new Dependency(phases[3], phases[1], false,
                false, 0));
        phases[2].addDependency(new Dependency(phases[5], phases[2], true,
                true, 0));
        phases[4].addDependency(new Dependency(phases[6], phases[4], true,
                false, 0));
        phases[4].setAttribute(CRITERIA, "test");

        persistence.createPhases(phases, CREATION_USER);
        persistence.deletePhases(phases);

        for (int i = 0; i < phases.length; i++) {
            assertNotExist(phases[i]);
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#deletePhases(Phase[])} method
     * failure. Checks if the {@link IllegalArgumentException} is thrown when
     * the <code>phases</code> is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testDeletePhases_NullArray() throws Exception {
        try {
            persistence.deletePhases(null);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#deletePhases(Phase[])} method
     * failure. Checks if the {@link IllegalArgumentException} is thrown when
     * the <code>phases</code> contains <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testDeletePhases_ArrayWithNull() throws Exception {
        try {
            persistence.deletePhases(new Phase[] {null });
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#isNewPhase(Phase)} method
     * accuracy. Checks if the phase with id = 0 is classified as new.
     */
    public void testIsNewPhase() {
        assertTrue("Should be new phase", persistence.isNewPhase(createPhase(0,
                1, true)));
        assertFalse("Should not be new phase", persistence
                .isNewPhase(createPhase(1, 1, true)));
    }

    /**
     * Tests the {@link InformixPhasePersistence#isNewPhase(Phase)} method
     * failure. Checks if the {@link IllegalArgumentException} is thrown when
     * the <code>phase</code> is <code>null</code>.
     */
    public void testIsNewPhase_NullPhase() {
        try {
            persistence.isNewPhase(null);
            fail("Null phase, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixPhasePersistence#isNewDependency(Dependency)}
     * method accuracy. Checks if <code>false</code> is returned if the
     * dependency is new.
     * @throws Exception to JUnit.
     */
    public void testIsNewDependency() throws Exception {
        Dependency dep = new Dependency(createPhase(1, 1, true), createPhase(1,
                1, false), true, false, 0);
        assertTrue("It should be new dependency", persistence
                .isNewDependency(dep));
    }

    /**
     * Tests the {@link InformixPhasePersistence#isNewDependency(Dependency)}
     * method accuracy. Checks if <code>true</code> is returned if the
     * dependency already exists.
     * @throws Exception to JUnit.
     */
    public void testIsNewDependency_False() throws Exception {
        Dependency dep = new Dependency(createPhase(1, 1, true), createPhase(1,
                1, false), true, false, 0);
        persistence.createPhases(new Phase[] {dep.getDependency(),
                dep.getDependent() }, CREATION_USER);
        dep.getDependent().addDependency(dep);

        persistence.updatePhase(dep.getDependent(), MODIFICATION_USER);

        assertFalse("It should not be new dependency", persistence
                .isNewDependency(dep));
    }

    /**
     * Tests the {@link InformixPhasePersistence#isNewDependency(Dependency)}
     * method failure. Checks if {@link IllegalArgumentException} is thrown when
     * the dependency is <code>null</code>.
     * @throws Exception to JUnit.
     */
    public void testIsNewDependency_Null() throws Exception {
        try {
            persistence.isNewDependency(null);
            fail("Null dependency, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
}
