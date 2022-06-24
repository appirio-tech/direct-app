/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit test cases for class <code>AbstractResourcePersistence </code>. In this test class, the connection
 * failure cases are tested.
 *
 * @author mittu
 * @version 1.1
 */
public abstract class TestAbstractlResourcePersistenceConnectionFailure extends TestCase {

    /**
     * Represents the AbstractResourcePersistence instance for test.
     */
    protected AbstractResourcePersistence persistence1, persistence2;

    /**
     * <p>
     * Represents the DBConnectionFactory for testing.
     * </p>
     */
    protected DBConnectionFactory factory;

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
        persistence1 = new SqlResourcePersistence(factory, "wronguser");
        persistence2 = new SqlResourcePersistence(factory, "nonexistinguser");
    }

    /**
     * Tear down the environment. Clear all namespaces in the config manager.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddResource() throws Exception {
        try {
            Resource r = DBTestUtil.createResource(11, 1, 1);
            persistence1.addResource(r);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            Resource r = DBTestUtil.createResource(11, 1, 1);
            persistence2.addResource(r);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code> void deleteResource(Resource resource)  </code>.
     *
     * @throws Exception to junit.
     */
    public void testDeleteResource() throws Exception {
        try {
            Resource r = DBTestUtil.createResource(100, 1, 1);
            persistence1.deleteResource(r);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            Resource r = DBTestUtil.createResource(100, 1, 1);
            persistence2.deleteResource(r);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateResource() throws Exception {
        try {
            Resource r = DBTestUtil.createResource(11, 1, 1);
            r.addSubmission(new Long(121));
            r.addSubmission(new Long(122));
            r.setProperty("name", new Integer(100));
            r.setProperty("name", "topcoder");
            persistence1.updateResource(r);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            Resource r = DBTestUtil.createResource(11, 1, 1);
            r.addSubmission(new Long(121));
            r.setProperty("name", new Integer(100));
            r.setProperty("name", "topcoder");
            persistence2.updateResource(r);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code> Resource loadResource(long resourceId) </code>.
     *
     * @throws Exception to junit.
     *
     */
    public void testLoadResource() throws Exception {
        try {
            persistence1.loadResource(1000);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            persistence2.loadResource(1000);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method
     * <code>void addNotification(long user, long project, long notificationType, String operator)</code>.
     *
     * @throws Exception to junit.
     */
    public void testAddNotification() throws Exception {
        try {
            persistence1.addNotification(1, 2, 2, "tc");
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            persistence2.addNotification(1, 2, 2, "tc");
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method
     * <code>void removeNotification(long user, long project, long notificationType, String operator) </code>.
     *
     * @throws Exception to junit.
     */
    public void testRemoveNotification() throws Exception {
        try {
            persistence1.removeNotification(1, 1, 2, "topcoder");
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            persistence2.removeNotification(1, 1, 2, "topcoder");
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotification() throws Exception {
        try {
            persistence1.loadNotification(1, 1, 2);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            persistence2.loadNotification(1, 1, 2);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddNotificationType() throws Exception {
        try {
            NotificationType type = DBTestUtil.createNotificationType(2);
            type.setName("tc");
            persistence1.addNotificationType(type);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            NotificationType type = DBTestUtil.createNotificationType(2);
            type.setName("tc");
            persistence2.addNotificationType(type);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>void deleteNotificationType(NotificationType notificationType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testDeleteNotificationType() throws Exception {
        try {
            NotificationType type = DBTestUtil.createNotificationType(2);
            type.setName("tc");
            persistence1.deleteNotificationType(type);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            NotificationType type = DBTestUtil.createNotificationType(2);
            type.setName("tc");
            persistence2.deleteNotificationType(type);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code> void updateNotificationType(NotificationType notificationType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateNotificationType() throws Exception {
        try {
            NotificationType type = DBTestUtil.createNotificationType(2);
            type.setName("tc");
            persistence1.updateNotificationType(type);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            NotificationType type = DBTestUtil.createNotificationType(2);
            type.setName("tc");
            persistence2.updateNotificationType(type);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>NotificationType loadNotificationType(long notificationTypeId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotificationType() throws Exception {
        try {
            NotificationType type = DBTestUtil.createNotificationType(2);
            type.setName("tc");
            persistence1.loadNotificationType(type.getId());
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            NotificationType type = DBTestUtil.createNotificationType(2);
            type.setName("tc");
            persistence2.loadNotificationType(type.getId());
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddResourceRole() throws Exception {
        try {
            ResourceRole role = DBTestUtil.createResourceRole(100);
            persistence1.addResourceRole(role);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            ResourceRole role = DBTestUtil.createResourceRole(100);
            persistence2.addResourceRole(role);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>void deleteResourceRole(ResourceRole resourceRole) </code>.
     *
     * @throws Exception to junit.
     */
    public void testDeleteResourceRole() throws Exception {
        try {
            ResourceRole role = DBTestUtil.createResourceRole(100);
            persistence1.deleteResourceRole(role);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            ResourceRole role = DBTestUtil.createResourceRole(100);
            persistence2.deleteResourceRole(role);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole)  </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateResourceRole() throws Exception {
        try {
            ResourceRole role = DBTestUtil.createResourceRole(100);
            // update the phaseType.
            role.setPhaseType(new Long(2));
            role.setName("developer");
            role.setDescription("test");
            persistence1.updateResourceRole(role);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            ResourceRole role = DBTestUtil.createResourceRole(100);
            // update the phaseType.
            role.setPhaseType(new Long(2));
            role.setName("developer");
            role.setDescription("test");
            persistence2.updateResourceRole(role);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>ResourceRole loadResourceRole(long resourceRoleId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadResourceRole() throws Exception {
        try {
            persistence1.loadResourceRole(1);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            persistence2.loadResourceRole(1);
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadResources() throws Exception {
        try {
            persistence1.loadResources(new long[] {11, 10});
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            persistence2.loadResources(new long[] {11, 10});
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>NotificationType[] loadNotificationTypes(long[] notificationTypeIds) </code>.
     *
     * @throws Exception to junit.
     *
     */
    public void testLoadNotificationTypes() throws Exception {
        try {
            persistence1.loadNotificationTypes(new long[] {1, 2, 3, 4, 5});
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            persistence2.loadNotificationTypes(new long[] {1, 2, 3, 4, 5});
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method <code>ResourceRole[] loadResourceRoles(long[] resourceRoleIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadResourceRoles() throws Exception {
        try {
            persistence1.loadResourceRoles(new long[] {100, 11});
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            persistence2.loadResourceRoles(new long[] {100, 11});
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }

    /**
     * Test method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotifications() throws Exception {
        try {
            persistence1.loadNotifications(new long[] {1, 2}, new long[] {2, 2}, new long[] {2, 2});
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
        try {
            persistence2.loadNotifications(new long[] {1, 2}, new long[] {2, 2}, new long[] {2, 2});
            fail("ResourcePersistenceException expected");
        } catch (ResourcePersistenceException e) {
            // expected
        }
    }
}
