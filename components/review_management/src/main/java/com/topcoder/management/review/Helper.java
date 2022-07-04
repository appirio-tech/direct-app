/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.Property;

import java.lang.reflect.InvocationTargetException;


/**
 * <p>
 * Package private helper class to simplify the argument checking and string parsing.
 * </p>
 *
 * @author icyriver
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
     * Check the given <code>obj</code> for null.
     * </p>
     *
     * @param obj the object to check.
     * @param paramName the paramName of the argument.
     *
     * @throws IllegalArgumentException if obj is null.
     */
    public static void checkNull(Object obj, String paramName) {
        if (obj == null) {
            throw new IllegalArgumentException("Parameter argument: '" + paramName
                + "' can not be null!");
        }
    }

    /**
     * <p>
     * Check the given <code>String</code> for null and empty.
     * </p>
     *
     * @param str string to check.
     * @param paramName paramName of the argument.
     *
     * @throws IllegalArgumentException if str is null or empty string.
     */
    public static void checkString(String str, String paramName) {
        // check null
        checkNull(str, paramName);

        // check empty
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter argument: '" + paramName
                + "' can not be empty string!");
        }
    }

    /**
     * <p>
     * Get the object with the specified class name and argument as the parameter of its
     * constructor.
     * </p>
     *
     * @param className the class name to create.
     * @param typeParam the string value of the argument.
     *
     * @return the object created.
     *
     * @throws ConfigurationException if any error occurs during the creating process.
     */
    public static Object createObject(String className, String typeParam)
        throws ConfigurationException {
        try {
            Class[] argClasses = new Class[0];
            Object[] argValues = new Object[0];

            if (typeParam != null) {
                argClasses = new Class[] {String.class};
                argValues = new Object[] {typeParam};
            }

            // create an object using reflection.
            return Class.forName(className).getConstructor(argClasses).newInstance(argValues);
        } catch (ClassNotFoundException cnfe) {
            throw new ConfigurationException("Error occurs, the class: '" + className
                + "' can't be found.", cnfe);
        } catch (SecurityException se) {
            throw new ConfigurationException("Security error occurs while initializing class: "
                + className, se);
        } catch (NoSuchMethodException nsme) {
            throw new ConfigurationException("Error occurs, the method can't be found for class: "
                + className, nsme);
        } catch (IllegalAccessException iae) {
            throw new ConfigurationException("Error occurs during creating the object for class: "
                + className, iae);
        } catch (IllegalArgumentException ex) {
            throw new ConfigurationException("Error occurs during creating the object for class: "
                + className, ex);
        } catch (InvocationTargetException ite) {
            throw new ConfigurationException("Error occurs during creating the object for class: "
                + className, ite);
        } catch (InstantiationException ie) {
            throw new ConfigurationException("Error occurs during creating the object for class: "
                + className, ie);
        }
    }

    /**
     * <p>
     * Returns the property with the given name from the namespace.
     * </p>
     *
     * @param namespace the namespace of the configuration property.
     * @param name the name of the property.
     *
     * @return the property from the namespace with given name.
     *
     * @throws ConfigurationException if property doesn't exist or any other error occurs.
     */
    public static Property getProperty(String namespace, String name)
        throws ConfigurationException {
        ConfigManager cm = ConfigManager.getInstance();
        Property property = null;

        // get the property with given name.
        try {
            property = cm.getPropertyObject(namespace, name);

            // if property doesn't exist, throw exception here.
            if (property == null) {
                throw new ConfigurationException("Missing property: [" + name
                    + "] from namespace: " + namespace);
            }
        } catch (ConfigManagerException cme) {
            throw new ConfigurationException("Error occurs when geting property: " + name
                + " fromo namespace: " + namespace);
        }

        return property;
    }

    /**
     * <p>
     * Gets the configuration value from the given property.
     * </p>
     *
     * @param property the container value for property.
     * @param name the name of the value.
     * @param isRequired flag indicating if value is required.
     *
     * @return the configuration value.
     *
     * @throws ConfigurationException if isRequired flag is true and value doesn't exists or the
     *         value is empty.
     */
    public static String getValue(Property property, String name, boolean isRequired)
        throws ConfigurationException {
        String value = property.getValue(name);

        if ((value != null) && (value.trim().length() == 0)) {
            throw new ConfigurationException("Empty value: " + name + " in property: "
                + property.getName());
        }

        // if required and null.
        if (isRequired && (value == null)) {
            throw new ConfigurationException("Missing value: " + name + " in property: "
                + property.getName());
        }

        return value;
    }
}
