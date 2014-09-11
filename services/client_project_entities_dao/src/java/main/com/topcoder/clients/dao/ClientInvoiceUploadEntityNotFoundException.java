/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

/**
 * The ClientInvoiceUploadEntityNotFoundException will be thrown from the update and delete methods if the entity is
 * not found in the persistence.
 * <p/>
 * <p/>
 * Thread safety: The class is mutable and not thread safe.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit - Billing Management)
 */
public class ClientInvoiceUploadEntityNotFoundException extends ClientInvoiceUploadServiceException {

    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message - the detailed error message of this exception
     */
    public ClientInvoiceUploadEntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message - the detailed error message of this exception
     * @param cause   - the inner cause of this exception
     */
    public ClientInvoiceUploadEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
