/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql.stresstests;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.sql.SqlDeliverablePersistence;

/**
 * Stress tests for the class: SqlDeliverablePersistence.
 *
 * @author kinfkong, FireIce
 * @version 1.1
 */
public class SqlDeliverablePersistenceStressTest extends DbStressTest {

    /**
     * SqlDeliverablePersistence instance to test on.
     */
    private SqlDeliverablePersistence persistence = null;

    /**
     * Stress tests for the method: loadDeliverables(long, long, long).
     *
     * @throws Exception to JUnit
     */
    public void testLoadDeliverablesLong() throws Exception {

        long[] expected = new long[] {1};

        Date start = new Date();

        // stress tests
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // load the deliverables
            Deliverable[] deliverables = persistence.loadDeliverables(1, 2, 2);
            // assert the accuracy
            assertEquals("The length of the deliverables is not correct.", 1, deliverables.length);

            for (int j = 0; j < deliverables.length; j++) {
                assertEquals("The elements in the result is not correct.", expected[j], deliverables[j].getId());
            }
        }

        Date finish = new Date();

        // output the test info
        outputStressInfo(start, finish, "loadDeliverable");
    }

    /**
     * Stress tests for the method: loadDeliverable(long, long).
     *
     * @throws Exception to JUnit
     */
    public void testLoadDeliverable() throws Exception {
        Date start = new Date();

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            Deliverable diverable = persistence.loadDeliverable(1, 1, 1, 1);
            assertNull("The record does not exists.", diverable);
        }

        Date finish = new Date();

        // output the test info
        outputStressInfo(start, finish, "loadDeliverable");

    }

    /**
     * Stress tests for the method: loadDeliverables(long[]).
     *
     * @throws Exception to JUnit
     */
    public void testLoadDeliverablesLongArray() throws Exception {
        Date start = new Date();

        // creates the query ids
        long[] ids = new long[10];
        long[] idstwo = new long[10];
        long[] idsthree = new long[10];

        // sets the ids
        for (int i = 0; i < 10; i++) {
            ids[i] = i + 1;
            idstwo[i] = i + 2;
            idsthree[i] = i + 2;
        }

        long[] expected = new long[] {2, 1};

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            Deliverable[] deliverables = persistence.loadDeliverables(ids, idstwo, idsthree);
            assertIds(expected, deliverables);
        }

        Date finish = new Date();

        // output the test info
        outputStressInfo(start, finish, "loadDeliverables(long[])");
    }

    /**
     * A helper method to assert the accuracy.
     *
     * @param expected the ids that expected
     * @param deliverables the result of the deliverables
     */
    private void assertIds(long[] expected, Deliverable[] deliverables) {

        assertEquals("The length of the returned result is incorrect.", expected.length, deliverables.length);

        // put the expected result into a set
        Set expectedSet = new HashSet();

        for (int i = 0; i < expected.length; i++) {
            expectedSet.add(new Long(expected[i]));
        }

        for (int i = 0; i < deliverables.length; i++) {
            assertTrue("The answer is not correct.", expectedSet.contains(new Long(deliverables[i].getId())));
        }

    }

    /**
     * Stress tests for the method: loadDeliverables(long[], long[]).
     *
     * @throws Exception to JUnit
     */
    public void testLoadDeliverablesLongArrayLongArray() throws Exception {
        Date start = new Date();

        // creates the query ids
        long[] id1s = new long[10];
        long[] id2s = new long[10];
        long[] id3s = new long[10];

        // sets the ids
        for (int i = 0; i < 10; i++) {
            id1s[i] = i + 1;
            id2s[i] = i + 3;
            id3s[i] = 1;
        }

        long[] expected = new long[] {};

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            Deliverable[] deliverables = persistence.loadDeliverables(id1s, id2s, id3s);
            assertIds(expected, deliverables);
        }

        Date finish = new Date();

        // output the test info
        outputStressInfo(start, finish, "loadDeliverables(long[], long[])");
    }

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();

        // create the instance for tests
        persistence = new SqlDeliverablePersistence(getConnectionFactory());

        // get the connection
        Connection conn = getConnection();
        Statement statement = conn.createStatement();


        statement.addBatch("INSERT INTO project (project_id) VALUES (1)");
        statement.addBatch("INSERT INTO project (project_id) VALUES (2)");
        statement.addBatch("INSERT INTO project (project_id) VALUES (3)");

        statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (1)");
        statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (2)");
        statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (3)");

        statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (1)");
        statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (2)");
        statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (3)");

        statement.addBatch("INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (1, 1, 1)");
        statement.addBatch("INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (2, 2, 2)");
        statement.addBatch("INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (3, 3, 3)");

        statement.addBatch("INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id)"
                + " VALUES (1, 2, 1, 1)");
        statement.addBatch("INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id)"
                + " VALUES (2, 2, 2, 2)");
        statement.addBatch("INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id)"
                + " VALUES (3, 3, 3, 3)");

        // add upload_type
        statement.addBatch("INSERT INTO upload_type_lu(upload_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'Submission', 'Submission', 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO upload_type_lu(upload_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'Test Case', 'Test Case', 'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO upload_type_lu(upload_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (3, 'Final Fix', 'Final Fix', 'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO upload_type_lu(upload_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (4, 'Review Document', 'Review Document', "
            + "'System', CURRENT, 'System', CURRENT)");

        // add upload_status
        statement.addBatch("INSERT INTO upload_status_lu(upload_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO upload_status_lu(upload_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT)");

        // add submission status
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'Failed Screening', 'Failed Manual Screening', "
            + "'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (3, 'Failed Review', 'Failed Review', "
            + "'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (4, 'Completed Without Win', 'Completed Without Win', "
            + "'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (5, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT)");

        // add submission type
        statement.addBatch("INSERT INTO submission_type_lu"
            + "(submission_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'Specification Submission', 'Specification Submission', 'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO submission_type_lu"
            + "(submission_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'Contest Submission', 'Contest Submission', "
            + "'System', CURRENT, 'System', CURRENT)");



        statement.addBatch("INSERT INTO upload"
            + "(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 2, 2, 1, 1, 'parameter 1', 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO upload"
            + "(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 3, 3, 1, 1, 'parameter 2', 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO submission"
            + "(submission_id, upload_id, submission_status_id, submission_type_id, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 2, 3, 1, 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO submission"
            + "(submission_id, upload_id, submission_status_id, submission_type_id, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 1, 1, 2, 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO deliverable_lu"
            + "(deliverable_id, phase_type_id, resource_role_id, submission_type_id, required, "
            + "name, description, create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 2, 2, 2, 1, 'deliverable 1', 'per submission deliverable', "
            + "'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO deliverable_lu"
            + "(deliverable_id, phase_type_id, resource_role_id, submission_type_id, required, "
            + "name, description, create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 3, 3, null, 0, 'deliverable 2', 'non per submission deliverable', "
            + "'System', CURRENT, 'System', CURRENT)");

        statement.executeBatch();
        statement.close();
        conn.commit();
        conn.close();
    }
}
