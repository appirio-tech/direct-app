/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import junit.framework.TestCase;

import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;

/**
 * Failure tests for class <code>SubmissionFilterBuilder</code>.
 *
 * @author assistant, TCSDEVELOPER
 * @since 1.0
 * @version 1.1
 */
public class SubmissionFilterBuilderTest extends TestCase {

    /**
     * Test method for createSubmissionIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateSubmissionIdFilterZeroId() {
        try {
            SubmissionFilterBuilder.createSubmissionIdFilter(0);
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
            SubmissionFilterBuilder.createSubmissionIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateUploadIdFilterZeroId() {
        try {
            SubmissionFilterBuilder.createUploadIdFilter(0);
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
            SubmissionFilterBuilder.createUploadIdFilter(-1);
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
            SubmissionFilterBuilder.createProjectIdFilter(0);
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
            SubmissionFilterBuilder.createProjectIdFilter(-1);
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
            SubmissionFilterBuilder.createResourceIdFilter(0);
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
            SubmissionFilterBuilder.createResourceIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmissionStatusIdFilter(long).
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateSubmissionStatusIdFilterZeroId() {
        try {
            SubmissionFilterBuilder.createSubmissionStatusIdFilter(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmissionStatusIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testCreateSubmissionStatusIdFilterNegativeId() {
        try {
            SubmissionFilterBuilder.createSubmissionStatusIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmissionTypeIdFilter(long).
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     */
    public void testcreateSubmissionTypeIdFilterNegativeId() {
        try {
            SubmissionFilterBuilder.createSubmissionTypeIdFilter(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
