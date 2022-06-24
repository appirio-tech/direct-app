/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql.failuretests;

import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.sql.SqlResourcePersistence;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Faliure test for AbstractResourcePersistence class.
 *
 * @author King_Bette
 * @version 1.1
 */
public class AbstractResourcePersistenceFailureTest extends TestCase {
    /**
     * This private instance is used in the test.
     */
    private SqlResourcePersistence persistence = null;

    /**
     * This private instance is used in the test.
     */
    private Resource resource = new Resource();
    /**
     * This private instance is used in the test.
     */
    private NotificationType notificationType = new NotificationType();
    /**
     * This private instance is used in the test.
     */
    private ResourceRole resourceRole = new ResourceRole();
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(AbstractResourcePersistenceFailureTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        ConfigManager.getInstance().add("failure/config.xml");
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        persistence = new SqlResourcePersistence(factory);
        resource.setId(1);
        resource.setCreationTimestamp(new Date());
        resource.setCreationUser("admin");
        resource.setModificationTimestamp(new Date());
        resource.setModificationUser("admin");
        resource.setResourceRole(new ResourceRole(1));

        notificationType.setId(1);
        notificationType.setName("name");
        notificationType.setDescription("description");
        notificationType.setCreationTimestamp(new Date());
        notificationType.setCreationUser("admin");
        notificationType.setModificationTimestamp(new Date());
        notificationType.setModificationUser("admin");

        resourceRole.setId(1);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        resourceRole.setCreationTimestamp(new Date());
        resourceRole.setCreationUser("admin");
        resourceRole.setModificationTimestamp(new Date());
        resourceRole.setModificationUser("admin");
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Failure test of
     * <code>SqlResourcePersistence(DBConnectionFactory connectionFactory)</code>
     * constructor.
     * <p>
     * connectionFactory is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSqlResourcePersistenceFailure1() throws Exception {
        try {
            new SqlResourcePersistence(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>SqlResourcePersistence(DBConnectionFactory connectionFactory, String connectionName)</code>
     * constructor.
     * <p>
     * connectionFactory is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSqlResourcePersistenceFailure2() throws Exception {
        try {
            new SqlResourcePersistence(null, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addResource(Resource resource)</code> method.
     * <p>
     * resource is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceFailure1() throws Exception {
        try {
            persistence.addResource(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addResource(Resource resource)</code> method.
     * <p>
     * resource's id is unset.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceFailure2() throws Exception {
        resource = new Resource();
        resource.setCreationTimestamp(new Date());
        resource.setCreationUser("admin");
        resource.setModificationTimestamp(new Date());
        resource.setModificationUser("admin");
        resource.setResourceRole(new ResourceRole(1));
        try {
            persistence.addResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addResource(Resource resource)</code> method.
     * <p>
     * resource's resource role is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceFailure3() throws Exception {
        resource.setResourceRole(null);
        try {
            persistence.addResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addResource(Resource resource)</code> method.
     * <p>
     * resource's creation timestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceFailure4() throws Exception {
        resource.setCreationTimestamp(null);
        try {
            persistence.addResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>addResource(Resource resource)</code> method.
     * <p>
     * resource's creation user is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceFailure5() throws Exception {
        resource.setCreationUser(null);
        try {
            persistence.addResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>addResource(Resource resource)</code> method.
     * <p>
     * resource's modification timestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceFailure6() throws Exception {
        resource.setModificationTimestamp(null);
        try {
            persistence.addResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>addResource(Resource resource)</code> method.
     * <p>
     * resource's modification user is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceFailure7() throws Exception {
        resource.setModificationUser(null);
        try {
            persistence.addResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>deleteResource(Resource resource)</code> method.
     * <p>
     * resource is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDeleteResourceFailure1() throws Exception {
        try {
            persistence.deleteResource(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>deleteResource(Resource resource)</code> method.
     * <p>
     * resource's id is unset.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDeleteResourceFailure2() throws Exception {
        resource = new Resource();
        resource.setCreationTimestamp(new Date());
        resource.setCreationUser("admin");
        resource.setModificationTimestamp(new Date());
        resource.setModificationUser("admin");
        resource.setResourceRole(new ResourceRole(1));
        try {
            persistence.deleteResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>deleteResource(Resource resource)</code> method.
     * <p>
     * resource's resource role is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDeleteResourceFailure3() throws Exception {
        resource.setResourceRole(null);
        try {
            persistence.deleteResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateResource(Resource resource)</code> method.
     * <p>
     * resource is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceFailure1() throws Exception {
        try {
            persistence.updateResource(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>updateResource(Resource resource)</code> method.
     * <p>
     * resource's id is unset.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceFailure2() throws Exception {
        resource = new Resource();
        resource.setCreationTimestamp(new Date());
        resource.setCreationUser("admin");
        resource.setModificationTimestamp(new Date());
        resource.setModificationUser("admin");
        resource.setResourceRole(new ResourceRole(1));
        try {
            persistence.updateResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateResource(Resource resource)</code> method.
     * <p>
     * resource's resource role is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceFailure3() throws Exception {
        resource.setResourceRole(null);
        try {
            persistence.updateResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateResource(Resource resource)</code> method.
     * <p>
     * resource's modification timestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceFailure4() throws Exception {
        resource.setModificationTimestamp(null);
        try {
            persistence.updateResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>updateResource(Resource resource)</code> method.
     * <p>
     * resource's modification user is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceFailure5() throws Exception {
        resource.setModificationUser(null);
        try {
            persistence.updateResource(resource);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadResource(long resourceId)</code> method.
     * <p>
     * resourceId is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourceFailure1() throws Exception {
        try {
            persistence.loadResource(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>loadResource(long resourceId)</code> method.
     * <p>
     * resourceId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourceFailure2() throws Exception {
        try {
            persistence.loadResource(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * user is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationFailure1() throws Exception {
        try {
            persistence.addNotification(0, 1, 1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * user is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationFailure2() throws Exception {
        try {
            persistence.addNotification(-1, 1, 1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>addNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * project is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationFailure3() throws Exception {
        try {
            persistence.addNotification(1, 0, 1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>addNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * project is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationFailure4() throws Exception {
        try {
            persistence.addNotification(1, -1, 1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>addNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * notificationType is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationFailure5() throws Exception {
        try {
            persistence.addNotification(1, 1, 0, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>addNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * notificationType is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationFailure6() throws Exception {
        try {
            persistence.addNotification(1, 1, -1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>addNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * operator is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationFailure7() throws Exception {
        try {
            persistence.addNotification(1, 1, 1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>removeNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * user is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveNotificationFailure1() throws Exception {
        try {
            persistence.removeNotification(0, 1, 1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>removeNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * user is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveNotificationFailure2() throws Exception {
        try {
            persistence.removeNotification(-1, 1, 1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>removeNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * project is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveNotificationFailure3() throws Exception {
        try {
            persistence.removeNotification(1, 0, 1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>removeNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * project is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveNotificationFailure4() throws Exception {
        try {
            persistence.removeNotification(1, -1, 1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>removeNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * notificationType is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveNotificationFailure5() throws Exception {
        try {
            persistence.removeNotification(1, 1, 0, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>removeNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * notificationType is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveNotificationFailure6() throws Exception {
        try {
            persistence.removeNotification(1, 1, -1, "admin");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>removeNotification(long user, long project, long notificationType, String operator)</code>
     * method.
     * <p>
     * operator is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveNotificationFailure7() throws Exception {
        try {
            persistence.removeNotification(1, 1, 1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>loadNotification(long user, long project, long notificationType)</code>
     * method.
     * <p>
     * user is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationFailure1() throws Exception {
        try {
            persistence.loadNotification(0, 1, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>loadNotification(long user, long project, long notificationType)</code>
     * method.
     * <p>
     * user is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationFailure2() throws Exception {
        try {
            persistence.loadNotification(-1, 1, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotification(long user, long project, long notificationType)</code>
     * method.
     * <p>
     * project is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationFailure3() throws Exception {
        try {
            persistence.loadNotification(1, 0, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotification(long user, long project, long notificationType)</code>
     * method.
     * <p>
     * project is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationFailure4() throws Exception {
        try {
            persistence.loadNotification(1, -1, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotification(long user, long project, long notificationType)</code>
     * method.
     * <p>
     * notificationType is zero.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationFailure5() throws Exception {
        try {
            persistence.loadNotification(1, 1, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotification(long user, long project, long notificationType)</code>
     * method.
     * <p>
     * notificationType is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationFailure6() throws Exception {
        try {
            persistence.loadNotification(1, 1, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationTypeFailure1() throws Exception {
        try {
            persistence.addNotificationType(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's id is unset.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationTypeFailure2() throws Exception {
        notificationType = new NotificationType();
        notificationType.setCreationTimestamp(new Date());
        notificationType.setCreationUser("admin");
        notificationType.setModificationTimestamp(new Date());
        notificationType.setModificationUser("admin");
        notificationType.setName("name");
        notificationType.setDescription("description");
        try {
            persistence.addNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationTypeFailure3() throws Exception {
        notificationType.setName(null);
        try {
            persistence.addNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's description is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationTypeFailure4() throws Exception {
        notificationType.setDescription(null);
        try {
            persistence.addNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's creation timestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationTypeFailure5() throws Exception {
        notificationType.setCreationTimestamp(null);
        try {
            persistence.addNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>addNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's creation user is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationTypeFailure6() throws Exception {
        notificationType.setCreationUser(null);
        try {
            persistence.addNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>addNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's modification timestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationTypeFailure7() throws Exception {
        notificationType.setModificationTimestamp(null);
        try {
            persistence.addNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>addNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's modification user is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddNotificationTypeFailure8() throws Exception {
        notificationType.setModificationUser(null);
        try {
            persistence.addNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>deleteNotificationType(NotificationType notificationType)</code>
     * method.
     * <p>
     * notificationType is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDeleteNotificationTypeFailure1() throws Exception {
        try {
            persistence.deleteNotificationType(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>deleteNotificationType(NotificationType notificationType)</code>
     * method.
     * <p>
     * notificationType's id is unset.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDeleteNotificationTypeFailure2() throws Exception {
        notificationType = new NotificationType();
        try {
            persistence.deleteNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateNotificationTypeFailure1() throws Exception {
        try {
            persistence.updateNotificationType(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's id is unset.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateNotificationTypeFailure2() throws Exception {
        notificationType = new NotificationType();
        notificationType.setCreationTimestamp(new Date());
        notificationType.setCreationUser("admin");
        notificationType.setModificationTimestamp(new Date());
        notificationType.setModificationUser("admin");
        notificationType.setName("name");
        notificationType.setDescription("description");
        try {
            persistence.updateNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateNotificationTypeFailure3() throws Exception {
        notificationType.setName(null);
        try {
            persistence.updateNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's description is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateNotificationTypeFailure4() throws Exception {
        notificationType.setDescription(null);
        try {
            persistence.updateNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's modification timestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateNotificationTypeFailure5() throws Exception {
        notificationType.setModificationTimestamp(null);
        try {
            persistence.updateNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>updateNotificationType(NotificationType notificationType)</code> method.
     * <p>
     * notificationType's modification user is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateNotificationTypeFailure6() throws Exception {
        notificationType.setModificationUser(null);
        try {
            persistence.updateNotificationType(notificationType);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>loadNotificationType(long notificationTypeId)</code> method.
     * <p>
     * notificationTypeId is zero
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationTypeFailure1() throws Exception {
        try {
            persistence.loadNotificationType(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotificationType(long notificationTypeId)</code> method.
     * <p>
     * notificationTypeId is negative
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationTypeFailure2() throws Exception {
        try {
            persistence.loadNotificationType(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceRoleFailure1() throws Exception {
        try {
            persistence.addResourceRole(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's id is unset.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceRoleFailure2() throws Exception {
        resourceRole = new ResourceRole();
        resourceRole.setCreationTimestamp(new Date());
        resourceRole.setCreationUser("admin");
        resourceRole.setModificationTimestamp(new Date());
        resourceRole.setModificationUser("admin");
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            persistence.addResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceRoleFailure3() throws Exception {
        resourceRole.setName(null);
        try {
            persistence.addResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's description is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceRoleFailure4() throws Exception {
        resourceRole.setDescription(null);
        try {
            persistence.addResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>addResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's creation timestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceRoleFailure5() throws Exception {
        resourceRole.setCreationTimestamp(null);
        try {
            persistence.addResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>addResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's creation user is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceRoleFailure6() throws Exception {
        resourceRole.setCreationUser(null);
        try {
            persistence.addResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>addResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's modification timestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceRoleFailure7() throws Exception {
        resourceRole.setModificationTimestamp(null);
        try {
            persistence.addResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>addResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's modification user is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddResourceRoleFailure8() throws Exception {
        resourceRole.setModificationUser(null);
        try {
            persistence.addResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>deleteResourceRole(ResourceRole resourceRole)</code>
     * method.
     * <p>
     * resourceRole is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDeleteResourceRoleFailure1() throws Exception {
        try {
            persistence.deleteResourceRole(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>deleteResourceRole(ResourceRole resourceRole)</code>
     * method.
     * <p>
     * resourceRole's id is unset.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDeleteResourceRoleFailure2() throws Exception {
        resourceRole = new ResourceRole();
        try {
            persistence.deleteResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceRoleFailure1() throws Exception {
        try {
            persistence.updateResourceRole(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's id is unset.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceRoleFailure2() throws Exception {
        resourceRole = new ResourceRole();
        resourceRole.setCreationTimestamp(new Date());
        resourceRole.setCreationUser("admin");
        resourceRole.setModificationTimestamp(new Date());
        resourceRole.setModificationUser("admin");
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            persistence.updateResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceRoleFailure3() throws Exception {
        resourceRole.setName(null);
        try {
            persistence.updateResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's description is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceRoleFailure4() throws Exception {
        resourceRole.setDescription(null);
        try {
            persistence.updateResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's modification timestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceRoleFailure5() throws Exception {
        resourceRole.setModificationTimestamp(null);
        try {
            persistence.updateResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>updateResourceRole(ResourceRole resourceRole)</code> method.
     * <p>
     * resourceRole's modification user is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateResourceRoleFailure6() throws Exception {
        resourceRole.setModificationUser(null);
        try {
            persistence.updateResourceRole(resourceRole);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>loadResourceRole(long resourceRoleId)</code> method.
     * <p>
     * resourceRoleId is zero
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourceRoleFailure1() throws Exception {
        try {
            persistence.loadResourceRole(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadResourceRole(long resourceRoleId)</code> method.
     * <p>
     * resourceRoleId is negative
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourceRoleFailure2() throws Exception {
        try {
            persistence.loadResourceRole(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadResources(long[] resourceIds)</code> method.
     * <p>
     * resourceIds is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourcesFailure1() throws Exception {
        try {
            persistence.loadResources((long[])null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadResources(long[] resourceIds)</code> method.
     * <p>
     * resourceIds contain a zero id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourcesFailure2() throws Exception {
        try {
            persistence.loadResources(new long[]{0});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadResources(long[] resourceIds)</code> method.
     * <p>
     * resourceIds contain a negative id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourcesFailure3() throws Exception {
        try {
            persistence.loadResources(new long[]{-1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadNotificationTypes(long[] notificationTypeIds)</code> method.
     * <p>
     * notificationTypeIds is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationTypesFailure1() throws Exception {
        try {
            persistence.loadNotificationTypes((long[])null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadNotificationTypes(long[] notificationTypeIds)</code> method.
     * <p>
     * notificationTypeIds contain a zero id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationTypesFailure2() throws Exception {
        try {
            persistence.loadNotificationTypes(new long[]{0});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadNotificationTypes(long[] notificationTypeIds)</code> method.
     * <p>
     * notificationTypeIds contain a negative id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationTypesFailure3() throws Exception {
        try {
            persistence.loadNotificationTypes(new long[]{-1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadResourceRoles(long[] resourceRoleIds)</code> method.
     * <p>
     * resourceRoleIds is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourceRolesFailure1() throws Exception {
        try {
            persistence.loadResourceRoles((long[])null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadResourceRoles(long[] resourceRoleIds)</code> method.
     * <p>
     * resourceRoleIds contain a zero id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourceRolesFailure2() throws Exception {
        try {
            persistence.loadResourceRoles(new long[]{0});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>loadResourceRoles(long[] resourceRoleIds)</code> method.
     * <p>
     * resourceRoleIds contain a negative id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadResourceRolesFailure3() throws Exception {
        try {
            persistence.loadResourceRoles(new long[]{-1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * userIds is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure1() throws Exception {
        try {
            persistence.loadNotifications(null, new long[0], new long[0]);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * projectIds is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure2() throws Exception {
        try {
            persistence.loadNotifications(new long[0], null, new long[0]);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * notificationTypes is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure3() throws Exception {
        try {
            persistence.loadNotifications(new long[0], new long[0], null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * array's length is not equal.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure4() throws Exception {
        try {
            persistence.loadNotifications(new long[]{1}, new long[]{1}, new long[]{1, 2});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * userIds contain a zero id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure5() throws Exception {
        try {
            persistence.loadNotifications(new long[]{0}, new long[]{1}, new long[]{1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * userIds contain a negative id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure6() throws Exception {
        try {
            persistence.loadNotifications(new long[]{-1}, new long[]{1}, new long[]{1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * projectIds contain a zero id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure7() throws Exception {
        try {
            persistence.loadNotifications(new long[]{1}, new long[]{0}, new long[]{1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * projectIds contain a negative id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure8() throws Exception {
        try {
            persistence.loadNotifications(new long[]{1}, new long[]{-1}, new long[]{1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * notificationTypes contain a zero id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure9() throws Exception {
        try {
            persistence.loadNotifications(new long[]{1}, new long[]{1}, new long[]{0});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)</code>
     * method.
     * <p>
     * notificationTypes contain a negative id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLoadNotificationsFailure10() throws Exception {
        try {
            persistence.loadNotifications(new long[]{1}, new long[]{1}, new long[]{-1});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
