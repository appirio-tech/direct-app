/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.search.UploadFilterBuilder;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test cases for <code>UploadFilterBuilder</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class UploadFilterBuilderAccuracyTests extends TestCase {
    /**
     * Tests createUploadIdFilter method.
     */
    public void testCreateUploadIdFilter() {
        assertNotNull("Unable to create upload id filter", UploadFilterBuilder.createUploadIdFilter(16));
    }

    /**
     * Tests createUploadTypeIdFilter method.
     */
    public void testCreateUploadTypeIdFilter() {
        assertNotNull("Unable to create upload type id filter", UploadFilterBuilder.createUploadTypeIdFilter(16));
    }

    /**
     * Tests createUploadStatusIdFilter method.
     */
    public void testCreateUploadStatusIdFilter() {
        assertNotNull("Unable to create upload status id filter", UploadFilterBuilder.createUploadStatusIdFilter(16));
    }

    /**
     * Tests createProjectIdFilter method.
     */
    public void testCreateProjectIdFilter() {
        assertNotNull("Unable to create project id filter", UploadFilterBuilder.createProjectIdFilter(16));
    }

    /**
     * Tests createResourceIdFilter method.
     */
    public void testCreateResourceIdFilter() {
        assertNotNull("Unable to create resource id filter", UploadFilterBuilder.createResourceIdFilter(16));
    }
}
