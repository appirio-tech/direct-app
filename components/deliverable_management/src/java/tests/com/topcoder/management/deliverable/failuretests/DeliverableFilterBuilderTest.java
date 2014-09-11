/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.search.DeliverableFilterBuilder;

import junit.framework.TestCase;

/**
 * Failure tests for class <code>DeliverableFilterBuilder</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DeliverableFilterBuilderTest extends TestCase {

    /**
     * Test method for createDeliverableIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateDeliverableIdFilterZeroId() {
        try {
            DeliverableFilterBuilder.createDeliverableIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createDeliverableIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateDeliverableIdFilterNegativeId() {
        try {
            DeliverableFilterBuilder.createDeliverableIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createNameFilter(java.lang.String).
     * The name is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateNameFilter() {
        try {
            DeliverableFilterBuilder.createNameFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createProjectIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateProjectIdFilter() {
        try {
            DeliverableFilterBuilder.createProjectIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createProjectIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateProjectIdFilterNegativeId() {
        try {
            DeliverableFilterBuilder.createProjectIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createPhaseIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreatePhaseIdFilterZeroId() {
        try {
            DeliverableFilterBuilder.createPhaseIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createPhaseIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreatePhaseIdFilterNegativeId() {
        try {
            DeliverableFilterBuilder.createPhaseIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmissionIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateSubmissionIdFilterZeroId() {
        try {
            DeliverableFilterBuilder.createSubmissionIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmissionIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateSubmissionIdFilterNegativeId() {
        try {
            DeliverableFilterBuilder.createSubmissionIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createResourceIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateResourceIdFilterZeroId() {
        try {
            DeliverableFilterBuilder.createResourceIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createResourceIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateResourceIdFilterNegativeId() {
        try {
            DeliverableFilterBuilder.createResourceIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
