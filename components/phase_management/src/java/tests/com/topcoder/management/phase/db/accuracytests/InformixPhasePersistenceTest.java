/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.accuracytests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.phase.db.InformixPhasePersistence;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

/**
 * Accuracy tests for {@link InformixPhasePersistence}.
 * <p>
 * The protected methods of <code>AbstractDbPhasePersistence</code> class and
 * <code>AbstractInformixPhasePersistence</code> class are also tested in this class.
 *
 * @author mayi, Savior
 * @version 1.1
 */
public class InformixPhasePersistenceTest extends TestCase {
    /**
     * Represents a <code>InformixPhasePersistence</code> to test against.
     */
    private InformixPhasePersistence persistence = null;

    /**
     * <p>
     * The id generator.
     * </p>
     */
    private IDGenerator generator = null;

    /**
     * Set up.
     * <p>
     * Load configuration, prepare database, and create the testing instances.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfigurations();
        TestHelper.executeDBScript(TestHelper.SCRIPT_FILL);

        generator = IDGeneratorFactory.getIDGenerator("phase_id_seq");

        persistence =
            new InformixPhasePersistence(
                new DBConnectionFactoryImpl(TestHelper.CONNECTION_FACTORY_NAMESPACE), "informix", generator);
        persistence.setUserManualCommit(true);
    }

    /**
     * Tear down.
     * <p>
     * Clear database, close all connections, clear configurations.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.executeDBScript(TestHelper.SCRIPT_CLEAR);
        TestHelper.closeAllConnections();
        TestHelper.clearAllConfigurations();

        super.tearDown();
    }

    /**
     * Test constructor.
     * <p>
     * We only verify it doesn't throw Exception for valid configuration.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor() throws Exception {
        try {
            persistence = new InformixPhasePersistence(TestHelper.PHASE_PERSISTENCE_NAMESPACE);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Should not throw exception.");
        }

        // in order to validate whether the connection factory created succesfully,
        // we try to delete one phase
        Phase phase = new Phase(new Project(new Date(), new DefaultWorkdays()), 12345);
        phase.setId(6);
        persistence.deletePhase(phase);
    }

    /**
     * Test <code>getProjectPhases(long)</code> for a simply project. There is no dependency relationship
     * between the dependencies.
     * <p>
     * In this case, we mainly focus on whether the fields of phase is retrieved properly.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhasesLong_Simple() throws Exception {
        Project project = persistence.getProjectPhases(2);

        // check the project
        assertEquals("Failed to set project id.", 2, project.getId());
        assertEquals("Failed to set startDate", parseDate("2006-01-01 11:11:11.111"), project.getStartDate());
        Phase[] phases = project.getAllPhases();
        assertEquals("Should contain 2 phases.", 2, phases.length);

        // pick a phase instance to check its fields
        Phase phase = null;
        for (int i = 0; i < phases.length; ++i) {
            if (phases[i].getId() == 5) {
                phase = phases[i];
            }
        }
        assertEquals("Failed to set project of phase.", project, phase.getProject());
        assertEquals("Failed to set phase status.", 3, phase.getPhaseStatus().getId());
        assertEquals("Failed to set phase type.", 1, phase.getPhaseType().getId());
        assertEquals("Failed to set fixedStartTime.", parseDate("2006-01-01 11:11:11.222"), phase
            .getFixedStartDate());
        assertEquals("Failed to set scheduledStartTime", parseDate("2006-01-01 11:11:11.333"), phase
            .getScheduledStartDate());
        assertEquals("Failed to set scheduledEndTime", parseDate("2006-01-01 11:11:22.444"), phase
            .getScheduledEndDate());
        assertEquals("Failed to set actualStartTime", parseDate("2006-01-01 11:11:11.555"), phase
            .getActualStartDate());
        assertEquals("Failed to set actualEndTime", parseDate("2006-01-01 11:11:11.666"), phase
            .getActualEndDate());
        assertEquals("Failed to set length", 6000, phase.getLength());
    }

    /**
     * Test <code>getProjectPhases(long)</code> for a simply project. There are some depedencies between the
     * phases.
     * <p>
     * In this case, we mainly focus on whether the dependencies retrieved properly.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhasesLong_Complex() throws Exception {
        Project project = persistence.getProjectPhases(1);

        // check the project
        assertEquals("Failed to set project id.", 1, project.getId());
        assertEquals("Failed to set startDate", parseDate("2006-01-01 11:11:11.111"), project.getStartDate());
        Phase[] phases = project.getAllPhases();
        assertEquals("Should contain 4 phases.", 4, phases.length);

        // chech the dependencies between phases.
        Arrays.sort(phases, new PhaseIdComparator());
        assertEquals("Phase 1 should have no dependency.", 0, phases[0].getAllDependencies().length);

        assertEquals("Phase 2 should have 1 dependency.", 1, phases[1].getAllDependencies().length);
        assertEquals("Phase 2 should depend on Phase 1.", 1, phases[1].getAllDependencies()[0]
            .getDependency().getId());
        assertEquals("Invalid dependency lagtime between 2 and 1.", 12000, phases[1].getAllDependencies()[0]
            .getLagTime());
        assertEquals("Invalid dependencyStart lagtime between 2 and 1.", true,
            phases[1].getAllDependencies()[0].isDependencyStart());
        assertEquals("Invalid dependencyStart lagtime between 2 and 1.", false, phases[1]
            .getAllDependencies()[0].isDependentStart());

        assertEquals("Phase 3 should have 1 dependency.", 1, phases[2].getAllDependencies().length);
        assertEquals("Phase 3 should depend on Phase 2.", 2, phases[2].getAllDependencies()[0]
            .getDependency().getId());
        assertEquals("Invalid dependency lagtime between 3 and 2.", 24000, phases[2].getAllDependencies()[0]
            .getLagTime());

        assertEquals("Phase 4 should have 1 dependency.", 1, phases[1].getAllDependencies().length);
        assertEquals("Phase 4 should depend on Phase 1.", 1, phases[1].getAllDependencies()[0]
            .getDependency().getId());
    }

    /**
     * Test <code>getProjectPhases(long)</code> for an empty project.
     * <p>
     * No phases can be retrieved.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhasesLong_Empty() throws Exception {
        Project project = persistence.getProjectPhases(3);

        // check the project
        assertEquals("Failed to set project id.", 3, project.getId());
        Phase[] phases = project.getAllPhases();
        assertEquals("Should contain 0 phases.", 0, phases.length);

        // The startDate is un-expected currently.
        // assertEquals("Failed to set startDate", new Date(), project.getStartDate());
    }

    /**
     * Test <code>getProjectPhases(long)</code> when the specified project doesn't exist.
     * <p>
     * Null should be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhasesLong_NotExist() throws Exception {
        Project project = persistence.getProjectPhases(33245);
        assertNull("Should return null.", project);
    }

    /**
     * Test <code>getProjectPhases(long[])</code> for a normal case.
     * <p>
     * All the expected projects are not empty.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhasesLongArray_Normal() throws Exception {
        Project[] projects = persistence.getProjectPhases(new long[] {2, 1});

        // validate the sequence.
        assertEquals("1st project should have id=2", 2, projects[0].getId());
        assertEquals("2nd project should have id=1", 1, projects[1].getId());

        // veiry all the phases are retrieved.
        assertEquals("project 2 should have 2 phases.", 2, projects[0].getAllPhases().length);
        assertEquals("project 1 should have 4 phases.", 4, projects[1].getAllPhases().length);

        // randomly pick some phase and dependency to check
        Phase[] phases = projects[1].getAllPhases();
        Arrays.sort(phases, new PhaseIdComparator());
        assertEquals("Failed to set project of phase.", projects[1], phases[0].getProject());
        assertEquals("Failed to set phase status.", 1, phases[0].getPhaseStatus().getId());
        assertEquals("Failed to set phase type.", 1, phases[0].getPhaseType().getId());
        assertEquals("Failed to set length", 6000, phases[0].getLength());
        assertEquals("Failed to get all dependencies.", 1, phases[1].getAllDependencies().length);
        assertEquals("Failed to get dependency.", 2, phases[2].getAllDependencies()[0].getDependency()
            .getId());
        assertEquals("Invalid dependency lagtime between 3 and 2.", 24000, phases[2].getAllDependencies()[0]
            .getLagTime());
    }

    /**
     * Test <code>getProjectPhases(long[])</code> for a special case.
     * <p>
     * The returned array should contain a null project and an empty project.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectPhasesLongArray_Special() throws Exception {
        Project[] projects = persistence.getProjectPhases(new long[] {1234545, 3, 923435});

        // validate the results.
        assertNull("Should return null project.", projects[0]);
        assertNull("Should return null project.", projects[2]);
        assertEquals("Should return empty project.", 0, projects[1].getAllPhases().length);
    }

    /**
     * Test <code>getAllPhaseTypes()</code>.
     * <p>
     * An array of phase types in persistence will be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllPhaseTypes() throws Exception {
        PhaseType[] phaseTypes = persistence.getAllPhaseTypes();
        assertEquals("Should return 2 types.", 2, phaseTypes.length);

        // pick one PhaseType to check the fields
        PhaseType phaseType = null;
        for (int i = 0; i < phaseTypes.length; ++i) {
            if (phaseTypes[i].getId() == 2) {
                phaseType = phaseTypes[i];
            }
        }
        assertEquals("Failed to get name", "Manual", phaseType.getName());
    }

    /**
     * Test <code>getAllPhaseStatuses()</code>.
     * <p>
     * An array of phase statuses in persistence will be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllPhaseStatuses() throws Exception {
        PhaseStatus[] phaseStatuses = persistence.getAllPhaseStatuses();
        assertEquals("Should return 3 statuses.", 3, phaseStatuses.length);

        // pick one PhaseType to check the fields
        PhaseStatus phaseStatus = null;
        for (int i = 0; i < phaseStatuses.length; ++i) {
            if (phaseStatuses[i].getId() == 2) {
                phaseStatus = phaseStatuses[i];
            }
        }
        assertEquals("Failed to get name", "Open", phaseStatus.getName());
    }

    /**
     * Test <code>createPhase(Phase)</code> with all the fields set.
     * <p>
     * The value should be inserted to the corresponding column and dependecy will also be added.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreatePhase_Normal() throws Exception {
        // prepare the phase instance to test.
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(1);
        Phase phase = new Phase(project, 12345678);
        phase.setFixedStartDate(parseDate("2006-01-01 11:11:13.111"));
        phase.setScheduledStartDate(parseDate("2006-01-02 11:11:13.111"));
        phase.setScheduledEndDate(parseDate("2006-01-03 11:11:13.111"));
        phase.setActualStartDate(parseDate("2006-01-04 11:11:13.111"));
        phase.setActualEndDate(parseDate("2006-01-05 11:11:13.111"));
        phase.setPhaseStatus(new PhaseStatus(2, "Open"));
        phase.setPhaseType(new PhaseType(1, "dummy"));
        phase.setId(13579);

        // add some dependencies to the phase
        Phase phase1 = new Phase(project, 1234);
        phase1.setId(1);
        phase.addDependency(new Dependency(phase1, phase, true, false, 1234567));
        Phase phase3 = new Phase(project, 1234);
        phase3.setId(3);
        phase.addDependency(new Dependency(phase3, phase, false, true, 7654321));

        // create the phase in persistence
        persistence.createPhase(phase, "reviewer");

        // validate the result
        DBRecord record = TestHelper.getPhaseRecords(" WHERE project_phase_id=" + phase.getId())[0];
        assertEquals("Failed to store project_id", new Long(1), record.getValue("project_id"));
        assertEquals("Failed to store fixedStartDate", phase.getFixedStartDate(), record
            .getValue("fixed_start_time"));
        assertEquals("Failed to store scheduledStartDate", phase.getScheduledStartDate(), record
            .getValue("scheduled_start_time"));
        assertEquals("Failed to store scheduledEndDate", phase.getScheduledEndDate(), record
            .getValue("scheduled_end_time"));
        assertEquals("Failed to store actualStartDate", phase.getActualStartDate(), record
            .getValue("actual_start_time"));
        assertEquals("Failed to store actualEndDate", phase.getActualEndDate(), record
            .getValue("actual_end_time"));
        assertEquals("Failed to store phase status id", new Long(2), record.getValue("phase_status_id"));
        assertEquals("Failed to store phase type id", new Long(1), record.getValue("phase_type_id"));

        // validate the dependency
        DBRecord[] records = TestHelper.getDependencyRecords(" WHERE dependent_phase_id=" + phase.getId());
        assertEquals("Should store 2 dependencies.", 2, records.length);
        record = null;
        for (int i = 0; i < records.length; ++i) {
            if (records[i].getValue("dependency_phase_id").equals(new Long(3))) {
                record = records[i];
            }
        }
        assertNotNull("Should add dependency with phase 3.", record);
        assertEquals("Failed to store dependencyStart.", new Boolean(false), record
            .getValue("dependency_start"));
        assertEquals("Failed to store dependentStart.", new Boolean(true), record.getValue("dependent_start"));
        assertEquals("Failed to store lagtime.", new Long(7654321), record.getValue("lag_time"));
    }

    /**
     * Test <code>createPhase(Phase)</code> with minimal requirements of fields.
     * <p>
     * The missing fields will be set as NULL, and no dependency should be added.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreatePhase_Special() throws Exception {
        // prepare the phase instance to test.
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(2);
        Phase phase = new Phase(project, 12345678);
        phase.setScheduledStartDate(parseDate("2006-01-02 11:11:13.111"));
        phase.setScheduledEndDate(parseDate("2006-01-03 11:11:13.111"));
        phase.setPhaseStatus(new PhaseStatus(2, "Open"));
        phase.setPhaseType(new PhaseType(1, "dummy"));
        phase.setId(2468);

        // create the phase in persistence
        persistence.createPhase(phase, "reviewer");

        // validate the result
        DBRecord record = TestHelper.getPhaseRecords(" WHERE project_phase_id=" + phase.getId())[0];
        assertEquals("Failed to store fixedStartDate as null", phase.getFixedStartDate(), record
            .getValue("fixed_start_time"));
        assertEquals("Failed to store scheduledStartDate", phase.getScheduledStartDate(), record
            .getValue("scheduled_start_time"));
        assertEquals("Failed to store scheduledEndDate", phase.getScheduledEndDate(), record
            .getValue("scheduled_end_time"));
        assertEquals("Failed to store actualStartDate as null", phase.getActualStartDate(), record
            .getValue("actual_start_time"));
        assertEquals("Failed to store actualEndDate as null", phase.getActualEndDate(), record
            .getValue("actual_end_time"));
        assertEquals("Failed to store phase status id", new Long(2), record.getValue("phase_status_id"));
        assertEquals("Failed to store phase type id", new Long(1), record.getValue("phase_type_id"));
    }

    /**
     * Test <code>createPhases(Phase[])</code> in a normal case.
     * <p>
     * The phases and all their dependencies should be added.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreatePhases_Normal() throws Exception {
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(1);
        Phase[] phases = new Phase[3];
        for (int i = 0; i < phases.length; ++i) {
            phases[i] = new Phase(project, 12345678);
            Phase phase = phases[i];
            phase.setFixedStartDate(parseDate("2006-01-01 11:11:13.11" + i));
            phase.setScheduledStartDate(parseDate("2006-01-02 11:11:13.11" + i));
            phase.setScheduledEndDate(parseDate("2006-01-03 11:11:13.11" + i));
            phase.setActualStartDate(parseDate("2006-01-04 11:11:13.11" + i));
            phase.setActualEndDate(parseDate("2006-01-05 11:11:13.11" + i));
            phase.setPhaseStatus(new PhaseStatus(2, "Open"));
            phase.setPhaseType(new PhaseType(1, "dummy"));
            phase.setId(1234500 + i);
        }

        // add some dependencies
        Phase another = new Phase(project, 12345);
        another.setId(3);
        phases[0].addDependency(new Dependency(another, phases[0], false, true, 123456));
        phases[0].addDependency(new Dependency(phases[1], phases[0], true, false, 654321));

        // create the phases in persistence
        persistence.createPhases(phases, "testCreatePhases_Normal");

        // validate the phases records(randomly)
        DBRecord[] phaseRecords =
            TestHelper
                .getPhaseRecords(" WHERE create_user=\'testCreatePhases_Normal\' ORDER BY project_phase_id ASC");
        assertEquals("Failed to create all phases.", phases.length, phaseRecords.length);
        assertEquals("Failed to store fixed_start_time.", phases[1].getFixedStartDate(), phaseRecords[1]
            .getValue("fixed_start_time"));
        assertEquals("Failed to store phase length.", new Long(phases[0].getLength()), phaseRecords[0]
            .getValue("duration"));
        assertEquals("Failed to store phase type.", new Long(phases[2].getPhaseStatus().getId()),
            phaseRecords[2].getValue("phase_status_id"));

        // validate the dependency
        DBRecord[] dependencyRecords =
            TestHelper.getDependencyRecords(" WHERE dependent_phase_id=" + phases[0].getId()
                + " ORDER BY dependency_phase_id ASC");
        assertEquals("Should contain 2 dependencies.", 2, dependencyRecords.length);
        assertEquals("Failed add dependency with old record.", new Long(3), dependencyRecords[0]
            .getValue("dependency_phase_id"));
        assertEquals("Failed add dependency with old record.", new Long(phases[1].getId()),
            dependencyRecords[1].getValue("dependency_phase_id"));
    }

    /**
     * Test <code>createPhases(Phase[])</code> with an empty array.
     * <p>
     * Nothing should be added.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreatePhases_Empty() throws Exception {
        // create the phases in persistence
        persistence.createPhases(new Phase[0], "testCreatePhases_Normal");

        // validate the phases records(randomly)
        DBRecord[] phaseRecords =
            TestHelper
                .getPhaseRecords(" WHERE create_user=\'testCreatePhases_Normal\' ORDER BY project_phase_id ASC");
        assertEquals("No phase should be added.", 0, phaseRecords.length);
    }

    /**
     * Test <code>getPhase(long)</code> for a complex phase. There is some dependencies associated with it.
     * <p>
     * In this case, we mainly focus on whether the dependencies of phase is retrieved properly.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPhase_Simple() throws Exception {
        Phase phase = persistence.getPhase(6);
        Project project = phase.getProject();
        assertEquals("Failed to get project of phase.", 2, project.getId());
        assertEquals("Failed to get phase status.", 1, phase.getPhaseStatus().getId());
        assertEquals("Failed to get phase type.", 1, phase.getPhaseType().getId());
        assertEquals("Failed to get fixedStartTime.", null, phase.getFixedStartDate());
        assertEquals("Failed to get scheduledStartTime", parseDate("2006-01-01 11:11:11.111"), phase
            .getScheduledStartDate());
        assertEquals("Failed to get scheduledEndTime", parseDate("2006-01-01 11:11:22.111"), phase
            .getScheduledEndDate());
        assertEquals("Failed to get actualStartTime", null, phase.getActualStartDate());
        assertEquals("Failed to get actualEndTime", null, phase.getActualEndDate());
        assertEquals("Failed to get length", 6000, phase.getLength());
        assertEquals("There should be 2 phases of the project.", 2, project.getAllPhases().length);
    }

    /**
     * Test <code>getPhase(long)</code> for a simply phase. There is no dependency associated with it.
     * <p>
     * In this case, we mainly focus on whether the fields of phase is retrieved properly.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPhase_Complex() throws Exception {
        Phase phase = persistence.getPhase(2);
        Project project = phase.getProject();

        // check the project
        assertEquals("Failed to set project id.", 1, project.getId());
        assertEquals("Failed to set startDate", parseDate("2006-01-01 11:11:11.111"), project.getStartDate());
        Phase[] phases = project.getAllPhases();
        assertEquals("Should contain 4 phases.", 4, phases.length);

        // chech the dependencies between phases.
        assertEquals("Phase 2 should have 1 dependency.", 1, phase.getAllDependencies().length);
        assertEquals("Phase 2 should depend on Phase 1.", 1, phase.getAllDependencies()[0].getDependency()
            .getId());
        assertEquals("Invalid dependency lagtime between 2 and 1.", 12000, phase.getAllDependencies()[0]
            .getLagTime());
        assertEquals("Invalid dependencyStart lagtime between 2 and 1.", true, phase.getAllDependencies()[0]
            .isDependencyStart());
        assertEquals("Invalid dependencyStart lagtime between 2 and 1.", false, phase.getAllDependencies()[0]
            .isDependentStart());

        assertEquals("Phase 1 should have no dependency.", 0, phase.getAllDependencies()[0].getDependency()
            .getAllDependencies().length);

        assertEquals("The project of phases should be same.", phase.getProject(),
            phase.getAllDependencies()[0].getDependency().getProject());
    }

    /**
     * Test <code>getPhase(long)</code> with an unknown phase id.
     * <p>
     * Null should be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPhase_NotExist() throws Exception {
        Phase phase = persistence.getPhase(212354);
        assertNull("Should return null for unknown id.", phase);
    }

    /**
     * Test <code>getPhases(long[])</code> from different projects.
     * <p>
     * The whole related projects should be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPhases_Normal() throws Exception {
        Phase[] phases = persistence.getPhases(new long[] {5, 2, 3, 6});

        // verify the results
        assertEquals("Phase 5 and 6 should have same project.", phases[0].getProject().getId(), phases[3]
            .getProject().getId());
        assertEquals("Phase 2 and 3 should have same project.", phases[1].getProject().getId(), phases[2]
            .getProject().getId());

        assertEquals("project 2 should have 2 phases.", 2, phases[0].getProject().getAllPhases().length);
        assertEquals("project 1 should have 4 phases.", 4, phases[2].getProject().getAllPhases().length);

        assertEquals("There is no dependency with phase 5.", 0, phases[0].getAllDependencies().length);
        assertEquals("Phase 3 should depend on phase 2.", phases[1], phases[2].getAllDependencies()[0]
            .getDependency());
    }

    /**
     * Test <code>getPhases(long[])</code> with some unknown phase id.
     * <p>
     * If the id is unknown, the corresponding element in result array will be null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPhases_SomeNotExist() throws Exception {
        Phase[] phases = persistence.getPhases(new long[] {34534, 5, 144351});

        // verify the results
        assertEquals("Element 0 should be null.", null, phases[0]);
        assertEquals("Element 2 should be null.", null, phases[2]);
        assertEquals("Element 2 should be retrieved.", 5, phases[1].getId());
    }

    /**
     * Test <code>getPhases(long[])</code> with an empty array.
     * <p>
     * An empty array will be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPhases_EmptyArray() throws Exception {
        Phase[] phases = persistence.getPhases(new long[] {});

        // verify the results
        assertEquals("Should return an empty array.", 0, phases.length);
    }

    /**
     * Test <code>updatePhase(Phase)</code> with an existing phase.
     * <p>
     * All the column value and dependency should be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdatePhase_Normal() throws Exception {
        // prepare the phase instance to test.
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(1);
        Phase phase = new Phase(project, 12345678);
        phase.setId(4);
        phase.setFixedStartDate(parseDate("2006-01-01 11:11:13.111"));
        phase.setScheduledStartDate(parseDate("2006-01-02 11:11:13.111"));
        phase.setScheduledEndDate(parseDate("2006-01-03 11:11:13.111"));
        phase.setActualStartDate(parseDate("2006-01-04 11:11:13.111"));
        phase.setActualEndDate(parseDate("2006-01-05 11:11:13.111"));
        phase.setPhaseStatus(new PhaseStatus(2, "Open"));
        phase.setPhaseType(new PhaseType(1, "dummy"));

        // add some dependencies to the phase
        Phase phase3 = new Phase(project, 1234);
        phase3.setId(3);
        phase.addDependency(new Dependency(phase3, phase, false, true, 1234567));
        Phase phase2 = new Phase(project, 1234);
        phase2.setId(2);
        phase.addDependency(new Dependency(phase2, phase, true, false, 7654321));

        // create the phase in persistence
        persistence.updatePhase(phase, "reviewer");

        // validate the result
        DBRecord record = TestHelper.getPhaseRecords(" WHERE project_phase_id=" + phase.getId())[0];
        assertEquals("Failed to store project_id", new Long(1), record.getValue("project_id"));
        assertEquals("Failed to store fixedStartDate", phase.getFixedStartDate(), record
            .getValue("fixed_start_time"));
        assertEquals("Failed to store scheduledStartDate", phase.getScheduledStartDate(), record
            .getValue("scheduled_start_time"));
        assertEquals("Failed to store scheduledEndDate", phase.getScheduledEndDate(), record
            .getValue("scheduled_end_time"));
        assertEquals("Failed to store actualStartDate", phase.getActualStartDate(), record
            .getValue("actual_start_time"));
        assertEquals("Failed to store actualEndDate", phase.getActualEndDate(), record
            .getValue("actual_end_time"));
        assertEquals("Failed to store phase status id", new Long(2), record.getValue("phase_status_id"));
        assertEquals("Failed to store phase type id", new Long(1), record.getValue("phase_type_id"));

        // validate the dependency
        DBRecord[] records = TestHelper.getDependencyRecords(" WHERE dependent_phase_id = 4");
        assertEquals("Should store 2 dependencies.", 2, records.length);
        record = null;
        for (int i = 0; i < records.length; ++i) {
            if (records[i].getValue("dependency_phase_id").equals(new Long(3))) {
                record = records[i];
            }
        }
        assertNotNull("Should add dependency with phase 3.", record);
        assertEquals("Failed to store dependencyStart.", new Boolean(false), record
            .getValue("dependency_start"));
        assertEquals("Failed to store dependentStart.", new Boolean(true), record.getValue("dependent_start"));
        assertEquals("Failed to store lagtime.", new Long(1234567), record.getValue("lag_time"));
    }

    /**
     * Test <code>updatePhase(Phase)</code> with an un-existing phase.
     * <p>
     * Nothing should happen.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdatePhase_NotExist() throws Exception {
        // prepare the phase instance to test.
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(1);
        Phase phase = new Phase(project, 12345678);
        phase.setId(434543265);
        phase.setFixedStartDate(parseDate("2006-01-01 11:11:13.111"));
        phase.setScheduledStartDate(parseDate("2006-01-02 11:11:13.111"));
        phase.setScheduledEndDate(parseDate("2006-01-03 11:11:13.111"));
        phase.setActualStartDate(parseDate("2006-01-04 11:11:13.111"));
        phase.setActualEndDate(parseDate("2006-01-05 11:11:13.111"));
        phase.setPhaseStatus(new PhaseStatus(2, "Open"));
        phase.setPhaseType(new PhaseType(1, "dummy"));

        // add some dependencies to the phase
        Phase another = new Phase(project, 1234);
        another.setId(3);
        phase.addDependency(new Dependency(another, phase, false, true, 1234567));

        // create the phase in persistence
        persistence.updatePhase(phase, "reviewer");

        // validate the result
        DBRecord[] records = TestHelper.getDependencyRecords("");
        assertEquals("New phase should be added.", 4, records.length);
    }

    /**
     * Test <code>updatePhases(Phase[])</code> in a normal case.
     * <p>
     * All the phases and their dependencies should be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdatePhases_Normal() throws Exception {
        // prepare the phase instance to test.
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(1);

        Phase[] phases = new Phase[2];
        for (int i = 0; i < phases.length; ++i) {
            phases[i] = new Phase(project, 12345678);
            Phase phase = phases[i];
            phase.setId(i + 2);
            phase.setFixedStartDate(parseDate("2006-01-01 11:11:13.111"));
            phase.setScheduledStartDate(parseDate("2006-01-02 11:11:13.111"));
            phase.setScheduledEndDate(parseDate("2006-01-03 11:11:13.111"));
            phase.setActualStartDate(parseDate("2006-01-04 11:11:13.111"));
            phase.setActualEndDate(parseDate("2006-01-05 11:11:13.111"));
            phase.setPhaseStatus(new PhaseStatus(2, "Open"));
            phase.setPhaseType(new PhaseType(1, "dummy"));
        }

        // The dependency between 2-3 is removed, and 1-3 is added.
        Phase another = new Phase(project, 1234);
        another.setId(1);
        phases[1].addDependency(new Dependency(another, phases[1], false, true, 1234567));

        // create the phase in persistence
        persistence.updatePhases(phases, "reviewer");
        DBRecord[] records = TestHelper.getDependencyRecords(" WHERE dependent_phase_id=3");
        assertEquals("Should remove one and add one dependency.", 1, records.length);
        assertEquals("Should update the dependency.", new Long(1), records[0].getValue("dependency_phase_id"));
    }

    /**
     * Test <code>updatePhases(Phase[])</code> with an emtpy array.
     * <p>
     * Nothing should happen.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdatePhases_EmptyArray() throws Exception {
        persistence.updatePhases(new Phase[0], "reviewer");

        // validate the result
        DBRecord[] records = TestHelper.getDependencyRecords("");
        assertEquals("Nothing should happen.", 3, records.length);
    }

    /**
     * Test <code>deletePhase(Phase)</code> when the phase does exist in persistence.
     * <p>
     * The phase and its corresponding dependency should be removed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDeletePhase_Normal() throws Exception {
        Phase phase = new Phase(new Project(new Date(), new DefaultWorkdays()), 123);
        phase.setId(2);
        persistence.deletePhase(phase);

        // check whether the phase is deleted.
        DBRecord[] records = TestHelper.getPhaseRecords(" WHERE project_phase_id=" + phase.getId());
        assertEquals("The phase should be deleted.", 0, records.length);

        // check whether the dependency is deleted.
        records = TestHelper.getDependencyRecords(" WHERE dependency_phase_id=2 OR dependent_phase_id=2");
        assertEquals("Failed to remove dependencies.", 0, records.length);
    }

    /**
     * Test <code>deletePhase(Phase)</code> when the phase does not exist in persistence.
     * <p>
     * Nothing should be removed from persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDeletePhase_NotExist() throws Exception {
        Phase phase = new Phase(new Project(new Date(), new DefaultWorkdays()), 123);
        phase.setId(23455);
        persistence.deletePhase(phase);

        // check whether the phase is deleted.
        DBRecord[] records = TestHelper.getPhaseRecords("");
        assertEquals("No phase should be deleted.", 6, records.length);
    }

    /**
     * Test <code>deletePhases(Phase[])</code> when all the phases does exist.
     * <p>
     * All the phases and their dependencies should be removed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDeletePhases_Normal() throws Exception {
        Phase[] phases = new Phase[5];
        for (int i = 0; i < phases.length; ++i) {
            phases[i] = new Phase(new Project(new Date(), new DefaultWorkdays()), 123);
            phases[i].setId(i + 1);
        }
        persistence.deletePhases(phases);

        // check whether the phases is deleted.
        DBRecord[] records = TestHelper.getPhaseRecords("");
        assertEquals("The phases should be deleted.", 1, records.length);

        // check whether the dependency is deleted.
        records = TestHelper.getDependencyRecords("");
        assertEquals("Failed to remove dependencies.", 0, records.length);
    }

    /**
     * Test <code>deletePhases(Phase[])</code> when some of the phases does exist.
     * <p>
     * Only the existing phases should be removed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDeletePhases_SomeNotExist() throws Exception {
        Phase[] phases = new Phase[5];
        for (int i = 0; i < phases.length; ++i) {
            phases[i] = new Phase(new Project(new Date(), new DefaultWorkdays()), 123);
            phases[i].setId(i + 100000);
        }
        phases[3].setId(1);
        persistence.deletePhases(phases);

        // check whether the phase is deleted.
        DBRecord[] records = TestHelper.getPhaseRecords(" WHERE project_phase_id=" + phases[3].getId());
        assertEquals("The phase should be deleted.", 0, records.length);

        // check whether the dependency is deleted.
        records =
            TestHelper.getDependencyRecords(" WHERE dependency_phase_id=1 OR dependent_phase_id="
                + phases[3].getId());
        assertEquals("Failed to remove dependencies.", 0, records.length);
    }

    /**
     * Test <code>deletePhases(Phase[])</code> with an empty array.
     * <p>
     * Nothing should be removed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDeletePhases_EmptyArray() throws Exception {
        persistence.deletePhases(new Phase[0]);

        // check whether the phase is deleted.
        DBRecord[] records = TestHelper.getPhaseRecords("");
        assertEquals("No phase should be deleted.", 6, records.length);

        // check whether the dependency is deleted.
        records = TestHelper.getDependencyRecords("");
        assertEquals("No dependency should be deleted.", 3, records.length);
    }

    /**
     * Test <code>isNewPhase(Phase)</code> with an old phase.
     * <p>
     * False should be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsNewPhase_OldPhase() throws Exception {
        // prepare the phase instance to test.
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(1);
        Phase phase = new Phase(project, 12345678);
        phase.setId(4);

        assertEquals("Should return false for old phase.", false, persistence.isNewPhase(phase));
    }

    /**
     * Test <code>isNewPhase(Phase)</code> with a new phase.
     * <p>
     * True should be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsNewPhase_NewPhase() throws Exception {
        // prepare the phase instance to test.
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(1);
        Phase phase = new Phase(project, 12345678);

        assertEquals("Should return true for new phase.", true, persistence.isNewPhase(phase));
    }

    /**
     * Test <code>isNewDependency(Dependency)</code>.
     * <p>
     * Since this method is not implemented before final fix. This case is always successful.
     * </p>
     */
    public void testIsNewDependency() {
        assertTrue("Un-implemented method.", true);
    }

