/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This is a factory class that creates instances of ScreeningManager. This class allows the user to
 * acquire ScreeningManager instances in a single place, instead of using the specific constructors.
 * </p>
 * <p>
 * An example object factory config for ScreeningManager is as following:
 * <pre>
 *  <Property name="com/cronos/onlinereview/autoscreening/management/ScreeningManager">
 *     <Property name="type">
 *       <Value>com.cronos.onlinereview.autoscreening.management.db.DefaultDbScreeningManager</Value>
 *     </Property>
 *     <Property name="params">
 *       <Property name="param1">
 *         <Property name="name">
 *           <Value>connectionFactory</Value>
 *         </Property>
 *      </Property>
 *       <Property name="param2">
 *         <Property name="name">
 *           <Value>idGenerator</Value>
 *         </Property>
 *       </Property>
 *     </Property>
 *   </Property>
 *  </pre>
 * Take care of 'connectionFactory', and 'idGenerator'.
 * You should define them as ScreeningManager, because they are complex object.
 * </p>
 * <p>
 * This class is thread-safe by being stateless.
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public class ScreeningManagerFactory {

    /**
     * <p>
     * Represents the default configuration namespace to use for instantiation of the
     * ScreeningManager subclasses. It is used in the createScreeningManager() method.
     * </p>
     *
     *
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.autoscreening.management";

    /**
     * <p>
     * Private constructor to prevent outside instantiation. Does nothing.
     * </p>
     *
     */
    private ScreeningManagerFactory() {
        // empty
    }

    /**
     * <p>
     * Creates the ScreeningManager instance which is configured under the default namespace.
     * </p>
     *
     *
     *
     * @return the ScreeningManager instance
     * @throws ConfigurationException
     *             if any error occurs in configuration
     */
    public static ScreeningManager createScreeningManager() throws ConfigurationException {

        return createScreeningManager(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Creates the ScreeningManager instance which is configured under the specified namespace.
     * <code>ScreeningManager.class</code> is used as the key to look for and he namespace must be
     * preloaded.
     * </p>
     *
     *
     * @param namespace
     *            the configuration namespace to use
     * @return the ScreeningManager instance
     * @throws IllegalArgumentException
     *             if namespace is null or empty String
     * @throws ConfigurationException
     *             if any error occurs in configuration
     */
    public static ScreeningManager createScreeningManager(String namespace)
        throws ConfigurationException {
        Helper.checkString(namespace, "namespace");

        try {
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
                    namespace));
            return (ScreeningManager) objectFactory.createObject(ScreeningManager.class);
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("Failed to create ConfigManagerSpecificationFactory.",
                    e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("Failed to create ConfigManagerSpecificationFactory.",
                    e);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException(
                    "Failed to create ScreeningManager instance via configuration.", e);
        } catch (ClassCastException e){
            throw new ConfigurationException(
                    "Failed to create ScreeningManager instance, the configed"
                            + "object is not a ScreeningManager implement.");
        }
    }
}
