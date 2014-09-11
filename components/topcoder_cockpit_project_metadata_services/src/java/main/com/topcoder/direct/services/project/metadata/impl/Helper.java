/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.entities.dao.IdentifiableEntity;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTO;

/**
 * <p>
 * Helper class for the component. It provides useful common methods for all the classes in this component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
final class Helper {
    /**
     * Represents the string 'create'.
     */
    static final String ACTION_CREATE = "create";

    /**
     * Represents the string 'update'.
     */
    static final String ACTION_UPDATE = "update";

    /**
     * Represents the string 'delete'.
     */
    static final String ACTION_DELETE = "delete";

    /**
     * <p>
     * Empty private constructor.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * Converts the object to a string.
     *
     * @param obj
     *            the object.
     *
     * @return the string.
     */
    static String toString(Object obj) {
//        if (obj instanceof IdentifiableEntity) {
//            // IdentifiableEntity
//            return ((IdentifiableEntity) obj).toJSONString();
//
//        } else if (obj instanceof TcDirectProject) {
//            // TcDirectProject
//            return ((TcDirectProject) obj).toJSONString();
//        } else if (obj instanceof DirectProjectFilter) {
//            // DirectProjectFilter
//            return ((DirectProjectFilter) obj).toJSONString();
//        } else if (obj instanceof DirectProjectMetadataDTO) {
//            // DirectProjectMetadataDTO
//            return ((DirectProjectMetadataDTO) obj).toJSONString();
//        } else if (obj instanceof List<?>) {
//            // DirectProjectMetadataDTO
//            return toString((List<?>) obj);
//        }

        return "";//String.valueOf(obj);
    }

    /**
     * <p>
     * Checks the map.
     * </p>
     *
     * @param <T1>
     *            the key type.
     * @param <T2>
     *            the value type.
     * @param map
     *            the map.
     * @param name
     *            the name.
     *
     * @throws ConfigurationException
     *             if map is <code>null</code>, contains <code>null</code> or empty (when the key is a String)
     *             key or <code>null</code> or empty (when the value is a String) value.
     */
    static <T1, T2> void checkMap(Map<T1, T2> map, String name) {
        ValidationUtility.checkNotNull(map, name, ConfigurationException.class);

        for (Entry<T1, T2> entry : map.entrySet()) {
            checkObj(entry.getKey(), "key of " + name);
            checkObj(entry.getValue(), "value of " + name);
        }
    }

    /**
     * Converts the List to a string.
     *
     * @param obj
     *            the List (not <code>null</code>).
     *
     * @return the string.
     */
    private static String toString(List<?> obj) {
        StringBuilder sb = new StringBuilder("[");

        boolean first = true;
        for (Object element : obj) {
            if (!first) {
                // Append a comma
                sb.append(", ");
            }
            first = false;

            sb.append(toString(element));
        }

        sb.append("]");

        return sb.toString();
    }

    /**
     * Checks the value.
     *
     * @param value
     *            the value.
     * @param name
     *            the name.
     *
     * @throws ConfigurationException
     *             if the value is <code>null</code> or empty (when the value is a String).
     */
    private static void checkObj(Object value, String name) {
        if (value instanceof String) {
            ValidationUtility.checkNotNullNorEmptyAfterTrimming((String) value, name, ConfigurationException.class);
        } else {
            ValidationUtility.checkNotNull(value, name, ConfigurationException.class);
        }
    }
}
