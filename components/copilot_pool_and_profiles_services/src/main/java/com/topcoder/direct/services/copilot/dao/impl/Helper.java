/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOInitializationException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>This helper class used by this component.</p>
 *
 * <p><strong>Thread safety:</strong> This class is immutable and thread safe.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
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
     * <p>Represents pattern used for logging method entrance.</p>
     */
    private static final String METHOD_ENTERED_PATTERN_WITH_PARAMS = "Method: {0}.{1} entered with parameters: {2}.";

    /**
     * <p>Represents pattern used for logging method exit.</p>
     */
    private static final String METHOD_EXITED_PATTERN = "Method {0}.{1} exited. Execution time took {2} ms.";

    /**
     * <p>Represents pattern used for logging method exit.</p>
     */
    private static final String METHOD_EXITED_PATTERN_RESULT =
            "Method {0}.{1} exited with return value: {3}. Execution time took {2} ms.";

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
     * <p>Checks if passed property is not <code>null</code>. If property is <code>null</code>, then
     * CopilotDAOInitializationException is thrown.</p>
     *
     * @param property     property to check
     * @param propertyName property name used in exception error message
     *
     * @throws CopilotDAOInitializationException
     *          if property is null
     */
    static void checkPropertyNotNull(Object property, String propertyName) {

        if (property == null) {
            throw new CopilotDAOInitializationException(MessageFormat.format("Property {0} must be set.",
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
     * <p>Logs entrance of passed method name with given parameters.</p>
     *
     * @param log        log to use
     * @param className  class name
     * @param methodName name of method
     * @param paramNames method parameter names
     * @param params     method parameters
     */
    static void logMethodEntered(Log log, String className, String methodName, String[] paramNames,
                                        Object[] params) {

        if (log != null && log.isEnabled(Level.DEBUG)) {
            log.log(Level.DEBUG, METHOD_ENTERED_PATTERN_WITH_PARAMS, new Object[]{className, methodName,
                    parametersToString(paramNames, params)});
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
     * <p>Logs exit of passed method name.</p>
     *
     * @param log                log to use
     * @param className          class name
     * @param methodName         name of method
     * @param methodResult       method result
     * @param executionTimeStart method execution time start
     */
    static void logMethodExited(Log log, String className, String methodName, long executionTimeStart,
                                       Object methodResult) {

        if (log != null && log.isEnabled(Level.DEBUG)) {
            log.log(Level.DEBUG, METHOD_EXITED_PATTERN_RESULT, new Object[]{className, methodName,
                    System.currentTimeMillis() - executionTimeStart, methodResultToString(methodResult)});
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

    /**
     * <p>Converts method parameters into human readable string representation.</p>
     *
     * @param paramNames names of parameters
     * @param params     parameters values
     *
     * @return formatted string with all parameters
     */
    private static Object parametersToString(String[] paramNames, Object[] params) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");
        for (int i = 0; i < paramNames.length && i < params.length; i++) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(paramNames[i]);
            stringBuilder.append(": ");
            objectToString(stringBuilder, params[i]);
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    /**
     * <p>Converts method result into human readable string representation.</p>
     *
     * @param methodResult method result
     *
     * @return formatted string
     */
    private static String methodResultToString(Object methodResult) {

        StringBuilder stringBuilder = new StringBuilder();

        objectToString(stringBuilder, methodResult);

        return stringBuilder.toString();
    }

    /**
     * <p>Retrieves all class fields including inherited fields.</p>
     *
     * @param clazz object class
     *
     * @return list of all object fields
     */
    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * <p>Converts object to string.</p>
     *
     * @param sb    string buffer
     * @param param object to convert
     */
    private static void objectToString(StringBuilder sb, Object param) {

        if (param == null || isPrimitiveType(param)) {
            sb.append(String.valueOf(param));
        } else if (param.getClass().isArray()) {

            sb.append("[");

            for (int i = 0; i < Array.getLength(param); i++) {
                if (i > 0) {
                    sb.append(", ");
                }

                objectToString(sb, Array.get(param, i));
            }

            sb.append("]");
        } else if (param instanceof Collection) {

            Collection collection = (Collection) param;
            sb.append("[");

            int i = 0;
            for (Object elem : collection) {
                if (i > 0) {
                    sb.append(", ");
                }

                objectToString(sb, elem);
                i++;
            }

            sb.append("]");

        } else {
            complexObjectToString(sb, param);
        }
    }

    /**
     * <p>Converts object to string.</p>
     *
     * @param sb    string buffer to use
     * @param param object to convert
     */
    private static void complexObjectToString(StringBuilder sb, Object param) {
        sb.append("[");
        sb.append(param.getClass().getSimpleName());
        sb.append(": ");
        List<Field> fields = getAllFields(param.getClass());
        int i = 0;
        for (Field field : fields) {
            try {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(field.getName());
                sb.append(": ");

                field.setAccessible(true);
                objectToString(sb, field.get(param));
            } catch (IllegalAccessException e) {
                // ignores exception
            }

            i++;
        }

        sb.append("]");
    }

    /**
     * <p>Returns true if passed object is primitive type or one primitive wrapper objects, false otherwise.</p>
     *
     * @param param object to check
     *
     * @return true if object is primitive type or one one the primitive wrapper objects (like for example Integer,
     *         Double)
     */
    private static boolean isPrimitiveType(Object param) {
        return param.getClass().isPrimitive()
                || param instanceof Integer
                || param instanceof Long
                || param instanceof Character
                || param instanceof Float
                || param instanceof Double
                || param instanceof Boolean
                || param instanceof String
                || param instanceof Date
                || param instanceof Enum
                || param instanceof BigDecimal;
    }
}
