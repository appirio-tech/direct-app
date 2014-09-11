/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.deliverable.PersistenceDeliverableManager;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests for class <code>PersistenceDeliverableManager</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class PersistenceDeliverableManagerTest extends TestCase {

    /**
     * Represents the manager to test.
     */
    private PersistenceDeliverableManager manager;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        ConfigManager cm = ConfigManager.getInstance();
        if (!cm.existsNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")) {
            cm.add("failure/db.xml");
        }


        Map fields = new HashMap();
        fields.put("11", null);
        Map alias = new HashMap();
        alias.put("11", "22");
        manager = new PersistenceDeliverableManager(
                new SqlDeliverablePersistence(
                    new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    new HashMap(),
                    new SearchBundle("deliverable", fields, alias, "dd"),
                    new SearchBundle("submission", fields, alias, "dd"));
    }

    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        ConfigManager cm = ConfigManager.getInstance();

        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace(it.next().toString());
        }
    }

    /**
     * Test method for PersistenceDeliverableManager(DeliverablePersistence, Map, SearchBundle, SearchBundle).
     */
    public void testPersistenceDeliverableManager1NullPersistence() {
        try {
            new PersistenceDeliverableManager(
                        null,
                        new HashMap(),
                        new SearchBundle("deliverable", new HashMap(), new HashMap(), "dd"),
                        new SearchBundle("submission", new HashMap(), new HashMap(), "dd"));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceDeliverableManager(DeliverablePersistence, Map, SearchBundle, SearchBundle).
     * @throws ConfigurationException to JUnit
     * @throws UnknownConnectionException to JUnit
     */
    public void testPersistenceDeliverableManager1NullMap() throws UnknownConnectionException, ConfigurationException {
        try {
            new PersistenceDeliverableManager(
                    new SqlDeliverablePersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                        null,
                        new SearchBundle("deliverable", new HashMap(), new HashMap(), "dd"),
                        new SearchBundle("submission", new HashMap(), new HashMap(), "dd"));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceDeliverableManager(DeliverablePersistence, Map, SearchBundle, SearchBundle).
     * @throws ConfigurationException to JUnit
     * @throws UnknownConnectionException to JUnit
     */
    public void testPersistenceDeliverableManager1NonStringKeyInMap()
        throws UnknownConnectionException, ConfigurationException {
        try {
            HashMap map = new HashMap();
            map.put(new Object(), new MockDeliverableChecker());
            new PersistenceDeliverableManager(
                    new SqlDeliverablePersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                        map,
                        new SearchBundle("deliverable", new HashMap(), new HashMap(), "dd"),
                        new SearchBundle("submission", new HashMap(), new HashMap(), "dd"));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceDeliverableManager(DeliverablePersistence, Map, SearchBundle, SearchBundle).
     * @throws ConfigurationException to JUnit
     * @throws UnknownConnectionException to JUnit
     */
    public void testPersistenceDeliverableManager1NonCheckerInMap()
        throws UnknownConnectionException, ConfigurationException {
        try {
            HashMap map = new HashMap();
            map.put("a", "b");
            new PersistenceDeliverableManager(
                    new SqlDeliverablePersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                        map,
                        new SearchBundle("deliverable", new HashMap(), new HashMap(), "dd"),
                        new SearchBundle("submission", new HashMap(), new HashMap(), "dd"));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceDeliverableManager(DeliverablePersistence, Map, SearchBundle, SearchBundle).
     * @throws ConfigurationException to JUnit
     * @throws UnknownConnectionException to JUnit
     */
    public void testPersistenceDeliverableManager1NullBundle1()
        throws UnknownConnectionException, ConfigurationException {
        try {
            HashMap map = new HashMap();
            new PersistenceDeliverableManager(
                    new SqlDeliverablePersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                        map,
                        null,
                        new SearchBundle("submission", new HashMap(), new HashMap(), "dd"));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceDeliverableManager(DeliverablePersistence, Map, SearchBundle, SearchBundle).
     * @throws ConfigurationException to JUnit
     * @throws UnknownConnectionException to JUnit
     */
    public void testPersistenceDeliverableManager1NullBundle2()
        throws UnknownConnectionException, ConfigurationException {
        try {
            HashMap map = new HashMap();
            new PersistenceDeliverableManager(
                    new SqlDeliverablePersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                        map,
                        new SearchBundle("submission", new HashMap(), new HashMap(), "dd"),
                        null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for searchDeliverablesWithSubmissions(com.topcoder.search.builder.filter.Filter, java.lang.Boolean).
     * @throws Exception to JUnit
     */
    public void testSearchDeliverablesWithSubmissions() throws Exception {
        try {
            manager.searchDeliverablesWithSubmissionFilter(null, Boolean.FALSE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for searchDeliverables(com.topcoder.search.builder.filter.Filter, java.lang.Boolean).
     * @throws Exception to JUnit
     */
    public void testSearchDeliverables() throws Exception {
        try {
            manager.searchDeliverables(null, Boolean.FALSE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
