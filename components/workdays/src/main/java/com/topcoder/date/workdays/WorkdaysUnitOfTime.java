/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * <p>
 * Instances of this class represent units of time. It has a private constructor, so only the three public static final
 * instances can be used: MINUTES, HOURS and DAYS.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class WorkdaysUnitOfTime extends Enum {
    /**
     * <p>
     * This represents minutes as the unit of time to add to a given date.
     * </p>
     */
    public static final WorkdaysUnitOfTime MINUTES = new WorkdaysUnitOfTime("MINUTES", 0);

    /**
     * <p>
     * This represents hours as the unit of time to add to a given date.
     * </p>
     */
    public static final WorkdaysUnitOfTime HOURS = new WorkdaysUnitOfTime("HOURS", 1);

    /**
     * <p>
     * This represents days as the unit of time to add to a given date.
     * </p>
     */
    public static final WorkdaysUnitOfTime DAYS = new WorkdaysUnitOfTime("DAYS", 2);

    /**
     * <p>
     * Name of this unit of time. It is used by toString() for a descriptive display of an instance of this class.
     * </p>
     */
    private String name = null;

    /**
     * <p>
     * Value of this unit of time. It is used for ordering the unit of time.
     * </p>
     */
    private int value = 0;

    /**
     * <p>
     * Private constructor to create a WorkdaysUnitOfTime with the given name and the given value.
     * </p>
     *
     * @param name name for the unit of time
     * @param value value for the unit of time
     *
     * @throws NullPointerException when parameter name is null
     * @throws IllegalArgumentException when parameter name is empty
     */
    private WorkdaysUnitOfTime(String name, int value) {
        if (name == null) {
            throw new NullPointerException("Parameter name is null");
        }

        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("parameter name is empty");
        }

        this.name = name;
        this.value = value;
    }

    /**
     * <p>
     * Getter for the value of a WorkdaysUnitOfTime instance. The value is used for ordering.
     * </p>
     *
     * @return value for the unit of time
     */
    public int getValue() {
        return this.value;
    }

    /**
     * <p>
     * Returns a descriptive string of an instance of WorkdaysUnitOfTime class.
     * </p>
     *
     * @return name of this unit of time
     */
    public String toString() {
        return this.name;
    }
}
