/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>ResourceRoleFilterBuilder</code> class. It tests the
 * createNameFilter(String), createNoPhaseTypeFilter(),
 * createPhaseTypeIdFilter(long), and createResourceRoleIdFilter(long) methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceRoleFilterBuilderAccuracyTest extends TestCase {
    /**
     * <p>
     * Test suite of <code>ResourceRoleFilterBuilderAccuracyTest</code>.
     * </p>
     * @return Test suite of <code>ResourceRoleFilterBuilderAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ResourceRoleFilterBuilderAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of the <code>createNameFilter(String)</code> method.
     * </p>
     */
    public void testMethod_createNameFilter() {
        String name = "rolename";
        Filter filter = ResourceRoleFilterBuilder.createNameFilter(name);

        // check null here.
        assertNotNull("Create NameFilter failed.", filter);

        // check the type here.
        assertTrue("The 'NameFilter' should extend EqualToFilter.", filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.", ResourceRoleFilterBuilder.NAME_FIELD_NAME,
                ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", name,
                ((String) ((EqualToFilter) filter).getValue()));
    }

    /**
     * <p>
     * Accuracy test of the <code>createPhaseTypeIdFilter(long)</code> method.
     * </p>
     */
    public void testMethod_createPhaseTypeIdFilter() {
        long phaseTypeId = 2;
        Filter filter = ResourceRoleFilterBuilder.createPhaseTypeIdFilter(phaseTypeId);

        // check null here.
        assertNotNull("Create PhaseTypeIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'PhaseTypeIdFilter' should extend EqualToFilter.", filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.", ResourceRoleFilterBuilder.PHASE_TYPE_ID_FIELD_NAME,
                ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", phaseTypeId, ((Long) ((EqualToFilter) filter)
                .getValue()).longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>createResourceRoleIdFilter(long)</code>
     * method.
     * </p>
     */
    public void testMethod_createResourceRoleIdFilter() {
        long resourceRoleId = Long.MAX_VALUE;
        Filter filter = ResourceRoleFilterBuilder.createResourceRoleIdFilter(resourceRoleId);

        // check null here.
        assertNotNull("Create ResourceRoleIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'ResourceRoleIdFilter' should extend EqualToFilter.", filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
                ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", resourceRoleId, ((Long) ((EqualToFilter) filter)
                .getValue()).longValue());
    }

    /**
     * <p>
     * Accuracy test of the static field of
     * <code>NotificationFilterBuilder</code> class.
     * </p>
     */
    public void testStaticField() {
        // check all static fields here.
        assertEquals("The 'NAME_FIELD_NAME' field is incorrect.", "name",
                ResourceRoleFilterBuilder.NAME_FIELD_NAME);
        assertEquals("The 'PHASE_TYPE_ID_FIELD_NAME' field is incorrect.", "phase_type_id",
                ResourceRoleFilterBuilder.PHASE_TYPE_ID_FIELD_NAME);
        assertEquals("The 'RESOURCE_ROLE_ID_FIELD_NAME' field is incorrect.", "resource_role_id",
                ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME);
    }
}
