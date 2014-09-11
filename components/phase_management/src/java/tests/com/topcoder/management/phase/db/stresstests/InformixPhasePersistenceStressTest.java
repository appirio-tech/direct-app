/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.stresstests;

import java.sql.Connection;
import java.util.Date;
import java.util.Map;

import junit.framework.TestCase;


/**
 * <p>
 * Stress tests for the InformixPhasePersistence class. In order to test
 * the protected method, we mock an class and call the test method in public
 * way, so we can test them in different package.
 * </p>
 *
 * @author KLW
 * @version 1.1
 */
public class InformixPhasePersistenceStressTest extends TestCase {
    /**
     * <p>The int number represents the loop times for stress test.</p>
     */
    private static final int LOOPTIMES = 20;
    
    /**
     * <p>
     * Key of context.
     * </p>
     */
    public static final String CONNECTION_CONTEXT_KEY = "connection";

    /**
     * <p>
     * Represents the InformixPhasePersistence instance for test.
     * </p>
     */
    private MockInformixPhasePersistence instance = null;

    /**
     * Sets up test test environment. Most of the work is made in base class.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        StressTestHelper.clearConfigManager();
        StressTestHelper.LoadConfigFile();
        instance = new MockInformixPhasePersistence(StressTestHelper.getConnectionFactory(),
                StressTestHelper.CONNECTION_NAME, StressTestHelper.getIDGenerator());
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <p>
     * The name spaces are removed and the project table is cleaned.
     * </p>
     * @throws Exception if there is any problem.
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearTables();
    }
    
    /**
    * <p>
    * test method GetConnection().
    * </p>
    *
    * @throws Exception throw to JUnit.
    */
    public void testDisposeConnection() throws Exception {
        Date start = new Date();
        Connection conn;
        for (int i = 0; i < LOOPTIMES; i++) {
            conn = StressTestHelper.getConnection();
            instance.disposeConnection(conn);
        }

        double cost = ((new Date()).getTime() - start.getTime()) / 1000;
        System.out.println(
            "Calling DisposeConnection() method 100 times,cost time " + cost + "s.");
    }

    /**
     * <p>
     * test method rollbackTransaction().
     * </p>
     *
     * @throws Exception throw to JUnit.
     */
    public void testRollbackTransaction() throws Exception {
        Date start = new Date();
        Connection conn = StressTestHelper.getConnection();
        conn.setAutoCommit(false);
        Map context = StressTestHelper.createContextMap(conn);
        for (int i = 0; i < LOOPTIMES; i++) {
            StressTestHelper.executeSQL(conn,"insert into project (project_id) values (" + (i+101) +")");
            instance.rollbackTransaction(context);
        }
        StressTestHelper.closeResource(conn, null, null);
        double cost = ((new Date()).getTime() - start.getTime()) / 1000;
        System.out.println(
            "Calling rollbackTransaction() method 100 times,cost time " + cost + "s.");
    }

    /**
     * <p>
     * test method startTransaction().
     * </p>
     *
     * @throws Exception throw to JUnit.
     */
    public void testStartTransaction() throws Exception {
        Date start = new Date();
        Connection conn = StressTestHelper.getConnection();
        for (int i = 0; i < LOOPTIMES; i++) {
            instance.startTransaction(StressTestHelper.createContextMap(conn));
        }

        double cost = ((new Date()).getTime() - start.getTime()) / 1000;
        System.out.println(
            "Calling startTransaction() method 100 times,cost time " + cost + "s.");
    }
    
    /**
     * <p>
     * test method startTransaction().
     * </p>
     *
     * @throws Exception throw to JUnit.
     */
    public void testCommitTransaction() throws Exception {
        Date start = new Date();

        Connection conn = StressTestHelper.getConnection();
        conn.setAutoCommit(false);
        Map context = StressTestHelper.createContextMap(conn);
        for (int i = 0; i < LOOPTIMES; i++) {
            StressTestHelper.executeSQL(conn,"insert into project (project_id) values (" + i+101 +")");
            instance.rollbackTransaction(context);
        }
        StressTestHelper.closeResource(conn, null, null);

        double cost = ((new Date()).getTime() - start.getTime()) / 1000;
        System.out.println(
            "Calling commitTransaction() method 100 times,cost time " + cost + "s.");
    }
}
