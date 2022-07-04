/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.stresstests;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.util.config.ConfigManager;


/**
 * Defines helper methods used in tests.
 *
 * @author mgmg
 * @version 1.0
 */
public class StressTestHelper extends TestCase {
    /** Represents the namespace used for testing. */
    public static final String NAMESPACE = 
        "com.topcoder.management.project.persistence.InformixProjectPersistence";

    /**
     * Emtpy private constructor to make this class can not be instanced.
     */
    private StressTestHelper() {
    }

    /**
     * <p>
     * clear the config.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * add the config.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void addConfig() throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("stresstests/stress.xml");
        configManager.add("stresstests/dbfactory.xml");
    }
    
    /**
     * Insert the database records for testing.
     *
     * @throws Exception
     *             to junit
     */
    public static void insertTestRecords() throws Exception {
        clearAllTestRecords();
        executeSqlFile("test_files/stresstests/insert.sql");
    }

    /**
     * Delete all records in database for testing.
     *
     * @throws Exception
     *             to junit
     */
    public static void clearAllTestRecords() throws Exception {
        executeSqlFile("test_files/stresstests/delete.sql");
    }

    /**
     * Get the database connection.
     *
     * @return the connection created.
     * @throws Exception
     *             to JUnit
     */
    private static Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
    }

    /**
     * Execute the sql statements in a file.
     *
     * @param filename
     *            the sql file.
     * @throws Exception
     *             to JUnit
     */
    private static void executeSqlFile(String filename) throws Exception {
        Reader file = new FileReader(filename);
        char[] buffer = new char[1024];
        int retLength = 0;
        StringBuffer content = new StringBuffer();

        while ((retLength = file.read(buffer)) >= 0) {
            content.append(buffer, 0, retLength);
        }

        file.close();

        List sqls = new ArrayList();
        int lastIndex = 0;

        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        try {
            for (int i = 0; i < sqls.size(); i++) {
                stmt.executeUpdate((String) sqls.get(i));
            }
        } finally {
            stmt.close();
            conn.close();
        }
    }
    
    /**
     * Get a sample Project object to test, with project id = 0, project
     * category = .Net, project type = Topcoder, project status = Active.
     * @return a sample Project object
     */
    static Project getSampleProject() {
        // create a ProjectStatus object
        ProjectStatus status = new ProjectStatus(1, "Active");

        // create a ProjectType object
        ProjectType type = new ProjectType(1, "Topcoder");

        // create a ProjectCategory object
        ProjectCategory category = new ProjectCategory(1, ".Net", type);

        // create the sample project object
        Project project = new Project(category, status);

        // set the properties
        project.setProperty("property 1", "value 1");
        project.setProperty("property 2", "value 2");
        return project;
    }

    /**
     * Get a sample Project object to test, with project id = 0, project
     * category = Customer Java, project type = Customer, project status =
     * Inactive.
     * @return a sample Project object
     */
    static Project getUpdatedProject() {
        // create a ProjectStatus object
        ProjectStatus status = new ProjectStatus(2, "Inactive");

        // create a ProjectType object
        ProjectType type = new ProjectType(2, "Customer");

        // create a ProjectCategory object
        ProjectCategory category = new ProjectCategory(4, "Customer Java", type);

        // create the sample project object
        Project project = new Project(category, status);

        // set the properties
        project.setProperty("property 2", "new value 2");
        project.setProperty("property 3", "value 3");
        project.setProperty("property 4", "value 4");
        return project;
    }
}
