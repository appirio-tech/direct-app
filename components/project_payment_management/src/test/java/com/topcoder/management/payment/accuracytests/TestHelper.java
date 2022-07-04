/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.accuracytests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Properties;


/**
 * <p>Helper methods used for the accuracy tests.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public final class TestHelper {
    /** Properties for the DB connection. */
    private final static Properties P = new Properties();

    static {
        try {
            P.load(new FileInputStream("test_files/accuracy/db.properties"));
        } catch (FileNotFoundException e) {
            // ignore
        } catch (IOException e) {
            // ignore
        }
    }

/**
     * Private constructor.
     */
    private TestHelper() {
    }

    /**
     * Get the jdbc connection.
     *
     * @return the jdbc connection
     *
     * @throws Exception if any error
     */
    public static Connection getConnection() throws Exception {
        Class.forName(P.getProperty("jdbc.driverClassName"));

        return DriverManager.getConnection(P.getProperty("jdbc.url"), P.getProperty("jdbc.username"),
            P.getProperty("jdbc.password"));
    }

    /**
     * <p>Closes the given connection.</p>
     *
     * @param connection the given connection.
     *
     * @throws Exception to JUnit.
     */
    public static void closeConnection(Connection connection)
        throws Exception {
        if ((connection != null) && (!connection.isClosed())) {
            connection.close();
        }
    }

    /**
     * <p>Loads data into the database.</p>
     *
     * @param connection the connection.
     *
     * @throws Exception to JUnit.
     */
    public static void loadDB(Connection connection) throws Exception {
        executeSQL(connection, "test_files/accuracy/insert.sql");
    }

    /**
     * <p>Clears the database.</p>
     *
     * @param connection the connection.
     *
     * @throws Exception to JUnit.
     */
    public static void clearDB(Connection connection) throws Exception {
        executeSQL(connection, "test_files/accuracy/delete.sql");
    }

    /**
     * <p>Executes the SQL statements in the file. Empty lines will be ignored.</p>
     *
     * @param connection the connection.
     * @param file the file.
     *
     * @throws Exception to JUnit.
     */
    private static void executeSQL(Connection connection, String file)
        throws Exception {
        String[] values = readFile(file).split(";");

        Statement statement = connection.createStatement();

        try {
            for (int i = 0; i < values.length; i++) {
                String sql = values[i].trim();

                if (sql.length() != 0) {
                    statement.executeUpdate(sql);
                }
            }
        } finally {
            statement.close();
        }
    }

    /**
     * <p>Reads the content of a given file.</p>
     *
     * @param fileName the name of the file to read.
     *
     * @return a string represents the content.
     *
     * @throws IOException if any error occurs during reading.
     */
    private static String readFile(String fileName) throws IOException {
        Reader reader = new FileReader(fileName);

        try {
            // Create a StringBuilder instance
            StringBuilder sb = new StringBuilder();

            // Buffer for reading
            char[] buffer = new char[1024];

            // Number of read chars
            int k = 0;

            // Read characters and append to string builder
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }

            // Return read content
            return sb.toString().replace("\r\n", "\n");
        } finally {
            reader.close();
        }
    }
}
