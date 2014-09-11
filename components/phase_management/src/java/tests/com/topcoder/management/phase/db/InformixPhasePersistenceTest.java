/*
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved.
 */

package com.topcoder.management.phase.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Unit test for class <code>InformixPhasePersistence</code>.
 * </p>
 * <p>
 * Note: The constructors are fully tested in the super class, so they are not
 * tested again here.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class InformixPhasePersistenceTest extends TestCase {

    /**
     * The namespace used to create the persistence instance.
     */
    private static final String NAMESPACE = "com.topcoder.management.phase.db";

    /**
     * The <code>InformixPhasePersistence</code> instance used for test.
     */
    private InformixPhasePersistence persistence = null;

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
        persistence = new InformixPhasePersistence(NAMESPACE);
        persistence.setUserManualCommit(true);
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
     * <p>
     * Accuracy test for method <code>getConnection()</code>
     * </p>.
     * <p>
     * Verifies that the connection could be retrieved and the auto commit is
     * set false.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testGetConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();
            assertFalse("Fails to get the proper connection.", conn
                    .getAutoCommit());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Failure test for method <code>getConnection()</code>
     * </p>.
     * <p>
     * Verifies that the <code>PhasePersistenceException</code> will be thrown
     * if fails to get the connection.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testGetConnectionFailure() throws Exception {
        persistence = new InformixPhasePersistence(
                "com.topcoder.management.phase.db.invalid");
        try {
            persistence.getConnection();
            fail("PhasePersistenceException is expected.");
        } catch (PhasePersistenceException pe) {
            // success
        }
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
            conn = persistence.getConnection();
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
     * Verifies that the changes are committed after this method is invoked.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testCommitTransactionAccuracy() throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();
            TestHelper.executeSQL(conn,
                    "insert into project (project_id) values (1000)");
            persistence.commitTransaction(TestHelper.createContextMap(conn));

            // check that the change was committed.
            assertTrue(
                    "The record should be in the database before commit.",
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
     * Verifies that the <code>PhasePersistenceException</code> will be thrown
     * if fails to commit the changes.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testCommitTransactionFailure() throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();
            conn.setAutoCommit(true);
            TestHelper.executeSQL(conn,
                    "insert into project (project_id) values (1000)");

            // if the auto commit is true, exception will be thrown if we commit
            // it.
            persistence.commitTransaction(TestHelper.createContextMap(conn));

            fail("PhasePersistenceException is expected.");
        } catch (PhasePersistenceException ppe) {
            // success
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
     * if the argument is null.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testCommitTransactionFailureNullArg() throws Exception {
        try {
            persistence.commitTransaction(null);
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
     * if the map contains null entry key.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testCommitTransactionFailureNullEntryKeyMap() throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();

            Map context = TestHelper.createContextMap(conn);
            context.put(null, "value");

            persistence.commitTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
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
     * if the map contains null entry value.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testCommitTransactionFailureNullEntryValueMap()
        throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();

            Map context = TestHelper.createContextMap(conn);
            context.put("key", null);

            persistence.commitTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
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
     * if the map context does not contain connection.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testCommitTransactionFailureNoConnection() throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();

            persistence.commitTransaction(new HashMap());
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
        } finally {
            TestHelper.closeResource(conn, null, null);
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>rollbackTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the changes could be roll back successfully.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testRollbackTransactionAccuracy() throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();
            TestHelper.executeSQL(conn,
                    "insert into project (project_id) values (1000)");
            persistence.rollbackTransaction(TestHelper.createContextMap(conn));

            // check that the change was committed.
            assertFalse(
                    "The record should be in the database before commit.",
                    TestHelper
                            .assertRecordExists("select * from project where project_id = 1000"));

        } finally {
            TestHelper.closeResource(conn, null, null);
        }
    }

    /**
     * <p>
     * Failure test for method <code>rollbackTransaction(Map)</code>.
     * </p>
     * <p>
     * Verifies that the <code>PhasePersistenceException</code> will be thrown
     * if fails to roll back the changes.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testRollbackTransactionFailure() throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();
            conn.setAutoCommit(true);
            TestHelper.executeSQL(conn,
                    "insert into project (project_id) values (1000)");

            // if the auto commit is true, exception will be thrown if we roll
            // back
            // it.
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
     * if the argument is null.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testRollbackTransactionFailureNullArg() throws Exception {
        try {
            persistence.rollbackTransaction(null);
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
     * if the map contains null entry key.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testRollbackTransactionFailureNullEntryKeyMap()
        throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();

            Map context = TestHelper.createContextMap(conn);
            context.put(null, "value");

            persistence.rollbackTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
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
     * if the map contains null entry value.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testRollbackTransactionFailureNullEntryValueMap()
        throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();

            Map context = TestHelper.createContextMap(conn);
            context.put("key", null);

            persistence.rollbackTransaction(context);
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
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
     * if the map context does not contain connection.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testRollbackTransactionFailureNoConnection() throws Exception {
        Connection conn = null;

        try {
            conn = persistence.getConnection();

            persistence.rollbackTransaction(new HashMap());
            fail("IllegalArgumentException is expected.");

        } catch (IllegalArgumentException iae) {
            // success
        } finally {
            TestHelper.closeResource(conn, null, null);
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
            conn = persistence.getConnection();

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
