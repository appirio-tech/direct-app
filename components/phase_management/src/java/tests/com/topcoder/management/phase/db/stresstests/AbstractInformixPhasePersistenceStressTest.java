/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.stresstests;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.phase.PhasePersistence;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import java.util.Date;


/**
 * <p>
 * Stress test for class <code>InformixPhasePersistence</code>.
 * </p>
 * <p>
 * Thread safe is not a requirement of this component and the class is designed
 * as stateless, so it is thread safe.
 * </p>
 * <p>
 * The efficiency of some method is tested here.
 * </p>
 * @author fuyun, KLW
 * @version 1.1
 */
public class AbstractInformixPhasePersistenceStressTest extends TestCase {
    /**
     * Represents namespace used to load configurations for db connection
     * factory.
     */
    private static final String DB_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Represents namespace used to load configurations for this component.
     */
    private static final String NAMESPACE = "com.topcoder.management.phase.db.InformixPhasePersistence.stress";

    /**
     * Represents the <code>DBConnectionFactory</code> used to connect
     * database.
     */
    private DBConnectionFactory factory = null;

    /**
     * Represents the <code>InformixPhasePersistence</code> instance used for
     * test.
     */
    private PhasePersistence persistence = null;

    /**
     * Setup the test environment.
     * @throws Exception
     *             to JUnit for any problem.
     */
    protected void setUp() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        if (manager.existsNamespace(DB_NAMESPACE)) {
            manager.removeNamespace(DB_NAMESPACE);
        }

        if (manager.existsNamespace(NAMESPACE)) {
            manager.removeNamespace(NAMESPACE);
        }

        manager.add(new File("test_files/stress/db_conf.xml").getCanonicalPath());
        manager.add(new File("test_files/stress/SampleConfig.xml").getCanonicalPath());
        
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = factory.createConnection();
            stmt = conn.createStatement();
            stmt.execute("delete from project_phase");
            stmt.execute(
                "delete from phase_type_lu where phase_type_id in (54321, 54322, 54323)");
            stmt.execute(
                "delete from phase_status_lu where phase_status_id in (54321, 54322, 54323)");
            stmt.execute(
                "delete from project where project_id in (54321, 54322, 54323)");

            // create project
            stmt.execute("insert into project values (54321)");
            stmt.execute("insert into project values (54322)");
            stmt.execute("insert into project values (54323)");

            // create phase_status_lu
            stmt.execute("insert into phase_status_lu values (54321, 'stress'," +
                " 'stress', 'stress', current, 'stress', current)");
            stmt.execute("insert into phase_status_lu values (54322, 'stress'," +
                " 'stress', 'stress', current, 'stress', current)");
            stmt.execute("insert into phase_status_lu values (54323, 'stress'," +
                " 'stress', 'stress', current, 'stress', current)");

            // create phase_type_lu
            stmt.execute("insert into phase_type_lu values (54321, 'stress', " +
                "'stress', 'stress', current, 'stress', current);");
            stmt.execute("insert into phase_type_lu values (54322, 'stress', " +
                "'stress', 'stress', current, 'stress', current);");
            stmt.execute("insert into phase_type_lu values (54323, 'stress', " +
                "'stress', 'stress', current, 'stress', current);");

