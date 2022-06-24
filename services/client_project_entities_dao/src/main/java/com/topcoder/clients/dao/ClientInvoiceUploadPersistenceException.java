/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

/**
 * The ClientInvoiceUploadPersistenceException will be thrown is any persistence related error occurs.
 * <p/>
 * Thread safety: The class is mutable and not thread safe.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit - Billing Management)
 */
public class ClientInvoiceUploadPersistenceException extends ClientInvoiceUploadServiceException {

    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message - the detailed error message of this exception
     */
    public ClientInvoiceUploadPersistenceException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message - the detailed error message of this exception
     * @param cause   - the inner cause of this exception
     */
    public ClientInvoiceUploadPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
