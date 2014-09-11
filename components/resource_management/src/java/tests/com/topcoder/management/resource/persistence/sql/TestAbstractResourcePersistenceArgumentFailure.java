/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.io.File;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Unit test case for class <code>SqlResourcePersistence </code>. In this class, it only test the failure for invalid
 * arguments. The unit test cases for functionalities of this class is in test class TestSqlResourcePersistence.
 *
 * @author Chenhong, mittu
 * @version 1.1
 */
public class TestAbstractResourcePersistenceArgumentFailure extends TestCase {
    /**
     * Represents the SqlResourcePersistence instance for test.
     */
    private SqlResourcePersistence persistence = null;

    /**
     * Set up the environment. Create SqlResourcePersistence instance for test.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        DBTestUtil.clearConfigManager();
        File file = new File("test_files/DBConnectionFactory.xml");

        cm.add(file.getAbsolutePath());

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        persistence = new SqlResourcePersistence(factory);
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
     * Test for constructor. SqlResourcePersistence(DBConnectionFactory) If parameter DBConnectionFactory is null,
     * IllegalArgumentException should be thrown.
     */
    public void testSqlResourcePersistenceDBConnectionFactory() {
        try {
            new SqlResourcePersistence(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test constructor. SqlResourcePersistence(DBConnectionFactory, String) If parameter DBConnectionFactory is null,
     * IllegalArgumentException should be thrown.
     */
    public void testSqlResourcePersistenceDBConnectionFactoryString() {
        try {
            new SqlResourcePersistence(null, "connectionName");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. IllegalArgumentException if resource is null or
     * its id is UNSET_ID or its ResourceRole is null or its creation/modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResource_1() throws Exception {
        try {
            persistence.addResource(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. IllegalArgumentException if resource is null or
     * its id is UNSET_ID or its ResourceRole is null or its creation/modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResource_2() throws Exception {
        Resource r = new Resource();

        try {
            persistence.addResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. IllegalArgumentException if resource is null or
     * its id is UNSET_ID or its ResourceRole is null or its creation/modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResource_3() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setResourceRole(null);

        try {
            persistence.addResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. IllegalArgumentException if resource is null or
     * its id is UNSET_ID or its ResourceRole is null or its creation/modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResource_4() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setCreationUser(null);

        try {
            persistence.addResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. IllegalArgumentException if resource is null or
     * its id is UNSET_ID or its ResourceRole is null or its creation/modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResource_5() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setCreationTimestamp(null);

        try {
            persistence.addResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. IllegalArgumentException if resource is null or
     * its id is UNSET_ID or its ResourceRole is null or its creation/modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResource_6() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setModificationUser(null);

        try {
            persistence.addResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. IllegalArgumentException if resource is null or
     * its id is UNSET_ID or its ResourceRole is null or its creation/modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResource_7() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setModificationTimestamp(null);

        try {
            persistence.addResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>deleteResource(Resource resource) </code>. IllegalArgumentException If resource is null or
     * its id is UNSET_ID or its ResourceRole is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteResource_1() throws Exception {
        try {
            persistence.deleteResource(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>deleteResource(Resource resource) </code>. IllegalArgumentException If resource is null or
     * its id is UNSET_ID or its ResourceRole is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteResource_2() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setResourceRole(null);
        try {
            persistence.deleteResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>deleteResource(Resource resource) </code>. IllegalArgumentException If resource is null or
     * its id is UNSET_ID or its ResourceRole is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteResource_3() throws Exception {
        Resource r = new Resource();

        try {
            persistence.deleteResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. IllegalArgumentException If resource is null
     * or its id is UNSET_ID or its ResourceRole is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResource_1() throws Exception {
        try {
            persistence.updateResource(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. IllegalArgumentException If resource is null
     * or its id is UNSET_ID or its ResourceRole is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResource_2() throws Exception {
        try {
            persistence.updateResource(new Resource());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. IllegalArgumentException If resource is null
     * or its id is UNSET_ID or its ResourceRole is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResource_3() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setResourceRole(null);

        try {
            persistence.updateResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. IllegalArgumentException If resource is null
     * or its id is UNSET_ID or its ResourceRole is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResource_4() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setCreationUser(null);

        try {
            persistence.updateResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. IllegalArgumentException If resource is null
     * or its id is UNSET_ID or its ResourceRole is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResource_5() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setCreationTimestamp(null);

        try {
            persistence.updateResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. IllegalArgumentException If resource is null
     * or its id is UNSET_ID or its ResourceRole is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResource_6() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setModificationUser(null);

        try {
            persistence.updateResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. IllegalArgumentException If resource is null
     * or its id is UNSET_ID or its ResourceRole is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResource_7() throws Exception {
        Resource r = DBTestUtil.createResource(1, 1, 1);
        r.setModificationTimestamp(null);

        try {
            persistence.updateResource(r);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Resource loadResource(long resourceId) </code>. IllegalArgumentException If resourceId is <=
     * 0
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLoadResource_1() throws Exception {
        try {
            persistence.loadResource(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Resource loadResource(long resourceId) </code>. IllegalArgumentException If resourceId is <=
     * 0
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLoadResource_2() throws Exception {
        try {
            persistence.loadResource(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotification(long user, long project, long notificationType, String operator)</code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddNotification_1() throws Exception {
        try {
            persistence.addNotification(0, 1, 1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotification(long user, long project, long notificationType, String operator)</code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddNotification_2() throws Exception {
        try {
            persistence.addNotification(-1, 1, 1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotification(long user, long project, long notificationType, String operator)</code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddNotification_3() throws Exception {
        try {
            persistence.addNotification(1, 0, 1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotification(long user, long project, long notificationType, String operator)</code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddNotification_4() throws Exception {
        try {
            persistence.addNotification(1, -1, 1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotification(long user, long project, long notificationType, String operator)</code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddNotification_5() throws Exception {
        try {
            persistence.addNotification(1, 1, 0, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotification(long user, long project, long notificationType, String operator)</code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddNotification_6() throws Exception {
        try {
            persistence.addNotification(1, 1, -1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotification(long user, long project, long notificationType, String operator)</code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testAddNotification_7() throws Exception {
        try {
            persistence.addNotification(1, 1, 1, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>removeNotification(long user, long project, long notificationType, String operator) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveNotification_1() throws Exception {
        try {
            persistence.removeNotification(0, 1, 1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>removeNotification(long user, long project, long notificationType, String operator) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveNotification_2() throws Exception {
        try {
            persistence.removeNotification(-1, 1, 1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>removeNotification(long user, long project, long notificationType, String operator) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveNotification_3() throws Exception {
        try {
            persistence.removeNotification(1, 0, 1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>removeNotification(long user, long project, long notificationType, String operator) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveNotification_4() throws Exception {
        try {
            persistence.removeNotification(1, -1, 1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>removeNotification(long user, long project, long notificationType, String operator) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveNotification_5() throws Exception {
        try {
            persistence.removeNotification(1, 1, 0, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>removeNotification(long user, long project, long notificationType, String operator) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveNotification_6() throws Exception {
        try {
            persistence.removeNotification(10, 1, -1, "tc");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>removeNotification(long user, long project, long notificationType, String operator) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0, operator is null.
     *
     * @throws Exception
     *             to junit
     */
    public void testRemoveNotification_7() throws Exception {
        try {
            persistence.removeNotification(10, 1, 1, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadNotification_1() throws Exception {
        try {
            persistence.loadNotification(0, 1, 1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadNotification_2() throws Exception {
        try {
            persistence.loadNotification(-1, 1, 1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadNotification_3() throws Exception {
        try {
            persistence.loadNotification(1, -1, 1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadNotification_4() throws Exception {
        try {
            persistence.loadNotification(1, 0, 1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadNotification_5() throws Exception {
        try {
            persistence.loadNotification(1, 1, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * IllegalArgumentException If user, project, or notificationType is <= 0
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadNotification_6() throws Exception {
        try {
            persistence.loadNotification(1, 1, -1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>. IllegalArgumentException
     * If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or its
     * creation/modification user/date are null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddNotificationType_1() throws Exception {
        try {
            persistence.addNotificationType(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>. IllegalArgumentException
     * If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or its
     * creation/modification user/date are null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddNotificationType_2() throws Exception {
        try {
            persistence.addNotificationType(new NotificationType());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>. IllegalArgumentException
     * If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or its
     * creation/modification user/date are null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddNotificationType_3() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setName(null);
        try {
            persistence.addNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>. IllegalArgumentException
     * If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or its
     * creation/modification user/date are null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddNotificationType_4() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setDescription(null);
        try {
            persistence.addNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>. IllegalArgumentException
     * If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or its
     * creation/modification user/date are null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddNotificationType_5() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setModificationUser(null);
        try {
            persistence.addNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>. IllegalArgumentException
     * If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or its
     * creation/modification user/date are null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddNotificationType_6() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setModificationTimestamp(null);
        try {
            persistence.addNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>. IllegalArgumentException
     * If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or its
     * creation/modification user/date are null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddNotificationType_7() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setCreationUser(null);
        try {
            persistence.addNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>. IllegalArgumentException
     * If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or its
     * creation/modification user/date are null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddNotificationType_8() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setCreationTimestamp(null);

        try {
            persistence.addNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void deleteNotificationType(NotificationType notificationType) </code>.
     * IllegalArgumentException If notificationType is null or its id is UNSET_ID.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteNotificationType_1() throws Exception {
        try {
            persistence.deleteNotificationType(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void deleteNotificationType(NotificationType notificationType) </code>.
     * IllegalArgumentException If notificationType is null or its id is UNSET_ID.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteNotificationType_2() throws Exception {
        try {
            persistence.deleteNotificationType(new NotificationType());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateNotificationType(NotificationType notificationType) </code>.
     * IllegalArgumentException If notificationType is null or its id is UNSET_ID or its name/description is null or its
     * modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateNotificationType_1() throws Exception {
        try {
            persistence.updateNotificationType(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateNotificationType(NotificationType notificationType) </code>.
     * IllegalArgumentException If notificationType is null or its id is UNSET_ID or its name/description is null or its
     * modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateNotificationType_2() throws Exception {
        try {
            persistence.updateNotificationType(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateNotificationType(NotificationType notificationType) </code>.
     * IllegalArgumentException If notificationType is null or its id is UNSET_ID or its name/description is null or its
     * modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateNotificationType_3() throws Exception {
        try {
            persistence.updateNotificationType(new NotificationType());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateNotificationType(NotificationType notificationType) </code>.
     * IllegalArgumentException If notificationType is null or its id is UNSET_ID or its name/description is null or its
     * modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateNotificationType_4() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setName(null);

        try {
            persistence.updateNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateNotificationType(NotificationType notificationType) </code>.
     * IllegalArgumentException If notificationType is null or its id is UNSET_ID or its name/description is null or its
     * modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateNotificationType_5() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setDescription(null);

        try {
            persistence.updateNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateNotificationType(NotificationType notificationType) </code>.
     * IllegalArgumentException If notificationType is null or its id is UNSET_ID or its name/description is null or its
     * modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateNotificationType_6() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setModificationTimestamp(null);

        try {
            persistence.updateNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateNotificationType(NotificationType notificationType) </code>.
     * IllegalArgumentException If notificationType is null or its id is UNSET_ID or its name/description is null or its
     * modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateNotificationType_7() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(1);
        type.setName(null);

        try {
            persistence.updateNotificationType(type);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>NotificationType loadNotificationType(long notificationTypeId) </code>.
     * IllegalArgumentException If notificationTypeId is <= 0
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLoadNotificationType_1() throws Exception {
        try {
            persistence.loadNotificationType(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>NotificationType loadNotificationType(long notificationTypeId) </code>.
     * IllegalArgumentException If notificationTypeId is <= 0
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLoadNotificationType_2() throws Exception {
        try {
            persistence.loadNotificationType(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its creation/modification date/user
     * is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResourceRole_1() throws Exception {
        try {
            persistence.addResourceRole(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its creation/modification date/user
     * is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResourceRole_2() throws Exception {
        try {
            persistence.addResourceRole(new ResourceRole());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its creation/modification date/user
     * is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResourceRole_3() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setName(null);
        try {
            persistence.addResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its creation/modification date/user
     * is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResourceRole_4() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setDescription(null);
        try {
            persistence.addResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its creation/modification date/user
     * is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResourceRole_5() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setCreationUser(null);
        try {
            persistence.addResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its creation/modification date/user
     * is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResourceRole_6() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setCreationTimestamp(null);
        try {
            persistence.addResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its creation/modification date/user
     * is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResourceRole_7() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setModificationUser(null);
        try {
            persistence.addResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its creation/modification date/user
     * is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddResourceRole_8() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setModificationTimestamp(null);

        try {
            persistence.addResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>deleteResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * notificationType is null or its id is UNSET_ID.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteResourceRole_1() throws Exception {
        try {
            persistence.deleteResourceRole(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>deleteResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * notificationType is null or its id is UNSET_ID.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteResourceRole_2() throws Exception {
        try {
            persistence.deleteResourceRole(new ResourceRole());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResourceRole_1() throws Exception {
        try {
            persistence.updateResourceRole(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResourceRole_2() throws Exception {
        try {
            persistence.updateResourceRole(new ResourceRole());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResourceRole_4() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setName(null);
        try {
            persistence.updateResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResourceRole_5() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setDescription(null);
        try {
            persistence.updateResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResourceRole_6() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setCreationUser(null);
        try {
            persistence.updateResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResourceRole_7() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setCreationTimestamp(null);
        try {
            persistence.updateResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResourceRole_8() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setModificationUser(null);
        try {
            persistence.updateResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole) </code>. IllegalArgumentException If
     * resourceRole is null or its id is UNSET_ID or its name/description is null or its modification user/date is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateResourceRole_9() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(1);
        role.setModificationTimestamp(null);
        try {
            persistence.updateResourceRole(role);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>ResourceRole loadResourceRole(long resourceRoleId) </code>. IllegalArgumentException If
     * resourceRoleId is <= 0
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadResourceRole_1() throws Exception {
        try {
            persistence.loadResourceRole(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>ResourceRole loadResourceRole(long resourceRoleId) </code>. IllegalArgumentException If
     * resourceRoleId is <= 0
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadResourceRole_2() throws Exception {
        try {
            persistence.loadResourceRole(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>. IllegalArgumentException If any id is <=
     * 0 or the array is null.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLoadResources_1() throws Exception {
        try {
            persistence.loadResources(( long[])null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>. IllegalArgumentException If any id is <=
     * 0 or the array is null.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLoadResources_2() throws Exception {
        try {
            persistence.loadResources(new long[] {0, -1});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>NotificationType[] loadNotificationTypes(long[] notificationTypeIds) </code>.
     * IllegalArgumentException If any id is <= 0 or the array is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadNotificationTypes_1() throws Exception {
        try {
            persistence.loadNotificationTypes((long[])null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>NotificationType[] loadNotificationTypes(long[] notificationTypeIds) </code>.
     * IllegalArgumentException If any id is <= 0 or the array is null
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadNotificationTypes_2() throws Exception {
        try {
            persistence.loadNotificationTypes(new long[] {0, -1, 1});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>ResourceRole[] loadResourceRoles(long[] resourceRoleIds) </code>. IllegalArgumentException If
     * any id is <= 0 or the array is null.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLoadResourceRoles_1() throws Exception {
        try {
            persistence.loadResourceRoles((long[])null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>ResourceRole[] loadResourceRoles(long[] resourceRoleIds) </code>. IllegalArgumentException If
     * any id is <= 0 or the array is null.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLoadResourceRoles_2() throws Exception {
        try {
            persistence.loadResourceRoles(new long[] {0, -1, 1});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Teste method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     * IllegalArgumentException If the three arrays don't all have the same number of elements Any array is null, all
     * three arrays do not have the same length, any id is <= 0
     *
     * @throws Exception
     *             to junt.
     */
    public void testLoadNotifications_1() throws Exception {
        try {
            persistence.loadNotifications(null, new long[] {1}, new long[] {2});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Teste method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     * IllegalArgumentException If the three arrays don't all have the same number of elements Any array is null, all
     * three arrays do not have the same length, any id is <= 0
     *
     * @throws Exception
     *             to junt.
     */
    public void testLoadNotifications_2() throws Exception {
        try {
            persistence.loadNotifications(new long[] {1}, null, new long[] {2});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Teste method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     * IllegalArgumentException If the three arrays don't all have the same number of elements Any array is null, all
     * three arrays do not have the same length, any id is <= 0
     *
     * @throws Exception
     *             to junt.
     */
    public void testLoadNotifications_3() throws Exception {
        try {
            persistence.loadNotifications(new long[] {1}, new long[] {1}, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Teste method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     * IllegalArgumentException If the three arrays don't all have the same number of elements Any array is null, all
     * three arrays do not have the same length, any id is <= 0
     *
     * @throws Exception
     *             to junt.
     */
    public void testLoadNotifications_4() throws Exception {
        try {
            persistence.loadNotifications(new long[] {1}, new long[] {1}, new long[] {1, 2});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Teste method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     * IllegalArgumentException If the three arrays don't all have the same number of elements Any array is null, all
     * three arrays do not have the same length, any id is <= 0
     *
     * @throws Exception
     *             to junt.
     */
    public void testLoadNotifications_5() throws Exception {
        try {
            persistence.loadNotifications(new long[] {1}, new long[] {1}, new long[] {-1});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Teste method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     * IllegalArgumentException If the three arrays don't all have the same number of elements Any array is null, all
     * three arrays do not have the same length, any id is <= 0
     *
     * @throws Exception
     *             to junt.
     */
    public void testLoadNotifications_6() throws Exception {
        try {
            persistence.loadNotifications(new long[] {1}, new long[] {-1}, new long[] {1});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Teste method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     * IllegalArgumentException If the three arrays don't all have the same number of elements Any array is null, all
     * three arrays do not have the same length, any id is <= 0
     *
     * @throws Exception
     *             to junt.
     */
    public void testLoadNotifications_7() throws Exception {
        try {
            persistence.loadNotifications(new long[] {-1}, new long[] {1}, new long[] {1});
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }
}
