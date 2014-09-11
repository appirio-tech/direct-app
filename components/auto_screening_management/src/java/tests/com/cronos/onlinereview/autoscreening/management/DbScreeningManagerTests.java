/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.sql.Connection;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of DbScreeningManager. Tests the class for proper behavior.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class DbScreeningManagerTests extends TestCase {
    /**
     * <p>
     * The connectionName used for testing.
     * </p>
     */
    private static final String NAME = "Informix_Connection_Producer";

    /**
     * <p>
     * An instance of <code>MockDbScreeningManager</code> which is tested.
     * </p>
     */
    private MockDbScreeningManager target = null;

    /**
     * <p>
     * The DBConnectionFactory used for testing.
     * </p>
     */
    private DBConnectionFactoryImpl dBConnectionFactory = null;

    /**
     * <p>
     * setUp() routine. Creates DBConnectionFactoryImpl instance.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadNamespaces();
        dBConnectionFactory = new DBConnectionFactoryImpl(
                "com.cronos.onlinereview.autoscreening.management.db");
    }

    /**
     * tearDown() routine. clear tables and namespaces loaded.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.releaseNamespaces();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>DbScreeningManager(DBConnectionFactoryImpl)</code> Create for
     * proper behavior.
     * </p>
     */
    public void testCreateDBConnectionFactory_accuracy() {
        target = new MockDbScreeningManager(dBConnectionFactory);
        assertNotNull("Failed to initialize DbScreeningManager object.", target);
    }

    /**
     * <p>
     * Failure test. Tests the <code>DbScreeningManager(DBConnectionFactoryImpl)</code> Failed for
     * null DBConnectionFactory.
     * </p>
     */
    public void testCreateDBConnectionFactory_failure() {
        try {
            new MockDbScreeningManager(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>DbScreeningManager(DBConnectionFactoryImpl, String)</code>
     * Create for proper behavior.
     * </p>
     */
    public void testCreateDBConnectionFactoryString_accuracy() {
        target = new MockDbScreeningManager(dBConnectionFactory, NAME);
        assertNotNull("Failed to initialize DbScreeningManager object.", target);
    }

    /**
     * <p>
     * Failure test. Tests the <code>DbScreeningManager(DBConnectionFactoryImpl, String)</code> Failed
     * for invalid parameter.
     * </p>
     */
    public void testCreateDBConnectionFactoryString_1_failure() {
        try {
            new MockDbScreeningManager(null, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>DbScreeningManager(DBConnectionFactoryImpl, String)</code> Failed
     * for invalid parameter.
     * </p>
     */
    public void testCreateDBConnectionFactoryString_2_failure() {
        try {
            new MockDbScreeningManager(dBConnectionFactory, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>DbScreeningManager(DBConnectionFactory, String)</code> Failed
     * for invalid parameter.
     * </p>
     */
    public void testCreateDBConnectionFactoryString_3_failure() {
        try {
            new MockDbScreeningManager(dBConnectionFactory, "  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>createConnection()</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateConnection_accuracy() throws Exception {
        target = new MockDbScreeningManager(dBConnectionFactory);
        Connection conn = target.createConnection();
        assertNotNull("Failed to create connection.", conn);
    }
}
