/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.failuretests;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * This is a Helper class.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class FailureHelper extends TestCase {
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
        executeSQL(getConnection(), "test_files/failure/clear.sql");
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
        executeSQL(getConnection(), "test_files/failure/data.sql");
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

            ConfigurationFileManager configFileManager = new ConfigurationFileManager("accuracy/DefaultProjectPaymentCalculator.properties");
            ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator");
            ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator");
            ConfigurationObject dbConnFactoryConfig =
            		config.getChild("db_connection_factory_config");
            DBConnectionFactoryImpl dbConnectionFactory = new DBConnectionFactoryImpl(dbConnFactoryConfig);
            connection = dbConnectionFactory.createConnection("informixconnection");
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
}
