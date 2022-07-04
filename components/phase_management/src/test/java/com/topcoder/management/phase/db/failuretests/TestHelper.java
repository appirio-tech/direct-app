/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.failuretests;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;


/**
 * Test helper class.
 */
public class TestHelper {
    /** namespace for db connection. */
    public static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** Config file for the component. */
    public static final String CONFIG_FILE = "failure" + java.io.File.separator + "config.xml";

    /** Namespace for this InformixPhasePersistence class. */
    public static final String INFORMIX_PHASE_PERSISTENCE_NAMESPACE = "com.topcoder.management.phase.db.InformixPhasePersistence.failure";
    /** Namespace for this UnmanagedTransactionInformixPhasePersistence class. */
    public static final String UNMANAGED_TRANSACTION_INFORMIX_PHASE_PERSISTENCE_NAMESPACE = "com.topcoder.management.phase.db.InformixPhasePersistence.failure";

    /**
     * <p>
     * Creates a new instance of TestHelper class. The private constructor prevents the creation of a new instance.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Add the config file.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public static void setUpConfiguration() throws Exception {
        ConfigManager.getInstance().add(CONFIG_FILE);
    }

    /**
     * This method clears all the namespaces from ConfigManager.
     *
     * @throws Exception if any error occurs when clearing ConfigManager
     */
    public static void clearConfiguration() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Insert data for testing.
     *
     * @param conn database connection
     *
     * @throws Exception into JUnit
     */
    public static void insertData(Connection conn) throws Exception {
        TestHelper.clearTables(conn);

        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            stmt.execute(
                "insert into id_sequences (name, next_block_start, block_size, exhausted) values ('phase', 1, 1, 0)");
            //insert the project
            stmt.execute("insert into project(project_id) values(1)");
            stmt.execute("insert into project(project_id) values(2)");
            //insert the phase_types
            stmt.execute("insert into phase_type_lu(phase_type_id,name,description,create_user,create_date,modify_user,modify_date) values("
            		+ "1,'register','user contest register',USER,CURRENT,USER,CURRENT)");
            stmt.execute("insert into phase_type_lu(phase_type_id,name,description,create_user,create_date,modify_user,modify_date) values("
            		+ "2,'submit','user contest submission',USER,CURRENT,USER,CURRENT)");
            stmt.execute("insert into phase_type_lu(phase_type_id,name,description,create_user,create_date,modify_user,modify_date) values("
            		+ "3,'screening','reviewer screening',USER,CURRENT,USER,CURRENT)");
            //insert the phase_status
            stmt.execute("insert into phase_status_lu(phase_status_id,name,description,create_user,create_date,modify_user,modify_date) values("
            		+ "1,'open','open',USER,CURRENT,USER,CURRENT)");
            stmt.execute("insert into phase_status_lu(phase_status_id,name,description,create_user,create_date,modify_user,modify_date) values("
            		+ "2,'SCHEDULED','SCHEDULED',USER,CURRENT,USER,CURRENT)");
            stmt.execute("insert into phase_status_lu(phase_status_id,name,description,create_user,create_date,modify_user,modify_date) values("
            		+ "3,'closed','closed',USER,CURRENT,USER,CURRENT)");
            
            //insert data to phase_criteria_type_lu
            stmt.execute("insert into phase_criteria_type_lu(phase_criteria_type_id,name,description,create_user,create_date,modify_user,modify_date) values("
            		+ "1,'designer','designer',USER,CURRENT,USER,CURRENT)");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Clear the tables.
     *
     * @param conn Database connection
     *
     * @throws Exception into Junit
     */
    public static void clearTables(Connection conn) throws Exception {
        Statement stmt = conn.createStatement();

        try {
            stmt.execute("delete from id_sequences where name = 'phase'");
            
            stmt.execute("delete from phase_dependency");
            stmt.execute("delete from phase_criteria");
            stmt.execute("delete from phase_criteria_type_lu");
            stmt.execute("delete from project_phase");
            stmt.execute("delete from phase_type_lu");
            stmt.execute("delete from phase_status_lu");
            stmt.execute("delete from project");
        } finally {
            stmt.close();
        }
    }

    /**
     * <p>
     * Parses a date string to a Date instance using format "ddMMyyyy".
     * </p>
     *
     * @param date a date string in "ddMMyyyy" format
     *
     * @return the corresponding Date instance.
     *
     * @throws Exception to  JUnit
     */
    public static Date parseDate(String date) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");

        return format.parse(date);
    }

    /**
     * Get phase instance for testing.
     *
     *
     * @return Phase instance
     */
    public static Phase [] getPhase(long projectId) throws Exception {
        Project project = new Project(new Date(), new MyWorkdays());
        project.setId(projectId);
        
        Phase screening = new Phase(project, 24 * 3600 * 1000L);

        screening.setPhaseType(new PhaseType(3, "screening"));
        screening.setPhaseStatus(PhaseStatus.OPEN);
        screening.setScheduledStartDate(parseDate("03052007"));
        screening.setScheduledEndDate(parseDate("04052007"));
        
        screening.setActualStartDate(parseDate("04052007"));
        screening.setActualEndDate(parseDate("05052007"));
        screening.setFixedStartDate(parseDate("05052007"));
        
        Phase submit = new Phase(project, 4* 24 * 3600 * 1000L);
        
        submit.setPhaseType(new PhaseType(2, "submit"));
        submit.setPhaseStatus(PhaseStatus.SCHEDULED);
        submit.setScheduledStartDate(parseDate("01052007"));
        submit.setScheduledEndDate(parseDate("03052007"));
        
        submit.setActualStartDate(parseDate("01052007"));
        submit.setActualEndDate(parseDate("03052007"));
        submit.setFixedStartDate(parseDate("03052007"));
        submit.setAttribute("designer", "pops");
        
        Dependency dep = new Dependency(submit, screening, false, true,24 * 3600 * 1000L);
        screening.addDependency(dep);
        
        return new Phase[]{submit, screening};
    }
}
