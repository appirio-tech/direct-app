/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * Unit tests for the class: ResourceFilterBuilder.
 *
 * @author kinfkong, Xuchen
 * @version 1.1
 * @since 1.0
 */
public class ResourceFilterBuilderTest extends TestCase {

    /**
     * Tests method: createResourceIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateResourceIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createResourceIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: testCreateResourceIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateResourceIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createResourceIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.RESOURCE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createProjectIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateProjectIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createProjectIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createProjectIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateProjectIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createProjectIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.PROJECT_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createPhaseIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreatePhaseIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createPhaseIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createPhaseIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreatePhaseIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createPhaseIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.PHASE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createSubmissionIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateSubmissionIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createSubmissionIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createSubmissionIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateSubmissionIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createSubmissionIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createResourceRoleIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateResourceRoleIdFilter_NonPositive() {
        try {
            ResourceFilterBuilder.createResourceRoleIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createResourceRoleIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateResourceRoleIdFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createResourceRoleIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createExtensionPropertyValueFilter(String).
     *
     * With a null parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateExtensionPropertyValueFilter_Null() {
        try {
            ResourceFilterBuilder.createExtensionPropertyValueFilter(null);
            fail("The parameter cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createExtensionPropertyValueFilter(String).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateExtensionPropertyValueFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createExtensionPropertyValueFilter("This is a name");
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.EXTENSION_PROPERTY_VALUE_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                "This is a name", (String) ((EqualToFilter) filter).getValue());

    }

    /**
     * Tests method: createExtensionPropertyNameFilter(String).
     *
     * With a null parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateExtensionPropertyNameFilter_Null() {
        try {
            ResourceFilterBuilder.createExtensionPropertyNameFilter(null);
            fail("The parameter cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createExtensionPropertyNameFilter(String).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateExtensionPropertyNameFilter_Accuracy() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createExtensionPropertyNameFilter("This is a name");
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.EXTENSION_PROPERTY_NAME_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                "This is a name", (String) ((EqualToFilter) filter).getValue());

    }

    /**
     * Tests method: createNoProjectFilter().
     *
     * Checks if the filter is a null filter.
     */
    public void testCreateNoProjectFilter() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createNoProjectFilter();

        // check if it is a NullFilter
        assertTrue("The filter should be a null filter.", filter instanceof NullFilter);
    }

    /**
     * Tests method: createNoPhaseFilter().
     *
     * Checks if the filter is a null filter.
     */
    public void testCreateNoPhaseFilter() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createNoPhaseFilter();

        // check if it is a NullFilter
        assertTrue("The filter should be a null filter.", filter instanceof NullFilter);
    }

    /**
     * Tests method: createNoSubmissionFilter().
     *
     * Checks if the filter is a null filter.
     */
    public void testCreateNoSubmissionFilter() {
        // create the filter
        Filter filter = ResourceFilterBuilder.createNoSubmissionFilter();

        // check if it is a NullFilter
        assertTrue("The filter should be a null filter.", filter instanceof NullFilter);
    }

    /**
     * Tests createAnySubmissionIdFilter method with null argument.
     * It should throw IllegalArgumentException.
     */
    public void testCreateAnySubmissionIdFilter_Null() {
        try {
            ResourceFilterBuilder.createAnySubmissionIdFilter(null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Tests createAnySubmissionIdFilter method with array containing non-positive value.
     * It should throw IllegalArgumentException.
     */
    public void testCreateAnySubmissionIdFilter_NonPositiveElement() {
        try {
            ResourceFilterBuilder.createAnySubmissionIdFilter(new long[] {1, 2, -1});
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Tests createAnySubmissionIdFilter method with empty array argument.
     */
    public void testCreateAnySubmissionIdFilter_EmptyArray() {
        Filter filter = ResourceFilterBuilder.createAnySubmissionIdFilter(new long[0]);

        // It should be an instance of NullFilter
        assertTrue("It should be an instance of NullFilter.", filter instanceof NullFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME, ((NullFilter) filter).getName());
        // check the value
        assertNull("The value is not property set.", ((NullFilter) filter).getValue());
    }

    /**
     * Tests createAnySubmissionIdFilter method with proper array argument.
     */
    public void testCreateAnySubmissionIdFilter_Accuracy1() {
        Filter filter = ResourceFilterBuilder.createAnySubmissionIdFilter(new long[] {1, 2, 3});

        // It should be an instance of InFilter
        assertTrue("It should be an instance of InFilter.", filter instanceof InFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME, ((InFilter) filter).getName());
        // check the list
        Set expectedListSet = new HashSet(3);
        expectedListSet.add(new Long(1));
        expectedListSet.add(new Long(2));
        expectedListSet.add(new Long(3));
        assertEquals("The value is not property set.", expectedListSet, new HashSet(((InFilter) filter).getList()));
    }

    /**
     * Tests createAnySubmissionIdFilter method with proper array argument which contains duplicated ids.
     */
    public void testCreateAnySubmissionIdFilter_Accuracy2() {
        Filter filter = ResourceFilterBuilder.createAnySubmissionIdFilter(new long[] {1, 2, 3, 3, 2, 1, 4});

        // It should be an instance of InFilter
        assertTrue("It should be an instance of InFilter.", filter instanceof InFilter);
        // check the name
        assertEquals("The name is not property set.",
                ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME, ((InFilter) filter).getName());
        // check the list
        Set expectedListSet = new HashSet(4);
        expectedListSet.add(new Long(1));
        expectedListSet.add(new Long(2));
        expectedListSet.add(new Long(3));
        expectedListSet.add(new Long(4));
        assertEquals("The value is not property set.", expectedListSet, new HashSet(((InFilter) filter).getList()));
    }

    // test static fields.

}
