/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.failuretests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.db.connectionfactory.ConnectionProducer;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the new test in version 1.1.
 * </p>
 *
 * @author iRabbit
 * @version 1.1
 */
public final class FailureTestHelper {

    /**
     * <p>
     * Represent the name of the database to test with. Its value can be configured in
     * "test_files\failuretests\testconfig.xml".
     * </p>
     */
    public static final String DATABASE;

    /**
     * <p>
     * Represent the user name that to connect to the database. Its value can be configured in
     * "test_files\failuretests\testconfig.xml".
     * </p>
     */
    public static final String USER;

    /**
     * <p>
     * Represent the password that to connect to the database. Its value can be configured in
     * "test_files\failuretests\testconfig.xml".
     * </p>
     */
    public static final String PASSWORD;

    /**
     * <p>
     * Represent the host name that to connect to the database. Its value can be configured in
     * "test_files\failuretests\testconfig.xml".
     * </p>
     */
    public static final String HOST;

    /**
     * <p>
     * Represent the port that to connect to the database. Its value can be configured in
     * "test_files\failuretests\testconfig.xml".
     * </p>
     */
    public static final String PORT;

    /**
     * <p>
     * Represent the jdbc driver name. Its value can be configured in "test_files\failuretests\testconfig.xml".
     * </p>
     */
    public static final String DRIVER_NAME;

    /**
     * <p>
     * Represent the name of the INFORMIXSERVER. Its value can be configured in
     * "test_files\failuretests\testconfig.xml".
     * </p>
     */
    public static final String SERVER_NAME;

    /**
     * <p>
     * Represent an instance of DBConnectionFactory for testing use.
     * </p>
     */
    private static DBConnectionFactory factory = null;

    /**
     * <p>
     * Represent test file path.
     * </p>
     */
    private static final String FILE_PATH = "test_files" + File.separator + "failuretests" + File.separator;

    /**
     * <p>
     * Represent the name of the file that holds the informix connection settings for the test.
     * </p>
     */
    private static final String TEST_CONFIG_FILE = getPath("testconfig.xml");

    /**
     * <p>
     * Represent an array of String that hold the script to create tables for test use.
     * </p>
     */
    private static String[] createtablesql = null;

    /**
     * <p>
     * Private constructor to prevent initialization.
     * </p>
     *
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Static code block to initialize those constants from "test_files\failuretests\testconfig.xml" using
     * Configuration API.
     * </p>
     */
    static {
        String values[] = new String[7];
        // build the XMLFilePersistence
        XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();

        // load the ConfigurationObject from the input file
        try {
            ConfigurationObject configObject = xmlFilePersistence.loadFile("test",
                    new File(FailureTestHelper.TEST_CONFIG_FILE)).getChild("testconfig");

            // read the values from configuration object
            values[0] = (String) configObject.getPropertyValue("host");
            values[1] = (String) configObject.getPropertyValue("port");
            values[2] = (String) configObject.getPropertyValue("password");
            values[3] = (String) configObject.getPropertyValue("user");
            values[4] = (String) configObject.getPropertyValue("driver");
            values[5] = (String) configObject.getPropertyValue("database");
            values[6] = (String) configObject.getPropertyValue("server");

        } catch (ConfigurationParserException e) {
            // ignore
        } catch (IOException e) {
            // ignore
        } catch (ConfigurationAccessException e) {
            // ignore
        } finally {
            // initialize these constants in finally block
            HOST = values[0];
            PORT = values[1];
            PASSWORD = values[2];
            USER = values[3];
            DRIVER_NAME = values[4];
            DATABASE = values[5];
            SERVER_NAME = values[6];
        }
    }

    /**
     * <p>
     * Helper method to get the instance of DBConnectionFactory for testing use. If the inner factory is null, a new
     * instance would be initialized.
     * </p>
     *
     * @return an instance of DBConnectionFactory
     * @throws Exception to JUnit
     */
    public static DBConnectionFactory getDBConnectionFactory() throws Exception {
        // create a DBConnectionFactoryImpl if factory if null
        if (factory == null) {
            DBConnectionFactoryImpl factoryImpl = new DBConnectionFactoryImpl();

            Properties parameters = new Properties();
            Class.forName("com.informix.jdbc.IfxDriver");
            parameters.setProperty(JDBCConnectionProducer.JDBC_DRIVER_CLASS_PROPERTY, "com.informix.jdbc.IfxDriver");

            // create a producer for the root
            String rootUrl = "jdbc:informix-sqli://" + HOST + ":" + PORT + ":INFORMIXSERVER=" + SERVER_NAME + ";user="
                    + USER + ";password=" + PASSWORD;
            ConnectionProducer producer = new JDBCConnectionProducer(rootUrl, parameters);
            factoryImpl.add("root", producer);

            // create a producer for the given database
            // add the JDBCConnectionProducer to the factory, as default and name "test"
            String url = "jdbc:informix-sqli://" + HOST + ":" + PORT + "/" + DATABASE + ":INFORMIXSERVER="
                    + SERVER_NAME + ";user=" + USER + ";password=" + PASSWORD;
            producer = new JDBCConnectionProducer(url, parameters);
            factoryImpl.add("test", producer);
            factoryImpl.setDefault("test");

            // create a producer with wrong url
            // add the JDBCConnectionProducer to the factory, as default and name "test"
            String wrongUrl = "jdbc:informix-sqli://wronghost:323/wrongdb:";
            producer = new JDBCConnectionProducer(wrongUrl, parameters);
            factoryImpl.add("wrongConnection", producer);

            factory = factoryImpl;
        }
        return factory;
    }