            // create phase
            stmt.execute(
                "insert into project_phase values (54321, 54321, 54321, 54321, current, " +
                "current, current, current, current, 100, 'stress', current, " +
                "'stress', current)");
            stmt.execute(
                "insert into project_phase values (54322, 54322, 54322, 54322, current, " +
                "current, current, current, current, 100, 'stress', current, " +
                "'stress', current)");
            stmt.execute(
                "insert into project_phase values (54323, 54323, 54323, 54323, current, " +
                "current, current, current, current, 100, 'stress', current, " +
                "'stress', current)");
            stmt.execute(
                "insert into project_phase values (54324, 54321, 54323, 54323, current, " +
                "current, current, current, current, 100, 'stress', current, " +
                "'stress', current)");
            stmt.execute(
                "insert into project_phase values (54325, 54321, 54323, 54323, current, " +
                "current, current, current, current, 100, 'stress', current, " +
                "'stress', current)");
            stmt.execute(
                "insert into project_phase values (54326, 54322, 54323, 54323, current, " +
                "current, current, current, current, 100, 'stress', current, " +
                "'stress', current)");
           
        } finally {
            stmt.close();
            conn.close();
        }

        persistence = new MockInformixPhasePersistence(NAMESPACE);
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             to JUnit for any problem.
     */
    protected void tearDown() throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = factory.createConnection();
            stmt = conn.createStatement();
            stmt.execute("delete from project_phase");
            stmt.execute(
                "delete from phase_type_lu where phase_type_id in (54321, 54322, 54323)");
            stmt.execute(
                "delete from phase_status_lu where phase_status_id in (54321, 54322, 54323)");
            stmt.execute(
                "delete from project where project_id in (54321, 54322, 54323)");
            
        } finally {
            stmt.close();
            conn.close();
        }

        ConfigManager manager = ConfigManager.getInstance();

        if (manager.existsNamespace(DB_NAMESPACE)) {
            manager.removeNamespace(DB_NAMESPACE);
        }

        if (manager.existsNamespace(NAMESPACE)) {
            manager.removeNamespace(NAMESPACE);
        }

        factory = null;

        persistence = null;
    }

    /**
     * Stress test for method <code>getProjectPhases(long[])</code>.
     * @throws Exception to JUnit for any problem.
     */
    public void testGetProjectPhasesLongArray() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            Project[] projects = persistence.getProjectPhases(new long[] {
                        54321, 54322, 54323
                    });
            assertEquals("Fail to get projects.", 3, projects.length);
            assertEquals("Fail to get projects.", 3,
                projects[0].getAllPhases().length);
            assertEquals("Fail to get projects.", 2,
                projects[1].getAllPhases().length);
            assertEquals("Fail to get projects.", 1,
                projects[2].getAllPhases().length);
        }

        System.out.println("getProjectPhases method spends " +
            (System.currentTimeMillis() - start) + " millisecond.");
    }

    /**
     * Stress test for method <code>createPhases(Phase[], String)</code>.
     * @throws Exception to JUnit for any problem.
     */
    public void testCreatePhases() throws Exception {
        Project project = new Project(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance());
        project.setId(54321);

        Phase[] phases = new Phase[100];

        for (int i = 0; i < 100; i++) {
            phases[i] = new Phase(project, i + 100);
            phases[i].setId(65432 + i);
            phases[i].setPhaseStatus(new PhaseStatus(54321 + (i % 3), "stress"));
            phases[i].setPhaseType(new PhaseType(54321 + (i % 3), "stress"));
            phases[i].setScheduledStartDate(new Date());
            phases[i].setScheduledEndDate(new Date());
        }

        long start = System.currentTimeMillis();
        persistence.createPhases(phases, "stress");
        System.out.println("createPhases method spends " +
            (System.currentTimeMillis() - start) + " millisecond.");
    }

    /**
     * Stress test for method <code>getPhases(long[])</code>.
     * @throws Exception to JUnit for any problem.
     */
    public void testGetPhases() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            Phase[] phases = persistence.getPhases(new long[] {
                        54321, 54322, 54323, 54324, 54325, 54326
                    });
            assertEquals("Fail to get phases.", 6, phases.length);
        }

        System.out.println("getPhases method spends " +
            (System.currentTimeMillis() - start) + " millisecond.");
    }

    /**
     * Stress test for <code>updatePhases(Phase[], String)</code>.
     * @throws Exception to JUnit for any problem.
     */
    public void testUpdatePhases() throws Exception {
        Phase[] phases = persistence.getPhases(new long[] {
                    54321, 54322, 54323, 54324, 54325, 54326
                });

        for (int i = 0; i < phases.length; i++) {
            phases[i].setLength(100);
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            persistence.updatePhases(phases, "stress_update");
        }

        System.out.println("updatePhases method spends " +
            (System.currentTimeMillis() - start) + " millisecond.");
    }
}
