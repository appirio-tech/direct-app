/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests;

import com.topcoder.management.resource.IdAlreadySetException;
import com.topcoder.management.resource.NotificationType;

import junit.framework.TestCase;

/**
 * Failure test for {@link NotificationType} class.
 *
 * @author mayi
 * @version 1.1
 * @since 1.0
 */
public class NotificationTypeFailureTest extends TestCase {
    /**
     * A <code>notificationType</code> to test against.
     */
    private NotificationType notificationType = null;

    /**
     * Set up.
     * <p>Initialize the <code>notificationType</code> instance to test.</p>
     */
    protected void setUp() {
        notificationType = new NotificationType();
    }

    /**
     * Test <code>NotificationType(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_Long_ZeroId() {
        try {
            new NotificationType(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>NotificationType(long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_Long_NegativeId() {
        try {
            new NotificationType(-1);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>NotificationType(long, String)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongString_ZeroId() {
        try {
            new NotificationType(0, "name");
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>NotificationType(long, String)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongString_NegativeId() {
        try {
            new NotificationType(-1, "name");
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>NotificationType(long, String)</code> with null name.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongString_NullName() {
        try {
            new NotificationType(1L, null);
            fail("Should throw IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_ZeroId() {
        try {
            notificationType.setId(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> with negative id after id is set.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_NegativeId() {
        notificationType.setId(1);

        try {
            notificationType.setId(-1);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> after id is set.
     * <p>IdAlreadySetException should be thrown.</p>
     */
    public void testSetId_AlreadySet() {
        notificationType.setId(1);

        try {
            notificationType.setId(1);
            fail("Should throw IdAlreadySetException for negative id.");
        } catch (IdAlreadySetException e) {
            // pass
        }
    }
}
