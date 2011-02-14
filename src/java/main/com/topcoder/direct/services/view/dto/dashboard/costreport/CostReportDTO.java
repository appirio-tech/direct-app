/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.costreport;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.ReportType;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;
import com.topcoder.excel.output.WorkbookSavingException;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


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
 * @author TCSDEVELOPER
 * @version  1.0 (TopCoder Cockpit - Cost Report Assembly)
 *
 */
public class CostReportDTO extends CommonDTO implements Serializable {

    /**
     * The project aggregation cost report data.
     */
    private Map<String, CostAggregationDTO> projectAggregation;

    /*
     * The contest type aggregation cost report data.
     */
    private Map<String, CostAggregationDTO> contestTypeAggregation;

    /**
     * The status aggregation cost report data.
     */
    private Map<String, CostAggregationDTO> statusAggregation;

    /**
     * The billing account aggregation cost report data.
     */
    private Map<String, CostAggregationDTO> billingAggregation;

    /**
     * The list of cost details of each contest.
     */
    private List<CostDetailsDTO> costDetails;

    /**
     * The map stores the direct project information.
     */
    private Map<Long, String> projectsLookupMap;

    /**
     * <p>A <code>Map</code> providing the mapping from IDs to names of client billing projects.</p>
     */
    private Map<Long, String> clientBillingProjects;

    /**
     * <p>A <code>Map</code> providing the mapping from IDs to names of available client accounts.</p>
     */
    private Map<Long, String> clientAccounts;

    /**
     * <p>A <code>Map</code> providing the mapping from project category IDs to names.</p>
     */
    private Map<Long, String> projectCategories;

    /**
     * <p>A <code>Map</code> providing the mapping from status id to status names.</p>
     */
    private Map<Long, String> contestStatus;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the cost report data is to be calculated and
     * displayed.</p>
     */
    private boolean showJustForm;

    /**
     * Gets the project aggregation cost report data.
     *
     * @return the project aggregation cost report data.
     */
    public Map<String, CostAggregationDTO> getProjectAggregation() {
        return projectAggregation;
    }

    /**
     * Sets the project aggregation cost report data.
     *
     * @param projectAggregation the project aggregation cost report data to set.
     */
    public void setProjectAggregation(Map<String, CostAggregationDTO> projectAggregation) {
        this.projectAggregation = projectAggregation;
    }

    /**
     * Gets the contest type aggregation cost report data.
     *
     * @return the contest type aggregation cost report data.
     */
    public Map<String, CostAggregationDTO> getContestTypeAggregation() {
        return contestTypeAggregation;
    }

    /**
     * Sets the contest type aggregation cost report data.
     *
     * @param contestTypeAggregation the contest type aggregation cost report data to set.
     */
    public void setContestTypeAggregation(Map<String, CostAggregationDTO> contestTypeAggregation) {
        this.contestTypeAggregation = contestTypeAggregation;
    }

    /**
     * Gets the status aggregation cost report data.
     *
     * @return the status aggregation cost report data.
     */
    public Map<String, CostAggregationDTO> getStatusAggregation() {
        return statusAggregation;
    }

    /**
     * Sets the status aggregation cost report data.
     *
     * @param statusAggregation the status aggregation cost report data to set.
     */
    public void setStatusAggregation(Map<String, CostAggregationDTO> statusAggregation) {
        this.statusAggregation = statusAggregation;
    }

    /**
     * Gets the billing aggregation cost report data.
     *
     * @return the billing aggregation cost report data.
     */
    public Map<String, CostAggregationDTO> getBillingAggregation() {
        return billingAggregation;
    }

