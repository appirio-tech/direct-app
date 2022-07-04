/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.ProjectPersistence;
import com.topcoder.management.project.persistence.Helper.DataType;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Unit tests for class <code>UnmanagedTransactionInformixProjectPersistence</code>.
 * </p>
 * <p>
 * Note that the constructor just calls the super constructor, so it is not tested again, only one simple case is
 * provided.
 * </p>
 *
 * @author fuyun
 * @version 1.1
 */
public class UnmanagedTransactionInformixProjectPersistenceTest extends TestCase {

    /**
     * Represents the <code>UnmanagedTransactionInformixProjectPersistence</code> instance which is used to test the
     * functionality of <code>UnmanagedTransactionInformixProjectPersistence</code>.
     */
    private UnmanagedTransactionInformixProjectPersistence persistence = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * <p>
     * Sets up the necessary configuration and prepares the test data.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    protected void setUp() throws Exception {

        ConfigManager cm = ConfigManager.getInstance();

        // load the configurations for db connection factory
        cm.add("dbfactory.xml");

        // load the configurations for InformixProjectPersistence
        cm.add("informix_persistence.xml");

        persistence = new UnmanagedTransactionInformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <p>
     * Cleans all the registered namespaces.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>UnmanagedTransactionInformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * An instance of UnmanagedTransactionInformixProjectPersistence can be created. All properties are provided.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        ProjectPersistence myPersistence = new UnmanagedTransactionInformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace");

        assertNotNull("Unable to create UnmanagedTransactionInformixProjectPersistence.", myPersistence);
    }

    /**
     * <p>
     * Accuracy test for the method <code>openConnection()</code>.
     * </p>
     * <p>
     * Verifies that the connection for the given connection name could be created and the auto commit property is set
     * true.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testOpenConnectionAccuracyConnNameExists() throws Exception {
        Connection conn = null;
        try {
            conn = persistence.openConnection();
            assertTrue("Fails to initialize the connection.", conn.getAutoCommit());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>openConnection()</code>.
     * </p>
     * <p>
     * Verifies that the connection for the given connection name could be created and the auto commit property is set
     * true.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testOpenConnectionAccuracyConnNameDoesNotExists() throws Exception {
        persistence = new UnmanagedTransactionInformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NoConnectionName");
        Connection conn = null;
        try {
            conn = persistence.openConnection();
            assertTrue("Fails to initialize the connection.", conn.getAutoCommit());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Failure test for the method <code>openConnection</code>.
     * </p>
     * <p>
     * Verifies that the <code>PersistenceException</code> will be thrown if fails to create the connection.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testOpenConnectionFailure() throws Exception {

        persistence = new UnmanagedTransactionInformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NonExistConnectionName");
        try {
            persistence.openConnection();
            fail("PersistenceException is expected.");
        } catch (PersistenceException pe) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>closeConnection(Connection)</code>.
     * </p>
     * <p>
     * Verifies that the connection could be closed properly without any change committed.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testCloseConnectionAccuracy() throws Exception {
        Connection conn = null;
        try {

            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            conn.setAutoCommit(false);
            // insert one record into project_info_type_lu table.
            Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu " + "(project_info_type_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (1, 'property 1', 'project property 1', " + "'topcoder', CURRENT, 'topcoder', CURRENT)",
                new Object[]{});
            // commit and close the connection.
            persistence.closeConnection(conn);
            assertTrue("Fails to close the connection.", conn.isClosed());

            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            conn.setAutoCommit(true);
            // query the project_info_type_lu table
            Object[][] rows = Helper.doQuery(conn,
                "SELECT name FROM project_info_type_lu where project_info_type_id = 1", new Object[]{},
                new DataType[]{Helper.STRING_TYPE});
            assertEquals("The close method should not commit the change(s).", 0, rows.length);
            conn.close();
        } finally {
            // try to close the connection if anything unexpected occurs.
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Failure test for method <code>closeConnection(Connection)</code>.
     * </p>
     * <p>
     * Verifies that <code>IllegalArgumentException</code> will be thrown if the argument is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testCloseConnectionFailure() throws Exception {
        try {
            persistence.closeConnection(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException iae) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>closeConnectionOnError(Connection)</code>.
     * </p>
     * <p>
     * Verifies that the changes in database will be rollback and the connection is closed if there is any problem when
     * accessing the database.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testCloseConnectionOnErrorAccuracy() throws Exception {
        Connection conn = null;
        try {

            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            conn.setAutoCommit(false);
            // insert one record into project_info_type_lu table.
            Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu " + "(project_info_type_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (1, 'property 1', 'project property 1', " + "'topcoder', CURRENT, 'topcoder', CURRENT)",
                new Object[]{});
            // commit and close the connection.
            persistence.closeConnectionOnError(conn);
            assertTrue("Fails to close the connection.", conn.isClosed());

            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            conn.setAutoCommit(true);
            // query the project_info_type_lu table
            Object[][] rows = Helper.doQuery(conn,
                "SELECT name FROM project_info_type_lu where project_info_type_id = 1", new Object[]{},
                new DataType[]{Helper.STRING_TYPE});
            assertEquals("The close method should not rollback the change(s).", 0, rows.length);
            conn.close();
        } finally {
            // try to close the connection if anything unexpected occurs.
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Failure test for method <code>closeConnectionOnError(Connection)</code>.
     * </p>
     * <p>
     * Verifies that <code>IllegalArgumentException</code> will be thrown if the argument is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testCloseConnectionOnErrorFailure() throws Exception {
        try {
            persistence.closeConnectionOnError(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException iae) {
            // success
        }
    }
}
