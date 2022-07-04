/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

/**
 * <p>An enumeration over the types of periods for the statistics available for <code>Enterprise Dashboard</code> view.
 * </p>
 *
 * <p>
 * Version 1.0.1 (Cockpit - Direct Enterprise Dashboard 2 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #HALF_OF_THE_YEAR} item.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.1 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 1)
 * <ol>
 *     <li>Added label and toString for the EnterpriseDashboardStatPeriodType enum.</li>
 *     <li>Added static method {@link #forName(String)}</li>
 * </ol>
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.1
 */
public enum EnterpriseDashboardStatPeriodType {

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Week</code> period.</p>
     */
    WEEK("Week"),

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Month</code> period.</p>
     */
    MONTH("Month"),

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Quarter</code> period.</p>
     */
    QUARTER("Quarter"),

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Half of the Year</code> period.</p>
     * 
     * @since 1.0.1
     */
    HALF_OF_THE_YEAR("HalfYear"),

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Year</code> period.</p>
     */
    YEAR("Year");

    /**
     * The label of the EnterpriseDashboardStatPeriodType
     * @since 1.1
     */
    private String label;

    /**
     * Creates the enum with the label name.
     *
     * @param label the label name.
     * @since 1.1
     */
    private EnterpriseDashboardStatPeriodType(String label) {
        this.label = label;
    }

    /**
     * Gets the string representation of the EnterpriseDashboardStatPeriodType
     *
     * @return the string representation of the EnterpriseDashboardStatPeriodType
     * @since 1.1
     */
    @Override
    public String toString() {
        return this.label;
    }

    /**
     * Gets the EnterpriseDashboardStatPeriodType by its label name.
     *
     * @param label the label name.
     * @return the EnterpriseDashboardStatPeriodType enum
     * @since 1.1
     */
    public static EnterpriseDashboardStatPeriodType forName(String label) {
        for(EnterpriseDashboardStatPeriodType t : EnterpriseDashboardStatPeriodType.values()) {
            if(t.label.equalsIgnoreCase(label)) {
                return t;
            }
        }

        return MONTH;
    }
}
