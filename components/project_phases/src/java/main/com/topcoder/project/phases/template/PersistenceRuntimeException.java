/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * <code>PersistenceRuntimeException</code> is the runtime version of exception for the
 * PhaseTemplatePersistence interface. This exception is used for DBPhaseTemplatePersistence's implementation
 * on the methods defined in version 1.0. These methods in version 1.0 defined in PhaseTemplatePersistence
 * interface would not allow to throw non-runtime exception.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class PersistenceRuntimeException extends BaseRuntimeException {

    /**
     * serialVersionUID instance.
     */
    private static final long serialVersionUID = -315653900240270620L;

    /**
     * <p>
     * Create <code>PersistenceRuntimeException</code> instance with the error message.
     * </p>
     *
     * @param message
     *            the message
     */
    public PersistenceRuntimeException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create <code>PersistenceRuntimeException</code> instance with error message and the cause exception.
     * </p>
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public PersistenceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
