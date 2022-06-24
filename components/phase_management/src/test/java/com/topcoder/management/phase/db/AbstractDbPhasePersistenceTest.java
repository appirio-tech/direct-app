/*
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved.
 */

package com.topcoder.management.phase.db;

import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.management.phase.ConfigurationException;
import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>
 * Unit test for abstract class <code>AbstractDbPhasePersistence</code>.
 * </p>
 * <p>
 * Note that the test for constructors are included in the
 * <code>InformixPhasePersistenceTest</code>.
 * </p>
 * <p>
 * The inner class <code>MyPersistence</code> is used to test the
 * <code>AbstractDbPhasePersistence</code> because it is abstract. The
 * <code>getConnection()</code> and <code>getIDGenerator()</code> method in
 * <code>MyPersistence</code> is declared as public and just call the
 * corresponding super methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class AbstractDbPhasePersistenceTest extends TestCase {

    /**
     * The namespace used to create the persistence instance.
     */
    private static final String NAMESPACE = "com.topcoder.management.phase.db";

    /**
     * The persistence instance used for test.
     */
    private MyPersistence persistence = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * <p>
     * The namespaces are added and the <code>MyPersistence</code> instance is
     * created.
     * </p>
     * @throws Exception if there is any problem.
     */
    protected void setUp() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        manager.add("configV11.xml");
        persistence = new MyPersistence(NAMESPACE);
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <p>
     * The namespaces are removed.
     * </p>
     * @throws Exception if there is any problem.
     */
    protected void tearDown() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        Iterator it = manager.getAllNamespaces();
        while (it.hasNext()) {
            manager.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>getConnection()</code>.
     * </p>
     * <p>
     * Verifies that the connection could be retrieved if the connection name is
     * configured.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testGetConnectionAccuracyWithConnectionName() throws Exception {
        Connection conn = null;
        try {
            conn = persistence.getConnection();
            assertNotNull("Fails to get the connection.", conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>getConnection()</code>.
     * </p>
     * <p>
     * Verifies that the connection could be retrieved if the connection name is
     * not configured.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testGetConnectionAccuracyWithoutConnectionName()
        throws Exception {
        persistence = new MyPersistence(
                "com.topcoder.management.phase.db.noconnname");
        Connection conn = null;
        try {
            conn = persistence.getConnection();
            assertNotNull("Fails to get the connection.", conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Failure test for method <code>getConnection()</code>.
     * </p>
     * <p>
     * Verifies that <code>PhasePersistenceException</code> will be thrown if
     * fails to get the connection.
     * </p>
     * @throws Exception if there is any problem.
     */
    public void testGetConnectionFailure() throws Exception {
        persistence = new MyPersistence(
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
     * Accuracy test for method <code>getIDGenerator()</code>.
     * </p>
     * <p>
     * Verifies that the <code>IDGenerator</code> instance could be retrieved
     * successfully.
     * </p>
     */
    public void testGetIDGenerator() {
        assertNotNull("Fails to get IDGenerator.", persistence.getIDGenerator());
    }

    /**
     * <p>
     * This inner class extends the
     * <code>AbstractInformixPhasePersistence</code> to test the functionality
     * of <code>AbstractInformixDbPersistence</code>.
     * </p>
     * <p>
     * It makes the <code>getConnection()</code> and
     * <code>getIDGenerator</code> public and they just call the corresponding
     * methods of the super class. Or other classes are left empty.
     * </p>
     * @author TCSDEVELOPER
     * @version 1.1
     */
    private class MyPersistence extends AbstractInformixPhasePersistence {

        /**
         * The constructor accepting the namespace as argument. Just call the
         * super constructor.
         * @param namespace the namespace used to load configuration.
         * @throws ConfigurationException if fails to load the configuration to
         *             create the instance.
         */
        protected MyPersistence(String namespace) throws ConfigurationException {
            super(namespace);
        }

        /**
         * Empty method.
         * @param context not used in this method.
         */
        protected void commitTransaction(Map context) {
        }

        /**
         * Empty method.
         * @param connection not used in this method.
         */
        protected void disposeConnection(Connection connection) {
        }

        /**
         * Empty method.
         * @param context not used in this method.
         */
        protected void rollbackTransaction(Map context) {
        }

        /**
         * Empty method.
         * @param context not used in this method.
         */
        protected void startTransaction(Map context) {
        }

        /**
         * <p>
         * Fetches a new connection from connection factory based on the
         * connectionName variable. If connectionName is not provided the
         * default connection from connection factory will be retrieved.
         * </p>
         * <p>
         * This method just calls the corresponding method of super class.
         * </p>
         * @return a new connection from the connection factory based on the
         *         given connection name.
         * @throws PhasePersistenceException if there is any issue when getting
         *             the connection.
         */
        public Connection getConnection() throws PhasePersistenceException {
            return super.getConnection();
        }

        /**
         * <p>
         * Gets the ID Generator instance.
         * </p>
         * <p>
         * This method just calls the corresponding method of super class.
         * </p>
         * @return the ID Generator instance.
         */
        public IDGenerator getIDGenerator() {
            return super.getIDGenerator();
        }

    }

}
