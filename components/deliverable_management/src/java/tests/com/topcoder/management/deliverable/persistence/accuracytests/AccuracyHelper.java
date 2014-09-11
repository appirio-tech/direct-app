/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */



package com.topcoder.management.deliverable.persistence.accuracytests;

import com.topcoder.util.config.ConfigManager;

import java.io.FileReader;
import java.io.Reader;

import java.sql.Connection;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * A helper class for accuracy test.
 * </p>
 *
 * @author arylio
 * @version 1.0
 */
public class AccuracyHelper {
    /**
     * <p>
     * The file contains clear sql statments.
     * </p>
     */
    private static String CLEAR_FILE = "./test_files/accuracytests/clear.sql";

    /**
     * <p>
     * The config file for db connection factory.
     * </p>
     */
    private static String DB_CONFIG = "accuracytests/dbconnectionfactory.xml";

    /**
     * <p>
     * The file contains prepare sql statments.
     * </p>
     */
    private static String PREPARE_FILE = "./test_files/accuracytests/prepare.sql";

    /**
     * <p>
     * Remove all namespace.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public static void clearNamespace() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Load config file for test.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public static void loadConfig() throws Exception {
        ConfigManager.getInstance().add(DB_CONFIG);
    }

    /**
     * <p>
     * Clear the data in table.
     * </p>
     *
     * @param connection the connection.
     * @throws Exception Exception to JUnit.
     */
    public static void clearData(Connection connection) throws Exception {
        executeSqlFile(CLEAR_FILE, connection);
    }

    /**
     * <p>
     * Prepare the data in table.
     * </p>
     *
     * @param connection the connection.
     */
    public static void prepareData(Connection connection) {
        try {
            executeSqlFile(PREPARE_FILE, connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute the sql statements in a file.
     *
     * @param filename the sql file.
     * @throws Exception to JUnit
     */
    private static void executeSqlFile(String filename, Connection connection) throws Exception {
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

        // parse the sqls
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }

        Statement stmt = null;

        try {
            stmt = connection.createStatement();;

            for (int i = 0; i < sqls.size(); i++) {
                stmt.executeUpdate((String) sqls.get(i));
            }
        } finally {
            stmt.close();
        }
    }
}
