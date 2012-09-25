/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import com.topcoder.direct.services.view.dto.ReportAggregationBaseDTO;
import com.topcoder.direct.services.view.dto.ReportType;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;

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
 * <p>
 * Version 1.2 (TC Cockpit - Member Participation Metrics Report Upgrade ) change log:
 * <ol>
 *    <li>Added {@link #participationViewType} field and getter/setter for it.</li>
 *    <li>Added method {@link #getExcelFileName()} to get the excel file name when exporting the report.</li>
 *    <li>Updated method {@link #getInputStream()}, remove the catch Throwable block.</li>
 *    <li>Updated method {@link #insertSheetData(Sheet)} to insert the table that is being viewed</li>
 *    <li>Updated method {@link #generateParticipationReportHeader(Sheet, String, int)} and 
 *    method {@link #generateParticipationReport(Sheet, List, int)} to add column "Milestone Submissions" and "Final Submissions"
 *    </li>
 * </ol>
 * </p>
 * 
 * @author TCSASSEMBER
 * @version  1.2 (TC Cockpit - Member Participation Metrics Report Upgrade )
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
     * The participation view type. 
     * @since 1.2
     */
    private ParticipationViewType participationViewType;
    
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
     * Gets the participation view type.
     *
     * @return the participation view type
     * @since 1.2
     */
    public ParticipationViewType getParticipationViewType() {
        return participationViewType;
    }

    /**
     * Sets the participation view type.
     *
     * @param participationViewType the new participation view type
     * @since 1.2
     */
    public void setParticipationViewType(ParticipationViewType participationViewType) {
        this.participationViewType = participationViewType;
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
     * Return the excel file name when exporting the report.
     *
     * @return the excel file name
     * @since 1.2
     */
    public String getExcelFileName() {
        return "participation_report_"+participationViewType.getKey()+".xls";
    }
    
    /**
     * <p>Gets the excel file download stream for participation metrics report.</p>
     *
     * @return the download stream.
     * @throws Exception if any error occurs.
     */
    public InputStream getInputStream() throws Exception {
        Workbook workbook = new ExcelWorkbook();
        Sheet sheet = new ExcelSheet("Project Details", (ExcelWorkbook) workbook);
        insertSheetData(sheet);
        workbook.addSheet(sheet);
        
        // Create a new WorkBookSaver
        WorkbookSaver saver = new Biff8WorkbookSaver();
        ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
        saver.save(workbook, saveTo);
        return new ByteArrayInputStream(saveTo.toByteArray());
    }
    
    /**
     * <p>Inserts the sheet data.</p>
     *
     * @param sheet the sheet.
     * @throws Exception if any error occurs.
     */
    private void insertSheetData(Sheet sheet) throws Exception {
        int rowIndex = 1;
        rowIndex = generateParticipationReportHeader(sheet, this.getParticipationViewType().getText(), rowIndex);
        rowIndex = generateParticipationReport(sheet, this.getExcelAggregation(), rowIndex);
    }

    	/**
    	 * Generate participation report header.
    	 *
    	 * @param sheet the sheet
    	 * @param viewBy the view by
    	 * @param rowIndex the row index
    	 * @return the int
    	 */
    private int generateParticipationReportHeader(Sheet sheet, String viewBy, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        int index = 1;
        row.getCell(index++).setStringValue(viewBy);
        row.getCell(index++).setStringValue("Total Registrations");
        row.getCell(index++).setStringValue("Unique Registrants");
        row.getCell(index++).setStringValue("Registrant Countries");
        row.getCell(index++).setStringValue("Total Submissions");
        row.getCell(index++).setStringValue("Milestone Submissions");
        row.getCell(index++).setStringValue("Final Submissions");
        row.getCell(index++).setStringValue("Unique Submitters");
        row.getCell(index++).setStringValue("Submitter Countries");
        row.getCell(index++).setStringValue("Milestone Winners");
        row.getCell(index++).setStringValue("Final Winners");
        row.getCell(index++).setStringValue("Total Winners");
        row.getCell(index++).setStringValue("Total Unique Winners");
        row.getCell(index++).setStringValue("Winner Countries");
        return ++rowIndex;
    }

    	/**
    	 * Generate participation report.
    	 *
    	 * @param sheet the sheet
    	 * @param dtos the dtos
    	 * @param rowIndex the row index
    	 * @return the int
    	 */
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
            row.getCell(index++).setNumberValue(dto.getTotalSubmissions());
            row.getCell(index++).setNumberValue(dto.getMilestoneSubmissions());
            row.getCell(index++).setNumberValue(dto.getFinalSubmissions());
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
