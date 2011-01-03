/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
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
 * @author isv
 * @version 1.0.1 (Direct Enterprise Dashboard Assembly 1.0)
 */
public enum EnterpriseDashboardStatPeriodType {

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Week</code> period.</p>
     */
    WEEK,

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Month</code> period.</p>
     */
    MONTH,

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Quarter</code> period.</p>
     */
    QUARTER,

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Half of the Year</code> period.</p>
     * 
     * @since 1.0.1
     */
    HALF_OF_THE_YEAR,

    /**
     * <p>A <code>EnterpriseDashboardStatPeriodType</code> referencing the <code>Year</code> period.</p>
     */
    YEAR
}
