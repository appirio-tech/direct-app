/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.model;

import java.util.Date;

/**
 * <p>This class is a container for information about a single invoice. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 * 
 * @author TCSASSEMBER
 * @version 1.0
 */
public class Invoice extends IdentifiableEntity {
    /**
     * <p>The invoice date.</p>
     */
    private Date invoiceDate;
    
    /**
     * <p>The invoice number.</p>
     */
    private String invoiceNumber;
    
    /**
     * <p>Creates new instance of <code>{@link Invoice}</code> class.</p>
     */
    public Invoice() {
        // empty constructor
    }

    /**
     * <p>Gets the invoice date.</p>
     * 
     * @return the invoice date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * <p>Sets the invoice date.<p>
     * 
     * @param invoiceDate the invoice date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * <p>Gets the invoice number.</p>
     * 
     * @return the invoice number
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * <p>Sets the invoice number.</p>
     * 
     * @param invoiceNumber the invoice number
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
