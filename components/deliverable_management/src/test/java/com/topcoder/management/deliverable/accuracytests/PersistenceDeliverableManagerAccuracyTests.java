/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.DeliverableTestHelper;
import com.topcoder.management.deliverable.PersistenceDeliverableManager;
import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.management.deliverable.persistence.sql.SqlDeliverablePersistence;
import com.topcoder.management.deliverable.search.DeliverableFilterBuilder;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * Unit test for PersistenceDeliverableManager.
 *
 * @author skatou
 * @version 1.0
 */
public class PersistenceDeliverableManagerAccuracyTests extends TestCase {

    /**
     * The path of the configuration file.
     */
    private static final String CONFIG = "accuracytests/PersistenceDeliverableManagerConfig.xml";

    /**
     * File contains sql statement to initial database for upload search.
     */
    private static final String INIT_DB_SQL = "test_files/accuracytests/InitDB.sql";

    /**
     * File contains sql statement to clear database for upload search.
     */
    private static final String CLEAR_DB_SQL = "test_files/accuracytests/ClearDB.sql";

    /**
     * Represents the configuration namespace for search builder.
     */
    private static final String SEARCH_BUILDER_NAMESPACE = "com.topcoder.searchbuilder.DeliverableManager";

    /**
     * The test PersistenceDeliverableManager instance.
     */
    private PersistenceDeliverableManager persistenceDeliverableManager = null;

    /**
     * The test DeliverablePersistence instance.
     */
    private DeliverablePersistence persistence = null;

    /**
     * The test Map instance.
     */
    private Map deliverableCheckers = null;

    /**
     * The test SearchBundle instance.
     */
    private SearchBundle deliverableSearchBundle = null;

    /**
     * The test SearchBundle instance.
     */
    //private SearchBundle submissionSearchBundle = null;

    /**
     * The test SearchBundleManager instance.
     */
    private SearchBundleManager searchBundleManager = null;

    /** Logger instance using the class name as category.
    */
    private static final Log logger = LogFactory.getLog(PersistenceDeliverableManagerAccuracyTests.class.getName());

    /**
     * Create the test instance.
     *
     * @throws Exception exception to JUnit.
     */
    public void setUp() throws Exception {
        DeliverableTestHelper.unloadConfig();
        DeliverableTestHelper.loadConfig(CONFIG);

        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection connection = factory.createConnection();
        Statement statement = connection.createStatement();
        statement.addBatch("DELETE FROM id_sequences");
        statement.addBatch("INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('upload_id_seq', 1, 20, 0);");
        statement.addBatch("INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('upload_type_id_seq', 1, 20, 0);");
        statement.addBatch("INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('upload_status_id_seq', 1, 20, 0);");
        statement.addBatch("INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('submission_id_seq', 1, 20, 0);");
        statement.addBatch("INSERT INTO id_sequences(name, next_block_start, block_size, exhausted)  VALUES('submission_status_id_seq', 1, 20, 0);");

        // Set up the SearchBundleManager.
        searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);

        deliverableCheckers = new HashMap();
        deliverableCheckers.put("name 5", new MockDeliverableChecker());
        deliverableCheckers.put("name1", new MockDeliverableChecker());

        persistence = new SqlDeliverablePersistence(null);

