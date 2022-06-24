/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.stresstests;

import java.sql.Connection;

import java.util.Date;
import java.util.Map;

import com.topcoder.management.phase.PhasePersistenceException;

import junit.framework.TestCase;


/**
 * Stress tests for the UnmanagedTransactionInformixPhasePersistence class.
 *
 * @author KLW
 * @version 1.1
 */
public class UnmanagedTransactionInformixPhasePersistenceStressTest
    extends TestCase {
    /**
     * <p>The int number represents the loop times for stress test.</p>
     */
    private static final int LOOPTIMES = 50;

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
    private MockUnmanagedTransactionInformixPhasePersistence instance = null;

    /**
     * Sets up test test environment. Most of the work is made in base class.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        StressTestHelper.clearConfigManager();
        StressTestHelper.LoadConfigFile();
        instance = new MockUnmanagedTransactionInformixPhasePersistence(StressTestHelper.getConnectionFactory(),
                StressTestHelper.CONNECTION_NAME, StressTestHelper.getIDGenerator());
    }
    /**
     * <p>
     * Tear down the test environment.
     * </p>
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
        Connection conn = null;
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
        Map context = StressTestHelper.createContextMap(conn);
        for (int i = 0; i < LOOPTIMES; i++) {
        	try {
                instance.rollbackTransaction(context);
        	} catch (PhasePersistenceException ex) {
        		//success
        	}
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
        Map context = StressTestHelper.createContextMap(conn);
        for (int i = 0; i < LOOPTIMES; i++) {
            instance.startTransaction(context);
        }
        StressTestHelper.closeResource(conn, null, null);
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
        Map context = StressTestHelper.createContextMap(conn);
        for (int i = 0; i < LOOPTIMES; i++) {
            instance.commitTransaction(context);
        }
        StressTestHelper.closeResource(conn, null, null);

        double cost = ((new Date()).getTime() - start.getTime()) / 1000;
        System.out.println(
            "Calling commitTransaction() method 100 times,cost time " + cost + "s.");
    }
}
