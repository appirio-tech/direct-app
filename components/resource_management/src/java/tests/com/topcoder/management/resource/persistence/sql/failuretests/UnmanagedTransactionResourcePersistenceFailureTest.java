/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql.failuretests;

import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.persistence.sql.UnmanagedTransactionResourcePersistence;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for UnmanagedTransactionResourcePersistence class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class UnmanagedTransactionResourcePersistenceFailureTest extends TestCase {
    /**
     * This instance is used in the test.
     */
    private MockUnmanagedTransactionResourcePersistence persistence;

    /**
     * This instance is used in the test.
     */
    private DBConnectionFactory factory;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UnmanagedTransactionResourcePersistenceFailureTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        ConfigManager.getInstance().add("failure/config.xml");
        factory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        persistence = new MockUnmanagedTransactionResourcePersistence(factory);
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Failure test of
     * <code>UnmanagedTransactionResourcePersistence(DBConnectionFactory connectionFactory)</code>
     * constructor.
     * <p>
     * connectionFactory is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUnmanagedTransactionResourcePersistence1_failure_null_connectionFactory() throws Exception {
        try {
            new UnmanagedTransactionResourcePersistence(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>UnmanagedTransactionResourcePersistence(DBConnectionFactory connectionFactory,
     * String connectionName)</code>
     * constructor.
     * <p>
     * connectionFactory is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUnmanagedTransactionResourcePersistence2_failure_null_connectionFactory() throws Exception {
        try {
            new UnmanagedTransactionResourcePersistence(null, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>openConnection()</code> method.
     * <p>
     * bad connection name.
     * </p>
     * <p>
     * Expect ResourcePersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testOpenConnection_failure_bad_connection_name() throws Exception {
        persistence = new MockUnmanagedTransactionResourcePersistence(factory, "bad");
        try {
            persistence.openConnection();
            fail("Expect ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>closeConnection(Connection connection)</code>
     * method.
     * <p>
     * connection is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCloseConnection_failure_null_connection() throws Exception {
        try {
            persistence.closeConnection(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>closeConnectionOnError(Connection connection)</code> method.
     * <p>
     * connection is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCloseConnectionOnError_failure_null_connection() throws Exception {
        try {
            persistence.closeConnectionOnError(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

}
