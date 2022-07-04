/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.projectreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.dto.ReportBaseDTO;
import com.topcoder.direct.services.view.dto.ReportType;
import com.topcoder.direct.services.view.dto.dashboard.DashboardCostBreakDownDTO;
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
 * The DTO to store the project metrics report data.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC TC Cockpit Project Metrics Report)
 */
public class ProjectMetricsReportDTO extends ReportBaseDTO {
	
    /**
     * Represents the excel file name when exporting project metrics report.
     */
    private static final String PROJECT_REPORT_EXCEL_FILE_NAME = "project_report.xls";

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
    
    /**
     * Return the excel file name when exporting the report.
     * 
     * @return the excel file name
     * @since 1.0.1
     */
    public String getExcelFileName() {
            return PROJECT_REPORT_EXCEL_FILE_NAME;
    }
    
    /**
     * <p>Gets the excel file download stream for project metrics report.</p>
     *
     * @return the download stream.
     * @throws WorkbookSavingException if any error occurs when generating project metrics report excel file.
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
        // the date format used for displaying 'completion date'
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // set up the sheet header first
        Row row = sheet.getRow(1);
        int index = 1;
        row.getCell(index++).setStringValue("Project Name");
        row.getCell(index++).setStringValue("Project Type");
        row.getCell(index++).setStringValue("Project Status");
        row.getCell(index++).setStringValue("Draft");
        row.getCell(index++).setStringValue("Scheduled");
		row.getCell(index++).setStringValue("Active");
        row.getCell(index++).setStringValue("Finished");
        row.getCell(index++).setStringValue("Canceled");
        row.getCell(index++).setStringValue("Total Budget");
        row.getCell(index++).setStringValue("Actual Cost");
        row.getCell(index++).setStringValue("Planned Cost");
        row.getCell(index++).setStringValue("Total Projected Cost");
        row.getCell(index++).setStringValue("Start Date");
        row.getCell(index++).setStringValue("Completion Date");
        row.getCell(index++).setStringValue("Total #of Contests");
        row.getCell(index++).setStringValue("Project Fulfillment");
        
        // insert sheet data from 2nd row
        int rowIndex = 2;

        for (ProjectMetricsReportEntryDTO dto : getEntries()) {
            row = sheet.getRow(rowIndex++);

            index = 1;

            row.getCell(index++).setStringValue(dto.getProjectName());
			if (dto.getProjectType() != null && !dto.getProjectType().equals(""))
			{
				row.getCell(index++).setStringValue(dto.getProjectType());
			}
			else 
			{
				row.getCell(index++).setStringValue("Not Set");
			}
            row.getCell(index++).setStringValue(dto.getProjectStatus());
            row.getCell(index++).setNumberValue(dto.getNumDraft());
            row.getCell(index++).setNumberValue(dto.getNumScheduled());
            row.getCell(index++).setNumberValue(dto.getNumActive());
            row.getCell(index++).setNumberValue(dto.getNumFinished());
            row.getCell(index++).setNumberValue(dto.getNumCanceled());
            row.getCell(index++).setStringValue(dto.getTotalBudget());
            row.getCell(index++).setNumberValue(dto.getActualCost());
            row.getCell(index++).setNumberValue(dto.getPlannedCost());
            row.getCell(index++).setNumberValue(dto.getProjectedCost());
            row.getCell(index++).setStringValue(dto.getStartDate() == null ? "None" :dateFormatter.format(dto.getStartDate()));
            row.getCell(index++).setStringValue(dto.getCompletionDate() == null ? "None" :dateFormatter.format(dto.getCompletionDate()));
            row.getCell(index++).setNumberValue(dto.getTotalContests());
            row.getCell(index++).setNumberValue(dto.getProjectFulfillment());

        }
    }
}
