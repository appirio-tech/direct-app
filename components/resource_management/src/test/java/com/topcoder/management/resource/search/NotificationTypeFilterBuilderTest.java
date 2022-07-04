/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.TestCase;

/**
 * Unit tests for the class: NotificationTypeFilterBuilder.
 *
 * @author kinfkong
 * @version 1.0
 */
public class NotificationTypeFilterBuilderTest extends TestCase {

    /**
     * Tests method: createNotificationTypeIdFilter(long).
     *
     * With a non-positive parameter, IllegalArgumentException should be thrown.
     */
    public void testCreateNotificationTypeIdFilter_NonPositive() {
        try {
            NotificationTypeFilterBuilder.createNotificationTypeIdFilter(0);
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
        Filter filter = NotificationTypeFilterBuilder.createNotificationTypeIdFilter(1);
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());
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
            NotificationTypeFilterBuilder.createNameFilter(null);
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
        Filter filter = NotificationTypeFilterBuilder.createNameFilter("This is a name");
        // It should be an instance of EqualToFilter
        assertTrue("It should be an instance of EqualToFilter.", filter instanceof EqualToFilter);
        // check the name
        assertEquals("The name is not property set.",
                NotificationTypeFilterBuilder.NAME_FIELD_NAME, ((EqualToFilter) filter).getName());
        // check the value
        assertEquals("The value is not property set.",
                "This is a name", (String) ((EqualToFilter) filter).getValue());

    }

}
