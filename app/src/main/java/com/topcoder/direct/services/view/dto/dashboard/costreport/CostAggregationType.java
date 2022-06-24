/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.costreport;

/**
 * The enum represents the type of aggregation cost report.
 *
 * @author TCSDEVELOPER
 * @version  1.0 (TopCoder Cockpit - Cost Report Assembly)
 */
public enum CostAggregationType {

    /**
     * The aggregation cost report type : direct project.
     */
    DIRECT_PROJECT_AGGREGATION,

    /**
     * The aggregation cost report type : contest type.
     */
    CONTEST_TYPE_AGGREGATION,

    /**
     * The aggregation cost report type : status.
     */
    STATUS_AGGREGATION,

    /**
     * The aggregation cost report type : billing account.
     */
    BILLING_ACCOUNT_AGGREGATION
}
