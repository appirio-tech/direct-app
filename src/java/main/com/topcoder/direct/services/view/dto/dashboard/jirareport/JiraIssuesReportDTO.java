/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.jirareport;

import com.topcoder.direct.services.view.dto.ReportBaseDTO;
import com.topcoder.direct.services.view.dto.ReportType;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;
import com.topcoder.excel.output.WorkbookSavingException;
import com.topcoder.web.common.TCWebException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * The DTO for the jira issues report.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - JIRA issues loading update and report creation)
 */
public class JiraIssuesReportDTO extends ReportBaseDTO {

    /**
     * Represents the excel file name when exporting jira issues report.
     */
    private static final String JIRA_ISSUES_REPORT_EXCEL_FILE_NAME = "jira_issues_report.xls";

    /**
     * <p>The list stores the jira issues report entries</p>
     */
    private List<JiraIssuesReportEntryDTO> entries;

    /**
     * Gets the list of jira issues report entries.
     *
     * @return the list of jira issues report entries
     */
    public List<JiraIssuesReportEntryDTO> getEntries() {
        return entries;
    }

    /**
     * Sets the list of jira issues report entries.
     *
     * @param entries the list of jira issues report entries
     */
    public void setEntries(List<JiraIssuesReportEntryDTO> entries) {
        this.entries = entries;
    }

    /**
     * Gets the report type of this report DTO. It simply returns ReportType.JIRA_ISSUES.
     *
     * @return the the jira issues report type.
     */
    public ReportType getReportType() {
        return ReportType.JIRA_ISSUES;
    }

    /**
     * Return the excel file name when exporting the report.
     *
     * @return the excel file name
     */
    public String getExcelFileName() {
        return JIRA_ISSUES_REPORT_EXCEL_FILE_NAME;
    }

    /**
     * <p>Gets the excel file download stream for jira issues report.</p>
     *
     * @return the download stream.
     * @throws com.topcoder.excel.output.WorkbookSavingException if any error occurs when generating jira issues report excel file.
     * @throws java.io.IOException if an I/O error occurs.
     */
    public InputStream getInputStream() throws Exception {
            Workbook workbook = new ExcelWorkbook();
            Sheet sheet = new ExcelSheet("Jira Issues", (ExcelWorkbook) workbook);
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
     */
    private void insertSheetData(Sheet sheet) throws Exception {
        //the date format used for displaying 'launch date' and 'resolution date'
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			// set up the sheet header first
			Row row = sheet.getRow(1);
			int index = 1;
			row.getCell(index++).setStringValue("Customer");
			row.getCell(index++).setStringValue("Billing");
			row.getCell(index++).setStringValue("Project");
			row.getCell(index++).setStringValue("Contest Name");
			row.getCell(index++).setStringValue("Contest ID");
			row.getCell(index++).setStringValue("Bug ID");
			row.getCell(index++).setStringValue("Launch Date");
			row.getCell(index++).setStringValue("Title");
			row.getCell(index++).setStringValue("Description");
			row.getCell(index++).setStringValue("Prize");
			row.getCell(index++).setStringValue("Status");
			row.getCell(index++).setStringValue("Reporter");
			row.getCell(index++).setStringValue("Assignee");
			row.getCell(index++).setStringValue("TCO points");
			row.getCell(index++).setStringValue("Resolution Date");
			row.getCell(index++).setStringValue("Votes");
			row.getCell(index++).setStringValue("winner");

			// insert sheet data from 2nd row
			int rowIndex = 2;

			for (JiraIssuesReportEntryDTO dto : getEntries()) {
				row = sheet.getRow(rowIndex++);

				index = 1;

				row.getCell(index++).setStringValue(dto.getCustomer());
				row.getCell(index++).setStringValue(dto.getBillingAccount());
				row.getCell(index++).setStringValue(dto.getProjectName());
				row.getCell(index++).setStringValue(dto.getContestName());
				row.getCell(index++).setNumberValue(dto.getContestId());
				row.getCell(index++).setStringValue(dto.getTicketId());
				row.getCell(index++).setStringValue(dateFormatter.format(dto.getLaunchDate()));
				row.getCell(index++).setStringValue(dto.getTicketTitle());
				row.getCell(index++).setStringValue(dto.getTicketDescription() == null ? "None" : dto.getTicketDescription());
				row.getCell(index++).setNumberValue(dto.getPrize());
				row.getCell(index++).setStringValue(dto.getStatus());
				row.getCell(index++).setStringValue(dto.getReporter());
				row.getCell(index++).setStringValue(dto.getAssignee() == null ? "None" : dto.getAssignee());
				row.getCell(index++).setNumberValue(dto.getTcoPoints());
				row.getCell(index++).setStringValue(dto.getResolutionDate() == null ? "None" : dateFormatter.format(dto.getResolutionDate()));
				row.getCell(index++).setNumberValue(dto.getVotesNumber());
				row.getCell(index++).setStringValue(dto.getWinner());
				}
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
				throw e;
			}

        }
 }
