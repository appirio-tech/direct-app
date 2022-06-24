/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import com.topcoder.management.resource.Helper;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The NotificationFilterBuilder class supports building filters for searching
 * for Notifications.
 * <p>
 *
 * <p>
 * This class consists of 2 parts. The first part consists of static Strings that name
 * the fields that are available for searching. All ResourceManager implementations should
 * use SearchBundles that are configured to use these names. The second part of this class
 * consists of convenience methods to create common filters based on these field names.
 * </p>
 *
 * <p>
 * This class has only final static fields/methods, so mutability is not an
 * issue.
 * </p>
 *
 * @author aubergineanode, kinfkong
 * @version 1.0
 */
public class NotificationFilterBuilder {

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order
     * to filter on the external reference id (example: user) when searching for notifications.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createExternalRefIdFilter method.
     * </p>
     */
    public static final String EXTERNAL_REF_ID_FIELD_NAME = "external_ref_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder
     * Filters can be built in order to filter on the id of the project the
     * notification references.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createProjectIdFilter method.
     * </p>
     */
    public static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the id of the
     * type of notification.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createNotificationTypeIdFilter method.
     * </p>
     *
     */
    public static final String NOTIFICATION_TYPE_ID_FIELD_NAME = "notification_type_id";

    /**
     * <p>
     * The constructor of NotificationFilterBuilder. It is private to prevent instantiation.
     * </p>
     */
    private NotificationFilterBuilder() {
        // do nothing
    }

    /**
     * <p>
     * Creates a filter that selects notifications which have the given external reference id.
     * </p>
     *
     * @param externalRefId The external reference id to filter on
     *
     * @return A filter for selecting notifications which are associated with the given external reference id
     *
     * @throws IllegalArgumentException If externalRefId is &lt;= 0
     */
    public static Filter createExternalRefIdFilter(long externalRefId) {
        Helper.checkLongPositive(externalRefId, "externalRefId");
        return new EqualToFilter(EXTERNAL_REF_ID_FIELD_NAME, new Long(externalRefId));
    }

    /**
     * <p>
     * Creates a filter that selects notifications which have the given project id.
     * </p>
     *
     * @param projectId The project id to filter on
     *
     * @return A filter for selecting notifications which are associated with the given project
     *
     * @throws IllegalArgumentException If projectId is &lt;= 0
     */
    public static Filter createProjectIdFilter(long projectId) {
        Helper.checkLongPositive(projectId, "projectId");
        return new EqualToFilter(PROJECT_ID_FIELD_NAME, new Long(projectId));
    }

    /**
     * <p>
     * Creates a filter that selects notifications which have the given notification type id.
     * </p>
     *
     * @return A filter for selecting notifications which are associated with
     *         the given notification type
     *
     * @param notificationTypeId The notification type id to filter on
     *
     * @throws IllegalArgumentException If notificationTypeId is &lt;= 0
     */
    public static Filter createNotificationTypeIdFilter(long notificationTypeId) {
        Helper.checkLongPositive(notificationTypeId, "notificationTypeId");
        return new EqualToFilter(NOTIFICATION_TYPE_ID_FIELD_NAME, new Long(notificationTypeId));
    }
}
