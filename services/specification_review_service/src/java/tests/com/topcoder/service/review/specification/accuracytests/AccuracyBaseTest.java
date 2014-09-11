/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This class provides the basic utility methods to initialize DB and set up the test
 * environment. It is referenced from class <code>StressBaseTest</code> of Online Review Phases
 * version 1.4
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
abstract class AccuracyBaseTest extends TestCase {

    /** constant for database connection factory configuration file. */
    public static final String DB_FACTORY_CONFIG_FILE = "config/DB_Factory.xml";

    /** constant for logging wrapper configuration file. */
    public static final String LOGGING_WRAPPER_CONFIG_FILE = "config/Logging_Wrapper.xml";

    /** array of all the config file names for various dependency components. */
    public static final String[] COMPONENT_FILE_NAMES = new String[]{"config/Project_Management.xml",
        "config/Phase_Management.xml", "config/Review_Management.xml",
        "config/Scorecard_Management.xml", "config/Screening_Management.xml",
        "config/Upload_Resource_Search.xml", "config/User_Project_Data_Store.xml",
        "config/SearchBuilderCommon.xml", "config/Project_Services.xml",
        "config/UploadExternal_Services.xml", "config/Failure_Config.xml" };

    /** an array of table names to be cleaned in setup() and tearDown() methods. */
    private static final String[] ALL_TABLE_NAMES = new String[]{"project_user_audit",
        "screening_result", "screening_task", "project_phase_audit", "project_info_audit",
        "notification", "project_audit", "review", "resource_submission", "submission", "upload",
        "resource_info", "resource", "phase_criteria", "phase_dependency", "project_phase",
        "project_scorecard", "project_info", "project", "scorecard", "comp_forum_xref", "comp_versions",
        "categories", "comp_catalog", "user_reliability", "user_rating", "user", "email",
        "linked_project_xref" };

    /** Represents the configuration manager instance used in tests. */
    private ConfigManager configManager;

    /** Holds db connection factory instance. */
    private DBConnectionFactory dbFactory;

    /** Holds connection. */
    private Connection connection;

    /** Day time. */
    public static final long DAY = 24 * 60 * 60 * 1000;

    /**
     * <p>
     * Sets up the test environment. The configurations are removed.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("deprecation")
    protected void setUp() throws Exception {
        configManager = ConfigManager.getInstance();

        // Remove all namespaces
        Iterator<?> iter = configManager.getAllNamespaces();

        while (iter.hasNext()) {
            configManager.removeNamespace((String) iter.next());
        }

        // init db factory
        configManager.add(DB_FACTORY_CONFIG_FILE);

        // load logging wrapper configuration
        configManager.add(LOGGING_WRAPPER_CONFIG_FILE);

        // add all dependencies config
        for (String config : COMPONENT_FILE_NAMES) {
            configManager.add(config);
        }

        dbFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // Remove all namespaces
        Iterator<?> iter = configManager.getAllNamespaces();

        while (iter.hasNext()) {
            configManager.removeNamespace((String) iter.next());
        }
        cleanTables();
        closeConnection();
    }

    /**
     * Returns a connection instance.
     *
     * @return a connection instance.
     *
     * @throws Exception not for this test case.
     */
    protected Connection getConnection() throws Exception {
        if (connection == null) {
            connection = dbFactory.createConnection();
        }

        return connection;
    }

    /**
     * Closes the connection.
     */
    protected void closeConnection() {
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
     * helper method to close a statement.
     *
     * @param stmt statement to close.
     */
    protected void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                // do nothing
            }
        }
    }

    /**
     * Cleans up records in the given table names.
     *
     * @throws Exception not under test.
     */
    protected void cleanTables() throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();

            for (int i = 0; i < ALL_TABLE_NAMES.length; i++) {
                String sql = "delete from " + ALL_TABLE_NAMES[i];
                stmt.addBatch(sql);
            }

            stmt.executeBatch();
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }

    /**
     * Gets the value of a private field in the given class. The field has the given name. The
     * value is retrieved from the given instance. If the instance is null, the field is a
     * static field. If any error occurs, null is returned.
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be retrieved
     * @return the value of the private field
     */
    public static Object getPrivateField(Class<?> type, Object instance, String name) {
        Field field = null;
        Object obj = null;
        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible.
            field.setAccessible(true);

            // get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
        return obj;
    }

    /**
     * <p>
     * Sets the value of a private field in the given class.
     * </p>
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     */
    public static void setPrivateField(Class<?> type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }
}
