/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * This exception is thrown if some configuration specific error occurs.
 * <p/>
 * Thread safety: The class is mutable and not thread safe.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit - Billing Management)
 */
public class ClientInvoiceUploadConfigurationException extends BaseRuntimeException {

    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message - the detailed error message of this exception
     */
    public ClientInvoiceUploadConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message - the detailed error message of this exception
     * @param cause   - the inner cause of this exception
     */
    public ClientInvoiceUploadConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
