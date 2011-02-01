/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.form.ContestIdForm;
import com.topcoder.service.user.Registrant;

import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;
import com.topcoder.excel.output.WorkbookSavingException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>A <code>DTO</code> class providing the data for displaying by <code>Contest Registrants</code> view.</p>
 *
 * <p>Version 1.1 (Direct Registrants List assembly) change notes:
 *   <ul>
 *     <li>Added the logic for converting the data content into format for <code>MS Excel</code> program.</li>
 *   </ul>
 * </p>
 *
 * @author isv
 * @version 1.1
 */
public class ContestRegistrantsDTO extends CommonDTO implements ContestStatsDTO.Aware, ContestIdForm.Aware {

    /**
     * <p>A <code>long</code> providing the ID of contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>ContestStatsDTO </code> providing the statistics on contest.</p>
     */
    private ContestStatsDTO contestStats;

    /**
     * <p>A <code>List</code> providing the details for registrants for contest.</p>
     */
    private List<Registrant> contestRegistrants;

    /**
     * <p>A <code>boolean </code> to indicate whether to show spec review comments.</p>
     */
    private boolean showSpecReview;
    
    /**
     * <p>Constructs new <code>ContestRegistrantsDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestRegistrantsDTO() {
    }
    /**
     * <p>Gets the statistics on contest.</p>
     *
     * @return a <code>ContestStatsDTO </code> providing the statistics on contest.
     */
    public ContestStatsDTO getContestStats() {
        return this.contestStats;
    }

    /**
     * <p>Sets the statistics on contest.</p>
     *
     * @param contestStats a <code>ContestStatsDTO </code> providing the statistics on contest.
     */
    public void setContestStats(ContestStatsDTO  contestStats) {
        this.contestStats = contestStats;
    }

    /**
     * <p>Gets the ID of contest.</p>
     *
     * @return a <code>long</code> providing the ID of contest.
     */
    public long getContestId() {
        return this.contestId;
    }

    /**
     * <p>Sets the ID of contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>Gets the list of registrants for requested contest.</p>
     *
     * @return a <code>List</code> providing the details on registrants for requested contest.
     */
    public List<Registrant> getContestRegistrants() {
        return this.contestRegistrants;
    }

    /**
     * <p>Sets the list of registrants for requested contest.</p>
     *
     * @param registrants a <code>List</code> providing the details on registrants for requested contest.
     */
    public void setContestRegistrants(List<Registrant> registrants) {
        this.contestRegistrants = registrants;
    }

    /**
     * <p>Gets the workbook download stream.</p>
     *
     * @return the download stream.
     * @throws WorkbookSavingException if any error occurs when generating workbook.
     * @throws IOException if an I/O error occurs.
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
     * <p>Inserts the sheet data.</p>
     *
     * @param sheet the sheet.
     */
    private void insertSheetData(Sheet sheet) {
        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        NumberFormat ratingFormatter = new DecimalFormat("#######0");

        Row row = sheet.getRow(1);
        row.getCell(1).setStringValue("User ID");
        row.getCell(2).setStringValue("Handle");
        row.getCell(3).setStringValue("Rating");
        row.getCell(4).setStringValue("Registration Date");
        row.getCell(5).setStringValue("Submission Date");

        int rowIndex = 2;
        for (Registrant registrant : this.contestRegistrants) {
            row = sheet.getRow(rowIndex++);
            row.getCell(1).setStringValue(String.valueOf(registrant.getUserId()));
            row.getCell(2).setStringValue(registrant.getHandle());
            row.getCell(3).setStringValue(
                registrant.getRating() == null ? "Not Rated" : ratingFormatter.format(registrant.getRating()));
            row.getCell(4).setStringValue(dateFormatter.format(registrant.getRegistrationDate()));
            if (registrant.getSubmissionDate() != null) {
                row.getCell(5).setStringValue(dateFormatter.format(registrant.getSubmissionDate()));
            }
        }
    }
    /**
     * Get the showSpecReview field.
     *
     * @return the showSpecReview
     */
    public boolean isShowSpecReview() {
        return showSpecReview;
    }
    /**
     * Set the showSpecReview field.
     *
     * @param showSpecReview the showSpecReview to set
     */
    public void setShowSpecReview(boolean showSpecReview) {
        this.showSpecReview = showSpecReview;
    }
}
