/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

import com.topcoder.direct.services.view.dto.ReportAggregationBaseDTO;
import com.topcoder.direct.services.view.dto.ReportType;

/**
 * <p>
 * The DTO to store the participation metrics report data. It includes basic metrics report data and 4 aggregation
 * participation metrics report data:
 * <ol>
 *   <li>project aggregation participation metrics report</li>
 *   <li>billing aggregation participation metrics report</li>
 *   <li>contest status aggregation participation metrics report</li>
 *   <li>contest type aggregation participation metrics report</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>This class has been refactoring. It's extended from <code>ReportAggregationBaseDTO</code>.</li>
 * </ol>
 * </p>
 * 
 * @author TCSASSEMBER
 * @version  1.1 (TC Cockpit Participation Metrics Report Part One Assembly 1)
 */
public class ParticipationReportDTO extends ReportAggregationBaseDTO<ParticipationAggregationReportDTO> {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -3985093109381162149L;
    
    /**
     * The basic participation metrics report.
     */
    private ParticipationBasicReportDTO participationBasicReport;
    
    /**
     * Empty constructor.
     */
    public ParticipationReportDTO() {
        super();
    }

    /**
     * Gets the report type of this report DTO. It simply returns ReportType.PARTICIPATION.
     *
     * @return the the cost report type.
     */
    public ReportType getReportType() {
        return ReportType.PARTICIPATION;
    }

    /**
     * Gets the basic participation metrics report.
     * 
     * @return the basic participation metrics report.
     */
    public ParticipationBasicReportDTO getParticipationBasicReport() {
        return participationBasicReport;
    }

    /**
     * Sets the basic participation metrics report.
     * 
     * @param participationBasicReport the basic participation metrics report.
     */
    public void setParticipationBasicReport(ParticipationBasicReportDTO participationBasicReport) {
        this.participationBasicReport = participationBasicReport;
    }
}
