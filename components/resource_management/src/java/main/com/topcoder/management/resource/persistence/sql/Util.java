/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

/**
 * A utility class containing helper methods.
 *
 * @author Chenhong
 * @version 1.1
 */
final class Util {

    /**
     * Private constructor.
     *
     */
    private Util() {
        // empty.
    }

    /**
     * A Utility method to convert a Date instance to Timestamp instance.
     *
     * @param date
     *            the date to convert.
     * @return the timestamp
     */
    static Timestamp dateToTimestamp(Date date) {
        if (date == null) {
            date = new Date();
        }
        return new Timestamp(date.getTime());
    }

    /**
     * check valid for the parameter Resource instance.
     *
     * @param resource
     *            the Resource instance for check.
     * @param isDelete flag for delete.
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole is null or its creation/modification
     *             user/date is null
     */
    static void checkResource(Resource resource, boolean isDelete) {
        if (resource == null) {
            throw new IllegalArgumentException("The parameter resource can not be null.");
        }

        if (resource.getId() == Resource.UNSET_ID) {
            throw new IllegalArgumentException("The id of the resource has not been set.");
        }

        if (resource.getResourceRole() == null) {
            throw new IllegalArgumentException("The resourceRole should not be null.");
        }

        if (!isDelete) {
            if (resource.getCreationUser() == null) {
                throw new IllegalArgumentException("The creation user should not be null.");
            }

            if (resource.getCreationTimestamp() == null) {
                throw new IllegalArgumentException("The creation time should not be null.");
            }

            if (resource.getModificationUser() == null) {
                throw new IllegalArgumentException("The modification user should not be null.");
            }

            if (resource.getModificationTimestamp() == null) {
                throw new IllegalArgumentException("The modification time should not be null.");
            }
        }
    }

    /**
     * Check if the long array values are valid.
     *
     * @param values
     *            the long array to check.
     * @param name
     *            the name of the parameter value.
     *
     * @throws IllegalArgumentException
     *             if values is null, or contains <= 0 value.
     */
    static void checkLongArray(long[] values, String name) {
        checkNull(values, name);

        for (int i = 0; i < values.length; i++) {
            if (values[i] <= 0) {
                throw new IllegalArgumentException("Parameter " + name + " should not contains value <= 0.");
            }
        }
    }

    /**
     * <p>
     * Check if the long value is positive.
     * </p>
     *
     * @param value
     *            the long value to check
     * @param paramName
     *            the paramName of the argument.
     * @throws IllegalArgumentException
     *             if the value is not positive.
     */
    static void checkPositiveValue(long value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException("The value for " + paramName + " should be positive, but was " + value);
        }
    }

    /**
     * Check if the parameter is null.
     *
     * @param obj
     *            the parameter to check.
     * @param name
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the parameter is null.
     */
    static void checkNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("Parameter " + name + " should not be null.");
        }
    }

    /**
     * Check if the ResourceRole instance is valid.
     *
     * @param resourceRole
     *            the ResourceRole instance.
     * @param isDelete
     *            if true , it is for deleteNotificationType method. if false, it is for add or update notificationType
     *            method.
     *
     * @throws IllegalArgumentException
     *             if the ResourceRole is not valid for certain method.
     */
    static void checkResourceRole(ResourceRole resourceRole, boolean isDelete) {
        checkNull(resourceRole, "resourceRole");

        if (resourceRole.getId() == ResourceRole.UNSET_ID) {
            throw new IllegalArgumentException("The id for the resourceRole is not set.");
        }

        if (!isDelete) {
            checkValidField(resourceRole.getName(), "name of ResourceRole");
            checkValidField(resourceRole.getDescription(), "description of ResourceRole");
            checkValidField(resourceRole.getCreationUser(), "creationUser of ResourceRole");
            checkValidField(resourceRole.getCreationTimestamp(), "creationTimestamp of ResourceRole");
            checkValidField(resourceRole.getModificationUser(), "modificationUser of ResourceRole");
            checkValidField(resourceRole.getModificationTimestamp(), "modificationTimestamp of ResourceRole");
        }
    }

    /**
     * check if the parameter notificationType is valid for add/delete/updateNotificationType method.
     *
     * @param notificationType
     *            the NotificationType instance to check.
     * @param isDelete
     *            If true, the parameter is passed from deleteNotificationType method. If false, the parameter is passed
     *            from add/update NotificationType methods.
     * @throws IllegalArgumentException
     *             if the parameter notificationType is not valid.
     */
    static void checkNotificationType(NotificationType notificationType, boolean isDelete) {
        checkNull(notificationType, "notificationType");

        if (notificationType.getId() == NotificationType.UNSET_ID) {
            throw new IllegalArgumentException("The id of the notificationType is not set.");
        }

        if (!isDelete) {
            checkValidField(notificationType.getName(), "name of NotificationType");
            checkValidField(notificationType.getDescription(), "description of NotificationType");
            checkValidField(notificationType.getCreationUser(), "creationUser of NotificationType");
            checkValidField(notificationType.getCreationTimestamp(), "creationDate of NotificationType");
            checkValidField(notificationType.getModificationUser(), "modificationUser of NotificationType");
            checkValidField(notificationType.getModificationTimestamp(), "modificationDate of NotificationType");
        }
    }

    /**
     * check the filed of object like ResourceRole, NotificationType if it is null.
     *
     * @param obj
     *            the object to be checked for null.
     * @param name
     *            the description of the obj passing
     */
    private static void checkValidField(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("The " + name + " should not be null.");
        }
    }

    /**
     * <p>
     * Closes the given ResultSet.
     * </p>
     *
     * @param rs
     *            the given ResultSet instance to close.
     */
    static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // ignore.
        }
    }

    /**
     * <p>
     * Closes the given PreparedStatement instance.
     * </p>
     *
     * @param statement
     *            the given Statement instance to close.
     */
    static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }
}
