/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.dto.ReportAggregationBaseDTO;
import com.topcoder.direct.services.view.dto.ReportType;
import com.topcoder.direct.services.view.dto.dashboard.DashboardCostBreakDownDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.ProjectMetricsReportEntryDTO;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;
import com.topcoder.excel.output.WorkbookSavingException;

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
    
    /**
     * <p>Gets the excel file download stream for participation metrics report.</p>
     *
     * @return the download stream.
     * @throws WorkbookSavingException if any error occurs when generating participation metrics report excel file.
     * @throws IOException if an I/O error occurs.
     */
    public InputStream getInputStream() throws WorkbookSavingException, IOException {
        try {

            Workbook workbook = new ExcelWorkbook();
            Sheet sheet = new ExcelSheet("Project Details", (ExcelWorkbook) workbook);
            insertSheetData(sheet);
            workbook.addSheet(sheet);

            // Create a new WorkBookSaver
            WorkbookSaver saver = new Biff8WorkbookSaver();
            ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
            saver.save(workbook, saveTo);
            return new ByteArrayInputStream(saveTo.toByteArray());

        } catch (Throwable e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * <p>Inserts the sheet data.</p>
     *
     * @param sheet the sheet.
     */
    private void insertSheetData(Sheet sheet) throws Exception {
        int rowIndex = 1;
        
        int index = 1;
        Row row = sheet.getRow(rowIndex);
        row.getCell(index++).setStringValue("Basic Metrics");
        rowIndex++;
        
        index = 1;
        row = sheet.getRow(rowIndex);
        row.getCell(index++).setStringValue("Projects");
        row.getCell(index++).setStringValue("Contests");
        row.getCell(index++).setStringValue("Copilots");
        rowIndex++;
        
        index = 1;
        row = sheet.getRow(rowIndex);
        row.getCell(index++).setNumberValue(this.getParticipationBasicReport().getTotalProjects());
        row.getCell(index++).setNumberValue(this.getParticipationBasicReport().getTotalContests());
        row.getCell(index++).setNumberValue(this.getParticipationBasicReport().getTotalCopilots());
        rowIndex++;
        
        
        rowIndex = generateParticipationReportHeader(sheet, "Project", rowIndex);
        rowIndex = generateParticipationReport(sheet, this.getProjectAggregation(), rowIndex);
        rowIndex = generateParticipationReportHeader(sheet, "Billing Account", rowIndex);
        rowIndex = generateParticipationReport(sheet, this.getBillingAggregation(), rowIndex);
        rowIndex = generateParticipationReportHeader(sheet, "Contest Type", rowIndex);
        rowIndex = generateParticipationReport(sheet, this.getContestTypeAggregation(), rowIndex);
        rowIndex = generateParticipationReportHeader(sheet, "Contest Status", rowIndex);
        rowIndex = generateParticipationReport(sheet, this.getStatusAggregation(), rowIndex);
        
    }

	private int generateParticipationReportHeader(Sheet sheet, String viewBy, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        int index = 1;
        row.getCell(index++).setStringValue(viewBy);
        row.getCell(index++).setStringValue("Total Registrations");
        row.getCell(index++).setStringValue("Unique Registrants");
        row.getCell(index++).setStringValue("Registrant Countries");
        row.getCell(index++).setStringValue("Total Submissions");
		row.getCell(index++).setStringValue("Unique Submitters");
        row.getCell(index++).setStringValue("Submitter Countries");
        row.getCell(index++).setStringValue("Milestone Winners");
        row.getCell(index++).setStringValue("Final Winners");
        row.getCell(index++).setStringValue("Total Winners");
        row.getCell(index++).setStringValue("Total Unique Winners");
        row.getCell(index++).setStringValue("Winner Countries");
		return ++rowIndex;
	}

	private int generateParticipationReport(Sheet sheet, List<ParticipationAggregationReportDTO> dtos, int rowIndex) {
		Row row;
		int index;
        
        for (ParticipationAggregationReportDTO dto : dtos) {
            row = sheet.getRow(rowIndex++);

            index = 1;

            row.getCell(index++).setStringValue(dto.getGroupName());
            row.getCell(index++).setNumberValue(dto.getTotalRegistrants());
            row.getCell(index++).setNumberValue(dto.getUniqueRegistrants());
            row.getCell(index++).setNumberValue(dto.getRegistrantCountries());
            row.getCell(index++).setNumberValue(dto.getTotalSubmitters());
            row.getCell(index++).setNumberValue(dto.getUniqueSubmitters());
            row.getCell(index++).setNumberValue(dto.getSubmitterContries());
            row.getCell(index++).setNumberValue(dto.getMilestoneWinners());
            row.getCell(index++).setNumberValue(dto.getFinalWinners());
            row.getCell(index++).setNumberValue(dto.getTotalWinners());
            row.getCell(index++).setNumberValue(dto.getTotalUniqueWinners());
            row.getCell(index++).setNumberValue(dto.getWinnerCountries());
        }
        return ++rowIndex;
	}
}
