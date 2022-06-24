/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.payment.impl.ProjectPaymentManagerImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * The base test case for Unit tests.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public abstract class BaseUnitTests {
    /**
     * <p>
     * Represents the empty string.
     * </p>
     */
    public static final String EMPTY_STRING = " \t ";

    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    public static final String TEST_FILES = "test_files" + File.separator;

    /**
     * <p>
     * Represents the configuration file used in tests.
     * </p>
     */
    private static final String CONFIG_FILE = TEST_FILES + "ProjectPaymentManager.xml";

    /**
     * Holds database connection.
     */
    private Connection connection;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        unloadConfig();
        loadConfig("SearchBundleManager.xml");

        connection = createConnection();
        clearDB();
        loadDB();
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        unloadConfig();

        if (connection.isClosed()) {
            connection = createConnection();
        }
        clearDB();
        closeConnection();
        connection = null;
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
                    // From the superclass
                    declaredField = obj.getClass().getSuperclass().getDeclaredField(field);

                } catch (NoSuchFieldException e) {
                    // Ignore
                }
            }

            if (declaredField == null) {
                // From the superclass
                declaredField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(field);
            }

            declaredField.setAccessible(true);

            value = declaredField.get(obj);

            declaredField.setAccessible(false);
        } catch (IllegalArgumentException e) {
            // Ignore
        } catch (SecurityException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }

        return value;
    }

    /**
     * <p>
     * Gets the configuration object used for tests.
     * </p>
     *
     * @param namespace
     *            the namespace.
     *
     * @return the configuration object.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static ConfigurationObject getConfig(String namespace) throws Exception {
        XMLFilePersistence persistence = new XMLFilePersistence();

        // Get configuration
        ConfigurationObject obj = persistence.loadFile(namespace, new File(CONFIG_FILE));

        return obj.getChild(namespace);
    }

    /**
     * Executes the SQL.
     *
     * @param sql
     *            the SQL string
     *
     * @throws Exception
     *             if any error occurs.
     */
    protected void executeUpdate(String sql) throws Exception {
        Statement statement = connection.createStatement();
        try {
            statement.executeUpdate(sql);
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * Gets the connection.
     * </p>
     *
     * @return the connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected Connection getConnection() throws Exception {
        return connection;
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void clearDB() throws Exception {
        executeSQL(connection, TEST_FILES + "clear.sql");
    }

    /**
     * <p>
     * Loads data into the database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private void loadDB() throws Exception {
        executeSQL(connection, TEST_FILES + "data.sql");
    }

    /**
     * <p>
     * Loads necessary configurations into ConfigManager.
     * </p>
     *
     * @param file
     *            the file contains configurations.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void loadConfig(String file) throws Exception {
        ConfigManager.getInstance().add(file);
    }

    /**
     * <p>
     * Unloads all configurations.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator<?> it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = it.next().toString();

            if (!"com.topcoder.util.log".equals(ns)) {
                cm.removeNamespace(ns);
            }
        }
    }

    /**
     * <p>
     * Gets a connection.
     * </p>
     *
     * @return the connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static Connection createConnection() throws Exception {
        ConfigurationObject config = getConfig(ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE).getChild(
            "projectPaymentPersistenceConfig");

        // Get configuration of DB Connection Factory
        ConfigurationObject dbConnectionFactoryConfig = config.getChild("dbConnectionFactoryConfig");

        // Create database connection factory using the extracted configuration
        DBConnectionFactoryImpl dbConnectionFactory = new DBConnectionFactoryImpl(dbConnectionFactoryConfig);
        return dbConnectionFactory.createConnection();
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Lines that are empty or starts with '#' will be ignore.
     * </p>
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
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            String[] values = readFile(file).split(";");

            for (int i = 0; i < values.length; i++) {
                String sql = values[i].trim();
                if ((sql.length() != 0) && (!sql.startsWith("#"))) {
                    stmt.executeUpdate(sql);
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>
     * Reads the content of a given file.
     * </p>
     *
     * @param fileName
     *            the name of the file to read.
     *
     * @return a string represents the content.
     *
     * @throws IOException
     *             if any error occurs during reading.
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
            return sb.toString();
        } finally {
            try {
                reader.close();
            } catch (IOException ioe) {
                // Ignore
            }
        }
    }

    /**
     * Closes the connection.
     */
    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                // do nothing.
            }
        }

        connection = null;
    }
}