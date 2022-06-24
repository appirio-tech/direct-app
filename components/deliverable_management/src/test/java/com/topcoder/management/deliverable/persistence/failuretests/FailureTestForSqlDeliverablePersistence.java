/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.failuretests;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.persistence.sql.SqlDeliverablePersistence;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure test cases for class <code>SqlDeliverablePersistenceTest</code>.
 *
 *
 * @author Chenhong
 * @version 1.0
 */
public class FailureTestForSqlDeliverablePersistence extends TestCase {

    /**
     * Represents the SqlDeliverablePersistence instance for test.
     */
    private SqlDeliverablePersistence persistence = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

        if (cm.existsNamespace(namespace)) {
            cm.removeNamespace(namespace);
        }

        cm.add("failuretests/DBConnectionFactory.xml");

        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        persistence = new SqlDeliverablePersistence(factory);

    }

    /**
     * Tear down the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

        if (cm.existsNamespace(namespace)) {
            cm.removeNamespace(namespace);
        }
    }

    /**
     * Test SqlDeliverablePersistence(DBConnectionFactory). If the parameter connectionFactory is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testSqlDeliverablePersistenceDBConnectionFactory() {
        try {
            new SqlDeliverablePersistence(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test for void SqlDeliverablePersistence(DBConnectionFactory, String). If the parameter connectionFactory is
     * null, IllegalArgumentException should be thrown.
     *
     */
    public void testSqlDeliverablePersistenceDBConnectionFactoryString() {
        try {
            new SqlDeliverablePersistence(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test Deliverable[] loadDeliverables(long).
     * If parameter deliverableId <= 0, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverableslong_1() throws Exception {
        try {
            persistence.loadDeliverables(0, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test Deliverable[] loadDeliverables(long).
     * If parameter deliverableId <= 0, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverableslong_2() throws Exception {
        try {
            persistence.loadDeliverables(0, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Deliverable loadDeliverable(long deliverableId, long submissionId) </code>.
     * If deliverableId or submissionId is <= 0 , IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverable_1() throws Exception {
        try {
            persistence.loadDeliverable(0, 1, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Deliverable loadDeliverable(long deliverableId, long submissionId) </code>.
     * If deliverableId or submissionId is <= 0 , IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverable_2() throws Exception {
        try {
            persistence.loadDeliverable(-1, 1, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Deliverable loadDeliverable(long deliverableId, long submissionId) </code>.
     * If deliverableId or submissionId is <= 0 , IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverable_3() throws Exception {
        try {
            persistence.loadDeliverable(1, 0, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Deliverable loadDeliverable(long deliverableId, long submissionId) </code>.
     * If deliverableId or submissionId is <= 0 , IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverable_4() throws Exception {
        try {
            persistence.loadDeliverable(1, -1, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test for Deliverable[] loadDeliverables(long[]).
     * If deliverableIds is null or any id is <= 0, IllegalArgumentException should be thrown.
     *
     */
    public void testLoadDeliverableslongArray_1() throws Exception {
        try {
            persistence.loadDeliverables(null, null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test for Deliverable[] loadDeliverables(long[]).
     * If deliverableIds is null or any id is <= 0, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLoadDeliverableslongArray_2() throws Exception {
        try {
            persistence.loadDeliverables(new long[] { 1, 0, -1, 1 }, new long[] {1, 1, 1, 1}, new long[] {1, 1, 1, 1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test for Deliverable[] loadDeliverables(long[]).
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverableslongArray_4() throws Exception {
        persistence.loadDeliverables(new long[0], new long[0], new long[0]);
    }

    /**
     * Test for Deliverable[] loadDeliverables(long[], long[]).
     * If any array is null or any id (in either array) is <= 0, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverableslongArraylongArray_1() throws Exception {
        try {
            persistence.loadDeliverables(null, new long[] { 1 }, new long[] {1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test for Deliverable[] loadDeliverables(long[], long[]).
     * If any array is null or any id (in either array) is <= 0, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverableslongArraylongArray_2() throws Exception {
        try {
            persistence.loadDeliverables(new long[] { 1 }, null, new long[] {1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test for Deliverable[] loadDeliverables(long[], long[]).
     * If any array is null or any id (in either array) is <= 0, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverableslongArraylongArray_3() throws Exception {
        try {
            persistence.loadDeliverables(new long[] { 0, 1 }, new long[] { 1, 2 }, new long[] { 1, 2 });
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test for Deliverable[] loadDeliverables(long[], long[]).
     * If any array is null or any id (in either array) is <= 0, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverableslongArraylongArray_4() throws Exception {
        try {
            persistence.loadDeliverables(new long[] { 1, 2 }, new long[] { 1, 0 }, new long[] { 1, 2 });
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test for Deliverable[] loadDeliverables(long[], long[]).
     * If the two arguments do not have the same number of elements, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadDeliverableslongArraylongArray_5() throws Exception {
        try {
            persistence.loadDeliverables(new long[] { 1, 2 }, new long[] { 1, 2, 3 }, new long[] { 1, 2, 3, 4 });
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }
}
