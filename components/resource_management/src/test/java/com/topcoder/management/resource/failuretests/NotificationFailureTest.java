/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;

import junit.framework.TestCase;

/**
 * Failure test for {@link Notification} class.
 *
 * @author mayi
 * @version 1.1
 * @since 1.0
 */
public class NotificationFailureTest extends TestCase {
    /**
     * A <code>NotificationType</code> instance to construct <code>Notification</code>.
     */
    private NotificationType notificationType = null;

    /**
     * Set up.
     * <p>Create the <code>notificationType</code> instance to test.</p>
     */
    protected void setUp() {
        notificationType = new NotificationType(1, "dummy");
    }

    /**
     * Test <code>Notification(long, NotificationType, long)</code> with zero project.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_ZeroProject() {
        try {
            new Notification(0, notificationType, 1L);
            fail("Should throw IllegalArgumentException for zero project.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Notification(long, NotificationType, long)</code> with negative project.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_NegativeProject() {
        try {
            new Notification(-2, notificationType, 1L);
            fail("Should throw IllegalArgumentException for negative project.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Notification(long, NotificationType, long)</code> with zero externalId.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_ZeroExternalId() {
        try {
            new Notification(1, notificationType, 0);
            fail("Should throw IllegalArgumentException for zero externalId.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Notification(long, NotificationType, long)</code> with negative externalId.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_NegativeExternalId() {
        try {
            new Notification(2, notificationType, -1L);
            fail("Should throw IllegalArgumentException for negative externalId.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Notification(long, NotificationType, long)</code> with null notificationType.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_NullNotificationType() {
        try {
            new Notification(2, null, 1L);
            fail("Should throw IllegalArgumentException for null notificationType.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
