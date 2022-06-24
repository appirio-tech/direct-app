/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

/**
 * <p>
 * <code>StartDateGenerationException</code> indicates that there're errors in the start date generation
 * process, e.g. connection errors in database-based start date generation implementations.
 * </p>
 * <p>
 * It may be thrown from <code>{@link StartDateGenerator}</code> implementation. It is not thrown at all in
 * the initial version, it is a placeholder for future extension.
 * </p>
 *
 * @author albertwang, TCSDEVELOPER
 * @author flying2hk, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class StartDateGenerationException extends PhaseTemplateException {

    /**
     * serialVersionUID instance.
     */
    private static final long serialVersionUID = -4124909171428947828L;

    /**
     * <p>
     * Create <code>StartDateGenerationException</code> instance with error message.
     * </p>
     *
     * @param message
     *            the error message
     */
    public StartDateGenerationException(String message) {

        super(message);
    }

    /**
     * <p>
     * Create <code>StartDateGenerationException</code> instance with error message and the cause exception.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause
     */
    public StartDateGenerationException(String message, Throwable cause) {

        super(message, cause);
    }
}
