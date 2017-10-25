/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade;

import com.topcoder.configuration.ConfigurationException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class provides static utility methods which are used to facilitate the coding or reduce the redundancies.
 * </p>
 * 
 * <p>
 * Thread Safety: This class is thread safe since it is immutable.
 * </p>
 * 
 * @author snow01
 * 
 * @since Jira & Confluence User Sync Service
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * Private constructor to prevent instantiation of this class.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks whether the param is null.
     * </p>
     * 
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the param is null
     */
    public static void checkNull(final Object param, final String paramName) {
        if (param == null) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the param is empty string.
     * </p>
     * 
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the param is empty string.
     */
    public static void checkEmpty(final String param, final String paramName) {
        if ((param != null) && (param.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be empty.");
        }
    }

    /**
     * <p>
     * Get the string value in <code>ConfigManager</code> with specified namespace and name of property.
     * </p>
     * 
     * @param namespace
     *            the namespace of the property.
     * @param name
     *            the name of the property.
     * @param required
     *            whether the property is required.
     * 
     * @return the string value in <code>ConfigManager</code> with specified namespace and name of property.
     * 
     * @throws ConfigurationException
     *             if the namespace doesn't exist, or the property doesn't exist or the property value is an empty
     *             string when it is required.
     */
    public static String getStringPropertyValue(String namespace, String name, boolean required)
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
     * Gets <code>ObjectFactory</code> by the specified namespace, on the assumption that the specNamespace is not null
     * or empty.
     * </p>
     * 
     * @param specNamespace
     *            the specified namespace
     * @return the retrieved ObjectFactory
     * @throws ConfigurationException
     *             if any error occurs
     */
    public static ObjectFactory getObjectFactory(String specNamespace) throws ConfigurationException {
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
     * @throws ConfigurationException
     *             if any error occurs
     */
    public static Object createObject(ObjectFactory objectFactory, String key, Class<?> type)
            throws ConfigurationException {
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
}
