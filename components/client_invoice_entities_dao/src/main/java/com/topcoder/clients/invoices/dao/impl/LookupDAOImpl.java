/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.topcoder.clients.invoices.dao.InvoiceDAOException;
import com.topcoder.clients.invoices.dao.LookupDAO;
import com.topcoder.clients.invoices.model.InvoiceType;

/**
 * <p>This class is an implementation of LookupDAO that uses Hibernate session to access entities in persistence. This
 * class uses Logging Wrapper logger to log errors and debug information.</p>
 * 
 * <p><strong>Configuration: </strong>This class will be used with Spring IoC and will be configured using Spring xml
 * file.</p>
 * 
 * <p><strong>Thread safety:</strong> This class has mutable attributes, thus it's not thread safe. But it's assumed
 * that it will be initialized via Spring IoC before calling any business method, this way it's always used in thread
 * safe manner. It uses thread safe SessionFactory, Session and Log instances.</p>
 *
 * @author flexme
 * @version 1.0
 */
@Transactional
public class LookupDAOImpl extends BaseDAO implements LookupDAO {

    /**
     * <p>Represents class name.</p>
     */
    private static final String CLASS_NAME = "LookupDAOImpl";
    
    /**
     * <p>Creates new instance of <code>{@link LookupDAOImpl}</code> class.</p>
     */
    public LookupDAOImpl() {
        // empty constructor
    }
    
    /**
     * <p>Retrieves all invoice types from persistence. Returns an empty list if none are found.</p>
     *
     * @return the retrieved invoices types (not null, doesn't contain null)
     *
     * @throws InvoiceDAOException if any error occurred
     */
    public List<InvoiceType> getAllInvoiceTypes() throws InvoiceDAOException {
        final String methodName = "getAllInvoiceTypes";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);

        try {
            // retrieves all CopilotProfileStatus stored in persistence - simply delegates to base class method
            List<InvoiceType> result = getAllEntities(InvoiceType.class);

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart);

            // returns the result
            return result;
        } catch (InvoiceDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

            throw e;
        }
    }

}
