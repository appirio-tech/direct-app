/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.Date;

/**
 * <p>
 * A Helper class for this component.
 * </p>
 * <p>
 * This class provides some useful methods, such as parameters checking, etc.
 * </p>
 *
 * @author littlebull
 * @version 2.0
 */
final class ProjectPhaseHelper {
    /**
     * Private empty constructor.
     */
    private ProjectPhaseHelper() {
        // Do nothing
    }

    /**
     * Check if the object is null.
     *
     * @param obj
     *            the object to check.
     * @param name
     *            the object name
     * @throws IllegalArgumentException
     *             if the object is null.
     */
    public static void checkObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " can not be null.");
        }
    }

    /**
     * Check if the long value is negative.
     *
     * @param value
     *            the long value to check.
     * @param name
     *            the parameter name
     * @throws IllegalArgumentException
     *             if the long value is negative
     */
    public static void checkLongNotNegative(long value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " can not be negative.");
        }
    }

    /**
     * Check if end date is before start date.
     *
     * @param endDate
     *            the end date value to check
     * @param startDate
     *            the start date value to check
     * @param endDateName
     *            the end date parameter name
     * @param startDateName
     *            the start date parameter name
     * @throws IllegalArgumentException
     *             if end date is before start date
     */
    public static void checkDateNotBefore(Date endDate, Date startDate, String endDateName, String startDateName) {
        if (endDate == null || startDate == null) {
            return;
        }

        if (endDate.getTime() < startDate.getTime()) {
            throw new IllegalArgumentException(endDateName + " can not before " + startDateName);
        }
    }

    /**
     * Check if dependency and dependent are same instance.
     *
     * @param dependency
     *            the dependency phase
     * @param dependent
     *            the dependent phase
     * @throws IllegalArgumentException
     *             if dependency and dependent are same instance
     */
    public static void checkPhaseNotSameInstance(Phase dependency, Phase dependent) {
        if (dependency == dependent) {
            throw new IllegalArgumentException("dependency and dependent are same instance.");
        }
    }
}
