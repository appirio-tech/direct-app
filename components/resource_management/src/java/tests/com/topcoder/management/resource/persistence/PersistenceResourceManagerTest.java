/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.search.NotificationFilterBuilder;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.LongValidator;
import com.topcoder.util.datavalidator.OrValidator;
import com.topcoder.util.datavalidator.StringValidator;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>Unit tests for the class PersistenceResourceMnager.</p>
 *
 * <p>Changes in version 1.1, add one more test case for searching resource with
 * ResourceFilterBuilder#createAnySubmissionIdFilter() filter which is introduced in version 1.1.</p>
 *
 * @author kinfkong, Xuchen
 * @version 1.1
 * @since 1.0
 */
public class PersistenceResourceManagerTest extends TestCase {

    /**
     * The config file path for the DBConnectionFactory.
     */
    private static final String DB_CONNECTION_CONFIG = "test_files" + File.separator + "ConnectionFactory.xml";

    /**
     * The config file path for the SearchBundleManager.
     */
    private static final String SEARCH_BUNDLE_MANAGER = "test_files" + File.separator + "SearchBundleManager.xml";

    /**
     * The invalid config root path for failure tests.
     */
    private static final String INVALID_CONFIG_ROOT = "test_files" + File.separator + "invalid" + File.separator;

    /**
     * An instance of ResourcePersistence for tests.
     */
    private ResourcePersistence persistence;

    /**
     * An instance of SearchBundle for tests.
     */
    private SearchBundle resourceSearchBundle;

    /**
     * An instance of SearchBundle for tests.
     */
    private SearchBundle resourceRoleSearchBundle;

    /**
     * An instance of SearchBundle for tests.
     */
    private SearchBundle notificationSearchBundle;

    /**
     * An instance of SearchBundle for tests.
     */
    private SearchBundle notificationTypeSearchBundle;

    /**
     * An instance of IDGenrator for tests.
     */
    private IDGenerator resourceIdGenerator;

    /**
     * An instance of IDGenrator for tests.
     */
    private IDGenerator resourceRoleIdGenerator;

    /**
     * An instance of IDGenrator for tests.
     */
    private IDGenerator notificationTypeIdGenerator;

    /**
     * An instance of ConfigManager for tests.
     */
    private ConfigManager cm = ConfigManager.getInstance();

    /**
     * An instance of PersistenceResourceManager for tests.
     */
    private PersistenceResourceManager manager = null;

