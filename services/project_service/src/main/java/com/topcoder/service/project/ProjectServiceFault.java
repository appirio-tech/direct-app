/*
 * Copyright (C) 2008-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

/**
 * <p>
 * This exception is thrown to indicate a fault during a service method. It acts as the base for other faults of this
 * component as well as a convenience for service clients. Please note that we extend from Exception since this is a
 * WSDL faults - not a run of the mill exception. Faults will be serialized and as per the WSDL contract, only the
 * message member will be serialized. Extending from
 * <code>{@link com.topcoder.util.errorhandling.BaseNonCriticalException}</code> adds unnecessary overhead of
 * exception data getters which would mislead the user.
 * </p>
 * <p>
 * It extends <code>{@link Exception}</code> to get the functionality of error messages. Note that no cause is stored
 * since the cause will not be serialized/deserialized as part of the WSDL fault contract. Hence all sub-classes should
 * be of low enough granularity to indicate the cause of the error.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is thread safe as it has no state and the super class is thread safe.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #ProjectServiceFault(String, Throwable)} constructor.</li>
 *   </ol>
 * </p>
 *
 * @author humblefool, FireIce, isv
 * @version 1.1
 */
public class ProjectServiceFault extends Exception {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -748752989298672778L;

    /**
     * <p>
     * Constructs this fault with an error message.
     * </p>
     *
     * @param message
     *            The error message describing this fault. Possibly null/empty.
     */
    public ProjectServiceFault(String message) {
        super(message);
    }

    /**
     * <p>Constructs this fault with an error message.</p>
     *
     * @param message The error message describing this fault. Possibly null/empty.
     * @param cause an original cause of error.
     * @since 1.1
     */
    public ProjectServiceFault(String message, Throwable cause) {
        super(message, cause);
    }
}
