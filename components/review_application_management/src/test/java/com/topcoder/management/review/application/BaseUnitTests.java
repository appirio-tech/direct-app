/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;
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
    private static final String CONFIG_FILE = TEST_FILES + "ReviewApplicationManagement.xml";

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
     * Checks the auction types.
     *
     * @param method
     *            the method
     * @param auctionTypes
     *            the auction types
     */
    public static void checkAuctionTypes(String method, List<ReviewAuctionType> auctionTypes) {
        assertEquals("'" + method + "' should be correct.", 3, auctionTypes.size());

        checkAuctionType1(method, auctionTypes.get(0));
        checkAuctionType2(method, auctionTypes.get(1));
        checkAuctionType3(method, auctionTypes.get(2));
    }

    /**
     * Checks the entity.
     *
     * @param method
     *            the method
     * @param entity
     *            the entity
     * @param id
     *            the id
     * @param name
     *            the name
     */
    public static void checkEntity(String method, BaseLookupEntity entity, long id, String name) {
        assertEquals("'" + method + "' should be correct.", id, entity.getId());
        assertEquals("'" + method + "' should be correct.", name, entity.getName());
    }

    /**
     * Checks the result.
     *
     * @param method
     *            the method
     * @param result
     *            the result
     * @param values
     *            the expected values
     */
    public static void checkResult(String method, List<Object> result, Object... values) {
        int index = 0;
        for (Object value : result) {
            if (!(value instanceof Date)) {

                value = value.toString().trim();

                assertEquals("'" + method + "' should be correct.", "" + values[index++], value);
            }
        }
    }

    /**
     * Executes the SQL.
     *
     * @param connection
     *            the connection
     * @param columnsCount
     *            the count of columns
     * @param sql
     *            the SQL string
     * @param params
     *            the parameters
     *
     * @return the result
     *
     * @throws Exception
     *             if any error occurs.
     */
    public static List<List<Object>> executeQuery(Connection connection, int columnsCount, String sql, Object... params)
        throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            // Set parameters
            setParameters(preparedStatement, params);

            // Execute
            ResultSet resultSet = preparedStatement.executeQuery();

            List<List<Object>> result = new ArrayList<List<Object>>();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<Object>();
                for (int i = 0; i < columnsCount; i++) {
                    row.add(resultSet.getObject(i + 1));
                }

                result.add(row);
            }

            return result;
        } finally {
            preparedStatement.close();
        }
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
        ConfigurationObject config = getConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE).getChild(
            "persistenceConfig");

        // Get configuration of DB Connection Factory
        ConfigurationObject dbConnectionFactoryConfig = Helper.getChildConfig(config, "dbConnectionFactoryConfig");

        // Create database connection factory using the extracted configuration
        DBConnectionFactoryImpl dbConnectionFactory = new DBConnectionFactoryImpl(dbConnectionFactoryConfig);
        return dbConnectionFactory.createConnection();
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

    /**
     * Sets the parameters.
     *
     * @param preparedStatement
     *            the prepared statement
     * @param params
     *            the parameters
     *
     * @throws SQLException
     *             if any error occurs
     */
    private static void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        // Set parameters
        for (int i = 0; i < params.length; i++) {
            int index = i + 1;
            Object paramValue = params[i];

            preparedStatement.setObject(index, paramValue);
        }
    }

    /**
     * Checks the auction type 1.
     *
     * @param method
     *            the method
     * @param auctionType
     *            the auction type
     */
    private static void checkAuctionType1(String method, ReviewAuctionType auctionType) {
        checkEntity(method, auctionType, 1, "Regular Contest Review");
        checkEntity(method, auctionType.getAuctionCategory(), 1, "Contest Review");

        List<ReviewApplicationRole> applicationRoles = auctionType.getApplicationRoles();
        assertEquals("'" + method + "' should be correct.", 2, applicationRoles.size());
        ReviewApplicationRole reviewApplicationRole = applicationRoles.get(0);
        checkEntity(method, reviewApplicationRole, 1, "Primary Reviewer", 1);
        List<ReviewApplicationResourceRole> resourceRoles = reviewApplicationRole.getResourceRoles();
        assertEquals("'" + method + "' should be correct.", 4, resourceRoles.size());
        checkEntity(method, resourceRoles.get(0), 2, true);
        checkEntity(method, resourceRoles.get(1), 4, false);
        checkEntity(method, resourceRoles.get(2), 8, true);
        checkEntity(method, resourceRoles.get(3), 9, true);
        reviewApplicationRole = applicationRoles.get(1);
        checkEntity(method, reviewApplicationRole, 2, "Secondary Reviewer", 3);
        resourceRoles = reviewApplicationRole.getResourceRoles();
        assertEquals("'" + method + "' should be correct.", 1, resourceRoles.size());
        checkEntity(method, resourceRoles.get(0), 4, false);
    }

    /**
     * Checks the auction type 2.
     *
     * @param method
     *            the method
     * @param auctionType
     *            the auction type
     */
    private static void checkAuctionType2(String method, ReviewAuctionType auctionType) {
        checkEntity(method, auctionType, 2, "Component Development Review");
        checkEntity(method, auctionType.getAuctionCategory(), 1, "Contest Review");

        List<ReviewApplicationRole> applicationRoles = auctionType.getApplicationRoles();
        assertEquals("'" + method + "' should be correct.", 4, applicationRoles.size());
        ReviewApplicationRole reviewApplicationRole = applicationRoles.get(0);
        checkEntity(method, reviewApplicationRole, 3, "Primary Failure Reviewer", 1);
        List<ReviewApplicationResourceRole> resourceRoles = reviewApplicationRole.getResourceRoles();
        assertEquals("'" + method + "' should be correct.", 4, resourceRoles.size());
        checkEntity(method, resourceRoles.get(0), 2, true);
        checkEntity(method, resourceRoles.get(1), 6, true);
        checkEntity(method, resourceRoles.get(2), 8, true);
        checkEntity(method, resourceRoles.get(3), 9, true);
        reviewApplicationRole = applicationRoles.get(1);
        checkEntity(method, reviewApplicationRole, 4, "Accuracy Reviewer", 1);
        resourceRoles = reviewApplicationRole.getResourceRoles();
        assertEquals("'" + method + "' should be correct.", 1, resourceRoles.size());
        checkEntity(method, resourceRoles.get(0), 5, true);
        reviewApplicationRole = applicationRoles.get(2);
        checkEntity(method, reviewApplicationRole, 5, "Stress Reviewer", 1);
        resourceRoles = reviewApplicationRole.getResourceRoles();
        assertEquals("'" + method + "' should be correct.", 1, resourceRoles.size());
        checkEntity(method, resourceRoles.get(0), 7, true);
        reviewApplicationRole = applicationRoles.get(3);
        checkEntity(method, reviewApplicationRole, 6, "Failure Reviewer", 1);
        resourceRoles = reviewApplicationRole.getResourceRoles();
        assertEquals("'" + method + "' should be correct.", 1, resourceRoles.size());
        checkEntity(method, resourceRoles.get(0), 6, true);
    }

    /**
     * Checks the auction type 3.
     *
     * @param method
     *            the method
     * @param auctionType
     *            the auction type
     */
    private static void checkAuctionType3(String method, ReviewAuctionType auctionType) {
        checkEntity(method, auctionType, 3, "Specification Review");
        checkEntity(method, auctionType.getAuctionCategory(), 2, "Specification Review");
        List<ReviewApplicationRole> applicationRoles = auctionType.getApplicationRoles();
        assertEquals("'" + method + "' should be correct.", 1, applicationRoles.size());
        ReviewApplicationRole reviewApplicationRole = applicationRoles.get(0);
        checkEntity(method, reviewApplicationRole, 7, "Specification Reviewer", 1);
        List<ReviewApplicationResourceRole> resourceRoles = reviewApplicationRole.getResourceRoles();
        assertEquals("'" + method + "' should be correct.", 1, resourceRoles.size());
        checkEntity(method, resourceRoles.get(0), 18, true);
    }

    /**
     * Checks the ReviewApplicationResourceRole entity.
     *
     * @param method
     *            the method
     * @param entity
     *            the entity
     * @param resourceRoleId
     *            the resource role id
     * @param uniqueRole
     *            the unique role flag
     */
    private static void checkEntity(String method, ReviewApplicationResourceRole entity, long resourceRoleId,
        boolean uniqueRole) {
        assertEquals("'" + method + "' should be correct.", resourceRoleId, entity.getResourceRoleId());
        assertEquals("'" + method + "' should be correct.", uniqueRole, entity.isUniqueRole());
    }

    /**
     * Checks the ReviewApplicationRole entity.
     *
     * @param method
     *            the method
     * @param entity
     *            the entity
     * @param id
     *            the id
     * @param name
     *            the name
     * @param positions
     *            the positions
     */
    private static void checkEntity(String method, ReviewApplicationRole entity, long id, String name, long positions) {
        checkEntity(method, entity, id, name);

        assertEquals("'" + method + "' should be correct.", positions, entity.getPositions());
    }
}