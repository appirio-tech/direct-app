/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.cloudvm.service.helper;

import com.topcoder.direct.cloudvm.service.CloudVMServiceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * Helper class for the component.
 *
 * <p>
 * Changes in version 1.1 (TopCoder Direct Contest VM Instances Management) :
 * <ul>
 *     <li>added {@link #checkPositive(String, long)} helper method.</li>
 * </ul>
 * </p>
 *
 * @author kanakarajank, gentva
 * @version 1.1
 *
 */
public class Helper {

	/**
	 * Represents the constant storing notus provider
	 */
	public static final int NOTUS_PROVIDER = 1;
	
	/**
	 * Represents the constant storing amazon provider
	 */
	public static final int AMAZON_PROVIDER = 2;
    
	/**
     * Checks method parameter for null value.
     *
     * @param name parameter name
     * @param data parameter value
     * @throws IllegalArgumentException if data is null
     */
	public static void checkNull(String name, Object data) {
        if (data == null) {
            throw new IllegalArgumentException(name + " is not supposed to be null");
        }
    }

    /**
     * Checks method parameter for positive number.
     *
     * @param name parameter name
     * @param value parameter value
     * @throws IllegalArgumentException if data is negative or zero.
     * @since 1.1
     */
    public static void checkPositive(String name, long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " is not supposed to be negative or zero.");
        }
    }

    /**
     * Logs method entry.
     *
     * @param methodName method name
     */
    public static void logEnter(Log logger,String methodName) {
        logger.log(Level.DEBUG, "Entering method " + methodName);
    }

    /**
     * Logs method exit.
     *
     * @param methodName method name
     */
    public static void logExit(Log logger,String methodName) {
        logger.log(Level.DEBUG, "Leaving method " + methodName);
    }

    /**
     * Logs error.
     *
     * @param error exception instance
     * @return same as error parameter (for rethrowing)
     */
    public static CloudVMServiceException logError(Log logger,CloudVMServiceException error) {
        logger.log(Level.ERROR, error, "service reports error");
        return error;
    }

}
