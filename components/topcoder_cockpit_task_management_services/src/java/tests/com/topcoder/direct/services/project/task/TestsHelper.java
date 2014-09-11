/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

import java.lang.reflect.Field;

/**
 * <p>
 * The base class for unit tests.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TestsHelper {
    /**
     * Creates an instance of TestsHelper.
     */
    private TestsHelper() {
        // Empty
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     *
     * @return the field value.
     */
    public static Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (declaredField == null) {
                try {
                    declaredField = obj.getClass().getSuperclass().getDeclaredField(field);
                } catch (NoSuchFieldException e) {
                    // Ignore
                }
            }

            if (declaredField == null) {
                declaredField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(field);
            }

            declaredField.setAccessible(true);

            try {
                value = declaredField.get(obj);
            } finally {
                declaredField.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }

        return value;
    }
}
