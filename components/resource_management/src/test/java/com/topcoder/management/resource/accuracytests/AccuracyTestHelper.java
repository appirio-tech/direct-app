/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.util.config.ConfigManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Iterator;

/**
 * <p>
 * Helper class to simplify the accuracy testing.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class AccuracyTestHelper {
    /**
     * <p>
     * The private constructor to avoid creating instance of this class.
     * </p>
     */
    private AccuracyTestHelper() {
    }

    /**
     * <p>
     * Clear the namespaces in ConfigManager.
     * </p>
     * @throws Exception if configuration could not be clear.
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
     * Clear the given tables from the database.
     * </p>
     * @param conn the Connection to execute.
     * @param tables the array of table name to clear.
     * @throws Exception if any error occurs.
     */
    public static void clearTables(Connection conn, String[] tables) throws Exception {
        Statement stmt = conn.createStatement();

        try {
            for (int i = 0; i < tables.length; i++) {
                stmt.executeUpdate("DELETE FROM " + tables[i]);
            }
        } finally {
            stmt.close();
        }
    }

    /**
     * <p>
     * Convert the content of the given file to string.
     * </p>
     * @param filename the file to read.
     * @return the content of the file.
     * @throws Exception any exception occurs.
     */
    public static String fileToString(String filename) throws Exception {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));

            StringBuffer sb = new StringBuffer();

            // Buffer for reading.
            char[] buffer = new char[1024];
            int k = 0;

            // Read characters and append to string buffer
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }

            return sb.toString();
        } finally {
            reader.close();
        }
    }

    /**
     * <p>
     * Run the sql statements in the specified file.
     * </p>
     * @param conn the Connection to execute.
     * @param fileName the file of sqls.
     * @throws Exception any exception occurs.
     */
    public static void runSqlFromFile(Connection conn, String fileName) throws Exception {
        Statement stmt = conn.createStatement();

        try {
            String[] sqls = fileToString(fileName).split("\\;");

            // execute each sql.
            for (int i = 0; i < sqls.length; i++) {
                if (sqls[i].trim().length() > 0) {
                    stmt.execute(sqls[i]);
                }
            }
        } finally {
            stmt.close();
        }
    }

    /**
     * <p>
     * Gets the record size from the database with the given table.
     * </p>
     * @param conn the Connection to execute.
     * @param tableName the table name (tracking_info).
     * @return the size of the record in given table.
     * @throws Exception if any error occurs.
     */
    public static int getTableSize(Connection conn, String tableName) throws Exception {
        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);

            // return the size here.
            rs.next();

            return rs.getInt(1);
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }
}
