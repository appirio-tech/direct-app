/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.AuditableResourceStructure;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>Notification</code> class. It tests the Notification(long,
 * NotificationType, long) constructor; getExternalId(), getNotificationType()
 * and getProject() methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class NotificationAccuracyTest extends TestCase {
    /**
     * <p>
     * The project id of <code>Notification</code> instance for test.
     * </p>
     */
    private long project = 1;

    /**
     * <p>
     * The external id of <code>Notification</code> instance for test.
     * </p>
     */
    private long externalId = 1234567890;

    /**
     * <p>
     * The instance of <code>NotificationType</code> for test.
     * </p>
     */
    private NotificationType type = new NotificationType(1, "NotificationType#1");

    /**
     * <p>
     * The instance of <code>Notification</code> for test.
     * </p>
     */
    private Notification notification = null;

    /**
     * <p>
     * Test suite of <code>NotificationAccuracyTest</code>.
     * </p>
     * @return Test suite of <code>NotificationAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(NotificationAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>Notification(long, NotificationType, long)</code>
     * constructor.
     * </p>
     */
    public void testNotificationCtor() {
        notification = new Notification(project, type, externalId);

        // check null here.
        assertNotNull("Create Notification failed.", notification);

        // check the type here.
        assertTrue("Notification should extend AuditableResourceStructure.",
                notification instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The project id should be: " + project + ".", project, notification.getProject());
        assertEquals("The externalId should be: " + externalId + ".", externalId, notification
                .getExternalId());
        assertNotNull("The type should not be null.", notification.getNotificationType());
    }

    /**
     * <p>
     * Accuracy test of the <code>getProject()</code> method.
     * </p>
     */
    public void testMethod_getProject() {
        notification = new Notification(project, type, externalId);

        // check the project id here.
        assertEquals("The project id should be: " + project + ".", project, notification.getProject());
    }

    /**
     * <p>
     * Accuracy test of the <code>getExternalId()</code> method.
     * </p>
     */
    public void testMethod_getExternalId() {
        notification = new Notification(project, type, externalId);

        // check the externalId here.
        assertEquals("The externalId should be: " + externalId + ".", externalId, notification
                .getExternalId());
    }

    /**
     * <p>
     * Accuracy test of the <code>getNotificationType()</code> method.
     * </p>
     */
    public void testMethod_getNotificationType() {
        notification = new Notification(project, type, externalId);

        // check the type here.
        assertEquals("The type id should be 1", 1, notification.getNotificationType().getId());
        assertEquals("The type name should be NotificationType#1", "NotificationType#1", notification
                .getNotificationType().getName());
    }
}
