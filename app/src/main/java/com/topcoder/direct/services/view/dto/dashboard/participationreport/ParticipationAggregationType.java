/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

/**
 * The enum represents the type of aggregation partipation metrics report.
 *
 * @author TCSASSEMBER
 * @version  1.0 (TC Cockpit Participation Metrics Report Part One Assembly 1)
 */
public enum ParticipationAggregationType {
    
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
