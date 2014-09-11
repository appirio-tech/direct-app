/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.dao;

import java.util.List;

import com.topcoder.clients.invoices.model.InvoiceType;

/**
 * <p>This interface represents a lookup DAO. It provides method for retrieving all
 * invoice types from persistence.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be thread safe.</p>
 *
 * @author flexme
 * @version 1.0
 */
public interface LookupDAO {
    
    /**
     * <p>Retrieves all invoice types from persistence. Returns an empty list if none are found.</p>
     *
     * @return the retrieved invoices types (not null, doesn't contain null)
     *
     * @throws InvoiceDAOException if any error occurred
     */
    List<InvoiceType> getAllInvoiceTypes() throws InvoiceDAOException;
}
