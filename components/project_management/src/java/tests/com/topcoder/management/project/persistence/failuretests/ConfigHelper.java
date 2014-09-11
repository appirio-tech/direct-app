/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.failuretests;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;

/**
 * <p>This is a helper class for existing accuracy test cases which provides a set of helpful methods manipulating with
 * configuration files.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ConfigHelper {

    /**
     * <p>A helper method to be used to initialize the specified configuration namespace with the configuration
     * properties provided by specified file. If specified namespace is already loaded to <code>ConfigurationManager
     * </code> then it is re-loaded with new configuration properties.</p>
     *
     * @param file a <code>File</code> providing the name of the file to load configuration file from.
     */
    public static final void loadConfiguration(File file) {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            configManager.add(file.getPath(), ConfigManager.EXCEPTIONS_ALL);
        } catch (ConfigManagerException e) {
            System.err.println("An error occurred while loading the configuration file '"
                + file.getPath() + "\n\n" + e);
        }
    }

    /**
     * <p>A helper method which releases all existing configuration namespaces from <code>Configuration Manager</code>.
     * </p>
     */
    public static final void releaseNamespaces() {
        ConfigManager configManager = ConfigManager.getInstance();
        Iterator iterator = configManager.getAllNamespaces();
        while (iterator.hasNext()) {
            try {
                configManager.removeNamespace((String) iterator.next());
            } catch (UnknownNamespaceException e) {
            }
        }
    }

    /**
     * <p>A helper method which removes the specified property from the specified namespace.</p>
     *
     * @param namespace a <code>String</code> providing the configuration namespace to remove the property from.
     * @param property a <code>String</code> providing the property to remove.
     * @return a <code>String</code> array providing the existing values for specified property.
     */
    public static final String[] removeProperty(String namespace, String property) {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            String[] previousValues = configManager.getStringArray(namespace, property);
            configManager.createTemporaryProperties(namespace);
            configManager.removeProperty(namespace, property);
            configManager.commit(namespace, "unittest");
            return previousValues;
        } catch (UnknownNamespaceException e) {
        } catch (ConfigManagerException e) {
        }
        return new String[0];
    }

    /**
     * <p>A helper method which removes the specified property from the specified namespace.</p>
     *
     * @param namespace a <code>String</code> providing the configuration namespace to remove the property from.
     * @param property a <code>String</code> providing the property to remove.
     * @param values a <code>String</code> array providing the previous values of property.
     */
    public static final void restoreProperty(String namespace, String property, String[] values) {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            configManager.createTemporaryProperties(namespace);
            configManager.setProperty(namespace, property, values);
            configManager.commit(namespace, "unittest");
        } catch (UnknownNamespaceException e) {
        } catch (ConfigManagerException e) {
        }
    }

    /**
     * <p>A helper method which initializes the specified property with specified value within the specified namespace.
     * </p>
     *
     * @param namespace a <code>String</code> providing the configuration namespace to remove the property from.
     * @param property a <code>String</code> providing the property to remove.
     * @param value a <code>String</code> providing the new value for property.
     * @return a <code>String</code> array providing the existing values for specified property.
     */
    public static final String[] setProperty(String namespace, String property, String value) {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            String[] previousValues = configManager.getStringArray(namespace, property);
            configManager.createTemporaryProperties(namespace);
            configManager.setProperty(namespace, property, value);
            configManager.commit(namespace, "unittest");
            return previousValues;
        } catch (UnknownNamespaceException e) {
        } catch (ConfigManagerException e) {
        }
        return new String[0];
    }

    /**
     * <p>A helper method to be used to <code>nullify</code> the singleton instance. The method uses a <code>Java
     * Reflection API</code> to access the field and initialize the field with <code>null</code> value. The operation
     * may fail if a <code>SecurityManager</code> prohibits such sort of accessing.</p>
     *
     * @param clazz a <code>Class</code> representing the class of the <code>Singleton</code> instance.
     * @param instanceName a <code>String</code> providing the name of the static field holding the reference to the
     * singleton instance.
     */
    public static final void releaseSingletonInstance(Class clazz, String instanceName) throws Exception {
        try {
            Field instanceField = clazz.getDeclaredField(instanceName);
            boolean accessibility = instanceField.isAccessible();
            instanceField.setAccessible(true);

            if (Modifier.isStatic(instanceField.getModifiers())) {
                instanceField.set(null, null);
            } else {
                System.out.println("An error occurred while trying to release the singleton instance - the "
                    + " '" + instanceName + "' field is not static");
            }

            instanceField.setAccessible(accessibility);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to release the singleton instance : " + e);
        }
    }

    /**
     * <p>A helper method which gets the specified property from the specified namespace.</p>
     *
     * @param namespace a <code>String</code> providing the configuration namespace to get the property from.
     * @param property a <code>String</code> providing the property to get.
     */
    public static final String getProperty(String namespace, String property) {
        ConfigManager configManager = ConfigManager.getInstance();
        try {
            return configManager.getString(namespace, property);
        } catch (UnknownNamespaceException e) {
            return null;
        }
    }


    /**
     * <p>Deletes specified configuration namespace from Config Manager.</p>
     *
     * @param namespace a <code>String</code> referencing the configuration namespace to remove from configuration
     * manager.
     */
    public static final void releaseNamespace(String namespace) {
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.existsNamespace(namespace)) {
            try {
                configManager.removeNamespace(namespace);
            } catch (UnknownNamespaceException e) {
            }
        }
    }

    /**
     * <p>A helper method to be used to initialize the specified configuration namespace with the configuration
     * properties provided by specified file. If specified namespace is already loaded to <code>ConfigurationManager
     * </code> then it is re-loaded with new configuration properties.</p>
     *
     * @param namespace a <code>String</code> providing the namespace to load configuration for.
     * @param filename a <code>String</code> providing the name of the file to load configuration file from.
     * @param format a <code>String</code> specifying the format of the configuration file.
     */
    public static final void loadConfiguration(String namespace, String filename, String format) {
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.existsNamespace(namespace)) {
            try {
                configManager.removeNamespace(namespace);
            } catch (UnknownNamespaceException e) {
            }
        }

        try {
            configManager.add(namespace, filename, format);
        } catch (ConfigManagerException e) {
            System.err.println("An error occurred while loading the configuration namespace '"
                + namespace + "' from file : " + filename + "\n\n" + e);
        }
    }
}
