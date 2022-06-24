/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.io.File;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Generating benchmark for the db operations.
 *
 * @author Chenhong
 * @version 1.1
 */
public class BenchmarkTest extends TestCase {
    /**
     * Represents the SqlResourcePersistence instance for test.
     */
    private SqlResourcePersistence persistence = null;

    /**
     * Represents the DBConnectionFactory instance for test.
     */
    private DBConnectionFactory factory = null;

    /**
     * Set up the environment. Create SqlResourcePersistence instance for test.
     *
     * @throws Exception to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        DBTestUtil.clearConfigManager();

        File file = new File("test_files/DBConnectionFactory.xml");

        cm.add(file.getAbsolutePath());

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

        factory = new DBConnectionFactoryImpl(namespace);

        persistence = new SqlResourcePersistence(factory);

        DBTestUtil.clearTables();

        DBTestUtil.setupDatbase();
    }

    /**
     * Tear down the environment. Clear all namespaces in the config manager.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        DBTestUtil.clearConfigManager();
    }

    /**
     * Benchmark for the addResource method.
     *
     * @throws Exception to junit.
     */
    public void testAddResource() throws Exception {
        // Create a role in advance.
        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence.addResourceRole(role);

        Date start = new Date();
        for (int i = 0; i < 20; i++) {
            long resourceId = i + 1;
            Resource resource = DBTestUtil.createResource(resourceId, 1, 1);
            this.persistence.addResource(resource);
        }

        double time = (new Date().getTime() - start.getTime()) / 1000.0;
        System.out.println("Calling addResource method 20 times, cost time: " + time + "s.");
    }

    /**
     * Benchmark the getResource method.
     *
     * @throws Exception to junit.
     */
    public void testGetResource() throws Exception {
        // Create a role in advance.
        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence.addResourceRole(role);

        // Create a resource.
        Resource resource = DBTestUtil.createResource(10, 1, 1);
        persistence.addResource(resource);

        Date start = new Date();
        for (int i = 0; i < 20; i++) {
            Resource result = this.persistence.loadResource(resource.getId());
            assertEquals("Incorrect id", resource.getId(), result.getId());
        }

        double time = (new Date().getTime() - start.getTime()) / 1000.0;
        System.out.println("Calling loadResource method 20 times, cost time: " + time + "s.");
    }

    /**
     * Benchmark the updateResource method.
     *
     * @throws Exception to junit.
     */
    public void testUpdateResource() throws Exception {
        // Create a role in advance.
        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence.addResourceRole(role);

        DBTestUtil.insertIntoResource_info_type_lu(1, "name");

        // Create a resource.
        Resource resource = DBTestUtil.createResource(10, 1, 1);
        persistence.addResource(resource);

        Date start = new Date();
        for (int i = 0; i < 20; i++) {
            resource.setProperty("name", "name" + i);
            this.persistence.updateResource(resource);
        }

        double time = (new Date().getTime() - start.getTime()) / 1000.0;
        System.out.println("Calling updateResource method 20 times, cost time: " + time + "s.");
    }

    /**
     * Benchmark the updateResource method.
     *
     * @throws Exception to junit.
     */
    public void testDeleteResource() throws Exception {
        // Create a role in advance.
        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence.addResourceRole(role);

        DBTestUtil.insertIntoResource_info_type_lu(1, "name");

        // Create a resource.
        Resource resource = DBTestUtil.createResource(10, 1, 1);
        persistence.addResource(resource);

        Date start = new Date();
        for (int i = 0; i < 20; i++) {
            this.persistence.deleteResource(resource);
        }

        double time = (new Date().getTime() - start.getTime()) / 1000.0;
        System.out.println("Calling deleteResource method 20 times, cost time: " + time + "s.");
    }

    /**
     * Benchmark the addNotification method.
     *
     * @throws Exception to junit.
     */
    public void testAddNotificationBenchmark() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(5);
        // first persist notificationType.
        persistence.addNotificationType(type);

        Date start = new Date();
        for (int i = 0; i < 20; i++) {
            persistence.addNotification(i + 1, 1, 5, "test");
        }
        double time = (new Date().getTime() - start.getTime()) / 1000.0;
        System.out.println("Calling addNotificationType method 20 times, cost time: " + time + "s.");
    }


    /**
     * Benchmark the loadNotification method.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotificationBenchmark() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(5);
        // first persist notificationType.
        persistence.addNotificationType(type);
        persistence.addNotification(1, 1, 5, "test");

        Date start = new Date();
        for (int i = 0; i < 20; i++) {
            Notification result = persistence.loadNotification(1, 1, 5);
            assertEquals("Incorrect notification id", 1, result.getExternalId());
        }
        double time = (new Date().getTime() - start.getTime()) / 1000.0;
        System.out.println("Calling loadNotification method 20 times, cost time: " + time + "s.");
    }
}


