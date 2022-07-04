/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

/**
 * <p>
 * This exception is thrown to indicate the fault that a required project was not found.
 * </p>
 * <p>
 * It extends <code>{@link ProjectServiceFault}</code> to get the functionality of error messages. Note that no cause
 * is stored since the cause will not be serialized/deserialized as part of the WSDL fault contract.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is thread safe as it has no state and the super class is thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public class ProjectNotFoundFault extends ProjectServiceFault {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -3500012673347797268L;

    /**
     * <p>
     * Constructs this fault with an error message.
     * </p>
     *
     * @param message
     *            The error message describing this fault. Possibly null/empty.
     */
    public ProjectNotFoundFault(String message) {
        super(message);
    }
}
