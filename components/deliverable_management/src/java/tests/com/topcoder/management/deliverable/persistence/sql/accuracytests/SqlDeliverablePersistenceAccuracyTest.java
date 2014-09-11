/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql.accuracytests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.sql.SqlDeliverablePersistence;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for <code>SqlDeliverablePersistence</code>
 * </p>
 *
 * @author urtks
 * @version 1.0
 */
public class SqlDeliverablePersistenceAccuracyTest extends TestCase {
    /**
     * <p>
     * The connection name.
     * </p>
     */
    private static final String CONNECTION_NAME = "informix";

    /**
     * <p>
     * The create date.
     * </p>
     */
    private Date PREDEFINED_CREATE_DATE = null;

    /**
     * <p>
     * Predefined Deliverable.
     * </p>
     */
    private Deliverable[] PREDEFINED_DELIVERABLE;

    /**
     * <p>
     * An instance of deliverable.
     * </p>
     */
    private Deliverable deliverable;

    /**
     * <p>
     * An instance of db connection factory.
     * </p>
     */
    private DBConnectionFactory factory;

    /**
     * <p>
     * A connection to database.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * An instance of <code>SqlDeliverablePersistence</code> to test.
     * </p>
     */
    private SqlDeliverablePersistence tester;

    /**
     * <p>
     * Test SqlDeliverablePersistence(DBConnectionFactory connectionFactory),
     * when connectionFactory is valid, an instance should be created.
     * </p>
     */
    public void testCtor1() {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(factory);
        assertNotNull("Failed to create instance of SqlDeliverablePersistence", persistence);
    }

    /**
     * <p>
     * Test SqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName),
     * when both args are valid, an instance should be created.
     * </p>
     */
    public void testCtor2() {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(factory, CONNECTION_NAME);
        assertNotNull("Failed to create instance of SqlDeliverablePersistence", persistence);
    }

    /**
     * <p>
     * Test loadDeliverables(long deliverableId),
     * the deliverables with the id should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadDeliverables1() throws Exception {
        Deliverable[] deliverables = tester.loadDeliverables(2, 1, 1);
        assertEquals("Two deliverables should be returned.", 2, deliverables.length);
        List submissions = new ArrayList();
        submissions.add(new Long(1));
        submissions.add(new Long(2));
        for (int i = 0; i < deliverables.length; i++) {
            assertEquals("Failed to load deliverable.", 1, deliverables[i].getProject());
            submissions.remove(deliverables[i].getSubmission());
        }
        assertEquals("Failed to load deliverable.", 0, submissions.size());
    }

    /**
     * <p>
     * Test loadDeliverables(long deliverableId),
     * the deliverables with the id should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadDeliverables1_PerSubmissionFalse() throws Exception {
        Deliverable[] deliverables = tester.loadDeliverables(3, 1, 1);
        assertEquals("Two deliverable should be returned.", 1, deliverables.length);
        List projects = new ArrayList();
        projects.add(new Long(1));
        for (int i = 0; i < deliverables.length; i++) {
            assertNull("Failed to load deliverable.", deliverables[i].getSubmission());
            projects.remove(new Long(deliverables[i].getProject()));
        }
        assertEquals("Failed to load deliverable.", 0, projects.size());
    }

    /**
     * <p>
     * Test loadDeliverables(long deliverableId),
     * when no such id existed, empty array should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadDeliverables1_Empty() throws Exception {
        Deliverable[] deliverables = tester.loadDeliverables(1000, 11, 1);
        assertEquals("When no such id existed, empty array should be returned", 0, deliverables.length);
    }

    /**
     * <p>
     * Test loadDeliverable(long deliverableId, long submissionId),
     * the deliverable with the give deliverableId and submissionId should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadDeliverable() throws Exception {
        Deliverable deliverable = tester.loadDeliverable(2, 1, 1, 1);
        assertEquals("Failed to load deliverable.", 1, deliverable.getProject());
        assertEquals("Failed to load deliverable.", 1, deliverable.getSubmission().longValue());
    }

    /**
     * <p>
     * Test loadDeliverable(long deliverableId, long submissionId),
     * when the deliverable with the give deliverableId and submissionId not existed, null should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadDeliverable_NotExisted() throws Exception {
        assertNull("Failed to load deliverable.", tester.loadDeliverable(100, 1000, 11, 1));
    }

    /**
     * <p>
     * Test loadDeliverables(long[] deliverableIds),
     * the deliverables of the give deliverableId should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadDeliverables2() throws Exception {
        Deliverable[] deliverables = tester.loadDeliverables(new long[] {1, 2, 1, 1000}, new long[] {2, 1, 3, 1000}, new long[] {1, 1, 1, 1});
        assertEquals("Six deliverables should be returned.", 2, deliverables.length);

        List result = new ArrayList();
        result.add(new Long(1));
        result.add(new Long(2));
        for (int i = 0; i < deliverables.length; i++) {
            if (deliverables[i].getId() == 3) {
                assertNull("Failed to load deliverable.", deliverables[i].getSubmission());
                assertTrue("Failed to load deliverable.", result.contains(new Long(deliverables[i].getProject())));
            } else {
                assertTrue("Failed to load deliverable.", result.contains(new Long(deliverables[i].getProject())));
                assertTrue("Failed to load deliverable.", result.contains(deliverables[i].getSubmission()));
            }
        }
    }

    /**
     * <p>
     * Test loadDeliverables(long[] deliverableIds),
     * the deliverables of the give deliverableId should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadDeliverables2_Empty() throws Exception {
        Deliverable[] deliverables = tester.loadDeliverables(new long[0], new long[0], new long[0]);
        assertEquals("Empty array should be returned.", 0, deliverables.length);
    }

    /**
     * <p>
     * Test loadDeliverables(long[] deliverableIds, long[] submissionIds),
     * the deliverables of the give deliverableId should be returned.
     * </p>
     * @throws Exception
     */
    public void testLoadDeliverables3() throws Exception {
        Deliverable[] deliverables = tester.loadDeliverables(new long[] {1, 2, 1, 1000}, new long[] {2, 1, 3, 2}, new long[] {1, 1, 1, 1});
        assertEquals("Two deliverables should be returned.", 2, deliverables.length);

        List result = new ArrayList();
        result.add(new Long(1));
        result.add(new Long(2));
        for (int i = 0; i < deliverables.length; i++) {
            assertTrue("Failed to load deliverable.", result.contains(new Long(deliverables[i].getProject())));
        }
    }

