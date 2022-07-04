/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.Statement;

import java.util.Iterator;


/**
 * Helper class for accuracy tests of manager classes. Contains methods to load and unload configurations.
 *
 * @author skatou
 * @version 1.0
 */
abstract class AccuracyTestsHelper extends TestCase {
    /** Represents namespace containing db connection factory configurations. */
    private static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Cleans up test environment. Configurations are unloaded.
     *
     * @throws Exception pass to JUnit.
     */
    protected void tearDown() throws Exception {
        unloadConfig();
    }

    /**
     * Loads necessary configurations into ConfigManager.
     *
     * @param file the file contains configurations.
     *
     * @throws Exception pass to JUnit.
     */
    protected void loadConfig(String file) throws Exception {
        ConfigManager.getInstance().add(file);
    }

    /**
     * Unloads all configurations. All namespaces in ConfigManager are removed.
     *
     * @throws Exception pass to JUnit.
     */
    protected void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace(it.next().toString());
        }
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file the file contains the sql statements.
     *
     * @throws Exception pass to JUnit.
     */
    protected void executeBatch(String file) throws Exception {
        // get db connection
        Connection connection = new DBConnectionFactoryImpl(DB_CONNECTION_NAMESPACE).createConnection();
        Statement statement = connection.createStatement();

        // get sql statements and add to statement
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                statement.addBatch(line);
            }
        }

        statement.executeBatch();
    }
}
