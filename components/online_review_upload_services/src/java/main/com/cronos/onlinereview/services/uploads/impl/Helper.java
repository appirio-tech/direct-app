/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads.impl;

import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

/**
 * <p>
 * This class provides static utility methods which are used to facilitate the coding or reduce the redundancies.
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
final class Helper {

    /**
     * <p>
     * Private constructor to prevent instantiation of this class.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks the given <code>obj</code> for <code>null</code>.
     * </p>
     *
     * @param obj       the object to check.
     * @param paramName the name of the argument.
     * @param log       the logger
     *
     * @throws IllegalArgumentException if obj is <code>null</code>.
     */
    static void checkNull(Object obj, String paramName, Log log) {
        if (obj == null) {
            logFormat(log, Level.ERROR, "Parameter argument: {0} cannot be null.", new Object[] {paramName});
            throw new IllegalArgumentException("Parameter argument: '" + paramName + "' cannot be null!");
        }
    }

    /**
     * <p>
     * Checks the given <code>String</code> for <code>null</code> and empty.
     * </p>
     * <p>
     * Note that the empty string is checked after trimming.
     * </p>
     *
     * @param str       string to check.
     * @param paramName the name of the argument.
     * @param log       the logger.
     *
     * @throws IllegalArgumentException if str is <code>null</code> or empty string.
     */
    static void checkString(String str, String paramName, Log log) {
        // check null
        checkNull(str, paramName, log);

        // check empty
        if (str.trim().length() == 0) {
            logFormat(log, Level.ERROR, "Parameter argument: {0} cannot be empty string.", new Object[]{paramName});
            throw new IllegalArgumentException("Parameter argument: '" + paramName + "' cannot be empty string!");
        }
    }

    /**
     * <p>
     * Creates the object from the namespace given using <code>ObjectFactory</code>. If the object created is
     * not of type Class
     * </p>
     *
     * @param namespace  the namespace to use
     * @param key        the key to get the property
     * @param defaultKey the default key to use
     * @param log        the logger for logging purpose
     * @param cls        the class type of the object to create
     *
     * @return the created object
     *
     * @throws ConfigurationException if any error occurs while reading the configuration
     */
    static Object createObject(String namespace, String key, String defaultKey, Log log, Class cls)
        throws ConfigurationException {
        try {
            // first check the objectFactoryNamespace is present in the namespace
            String objectFactoryNamespace = readProperty(namespace, "objectFactoryNamespace", null, log, true);
            String identifier = readProperty(namespace, key, defaultKey, log, defaultKey == null);
            ConfigManagerSpecificationFactory configFactory = new ConfigManagerSpecificationFactory(
                    objectFactoryNamespace);
            ObjectFactory factory = new ObjectFactory(configFactory);
            Object object = factory.createObject(identifier);
            if (cls.isInstance(object)) {
                return object;
            }
            logFormat(log, Level.FATAL, "Failed to create object {0}. Wrong class type in configuration.",
                    new Object[] {cls.getName()});
            throw new ConfigurationException("Failed to create " + cls.getName()
                    + ". Wrong class type in configuration.");
        } catch (SpecificationConfigurationException e) {
            logFormat(log, Level.FATAL, e, "Failed to create object {0}.", new Object[] {cls.getName()});
            throw new ConfigurationException("Failed to create " + cls.getName() + ".", e);
        } catch (IllegalReferenceException e) {
            logFormat(log, Level.FATAL, e, "Failed to create object {0}.", new Object[] {cls.getName()});
            throw new ConfigurationException("Failed to create " + cls.getName() + ".", e);
        } catch (InvalidClassSpecificationException e) {
            logFormat(log, Level.FATAL, e, "Failed to create object {0}.", new Object[] {cls.getName()});
            throw new ConfigurationException("Failed to create " + cls.getName() + ".", e);
        }
    }

    /**
     * <p>
     * Creates the object from the given arguments, or returns the defualt object if fails to create the object.
     * </p>
     *
     * @param namespace  the namespace to use
     * @param key        the key to get the property
     * @param defaultKey the default property value if the key is missing
     * @param log        the logger for logging purpose
     * @param cls        the class type of the object to create
     * @param object     the default object
     *
     * @return the created object
     *
     * @throws ConfigurationException if any configuration error occurs
     */
    static Object createObject(String namespace, String key, String defaultKey, Log log, Class cls, Object object)
        throws ConfigurationException {
        String objectFactoryNamespace = readProperty(namespace, "objectFactoryNamespace", null, log, false);
        if (objectFactoryNamespace == null) {
            logFormat(log, Level.INFO, "No 'objectFactoryNamespace' present. Using the default class {0}",
                    new Object[]{cls.getName()});
            return object;
        }
        return createObject(namespace, key, defaultKey, log, cls);
    }

    /**
     * <p>
     * Reads the property from the configuration.
     * </p>
     *
     * @param namespace the namespace to get the property
     * @param key       the key to search
     * @param defValue  the default value to return
     * @param log       the logger
     * @param required  boolean representing the property is required or optional
     *
     * @return the property value
     *
     * @throws ConfigurationException if any error occurs while reading configuration
     */
    static String readProperty(String namespace, String key, String defValue, Log log, boolean required)
        throws ConfigurationException {
        ConfigManager manager = ConfigManager.getInstance();
        try {
            String value = manager.getString(namespace, key);
            if (value == null || value.trim().length() == 0) {
                if (required) {
                    logFormat(log, Level.FATAL, "Missing the property {0} from configuration", new Object[]{key});
                    throw new ConfigurationException("Missing '" + key + "' property.");
                } else {
                    return defValue;
                }
            }
            logFormat(log, Level.INFO, "Read the property {0} from configuration - {1}", new Object[]{key, value});
            return value;
        } catch (UnknownNamespaceException e) {
            logFormat(log, Level.FATAL, e,
                    "Failed to read property {0} from configuaration. Unknown namespace {1}.",
                    new Object[]{key, namespace});
            throw new ConfigurationException("Failed to read '" + key + "' property.", e);
        }
    }

    /**
     * <p>
     * Checks the given <code>id</code> is less than 0.
     * </p>
     *
     * @param id        the id to check.
     * @param paramName the name of the argument.
     * @param log       the logger for logging purpose.
     *
     * @throws IllegalArgumentException if the id is &lt; 0
     */
    static void checkId(long id, String paramName, Log log) {
        if (id < 0) {
            logFormat(log, Level.ERROR, "Parameter argument: {0} cannot be negative.", new Object[] {paramName});
            throw new IllegalArgumentException("Parameter argument: '" + paramName + "' cannot be negative!");
        }
    }

    /**
     * <p>Logs message with specified logging level.</p>
     *
     * @param log logger to use
     * @param level logging level
     * @param message message to log
     */
    public static void logFormat(Log log, Level level, String message) {
    	log.log(level, message);
    }

    /**
     * <p>Formats and logs message with specified logging level.</p>
     *
     * @param log logger to use
     * @param level logging level
     * @param message message pattern
     * @param params message arguments
     */
    public static void logFormat(Log log, Level level, String message, Object... params) {
    	log.log(level, MessageFormat.format(message, params));
    }

    /**
     * <p>Formats and logs message with specified logging level.</p>
     *
     * @param log logger to use
     * @param level logging level
     * @param message message pattern
     * @param params message arguments
     */
    public static void logFormat(Log log, Level level, Throwable error, String message, Object... params) {
        if (error != null) {
            // 'convert' the exception stack trace into string
            // note: closing the stringWriter has no effect
            StringWriter buffer = new StringWriter();
            buffer.append("\n");
            error.printStackTrace(new PrintWriter(buffer));
            log.log(level, MessageFormat.format(message, params) + buffer.toString());
        } else {
            log.log(level, MessageFormat.format(message, params));
        }

    }
}
