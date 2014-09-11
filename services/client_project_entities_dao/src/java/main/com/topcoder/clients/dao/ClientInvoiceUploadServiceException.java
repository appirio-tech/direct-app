/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import com.topcoder.util.errorhandling.BaseCriticalException;

/**
 * This exception is a base class for all other custom checked exceptions related to ClientInvoiceUpload.
 * It is never thrown directly, subclasses are used instead.
 * <p/>
 * Thread safety: The class is mutable and not thread safe.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit - Billing Management)
 */
public class ClientInvoiceUploadServiceException extends BaseCriticalException {

    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message - the detailed error message of this exception
     */
    public ClientInvoiceUploadServiceException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message - the detailed error message of this exception
     * @param cause   - the inner cause of this exception
     */
    public ClientInvoiceUploadServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
