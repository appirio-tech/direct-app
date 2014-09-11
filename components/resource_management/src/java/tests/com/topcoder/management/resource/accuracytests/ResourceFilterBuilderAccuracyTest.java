/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.search.ResourceFilterBuilder;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>ResourceFilterBuilder</code> class. It tests the
 * createExtensionPropertyNameFilter(String),
 * createExtensionPropertyValueFilter(String), createNoPhaseFilter(),
 * createNoProjectFilter(), createNoSubmissionFilter(),
 * createPhaseIdFilter(long), createProjectIdFilter(long),
 * createResourceRoleIdFilter(long), createResourceRoleIdFilter(long) and
 * createSubmissionIdFilter(long) methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceFilterBuilderAccuracyTest extends TestCase {
    /**
     * <p>
     * Test suite of <code>ResourceFilterBuilderAccuracyTest</code>.
     * </p>
     * @return Test suite of <code>ResourceFilterBuilderAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ResourceFilterBuilderAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of the
     * <code>createExtensionPropertyNameFilter(String)</code> method.
     * </p>
     */
    public void testMethod_createExtensionPropertyNameFilter() {
        String name = "operator";
        Filter filter = ResourceFilterBuilder.createExtensionPropertyNameFilter(name);

        // check null here.
        assertNotNull("Create ExtensionPropertyNameFilter failed.", filter);

        // check the type here.
        assertTrue("The 'ExtensionPropertyNameFilter' should extend EqualToFilter.",
                filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
                ResourceFilterBuilder.EXTENSION_PROPERTY_NAME_FIELD_NAME, ((EqualToFilter) filter)
                        .getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", name, ((String) ((EqualToFilter) filter)
                .getValue()));
    }

    /**
     * <p>
     * Accuracy test of the
     * <code>createExtensionPropertyValueFilter(String)</code> method.
     * </p>
     */
    public void testMethod_createExtensionPropertyValueFilter() {
        String value = "tc";
        Filter filter = ResourceFilterBuilder.createExtensionPropertyValueFilter(value);

        // check null here.
        assertNotNull("Create ExtensionPropertyValueFilter failed.", filter);

        // check the type here.
        assertTrue("The 'ExtensionPropertyValueFilter' should extend EqualToFilter.",
                filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
                ResourceFilterBuilder.EXTENSION_PROPERTY_VALUE_FIELD_NAME, ((EqualToFilter) filter)
                        .getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", value, ((String) ((EqualToFilter) filter)
                .getValue()));
    }

    /**
     * <p>
     * Accuracy test of the <code>createPhaseIdFilter(long)</code> method.
     * </p>
     */
    public void testMethod_createPhaseIdFilter() {
        long phaseId = 1;
        Filter filter = ResourceFilterBuilder.createPhaseIdFilter(phaseId);

        // check null here.
        assertNotNull("Create PhaseIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'PhaseIdFilter' should extend EqualToFilter.",
                filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.", ResourceFilterBuilder.PHASE_ID_FIELD_NAME,
                ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", phaseId, ((Long) ((EqualToFilter) filter)
                .getValue()).longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>createProjectIdFilter(long)</code> method.
     * </p>
     */
    public void testMethod_createProjectIdFilter() {
        long projectId = 100;
        Filter filter = ResourceFilterBuilder.createProjectIdFilter(projectId);

        // check null here.
        assertNotNull("Create ProjectIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'ProjectIdFilter' should extend EqualToFilter.",
                filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
                ResourceFilterBuilder.PROJECT_ID_FIELD_NAME, ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", projectId,
                ((Long) ((EqualToFilter) filter).getValue()).longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>createResourceIdFilter(long)</code> method.
     * </p>
     */
    public void testMethod_createResourceIdFilter() {
        long resourceId = Long.MAX_VALUE;
        Filter filter = ResourceFilterBuilder.createResourceIdFilter(resourceId);

        // check null here.
        assertNotNull("Create ResourceIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'ResourceIdFilter' should extend EqualToFilter.",
                filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
                ResourceFilterBuilder.RESOURCE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", resourceId,
                ((Long) ((EqualToFilter) filter).getValue()).longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>createResourceRoleIdFilter(long)</code>
     * method.
     * </p>
     */
    public void testMethod_createResourceRoleIdFilter() {
        long resourceRoleId = 1;
        Filter filter = ResourceFilterBuilder.createResourceRoleIdFilter(resourceRoleId);

        // check null here.
        assertNotNull("Create ResourceRoleIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'ResourceRoleIdFilter' should extend EqualToFilter.",
                filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
                ResourceFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, ((EqualToFilter) filter)
                        .getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", resourceRoleId,
                ((Long) ((EqualToFilter) filter).getValue()).longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>createSubmissionIdFilter(long)</code>
     * method.
     * </p>
     */
    public void testMethod_createSubmissionIdFilter() {
        long submissionId = Long.MAX_VALUE;
        Filter filter = ResourceFilterBuilder.createSubmissionIdFilter(submissionId);

        // check null here.
        assertNotNull("Create SubmissionIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'SubmissionIdFilter' should extend EqualToFilter.",
            filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
            ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME, ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", submissionId,
            ((Long) ((EqualToFilter) filter).getValue()).longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>createAnySubmissionIdFilter(long[])</code>
     * method.
     * </p>
     */
    public void testMethod_createAnySubmissionIdFilter() {
        long[] submissionIds = {1, 2, 3 };
        Filter filter = ResourceFilterBuilder.createAnySubmissionIdFilter(submissionIds);

        // check null here.
        assertNotNull("Create AnySubmissionIdFilter failed.", filter);

        if (filter instanceof InFilter) {
            assertTrue("The num of filter is equal to 3.", ((InFilter) filter).getList().size() == 3);
        } else if (filter instanceof OrFilter) {
            assertTrue("The num of filter is equal to 3.", ((OrFilter) filter).getFilters().size() == 3);
        } else {
            fail("AnySubmissionIdFilter type is incorrect.");
        }       
    }

    /**
     * <p>
     * Accuracy test of the static field of
     * <code>NotificationFilterBuilder</code> class.
     * </p>
     */
    public void testStaticField() {
        // check all static fields here.
        assertEquals("The 'EXTENSION_PROPERTY_NAME_FIELD_NAME' field is incorrect.", "name",
                ResourceFilterBuilder.EXTENSION_PROPERTY_NAME_FIELD_NAME);
        assertEquals("The 'EXTENSION_PROPERTY_VALUE_FIELD_NAME' field is incorrect.", "value",
                ResourceFilterBuilder.EXTENSION_PROPERTY_VALUE_FIELD_NAME);
        assertEquals("The 'PHASE_ID_FIELD_NAME' field is incorrect.", "project_phase_id",
                ResourceFilterBuilder.PHASE_ID_FIELD_NAME);
        assertEquals("The 'PROJECT_ID_FIELD_NAME' field is incorrect.", "project_id",
                ResourceFilterBuilder.PROJECT_ID_FIELD_NAME);
        assertEquals("The 'RESOURCE_ID_FIELD_NAME' field is incorrect.", "resource.resource_id",
                ResourceFilterBuilder.RESOURCE_ID_FIELD_NAME);
        assertEquals("The 'RESOURCE_ROLE_ID_FIELD_NAME' field is incorrect.", "resource_role_id",
                ResourceFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME);
        assertEquals("The 'SUBMISSION_ID_FIELD_NAME' field is incorrect.", "submission_id",
                ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME);
    }
}