    /**
     * Sets up the environment, mainly initialize the private fields.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        loadNamespaces();

        // insert records
        removeTables();
        insertRecords();

        // set the persistence
        persistence = new MockResourcePersistenceImpl();

        // get the id generators
        resourceIdGenerator = IDGeneratorFactory.getIDGenerator(
                PersistenceResourceManager.RESOURCE_ID_GENERATOR_NAME);
        resourceRoleIdGenerator = IDGeneratorFactory.getIDGenerator(
                PersistenceResourceManager.RESOURCE_ROLE_ID_GENERATOR_NAME);
        notificationTypeIdGenerator = IDGeneratorFactory.getIDGenerator(
                PersistenceResourceManager.NOTIFICATION_TYPE_ID_GENERATOR_NAME);

        // get the search bundles
        SearchBundleManager searchBundleManager = new SearchBundleManager(
                "com.topcoder.search.builder.SearchBundleManager");


        resourceSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.RESOURCE_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(resourceSearchBundle);

        resourceRoleSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.RESOURCE_ROLE_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(resourceRoleSearchBundle);

        notificationSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(notificationSearchBundle);

        notificationTypeSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(notificationTypeSearchBundle);

        // initialize the PersistenceResourceManager
        manager = new PersistenceResourceManager(persistence, searchBundleManager);
    }

    /**
     * Clears the test environments.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        removeTables();
        clearNamespaces();
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundle,
     *                  SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     *
     * With the parameter: persistence is null, IllegalArgumentException should be thrown.
     */
    public void testPersistenceResourceManagerEigthArgs_NullPersistence() {
        try {
            new PersistenceResourceManager(null, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("persistence cannot be null");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundle,
     *                  SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     *
     * With the parameter: resourceSearchBundle is null, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testPersistenceResourceManagerEigthArgs_NullResourceSearchBundle() throws Exception {
        try {
            new PersistenceResourceManager(persistence, null,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("resourceSearchBundle cannot be null");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundle,
     *                  SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     *
     * With the parameter: resourceRoleSearchBundle is null, IllegalArgumentException should be thrown.
     */
    public void testPersistenceResourceManagerEigthArgs_NullResourceRoleSearchBundle() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    null, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("resourceRoleSearchBundle cannot be null");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundle,
     *                  SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     *
     * With the parameter: notificationSearchBundle is null, IllegalArgumentException should be thrown.
     */
    public void testPersistenceResourceManagerEigthArgs_NullNotificationSearchBundle() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, null,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("notificationSearchBundle cannot be null");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundle,
     *                  SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     *
     * With the parameter: notificationTypeSearchBundle is null, IllegalArgumentException should be thrown.
     */
    public void testPersistenceResourceManagerEigthArgs_NullNotificationTypeSearchBundle() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    null, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("notificationTypeSearchBundle cannot be null");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundle,
     *                  SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     *
     * With the parameter: resourceIdGenerator is null, IllegalArgumentException should be thrown.
     */
    public void testPersistenceResourceManagerEigthArgs_NullResourceIdGenerator() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, null,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("resourceIdGenerator cannot be null");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundle,
     *                  SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     *
     * With the parameter: resourceRoleIdGenerator is null, IllegalArgumentException should be thrown.
     */
    public void testPersistenceResourceManagerEigthArgs_NullResourceRoleIdGenerator() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    null, notificationTypeIdGenerator);
            fail("resourceRoleIdGenerator cannot be null");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundle,
     *                  SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     *
     * With the parameter: notificationTypeIdGenerator is null, IllegalArgumentException should be thrown.
     */
    public void testPersistenceResourceManagerEigthArgs_NullNotificationTypeIdGenerator() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, null);
            fail("notificationTypeIdGenerator cannot be null");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundle,
     *                  SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     *
     * Checks if all the fields are properly set.
     */
    public void testPersistenceResourceManagerEigthArgs_Accuracy() {
        new PersistenceResourceManager(persistence, resourceSearchBundle,
                resourceRoleSearchBundle, notificationSearchBundle,
                notificationTypeSearchBundle, resourceIdGenerator,
                resourceRoleIdGenerator, notificationTypeIdGenerator);

        // OK, no exception would be thrown.
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundleManager).
     *
     * With null persistence, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testPersistenceResourceManager_TwoArgs_NullPersistence() throws Exception {
        SearchBundleManager searchBundleManager = new SearchBundleManager(
            "com.topcoder.search.builder.SearchBundleManager");
        try {
            new PersistenceResourceManager(null, searchBundleManager);
            fail("Persistence cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundleManager).
     *
     * With null searchBundleManager, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testPersistenceResourceManager_TwoArgs_NullSearchBundleManager() throws Exception {
        try {
            new PersistenceResourceManager(persistence, null);
            fail("searchBundleManager cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundleManager).
     *
     * With resourceSearchBundle is not available, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testPersistenceResourceManager_TwoArgs_resourceSearchBundle_Unavailable() throws Exception {
        // load the invalid namespace
        cm.add(new File(INVALID_CONFIG_ROOT + "missing_resourceSearchBundle.xml").getCanonicalPath());
        // create the searchBundleManager
        SearchBundleManager searchBundleManager = new SearchBundleManager("com.topcoder.search.builder.invalid");
        try {
            new PersistenceResourceManager(persistence, searchBundleManager);
            fail("resourceSearchBundle is not available.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundleManager).
     *
     * With resourceRoleSearchBundle is not available, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testPersistenceResourceManager_TwoArgs_resourceRoleSearchBundle_Unavailable() throws Exception {
        // load the invalid namespace
        cm.add(new File(INVALID_CONFIG_ROOT + "missing_resourceRoleSearchBundle.xml").getCanonicalPath());
        // create the searchBundleManager
        SearchBundleManager searchBundleManager = new SearchBundleManager("com.topcoder.search.builder.invalid");
        try {
            new PersistenceResourceManager(persistence, searchBundleManager);
            fail("resourceRoleSearchBundle is not available.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundleManager).
     *
     * With notificationSearchBundle is not available, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testPersistenceResourceManager_TwoArgs_notificationSearchBundle_Unavailable() throws Exception {
        // load the invalid namespace
        cm.add(new File(INVALID_CONFIG_ROOT + "missing_notificationSearchBundle.xml").getCanonicalPath());
        // create the searchBundleManager
        SearchBundleManager searchBundleManager = new SearchBundleManager("com.topcoder.search.builder.invalid");
        try {
            new PersistenceResourceManager(persistence, searchBundleManager);
            fail("notificationSearchBundle is not available.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundleManager).
     *
     * With notificationTypeSearchBundle is not available, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testPersistenceResourceManager_TwoArgs_notificationTypeSearchBundle_Unavailable() throws Exception {
        // load the invalid namespace
        cm.add(new File(INVALID_CONFIG_ROOT + "missing_notificationTypeSearchBundle.xml").getCanonicalPath());
        // create the searchBundleManager
        SearchBundleManager searchBundleManager = new SearchBundleManager("com.topcoder.search.builder.invalid");
        try {
            new PersistenceResourceManager(persistence, searchBundleManager);
            fail("notificationTypeSearchBundle is not available.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor PersistenceResourceManager(ResourcePersistence, SearchBundleManager).
     *
     * All arguments are correct, checks if the instance is successfully constructed.
     *
     * @throws Exception to JUnit
     */
    public void testPersistenceResourceManager_TwoArgs_Accuracy() throws Exception {
        SearchBundleManager searchBundleManager = new SearchBundleManager(
                "com.topcoder.search.builder.SearchBundleManager");

        PersistenceResourceManager persistenceManager = new PersistenceResourceManager(
                persistence, searchBundleManager);

        assertNotNull("The persistence resource manager cannot be created.", persistenceManager);

    }

    /**
     * Tests method: updateResource(Resource, String).
     *
     * With null resource, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResource_NullResource() throws Exception {
        try {
            manager.updateResource(null, "DEVELOPER");
            fail("The resource cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResource(Resource, String).
     *
     * With null operator, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResource_NullOperator() throws Exception {
        try {
            manager.updateResource(getResource(1), null);
            fail("The operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResource(Resource, String).
     *
     * With invalid resourceRole - unset, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResource_UnsetResourceRole() throws Exception {
        try {
            manager.updateResource(new Resource(1), "developer");
            fail("The resourceRole must be set.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResource(Resource, String).
     *
     * With resourceRole with a phaseType but resource not with phase, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResource_IvalidResource() throws Exception {
        try {
            Resource resource = new Resource(1);
            // set the resourceRole with a phaseType
            resource.setResourceRole(new ResourceRole(1, "TEST", 1));
            manager.updateResource(new Resource(1), "developer");
            fail("The resource is invalid.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResource(Resource, String).
     *
     * With a new resource (id unset).
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResource_NewResource() throws Exception {
        clearTableAfter("resource");
        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole(2));
        // update it
        manager.updateResource(resource, "DEVELOPER_UPDATE_RESOURCE");

        // checks exist record
        String sql = "SELECT resource_id FROM resource WHERE resource_role_id = ?";
        assertTrue("The record does not added.", existsRecord(sql, new Object[] {new Long(2)}));
        CustomResultSet rs = doSQLQuery(sql, new Object[] {new Long(2)});
        rs.next();
        long id = rs.getInt(1);
        assertEquals("The id is not set back to the resource.", id, resource.getId());
    }

    /**
     * Tests method: updateResource(Resource, String).
     *
     * With an already exist record, update it.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResource_UpdateRecord() throws Exception {
        Resource resource = getResource(2);
        // update it
        manager.updateResource(resource, "DEVELOPER_UPDATE_RESOURCE");

        // checks exist record
        String sql = "SELECT * FROM resource WHERE resource_id = ? AND modify_user = ?";

        assertTrue("The record has not been updated.",
                existsRecord(sql, new Object[] {new Long(2), "DEVELOPER_UPDATE_RESOURCE"}));
    }


    /**
     * Tests method removeResource(Resource, String).
     *
     * With null resource, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveResource_NullResource() throws Exception {
        try {
            manager.removeResource(null, "developer");
            fail("resource cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method removeResource(Resource, String).
     *
     * With null operator, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveResource_NullOperator() throws Exception {
        try {
            manager.removeResource(new Resource(1), null);
            fail("operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method removeResource(Resource, String).
     *
     * With resource id unset, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveResource_UnSetId() throws Exception {
        try {
            manager.removeResource(new Resource(), "DEVELOPER");
            fail("operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method removeResource(Resource, String).
     *
     * Checks if it successfully remove a resource
     *
     * @throws Exception to JUnit
     */
    public void testRemoveResource_remove() throws Exception {
        // delete all the reference tables
        clearTableAfter("resource_info_type_lu");

        Resource resource = new Resource(2);

        // the original record exists
        String sql = "SELECT * FROM resource WHERE resource_id = 2";
        assertTrue("The record exists.", existsRecord(sql, new Object[] {}));

        // remove it
        manager.removeResource(resource, "TEST_DEVELOPER");

        // the record does not exist
        assertFalse("The record does not exists.", existsRecord(sql, new Object[] {}));

        // the modification user is set back to the resource
        assertEquals("The modification user does not set back.", "TEST_DEVELOPER", resource.getModificationUser());
    }

    /**
     * Tests method: updateResources(Resource[], long, String).
     *
     * With null resources, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResources_NullResources() throws Exception {
        try {
            manager.updateResources(null, 1, "DEVELOPER");
            fail("Resources cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResources(Resource[], long, String).
     *
     * With null operator, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResources_NullOperator() throws Exception {
        Resource[] resources = new Resource[10];
        for (int i = 0; i < resources.length; i++) {
            resources[i] = new Resource(i + 1);
            resources[i].setResourceRole(new ResourceRole(i + 20));
            resources[i].setProject(new Long(1));
        }
        try {
            manager.updateResources(resources, 1, null);
            fail("operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResources(Resource[], long, String).
     *
     * With resources contains null, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResources_NullInResources() throws Exception {
        Resource[] resources = new Resource[11];
        for (int i = 0; i < resources.length - 1; i++) {
            resources[i] = new Resource(i + 1);
            resources[i].setResourceRole(new ResourceRole(i + 20));
            resources[i].setProject(new Long(1));
        }
        resources[resources.length - 1] = null;
        try {
            manager.updateResources(resources, 1, "DEVELOPER");
            fail("Resources cannot contain null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResources(Resource[], long, String).
     *
     * With non-positive project id, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResources_NonPositiveProjectId() throws Exception {
        Resource[] resources = new Resource[10];
        for (int i = 0; i < resources.length; i++) {
            resources[i] = new Resource(i + 1);
            resources[i].setResourceRole(new ResourceRole(i + 20));
            resources[i].setProject(new Long(1));
        }
        try {
            manager.updateResources(resources, -11, "DEVELOPER");
            fail("project id should be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResources(Resource[], long, String).
     *
     * With a project id in resources not the same as the argument project, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResources_ProjectIdNotTheSame() throws Exception {
        Resource[] resources = new Resource[10];
        for (int i = 0; i < resources.length; i++) {
            resources[i] = new Resource(i + 1);
            resources[i].setResourceRole(new ResourceRole(i + 20));
            resources[i].setProject(new Long(1));
        }

        resources[5].setProject(new Long(20));

        try {
            manager.updateResources(resources, 1, "DEVELOPER");
            fail("project id not the same.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResource(Resource[], long, String).
     *
     * Tests the accuracy of updateResource with some resource are added, some are changed and some are removed.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResources_accuracy() throws Exception {
        // make sure the constraints ok
        clearTableAfter("resource_info_type_lu");

        // create the new resources of 1, 4, 6 and a unset id
        Resource[] resources = new Resource[] {
            new Resource(1),
            new Resource(4),
            new Resource(6),
            new Resource()
        };

        String sql = null;

        resources[0].setResourceRole(new ResourceRole(1));
        resources[1].setResourceRole(new ResourceRole(1));
        resources[2].setResourceRole(new ResourceRole(2));
        resources[3].setResourceRole(new ResourceRole(3));

        resources[0].setProject(new Long(1));
        resources[1].setProject(new Long(1));
        resources[2].setProject(new Long(1));
        resources[3].setProject(new Long(1));

        // the original resources of project 1 should be {1, 4, 7}
        // so, resource 7 would be removed, 1 and 4 would be updated, 6 would be insert and a new one is update.
        manager.updateResources(resources, 1, "DEVELOPER_UPDATE_RESOURCES");

        // resource 7 removed
        sql = "SELECT * FROM resource WHERE resource_id = ?";
        assertFalse("Resource 7 should be removed.", existsRecord(sql, new Object[] {new Long(7)}));

        // 1 and 4 are updated
        sql = "SELECT * FROM resource WHERE resource_id = ? AND modify_user = 'DEVELOPER_UPDATE_RESOURCES'";
        assertTrue("Resource 1 should be removed.", existsRecord(sql, new Object[] {new Long(1)}));
        assertTrue("Resource 4 should be removed.", existsRecord(sql, new Object[] {new Long(4)}));
        assertTrue("Resource 6 should be removed.", existsRecord(sql, new Object[] {new Long(6)}));

        sql = "SELECT * FROM resource WHERE create_user = 'DEVELOPER_UPDATE_RESOURCES'";
        assertTrue("a new resource is inserted.", existsRecord(sql, new Object[] {}));
    }

    /**
     * Tests method: getResource(long).
     *
     * With non-positive id, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetResource_NonPositiveId() throws Exception {
        try {
            manager.getResource(-1);
            fail("The id must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: getResource(long).
     *
     * Checks if the resource is successfully returned.
     *
     * @throws Exception to JUnit
     */
    public void testGetResource_accuracy() throws Exception {
        Resource resource = manager.getResource(5);
        assertEquals("The method: getResource does not work properly.", 5, resource.getId());
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With a null filter, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_NullFilter() throws Exception {
        try {
            manager.searchResources(null);
            fail("The filter cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With a wrong configuration of search bundle, SearchBuilderConfigurationException should be thrown.
     *
     * The selected column is not an integer / long.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_WrongConfig_NotInteger() throws Exception {
        File file = new File(INVALID_CONFIG_ROOT + "wrong_resourceSearchBundle_notInteger.xml");
        cm.add(file.getCanonicalPath());

        SearchBundleManager searchBundleManager1 = new SearchBundleManager(
            "com.topcoder.search.builder.SearchBundleManager.NotInteger");

        manager = new PersistenceResourceManager(persistence, searchBundleManager1);

        setAllFieldsSearchable(searchBundleManager1.getSearchBundle(
                PersistenceResourceManager.RESOURCE_SEARCH_BUNDLE_NAME));

        Filter filter = ResourceFilterBuilder.createProjectIdFilter(1);
        try {
            manager.searchResources(filter);
            fail("The selected column is not resource_id.");
        } catch (SearchBuilderConfigurationException e) {
            // success
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With a wrong configuration of search bundle, SearchBuilderConfigurationException should be thrown.
     *
     * The context of the search bundle is an invalid sql.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_WrongConfig_InvalidSQL() throws Exception {
        File file = new File(INVALID_CONFIG_ROOT + "wrong_resourceSearchBundle_invalidSql.xml");
        cm.add(file.getCanonicalPath());

        SearchBundleManager searchBundleManager1 = new SearchBundleManager(
            "com.topcoder.search.builder.SearchBundleManager.InvalidSQL");

        manager = new PersistenceResourceManager(persistence, searchBundleManager1);

        setAllFieldsSearchable(searchBundleManager1.getSearchBundle(
                PersistenceResourceManager.RESOURCE_SEARCH_BUNDLE_NAME));

        Filter filter = ResourceFilterBuilder.createProjectIdFilter(1);
        try {
            manager.searchResources(filter);
            fail("The sql statement is invalid.");
        } catch (SearchBuilderException e) {
            // success
        }
    }


    /**
     * Tests method: searchResources(Filter).
     *
     * With ProjectIdFilter.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_ProjectID() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createProjectIdFilter(1L);

        Resource[] resources = manager.searchResources(filter);

        long[] expected = new long[] {1, 4, 7};

        // check the accuracy of the returned result

        assertEquals("Should contain 3 elements.", 3, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With SubmissionIdFilter
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_SubmissionIdFilter() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createSubmissionIdFilter(1L);

        Resource[] resources = manager.searchResources(filter);

        long[] expected = new long[] {1, 2, 3};

        // check the accuracy of the returned result

        assertEquals("Should contain 3 elements.", 3, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With ResourceFilterBuilder#createAnySubmissionIdFilter() method returned InFilter.
     *
     * @throws Exception to JUnit
     * @since 1.1
     */
    public void testSearchResources_AnySubmissionIdFilter_NonEmptyArray() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createAnySubmissionIdFilter(new long[]{1, 2, 3, 3, 2, 1});

        Resource[] resources = manager.searchResources(filter);

        long[] expected = new long[] {1, 2, 3};

        // check the accuracy of the returned result

        assertEquals("Should contain 3 elements.", 3, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With ResourceFilterBuilder#createAnySubmissionIdFilter() method returned NullFilter.
     *
     * @throws Exception to JUnit
     * @since 1.1
     */
    public void testSearchResources_AnySubmissionIdFilter_EmptyArray() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createAnySubmissionIdFilter(new long[0]);

        Resource[] resources = manager.searchResources(filter);
        long[] expected = new long[] {4, 5, 6, 7, 8, 9};

        // check the accuracy of the returned result
        assertEquals("Should contain 6 elements.", 6, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With ResourceIdFilter
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_ResourceIdFilter() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createResourceIdFilter(1L);

        Resource[] resources = manager.searchResources(filter);

        long[] expected = new long[] {1};

        // check the accuracy of the returned result

        assertEquals("Should contain 1 elements.", 1, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With ResourceIdFilter and no return result
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_ResourceIdFilter_empty() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createResourceIdFilter(100L);

        Resource[] resources = manager.searchResources(filter);

        // not exist
        long[] expected = new long[] {};

        // check the accuracy of the returned result

        assertEquals("Should contain no elements.", 0, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With ResourceRoleIdFilter and no return result
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_ResourceRoleIdFilter() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createResourceRoleIdFilter(2L);

        Resource[] resources = manager.searchResources(filter);

        // not exist
        long[] expected = new long[] {4, 5, 6};

        // check the accuracy of the returned result

        assertEquals("Should contain 3 elements.", 3, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            // System.out.println(resources[i].getId());
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With PhaseIdFilter and no return result.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_PhaseIdFilter() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createPhaseIdFilter(2L);

        Resource[] resources = manager.searchResources(filter);

        // not exist
        long[] expected = new long[] {};

        // check the accuracy of the returned result

        assertEquals("Should contain 0 elements.", 0, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With ExtensionPropertyNameFilter.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_ExtensionPropertyNameFilter() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createExtensionPropertyNameFilter("resource info type 1");

        Resource[] resources = manager.searchResources(filter);

        // not exist
        long[] expected = new long[] {1, 2, 3};

        // check the accuracy of the returned result

        assertEquals("Should contain 3 elements.", 3, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            // System.out.println(resources[i].getId());
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResources(Filter).
     *
     * With ExtensionPropertyValueFilter.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResources_ExtensionPropertyValueFilter() throws Exception {
        // creates the filter
        Filter filter = ResourceFilterBuilder.createExtensionPropertyValueFilter("value 2");

        Resource[] resources = manager.searchResources(filter);

        // not exist
        long[] expected = new long[] {2};

        // check the accuracy of the returned result

        assertEquals("Should contain 1 elements.", 1, resources.length);

        for (int i = 0; i < resources.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resources[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resource id " + resources[i].getId() + " not found.");
            }
        }
    }



    /**
     * Tests method: updateResourceRole(ResourceRole, String).
     *
     * With null resource, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResourceRole_NullResourceRole() throws Exception {
        try {
            manager.updateResourceRole(null, "DEVELOPER");
            fail("The resourceRole cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResourceRole(ResourceRole, String).
     *
     * With null operator, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResourceRole_NullOperator() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1);
        resourceRole.setDescription("test");
        resourceRole.setName("test");
        try {
            manager.updateResourceRole(resourceRole, null);
            fail("The operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResourceRole(ResourceRole, String).
     *
     * With invalid resourceRole - unset name, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResourceRole_UnsetResourceRoleRole() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1);
        resourceRole.setDescription("test");
        // resourceRole.setName("test");
        try {
            manager.updateResourceRole(resourceRole, "developer");
            fail("The resourceRole must be set.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResourceRole(ResourceRole, String).
     *
     * Invalid ResourceRole - unset description, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResourceRole_IvalidResourceRole() throws Exception {
        ResourceRole resourceRole = new ResourceRole(1);
        // resourceRole.setDescription("test");
        resourceRole.setName("test");
        try {
            manager.updateResourceRole(resourceRole, "developer");
            fail("The resourceRole is invalid.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateResourceRole(ResourceRole, String).
     *
     * With a new resourceRoles (id unset).
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResourceRole_NewResourceRole() throws Exception {
        clearTableAfter("resource_role_lu");
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setName("test");
        resourceRole.setDescription("test");

        // update it
        manager.updateResourceRole(resourceRole, "DEVELOPER_UPDATE_RESOURCE");

        // checks exist record
        String sql = "SELECT resource_role_id FROM resource_role_lu WHERE "
            + "create_user = ? AND modify_user = ? AND name = 'test'";
        assertTrue("The record does not added.", existsRecord(sql, new Object[] {
            "DEVELOPER_UPDATE_RESOURCE", "DEVELOPER_UPDATE_RESOURCE"}));
        CustomResultSet rs = doSQLQuery(sql, new Object[] {
            "DEVELOPER_UPDATE_RESOURCE", "DEVELOPER_UPDATE_RESOURCE"});
        rs.next();
        long id = rs.getInt(1);
        assertEquals("The id is not set back to the resourceRole.", id, resourceRole.getId());
    }

    /**
     * Tests method: updateResourceRole(ResourceRole, String).
     *
     * With an already exist record, update it.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateResourceRole_UpdateRecord() throws Exception {
        ResourceRole resourceRole = new ResourceRole(2);
        resourceRole.setName("test");
        resourceRole.setDescription("test");
        // update it
        manager.updateResourceRole(resourceRole, "DEVELOPER_UPDATE_RESOURCE");

        // checks exist record
        String sql = "SELECT * FROM resource_role_lu WHERE resource_role_id = ? AND modify_user = ?";

        assertTrue("The record has not been updated.",
                existsRecord(sql, new Object[] {new Long(2), "DEVELOPER_UPDATE_RESOURCE"}));
    }

    /**
     * Tests method removeResourceRole(ResourceRole, String).
     *
     * With null resourceRole, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveResourceRole_NullResourceRole() throws Exception {
        try {
            manager.removeResourceRole(null, "developer");
            fail("resourceRole cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method removeResourceRole(ResourceRole, String).
     *
     * With null operator, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveResourceRole_NullOperator() throws Exception {
        try {
            manager.removeResourceRole(new ResourceRole(1), null);
            fail("operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method removeResourceRole(ResourceRole, String).
     *
     * With resourceRole id unset, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveResourceRole_UnSetId() throws Exception {
        try {
            manager.removeResourceRole(new ResourceRole(), "DEVELOPER");
            fail("operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method removeResourceRole(ResourceRole, String).
     *
     * Checks if it successfully remove a resourceRole
     *
     * @throws Exception to JUnit
     */
    public void testRemoveResourceRole_remove() throws Exception {
        // delete all the reference tables
        clearTableAfter("resource");

        ResourceRole resourceRole = new ResourceRole(2);

        // the original record exists
        String sql = "SELECT * FROM resource_role_lu WHERE resource_role_id = 2";
        assertTrue("The record exists.", existsRecord(sql, new Object[] {}));

        // remove it
        manager.removeResourceRole(resourceRole, "TEST_DEVELOPER");

        // the record does not exist
        assertFalse("The record does not exists.", existsRecord(sql, new Object[] {}));

        // the modification user is set back to the resourceRoleRole
        assertEquals("The modification user does not set back.", "TEST_DEVELOPER", resourceRole.getModificationUser());
    }

    /**
     * Tests method: getAllResourceRoles().
     *
     * Checks if all the resourceRoles are returned.
     *
     * @throws Exception to JUnit
     */
    public void testGetAllResourceRoles() throws Exception {
        // get all
        ResourceRole[] resourceRoles = manager.getAllResourceRoles();

        long[] expected = new long[] {1, 2, 3};

        // check the accuracy of the returned result

        assertEquals("Should contain 3 elements.", 3, resourceRoles.length);

        for (int i = 0; i < resourceRoles.length; i++) {
            int j;
            // System.out.println(resourceRoles[i].getId());
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resourceRoles[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resourceRole id " + resourceRoles[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResourceRoles(Filter).
     *
     * With a null filter, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResourceRoles_NullFilter() throws Exception {
        try {
            manager.searchResourceRoles(null);
            fail("The filter cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: searchResourceRoles(Filter).
     *
     * With a wrong configuration of search bundle, SearchBuilderConfigurationException should be thrown.
     *
     * The selected column is not an integer / long.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResourceRoles_WrongConfig_NotInteger() throws Exception {
        File file = new File(INVALID_CONFIG_ROOT + "wrong_resourceRoleSearchBundle_notInteger.xml");
        cm.add(file.getCanonicalPath());

        SearchBundleManager searchBundleManager1 = new SearchBundleManager(
            "com.topcoder.search.builder.SearchBundleManager.NotInteger");

        setAllFieldsSearchable(searchBundleManager1.getSearchBundle(
                PersistenceResourceManager.RESOURCE_ROLE_SEARCH_BUNDLE_NAME));

        manager = new PersistenceResourceManager(persistence, searchBundleManager1);

        Filter filter = ResourceRoleFilterBuilder.createPhaseTypeIdFilter(1);
        try {
            manager.searchResourceRoles(filter);
            fail("The selected column is not resourceRole_id.");
        } catch (SearchBuilderConfigurationException e) {
            // success
        }
    }

    /**
     * Tests method: searchResourceRoles(Filter).
     *
     * With ResourceRoleIdFilter.
     *
     * @throws Exception to JUnit
     */
    public void testSearchResourceRoles_ResourceRoleIdFilter() throws Exception {
        // creates the filter
        Filter filter = ResourceRoleFilterBuilder.createResourceRoleIdFilter(1L);

        ResourceRole[] resourceRoles = manager.searchResourceRoles(filter);

        long[] expected = new long[] {1};

        // check the accuracy of the returned result

        assertEquals("Should contain 1 elements.", 1, resourceRoles.length);

        for (int i = 0; i < resourceRoles.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resourceRoles[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resourceRole id " + resourceRoles[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResourceRoles(Filter).
     *
     * With NameFilter
     *
     * @throws Exception to JUnit
     */
    public void testSearchResourceRoles_NameFilter() throws Exception {
        // creates the filter
        Filter filter = ResourceRoleFilterBuilder.createNameFilter("role 2");

        ResourceRole[] resourceRoles = manager.searchResourceRoles(filter);

        long[] expected = new long[] {2};

        // check the accuracy of the returned result

        assertEquals("Should contain 1 elements.", 1, resourceRoles.length);

        for (int i = 0; i < resourceRoles.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resourceRoles[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resourceRole id " + resourceRoles[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResourceRoles(Filter).
     *
     * With ResourceRoleIdFilter and no return result
     *
     * @throws Exception to JUnit
     */
    public void testSearchResourceRoles_ResourceRoleIdFilter_empty() throws Exception {
        // creates the filter
        Filter filter = ResourceRoleFilterBuilder.createResourceRoleIdFilter(100L);

        ResourceRole[] resourceRoles = manager.searchResourceRoles(filter);

        // not exist
        long[] expected = new long[] {};

        // check the accuracy of the returned result

        assertEquals("Should contain no elements.", 0, resourceRoles.length);

        for (int i = 0; i < resourceRoles.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resourceRoles[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resourceRole id " + resourceRoles[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchResourceRoles(Filter).
     *
     * With PhaseTypeIdFilter and no return result
     *
     * @throws Exception to JUnit
     */
    public void testSearchResourceRoles_ResourcePhaseTypeIdFilter() throws Exception {
        // creates the filter
        Filter filter = ResourceRoleFilterBuilder.createPhaseTypeIdFilter(3L);

        ResourceRole[] resourceRoles = manager.searchResourceRoles(filter);

        // not exist
        long[] expected = new long[] {3};

        // check the accuracy of the returned result

        assertEquals("Should contain 1 elements.", 1, resourceRoles.length);

        for (int i = 0; i < resourceRoles.length; i++) {
            int j;
            // System.out.println(resourceRoles[i].getId());
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == resourceRoles[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The resourceRole id " + resourceRoles[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests addNotifications(long[], long, long, String).
     *
     * With null users, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testAddNotifications_NullUsers() throws Exception {
        try {
            manager.addNotifications(null, 1, 2, "DEVELOPER_ADD_NOTIFICATION");
            fail("Users can not be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests addNotifications(long[], long, long, String).
     *
     * With null operator, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testAddNotifications_NullOperator() throws Exception {
        try {
            manager.addNotifications(new long[] {1, 2, 3}, 1, 2, null);
            fail("operator can not be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests addNotifications(long[], long, long, String).
     *
     * Checks if the notifications are properly set.
     *
     * @throws Exception to JUnit
     */
    public void testAddNotifications_Accuracy() throws Exception {
        long users[] = new long[] {1, 2, 3};
        long project = 2;
        long notificationType = 3;

        String operator = "DEVELOPER_ADD_NOTIFICATIONS";
        // remove the table first
        clearTableAfter("notification");

        manager.addNotifications(users, project, notificationType, operator);

        // checks if the record exists
        for (int i = 0; i < users.length; i++) {
            String sql = "SELECT * FROM notification WHERE "
                + "external_ref_id = ? AND notification_type_id = ? AND project_id = ? AND create_user = ?";
            assertTrue("The record is not added.", existsRecord(sql, new Object[] {
                new Long(users[i]),
                new Long(notificationType),
                new Long(project),
                operator
            }));
        }
    }

    /**
     * Tests addNotifications(long[], long, long, String).
     *
     * With non-positive project id, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testAddNotifications_NonPostiveProject() throws Exception {
        try {
            manager.addNotifications(new long[] {1, 2, 3}, -1, 2, null);
            fail("project id must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests addNotifications(long[], long, long, String).
     *
     * With non-positive notificationType, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testAddNotifications_NonPostiveNotificationType() throws Exception {
        try {
            manager.addNotifications(new long[] {1, 2, 3}, 1, -2, null);
            fail("notificationType must be positive");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests addNotifications(long[], long, long, String).
     *
     * With non-positive user ids, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testAddNotifications_NonPostiveUserIds() throws Exception {
        try {
            manager.addNotifications(new long[] {1, -2, 3}, 1, 2, null);
            fail("User ids cannot contain non-positive id.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests removeNotifications(long[], long, long, String).
     *
     * With null users, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotifications_NullUsers() throws Exception {
        try {
            manager.removeNotifications(null, 1, 2, "DEVELOPER_ADD_NOTIFICATION");
            fail("Users can not be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests removeNotifications(long[], long, long, String).
     *
     * With null operator, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotifications_NullOperator() throws Exception {
        try {
            manager.removeNotifications(new long[] {1, 2, 3}, 1, 2, null);
            fail("operator can not be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests removeNotifications(long[], long, long, String).
     *
     * Checks if the notifications are properly set.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotifications_Accuracy() throws Exception {
        long users[] = new long[] {1, 2, 3};
        long project = 2;
        long notificationType = 3;

        String operator = "DEVELOPER_ADD_NOTIFICATIONS";
        // remove the table first
        clearTableAfter("notification");

        // insert the notifications
        manager.addNotifications(users, project, notificationType, operator);

        // remove them
        manager.removeNotifications(users, project, notificationType, operator);

        // checks if the record exists
        for (int i = 0; i < users.length; i++) {
            String sql = "SELECT * FROM notification WHERE "
                + "external_ref_id = ? AND notification_type_id = ? AND project_id = ? AND create_user = ?";
            // the record does not exists.
            assertFalse("The record is not removed.", existsRecord(sql, new Object[] {
                new Long(users[i]),
                new Long(notificationType),
                new Long(project),
                operator
            }));
        }
    }

    /**
     * Tests removeNotifications(long[], long, long, String).
     *
     * With non-positive project id, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotifications_NonPostiveProject() throws Exception {
        try {
            manager.removeNotifications(new long[] {1, 2, 3}, -1, 2, null);
            fail("project id must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests removeNotifications(long[], long, long, String).
     *
     * With non-positive notificationType, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotifications_NonPostiveNotificationType() throws Exception {
        try {
            manager.removeNotifications(new long[] {1, 2, 3}, 1, -2, null);
            fail("notificationType must be positive");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests removeNotifications(long[], long, long, String).
     *
     * With non-positive user ids, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotifications_NonPostiveUserIds() throws Exception {
        try {
            manager.removeNotifications(new long[] {1, -2, 3}, 1, 2, null);
            fail("User ids cannot contain non-positive id.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: getNotifications(long, long).
     *
     * With non-positive project id, IlelgalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetNotifications_NonPositiveProjectId() throws Exception {
        try {
            manager.getNotifications(-1, 2);
            fail("The project id must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: getNotifications(long, long).
     *
     * With non-positive notification type id, IlelgalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetNotifications_NonPositiveNotificationTypeId() throws Exception {
        try {
            manager.getNotifications(1, -2);
            fail("The notification type id must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: getNotifications(long, long).
     *
     * Checks if the notifications are properly returned.
     *
     * @throws Exception to JUnit
     */
    public void testGetNotifications_Accuracy() throws Exception {
        long[] notifications = manager.getNotifications(1, 2);
        long[] expected = new long [] {1, 2, 3};

        // assert the returned number
        assertEquals("The elements number is incorrect.", expected.length, notifications.length);

        for (int i = 0; i < notifications.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == notifications[i]) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The notification id " + notifications[i] + " not found.");
            }
        }

    }

    /**
     * Tests method: searchNotifications(Filter).
     *
     * With a null filter, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotifications_NullFilter() throws Exception {
        try {
            manager.searchNotifications(null);
            fail("The filter cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: searchNotifications(Filter).
     *
     * With a wrong configuration of search bundle, SearchBuilderConfigurationException should be thrown.
     *
     * The selected column is not an integer / long.
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotifications_WrongConfig_LessThan3() throws Exception {
        File file = new File(INVALID_CONFIG_ROOT + "wrong_notificationSearchBundle_lessThan3Column.xml");
        cm.add(file.getCanonicalPath());

        SearchBundleManager searchBundleManager1 = new SearchBundleManager(
            "com.topcoder.search.builder.SearchBundleManager.lessThanThree");

        manager = new PersistenceResourceManager(persistence, searchBundleManager1);
        setAllFieldsSearchable(searchBundleManager1.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_SEARCH_BUNDLE_NAME));

        Filter filter = NotificationFilterBuilder.createProjectIdFilter(1);
        try {
            manager.searchNotifications(filter);
            fail("The selected columns should of number 3.");
        } catch (SearchBuilderConfigurationException e) {
            // success
        }
    }

    /**
     * Tests method: searchNotifications(Filter).
     *
     * With NotificationTypeIdFilter.
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotifications_NotificationTypeIdFilter() throws Exception {
        // creates the filter
        Filter filter = NotificationFilterBuilder.createNotificationTypeIdFilter(1L);

        Notification[] notifications = manager.searchNotifications(filter);

        Notification[] expected = new Notification[] {
            getNotification(1, 1, 1),
            getNotification(1, 2, 1),
            getNotification(1, 3, 1),
            getNotification(2, 1, 1),
            getNotification(2, 2, 1),
            getNotification(2, 3, 1),
            getNotification(3, 1, 1),
            getNotification(3, 2, 1),
            getNotification(3, 3, 1),
        };

        // check the accuracy of the returned result

        assertEquals("Should contain 9 elements.", 9, notifications.length);

        for (int i = 0; i < notifications.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (checkNotificationEquals(expected[j], notifications[i])) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("notification in location : " + i + " not found.");
            }
        }
    }


    /**
     * Tests method: searchNotifications(Filter).
     *
     * With ProjectIdFilter.
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotifications_ProjectIdFilter() throws Exception {
        // creates the filter
        Filter filter = NotificationFilterBuilder.createProjectIdFilter(1L);

        Notification[] notifications = manager.searchNotifications(filter);

        Notification[] expected = new Notification[] {
            getNotification(1, 1, 1),
            getNotification(1, 2, 1),
            getNotification(1, 3, 1),
            getNotification(1, 1, 2),
            getNotification(1, 2, 2),
            getNotification(1, 3, 2),
            getNotification(1, 1, 3),
            getNotification(1, 2, 3),
            getNotification(1, 3, 3),
        };

        // check the accuracy of the returned result

        assertEquals("Should contain 9 elements.", 9, notifications.length);

        for (int i = 0; i < notifications.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (checkNotificationEquals(expected[j], notifications[i])) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("notification in location : " + i + " not found.");
            }
        }
    }


    /**
     * Tests method: searchNotifications(Filter).
     *
     * With ExternalRefIdFilter.
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotifications_ExternalRefIdFilter() throws Exception {
        // creates the filter
        Filter filter = NotificationFilterBuilder.createExternalRefIdFilter(3L);

        Notification[] notifications = manager.searchNotifications(filter);

        Notification[] expected = new Notification[] {
            getNotification(1, 3, 1),
            getNotification(1, 3, 2),
            getNotification(1, 3, 3),
            getNotification(2, 3, 1),
            getNotification(2, 3, 2),
            getNotification(2, 3, 3),
            getNotification(3, 3, 1),
            getNotification(3, 3, 2),
            getNotification(3, 3, 3),
        };

        // check the accuracy of the returned result

        assertEquals("Should contain 9 elements.", 9, notifications.length);

        for (int i = 0; i < notifications.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (checkNotificationEquals(expected[j], notifications[i])) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("notification in location : " + i + " not found.");
            }
        }
    }

    /**
     * Tests method: updateNotificationType(NotificationType, String).
     *
     * With null notificationType, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNotificationType_NullNotificationType() throws Exception {
        try {
            manager.updateNotificationType(null, "DEVELOPER");
            fail("The notificationType cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateNotificationType(NotificationType, String).
     *
     * With null operator, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNotificationType_NullOperator() throws Exception {
        NotificationType notificationType = new NotificationType(1);
        notificationType.setName("test");
        notificationType.setDescription("test");
        try {
            manager.updateNotificationType(notificationType, null);
            fail("The operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateNotificationType(NotificationType, String).
     *
     * With invalid notificationType - unset name, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNotificationType_UnsetNotificationTypeRole() throws Exception {
        NotificationType notificationType = new NotificationType(1);
        notificationType.setDescription("test");
        // notificationType.setName("test");
        try {
            manager.updateNotificationType(notificationType, "developer");
            fail("The notificationType must be set.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateNotificationType(NotificationType, String).
     *
     * Invalid NotificationType - unset description, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNotificationType_IvalidNotificationType() throws Exception {
        NotificationType notificationType = new NotificationType(1);
        // notificationType.setDescription("test");
        notificationType.setName("test");
        try {
            manager.updateNotificationType(notificationType, "developer");
            fail("The notificationType is invalid.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: updateNotificationType(NotificationType, String).
     *
     * With a new notificationTypes (id unset).
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNotificationType_NewNotificationType() throws Exception {
        clearTableAfter("notification_type_lu");
        NotificationType notificationType = new NotificationType();
        notificationType.setName("test");
        notificationType.setDescription("test");

        // update it
        manager.updateNotificationType(notificationType, "DEVELOPER_UPDATE_RESOURCE");

        // checks exist record
        String sql = "SELECT notification_type_id FROM notification_type_lu WHERE "
            + "create_user = ? AND modify_user = ? AND name = 'test'";
        assertTrue("The record does not added.", existsRecord(sql, new Object[] {
            "DEVELOPER_UPDATE_RESOURCE", "DEVELOPER_UPDATE_RESOURCE"}));
        CustomResultSet rs = doSQLQuery(sql, new Object[] {
            "DEVELOPER_UPDATE_RESOURCE", "DEVELOPER_UPDATE_RESOURCE"});
        rs.next();
        long id = rs.getInt(1);
        assertEquals("The id is not set back to the notificationType.", id, notificationType.getId());
    }

    /**
     * Tests method: updateNotificationType(NotificationType, String).
     *
     * With an already exist record, update it.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNotificationType_UpdateRecord() throws Exception {
        NotificationType notificationType = new NotificationType(2);
        notificationType.setName("test");
        notificationType.setDescription("test");
        // update it
        manager.updateNotificationType(notificationType, "DEVELOPER_UPDATE_RESOURCE");

        // checks exist record
        String sql = "SELECT * FROM notification_type_lu WHERE notification_type_id = ? AND modify_user = ?";

        assertTrue("The record has not been updated.",
                existsRecord(sql, new Object[] {new Long(2), "DEVELOPER_UPDATE_RESOURCE"}));
    }

    /**
     * Tests method removeNotificationType(NotificationType, String).
     *
     * With null notificationType, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotificationType_NullNotificationType() throws Exception {
        try {
            manager.removeNotificationType(null, "developer");
            fail("notificationType cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method removeNotificationType(NotificationType, String).
     *
     * With null operator, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotificationType_NullOperator() throws Exception {
        try {
            manager.removeNotificationType(new NotificationType(1), null);
            fail("operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method removeNotificationType(NotificationType, String).
     *
     * With notificationType id unset, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotificationType_UnSetId() throws Exception {
        try {
            manager.removeNotificationType(new NotificationType(), "DEVELOPER");
            fail("operator cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method removeNotificationType(NotificationType, String).
     *
     * Checks if it successfully remove a notificationType
     *
     * @throws Exception to JUnit
     */
    public void testRemoveNotificationType_remove() throws Exception {
        // delete all the reference tables
        clearTableAfter("notification");

        NotificationType notificationType = new NotificationType(2);

        // the original record exists
        String sql = "SELECT * FROM notification_type_lu WHERE notification_type_id = 2";
        assertTrue("The record exists.", existsRecord(sql, new Object[] {}));

        // remove it
        manager.removeNotificationType(notificationType, "TEST_DEVELOPER");

        // the record does not exist
        assertFalse("The record does not exists.", existsRecord(sql, new Object[] {}));

        // the modification user is set back to the notificationTypeRole
        assertEquals("The modification user does not set back.", "TEST_DEVELOPER",
                notificationType.getModificationUser());
    }

    /**
     * Tests method: searchNotificationTypes(Filter).
     *
     * With a null filter, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotificationTypes_NullFilter() throws Exception {
        try {
            manager.searchNotificationTypes(null);
            fail("The filter cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: searchNotificationTypes(Filter).
     *
     * With a wrong configuration of search bundle, SearchBuilderConfigurationException should be thrown.
     *
     * The selected column is not an integer / long.
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotificationTypes_WrongConfig_NotInteger() throws Exception {
        File file = new File(INVALID_CONFIG_ROOT + "wrong_notificationTypeSearchBundle_notInteger.xml");
        cm.add(file.getCanonicalPath());

        SearchBundleManager searchBundleManager1 = new SearchBundleManager(
            "com.topcoder.search.builder.SearchBundleManager.NotInteger");

        manager = new PersistenceResourceManager(persistence, searchBundleManager1);
        setAllFieldsSearchable(searchBundleManager1.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME));

        Filter filter = NotificationTypeFilterBuilder.createNotificationTypeIdFilter(1);
        try {
            manager.searchNotificationTypes(filter);
            fail("The selected column is not notificationType_id.");
        } catch (SearchBuilderConfigurationException e) {
            // success
        }
    }

    /**
     * Tests method: searchNotificationTypes(Filter).
     *
     * With NotificationTypeIdFilter.
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotificationTypes_NotificationTypeIdFilter() throws Exception {
        // creates the filter
        Filter filter = NotificationTypeFilterBuilder.createNotificationTypeIdFilter(1L);

        NotificationType[] notificationTypes = manager.searchNotificationTypes(filter);

        long[] expected = new long[] {1};

        // check the accuracy of the returned result

        assertEquals("Should contain 1 elements.", 1, notificationTypes.length);

        for (int i = 0; i < notificationTypes.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == notificationTypes[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The notificationType id " + notificationTypes[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchNotificationTypes(Filter).
     *
     * With NameFilter
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotificationTypes_NameFilter() throws Exception {
        // creates the filter
        Filter filter = NotificationTypeFilterBuilder.createNameFilter("notification type 1");

        NotificationType[] notificationTypes = manager.searchNotificationTypes(filter);

        long[] expected = new long[] {1};

        // check the accuracy of the returned result

        assertEquals("Should contain 1 elements.", 1, notificationTypes.length);

        for (int i = 0; i < notificationTypes.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == notificationTypes[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The notificationType id " + notificationTypes[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: searchNotificationTypes(Filter).
     *
     * With NotificationTypeIdFilter and no return result
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotificationTypes_NotificationTypeIdFilter_empty() throws Exception {
        // creates the filter
        Filter filter = NotificationTypeFilterBuilder.createNotificationTypeIdFilter(100L);

        NotificationType[] notificationTypes = manager.searchNotificationTypes(filter);

        // not exist
        long[] expected = new long[] {};

        // check the accuracy of the returned result

        assertEquals("Should contain no elements.", 0, notificationTypes.length);

        for (int i = 0; i < notificationTypes.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == notificationTypes[i].getId()) {
                    break;
                }
            }
            // not found
            if (j == expected.length) {
                fail("The notificationType id " + notificationTypes[i].getId() + " not found.");
            }
        }
    }

    /**
     * Tests method: getAllNotificationTypes().
     *
     * Check if the the notification types are properly returned.
     *
     * @throws Exception to JUnit
     */
    public void testGetAllNotificationTypes() throws Exception {
        long expected[] = new long[] {1, 2, 3};

        NotificationType[] notificationTypes = manager.getAllNotificationTypes();

        assertEquals("The number of element is not the same.", expected.length, notificationTypes.length);

        for (int i = 0; i < notificationTypes.length; i++) {
            int j;
            for (j = 0; j < expected.length; j++) {
                if (expected[j] == notificationTypes[i].getId()) {
                    break;
                }
            }
            if (j == expected.length) {
                fail("The element in location " + " i is unexpected.");
            }
        }
    }

    /**
     * Loads the specific namespaces to the ConfigManager.
     *
     * @throws Exception to JUnit.
     */
    private void loadNamespaces() throws Exception {
        String fileNames[] = {DB_CONNECTION_CONFIG, SEARCH_BUNDLE_MANAGER};
        for (int i = 0; i < fileNames.length; i++) {
            File file = new File(fileNames[i]);
            cm.add(file.getCanonicalPath());
        }
    }

    /**
     * Removes all the namespaces from the ConfigManager.
     *
     * @throws Exception to JUnit
     */
    private void clearNamespaces() throws Exception {
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String namespace = (String) it.next();
            if (cm.existsNamespace(namespace)) {
                cm.removeNamespace(namespace);
            }
        }
    }

    /**
     * Inserts the test records to database.
     *
     * @throws Exception to JUnit
     */
    private void insertRecords() throws Exception {
        Connection con = getConnection();

        String sql = null;

        // insert records to the table: project
        sql = "INSERT INTO project(project_id) VALUES(?)";

        doSQLUpdate(con, sql, new Object[] {new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3)});

        // insert records to the table: phase_type_lu
        sql = "INSERT INTO phase_type_lu(phase_type_id) VALUES(?)";

        doSQLUpdate(con, sql, new Object[] {new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3)});

        // insert records to the table: phase

        sql = "INSERT INTO project_phase(project_phase_id, project_id, phase_type_id) VALUES(?, ?, ?)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(4), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(5), new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(6), new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(7), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(8), new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(9), new Long(3), new Long(3)});

        // insert records to the table: submission

        sql = "INSERT INTO submission(submission_id) VALUES(?)";

        doSQLUpdate(con, sql, new Object[] {new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3)});

        // insert records to the table: resource_role_lu

        sql = "INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name,"
            + " description, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, ?, 'test', 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), "role 1"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), "role 2"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), "role 3"});

        // insert records to the table: resource.

        sql = "INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id, "
            + "create_user, create_date, modify_user, modify_date)"
            + " VALUES (?, ?, ?, ?, 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1),
            new Long(1), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2),
            new Long(1), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3),
            new Long(1), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(4),
            new Long(2), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(5),
            new Long(2), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(6),
            new Long(2), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(7),
            new Long(3), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(8),
            new Long(3), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(9),
            new Long(3), new Long(3), new Long(1)});

        // insert records to the table: resource_info_type_lu

        sql = "INSERT INTO resource_info_type_lu"
            + " (resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, 'test', 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), "resource info type 1"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), "resource info type 2"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), "resource info type 3"});

        // insert records to the table: resource_info
        sql = "INSERT INTO resource_info"
            + " (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, ?, 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), "value 1"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), "value 2"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), "value 3"});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2), "value 4"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), "value 5"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2), "value 6"});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3), "value 7"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3), "value 8"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), "value 9"});

        // insert records to the table: resource_submission
        sql = "INSERT INTO resource_submission"
            + " (resource_id, submission_id, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3)});

        // insert records to the table: notification_type_lu
        sql = "INSERT INTO notification_type_lu"
            + " (notification_type_id, name, description, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, 'test', 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), "notification type 1"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), "notification type 2"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), "notification type 3"});

        // insert records to the table: notification
        sql = "INSERT INTO notification ("
            + "project_id, external_ref_id, notification_type_id, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, ?, 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), new Long(3)});

        con.commit();
        closeConnection(con);
    }

    /**
     * Does the update operation to the database.
     *
     * @param con the connection
     * @param sql the sql statement to execute
     * @param args the arguments to be set into the database
     */
    private void doSQLUpdate(Connection con, String sql, Object[] args) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                setElement(ps, i + 1, args[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            // ignore
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    /**
     * Sets the element to the prepared statement with given index.
     *
     * @param ps the prepared statement
     * @param index the index to set
     * @param obj the value of the argument
     *
     * @throws SQLException if any error occurs
     */
    private void setElement(PreparedStatement ps, int index, Object obj) throws SQLException {
        if (obj instanceof String) {
            ps.setString(index, (String) obj);
        } else if (obj instanceof Integer) {
            ps.setInt(index, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            ps.setLong(index, ((Long) obj).longValue());
        } else if (obj instanceof Timestamp) {
            ps.setTimestamp(index, (Timestamp) obj);
        } else if (obj instanceof Date) {
            ps.setDate(index, new java.sql.Date(((Date) obj).getTime()));
        } else {
            throw new IllegalArgumentException("The element type is not supported yet.");
        }
    }

    /**
     * Closes a prepared statement silently.
     *
     * @param ps the prepared statement to close
     */
    private void closeStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes a connection silently.
     *
     * @param con the connection to close
     */
    private void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Returns a connection.
     *
     * @return the connection
     *
     * @throws Exception to JUnit
     */
    private Connection getConnection() throws Exception {
        String dbNamespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(dbNamespace);
        Connection con = factory.createConnection();
        con.setAutoCommit(false);
        return con;
    }

    /**
     * Clears all the tables in the database.
     *
     * @throws Exception to JUnit
     */
    private void removeTables() throws Exception {
        String[] tables = new String[] {"notification", "notification_type_lu", "resource_submission",
            "resource_info", "resource_info_type_lu", "resource", "resource_role_lu", "submission",
            "project_phase", "phase_type_lu", "project"};
        Connection con = getConnection();
        for (int i = 0; i < tables.length; i++) {
            doSQLUpdate(con, "DELETE FROM " + tables[i], new Object[] {});
        }
        con.commit();
        closeConnection(con);
    }

    /**
     * Removes the id generator in the IDGeneratorFactory via reflection.
     *
     * @param name the name of the IDGenerator
     *
     * @throws Exception to JUnit
     */
    private void removeIdGenerator(String name) throws Exception {
        Field field = IDGeneratorFactory.class.getDeclaredField("generators");
        field.setAccessible(true);
        Map map = (Map) field.get(IDGeneratorFactory.class);
        map.remove(name);
        field.setAccessible(false);
    }

    /**
     * Returns a valid resource with the given id.
     *
     * @param id the id of the resource
     *
     * @return the resource with the specific id
     */
    private Resource getResource(long id) {
        Resource resource = new Resource(id);
        resource.setResourceRole(new ResourceRole(id));
        return resource;
    }

    /**
     * Checks if the record exists.
     *
     * @param sql the statement to execute.
     *
     * @param objects the values to set in to the statement
     *
     * @return true if exist, otherwise false
     *
     * @throws Exception to JUnit
     */
    private boolean existsRecord(String sql, Object[] objects) throws Exception {
        CustomResultSet rs = doSQLQuery(sql, objects);
        return rs.next();
    }

    /**
     * Returns a custom result set for the query.
     *
     * @param sql the sql statement to execute
     * @param objects the values to set into the statement
     *
     * @return the custom result set
     *
     * @throws Exception to JUint
     */
    private CustomResultSet doSQLQuery(String sql, Object[] objects) throws Exception {
        Connection con = getConnection();

        // create the prepared statement
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            setElement(ps, i + 1, objects[i]);
        }
        CustomResultSet rs = new CustomResultSet(ps.executeQuery());
        closeStatement(ps);
        closeConnection(con);
        return rs;
    }

    /**
     * Clears a specific table.
     *
     * @param tableName the name of the table
     *
     * @throws Exception to JUnit
     */
    private void clearTableAfter(String tableName) throws Exception {
        String[] tables = new String[] {"notification", "notification_type_lu", "resource_submission",
            "resource_info", "resource_info_type_lu", "resource", "resource_role_lu", "submission",
            "project_phase", "phase_type_lu", "project"};
        Connection con = getConnection();
        for (int i = 0; i < tables.length; i++) {
            doSQLUpdate(con, "DELETE FROM " + tables[i], new Object[] {});
            if (tables[i].equals(tableName)) {
                break;
            }
        }
        con.commit();
        closeConnection(con);
    }

    /**
     * Returns a notification of the given ids.
     *
     * @param projectId the project id
     * @param externalRefId the external reference id
     * @param notificationTypeId the notification type id
     *
     * @return a notification of the given ids
     */
    private Notification getNotification(long projectId, long externalRefId, long notificationTypeId) {
        return new Notification(projectId, new NotificationType(notificationTypeId), externalRefId);
    }

    /**
     * Checks if the two notifications are the same.
     *
     * @param n1 the first notification
     * @param n2 the second notification
     *
     * @return true if they are the same, false otherwise.
     */
    private boolean checkNotificationEquals(Notification n1, Notification n2) {
        return n1.getProject() == n2.getProject() && n1.getNotificationType().getId()
                == n2.getNotificationType().getId() && n1.getExternalId() == n2.getExternalId();

    }

    /**
     * Sets the searchable fields to the search bundle.
     *
     * @param searchBundle the search bundle to set
     */
    private void setAllFieldsSearchable(SearchBundle searchBundle) {
        Map fields = new HashMap();

        // set the resource filter fields
        fields.put(ResourceFilterBuilder.RESOURCE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.PHASE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.PROJECT_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME,
                new OrValidator(StringValidator.containsSubstring("null"), LongValidator.isPositive()));
        fields.put(ResourceFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.EXTENSION_PROPERTY_NAME_FIELD_NAME, StringValidator.startsWith(""));
        fields.put(ResourceFilterBuilder.EXTENSION_PROPERTY_VALUE_FIELD_NAME, StringValidator.startsWith(""));

        // set the resource role filter fields
        fields.put(ResourceRoleFilterBuilder.NAME_FIELD_NAME, StringValidator.startsWith(""));
        fields.put(ResourceRoleFilterBuilder.PHASE_TYPE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, LongValidator.isPositive());

        // set the notification filter fields
        fields.put(NotificationFilterBuilder.EXTERNAL_REF_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(NotificationFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(NotificationFilterBuilder.PROJECT_ID_FIELD_NAME, LongValidator.isPositive());

        // set the notification type filter fields
        fields.put(NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(NotificationTypeFilterBuilder.NAME_FIELD_NAME, StringValidator.startsWith(""));

        searchBundle.setSearchableFields(fields);
    }
}

