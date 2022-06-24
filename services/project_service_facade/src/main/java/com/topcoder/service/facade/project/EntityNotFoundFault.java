/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project;
/**
 * <p>
 * Basically it is an wrapper for the EntityNotFoundException from the client_project_entities_dao component.
 * </p>
 * <b>Thread Safety</b>: This class is thread safe as it has no state and the super class is thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public class EntityNotFoundFault extends DAOFault {
	/**
     * <p>
     * Constructs this fault with an error message.
     * </p>
     *
     * @param message
     *            The error message describing this fault. Possibly null/empty.
     */
    public EntityNotFoundFault(String message) {
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
    public EntityNotFoundFault(String message, Throwable cause) {
        super(message, cause);
    }
}
