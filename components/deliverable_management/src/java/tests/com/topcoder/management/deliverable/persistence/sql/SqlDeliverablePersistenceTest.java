/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.management.deliverable.persistence.TestHelper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for <code>SqlDeliverablePersistenceTest</code>.
 *
 * @author urtks, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class SqlDeliverablePersistenceTest extends TestCase {
    /**
     * Represents an instance of connection factory used in test methods.
     */
    private DBConnectionFactory connectionFactory = null;

    /**
     * Aggregates all tests in this class.
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(SqlDeliverablePersistenceTest.class);
    }

    /**
     * Sets up the test environment. The configuration will be loaded.
     *
     * @throws Exception
     *             if any error occurs
     */
    protected void setUp() throws Exception {
        tearDown();

        TestHelper.loadConfig();

        TestHelper.executeBatch("test_files/insertDeliverable.sql");

        // create a connection factory
        connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
    }

    /**
     * Clean up the test environment. The configuration will be unloaded.
     *
     * @throws Exception
     *             if any error occurs
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadConfig();

        TestHelper.executeBatch("test_files/delete.sql");

        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory)</code>.
     * </p>
     * <p>
     * An instance of SqlDeliverablePersistence can be created.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyConstructorA1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory);

        assertNotNull("Unable to create SqlUploadPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureConstructorA1() throws Exception {
        try {
            new SqlDeliverablePersistence(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("connectionFactory should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName)</code>.
     * </p>
     * <p>
     * An instance of SqlDeliverablePersistence can be created.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyConstructorB1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        assertNotNull("Unable to create SqlUploadPersistence.", persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName)</code>.
     * </p>
     * <p>
     * An instance of SqlDeliverablePersistence can be created. connectionName is null.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyConstructorB2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, null);

        assertNotNull("Unable to create SqlUploadPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureConstructorB1() throws Exception {
        try {
            new SqlDeliverablePersistence(null, "informix_connection");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("connectionFactory should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadDeliverables(long deliverableId)</code>.
     * </p>
     * <p>
     * Check if the "per submission" deliverables are loaded correctly from the database.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverablesA1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(1, 2, 2);

        assertEquals("1 results", 1, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase type id", 2, deliverable.getPhase());
        assertEquals("check resource id", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable.getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadDeliverables(long deliverableId)</code>.
     * </p>
     * <p>
     * Check if the non-"per submission" deliverables are loaded correctly from the database.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverablesA2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(2, 3, 3);

        assertEquals("1 results", 1, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 2, deliverable.getId());
        assertEquals("check project id", 3, deliverable.getProject());
        assertEquals("check phase type id", 3, deliverable.getPhase());
        assertEquals("check resource id", 3, deliverable.getResource());
        assertNull("check submission id", deliverable.getSubmission());
        assertEquals("check required", false, deliverable.isRequired());
        assertEquals("check name", "deliverable 2", deliverable.getName());
        assertEquals("check description", "non per submission deliverable", deliverable.getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());

    }

    /**
     * <p>
     * Accuracy test of the method <code>loadDeliverables(long deliverableId)</code>.
     * </p>
     * <p>
     * Check if the deliverable is loaded correctly from the database. empty array is returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverablesA3() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(10, 1, 1);

        assertEquals("0 results", 0, deliverables.length);
    }

    /**
     * <p>
     * Failure test of the method <code>loadDeliverables(long deliverableId)</code>.
     * </p>
     * <p>
     * deliverableId is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverablesA1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverables(-1, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadDeliverables(long[] deliverableIds)</code>.
     * </p>
     * <p>
     * Check if the "per submission" deliverables are loaded correctly from the database.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverablesB1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[] {1, 3}, new long[] {2, 2}, new long[] {2,
            1});

        assertEquals("1 results", 1, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase type id", 2, deliverable.getPhase());
        assertEquals("check resource id", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable.getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadDeliverables(long[] deliverableIds)</code>.
     * </p>
     * <p>
     * Check if the non-"per submission" deliverables are loaded correctly from the database.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverablesB2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[] {1, 2}, new long[] {2, 3}, new long[] {2,
            3});

        assertEquals("2 results", 2, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 2, deliverable.getId());
        assertEquals("check project id", 3, deliverable.getProject());
        assertEquals("check phase type id", 3, deliverable.getPhase());
        assertEquals("check resource role id", 3, deliverable.getResource());
        assertNull("check submission id", deliverable.getSubmission());
        assertEquals("check required", false, deliverable.isRequired());
        assertEquals("check name", "deliverable 2", deliverable.getName());
        assertEquals("check description", "non per submission deliverable", deliverable.getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());

        deliverable = deliverables[1];
        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase type id", 2, deliverable.getPhase());
        assertEquals("check resource id", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable.getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());

    }

    /**
     * <p>
     * Accuracy test of the method <code>loadDeliverables(long[] deliverableIds)</code>.
     * </p>
     * <p>
     * Check if the deliverables are loaded correctly from the database. deliverableIds is empty and empty array is
     * returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverablesB3() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[0], new long[0], new long[0]);

        assertEquals("0 results", 0, deliverables.length);
    }

    /**
     * <p>
     * Failure test of the method <code>loadDeliverables(long[] deliverableIds)</code>.
     * </p>
     * <p>
     * deliverableIds is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverablesB1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverables(null, null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadDeliverables(long[], long[], long[])</code>.
     * </p>
     * <p>
     * deliverableIds contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverablesB2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1, -1}, new long[] {1, 1}, new long[] {1, 1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>loadDeliverables(long[], long[], long[])</code> method when connection to database is
     * invalid.
     * </p>
     * <p>
     * DeliverablePersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverablesB3() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "invalid_connection");
        try {
            persistence.loadDeliverables(new long[] {1, 2}, new long[] {2, 3}, new long[] {2, 3});
            fail("DeliverablePersistenceException should be thrown.");
        } catch (DeliverablePersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>Deliverable loadDeliverable(long deliverableId, long submissionId)</code>.
     * </p>
     * <p>
     * Check if the per submission deliverable is loaded correctly from the database.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverable1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable deliverable = persistence.loadDeliverable(1, 2, 2, 2);

        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase id", 2, deliverable.getPhase());
        assertEquals("check resource id", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable.getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the method <code>Deliverable loadDeliverable(long deliverableId, long submissionId)</code>.
     * </p>
     * <p>
     * Check if the per submission deliverable is loaded correctly from the database. null should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverable2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable deliverable = persistence.loadDeliverable(2, 1, 1, 1);

        assertNull("deliverable should be null", deliverable);
    }

    /**
     * <p>
     * Failure test of the method <code>Deliverable loadDeliverable(long deliverableId, long submissionId)</code>.
     * </p>
     * <p>
     * deliverableId is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverable1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverable(-1, 2, 2, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>Deliverable loadDeliverable(long deliverableId, long submissionId)</code>.
     * </p>
     * <p>
     * submissionId is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverable2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverable(1, -1, 2, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("resourceId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>loadDeliverable(long, long, long, long)</code> method when connection to database is
     * invalid.
     * </p>
     * <p>
     * DeliverablePersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverable3() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "invalid_connection");
        try {
            persistence.loadDeliverable(2, 1, 1, 1);
            fail("DeliverablePersistenceException should be thrown.");
        } catch (DeliverablePersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadDeliverables(long[], long[], long[])</code>.
     * </p>
     * <p>
     * Check if the deliverables are loaded correctly from the database.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverablesC1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[] {1, 1, 2, 2}, new long[] {1, 2, 1, 2},
                new long[] {1, 2, 1, 1});

        assertEquals("1 result", 1, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase type id", 2, deliverable.getPhase());
        assertEquals("check resource id", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable.getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadDeliverables(long[], long[], long[], long[])</code>.
     * </p>
     * <p>
     * Check if the deliverable is loaded correctly from the database. deliverableIds and submissionIds is empty and
     * empty array is returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadDeliverablesC2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[0], new long[0], new long[0], new long[0]);

        assertEquals("0 results", 0, deliverables.length);
    }

    /**
     * <p>
     * Failure test of the method <code>loadDeliverables(long[], long[])</code>.
     * </p>
     * <p>
     * deliverableIds is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverablesC1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverables(null, new long[] {1}, new long[] {1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadDeliverables(long[], long[], long[])</code>.
     * </p>
     * <p>
     * submissionIds is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverablesC2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1}, null, new long[] {1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("resourceIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadDeliverables(long[], long[], long[])</code>.
     * </p>
     * <p>
     * deliverableIds contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverablesC3() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1, -1}, new long[] {1, 2}, new long[] {1, 1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadDeliverables(long[], long[], long[])</code>.
     * </p>
     * <p>
     * submissionIds contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverablesC4() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1, 2}, new long[] {1, -1}, new long[] {1, 1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("resourceIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadDeliverables(long[], long[], long[])</code>.
     * </p>
     * <p>
     * deliverableIds and submissionIds contain different number of items. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadDeliverablesC5() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1, 2, 3}, new long[] {1, 2}, new long[] {1, 1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableIds, resourceIds and phaseIds should have the same number of elements.", e
                    .getMessage());
        }
    }
}
