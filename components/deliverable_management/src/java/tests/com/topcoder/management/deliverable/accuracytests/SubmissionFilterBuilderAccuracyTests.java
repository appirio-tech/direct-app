/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test cases for <code>SubmissionFilterBuilder</code>.
 * </p>
 *
 * @author skatou, FireIce
 * @version 1.1
 */
public class SubmissionFilterBuilderAccuracyTests extends TestCase {
    /**
     * Tests createSubmissionIdFilter method.
     */
    public void testCreateSubmissionIdFilter() {
        assertNotNull("Unable to create submission id filter", SubmissionFilterBuilder.createSubmissionIdFilter(32));
    }

    /**
     * Tests createUploadIdFilter method.
     */
    public void testCreateUploadIdFilter() {
        assertNotNull("Unable to create upload id filter", SubmissionFilterBuilder.createUploadIdFilter(32));
    }

    /**
     * Tests createProjectIdFilter method.
     */
    public void testCreateProjectIdFilter() {
        assertNotNull("Unable to create project id filter", SubmissionFilterBuilder.createProjectIdFilter(32));
    }

    /**
     * Tests createResourceIdFilter method.
     */
    public void testCreateResourceIdFilter() {
        assertNotNull("Unable to create resource id filter", SubmissionFilterBuilder.createResourceIdFilter(32));
    }

    /**
     * Tests createSubmissionStatusIdFilter method.
     */
    public void testCreateSubmissionStatusIdFilter() {
        assertNotNull("Unable to create submission status id filter",
            SubmissionFilterBuilder.createSubmissionStatusIdFilter(32));
    }

    /**
     * Tests createSubmissionTypeIdFilter method.
     */
    public void testCreateSubmissionTypeIdFilter() {
        assertNotNull("Unable to create submission type id filter",
            SubmissionFilterBuilder.createSubmissionTypeIdFilter(32));
    }
}
