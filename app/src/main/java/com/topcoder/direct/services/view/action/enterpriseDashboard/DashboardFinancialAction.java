/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.enterpriseDashboard;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardProjectFinancialDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardTotalSpendDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.TotalSpendDrillInDTO;
import com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * The action handles the ajax requests for financial part of the enterprise dashboard.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 1)
 * <ul>
 *     <li>Add new data in the return json of getTotalSpend Ajax call.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
 * <ul>
 *     <li>Add method {@link #getTotalSpendDrillIn()} to handle Total Spend Drill-in Ajax request</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
 * <ul>
 *     <li>Add property {@link #financialExportStream}</li>
 *     <li>Add method {@link #getTotalSpendExportAll()}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TC Cockpit Enterprise Dashboard Projected Cost and Project Health Page)
 * <ul>
 *     <li>Adds projected cost to total spend chart</li>
 *     <li>Adds projected cost to total spend drill in and export</li>
 *     <li>Adds method {@link #getHealthExportAll()} to export the financial health data of projects</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.4
 */
public class DashboardFinancialAction extends BaseDirectStrutsAction implements FormAction<EnterpriseDashboardFilterForm> {

    /**
     * The date format to display the X axis of total spend chart.
     */
    private static final DateFormat AXIS_DATE_FORMAT = new SimpleDateFormat("MMM''yy");

    /**
     * The form data of the action.
     */
    private EnterpriseDashboardFilterForm formData = new EnterpriseDashboardFilterForm();

    /**
     * The export input stream of total spend drill-in.
     *
     * @since 1.2
     */
    private InputStream financialDrillInStream;

    /**
     * The export input stream for the total spend export all.
     *
     * @since 1.3
     */
    private InputStream financialExportStream;

    /**
     * The flag to determine whether to export the drill-in result to excel file.
     *
     * @since 1.2
     */
    private boolean export;

    /**
     * Gets the form data of the action.
     *
     * @return the form data of the action.
     */
    public EnterpriseDashboardFilterForm getFormData() {
        return this.formData;
    }

    /**
     * Sets the form data of the action.
     *
     * @param formData the form data of the action.
     */
    public void setFormData(EnterpriseDashboardFilterForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the total spend financial drill-in export stream.
     *
     * @return the total spend financial drill-in export stream
     * @since 1.2
     */
    public InputStream getFinancialDrillInStream() {
        return financialDrillInStream;
    }

    /**
     * Gets the total spend export all stream
     *
     * @return the total spend export all stream
     * @since 1.3
     */
    public InputStream getFinancialExportStream() {
        return financialExportStream;
    }

    /**
     * Sets the export flag.
     *
     * @param export the export flag.
     * @since 1.2
     */
    public void setExport(boolean export) {
        this.export = export;
    }

    /**
     * Empty, ajax requests are handled via action methods invocation.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * Handles the ajax request to get the data for the total spend chart.
     *
     * @return the result code.
     */
    public String getTotalSpend() {
        try {
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();

            final List<EnterpriseDashboardTotalSpendDTO> enterpriseDashboardTotalSpend =
                    DataProvider.getEnterpriseDashboardTotalSpend(getFormData());

            for(EnterpriseDashboardTotalSpendDTO item : enterpriseDashboardTotalSpend) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("label", AXIS_DATE_FORMAT.format(item.getDate()));
                m.put("memberCost", String.valueOf(item.getMemberCost()));
                m.put("contestFee", String.valueOf(item.getContestFee()));
                m.put("spend", String.valueOf(item.getTotalSpend()));
                m.put("projected", String.valueOf(item.getTotalProjected()));
                m.put("average", String.valueOf(item.getAverageSpend()));
                result.add(m);
            }

            setResult(result);
        } catch (Throwable e) {

            e.printStackTrace(System.err);

            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Gets the total spend drill-in ajax request.
     *
     * @return result code.
     * @since 1.2
     */
    public String getTotalSpendDrillIn() {
        try {
            final List<TotalSpendDrillInDTO> drillInDTOList =
                    DataProvider.getEnterpriseDashboardTotalSpendDrillIn(getFormData(), true);

            if(export) {
                Workbook workbook = new ExcelWorkbook();

                workbook.addSheet(createTotalSpendDrillInSheet(workbook, "Total Spend " + getFormData().getStartMonth
                        (), drillInDTOList));

                // Create a new WorkBookSaver
                WorkbookSaver saver = new Biff8WorkbookSaver();
                ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
                saver.save(workbook, saveTo);
                this.financialDrillInStream = new ByteArrayInputStream(saveTo.toByteArray());

                return "download";

            } else {
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();

                for(TotalSpendDrillInDTO item : drillInDTOList) {
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("directProjectId", String.valueOf(item.getDirectProjectId()));
                    m.put("directProjectName", item.getDirectProjectName());
                    m.put("memberCost", String.valueOf(item.getMemberCost()));
                    m.put("contestFee", String.valueOf(item.getContestFee()));
                    m.put("spend", String.valueOf(item.getTotalSpend()));
                    m.put("projectedCost", String.valueOf(item.getTotalProjected()));
                    m.put("projectedTotal", String.valueOf(item.getProjectedTotalSpend()));
                    result.add(m);
                }

                setResult(result);
            }

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }


    /**
     * Handles the total spend export all data request.
     *
     * @return result code.
     * @since 1.3
     */
    public String getTotalSpendExportAll() {
        try {
            final List<TotalSpendDrillInDTO> drillInDTOList =
                    DataProvider.getEnterpriseDashboardTotalSpendDrillIn(getFormData(), false);

            Workbook workbook = new ExcelWorkbook();

            // sheets map store all the sheets, one sheet per month
            Map<Date, List<TotalSpendDrillInDTO>> sheetsMap = new TreeMap<Date, List<TotalSpendDrillInDTO>>();

            // initialize the sheets map, from start month to end month, each month will have an entry in sheetsMap
            DateFormat formDateFormatter = new SimpleDateFormat("MMM''yy");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formDateFormatter.parse(getFormData().getStartMonth()));
            long startMonthCount = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
            calendar.setTime(formDateFormatter.parse(getFormData().getEndMonth()));
            long endMonthCount = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);

            for (long m = startMonthCount; m <= endMonthCount; m++) {
                calendar.set(Calendar.YEAR, (int) m / 12);
                calendar.set(Calendar.MONTH, (int) m % 12);
                // put in an empty array list first
                sheetsMap.put(calendar.getTime(), new ArrayList<TotalSpendDrillInDTO>());
            }

            for (TotalSpendDrillInDTO data : drillInDTOList) {
                sheetsMap.get(dateFormatter.parse(data.getYearMonthLabel())).add(data);
            }

            // Add all the sheets into the workbook, one sheet per month
            for (Map.Entry<Date, List<TotalSpendDrillInDTO>> entry : sheetsMap.entrySet()) {
                workbook.addSheet(createTotalSpendDrillInSheet(workbook, "Total Spend " + dateFormatter.format(entry
                        .getKey()), entry.getValue()));
            }

            // Create a new WorkBookSaver
            WorkbookSaver saver = new Biff8WorkbookSaver();
            ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
            saver.save(workbook, saveTo);
            this.financialExportStream = new ByteArrayInputStream(saveTo.toByteArray());
            return "download";
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Exports the financial health data to the excel file.
     *
     * @return the result code.
     * @since 1.4 (Release Assembly - TC Cockpit Enterprise Dashboard Projected Cost and Project Health Page)
     */
    public String getHealthExportAll() {
        try {
            final List<EnterpriseDashboardProjectFinancialDTO> projects = DataProvider.getEnterpriseDashboardProjectsFinancialInfo(
                    getFormData());

            Workbook workbook = new ExcelWorkbook();

            Sheet sheet = new ExcelSheet("Projects Health", (ExcelWorkbook) workbook);

            // set up the sheet header first
            Row row = sheet.getRow(1);
            int index = 1;

            row.getCell(index++).setStringValue("Project Name");
            row.getCell(index++).setStringValue("Total Budget");
            row.getCell(index++).setStringValue("Actual Cost");
            row.getCell(index++).setStringValue("Projected Cost");

            // insert sheet data from 2nd row
            int rowIndex = 2;

            for(EnterpriseDashboardProjectFinancialDTO data: projects) {
                // get the next row, and increase index
                row = sheet.getRow(rowIndex++);

                // set column index, start from 1
                index = 1;
                row.getCell(index++).setStringValue(data.getProjectName());
                row.getCell(index++).setNumberValue(data.getBudget());
                row.getCell(index++).setNumberValue(data.getActualCost());
                row.getCell(index++).setNumberValue(data.getProjectedCost());
            }

            workbook.addSheet(sheet);

            // Create a new WorkBookSaver
            WorkbookSaver saver = new Biff8WorkbookSaver();
            ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
            saver.save(workbook, saveTo);
            this.financialExportStream = new ByteArrayInputStream(saveTo.toByteArray());
            return "download";
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }



    /**
     * Handles the ajax request to get the data for the financial section.
     *
     * @return the result code.
     */
    public String getFinancialStatistics() {
        try {
            Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String,String>>>();
            List<Map<String, String>> financialList = new ArrayList<Map<String, String>>();

            final List<EnterpriseDashboardProjectFinancialDTO> projects = DataProvider.getEnterpriseDashboardProjectsFinancialInfo(getFormData());

            for(EnterpriseDashboardProjectFinancialDTO item : projects) {
                Map<String, String> itemResult = new HashMap<String, String>();
                itemResult.put("projectId", String.valueOf(item.getProjectId()));
                itemResult.put("projectName", item.getProjectName());
                itemResult.put("totalBudget", String.valueOf(item.getBudget()));
                itemResult.put("actualCost", String.valueOf(item.getActualCost()));
                itemResult.put("plannedCost", String.valueOf(item.getPlannedCost()));
                itemResult.put("projectedCost", String.valueOf(item.getProjectedCost()));
                financialList.add(itemResult);
            }

            result.put("financials", financialList);

            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Creates the excel sheet for a specified workbook with the given sheet name and sheet data for
     * total spend drill in.
     *
     * @param workbook the work book the sheet is in
     * @param sheetName the name of the sheet.
     * @param sheetData the sheet data
     * @return the created sheet
     * @throws Exception if any error
     * @since 1.3
     */
    private static Sheet createTotalSpendDrillInSheet(Workbook workbook, String sheetName,
                                                      List<TotalSpendDrillInDTO> sheetData) throws Exception {
        Sheet sheet = new ExcelSheet(sheetName, (ExcelWorkbook) workbook);

        // set up the sheet header first
        Row row = sheet.getRow(1);
        int index = 1;

        row.getCell(index++).setStringValue("Project Name");
        row.getCell(index++).setStringValue("Actual Member Cost");
        row.getCell(index++).setStringValue("Projected Cost");
        row.getCell(index++).setStringValue("Actual Contest Fees");
        row.getCell(index++).setStringValue("Actual Total Cost");
        row.getCell(index++).setStringValue("Projected Total Cost");

        // insert sheet data from 2nd row
        int rowIndex = 2;

        for(TotalSpendDrillInDTO data: sheetData) {
            // get the next row, and increase index
            row = sheet.getRow(rowIndex++);

            // set column index, start from 1
            index = 1;
            row.getCell(index++).setStringValue(data.getDirectProjectName());
            row.getCell(index++).setNumberValue(data.getMemberCost());
            row.getCell(index++).setNumberValue(data.getTotalProjected());
            row.getCell(index++).setNumberValue(data.getContestFee());
            row.getCell(index++).setNumberValue(data.getTotalSpend());
            row.getCell(index++).setNumberValue(data.getProjectedTotalSpend());
        }

        return sheet;
    }

}
