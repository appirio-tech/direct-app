/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.PersistenceResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.management.resource.search.NotificationFilterBuilder;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.IntegerValidator;
import com.topcoder.util.datavalidator.LongValidator;
import com.topcoder.util.datavalidator.StringValidator;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.sql.Connection;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>PersistenceResourceManager</code> class.
 * </p>
 *
 * @author still
 * @version 1.0
 */
public class PersistenceResourceManagerAccuracyTest extends TestCase {
    /**
     * <p>
     * The default config file path of <code>DBConnectionFactory</code> for test.
     * </p>
     */
    private static final String DB_CONFIG_FILE = "test_files/accuracytests/ConnectionFactory.xml";

    /**
     * <p>
     * The default config file path of <code>SearchBundleManager</code> for test.
     * </p>
     */
    private static final String SB_CONFIG_FILE = "test_files/accuracytests/SearchBundleManager.xml";

    /**
     * <p>
     * The insert sql file path of <code>PersistenceResourceManager</code> for test.
     * </p>
     */
    private static final String INSERT_SQL_FILE = "test_files/accuracytests/InsertRecords.sql";

    /**
     * <p>
     * The array of  table names for test.
     * </p>
     */
    private String[] tables = new String[] {
        "notification", "notification_type_lu", "resource_submission", "resource_info",
        "resource_info_type_lu", "resource", "resource_role_lu", "submission", "project_phase",
        "phase_type_lu", "project"
    };

    /**
     * <p>
     * The instance of <code>ResourcePersistence</code> for tests.
     * </p>
     */
    private ResourcePersistence persistence = null;

    /**
     * <p>
     * The instance of resource SearchBundle for tests.
     * </p>
     */
    private SearchBundle resourceSearchBundle = null;

    /**
     * <p>
     * The instance of resourceRole SearchBundle for tests.
     * </p>
     */
    private SearchBundle resourceRoleSearchBundle = null;

    /**
     * <p>
     * The instance of notification SearchBundle for tests.
     * </p>
     */
    private SearchBundle notificationSearchBundle = null;

    /**
     * <p>
     * The instance of notificationType SearchBundle for tests.
     * </p>
     */
    private SearchBundle notificationTypeSearchBundle = null;

    /**
     * <p>
     * The instance of resource IDGenrator for tests.
     * </p>
     */
    private IDGenerator resourceIdGenerator = null;

    /**
     * <p>
     * The instance of resourceRole IDGenrator for tests.
     * </p>
     */
    private IDGenerator resourceRoleIdGenerator = null;

    /**
     * <p>
     * The instance of notificationType IDGenrator for tests.
     * </p>
     */
    private IDGenerator notificationTypeIdGenerator = null;

    /**
     * <p>
     * The instance of <code>Connection</code> for test.
     * </p>
     */
    private Connection conn = null;

    /**
     * <p>
     * The instance of SearchBundleManager for test.
     * </p>
     */
    private SearchBundleManager sm = null;

    /**
     * <p>
     * The instance of <code>PersistenceResourceManager</code> for test.
     * </p>
     */
    private PersistenceResourceManager manager = null;

