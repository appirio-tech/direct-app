/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.search.DeliverableFilterBuilder;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test cases for <code>DeliverableFilterBuilder</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class DeliverableFilterBuilderAccuracyTests extends TestCase {
    /**
     * Tests createDeliverableIdFilter method.
     */
    public void testCreateDeliverableIdFilter() {
        assertNotNull("Unable to create deliverable id filter", DeliverableFilterBuilder.createDeliverableIdFilter(2));
    }

    /**
     * Tests createNameFilter method.
     */
    public void testCreateNameFilter() {
        assertNotNull("Unable to create name filter", DeliverableFilterBuilder.createNameFilter("name"));
        assertNotNull("Unable to create name filter", DeliverableFilterBuilder.createNameFilter(" "));
    }

    /**
     * Tests createProjectIdFilter method.
     */
    public void testCreateProjectIdFilter() {
        assertNotNull("Unable to create project id filter", DeliverableFilterBuilder.createProjectIdFilter(2));
    }

    /**
     * Tests createPhaseIdFilter method.
     */
    public void testCreatePhaseIdFilter() {
        assertNotNull("Unable to create phase id filter", DeliverableFilterBuilder.createPhaseIdFilter(2));
    }

    /**
     * Tests createSubmissionIdFilter method.
     */
    public void testCreateSubmissionIdFilter() {
        assertNotNull("Unable to create submission id filter", DeliverableFilterBuilder.createSubmissionIdFilter(2));
    }

    /**
     * Tests createResourceIdFilter method.
     */
    public void testCreateResourceIdFilter() {
        assertNotNull("Unable to create resource id filter", DeliverableFilterBuilder.createResourceIdFilter(2));
    }

    /**
     * Tests createRequiredFilter method.
     */
    public void testCreateRequiredFilter() {
        assertNotNull("Unable to create required filter", DeliverableFilterBuilder.createRequiredFilter(true));
    }
}
