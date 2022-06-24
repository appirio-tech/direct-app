/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.HibernateException;

import com.topcoder.clients.invoices.dao.InvoiceDAO;
import com.topcoder.clients.invoices.dao.InvoiceDAOException;
import com.topcoder.clients.invoices.model.Invoice;

/**
 * <p>This class is an implementation of InvoiceDAO that uses Hibernate session to access entities in
 * persistence. It extends GenericDAOImpl&lt;Invoice&gt;. This class
 * uses Logging Wrapper logger to log errors and debug information.</p>
 *
 * <p><strong>Configuration: </strong>This class will be used with Spring IoC and will be configured using Spring xml
 * file.</p>
 * 
 * <p><strong>Thread safety:</strong> This class has mutable attributes, thus it's not thread safe. But it's assumed
 * that it will be initialized via Spring IoC before calling any business method, this way it's always used in thread
 * safe manner. It uses thread safe SessionFactory, Session and Log instances.</p>
 * 
 * @author TCSASSEMBER
 * @version 1.0
 */
public class InvoiceDAOImpl extends GenericDAOImpl<Invoice> implements InvoiceDAO {
    /**
     * <p>Represents class name.</p>
     */
    private static final String CLASS_NAME = "InvoiceDAOImpl";
    
    /**
     * <p>Represents a HQL query used for retrieving {@link Invoice} by invoice number.</p>
     */
    private static final String QUERY_BY_INVOICE_NUMBER = "From Invoice where invoiceNumber=:invoiceNumber";
    
    /**
     * <p>Creates new instance of <code>{@link InvoiceDAOImpl}</code> class.</p>
     */
    public InvoiceDAOImpl() {
        // empty constructor
    }
    
    /**
     * <p>Gets the invoice by the invoice number.</p>
     * 
     * @param invoiceNumber the invoice number.
     * @return the invoice whose invoice number is the specified invoice number, null if not found.
     * @throws IllegalArgumentException if invoiceNumber is null or empty.
     * @throws InvoiceDAOException if some other error occurred.
     */
    @SuppressWarnings("unchecked")
    public Invoice getByInvoiceNumber(String invoiceNumber) throws InvoiceDAOException {
        final String methodName = "getByInvoiceNumber";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);
        
        Helper.checkString(getLog(), invoiceNumber, "invoiceNumber", CLASS_NAME, methodName);
        
        try {
            List<Invoice> list = getSession().createQuery(QUERY_BY_INVOICE_NUMBER)
                .setParameter("invoiceNumber", invoiceNumber).list();
            if (list.size() == 0) {
                // not found
                return null;
            }
            
            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart);
            return list.get(0);
        } catch (HibernateException e) {
            InvoiceDAOException ex = new InvoiceDAOException(MessageFormat.format("Error occurred when retrieving entities of {0}",
                    "Invoice"), e);
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), ex);
            throw ex;
        }
    }
}
