/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This is a helper class for the whole component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is immutable and thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public final class Util {

    /**
     * <p>
     * Private constructor preventing this class from being instantiated.
     * </p>
     */
    private Util() {
    }

    /**
     * <p>
     * Checks whether given object is null or not.
     * </p>
     *
     * @param obj
     *            given object to be checked
     * @param name
     *            name of the specified object
     * @param logger
     *            the logger to log message
     * @throws IllegalArgumentException
     *             if specified object is null
     */
    public static void checkObjNotNull(Object obj, String name, Log logger) {
        if (obj == null) {
            if (logger != null) {
                logger.log(Level.ERROR, "IllegalArgumentException occurred.");
            }
            throw new IllegalArgumentException("[" + name + "] should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether given Object array has null elements.
     * </p>
     *
     * @param array
     *            the Object array to be checked
     * @param name
     *            name of the array
     * @throws IllegalArgumentException
     *             if given Object array has null elements
     */
    public static void checkArrrayHasNull(Object[] array, String name) {
        for (int i = 0; i < array.length; i++) {
            checkObjNotNull(array[i], "Elements of " + name, null);
        }
    }

    /**
     * <p>
     * Checks whether given String array has null or empty elements.
     * </p>
     *
     * @param array
     *            the array to be checked
     * @param name
     *            name of the array
     * @throws IllegalArgumentException
     *             if given array has null or empty elements
     */
    public static void checkStringArrayHasNullOrEmptyEle(String[] array, String name) {
        for (int i = 0; i < array.length; i++) {
            checkStrNotNullOrEmpty(array[i], "Elements of " + name);
        }

    }

    /**
     * <p>
     * Checks whether given string is null or empty.
     * </p>
     *
     * @param str
     *            the string to be checked
     * @param name
     *            name of the string
     * @throws IllegalArgumentException
     *             if given string is null or empty
     */
    public static void checkStrNotNullOrEmpty(String str, String name) {
        checkObjNotNull(str, name, null);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("[" + name + "] should not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether given ID is negative or not.
     * </p>
     *
     * @param id
     *            the id to be checked
     * @param name
     *            name of the id
     * @param logger
     *            the logger to log message
     * @throws IllegalArgumentException
     *             if given ID is negative
     */
    public static void checkIDNotNegative(long id, String name, Log logger) {
        if (id < 0) {
            if (logger != null) {
                logger.log(Level.ERROR, "IllegalArgumentException occurred.");
            }
            throw new IllegalArgumentException("The parameter [" + name + "] should not be negative.");
        }
    }

    /**
     * <p>
     * Get the string value in <code>ConfigManager</code> with specified namespace and name of
     * property.
     * </p>
     *
     * @param namespace
     *            the namespace of the property.
     * @param name
     *            the name of the property.
     * @param required
     *            whether the property is required.
     *
     * @return the string value in <code>ConfigManager</code> with specified namespace and name of
     *         property.
     *
     * @throws ConfigurationException
     *             if the namespace doesn't exist, or the property doesn't exist or the property
     *             value is an empty string when it is required.
     * @since 1.1
     */
    static String getStringPropertyValue(String namespace, String name, boolean required)
            throws ConfigurationException {
        try {
            String value = ConfigManager.getInstance().getString(namespace, name);

            // if the value is required, but the value doesn't exist or is empty, throw exception.
            if (required) {
                if (value == null) {
                    throw new ConfigurationException("The required parameter " + name + " in namespace " + namespace
                            + " doesn't exist.");
                } else if (value.trim().length() == 0) {
                    throw new ConfigurationException("The parameter " + name + " in namespace " + namespace
                            + " has empty string value.");
                }
            }

            return (value == null) ? null : value.trim();
        } catch (UnknownNamespaceException une) {
            throw new ConfigurationException("The namespace with the name of " + namespace + " doesn't exist.", une);
        }
    }

    /**
     * <p>
     * Gets <code>ObjectFactory</code> by the specified namespace, on the assumption that the
     * specNamespace is not null or empty.
     * </p>
     *
     * @param specNamespace
     *            the specified namespace
     * @return the retrieved ObjectFactory
     * @throws ConfigurationException
     *             if any error occurs
     * @since 1.1
     */
    static ObjectFactory getObjectFactory(String specNamespace) {
        try {
            // Create the ConfigManagerSpecificationFactory instance using specNamespace
            ConfigManagerSpecificationFactory specificationFactory = new ConfigManagerSpecificationFactory(
                    specNamespace);
            // Create the ObjectFactory using ConfigManagerSpecificationFactory instance
            return new ObjectFactory(specificationFactory);
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("SpecificationConfigurationException occurs"
                    + " while creating ConfigManagerSpecificationFactory instance : " + e.getMessage(), e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("IllegalReferenceException occurs"
                    + " while creating ConfigManagerSpecificationFactory instance : " + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Creates object by ObjectFactory with the value, which get from ConfigurationObject by key.
     * </p>
     *
     * @param objectFactory
     *            create object from
     * @param key
     *            the key to get value
     * @param type
     *            the correct type of the Object
     *
     * @return the created object
     *
     * @throws EnforcerConfigurationException
     *             if any error occurs
     * @since 1.1
     */
    static Object createObject(ObjectFactory objectFactory, String key, Class<?> type) {
        Object object = null;
        try {
            object = objectFactory.createObject(key);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException("InvalidClassSpecificationException occurs while creating object by ["
                    + key + "]: " + e.getMessage(), e);
        }

        // The object must not be null, and must be instance of the type.
        if (!type.isInstance(object)) {
            // the created object's is not the right type.
            throw new ConfigurationException("The created object should be " + type.getName());
        }

        return object;
    }

    /**
     * <p>
     * Logs messages if logging is not null.
     * </p>
     *
     * @param logger
     *            the logger to log
     * @param level
     *            the log level
     * @param msg
     *            the log message
     * @since 1.1
     */
    public static void log(Log logger, Level level, String msg) {
        if (logger != null) {
            logger.log(level, msg);
        }
    }
}
