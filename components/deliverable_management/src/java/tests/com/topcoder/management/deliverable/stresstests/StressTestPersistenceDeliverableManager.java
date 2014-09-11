/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */



package com.topcoder.management.deliverable.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.PersistenceDeliverableManager;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.sql.MySqlDeliverablePersistence;
import com.topcoder.management.deliverable.search.DeliverableFilterBuilder;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.IntegerValidator;
import com.topcoder.util.datavalidator.LongValidator;
import com.topcoder.util.datavalidator.StringValidator;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Stress test for method <code>PersistenceDeliverableManager </code>.
 *
 * @author Chenhong, morehappiness
 * @version 1.1
 */
public class StressTestPersistenceDeliverableManager extends TestCase {
    /**
     * The run times of the tests.
     */
    private static final int RUN_TIMES = 20;

    /**
     * Represents the PersistenceDeliverableManager instance for test.
     */
    private PersistenceDeliverableManager manager = null;

    /**
     * Represents the DeliverablePersistence instance for test.
     */
    private DeliverablePersistence persistence = null;

    /**
     * Represents the SearchBundleManager instance for test.
     */
    private SearchBundleManager sm = null;

    /**
     * Set up the testing environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext(); ) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add("stresstests/DBConnectionFactory.xml");
        cm.add("stresstests/SearchBundleManager_2.xml");

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        persistence = new MySqlDeliverablePersistence(factory);

        String s_n = "com.topcoder.searchbuilder.DeliverableManager";

        sm = new SearchBundleManager(s_n);

        Map fields = new HashMap();

        fields.put("name", StringValidator.hasLength(IntegerValidator.greaterThanOrEqualTo(0)));
        fields.put("deliverable_id", LongValidator.greaterThanOrEqualTo(0));
        fields.put("project_id", LongValidator.greaterThanOrEqualTo(0));
        fields.put("resource_id", LongValidator.greaterThanOrEqualTo(0));
        fields.put("submission_id", LongValidator.greaterThanOrEqualTo(0));

        SearchBundle deliverableSearchBundle = sm.getSearchBundle("Deliverable Search Bundle");

        deliverableSearchBundle.setSearchableFields(fields);

        SearchBundle deliverableWithSubmissionsSearchBundle =
            sm.getSearchBundle("Deliverable With Submission Search Bundle");

        deliverableWithSubmissionsSearchBundle.setSearchableFields(fields);

        manager = new PersistenceDeliverableManager(persistence, new HashMap(), sm);

        DBTestUtil.setUpTest();

        DBTestUtil.insertDeliverable();
    }

    /**
     * Clear the tables for testing and remove all the namespaces in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        DBTestUtil.tearDownTest();

        DBTestUtil.removeAllNamespace();
    }

    /**
     * Test for void PersistenceDeliverableManager(DeliverablePersistence, Map, SearchBundleManager).
     */
    public void testConstructor() {
        assertNotNull("Should not be null.", manager);
    }

    /**
     * Stress test method
     * <code> Deliverable[] searchDeliverablesWithSubmissions(Filter filter, Boolean complete) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchDeliverablesWithSubmissions() throws Exception {
        long total = 0;

        Filter filter = DeliverableFilterBuilder.createProjectIdFilter(1);
        Deliverable[] ds = null;

        for (int i = 0; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();

            ds = manager.searchDeliverablesWithSubmissionFilter(filter, null);

            total += System.currentTimeMillis() - start;

            // There is only one deliverable in the database.
            // The parameter complete passed is null, indicating that both complete and
            // incomplete deliverable should be returned.
            // So one deliverable should be returned.

            assertEquals("Equal is expected.", 0, ds.length);
        }

        System.out.print("Invoke SearchDeliverablesWithSubmissions for 20 times cost ");
        System.out.println(total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>Deliverable[] searchDeliverables(Filter filter, Boolean complete) </code>
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchDeliverables() throws Exception {
        long total = 0;

        Filter filter = DeliverableFilterBuilder.createDeliverableIdFilter(1);

        for (int i = 0; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();
            Deliverable[] ds = manager.searchDeliverables(filter, null);

            total += System.currentTimeMillis() - start;

            // There is only one deliverable in the database.
            // The parameter complete passed is null, indicating that both complete and
            // incomplete deliverable should be returned.
            // So one deliverable should be returned.

            assertEquals("Equal is expected.", 2, ds.length);
        }

        System.out.println("Invoke SearchDeliverables for 20 times cost " + total / 1000.0 + " seconds.");
    }
}
