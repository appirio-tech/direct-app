/*
 * Copyright (C) 2007 - 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project.persistence;

import java.sql.Connection;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.persistence.Helper.DataType;

/**
 * <p>
 * Unit test for the new APIs in <code>InformixProjectPersistence</code> version 1.1 and some methods in
 * <code>AbstractInformixProjectPersistence</code>.
 * </p>
 * <p>
 * Because the added methods in the class are protected, so the subclass of <code>InformixProjectPersistence</code> is
 * used for testing which make the protected methods public.
 * </p>
 * <p>
 * The <code>getConnectionName()</code> and <code>getConnectionFactory()</code> of
 * <code>AbstractInformixProjectPersistence</code> are also tested in this class.
 * </p>
 *
 * @author TCSDEVELOPER, TCSDEVELOPER
 * @version 1.2
 * @since 1.1
 */
public class InformixProjectPersistenceV11Test extends TestCase {

    /**
     * Represents the <code>InformixProjectPersistence</code> instance which is used to test the functionality of
     * <code>InformixProjectPersistence</code>.
     */
    private InformixProjectPersistence persistence = null;

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
     * @since 1.1
     */
    protected void setUp() throws Exception {
        TestHelper.prepareConfig();

        persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

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
     * @since 1.1
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Accuracy test for the method <code>openConnection()</code>.
     * </p>
     * <p>
     * Verifies that the connection for the given connection name could be created and the auto commit property is set
     * false.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testOpenConnectionAccuracyConnNameExists() throws Exception {
        Connection conn = null;
        try {
            conn = persistence.openConnection();
            assertFalse("Fails to initialize the connection.", conn.getAutoCommit());
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
     * false.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testOpenConnectionAccuracyConnNameDoesNotExists() throws Exception {
        persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.NoConnectionName");
        Connection conn = null;
        try {
            conn = persistence.openConnection();
            assertFalse("Fails to initialize the connection.", conn.getAutoCommit());
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

        persistence = new InformixProjectPersistence(
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
     * Verifies that the connection could be committed and closed properly.
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
            assertEquals("Fails to commit the changes in database.", "property 1", rows[0][0]);
            Helper.doDMLQuery(conn, "DELETE FROM project_info_type_lu WHERE project_info_type_id = 1", new Object[]{});
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
     * Verifies that the <code>PersistenceException</code> will be thrown if fails to commit the connection.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testCloseConnectionFailure() throws Exception {
        Connection conn = null;
        try {

            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();

            // set the auto commit true to make the commit call fail.
            conn.setAutoCommit(true);

            // commit and close the connection.
            persistence.closeConnection(conn);
            fail("PersistenceException is expected");
        } catch (PersistenceException pe) {
            // success
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
    public void testCloseConnectionFailureNullArg() throws Exception {
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
            assertEquals("Fails to commit the changes in database.", 0, rows.length);
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
     * Verifies that the <code>PersistenceException</code> will be thrown if fails to rollback the connection.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testCloseConnectionOnErrorFailure() throws Exception {
        Connection conn = null;
        try {

            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();

            // set the auto commit true to make the rollback call fail.
            conn.setAutoCommit(true);

            // commit and close the connection.
            persistence.closeConnectionOnError(conn);
            fail("PersistenceException is expected");
        } catch (PersistenceException pe) {
            // success
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
    public void testCloseConnectionOnErrorFailureNullArg() throws Exception {
        try {
            persistence.closeConnectionOnError(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException iae) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>getConnectionName()</code> in <code>AbstractInformixProjectPersistence</code>.
     * </p>
     * <p>
     * Verifies that the connection name could be retrieved if it is configured.
     * </p>
     */
    public void testGetConnectionNameConnNameExist() {
        assertEquals("Fails to get the connection name.", "informix_connection", persistence.getConnectionName());
    }

    /**
     * <p>
     * Accuracy test for method <code>getConnectionName()</code> in <code>AbstractInformixProjectPersistence</code>.
     * </p>
     * <p>
     * Verifies that <code>null</code> will be retrieved if it is not configured.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testGetConnectionNameConnNameDoesNotExist() throws Exception {
        persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.NoConnectionName");
        assertNull("Fails to get the connection name.", persistence.getConnectionName());
    }

    /**
     * <p>
     * Accuracy test for method <code>getConnectionFactory()</code> in <code>AbstractInformixProjectPersistence</code>.
     * </p>
     * <p>
     * Verifies that the connection factory could be retrieved properly.
     * </p>
     */
    public void testGetConnectionFactory() {
        assertNotNull("Fails to get the connection factory.", persistence.getConnectionFactory());
    }
}
