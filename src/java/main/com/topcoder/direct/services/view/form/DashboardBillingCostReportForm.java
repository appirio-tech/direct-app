/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

/**
 * <p>A form bean providing the form data submitted by user for getting the billing cost report.</p>
 *
 * @author Blues
 * @version 1.0 (TC Cockpit Billing Cost Report Assembly)
 */
public class DashboardBillingCostReportForm extends DashboardCostReportForm {
    /**
     * Represents the filtered payment type ids submitted by billing cost report form.
     */
    private long[] paymentTypeIds;

    /**
     * Represents the invoice number submitted by billing cost report form.
     */
    private String invoiceNumber;
    
    /**
     * Gets the payment type ids used for filtering billing cost report.
     *
     * @return the payment type ids
     */
    public long[] getPaymentTypeIds() {
        return paymentTypeIds;
    }

    /**
     * Sets the payment type ids for filtering billing cost report.
     *
     * @param paymentTypeIds the payment type ids to set.
     */
    public void setPaymentTypeIds(long[] paymentTypeIds) {
        this.paymentTypeIds = paymentTypeIds;
    }

    /**
     * Gets the invoice number for filtering billing cost report.
     * 
     * @return the invoiceNumber the invoice number.
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the invoice number for filtering billing cost report.
     * 
     * @param invoiceNumber the invoice number to set.
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
