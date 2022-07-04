/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests.search;

import com.topcoder.management.resource.search.NotificationFilterBuilder;

import junit.framework.TestCase;

/**
 * Failure tests for <code>NotificationFilterBuilder</code>.
 *
 * @author mayi
 * @version 1.1
 * @since 1.0
 */
public class NotificationFilterBuilderFailureTest extends TestCase {

    /**
     * Test <code>createNotificationTypeIdFilter(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateNotificationTypeIdFilter_ZeroId() {
        try {
            NotificationFilterBuilder.createNotificationTypeIdFilter(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createNotificationTypeIdFilter(long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateNotificationTypeIdFilter_NegativeId() {
        try {
            NotificationFilterBuilder.createNotificationTypeIdFilter(-2L);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createExternalRefIdFilter(long)</code> with zero external ref-id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateExternalRefIdFilter_ZeroId() {
        try {
            NotificationFilterBuilder.createExternalRefIdFilter(0);
            fail("Should throw IllegalArgumentException for zero value.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createExternalRefIdFilter(long)</code> with negative external ref-id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateExternalRefIdFilter_NegativeId() {
        try {
            NotificationFilterBuilder.createExternalRefIdFilter(-299L);
            fail("Should throw IllegalArgumentException for negative value.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createProjectIdFilter(long)</code> with zero project id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateProjectIdFilter_ZeroId() {
        try {
            NotificationFilterBuilder.createProjectIdFilter(0);
            fail("Should throw IllegalArgumentException for zero value.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createProjectIdFilter(long)</code> with negative project id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateProjectIdFilter_NegativeId() {
        try {
            NotificationFilterBuilder.createProjectIdFilter(-1L);
            fail("Should throw IllegalArgumentException for negative value.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
