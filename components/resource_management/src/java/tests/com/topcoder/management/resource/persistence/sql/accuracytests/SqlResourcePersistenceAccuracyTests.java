/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.resource.persistence.sql.SqlResourcePersistence;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.File;

import java.sql.Connection;

/**
 * Unit test cases for class <code>SqlResourcePersistence </code>. In this
 * test class, the functionality of this component will be tested.
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class SqlResourcePersistenceAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the SqlResourcePersistence instance for test.
     * </p>
     */
    private SqlResourcePersistence instance = null;

    /**
     * <p>
     * Represents the DBConnectionFactory instance for test.
     * </p>
     */
    private DBConnectionFactory factory = null;

    /**
     * <p>
     * The namespace for the db connection.
     * </p>
     */
    private final String dbNamespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Set up the environment.
     * </p>
     * @throws Exception to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        AccuracyHelper.clearConfigManager();

        File file = new File("test_files/accuracy/DBConnectionFactory.xml");

        cm.add(file.getAbsolutePath());

        factory = new DBConnectionFactoryImpl(dbNamespace);

        instance = new SqlResourcePersistence(factory);

        AccuracyHelper.clearTables();

        AccuracyHelper.setupDatbase();
    }

    /**
     * <p>
     * Tear down the environment. Clear all namespaces in the config manager.
     * </p>
     * @throws Exception to JUnit.
     */
    public void tearDown() throws Exception {
        AccuracyHelper.clearConfigManager();
    }

    /**
     * <p>
     * Test the constructor with one parameter.
     * </p>
     * @throws Exception throw to JUnit.
     */
    public void testCtor1Accuracy() throws Exception {
        instance = new SqlResourcePersistence(factory);
        assertNotNull("The created SqlResourcePersistence object should not be null.", instance);
    }

    /**
     * <p>
     * Test the constructor with two parameters.
     * </p>
     * @throws Exception throw to JUnit.
     */
    public void testCtor2Accuracy() throws Exception {
        instance = new SqlResourcePersistence(factory, "sysuser");
        assertNotNull("The created db connection factory should not be null.", instance);
    }

    /**
     * <p>
     * Test the method openConnection().
     * </p>
     * @throws Exception throw to JUnit.
     */
    public void testOpenConnectionAccuracy() throws Exception {
        Class[] paramsClass = new Class[0];
        Object[] params = new Object[0];
        Connection conn = (Connection) AccuracyHelper.invokeMethod(instance, SqlResourcePersistence.class,
                "openConnection", paramsClass, params);

        assertNotNull("the connection to the database should no be null.", conn);
        // because it support transaction, so the auto commit is false.
        assertEquals("The auto commit is not set.", false, conn.getAutoCommit());
    }

    /**
     * <p>
     * Test the method <code>closeConnection(Connection connection)</code>.
     * </p>
     * @throws Exception throw to JUnit.
     */
    public void testCloseConnectionAccuracy() throws Exception {
        // open the connection
        Class[] paramsClass = new Class[0];
        Object[] params = new Object[0];
        Connection conn = (Connection) AccuracyHelper.invokeMethod(instance, SqlResourcePersistence.class,
                "openConnection", paramsClass, params);

        // close the conn.
        Class[] paramsClass1 = new Class[1];
        paramsClass1[0] = Connection.class;

        Object[] params1 = new Object[1];
        params1[0] = conn;
        AccuracyHelper.invokeMethod(instance, SqlResourcePersistence.class, "closeConnection", paramsClass1,
                params1);
        // it should be closed.
        assertTrue(conn.isClosed());
    }

    /**
     * <p>
     * Test the method
     * <code>closeConnectionOnError(Connection connection)</code>.
     * </p>
     * @throws Exception throw to JUnit.
     */
    public void testCloseConnectionOnErrorAccuracy() throws Exception {
        // open the connection
        Class[] paramsClass = new Class[0];
        Object[] params = new Object[0];
        Connection connection = (Connection) AccuracyHelper.invokeMethod(instance,
                SqlResourcePersistence.class, "openConnection", paramsClass, params);

        // close the connection.
        Class[] paramsClass1 = new Class[1];
        paramsClass1[0] = Connection.class;

        Object[] params1 = new Object[1];
        params1[0] = connection;
        AccuracyHelper.invokeMethod(instance, SqlResourcePersistence.class, "closeConnectionOnError",
                paramsClass1, params1);

        // it should be closed.
        assertTrue(connection.isClosed());
    }
}