    /**
     * Parse date text to <code>Date</code> instance.
     * <p>
     * The format of the text is expected as <em>yyyy-MM-dd HH:mm:ss.SSS</em>.
     * </p>
     *
     * @param text the date text to parse.
     * @return a <code>Date</code> instance
     * @throws Exception to JUnit.
     */
    private Date parseDate(String text) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return dateFormat.parse(text);
    }

    /**
     * Test <code>getConnection()</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testGetConnection() throws Exception {
        Connection connection =
            (Connection) TestHelper.invokeMethod(persistence, "getConnection", new Class[] {},
                new Object[] {});

        assertFalse("getConnection() should be correct.", connection.getAutoCommit());
    }

    /**
     * Test <code>disposeConnection(Connection)</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testDisposeConnection() throws Exception {
        Connection connection =
            (Connection) TestHelper.invokeMethod(persistence, "getConnection", new Class[] {},
                new Object[] {});
        assertFalse("The connection has not been closed.", connection.isClosed());

        TestHelper.invokeMethod(persistence, "disposeConnection", new Class[] {Connection.class},
            new Object[] {connection});

        assertTrue("The connection should be closed.", connection.isClosed());
    }

    /**
     * Tests <code>commitTransaction(Map context)</code>.
     *
     * @throws Exception To JUnit.
     */
    public void testCommitTransaction() throws Exception {
        Connection connection =
            (Connection) TestHelper.invokeMethod(persistence, "getConnection", new Class[] {},
                new Object[] {});
        Statement statement = connection.createStatement();

        Map context = new HashMap();
        context.put("connection", connection);
        statement.execute("INSERT INTO id_sequences"
            + "(name, next_block_start, block_size, exhausted) VALUES('aaaa', 2, 13, 12)");
        ResultSet rs = statement.executeQuery("select * from id_sequences where block_size = 13");

        TestHelper.invokeMethod(persistence, "commitTransaction", new Class[] {Map.class},
            new Object[] {context});

        rs.next();
        String str = rs.getString(1);
        assertEquals("commitTransaction should be correct.", "aaaa", str);

        statement.execute("delete from id_sequences where name = 'aaaa'");
        connection.commit();
        connection.close();
    }

    /**
     * Tests <code>rollbackTransaction(Map context)</code>.
     *
     * @throws Exception To JUnit.
     */
    public void testRollbackTransaction() throws Exception {
        Connection connection =
            (Connection) TestHelper.invokeMethod(persistence, "getConnection", new Class[] {},
                new Object[] {});
        Statement statement = connection.createStatement();

        Map context = new HashMap();
        context.put("connection", connection);
        statement.execute("INSERT INTO id_sequences"
            + "(name, next_block_start, block_size, exhausted) VALUES('pd12a_i26', 2, 70, 12)");

        ResultSet rs = statement.executeQuery("select * from id_sequences where block_size=70");
        assertTrue("The query should succeed.", rs.next());

        TestHelper.invokeMethod(persistence, "rollbackTransaction", new Class[] {Map.class},
            new Object[] {context});

        rs = statement.executeQuery("select * from id_sequences where block_size=70");
        assertFalse("rollbackTransaction should be correct.", rs.next());
        connection.close();
    }
}
