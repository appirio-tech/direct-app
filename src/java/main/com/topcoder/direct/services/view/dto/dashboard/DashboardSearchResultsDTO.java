/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.util.JSPHelper;
import com.topcoder.excel.Cell;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;
import com.topcoder.excel.output.WorkbookSavingException;
import com.topcoder.service.facade.contest.ProjectStatusData;

/**
 * <p>
 * A DTO providing the search results for dashboard.
 * </p>
 * <p>
 * Version 2.0 - Add download stream for excel download
 * </p>
 *
 * @author isv, BeBetter
 * @version 2.0
 */
public class DashboardSearchResultsDTO extends CommonDTO implements Serializable {
    /**
     * <p>
     * The constant for date format.
     * </p>
     */
    final static String DATE_FORMAT = "MM/dd/yyyy HH:mm zzz";

    /**
     * <p>
     * A <code>List</code> listing the details for found projects.
     * </p>
     */
    private List<DashboardProjectSearchResultDTO> projects;

    /**
     * <p>
     * A <code>List</code> listing the details for found contests.
     * </p>
     */
    private List<DashboardContestSearchResultDTO> contests;

    /**
     * <p>
     * A <code>List</code> listing the details for found members.
     * </p>
     */
    private List<DashboardMemberSearchResultDTO> members;

    /**
     * <p>
     * A <code>DashboardSearchCriteriaType</code> referencing the type of the results.
     * </p>
     */
    private DashboardSearchCriteriaType resultType;

    /**
     * <p>
     * Constructs new <code>DashboardSearchResultsDTO</code> instance. This implementation does nothing.
     * </p>
     */
    public DashboardSearchResultsDTO() {
    }

    /**
     * <p>
     * Gets the list of found projects.
     * </p>
     *
     * @return a <code>List</code> listing the details for found projects.
     */
    public List<DashboardProjectSearchResultDTO> getProjects() {
        return projects;
    }

    /**
     * <p>
     * Sets the list of found projects.
     * </p>
     *
     * @param projects a <code>List</code> listing the details for found projects.
     */
    public void setProjects(List<DashboardProjectSearchResultDTO> projects) {
        this.projects = projects;
    }

    /**
     * <p>
     * Gets the list of found contests.
     * </p>
     *
     * @return a <code>List</code> listing the details for found contests.
     */
    public List<DashboardContestSearchResultDTO> getContests() {
        return contests;
    }

    /**
     * <p>
     * Sets the list of found contests.
     * </p>
     *
     * @param contests a <code>List</code> listing the details for found contests.
     */
    public void setContests(List<DashboardContestSearchResultDTO> contests) {
        this.contests = contests;
    }

    /**
     * <p>
     * Gets the list of found members.
     * </p>
     *
     * @return a <code>List</code> listing the details for found members.
     */
    public List<DashboardMemberSearchResultDTO> getMembers() {
        return members;
    }

    /**
     * <p>
     * Sets the list of found members.
     * </p>
     *
     * @param members a <code>List</code> listing the details for found members.
     */
    public void setMembers(List<DashboardMemberSearchResultDTO> members) {
        this.members = members;
    }

    /**
     * <p>
     * Gets the results type.
     * </p>
     *
     * @return a <code>DashboardSearchCriteriaType</code> referencing the type of the results.
     */
    public DashboardSearchCriteriaType getResultType() {
        return resultType;
    }

    /**
     * <p>
     * Sets the results type.
     * </p>
     *
     * @param resultType a <code>DashboardSearchCriteriaType</code> referencing the type of the results.
     */
    public void setResultType(DashboardSearchCriteriaType resultType) {
        this.resultType = resultType;
    }

    /**
     * <p>
     * Gets the workbook download stream.
     * </p>
     *
     * @return the download stream
     * @throws WorkbookSavingException if any error occurs when generating workbook
     * @throws IOException IO error occurs
     */
    public InputStream getInputStream() throws WorkbookSavingException, IOException {
        Workbook workbook = new ExcelWorkbook();
        Sheet sheet = new ExcelSheet("Sheet1", (ExcelWorkbook) workbook);
        insertSheetData(sheet);
        workbook.addSheet(sheet);

        // Create a new WorkBookSaver
        WorkbookSaver saver = new Biff8WorkbookSaver();
        ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
        saver.save(workbook, saveTo);
        return new ByteArrayInputStream(saveTo.toByteArray());
    }

    /**
     * <p>
     * Gets status string.
     * </p>
     *
     * @param statusData the status data
     * @param nf the numer format
     * @return the status string
     */
    private String getStatusString(ProjectStatusData statusData, NumberFormat nf) {
        return statusData.getTotalNumber() + "/" + nf.format(statusData.getTotalPayment());
    }

    /**
     * <p>
     * Gets start end string.
     * </p>
     *
     * @param start the start date
     * @param end the end date
     * @return the start end string
     */
    private String getStartEndString(Date start, Date end) {
        return new SimpleDateFormat(DATE_FORMAT).format(start) + " " + JSPHelper.getEndText(end);
    }

    /**
     * <p>
     * Inserts the sheet data.
     * </p>
     *
     * @param sheet the sheet
     */
    private void insertSheetData(Sheet sheet) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        if (projects != null) {
            int rowIndex = 1;
            {
                Row row = sheet.getRow(rowIndex++);
                int columnIndex = 1;
                Cell cell = row.getCell(columnIndex++);
                cell.setStringValue("Project");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Draft");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Scheduled");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Active");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Finished");
            }

            for (DashboardProjectSearchResultDTO project : projects) {
                Row row = sheet.getRow(rowIndex++);
                int columnIndex = 1;
                Cell cell = row.getCell(columnIndex++);
                cell.setStringValue(project.getProject().getName());
                cell = row.getCell(columnIndex++);
                cell.setStringValue(getStatusString(project.getData().getDraft(), nf));
                cell = row.getCell(columnIndex++);
                cell.setStringValue(getStatusString(project.getData().getScheduled(), nf));
                cell = row.getCell(columnIndex++);
                cell.setStringValue(getStatusString(project.getData().getActive(), nf));
                cell = row.getCell(columnIndex++);
                cell.setStringValue(getStatusString(project.getData().getFinished(), nf));
            }
        } else if (contests != null) {
            int rowIndex = 1;
            {
                Row row = sheet.getRow(rowIndex++);
                int columnIndex = 1;
                Cell cell = row.getCell(columnIndex++);
                cell.setStringValue("Contest Type");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Contest Name");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Start/End");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Registrants");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Submissions");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Forums");
                cell = row.getCell(columnIndex++);
                cell.setStringValue("Status");
            }

            for (DashboardContestSearchResultDTO contest : contests) {
                Row row = sheet.getRow(rowIndex++);
                int columnIndex = 1;
                Cell cell = row.getCell(columnIndex++);
                cell.setStringValue(contest.getContestType());
                cell = row.getCell(columnIndex++);
                cell.setStringValue(contest.getContest().getTitle());
                cell = row.getCell(columnIndex++);
                cell.setStringValue(getStartEndString(contest.getStartTime(), contest.getEndTime()));
                cell = row.getCell(columnIndex++);
                cell.setNumberValue(contest.getRegistrantsNumber());
                cell = row.getCell(columnIndex++);
                cell.setNumberValue(contest.getSubmissionsNumber());
                cell = row.getCell(columnIndex++);
                cell.setNumberValue(contest.getForumPostsNumber());
                cell = row.getCell(columnIndex++);
                cell.setStringValue(contest.getStatus().getName());
            }
        }
    }

}
