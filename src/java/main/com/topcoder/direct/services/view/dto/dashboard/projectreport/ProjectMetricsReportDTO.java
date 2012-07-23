/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.projectreport;

import java.util.List;

import com.topcoder.direct.services.view.dto.ReportBaseDTO;
import com.topcoder.direct.services.view.dto.ReportType;

/**
 * <p>
 * The DTO to store the project metrics report data.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC TC Cockpit Project Metrics Report)
 */
public class ProjectMetricsReportDTO extends ReportBaseDTO {

    /**
     * <p>The list stores the billing cost report entries</p>
     */
    private List<ProjectMetricsReportEntryDTO> entries;

    
    public List<ProjectMetricsReportEntryDTO> getEntries() {
		return entries;
	}

	public void setEntries(List<ProjectMetricsReportEntryDTO> entries) {
		this.entries = entries;
	}

	/**
     * Gets the report type of this report DTO. It simply returns ReportType.BILLING_COST.
     *
     * @return the the billing cost report type.
     */
    public ReportType getReportType() {
        return ReportType.PROJECT_METRICS;
    }
}
