/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.NotificationFilterBuilder;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests for <code>PersistenceResourceManager</code>.
 * <p>
 * This test case mainly focuses on the invalid configurations of search bundle.
 * </p>
 *
 * @author mayi
 * @author liuliquan
 * @version 1.1
 * @since 1.0
 */
public class PersistenceResourceManagerFailureTest_InvalidSearchBundle extends PersistenceResourceManagerBaseFailureTestCase {

    /**
     * The config file path for the DBConnectionFactory.
     */
    private static final String DB_CONNECTION_CONFIG = "failure" + File.separator + "ConnectionFactory.xml";

    /**
     * The config file path for the SearchBundleManager.
     */
    private static final String SEARCH_BUNDLE_MANAGER_CONFIG =
        "failure" + File.separator + "SearchBundleManager_InvalidSearchBundle.xml";

    /**
     * Set up. Prepare to test.
     *
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        super.setUp();

        executeSQL("/failure/prepare.sql");
    }

    /**
     * Tear down.
     *
     * @throws Exception to JUnit.
     */
    public void tearDown() throws Exception {
        executeSQL("/failure/clear.sql");
        super.tearDown();
    }

    /**
     * Load configurations used for this test case.
     *
     * @throws Exception to JUnit.
     */
    protected void loadConfigurations() throws Exception {
        clearAllConfigurations();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(DB_CONNECTION_CONFIG);
        cm.add(SEARCH_BUNDLE_MANAGER_CONFIG);
    }

    /**
     * Test <code>searchNotifications(Filter)</code> with invalid search bundle
     * configurations.
     * <p>SearchBuilderConfigurationException should be thrown.</p>.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchNotifications_InvalidSearchBundle() throws Exception {
        try {
            manager.searchNotifications(
                    NotificationFilterBuilder.createExternalRefIdFilter(1));
            fail("Should throw SearchBuilderConfigurationException.");
        } catch (SearchBuilderConfigurationException e) {
            // pass
        }
    }


    /**
     * Test <code>searchNotificationTypes(Filter)</code> with invalid search bundle
     * configurations.
     * <p>SearchBuilderConfigurationException should be thrown.</p>.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchNotificationTypes_InvalidSearchBundle() throws Exception {
        try {
            manager.searchNotificationTypes(
                    NotificationTypeFilterBuilder.createNameFilter("failuretests"));
            fail("Should throw SearchBuilderConfigurationException.");
        } catch (SearchBuilderConfigurationException e) {
            // pass
        }
    }


    /**
     * Test <code>searchResourceRoles(Filter)</code> with invalid search bundle
     * configurations.
     * <p>SearchBuilderConfigurationException should be thrown.</p>.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchResourceRoles_InvalidSearchBundle() throws Exception {
        try {
            manager.searchResourceRoles(
                    ResourceRoleFilterBuilder.createNameFilter("failuretests"));
            fail("Should throw SearchBuilderConfigurationException.");
        } catch (SearchBuilderConfigurationException e) {
            // pass
        }
    }


    /**
     * Test <code>searchResources(Filter)</code> with invalid search bundle
     * configurations.
     * <p>SearchBuilderConfigurationException should be thrown.</p>.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchResources_InvalidSearchBundle() throws Exception {
        try {
            manager.searchResources(
                    ResourceFilterBuilder.createPhaseIdFilter(1));
            fail("Should throw SearchBuilderConfigurationException.");
        } catch (SearchBuilderConfigurationException e) {
            // pass
        }
    }

    /**
     * Test <code>getAllResourceRoles()</code> with invalid search bundle
     * configurations.
     * <p>ResourcePersistenceException should be thrown.</p>.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllResourceRoles() throws Exception {
        try {
            manager.getAllResourceRoles();
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>getAllNotificationTypes()</code> with invalid search bundle
     * configurations.
     * <p>ResourcePersistenceException should be thrown.</p>.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllNotificationTypes() throws Exception {
        try {
            manager.getAllNotificationTypes();
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
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
        Connection connection = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").createConnection();
        InputStream is =
        	PersistenceResourceManagerFailureTest_InvalidSearchBundle.class.getResourceAsStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));;
        Statement statement = connection.createStatement();
        
        String command = "";
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0 || line.startsWith("--")) {
                    continue;
                }
                
                command += " " + line;
                if (command.endsWith(";")) {
                    statement.execute(command);
                    command = "";
                }
            }
        } finally {
            System.out.println(command);
            reader.close();
            statement.close();
            connection.close();
        }
    }
}
