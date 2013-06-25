/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.longcontest;

/**
 * <p>
 * This exception is design to be thrown when there is a configuration error detected.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe as its parent class is not thread-safe.
 * </p>
 * 
 * @author sampath01, zhu_tao
 * @version 1.0
 * @since 1.0
 */
public class ConfigurationException extends RuntimeException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2083240322329178302L;

    /**
     * Constructor to instantiate an instance with exception message.
     * 
     * @param msg
     *            Exception message.
     */
    public ConfigurationException(String msg) {
        super(msg);
    }

    /**
     * Constructor to instantiate an instance with exception message.
     * 
     * @param msg
     *            Exception message.
     * @param cause
     *            Cause of the exception.
     */
    public ConfigurationException(String msg, Throwable cause) {
        super(msg);
    }

}
