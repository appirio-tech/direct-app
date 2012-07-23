/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

/**
 * <p>An enumeration over the possible types of reports.</p>
 *
 * Version 1.1: add report type: COST - (Direct Cockpit - Cost Report Assembly)
 * Version 1.2: add report type: BILLING_COST (TC Cockpit Billing Cost Report Assembly )
 * Version 1.3: add report type: PARTICIPATION (TC Cockpit Participation Metrics Report Part One Assembly 1 )
 * Version 1.4: add report type: PROJECT_METRICS (TC Cockpit Project Metrics Report)
 *
 * @author TCSDEVELOPER, TCSASSEMBLER
 * @version 1.4 (TC Cockpit Project Metrics Report)
 */
public enum ReportType {

    /**
     * <p>A <code>ReportType</code> corresponding to <code>Pipeline</code> report.</p>
     */
    PIPELINE,
    /**
     * <p>A <code>ReportType</code> corresponding to <code>Cost</code> report.</p>
     */
    COST,
    /**
     * <p>A <code>ReportType</code> corresponding to <code>Billing Cost</code> report.</p>
     */
    BILLING_COST,
    /**
     * <p>A <code>ReportType</code> corresponding to <code>Participation Metrics</code> report.</p>
     */
    PARTICIPATION,
    /**
     * <p>A <code>ReportType</code> corresponding to <code>Project Metrics</code> report.</p>
     */    
    PROJECT_METRICS
}
