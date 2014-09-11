/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception is thrown if anything goes wrong in the process of loading the configuration file. So this exception
 * is thrown::
 * </p>
 *
 * <p>
 * -if the file doesn't exist;
 * </p>
 *
 * <p>
 * -if the file can't be read : SecurityManager.checkRead(java.lang.String);
 * </p>
 *
 * <p>
 * -if the configuration file is not well formed: the configuration manager throws an exception, or any other abnormal
 * situation occurs;
 * </p>
 *
 * <p>
 * -if the values in the configuration file are not in the required format (dates, hours, minutes)
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationFileException extends BaseException {
    /**
     * <p>
     * Constructor of a ConfigurationFileException.
     * </p>
     *
     * <p>
     * The ConfigurationFileException constructor is passed&nbsp; the exception that caused it and a short descriptive
     * message.
     * </p>
     *
     * @param message a short descriptive message
     * @param cause the exception that caused this exception
     */
    public ConfigurationFileException(String message, Exception cause) {
        super(message, cause);
    }
}
