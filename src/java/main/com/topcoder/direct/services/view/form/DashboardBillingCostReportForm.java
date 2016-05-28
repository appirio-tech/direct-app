/*
 * Copyright (C) 2011 -2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

/**
 * <p>A form bean providing the form data submitted by user for getting the billing cost report.</p>
 *
 * <p>
 *  Version 1.1 (Release Assembly - TC Direct Cockpit Release Six) changes:
 *  <ol>
 *      <li>Add property {@link #invoiceNumberSelection} and its getter and setter.</li>
 *  </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Module Assembly - TC Cockpit Invoice History Page Update) changes:
 * <ol>
 *   <li>Add property {@link #selectInvoiced} and {@link #selectNotInvoiced} and their getters and setters.</li>
 * </ol>
 * </p>
 * @author Blues, GreatKevin, notpad
 * @version 1.2
 */
public class DashboardBillingCostReportForm extends DashboardCostReportForm {
    /**
     * Represents the filtered payment type ids submitted by billing cost report form.
     */
    private long[] paymentTypeIds;

    /**
     * <p>Represents to select the invoiced records.</p>
     * 
     * @since 1.2
     */
    private Boolean selectInvoiced;
    
    /**
     * <p>Represents to select the not invoiced records.</p>
     * @since 1.2
     */
    private Boolean selectNotInvoiced;
    
    /**
     * The invoice number selected in the dropdown.
     *
     * @since 1.1
     */
    private String invoiceNumberSelection;

    /**
     * Represents the invoice number submitted by billing cost report form.
     */
    private String invoiceNumber;

    /**
     * <p>Represents to use redshift instead of informix.</p>
     * 
     * @since 1.2
     */
    private Boolean useRedshift;
    
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
     * Gets whether to select the invoiced records.
     *
     * @return whether to select the invoiced records.
     * @since 1.2
     */
    public Boolean getSelectInvoiced() {
        return selectInvoiced;
    }
    
    /**
     * Sets whether to select the invoiced records.
     *
     * @param selectInvoiced whether to select the invoiced records
     * @since 1.2
     */
    public void setSelectInvoiced(Boolean selectInvoiced) {
        this.selectInvoiced = selectInvoiced;
    }

    /**
     * Gets whether to use redshift or not.
     *
     * @return whether to use redshift.
     * @since 1.2
     */
    public Boolean getUseRedshift() {
        return useRedshift;
    }
    
    /**
     * Sets whether to use redshift or not.
     *
     * @param useRedshift whether to use redshift or not
     * @since 1.2
     */
    public void setUseRedshift(Boolean useRedshift) {
        this.useRedshift = useRedshift;
    }
    
    /**
     * Sets whether to select the not invoiced records.
     *
     * @param selectNotInvoiced whether to select the not invoiced records
     * @since 1.2
     */
    public void setSelectNotInvoiced(Boolean selectNotInvoiced) {
        this.selectNotInvoiced = selectNotInvoiced;
    }
    
    /**
     * Gets whether to select the not invoiced records.
     *
     * @return whether to select the not invoiced records.
     * @since 1.2
     */
    public Boolean getSelectNotInvoiced() {
        return selectNotInvoiced;
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

    /**
     * Gets the invoice number selected.
     *
     * @return the invoice number selected.
     * @since 1.1
     */
    public String getInvoiceNumberSelection() {
        return invoiceNumberSelection;
    }

    /**
     * Sets the invoice number selected.
     *
     * @param invoiceNumberSelection the invoice number selected.
     * @since 1.1
     */
    public void setInvoiceNumberSelection(String invoiceNumberSelection) {
        this.invoiceNumberSelection = invoiceNumberSelection;
    }
}
