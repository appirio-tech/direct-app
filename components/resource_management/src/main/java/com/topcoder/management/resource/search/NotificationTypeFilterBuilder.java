/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import com.topcoder.management.resource.Helper;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The NotificationTypeFilterBuilder class supports building filters for
 * searching for NotificationTypes.
 * </p>
 *
 * <p>
 * This class consists of 2 parts. The first part consists of static Strings that
 * name the fields that are available for searching. All ResourceManager implementations
 * should use SearchBundles that are configured to use these names. The second part of
 * this class consists of convenience methods to create common filters based on these field names.
 * </p>
 *
 * <p>
 * This class has only final static fields/methods, so mutability is not an issue. This class is thread-safe.
 * </p>
 *
 * @author aubergineanode, kinfkong
 * @version 1.0
 */
public class NotificationTypeFilterBuilder {

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the id of the
     * notification type.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createNotificationTypeIdFilter method.
     * </p>
     */
    public static final String NOTIFICATION_TYPE_ID_FIELD_NAME = "notification_type_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the name
     *  of the notification type.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createNotificationTypeNameFilter method.
     * </p>
     */
    public static final String NAME_FIELD_NAME = "name";

    /**
     * <p>
     * The constructor of class NotificationTypeFilterBuilder. It is set to be private to prevent instantiation.
     * </p>
     */
    private NotificationTypeFilterBuilder() {
        // do nothing
    }

    /**
     * <p>
     * Creates a filter that selects notification types which have the given id.
     * </p>
     *
     * @param notificationTypeId The notification type id to filter on
     *
     * @return A filter for selecting notifications types which have the given id
     *
     * @throws IllegalArgumentException If notificationTypeId is &lt;= 0
     */
    public static Filter createNotificationTypeIdFilter(long notificationTypeId) {
        Helper.checkLongPositive(notificationTypeId, "notificationTypeId");
        return new EqualToFilter(NOTIFICATION_TYPE_ID_FIELD_NAME, new Long(notificationTypeId));
    }

    /**
     * <p>
     * Creates a filter that selects notification types which have the given name.
     * </p>
     *
     * @param name The notification type name to filter on
     *
     * @return A filter for selecting notifications types which have the given name.
     *
     * @throws IllegalArgumentException If name is null
     */
    public static Filter createNameFilter(String name) {
        Helper.checkNull(name, "name");
        return new EqualToFilter(NAME_FIELD_NAME, name);
    }
}
