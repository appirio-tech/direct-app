/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.stresstests;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

import junit.framework.TestCase;

/**
 * <p>
 * This test case aggregates all stress unit test cases.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class StressHelper extends TestCase {
    private static Connection connection;

    /**
     * Clear the database.
     * 
     * @param connection
     *            the connection.
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected static void clearDB( ) throws Exception {
        executeSQL(getConnection(), "test_files/stress/clear.sql");
    }

    /**
     * Clear the database.
     * 
     * @param connection
     *            the connection.
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected static void prepareDB( ) throws Exception {
        executeSQL(getConnection(), "test_files/stress/data.sql");
    }
    
    /**
     * Clear the database.
     * 
     * @param connection
     *            the connection.
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected static Connection getConnection() throws Exception {
    	if (connection == null) {

            Properties p = new Properties();
            FileInputStream in = new FileInputStream("test_files/stress/database.properties");
            p.load(in);
            in.close();
            Class.forName(p.getProperty("DatabaseDriver"));
            return DriverManager.getConnection(p.getProperty("DatabaseUrl"), p.getProperty("username"), p
                    .getProperty("password"));
    	}
    	return connection;
    }
    /**
     * Executes the SQL statements from file.
     * 
     * @param connection
     *            the connection.
     * @param file
     *            the file.
     * 
     * @throws Exception
     *             to JUnit.
     */
    private static void executeSQL(Connection connection, String file) throws Exception {
        String[] values = readFile(file).split(";");

        Statement statement = connection.createStatement();
        try {

            for (int i = 0; i < values.length; i++) {
                String sql = values[i].trim();
                if ((sql.length() != 0) && (!sql.startsWith("#"))) {
                    statement.executeUpdate(sql);
                }
            }
        } finally {
            statement.close();
        }
    }

    /**
     * Reads file to a string.
     * 
     * @param fileName
     *            the name of the file to read.
     * 
     * @return a string represents the content.
     * 
     * @throws IOException
     *             if any IO error occurs.
     */
    private static String readFile(String fileName) throws IOException {
        Reader reader = new FileReader(fileName);

        try {
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            int k = 0;
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }
            return sb.toString().replace("\r\n", "\n");
        } finally {
            try {
                reader.close();
            } catch (IOException ioe) {
                // Ignore
            }
        }
    }
    

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     *
     * @return the field value.
     */
    public static Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (declaredField == null) {
                try {
                    declaredField = obj.getClass().getSuperclass().getDeclaredField(field);
                } catch (NoSuchFieldException e) {
                    // Ignore
                }
            }

            if (declaredField == null) {
                declaredField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(field);
            }

            declaredField.setAccessible(true);

            try {
                value = declaredField.get(obj);
            } finally {
                declaredField.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }

        return value;
    }
}
