/*
 * Copyright (C) 2006, 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.stresstests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.PersistenceResourceManager;
import com.topcoder.management.resource.search.NotificationFilterBuilder;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.LongValidator;
import com.topcoder.util.datavalidator.StringValidator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Stress test case of PersistenceResourceManagerTest class.<br>
 *
 * @author King_Bette, biotrail
 * @version 1.1
 *
 * @since 1.0
 */
public class PersistenceResourceManagerTest extends TestCase {
    /**
     * This instance is used in the test.
     */
    private ResourceManager resourceManager;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PersistenceResourceManagerTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
        ConfigManager.getInstance().add(new File("test_files/stress/config.xml").getAbsolutePath());
        ConfigManager.getInstance().add(new File("test_files/stress/ConnectionFactory.xml").getAbsolutePath());
        // get the search bundles
        SearchBundleManager searchBundleManager = new SearchBundleManager(
            "com.topcoder.search.builder.SearchBundleManager");
        Map fields = new HashMap();
        // set the resource filter fields
        fields.put(ResourceFilterBuilder.RESOURCE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.PHASE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.PROJECT_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.EXTENSION_PROPERTY_NAME_FIELD_NAME, StringValidator.startsWith(""));
        fields.put(ResourceFilterBuilder.EXTENSION_PROPERTY_VALUE_FIELD_NAME, StringValidator.startsWith(""));
        searchBundleManager.getSearchBundle(PersistenceResourceManager.RESOURCE_SEARCH_BUNDLE_NAME).setSearchableFields(
            fields);

        fields.clear();
        // set the resource role filter fields
        fields.put(ResourceRoleFilterBuilder.NAME_FIELD_NAME, StringValidator.startsWith(""));
        fields.put(ResourceRoleFilterBuilder.PHASE_TYPE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, LongValidator.isPositive());
        searchBundleManager.getSearchBundle(PersistenceResourceManager.RESOURCE_ROLE_SEARCH_BUNDLE_NAME).setSearchableFields(
            fields);

