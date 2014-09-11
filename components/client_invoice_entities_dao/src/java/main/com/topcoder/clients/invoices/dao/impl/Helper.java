/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.dao.impl;

import java.text.MessageFormat;

import com.topcoder.clients.invoices.dao.InvoiceDAOInitializationException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>This helper class used by this component.</p>
 *
 * <p><strong>Thread safety:</strong> This class is immutable and thread safe.</p>
 *
 * <p>
 * Version 1.1 (TC Accounting Tracking Invoiced Payments Part 2) change log:
 * <ol>
 *   <li>Added {@link #checkString(Log, String, String, String, String)} method to check the string
 *   is not null or empty.</li>
 * </ol>
 * </p>
 *
 * @author flexme
 * @version 1.1
 */
final class Helper {
    /**
     * <p>Represents pattern used for logging exception caught in specified method.</p>
     */
    static final String METHOD_ERROR = "Error occurred in {0}.{1} method.";

    /**
     * <p>Represents pattern used for logging illegal argument in specified method.</p>
     */
    static final String ILLEGAL_ARGUMENT = "Illegal argument in {0}.{1} method.";

    /**
     * <p>Represents pattern used for logging method entrance.</p>
     */
    private static final String METHOD_ENTERED_PATTERN = "Method: {0}.{1} entered.";

    /**
     * <p>Represents pattern used for logging method exit.</p>
     */
    private static final String METHOD_EXITED_PATTERN = "Method {0}.{1} exited. Execution time took {2} ms.";

    /**
     * <p>Creates new instance of <code>{@link Helper}</code> class.</p>
     *
     * <p>Private constructor prevents from instantiation outside this class.</p>
     */
    private Helper() {
        // empty constructor
    }
    
    /**
     * <p>Checks if passed parameter is not <code>null</code>. If param is <code>null</code>, then
     * IllegalArgumentException is thrown.</p>
     *
     * @param param     parameter to check
     * @param paramName parameter name used in exception error message
     *
     * @throws IllegalArgumentException if param is <code>null</code>
     */
    static void checkIsNotNull(Object param, String paramName) {
        if (param == null) {
            throw new IllegalArgumentException(MessageFormat.format("Parameter {0} must be not null.",
                    paramName));
        }
    }
    
    /**
     * <p>Checks if passed parameter is positive. If param is less or equal to 0, then IllegalArgumentException is
     * thrown.</p>
     *
     * @param param     parameter to check
     * @param paramName parameter name used in exception error message
     *
     * @throws IllegalArgumentException if param is not positive
     */
    static void checkIsPositive(long param, String paramName) {
        if (param <= 0) {
            throw new IllegalArgumentException(MessageFormat.format("Parameter {0} must be positive.",
                    paramName));
        }
    }
    
    /**
     * <p>Check is passed entity id is positive if not {@link IllegalArgumentException} is log and throw.</p>
     *
     * @param log   log to use
     * @param param   param to check
     * @param paramName parameter name used in exception error message
     * @param methodName method name
     * @param className class name
     */
    static void checkIsPositive(Log log, long param, String paramName, String className, String methodName) {
        try {
            checkIsPositive(param, paramName);
        } catch (IllegalArgumentException e) {
            Helper.logError(log, MessageFormat.format(ILLEGAL_ARGUMENT, className,
                    methodName), e);
            throw e;
        }
    }
    
   /**
    * <p>Check is passed string is null or empty. If not {@link IllegalArgumentException} is log and throw.</p>
    *
    * @param log   log to use
    * @param param   param to check
    * @param paramName parameter name used in exception error message
    * @param methodName method name
    * @param className class name
    * @throws IllegalArgumentException if the argument is invalid
    * @since 1.1
    */
    static void checkString(Log log, String value, String paramName, String className, String methodName) {
        if (value == null || value.trim().length() == 0) {
            IllegalArgumentException e = new IllegalArgumentException(MessageFormat.format("Parameter {0} cannot be null or empty.",
                    paramName));
            Helper.logError(log, MessageFormat.format(ILLEGAL_ARGUMENT, className,
                    methodName), e);
            throw e;
        }
    }
    
    /**
     * <p>Checks if passed property is not <code>null</code>. If property is <code>null</code>, then
     * InvoiceDAOInitializationException is thrown.</p>
     *
     * @param property     property to check
     * @param propertyName property name used in exception error message
     *
     * @throws InvoiceDAOInitializationException
     *          if property is null
     */
    static void checkPropertyNotNull(Object property, String propertyName) {

        if (property == null) {
            throw new InvoiceDAOInitializationException(MessageFormat.format("Property {0} must be set.",
                    propertyName));
        }
    }
    
    /**
     * <p>Logs entrance of passed method name.</p>
     *
     * @param log        log to use
     * @param className  class name
     * @param methodName name of method
     */
    static void logMethodEntered(Log log, String className, String methodName) {

        if (log != null && log.isEnabled(Level.DEBUG)) {
            log.log(Level.DEBUG, METHOD_ENTERED_PATTERN, new Object[]{className, methodName});
        }
    }

    /**
     * <p>Logs exit of passed method name.</p>
     *
     * @param log                log to use
     * @param className          class name
     * @param methodName         name of method
     * @param executionTimeStart method execution time start
     */
    static void logMethodExited(Log log, String className, String methodName, long executionTimeStart) {

        if (log != null && log.isEnabled(Level.DEBUG)) {
            log.log(Level.DEBUG, METHOD_EXITED_PATTERN, new Object[]{className, methodName,
                    System.currentTimeMillis() - executionTimeStart});
        }
    }

    /**
     * <p>Logs error with given message and exception stack trace.</p>
     *
     * @param log      log to use
     * @param errorMsg error message
     * @param e        exception to use
     */
    static void logError(Log log, String errorMsg, Throwable e) {

        if (log != null) {
            log.log(Level.ERROR, errorMsg, e);
        }
    }
}
