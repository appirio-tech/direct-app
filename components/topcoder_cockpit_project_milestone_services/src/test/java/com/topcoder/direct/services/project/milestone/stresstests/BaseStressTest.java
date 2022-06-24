/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.stresstests;

/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * <p>
 * BaseStressTest class for the stress tests.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@ContextConfiguration(locations = {"classpath:stresstests/applicationContext.xml" })
public class BaseStressTest {
    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    static final long NUMBER = 1;

    /**
     * <p>
     * Represents the delete sql.
     * </p>
     */
    private static final String[] DELETE_SQL = {"DELETE FROM project_milestone_owner",
        "DELETE FROM project_milestone" };

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private static long current = -1;

    /**
     * <p>
     * Represents the connection for testing.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the dataSource for testing.
     * </p>
     */
    @Autowired
    private DataSource dataSource;

    /**
     * <p>
     * Setup the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        connection = dataSource.getConnection();
        clearDB(connection);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        clearDB(connection);
        connection.close();
    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    static void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name
     *            the test name
     * @param count
     *            the run count
     */
    void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: "
                + (System.currentTimeMillis() - current) + " ms");
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @param connection
     *            the connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void clearDB(Connection connection) throws Exception {
        executeSQL(connection, DELETE_SQL);
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Empty lines will be ignored.
     * </p>
     *
     * @param connection
     *            the connection.
     * @param sqls
     *            the sqls.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void executeSQL(Connection connection, String[] sqls) throws Exception {
        Statement statement = connection.createStatement();
        try {
            for (int i = 0; i < sqls.length; i++) {
                statement.executeUpdate(sqls[i]);
            }
        } finally {
            statement.close();
        }
    }
}
