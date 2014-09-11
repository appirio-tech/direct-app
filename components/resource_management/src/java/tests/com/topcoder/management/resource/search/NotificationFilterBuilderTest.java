/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * Unit tests for the class: NotificationFilterBuilder.
 *
 * @author kinfkong
 * @version 1.0
 */
public class NotificationFilterBuilderTest extends TestCase {

    /**
     * Tests method: createExternalRefIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateExternalRefIdFilter_NonPositive() {
        try {
            NotificationFilterBuilder.createExternalRefIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createExternalRefIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateExternalRefIdFilter_Accuracy() {
        // create the filter
        Filter filter = NotificationFilterBuilder.createExternalRefIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                NotificationFilterBuilder.EXTERNAL_REF_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
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
            NotificationFilterBuilder.createProjectIdFilter(0);
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
        Filter filter = NotificationFilterBuilder.createProjectIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                NotificationFilterBuilder.PROJECT_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

    /**
     * Tests method: createNotificationTypeIdFilter(Long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateNotificationTypeIdFilter_NonPositive() {
        try {
            NotificationFilterBuilder.createNotificationTypeIdFilter(0);
            fail("The parameter must be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: createNotificationTypeIdFilter(long).
     *
     * Checks if the filter is properly created.
     */
    public void testCreateNotificationTypeIdFilter_Accuracy() {
        // create the filter
        Filter filter = NotificationFilterBuilder.createNotificationTypeIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                NotificationFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                1, ((Long) ((EqualToFilter) filter).getValue()).longValue());

    }

}
