/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.search;

import java.util.List;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.NotFilter;

/**
 * Unit test for DeliverableFilterBuilder.
 *
 * @author singlewood
 * @version 1.0
 */
public class DeliverableFilterBuilderTests extends TestCase {

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the Deliverable.
     */
    private static final String DELIVERABLE_ID_FIELD_NAME = "deliverable_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the name of the Deliverable.
     */
    private static final String NAME_FIELD_NAME = "name";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the project of the Deliverable.
     */
    private static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the phase the Deliverable falls in.
     */
    private static final String PHASE_ID_FIELD_NAME = "phase_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the submission the Deliverable is associated with.
     */
    private static final String SUBMISSION_ID_FIELD_NAME = "submission_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the resource id of the Deliverable.
     */
    private static final String RESOURCE_ID_FIELD_NAME = "resource_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * whether the Deliverable is required or not.
     */
    private static final String REQUIRED_FIELD_NAME = "required";

    /**
     * Create the test instance.
     *
     * @throws Exception exception to JUnit.
     */
    public void setUp() throws Exception {
        // no operation.
    }

    /**
     * Clean the config.
     *
     * @throws Exception exception to JUnit.
     */
    public void tearDown() throws Exception {
        // no operation.
    }

    /**
     * Test the behavior of createDeliverableIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateDeliverableIdFilter_Invalid1() {
        try {
            DeliverableFilterBuilder.createDeliverableIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createDeliverableIdFilter, Check if it returns a correct filter.
     * If it is correct, the return filter will be a EqualToFilter, and it's name and value field
     * is set by createDeliverableIdFilter. No exception should be thrown.
     *
     */
    public void testCreateDeliverableIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) DeliverableFilterBuilder.createDeliverableIdFilter(12);
        assertEquals("createDeliverableIdFilter doesn't build a correct filter", DELIVERABLE_ID_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createDeliverableIdFilter doesn't build a correct filter", new Long(12),
                equalToFilter.getValue());
    }

    /**
     * Test the behavior of createNameFilter. Set the parameter deliverableId = null
     * IllegalArgumentException should be thrown.
     */
    public void testCreateNameFilter_Invalid1() {
        try {
            DeliverableFilterBuilder.createNameFilter(null);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createNameFilter, Check if it returns a correct filter.
     * If it is correct, the return filter will be a EqualToFilter, and it's name and value field
     * is set by createNameFilter. No exception should be thrown.
     *
     */
    public void testCreateNameFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) DeliverableFilterBuilder.createNameFilter("somebody");
        assertEquals("createNameFilter doesn't build a correct filter", NAME_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createNameFilter doesn't build a correct filter", "somebody",
                equalToFilter.getValue());
    }

    /**
     * Test the behavior of createProjectIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateProjectIdFilter_Invalid1() {
        try {
            DeliverableFilterBuilder.createProjectIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createProjectIdFilter, Check if it returns a correct filter.
     * If it is correct, the return filter will be a EqualToFilter, and it's name and value field
     * is set by createProjectIdFilter. No exception should be thrown.
     */
    public void testCreateProjectIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) DeliverableFilterBuilder.createProjectIdFilter(22);
        assertEquals("createProjectIdFilter doesn't build a correct filter", PROJECT_ID_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createProjectIdFilter doesn't build a correct filter", new Long(22),
                equalToFilter.getValue());
    }

    /**
     * Test the behavior of createPhaseIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreatePhaseIdFilter_Invalid1() {
        try {
            DeliverableFilterBuilder.createPhaseIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createPhaseIdFilter, Check if it returns a correct filter.
     * If it is correct, the return filter will be a EqualToFilter, and it's name and value field
     * is set by createPhaseIdFilter. No exception should be thrown.
     */
    public void testCreatePhaseIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) DeliverableFilterBuilder.createPhaseIdFilter(33);
        assertEquals("createPhaseIdFilter doesn't build a correct filter", PHASE_ID_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createPhaseIdFilter doesn't build a correct filter", new Long(33),
                equalToFilter.getValue());
    }

    /**
     * Test the behavior of createSubmissionIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateSubmissionIdFilter_Invalid1() {
        try {
            DeliverableFilterBuilder.createSubmissionIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createSubmissionIdFilter, Check if it returns a correct filter.
     * If it is correct, the return filter will be a EqualToFilter, and it's name and value field
     * is set by createSubmissionIdFilter. No exception should be thrown.
     */
    public void testCreateSubmissionIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) DeliverableFilterBuilder.createSubmissionIdFilter(44);
        assertEquals("createSubmissionIdFilter doesn't build a correct filter", SUBMISSION_ID_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createSubmissionIdFilter doesn't build a correct filter", new Long(44),
                equalToFilter.getValue());
    }

    /**
     * Test the behavior of createResourceIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateResourceIdFilter_Invalid1() {
        try {
            DeliverableFilterBuilder.createResourceIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createResourceIdFilter. Check if it returns a correct filter.
     * If it is correct, the return filter will be a EqualToFilter, and it's name and value field
     * is set by createResourceIdFilter. No exception should be thrown.
     */
    public void testCreateResourceIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) DeliverableFilterBuilder.createResourceIdFilter(55);
        assertEquals("createResourceIdFilter doesn't build a correct filter", RESOURCE_ID_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createResourceIdFilter doesn't build a correct filter", new Long(55),
                equalToFilter.getValue());
    }

    /**
     * Build a Filter using createRequiredFilter with parameter 'true'. Check if it returns a correct filter.
     * If it is correct, the return filter will be a NotFilter. NotFilter.getFilter() will return a EqualToFilter,
     * and it's name and value field is set by createRequiredFilter. No exception should be thrown.
     */
    public void testCreateRequiredFilter_Accuracy1() {
        NotFilter notFilter = (NotFilter) DeliverableFilterBuilder.createRequiredFilter(true);
        assertEquals("createRequiredFilter doesn't build a correct filter", REQUIRED_FIELD_NAME,
                ((EqualToFilter) notFilter.getFilter()).getName());
        assertEquals("createRequiredFilter doesn't build a correct filter", new Integer(0),
                ((EqualToFilter) notFilter.getFilter()).getValue());
    }

    /**
     * Build a Filter using createRequiredFilter. Check if it returns a correct filter.
     * If it is correct, the return filter will be a EqualToFilter, and it's name and value field
     * is set by createRequiredFilter. No exception should be thrown.
     */
    public void testCreateRequiredFilter_Accuracy2() {
        EqualToFilter equalToFilter = (EqualToFilter) DeliverableFilterBuilder.createRequiredFilter(false);
        assertEquals("createRequiredFilter doesn't build a correct filter", REQUIRED_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createRequiredFilter doesn't build a correct filter", new Integer(0),
                equalToFilter.getValue());
    }

    /**
     * Build a Filter using createDeliverableIdResourceIdFilter with parameter 'true'. Check if it returns a correct
     * filter. If it is correct, the return filter will be a NotFilter. NotFilter.getFilter() will return a
     * EqualToFilter, and it's name and value field is set by createDeliverableIdResourceIdFilter. No exception should
     * be thrown.
     */
    @SuppressWarnings("unchecked")
    public void testCreateDeliverableIdResourceIdFilter_Accuracy1() {
        AndFilter andFilter = (AndFilter) DeliverableFilterBuilder.createDeliverableIdResourceIdFilter(1, 2);

        List filters = andFilter.getFilters();
        assertEquals("createDeliverableIdResourceIdFilter doesn't build a correct filter", 2, filters.size());

        assertEquals("createDeliverableIdResourceIdFilter doesn't build a correct filter", DELIVERABLE_ID_FIELD_NAME,
            ((EqualToFilter) filters.get(0)).getName());
        assertEquals("createDeliverableIdResourceIdFilter doesn't build a correct filter", new Long(1),
            ((EqualToFilter) filters.get(0)).getValue());

        assertEquals("createDeliverableIdResourceIdFilter doesn't build a correct filter", RESOURCE_ID_FIELD_NAME,
            ((EqualToFilter) filters.get(1)).getName());
        assertEquals("createDeliverableIdResourceIdFilter doesn't build a correct filter", new Long(2),
            ((EqualToFilter) filters.get(1)).getValue());
    }

    /**
     * Test the behavior of createDeliverableIdResourceIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateDeliverableIdResourceIdFilter_Invalid1() {
        try {
            DeliverableFilterBuilder.createDeliverableIdResourceIdFilter(0, 1);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test the behavior of createDeliverableIdResourceIdFilter. Set the parameter resourceId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateDeliverableIdResourceIdFilter_Invalid2() {
        try {
            DeliverableFilterBuilder.createDeliverableIdResourceIdFilter(1, 0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }
}
