/*
 * Copyright (C) 2006,2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.management.deliverable.persistence.sql.SqlDeliverablePersistence;
import com.topcoder.management.deliverable.search.DeliverableFilterBuilder;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;

/**
 * Unit test for PersistenceDeliverableManager.
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>generic types are used for simplification.</li>
 * <li>some useless test cases are removed.</li>
 * </ul>
 * </p>
 *
 * @author singlewood
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class PersistenceDeliverableManagerTests extends TestCase {

    /**
     * The path of the configuration file.
     */
    private static final String CONFIG = "SearchBundleManager.xml";

    /**
     * File contains sql statement to initial database for upload search.
     */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /**
     * File contains sql statement to clear database for upload search.
     */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

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
     * <p>
     * Changes in version 1.2: generic type is used.
     * </p>
     */
    private Map<String, DeliverableChecker> deliverableCheckers = null;

    /**
     * The test SearchBundle instance.
     */
    private SearchBundle deliverableSearchBundle = null;

    /**
     * The test SearchBundle instance.
     */
    private SearchBundle submissionSearchBundle = null;

    /**
     * The test SearchBundleManager instance.
     */
    private SearchBundleManager searchBundleManager = null;

    /**
     * Create the test instance.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    public void setUp() throws Exception {
        DeliverableTestHelper.unloadConfig();
        DeliverableTestHelper.loadConfig(CONFIG);

        // Set up the SearchBundleManager.
        searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);

        deliverableCheckers = new HashMap<String, DeliverableChecker>();
        deliverableCheckers.put("name1", new MockDeliverableChecker());

        persistence = new SqlDeliverablePersistence(null);

        persistenceDeliverableManager = new PersistenceDeliverableManager(persistence, deliverableCheckers,
                searchBundleManager);
    }

    /**
     * Clean the config.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    public void tearDown() throws Exception {
        DeliverableTestHelper.unloadConfig();
    }

    /**
     * Set the first parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null1() {
        try {
            new PersistenceDeliverableManager(null, deliverableCheckers, deliverableSearchBundle,
                    submissionSearchBundle);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the second parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null2() {
        try {
            new PersistenceDeliverableManager(persistence, null, deliverableSearchBundle, submissionSearchBundle);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the third parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null3() {
        try {
            new PersistenceDeliverableManager(persistence, deliverableCheckers, null, submissionSearchBundle);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the fourth parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null4() {
        try {
            new PersistenceDeliverableManager(persistence, deliverableCheckers, deliverableSearchBundle, null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
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
     * If deliverableCheckers is not a Map of non-null String -> DeliverableChecker, IllegalArgumentException should be
     * thrown. Here we give a null key
     */
    public void testConstructor1_InvalidDeliverableCheckers2() {
        try {
            Map<String, DeliverableChecker> m = new HashMap<String, DeliverableChecker>();
            m.put(null, new DeliverableCheckerImp());
            new PersistenceDeliverableManager(persistence, m, deliverableSearchBundle, submissionSearchBundle);
            fail("IllegalArgumentException should be thrown because of invalid parameters.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the first parameter of constructor2 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor2_Null1() {
        try {
            new PersistenceDeliverableManager(null, deliverableCheckers, searchBundleManager);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the second parameter of constructor2 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor2_Null2() {
        try {
            new PersistenceDeliverableManager(persistence, null, searchBundleManager);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the third parameter of constructor2 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor2_Null3() {
        try {
            new PersistenceDeliverableManager(persistence, deliverableCheckers, null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the searchBundleManager without the SearchBundle, named 'DELIVERABLE_SEARCH_BUNDLE_NAME', in it.
     * IllegalArgumentException should be thrown.
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
     * Set the searchBundleManager without the SearchBundle, named 'DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME', in
     * it. IllegalArgumentException should be thrown.
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
     * Test the behavior of searchDeliverablesWithSubmissionFilter. Set the first parameter as null.
     * IllegalArgumentException should be thrown.
     */
    public void testSearchDeliverablesWithSubmissionFilter_Null1() {
        try {
            persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(null, null);
            fail("IllegalArgumentException should be thrown with filter = null");
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
     * Test the behavior of searchDeliverables. Set the first parameter as null. IllegalArgumentException should be
     * thrown.
     */
    public void testSearchDeliverablesFilter_Null1() {
        try {
            persistenceDeliverableManager.searchDeliverables(null, null);
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
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesByDeliverableId() throws Exception {
        persistenceDeliverableManager = new PersistenceDeliverableManager(persistence, deliverableCheckers,
                searchBundleManager);

        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createDeliverableIdFilter(1), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);
            assertEquals("Wrong deliverable retrieved", 1, deliverables[0].getId());

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));

            // search complete deliverables
            deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createDeliverableIdFilter(4), new Boolean(false));
            assertEquals("Wrong number of deliverable retrieved", 1, deliverables.length);
            assertEquals("Wrong deliverable retrieved", 4, deliverables[0].getId());
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with name filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesByName() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createNameFilter("name2"), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);
            assertEquals("Wrong deliverable retrieved", 2, deliverables[0].getId());

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with project id filter.
     *
     * @throws Exception
     *             pass to JUnit.
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
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesByPhaseId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createPhaseIdFilter(2), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(2)));

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with resource role id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesByResourceRoleId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createResourceIdFilter(1), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(1)));

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with required filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesByRequired() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverables(DeliverableFilterBuilder
                    .createRequiredFilter(true), null);
            assertEquals("Wrong number of deliverable retrieved", 1, deliverables.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(4)));

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with deliverable id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByDeliverableId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createDeliverableIdFilter(1), null);
            assertEquals("Wrong number of deliverable retrieved", 8, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with name filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByName() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createNameFilter("name2"), null);
            assertEquals("Wrong number of deliverable retrieved", 8, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with project id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByProjectId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createProjectIdFilter(2), null);

            assertEquals("Wrong number of deliverable retrieved", 16, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with phase id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByPhaseId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createPhaseIdFilter(2), null);
            assertEquals("Wrong number of deliverable retrieved", 4, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with resource role id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByResourceRoleId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createResourceIdFilter(2), null);
            assertEquals("Wrong number of deliverable retrieved", 16, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with submission id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterBySubmissionId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = persistenceDeliverableManager.searchDeliverablesWithSubmissionFilter(
                    DeliverableFilterBuilder.createSubmissionIdFilter(1), null);
            assertEquals("Wrong number of deliverable retrieved", 0, deliverables.length);

            assertTrue("Persistence method not called correctly", SqlDeliverablePersistence.getLastCalled().startsWith(
                    "loadDeliverables"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }
}
