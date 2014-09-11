/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project;
/**
 * <p>
 * This exception is thrown to indicate a fault during a service method. It acts as the base for other faults of this
 * component as well as a convenience for service clients. Please note that we extend from Exception since this is a
 * WSDL faults - not a run of the mill exception.
 * <p>
 * <p>
 * Basically it is an wrapper for the DAOException from the client_project_entities_dao component.
 * </p>
 * <b>Thread Safety</b>: This class is thread safe as it has no state and the super class is thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public class DAOFault extends Exception {
    /**
     * <p>
     * Constructs this fault with an error message.
     * </p>
     *
     * @param message
     *            The error message describing this fault. Possibly null/empty.
     */
    public DAOFault(String message) {
        super(message);
    }
    /**
     * <p>
     * Constructs this fault with an error message.
     * </p>
     *
     * @param message The error message describing this fault. Possibly null/empty.
     * @param cause the inner cause
     */
    public DAOFault(String message, Throwable cause) {
        super(message, cause);
    }
}
