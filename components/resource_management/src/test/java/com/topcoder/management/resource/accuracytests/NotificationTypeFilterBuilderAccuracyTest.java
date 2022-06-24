/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>NotificationTypeFilterBuilder</code> class. It tests the
 * createNameFilter(String) and createNotificationTypeIdFilter(long) methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class NotificationTypeFilterBuilderAccuracyTest extends TestCase {
    /**
     * <p>
     * Test suite of <code>NotificationTypeFilterBuilderAccuracyTest</code>.
     * </p>
     * @return Test suite of
     *         <code>NotificationTypeFilterBuilderAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(NotificationTypeFilterBuilderAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of the <code>createNameFilter(String)</code> method.
     * </p>
     */
    public void testMethod_createNameFilter() {
        String name = "operator";
        Filter filter = NotificationTypeFilterBuilder.createNameFilter(name);

        // check null here.
        assertNotNull("Create NameFilter failed.", filter);

        // check the type here.
        assertTrue("The 'NameFilter' should extend EqualToFilter.", filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.", NotificationTypeFilterBuilder.NAME_FIELD_NAME,
                ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", name,
                ((String) ((EqualToFilter) filter).getValue()));
    }

    /**
     * <p>
     * Accuracy test of the <code>createNotificationTypeIdFilter(long)</code>
     * method.
     * </p>
     */
    public void testMethod_createNotificationTypeIdFilter() {
        Filter filter = NotificationTypeFilterBuilder.createNotificationTypeIdFilter(1);

        // check null here.
        assertNotNull("Create TypeIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'TypeIdFilter' should extend EqualToFilter.", filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
                NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, ((EqualToFilter) filter)
                        .getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", 1, ((Long) ((EqualToFilter) filter).getValue())
                .longValue());
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
                NotificationTypeFilterBuilder.NAME_FIELD_NAME);
        assertEquals("The 'NOTIFICATION_TYPE_ID_FIELD_NAME' field is incorrect.", "notification_type_id",
                NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME);
    }
}