        fields.clear();
        // set the notification filter fields
        fields.put(NotificationFilterBuilder.EXTERNAL_REF_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(NotificationFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(NotificationFilterBuilder.PROJECT_ID_FIELD_NAME, LongValidator.isPositive());
        searchBundleManager.getSearchBundle(PersistenceResourceManager.NOTIFICATION_SEARCH_BUNDLE_NAME).setSearchableFields(
            fields);

        fields.clear();
        // set the notification type filter fields
        fields.put(NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(NotificationTypeFilterBuilder.NAME_FIELD_NAME, StringValidator.startsWith(""));
        searchBundleManager.getSearchBundle(PersistenceResourceManager.NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME).setSearchableFields(
            fields);

        executeSQL("test_files/stress/insert_records.sql");
        resourceManager = new PersistenceResourceManager(new MockResourcePersistence(), searchBundleManager);
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        executeSQL("test_files/stress/delete_records.sql");
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * search resource with a submissionIdfilter 100 times.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testSearchResources1() throws Exception {
        long startTime = System.currentTimeMillis();
        Filter filter = ResourceFilterBuilder.createSubmissionIdFilter(4);
        for (int i = 0; i < 100; i++) {
            Resource[] resources = resourceManager.searchResources(filter);
            assertEquals("length should be 1.", 1, resources.length);
            assertEquals("resource id should be 4", 4, resources[0].getId());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("search resource with a subbmissionIdFilter 100 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * search resource with a PhaseIdfilter 100 times.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testSearchResources2() throws Exception {
        long startTime = System.currentTimeMillis();
        Filter filter = ResourceFilterBuilder.createPhaseIdFilter(1);
        for (int i = 0; i < 100; i++) {
            Resource[] resources = resourceManager.searchResources(filter);
            assertEquals("length should be 0.", 0, resources.length);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("search resource with a PhaseIdfilter 100 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * search resource role with a nameFiter 100 times.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testSearchResourceRoles1() throws Exception {
        long startTime = System.currentTimeMillis();
        Filter filter = ResourceRoleFilterBuilder.createNameFilter("A");
        for (int i = 0; i < 100; i++) {
            ResourceRole[] resourceRoles = resourceManager.searchResourceRoles(filter);
            assertEquals("length should be 5.", 5, resourceRoles.length);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("search resource role with a nameFiter 100 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * search resource role with a PhaseTypeIdfilter 100 times.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testSearchResourceRoles2() throws Exception {
        long startTime = System.currentTimeMillis();
        Filter filter = ResourceRoleFilterBuilder.createPhaseTypeIdFilter(1);
        for (int i = 0; i < 100; i++) {
            ResourceRole[] resourceRoles = resourceManager.searchResourceRoles(filter);
            assertEquals("length should be 0.", 0, resourceRoles.length);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("search resource role with a PhaseTypeIdfilter 100 times takes " + (endTime - startTime)
            + "ms");
    }

    /**
     * search notification with a ProjectIdFilter 100 times.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testSearchNotifications1() throws Exception {
        long startTime = System.currentTimeMillis();
        Filter filter = NotificationFilterBuilder.createProjectIdFilter(1);
        for (int i = 0; i < 100; i++) {
            Notification[] notifications = resourceManager.searchNotifications(filter);
            assertEquals("length should be 1.", 1, notifications.length);
            assertEquals("projectId should be 1.", 1, notifications[0].getProject());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("search notification with a ProjectIdFilter 100 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * search notification with a NotificationTypeIdFilter 100 times.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testSearchNotifications2() throws Exception {
        long startTime = System.currentTimeMillis();
        Filter filter = NotificationFilterBuilder.createNotificationTypeIdFilter(1);
        for (int i = 0; i < 100; i++) {
            Notification[] notifications = resourceManager.searchNotifications(filter);
            assertEquals("length should be 1.", 1, notifications.length);
            assertEquals("NotificationTypeId should be 1.", 1, notifications[0].getNotificationType().getId());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("search notification with a NotificationTypeIdFilter 100 times takes "
            + (endTime - startTime) + "ms");
    }

    /**
     * search notification type with a NameFilter 100 times.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testSearchNotificationTypes1() throws Exception {
        long startTime = System.currentTimeMillis();
        Filter filter = NotificationTypeFilterBuilder.createNameFilter("A");
        for (int i = 0; i < 100; i++) {
            NotificationType[] notificationTypes = resourceManager.searchNotificationTypes(filter);
            assertEquals("length should be 5.", 5, notificationTypes.length);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("search NotificationType with a NameFilter 100 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * search notification type with a NotificationTypeIdFilter 100 times.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testSearchNotificationTypes2() throws Exception {
        long startTime = System.currentTimeMillis();
        Filter filter = NotificationTypeFilterBuilder.createNotificationTypeIdFilter(2);
        for (int i = 0; i < 100; i++) {
            NotificationType[] notificationTypes = resourceManager.searchNotificationTypes(filter);
            assertEquals("length should be 1.", 1, notificationTypes.length);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("search NotificationType with a NotificationTypeIdFilter 100 times takes "
            + (endTime - startTime) + "ms");
    }

    /**
     * Creates submissionId filter 10000 times.
     *
     * @since 1.1
     */
    public void testCreateAnySubmissionIdFilter() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            NullFilter filter = (NullFilter) ResourceFilterBuilder.createAnySubmissionIdFilter(new long[] {});
            assertEquals("Failed to create any submission id filter.", "submission_id", filter.getName());
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Create submissionId filter 10000 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file
     *            the file contains the sql statements.
     * @throws Exception
     *             pass to JUnit.
     */
    private void executeSQL(String file) throws Exception {
        // get db connection
        Connection connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory."
            + "DBConnectionFactoryImpl").createConnection();
        Statement statement = connection.createStatement();

        // get sql statements and add to statement
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                statement.addBatch(line);
            }
        }
        statement.executeBatch();
    }
}
