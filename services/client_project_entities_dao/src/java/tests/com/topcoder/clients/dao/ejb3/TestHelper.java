/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.lang.reflect.Field;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * Defines helper methods used in tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class TestHelper {

    /**
     * Represents the namespace to load search builder configuration.
     */
    static final String NAMESPACE = "com.topcoder.search.builder";

    /**
     * Represents the namespace to load dao configuration.
     */
    static final String EARNAME = "client_project_entities_dao";

    /**
     * Empty private constructor to make this class can not be instanced.
     */
    private TestHelper() {
    }

    /**
     * Gets the value of a private field in the given class. The field has the
     * given name. The value is retrieved from the given instance. If the
     * instance is null, the field is a static field. If any error occurs, null
     * is returned.
     *
     * @param type
     *                the class which the private field belongs to
     * @param instance
     *                the instance which the private field belongs to
     * @param name
     *                the name of the private field to be retrieved
     * @return the value of the private field
     */
    @SuppressWarnings("unchecked")
    public static Object getPrivateField(Class type, Object instance,
            String name) {
        Field field = null;
        Object obj = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Sets the value of a private field in the given class.
     *
     * @param type
     *                the class which the private field belongs to
     * @param instance
     *                the instance which the private field belongs to
     * @param name
     *                the name of the private field to be set
     * @param value
     *                the value to set
     */
    @SuppressWarnings("unchecked")
    public static void setPrivateField(Class type, Object instance,
            String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * Remove all the namespace.
     *
     * @throws Exception
     *                 to JUnit
     */
    @SuppressWarnings("unchecked")
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Add the namespace.
     *
     * @param filename
     *                the config filename
     * @throws Exception
     *                 to JUnit
     */
    public static void addConfig(String filename) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(filename);
    }

}
