/*
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved.
 */

package com.topcoder.management.phase.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Unit test for class <code>UnmanagedTransactionInformixPhasePersistence</code>.
 * </p>
 * <p>
 * Note: The constructors are fully tested in the super class, so they are not
 * tested again here.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class UnmanagedTransactionInformixPhasePersistenceTest extends TestCase {

    /**
     * The namespace used to create the persistence instance.
     */
    private static final String NAMESPACE = "com.topcoder.management.phase.db";

    /**
     * The <code>UnmanagedTransactionInformixPhasePersistence</code> instance
     * used for test.
     */
    private UnmanagedTransactionInformixPhasePersistence persistence = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * <p>
     * The namespaces are added and the <code>InformixPhasePersistence</code>
     * instance is created.
     * </p>
     * @throws Exception if there is any problem.
     */
    protected void setUp() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        manager.add("configV11.xml");
        TestHelper.initDatabase();
        persistence = new UnmanagedTransactionInformixPhasePersistence(
                NAMESPACE);
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <p>
     * The namespaces are removed and the project table is cleaned.
     * </p>
     * @throws Exception if there is any problem.
     */
    protected void tearDown() throws Exception {
        TestHelper.executeSQL("delete from project");
        TestHelper.cleanDatabase();
        ConfigManager manager = ConfigManager.getInstance();
        Iterator it = manager.getAllNamespaces();
        while (it.hasNext()) {
            manager.removeNamespace((String) it.next());
        }

    }

    /**
     * Accuracy test for constructor <code>UnmanagedTransactionInformixPhasePersistence(
     *       String)</code>.
     * @throws Exception if there is any problem.
     */
    public void testConstructor1() throws Exception {
        assertNotNull("Fails to create constructor.",
                new UnmanagedTransactionInformixPhasePersistence(NAMESPACE));
    }


    /**
     * Accuracy test for constructor <code>UnmanagedTransactionInformixPhasePersistence(
     *       DBConnectionFactory connectionFactory, String connectionName,
     *       IDGenerator idGen)</code>.
     * @throws Exception if there is any problem.
     */
    public void testConstructor2() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class
                .getName());
        IDGenerator idGenerator = IDGeneratorFactory.getIDGenerator("phase_id_seq");
        assertNotNull("Fails to create constructor.",
                new UnmanagedTransactionInformixPhasePersistence(factory, null, idGenerator));
    }

    /**
     * <p>
     * Accuracy test for method <code>disposeConnection(Connection</code>.
     * </p>
     * <p>
     * Verifies that the connection is closed properly.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testDisposeConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            conn = TestHelper.getConnection();
            persistence.disposeConnection(conn);
            assertTrue("Fails to dispose the connection.", conn.isClosed());
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Failure test for method <code>disposeConnection(Connection</code>.
     * </p>
     * <p>
     * Verifies that the <code>IllegalArgumentException</code> will be thrown
     * if the argument is <code>null</code>.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testDisposeConnectionFailure() throws Exception {

        try {
            persistence.disposeConnection(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException iae) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>commitTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the changes are not committed after this method is invoked.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testCommitTransaction() throws Exception {
        Connection conn = null;

        try {
            conn = TestHelper.getConnection();
            conn.setAutoCommit(false);
            TestHelper.executeSQL(conn,
                    "insert into project (project_id) values (1000)");
            persistence.commitTransaction(TestHelper.createContextMap(conn));

            // close the connection first to avoid dead lock.
            conn.close();
            // check that the change was not committed.
            assertFalse(
                    "The record should not be in the database before commit.",
                    TestHelper
                            .assertRecordExists("select * from project where project_id = 1000"));

        } finally {
            TestHelper.closeResource(conn, null, null);
        }

    }

    /**
     * <p>
     * Failure test for method <code>commitTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the <code>IllegalArgumentException</code> will be thrown
     * if the map contains null entry key.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testCommitTransactionFailureNullEntryKeyMap() throws Exception {

        try {
            Map context = new HashMap();
            context.put(null, "value");

            persistence.commitTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>commitTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the <code>IllegalArgumentException</code> will be thrown
     * if the map contains null entry value.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testCommitTransactionFailureNullEntryValueMap()
        throws Exception {
        try {
            Map context = new HashMap();
            context.put("key", null);

            persistence.commitTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>rollbackTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the <code>PhasePersistenceException</code> will be
     * thrown.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testRollbackTransactionAccuracy() throws Exception {
        Connection conn = null;

        try {
            conn = TestHelper.getConnection();

            persistence.rollbackTransaction(TestHelper.createContextMap(conn));

            fail("PhasePersistenceException is expected.");

        } catch (PhasePersistenceException ppe) {
            // success
        } finally {
            TestHelper.closeResource(conn, null, null);
        }
    }

    /**
     * <p>
     * Failure test for method <code>rollbackTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the <code>IllegalArgumentException</code> will be thrown
     * if the map contains null entry key.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testRollbackTransactionFailureNullEntryKeyMap()
        throws Exception {

        try {
            Map context = new HashMap();
            context.put(null, "value");

            persistence.rollbackTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>rollbackTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the <code>IllegalArgumentException</code> will be thrown
     * if the map contains null entry value.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testRollbackTransactionFailureNullEntryValueMap()
        throws Exception {
        try {
            Map context = new HashMap();
            context.put("key", null);

            persistence.rollbackTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>startTransaction(Map)</code>.
     * </p>
     * <p>
     * Nothing is checked for accuracy because it is no-op method.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testStartTransactionAccuracy() throws Exception {
        Connection conn = null;

        try {
            conn = TestHelper.getConnection();

            persistence.startTransaction(TestHelper.createContextMap(conn));

        } finally {
            TestHelper.closeResource(conn, null, null);
        }
    }

    /**
     * <p>
     * Failure test for method <code>startTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the <code>IllegalArgumentException</code> will be thrown
     * if the map contains null entry key.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testStartTransactionFailureNullEntryKeyMap() throws Exception {

        try {
            Map context = new HashMap();
            context.put(null, "value");

            persistence.startTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>startTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the <code>IllegalArgumentException</code> will be thrown
     * if the map contains null entry value.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testStartTransactionFailureNullEntryValueMap() throws Exception {
        try {
            Map context = new HashMap();
            context.put("key", null);

            persistence.startTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
        }
    }

}
