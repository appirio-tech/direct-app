/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

/**
 * <p>
 * <code>ConfigurationException</code> indicates that there're errors with the component configuration, for
 * example, the expected namespace is not loaded, the required configuration property is missing, etc.
 * </p>
 * <p>
 * It may be thrown from all classes which need outside configurations.
 * </p>
 *
 * @author albertwang, TCSDEVELOPER
 * @author flying2hk, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class ConfigurationException extends PhaseTemplateException {

    /**
     * serialVersionUID instance.
     */
    private static final long serialVersionUID = -7685977596935069209L;

    /**
     * <p>
     * Create <code>ConfigurationException</code> instance with the error message.
     * </p>
     * @param message the error message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create <code>ConfigurationException</code> instance with error message and the cause
     * exception.
     * </p>
     * @param message the error message
     * @param cause the cause
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