    /**
     * Sets the billing aggregation cost report data.
     *
     * @param billingAggregation the billing aggregation cost report data to set.
     */
    public void setBillingAggregation(Map<String, CostAggregationDTO> billingAggregation) {
        this.billingAggregation = billingAggregation;
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
     * Gets the projects lookup map which stores the direct project information.
     *
     * @return the projects lookup map.
     */
    public Map<Long, String> getProjectsLookupMap() {
        return projectsLookupMap;
    }

    /**
     * Sets the project lookup map.
     *
     * @param projectsLookupMap the projects lookup map to set.
     */
    public void setProjectsLookupMap(Map<Long, String> projectsLookupMap) {
        this.projectsLookupMap = projectsLookupMap;
    }

    /**
     * Gets the client billing projects.
     *
     * @return the client billing projects.
     */
    public Map<Long, String> getClientBillingProjects() {
        return clientBillingProjects;
    }

    /**
     * Sets the client billing projects.
     *
     * @param clientBillingProjects the client billing projects to set.
     */
    public void setClientBillingProjects(Map<Long, String> clientBillingProjects) {
        this.clientBillingProjects = clientBillingProjects;
    }

    /**
     * Gets the client accounts.
     *
     * @return the client accounts.
     */
    public Map<Long, String> getClientAccounts() {
        return clientAccounts;
    }

    /**
     * Sets the client accounts.
     *
     * @param clientAccounts the client accounts to set.
     */
    public void setClientAccounts(Map<Long, String> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    /**
     * Gets the project categories.
     *
     * @return the project categories.
     */
    public Map<Long, String> getProjectCategories() {
        return projectCategories;
    }

    /**
     * Sets the project categories.
     *
     * @param projectCategories the project categories to set.
     */
    public void setProjectCategories(Map<Long, String> projectCategories) {
        this.projectCategories = projectCategories;
    }

    /**
     * Gets the contest status.
     *
     * @return the contest status.
     */
    public Map<Long, String> getContestStatus() {
        return contestStatus;
    }

    /**
     * Sets the contest status.
     *
     * @param contestStatus the contest status to set.
     */
    public void setContestStatus(Map<Long, String> contestStatus) {
        this.contestStatus = contestStatus;
    }

    /**
     * Gets the flag to determine whether only show the form.
     *
     * @return the flag to determine whether only show the form.
     */
    public boolean isShowJustForm() {
        return showJustForm;
    }

    /**
     * Sets the flag of whether only show the form.
     *
     * @param showJustForm flag to determine whether only show the form.
     */
    public void setShowJustForm(boolean showJustForm) {
        this.showJustForm = showJustForm;
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
    private void insertSheetData(Sheet sheet) {
        // the date format used for displaying 'completion date'
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        // the format used for various cost and fees
        NumberFormat moneyFormatter = new DecimalFormat("$###,##0.00");

        // set up the sheet header first
        Row row = sheet.getRow(1);
        row.getCell(1).setStringValue("Customer");
        row.getCell(2).setStringValue("Billing");
        row.getCell(3).setStringValue("Project");
        row.getCell(4).setStringValue("Contest");
        row.getCell(5).setStringValue("Contest Type");
        row.getCell(6).setStringValue("Status");
        row.getCell(7).setStringValue("Completion Date");
        row.getCell(8).setStringValue("Contest Fee");
        row.getCell(9).setStringValue("Estimated Member Cost");
        row.getCell(10).setStringValue("Actual Member Cost");
        row.getCell(11).setStringValue("Total");

        // insert sheet data from 2nd row
        int rowIndex = 2;

        for (CostDetailsDTO costDetail : getCostDetails()) {
            row = sheet.getRow(rowIndex++);

            // set the customer
            row.getCell(1).setStringValue(costDetail.getClient().getName());

            // set the billing account name
            row.getCell(2).setStringValue(costDetail.getBilling().getName());

            // set the project name
            row.getCell(3).setStringValue(costDetail.getProject().getName());

            // set the contest name
            row.getCell(4).setStringValue(costDetail.getContest().getName());

            // set the status
            row.getCell(5).setStringValue(costDetail.getStatus());

            // set the contest type
            row.getCell(6).setStringValue(costDetail.getContestType().getName());

            // set the completion date
            row.getCell(7).setStringValue(dateFormatter.format(costDetail.getCompletionDate()));

            // set the contest fee
            row.getCell(8).setStringValue(moneyFormatter.format(costDetail.getContestFee()));

            // set the estimated member cost
            row.getCell(9).setStringValue(moneyFormatter.format(costDetail.getEstimatedCost()));


            // set the actual member cost, the 'active' and 'scheduled' contest does not have actual member cost
            if (costDetail.getStatus().trim().toLowerCase().equals("finished")) {
                row.getCell(10).setStringValue(moneyFormatter.format(costDetail.getActualCost()));
            }

            // set the total cost
            row.getCell(11).setStringValue(moneyFormatter.format(costDetail.getTotal()));
        }
    }
}
