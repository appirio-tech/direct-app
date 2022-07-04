/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.ClientUserStatsDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;
import com.topcoder.security.TCSubject;
import com.topcoder.service.permission.PermissionServiceException;

/**
 * <p>
 * This action grabs required data for front-end page regarding client user statistics.
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe. But Struts2 framework would use it in thread-safe
 * manner.
 * </p>
 * 
 * @author leo_lol
 * @version 1.0
 * @since 1.0
 */
public class ClientUserStatsReportAction extends AbstractAction {
    /**
     * Represents the excel file name when exporting client user stats report.
     */
    private static final String CLIENT_USER_STATS_REPORT_EXCEL_FILE_NAME = "client_user_stats_report.xls";

    /**
     * <p>
     * Serial ID.
     * </p>
     */
    private static final long serialVersionUID = -3594794941833381195L;

    /**
     * List of client names, used to display in the front end.
     */
    private List<String> clientNames = new ArrayList<String>();

    /**
     * Available year-month values.
     */
    private List<String> yearMonths = new ArrayList<String>();

    /**
     * Currently selected client Id.
     */
    private String currentClientName;

    /**
     * Currently selected yearMonth pair.
     */
    private String currentYearMonth;

    /**
     * Resulting statistics to display.
     */
    private List<ClientUserStatsDTO> clientUserStats;

    /**
     * Indicate if required to export as excel document.
     */
    private boolean excel;


    /**
     * <p>
     * This method would load required data from database.
     * </p>
     * 
     * @return action result.
     * @throws Exception
     *             if there is any error.
     */
    @Override
    public String execute() throws Exception {
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        boolean isAdmin = DirectUtils.isTcStaff(currentUser) || DirectUtils.isTcOperations(currentUser);

        if (!isAdmin) {
            throw new PermissionServiceException("You do not have permission to access this page");
        }

        List<ClientUserStatsDTO> all = DataProvider.getClientUserStats();
        
        for (ClientUserStatsDTO dto : all) {
            if(!clientNames.contains(dto.getClientName())) {
                clientNames.add(dto.getClientName());
            }
            
            if(!yearMonths.contains(dto.getYearMonth())) {
                yearMonths.add(dto.getYearMonth());
            }
        }

        if (null == currentClientName || "All".equals(currentClientName)) {
            if(null == currentYearMonth || "All".equals(currentYearMonth)) {
                clientUserStats = all;
            } else {
                clientUserStats = new ArrayList<ClientUserStatsDTO>();
                for(ClientUserStatsDTO dto : all) {
                    if(currentYearMonth.equals(dto.getYearMonth())) {
                        clientUserStats.add(dto);
                    }
                }
            }
        } else {
            clientUserStats = new ArrayList<ClientUserStatsDTO>();
            if (null == currentYearMonth || "All".equals(currentYearMonth)) {
                for (ClientUserStatsDTO dto : all) {
                    if (currentClientName.equals(dto.getClientName())) {
                        clientUserStats.add(dto);
                    }
                }
            } else {
                for (ClientUserStatsDTO dto : all) {
                    if (currentClientName.equals(dto.getClientName()) && currentYearMonth.equals(dto.getYearMonth())) {
                        clientUserStats.add(dto);
                    }
                }
            }
        }
        Collections.sort(clientUserStats);
        
        if (excel) {
            return "download";
        }
        return SUCCESS;
    }
    
    /**
     * Return the excel file name when exporting the report.
     * 
     * @return the excel file name
     */
    public String getExcelFileName() {
        return CLIENT_USER_STATS_REPORT_EXCEL_FILE_NAME;
    }

    /**
     * <p>
     * Gets the excel file download stream for client user stats report.
     * </p>
     * 
     * @return the download stream.
     * @throws com.topcoder.excel.output.WorkbookSavingException
     *             if any error occurs when generating client user stats report excel file.
     * @throws java.io.IOException
     *             if an I/O error occurs.
     */
    public InputStream getInputStream() throws Exception {
        Workbook workbook = new ExcelWorkbook();
        Sheet sheet = new ExcelSheet("Client User Stats", (ExcelWorkbook) workbook);
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
     * Inserts the sheet data.
     * </p>
     * 
     * @param sheet
     *            the sheet.
     * @throws Exception if there is any error.
     */
    private void insertSheetData(Sheet sheet) throws Exception {
        // set up the sheet header first
        Row row = sheet.getRow(1);
        int index = 1;
        row.getCell(index++).setStringValue("Client Name");
        row.getCell(index++).setStringValue("Year/Month");
        row.getCell(index++).setStringValue("# of user");
        // insert sheet data from 2nd row
        int rowIndex = 2;

        for (ClientUserStatsDTO dto : clientUserStats) {
            row = sheet.getRow(rowIndex++);
            index = 1;
            row.getCell(index++).setStringValue(dto.getClientName());
            row.getCell(index++).setStringValue(dto.getYearMonth());
            row.getCell(index++).setStringValue(String.valueOf(dto.getUserCount()));
        }
    }
    
    /**
     * <p>
     * Getter of clientNames field.
     * </p>
     *
     * @return the clientNames
     */
    public List<String> getClientNames() {
        return clientNames;
    }

    /**
     * <p>
     * Setter of clientNames field.
     * </p>
     *
     * @param clientNames the clientNames to set
     */
    public void setClientNames(List<String> clientNames) {
        this.clientNames = clientNames;
    }

    /**
     * <p>
     * Getter of currentClientName field.
     * </p>
     *
     * @return the currentClientName
     */
    public String getCurrentClientName() {
        return currentClientName;
    }

    /**
     * <p>
     * Setter of currentClientName field.
     * </p>
     *
     * @param currentClientName the currentClientName to set
     */
    public void setCurrentClientName(String currentClientName) {
        this.currentClientName = currentClientName;
    }

    /**
     * <p>
     * Getter of currentYearMonth field.
     * </p>
     * 
     * @return the currentYearMonth
     */
    public String getCurrentYearMonth() {
        return currentYearMonth;
    }

    /**
     * <p>
     * Setter of currentYearMonth field.
     * </p>
     * 
     * @param currentYearMonth
     *            the currentYearMonth to set
     */
    public void setCurrentYearMonth(String currentYearMonth) {
        this.currentYearMonth = currentYearMonth;
    }

    /**
     * <p>
     * Getter of clientUserStats field.
     * </p>
     * 
     * @return the clientUserStats
     */
    public List<ClientUserStatsDTO> getClientUserStats() {
        return clientUserStats;
    }

    /**
     * <p>
     * Setter of clientUserStats field.
     * </p>
     * 
     * @param clientUserStats
     *            the clientUserStats to set
     */
    public void setClientUserStats(List<ClientUserStatsDTO> clientUserStats) {
        this.clientUserStats = clientUserStats;
    }

    /**
     * <p>
     * Getter of yearMonths field.
     * </p>
     * 
     * @return the yearMonths
     */
    public List<String> getYearMonths() {
        return yearMonths;
    }

    /**
     * <p>
     * Setter of yearMonths field.
     * </p>
     * 
     * @param yearMonths
     *            the yearMonths to set
     */
    public void setYearMonths(List<String> yearMonths) {
        this.yearMonths = yearMonths;
    }

    /**
     * <p>
     * Getter of excel field.
     * </p>
     * 
     * @return the excel
     */
    public boolean isExcel() {
        return excel;
    }

    /**
     * <p>
     * Setter of excel field.
     * </p>
     * 
     * @param excel
     *            the excel to set
     */
    public void setExcel(boolean excel) {
        this.excel = excel;
    }
}
