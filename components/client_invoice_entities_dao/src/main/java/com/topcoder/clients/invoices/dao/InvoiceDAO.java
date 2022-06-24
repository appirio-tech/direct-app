/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.dao;

import com.topcoder.clients.invoices.model.Invoice;

/**
 * <p>This interface represents an <code>Invoice</code> DAO. It extends GenericDAO&lt;Invoice&gt; and provides an
 * additional method for retrieving <code>Invoice</code> by invoice number.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be thread safe when entities passed to them
 * are used by the caller in thread safe manner.</p>
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
public interface InvoiceDAO extends GenericDAO<Invoice> {
    /**
     * <p>Gets the invoice by the invoice number.</p>
     * 
     * @param invoiceNumber the invoice number.
     * @return the invoice whose invoice number is the specified invoice number.
     * @throws IllegalArgumentException if invoiceNumber is null or empty.
     * @throws InvoiceDAOException if some other error occurred.
     */
    Invoice getByInvoiceNumber(String invoiceNumber) throws InvoiceDAOException;
}
