/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.billingcostreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.dto.ReportBaseDTO;
import com.topcoder.direct.services.view.dto.ReportType;
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
 * The DTO to store the billing cost report data.
 * It also contains the input stream to serialize billing cost report data.
 * </p>
 * 
 * <p>
 * Version 1.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>This class has been refactoring. It's extended from <code>ReportBaseDTO</code>.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 (TC Accounting Tracking Invoiced Payments) change notes:
 * <ol>
 *   <li>Added {@link #canProcessInvoices} field.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TC Direct Cockpit Release Six)
 * <ol>
 *   <li>
 *       Add properties {@link #invoiceNumbers}, {@link #totalContestsNumber} and {@link #totalBugRacesNumber} and their
 *       getters and setters.
 *   </li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version  1.4 - Topcoder - Remove JIRA Issues Related Functionality In Direct App v1.0
 * - remove JIRA related functionality
 * </p>
 * 
 * @author Blues, GreatKevin, TCCoder 
 * @version  1.4 
 */
public class BillingCostReportDTO extends ReportBaseDTO {

    /**
     * <p>The list stores the billing cost report entries</p>
     */
    private List<BillingCostReportEntryDTO> entries;

    /**
     * <p>A <code>Map</code> providing the mapping from payment type IDs to names.</p>
     */
    private Map<Long, String> paymentTypes;

    /**
     * The invoice numbers.
     *
     * @since 1.3
     */
    private List<String> invoiceNumbers;

    /**
     * The total contests number.
     *
     * @since 1.3
     */
    private int totalContestsNumber;

    /**
     * The total bug races number.
     *
     * @since 1.3
     */
    private int totalBugRacesNumber;


    /**
     *
     * <p>A flag indicates whether the user can perform operators on invoice records.</p>
     * 
     * @since 1.1
     */
    private boolean canProcessInvoices;
    
    /**
     * Gets the total cost amount of this billing cost report.
     *
     * @return the total billing cost report amount.
     */
    public double getTotalAmount() {
        double totalAmount = 0;
        for(BillingCostReportEntryDTO entry: this.entries) {
            totalAmount += entry.getPaymentAmount();
        }
        return totalAmount;
    }

    /**
     * Gets the payment types map.
     *
     * @return the payment types map.
     */
    public Map<Long, String> getPaymentTypes() {
        return paymentTypes;
    }

    /**
     * Sets the payment types map.
     *
     * @param paymentTypes the payment types map to set.
     */
    public void setPaymentTypes(Map<Long, String> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    /**
     * Gets the billing cost report entries.
     *
     * @return the billing cost report entries.
     */
    public List<BillingCostReportEntryDTO> getEntries() {
        return entries;
    }

    /**
     * Sets the billing cost report entries.
     *
     * @param entries the billing cost entries to  set.
     */
    public void setEntries(List<BillingCostReportEntryDTO> entries) {
        this.entries = entries;
    }

    /**
     * Gets the report type of this report DTO. It simply returns ReportType.BILLING_COST.
     *
     * @return the the billing cost report type.
     */
    public ReportType getReportType() {
        return ReportType.BILLING_COST;
    }

    /**
     * Gets the flag indicates whether the user can perform operators on invoice records.
     * 
     * @return the flag indicates whether the user can perform operators on invoice records.
     * @since 1.1
     */
    public boolean isCanProcessInvoices() {
        return canProcessInvoices;
    }

    /**
     * Sets the flag indicates whether the user can perform operators on invoice records.
     * 
     * @param canProcessInvoices the flag indicates whether the user can perform operators on invoice records.
     * @since 1.1
     */
    public void setCanProcessInvoices(boolean canProcessInvoices) {
        this.canProcessInvoices = canProcessInvoices;
    }

    /**
     * Gets the invoice numbers.
     *
     * @return the invoice numbers.
     * @since 1.3
     */
    public List<String> getInvoiceNumbers() {
        return invoiceNumbers;
    }

    /**
     * Sets the invoice numbers.
     *
     * @param invoiceNumbers the invoice numbers.
     *
     * @since 1.3
     */
    public void setInvoiceNumbers(List<String> invoiceNumbers) {
        this.invoiceNumbers = invoiceNumbers;
    }

    /**
     * Gets the total contests number.
     *
     * @return the total contests number.
     * @since 1.3
     */
    public int getTotalContestsNumber() {
        return totalContestsNumber;
    }

    /**
     * Sets the total contests number.
     *
     * @param totalContestsNumber the total contests number.
     * @since 1.3
     */
    public void setTotalContestsNumber(int totalContestsNumber) {
        this.totalContestsNumber = totalContestsNumber;
    }

    /**
     * Sets the total bug races number.
     *
     * @return the total bug races number.
     * @since 1.3
     */
    public int getTotalBugRacesNumber() {
        return totalBugRacesNumber;
    }

    /**
     * Sets the total bug races number.
     *
     * @param totalBugRacesNumber the total bug races number.
     * @since 1.3
     */
    public void setTotalBugRacesNumber(int totalBugRacesNumber) {
        this.totalBugRacesNumber = totalBugRacesNumber;
    }

    /**
     * <p>Gets the excel file download stream for billing cost report.</p>
     *
     * @return the download stream.
     * @throws WorkbookSavingException if any error occurs when generating cost report excel file.
     * @throws IOException if an I/O error occurs.
     */
    public InputStream getInputStream() throws WorkbookSavingException, IOException {
        try {

            Workbook workbook = new ExcelWorkbook();
            Sheet sheet = new ExcelSheet("Billing Cost Details", (ExcelWorkbook) workbook);
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
        NumberFormat moneyFormatter = new DecimalFormat("###,##0.00");

        // set up the sheet header first
        Row row = sheet.getRow(1);
        row.getCell(1).setStringValue("Payment Date");
        row.getCell(2).setStringValue("Customer");
        row.getCell(3).setStringValue("Billing");
        row.getCell(4).setStringValue("Project");
        row.getCell(5).setStringValue("Filter Value");
        row.getCell(6).setStringValue("Contest");
        row.getCell(7).setStringValue("Contest ID");
        row.getCell(8).setStringValue("Reference ID");
        row.getCell(9).setStringValue("Category");
        row.getCell(10).setStringValue("Status");
        row.getCell(11).setStringValue("Launch Date");
        row.getCell(12).setStringValue("Completion Date");
        row.getCell(13).setStringValue("Actual Total Member Cost");
        row.getCell(14).setStringValue("Payment Type");
        row.getCell(15).setStringValue("Amount");
		row.getCell(16).setStringValue("Invoice Number");
		row.getCell(17).setStringValue("Invoice Date");
		row.getCell(18).setStringValue("Invoice Amount");
		if (isCanProcessInvoices())
		{
			row.getCell(19).setStringValue("Payment ID");
		}

        // insert sheet data from 2nd row
        int rowIndex = 2;

        for (BillingCostReportEntryDTO costDetail : getEntries()) {
            row = sheet.getRow(rowIndex++);

            // set payment date
            row.getCell(1).setStringValue(dateFormatter.format(costDetail.getPaymentDate()));

            // set the customer
            row.getCell(2).setStringValue(costDetail.getClient().getName());

            // set the billing account name
			if (costDetail.getBilling() == null || costDetail.getBilling().getName() == null ||
					costDetail.getBilling().getName().equals(""))
			{
				row.getCell(3).setStringValue("None");
			}
			else 
			{
				row.getCell(3).setStringValue(costDetail.getBilling().getName());
			}

            // set the project name
			if (costDetail.getProject() == null || costDetail.getProject().getName() == null
			     || costDetail.getProject().getName().equals(""))
			{
				row.getCell(4).setStringValue("None");
			}
			else
			{
				row.getCell(4).setStringValue(costDetail.getProject().getName());
			}
            
            row.getCell(5).setStringValue(costDetail.getProjectFilterValue() == null ? "None" : costDetail.getProjectFilterValue());

            // set the contest name
			if (costDetail.getContest() == null || costDetail.getContest().getName() == null
			     || costDetail.getContest().getName().equals(""))
			{
				row.getCell(6).setStringValue("None");
			}
			else
			{
				row.getCell(6).setStringValue(costDetail.getContest().getName());
			}

            // set the contest id
            row.getCell(7).setNumberValue(costDetail.getContest().getId());

            // set the reference id
            if (costDetail.getReferenceId() == null || costDetail.getReferenceId().equals(""))
            {
                row.getCell(8).setStringValue("None");
            }
            else
            {
                row.getCell(8).setStringValue(String.valueOf(costDetail.getPaymentDesc()));
            }

            // set the contest type
			if (costDetail.getContestType() == null || costDetail.getContestType().getName() == null
			     || costDetail.getContestType().getName().equals(""))
			{
				row.getCell(9).setStringValue("None");
			}
			else
			{
				row.getCell(9).setStringValue(costDetail.getContestType().getName());
			}

            // set the status
			if (costDetail.getStatus() == null || costDetail.getStatus().equals(""))
			{
				row.getCell(10).setStringValue("None");
			}
			else
			{
				row.getCell(10).setStringValue(costDetail.getStatus());
			}	

            // set the Launch date
			if (costDetail.getLaunchDate() == null)
			{
				row.getCell(11).setStringValue("None");
			}
			else
			{
				row.getCell(11).setStringValue(dateFormatter.format(costDetail.getLaunchDate()));
			}

            // set the completion date
			if (costDetail.getCompletionDate() == null)
			{
				row.getCell(12).setStringValue("None");
			}
			else
			{
				row.getCell(12).setStringValue(dateFormatter.format(costDetail.getCompletionDate()));
			}

            // set the actual total member cost,
            row.getCell(13).setNumberValue((costDetail.getActualTotalMemberCost()));  

            // set the payment type
            row.getCell(14).setStringValue(costDetail.getPaymentType());

            // set the amount
            row.getCell(15).setNumberValue((costDetail.getPaymentAmount()));
			
			if (costDetail.getInvoiceNumber() == null || costDetail.getInvoiceNumber().equals(""))
			{
				row.getCell(16).setStringValue("-");
			}
			else 
			{
				row.getCell(16).setStringValue((costDetail.getInvoiceNumber()));
			}
			
			if (costDetail.getInvoiceDate() == null || costDetail.getInvoiceDate().equals(""))
			{
				row.getCell(17).setStringValue("-");
			}
			else 
			{
				row.getCell(17).setStringValue(dateFormatter.format(costDetail.getInvoiceDate()));
			}
			
			
			if (row.getCell(18) != null)
			{
				row.getCell(18).setNumberValue((costDetail.getInvoiceAmount()));
			}
			
			
			if (isCanProcessInvoices() && row.getCell(19) != null)
			{
				row.getCell(19).setNumberValue((costDetail.getPaymentId()));
			}
			
			
        }
    }
}
