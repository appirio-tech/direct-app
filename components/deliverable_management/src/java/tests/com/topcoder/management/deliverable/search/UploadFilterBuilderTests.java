/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.search;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.EqualToFilter;

/**
 * Unit test for UploadFilterBuilder.
 *
 * @author singlewood
 * @version 1.0
 */
public class UploadFilterBuilderTests extends TestCase {

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the Upload.
     */
    private static final String UPLOAD_ID_FIELD_NAME = "upload_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the type id of the Upload.
     */
    private static final String UPLOAD_TYPE_ID_FIELD_NAME = "upload_type_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the status id of the Upload.
     */
    private static final String UPLOAD_STATUS_ID_FIELD_NAME = "upload_status_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the project the Upload is associated with.
     */
    private static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the resource the Upload is associated with.
     */
    private static final String RESOURCE_ID_FIELD_NAME = "resource_id";

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
     * Test the behavior of createUploadIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateUploadIdFilter_Invalid1() {
        try {
            UploadFilterBuilder.createUploadIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createUploadIdFilter, Check if it returns a correct filter. If it is
     * correct, the return filter will be a EqualToFilter, and it's name and value field is set by
     * createUploadIdFilter. No exception should be thrown.
     */
    public void testCreateUploadIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) UploadFilterBuilder.createUploadIdFilter(22);
        assertEquals("createUploadIdFilter doesn't build a correct filter", UPLOAD_ID_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createUploadIdFilter doesn't build a correct filter", new Long(22), equalToFilter
                .getValue());
    }

    /**
     * Test the behavior of createUploadTypeIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateUploadTypeIdFilter_Invalid1() {
        try {
            UploadFilterBuilder.createUploadTypeIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createUploadTypeIdFilter, Check if it returns a correct filter. If it is
     * correct, the return filter will be a EqualToFilter, and it's name and value field is set by
     * createUploadTypeIdFilter. No exception should be thrown.
     */
    public void testCreateUploadTypeIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) UploadFilterBuilder.createUploadTypeIdFilter(44);
        assertEquals("createUploadTypeIdFilter doesn't build a correct filter", UPLOAD_TYPE_ID_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createUploadTypeIdFilter doesn't build a correct filter", new Long(44), equalToFilter
                .getValue());
    }

    /**
     * Test the behavior of createUploadStatusIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateUploadStatusIdFilter_Invalid1() {
        try {
            UploadFilterBuilder.createUploadStatusIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createUploadStatusIdFilter, Check if it returns a correct filter. If
     * it is correct, the return filter will be a EqualToFilter, and it's name and value field is
     * set by createUploadStatusIdFilter. No exception should be thrown.
     */
    public void testCreateUploadStatusIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) UploadFilterBuilder
                .createUploadStatusIdFilter(33);
        assertEquals("createUploadStatusIdFilter doesn't build a correct filter",
                UPLOAD_STATUS_ID_FIELD_NAME, equalToFilter.getName());
        assertEquals("createUploadStatusIdFilter doesn't build a correct filter", new Long(33),
                equalToFilter.getValue());
    }

    /**
     * Test the behavior of createProjectIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateProjectIdFilter_Invalid1() {
        try {
            UploadFilterBuilder.createProjectIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createProjectIdFilter, Check if it returns a correct filter. If it is
     * correct, the return filter will be a EqualToFilter, and it's name and value field is set by
     * createProjectIdFilter. No exception should be thrown.
     */
    public void testCreateProjectIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) UploadFilterBuilder.createProjectIdFilter(33);
        assertEquals("createProjectIdFilter doesn't build a correct filter", PROJECT_ID_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createProjectIdFilter doesn't build a correct filter", new Long(33), equalToFilter
                .getValue());
    }

    /**
     * Test the behavior of createResourceIdFilter. Set the parameter deliverableId = 0
     * IllegalArgumentException should be thrown.
     */
    public void testCreateResourceIdFilter_Invalid1() {
        try {
            UploadFilterBuilder.createResourceIdFilter(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Build a Filter using createResourceIdFilter. Check if it returns a correct filter. If it is
     * correct, the return filter will be a EqualToFilter, and it's name and value field is set by
     * createResourceIdFilter. No exception should be thrown.
     */
    public void testCreateResourceIdFilter_Accuracy1() {
        EqualToFilter equalToFilter = (EqualToFilter) UploadFilterBuilder.createResourceIdFilter(55);
        assertEquals("createResourceIdFilter doesn't build a correct filter", RESOURCE_ID_FIELD_NAME,
                equalToFilter.getName());
        assertEquals("createResourceIdFilter doesn't build a correct filter", new Long(55), equalToFilter
                .getValue());
    }

}
