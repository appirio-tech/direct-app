/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * TestHelper.java
 */
package com.topcoder.management.scorecard.persistence.stresstests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * Set of help functions.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTestHelper {

    /**
     * Storage for all configurations tests.
     */
    private static final String CONFIG_TESTS_FILE = "stress/configurations.xml";

    /**
     * Storage for sql statements for inserting values in defaultusers table.
     */
    private static final String PRECONDITIONS_SQL_FILE = "test_files/stress/preconditions.sql";

    /**
     * empty construction.
     */
    private StressTestHelper() {
    }

    /**
     * Adding data from sql file for testing purposes.
     * @param connection connection to database
     * @throws Exception wrap all exceptions
     */
    public static void addTestData(Connection connection) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(PRECONDITIONS_SQL_FILE));
        String sql;
        while ((sql = reader.readLine()) != null) {
            // ececute each sql insert statement,
            if (sql.length() > 2) {
                connection.createStatement().executeUpdate(sql);
            }
        }
    }

    /**
     * Deleting all data from specified table.
     *
     * @param connection connection to database
     * @throws Exception wrap all exceptions
     */
    public static void clearTables(Connection connection) throws Exception {
        String[] tables = new String[] {"scorecard_question",
            "scorecard_section", "scorecard_group", "scorecard",
            "scorecard_status_lu", "scorecard_type_lu",
            "scorecard_question_type_lu", "project_category_lu" };

        for (int i = 0; i < tables.length; i++) {
            String query = "DELETE FROM " + tables[i];
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }
    }

    /**
     * Loading test configurations.
     * @throws Exception wrap all exceptions
     */
    public static void loadAllConfig() throws Exception {
        clearTestConfig();
        ConfigManager config = ConfigManager.getInstance();
        config.add(CONFIG_TESTS_FILE);
    }

    /**
     * Clearing all configurations.
     * @throws Exception wrap all exceptions
     */
    public static void clearTestConfig() throws Exception {
        ConfigManager config = ConfigManager.getInstance();
        Iterator iter = config.getAllNamespaces();
        while (iter.hasNext()) {
            String namespace = (String) iter.next();
            config.removeNamespace(namespace);
        }
    }
}