    /**
     * <p>
     * Test loadDeliverables(long[] deliverableIds, long[] submissionIds),
     * when the give id pairs is emtpy, empty array should be returned.
     * </p>
     * @throws Exception
     */
    public void testLoadDeliverables3_Empty() throws Exception {
        assertEquals("when the give id pairs is emtpy, empty array should be returned.",
            0, tester.loadDeliverables(new long[0], new long[0], new long[0]).length);
    }

    /**
     * <p>
     * Assert the Deliverable with expected.
     * </p>
     *
     * @param msg the message.
     * @param expected the expected.
     * @param fact the fact.
     */
    private void assertEquals(String msg, Deliverable expected, Deliverable fact) {
        assertEquals(msg, expected.getId(), fact.getId());
        assertEquals(msg, expected.getPhase(), fact.getPhase());
        assertEquals(msg, expected.getResource(), fact.getResource());
        assertEquals(msg, expected.getCreationTimestamp(), fact.getCreationTimestamp());
        assertEquals(msg, expected.getCreationUser(), fact.getCreationUser());
        assertEquals(msg, expected.getDescription(), fact.getDescription());
        assertEquals(msg, expected.getModificationTimestamp(), fact.getModificationTimestamp());
        assertEquals(msg, expected.getModificationUser(), fact.getModificationUser());
        assertEquals(msg, expected.getName(), fact.getName());
    }

    /**
     * <p>
     * Assert Date.
     * </p>
     *
     * @param msg the message.
     * @param expected the expected date.
     * @param fact the fact.
     */
    private void assertEquals(String msg, Date expected, Date fact) {
        assertEquals(msg, expected.getTime(), fact.getTime());
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        AccuracyHelper.clearNamespace();
        AccuracyHelper.loadConfig();
        factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        connection = factory.createConnection();
        AccuracyHelper.clearData(connection);
        AccuracyHelper.prepareData(connection);
        predefineObject();
        tester = new SqlDeliverablePersistence(factory);
    }

    /**
     * <p>
     * Clean up the test environment.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    protected void tearDown() throws Exception {
        try {
            AccuracyHelper.clearData(connection);
        } catch (SQLException e) {
            // ignore
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore.
            }
        }
        AccuracyHelper.clearNamespace();
    }

    /**
     * <p>
     * Predefined deliverables.
     * </p>
     */
    private void predefineObject() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2006, 06, 18, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        PREDEFINED_CREATE_DATE = calendar.getTime();
        {
            PREDEFINED_DELIVERABLE =  new Deliverable[] {
                new Deliverable(1, 1, 1, new Long(1), true),
                new Deliverable(1, 2, 2, new Long(1), false),
                new Deliverable(1, 3, 2, new Long(2), true),
                new Deliverable(1, 4, 1, new Long(1), false)
            };
            for (int i = 0; i < PREDEFINED_DELIVERABLE.length; i++) {
                PREDEFINED_DELIVERABLE[i].setId(i + 1);
                PREDEFINED_DELIVERABLE[i].setDescription("deliverable desc");
                PREDEFINED_DELIVERABLE[i].setCreationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_DELIVERABLE[i].setModificationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_DELIVERABLE[i].setCreationUser("System");
                PREDEFINED_DELIVERABLE[i].setModificationUser("System");
                PREDEFINED_DELIVERABLE[i].setName("deliverable " + (i + 1));
            }
            deliverable = new Deliverable(2, 1, 1, new Long(1), true);
            deliverable.setDescription("deliverable desc");
            deliverable.setCreationTimestamp(PREDEFINED_CREATE_DATE);
            deliverable.setModificationTimestamp(PREDEFINED_CREATE_DATE);
            deliverable.setCreationUser("Tester");
            deliverable.setModificationUser("Tester");
            deliverable.setName("tester");
        }
    }
}