    /**
     * <p>
     * Helper method to initialize the database for test use. It create a database with the name "tcmodule", create
     * some tables, and add some entries using the sql script.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void initializeDB() throws Exception {
        getDBConnectionFactory();
        Connection connection = factory.createConnection("root");

        // loading the sql statement for creating tables
        if (createtablesql == null) {
            String[] tableName = {"template", "phase_type", "default_template", "phase", "dependency"};
            createtablesql = new String[tableName.length];
            String sqlScriptPath = getPath("sqlscript" + File.separator);
            for (int i = 0; i < tableName.length; ++i) {
                createtablesql[i] = getSQLScript(sqlScriptPath + tableName[i] + ".sql");
            }
        }

        // execute the sql statement to create database, add tables, and add entries
        connection.setAutoCommit(true);
        Statement statement = connection.createStatement();
        statement.execute("create database " + DATABASE + " WITH LOG");
        statement.execute("database " + DATABASE);

        // create tables
        for (int i = 0; i < createtablesql.length; ++i) {
            statement.execute(createtablesql[i]);
        }

        // add some entries
        statement.execute(getSQLScript(getPath("sqlscript" + File.separator + "addentry.sql")));

        // close
        closeStatement(statement);
        closeConnection(connection);
    }

    /**
     * <p>
     * Helper method to initialize the database for test use. It create a database with the name "tcmodule", create the
     * required table tables. No entries would be added.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void initializeDBWithNoEntry() throws Exception {
        getDBConnectionFactory();
        Connection connection = factory.createConnection("root");

        // loading the sql statement for creating tables
        if (createtablesql == null) {
            String[] tableName = {"template", "phase_type", "default_template", "phase", "dependency"};
            createtablesql = new String[tableName.length];
            for (int i = 0; i < tableName.length; ++i) {
                createtablesql[i] = getSQLScript(getPath("sqlscript" + File.separator) + tableName[i] + ".sql");
            }
        }

        // execute the sql statement to create database, add tables, and add entries
        connection.setAutoCommit(true);
        Statement statement = connection.createStatement();
        statement.execute("create database " + DATABASE + " WITH LOG");
        statement.execute("database " + DATABASE);

        // create tables
        for (int i = 0; i < createtablesql.length; ++i) {
            statement.execute(createtablesql[i]);
        }

        closeStatement(statement);
        closeConnection(connection);
    }

    /**
     * <p>
     * Helper method to clear the database after tests. It simply delete the database "tcmodule".
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void clearDB() throws Exception {
        getDBConnectionFactory();
        Connection connection = factory.createConnection("root");
        Statement statement = null;
        try {
            // execute the sql statement to delete the database
            connection.setAutoCommit(true);
            statement = connection.createStatement();
            statement.execute("database " + DATABASE);
            statement.execute("close database");
            statement.execute("drop database " + DATABASE);
        } catch (SQLException e) {
            // ignore
        } finally {
            // close
            closeStatement(statement);
            closeConnection(connection);
        }

    }

    /**
     * <p>
     * Get the sql script from a file with the given name.
     * </p>
     *
     * @param name the name of the file
     * @return the sql script the given file hold
     * @throws Exception to JUnit
     */
    public static String getSQLScript(String name) throws Exception {
        StringBuffer content = new StringBuffer();
        InputStream is = new FileInputStream(new File(name));
        int value;
        while ((value = is.read()) != -1) {
            content.append((char) value);
        }
        return content.toString();
    }

    /**
     * <p>
     * Helper method to close the JDBC interface(statement).
     * </p>
     *
     * @param statement the statement to be closed, could be null
     * @throws SQLException if any error occurs in this method
     */
    public static void closeStatement(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    /**
     * <p>
     * Helper method to close the JDBC interface(connection).
     * </p>
     *
     * @param connection the connection to be closed, could be nulll
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore the exception in this case
            }
        }
    }

    /**
     * <p>
     * Return the test file path.
     * </p>
     *
     * @param fileName short name
     * @return full path
     */
    public static String getPath(String fileName) {
        return FILE_PATH + fileName;
    }
}