        persistenceDeliverableManager = new PersistenceDeliverableManager(persistence, deliverableCheckers,
                searchBundleManager);
    }

    /**
     * Clean the config.
     *
     * @throws Exception exception to JUnit.
     */
    public void tearDown() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection connection = factory.createConnection();
        Statement statement = connection.createStatement();
        statement.addBatch("DELETE FROM id_sequences");

        DeliverableTestHelper.unloadConfig();
    }

    /**
     * Set constructor1 with valid parameters. No exception should be thrown.
     */
    public void testConstructor1_Valid() {
        try {
            new PersistenceDeliverableManager(persistence, deliverableCheckers, searchBundleManager
                    .getSearchBundle("Deliverable Search Bundle"), searchBundleManager
                    .getSearchBundle("Deliverable With Submission Search Bundle"));
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown because of valid parameters.");
        }
    }

    /**
     * Set the searchBundleManager without the SearchBundle, named 'DELIVERABLE_SEARCH_BUNDLE_NAME',
     * in it. IllegalArgumentException should be thrown.
     */
    public void testConstructor2_searchBundleManagerEmpty1() {
        try {
            searchBundleManager.removeSearchBundle("Deliverable Search Bundle");
            new PersistenceDeliverableManager(persistence, deliverableCheckers, searchBundleManager);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the searchBundleManager without the SearchBundle, named
     * 'DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME', in it. IllegalArgumentException should be
     * thrown.
     */
    public void testConstructor2_searchBundleManagerEmpty2() {
        try {
            searchBundleManager.removeSearchBundle("Deliverable With Submission Search Bundle");
            new PersistenceDeliverableManager(persistence, deliverableCheckers, searchBundleManager);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test the behavior of searchDeliverablesWithSubmissionFilter.
     */
    public void testSearchDeliverablesWithSubmissionFilter_Accuracy() {
        try {
            persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(null, null);
            fail("IllegalArgumentException should not be thrown with filter = null");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (DeliverablePersistenceException e) {
            fail("DeliverablePersistenceException should not be thrown under this config.");
        } catch (SearchBuilderException e) {
            fail("SearchBuilderException should not be thrown under this config.");
        } catch (DeliverableCheckingException e) {
            fail("DeliverableCheckingException should not be thrown under this config.");
        }
    }

    /**
     * Tests searchDeliverables method with deliverable id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByDeliverableId() throws Exception {
        try {
            logger.log(Level.INFO, "testSearchDeliverablesByDeliverableId - start");

            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(
                DeliverableFilterBuilder.createDeliverableIdFilter(1), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);
            assertEquals("Wrong deliverable retrieved", 1, deliverables[0].getId());

            assertNotNull("No Last Method Called", SqlDeliverablePersistence.getLastCalled());

            assertTrue("Persistence method not called correctly: "
                + SqlDeliverablePersistence.getLastCalled(),
                SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));

            // search complete deliverables
            deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createDeliverableIdFilter(4), new Boolean(true));
            assertEquals("Wrong number of deliverable retrieved", 1, deliverables.length);
            assertEquals("Wrong deliverable retrieved", 4, deliverables[0].getId());

            logger.log(Level.INFO, "testSearchDeliverablesByDeliverableId - end");
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with name filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByName() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createNameFilter("name2"), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);
            assertEquals("Wrong deliverable retrieved", 2, deliverables[0].getId());

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with project id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByProjectId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createProjectIdFilter(2), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with phase id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByPhaseId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createPhaseIdFilter(2), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

            Set ids = new HashSet();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(2)));

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with resource role id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByResourceRoleId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createResourceIdFilter(1), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

            Set ids = new HashSet();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(1)));

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with required filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByRequired() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createRequiredFilter(true), null);
            assertEquals("Wrong number of deliverable retrieved", 1, deliverables.length);

            Set ids = new HashSet();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(4)));

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with deliverable id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByDeliverableId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createDeliverableIdFilter(1), null);
            assertEquals("Wrong number of deliverable retrieved", 0, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with name filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByName() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createNameFilter("name2"), null);
            assertEquals("Wrong number of deliverable retrieved", 8, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with project id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByProjectId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createProjectIdFilter(2), null);

            assertEquals("Wrong number of deliverable retrieved", 8, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with phase id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByPhaseId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createPhaseIdFilter(2), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with resource role id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByResourceRoleId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createResourceIdFilter(2), null);
            assertEquals("Wrong number of deliverable retrieved", 8, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with submission id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterBySubmissionId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createSubmissionIdFilter(1), null);
            assertEquals("Wrong number of deliverable retrieved", 0, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled()
                    .startsWith("loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }
}
