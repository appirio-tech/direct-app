/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.costreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.ReportAggregationBaseDTO;
import com.topcoder.direct.services.view.dto.ReportType;
import com.topcoder.direct.services.view.dto.dashboard.DashboardCostBreakDownDTO;
import com.topcoder.direct.services.view.util.DataProvider;
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
 * The DTO to store the cost report data. It includes cost details report data and 4 aggregation cost report data:
 * <li>project aggregation cost report</li>
 * <li>billing aggregation cost report</li>
 * <li>contest status aggregation cost report</li>
 * <li>contest type aggregation cost report</li>
 * It also contains the input stream to serialize cost details report data.
 * </p>
 *
 * <p>
 * Version 1.0.1 (TC Cockpit Cost Report Update Cost Breakdown Assembly) change note:
 * <ol>
 * <li>Added {@link #showBreakdown} property to indicate whether to export the cost break down,
 * also the getter/setter were added.</li>
 * <li>Added {@link #getExcelFileName()} method to return the excel file name when exporting the report.</li>
 * <li>Updated {@link #insertSheetData(Sheet)} method to support exporting cost breakdown data to excel.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - Direct Improvements Assembly Release 3) change note:
 * <ol>
 * <li>Change all cost related call value in the excel to format 'Number', it was 'String'.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>This class has been refactoring. It's extended from <code>ReportAggregationBaseDTO</code>.</li>
 * </ol>
 * </p>
 * 
 * @author Blues, flexme, TCSDEVELOPER
 * @version  1.2 (TopCoder Cockpit - Cost Report Assembly)
 *
 */
public class CostReportDTO extends ReportAggregationBaseDTO<CostAggregationDTO> {

    /**
     * Represents the excel file name when exporting cost report.
     *
     * @since 1.0.1
     */
    private static final String COST_REPORT_EXCEL_FILE_NAME = "cost_report.xls";
    
    /**
     * Represents the excel file name when exporting cost breakdown report.
     *
     * @since 1.0.1
     */
    private static final String COST_BREAKDOWN_REPORT_EXCEL_FILE_NAME = "cost_report_breakdown.xls";

    /**
     * The list of cost details of each contest.
     */
    private List<CostDetailsDTO> costDetails;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the cost break down data should display in
     * the cost report.</p>
     * 
     * @since 1.0.1
     */
    private boolean showBreakdown;

    /**
     * Construct a new <code>CostReportDTO</code> instance.
     */
    public CostReportDTO() {
        super();
    }

    /**
     * Gets the list of contest cost details.
     *
     * @return the list of contest cost details
     */
    public List<CostDetailsDTO> getCostDetails() {
        return costDetails;
    }

    /**
     * Sets the list of contest cost  details.
     *
     * @param costDetails the contes cost details to set.
     */
    public void setCostDetails(List<CostDetailsDTO> costDetails) {
        this.costDetails = costDetails;
    }

    /**
     * Gets the report type of this report DTO. It simply returns ReportType.COST.
     *
     * @return the the cost report type.
     */
    public ReportType getReportType() {
        return ReportType.COST;
    }

    /**
     * Gets the flag indicating whether the cost breakdown data should display in the report.
     *
     * @return true if the cost breakdown data should display in the report, false otherwise.
     * @since 1.0.1
     */
    public boolean isShowBreakdown() {
        return showBreakdown;
    }

    /**
     * Sets the flag indicating whether the cost breakdown data should display in the report.
     * 
     * @param showBreakdown true if the cost breakdown data should display in the report, false otherwise.
     * @since 1.0.1
     */
    public void setShowBreakdown(boolean showBreakdown) {
        this.showBreakdown = showBreakdown;
    }

    /**
     * <p>Gets the excel file download stream for cost report.</p>
     *
     * @return the download stream.
     * @throws WorkbookSavingException if any error occurs when generating cost report excel file.
     * @throws IOException if an I/O error occurs.
     */
    public InputStream getInputStream() throws WorkbookSavingException, IOException {
        try {

            Workbook workbook = new ExcelWorkbook();
            Sheet sheet = new ExcelSheet("Cost Details", (ExcelWorkbook) workbook);
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
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");


        // set up the sheet header first
        Row row = sheet.getRow(1);
        int index = 1;
        row.getCell(index++).setStringValue("Customer");
        row.getCell(index++).setStringValue("Billing");
        row.getCell(index++).setStringValue("Project");
        row.getCell(index++).setStringValue("Filter Value");
        row.getCell(index++).setStringValue("Contest");
		row.getCell(index++).setStringValue("Contest Id");
        row.getCell(index++).setStringValue("Contest Type");
        row.getCell(index++).setStringValue("Status");
        row.getCell(index++).setStringValue("Completion Date");
        row.getCell(index++).setStringValue("Contest Fee");
        row.getCell(index++).setStringValue("Estimated Member Cost");
        row.getCell(index++).setStringValue("Actual Member Cost");
        if (isShowBreakdown()) {
            row.getCell(index++).setStringValue("Prizes");
            row.getCell(index++).setStringValue("Spec Review");
            row.getCell(index++).setStringValue("Review");
            row.getCell(index++).setStringValue("Reliability");
            row.getCell(index++).setStringValue("Digital Run");
            row.getCell(index++).setStringValue("Copilot");
            row.getCell(index++).setStringValue("Build");
            row.getCell(index++).setStringValue("Bugs");
            row.getCell(index++).setStringValue("Misc");
        }
        row.getCell(index++).setStringValue("Total");
        
        // insert sheet data from 2nd row
        int rowIndex = 2;

        Map<Long, DashboardCostBreakDownDTO> breakdownMap = new HashMap<Long, DashboardCostBreakDownDTO>();
        if (isShowBreakdown()) {
            long[] projectIds = new long[getCostDetails().size()];
            for (int i = 0; i < getCostDetails().size(); i++) {
                projectIds[i] = getCostDetails().get(i).getContest().getId();
            }
            List<DashboardCostBreakDownDTO> breakDown = DataProvider.getDashboardCostBreakDown(DirectStrutsActionsHelper.getTCSubjectFromSession(), projectIds, null, null, null);
            for (DashboardCostBreakDownDTO data : breakDown) {
                breakdownMap.put(data.getId(), data);
            }
        }
        for (CostDetailsDTO costDetail : getCostDetails()) {
            row = sheet.getRow(rowIndex++);

            index = 1;
            // set the customer
            row.getCell(index++).setStringValue(costDetail.getClient().getName());

            // set the billing account name
            row.getCell(index++).setStringValue(costDetail.getBilling().getName());

            // set the project name
            row.getCell(index++).setStringValue(costDetail.getProject().getName());
			
            
            row.getCell(index++).setStringValue(costDetail.getProjectFilterValue() == null ? "None" : costDetail.getProjectFilterValue());

            // set the contest name
            row.getCell(index++).setStringValue(costDetail.getContest().getName());
			
			            // set the contest id
            row.getCell(index++).setNumberValue(costDetail.getContest().getId());

            // set the contest type
            row.getCell(index++).setStringValue(costDetail.getContestType().getName());

            // set the status
            row.getCell(index++).setStringValue(costDetail.getStatus());

            // set the completion date
            row.getCell(index++).setStringValue(dateFormatter.format(costDetail.getCompletionDate()));

            // set the contest fee
            row.getCell(index++).setNumberValue(costDetail.getContestFee());

			// set the estimated member cost
            row.getCell(index++).setNumberValue(costDetail.getEstimatedCost());


            // set the actual member cost, the 'active' and 'scheduled' contest does not have actual member cost
            if (costDetail.getStatus().trim().toLowerCase().equals("finished")) {
                row.getCell(index).setNumberValue(costDetail.getActualCost());
            }
            index++;

            if (isShowBreakdown()) {
                DashboardCostBreakDownDTO breakdown = breakdownMap.get(costDetail.getContest().getId());
                if (breakdown != null) {
                    // set Prizes cost
                    row.getCell(index++).setNumberValue(breakdown.getPrizes());
                    
                    // set Spec Review cost
                    row.getCell(index++).setNumberValue(breakdown.getSpecReview());
                    
                    // set Review cost
                    row.getCell(index++).setNumberValue(breakdown.getReview());
                    
                    // set Reliability cost
                    row.getCell(index++).setNumberValue(breakdown.getReliability());
                    
                    // set Digital Run cost
                    row.getCell(index++).setNumberValue(breakdown.getDigitalRun());
                    
                    // set Copilot cost
                    row.getCell(index++).setNumberValue(breakdown.getCopilot());
                    
                    // set Build cost
                    row.getCell(index++).setNumberValue(breakdown.getBuild());
                    
                    // set Bugs cost
                    row.getCell(index++).setNumberValue(breakdown.getBugs());
                    
                    // set Misc cost
                    row.getCell(index++).setNumberValue(breakdown.getMisc());
                } else {
                    index += 9;
                }
            }
            // set the total cost
            row.getCell(index++).setNumberValue(costDetail.getTotal());
        }
    }

    /**
     * Return the excel file name when exporting the report.
     * 
     * @return the excel file name
     * @since 1.0.1
     */
    public String getExcelFileName() {
        if (isShowBreakdown()) {
            return COST_BREAKDOWN_REPORT_EXCEL_FILE_NAME;
        } else {
            return COST_REPORT_EXCEL_FILE_NAME;
        }
    }
}
