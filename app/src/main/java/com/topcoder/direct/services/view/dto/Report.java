/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

/**
 * <p>An interface for <code>DTO</code> objects corresponding to single report.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Direct Pipeline Integration Assembly)
 */
public interface Report {

    /**
     * <p>Gets the type of this report.</p>
     *
     * @return a <code>ReportType</code> specifying the type of this report.
     */
    ReportType getReportType();
}