    /**
     * <p>
     * Test suite of <code>PersistenceResourceManagerAccuracyTest</code>.
     * </p>
     *
     * @return Test suite of <code>PersistenceResourceManagerAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(PersistenceResourceManagerAccuracyTest.class);
    }

    /**
     * <p>
     * Set up the accuracy testing envionment.
     * </p>
     *
     * @throws Exception if configuration could not be clear.
     */
    protected void setUp() throws Exception {
        // clear all namespaces here.
        AccuracyTestHelper.clearNamespace();

        ConfigManager cm = ConfigManager.getInstance();

        // load config files.
        cm.add(new File(DB_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(SB_CONFIG_FILE).getAbsolutePath());

        DBConnectionFactory factory = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        conn = factory.createConnection("TestConnectionImpl");

        // clear the sql table.
        AccuracyTestHelper.clearTables(conn, tables);

        // insert record for IDGenerator.
        AccuracyTestHelper.runSqlFromFile(conn, INSERT_SQL_FILE);


        // new SearchBundleManager here¡£
        String namespace = "com.topcoder.search.builder.SearchBundleManager";
        sm = new SearchBundleManager(namespace);

        // new id generators here.
        resourceIdGenerator = IDGeneratorFactory.getIDGenerator(PersistenceResourceManager.RESOURCE_ID_GENERATOR_NAME);
        resourceRoleIdGenerator = IDGeneratorFactory.getIDGenerator(
            PersistenceResourceManager.RESOURCE_ROLE_ID_GENERATOR_NAME);
        notificationTypeIdGenerator = IDGeneratorFactory.getIDGenerator(
            PersistenceResourceManager.NOTIFICATION_TYPE_ID_GENERATOR_NAME);

        // set resource field here.
        Map resourceFields = new HashMap();
        resourceFields.put("resource.resource_id", LongValidator.greaterThan(0));
        resourceFields.put("resource_id", LongValidator.greaterThan(0));
        resourceFields.put("project_id", LongValidator.greaterThanOrEqualTo(0));
        resourceFields.put("project_phase_id", LongValidator.greaterThan(0));
        resourceFields.put("submission_id", LongValidator.greaterThan(0));
        resourceFields.put("name", StringValidator.hasLength(IntegerValidator.greaterThan(1)));
        resourceFields.put("value", StringValidator.hasLength(IntegerValidator.greaterThan(0)));

        this.resourceSearchBundle = sm.getSearchBundle("Resource Search Bundle");
        this.resourceSearchBundle.setSearchableFields(resourceFields);

        // set role field here.
        Map roleFields = new HashMap();
        roleFields.put("resource_role_id", LongValidator.greaterThanOrEqualTo(0));
        roleFields.put("phase_type_id", LongValidator.greaterThan(0));
        roleFields.put("name", StringValidator.hasLength(IntegerValidator.greaterThan(0)));

        this.resourceRoleSearchBundle = sm.getSearchBundle("Resource Role Search Bundle");
        this.resourceRoleSearchBundle.setSearchableFields(roleFields);

        // set notification field here.
        Map notificationFields = new HashMap();
        notificationFields.put("project_id", LongValidator.greaterThan(0));
        notificationFields.put("external_ref_id", LongValidator.greaterThan(0));
        notificationFields.put("notification_type_id", LongValidator.greaterThan(0));

        this.notificationSearchBundle = sm.getSearchBundle("Notification Search Bundle");
        this.notificationSearchBundle.setSearchableFields(notificationFields);

        // set notificationType field here.
        Map notificationTypeFields = new HashMap();
        notificationTypeFields.put("notification_type_id", LongValidator.greaterThanOrEqualTo(0));
        notificationTypeFields.put("name",
            StringValidator.hasLength(IntegerValidator.greaterThanOrEqualTo(0)));

        this.notificationTypeSearchBundle = sm.getSearchBundle("Notification Type Search Bundle");
        this.notificationTypeSearchBundle.setSearchableFields(notificationTypeFields);

        persistence = new MockSqlResourcePersistence(factory);
        manager = new PersistenceResourceManager(persistence, sm);
    }

    /**
     * <p>
     * Clean the accuracy test environment.
     * </p>
     *
     * @throws Exception if configuration could not be clear.
     */
    protected void tearDown() throws Exception {
        // clear all namespaces.
        AccuracyTestHelper.clearNamespace();

        // clear the sql table.
        try {
            AccuracyTestHelper.clearTables(conn, tables);
        } finally {
            conn.close();
        }
    }

    /**
     * <p>
     * Accuracy test of <code>PersistenceResourceManager(ResourcePersistence, SearchBundle,
     * SearchBundle, SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator)</code>
     * constructor.
     * </p>
     */
    public void testPersistenceResourceManagerCtor1() {
        manager = new PersistenceResourceManager(persistence, resourceSearchBundle,
                resourceRoleSearchBundle, notificationSearchBundle, notificationTypeSearchBundle,
                resourceIdGenerator, resourceRoleIdGenerator, notificationTypeIdGenerator);

        // check null here.
        assertNotNull("Create PersistenceResourceManager failed.", manager);
    }

    /**
     * <p>
     * Accuracy test of <code>PersistenceResourceManager(ResourcePersistence,
     * SearchBundleManager)</code> constructor.
     * </p>
     */
    public void testPersistenceResourceManagerCtor2() {
        // check null here.
        assertNotNull("Create PersistenceResourceManager failed.", manager);
    }

    /**
     * <p>
     * Accuracy test of the <code>updateResource(Resource, String)</code> method. It tests add a
     * resource instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_updateResource_Add_Once() throws Exception {
        // create a resource instance here.
        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole(2));
        resource.setProject(new Long(1));
        resource.setPhase(new Long(2));

        // add into datebase.
        manager.updateResource(resource, "tc");

        // check the resource.
        assertTrue("The resource should be updated.", resource.getId() > 0);
        assertEquals("One resource should be added into persistence.", 1,
            AccuracyTestHelper.getTableSize(conn, "resource"));

        // get the Resource instance back from the database.
        Resource ret = persistence.loadResource(resource.getId());

        // check the result here.
        assertEquals("The creationUser should be updated to 'tc'", "tc", ret.getCreationUser());
        assertNotNull("The creationTimestamp should be updated.", ret.getCreationTimestamp());
        assertEquals("The modificationUser should be updated to 'tc'", "tc",
            ret.getModificationUser());
        assertNotNull("The modificationTimestamp should be updated.", ret.getModificationTimestamp());
        assertEquals("The creationUser should be updated to '1'", 1, ret.getProject().longValue());
        assertEquals("The creationUser should be updated to '2'", 2, ret.getPhase().longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>updateResource(Resource, String)</code> method. It tests add some
     * resource instances into persistence.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_updateResource_Add_MoreTimes()
        throws Exception {
        // create some resource instances here.
        Resource resource1 = new Resource();
        resource1.setResourceRole(new ResourceRole(1));
        resource1.setProject(new Long(1));
        resource1.setPhase(new Long(2));

        Resource resource2 = new Resource();
        resource2.setResourceRole(new ResourceRole(2));
        resource2.setProject(new Long(2));
        resource2.setPhase(new Long(2));

        // add into datebase.
        manager.updateResource(resource1, "tc1");
        manager.updateResource(resource2, "tc2");

        // check the resource.
        assertTrue("The resource should be updated.", resource1.getId() > 0);
        assertTrue("The resource should be updated.", resource2.getId() > 0);
        assertEquals("Two resources should be added into persistence.", 2,
            AccuracyTestHelper.getTableSize(conn, "resource"));

        // get the Resource instance back from the database.
        Resource ret = persistence.loadResource(resource2.getId());

        // check the result here.
        assertEquals("The creationUser should be updated to 'tc2'", "tc2", ret.getCreationUser());
        assertNotNull("The creationTimestamp should be updated.", ret.getCreationTimestamp());
        assertEquals("The modificationUser should be updated to 'tc2'", "tc2",
            ret.getModificationUser());
        assertNotNull("The modificationTimestamp should be updated.", ret.getModificationTimestamp());
        assertEquals("The creationUser should be updated to '2'", 2, ret.getProject().longValue());
        assertEquals("The creationUser should be updated to '2'", 2, ret.getPhase().longValue());

        ret = persistence.loadResource(resource1.getId());

        // check the result here.
        assertEquals("The creationUser should be updated to 'tc1'", "tc1", ret.getCreationUser());
        assertNotNull("The creationTimestamp should be updated.", ret.getCreationTimestamp());
        assertEquals("The modificationUser should be updated to 'tc1'", "tc1",
            ret.getModificationUser());
        assertNotNull("The modificationTimestamp should be updated.", ret.getModificationTimestamp());
        assertEquals("The creationUser should be updated to '1'", 1, ret.getProject().longValue());
        assertEquals("The creationUser should be updated to '2'", 2, ret.getPhase().longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>updateResource(Resource, String)</code> method. It tests update a
     * resource instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_updateResource_Update_Once()
        throws Exception {
        // create a resource instance here.
        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole(2));
        resource.setProject(new Long(1));
        resource.setPhase(new Long(2));

        // add into datebase.
        manager.updateResource(resource, "tc");

        long resourceId = resource.getId();
        Date creationTimestamp = resource.getCreationTimestamp();
        manager.updateResource(resource, "tc1");

        // check the resource.
        assertEquals("The resource should not be changed.", resourceId, resource.getId());
        assertEquals("One resource should be added into persistence.", 1,
            AccuracyTestHelper.getTableSize(conn, "resource"));

        // get the Resource instance back from the database.
        Resource ret = persistence.loadResource(resource.getId());

        // check the result here.
        assertEquals("The creationUser should be updated to 'tc'", "tc", ret.getCreationUser());
        assertEquals("The creationTimestamp not be changed.", creationTimestamp,
            ret.getCreationTimestamp());
        assertEquals("The modificationUser should be updated to 'tc1'", "tc1",
            ret.getModificationUser());
        assertTrue("The modificationTimestamp should be updated.",
            ret.getModificationTimestamp().getTime() > creationTimestamp.getTime());

        assertEquals("The creationUser should be updated to '1'", 1, ret.getProject().longValue());
        assertEquals("The creationUser should be updated to '2'", 2, ret.getPhase().longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>updateResource(Resource, String)</code> method. It tests update a
     * resource instance into persistence more times.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_updateResource_Update_MoreTimes()
        throws Exception {
        // create a resource instance here.
        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole(2));
        resource.setProject(new Long(1));
        resource.setPhase(new Long(2));

        // add into datebase.
        manager.updateResource(resource, "tc");

        long resourceId = resource.getId();
        Date creationTimestamp = resource.getCreationTimestamp();

        // update this resource instance.
        for (int i = 0; i < 10; i++) {
            manager.updateResource(resource, "tc" + i);
        }

        // check the resource.
        assertEquals("The resource should not be changed.", resourceId, resource.getId());
        assertEquals("One resource should be added into persistence.", 1,
            AccuracyTestHelper.getTableSize(conn, "resource"));

        // get the Resource instance back from the database.
        Resource ret = persistence.loadResource(resource.getId());

        // check the result here.
        assertEquals("The creationUser should be updated to 'tc'", "tc", ret.getCreationUser());
        assertEquals("The creationTimestamp not be changed.", creationTimestamp,
            ret.getCreationTimestamp());
        assertEquals("The modificationUser should be updated to 'tc9'", "tc9",
            ret.getModificationUser());
        assertTrue("The modificationTimestamp should be updated.",
            ret.getModificationTimestamp().getTime() > creationTimestamp.getTime());

        assertEquals("The creationUser should be updated to '1'", 1, ret.getProject().longValue());
        assertEquals("The creationUser should be updated to '2'", 2, ret.getPhase().longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>removeResource(Resource, String)</code> method. It tests remove a
     * resource instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when removing.
     */
    public void testMethod_removeResource() throws Exception {
        // create some resource instances here.
        Resource resource1 = new Resource();
        resource1.setResourceRole(new ResourceRole(1));
        resource1.setProject(new Long(1));
        resource1.setPhase(new Long(2));

        Resource resource2 = new Resource();
        resource2.setResourceRole(new ResourceRole(2));
        resource2.setProject(new Long(2));
        resource2.setPhase(new Long(2));

        // add into datebase.
        manager.updateResource(resource1, "tc1");
        manager.updateResource(resource2, "tc2");

        // remove the resource instances here.
        manager.removeResource(resource1, "remover1");

        // check the resource.
        assertEquals("The modificationUser should be updated to 'remover1'", "remover1",
            resource1.getModificationUser());
        assertEquals("One resource should be removed from persistence.", 1,
            AccuracyTestHelper.getTableSize(conn, "resource"));

        // remove the resource instances here.
        manager.removeResource(resource1, "remover2");

        // check the resource.
        assertEquals("The modificationUser should be updated to 'remover2'", "remover2",
            resource1.getModificationUser());
        assertEquals("One resource should be removed from persistence.", 1,
            AccuracyTestHelper.getTableSize(conn, "resource"));

        // remove the resource instances here.
        manager.removeResource(resource2, "remover2");

        // check the resource.
        assertEquals("The modificationUser should be updated to 'remover2'", "remover2",
            resource2.getModificationUser());
        assertEquals("One resource should be removed from persistence.", 0,
            AccuracyTestHelper.getTableSize(conn, "resource"));
    }

    /**
     * <p>
     * Accuracy test of the <code>getResource(long)</code> method. It tests getting a resource
     * instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when getting.
     */
    public void testMethod_getResource() throws Exception {
        // create some resource instances here.
        Resource resource1 = new Resource();
        resource1.setResourceRole(new ResourceRole(1));
        resource1.setProject(new Long(1));
        resource1.setPhase(new Long(2));

        Resource resource2 = new Resource();
        resource2.setResourceRole(new ResourceRole(2));
        resource2.setProject(new Long(2));
        resource2.setPhase(new Long(2));

        // add into datebase.
        manager.updateResource(resource1, "tc1");
        manager.updateResource(resource2, "tc2");

        // get the resource instance from the database.
        Resource ret = manager.getResource(resource1.getId());
        assertEquals("The resource from persistence is incorrect.", "tc1", ret.getCreationUser());

        // remove this resource1 and then get it for test.
        manager.removeResource(resource1, "remove1");
        assertNull("The resource should be removed from persistence.",
            manager.getResource(resource1.getId()));

        // get another resource instance from the database.
        ret = manager.getResource(resource2.getId());
        assertEquals("The resource from persistence is incorrect.", "tc2", ret.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>updateResourceRole(ResourceRole, String)</code> method. It tests
     * add a resource instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_updateResourceRole_Add() throws Exception {
        ResourceRole role = new ResourceRole();
        role.setDescription("resource role");
        role.setName("role1");
        role.setPhaseType(new Long(1));

        // add into datebase.
        manager.updateResourceRole(role, "tc");

        // check the role.
        assertTrue("The role should be updated.", role.getId() > 0);
        assertEquals("One role should be added into persistence.", 5,
            AccuracyTestHelper.getTableSize(conn, "resource_role_lu"));

        // check the role here.
        assertEquals("The creationUser should be updated to 'tc'", "tc", role.getCreationUser());
        assertNotNull("The creationTimestamp should be updated.", role.getCreationTimestamp());
        assertEquals("The modificationUser should be updated to 'tc'", "tc",
            role.getModificationUser());
        assertNotNull("The modificationTimestamp should be updated.",
            role.getModificationTimestamp());

        // get the ResourceRole instance from the database.
        ResourceRole ret = persistence.loadResourceRole(role.getId());
        assertEquals("The creationUser should be updated to 'tc'", "tc", ret.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>updateResourceRole(ResourceRole, String)</code> method. It tests
     * update a resource instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_updateResourceRole_Update()
        throws Exception {
        ResourceRole role = new ResourceRole();
        role.setDescription("resource role");
        role.setName("role1");
        role.setPhaseType(new Long(1));

        // add into datebase.
        manager.updateResourceRole(role, "tc");

        long roleId = role.getId();
        Date creationTimestamp = role.getCreationTimestamp();

        // update it more times.
        for (int i = 0; i < 10; i++) {
            manager.updateResourceRole(role, "tc" + i);
        }

        // check the role.
        assertEquals("The role id should not be changed.", roleId, role.getId());
        assertEquals("Only One role should be added into persistence.", 5,
            AccuracyTestHelper.getTableSize(conn, "resource_role_lu"));

        // check the role here.
        assertEquals("The creationUser should be updated to 'tc'", "tc", role.getCreationUser());
        assertEquals("The creationTimestamp should be updated.", creationTimestamp,
            role.getCreationTimestamp());
        assertEquals("The modificationUser should be updated to 'tc9'", "tc9",
            role.getModificationUser());
        assertTrue("The modificationTimestamp should be updated.",
            role.getModificationTimestamp().compareTo(creationTimestamp) > 0);

        // get the ResourceRole instance from the database.
        ResourceRole ret = persistence.loadResourceRole(role.getId());
        assertEquals("The creationUser should be updated to 'tc'", "tc", ret.getCreationUser());
        assertEquals("The modificationUser should be updated to 'tc9'", "tc9",
            role.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>removeResourceRole(ResourceRole, String)</code> method. It tests
     * removing a resource instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when removing.
     */
    public void testMethod_removeResourceRole() throws Exception {
        ResourceRole role1 = new ResourceRole();
        role1.setDescription("resource role");
        role1.setName("role1");
        role1.setPhaseType(new Long(1));

        ResourceRole role2 = new ResourceRole();
        role2.setDescription("resource role");
        role2.setName("role2");
        role2.setPhaseType(new Long(2));

        // add into datebase.
        manager.updateResourceRole(role1, "tc1");
        manager.updateResourceRole(role2, "tc2");

        // remove one role here.
        manager.removeResourceRole(role1, "remove1");
        assertEquals("Only 4 role should be added into persistence.", 5,
            AccuracyTestHelper.getTableSize(conn, "resource_role_lu"));
        assertEquals("The modificationUser should be updated to 'remove1'", "remove1",
            role1.getModificationUser());

        // remove it again.
        manager.removeResourceRole(role1, "remove2");
        assertEquals("Only 4 role should be added into persistence.", 5,
            AccuracyTestHelper.getTableSize(conn, "resource_role_lu"));
        assertEquals("The modificationUser should be updated to 'remove2'", "remove2",
            role1.getModificationUser());

        // remove all roles here.
        manager.removeResourceRole(role2, "remove2");
        assertEquals("Only 4 role should be added into persistence.", 4,
            AccuracyTestHelper.getTableSize(conn, "resource_role_lu"));
        assertEquals("The modificationUser should be updated to 'remove2'", "remove2",
            role2.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>getAllResourceRoles()</code> method. It tests getting all
     * resource roles instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when getting.
     */
    public void testMethod_getAllResourceRoles() throws Exception {
        ResourceRole role1 = new ResourceRole();
        role1.setDescription("resource role");
        role1.setName("role1");
        role1.setPhaseType(new Long(1));

        ResourceRole role2 = new ResourceRole();
        role2.setDescription("resource role");
        role2.setName("role2");
        role2.setPhaseType(new Long(2));

        // add into datebase.
        manager.updateResourceRole(role1, "tc1");
        manager.updateResourceRole(role2, "tc2");

        // get all roles and test it.
        ResourceRole[] roles = manager.getAllResourceRoles();
        assertEquals("Only 6 role should be added into persistence.", 6, roles.length);

        // remove some rols and then test them for test.
        manager.removeResourceRole(role1, "remover1");
        manager.removeResourceRole(role2, "remover2");
        roles = manager.getAllResourceRoles();
        assertEquals("Only 4 role should be added into persistence.", 4, roles.length);
    }

    /**
     * <p>
     * Accuracy test of the <code>addNotifications(long[], long, long, String)</code> method. It
     * tests add a notification instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_addNotifications_One() throws Exception {
        manager.addNotifications(new long[] {1}, 1, 2, "tc");

        // get notification from persistence.
        Notification notification = persistence.loadNotification(1, 1, 2);

        // check the result here.
        assertNotNull("The notification should not be null.", notification);
    }

    /**
     * <p>
     * Accuracy test of the <code>addNotifications(long[], long, long, String)</code> method. It
     * tests add more notification instances into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_addNotifications_More() throws Exception {
        manager.addNotifications(new long[] {1, 2}, 1, 2, "tc");

        // get notification from persistence.
        Notification[] notifications = persistence.loadNotifications(new long[] {1, 2},
                new long[] {1, 1}, new long[] {2, 2});

        assertEquals("Should return 2", 2, notifications.length);
    }

    /**
     * <p>
     * Accuracy test of the <code>removeNotifications(long[], long, long, String)</code> method. It
     * tests removing more notification instances into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when removing.
     */
    public void testMethod_removeNotifications() throws Exception {
        manager.addNotifications(new long[] {1, 2}, 1, 2, "tc");
        manager.removeNotifications(new long[] {1, 2}, 1, 2, "tc");

        // get the notification instances.
        Notification[] notifications = persistence.loadNotifications(new long[] {1, 2},
                new long[] {1, 1}, new long[] {2, 2});

        assertEquals("Should return 0", 0, notifications.length);
    }

    /**
     * <p>
     * Accuracy test of the <code>getNotifications(long, long)</code> method. It tests getting more
     * notification instances into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when getting.
     */
    public void testMethod_getNotifications() throws Exception {
        manager.addNotifications(new long[] {1, 2}, 1, 2, "tc");

        // get the notification instances.
        long[] notificationIds = manager.getNotifications(1, 2);
        assertEquals("Should return 2", 2, notificationIds.length);
    }

    /**
     * <p>
     * Accuracy test of the <code>updateNotificationType(NotificationType, String)</code> method.
     * It tests add a notification type instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_updateNotificationType_Add()
        throws Exception {
        NotificationType type = new NotificationType();
        type.setName("type1");
        type.setDescription("NotificationType");
        manager.updateNotificationType(type, "tc");

        // check the type.
        assertTrue("The type should be updated.", type.getId() > 0);
        assertEquals("One type should be added into persistence.", 4,
            AccuracyTestHelper.getTableSize(conn, "notification_type_lu"));

        // check the role here.
        assertEquals("The creationUser should be updated to 'tc'", "tc", type.getCreationUser());
        assertNotNull("The creationTimestamp should be updated.", type.getCreationTimestamp());
        assertEquals("The modificationUser should be updated to 'tc'", "tc",
            type.getModificationUser());
        assertNotNull("The modificationTimestamp should be updated.",
            type.getModificationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of the <code>updateNotificationType(NotificationType, String)</code> method.
     * It tests add a notification type instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when updating.
     */
    public void testMethod_updateNotificationType_Update()
        throws Exception {
        NotificationType type = new NotificationType();
        type.setName("type1");
        type.setDescription("NotificationType");
        manager.updateNotificationType(type, "tc");

        long typeId = type.getId();
        Date creationTimestamp = type.getCreationTimestamp();

        // update it more times.
        for (int i = 0; i < 10; i++) {
            manager.updateNotificationType(type, "tc" + i);
        }

        // check the type.
        assertEquals("The type id should not be changed.", typeId, type.getId());
        assertEquals("One type should be added into persistence.", 4,
            AccuracyTestHelper.getTableSize(conn, "notification_type_lu"));

        // check the role here.
        assertEquals("The creationUser should be updated to 'tc'", "tc", type.getCreationUser());
        assertEquals("The creationTimestamp should not be changed.", creationTimestamp,
            type.getCreationTimestamp());
        assertEquals("The modificationUser should be updated to 'tc9'", "tc9",
            type.getModificationUser());
        assertTrue("The modificationTimestamp should be updated.",
            type.getModificationTimestamp().compareTo(creationTimestamp) > 0);
    }

    /**
     * <p>
     * Accuracy test of the <code>removeNotificationType(NotificationType, String)</code> method.
     * It tests removing a notification type instance into persistence once.
     * </p>
     *
     * @throws Exception if any exception occurs when removing.
     */
    public void testMethod_removeNotificationType() throws Exception {
        NotificationType type1 = new NotificationType();
        type1.setName("type1");
        type1.setDescription("NotificationType");

        NotificationType type2 = new NotificationType();
        type2.setName("type2");
        type2.setDescription("NotificationType");

        manager.updateNotificationType(type1, "tc1");
        manager.updateNotificationType(type2, "tc2");

        // remove one type.
        manager.removeNotificationType(type1, "remover1");
        assertEquals("Only 2 type should be in persistence.", 4,
            AccuracyTestHelper.getTableSize(conn, "notification_type_lu"));
        assertEquals("The modificationUser should be updated to 'remover1'", "remover1",
            type1.getModificationUser());

        manager.removeNotificationType(type1, "remover2");
        assertEquals("Only 2 type should be in persistence.", 4,
            AccuracyTestHelper.getTableSize(conn, "notification_type_lu"));
        assertEquals("The modificationUser should be updated to 'remover2'", "remover2",
            type1.getModificationUser());

        manager.removeNotificationType(type2, "remover2");
        assertEquals("Only 2 type should be in persistence.", 3,
            AccuracyTestHelper.getTableSize(conn, "notification_type_lu"));
        assertEquals("The modificationUser should be updated to 'remover2'", "remover2",
            type2.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>searchResources(Filter)</code> method.
     * </p>
     *
     * @throws Exception if any exception occurs when removing.
     */
    public void testMethod_searchResources() throws Exception {
        // create some resource instances here.
        Resource resource1 = new Resource();
        resource1.setResourceRole(new ResourceRole(1));
        resource1.setProject(new Long(1));
        resource1.setPhase(new Long(2));

        Resource resource2 = new Resource();
        resource2.setResourceRole(new ResourceRole(2));
        resource2.setProject(new Long(2));
        resource2.setPhase(new Long(2));

        Resource resource3 = new Resource();
        resource3.setResourceRole(new ResourceRole(2));
        resource3.setProject(new Long(2));
        resource3.setPhase(new Long(3));

        // add into datebase.
        manager.updateResource(resource1, "tc1");
        manager.updateResource(resource2, "tc2");
        manager.updateResource(resource3, "tc3");

        Resource[] resources = manager.searchResources(ResourceFilterBuilder.createPhaseIdFilter(2));

        assertEquals("The Resources returned by phase_id = 1 should be 2", 2, resources.length);

        resources = manager.searchResources(ResourceFilterBuilder.createResourceIdFilter(
                    resource3.getId()));

        assertEquals("The Resources returned by resource_id = " + resource3.getId()
            + " should be 1", 1, resources.length);

        resources = manager.searchResources(ResourceFilterBuilder.createProjectIdFilter(4));

        assertEquals("The Resources returned by project_id = 4 should be 0", 0, resources.length);
    }

    /**
     * <p>
     * Accuracy test of the <code>searchResourceRoles(Filter)</code> method.
     * </p>
     *
     * @throws Exception if any exception occurs when removing.
     */
    public void testMethod_searchResourceRoles() throws Exception {
        ResourceRole role1 = new ResourceRole();
        role1.setDescription("resource role");
        role1.setName("role1");
        role1.setPhaseType(new Long(1));

        ResourceRole role2 = new ResourceRole();
        role2.setDescription("resource role");
        role2.setName("role2");
        role2.setPhaseType(new Long(2));

        ResourceRole role3 = new ResourceRole();
        role3.setDescription("resource role");
        role3.setName("role3");
        role3.setPhaseType(new Long(2));

        // add into datebase.
        manager.updateResourceRole(role1, "tc1");
        manager.updateResourceRole(role2, "tc2");
        manager.updateResourceRole(role3, "tc3");

        ResourceRole[] roles = manager.searchResourceRoles(ResourceRoleFilterBuilder
                .createPhaseTypeIdFilter(2));

        assertEquals("The roles returned by phase_id = 2 should be 3", 3, roles.length);

        roles = manager.searchResourceRoles(ResourceRoleFilterBuilder.createNameFilter("role2"));

        assertEquals("The roles returned by name = 'role2' should be 1", 1, roles.length);

        roles = manager.searchResourceRoles(ResourceRoleFilterBuilder.createPhaseTypeIdFilter(5));

        assertEquals("The roles returned by phase_id = 5 should be 0", 0, roles.length);
    }

    /**
     * <p>
     * Accuracy test of the <code>searchNotificationTypes(Filter)</code> method.
     * </p>
     *
     * @throws Exception if any exception occurs when removing.
     */
    public void testMethod_searchNotificationTypes() throws Exception {
        NotificationType type1 = new NotificationType();
        type1.setName("type1");
        type1.setDescription("NotificationType");

        NotificationType type2 = new NotificationType();
        type2.setName("type2");
        type2.setDescription("NotificationType");

        NotificationType type3 = new NotificationType();
        type3.setName("type3");
        type3.setDescription("NotificationType");

        // add into datebase.
        manager.updateNotificationType(type1, "tc1");
        manager.updateNotificationType(type2, "tc2");
        manager.updateNotificationType(type3, "tc3");

        NotificationType[] types = manager.searchNotificationTypes(NotificationTypeFilterBuilder
                .createNameFilter("type2"));

        assertEquals("The types returned by name = 'type2' should be 1", 1, types.length);

        types = manager.searchNotificationTypes(NotificationTypeFilterBuilder.createNameFilter(
                    "type4"));

        assertEquals("The types returned by name = 'type4' should be 1", 0, types.length);
    }

    /**
     * <p>
     * Accuracy test of the <code>searchNotifications(Filter)</code> method.
     * </p>
     *
     * @throws Exception if any exception occurs when removing.
     */
    public void testMethod_searchNotifications() throws Exception {
        manager.addNotifications(new long[] {1, 2, 3}, 1, 2, "tc");

        Notification[] notifications = manager.searchNotifications(NotificationFilterBuilder
                .createProjectIdFilter(1));

        assertEquals("The notifications returned by project_id = 1 should be 3", 3,
            notifications.length);

        notifications = manager.searchNotifications(NotificationFilterBuilder
                .createExternalRefIdFilter(1));

        assertEquals("The notifications returned by external_ref_id = 1 should be 1", 1,
            notifications.length);

        notifications = manager.searchNotifications(NotificationFilterBuilder
                .createNotificationTypeIdFilter(3));

        assertEquals("The notifications returned by notification_type_id = 3 should be 0", 0,
            notifications.length);
    }
}
