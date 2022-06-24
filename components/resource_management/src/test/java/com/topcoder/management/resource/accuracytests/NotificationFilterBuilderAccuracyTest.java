/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.search.NotificationFilterBuilder;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>NotificationFilterBuilder</code> class. It tests the
 * createExternalRefIdFilter(long), createNotificationTypeIdFilter(long) and
 * createProjectIdFilter(long) methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class NotificationFilterBuilderAccuracyTest extends TestCase {
    /**
     * <p>
     * The id of <code>NotificationFilter</code> for test.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * Test suite of <code>NotificationFilterBuilderAccuracyTest</code>.
     * </p>
     * @return Test suite of <code>NotificationFilterBuilderAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(NotificationFilterBuilderAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of the <code>createExternalRefIdFilter(long)</code>
     * method.
     * </p>
     */
    public void testMethod_createExternalRefIdFilter() {
        Filter filter = NotificationFilterBuilder.createExternalRefIdFilter(id);

        // check null here.
        assertNotNull("Create ExternalRefIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'ExternalRefIdFilter' should extend EqualToFilter.", filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
                NotificationFilterBuilder.EXTERNAL_REF_ID_FIELD_NAME, ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", id, ((Long) ((EqualToFilter) filter).getValue())
                .longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>createNotificationTypeIdFilter(long)</code>
     * method.
     * </p>
     */
    public void testMethod_createNotificationTypeIdFilter() {
        Filter filter = NotificationFilterBuilder.createNotificationTypeIdFilter(id);

        // check null here.
        assertNotNull("Create NotificationTypeIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'NotificationTypeIdFilter' should extend EqualToFilter.",
                filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.",
                NotificationFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", id, ((Long) ((EqualToFilter) filter).getValue())
                .longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>createProjectIdFilter(long)</code> method.
     * </p>
     */
    public void testMethod_createProjectIdFilter() {
        Filter filter = NotificationFilterBuilder.createProjectIdFilter(id);

        // check null here.
        assertNotNull("Create ProjectIdFilter failed.", filter);

        // check the type here.
        assertTrue("The 'ProjectIdFilter' should extend EqualToFilter.", filter instanceof EqualToFilter);

        // check the filter's name here.
        assertEquals("The name of filter is incorrect.", NotificationFilterBuilder.PROJECT_ID_FIELD_NAME,
                ((EqualToFilter) filter).getName());

        // check the filter's value here.
        assertEquals("The value of filter is incorrect.", id, ((Long) ((EqualToFilter) filter).getValue())
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
        assertEquals("The 'EXTERNAL_REF_ID_FIELD_NAME' field is incorrect.", "external_ref_id",
                NotificationFilterBuilder.EXTERNAL_REF_ID_FIELD_NAME);
        assertEquals("The 'NOTIFICATION_TYPE_ID_FIELD_NAME' field is incorrect.", "notification_type_id",
                NotificationFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME);
        assertEquals("The 'PROJECT_ID_FIELD_NAME' field is incorrect.", "project_id",
                NotificationFilterBuilder.PROJECT_ID_FIELD_NAME);
    }
}
