/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

import junit.framework.TestCase;

/**
 * Unit tests for the class: ResourceRoleFilterBuilder.
 *
 * @author kinfkong, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class ResourceRoleFilterBuilderTest extends TestCase {

    /**
     * Tests method: createResourceRoleIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateResourceRoleIdFilter_NonPositive() {
        try {
            ResourceRoleFilterBuilder.createResourceRoleIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: testCreateResourceRoleIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateResourceRoleIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceRoleFilterBuilder.createResourceRoleIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createNameFilter(String).
     *
     * With a null parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateNameFilter_Null() {
        try {
            ResourceRoleFilterBuilder.createNameFilter(null);
            fail("The parameter cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createNameFilter(String).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateNameFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceRoleFilterBuilder.createNameFilter("This is a name");
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceRoleFilterBuilder.NAME_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                "This is a name", (String) ((EqualToFilter) filter).getValue());

    }

    /**
     * Tests method: createPhaseTypeIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreatePhaseTypeIdFilter_NonPositive() {
        try {
            ResourceRoleFilterBuilder.createPhaseTypeIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: testCreatePhaseTypeIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreatePhaseTypeIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceRoleFilterBuilder.createPhaseTypeIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceRoleFilterBuilder.PHASE_TYPE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests createNoPhaseTypeFilter() method.
     * @since 1.1
     */
    public void testCreateNoPhaseTypeFilter_Accuracy() {
        Filter filter = ResourceRoleFilterBuilder.createNoPhaseTypeFilter();

        // It should be an instance of NullFilter
        assertTrue("It should be an instance of NullFilter.", filter instanceof NullFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceRoleFilterBuilder.PHASE_TYPE_ID_FIELD_NAME, ((NullFilter) filter).getName());
        // check the value
        assertNull("The value is not property set.", ((NullFilter) filter).getValue());
    }
}
