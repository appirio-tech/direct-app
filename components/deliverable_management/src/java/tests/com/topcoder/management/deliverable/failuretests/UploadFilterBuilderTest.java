/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.search.DeliverableFilterBuilder;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.deliverable.search.UploadFilterBuilder;

import junit.framework.TestCase;

/**
 * Failure tests for class <code>UploadFilterBuilder</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class UploadFilterBuilderTest extends TestCase {

    /**
     * Test method for createUploadIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateUploadIdFilterZeroId() {
        try {
            UploadFilterBuilder.createUploadIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateUploadIdFilterNegativeId() {
        try {
            UploadFilterBuilder.createUploadIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadTypeIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateUploadTypeIdFilterZeroId() {
        try {
            UploadFilterBuilder.createUploadTypeIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadTypeIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateUploadTypeIdFilterNegativeId() {
        try {
            UploadFilterBuilder.createUploadTypeIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadStatusIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateUploadStatusIdFilterZeroId() {
        try {
            UploadFilterBuilder.createUploadStatusIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadStatusIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateUploadStatusIdFilterNegativeId() {
        try {
            UploadFilterBuilder.createUploadStatusIdFilter(-1);
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
            UploadFilterBuilder.createProjectIdFilter(0);
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
            UploadFilterBuilder.createProjectIdFilter(-1);
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
            UploadFilterBuilder.createResourceIdFilter(0);
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
            UploadFilterBuilder.createResourceIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
