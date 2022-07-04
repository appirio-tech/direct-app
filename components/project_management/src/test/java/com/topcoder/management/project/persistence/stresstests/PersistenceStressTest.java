/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.stresstests;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.persistence.stresstests.StressHelper.DataType;
import com.topcoder.management.project.persistence.stresstests.StressHelper;

/**
 * <p>
 * This class is the stress test for Project Management Persistence 1.0.
 * </p>
 * 
 * @author Hacker_QC
 * @version 1.1
 */
public class PersistenceStressTest extends TestCase {

    /**
     * The loop count for stress test.
     */
    private static final int COUNT = 20;

    /**
     * Represents the config file for stress test.
     */
    private static final String CONFIG_FILE1 = "stresstests/dbfactory.xml";

    /**
     * Represents the config file for stress test.
     */
    private static final String CONFIG_FILE2 = "stresstests/informix_persistence.xml";

    /**
     * Represents the query statement for stress test.
     */
    private static final String QUERY1 = "INSERT INTO project_info_type_lu "
            + "(project_info_type_id, name, description, " + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'property 1', 'project property 1', " + "'topcoder', CURRENT, 'topcoder', CURRENT)";

    /**
     * Represents the query statement for stress test.
     */
    private static final String QUERY2 = "SELECT name FROM project_info_type_lu where project_info_type_id = 1";

    /**
     * Represents the query statement for stress test.
     */
    private static final String QUERY3 = "DELETE FROM project_info_type_lu WHERE project_info_type_id = 1";

    /**
     * Represents the query statement for stress test.
     */
    private static final String QUERY4 = "INSERT INTO project_info_type_lu "
            + "(project_info_type_id, name, description, " + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'property 1', 'project property 1', " + "'topcoder', CURRENT, 'topcoder', CURRENT)";
    

    /**
     * Represents the query statement for stress test.
     */
    private static final String QUERY5 = "SELECT name FROM project_info_type_lu where project_info_type_id = 1";
    
    /**
     * Represents the query statement for stress test.
     */
    private static final String QUERY6 = "DELETE FROM project_info_type_lu WHERE project_info_type_id = 1";

    /**
     * Represents the <code>MyPersistence</code> instance for stress test.
     */
    private MockPersistence persistence = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * 
     * @throws Exception
     *             if there is any problem.
     */
    protected void setUp() throws Exception {
        StressHelper.unloadConfig();
        StressHelper.loadConfig(CONFIG_FILE1);
        StressHelper.loadConfig(CONFIG_FILE2);
        persistence = new MockPersistence("InformixProjectPersistence.CustomNamespace");
    }

    /**
     * <p>
     * Clears the test environment.
     * </p>
     * 
     * @throws Exception
     *             if there is any problem.
     */
    protected void tearDown() throws Exception {
        StressHelper.unloadConfig();
    }

    /**
     * <p>
     * This method tests the functionality of connection with non-exist name in high stress.
     * </p>
     * 
     * @throws Exception
     *             if any error occurs.
     */
    public void testStressForConnectionWithNonExistName() throws Exception {

        long startTime = System.currentTimeMillis();

        persistence = new MockPersistence("InformixProjectPersistence.CustomNamespace.NoConnectionName");
        for (int i = 0; i < COUNT; ++i) {
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

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test for connection with non-exist name costs: " + (endTime - startTime)
                + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of connection with failure in high stress.
     * </p>
     * 
     * @throws Exception
     *             if any error occurs.
     */
    public void testStressForConnectionWithFailure() throws Exception {

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            Connection conn = null;
            try {

                conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
                conn.setAutoCommit(true);
                persistence.closeConnection(conn);
                fail("PersistenceException is expected");

            } catch (PersistenceException pe) {
                // expect

            } finally {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test for connection with failure costs: " + (endTime - startTime)
                + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of connection with error in high stress.
     * </p>
     * 
     * @throws Exception
     *             if any error occurs.
     */
    public void testStressForConnectionWithError() throws Exception {

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            Connection conn = null;
            try {
                
                conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
                conn.setAutoCommit(true);
                StressHelper.doDMLQuery(conn, QUERY6, new Object[] {});
                conn.close();

                conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
                conn.setAutoCommit(false);
                StressHelper.doDMLQuery(conn, QUERY4, new Object[] {});
                persistence.closeConnectionOnError(conn);
                assertTrue("Fails to close the connection.", conn.isClosed());

                conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
                conn.setAutoCommit(true);
                Object[][] rows = StressHelper.doQuery(conn, QUERY5, new Object[] {},
                        new DataType[] { StressHelper.STRING_TYPE });
                assertEquals("Fails to commit the changes in database.", 0, rows.length);
                conn.close();
                
                conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
                conn.setAutoCommit(true);
                StressHelper.doDMLQuery(conn, QUERY6, new Object[] {});
                conn.close();

            } finally {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test for connection with error costs: " + (endTime - startTime)
                + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of connection in high stress.
     * </p>
     * 
     * @throws Exception
     *             if any error occurs.
     */
    public void testStressForConnection() throws Exception {

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            Connection conn = null;
            try {
                conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
                conn.setAutoCommit(false);
                StressHelper.doDMLQuery(conn, QUERY1, new Object[] {});
                persistence.closeConnection(conn);
                assertTrue("Fails to close the connection.", conn.isClosed());
                conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
                conn.setAutoCommit(true);
                Object[][] rows = StressHelper.doQuery(conn, QUERY2, new Object[] {},
                        new DataType[] { StressHelper.STRING_TYPE });
                assertEquals("Fails to commit the changes in database.", "property 1", rows[0][0]);
                StressHelper.doDMLQuery(conn, QUERY3, new Object[] {});
                conn.close();
            } finally {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test for connection costs: " + (endTime - startTime) + " milliseconds.");
    }
}
