/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project.persistence;

import java.sql.Connection;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit test for the <code>Helper</code>.
 *
 * @author fuyun
 * @version 1.1
 */
public class HelperTest extends TestCase {

    /**
     * Represents the <code>DBConnectionFactory</code> instance used for test.
     */
    private DBConnectionFactory factory = null;

    /**
     * Sets up the test environment.
     *
     * @throws Exception
     *             if there is any problem.
     */
    protected void setUp() throws Exception {
        ConfigManager.getInstance().add("dbfactory.xml");
        factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
    }

    /**
     * Cleans up the test environment.
     *
     * @throws Exception
     *             if there is any problem.
     */
    protected void tearDown() throws Exception {
        ConfigManager.getInstance().removeNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
    }

    /**
     * <p>
     * Accuracy test for method <code>createConnection</code>.
     * </p>
     * <p>
     * Verifies that the <code>Connection</code> could be returned if the connection name is provided.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testCreateConnectionWithConnectionName() throws Exception {
        Connection conn = null;
        try {
            conn = Helper.createConnection(factory, "informix_connection");
            assertNotNull("Fails to create connection.", conn);
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createConnection</code>.
     * </p>
     * <p>
     * Verifies that the <code>Connection</code> could be returned if the connection name is not provided.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    public void testCreateConnectionWithNullConnectionName() throws Exception {
        Connection conn = null;
        try {
            conn = Helper.createConnection(factory, null);
            assertNotNull("Fails to create connection.", conn);
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Failure test for method <code>createConnection</code>.
     * </p>
     * <p>
     * Verifies taht the <code>PersistenceException</code> will be thrown if fails to create the connection.
     * </p>
     */
    public void testCreateConnectionFailure() {
        try {
            Helper.createConnection(factory, "nosuchname");
            fail("PersistenceException is expected.");
        } catch (PersistenceException pe) {
            // success
        }
    }
}
